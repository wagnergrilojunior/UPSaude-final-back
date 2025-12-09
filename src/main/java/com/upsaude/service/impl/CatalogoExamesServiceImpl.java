package com.upsaude.service.impl;

import com.upsaude.api.request.CatalogoExamesRequest;
import com.upsaude.api.response.CatalogoExamesResponse;
import com.upsaude.entity.CatalogoExames;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.CatalogoExamesMapper;
import com.upsaude.repository.CatalogoExamesRepository;
import com.upsaude.service.CatalogoExamesService;
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
 * Implementação do serviço de gerenciamento de CatalogoExames.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogoExamesServiceImpl implements CatalogoExamesService {

    private final CatalogoExamesRepository catalogoExamesRepository;
    private final CatalogoExamesMapper catalogoExamesMapper;

    @Override
    @Transactional
    @CacheEvict(value = "catalogoexames", allEntries = true)
    public CatalogoExamesResponse criar(CatalogoExamesRequest request) {
        log.debug("Criando novo exame no catálogo");

        validarDadosBasicos(request);

        CatalogoExames exame = catalogoExamesMapper.fromRequest(request);
        exame.setActive(true);

        // Valida duplicidade antes de salvar (após o tenant ser setado)
        validarDuplicidade(null, exame, request);

        CatalogoExames exameSalvo = catalogoExamesRepository.save(exame);
        log.info("Exame criado no catálogo com sucesso. ID: {}", exameSalvo.getId());

        return catalogoExamesMapper.toResponse(exameSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "catalogoexames", key = "#id")
    public CatalogoExamesResponse obterPorId(UUID id) {
        log.debug("Buscando exame no catálogo por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do exame é obrigatório");
        }

        CatalogoExames exame = catalogoExamesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exame não encontrado no catálogo com ID: " + id));

        return catalogoExamesMapper.toResponse(exame);
    }

    @Override
    public Page<CatalogoExamesResponse> listar(Pageable pageable) {
        log.debug("Listando exames do catálogo paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<CatalogoExames> exames = catalogoExamesRepository.findAll(pageable);
        return exames.map(catalogoExamesMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "catalogoexames", key = "#id")
    public CatalogoExamesResponse atualizar(UUID id, CatalogoExamesRequest request) {
        log.debug("Atualizando exame no catálogo. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do exame é obrigatório");
        }

        validarDadosBasicos(request);

        CatalogoExames exameExistente = catalogoExamesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exame não encontrado no catálogo com ID: " + id));

        // Valida duplicidade antes de atualizar
        validarDuplicidade(id, exameExistente, request);

        atualizarDadosExame(exameExistente, request);

        CatalogoExames exameAtualizado = catalogoExamesRepository.save(exameExistente);
        log.info("Exame atualizado no catálogo com sucesso. ID: {}", exameAtualizado.getId());

        return catalogoExamesMapper.toResponse(exameAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "catalogoexames", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo exame do catálogo. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do exame é obrigatório");
        }

        CatalogoExames exame = catalogoExamesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exame não encontrado no catálogo com ID: " + id));

        if (Boolean.FALSE.equals(exame.getActive())) {
            throw new BadRequestException("Exame já está inativo");
        }

        exame.setActive(false);
        catalogoExamesRepository.save(exame);
        log.info("Exame excluído (desativado) do catálogo com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(CatalogoExamesRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do exame são obrigatórios");
        }
    }

    /**
     * Valida se já existe um exame no catálogo com o mesmo nome ou código no mesmo tenant.
     * 
     * @param id ID do exame sendo atualizado (null para criação)
     * @param exame objeto CatalogoExames (usado para obter o tenant)
     * @param request dados do exame sendo cadastrado/atualizado
     * @throws BadRequestException se já existe um exame com o mesmo nome ou código no tenant
     */
    private void validarDuplicidade(UUID id, CatalogoExames exame, CatalogoExamesRequest request) {
        if (request == null || exame == null || exame.getTenant() == null) {
            return;
        }

        Tenant tenant = exame.getTenant();

        // Valida duplicidade do nome
        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            boolean nomeDuplicado;
            if (id == null) {
                // Criação: verifica se existe qualquer registro com este nome no tenant
                nomeDuplicado = catalogoExamesRepository.existsByNomeAndTenant(request.getNome().trim(), tenant);
            } else {
                // Atualização: verifica se existe outro registro (diferente do atual) com este nome no tenant
                nomeDuplicado = catalogoExamesRepository.existsByNomeAndTenantAndIdNot(request.getNome().trim(), tenant, id);
            }

            if (nomeDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar exame no catálogo com nome duplicado. Nome: {}, Tenant: {}", request.getNome(), tenant.getId());
                throw new BadRequestException(
                    String.format("Já existe um exame cadastrado no catálogo com o nome '%s' no banco de dados", request.getNome())
                );
            }
        }

        // Valida duplicidade do código (apenas se fornecido)
        if (request.getCodigo() != null && !request.getCodigo().trim().isEmpty()) {
            boolean codigoDuplicado;
            if (id == null) {
                // Criação: verifica se existe qualquer registro com este código no tenant
                codigoDuplicado = catalogoExamesRepository.existsByCodigoAndTenant(request.getCodigo().trim(), tenant);
            } else {
                // Atualização: verifica se existe outro registro (diferente do atual) com este código no tenant
                codigoDuplicado = catalogoExamesRepository.existsByCodigoAndTenantAndIdNot(request.getCodigo().trim(), tenant, id);
            }

            if (codigoDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar exame no catálogo com código duplicado. Código: {}, Tenant: {}", request.getCodigo(), tenant.getId());
                throw new BadRequestException(
                    String.format("Já existe um exame cadastrado no catálogo com o código '%s' no banco de dados", request.getCodigo())
                );
            }
        }
    }

    private void atualizarDadosExame(CatalogoExames exame, CatalogoExamesRequest request) {
        CatalogoExames exameAtualizado = catalogoExamesMapper.fromRequest(request);
        
        // Preserva campos de controle
        UUID idOriginal = exame.getId();
        com.upsaude.entity.Tenant tenantOriginal = exame.getTenant();
        com.upsaude.entity.Estabelecimentos estabelecimentoOriginal = exame.getEstabelecimento();
        Boolean activeOriginal = exame.getActive();
        java.time.OffsetDateTime createdAtOriginal = exame.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(exameAtualizado, exame);
        
        // Restaura campos de controle
        exame.setId(idOriginal);
        exame.setTenant(tenantOriginal);
        exame.setEstabelecimento(estabelecimentoOriginal);
        exame.setActive(activeOriginal);
        exame.setCreatedAt(createdAtOriginal);
        // Estabelecimento não faz parte do Request
    }
}

