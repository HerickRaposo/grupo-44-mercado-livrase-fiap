package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service;

import org.springframework.data.domain.PageRequest;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in.PedidoDTOin;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out.PedidoDTOout;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.responde.RestDataReturnDTO;



public interface PedidoService {
	PedidoDTOout salvar(PedidoDTOin pedidoDTOin);
	PedidoDTOout atualizar(PedidoDTOin pedidoDTOin,Long id);
	String apagar(Long id);
	PedidoDTOout buscarPorId(Long id);
	RestDataReturnDTO buscarTodos(PageRequest pageRequest);
}
