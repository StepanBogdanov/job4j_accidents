package ru.job4j.accidents.repository.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.mapper.AccidentRowMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

    private final JdbcTemplate jdbc;

    private static final String INSERT_INTO_ACCIDENTS = "INSERT INTO accidents (name, text, address, accident_type_id) "
            + "VALUES (?, ?, ?, ?) RETURNING id";
    private static final String INSERT_INTO_ACCIDENTS_RULES = "INSERT INTO accidents_rules (accident_id, rule_id) "
            + "VALUES (?, ?)";
    private static final String DELETE_FROM_ACCIDENTS_RULES = "DELETE FROM accidents_rules WHERE accident_id = ?";
    private static final String UPDATE_ACCIDENTS = "UPDATE accidents SET name = ?, text = ?, address = ?, "
            + "accident_type_id = ? WHERE id = ?";
    private static final String DELETE_FROM_ACCIDENTS = "DELETE FROM accidents WHERE id = ?";
    private static final String SELECT_ALL_FROM_ACCIDENTS = "SELECT ac.id, ac.name, ac.text, ac.address, "
            + "ac.accident_type_id, at.name AS type_name, ar.rule_id, r.name AS rule_name FROM accidents AS ac "
            + "LEFT JOIN accident_types AS at ON ac.accident_type_id = at.id "
            + "LEFT JOIN accidents_rules AS ar ON ac.id = ar.accident_id "
            + "LEFT JOIN rules AS r ON ar.rule_id = r.id";
    private static final String SELECT_FROM_ACCIDENTS_BY_ID = "SELECT ac.id, ac.name, ac.text, ac.address, "
            + "ac.accident_type_id, at.name AS type_name, ar.rule_id, r.name AS rule_name FROM accidents AS ac "
            + "LEFT JOIN accident_types AS at ON ac.accident_type_id = at.id "
            + "LEFT JOIN accidents_rules AS ar ON ac.id = ar.accident_id "
            + "LEFT JOIN rules AS r ON ar.rule_id = r.id WHERE ac.id = ?";

    @Override
    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_INTO_ACCIDENTS, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        accident.setId(keyHolder.getKey().intValue());
        for (Rule rule : accident.getRules()) {
            jdbc.update(INSERT_INTO_ACCIDENTS_RULES, accident.getId(), rule.getId());
        }
        return accident;
    }

    @Override
    public boolean update(Accident accident) {
        var result = jdbc.update(UPDATE_ACCIDENTS, accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId(), accident.getId());
        if (result > 0) {
            jdbc.update(DELETE_FROM_ACCIDENTS_RULES, accident.getId());
            for (Rule rule : accident.getRules()) {
                jdbc.update(INSERT_INTO_ACCIDENTS_RULES, accident.getId(), rule.getId());            }
        }
        return result > 0;
    }

    @Override
    public boolean delete(int id) {
        var result = jdbc.update(DELETE_FROM_ACCIDENTS, id);
        return result > 0;
    }

    @Override
    public Collection<Accident> findAll() {
        Map<Integer, Accident> accidentMap = new HashMap<>();
        jdbc.query(SELECT_ALL_FROM_ACCIDENTS, new AccidentRowMapper(accidentMap));
        return accidentMap.values();
    }

    @Override
    public Optional<Accident> findById(int id) {
        Map<Integer, Accident> accidentMap = new HashMap<>();
        jdbc.query(SELECT_FROM_ACCIDENTS_BY_ID, new AccidentRowMapper(accidentMap), id);
        return Optional.ofNullable(accidentMap.get(id));
    }
}
