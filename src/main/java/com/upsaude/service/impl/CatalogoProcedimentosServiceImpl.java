package com.upsaude.service.impl;

import com.upsaude.api.request.CatalogoProcedimentosRequest;
import com.upsaude.api.response.CatalogoProcedimentosResponse;
import com.upsaude.entity.CatalogoProcedimentos;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.CatalogoProcedimentosMapper;
import com.upsaude.repository.CatalogoProcedimentosRepository;
import com.upsaude.repository.UsuariosSistemaRepository;
import com.upsaude.service.CatalogoProcedimentosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogoProcedimentosServiceImpl implements CatalogoProcedimentosService {

    private final CatalogoProcedimentosRepository catalogoProcedimentosRepository;
    private final CatalogoProcedimentosMapper catalogoProcedimentosMapper;
    private final UsuariosSistemaRepository usuariosSistemaRepository;

    @Override
    @Transactional
    @CacheEvict(value = "catalogoprocedimentos", allEntries = true)
    public CatalogoProcedimentosResponse criar(CatalogoProcedimentosRequest request) {
        log.debug("Criando novo procedimento no catálogo");

        CatalogoProcedimentos procedimento = catalogoProcedimentosMapper.fromRequest(request);
        procedimento.setActive(true);

        Tenant tenant = obterTenantDoUsuarioAutenticado();
        procedimento.setTenant(tenant);

        validarDuplicidade(null, procedimento, request);

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

        CatalogoProcedimentos procedimentoExistente = catalogoProcedimentosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Procedimento não encontrado no catálogo com ID: " + id));

        validarDuplicidade(id, procedimentoExistente, request);

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

    private void validarDuplicidade(UUID id, CatalogoProcedimentos procedimento, CatalogoProcedimentosRequest request) {
        if (request == null || procedimento == null || procedimento.getTenant() == null) {
            return;
        }

        Tenant tenant = procedimento.getTenant();

        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            boolean nomeDuplicado;
            if (id == null) {

                nomeDuplicado = catalogoProcedimentosRepository.existsByNomeAndTenant(request.getNome().trim(), tenant);
            } else {

                nomeDuplicado = catalogoProcedimentosRepository.existsByNomeAndTenantAndIdNot(request.getNome().trim(), tenant, id);
            }

            if (nomeDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar procedimento no catálogo com nome duplicado. Nome: {}, Tenant: {}", request.getNome(), tenant.getId());
                throw new BadRequestException(
                    String.format("Já existe um procedimento cadastrado no catálogo com o nome '%s' no banco de dados", request.getNome())
                );
            }
        }

        if (request.getCodigo() != null && !request.getCodigo().trim().isEmpty()) {
            boolean codigoDuplicado;
            if (id == null) {

                codigoDuplicado = catalogoProcedimentosRepository.existsByCodigoAndTenant(request.getCodigo().trim(), tenant);
            } else {

                codigoDuplicado = catalogoProcedimentosRepository.existsByCodigoAndTenantAndIdNot(request.getCodigo().trim(), tenant, id);
            }

            if (codigoDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar procedimento no catálogo com código duplicado. Código: {}, Tenant: {}", request.getCodigo(), tenant.getId());
                throw new BadRequestException(
                    String.format("Já existe um procedimento cadastrado no catálogo com o código '%s' no banco de dados", request.getCodigo())
                );
            }
        }
    }

    private void atualizarDadosProcedimento(CatalogoProcedimentos procedimento, CatalogoProcedimentosRequest request) {
        CatalogoProcedimentos procedimentoAtualizado = catalogoProcedimentosMapper.fromRequest(request);

        UUID idOriginal = procedimento.getId();
        com.upsaude.entity.Tenant tenantOriginal = procedimento.getTenant();
        com.upsaude.entity.Estabelecimentos estabelecimentoOriginal = procedimento.getEstabelecimento();
        Boolean activeOriginal = procedimento.getActive();
        java.time.OffsetDateTime createdAtOriginal = procedimento.getCreatedAt();

        BeanUtils.copyProperties(procedimentoAtualizado, procedimento);

        procedimento.setId(idOriginal);
        procedimento.setTenant(tenantOriginal);
        procedimento.setEstabelecimento(estabelecimentoOriginal);
        procedimento.setActive(activeOriginal);
        procedimento.setCreatedAt(createdAtOriginal);

    }

    private Tenant obterTenantDoUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Tentativa de criar procedimento sem autenticação");
            throw new BadRequestException("Usuário não autenticado");
        }

        try {
            UUID userId = UUID.fromString(authentication.getName());
            return usuariosSistemaRepository.findByUserId(userId)
                    .map(usuario -> usuario.getTenant())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado ou sem tenant associado"));
        } catch (IllegalArgumentException e) {
            log.error("Erro ao converter userId para UUID: {}", authentication.getName(), e);
            throw new BadRequestException("ID do usuário inválido");
        }
    }
}
