package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

import jakarta.transaction.Transactional;

@Service
public class ItensPedidoServiceImpl implements ItensPedidoService{
	
    private @Autowired ItensPedidoRepository itensPedidoRepository;
    
    @Transactional
	@Override
	public PedidoDTOout salvar(List<ItensPedidoDTOin> itensPedido, Pedido pedido) {
		PedidoDTOout pedidoItensDTO = new PedidoDTOout(pedido);
		BigDecimal VALOR_TOTAL_PEDIDO=BigDecimal.ZERO;
		BigDecimal VALOR_TOTAL_PRODUTO=BigDecimal.ZERO;; 
		
		for (ItensPedidoDTOin itensPedidoDTOin : itensPedido) {
			ItensPedido itemPedido = this.itensPedidoRepository.save(itensPedidoDTOin.getEntity(pedido));
			pedidoItensDTO.getItensPedido().add(new ItensPedidoDTOout(itemPedido));
			
			VALOR_TOTAL_PRODUTO = itensPedidoDTOin.getValorUnitario().multiply(new BigDecimal(itensPedidoDTOin.getQuantidade()) ).setScale(2, RoundingMode.HALF_UP);
			
			VALOR_TOTAL_PEDIDO=VALOR_TOTAL_PEDIDO.add(VALOR_TOTAL_PRODUTO);
		}
		
		pedido.setValorPedido(VALOR_TOTAL_PEDIDO);
		pedidoItensDTO.setValorPedido(VALOR_TOTAL_PEDIDO);
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
