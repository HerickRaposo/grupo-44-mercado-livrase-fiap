package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in.ItensPedidoDTOin;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out.ItensPedidoDTOout;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out.PedidoDTOout;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.responde.RestDataReturnDTO;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.Pedido;

@Service
public interface ItensPedidoService {
	PedidoDTOout salvar(List<ItensPedidoDTOin> itensPedido,Pedido pedido);
	ItensPedidoDTOout atualizar(ItensPedidoDTOin itensPedidoDTOin,Long id);
	String apagar(Long id);
	ItensPedidoDTOout buscarPorId(Long id);
	RestDataReturnDTO buscarTodos(PageRequest pageRequest);
}
