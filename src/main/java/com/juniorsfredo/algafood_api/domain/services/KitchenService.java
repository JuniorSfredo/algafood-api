package com.juniorsfredo.algafood_api.domain.services;

import com.juniorsfredo.algafood_api.domain.exceptions.KitchenInUseException;
import com.juniorsfredo.algafood_api.domain.exceptions.KitchenNotFoundException;
import com.juniorsfredo.algafood_api.domain.model.Kitchen;
import com.juniorsfredo.algafood_api.domain.repository.KitchenRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KitchenService {

    @Autowired
    private KitchenRepository kitchenRepository;

    public List<Kitchen> getAllKitchens() {
        return kitchenRepository.findAll();
    }

    public Kitchen getKitchenById(Long id) {
        return findKitchenById(id).orElseThrow( () -> new KitchenNotFoundException("Kitchen not found with id: " + id));
    }

    public Kitchen saveKitchen(Kitchen kitchen) {
        return this.kitchenRepository.save(kitchen);
    }

    public Kitchen updateKitchen(Long id, Kitchen kitchen) {
        Kitchen oldKitchen = getKitchenById(id);
        return updateData(oldKitchen, kitchen);
    }

    public void deleteKitchen(Long id) {
        Kitchen kitchen = getKitchenById(id);
        try {
            kitchenRepository.delete(kitchen);
        } catch (DataIntegrityViolationException e) {
            throw new KitchenInUseException("Kitchen with id: " + id + " is being used");
        }
    }

    public List<Kitchen> getKitchenByName(String name) {
        return kitchenRepository.findByNameContaining(name);
    }

    private Kitchen updateData(Kitchen oldKitchen, Kitchen newKitchen) {
        BeanUtils.copyProperties(newKitchen, oldKitchen, "id");
        return this.kitchenRepository.save(oldKitchen);
    }

    public Kitchen findKitchenByName(String name) {
        return kitchenRepository.findByName(name).orElseThrow( () -> new KitchenNotFoundException("Kitchen not found with name:" + name));
    }

    private Optional<Kitchen> findKitchenById(Long id) {
        return kitchenRepository.findById(id);
    }
}
