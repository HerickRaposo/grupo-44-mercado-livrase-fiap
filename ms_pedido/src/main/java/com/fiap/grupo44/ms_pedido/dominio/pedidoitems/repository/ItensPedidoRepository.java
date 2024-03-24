package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.ItensPedido;

public interface ItensPedidoRepository extends JpaRepository<ItensPedido, Long>{
	@Query(value = "SELECT * FROM TB_ITENS_PEDIDO A WHERE A.PEDIDO_ID=?1",nativeQuery = true)
	List<ItensPedido> BUSCAR_ITENS_POR_PEDIDO(Long idPedido);
}
