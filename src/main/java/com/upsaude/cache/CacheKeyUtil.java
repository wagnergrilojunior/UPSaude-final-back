package com.upsaude.cache;

import com.upsaude.entity.clinica.atendimento.Atendimento;

import java.util.UUID;

public final class CacheKeyUtil {

    public static final String CACHE_MEDICOS = "medicos";
    public static final String CACHE_PACIENTES = "pacientes";
    public static final String CACHE_PROFISSIONAIS_SAUDE = "profissionaisSaude";
    public static final String CACHE_AGENDAMENTOS = "agendamentos";
    public static final String CACHE_ALERGIAS = "alergias";
    public static final String CACHE_EQUIPES_SAUDE = "equipesSaude";
    public static final String CACHE_VACINAS = "vacinas";
    public static final String CACHE_ATENDIMENTOS = "atendimentos";
    // Compatibilidade: módulo Atendimento usa cache name singular "atendimento"
    public static final String CACHE_ATENDIMENTO = "atendimento";
    public static final String CACHE_CONSULTAS = "consultas";
    public static final String CACHE_PRONTUARIOS = "prontuarios";
    public static final String CACHE_EXAMES = "exames";
    public static final String CACHE_CONVENIOS = "convenio";
    public static final String CACHE_DADOS_CLINICOS_BASICOS = "dadosClinicosBasicos";
    public static final String CACHE_DADOS_SOCIODEMOGRAFICOS = "dadosSociodemograficos";
    public static final String CACHE_CID_DOENCAS = "ciddoencas";
    public static final String CACHE_CIDADES = "cidades";
    public static final String CACHE_CIRURGIAS = "cirurgias";
    public static final String CACHE_ESTABELECIMENTOS = "estabelecimentos";
    public static final String CACHE_ESTADOS = "estados";
    public static final String CACHE_FABRICANTES_EQUIPAMENTO = "fabricantesequipamento";
    public static final String CACHE_DEFICIENCIAS = "deficiencias";
    public static final String CACHE_DEPARTAMENTOS = "departamentos";
    public static final String CACHE_FALTAS = "faltas";
    public static final String CACHE_FILA_ESPERA = "filaEspera";
    public static final String CACHE_PERFIS_USUARIOS = "perfisusuarios";
    public static final String CACHE_PERMISSOES = "permissoes";
    public static final String CACHE_PLANTOES = "plantoes";
    public static final String CACHE_PROCEDIMENTOS_CIRURGICOS = "procedimentosCirurgicos";
    public static final String CACHE_PROCEDIMENTOS_ODONTOLOGICOS = "procedimentosOdontologicos";
    public static final String CACHE_RECEITAS_MEDICAS = "receitasmedicas";
    public static final String CACHE_RESPONSAVEIS_LEGAIS = "responsavellegal";
    public static final String CACHE_SERVICOS_ESTABELECIMENTO = "servicosEstabelecimento";
    public static final String CACHE_TEMPLATES_NOTIFICACAO = "templatesNotificacao";
    public static final String CACHE_NOTIFICACOES = "notificacoes";
    public static final String CACHE_CONFIGURACOES_ESTABELECIMENTO = "configuracoesEstabelecimento";
    public static final String CACHE_CONSELHOS_PROFISSIONAIS = "conselhosprofissionais";
    public static final String CACHE_CONTROLE_PONTO = "controleponto";
    public static final String CACHE_CUIDADOS_ENFERMAGEM = "cuidadosenfermagem";
    public static final String CACHE_DISPENSACOES_MEDICAMENTOS = "dispensacoesmedicamentos";
    public static final String CACHE_EQUIPAMENTOS = "equipamentos";
    public static final String CACHE_EQUIPAMENTOS_ESTABELECIMENTO = "equipamentosestabelecimento";
    public static final String CACHE_ESCALA_TRABALHO = "escalatrabalho";
    public static final String CACHE_CHECKIN_ATENDIMENTO = "checkinatendimento";
    public static final String CACHE_HISTORICO_CLINICO = "historicoclinico";
    public static final String CACHE_INFRAESTRUTURA_ESTABELECIMENTO = "infraestruturaestabelecimento";
    public static final String CACHE_INTEGRACAO_GOV = "integracaogov";
    public static final String CACHE_LGPD_CONSENTIMENTO = "lgpdconsentimento";
    public static final String CACHE_MEDICAO_CLINICA = "medicaoclinica";
    public static final String CACHE_LOGS_AUDITORIA = "logsauditoria";

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

