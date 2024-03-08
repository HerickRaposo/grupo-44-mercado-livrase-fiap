package com.fiap.grupo44.ms_carrinho.dominio.item.controller;

import com.fiap.grupo44.ms_carrinho.dominio.item.dto.ItemDTO;
import com.fiap.grupo44.ms_carrinho.dominio.item.dto.LoginResponseDTO;
import com.fiap.grupo44.ms_carrinho.dominio.item.service.ItemService;
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
public class ItemController {
    @Autowired
    private ItemService itemoService;

    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public ResponseEntity<Page<ItemDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage
    ) {
        // Obter token do cabeçalho da requisição
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        LoginResponseDTO usuarioResponse = validateToken(token);
        if (usuarioResponse.getRole() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Prosseguir com a lógica original do endpoint
        PageRequest pageRequest = PageRequest.of(page, linesPerPage);
        var listaItens = itemoService.findAll(pageRequest);
        return ResponseEntity.ok(listaItens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> findById(@PathVariable Long id) {
        // Obter token do cabeçalho da requisição
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        LoginResponseDTO usuarioResponse = validateToken(token);
        if (usuarioResponse.getRole() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var item = itemoService.findById(id);
        return ResponseEntity.ok(item);
    }


    @PostMapping
    public ResponseEntity insert(@RequestBody ItemDTO itemDTO) {
        // Obter token do cabeçalho da requisição
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        LoginResponseDTO usuarioResponse = validateToken(token);
        if (usuarioResponse.getRole() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<String> violacoesToList = itemoService.validate(itemDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var produtoSaved = itemoService.insert(itemDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand((produtoSaved.getId())).toUri();
        return ResponseEntity.created(uri).body(produtoSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody ItemDTO itemDTO, @PathVariable Long id) {
        // Obter token do cabeçalho da requisição
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        LoginResponseDTO usuarioResponse = validateToken(token);
        if (usuarioResponse.getRole() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<String> violacoesToList = itemoService.validate(itemDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var itemUpdated = itemoService.update(id, itemDTO);
        return  ResponseEntity.ok(itemUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Obter token do cabeçalho da requisição
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        LoginResponseDTO usuarioResponse = validateToken(token);
        if (usuarioResponse.getRole() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        itemoService.delete(id);
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
                    "http://localhost:8080/login/validateToken",
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
