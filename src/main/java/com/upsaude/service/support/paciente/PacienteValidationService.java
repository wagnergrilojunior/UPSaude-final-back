package com.upsaude.service.support.paciente;

import com.upsaude.api.request.PacienteRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.helper.ValidadorHelper;
import com.upsaude.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public void validarUnicidadeParaCriacao(PacienteRequest request, PacienteRepository pacienteRepository) {
        ValidadorHelper.validarCpfUnicoPaciente(null, request.getCpf(), pacienteRepository);
        ValidadorHelper.validarEmailUnicoPaciente(null, request.getEmail(), pacienteRepository);
        ValidadorHelper.validarCnsUnicoPaciente(null, request.getCns(), pacienteRepository);
        ValidadorHelper.validarRgUnicoPaciente(null, request.getRg(), pacienteRepository);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, PacienteRequest request, PacienteRepository pacienteRepository) {
        ValidadorHelper.validarCpfUnicoPaciente(id, request.getCpf(), pacienteRepository);
        ValidadorHelper.validarEmailUnicoPaciente(id, request.getEmail(), pacienteRepository);
        ValidadorHelper.validarCnsUnicoPaciente(id, request.getCns(), pacienteRepository);
        ValidadorHelper.validarRgUnicoPaciente(id, request.getRg(), pacienteRepository);
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
}
