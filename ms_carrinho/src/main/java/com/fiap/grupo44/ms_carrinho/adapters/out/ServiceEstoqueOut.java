package com.fiap.grupo44.ms_carrinho.adapters.out;

import com.fiap.grupo44.ms_carrinho.dominio.item.dto.ItemDTO;
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

	@Value("${api-comunicationestoque.host.json}")
	private String json;
	private final RestTemplate restTemplate=new RestTemplate();

	public ProdutoResponseDTO buscarInformacoesProduto(ItemDTO itemDTO, String bearerToken) {
		String url = apiHostce + itemDTO.getIdProduto();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(bearerToken);
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<ProdutoResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, ProdutoResponseDTO.class);
		return response.getBody();
	}

//	public EnderecoResultViaCepDTO populaCep(String cepDTO) {
//		return this.restTemplate.getForEntity(
//				apiHostce+cepDTO+json,
//				EnderecoResultViaCepDTO.class).getBody();
//	}
}
