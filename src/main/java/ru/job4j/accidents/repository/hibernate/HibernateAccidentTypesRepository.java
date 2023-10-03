package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HibernateAccidentTypesRepository implements AccidentTypeRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<AccidentType> findAll() {
        return crudRepository.query("FROM AccidentType", AccidentType.class);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return crudRepository.optional("FROM AccidentType WHERE id = :fId", AccidentType.class,
                Map.of("fId", id));
    }
}
