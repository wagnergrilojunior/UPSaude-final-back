package com.upsaude.service.api.support.agendamento;

import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Set;

@Slf4j
@Component
public class AgendamentoStatusValidator {

    private static final Set<StatusAgendamentoEnum> STATUS_FINAIS = Set.of(
            StatusAgendamentoEnum.CONCLUIDO,
            StatusAgendamentoEnum.CANCELADO,
            StatusAgendamentoEnum.FALTA,
            StatusAgendamentoEnum.REAGENDADO
    );

    public void validarTransicao(Agendamento agendamento, StatusAgendamentoEnum novoStatus) {
        if (agendamento == null) {
            throw new BadRequestException("Agendamento não pode ser nulo");
        }

        StatusAgendamentoEnum statusAtual = agendamento.getStatus();
        if (statusAtual == null) {
            return;
        }

        if (statusAtual == novoStatus) {
            return;
        }

        validarTransicaoPermitida(statusAtual, novoStatus);
        validarCamposObrigatorios(agendamento, novoStatus);
    }

    private void validarTransicaoPermitida(StatusAgendamentoEnum statusAtual, StatusAgendamentoEnum novoStatus) {
        if (STATUS_FINAIS.contains(statusAtual)) {
            if (novoStatus == StatusAgendamentoEnum.REAGENDADO) {
                return;
            }
            throw new BadRequestException(
                    String.format("Não é possível alterar status de %s para %s. Status finais não podem ser alterados diretamente.",
                            statusAtual.getDescricao(), novoStatus.getDescricao())
            );
        }

        switch (statusAtual) {
            case AGENDADO:
                if (!Set.of(StatusAgendamentoEnum.CONFIRMADO, StatusAgendamentoEnum.CANCELADO, StatusAgendamentoEnum.REAGENDADO, StatusAgendamentoEnum.SUSPENSO).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case CONFIRMADO:
                if (!Set.of(StatusAgendamentoEnum.AGUARDANDO, StatusAgendamentoEnum.EM_ATENDIMENTO, StatusAgendamentoEnum.CANCELADO, StatusAgendamentoEnum.REAGENDADO).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case AGUARDANDO:
                if (!Set.of(StatusAgendamentoEnum.EM_ATENDIMENTO, StatusAgendamentoEnum.CANCELADO, StatusAgendamentoEnum.FALTA).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case EM_ATENDIMENTO:
                if (!Set.of(StatusAgendamentoEnum.CONCLUIDO, StatusAgendamentoEnum.CANCELADO, StatusAgendamentoEnum.SUSPENSO).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case SUSPENSO:
                if (!Set.of(StatusAgendamentoEnum.EM_ATENDIMENTO, StatusAgendamentoEnum.CANCELADO, StatusAgendamentoEnum.REAGENDADO).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case ENCAIXE:
                if (!Set.of(StatusAgendamentoEnum.EM_ATENDIMENTO, StatusAgendamentoEnum.CANCELADO).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
        }
    }

    private void validarCamposObrigatorios(Agendamento agendamento, StatusAgendamentoEnum novoStatus) {
        switch (novoStatus) {
            case CONCLUIDO:
                if (agendamento.getDataHora() == null) {
                    throw new BadRequestException("Data/hora de início é obrigatória para concluir agendamento");
                }
                if (agendamento.getDataHoraFim() == null) {
                    throw new BadRequestException("Data/hora de fim é obrigatória para concluir agendamento");
                }
                if (agendamento.getDataHoraFim().isBefore(agendamento.getDataHora())) {
                    throw new BadRequestException("Data/hora de fim deve ser posterior à data/hora de início");
                }
                break;
            case EM_ATENDIMENTO:
                if (agendamento.getDataHora() == null) {
                    throw new BadRequestException("Data/hora de início é obrigatória para iniciar atendimento");
                }
                break;
            case CANCELADO:
                if (agendamento.getMotivoCancelamento() == null || agendamento.getMotivoCancelamento().trim().isEmpty()) {
                    log.warn("Agendamento cancelado sem motivo. ID: {}", agendamento.getId());
                }
                break;
        }
    }
}
