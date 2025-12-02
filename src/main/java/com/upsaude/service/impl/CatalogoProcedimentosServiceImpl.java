package com.upsaude.service.impl;

import com.upsaude.api.request.CatalogoProcedimentosRequest;
import com.upsaude.api.response.CatalogoProcedimentosResponse;
import com.upsaude.entity.CatalogoProcedimentos;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.CatalogoProcedimentosMapper;
import com.upsaude.repository.CatalogoProcedimentosRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.service.CatalogoProcedimentosService;
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
 * Implementação do serviço de gerenciamento de CatalogoProcedimentos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogoProcedimentosServiceImpl implements CatalogoProcedimentosService {

    private final CatalogoProcedimentosRepository catalogoProcedimentosRepository;
    private final CatalogoProcedimentosMapper catalogoProcedimentosMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;

    @Override
    @Transactional
    @CacheEvict(value = "catalogoprocedimentos", allEntries = true)
    public CatalogoProcedimentosResponse criar(CatalogoProcedimentosRequest request) {
        log.debug("Criando novo procedimento no catálogo");

        validarDadosBasicos(request);

        CatalogoProcedimentos procedimento = catalogoProcedimentosMapper.fromRequest(request);
        procedimento.setActive(true);

        // Carrega e define o estabelecimento se fornecido
        if (request.getEstabelecimentoId() != null) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
            procedimento.setEstabelecimento(estabelecimento);
        }

        CatalogoProcedimentos procedimentoSalvo = catalogoProcedimentosRepository.save(procedimento);
        log.info("Procedimento criado no catálogo com sucesso. ID: {}", procedimentoSalvo.getId());

        return catalogoProcedimentosMapper.toResponse(procedimentoSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "catalogoprocedimentos", key = "#id")
    public CatalogoProcedimentosResponse obterPorId(UUID id) {
        log.debug("Buscando procedimento no catálogo por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do procedimento é obrigatório");
        }

        CatalogoProcedimentos procedimento = catalogoProcedimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Procedimento não encontrado no catálogo com ID: " + id));

        return catalogoProcedimentosMapper.toResponse(procedimento);
    }

    @Override
    public Page<CatalogoProcedimentosResponse> listar(Pageable pageable) {
        log.debug("Listando procedimentos do catálogo paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<CatalogoProcedimentos> procedimentos = catalogoProcedimentosRepository.findAll(pageable);
        return procedimentos.map(catalogoProcedimentosMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "catalogoprocedimentos", key = "#id")
    public CatalogoProcedimentosResponse atualizar(UUID id, CatalogoProcedimentosRequest request) {
        log.debug("Atualizando procedimento no catálogo. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do procedimento é obrigatório");
        }

        validarDadosBasicos(request);

        CatalogoProcedimentos procedimentoExistente = catalogoProcedimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Procedimento não encontrado no catálogo com ID: " + id));

        atualizarDadosProcedimento(procedimentoExistente, request);

        CatalogoProcedimentos procedimentoAtualizado = catalogoProcedimentosRepository.save(procedimentoExistente);
        log.info("Procedimento atualizado no catálogo com sucesso. ID: {}", procedimentoAtualizado.getId());

        return catalogoProcedimentosMapper.toResponse(procedimentoAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "catalogoprocedimentos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo procedimento do catálogo. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do procedimento é obrigatório");
        }

        CatalogoProcedimentos procedimento = catalogoProcedimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Procedimento não encontrado no catálogo com ID: " + id));

        if (Boolean.FALSE.equals(procedimento.getActive())) {
            throw new BadRequestException("Procedimento já está inativo");
        }

        procedimento.setActive(false);
        catalogoProcedimentosRepository.save(procedimento);
        log.info("Procedimento excluído (desativado) do catálogo com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(CatalogoProcedimentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do procedimento são obrigatórios");
        }
    }

    private void atualizarDadosProcedimento(CatalogoProcedimentos procedimento, CatalogoProcedimentosRequest request) {
        CatalogoProcedimentos procedimentoAtualizado = catalogoProcedimentosMapper.fromRequest(request);
        
        // Preserva campos de controle
        UUID idOriginal = procedimento.getId();
        com.upsaude.entity.Tenant tenantOriginal = procedimento.getTenant();
        com.upsaude.entity.Estabelecimentos estabelecimentoOriginal = procedimento.getEstabelecimento();
        Boolean activeOriginal = procedimento.getActive();
        java.time.OffsetDateTime createdAtOriginal = procedimento.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(procedimentoAtualizado, procedimento);
        
        // Restaura campos de controle
        procedimento.setId(idOriginal);
        procedimento.setTenant(tenantOriginal);
        procedimento.setEstabelecimento(estabelecimentoOriginal);
        procedimento.setActive(activeOriginal);
        procedimento.setCreatedAt(createdAtOriginal);

        // Atualiza estabelecimento se fornecido
        if (request.getEstabelecimentoId() != null) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
            procedimento.setEstabelecimento(estabelecimento);
        }
    }
}

