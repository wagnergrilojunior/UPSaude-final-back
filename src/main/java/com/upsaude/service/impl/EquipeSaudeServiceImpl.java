package com.upsaude.service.impl;

import com.upsaude.api.request.EquipeSaudeRequest;
import com.upsaude.api.response.EquipeSaudeResponse;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.VinculoProfissionalEquipe;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EquipeSaudeMapper;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.EquipeSaudeRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.service.EquipeSaudeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Equipes de Saúde.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EquipeSaudeServiceImpl implements EquipeSaudeService {

    private final EquipeSaudeRepository equipeSaudeRepository;
    private final EquipeSaudeMapper equipeSaudeMapper;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;

    @Override
    @Transactional
    @CacheEvict(value = "equipesaude", allEntries = true)
    public EquipeSaudeResponse criar(EquipeSaudeRequest request) {
        log.debug("Criando nova equipe de saúde");

        validarDadosBasicos(request);

        // Valida se já existe equipe com mesmo INE no estabelecimento
        if (equipeSaudeRepository.findByIneAndEstabelecimentoId(request.getIne(), request.getEstabelecimentoId()).isPresent()) {
            throw new BadRequestException("Já existe uma equipe com o INE " + request.getIne() + " neste estabelecimento");
        }

        // Carrega e valida estabelecimento
        Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));

        EquipeSaude equipe = equipeSaudeMapper.fromRequest(request);
        equipe.setEstabelecimento(estabelecimento);
        equipe.setActive(true);

        EquipeSaude equipeSalva = equipeSaudeRepository.save(equipe);

        // Cria vínculos com profissionais se fornecidos
        // Nota: Os vínculos devem ser criados através de endpoint específico ou service dedicado
        // Aqui apenas salvamos a equipe sem vínculos iniciais

        log.info("Equipe de saúde criada com sucesso. ID: {}", equipeSalva.getId());

        return equipeSaudeMapper.toResponse(equipeSalva);
    }

    @Override
    @Transactional
    @Cacheable(value = "equipesaude", key = "#id")
    public EquipeSaudeResponse obterPorId(UUID id) {
        log.debug("Buscando equipe de saúde por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da equipe é obrigatório");
        }

        EquipeSaude equipe = equipeSaudeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipe de saúde não encontrada com ID: " + id));

        return equipeSaudeMapper.toResponse(equipe);
    }

    @Override
    public Page<EquipeSaudeResponse> listar(Pageable pageable) {
        log.debug("Listando equipes de saúde paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<EquipeSaude> equipes = equipeSaudeRepository.findAll(pageable);
        return equipes.map(equipeSaudeMapper::toResponse);
    }

    @Override
    public Page<EquipeSaudeResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando equipes de saúde do estabelecimento: {}", estabelecimentoId);

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<EquipeSaude> equipes = equipeSaudeRepository.findByEstabelecimentoIdOrderByNomeReferenciaAsc(estabelecimentoId, pageable);
        return equipes.map(equipeSaudeMapper::toResponse);
    }

    @Override
    public Page<EquipeSaudeResponse> listarPorStatus(StatusAtivoEnum status, UUID estabelecimentoId, Pageable pageable) {
        log.debug("Listando equipes de saúde por status: {} e estabelecimento: {}", status, estabelecimentoId);

        if (status == null) {
            throw new BadRequestException("Status é obrigatório");
        }

        if (estabelecimentoId == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        Page<EquipeSaude> equipes = equipeSaudeRepository.findByStatusAndEstabelecimentoId(status, estabelecimentoId, pageable);
        return equipes.map(equipeSaudeMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "equipesaude", key = "#id")
    public EquipeSaudeResponse atualizar(UUID id, EquipeSaudeRequest request) {
        log.debug("Atualizando equipe de saúde. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da equipe é obrigatório");
        }

        validarDadosBasicos(request);

        EquipeSaude equipeExistente = equipeSaudeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipe de saúde não encontrada com ID: " + id));

        // Valida se o INE foi alterado e se já existe outro com o mesmo INE no estabelecimento
        if (!equipeExistente.getIne().equals(request.getIne())) {
            if (equipeSaudeRepository.findByIneAndEstabelecimentoId(request.getIne(), request.getEstabelecimentoId()).isPresent()) {
                throw new BadRequestException("Já existe uma equipe com o INE " + request.getIne() + " neste estabelecimento");
            }
        }

        atualizarDadosEquipe(equipeExistente, request);

        EquipeSaude equipeAtualizada = equipeSaudeRepository.save(equipeExistente);
        log.info("Equipe de saúde atualizada com sucesso. ID: {}", equipeAtualizada.getId());

        return equipeSaudeMapper.toResponse(equipeAtualizada);
    }

    @Override
    @Transactional
    @CacheEvict(value = "equipesaude", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo equipe de saúde. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da equipe é obrigatório");
        }

        EquipeSaude equipe = equipeSaudeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipe de saúde não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(equipe.getActive())) {
            throw new BadRequestException("Equipe já está inativa");
        }

        equipe.setActive(false);
        equipeSaudeRepository.save(equipe);
        log.info("Equipe de saúde excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(EquipeSaudeRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da equipe são obrigatórios");
        }
        if (request.getIne() == null || request.getIne().trim().isEmpty()) {
            throw new BadRequestException("INE é obrigatório");
        }
        if (request.getNomeReferencia() == null || request.getNomeReferencia().trim().isEmpty()) {
            throw new BadRequestException("Nome de referência é obrigatório");
        }
        if (request.getTipoEquipe() == null) {
            throw new BadRequestException("Tipo de equipe é obrigatório");
        }
        if (request.getEstabelecimentoId() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
        if (request.getDataAtivacao() == null) {
            throw new BadRequestException("Data de ativação é obrigatória");
        }
        if (request.getStatus() == null) {
            throw new BadRequestException("Status é obrigatório");
        }
    }

    private void atualizarDadosEquipe(EquipeSaude equipe, EquipeSaudeRequest request) {
        equipe.setIne(request.getIne());
        equipe.setNomeReferencia(request.getNomeReferencia());
        equipe.setTipoEquipe(request.getTipoEquipe());

        // Atualiza estabelecimento se foi alterado
        if (!equipe.getEstabelecimento().getId().equals(request.getEstabelecimentoId())) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
            equipe.setEstabelecimento(estabelecimento);
        }

        equipe.setDataAtivacao(request.getDataAtivacao());
        equipe.setDataInativacao(request.getDataInativacao());
        equipe.setStatus(request.getStatus());
        equipe.setObservacoes(request.getObservacoes());

        // Nota: Vínculos com profissionais devem ser gerenciados através de endpoints específicos
        // ou service dedicado para VinculoProfissionalEquipe
        // Não atualizamos vínculos diretamente aqui para manter histórico
    }
}

