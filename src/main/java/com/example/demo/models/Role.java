package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private int status;

    public static String ADMIN = "ADMIN";

    public static String USER = "USER";
}
