package com.github.rafaelcardosodev.rest.controller;

import com.github.rafaelcardosodev.domain.entity.ItemPedido;
import com.github.rafaelcardosodev.domain.entity.Pedido;
import com.github.rafaelcardosodev.domain.entity.enums.StatusPedido;
import com.github.rafaelcardosodev.rest.dto.AtualizarStatusDTO;
import com.github.rafaelcardosodev.rest.dto.DetalhesItemPedidoDTO;
import com.github.rafaelcardosodev.rest.dto.DetalhesPedidoDTO;
import com.github.rafaelcardosodev.rest.dto.PedidoDTO;
import com.github.rafaelcardosodev.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO pedidoDTO) {
        return service.save(pedidoDTO).getId();
    }

    @GetMapping("/{id}")
    public DetalhesPedidoDTO getById(@PathVariable Integer id) {
        return service.getDetailedPedido(id)
                .map(this::convert)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizarStatusDTO statusDTO) {

        service.updateStatus(id, StatusPedido.valueOf(statusDTO.getNovoStatus()));
    }

    private DetalhesPedidoDTO convert(Pedido pedido) {
        return DetalhesPedidoDTO.builder()
                .id(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itensPedidos(convert(pedido.getItensPedidos()))
                .build();
    }

    private List<DetalhesItemPedidoDTO> convert(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return itens.stream()
                .map(item -> DetalhesItemPedidoDTO.builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .quantidade(item.getQuantidade())
                        .precoUnitario(item.getProduto().getPreco())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
