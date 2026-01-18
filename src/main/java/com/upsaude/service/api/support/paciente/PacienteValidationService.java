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
        if (request == null) {
            throw new BadRequestException("Objeto de requisição nulo");
        }

        if (request.getDadosPessoaisBasicos() == null) {
            throw new BadRequestException("Dados pessoais básicos são obrigatórios");
        }

        var dados = request.getDadosPessoaisBasicos();
        if (!StringUtils.hasText(dados.getNomeCompleto())) {
            throw new BadRequestException("Nome completo é obrigatório");
        }
        if (dados.getSexo() == null) {
            throw new BadRequestException("Sexo é obrigatório");
        }

        
        if (request.getObito() != null) {
            if (request.getObito().getDataObito() == null) {
                throw new BadRequestException("Data de óbito é obrigatória quando informações de óbito são fornecidas");
            }
        }
    }

    public void validarUnicidadeParaCriacao(PacienteRequest request,
            PacienteRepository pacienteRepository,
            PacienteIdentificadorRepository identificadorRepository,
            PacienteContatoRepository contatoRepository,
            UUID tenantId) {
        if (request.getDocumentosBasicos() != null) {
            validarCpfUnico(null, request.getDocumentosBasicos().getCpf(), identificadorRepository, tenantId);
            validarCnsUnico(null, request.getDocumentosBasicos().getCns(), identificadorRepository, tenantId);
        }

        if (request.getContato() != null) {
            validarEmailUnico(null, request.getContato().getEmail(), contatoRepository, tenantId);
        }
    }

    public void validarUnicidadeParaAtualizacao(UUID id, PacienteRequest request,
            PacienteRepository pacienteRepository,
            PacienteIdentificadorRepository identificadorRepository,
            PacienteContatoRepository contatoRepository,
            UUID tenantId) {
        if (request.getDocumentosBasicos() != null) {
            validarCpfUnico(id, request.getDocumentosBasicos().getCpf(), identificadorRepository, tenantId);
            validarCnsUnico(id, request.getDocumentosBasicos().getCns(), identificadorRepository, tenantId);
        }

        if (request.getContato() != null) {
            validarEmailUnico(id, request.getContato().getEmail(), contatoRepository, tenantId);
        }
    }

    public void sanitizarFlags(PacienteRequest request) {
        if (request.getDadosClinicosBasicos() != null) {
            if (request.getDadosClinicosBasicos().getGestante() == null) {
                request.getDadosClinicosBasicos().setGestante(false);
            }
        }
    }

    private void validarCpfUnico(UUID pacienteId, String cpf, PacienteIdentificadorRepository identificadorRepository,
            UUID tenantId) {
        if (!StringUtils.hasText(cpf)) {
            return;
        }

        Optional<PacienteIdentificador> identificadorExistente = identificadorRepository
                .findByTipoAndValorAndTenantId(TipoIdentificadorEnum.CPF, cpf, tenantId);

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

    private void validarEmailUnico(UUID pacienteId, String email, PacienteContatoRepository contatoRepository,
            UUID tenantId) {
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

    private void validarCnsUnico(UUID pacienteId, String cns, PacienteIdentificadorRepository identificadorRepository,
            UUID tenantId) {
        if (!StringUtils.hasText(cns)) {
            return;
        }

        Optional<PacienteIdentificador> identificadorExistente = identificadorRepository
                .findByTipoAndValorAndTenantId(TipoIdentificadorEnum.CNS, cns, tenantId);

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

    private void validarRgUnico(UUID pacienteId, String rg, PacienteIdentificadorRepository identificadorRepository,
            UUID tenantId) {
        if (!StringUtils.hasText(rg)) {
            return;
        }

        Optional<PacienteIdentificador> identificadorExistente = identificadorRepository
                .findByTipoAndValorAndTenantId(TipoIdentificadorEnum.RG, rg, tenantId);

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
