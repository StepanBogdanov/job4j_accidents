package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.data.RuleDataRepository;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {

    private final RuleDataRepository ruleRepository;

    @Override
    public Iterable<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }
}