    public static String checkInAtendimento(UUID tenantId, UUID checkInAtendimentoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (checkInAtendimentoId == null) {
            throw new IllegalArgumentException("checkInAtendimentoId é obrigatório");
        }
        return String.format("checkInAtendimento_%s_%s", tenantId, checkInAtendimentoId);
    }

    public static String historicoClinico(UUID tenantId, UUID historicoClinicoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (historicoClinicoId == null) {
            throw new IllegalArgumentException("historicoClinicoId é obrigatório");
        }
        return String.format("historicoClinico_%s_%s", tenantId, historicoClinicoId);
    }



    public static String infraestruturaEstabelecimento(UUID tenantId, UUID infraestruturaId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (infraestruturaId == null) {
            throw new IllegalArgumentException("infraestruturaEstabelecimentoId é obrigatório");
        }
        return String.format("infraestruturaEstabelecimento_%s_%s", tenantId, infraestruturaId);
    }

    public static String integracaoGov(UUID tenantId, UUID integracaoGovId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (integracaoGovId == null) {
            throw new IllegalArgumentException("integracaoGovId é obrigatório");
        }
        return String.format("integracaoGov_%s_%s", tenantId, integracaoGovId);
    }

    public static String lgpdConsentimento(UUID tenantId, UUID lgpdConsentimentoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (lgpdConsentimentoId == null) {
            throw new IllegalArgumentException("lgpdConsentimentoId é obrigatório");
        }
        return String.format("lgpdConsentimento_%s_%s", tenantId, lgpdConsentimentoId);
    }

    public static String medicaoClinica(UUID tenantId, UUID medicaoClinicaId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (medicaoClinicaId == null) {
            throw new IllegalArgumentException("medicaoClinicaId é obrigatório");
        }
        return String.format("medicaoClinica_%s_%s", tenantId, medicaoClinicaId);
    }


