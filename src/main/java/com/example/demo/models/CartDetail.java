package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
