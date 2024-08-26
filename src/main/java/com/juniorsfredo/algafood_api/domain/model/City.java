package com.juniorsfredo.algafood_api.domain.model;

import com.juniorsfredo.algafood_api.core.validator.Groups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_city")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class City {

    @NotNull(groups = Groups.CityId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Valid
    @NotNull
    @ConvertGroup(to = Groups.StateId.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private State state;
}
