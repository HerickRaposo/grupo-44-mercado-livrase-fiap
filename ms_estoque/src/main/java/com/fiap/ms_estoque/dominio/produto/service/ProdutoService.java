package com.fiap.ms_estoque.dominio.produto.service;

import com.fiap.ms_estoque.dominio.produto.dto.ProdutoDTO;
import com.fiap.ms_estoque.dominio.produto.entity.Produto;
import com.fiap.ms_estoque.dominio.produto.repository.ProdutoRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repo;


    public List<String> validate(ProdutoDTO dto){
        Set<ConstraintViolation<ProdutoDTO>> violacoes = Validation.buildDefaultValidatorFactory().getValidator().validate(dto);
        List<String> violacoesToList = violacoes.stream()
                .map(violacao -> violacao.getPropertyPath() + ":" + violacao.getMessage())
                .collect(Collectors.toList());
        return violacoesToList;
    }

    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findAll(PageRequest pagina) {
        var produtos = repo.findAll(pagina);

        return produtos.map(prod ->  new ProdutoDTO(prod));
    }

    @Transactional(readOnly = true)
    public ProdutoDTO findById(Long id) {
        var produto = repo.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return new ProdutoDTO(produto);
    }

    @Transactional
    public ProdutoDTO insert(ProdutoDTO dto) {
        var entity = new Produto();
        BeanUtils.copyProperties(dto, entity);
        if (repo.findByDescricao(dto.getDescricao()) != null){
            throw new ValidationException("Produto ja cadastrado");     //TODO: TRATAR LANÇAMENTO DE EXCESSÃO
        }

        var produtoSaved = repo.save(entity);
        return new ProdutoDTO(produtoSaved);
    }

    @Transactional
    public ProdutoDTO update(Long id, ProdutoDTO dto) {
        try {
            Produto entity = repo.getOne(id);
            BeanUtils.copyProperties(dto, entity);
            return new ProdutoDTO(entity);
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

    public Boolean verificaDisponibilidadeImtem(ProdutoDTO dto)throws Exception{
        var itemBuscado = findById(dto.getId());
        if (itemBuscado.getQuantidadeEstoque() >= dto.getQuantidadeEstoque()){
            return true;
        }
        return false;
    }
}
