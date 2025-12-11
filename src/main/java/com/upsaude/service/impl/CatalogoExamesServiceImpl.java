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

        CatalogoExames exame = catalogoExamesMapper.fromRequest(request);
        exame.setActive(true);

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

        CatalogoExames exameExistente = catalogoExamesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exame não encontrado no catálogo com ID: " + id));

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

    private void validarDuplicidade(UUID id, CatalogoExames exame, CatalogoExamesRequest request) {
        if (request == null || exame == null || exame.getTenant() == null) {
            return;
        }

        Tenant tenant = exame.getTenant();

        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            boolean nomeDuplicado;
            if (id == null) {

                nomeDuplicado = catalogoExamesRepository.existsByNomeAndTenant(request.getNome().trim(), tenant);
            } else {

                nomeDuplicado = catalogoExamesRepository.existsByNomeAndTenantAndIdNot(request.getNome().trim(), tenant, id);
            }

            if (nomeDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar exame no catálogo com nome duplicado. Nome: {}, Tenant: {}", request.getNome(), tenant.getId());
                throw new BadRequestException(
                    String.format("Já existe um exame cadastrado no catálogo com o nome '%s' no banco de dados", request.getNome())
                );
            }
        }

        if (request.getCodigo() != null && !request.getCodigo().trim().isEmpty()) {
            boolean codigoDuplicado;
            if (id == null) {

                codigoDuplicado = catalogoExamesRepository.existsByCodigoAndTenant(request.getCodigo().trim(), tenant);
            } else {

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

        UUID idOriginal = exame.getId();
        com.upsaude.entity.Tenant tenantOriginal = exame.getTenant();
        com.upsaude.entity.Estabelecimentos estabelecimentoOriginal = exame.getEstabelecimento();
        Boolean activeOriginal = exame.getActive();
        java.time.OffsetDateTime createdAtOriginal = exame.getCreatedAt();

        BeanUtils.copyProperties(exameAtualizado, exame);

        exame.setId(idOriginal);
        exame.setTenant(tenantOriginal);
        exame.setEstabelecimento(estabelecimentoOriginal);
        exame.setActive(activeOriginal);
        exame.setCreatedAt(createdAtOriginal);

    }
}
