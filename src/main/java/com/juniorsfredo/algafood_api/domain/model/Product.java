package com.juniorsfredo.algafood_api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Boolean isActive;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;
}
