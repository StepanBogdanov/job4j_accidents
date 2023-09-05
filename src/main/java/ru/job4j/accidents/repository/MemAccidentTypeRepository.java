package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
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
}
