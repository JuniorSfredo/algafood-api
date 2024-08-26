package com.juniorsfredo.algafood_api.domain.model;

import com.juniorsfredo.algafood_api.core.validator.Groups;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_state")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class State {

    @NotNull(groups = Groups.StateId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;
}
