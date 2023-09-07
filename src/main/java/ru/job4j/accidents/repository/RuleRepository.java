package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RuleRepository {

    Collection<Rule> findAll();

    Optional<Rule> findById(int id);

    Set<Rule> findByIds(Set<Integer> ids);
}
