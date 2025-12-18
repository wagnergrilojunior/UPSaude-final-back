package com.upsaude.service.support.agendamento;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Service
public class AgendamentoValidationService {

    public void validarObrigatorios(AgendamentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do agendamento são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional é obrigatório");
        }
        if (request.getDataHora() == null) {
            throw new BadRequestException("Data e hora são obrigatórias");
        }
        if (request.getStatus() == null) {
            throw new BadRequestException("Status do agendamento é obrigatório");
        }
    }

    public void verificarConflitosHorario(AgendamentoRequest request, AgendamentoRepository repository, UUID tenantId) {
        if (request == null || request.getProfissional() == null || request.getDataHora() == null) {
            return;
        }

        OffsetDateTime inicio = request.getDataHora();
        OffsetDateTime fim = request.getDataHoraFim();

        if (fim == null) {
            Integer duracao = request.getDuracaoPrevistaMinutos();
            if (duracao != null && duracao > 0) {
                fim = inicio.plusMinutes(duracao);
            } else {
                // fallback: janela mínima para detecção de sobreposição
                fim = inicio.plusMinutes(1);
            }
        }

        if (Boolean.TRUE.equals(request.getSobreposicaoPermitida())) {
            return;
        }

        var conflitos = repository.findConflitosHorario(request.getProfissional(), tenantId, inicio, fim, StatusAgendamentoEnum.CANCELADO);
        if (!conflitos.isEmpty()) {
            log.warn("Conflito de horário detectado. Profissional: {}, tenant: {}, inicio: {}, fim: {}", request.getProfissional(), tenantId, inicio, fim);
            throw new BadRequestException("Conflito de horário detectado para o profissional");
        }
    }
}
