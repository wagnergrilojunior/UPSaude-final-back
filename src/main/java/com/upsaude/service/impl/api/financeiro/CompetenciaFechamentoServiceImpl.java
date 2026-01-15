package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFechamentoRequest;
import com.upsaude.api.response.financeiro.CompetenciaFechamentoResponse;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.faturamento.DocumentoFaturamentoRepository;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import com.upsaude.repository.financeiro.ReservaOrcamentariaAssistencialRepository;
import com.upsaude.service.api.financeiro.BpaGenerationService;
import com.upsaude.service.api.financeiro.CompetenciaFechamentoService;
import com.upsaude.util.bpa.BpaHashCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
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
    private final ReservaOrcamentariaAssistencialRepository reservaRepository;
    private final DocumentoFaturamentoRepository documentoFaturamentoRepository;
    private final BpaGenerationService bpaGenerationService;
    private final AuditorAware<UsuariosSistema> auditorAware;

    @Override
    @Transactional
    @SuppressWarnings("null")
    public CompetenciaFechamentoResponse fecharCompetencia(
            UUID competenciaId,
            UUID tenantId,
            CompetenciaFechamentoRequest request) {

        UsuariosSistema usuarioSistema = auditorAware.getCurrentAuditor().orElse(null);
        log.info("Iniciando fechamento de competência {} para tenant {} pelo usuário {}", competenciaId, tenantId,
                usuarioSistema != null ? usuarioSistema.getId() : null);

        CompetenciaFinanceira competencia = competenciaFinanceiraRepository
                .findByIdAndTenant(competenciaId, tenantId)
                .orElseThrow(() -> new NotFoundException("Competência financeira não encontrada para o tenant"));

        if (competencia.isFechada()) {
            throw new BadRequestException("Competência já está fechada");
        }

        validarPreRequisitos(competenciaId, tenantId);

        String conteudoBpa = bpaGenerationService.gerarBpa(competenciaId, tenantId);
        if (conteudoBpa == null || conteudoBpa.isEmpty()) {
            log.warn("Nenhum dado encontrado para gerar BPA. Competência: {}, Tenant: {}", competenciaId, tenantId);
            conteudoBpa = "";
        }

        String hashBpa = BpaHashCalculator.calcularHash(conteudoBpa);
        String hashMovimentacoes = calcularHashMovimentacoes(competenciaId, tenantId);
        String snapshotHash = BpaHashCalculator.calcularHashCombinado(hashMovimentacoes, hashBpa);
        boolean validacaoIntegridade = BpaHashCalculator.validarHash(hashMovimentacoes, hashBpa);

        DocumentoFaturamento documentoBpa = new DocumentoFaturamento();
        documentoBpa.setCompetencia(competencia);
        documentoBpa.setTipo("BPA");
        documentoBpa.setNumero("BPA-" + competencia.getCodigo() + "-" + System.currentTimeMillis());
        documentoBpa.setSerie("001");
        documentoBpa.setStatus("FECHADO");
        documentoBpa.setEmitidoEm(OffsetDateTime.now());
documentoBpa.setPayloadLayout(conteudoBpa);

        documentoBpa = documentoFaturamentoRepository.save(documentoBpa);

        competencia.setStatus("FECHADA");
        competencia.setFechadaEm(OffsetDateTime.now());
        competencia.setFechadaPor(usuarioSistema);
        competencia.setMotivoFechamento(request != null ? request.getMotivo() : null);
        competencia.setDocumentoBpaFechamento(documentoBpa);
        competencia.setHashMovimentacoes(hashMovimentacoes);
        competencia.setHashBpa(hashBpa);
        competencia.setSnapshotHash(snapshotHash);
        competencia.setValidacaoIntegridade(validacaoIntegridade);

        competencia = competenciaFinanceiraRepository.save(competencia);

        log.info("Competência fechada com sucesso. Documento BPA: {}", documentoBpa.getId());

        return CompetenciaFechamentoResponse.builder()
                .competenciaId(competenciaId)
                .tenantId(tenantId)
                .status(competencia.getStatus())
                .fechadaEm(competencia.getFechadaEm())
                .fechadaPor(competencia.getFechadaPor() != null ? competencia.getFechadaPor().getId() : null)
                .motivoFechamento(competencia.getMotivoFechamento())
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
        List<ReservaOrcamentariaAssistencial> reservasAtivas = reservaRepository
                .findByCompetenciaAndStatus(competenciaId, "ATIVA", tenantId, PageRequest.of(0, 1))
                .getContent();

        if (!reservasAtivas.isEmpty()) {
            log.warn("Existem {} reservas ATIVAS para esta competência. Fechamento prosseguirá mesmo assim.", reservasAtivas.size());
        }
    }

    private String calcularHashMovimentacoes(UUID competenciaId, UUID tenantId) {
        List<ReservaOrcamentariaAssistencial> reservas = reservaRepository
                .findByCompetencia(competenciaId, tenantId, PageRequest.of(0, 10000))
                .getContent();

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
