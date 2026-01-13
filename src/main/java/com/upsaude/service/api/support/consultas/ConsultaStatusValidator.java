package com.upsaude.service.api.support.consultas;

import com.upsaude.entity.clinica.atendimento.Consulta;
import com.upsaude.enums.StatusConsultaEnum;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class ConsultaStatusValidator {

    private static final Set<StatusConsultaEnum> STATUS_FINAIS = Set.of(
            StatusConsultaEnum.CONCLUIDA,
            StatusConsultaEnum.CANCELADA,
            StatusConsultaEnum.FALTA_PACIENTE,
            StatusConsultaEnum.NAO_COMPARECEU,
            StatusConsultaEnum.REMARCADA
    );

    public void validarTransicao(Consulta consulta, StatusConsultaEnum novoStatus) {
        if (consulta == null) {
            throw new BadRequestException("Consulta não pode ser nula");
        }

        StatusConsultaEnum statusAtual = consulta.getInformacoes() != null ? consulta.getInformacoes().getStatusConsulta() : null;
        if (statusAtual == null) {
            return;
        }

        if (statusAtual == novoStatus) {
            return;
        }

        validarTransicaoPermitida(statusAtual, novoStatus);
    }

    private void validarTransicaoPermitida(StatusConsultaEnum statusAtual, StatusConsultaEnum novoStatus) {
        if (STATUS_FINAIS.contains(statusAtual)) {
            if (novoStatus == StatusConsultaEnum.REMARCADA) {
                return;
            }
            throw new BadRequestException(
                    String.format("Não é possível alterar status de %s para %s. Status finais não podem ser alterados diretamente.",
                            statusAtual.getDescricao(), novoStatus.getDescricao())
            );
        }

        switch (statusAtual) {
            case AGENDADA:
                if (!Set.of(StatusConsultaEnum.CONFIRMADA, StatusConsultaEnum.CANCELADA, StatusConsultaEnum.REMARCADA, StatusConsultaEnum.SUSPENSA).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case CONFIRMADA:
                if (!Set.of(StatusConsultaEnum.EM_ESPERA, StatusConsultaEnum.EM_ANDAMENTO, StatusConsultaEnum.CANCELADA, StatusConsultaEnum.REMARCADA).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case EM_ESPERA:
                if (!Set.of(StatusConsultaEnum.EM_ANDAMENTO, StatusConsultaEnum.CANCELADA, StatusConsultaEnum.FALTA_PACIENTE).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case EM_ANDAMENTO:
                if (!Set.of(StatusConsultaEnum.CONCLUIDA, StatusConsultaEnum.ATENDIDA, StatusConsultaEnum.CANCELADA, StatusConsultaEnum.SUSPENSA).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
            case SUSPENSA:
                if (!Set.of(StatusConsultaEnum.EM_ANDAMENTO, StatusConsultaEnum.CANCELADA, StatusConsultaEnum.REMARCADA).contains(novoStatus)) {
                    throw new BadRequestException(String.format("Transição inválida: %s -> %s", statusAtual.getDescricao(), novoStatus.getDescricao()));
                }
                break;
        }
    }
}
