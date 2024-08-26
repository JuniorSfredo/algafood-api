package com.juniorsfredo.algafood_api.domain.repository;

import com.juniorsfredo.algafood_api.domain.model.Restaurant;

import java.util.List;

public interface RestaurantRepositoryQueries {

    List<Restaurant> findByDeliveryFeeAndName(String name, Double deliveryFeeMinimal, Double deliveryFeeMaximal);
    List<Restaurant> findWithFreeDeliveryFeeAndName(String name);
}
