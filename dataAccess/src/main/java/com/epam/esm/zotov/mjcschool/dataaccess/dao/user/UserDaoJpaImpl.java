package com.epam.esm.zotov.mjcschool.dataaccess.dao.user;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.epam.esm.zotov.mjcschool.dataaccess.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:jpa.user.properties")
public class UserDaoJpaImpl implements UserDao {
    @Value("${jpa.user.idParam}")
    private String idParam;
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(idParam)));
        List<User> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        return resultList;
    }

    @Override
    public List<User> getPage(int limit, long afterId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.select(root).where(criteriaBuilder.gt(root.get(idParam), afterId))
                .orderBy(criteriaBuilder.asc(root.get(idParam)));
        List<User> resultList = entityManager.createQuery(criteriaQuery).setMaxResults(limit).getResultList();

        return resultList;
    }

    @Override
    public Optional<User> getById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }
}