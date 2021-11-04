package com.epam.esm.zotov.mjcschool.api.controller.certificate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.api.exception.NoResourceFoundException;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.service.certificate.CertificateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//TODO validator
//TODO add HATEOAS to "pages"
@RestController
@RequestMapping("/certificates")
public class CertificateControllerImpl implements CertificateController {
    private CertificateService certificateService;

    @Autowired
    public CertificateControllerImpl(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @Override
    public List<Certificate> getPage(int limit, long afterId) {
        List<Certificate> certificates = certificateService.getPage(limit, afterId);
        if (certificates.isEmpty()) {
            throw new NoResourceFoundException();
        }
        for (Certificate certificate : certificates) {
            addCommonHateoasLinks(certificate);
        }
        return certificates;
    }

    @Override
    public Certificate getById(long targetId) {
        Optional<Certificate> certificate = certificateService.getById(targetId);
        if (certificate.isEmpty()) {
            throw new NoResourceFoundException();
        }
        addCommonHateoasLinks(certificate.get());
        return certificate.get();
    }

    @Override
    public Certificate save(Certificate certificate) {
        Certificate savedCertificate = certificateService.save(certificate).get();
        addCommonHateoasLinks(savedCertificate);
        return savedCertificate;
    }

    @Override
    public void delete(long targetId) {
        certificateService.delete(targetId);
    }

    @Override
    public Optional<Certificate> selectiveUpdate(long targetId, Certificate updatedCertificate) {
        updatedCertificate.setCertificateId(targetId);
        Optional<Certificate> returnedCertificate = certificateService.selectiveUpdate(updatedCertificate);
        addCommonHateoasLinks(returnedCertificate.get());
        return returnedCertificate;
    }

    @Override
    public List<Certificate> search(Map<String, String> searchParams) {
        List<Certificate> certificates = certificateService.search(searchParams);
        if (certificates.isEmpty()) {
            throw new NoResourceFoundException();
        }
        for (Certificate certificate : certificates) {
            addCommonHateoasLinks(certificate);
        }
        return certificates;
    }

    private void addCommonHateoasLinks(Certificate certificate) {
        certificate.add(linkTo(methodOn(CertificateControllerImpl.class).getById(certificate.getCertificateId()))
                .withSelfRel());
    }
}