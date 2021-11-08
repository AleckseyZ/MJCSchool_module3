package com.epam.esm.zotov.mjcschool.dataaccess.model;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "purchase_timestamp")
    private Instant purchaseTime;
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;
    @Column(name = "is_refunded")
    private Boolean isRefunded;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private @ToString.Exclude @EqualsAndHashCode.Exclude User user;
    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private @ToString.Exclude @EqualsAndHashCode.Exclude Certificate certificate;
}