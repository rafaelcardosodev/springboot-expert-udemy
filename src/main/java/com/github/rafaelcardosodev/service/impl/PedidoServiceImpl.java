package com.github.rafaelcardosodev.service.impl;

import com.github.rafaelcardosodev.domain.entity.Cliente;
import com.github.rafaelcardosodev.domain.entity.ItemPedido;
import com.github.rafaelcardosodev.domain.entity.Pedido;
import com.github.rafaelcardosodev.domain.entity.Produto;
import com.github.rafaelcardosodev.domain.entity.enums.StatusPedido;
import com.github.rafaelcardosodev.domain.repository.ClienteRepository;
import com.github.rafaelcardosodev.domain.repository.ItemPedidoRepository;
import com.github.rafaelcardosodev.domain.repository.PedidoRepository;
import com.github.rafaelcardosodev.domain.repository.ProdutoRepository;
import com.github.rafaelcardosodev.exception.PedidoNotFoundException;
import com.github.rafaelcardosodev.exception.RegraNegocioException;
import com.github.rafaelcardosodev.rest.dto.ItemPedidoDTO;
import com.github.rafaelcardosodev.rest.dto.PedidoDTO;
import com.github.rafaelcardosodev.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido save(PedidoDTO pedidoDTO) {
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteID())
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();

        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedido = convertItensPedido(pedido, pedidoDTO.getItensPedidos());
        repository.save(pedido);
        itemPedidoRepository.saveAll(itensPedido);
        pedido.setItensPedidos(itensPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> getDetailedPedido(Integer id) {
        return repository.findByIdFetchItensPedidos(id);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    repository.save(pedido);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new PedidoNotFoundException());
    }

    private List<ItemPedido> convertItensPedido(Pedido pedido, List<ItemPedidoDTO> itensPedidoDTO) {
        if (itensPedidoDTO.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
        }

        return itensPedidoDTO.stream()
                .map(itemDTO -> {
                    Integer produtoID = itemDTO.getProdutoID();
                    Produto produto = produtoRepository.findById(produtoID)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + produtoID));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(itemPedido.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                })
                .collect(Collectors.toList());
    }
}
