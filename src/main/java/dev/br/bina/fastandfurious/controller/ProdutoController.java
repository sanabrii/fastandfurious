/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.br.bina.fastandfurious.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/fastandfurious/produto")
public class ProdutoController {

    private Map<Long, Produto> produtos = new HashMap<>();
    private long contadorId = 1;

    static class Produto {
        public Long id;
        public String nome;
        public String categoria;
        public double preco;

        public Produto() {}
        public Produto(Long id, String nome, String categoria, double preco) {
            this.id = id;
            this.nome = nome;
            this.categoria = categoria;
            this.preco = preco;
        }
    }

    @GetMapping
    public List<Produto> listarProdutos() {
        return new ArrayList<>(produtos.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoPorId(@PathVariable Long id) {
        Produto p = produtos.get(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(p);
    }

    @GetMapping("/cat/{categoria}")
    public List<Produto> getProdutosPorCategoria(@PathVariable String categoria) {
        List<Produto> lista = new ArrayList<>();
        for (Produto p : produtos.values()) {
            if (p.categoria.equalsIgnoreCase(categoria)) {
                lista.add(p);
            }
        }
        return lista;
    }

    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        produto.id = contadorId++;
        produtos.put(produto.id, produto);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Produto existente = produtos.get(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.nome = produto.nome;
        existente.categoria = produto.categoria;
        existente.preco = produto.preco;
        return ResponseEntity.ok(existente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        Produto removido = produtos.remove(id);
        if (removido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
