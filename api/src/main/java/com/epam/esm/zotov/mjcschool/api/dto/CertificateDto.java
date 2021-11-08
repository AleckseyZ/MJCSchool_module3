package com.epam.esm.zotov.mjcschool.api.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class CertificateDto extends RepresentationModel<CertificateDto> {
    private Long certificateId;
    private String name;
    private String description;
    private BigDecimal price;
    private Short duration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant lastUpdateDate;
    private String[] tags;

    public Certificate convertToCertificate() {
        List<Tag> tagList = new ArrayList<Tag>();
        for (String tag : tags) {
            tagList.add(new Tag(null, tag));
        }
        return new Certificate(certificateId, name, description, price, duration, createDate, lastUpdateDate, tagList);
    }

    public CertificateDto() {
        tags = new String[0];
    }

    public CertificateDto(Certificate certificate) {
        certificateId = certificate.getCertificateId();
        name = certificate.getName();
        description = certificate.getDescription();
        price = certificate.getPrice();
        duration = certificate.getDuration();
        createDate = certificate.getCreateDate();
        lastUpdateDate = certificate.getLastUpdateDate();
        List<Tag> tagList = certificate.getTags();
        if (Objects.nonNull(tagList) && !tagList.isEmpty()) {
            tags = tagList.stream().map(tag -> tag.getName()).toArray(String[]::new);
        } else {
            tags = new String[0];
        }
    }
}