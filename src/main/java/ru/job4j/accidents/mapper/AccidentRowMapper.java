package ru.job4j.accidents.mapper;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@AllArgsConstructor
public class AccidentRowMapper implements RowMapper<Accident> {

    private final Map<Integer, Accident> accidents;

    @Override
    public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
        Accident accident = new Accident();
        accident.setId(rs.getInt("id"));
        accident.setName(rs.getString("name"));
        accident.setText(rs.getString("text"));
        accident.setAddress(rs.getString("address"));
        accident.setType(new AccidentType(rs.getInt("accident_type_id"), rs.getString("type_name")));
        accidents.putIfAbsent(accident.getId(), accident);
        accidents.get(accident.getId()).getRules().add(
                new Rule(rs.getInt("rule_id"), rs.getString("rule_name")));
        return accident;
    }
}
