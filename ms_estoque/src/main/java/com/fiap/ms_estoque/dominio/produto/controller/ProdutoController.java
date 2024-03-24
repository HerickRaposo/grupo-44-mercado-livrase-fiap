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

    @GetMapping("/listar")
    public ResponseEntity<Page<ProdutoDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage
    ) {

        PageRequest pageRequest = PageRequest.of(page, linesPerPage);
        var produto = produtoService.findAll(pageRequest);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/busca/{id}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {
        var produto = produtoService.findById(id);
        return ResponseEntity.ok(produto);
    }


    @PostMapping("/cadastro")
    public ResponseEntity insert(@RequestBody ProdutoDTO produtoDTO) {
        List<String> violacoesToList = produtoService.validate(produtoDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var produtoSaved = produtoService.insert(produtoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand((produtoSaved.getId())).toUri();
        return ResponseEntity.created(uri).body(produtoSaved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@RequestBody ProdutoDTO produtoDTO, @PathVariable Long id) {
        List<String> violacoesToList = produtoService.validate(produtoDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var usrUpdated = produtoService.update(id, produtoDTO);
        return  ResponseEntity.ok(usrUpdated);
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
