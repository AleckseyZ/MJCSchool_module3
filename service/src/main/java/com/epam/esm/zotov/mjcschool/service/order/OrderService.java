package com.epam.esm.zotov.mjcschool.service.order;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;
import com.epam.esm.zotov.mjcschool.service.CreateService;
import com.epam.esm.zotov.mjcschool.service.ReadService;

public interface OrderService extends CreateService<Order>, ReadService<Order> {
    public Optional<Order> refund(long orderId);
}