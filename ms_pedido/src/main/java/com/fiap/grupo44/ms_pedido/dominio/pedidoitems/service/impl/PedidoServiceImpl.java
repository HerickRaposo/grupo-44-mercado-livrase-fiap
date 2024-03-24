package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in.PedidoDTOin;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out.PedidoDTOout;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.responde.RestDataReturnDTO;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.Pedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.repository.PedidoRepository;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service.ItensPedidoService;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service.PedidoService;

import jakarta.transaction.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService{
	
	private @Autowired PedidoRepository PedidoRepository;
	private @Autowired ItensPedidoService itensPedidoService;
	
	@Transactional
	@Override
	public RestDataReturnDTO salvar(PedidoDTOin pedidoDTOin) {
		//001. PERSISTIR O PEDIDO
		Pedido pedido = this.PedidoRepository.save(pedidoDTOin.getEntity());

		//002. INVOCAR O SALVAR DO PEDIDO ITENS
		PedidoDTOout pedidoItensDTO = this.itensPedidoService.salvar(pedidoDTOin.getItensPedido(), pedido);
		
		return new RestDataReturnDTO(pedidoItensDTO, "Pedido efetuado com sucesso!");
	}

	@Override
	public PedidoDTOout atualizar(PedidoDTOin pedidoDTOin, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String apagar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PedidoDTOout buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestDataReturnDTO buscarTodos(PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}
}
