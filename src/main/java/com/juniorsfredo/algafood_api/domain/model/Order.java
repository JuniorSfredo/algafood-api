package com.juniorsfredo.algafood_api.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tb_order")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double subtotal;

    @Column(name = "delivery_fee", nullable = false)
    private Double deliveryFee;

    @Column(name = "total_value")
    private Double totalValue;

    private OrderStatus status;

    @Embedded
    private Address deliveryAddress;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "confirmation_date", nullable = false)
    private LocalDateTime confirmationDate;

    @Column(name = "cancellation_date")
    private LocalDateTime cancellationDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @ManyToOne
    @JoinColumn(name = "user_client_id", nullable = false)
    private User client;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();
}
