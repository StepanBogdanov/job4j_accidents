package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateAccidentRepository implements AccidentRepository {

    private final CrudRepository crudRepository;

    @Override
    public Accident save(Accident accident) {
        crudRepository.run(session -> session.save(accident));
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        crudRepository.run(session -> session.merge(accident));
        return true;
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.bool("DELETE FROM Accident WHERE id = :dId", Map.of("dId", id));
    }

    @Override
    public Collection<Accident> findAll() {
        return crudRepository.query("SELECT DISTINCT a FROM Accident a LEFT JOIN FETCH a.rules", Accident.class);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.optional("SELECT DISTINCT a FROM Accident a LEFT JOIN FETCH a.rules WHERE a.id = :fId",
                Accident.class, Map.of("fId", id));
    }
}
