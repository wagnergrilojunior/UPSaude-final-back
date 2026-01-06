package com.upsaude.service.api.support.profissionaissaude;

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
        if (request == null || 
            request.getDadosPessoaisBasicos() == null || 
            !StringUtils.hasText(request.getDadosPessoaisBasicos().getNomeCompleto())) {
            throw new BadRequestException("Nome completo é obrigatório");
        }
        if (request.getRegistroProfissional() == null || 
            !StringUtils.hasText(request.getRegistroProfissional().getRegistroProfissional())) {
            throw new BadRequestException("Registro profissional é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(ProfissionaisSaudeRequest request, ProfissionaisSaudeRepository repository, UUID tenantId) {
        String cpf = request.getDocumentosBasicos() != null ? request.getDocumentosBasicos().getCpf() : null;
        String email = request.getContato() != null ? request.getContato().getEmail() : null;
        String rg = request.getDocumentosBasicos() != null ? request.getDocumentosBasicos().getRg() : null;
        String cns = request.getDocumentosBasicos() != null ? request.getDocumentosBasicos().getCns() : null;

        validarCpfUnico(null, cpf, repository, tenantId);
        validarEmailUnico(null, email, repository, tenantId);
        validarRgUnico(null, rg, repository, tenantId);
        validarCnsUnico(null, cns, repository, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, ProfissionaisSaudeRequest request, ProfissionaisSaudeRepository repository, UUID tenantId) {
        String cpf = request.getDocumentosBasicos() != null ? request.getDocumentosBasicos().getCpf() : null;
        String email = request.getContato() != null ? request.getContato().getEmail() : null;
        String rg = request.getDocumentosBasicos() != null ? request.getDocumentosBasicos().getRg() : null;
        String cns = request.getDocumentosBasicos() != null ? request.getDocumentosBasicos().getCns() : null;

        validarCpfUnico(id, cpf, repository, tenantId);
        validarEmailUnico(id, email, repository, tenantId);
        validarRgUnico(id, rg, repository, tenantId);
        validarCnsUnico(id, cns, repository, tenantId);
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

}
