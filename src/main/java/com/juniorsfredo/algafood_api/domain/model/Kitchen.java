package com.juniorsfredo.algafood_api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juniorsfredo.algafood_api.core.validator.Groups;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_kitchen")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Kitchen {

    @NotNull(groups = Groups.KitchenId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "kitchen")
    private List<Restaurant> restaurants = new ArrayList<>();
}
