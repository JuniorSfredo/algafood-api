package com.juniorsfredo.algafood_api.api.controllers;

import com.juniorsfredo.algafood_api.domain.model.Kitchen;
import com.juniorsfredo.algafood_api.domain.repository.KitchenRepository;
import com.juniorsfredo.algafood_api.domain.services.KitchenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/kitchens")
public class KitchenController {

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private KitchenRepository kitchenRepository;

    @GetMapping
    public ResponseEntity<List<Kitchen>> getAllKitchens() {
        List<Kitchen> kitchens = kitchenService.getAllKitchens();
        return ResponseEntity.ok(kitchens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kitchen> getKitchenById(@PathVariable("id") Long id) {
        Kitchen kitchen = kitchenService.getKitchenById(id);
        return ResponseEntity.ok(kitchen);
    }

    @PostMapping
    public ResponseEntity<Kitchen> saveKitchen (@RequestBody Kitchen kitchen, UriComponentsBuilder uriComponentsBuilder) {
        Kitchen newKitchen = kitchenService.saveKitchen(kitchen);
        var uri = uriComponentsBuilder.path("/kitchens/{id}").buildAndExpand(newKitchen.getId()).toUri();
        return ResponseEntity.created(uri).body(newKitchen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> updateKitchen(@PathVariable Long id, @RequestBody @Valid Kitchen kitchen) {
        Kitchen updatedKitchen = kitchenService.updateKitchen(id, kitchen);
        return ResponseEntity.ok(updatedKitchen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKitchen(@PathVariable Long id) {
        kitchenService.deleteKitchen(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Kitchen> searchKitchen(@RequestParam("name") String name) {
        Kitchen kitchen = kitchenService.findKitchenByName(name);
        return ResponseEntity.ok(kitchen);
    }
}
