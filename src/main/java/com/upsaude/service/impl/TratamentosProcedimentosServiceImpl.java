package com.upsaude.service.impl;

import com.upsaude.api.request.TratamentosProcedimentosRequest;
import com.upsaude.api.response.TratamentosProcedimentosResponse;
import com.upsaude.entity.TratamentosProcedimentos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.TratamentosProcedimentosMapper;
import com.upsaude.repository.TratamentosProcedimentosRepository;
import com.upsaude.service.TratamentosProcedimentosService;
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
 * Implementação do serviço de gerenciamento de TratamentosProcedimentos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TratamentosProcedimentosServiceImpl implements TratamentosProcedimentosService {

    private final TratamentosProcedimentosRepository tratamentosProcedimentosRepository;
    private final TratamentosProcedimentosMapper tratamentosProcedimentosMapper;

    @Override
    @Transactional
    @CacheEvict(value = "tratamentosprocedimentos", allEntries = true)
    public TratamentosProcedimentosResponse criar(TratamentosProcedimentosRequest request) {
        log.debug("Criando novo tratamentosprocedimentos");

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        TratamentosProcedimentos tratamentosProcedimentos = tratamentosProcedimentosMapper.fromRequest(request);
        tratamentosProcedimentos.setActive(true);

        TratamentosProcedimentos tratamentosProcedimentosSalvo = tratamentosProcedimentosRepository.save(tratamentosProcedimentos);
        log.info("TratamentosProcedimentos criado com sucesso. ID: {}", tratamentosProcedimentosSalvo.getId());

        return tratamentosProcedimentosMapper.toResponse(tratamentosProcedimentosSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "tratamentosprocedimentos", key = "#id")
    public TratamentosProcedimentosResponse obterPorId(UUID id) {
        log.debug("Buscando tratamentosprocedimentos por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do tratamentosprocedimentos é obrigatório");
        }

        TratamentosProcedimentos tratamentosProcedimentos = tratamentosProcedimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TratamentosProcedimentos não encontrado com ID: " + id));

        return tratamentosProcedimentosMapper.toResponse(tratamentosProcedimentos);
    }

    @Override
    public Page<TratamentosProcedimentosResponse> listar(Pageable pageable) {
        log.debug("Listando TratamentosProcedimentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<TratamentosProcedimentos> tratamentosProcedimentos = tratamentosProcedimentosRepository.findAll(pageable);
        return tratamentosProcedimentos.map(tratamentosProcedimentosMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "tratamentosprocedimentos", key = "#id")
    public TratamentosProcedimentosResponse atualizar(UUID id, TratamentosProcedimentosRequest request) {
        log.debug("Atualizando tratamentosprocedimentos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do tratamentosprocedimentos é obrigatório");
        }

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        TratamentosProcedimentos tratamentosProcedimentosExistente = tratamentosProcedimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TratamentosProcedimentos não encontrado com ID: " + id));

        atualizarDadosTratamentosProcedimentos(tratamentosProcedimentosExistente, request);

        TratamentosProcedimentos tratamentosProcedimentosAtualizado = tratamentosProcedimentosRepository.save(tratamentosProcedimentosExistente);
        log.info("TratamentosProcedimentos atualizado com sucesso. ID: {}", tratamentosProcedimentosAtualizado.getId());

        return tratamentosProcedimentosMapper.toResponse(tratamentosProcedimentosAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "tratamentosprocedimentos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo tratamentosprocedimentos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do tratamentosprocedimentos é obrigatório");
        }

        TratamentosProcedimentos tratamentosProcedimentos = tratamentosProcedimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TratamentosProcedimentos não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(tratamentosProcedimentos.getActive())) {
            throw new BadRequestException("TratamentosProcedimentos já está inativo");
        }

        tratamentosProcedimentos.setActive(false);
        tratamentosProcedimentosRepository.save(tratamentosProcedimentos);
        log.info("TratamentosProcedimentos excluído (desativado) com sucesso. ID: {}", id);
    }

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

        private void atualizarDadosTratamentosProcedimentos(TratamentosProcedimentos tratamentosProcedimentos, TratamentosProcedimentosRequest request) {
        TratamentosProcedimentos tratamentosProcedimentosAtualizado = tratamentosProcedimentosMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = tratamentosProcedimentos.getId();
        com.upsaude.entity.Tenant tenantOriginal = tratamentosProcedimentos.getTenant();
        Boolean activeOriginal = tratamentosProcedimentos.getActive();
        java.time.OffsetDateTime createdAtOriginal = tratamentosProcedimentos.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(tratamentosProcedimentosAtualizado, tratamentosProcedimentos);
        
        // Restaura campos de controle
        tratamentosProcedimentos.setId(idOriginal);
        tratamentosProcedimentos.setTenant(tenantOriginal);
        tratamentosProcedimentos.setActive(activeOriginal);
        tratamentosProcedimentos.setCreatedAt(createdAtOriginal);
    }
}
