package com.epam.esm.zotov.mjcschool.dataaccess.dao.order;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.dao.CreateDao;
import com.epam.esm.zotov.mjcschool.dataaccess.dao.ReadDao;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;

public interface OrderDao extends CreateDao<Order>, ReadDao<Order> {
    public Optional<Order> refund(long orderId);
}