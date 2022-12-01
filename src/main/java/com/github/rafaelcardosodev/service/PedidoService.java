package com.github.rafaelcardosodev.service;

import com.github.rafaelcardosodev.domain.entity.Pedido;
import com.github.rafaelcardosodev.domain.entity.enums.StatusPedido;
import com.github.rafaelcardosodev.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido save(PedidoDTO pedidoDTO);
    Optional<Pedido> getDetailedPedido(Integer id);
    void updateStatus(Integer id, StatusPedido statusPedido);
}
