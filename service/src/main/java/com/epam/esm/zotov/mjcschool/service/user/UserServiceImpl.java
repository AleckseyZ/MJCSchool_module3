package com.epam.esm.zotov.mjcschool.service.user;

import java.util.List;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.dao.user.UserDao;
import com.epam.esm.zotov.mjcschool.dataaccess.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public List<User> getPage(int limit, long afterId) {
        return userDao.getPage(limit, afterId);
    }

    @Override
    public Optional<User> getById(long id) {
        return userDao.getById(id);
    }
}