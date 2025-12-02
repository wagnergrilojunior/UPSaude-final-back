package com.upsaude.service.impl;

import com.upsaude.api.request.TratamentosOdontologicosRequest;
import com.upsaude.api.response.TratamentosOdontologicosResponse;
import com.upsaude.entity.TratamentosOdontologicos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.TratamentosOdontologicosMapper;
import com.upsaude.repository.TratamentosOdontologicosRepository;
import com.upsaude.service.TratamentosOdontologicosService;
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
 * Implementação do serviço de gerenciamento de TratamentosOdontologicos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TratamentosOdontologicosServiceImpl implements TratamentosOdontologicosService {

    private final TratamentosOdontologicosRepository tratamentosOdontologicosRepository;
    private final TratamentosOdontologicosMapper tratamentosOdontologicosMapper;

    @Override
    @Transactional
    @CacheEvict(value = "tratamentosodontologicos", allEntries = true)
    public TratamentosOdontologicosResponse criar(TratamentosOdontologicosRequest request) {
        log.debug("Criando novo tratamentosodontologicos");

        validarDadosBasicos(request);

        TratamentosOdontologicos tratamentosOdontologicos = tratamentosOdontologicosMapper.fromRequest(request);
        tratamentosOdontologicos.setActive(true);

        TratamentosOdontologicos tratamentosOdontologicosSalvo = tratamentosOdontologicosRepository.save(tratamentosOdontologicos);
        log.info("TratamentosOdontologicos criado com sucesso. ID: {}", tratamentosOdontologicosSalvo.getId());

        return tratamentosOdontologicosMapper.toResponse(tratamentosOdontologicosSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "tratamentosodontologicos", key = "#id")
    public TratamentosOdontologicosResponse obterPorId(UUID id) {
        log.debug("Buscando tratamentosodontologicos por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do tratamentosodontologicos é obrigatório");
        }

        TratamentosOdontologicos tratamentosOdontologicos = tratamentosOdontologicosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TratamentosOdontologicos não encontrado com ID: " + id));

        return tratamentosOdontologicosMapper.toResponse(tratamentosOdontologicos);
    }

    @Override
    public Page<TratamentosOdontologicosResponse> listar(Pageable pageable) {
        log.debug("Listando TratamentosOdontologicos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<TratamentosOdontologicos> tratamentosOdontologicos = tratamentosOdontologicosRepository.findAll(pageable);
        return tratamentosOdontologicos.map(tratamentosOdontologicosMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "tratamentosodontologicos", key = "#id")
    public TratamentosOdontologicosResponse atualizar(UUID id, TratamentosOdontologicosRequest request) {
        log.debug("Atualizando tratamentosodontologicos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do tratamentosodontologicos é obrigatório");
        }

        validarDadosBasicos(request);

        TratamentosOdontologicos tratamentosOdontologicosExistente = tratamentosOdontologicosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TratamentosOdontologicos não encontrado com ID: " + id));

        atualizarDadosTratamentosOdontologicos(tratamentosOdontologicosExistente, request);

        TratamentosOdontologicos tratamentosOdontologicosAtualizado = tratamentosOdontologicosRepository.save(tratamentosOdontologicosExistente);
        log.info("TratamentosOdontologicos atualizado com sucesso. ID: {}", tratamentosOdontologicosAtualizado.getId());

        return tratamentosOdontologicosMapper.toResponse(tratamentosOdontologicosAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "tratamentosodontologicos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo tratamentosodontologicos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do tratamentosodontologicos é obrigatório");
        }

        TratamentosOdontologicos tratamentosOdontologicos = tratamentosOdontologicosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TratamentosOdontologicos não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(tratamentosOdontologicos.getActive())) {
            throw new BadRequestException("TratamentosOdontologicos já está inativo");
        }

        tratamentosOdontologicos.setActive(false);
        tratamentosOdontologicosRepository.save(tratamentosOdontologicos);
        log.info("TratamentosOdontologicos excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(TratamentosOdontologicosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do tratamentosodontologicos são obrigatórios");
        }
    }

        private void atualizarDadosTratamentosOdontologicos(TratamentosOdontologicos tratamentosOdontologicos, TratamentosOdontologicosRequest request) {
        TratamentosOdontologicos tratamentosOdontologicosAtualizado = tratamentosOdontologicosMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = tratamentosOdontologicos.getId();
        com.upsaude.entity.Tenant tenantOriginal = tratamentosOdontologicos.getTenant();
        Boolean activeOriginal = tratamentosOdontologicos.getActive();
        java.time.OffsetDateTime createdAtOriginal = tratamentosOdontologicos.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(tratamentosOdontologicosAtualizado, tratamentosOdontologicos);
        
        // Restaura campos de controle
        tratamentosOdontologicos.setId(idOriginal);
        tratamentosOdontologicos.setTenant(tenantOriginal);
        tratamentosOdontologicos.setActive(activeOriginal);
        tratamentosOdontologicos.setCreatedAt(createdAtOriginal);
    }
}
