package com.upsaude.service.impl;

import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.response.EnderecoResponse;
import com.upsaude.entity.Endereco;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EnderecoMapper;
import com.upsaude.repository.EnderecoRepository;
import com.upsaude.service.EnderecoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Endereco.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Override
    @Transactional
    public EnderecoResponse criar(EnderecoRequest request) {
        log.debug("Criando novo endereco");

        validarDadosBasicos(request);

        Endereco endereco = enderecoMapper.fromRequest(request);
        endereco.setActive(true);

        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        log.info("Endereco criado com sucesso. ID: {}", enderecoSalvo.getId());

        return enderecoMapper.toResponse(enderecoSalvo);
    }

    @Override
    @Transactional
    public EnderecoResponse obterPorId(UUID id) {
        log.debug("Buscando endereco por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do endereco é obrigatório");
        }

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Endereco não encontrado com ID: " + id));

        return enderecoMapper.toResponse(endereco);
    }

    @Override
    public Page<EnderecoResponse> listar(Pageable pageable) {
        log.debug("Listando Enderecos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Endereco> enderecos = enderecoRepository.findAll(pageable);
        return enderecos.map(enderecoMapper::toResponse);
    }

    @Override
    @Transactional
    public EnderecoResponse atualizar(UUID id, EnderecoRequest request) {
        log.debug("Atualizando endereco. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do endereco é obrigatório");
        }

        validarDadosBasicos(request);

        Endereco enderecoExistente = enderecoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Endereco não encontrado com ID: " + id));

        atualizarDadosEndereco(enderecoExistente, request);

        Endereco enderecoAtualizado = enderecoRepository.save(enderecoExistente);
        log.info("Endereco atualizado com sucesso. ID: {}", enderecoAtualizado.getId());

        return enderecoMapper.toResponse(enderecoAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo endereco. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do endereco é obrigatório");
        }

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Endereco não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(endereco.getActive())) {
            throw new BadRequestException("Endereco já está inativo");
        }

        endereco.setActive(false);
        enderecoRepository.save(endereco);
        log.info("Endereco excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(EnderecoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do endereco são obrigatórios");
        }
    }

        private void atualizarDadosEndereco(Endereco endereco, EnderecoRequest request) {
        Endereco enderecoAtualizado = enderecoMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = endereco.getId();
        com.upsaude.entity.Tenant tenantOriginal = endereco.getTenant();
        Boolean activeOriginal = endereco.getActive();
        java.time.OffsetDateTime createdAtOriginal = endereco.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(enderecoAtualizado, endereco);
        
        // Restaura campos de controle
        endereco.setId(idOriginal);
        endereco.setTenant(tenantOriginal);
        endereco.setActive(activeOriginal);
        endereco.setCreatedAt(createdAtOriginal);
    }
}
