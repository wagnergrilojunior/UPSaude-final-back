package com.upsaude.service.api.support.dadossociodemograficos;

import com.upsaude.api.request.paciente.DadosSociodemograficosRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.repository.paciente.DadosSociodemograficosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DadosSociodemograficosValidationService {

    private final DadosSociodemograficosRepository repository;

    public void validarObrigatorios(DadosSociodemograficosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados sociodemográficos são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
    }

    public void validarDuplicidadeParaCriacao(UUID pacienteId, UUID tenantId) {
        if (repository.existsByPacienteIdAndTenantId(pacienteId, tenantId)) {
            throw new ConflictException("Dados sociodemográficos já existem para este paciente");
        }
    }

    public void validarDuplicidadeParaAtualizacao(UUID id, UUID pacienteId, UUID tenantId) {
        repository.findByPacienteIdAndTenantId(pacienteId, tenantId).ifPresent(d -> {
            if (!d.getId().equals(id)) {
                throw new ConflictException("Dados sociodemográficos já existem para este paciente");
            }
        });
    }
}
