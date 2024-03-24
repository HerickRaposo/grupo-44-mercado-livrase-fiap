package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in.ItensPedidoDTOin;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out.ItensPedidoDTOout;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out.PedidoDTOout;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.responde.RestDataReturnDTO;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.ItensPedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.Pedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.repository.ItensPedidoRepository;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service.ItensPedidoService;

@Service
public class ItensPedidoServiceImpl implements ItensPedidoService{
	
    private @Autowired ItensPedidoRepository itensPedidoRepository;
    
	@Override
	public PedidoDTOout salvar(List<ItensPedidoDTOin> itensPedido, Pedido pedido) {
		PedidoDTOout pedidoItensDTO = new PedidoDTOout(pedido);
		for (ItensPedidoDTOin itensPedidoDTOin : itensPedido) {
			ItensPedido itemPedido = this.itensPedidoRepository.save(itensPedidoDTOin.getEntity(pedido));
			
			pedidoItensDTO.getItensPedido().add(new ItensPedidoDTOout(itemPedido));
		}
		return pedidoItensDTO;
	}

	@Override
	public ItensPedidoDTOout atualizar(ItensPedidoDTOin itensPedidoDTOin, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String apagar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItensPedidoDTOout buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestDataReturnDTO buscarTodos(PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
