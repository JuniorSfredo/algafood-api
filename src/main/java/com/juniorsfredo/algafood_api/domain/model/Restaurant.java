package com.juniorsfredo.algafood_api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juniorsfredo.algafood_api.core.validator.Groups;
import com.juniorsfredo.algafood_api.core.validator.Multiple;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.ConvertGroup;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_restaurant")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Multiple(number = 1) // Not used
    @PositiveOrZero
    @Column(name = "delivery_fee")
    private BigDecimal deliveryFee;

    @Valid
    @ConvertGroup(to = Groups.KitchenId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Kitchen kitchen;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Valid
    @NotNull
    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurant_has_payment_method",
            joinColumns = {@JoinColumn(name = "restaurant_id")},
            inverseJoinColumns = {@JoinColumn(name = "payment_method_id")})
    private List<PaymentMethod> paymentMethods;
}
