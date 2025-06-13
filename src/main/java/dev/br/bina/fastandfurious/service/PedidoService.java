package dev.br.bina.fastandfurious.service;

import dev.br.bina.fastandfurious.dto.PedidoRequestDTO;
import dev.br.bina.fastandfurious.dto.ItemPedidoDTO;
import dev.br.bina.fastandfurious.model.ItemPedido;
import dev.br.bina.fastandfurious.model.Pedido;
import dev.br.bina.fastandfurious.model.Produto;
import dev.br.bina.fastandfurious.repositories.PedidoRepository;
import dev.br.bina.fastandfurious.repositories.ProdutoRepository;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PedidoService {

    @Autowired
    private final PedidoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public List<PedidoRequestDTO> listarTodos() {
        List<PedidoRequestDTO> pedidoDTO = new ArrayList<>();
        
        for (Pedido p : repository.findAll()) {
            pedidoDTO.add(new PedidoRequestDTO(p));
        }
        
        return pedidoDTO;
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

    public PedidoRequestDTO criarPedidoRequestDTO(PedidoRequestDTO pedidoRequestDTO) {

        Pedido pedido = new Pedido();
        pedido.setDetalhes(pedidoRequestDTO.getDetalhes());
        pedido.setStatus("ABERTO");

        List<ItemPedido> itensPedido = new ArrayList<>();

        System.out.println("Carregando itens...");
        for (ItemPedidoDTO item : pedidoRequestDTO.getItens()) {

            ItemPedido itemPedido = new ItemPedido();

            Optional<Produto> p = produtoRepository.findById(item.getId());

            if (p.isPresent()) {
                System.out.println(p.get().getNome());
                itemPedido.setProduto(p.get());
                itemPedido.setQtd(item.getQtd());
                itemPedido.setValor(p.get().getPreco());
                itemPedido.setPedido(pedido);

            } else {
                System.out.println("\n\nSem produto :-(\n\n");
            }

            itensPedido.add(itemPedido);

        }
        
        pedido.setItensPedido(itensPedido);
        
        System.out.println(pedido.toString());
        repository.save(pedido);
        
        // Converter para PedidoRequestDTO e devolver totalmente preenchido.

        return new PedidoRequestDTO(pedido);
    }
}
