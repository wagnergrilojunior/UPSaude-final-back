package com.upsaude.service.impl;

import com.upsaude.api.request.EducacaoSaudeRequest;
import com.upsaude.api.response.EducacaoSaudeResponse;
import com.upsaude.entity.EducacaoSaude;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EducacaoSaudeMapper;
import com.upsaude.repository.EducacaoSaudeRepository;
import com.upsaude.service.EducacaoSaudeService;
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
public class EducacaoSaudeServiceImpl implements EducacaoSaudeService {

    private final EducacaoSaudeRepository educacaoSaudeRepository;
    private final EducacaoSaudeMapper educacaoSaudeMapper;

    @Override
    @Transactional
    @CacheEvict(value = "educacaosaude", allEntries = true)
    public EducacaoSaudeResponse criar(EducacaoSaudeRequest request) {
        log.debug("Criando nova ação de educação em saúde");

        EducacaoSaude educacaoSaude = educacaoSaudeMapper.fromRequest(request);
        educacaoSaude.setActive(true);

        if (educacaoSaude.getAtividadeRealizada() == null) {
            educacaoSaude.setAtividadeRealizada(false);
        }

        EducacaoSaude educacaoSalva = educacaoSaudeRepository.save(educacaoSaude);
        log.info("Educação em saúde criada com sucesso. ID: {}", educacaoSalva.getId());

        return educacaoSaudeMapper.toResponse(educacaoSalva);
    }

    @Override
    @Transactional
    @Cacheable(value = "educacaosaude", key = "#id")
    public EducacaoSaudeResponse obterPorId(UUID id) {
        log.debug("Buscando educação em saúde por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da educação em saúde é obrigatório");
        }

        EducacaoSaude educacaoSaude = educacaoSaudeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Educação em saúde não encontrada com ID: " + id));

        return educacaoSaudeMapper.toResponse(educacaoSaude);
    }

    @Override
    public Page<EducacaoSaudeResponse> listar(Pageable pageable) {
        log.debug("Listando educações em saúde paginadas");

        Page<EducacaoSaude> educacoes = educacaoSaudeRepository.findAll(pageable);
        return educacoes.map(educacaoSaudeMapper::toResponse);
    }

    @Override
    public Page<EducacaoSaudeResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando educações em saúde por estabelecimento: {}", estabelecimentoId);

        Page<EducacaoSaude> educacoes = educacaoSaudeRepository.findByEstabelecimentoIdOrderByDataHoraInicioDesc(estabelecimentoId, pageable);
        return educacoes.map(educacaoSaudeMapper::toResponse);
    }

    @Override
    public Page<EducacaoSaudeResponse> listarPorProfissionalResponsavel(UUID profissionalId, Pageable pageable) {
        log.debug("Listando educações em saúde por profissional: {}", profissionalId);

        Page<EducacaoSaude> educacoes = educacaoSaudeRepository.findByProfissionalResponsavelIdOrderByDataHoraInicioDesc(profissionalId, pageable);
        return educacoes.map(educacaoSaudeMapper::toResponse);
    }

    @Override
    public Page<EducacaoSaudeResponse> listarRealizadas(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando educações em saúde realizadas: {}", estabelecimentoId);

        Page<EducacaoSaude> educacoes = educacaoSaudeRepository.findByAtividadeRealizadaAndEstabelecimentoIdOrderByDataHoraInicioDesc(true, estabelecimentoId, pageable);
        return educacoes.map(educacaoSaudeMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "educacaosaude", key = "#id")
    public EducacaoSaudeResponse atualizar(UUID id, EducacaoSaudeRequest request) {
        log.debug("Atualizando educação em saúde. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da educação em saúde é obrigatório");
        }

        EducacaoSaude educacaoExistente = educacaoSaudeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Educação em saúde não encontrada com ID: " + id));

        atualizarDadosEducacaoSaude(educacaoExistente, request);

        EducacaoSaude educacaoAtualizada = educacaoSaudeRepository.save(educacaoExistente);
        log.info("Educação em saúde atualizada com sucesso. ID: {}", educacaoAtualizada.getId());

        return educacaoSaudeMapper.toResponse(educacaoAtualizada);
    }

    @Override
    @Transactional
    @CacheEvict(value = "educacaosaude", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo educação em saúde. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da educação em saúde é obrigatório");
        }

        EducacaoSaude educacaoSaude = educacaoSaudeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Educação em saúde não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(educacaoSaude.getActive())) {
            throw new BadRequestException("Educação em saúde já está inativa");
        }

        educacaoSaude.setActive(false);
        educacaoSaudeRepository.save(educacaoSaude);
        log.info("Educação em saúde excluída (desativada) com sucesso. ID: {}", id);
    }

    private void atualizarDadosEducacaoSaude(EducacaoSaude educacao, EducacaoSaudeRequest request) {
        EducacaoSaude educacaoAtualizada = educacaoSaudeMapper.fromRequest(request);

        UUID idOriginal = educacao.getId();
        com.upsaude.entity.Tenant tenantOriginal = educacao.getTenant();
        Boolean activeOriginal = educacao.getActive();
        java.time.OffsetDateTime createdAtOriginal = educacao.getCreatedAt();

        BeanUtils.copyProperties(educacaoAtualizada, educacao);

        educacao.setId(idOriginal);
        educacao.setTenant(tenantOriginal);
        educacao.setActive(activeOriginal);
        educacao.setCreatedAt(createdAtOriginal);
    }
}
