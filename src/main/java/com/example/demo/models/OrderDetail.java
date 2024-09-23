package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
