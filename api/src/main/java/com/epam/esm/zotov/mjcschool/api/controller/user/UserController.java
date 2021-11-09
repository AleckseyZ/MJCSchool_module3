package com.epam.esm.zotov.mjcschool.api.controller.user;

import com.epam.esm.zotov.mjcschool.api.controller.ReadController;
import com.epam.esm.zotov.mjcschool.api.dto.ListDto;
import com.epam.esm.zotov.mjcschool.api.dto.OrderDto;
import com.epam.esm.zotov.mjcschool.api.dto.UserDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserController extends ReadController<UserDto> {
    @GetMapping("/{userId}/orders")
    public ListDto<OrderDto> getUsersOrders(@PathVariable long userId, @RequestParam int limit,
            @RequestParam int startPosition);

    @GetMapping("/{userId}/orders/{orderPosition}")
    public OrderDto getOrder(@PathVariable long userId, @PathVariable int orderPosition);

    @PostMapping("/{userId}/orders")
    public OrderDto addOrder(@PathVariable long userId, @RequestParam long certificateId);

    @PutMapping("/{userId}/orders/{orderPosition}")
    public OrderDto refundOrder(@PathVariable long userId, @PathVariable int orderPosition);
}