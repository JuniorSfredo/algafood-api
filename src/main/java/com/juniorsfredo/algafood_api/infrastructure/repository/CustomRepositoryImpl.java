package com.juniorsfredo.algafood_api.infrastructure.repository;

import com.juniorsfredo.algafood_api.domain.repository.CustomRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.Optional;

public class CustomRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomRepository<T, ID> {

    private EntityManager entityManager;

    public CustomRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findByFirst() {
        var jpql = "FROM " + getDomainClass().getName();

        T entity = entityManager.createQuery(jpql, getDomainClass())
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(entity);
    }
}
