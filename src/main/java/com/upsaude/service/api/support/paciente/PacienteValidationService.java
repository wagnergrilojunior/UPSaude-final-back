package com.upsaude.service.api.support.paciente;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.paciente.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class PacienteValidationService {

    public void validarObrigatorios(PacienteRequest request) {
        if (request == null || !StringUtils.hasText(request.getNomeCompleto())) {
            throw new BadRequestException("Nome completo é obrigatório");
        }
        if (StringUtils.hasText(request.getRg()) && request.getRg().length() > 20) {
            throw new BadRequestException("RG deve ter no máximo 20 caracteres");
        }
    }

    public void validarUnicidadeParaCriacao(PacienteRequest request, PacienteRepository pacienteRepository, UUID tenantId) {
        validarCpfUnico(null, request.getCpf(), pacienteRepository, tenantId);
        validarEmailUnico(null, request.getEmail(), pacienteRepository, tenantId);
        validarCnsUnico(null, request.getCns(), pacienteRepository, tenantId);
        validarRgUnico(null, request.getRg(), pacienteRepository, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, PacienteRequest request, PacienteRepository pacienteRepository, UUID tenantId) {
        validarCpfUnico(id, request.getCpf(), pacienteRepository, tenantId);
        validarEmailUnico(id, request.getEmail(), pacienteRepository, tenantId);
        validarCnsUnico(id, request.getCns(), pacienteRepository, tenantId);
        validarRgUnico(id, request.getRg(), pacienteRepository, tenantId);
    }

    public void sanitizarFlags(PacienteRequest request) {
        if (request == null) return;
        if (request.getAcompanhadoPorEquipeEsf() == null) {
            request.setAcompanhadoPorEquipeEsf(false);
        }
        if (request.getPossuiDeficiencia() == null) {
            request.setPossuiDeficiencia(false);
        }
        if (request.getCnsValidado() == null) {
            request.setCnsValidado(false);
        }
        if (request.getSituacaoRua() == null) {
            request.setSituacaoRua(false);
        }
        if (request.getCartaoSusAtivo() == null) {
            request.setCartaoSusAtivo(true);
        }
    }

    private void validarCpfUnico(UUID pacienteId, String cpf, PacienteRepository pacienteRepository, UUID tenantId) {
        if (!StringUtils.hasText(cpf)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByCpfAndTenantId(cpf, tenantId);

        if (pacienteExistente.isPresent()) {
            Paciente pacienteEncontrado = pacienteExistente.get();

            if (pacienteId != null && !pacienteEncontrado.getId().equals(pacienteId)) {
                throw new BadRequestException("Já existe um paciente cadastrado com o CPF: " + cpf);
            }

            if (pacienteId == null) {
                throw new BadRequestException("Já existe um paciente cadastrado com o CPF: " + cpf);
            }
        }
    }

    private void validarEmailUnico(UUID pacienteId, String email, PacienteRepository pacienteRepository, UUID tenantId) {
        if (!StringUtils.hasText(email)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByEmailAndTenantId(email, tenantId);

        if (pacienteExistente.isPresent()) {
            Paciente pacienteEncontrado = pacienteExistente.get();

            if (pacienteId != null && !pacienteEncontrado.getId().equals(pacienteId)) {
                throw new BadRequestException("Já existe um paciente cadastrado com o email: " + email);
            }

            if (pacienteId == null) {
                throw new BadRequestException("Já existe um paciente cadastrado com o email: " + email);
            }
        }
    }

    private void validarCnsUnico(UUID pacienteId, String cns, PacienteRepository pacienteRepository, UUID tenantId) {
        if (!StringUtils.hasText(cns)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByCnsAndTenantId(cns, tenantId);

        if (pacienteExistente.isPresent()) {
            Paciente pacienteEncontrado = pacienteExistente.get();

            if (pacienteId != null && !pacienteEncontrado.getId().equals(pacienteId)) {
                throw new BadRequestException("Já existe um paciente cadastrado com o CNS: " + cns);
            }

            if (pacienteId == null) {
                throw new BadRequestException("Já existe um paciente cadastrado com o CNS: " + cns);
            }
        }
    }

    private void validarRgUnico(UUID pacienteId, String rg, PacienteRepository pacienteRepository, UUID tenantId) {
        if (!StringUtils.hasText(rg)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByRgAndTenantId(rg, tenantId);

        if (pacienteExistente.isPresent()) {
            Paciente pacienteEncontrado = pacienteExistente.get();

            if (pacienteId != null && !pacienteEncontrado.getId().equals(pacienteId)) {
                throw new BadRequestException("Já existe um paciente cadastrado com o RG: " + rg);
            }

            if (pacienteId == null) {
                throw new BadRequestException("Já existe um paciente cadastrado com o RG: " + rg);
            }
        }
    }
}
