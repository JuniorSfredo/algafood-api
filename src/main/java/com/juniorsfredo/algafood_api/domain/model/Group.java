package com.juniorsfredo.algafood_api.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_group")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "group_has_permission",
                joinColumns = { @JoinColumn (name="group_id")},
                inverseJoinColumns = { @JoinColumn(name = "permission_id")})
    private List<Permission> permissions = new ArrayList<>();
}
