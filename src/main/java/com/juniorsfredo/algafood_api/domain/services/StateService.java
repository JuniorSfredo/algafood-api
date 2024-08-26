package com.juniorsfredo.algafood_api.domain.services;

import com.juniorsfredo.algafood_api.domain.exceptions.StateInUseException;
import com.juniorsfredo.algafood_api.domain.exceptions.StateNotFoundException;
import com.juniorsfredo.algafood_api.domain.model.State;
import com.juniorsfredo.algafood_api.domain.repository.StateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    public State getStateById(Long id) {
        return findStateById(id).orElseThrow( () -> new StateNotFoundException("State not found with id: " + id));
    }

    public State saveState(State state) {
        return stateRepository.save(state);
    }

    public State updateState(Long id, State newState) {
        State oldState = getStateById(id);
        return updateData(oldState, newState);
    }

    public void deleteState(Long id) {
        State state = getStateById(id);

        try {
            stateRepository.delete(state);
        } catch (DataIntegrityViolationException e) {
            throw new StateInUseException("State with id: " + id + " in use");
        }
    }

    private State updateData(State oldState, State newState) {
        BeanUtils.copyProperties(newState, oldState, "id");
        return stateRepository.save(oldState);
    }

    private Optional<State> findStateById(Long id) {
        return stateRepository.findById(id);
    }
}
