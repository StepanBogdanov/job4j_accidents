package ru.job4j.accidents.service;

import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

public interface AccidentTypeService {

    Iterable<AccidentType> findAll();

    Optional<AccidentType> findById(int id);
}
