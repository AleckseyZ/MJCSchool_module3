package com.epam.esm.zotov.mjcschool.dataaccess.dao.order;

import com.epam.esm.zotov.mjcschool.dataaccess.dao.CreateDao;
import com.epam.esm.zotov.mjcschool.dataaccess.dao.ReadDao;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;

public interface OrderDao extends CreateDao<Order>, ReadDao<Order> {
}