package ru.job4j.accidents.repository.memory;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemAccidentTypeRepository implements AccidentTypeRepository {

    private AtomicInteger nextId = new AtomicInteger(1);
    private final ConcurrentHashMap<Integer, AccidentType> store = new ConcurrentHashMap<>();

    public MemAccidentTypeRepository() {
        store.put(1, new AccidentType(1, "Две машины"));
        store.put(2, new AccidentType(2, "Машина и человек"));
        store.put(3, new AccidentType(3, "Машина и велосипед"));
    }

    @Override
    public Collection<AccidentType> findAll() {
        return store.values();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }
}
