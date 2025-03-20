package edu.miu.waa.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Offer {
    // Product Name, product price, product image, offer status, created date
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private OfferStatusEnum status = OfferStatusEnum.OPEN;
    private LocalDate createdDate = LocalDate.now();
    private Double offerAmount;
    private String message;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
