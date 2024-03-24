package com.fiap.grupo44.ms_pedido.dominio.pedidoitems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in.PedidoDTOin;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.in.ValidarPagamentoDTO;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.out.PedidoDTOout;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.dto.responde.RestDataReturnDTO;
import com.fiap.grupo44.ms_pedido.dominio.pedidoitems.service.PedidoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping(value = "/pedido",produces = {"application/json"})
@Tag(name = "API Gestão de Pedidos")
public class PedidoController {
	
    private @Autowired PedidoService pedidoService;
	

	@PostMapping("/salvar")
	public ResponseEntity<RestDataReturnDTO> salvar(@RequestBody PedidoDTOin pedidoDTOin) {
		RestDataReturnDTO restDataReturnDTO = this.pedidoService.salvar(pedidoDTOin);
		return ResponseEntity.status(HttpStatus.CREATED).body(restDataReturnDTO); 
	}
	
	@PostMapping("/validar-pagamento")
	public ResponseEntity<RestDataReturnDTO> validarPagamento(@RequestBody ValidarPagamentoDTO validarPagamentoDTO) {
		RestDataReturnDTO restDataReturnDTO = this.pedidoService.validarPagamento(validarPagamentoDTO);
		return ResponseEntity.status(HttpStatus.OK).body(restDataReturnDTO); 
	}
	
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<PedidoDTOout> atualizar(@RequestBody PedidoDTOin pedidoDTOin,@PathVariable Long id) {
		PedidoDTOout pedidoDTO = this.pedidoService.atualizar(pedidoDTOin,id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(pedidoDTO); 
	}
	
	@Transactional
	@DeleteMapping("/apagar")
	public ResponseEntity<RestDataReturnDTO> apagar(@RequestParam Long idPedido) {
		
		System.err.println(idPedido);
		RestDataReturnDTO restDataReturnDTO = this.pedidoService.apagar(idPedido);
		return ResponseEntity.status(HttpStatus.OK).body(restDataReturnDTO);
	}
	
	@GetMapping("/buscarPorId")
	public ResponseEntity<RestDataReturnDTO> getID(@RequestParam Long idPedido) {
		RestDataReturnDTO restDataReturnDTO = this.pedidoService.buscarPorId(idPedido);
		return ResponseEntity.status(HttpStatus.FOUND).body(restDataReturnDTO); 
	}
	
	@GetMapping("/buscar-todos")
	public ResponseEntity<RestDataReturnDTO> getAll(@RequestParam(value = "pagina", defaultValue  = "0")  Integer pagina,@RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho) {
		 RestDataReturnDTO restDataReturnDTO = this.pedidoService.buscarTodos(PageRequest.of(pagina,tamanho));
		return ResponseEntity.status(HttpStatus.FOUND).body(restDataReturnDTO); 

	}
}
