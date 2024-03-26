package com.fiap.grupo44.ms_carrinho.adapters.out;

import com.fiap.grupo44.ms_carrinho.dominio.item.dto.ItemDTO;
import com.fiap.grupo44.ms_carrinho.dominio.item.dto.PedidoDTOin;
import com.fiap.grupo44.ms_carrinho.dominio.item.dto.PedidoDTOout;
import com.fiap.grupo44.ms_carrinho.dominio.item.dto.rsponse.ResponsePedidoDTO;
import com.fiap.grupo44.ms_carrinho.dominio.produto.ProdutoResponseDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ServiceEstoqueOut {

	@Value("${api-comunicationestoque.host}")
	private String apiHostce;
	
	@Value("${api-comunicationpedido.host}")
	private String apiHostPedido;

	@Value("${api-comunicationestoque.host.json}")
	private String json;
	private final RestTemplate restTemplate=new RestTemplate();

	public ProdutoResponseDTO buscarInformacoesProduto(ItemDTO itemDTO, String bearerToken) {
		String url = apiHostce + itemDTO.getIdProduto();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", bearerToken);
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<ProdutoResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, ProdutoResponseDTO.class);
		ProdutoResponseDTO body = response.getBody();
		return response.getBody();
	}
	
	public ResponsePedidoDTO efetivaCompra(PedidoDTOin pedidoDTOin, String bearerToken) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", bearerToken);
		
		 HttpEntity<PedidoDTOin> httpEntity = new HttpEntity<>(pedidoDTOin, headers);
		 httpEntity.getHeaders();
		
		
		
		ResponseEntity<ResponsePedidoDTO> response = restTemplate.exchange(apiHostPedido, HttpMethod.POST, httpEntity, ResponsePedidoDTO.class);
		return response.getBody();
	}
}
