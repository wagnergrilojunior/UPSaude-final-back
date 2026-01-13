package com.upsaude.service.api.support.atendimento;

import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.enums.StatusAtendimentoEnum;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Set;

@Slf4j
@Component
public class AtendimentoStatusValidator {

    private static final Set<StatusAtendimentoEnum> STATUS_FINAIS = Set.of(
            StatusAtendimentoEnum.CONCLUIDO,
            StatusAtendimentoEnum.CANCELADO,
            StatusAtendimentoEnum.FALTA_PACIENTE,
            StatusAtendimentoEnum.REMARCADO
    );

    public void validarTransicao(Atendimento atendimento, StatusAtendimentoEnum novoStatus) {
        if (atendimento == null) {
            throw new BadRequestException("Atendimento não pode ser nulo");
        }

        if (atendimento.getInformacoes() == null) {
            return;
        }

        StatusAtendimentoEnum statusAtual = atendimento.getInformacoes().getStatusAtendimento();
        if (statusAtual == null) {
            return;
        }

        if (statusAtual == novoStatus) {
            return;
        }

        validarTransicaoPermitida(statusAtual, novoStatus);
        validarCamposObrigatorios(atendimento, novoStatus);
    }

    private void validarTransicaoPermitida(StatusAtendimentoEnum statusAtual, StatusAtendimentoEnum novoStatus) {
        if (STATUS_FINAIS.contains(statusAtual)) {
            if (novoStatus == StatusAtendimentoEnum.REMARCADO) {
                return;
            }
            throw new BadRequestException(
                    String.format("Não é possível alterar status de %s para %s. Status finais não podem ser alterados diretamente.",
                            statusAtual.getDescricao(), novoStatus.getDescricao())
            );
        }

        switch (statusAtual) {
            case AGENDADO:
                if (!Set.of(StatusAtendimentoEnum.EM_ESPERA, StatusAtendimentoEnum.EM_ANDAMENTO, StatusAtendimentoEnum.CANCELADO, StatusAtendimentoEnum.REMARCADO).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case EM_ESPERA:
                if (!Set.of(StatusAtendimentoEnum.EM_ANDAMENTO, StatusAtendimentoEnum.CANCELADO, StatusAtendimentoEnum.FALTA_PACIENTE).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case EM_ANDAMENTO:
                if (!Set.of(StatusAtendimentoEnum.CONCLUIDO, StatusAtendimentoEnum.CANCELADO, StatusAtendimentoEnum.SUSPENSO, StatusAtendimentoEnum.INTERROMPIDO).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case SUSPENSO:
                if (!Set.of(StatusAtendimentoEnum.EM_ANDAMENTO, StatusAtendimentoEnum.CANCELADO, StatusAtendimentoEnum.REMARCADO).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case INTERROMPIDO:
                if (!Set.of(StatusAtendimentoEnum.EM_ANDAMENTO, StatusAtendimentoEnum.CANCELADO).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
        }
    }

    private void validarCamposObrigatorios(Atendimento atendimento, StatusAtendimentoEnum novoStatus) {
        switch (novoStatus) {
            case CONCLUIDO:
                if (atendimento.getInformacoes().getDataInicio() == null) {
                    throw new BadRequestException("Data/hora de início é obrigatória para concluir atendimento");
                }
                if (atendimento.getInformacoes().getDataFim() == null) {
                    throw new BadRequestException("Data/hora de fim é obrigatória para concluir atendimento");
                }
                if (atendimento.getInformacoes().getDataFim().isBefore(atendimento.getInformacoes().getDataInicio())) {
                    throw new BadRequestException("Data/hora de fim deve ser posterior à data/hora de início");
                }
                break;
            case EM_ANDAMENTO:
                if (atendimento.getInformacoes().getDataInicio() == null) {
                    throw new BadRequestException("Data/hora de início é obrigatória para iniciar atendimento");
                }
                break;
            case CANCELADO:
                if (atendimento.getInformacoes().getMotivo() == null || atendimento.getInformacoes().getMotivo().trim().isEmpty()) {
                    log.warn("Atendimento cancelado sem motivo. ID: {}", atendimento.getId());
                }
                break;
        }
    }
}
