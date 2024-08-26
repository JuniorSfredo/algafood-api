package com.juniorsfredo.algafood_api.api.controllers;

import com.juniorsfredo.algafood_api.domain.model.Restaurant;
import com.juniorsfredo.algafood_api.domain.services.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public ResponseEntity<?> saveRestaurant(@RequestBody @Valid Restaurant restaurant, UriComponentsBuilder uriComponentsBuilder) {
        Restaurant newRestaurant = restaurantService.saveRestaurant(restaurant);
        var uri = uriComponentsBuilder.path("/restaurants/{id}").buildAndExpand(newRestaurant.getId()).toUri();
        return ResponseEntity.created(uri).body(newRestaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody @Valid Restaurant restaurant) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/searchNameAndDelivery")
    ResponseEntity<List<Restaurant>> findRestaurantByNameAndDelivery(@RequestParam(value = "name", required = true) String name,
                                                                     @RequestParam(value = "deliveryFeeMinimal", required = true) Double deliveryFeeMinimal,
                                                                     @RequestParam(value = "deliveryFeeMaximal", required = true) Double deliveryFeeMaximal) {

        List<Restaurant> restaurants = restaurantService.findRestaurantByNameAndDeliveryFee(name,
                                                                                            deliveryFeeMinimal,
                                                                                            deliveryFeeMaximal);

        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    @GetMapping("/searchFreeDeliveryAndName")
    ResponseEntity<List<Restaurant>> findRestaurantWithFreeDeliveryAndName(@RequestParam(value = "name") String name) {
        List<Restaurant> restaurants = restaurantService
                                            .findRestaurantWithFreeDeliveryFeeAndName(name);

        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    @GetMapping("/first")
    ResponseEntity<Restaurant> findFirstRestaurant() {
        Restaurant restaurantFound = restaurantService.findRestaurantByFirst();
        return ResponseEntity.status(HttpStatus.OK).body(restaurantFound);
    }
}
