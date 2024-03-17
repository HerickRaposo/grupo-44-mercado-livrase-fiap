package com.fiap.ms_estoque.dominio.produto.repository;

import com.fiap.ms_estoque.dominio.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    Produto findByDescricao(String descricao);
}
