package com.github.rafaelcardosodev.domain.repository;

import com.github.rafaelcardosodev.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteRepository {

    private static String INSERT = "INSERT INTO CLIENTE(NOME) VALUES (?)";
    private static String SELECT_ALL = "SELECT * FROM CLIENTE";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cliente save(Cliente cliente) {
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }

    public List<Cliente> getAll() {
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<Cliente>() {

            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Cliente(resultSet.getString("nome"));
            }
        });
    }
}
