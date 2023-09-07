package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AccidentService {

    Accident save(Accident accident);

    boolean update(Accident accident);

    boolean delete(int id);

    Collection<Accident> findAll();

    Optional<Accident> findById(int id);

    void setRules(Accident accident, Set<Integer> ids);
}
