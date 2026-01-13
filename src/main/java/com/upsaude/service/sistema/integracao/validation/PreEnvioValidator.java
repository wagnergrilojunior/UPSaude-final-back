package com.upsaude.service.sistema.integracao.validation;

import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.atendimento.Consulta;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.paciente.PacienteIdentificador;
import com.upsaude.entity.paciente.PacienteVinculoTerritorial;
import com.upsaude.enums.TipoErroIntegracaoEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.repository.paciente.PacienteVinculoTerritorialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PreEnvioValidator {

    private final PacienteVinculoTerritorialRepository vinculoTerritorialRepository;

    public ValidacaoPreEnvioResultado validarParaRnds(Agendamento agendamento) {
        List<String> erros = new ArrayList<>();

        if (agendamento == null) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Agendamento não pode ser nulo");
        }

        if (agendamento.getEstabelecimento() == null) {
            erros.add("Estabelecimento não informado");
        } else {
            String cnes = obterCnes(agendamento.getEstabelecimento());
            if (cnes == null || cnes.trim().isEmpty()) {
                erros.add("CNES do estabelecimento não informado ou inválido");
            }
        }

        if (agendamento.getPaciente() == null) {
            erros.add("Paciente não informado");
        } else {
            ValidacaoPreEnvioResultado validacaoPaciente = validarIdentificadoresPaciente(agendamento.getPaciente(), true);
            if (!validacaoPaciente.isValido()) {
                erros.addAll(validacaoPaciente.getDetalhesErros());
            }
        }

        if (agendamento.getDataHora() == null) {
            erros.add("Data/hora do agendamento não informada");
        }

        if (!erros.isEmpty()) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Validação falhou", erros);
        }

        return ValidacaoPreEnvioResultado.sucesso();
    }

    public ValidacaoPreEnvioResultado validarParaRnds(Atendimento atendimento) {
        List<String> erros = new ArrayList<>();

        if (atendimento == null) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Atendimento não pode ser nulo");
        }

        if (atendimento.getEstabelecimento() == null) {
            erros.add("Estabelecimento não informado");
        } else {
            String cnes = obterCnes(atendimento.getEstabelecimento());
            if (cnes == null || cnes.trim().isEmpty()) {
                erros.add("CNES do estabelecimento não informado ou inválido");
            }
        }

        if (atendimento.getPaciente() == null) {
            erros.add("Paciente não informado");
        } else {
            ValidacaoPreEnvioResultado validacaoPaciente = validarIdentificadoresPaciente(atendimento.getPaciente(), true);
            if (!validacaoPaciente.isValido()) {
                erros.addAll(validacaoPaciente.getDetalhesErros());
            }
        }

        if (atendimento.getInformacoes() != null && atendimento.getInformacoes().getDataHora() == null) {
            erros.add("Data/hora do atendimento não informada");
        }

        if (!erros.isEmpty()) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Validação falhou", erros);
        }

        return ValidacaoPreEnvioResultado.sucesso();
    }

    public ValidacaoPreEnvioResultado validarParaEsusAps(Agendamento agendamento) {
        List<String> erros = new ArrayList<>();

        if (agendamento == null) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Agendamento não pode ser nulo");
        }

        if (agendamento.getEstabelecimento() == null) {
            erros.add("Estabelecimento não informado");
        } else {
            String cnes = obterCnes(agendamento.getEstabelecimento());
            if (cnes == null || cnes.trim().isEmpty()) {
                erros.add("CNES do estabelecimento não informado ou inválido");
            }
        }

        if (agendamento.getPaciente() == null) {
            erros.add("Paciente não informado");
        } else {
            ValidacaoPreEnvioResultado validacaoPaciente = validarIdentificadoresPaciente(agendamento.getPaciente(), false);
            if (!validacaoPaciente.isValido()) {
                erros.addAll(validacaoPaciente.getDetalhesErros());
            }

            ValidacaoPreEnvioResultado validacaoVinculo = validarVinculoTerritorial(agendamento.getPaciente(), agendamento.getEstabelecimento() != null ? obterCnes(agendamento.getEstabelecimento()) : null);
            if (!validacaoVinculo.isValido()) {
                erros.addAll(validacaoVinculo.getDetalhesErros());
            }
        }

        if (agendamento.getDataHora() == null) {
            erros.add("Data/hora do agendamento não informada");
        }

        if (!erros.isEmpty()) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Validação falhou", erros);
        }

        return ValidacaoPreEnvioResultado.sucesso();
    }

    public ValidacaoPreEnvioResultado validarParaEsusAps(Atendimento atendimento) {
        List<String> erros = new ArrayList<>();

        if (atendimento == null) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Atendimento não pode ser nulo");
        }

        if (atendimento.getEstabelecimento() == null) {
            erros.add("Estabelecimento não informado");
        } else {
            String cnes = obterCnes(atendimento.getEstabelecimento());
            if (cnes == null || cnes.trim().isEmpty()) {
                erros.add("CNES do estabelecimento não informado ou inválido");
            }
        }

        if (atendimento.getPaciente() == null) {
            erros.add("Paciente não informado");
        } else {
            ValidacaoPreEnvioResultado validacaoPaciente = validarIdentificadoresPaciente(atendimento.getPaciente(), false);
            if (!validacaoPaciente.isValido()) {
                erros.addAll(validacaoPaciente.getDetalhesErros());
            }

            ValidacaoPreEnvioResultado validacaoVinculo = validarVinculoTerritorial(atendimento.getPaciente(), atendimento.getEstabelecimento() != null ? obterCnes(atendimento.getEstabelecimento()) : null);
            if (!validacaoVinculo.isValido()) {
                erros.addAll(validacaoVinculo.getDetalhesErros());
            }
        }

        if (atendimento.getInformacoes() != null && atendimento.getInformacoes().getDataHora() == null) {
            erros.add("Data/hora do atendimento não informada");
        }

        if (!erros.isEmpty()) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Validação falhou", erros);
        }

        return ValidacaoPreEnvioResultado.sucesso();
    }

    public ValidacaoPreEnvioResultado validarParaEsusAps(Consulta consulta) {
        List<String> erros = new ArrayList<>();

        if (consulta == null) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Consulta não pode ser nula");
        }

        if (consulta.getAtendimento() == null || consulta.getAtendimento().getEstabelecimento() == null) {
            erros.add("Estabelecimento não informado");
        } else {
            String cnes = obterCnes(consulta.getAtendimento().getEstabelecimento());
            if (cnes == null || cnes.trim().isEmpty()) {
                erros.add("CNES do estabelecimento não informado ou inválido");
            }
        }

        if (consulta.getPaciente() == null) {
            erros.add("Paciente não informado");
        } else {
            ValidacaoPreEnvioResultado validacaoPaciente = validarIdentificadoresPaciente(consulta.getPaciente(), false);
            if (!validacaoPaciente.isValido()) {
                erros.addAll(validacaoPaciente.getDetalhesErros());
            }

            String cnesEstabelecimento = consulta.getAtendimento() != null && consulta.getAtendimento().getEstabelecimento() != null
                    ? obterCnes(consulta.getAtendimento().getEstabelecimento())
                    : null;
            ValidacaoPreEnvioResultado validacaoVinculo = validarVinculoTerritorial(consulta.getPaciente(), cnesEstabelecimento);
            if (!validacaoVinculo.isValido()) {
                erros.addAll(validacaoVinculo.getDetalhesErros());
            }
        }

        if (!erros.isEmpty()) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Validação falhou", erros);
        }

        return ValidacaoPreEnvioResultado.sucesso();
    }

    private ValidacaoPreEnvioResultado validarIdentificadoresPaciente(Paciente paciente, boolean requerCns) {
        List<String> erros = new ArrayList<>();

        if (paciente.getIdentificadores() == null || paciente.getIdentificadores().isEmpty()) {
            erros.add("Paciente não possui identificadores cadastrados");
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Identificadores faltando", erros);
        }

        Optional<PacienteIdentificador> cnsOpt = paciente.getIdentificadores().stream()
                .filter(id -> id.getTipo() == TipoIdentificadorEnum.CNS && Boolean.TRUE.equals(id.getPrincipal()))
                .findFirst();

        Optional<PacienteIdentificador> cpfOpt = paciente.getIdentificadores().stream()
                .filter(id -> id.getTipo() == TipoIdentificadorEnum.CPF && Boolean.TRUE.equals(id.getPrincipal()))
                .findFirst();

        if (requerCns && cnsOpt.isEmpty()) {
            erros.add("Paciente não possui CNS cadastrado");
        }

        if (cpfOpt.isEmpty() && cnsOpt.isEmpty()) {
            erros.add("Paciente deve possuir ao menos CPF ou CNS cadastrado");
        }

        if (!erros.isEmpty()) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Identificadores faltando", erros);
        }

        return ValidacaoPreEnvioResultado.sucesso();
    }

    private ValidacaoPreEnvioResultado validarVinculoTerritorial(Paciente paciente, String cnesEstabelecimento) {
        if (cnesEstabelecimento == null || cnesEstabelecimento.trim().isEmpty()) {
            return ValidacaoPreEnvioResultado.sucesso();
        }

        List<PacienteVinculoTerritorial> vinculos = vinculoTerritorialRepository.findByPacienteId(paciente.getId());
        if (vinculos == null || vinculos.isEmpty()) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Paciente não possui vínculo territorial cadastrado para APS");
        }

        boolean temVinculoAtivoComEstabelecimento = vinculos.stream()
                .anyMatch(v -> Boolean.TRUE.equals(v.getActive()) 
                        && cnesEstabelecimento.equals(v.getCnesEstabelecimento()) 
                        && v.getDataFim() == null);

        if (!temVinculoAtivoComEstabelecimento) {
            return ValidacaoPreEnvioResultado.erro(TipoErroIntegracaoEnum.VALIDACAO, "Paciente não possui vínculo territorial ativo com o estabelecimento CNES: " + cnesEstabelecimento);
        }

        return ValidacaoPreEnvioResultado.sucesso();
    }

    private String obterCnes(com.upsaude.entity.estabelecimento.Estabelecimentos estabelecimento) {
        if (estabelecimento == null) {
            return null;
        }
        if (estabelecimento.getDadosIdentificacao() == null) {
            return null;
        }
        return estabelecimento.getDadosIdentificacao().getCnes();
    }
}
