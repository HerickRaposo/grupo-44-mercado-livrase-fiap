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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/carrinho",produces = {"application/json"})
public class ItemController {
    @Autowired
    private ItemService itemoService;

    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public ResponseEntity<Page<ItemDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
            @RequestParam(value = "email_usuario", required = false) String filtroEmailUsuario
    ) {
        // Prosseguir com a lógica original do endpoint
        PageRequest pageRequest = PageRequest.of(page, linesPerPage);
        var listaItens = itemoService.findAll(pageRequest,filtroEmailUsuario);
        return ResponseEntity.ok(listaItens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> findById(@PathVariable Long id) {
        var item = itemoService.findById(id);
        return ResponseEntity.ok(item);
    }


    @PostMapping
    public ResponseEntity insert(@RequestBody ItemDTO itemDTO) {
        // Obter token do cabeçalho da requisição

        List<String> violacoesToList = itemoService.validate(itemDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        var produtoSaved = itemoService.insert(itemDTO,token);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand((produtoSaved.getId())).toUri();
        return ResponseEntity.created(uri).body(produtoSaved);
    }

    @PatchMapping("atualizaqtde/{id}/{novaQuantidade}")
    public ResponseEntity<?> updateQuantidade(@PathVariable Long id, @PathVariable Long novaQuantidade) {
       if (novaQuantidade != 0L){
           ItemDTO itemDTO = itemoService.updateQuantidade(id, novaQuantidade);
           return ResponseEntity.ok(itemDTO);
       } else {
           itemoService.delete(id);
           return ResponseEntity.noContent().build();
       }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
