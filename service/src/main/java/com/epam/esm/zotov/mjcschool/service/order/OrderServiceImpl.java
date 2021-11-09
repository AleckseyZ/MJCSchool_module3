package com.epam.esm.zotov.mjcschool.service.order;

import java.util.List;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.dao.order.OrderDao;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public List<Order> getPage(int limit, long afterId) {
        return orderDao.getPage(limit, afterId);
    }

    @Override
    public Optional<Order> getById(long id) {
        return orderDao.getById(id);
    }

    @Override
    public Optional<Order> save(Order order) {
        return orderDao.save(order);
    }

    @Override
    public Optional<Order> refund(long orderId) {
        return orderDao.refund(orderId);
    }
}