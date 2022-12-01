package com.github.rafaelcardosodev.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Integer clienteID;
    private BigDecimal total;
    private List<ItemPedidoDTO> itensPedidos;

}
