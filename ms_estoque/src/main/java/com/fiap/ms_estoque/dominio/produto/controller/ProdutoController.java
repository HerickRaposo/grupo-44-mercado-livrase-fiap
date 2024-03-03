package com.fiap.ms_estoque.dominio.produto.controller;


import com.fiap.ms_estoque.dominio.produto.dto.LoginResponseDTO;
import com.fiap.ms_estoque.dominio.produto.dto.ProdutoDTO;
import com.fiap.ms_estoque.dominio.produto.service.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/produto",produces = {"application/json"})
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage
    ) {
        // Obter token do cabeçalho da requisição
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        LoginResponseDTO usuarioResponse = validateToken(token);
        if (!usuarioResponse.getRole().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Prosseguir com a lógica original do endpoint
        PageRequest pageRequest = PageRequest.of(page, linesPerPage);
        var produto = produtoService.findAll(pageRequest);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {
        var produto = produtoService.findById(id);
        return ResponseEntity.ok(produto);
    }


    @PostMapping
    public ResponseEntity insert(@RequestBody ProdutoDTO produtoDTO) {
        List<String> violacoesToList = produtoService.validate(produtoDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var produtoSaved = produtoService.insert(produtoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand((produtoSaved.getId())).toUri();
        return ResponseEntity.created(uri).body(produtoSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody ProdutoDTO produtoDTO, @PathVariable Long id) {
        List<String> violacoesToList = produtoService.validate(produtoDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var usrUpdated = produtoService.update(id, produtoDTO);
        return  ResponseEntity.ok(usrUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private LoginResponseDTO validateToken(String token) {
        try {
            // Criar objeto de requisição HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            // Chamar o endpoint validateToken do ms_user
            ResponseEntity<LoginResponseDTO> responseEntity = restTemplate.exchange(
                    "https://blog.syss.com/posts/abusing-ms-office-protos//usuario/validateToken",
                    HttpMethod.GET,
                    requestEntity,
                    LoginResponseDTO.class
            );

            // Retornar a resposta do endpoint
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            // Tratar a exceção de acordo com o código de erro
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new RuntimeException("Token inválido ou expirado");
            } else {
                throw new RuntimeException("Erro ao validar o token");
            }
        }
    }
}
