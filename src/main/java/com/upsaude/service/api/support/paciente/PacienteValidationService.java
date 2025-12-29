package com.upsaude.service.api.support.paciente;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.PacienteIdentificador;
import com.upsaude.entity.paciente.PacienteContato;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.paciente.PacienteIdentificadorRepository;
import com.upsaude.repository.paciente.PacienteContatoRepository;
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

    public void validarUnicidadeParaCriacao(PacienteRequest request, 
                                           PacienteRepository pacienteRepository,
                                           PacienteIdentificadorRepository identificadorRepository,
                                           PacienteContatoRepository contatoRepository,
                                           UUID tenantId) {
        validarCpfUnico(null, request.getCpf(), identificadorRepository, tenantId);
        validarEmailUnico(null, request.getEmail(), contatoRepository, tenantId);
        validarCnsUnico(null, request.getCns(), identificadorRepository, tenantId);
        validarRgUnico(null, request.getRg(), identificadorRepository, tenantId);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, PacienteRequest request,
                                                PacienteRepository pacienteRepository,
                                                PacienteIdentificadorRepository identificadorRepository,
                                                PacienteContatoRepository contatoRepository,
                                                UUID tenantId) {
        validarCpfUnico(id, request.getCpf(), identificadorRepository, tenantId);
        validarEmailUnico(id, request.getEmail(), contatoRepository, tenantId);
        validarCnsUnico(id, request.getCns(), identificadorRepository, tenantId);
        validarRgUnico(id, request.getRg(), identificadorRepository, tenantId);
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

    private void validarCpfUnico(UUID pacienteId, String cpf, PacienteIdentificadorRepository identificadorRepository, UUID tenantId) {
        if (!StringUtils.hasText(cpf)) {
            return;
        }

        Optional<PacienteIdentificador> identificadorExistente = identificadorRepository.findByTipoAndValorAndTenantId(TipoIdentificadorEnum.CPF, cpf, tenantId);

        if (identificadorExistente.isPresent()) {
            PacienteIdentificador identificador = identificadorExistente.get();
            UUID pacienteEncontradoId = identificador.getPaciente().getId();

            if (pacienteId != null && !pacienteEncontradoId.equals(pacienteId)) {
                throw new BadRequestException("Já existe um paciente cadastrado com o CPF: " + cpf);
            }

            if (pacienteId == null) {
                throw new BadRequestException("Já existe um paciente cadastrado com o CPF: " + cpf);
            }
        }
    }

    private void validarEmailUnico(UUID pacienteId, String email, PacienteContatoRepository contatoRepository, UUID tenantId) {
        if (!StringUtils.hasText(email)) {
            return;
        }

        Optional<PacienteContato> contatoExistente = contatoRepository.findByEmailAndTenantId(email, tenantId);

        if (contatoExistente.isPresent()) {
            PacienteContato contato = contatoExistente.get();
            UUID pacienteEncontradoId = contato.getPaciente().getId();

            if (pacienteId != null && !pacienteEncontradoId.equals(pacienteId)) {
                throw new BadRequestException("Já existe um paciente cadastrado com o email: " + email);
            }

            if (pacienteId == null) {
                throw new BadRequestException("Já existe um paciente cadastrado com o email: " + email);
            }
        }
    }

    private void validarCnsUnico(UUID pacienteId, String cns, PacienteIdentificadorRepository identificadorRepository, UUID tenantId) {
        if (!StringUtils.hasText(cns)) {
            return;
        }

        Optional<PacienteIdentificador> identificadorExistente = identificadorRepository.findByTipoAndValorAndTenantId(TipoIdentificadorEnum.CNS, cns, tenantId);

        if (identificadorExistente.isPresent()) {
            PacienteIdentificador identificador = identificadorExistente.get();
            UUID pacienteEncontradoId = identificador.getPaciente().getId();

            if (pacienteId != null && !pacienteEncontradoId.equals(pacienteId)) {
                throw new BadRequestException("Já existe um paciente cadastrado com o CNS: " + cns);
            }

            if (pacienteId == null) {
                throw new BadRequestException("Já existe um paciente cadastrado com o CNS: " + cns);
            }
        }
    }

    private void validarRgUnico(UUID pacienteId, String rg, PacienteIdentificadorRepository identificadorRepository, UUID tenantId) {
        if (!StringUtils.hasText(rg)) {
            return;
        }

        Optional<PacienteIdentificador> identificadorExistente = identificadorRepository.findByTipoAndValorAndTenantId(TipoIdentificadorEnum.RG, rg, tenantId);

        if (identificadorExistente.isPresent()) {
            PacienteIdentificador identificador = identificadorExistente.get();
            UUID pacienteEncontradoId = identificador.getPaciente().getId();

            if (pacienteId != null && !pacienteEncontradoId.equals(pacienteId)) {
                throw new BadRequestException("Já existe um paciente cadastrado com o RG: " + rg);
            }

            if (pacienteId == null) {
                throw new BadRequestException("Já existe um paciente cadastrado com o RG: " + rg);
            }
        }
    }
}
