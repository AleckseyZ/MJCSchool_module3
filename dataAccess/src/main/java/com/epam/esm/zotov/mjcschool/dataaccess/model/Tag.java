package com.epam.esm.zotov.mjcschool.dataaccess.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tags")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;
    @Column(name = "name", unique = true)
    private String name;
    @OneToMany(mappedBy = "user")
    private @ToString.Exclude @EqualsAndHashCode.Exclude List<Order> orders = new ArrayList<>();
    @Column(name = "last_update")
    private @ToString.Exclude @EqualsAndHashCode.Exclude Instant lastUpdate;

    public Tag(Long tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }

    @PrePersist
    @PreUpdate
    private void update() {
        setLastUpdate(Instant.now());
    }
}