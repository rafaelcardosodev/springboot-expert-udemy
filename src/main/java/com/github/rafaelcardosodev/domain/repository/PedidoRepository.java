package com.github.rafaelcardosodev.domain.repository;

import com.github.rafaelcardosodev.domain.entity.Cliente;
import com.github.rafaelcardosodev.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query("SELECT p FROM Pedido p " +
            "LEFT JOIN FETCH p.itensPedidos " +
            "WHERE p.id = :id")
    Optional<Pedido> findByIdFetchItensPedidos(@Param("id") Integer id);
}
