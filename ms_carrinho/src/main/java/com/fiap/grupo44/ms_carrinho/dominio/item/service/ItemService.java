package com.fiap.grupo44.ms_carrinho.dominio.item.service;

import com.fiap.grupo44.ms_carrinho.adapters.out.ServiceEstoqueOut;
import com.fiap.grupo44.ms_carrinho.dominio.item.dto.FecharPedidoDTO;
import com.fiap.grupo44.ms_carrinho.dominio.item.dto.ItemDTO;
import com.fiap.grupo44.ms_carrinho.dominio.item.dto.ItensPedidoDTOin;
import com.fiap.grupo44.ms_carrinho.dominio.item.dto.PedidoDTOin;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import io.jsonwebtoken.Jwts;
import org.springframework.util.StringUtils;

import static org.springframework.cache.interceptor.SimpleKeyGenerator.generateKey;

@Service
public class ItemService {
    @Autowired
    private ItemRepository repo;

    @Autowired
    private ServiceEstoqueOut serviceEstoqueOut;
    @Autowired
    private JwtService jwtService;


    public List<String> validate(ItemDTO dto){
        Set<ConstraintViolation<ItemDTO>> violacoes = Validation.buildDefaultValidatorFactory().getValidator().validate(dto);
        List<String> violacoesToList = violacoes.stream()
                .map(violacao -> violacao.getPropertyPath() + ":" + violacao.getMessage())
                .collect(Collectors.toList());
        return violacoesToList;
    }

    @Transactional(readOnly = true)
    public Page<ItemDTO> findAll(PageRequest pagina,String filtroEmailUsuario) {
        List<ItemDTO> listaItens = new ArrayList<>();
        Specification<Item> specification = Specification.where(null);
        if (!StringUtils.isEmpty(filtroEmailUsuario)) {
            specification = specification.and((root, query, builder) ->
                    builder.equal(root.get("emailUsuario"), filtroEmailUsuario));
        }

        var itens = repo.findAll(specification, pagina);

        for (Item item : itens.getContent()) {
            ItemDTO itemDTO = new ItemDTO(item);
            BeanUtils.copyProperties(item, itemDTO);
            listaItens.add(itemDTO);
        }

        Page<ItemDTO> pageLocalidadeDTO = new PageImpl<>(listaItens, pagina, itens.getTotalElements());
        return pageLocalidadeDTO;
    }

    @Transactional(readOnly = true)
    public ItemDTO findById(Long id) {
        var item = repo.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return new ItemDTO(item);
    }

    @Transactional
    public ItemDTO insert(ItemDTO dto, String bearerToken) {
        var entity = new Item();
        dto = processaProdutoEstoque(dto,bearerToken);
        
        //String username = jwtService.extractUsername(bearerToken);
        dto.setEmailUsuario(dto.getEmailUsuario());
        BeanUtils.copyProperties(dto, entity);
        var itemSaved = repo.save(entity);
        return new ItemDTO(itemSaved);
    }
    
    @Transactional
    public PedidoDTOin fecharCompra(FecharPedidoDTO fecharPedidoDTO, String bearerToken) {
        //INVOCAR AQUI O MS-PEDIDO
    	PedidoDTOin pedidoDTOin=new PedidoDTOin();
    	pedidoDTOin.setEmailUsuario(fecharPedidoDTO.getEmailUsuario());
    	pedidoDTOin.setFormaPagamento(fecharPedidoDTO.getFormaPagamento());
    	
    	List<Item> produtosNoCarrinho = this.repo.BUSCAR_TODOS_PRODUTOS_NO_CARRINHO(fecharPedidoDTO.getEmailUsuario());
    	ItensPedidoDTOin itensPedidoDTOin=null;
    	for (Item item : produtosNoCarrinho) {
    		itensPedidoDTOin=new ItensPedidoDTOin();
    		
    		itensPedidoDTOin.setDescricao(item.getDescricao());
    		itensPedidoDTOin.setIdProduto(item.getIdProduto());
    		itensPedidoDTOin.setQuantidade(item.getQuantidade());
    		itensPedidoDTOin.setValorUnitario(item.getValor());
    		
    		pedidoDTOin.getItensPedido().add(itensPedidoDTOin);
    		
    		item.setCompraFechada(true);
    		this.repo.save(item);
		}
    	
    	//INVOCAR A API DE PEDIDO
    	serviceEstoqueOut.efetivaCompra(pedidoDTOin, bearerToken);
    	
        return pedidoDTOin;
    } 
    
   

    @Transactional
    public ItemDTO updateQuantidade(Long id, Long novaQuantidade) {
        Item item = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        Double valorUnit = item.getValor() / item.getQuantidade();
        item.setQuantidade(novaQuantidade);
        item.setValor(valorUnit * novaQuantidade);
        var itemSaved = repo.save(item);
        return new ItemDTO(itemSaved);
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

    private ItemDTO processaProdutoEstoque(ItemDTO item, String bearerToken){
        var produto = serviceEstoqueOut.buscarInformacoesProduto(item,bearerToken);
        item.setIdProduto(produto.getId());
        item.setDescricao(produto.getDescricao());
        item.setValor(produto.getValorUnitario() * item.getQuantidade());
        return item;
    }
}
