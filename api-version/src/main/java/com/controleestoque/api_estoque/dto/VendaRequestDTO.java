package com.controleestoque.api_estoque.dto;

import java.util.List;

public class VendaRequestDTO {

    private Long clienteId;
    private List<ItemVendaDTO> itens;

    public VendaRequestDTO() {
    }

    public VendaRequestDTO(Long clienteId, List<ItemVendaDTO> itens) {
        this.clienteId = clienteId;
        this.itens = itens;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemVendaDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaDTO> itens) {
        this.itens = itens;
    }
}