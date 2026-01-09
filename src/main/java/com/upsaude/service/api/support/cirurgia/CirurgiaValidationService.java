package com.upsaude.service.api.support.cirurgia;

import com.upsaude.api.request.clinica.cirurgia.CirurgiaRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;

@Service
public class CirurgiaValidationService {

    public void validarObrigatorios(CirurgiaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da cirurgia são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
        if (request.getCirurgiaoPrincipal() == null) {
            throw new BadRequestException("Cirurgião principal é obrigatório");
        }
        if (!StringUtils.hasText(request.getDescricao())) {
            throw new BadRequestException("Descrição da cirurgia é obrigatória");
        }
        if (request.getDataHoraPrevista() == null) {
            throw new BadRequestException("Data e hora prevista são obrigatórias");
        }
        if (request.getStatus() == null) {
            throw new BadRequestException("Status da cirurgia é obrigatório");
        }

        validarConsistenciaDatas(request.getDataHoraPrevista(), request.getDataHoraInicio(), request.getDataHoraFim());
    }

    private void validarConsistenciaDatas(OffsetDateTime prevista, OffsetDateTime inicio, OffsetDateTime fim) {
        if (inicio != null && fim != null && fim.isBefore(inicio)) {
            throw new BadRequestException("Data/hora fim não pode ser anterior à data/hora início");
        }
        if (inicio != null && prevista != null && inicio.isBefore(prevista.minusDays(3650))) {

            throw new BadRequestException("Data/hora início inválida");
        }
    }
}
