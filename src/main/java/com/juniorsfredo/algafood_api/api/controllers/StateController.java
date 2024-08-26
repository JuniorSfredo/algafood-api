package com.juniorsfredo.algafood_api.api.controllers;

import com.juniorsfredo.algafood_api.domain.model.State;
import com.juniorsfredo.algafood_api.domain.services.StateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping
    public ResponseEntity<List<State>> getStates() {
        List<State> states = stateService.getAllStates();
        return ResponseEntity.ok(states);
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> getStateById(@PathVariable Long id) {
        State state = stateService.getStateById(id);
        return ResponseEntity.ok(state);
    }

    @PostMapping
    public ResponseEntity<State> saveState(@RequestBody @Valid State state, UriComponentsBuilder uriComponentsBuilder) {
        State newState = stateService.saveState(state);
        var uri = uriComponentsBuilder.path("/states/{id}").buildAndExpand(newState.getId()).toUri();
        return ResponseEntity.created(uri).body(newState);
    }

    @PutMapping("/{id}")
    public ResponseEntity<State> updateState(@PathVariable Long id, @RequestBody @Valid State state) {
        stateService.deleteState(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        stateService.deleteState(id);
        return ResponseEntity.noContent().build();
    }

}
