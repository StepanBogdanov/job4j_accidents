package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.data.AccidentDataRepository;
import ru.job4j.accidents.repository.data.AccidentTypeDataRepository;
import ru.job4j.accidents.repository.data.RuleDataRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private final AccidentDataRepository accidentRepository;
    private final AccidentTypeDataRepository accidentTypeRepository;
    private final RuleDataRepository ruleRepository;

    @Override
    public Accident save(Accident accident, Set<Integer> ids) {
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        accident.setRules(ruleRepository.findByIdIn(ids));
        return accidentRepository.save(accident);
    }

    @Override
    public boolean update(Accident accident, Set<Integer> ids) {
        save(accident, ids);
        return true;
    }

    @Override
    public boolean delete(int id) {
        accidentRepository.deleteById(id);
        return true;
    }

    @Override
    public Iterable<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

}
