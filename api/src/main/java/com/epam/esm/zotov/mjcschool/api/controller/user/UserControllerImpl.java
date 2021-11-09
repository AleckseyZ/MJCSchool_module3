package com.epam.esm.zotov.mjcschool.api.controller.user;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.esm.zotov.mjcschool.api.dto.ListDto;
import com.epam.esm.zotov.mjcschool.api.dto.OrderDto;
import com.epam.esm.zotov.mjcschool.api.dto.UserDto;
import com.epam.esm.zotov.mjcschool.api.exception.NoResourceFoundException;
import com.epam.esm.zotov.mjcschool.api.exception.RequestNotExecutedException;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;
import com.epam.esm.zotov.mjcschool.dataaccess.model.User;
import com.epam.esm.zotov.mjcschool.service.certificate.CertificateService;
import com.epam.esm.zotov.mjcschool.service.order.OrderService;
import com.epam.esm.zotov.mjcschool.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements UserController {
    private static final String ORDER_RELATION = "orders";
    private static final int DEFAULT_LIMIT = 10;
    private static final int DEFAULT_START_ID = 0;
    private UserService userService;
    private CertificateService certificateService;
    private OrderService orderService;

    @Autowired
    public UserControllerImpl(UserService userService, CertificateService certificateService,
            OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
        this.certificateService = certificateService;
    }

    @Override
    public ListDto<UserDto> getPage(int limit, long afterId) {
        List<User> users = userService.getPage(limit, afterId);
        if (Objects.isNull(users) || users.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            ListDto<UserDto> listDto = new ListDto<UserDto>(
                    users.stream().map(user -> new UserDto(user)).collect(Collectors.toList()));

            listDto.getList().stream().forEach(userDto -> addCommonUserHateoasLinks(userDto, limit, afterId));
            listDto.addNextPageLink(methodOn(UserControllerImpl.class).getPage(limit, limit + afterId));
            listDto.addPreviousPageLink(methodOn(UserControllerImpl.class).getPage(limit, afterId - limit));
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
            addCommonUserHateoasLinks(dto, DEFAULT_LIMIT, DEFAULT_START_ID);
            return dto;
        }
    }

    @Override
    public OrderDto getOrder(long userId, int orderPosition) {
        Optional<User> user = userService.getById(userId);
        if (user.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            List<Order> orderList = user.get().getOrders();
            if (Objects.isNull(orderList) || orderList.isEmpty() || orderList.size() >= orderPosition) {
                throw new NoResourceFoundException();
            } else {
                OrderDto dto = new OrderDto(orderList.get(orderPosition));
                addCommonOrderHateoasLinks(dto, orderPosition);
                return dto;
            }
        }
    }

    @Override
    public ListDto<OrderDto> getUsersOrders(long userId, int startPosition, int limit) {
        Optional<User> user = userService.getById(userId);
        if (user.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            List<Order> orders = user.get().getOrders();
            if (Objects.isNull(orders) || orders.isEmpty() || orders.size() < startPosition) {
                throw new NoResourceFoundException();
            } else {
                int ordersCount = orders.size();
                List<Order> sublist;
                if (ordersCount > startPosition + limit) {
                    sublist = orders.subList(startPosition, startPosition + limit);
                } else {
                    sublist = orders.subList(startPosition, ordersCount);
                }
                List<OrderDto> orderSublist = sublist.stream().map(order -> new OrderDto(order))
                        .collect(Collectors.toList());
                int sublistSize = orderSublist.size();
                for (int i = 0; i < sublistSize; i++) {
                    addCommonOrderHateoasLinks(orderSublist.get(i), startPosition + i);
                }

                ListDto<OrderDto> listDto = new ListDto<OrderDto>(orderSublist);
                listDto.addNextPageLink(methodOn(UserControllerImpl.class).getPage(limit, limit + startPosition));
                listDto.addPreviousPageLink(methodOn(UserControllerImpl.class).getPage(limit, startPosition - limit));
                return listDto;
            }

        }
    }

    @Override
    public OrderDto addOrder(long userId, long certificateId) {
        Optional<User> user = userService.getById(userId);
        Optional<Certificate> certificate = certificateService.getById(certificateId);
        if (user.isEmpty() || certificate.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            Order order = Order.builder().purchaseTime(Instant.now()).purchasePrice(certificate.get().getPrice())
                    .certificate(certificate.get()).user(user.get()).isRefunded(false).build();

            Optional<Order> savedOrderOptional = orderService.save(order);

            if (savedOrderOptional.isEmpty()) {
                throw new RequestNotExecutedException();
            } else {
                Order savedOrder = savedOrderOptional.get();
                OrderDto dto = new OrderDto(savedOrder);
                addCommonOrderHateoasLinks(dto, savedOrder.getUser().getOrders().indexOf(savedOrder));
                return dto;
            }
        }
    }

    @Override
    public OrderDto refundOrder(long userId, int orderPosition) {
        Optional<User> user = userService.getById(userId);
        if (user.isEmpty() || Objects.isNull(user.get().getOrders()) || user.get().getOrders().isEmpty()
                || user.get().getOrders().size() < orderPosition) {
            throw new NoResourceFoundException();
        } else {
            Optional<Order> order = orderService.refund(user.get().getOrders().get(orderPosition).getOrderId());
            if (order.isEmpty()) {
                throw new RequestNotExecutedException();
            } else {
                OrderDto dto = new OrderDto(order.get());
                addCommonOrderHateoasLinks(dto, orderPosition);
                return dto;
            }
        }
    }

    private void addCommonUserHateoasLinks(UserDto user, int limit, long afterId) {
        user.add(linkTo(methodOn(UserControllerImpl.class).getById(user.getUserId())).withSelfRel());
        user.add(linkTo(methodOn(UserControllerImpl.class).getUsersOrders(user.getUserId(), limit, DEFAULT_START_ID))
                .withRel(ORDER_RELATION));
    }

    private void addCommonOrderHateoasLinks(OrderDto order, int orderPosition) {
        order.add(linkTo(methodOn(UserControllerImpl.class).getOrder(order.getUser().getUserId(), orderPosition))
                .withSelfRel());
    }
}