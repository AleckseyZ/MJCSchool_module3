package com.epam.esm.zotov.mjcschool.dataaccess.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Cacheable(false)
@Table(name = "certificates")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long certificateId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "duration")
    private Short duration;
    @Column(name = "create_date", updatable = false)
    private Instant createDate;
    @Column(name = "last_update_date")
    private Instant lastUpdateDate;
    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(name = "certificates_tags", joinColumns = @JoinColumn(name = "certificate_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private @ToString.Exclude @EqualsAndHashCode.Exclude List<Tag> tags;
    @OneToMany(mappedBy = "certificate")
    private @ToString.Exclude @EqualsAndHashCode.Exclude List<Order> orders;

    public Certificate() {
        tags = new ArrayList<Tag>();
        orders = new ArrayList<Order>();
    }

    public Certificate(Long certificateId, String name, String description, BigDecimal price, Short duration,
            Instant createDate, Instant lastUpdateDate, List<Tag> tags) {
        this.certificateId = certificateId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }
}