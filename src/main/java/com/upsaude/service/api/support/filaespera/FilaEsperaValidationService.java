package com.upsaude.service.api.support.filaespera;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.agendamento.FilaEsperaRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class FilaEsperaValidationService {

    public void validarObrigatorios(FilaEsperaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da fila de espera são obrigatórios");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
        if (request.getDataEntrada() == null) {
            throw new BadRequestException("Data de entrada na fila é obrigatória");
        }
    }

    public void validarConsistencias(FilaEsperaRequest request) {
        if (request == null) return;

        if (request.getNotificacoesEnviadas() != null && request.getNotificacoesEnviadas() < 0) {
            throw new BadRequestException("Notificações enviadas não pode ser negativo");
        }
        if (request.getPosicaoFila() != null && request.getPosicaoFila() < 0) {
            throw new BadRequestException("Posição na fila não pode ser negativa");
        }
    }
}

