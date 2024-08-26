package com.juniorsfredo.algafood_api.api.controllers;

import com.juniorsfredo.algafood_api.domain.model.City;
import com.juniorsfredo.algafood_api.domain.services.CityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        List<City> cities =  cityService.findAll();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getKitchenById(@PathVariable Long id) {
        City cityFound = cityService.getCityById(id);
        return ResponseEntity.ok(cityFound);
    }

    @PostMapping
    public ResponseEntity<City> saveCity(@RequestBody @Valid City city, UriComponentsBuilder uriComponentsBuilder) {
        City newCity = cityService.saveCity(city);
        var uri = uriComponentsBuilder.path("/cities/{id}").buildAndExpand(newCity.getId()).toUri();
        return ResponseEntity.created(uri).body(newCity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@RequestBody @Valid City city, @PathVariable Long id) {
        City updatedCity = cityService.updateCity(id, city);
        return ResponseEntity.ok(updatedCity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

}
