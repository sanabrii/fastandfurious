/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.br.bina.fastandfurious.service;

import dev.br.bina.fastandfurious.model.Produto;
import dev.br.bina.fastandfurious.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProdutoService {

    @Autowired
    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Produto> buscarPorCategoria(String categoria) {
        return repository.findByCategoriaIgnoreCase(categoria);
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Produto atualizar(Long id, Produto produto) {
        Produto existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        existente.setNome(produto.getNome());
        existente.setCategoria(produto.getCategoria());
        existente.setPreco(produto.getPreco());

        return repository.save(existente);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
