package dev.br.bina.fastandfurious.dto;

import dev.br.bina.fastandfurious.model.ItemPedido;
import dev.br.bina.fastandfurious.model.Pedido;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sesidevb
 */
public class PedidoRequestDTO {
    
    long id;
    
    @NotBlank
    String status;
    
    @NotBlank
    String detalhes;
    
    List<ItemPedidoDTO> itens = new ArrayList<>();

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(String status, String detalhes, List<ItemPedidoDTO> listaItens) {
        this.status = status;
        this.detalhes = detalhes;
        this.itens = listaItens;
    }

    public PedidoRequestDTO(Pedido pedido) {
        this.status = pedido.getStatus();
        this.detalhes = pedido.getDetalhes();
        for (ItemPedido itemPedido : pedido.getItensPedido()) {
            itens.add( new ItemPedidoDTO (
                    itemPedido.getId(),
                    itemPedido.getQtd(),
                    itemPedido.getProduto().getNome(),
                    itemPedido.getValor()) );
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
    
    
}
