package com.fiap.ms_user.dominio.usuario.controller;
import com.fiap.ms_user.dominio.usuario.dto.UsuarioDTO;
import com.fiap.ms_user.dominio.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/usuario",produces = {"application/json"})
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage);
        var usuario = usuarioService.findAll(pageRequest);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        var usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }


    @PostMapping("/aqui")
    public ResponseEntity insert(@RequestBody UsuarioDTO usuarioDTO) {
        List<String> violacoesToList = usuarioService.validate(usuarioDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var usuarioSaved = usuarioService.insert(usuarioDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand((usuarioSaved.getId())).toUri();
        return ResponseEntity.created(uri).body(usuarioSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody UsuarioDTO usuarioDTO, @PathVariable Long id) {
        List<String> violacoesToList = usuarioService.validate(usuarioDTO);
        if (!violacoesToList.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToList);
        }
        var usrUpdated = usuarioService.update(id, usuarioDTO);
        return  ResponseEntity.ok(usrUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
