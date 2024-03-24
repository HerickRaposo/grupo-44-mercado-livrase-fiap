package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in.PedidoDTOin;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in.ValidarPagamentoDTO;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.responde.RestDataReturnDTO;

@Service
public interface PedidoService {
	RestDataReturnDTO salvar(PedidoDTOin pedidoDTOin);
	RestDataReturnDTO apagar(Long id);
	RestDataReturnDTO buscarPorId(Long id);
	RestDataReturnDTO buscarTodos(PageRequest pageRequest);
	RestDataReturnDTO validarPagamento(ValidarPagamentoDTO validarPagamentoDTO);
}
