package com.fiap.ms_user.dominio.usuario.service;

import com.fiap.ms_user.dominio.usuario.dto.UsuarioDTO;
import com.fiap.ms_user.dominio.usuario.entity.Usuario;
import com.fiap.ms_user.dominio.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repo;

    public List<String> validate(UsuarioDTO dto){
        Set<ConstraintViolation<UsuarioDTO>> violacoes = Validation.buildDefaultValidatorFactory().getValidator().validate(dto);
        List<String> violacoesToList = violacoes.stream()
                .map(violacao -> violacao.getPropertyPath() + ":" + violacao.getMessage())
                .collect(Collectors.toList());
        return violacoesToList;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByEmail(username);
    }

    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(PageRequest pagina) {
        var usuarios = repo.findAll(pagina);

        return usuarios.map(usr ->  new UsuarioDTO(usr));
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {
        var usuario = repo.findById(id).orElseThrow(() -> new RuntimeException("usuario não encontrado"));
        return new UsuarioDTO(usuario);
    }

    @Transactional(readOnly = true)
    public UserDetails findByEmail(String email) {
        var usuario = repo.findByEmail(email);
        if (usuario != null) {
            return usuario;
        }
        throw new RuntimeException("UsuarioNão encontrado");
    }

    @Transactional
    public UsuarioDTO insert(UsuarioDTO dto) {
        var entity = new Usuario();
        BeanUtils.copyProperties(dto, entity);
        if (repo.findByEmail(dto.getEmail()) != null){
            throw new ValidationException("Usuario ja cadastrado");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getSenha());
        entity.setPassword(encryptedPassword);
        var produtoSaved = repo.save(entity);
        return new UsuarioDTO(produtoSaved);
    }

    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO dto) {
        try {
            Usuario entity = repo.getOne(id);
            BeanUtils.copyProperties(dto, entity);
            return new UsuarioDTO(entity);
        } catch (EntityNotFoundException e) {
            throw  new EntityNotFoundException("Produto não encontrado, id:" + id);
        }
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Entity not found with ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Violação de integridade da base");
        }

    }

}
