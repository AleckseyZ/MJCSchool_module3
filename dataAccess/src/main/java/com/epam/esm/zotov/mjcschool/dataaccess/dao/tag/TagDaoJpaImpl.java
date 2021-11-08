package com.epam.esm.zotov.mjcschool.dataaccess.dao.tag;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:jpa.tag.properties")
public class TagDaoJpaImpl implements TagDao {
    @Value("${jpa.tag.idParam}")
    private String idParam;
    @Value("${jpa.tag.nameParam}")
    private String nameParam;
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Tag> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(idParam)));
        List<Tag> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        return resultList;
    }

    @Override
    public List<Tag> getPage(int limit, long afterId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        criteriaQuery.select(root).where(criteriaBuilder.gt(root.get(idParam), afterId))
                .orderBy(criteriaBuilder.asc(root.get(idParam)));
        List<Tag> resultList = entityManager.createQuery(criteriaQuery).setMaxResults(limit).getResultList();

        return resultList;
    }

    @Override
    public Optional<Tag> getById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> getByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(nameParam), name));
        List<Tag> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        Optional<Tag> result = resultList.stream().findAny();

        return result;
    }

    @Transactional
    @Override
    public Optional<Tag> save(Tag tag) {
        entityManager.persist(tag);
        if (!entityManager.contains(tag)) {
            tag = null;
        }
        return Optional.ofNullable(tag);
    }

    @Transactional
    @Override
    public boolean delete(long id) {
        boolean isSuccessful = false;
        Optional<Tag> target = getById(id);
        if (target.isPresent()) {
            entityManager.remove(target.get());
            isSuccessful = true;
        }
        return isSuccessful;
    }
}