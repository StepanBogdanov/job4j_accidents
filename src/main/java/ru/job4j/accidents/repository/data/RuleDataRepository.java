package ru.job4j.accidents.repository.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Set;

@Repository
public interface RuleDataRepository extends CrudRepository<Rule, Integer> {

    public Set<Rule> findByIdIn(Set<Integer> ids);


}
