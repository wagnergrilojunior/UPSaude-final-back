package com.upsaude.service.api.support.paciente;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.Paciente;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PacienteAssociacoesManager {

    @Transactional
    public void processarTodas(Paciente paciente, PacienteRequest request, UUID tenantId) {
        log.debug("Processando todas as associações para paciente ID: {}", paciente.getId());

        // Campos removidos do PacienteRequest - devem ser processados através dos Request específicos:
        // - Identificadores (CPF, CNS, RG) -> PacienteIdentificadorRequest
        // - Contatos (telefone, email) -> PacienteContatoRequest
        // - Dados pessoais complementares -> PacienteDadosPessoaisComplementaresRequest
        // - Endereços -> PacienteEnderecoRequest
        // - Deficiências -> DeficienciasPacienteRequest

        log.info("Associações devem ser processadas através dos endpoints específicos para paciente ID: {}", paciente.getId());
    }
}
