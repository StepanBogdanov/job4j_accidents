package ru.job4j.accidents.repository.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.Set;

@Repository
public interface RuleDataRepository extends CrudRepository<Rule, Integer> {

    default Set<Rule> findByIds(Set<Integer> ids) {
        Set<Rule> rules = new HashSet<>();
        for (Rule rule : this.findAllById(ids)) {
            rules.add(rule);
        }
        return rules;
    }


}
