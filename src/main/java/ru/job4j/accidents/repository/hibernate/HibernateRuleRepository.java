package ru.job4j.accidents.repository.hibernate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class HibernateRuleRepository implements RuleRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Rule> findAll() {
        return crudRepository.query("FROM Rule", Rule.class);
    }

    @Override
    public Optional<Rule> findById(int id) {
        return crudRepository.optional("FROM Rule WHERE id = :fId", Rule.class, Map.of("fId", id));
    }

    @Override
    public Set<Rule> findByIds(Set<Integer> ids) {
        return Set.copyOf(crudRepository.query("FROM Rule WHERE id IN :fIds", Rule.class,
                Map.of("fIds", ids)));
    }
}
