/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.br.bina.fastandfurious.controller;

import dev.br.bina.fastandfurious.model.Pedido;
import dev.br.bina.fastandfurious.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/fastandfurious/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        Pedido criado = pedidoService.criarPedido(pedido);
        return new ResponseEntity<>(criado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> alterarPedido(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
        try {
            Pedido atualizado = pedidoService.atualizarPedido(id, pedidoAtualizado);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        if (pedidoService.cancelarPedido(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/status/{status}")
    public List<Pedido> listarPorStatus(@PathVariable String status) {
        return pedidoService.buscarPorStatus(status);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Pedido> alterarStatus(@PathVariable Long id, @RequestBody Map<String, String> statusMap) {
        String novoStatus = statusMap.get("status");
        if (novoStatus == null) return ResponseEntity.badRequest().build();

        try {
            Pedido atualizado = pedidoService.alterarStatus(id, novoStatus.toUpperCase());
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
