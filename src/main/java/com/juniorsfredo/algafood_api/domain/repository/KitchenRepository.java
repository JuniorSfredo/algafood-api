package com.juniorsfredo.algafood_api.domain.repository;

import com.juniorsfredo.algafood_api.domain.model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KitchenRepository extends CustomRepository<Kitchen, Long> {

    List<Kitchen> findByNameContaining(String name);

    @Query("from Kitchen k where k.name = ?1")
    Optional<Kitchen> findByName(String name);
}
