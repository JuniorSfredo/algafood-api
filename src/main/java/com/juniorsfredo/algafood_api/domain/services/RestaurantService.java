package com.juniorsfredo.algafood_api.domain.services;

import com.juniorsfredo.algafood_api.domain.exceptions.*;
import com.juniorsfredo.algafood_api.domain.model.Kitchen;
import com.juniorsfredo.algafood_api.domain.model.Restaurant;
import com.juniorsfredo.algafood_api.domain.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenService kitchenService;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
         return findRestaurantById(id).orElseThrow( () -> new RestaurantNotFoundException("Restaurant not found with id: " + id) );
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long id, Restaurant newRestaurant) {
        Restaurant restaurantFound = this.getRestaurantById(id);
        return updateData(restaurantFound, newRestaurant);
    }

    public void deleteRestaurant(Long id) {
        Restaurant restaurantFound = this.getRestaurantById(id);
        try {
            restaurantRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RestaurantInUseException("Restaurant with id: " + id + " already exists");
        }
    }

    public List<Restaurant> findRestaurantByNameAndDeliveryFee(String name,
                                                               Double deliveryFeeMinimal,
                                                               Double deliveryFeeMaximal) {

        return restaurantRepository.findByDeliveryFeeAndName(name, deliveryFeeMinimal, deliveryFeeMaximal);
    }

    public List<Restaurant> findRestaurantWithFreeDeliveryFeeAndName(String name) {
        return restaurantRepository.findWithFreeDeliveryFeeAndName(name);
    }

    public Restaurant findRestaurantByFirst() {
        return restaurantRepository.findByFirst().orElseThrow( () -> new RestaurantNotFoundException("Restaurant not found"));
    }

    private Restaurant updateData(Restaurant oldRestaurant, Restaurant newRestaurant) {
        BeanUtils.copyProperties(newRestaurant, oldRestaurant, "id", "paymentMethods","createdAt");
        oldRestaurant.setUpdatedAt(LocalDateTime.now());
        return restaurantRepository.save(oldRestaurant);
    }

    private Optional<Restaurant> findRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }
}