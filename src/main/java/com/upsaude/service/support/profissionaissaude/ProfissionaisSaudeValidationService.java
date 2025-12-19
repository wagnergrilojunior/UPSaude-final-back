package com.upsaude.service.support.profissionaissaude;

import com.upsaude.api.request.profissional.ProfissionaisSaudeRequest;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProfissionaisSaudeValidationService {

    public void validarObrigatorios(ProfissionaisSaudeRequest request) {
        if (request == null || !StringUtils.hasText(request.getNomeCompleto())) {
            throw new BadRequestException("Nome completo é obrigatório");
        }
        if (!StringUtils.hasText(request.getRegistroProfissional())) {
            throw new BadRequestException("Registro profissional é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(ProfissionaisSaudeRequest request, ProfissionaisSaudeRepository repository, UUID tenantId) {
        validarCpfUnico(null, request.getCpf(), repository, tenantId);
        validarEmailUnico(null, request.getEmail(), repository, tenantId);
        validarRgUnico(null, request.getRg(), repository, tenantId);
        validarCnsUnico(null, request.getCns(), repository, tenantId);
        // Conselho removido - ConselhosProfissionais foi deletado
    }

    public void validarUnicidadeParaAtualizacao(UUID id, ProfissionaisSaudeRequest request, ProfissionaisSaudeRepository repository, UUID tenantId) {
        validarCpfUnico(id, request.getCpf(), repository, tenantId);
        validarEmailUnico(id, request.getEmail(), repository, tenantId);
        validarRgUnico(id, request.getRg(), repository, tenantId);
        validarCnsUnico(id, request.getCns(), repository, tenantId);
        // Conselho removido - ConselhosProfissionais foi deletado
    }

    public void sanitizarFlags(ProfissionaisSaudeRequest request) {
        if (request == null) return;
    }

    private void validarCpfUnico(UUID profissionalId, String cpf, ProfissionaisSaudeRepository repository, UUID tenantId) {
        if (!StringUtils.hasText(cpf)) {
            return;
        }

        Optional<ProfissionaisSaude> profissionalExistente = repository.findByCpfAndTenantId(cpf, tenantId);

        if (profissionalExistente.isPresent()) {
            ProfissionaisSaude profissionalEncontrado = profissionalExistente.get();

            if (profissionalId != null && !profissionalEncontrado.getId().equals(profissionalId)) {
                throw new BadRequestException("Já existe um profissional cadastrado com o CPF: " + cpf);
            }

            if (profissionalId == null) {
                throw new BadRequestException("Já existe um profissional cadastrado com o CPF: " + cpf);
            }
        }
    }

    private void validarEmailUnico(UUID profissionalId, String email, ProfissionaisSaudeRepository repository, UUID tenantId) {
        if (!StringUtils.hasText(email)) {
            return;
        }

        Optional<ProfissionaisSaude> profissionalExistente = repository.findByEmailAndTenantId(email, tenantId);

        if (profissionalExistente.isPresent()) {
            ProfissionaisSaude profissionalEncontrado = profissionalExistente.get();

            if (profissionalId != null && !profissionalEncontrado.getId().equals(profissionalId)) {
                throw new BadRequestException("Já existe um profissional cadastrado com o email: " + email);
            }

            if (profissionalId == null) {
                throw new BadRequestException("Já existe um profissional cadastrado com o email: " + email);
            }
        }
    }

    private void validarRgUnico(UUID profissionalId, String rg, ProfissionaisSaudeRepository repository, UUID tenantId) {
        if (!StringUtils.hasText(rg)) {
            return;
        }

        Optional<ProfissionaisSaude> profissionalExistente = repository.findByRgAndTenantId(rg, tenantId);

        if (profissionalExistente.isPresent()) {
            ProfissionaisSaude profissionalEncontrado = profissionalExistente.get();

            if (profissionalId != null && !profissionalEncontrado.getId().equals(profissionalId)) {
                throw new BadRequestException("Já existe um profissional cadastrado com o RG: " + rg);
            }

            if (profissionalId == null) {
                throw new BadRequestException("Já existe um profissional cadastrado com o RG: " + rg);
            }
        }
    }

    private void validarCnsUnico(UUID profissionalId, String cns, ProfissionaisSaudeRepository repository, UUID tenantId) {
        if (!StringUtils.hasText(cns)) {
            return;
        }

        Optional<ProfissionaisSaude> profissionalExistente = repository.findByCnsAndTenantId(cns, tenantId);

        if (profissionalExistente.isPresent()) {
            ProfissionaisSaude profissionalEncontrado = profissionalExistente.get();

            if (profissionalId != null && !profissionalEncontrado.getId().equals(profissionalId)) {
                throw new BadRequestException("Já existe um profissional cadastrado com o CNS: " + cns);
            }

            if (profissionalId == null) {
                throw new BadRequestException("Já existe um profissional cadastrado com o CNS: " + cns);
            }
        }
    }

    // Método validarRegistroProfissionalUnico removido - ConselhosProfissionais foi deletado
}
