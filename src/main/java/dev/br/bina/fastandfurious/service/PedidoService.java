/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.br.bina.fastandfurious.service;

import dev.br.bina.fastandfurious.model.Pedido;
import dev.br.bina.fastandfurious.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PedidoService {

    @Autowired
    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public List<Pedido> listarTodos() {
        return repository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Pedido criarPedido(Pedido pedido) {
        pedido.setStatus("ABERTO");
        return repository.save(pedido);
    }

    public Pedido atualizarPedido(Long id, Pedido atualizado) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setDetalhes(atualizado.getDetalhes());
        return repository.save(pedido);
    }

    public boolean cancelarPedido(Long id) {
        return repository.findById(id).map(pedido -> {
            pedido.setStatus("CANCELADO");
            repository.save(pedido);
            return true;
        }).orElse(false);
    }

    public List<Pedido> buscarPorStatus(String status) {
        return repository.findByStatusIgnoreCase(status);
    }

    public Pedido alterarStatus(Long id, String novoStatus) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(novoStatus);
        return repository.save(pedido);
    }
}
