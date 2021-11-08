package com.epam.esm.zotov.mjcschool.api.controller.certificate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.api.dto.CertificateDto;
import com.epam.esm.zotov.mjcschool.api.dto.ListDto;
import com.epam.esm.zotov.mjcschool.api.exception.NoResourceFoundException;
import com.epam.esm.zotov.mjcschool.api.exception.RequestNotExecutedException;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.service.certificate.CertificateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//TODO validator
@RestController
@RequestMapping("/certificates")
public class CertificateControllerImpl implements CertificateController {
    private CertificateService certificateService;

    @Autowired
    public CertificateControllerImpl(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @Override
    public ListDto<CertificateDto> getPage(int limit, long afterId) {
        List<Certificate> certificates = certificateService.getPage(limit, afterId);
        if (Objects.isNull(certificates) || certificates.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            List<CertificateDto> dtos = new ArrayList<>();
            for (Certificate certificate : certificates) {
                CertificateDto dto = new CertificateDto(certificate);
                addCommonHateoasLinks(dto);
                dtos.add(dto);
            }
            ListDto<CertificateDto> listDto = new ListDto<CertificateDto>(dtos);
            long lastId = dtos.get(dtos.size() - 1).getCertificateId();
            listDto.add(linkTo(methodOn(CertificateControllerImpl.class).getPage(limit, lastId)).withRel("next"));
            long firstId = dtos.stream().findFirst().get().getCertificateId();
            listDto.add(
                    linkTo(methodOn(CertificateControllerImpl.class).getPage(limit, firstId - limit)).withRel("last"));
            return listDto;
        }
    }

    @Override
    public CertificateDto getById(long targetId) {
        Optional<Certificate> certificate = certificateService.getById(targetId);
        if (certificate.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            CertificateDto dto = new CertificateDto(certificate.get());
            addCommonHateoasLinks(dto);
            return dto;
        }
    }

    @Override
    public CertificateDto save(CertificateDto certificate) {
        Optional<Certificate> savedCertificate = certificateService.save(certificate.convertToCertificate());
        if (savedCertificate.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            CertificateDto dto = new CertificateDto(savedCertificate.get());
            addCommonHateoasLinks(dto);
            return dto;
        }
    }

    @Override
    public void delete(long targetId) {
        if (!certificateService.delete(targetId)) {
            throw new RequestNotExecutedException();
        }
    }

    @Override
    public CertificateDto selectiveUpdate(long targetId, CertificateDto updatedCertificate) {
        updatedCertificate.setCertificateId(targetId);
        Optional<Certificate> returnedCertificate = certificateService
                .selectiveUpdate(updatedCertificate.convertToCertificate());
        if (returnedCertificate.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            CertificateDto dto = new CertificateDto(returnedCertificate.get());
            addCommonHateoasLinks(dto);
            return dto;
        }
    }

    @Override
    public List<CertificateDto> search(Map<String, String> searchParams) {
        List<Certificate> certificates = certificateService.search(searchParams);
        if (certificates.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            List<CertificateDto> dtos = new ArrayList<>();
            for (Certificate certificate : certificates) {
                CertificateDto dto = new CertificateDto(certificate);
                addCommonHateoasLinks(dto);
                dtos.add(dto);
            }
            return dtos;
        }
    }

    private void addCommonHateoasLinks(CertificateDto certificate) {
        certificate.add(linkTo(methodOn(CertificateControllerImpl.class).getById(certificate.getCertificateId()))
                .withSelfRel());
    }
}