    public static String logAuditoria(UUID tenantId, UUID logAuditoriaId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (logAuditoriaId == null) {
            throw new IllegalArgumentException("logAuditoriaId é obrigatório");
        }
        return String.format("logAuditoria_%s_%s", tenantId, logAuditoriaId);
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


    public static String exame(UUID tenantId, UUID exameId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (exameId == null) {
            throw new IllegalArgumentException("exameId é obrigatório");
        }
        return String.format("exame_%s_%s", tenantId, exameId);
    }

    public static String falta(UUID tenantId, UUID faltaId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (faltaId == null) {
            throw new IllegalArgumentException("faltaId é obrigatório");
        }
        return String.format("falta_%s_%s", tenantId, faltaId);
    }

    public static String filaEspera(UUID tenantId, UUID filaEsperaId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (filaEsperaId == null) {
            throw new IllegalArgumentException("filaEsperaId é obrigatório");
        }
        return String.format("filaEspera_%s_%s", tenantId, filaEsperaId);
    }

    public static String perfilUsuario(UUID tenantId, UUID perfilUsuarioId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (perfilUsuarioId == null) {
            throw new IllegalArgumentException("perfilUsuarioId é obrigatório");
        }
        return String.format("perfilUsuario_%s_%s", tenantId, perfilUsuarioId);
    }

    public static String permissao(UUID tenantId, UUID permissaoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (permissaoId == null) {
            throw new IllegalArgumentException("permissaoId é obrigatório");
        }
        return String.format("permissao_%s_%s", tenantId, permissaoId);
    }

    public static String plantao(UUID tenantId, UUID plantaoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (plantaoId == null) {
            throw new IllegalArgumentException("plantaoId é obrigatório");
        }
        return String.format("plantao_%s_%s", tenantId, plantaoId);
    }

    public static String procedimentoCirurgico(UUID tenantId, UUID procedimentoCirurgicoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (procedimentoCirurgicoId == null) {
            throw new IllegalArgumentException("procedimentoCirurgicoId é obrigatório");
        }
        return String.format("procedimentoCirurgico_%s_%s", tenantId, procedimentoCirurgicoId);
    }

    public static String procedimentoOdontologico(UUID tenantId, UUID procedimentoOdontologicoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (procedimentoOdontologicoId == null) {
            throw new IllegalArgumentException("procedimentoOdontologicoId é obrigatório");
        }
        return String.format("procedimentoOdontologico_%s_%s", tenantId, procedimentoOdontologicoId);
    }

    public static String receitaMedica(UUID tenantId, UUID receitaMedicaId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (receitaMedicaId == null) {
            throw new IllegalArgumentException("receitaMedicaId é obrigatório");
        }
        return String.format("receitaMedica_%s_%s", tenantId, receitaMedicaId);
    }

    public static String responsavelLegal(UUID tenantId, UUID responsavelLegalId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (responsavelLegalId == null) {
            throw new IllegalArgumentException("responsavelLegalId é obrigatório");
        }
        return String.format("responsavelLegal_%s_%s", tenantId, responsavelLegalId);
    }

    public static String servicoEstabelecimento(UUID tenantId, UUID servicoEstabelecimentoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (servicoEstabelecimentoId == null) {
            throw new IllegalArgumentException("servicoEstabelecimentoId é obrigatório");
        }
        return String.format("servicoEstabelecimento_%s_%s", tenantId, servicoEstabelecimentoId);
    }

    public static String templateNotificacao(UUID tenantId, UUID templateNotificacaoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (templateNotificacaoId == null) {
            throw new IllegalArgumentException("templateNotificacaoId é obrigatório");
        }
        return String.format("templateNotificacao_%s_%s", tenantId, templateNotificacaoId);
    }

    public static String notificacao(UUID tenantId, UUID notificacaoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (notificacaoId == null) {
            throw new IllegalArgumentException("notificacaoId é obrigatório");
        }
        return String.format("notificacao_%s_%s", tenantId, notificacaoId);
    }

    public static String configuracaoEstabelecimento(UUID tenantId, UUID configuracaoEstabelecimentoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (configuracaoEstabelecimentoId == null) {
            throw new IllegalArgumentException("configuracaoEstabelecimentoId é obrigatório");
        }
        return String.format("configuracaoEstabelecimento_%s_%s", tenantId, configuracaoEstabelecimentoId);
    }

    public static String tratamentoOdontologico(UUID tenantId, UUID tratamentoOdontologicoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (tratamentoOdontologicoId == null) {
            throw new IllegalArgumentException("tratamentoOdontologicoId é obrigatório");
        }
        return String.format("tratamentoOdontologico_%s_%s", tenantId, tratamentoOdontologicoId);
    }


    public static String conselhoProfissional(UUID conselhoProfissionalId) {
        if (conselhoProfissionalId == null) {
            throw new IllegalArgumentException("conselhoProfissionalId é obrigatório");
        }
        return String.format("conselhoProfissional_%s", conselhoProfissionalId);
    }

    public static String controlePonto(UUID tenantId, UUID controlePontoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (controlePontoId == null) {
            throw new IllegalArgumentException("controlePontoId é obrigatório");
        }
        return String.format("controlePonto_%s_%s", tenantId, controlePontoId);
    }

    public static String cuidadoEnfermagem(UUID tenantId, UUID cuidadoEnfermagemId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (cuidadoEnfermagemId == null) {
            throw new IllegalArgumentException("cuidadoEnfermagemId é obrigatório");
        }
        return String.format("cuidadoEnfermagem_%s_%s", tenantId, cuidadoEnfermagemId);
    }

    public static String dispensacaoMedicamento(UUID tenantId, UUID dispensacaoMedicamentoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (dispensacaoMedicamentoId == null) {
            throw new IllegalArgumentException("dispensacaoMedicamentoId é obrigatório");
        }
        return String.format("dispensacaoMedicamento_%s_%s", tenantId, dispensacaoMedicamentoId);
    }

    public static String equipamento(UUID tenantId, UUID equipamentoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (equipamentoId == null) {
            throw new IllegalArgumentException("equipamentoId é obrigatório");
        }
        return String.format("equipamento_%s_%s", tenantId, equipamentoId);
    }

    public static String equipamentoEstabelecimento(UUID tenantId, UUID equipamentoEstabelecimentoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (equipamentoEstabelecimentoId == null) {
            throw new IllegalArgumentException("equipamentoEstabelecimentoId é obrigatório");
        }
        return String.format("equipamentoEstabelecimento_%s_%s", tenantId, equipamentoEstabelecimentoId);
    }

    public static String escalaTrabalho(UUID tenantId, UUID escalaTrabalhoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (escalaTrabalhoId == null) {
            throw new IllegalArgumentException("escalaTrabalhoId é obrigatório");
        }
        return String.format("escalaTrabalho_%s_%s", tenantId, escalaTrabalhoId);
    }


    public static String cidade(UUID cidadeId) {
        if (cidadeId == null) {
            throw new IllegalArgumentException("cidadeId é obrigatório");
        }
        return String.format("cidade_%s", cidadeId);
    }

    public static String cirurgia(UUID tenantId, UUID cirurgiaId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (cirurgiaId == null) {
            throw new IllegalArgumentException("cirurgiaId é obrigatório");
        }
        return String.format("cirurgia_%s_%s", tenantId, cirurgiaId);
    }


    public static String estado(UUID estadoId) {
        if (estadoId == null) {
            throw new IllegalArgumentException("estadoId é obrigatório");
        }
        return String.format("estado_%s", estadoId);
    }

    public static String estabelecimento(UUID tenantId, UUID estabelecimentoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (estabelecimentoId == null) {
            throw new IllegalArgumentException("estabelecimentoId é obrigatório");
        }
        return String.format("estabelecimento_%s_%s", tenantId, estabelecimentoId);
    }

    public static String fabricanteEquipamento(UUID fabricanteEquipamentoId) {
        if (fabricanteEquipamentoId == null) {
            throw new IllegalArgumentException("fabricanteEquipamentoId é obrigatório");
        }
        return String.format("fabricanteEquipamento_%s", fabricanteEquipamentoId);
    }

    public static String fabricanteMedicamento(UUID fabricanteMedicamentoId) {
        if (fabricanteMedicamentoId == null) {
            throw new IllegalArgumentException("fabricanteMedicamentoId é obrigatório");
        }
        return String.format("fabricanteMedicamento_%s", fabricanteMedicamentoId);
    }


    public static String deficiencia(UUID deficienciaId) {
        if (deficienciaId == null) {
            throw new IllegalArgumentException("deficienciaId é obrigatório");
        }
        return String.format("deficiencia_%s", deficienciaId);
    }

    public static String departamento(UUID tenantId, UUID departamentoId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (departamentoId == null) {
            throw new IllegalArgumentException("departamentoId é obrigatório");
        }
        return String.format("departamento_%s_%s", tenantId, departamentoId);
    }


    public static String convenio(UUID tenantId, UUID convenioId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (convenioId == null) {
            throw new IllegalArgumentException("convenioId é obrigatório");
        }
        return String.format("convenio_%s_%s", tenantId, convenioId);
    }

    public static String dadosClinicosBasicos(UUID tenantId, UUID dadosClinicosBasicosId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (dadosClinicosBasicosId == null) {
            throw new IllegalArgumentException("dadosClinicosBasicosId é obrigatório");
        }
        return String.format("dadosClinicosBasicos_%s_%s", tenantId, dadosClinicosBasicosId);
    }

    public static String dadosSociodemograficos(UUID tenantId, UUID dadosSociodemograficosId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId é obrigatório");
        }
        if (dadosSociodemograficosId == null) {
            throw new IllegalArgumentException("dadosSociodemograficosId é obrigatório");
        }
        return String.format("dadosSociodemograficos_%s_%s", tenantId, dadosSociodemograficosId);
    }

}
