package com.epam.esm.zotov.mjcschool.api.controller.user;

import java.util.List;

import com.epam.esm.zotov.mjcschool.api.controller.ReadController;
import com.epam.esm.zotov.mjcschool.api.dto.OrderDto;
import com.epam.esm.zotov.mjcschool.api.dto.UserDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserController extends ReadController<UserDto> {
    @GetMapping("/{userId}/orders")
    public List<OrderDto> getUsersOrders(@PathVariable long userId);

    @GetMapping("/{userId}/orders/{orderId}")
    public OrderDto getOrder(@PathVariable long userId, @PathVariable long orderId);
}