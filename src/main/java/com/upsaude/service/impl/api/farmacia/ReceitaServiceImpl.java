package com.upsaude.service.impl.api.farmacia;

import com.upsaude.api.request.farmacia.ReceitaRequest;
import com.upsaude.api.response.farmacia.ReceitaResponse;
import com.upsaude.entity.farmacia.Receita;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.farmacia.ReceitaRepository;
import com.upsaude.service.api.farmacia.ReceitaService;
import com.upsaude.service.api.support.farmacia.ReceitaCreator;
import com.upsaude.service.api.support.farmacia.ReceitaResponseBuilder;
import com.upsaude.service.api.support.farmacia.ReceitaTenantEnforcer;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceitaServiceImpl implements ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final TenantService tenantService;
    private final ReceitaCreator receitaCreator;
    private final ReceitaResponseBuilder responseBuilder;
    private final ReceitaTenantEnforcer tenantEnforcer;
    private final com.upsaude.repository.clinica.prontuario.AlergiaPacienteRepository alergiaRepository;
    private final com.upsaude.repository.farmacia.MedicamentoRepository medicamentoRepository;

    @Override
    @Transactional
    public ReceitaResponse criar(ReceitaRequest request) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            validarContraindicacoes(request.getPacienteId(), request.getItens(), tenantId);
            Receita receita = receitaCreator.criar(request, tenantId);
            Receita receitaSalva = receitaRepository.save(receita);
            return responseBuilder.build(receitaSalva);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar Receita. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar Receita. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir Receita", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ReceitaResponse obterPorId(UUID id) {
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Receita receita = receitaRepository.findByIdCompleto(id)
                    .orElseThrow(() -> {
                        log.warn("Receita não encontrada com ID: {}", id);
                        return new NotFoundException("Receita não encontrada com ID: " + id);
                    });
            tenantEnforcer.validarAcesso(id, tenantId);
            return responseBuilder.build(receita);
        } catch (NotFoundException e) {
            log.warn("Erro ao buscar Receita. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar Receita. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao buscar Receita", e);
        }
    }

    @Override
    public Page<ReceitaResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        try {
            Page<Receita> receitas = receitaRepository.findByPacienteId(pacienteId, pageable);
            return receitas.map(responseBuilder::build);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar Receitas. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao listar Receitas", e);
        }
    }

    private void validarContraindicacoes(UUID pacienteId,
            java.util.List<com.upsaude.api.request.farmacia.ReceitaItemRequest> itens,
            UUID tenantId) {
        if (itens == null || itens.isEmpty()) {
            return;
        }

        java.util.List<com.upsaude.entity.clinica.prontuario.AlergiaPaciente> alergiasAtivas = alergiaRepository
                .findActiveByPacienteId(pacienteId, tenantId);

        if (alergiasAtivas.isEmpty()) {
            return;
        }

        for (com.upsaude.api.request.farmacia.ReceitaItemRequest item : itens) {
            if (item.getMedicamentoId() != null) {
                com.upsaude.entity.farmacia.Medicamento medicamento = medicamentoRepository
                        .findById(item.getMedicamentoId())
                        .orElseThrow(
                                () -> new NotFoundException("Medicamento não encontrado: " + item.getMedicamentoId()));

                for (com.upsaude.entity.clinica.prontuario.AlergiaPaciente alergia : alergiasAtivas) {
                    // Validação 1: Se o alérgeno é o próprio medicamento
                    if (alergia.getAlergeno() != null && alergia.getAlergeno().getNome() != null &&
                            medicamento.getNome().toLowerCase()
                                    .contains(alergia.getAlergeno().getNome().toLowerCase())) {
                        throw new BadRequestException("CONTRAINDICAÇÃO: Paciente possui alergia ativa a: "
                                + alergia.getAlergeno().getNome());
                    }

                    // Validação 2: Descrição da alergia (caso não tenha alergéno estruturado)
                    if (alergia.getDescricao() != null &&
                            medicamento.getNome().toLowerCase().contains(alergia.getDescricao().toLowerCase())) {
                        throw new BadRequestException("CONTRAINDICAÇÃO: Paciente possui histórico de alergia a: "
                                + alergia.getDescricao());
                    }
                }
            }
        }
    }
}
