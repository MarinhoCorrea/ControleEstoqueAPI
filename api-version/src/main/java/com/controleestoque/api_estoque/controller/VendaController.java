package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.dto.ItemVendaDTO;
import com.controleestoque.api_estoque.dto.VendaRequestDTO;
import com.controleestoque.api_estoque.model.*;
import com.controleestoque.api_estoque.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    @GetMapping
    public List<Venda> getAllVendas() {
        return vendaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getVendaById(@PathVariable Long id) {
        return vendaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createVenda(@RequestBody VendaRequestDTO vendaRequest) {
        

        Cliente cliente = clienteRepository.findById(vendaRequest.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));


        Map<String, String> errosEstoque = new HashMap<>();
        
        for (ItemVendaDTO itemDTO : vendaRequest.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: ID " + itemDTO.getProdutoId()));

            Estoque estoque = estoqueRepository.findByProduto(produto)
                    .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto: " + produto.getNome()));

            if (estoque.getQuantidade() < itemDTO.getQuantidade()) {
                errosEstoque.put(produto.getNome(), 
                    String.format("Estoque insuficiente. Disponível: %d, Solicitado: %d", 
                        estoque.getQuantidade(), itemDTO.getQuantidade()));
            }
        }

        if (!errosEstoque.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("erro", "Estoque insuficiente para os seguintes produtos:");
            response.put("detalhes", errosEstoque);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Venda venda = new Venda();
        venda.setCliente(cliente);
        
        List<ItemVenda> itensVenda = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemVendaDTO itemDTO : vendaRequest.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId()).get();
            Estoque estoque = estoqueRepository.findByProduto(produto).get();

            
            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setVenda(venda);
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(itemDTO.getQuantidade());
            itemVenda.setPrecoUnitario(produto.getPreco());
            
            itensVenda.add(itemVenda);
            
            
            valorTotal = valorTotal.add(itemVenda.getSubtotal());

            
            estoque.setQuantidade(estoque.getQuantidade() - itemDTO.getQuantidade());
            estoqueRepository.save(estoque);
        }

        venda.setItens(itensVenda);
        venda.setValorTotal(valorTotal);

        Venda vendaSalva = vendaRepository.save(venda);

        return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id) {
        if (!vendaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        vendaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
