package com.github.rafaelcardosodev.domain.repository;

import com.github.rafaelcardosodev.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("SELECT c FROM Cliente c WHERE c.nome like :nome")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    List<Cliente> findByNomeLike(String nome);
    boolean existsByNome(String nome);

    @Query("SELECT c FROM Cliente c " +
            "LEFT JOIN fetch c.pedidos " +
            "WHERE c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);
}
