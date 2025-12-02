package com.upsaude.service.impl;

import com.upsaude.api.request.ConvenioRequest;
import com.upsaude.api.response.ConvenioResponse;
import com.upsaude.entity.Convenio;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ConvenioMapper;
import com.upsaude.repository.ConvenioRepository;
import com.upsaude.service.ConvenioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Convenio.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConvenioServiceImpl implements ConvenioService {

    private final ConvenioRepository convenioRepository;
    private final ConvenioMapper convenioMapper;

    @Override
    @Transactional
    @CacheEvict(value = "convenio", allEntries = true)
    public ConvenioResponse criar(ConvenioRequest request) {
        log.debug("Criando novo convenio");

        validarDadosBasicos(request);

        Convenio convenio = convenioMapper.fromRequest(request);
        convenio.setActive(true);

        Convenio convenioSalvo = convenioRepository.save(convenio);
        log.info("Convenio criado com sucesso. ID: {}", convenioSalvo.getId());

        return convenioMapper.toResponse(convenioSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "convenio", key = "#id")
    public ConvenioResponse obterPorId(UUID id) {
        log.debug("Buscando convenio por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do convenio é obrigatório");
        }

        Convenio convenio = convenioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Convenio não encontrado com ID: " + id));

        return convenioMapper.toResponse(convenio);
    }

    @Override
    public Page<ConvenioResponse> listar(Pageable pageable) {
        log.debug("Listando Convenios paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Convenio> convenios = convenioRepository.findAll(pageable);
        return convenios.map(convenioMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "convenio", key = "#id")
    public ConvenioResponse atualizar(UUID id, ConvenioRequest request) {
        log.debug("Atualizando convenio. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do convenio é obrigatório");
        }

        validarDadosBasicos(request);

        Convenio convenioExistente = convenioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Convenio não encontrado com ID: " + id));

        atualizarDadosConvenio(convenioExistente, request);

        Convenio convenioAtualizado = convenioRepository.save(convenioExistente);
        log.info("Convenio atualizado com sucesso. ID: {}", convenioAtualizado.getId());

        return convenioMapper.toResponse(convenioAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "convenio", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo convenio. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do convenio é obrigatório");
        }

        Convenio convenio = convenioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Convenio não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(convenio.getActive())) {
            throw new BadRequestException("Convenio já está inativo");
        }

        convenio.setActive(false);
        convenioRepository.save(convenio);
        log.info("Convenio excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(ConvenioRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do convenio são obrigatórios");
        }
    }

        private void atualizarDadosConvenio(Convenio convenio, ConvenioRequest request) {
        Convenio convenioAtualizado = convenioMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = convenio.getId();
        com.upsaude.entity.Tenant tenantOriginal = convenio.getTenant();
        Boolean activeOriginal = convenio.getActive();
        java.time.OffsetDateTime createdAtOriginal = convenio.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(convenioAtualizado, convenio);
        
        // Restaura campos de controle
        convenio.setId(idOriginal);
        convenio.setTenant(tenantOriginal);
        convenio.setActive(activeOriginal);
        convenio.setCreatedAt(createdAtOriginal);
    }
}
