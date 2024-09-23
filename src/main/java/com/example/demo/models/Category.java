package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
}
