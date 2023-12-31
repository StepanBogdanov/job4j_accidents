package ru.job4j.accidents.repository.memory;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemRuleRepository implements RuleRepository {

    private AtomicInteger nextId = new AtomicInteger(1);
    private final ConcurrentHashMap<Integer, Rule> store = new ConcurrentHashMap<>();

    public MemRuleRepository() {
        store.put(1, new Rule(1, "Статья. 1"));
        store.put(2, new Rule(2, "Статья. 2"));
        store.put(3, new Rule(3, "Статья. 3"));
    }

    @Override
    public Collection<Rule> findAll() {
        return store.values();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Set<Rule> findByIds(Set<Integer> ids) {
        Set<Rule> rules = new HashSet<>();
        for (Integer id : ids) {
            rules.add(store.get(id));
        }
        return rules;
    }
}
