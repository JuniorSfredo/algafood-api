package com.juniorsfredo.algafood_api.infrastructure.repository;

import com.juniorsfredo.algafood_api.domain.model.Restaurant;
import com.juniorsfredo.algafood_api.domain.repository.RestaurantRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurant> findByDeliveryFeeAndName(String name,
                                                     Double deliveryFeeMinimal,
                                                     Double deliveryFeeMaximal)
    {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurant> criteriaQuery = cb.createQuery(Restaurant.class);
        Root<Restaurant> root = criteriaQuery.from(Restaurant.class);
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(name)) {
            predicates.add(cb.like(root.get("name"), "%" + name + "%"));
        }

        if (deliveryFeeMinimal != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("deliveryFee"), deliveryFeeMinimal));
        }
        if (deliveryFeeMaximal != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("deliveryFee"), deliveryFeeMaximal));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Restaurant> findWithFreeDeliveryFeeAndName(String name)
    {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurant> criteriaQuery = cb.createQuery(Restaurant.class);
        Root<Restaurant> root = criteriaQuery.from(Restaurant.class);
        ArrayList<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("deliveryFee"), BigDecimal.ZERO));

        if (StringUtils.hasText(name)) {
            predicates.add(cb.like(root.get("name"), "%" + name + "%"));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
