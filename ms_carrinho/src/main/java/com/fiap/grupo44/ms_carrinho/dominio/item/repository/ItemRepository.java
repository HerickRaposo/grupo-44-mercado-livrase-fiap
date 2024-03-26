package com.fiap.grupo44.ms_carrinho.dominio.item.repository;

import com.fiap.grupo44.ms_carrinho.dominio.item.entity.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    Item findByIdProduto(Long id);
    
    @Query(value = "SELECT * FROM ITEM A WHERE A.EMAIL_USUARIO=?1 AND COMPRA_FECHADA=0",nativeQuery = true)
    List<Item> BUSCAR_TODOS_PRODUTOS_NO_CARRINHO(String emailUsuario);
    
}
