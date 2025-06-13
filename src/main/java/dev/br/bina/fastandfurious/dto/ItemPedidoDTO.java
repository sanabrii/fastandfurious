package dev.br.bina.fastandfurious.dto;

import jakarta.validation.constraints.NotBlank;


/**
 *
 * @author sesidevb
 */
public class ItemPedidoDTO {

    @NotBlank
    private Long id;
    
    @NotBlank
    private int qtd;
    
    // Apenas para devolução
    private String nome;
    private double preco;

    public ItemPedidoDTO() {
    }

    public ItemPedidoDTO(Long id, int qtd, String nome, double preco) {
        this.id = id;
        this.qtd = qtd;
        this.nome = nome;
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
}
