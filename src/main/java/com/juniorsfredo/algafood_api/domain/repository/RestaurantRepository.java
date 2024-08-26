package com.juniorsfredo.algafood_api.domain.repository;

import com.juniorsfredo.algafood_api.domain.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends CustomRepository<Restaurant, Long>,
        RestaurantRepositoryQueries {

    @Query("from Restaurant r join fetch r.kitchen left join fetch r.paymentMethods join fetch r.address a join fetch a.city c join fetch c.state ORDER BY r.id")
    List<Restaurant> findAll();

}
