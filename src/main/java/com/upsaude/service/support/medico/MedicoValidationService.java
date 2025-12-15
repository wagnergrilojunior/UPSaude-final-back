package com.upsaude.service.support.medico;

import com.upsaude.api.request.MedicosRequest;
import com.upsaude.entity.Medicos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.MedicosRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class MedicoValidationService {

    public void validarObrigatorios(MedicosRequest request) {
        if (request == null || !StringUtils.hasText(request.getNomeCompleto())) {
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
        if (request.getFormacao() != null && request.getFormacao().getTituloEspecialista() == null) {
            request.getFormacao().setTituloEspecialista(false);
        }
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
        if (request == null || request.getDadosPessoais() == null) {
            return;
        }

        String cpf = request.getDadosPessoais().getCpf();

        if (!StringUtils.hasText(cpf)) {
            return;
        }

        Optional<Medicos> medicoExistente = medicosRepository.findByDadosPessoaisCpfAndTenantId(cpf, tenantId);

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
