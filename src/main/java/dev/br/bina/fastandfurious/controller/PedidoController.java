/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.br.bina.fastandfurious.controller;

import dev.br.bina.fastandfurious.model.Pedido;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/fastandfurious/pedido")
public class PedidoController {

    private Map<Long, Pedido> pedidos = new HashMap<>();
    private Long contador = 1L;

    @GetMapping
    public List<Pedido> listarPedidos() {
        return new ArrayList<>(pedidos.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidos.get(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        pedido.setId(contador++);
        pedido.setStatus("ABERTO");
        pedidos.put(pedido.getId(), pedido);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> alterarPedido(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
        Pedido pedidoExistente = pedidos.get(id);
        if (pedidoExistente == null) {
            return ResponseEntity.notFound().build();
        }
        pedidoExistente.setDetalhes(pedidoAtualizado.getDetalhes());
        return ResponseEntity.ok(pedidoExistente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        Pedido pedido = pedidos.get(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        pedido.setStatus("CANCELADO");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public List<Pedido> listarPorStatus(@PathVariable String status) {
        List<Pedido> filtrados = new ArrayList<>();
        for (Pedido p : pedidos.values()) {
            if (p.getStatus().equalsIgnoreCase(status)) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Pedido> alterarStatus(@PathVariable Long id, @RequestBody Map<String, String> statusMap) {
        Pedido pedido = pedidos.get(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        String novoStatus = statusMap.get("status");
        if (novoStatus == null) {
            return ResponseEntity.badRequest().build();
        }
        pedido.setStatus(novoStatus.toUpperCase());
        return ResponseEntity.ok(pedido);
    }
}
