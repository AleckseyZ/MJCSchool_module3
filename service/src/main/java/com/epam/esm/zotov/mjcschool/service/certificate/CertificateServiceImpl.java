package com.epam.esm.zotov.mjcschool.service.certificate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.esm.zotov.mjcschool.dataaccess.dao.certificate.CertificateDao;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:search.properties")
public class CertificateServiceImpl implements CertificateService {
    @Value("${search.tagName}")
    private String tagNameParam;
    @Value("${search.name}")
    private String nameParam;
    @Value("${search.description}")
    private String descriptionParam;
    @Value("${search.sortByName}")
    private String sortByNameParam;
    @Value("${search.sortByDate}")
    private String sortByDateParam;
    @Value("${search.ascending}")
    private String ascending;
    @Value("${search.descending}")
    private String descending;
    private static final String TAG_SEPARATOR = "+";
    private CertificateDao certificateDao;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public List<Certificate> getAll() {
        return certificateDao.getAll();
    }

    @Override
    public List<Certificate> getPage(int limit, long afterId) {
        return certificateDao.getPage(limit, afterId);
    }

    @Override
    public Optional<Certificate> getById(long id) {
        return certificateDao.getById(id);
    }

    @Override
    public Optional<Certificate> save(Certificate certificate) {
        return certificateDao.save(certificate);
    }

    @Override
    public boolean delete(long id) {
        return certificateDao.delete(id);
    }

    @Override
    public Optional<Certificate> selectiveUpdate(Certificate updatedCertificate) {
        return certificateDao.selectiveUpdate(updatedCertificate);
    }

    @Override
    public List<Certificate> search(Map<String, String> searchParams) {
        List<Certificate> filteredCertificates = certificateDao.getAll();

        String tagNames = searchParams.get(tagNameParam);
        if (Objects.nonNull(tagNames)) {
            filteredCertificates = searchByTags(filteredCertificates, Arrays.asList(tagNames.split(TAG_SEPARATOR)));
        }

        String name = searchParams.get(nameParam);
        if (Objects.nonNull(name)) {
            filteredCertificates = searchByName(filteredCertificates, name);
        }

        String description = searchParams.get(descriptionParam);
        if (Objects.nonNull(description)) {
            filteredCertificates = searchByDescription(filteredCertificates, description);
        }

        String dateSort = searchParams.get(sortByDateParam);
        if (Objects.nonNull(dateSort)) {
            filteredCertificates = sortByDate(filteredCertificates, dateSort);
        }

        String nameSort = searchParams.get(sortByNameParam);
        if (Objects.nonNull(nameSort)) {
            filteredCertificates = sortByName(filteredCertificates, nameSort);
        }

        return filteredCertificates;
    }

    private List<Certificate> searchByTags(List<Certificate> certificates, List<String> desiredTags) {
        List<Certificate> fittingCertificates = new ArrayList<>();
        for (Certificate certificate : certificates) {
            List<String> tagNames = certificate.getTags().stream().map(tag -> tag.getName())
                    .collect(Collectors.toList());
            if (tagNames.containsAll(desiredTags)) {
                fittingCertificates.add(certificate);
            }
        }
        return fittingCertificates;
    }

    private List<Certificate> searchByName(List<Certificate> certificates, String name) {
        List<Certificate> fittingCertificates = certificates.stream()
                .filter(certificate -> certificate.getName().contains(name)).collect(Collectors.toList());
        return fittingCertificates;
    }

    private List<Certificate> searchByDescription(List<Certificate> certificates, String description) {
        List<Certificate> fittingCertificates = certificates.stream()
                .filter(certificate -> certificate.getDescription().contains(description)).collect(Collectors.toList());
        return fittingCertificates;
    }

    private List<Certificate> sortByDate(List<Certificate> certificates, String sortType) {
        if (sortType.equals(ascending)) {
            certificates = certificates.stream()
                    .sorted((cert1, cert2) -> cert1.getCreateDate().compareTo(cert2.getCreateDate()))
                    .collect(Collectors.toList());
        } else if (sortType.equals(descending)) {
            certificates = certificates.stream()
                    .sorted((cert1, cert2) -> cert2.getCreateDate().compareTo(cert1.getCreateDate()))
                    .collect(Collectors.toList());
        }
        return certificates;
    }

    private List<Certificate> sortByName(List<Certificate> certificates, String sortType) {
        if (sortType.equals(ascending)) {
            certificates = certificates.stream().sorted((cert1, cert2) -> cert1.getName().compareTo(cert2.getName()))
                    .collect(Collectors.toList());
        } else if (sortType.equals(descending)) {
            certificates = certificates.stream().sorted((cert1, cert2) -> cert2.getName().compareTo(cert1.getName()))
                    .collect(Collectors.toList());
        }
        return certificates;
    }
}