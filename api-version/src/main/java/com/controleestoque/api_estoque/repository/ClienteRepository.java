package com.controleestoque.api_estoque.repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.controleestoque.api_estoque.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> 
{
    
}