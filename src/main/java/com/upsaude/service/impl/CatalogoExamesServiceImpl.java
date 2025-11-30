package com.upsaude.service.impl;

import com.upsaude.api.request.CatalogoExamesRequest;
import com.upsaude.api.response.CatalogoExamesResponse;
import com.upsaude.entity.CatalogoExames;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.CatalogoExamesMapper;
import com.upsaude.repository.CatalogoExamesRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.service.CatalogoExamesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
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
    private final EstabelecimentosRepository estabelecimentosRepository;

    @Override
    @Transactional
    public CatalogoExamesResponse criar(CatalogoExamesRequest request) {
        log.debug("Criando novo exame no catálogo");

        validarDadosBasicos(request);

        CatalogoExames exame = catalogoExamesMapper.fromRequest(request);
        exame.setActive(true);

        // Carrega e define o estabelecimento se fornecido
        if (request.getEstabelecimentoId() != null) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
            exame.setEstabelecimento(estabelecimento);
        }

        CatalogoExames exameSalvo = catalogoExamesRepository.save(exame);
        log.info("Exame criado no catálogo com sucesso. ID: {}", exameSalvo.getId());

        return catalogoExamesMapper.toResponse(exameSalvo);
    }

    @Override
    @Transactional
    public CatalogoExamesResponse obterPorId(UUID id) {
        log.debug("Buscando exame no catálogo por ID: {}", id);

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
    public CatalogoExamesResponse atualizar(UUID id, CatalogoExamesRequest request) {
        log.debug("Atualizando exame no catálogo. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do exame é obrigatório");
        }

        validarDadosBasicos(request);

        CatalogoExames exameExistente = catalogoExamesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exame não encontrado no catálogo com ID: " + id));

        atualizarDadosExame(exameExistente, request);

        CatalogoExames exameAtualizado = catalogoExamesRepository.save(exameExistente);
        log.info("Exame atualizado no catálogo com sucesso. ID: {}", exameAtualizado.getId());

        return catalogoExamesMapper.toResponse(exameAtualizado);
    }

    @Override
    @Transactional
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

        // Atualiza estabelecimento se fornecido
        if (request.getEstabelecimentoId() != null) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
            exame.setEstabelecimento(estabelecimento);
        }
    }
}

