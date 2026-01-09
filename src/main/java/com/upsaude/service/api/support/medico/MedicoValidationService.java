package com.upsaude.service.api.support.medico;

import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.profissional.MedicosRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class MedicoValidationService {

    public void validarObrigatorios(MedicosRequest request) {
        if (request == null || 
            request.getDadosPessoaisBasicos() == null || 
            !StringUtils.hasText(request.getDadosPessoaisBasicos().getNomeCompleto())) {
            throw new BadRequestException("Nome completo é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(MedicosRequest request, MedicosRepository medicosRepository, UUID tenantId) {
        validarCrmUnico(null, request, medicosRepository, tenantId);
        validarCpfUnico(null, request, medicosRepository, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, MedicosRequest request, MedicosRepository medicosRepository, UUID tenantId) {
        validarCrmUnico(id, request, medicosRepository, tenantId);
        validarCpfUnico(id, request, medicosRepository, tenantId);
    }

    public void sanitizarFlags(MedicosRequest request) {
        if (request == null) return;
    }

    private void validarCrmUnico(UUID medicoId, MedicosRequest request, MedicosRepository medicosRepository, UUID tenantId) {
        if (request == null || request.getRegistroProfissional() == null) {
            return;
        }

        String crm = request.getRegistroProfissional().getCrm();
        String crmUf = request.getRegistroProfissional().getCrmUf();

        if (!StringUtils.hasText(crm) || !StringUtils.hasText(crmUf)) {
            return;
        }

        Optional<Medicos> medicoExistente = medicosRepository
                .findByRegistroProfissionalCrmAndRegistroProfissionalCrmUfAndTenantId(crm, crmUf, tenantId);

        if (medicoExistente.isPresent()) {
            Medicos medicoEncontrado = medicoExistente.get();

            if (medicoId != null && !medicoEncontrado.getId().equals(medicoId)) {
                throw new BadRequestException(
                        String.format("Já existe um médico cadastrado com CRM %s/%s neste tenant.", crm, crmUf));
            }

            if (medicoId == null) {
                throw new BadRequestException(
                        String.format("Já existe um médico cadastrado com CRM %s/%s neste tenant.", crm, crmUf));
            }
        }
    }

    private void validarCpfUnico(UUID medicoId, MedicosRequest request, MedicosRepository medicosRepository, UUID tenantId) {
        if (request == null || request.getDocumentosBasicos() == null) {
            return;
        }

        String cpf = request.getDocumentosBasicos().getCpf();

        if (!StringUtils.hasText(cpf)) {
            return;
        }

        Optional<Medicos> medicoExistente = medicosRepository.findByDocumentosBasicosCpfAndTenantId(cpf, tenantId);

        if (medicoExistente.isPresent()) {
            Medicos medicoEncontrado = medicoExistente.get();

            if (medicoId != null && !medicoEncontrado.getId().equals(medicoId)) {
                throw new BadRequestException(
                        String.format("Já existe um médico cadastrado com CPF %s neste tenant.", cpf));
            }

            if (medicoId == null) {
                throw new BadRequestException(
                        String.format("Já existe um médico cadastrado com CPF %s neste tenant.", cpf));
            }
        }
    }
}
