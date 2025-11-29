package com.controleestoque.api_estoque.repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.controleestoque.api_estoque.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> 
{
    
}