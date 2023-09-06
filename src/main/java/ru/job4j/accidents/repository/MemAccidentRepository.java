package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemAccidentRepository implements AccidentRepository {

    private AtomicInteger nextId = new AtomicInteger(1);
    private final ConcurrentHashMap<Integer, Accident> store = new ConcurrentHashMap<>();

    @Override
    public Accident save(Accident accident) {
        accident.setId(nextId.getAndIncrement());
        store.put(accident.getId(), accident);
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        return store.computeIfPresent(accident.getId(), (id, oldAccident) -> new Accident(
                oldAccident.getId(), accident.getName(), accident.getText(),
                accident.getAddress(), accident.getType(), accident.getRules())) != null;
    }

    @Override
    public boolean delete(int id) {
        return store.remove(id) != null;
    }

    @Override
    public Collection<Accident> findAll() {
        return store.values();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }
}
