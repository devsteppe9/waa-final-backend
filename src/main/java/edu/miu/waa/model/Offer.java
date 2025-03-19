package edu.miu.waa.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Offer {
    // Product Name, product price, product image, offer status, created date
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OfferStatusEnum status;
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
