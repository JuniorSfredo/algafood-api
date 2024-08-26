package com.juniorsfredo.algafood_api.domain.repository;

import com.juniorsfredo.algafood_api.domain.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
