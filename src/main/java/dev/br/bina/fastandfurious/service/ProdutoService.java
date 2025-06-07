/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.br.bina.fastandfurious.service;

import dev.br.bina.fastandfurious.model.Produto;
import dev.br.bina.fastandfurious.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProdutoService {

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

    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto com ID " + id + " não encontrado."));

        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setCategoria(produtoAtualizado.getCategoria());

        return repository.save(produto);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Produto com id " + id + " não encontrado");
    }
        repository.deleteById(id);
}



}
