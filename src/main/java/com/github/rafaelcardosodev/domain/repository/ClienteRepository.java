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
    private static String UPDATE = "UPDATE CLIENTE SET NOME = ? WHERE ID = ?";
    private static String DELETE = "DELETE FROM CLIENTE WHERE ID = ?";
    private static String SELECT_ALL = "SELECT * FROM CLIENTE";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cliente save(Cliente cliente) {
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }

    public Cliente update(Cliente cliente) {
        jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getId()});
        return cliente;
    }

    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, new Object[]{id});
    }

    public void delete(Cliente cliente) {
        jdbcTemplate.update(DELETE, new Object[]{cliente.getId()});
    }

    public List<Cliente> getAll() {
        return jdbcTemplate.query(SELECT_ALL, getClienteRowMapper());
    }

    private static RowMapper<Cliente> getClienteRowMapper() {
        return new RowMapper<Cliente>() {

            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Cliente(
                        resultSet.getInt("id"),
                        resultSet.getString("nome")
                );
            }
        };
    }

    public List<Cliente> getByNome(String nome) {
        return jdbcTemplate.query(
                SELECT_ALL.concat(" WHERE NOME LIKE ?"),
                new Object[]{"%" + nome + "%"},
                getClienteRowMapper());
    }
}
