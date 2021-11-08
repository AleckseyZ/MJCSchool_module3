package com.epam.esm.zotov.mjcschool.dataaccess.dao.certificate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.zotov.mjcschool.dataaccess.dao.tag.TagDao;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:jpa.certificate.properties")
public class CertificateDaoJpaImpl implements CertificateDao {
    private EntityManager entityManager;
    private TagDao tagDao;
    @Value("${jpa.certificate.idParam}")
    private String idParam;
    @Value("${jpa.certificate.nameParam}")
    private String nameParam;
    @Value("${jpa.certificate.descriptionParam}")
    private String descriptionParam;
    @Value("${jpa.certificate.priceParam}")
    private String priceParam;
    @Value("${jpa.certificate.durationParam}")
    private String durationParam;
    @Value("${jpa.certificate.updateDate}")
    private String updateDateParam;
    private static final int EXPECTED_UPDATE_ROWS = 1;

    @Autowired
    public CertificateDaoJpaImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Certificate> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);

        criteriaQuery.select(root).orderBy(criteriaBuilder.asc(root.get(idParam)));
        List<Certificate> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        return resultList;
    }

    @Override
    public List<Certificate> getPage(int limit, long afterId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);

        criteriaQuery.select(root).where(criteriaBuilder.gt(root.get(idParam), afterId))
                .orderBy(criteriaBuilder.asc(root.get(idParam)));
        List<Certificate> resultList = entityManager.createQuery(criteriaQuery).setMaxResults(limit).getResultList();

        return resultList;
    }

    @Override
    public Optional<Certificate> getById(long id) {
        return Optional.ofNullable(entityManager.find(Certificate.class, id));
    }

    @Override
    @Transactional
    public Optional<Certificate> save(Certificate certificate) {
        certificate.setTags(processTags(certificate.getTags()));
        certificate.setCreateDate(Instant.now());
        certificate.setLastUpdateDate(Instant.now());

        entityManager.persist(certificate);
        if (!entityManager.contains(certificate)) {
            certificate = null;
        }

        return Optional.ofNullable(certificate);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        boolean isSuccessful = false;
        Optional<Certificate> target = getById(id);
        if (target.isPresent() && (Objects.isNull(target.get().getOrders()) || target.get().getOrders().isEmpty())) {
            entityManager.remove(target.get());
            isSuccessful = true;
        }
        return isSuccessful;
    }

    @Override
    @Transactional()
    public Optional<Certificate> selectiveUpdate(Certificate updatedCertificate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Certificate> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Certificate.class);
        Root<Certificate> root = criteriaUpdate.from(Certificate.class);

        Certificate original = getById(updatedCertificate.getCertificateId()).get();

        criteriaUpdate.set(nameParam, coalesce(updatedCertificate.getName(), original.getName()));
        criteriaUpdate.set(descriptionParam, coalesce(updatedCertificate.getDescription(), original.getDescription()));
        criteriaUpdate.set(priceParam, coalesce(updatedCertificate.getPrice(), original.getPrice()));
        criteriaUpdate.set(durationParam, coalesce(updatedCertificate.getDuration(), original.getDuration()));
        criteriaUpdate.set(updateDateParam, Instant.now());

        criteriaUpdate.where(criteriaBuilder.equal(root.get(idParam), updatedCertificate.getCertificateId()));
        int affectedRows = entityManager.createQuery(criteriaUpdate).executeUpdate();
        entityManager.flush();
        entityManager.clear();

        Certificate result = null;
        if (affectedRows == EXPECTED_UPDATE_ROWS) {
            result = getById(updatedCertificate.getCertificateId()).get();
        }

        List<Tag> updatedTags = updatedCertificate.getTags();

        if (Objects.nonNull(updatedTags)) {
            result.setTags(processTags(updatedTags));
        }

        return Optional.ofNullable(result);
    }

    private List<Tag> processTags(List<Tag> tags) {
        if (Objects.nonNull(tags) && !tags.isEmpty()) {
            List<Tag> processedTags = new ArrayList<Tag>();

            tags.stream().forEach(tag -> {
                Optional<Tag> tempTag = tagDao.getByName(tag.getName());
                if (tempTag.isPresent()) {
                    processedTags.add(tempTag.get());
                } else {
                    processedTags.add(tagDao.save(tag).get());
                }
            });
            tags = processedTags;
        }

        return tags;
    }

    private Object coalesce(Object... objects) {
        Object firstNotNull = null;

        for (Object object : objects) {
            if (Objects.nonNull(object)) {
                firstNotNull = object;
                break;
            }
        }

        return firstNotNull;
    }
}