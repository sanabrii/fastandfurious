/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.br.bina.fastandfurious.repositories;

import dev.br.bina.fastandfurious.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByCategoriaIgnoreCase(String categoria);

}
