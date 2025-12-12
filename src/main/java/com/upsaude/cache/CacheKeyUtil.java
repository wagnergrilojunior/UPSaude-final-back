package com.upsaude.cache;

import java.util.UUID;

public final class CacheKeyUtil {

    public static final String CACHE_MEDICOS = "medicos";
    public static final String CACHE_PACIENTES = "pacientes";
    public static final String CACHE_PROFISSIONAIS_SAUDE = "profissionaisSaude";
    public static final String CACHE_ACOES_PROMOCAO_PREVENCAO = "acoesPromocaoPrevencao";
    public static final String CACHE_AGENDAMENTOS = "agendamentos";
    public static final String CACHE_ALERGIAS = "alergias";
    public static final String CACHE_CATALOGO_EXAMES = "catalogoExames";
    public static final String CACHE_EQUIPES_SAUDE = "equipesSaude";
    public static final String CACHE_VACINAS = "vacinas";
    public static final String CACHE_ATENDIMENTOS = "atendimentos";
    public static final String CACHE_CONSULTAS = "consultas";
    public static final String CACHE_PRONTUARIOS = "prontuarios";
    public static final String CACHE_PRE_NATAL = "preNatal";
    public static final String CACHE_PUERICULTURA = "puericultura";
    public static final String CACHE_VACINACOES = "vacinacoes";
    public static final String CACHE_EXAMES = "exames";
    public static final String CACHE_ESTOQUES_VACINA = "estoquesVacina";
    public static final String CACHE_MEDICACAO = "medicacao";
    public static final String CACHE_MEDICACAO_PACIENTE = "medicacaoPaciente";

    private CacheKeyUtil() {
    }

    public static String medico(UUID tenantId, UUID medicoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (medicoId == null) {
            throw new IllegalArgumentException("medicoId é obrigatório");
        }
        return String.format("medico_%s_%s", tenantId, medicoId);
    }

    public static String paciente(UUID tenantId, UUID pacienteId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (pacienteId == null) {
            throw new IllegalArgumentException("pacienteId é obrigatório");
        }
        return String.format("paciente_%s_%s", tenantId, pacienteId);
    }

    public static String profissionalSaude(UUID tenantId, UUID profissionalSaudeId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (profissionalSaudeId == null) {
            throw new IllegalArgumentException("profissionalSaudeId é obrigatório");
        }
        return String.format("profissionalSaude_%s_%s", tenantId, profissionalSaudeId);
    }

    public static String acaoPromocaoPrevencao(UUID tenantId, UUID acaoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (acaoId == null) {
            throw new IllegalArgumentException("acaoId é obrigatório");
        }
        return String.format("acaoPromocaoPrevencao_%s_%s", tenantId, acaoId);
    }

    public static String agendamento(UUID tenantId, UUID agendamentoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (agendamentoId == null) {
            throw new IllegalArgumentException("agendamentoId é obrigatório");
        }
        return String.format("agendamento_%s_%s", tenantId, agendamentoId);
    }

    public static String alergia(UUID alergiaId) {
        if (alergiaId == null) {
            throw new IllegalArgumentException("alergiaId é obrigatório");
        }
        return String.format("alergia_%s", alergiaId);
    }

    public static String catalogoExame(UUID tenantId, UUID catalogoExameId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (catalogoExameId == null) {
            throw new IllegalArgumentException("catalogoExameId é obrigatório");
        }
        return String.format("catalogoExame_%s_%s", tenantId, catalogoExameId);
    }

    public static String equipeSaude(UUID tenantId, UUID equipeSaudeId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (equipeSaudeId == null) {
            throw new IllegalArgumentException("equipeSaudeId é obrigatório");
        }
        return String.format("equipeSaude_%s_%s", tenantId, equipeSaudeId);
    }

    public static String vacina(UUID vacinaId) {
        if (vacinaId == null) {
            throw new IllegalArgumentException("vacinaId é obrigatório");
        }
        return String.format("vacina_%s", vacinaId);
    }

    public static String atendimento(UUID tenantId, UUID atendimentoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (atendimentoId == null) {
            throw new IllegalArgumentException("atendimentoId é obrigatório");
        }
        return String.format("atendimento_%s_%s", tenantId, atendimentoId);
    }

    public static String consulta(UUID tenantId, UUID consultaId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (consultaId == null) {
            throw new IllegalArgumentException("consultaId é obrigatório");
        }
        return String.format("consulta_%s_%s", tenantId, consultaId);
    }

    public static String prontuario(UUID tenantId, UUID prontuarioId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (prontuarioId == null) {
            throw new IllegalArgumentException("prontuarioId é obrigatório");
        }
        return String.format("prontuario_%s_%s", tenantId, prontuarioId);
    }

    public static String preNatal(UUID tenantId, UUID preNatalId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (preNatalId == null) {
            throw new IllegalArgumentException("preNatalId é obrigatório");
        }
        return String.format("preNatal_%s_%s", tenantId, preNatalId);
    }

    public static String puericultura(UUID tenantId, UUID puericulturaId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (puericulturaId == null) {
            throw new IllegalArgumentException("puericulturaId é obrigatório");
        }
        return String.format("puericultura_%s_%s", tenantId, puericulturaId);
    }

    public static String vacinacao(UUID tenantId, UUID vacinacaoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (vacinacaoId == null) {
            throw new IllegalArgumentException("vacinacaoId é obrigatório");
        }
        return String.format("vacinacao_%s_%s", tenantId, vacinacaoId);
    }

    public static String exame(UUID tenantId, UUID exameId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (exameId == null) {
            throw new IllegalArgumentException("exameId é obrigatório");
        }
        return String.format("exame_%s_%s", tenantId, exameId);
    }

    public static String estoqueVacina(UUID tenantId, UUID estoqueVacinaId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (estoqueVacinaId == null) {
            throw new IllegalArgumentException("estoqueVacinaId é obrigatório");
        }
        return String.format("estoqueVacina_%s_%s", tenantId, estoqueVacinaId);
    }

    public static String medicacao(UUID medicacaoId) {
        if (medicacaoId == null) {
            throw new IllegalArgumentException("medicacaoId é obrigatório");
        }
        return String.format("medicacao_%s", medicacaoId);
    }

    public static String medicacaoPaciente(UUID tenantId, UUID medicacaoPacienteId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (medicacaoPacienteId == null) {
            throw new IllegalArgumentException("medicacaoPacienteId é obrigatório");
        }
        return String.format("medicacaoPaciente_%s_%s", tenantId, medicacaoPacienteId);
    }
}
