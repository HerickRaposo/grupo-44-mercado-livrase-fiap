package com.fiap.grupo44.ms_carrinho.dominio.item.service;

import com.fiap.grupo44.ms_carrinho.dominio.item.dto.ItemDTO;
import com.fiap.grupo44.ms_carrinho.dominio.item.entity.Item;
import com.fiap.grupo44.ms_carrinho.dominio.item.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
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
public class ItemService {
    @Autowired
    private ItemRepository repo;


    public List<String> validate(ItemDTO dto){
        Set<ConstraintViolation<ItemDTO>> violacoes = Validation.buildDefaultValidatorFactory().getValidator().validate(dto);
        List<String> violacoesToList = violacoes.stream()
                .map(violacao -> violacao.getPropertyPath() + ":" + violacao.getMessage())
                .collect(Collectors.toList());
        return violacoesToList;
    }

    @Transactional(readOnly = true)
    public Page<ItemDTO> findAll(PageRequest pagina) {
        var listaItens = repo.findAll(pagina);
        //TODO: Implementar função que busca informações do item e que  multiplique valor obtido por quantidade
        return listaItens.map(item ->  new ItemDTO(item));
    }

    @Transactional(readOnly = true)
    public ItemDTO findById(Long id) {
        var item = repo.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return new ItemDTO(item);
    }

    @Transactional
    public ItemDTO insert(ItemDTO dto) {
        var entity = new Item();
        BeanUtils.copyProperties(dto, entity);
        var itemSaved = repo.save(entity);
        return new ItemDTO(itemSaved);
    }

    @Transactional
    public ItemDTO update(Long id, ItemDTO dto) {
        try {
            Item entity = repo.getOne(id);
            BeanUtils.copyProperties(dto, entity);
            //TODO: Fazer busca no banco e adicionar quantidade adicionando ou subtraindo valor
            return new ItemDTO(entity);
        } catch (EntityNotFoundException e) {
            throw  new EntityNotFoundException("Item não encontrado, id:" + id);
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
