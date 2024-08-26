package com.juniorsfredo.algafood_api.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(name = "tb_user_has_group",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name="group_id")})
    private List<Group> groups = new ArrayList<>();
}
