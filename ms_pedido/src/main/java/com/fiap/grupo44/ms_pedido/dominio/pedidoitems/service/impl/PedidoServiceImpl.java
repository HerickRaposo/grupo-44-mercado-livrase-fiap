package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in.PedidoDTOin;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in.ValidarPagamentoDTO;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out.ItensPedidoDTOout;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out.PedidoDTOout;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.responde.Paginator;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.responde.RestDataReturnDTO;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.ItensPedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.entity.Pedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.enumerations.EstadoPedido;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.exceptions.ControllerNotFoundException;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.repository.PedidoRepository;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service.ItensPedidoService;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service.PedidoService;

import jakarta.transaction.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService{
	
	private @Autowired PedidoRepository pedidoRepository;
	private @Autowired ItensPedidoService itensPedidoService;
	
	@Transactional
	@Override
	public RestDataReturnDTO salvar(PedidoDTOin pedidoDTOin) {
		//001. PERSISTIR O PEDIDO
		Pedido pedido = this.pedidoRepository.save(pedidoDTOin.getEntity());

		//002. INVOCAR O SALVAR DO PEDIDO ITENS
		PedidoDTOout pedidoItensDTO = this.itensPedidoService.salvar(pedidoDTOin.getItensPedido(), pedido);
		
		return new RestDataReturnDTO(pedidoItensDTO, "Pedido efetuado com sucesso!");
	}
	
	@Transactional
	@Override
	public RestDataReturnDTO validarPagamento(ValidarPagamentoDTO validarPagamentoDTO) {
		try {
			 Optional<Pedido> OPedido = this.pedidoRepository.findById(validarPagamentoDTO.idPedido());
			 
			 Pedido pedido = OPedido.get();
			 System.err.println(!EstadoPedido.CANCELADO.equals(pedido.getEstadoPedido())+" - "+pedido.getEstadoPedido());
			 
			 System.err.println(!EstadoPedido.PAGO.equals(pedido.getEstadoPedido()));
			 if(EstadoPedido.CANCELADO.equals(pedido.getEstadoPedido()))
				 return new RestDataReturnDTO(validarPagamentoDTO, "O pedido já se econtra "+pedido.getEstadoPedido()+" e não pode ser pago.");
			   
				 if(!EstadoPedido.PAGO.equals(pedido.getEstadoPedido())) {
				   pedido.setDataPagamento(validarPagamentoDTO.dataPagamento());
				   pedido.setEstadoPedido(EstadoPedido.PAGO);
				   this.pedidoRepository.save(pedido);
				   return new RestDataReturnDTO(pedido, "Pagamento do pedido validado com sucesso!");				 
			   }
			 
			 return new RestDataReturnDTO(validarPagamentoDTO, "O pedido já se econtra "+pedido.getEstadoPedido()+" e não pode ser pago.");
			 
		}catch(Exception e) {
			throw new ControllerNotFoundException("Pedido não encontrado, id: " + validarPagamentoDTO.idPedido());			
		}
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
	public RestDataReturnDTO buscarPorId(Long id) {
		try {
			 Optional<Pedido> OPedido = this.pedidoRepository.findById(id);
			 Pedido pedido = OPedido.get();
			 PedidoDTOout pedidoDTOout = new PedidoDTOout(pedido);

			 List<ItensPedido> ItensPedido = this.itensPedidoService.buscarItensPorPedido(id);
			 for (ItensPedido o : ItensPedido) {
				 pedidoDTOout.getItensPedido().add(new ItensPedidoDTOout(o));
			 }
			 
			 return new RestDataReturnDTO(pedidoDTOout, "Buscar efetivada com sucesso!");
		}catch(Exception e) {
			throw new ControllerNotFoundException("Registo não encontrado, id: " + id);			
		}
	}

	@Override
	public RestDataReturnDTO buscarTodos(PageRequest pageRequest) {
		Page<Pedido> pedidoPage = this.pedidoRepository.findAll(pageRequest);
		List<PedidoDTOout> pedidoDTOouts=new ArrayList<PedidoDTOout>();
		PedidoDTOout pedidoDTOout;
		if(!pedidoPage.isEmpty()) {
			List<Pedido> pedidosItens = pedidoPage.getContent();
			for (Pedido pedido : pedidosItens) {
				pedidoDTOout=new PedidoDTOout(pedido,itensPedidoService);
				pedidoDTOouts.add(pedidoDTOout);	
			}	
		}
		return new RestDataReturnDTO(pedidoDTOouts,new Paginator(pedidoPage.getNumber(), pedidoPage.getTotalElements(), pedidoPage.getTotalPages()));
	}
}
