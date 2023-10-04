package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AccidentService {

    Accident save(Accident accident, Set<Integer> ids);

    boolean update(Accident accident, Set<Integer> ids);

    boolean delete(int id);

    Iterable<Accident> findAll();

    Optional<Accident> findById(int id);

}
