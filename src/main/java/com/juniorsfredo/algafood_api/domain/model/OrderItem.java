package com.juniorsfredo.algafood_api.domain.model;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_order_item")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItem {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    private String observation;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
