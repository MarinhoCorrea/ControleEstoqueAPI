
package com.controleestoque.api_estoque.dto;

public class ItemVendaDTO {

    private Long produtoId;
    private Integer quantidade;

    public ItemVendaDTO() {
    }

    public ItemVendaDTO(Long produtoId, Integer quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}