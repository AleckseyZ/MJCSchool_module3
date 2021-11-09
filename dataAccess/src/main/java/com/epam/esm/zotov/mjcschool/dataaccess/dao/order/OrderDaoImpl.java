package com.epam.esm.zotov.mjcschool.dataaccess.dao.order;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.epam.esm.zotov.mjcschool.dataaccess.dao.certificate.CertificateDao;
import com.epam.esm.zotov.mjcschool.dataaccess.dao.user.UserDao;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;
import com.epam.esm.zotov.mjcschool.dataaccess.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@PropertySource("classpath:jpa.order.properties")
public class OrderDaoImpl implements OrderDao {
    @Value("${jpa.order.idParam}")
    private String idParam;
    @Value("${jpa.order.refundParam}")
    private String refundParam;
    private EntityManager entityManager;
    private UserDao userDao;
    private CertificateDao certificateDao;

    @Autowired
    public OrderDaoImpl(UserDao userDao, CertificateDao certificateDao) {
        this.userDao = userDao;
        this.certificateDao = certificateDao;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(idParam)));
        List<Order> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        return resultList;
    }

    @Override
    public List<Order> getPage(int limit, long afterId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root).where(criteriaBuilder.gt(root.get(idParam), afterId))
                .orderBy(criteriaBuilder.asc(root.get(idParam)));
        List<Order> resultList = entityManager.createQuery(criteriaQuery).setMaxResults(limit).getResultList();

        return resultList;
    }

    @Override
    public Optional<Order> getById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    @Transactional
    public Optional<Order> save(Order order) {
        Optional<User> user = userDao.getById(order.getUser().getUserId());
        Optional<Certificate> certificate = certificateDao.getById(order.getCertificate().getCertificateId());
        if (user.isPresent() && certificate.isPresent()) {
            order.setUser(user.get());
            order.setCertificate(certificate.get());
            entityManager.persist(order);
        }
        if (!entityManager.contains(order)) {
            order = null;
        }
        return Optional.ofNullable(order);
    }

    @Override
    @Transactional
    public Optional<Order> refund(long orderId) {
        Optional<Order> order = getById(orderId);
        if (order.isPresent()) {
            order.get().setIsRefunded(true);
        }
        return order;
    }
}