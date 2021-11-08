package com.epam.esm.zotov.mjcschool.api.controller.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.api.dto.ListDto;
import com.epam.esm.zotov.mjcschool.api.dto.OrderDto;
import com.epam.esm.zotov.mjcschool.api.dto.UserDto;
import com.epam.esm.zotov.mjcschool.api.exception.NoResourceFoundException;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;
import com.epam.esm.zotov.mjcschool.dataaccess.model.User;
import com.epam.esm.zotov.mjcschool.service.order.OrderService;
import com.epam.esm.zotov.mjcschool.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//TODO fix magic values
@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserController {
    private UserService userService;
    private OrderService orderService;

    @Autowired
    public UserControllerImpl(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public ListDto<UserDto> getPage(int limit, long afterId) {
        List<User> users = userService.getPage(limit, afterId);
        if (Objects.isNull(users) || users.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            List<UserDto> dtos = new ArrayList<>();
            for (User user : users) {
                UserDto dto = new UserDto(user);
                addCommonUserHateoasLinks(dto);
                dtos.add(dto);
            }
            ListDto<UserDto> listDto = new ListDto<UserDto>(dtos);
            long lastId = dtos.get(dtos.size() - 1).getUserId();
            listDto.add(linkTo(methodOn(UserControllerImpl.class).getPage(limit, lastId)).withRel("next"));
            long firstId = dtos.stream().findFirst().get().getUserId();
            listDto.add(linkTo(methodOn(UserControllerImpl.class).getPage(limit, firstId - limit)).withRel("last"));
            return listDto;
        }
    }

    @Override
    public UserDto getById(long targetId) {
        Optional<User> user = userService.getById(targetId);
        if (user.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            UserDto dto = new UserDto(user.get());
            addCommonUserHateoasLinks(dto);
            return dto;
        }
    }

    // TODO might move from order id to orded count
    @Override
    public OrderDto getOrder(long userId, long orderId) {
        Optional<Order> order = orderService.getById(userId);
        if (order.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            OrderDto dto = new OrderDto(order.get());
            dto.add(linkTo(methodOn(UserControllerImpl.class).getOrder(dto.getUser().getUserId(), dto.getOrderId()))
                    .withSelfRel());
            return dto;
        }
    }

    // TODO page
    @Override
    public List<OrderDto> getUsersOrders(long userId) {
        Optional<User> user = userService.getById(userId);
        if (user.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            List<Order> orders = user.get().getOrders();
            List<OrderDto> dtos = new ArrayList<>();
            for (Order order : orders) {
                dtos.add(new OrderDto(order));
            }
            return dtos;
        }
    }

    private void addCommonUserHateoasLinks(UserDto user) {
        user.add(linkTo(methodOn(UserControllerImpl.class).getById(user.getUserId())).withSelfRel());
        user.add(linkTo(methodOn(UserControllerImpl.class).getUsersOrders(user.getUserId())).withRel("orders"));
    }
}