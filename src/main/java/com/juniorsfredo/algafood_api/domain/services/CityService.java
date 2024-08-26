package com.juniorsfredo.algafood_api.domain.services;

import com.juniorsfredo.algafood_api.domain.exceptions.CityInUseException;
import com.juniorsfredo.algafood_api.domain.exceptions.CityNotFoundException;
import com.juniorsfredo.algafood_api.domain.exceptions.EntityInUseException;
import com.juniorsfredo.algafood_api.domain.model.City;
import com.juniorsfredo.algafood_api.domain.repository.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id) {
        return findCityById(id).orElseThrow( () -> new CityNotFoundException("City not found with id: " + id));
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public City updateCity(Long id, City city) {
        City oldCity = getCityById(id);
        return updateData(oldCity, city);
    }

    public void deleteCity(Long id) {
        City cityFound = getCityById(id);
        try {
            cityRepository.delete(cityFound);
        } catch (DataIntegrityViolationException e) {
            throw new CityInUseException("City with id: " + id + " in use!");
        }
    }

    private City updateData(City oldCity, City newCity) {
        BeanUtils.copyProperties(newCity, oldCity, "id");
        return cityRepository.save(oldCity);
    }

    private Optional<City> findCityById(Long id) {
        return cityRepository.findById(id);
    }
}
