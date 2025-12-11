package com.upsaude.helper;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.util.StringUtils;

import com.upsaude.entity.Paciente;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.PacienteRepository;

public class ValidadorHelper {

    public static void validarCpfUnicoPaciente(UUID pacienteId, String cpf, PacienteRepository pacienteRepository) {
        if (!StringUtils.hasText(cpf)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByCpf(cpf);

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

    public static void validarEmailUnicoPaciente(UUID pacienteId, String email, PacienteRepository pacienteRepository) {
        if (!StringUtils.hasText(email)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByEmail(email);

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

    public static void validarCnsUnicoPaciente(UUID pacienteId, String cns, PacienteRepository pacienteRepository) {
        if (!StringUtils.hasText(cns)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByCns(cns);

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

    public static void validarRgUnicoPaciente(UUID pacienteId, String rg, PacienteRepository pacienteRepository) {
        if (!StringUtils.hasText(rg)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByRg(rg);

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

    public static <T> void validarCampoUnico(
            UUID entidadeId,
            String valor,
            Function<String, Optional<T>> buscarPorCampo,
            Function<T, UUID> obterId,
            String nomeCampo,
            String nomeEntidade) {
        
        if (!StringUtils.hasText(valor)) {
            return;
        }

        Optional<T> entidadeExistente = buscarPorCampo.apply(valor);

        if (entidadeExistente.isPresent()) {
            T entidadeEncontrada = entidadeExistente.get();
            UUID idEncontrado = obterId.apply(entidadeEncontrada);

            if (entidadeId != null && !idEncontrado.equals(entidadeId)) {
                throw new BadRequestException(
                    String.format("Já existe um %s cadastrado com o %s: %s", nomeEntidade, nomeCampo, valor));
            }

            if (entidadeId == null) {
                throw new BadRequestException(
                    String.format("Já existe um %s cadastrado com o %s: %s", nomeEntidade, nomeCampo, valor));
            }
        }
    }

    public static <T> void validarCampoUnicoPorTenant(
            UUID entidadeId,
            String valor,
            Tenant tenant,
            Function<Object[], Optional<T>> buscarPorCampoETenant,
            Function<T, UUID> obterId,
            String nomeCampo,
            String nomeEntidade) {
        
        if (!StringUtils.hasText(valor) || tenant == null) {
            return;
        }

        Optional<T> entidadeExistente = buscarPorCampoETenant.apply(new Object[]{valor, tenant});

        if (entidadeExistente.isPresent()) {
            T entidadeEncontrada = entidadeExistente.get();
            UUID idEncontrado = obterId.apply(entidadeEncontrada);

            if (entidadeId != null && !idEncontrado.equals(entidadeId)) {
                throw new BadRequestException(
                    String.format("Já existe um %s cadastrado com o %s %s neste tenant.", nomeEntidade, nomeCampo, valor));
            }

            if (entidadeId == null) {
                throw new BadRequestException(
                    String.format("Já existe um %s cadastrado com o %s %s neste tenant.", nomeEntidade, nomeCampo, valor));
            }
        }
    }
}
