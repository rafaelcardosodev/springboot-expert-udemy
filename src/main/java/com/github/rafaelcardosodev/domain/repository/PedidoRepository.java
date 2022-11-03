package com.github.rafaelcardosodev.domain.repository;

import com.github.rafaelcardosodev.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
