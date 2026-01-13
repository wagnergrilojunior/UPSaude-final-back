package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFechamentoRequest;
import com.upsaude.api.response.financeiro.CompetenciaFechamentoResponse;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.entity.financeiro.CompetenciaFinanceiraTenant;
import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.faturamento.DocumentoFaturamentoRepository;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraTenantRepository;
import com.upsaude.repository.financeiro.ReservaOrcamentariaAssistencialRepository;
import com.upsaude.service.api.financeiro.BpaGenerationService;
import com.upsaude.service.api.financeiro.CompetenciaFechamentoService;
import com.upsaude.util.bpa.BpaHashCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetenciaFechamentoServiceImpl implements CompetenciaFechamentoService {

    private final CompetenciaFinanceiraRepository competenciaFinanceiraRepository;
    private final CompetenciaFinanceiraTenantRepository competenciaTenantRepository;
    private final ReservaOrcamentariaAssistencialRepository reservaRepository;
    private final DocumentoFaturamentoRepository documentoFaturamentoRepository;
    private final BpaGenerationService bpaGenerationService;

    @Override
    @Transactional
    @SuppressWarnings("null")
    public CompetenciaFechamentoResponse fecharCompetencia(
            UUID competenciaId, 
            UUID tenantId, 
            UUID usuarioId, 
            CompetenciaFechamentoRequest request) {
        
        log.info("Iniciando fechamento de competência {} para tenant {} pelo usuário {}", competenciaId, tenantId, usuarioId);
        
        // 1. Validar pré-requisitos
        validarPreRequisitos(competenciaId, tenantId);
        
        // 2. Buscar ou criar CompetenciaFinanceiraTenant
        CompetenciaFinanceiraTenant competenciaTenant = competenciaTenantRepository
                .findByTenantAndCompetencia(tenantId, competenciaId)
                .orElseGet(() -> {
                    CompetenciaFinanceira competencia = competenciaFinanceiraRepository.findById(competenciaId)
                            .orElseThrow(() -> new NotFoundException("Competência não encontrada"));
                    CompetenciaFinanceiraTenant nova = new CompetenciaFinanceiraTenant();
                    nova.setCompetencia(competencia);
                    nova.setStatus("ABERTA");
                    return competenciaTenantRepository.save(nova);
                });
        
        if ("FECHADA".equals(competenciaTenant.getStatus())) {
            throw new BadRequestException("Competência já está fechada");
        }
        
        // 3. Gerar arquivo BPA
        String conteudoBpa = bpaGenerationService.gerarBpa(competenciaId, tenantId);
        if (conteudoBpa == null || conteudoBpa.isEmpty()) {
            log.warn("Nenhum dado encontrado para gerar BPA. Competência: {}, Tenant: {}", competenciaId, tenantId);
            conteudoBpa = ""; // Permitir fechamento mesmo sem dados
        }
        
        // 4. Calcular hashes
        String hashBpa = BpaHashCalculator.calcularHash(conteudoBpa);
        String hashMovimentacoes = calcularHashMovimentacoes(competenciaId, tenantId);
        String snapshotHash = BpaHashCalculator.calcularHashCombinado(hashMovimentacoes, hashBpa);
        boolean validacaoIntegridade = BpaHashCalculator.validarHash(hashMovimentacoes, hashBpa);
        
        // 5. Criar DocumentoFaturamento BPA
        CompetenciaFinanceira competencia = competenciaFinanceiraRepository.findById(competenciaId)
                .orElseThrow(() -> new NotFoundException("Competência não encontrada"));
        
        DocumentoFaturamento documentoBpa = new DocumentoFaturamento();
        documentoBpa.setCompetencia(competencia);
        documentoBpa.setTipo("BPA");
        documentoBpa.setNumero("BPA-" + competencia.getCodigo() + "-" + System.currentTimeMillis());
        documentoBpa.setSerie("001");
        documentoBpa.setStatus("FECHADO");
        documentoBpa.setEmitidoEm(OffsetDateTime.now());
        documentoBpa.setPayloadLayout(conteudoBpa); // Armazenar conteúdo do BPA
        
        documentoBpa = documentoFaturamentoRepository.save(documentoBpa);
        
        // 6. Atualizar CompetenciaFinanceiraTenant
        competenciaTenant.setStatus("FECHADA");
        competenciaTenant.setFechadaEm(OffsetDateTime.now());
        competenciaTenant.setFechadaPor(usuarioId);
        competenciaTenant.setMotivoFechamento(request != null ? request.getMotivo() : null);
        competenciaTenant.setDocumentoBpaFechamento(documentoBpa);
        competenciaTenant.setHashMovimentacoes(hashMovimentacoes);
        competenciaTenant.setHashBpa(hashBpa);
        competenciaTenant.setSnapshotHash(snapshotHash);
        competenciaTenant.setValidacaoIntegridade(validacaoIntegridade);
        
        competenciaTenant = competenciaTenantRepository.save(competenciaTenant);
        
        log.info("Competência fechada com sucesso. Documento BPA: {}", documentoBpa.getId());
        
        return CompetenciaFechamentoResponse.builder()
                .competenciaId(competenciaId)
                .tenantId(tenantId)
                .status(competenciaTenant.getStatus())
                .fechadaEm(competenciaTenant.getFechadaEm())
                .fechadaPor(competenciaTenant.getFechadaPor())
                .motivoFechamento(competenciaTenant.getMotivoFechamento())
                .documentoBpaId(documentoBpa.getId())
                .hashMovimentacoes(hashMovimentacoes)
                .hashBpa(hashBpa)
                .snapshotHash(snapshotHash)
                .validacaoIntegridade(validacaoIntegridade)
                .mensagem("Competência fechada com sucesso")
                .build();
    }

    @SuppressWarnings("null")
    private void validarPreRequisitos(UUID competenciaId, UUID tenantId) {
        // Verificar se competência existe
        competenciaFinanceiraRepository.findById(competenciaId)
                .orElseThrow(() -> new NotFoundException("Competência financeira não encontrada"));
        
        // Verificar se já está fechada
        competenciaTenantRepository.findByTenantAndCompetencia(tenantId, competenciaId)
                .ifPresent(ct -> {
                    if ("FECHADA".equals(ct.getStatus())) {
                        throw new BadRequestException("Competência já está fechada");
                    }
                });
        
        // Verificar se há reservas ATIVAS pendentes (opcional - pode ser política de negócio)
        List<ReservaOrcamentariaAssistencial> reservasAtivas = reservaRepository
                .findByCompetenciaAndStatus(competenciaId, "ATIVA", tenantId, PageRequest.of(0, 1))
                .getContent();
        
        if (!reservasAtivas.isEmpty()) {
            log.warn("Existem {} reservas ATIVAS para esta competência. Fechamento prosseguirá mesmo assim.", reservasAtivas.size());
            // Não bloquear, apenas logar warning
        }
    }

    private String calcularHashMovimentacoes(UUID competenciaId, UUID tenantId) {
        // Consolidar todas as movimentações financeiras (reservas, consumos, estornos)
        List<ReservaOrcamentariaAssistencial> reservas = reservaRepository
                .findByCompetencia(competenciaId, tenantId, PageRequest.of(0, 10000))
                .getContent();
        
        // Criar string consolidada para hash
        StringBuilder movimentacoes = new StringBuilder();
        for (ReservaOrcamentariaAssistencial reserva : reservas) {
            movimentacoes.append(reserva.getId())
                    .append("|")
                    .append(reserva.getStatus())
                    .append("|")
                    .append(reserva.getValorReservadoTotal())
                    .append("|");
        }
        
        return BpaHashCalculator.calcularHash(movimentacoes.toString());
    }
}
