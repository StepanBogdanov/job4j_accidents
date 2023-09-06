package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
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
}
