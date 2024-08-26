package com.juniorsfredo.algafood_api.domain.repository;

import com.juniorsfredo.algafood_api.domain.model.City;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CustomRepository<City, Long> {
    
}
