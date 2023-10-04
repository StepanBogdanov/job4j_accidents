package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;

public interface RuleService {

    Iterable<Rule> findAll();

    Optional<Rule> findById(int id);
}
