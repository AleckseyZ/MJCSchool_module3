package com.epam.esm.zotov.mjcschool.api.dto;

import java.time.Instant;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;
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
public class OrderDto extends RepresentationModel<OrderDto> {
    private Long orderId;
    private Boolean isRefunded;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant purchaseTime;
    private UserDto user;
    private CertificateDto certificate;

    public OrderDto(Order order) {
        orderId = order.getOrderId();
        isRefunded = order.getIsRefunded();
        purchaseTime = order.getPurchaseTime();
        user = new UserDto(order.getUser());
        certificate = new CertificateDto(order.getCertificate());
        certificate.setPrice(order.getPurchasePrice());
    }

    public Order convertToOrder() {
        return new Order(orderId, purchaseTime, certificate.getPrice(), isRefunded, user.convertToUser(),
                certificate.convertToCertificate());
    }
}