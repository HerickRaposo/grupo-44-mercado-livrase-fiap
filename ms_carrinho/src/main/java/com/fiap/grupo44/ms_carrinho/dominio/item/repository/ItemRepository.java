package com.fiap.grupo44.ms_carrinho.dominio.item.repository;

import com.fiap.grupo44.ms_carrinho.dominio.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    Item findByIdProduto(Long id);
}
