package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;

    @Override
    public Accident save(Accident accident, Set<Integer> ids) {
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        accident.setRules(ruleRepository.findByIds(ids));
        return accidentRepository.save(accident);
    }

    @Override
    public boolean update(Accident accident, Set<Integer> ids) {
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        accident.setRules(ruleRepository.findByIds(ids));
        return accidentRepository.update(accident);
    }

    @Override
    public boolean delete(int id) {
        return accidentRepository.delete(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

}
