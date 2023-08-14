package com.skosarev.library.dao;

import com.skosarev.library.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM People", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person get(int id) {
        return jdbcTemplate.query("SELECT * FROM People WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream().findAny().orElse(null);
    }

    public Optional<Person> get(String fullName) {
        return jdbcTemplate.query("SELECT * FROM People WHERE full_name = ?",
                new BeanPropertyRowMapper<>(Person.class), fullName)
                .stream().findAny();
    }

    public void create(Person person) {
        jdbcTemplate.update("INSERT INTO People(full_name, year_of_birth) VALUES(?, ?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE People SET full_name = ?, year_of_birth = ? WHERE id = ?",
                person.getFullName(), person.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM People WHERE id = ?", id);
    }
}
