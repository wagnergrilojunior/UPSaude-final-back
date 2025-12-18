package com.upsaude.enums;

import com.upsaude.entity.clinica.atendimento.Atendimento;

import java.util.Arrays;
import java.util.Locale;

public enum TipoProfissionalEnum {
    ENFERMEIRO(1, "Enfermeiro"),
    TECNICO_ENFERMAGEM(2, "Técnico de Enfermagem"),
    AUXILIAR_ENFERMAGEM(3, "Auxiliar de Enfermagem"),
    FARMACEUTICO(4, "Farmacêutico"),
    FISIOTERAPEUTA(5, "Fisioterapeuta"),
    PSICOLOGO(6, "Psicólogo"),
    NUTRICIONISTA(7, "Nutricionista"),
    ODONTOLOGO(8, "Odontólogo"),
    FONOAUDIOLOGO(9, "Fonoaudiólogo"),
    TERAPEUTA_OCUPACIONAL(10, "Terapeuta Ocupacional"),
    BIOMEDICO(11, "Biomédico"),
    ANALISTA_LABORATORIAL(12, "Analista Laboratorial"),
    TECNICO_LABORATORIAL(13, "Técnico Laboratorial"),
    TECNOLOGISTA_MEDICO_LABORATORIAL(14, "Tecnologista Médico Laboratorial"),
    RADIOLOGISTA(15, "Radiologista"),
    TEC_LABORATORIAL_RADIOLOGIA(16, "Técnico Laboratorial de Radiologia"),
    TECNICO_RADIOLOGIA(17, "Técnico de Radiologia"),
    TERAPEUTA_FISICO(18, "Terapeuta Físico"),
    NUTRICIONISTA_CLINICO(19, "Nutricionista Clínico"),
    NUTRICIONISTA_DIETISTICA(20, "Nutricionista Dietética"),
    MEDICO_VETERINARIO(21, "Médico Veterinário"),
    MEDICO_CLINICO_GERAL(22, "Médico Clínico Geral"),
    MEDICO_PEDIATRA(23, "Médico Pediatra"),
    MEDICO_CIRURGIAO(24, "Médico Cirurgião"),
    MEDICO_CARDIOLOGISTA(25, "Médico Cardiologista"),
    MEDICO_GINECOLOGISTA(26, "Médico Ginecologista"),
    MEDICO_ORTOPEDISTA(27, "Médico Ortopedista"),
    MEDICO_NEUROLOGISTA(28, "Médico Neurologista"),
    MEDICO_ATENDIMENTO_PRONTO_SOCORRO(29, "Médico Atendimento Pronto Socorro"),
    ENFERMEIRO_CHEFE(30, "Enfermeiro Chefe"),
    ENFERMEIRO_PLANTONISTA(31, "Enfermeiro Plantonista"),
    TECNICO_FARMACIA(32, "Técnico de Farmácia"),
    BIOSTATISTICO(33, "Bioestatístico"),
    AGENTE_COMUNITARIO_SAUDE(34, "Agente Comunitário de Saúde"),
    AGENTE_SAUDE_PUBLICA(35, "Agente de Saúde Pública"),
    AGENTE_COMBATE_ENDEMIAS(36, "Agente de Combate a Endemias"),
    AUXILIAR_SAUDE_BUCAL(37, "Auxiliar de Saúde Bucal"),
    ODONTOLOGO_SAUDE_COLETIVA(38, "Odontólogo Saúde Coletiva"),
    ODONTOLOGO_CLINICO_GERAL(39, "Odontólogo Clínico Geral"),
    ODONTOLOGO_ORTODONTISTA(40, "Odontólogo Ortodontista"),
    ASSISTENTE_SOCIAL(41, "Assistente Social"),
    PSICOPEDAGOGO(42, "Psicopedagogo"),
    EDUCADOR_FISICO(43, "Educador Físico"),
    TERAPEUTA_MANUAL(44, "Terapeuta Manual"),
    FISIOTERAPEUTA_RESPIRATORIO(45, "Fisioterapeuta Respiratório"),
    FISIOTERAPEUTA_NEUROFUNCIONAL(46, "Fisioterapeuta Neurofuncional"),
    FISIOTERAPEUTA_ORTOPEDICO(47, "Fisioterapeuta Ortopédico"),
    ENFERMAGEM_DA_FAMILIA(48, "Enfermagem da Família"),
    ENFERMEIRO_ESF(49, "Enfermeiro ESF"),
    MEDICO_ESF(50, "Médico ESF"),
    COORDENADOR_SAUDE(51, "Coordenador de Saúde"),
    GESTOR_UNIDADE(52, "Gestor de Unidade"),
    ADMINISTRADOR_SISTEMA(53, "Administrador de Sistema"),
    RECEPCIONISTA(54, "Recepcionista"),
    ATENDENTE(55, "Atendente"),
    ATENDENTE_CLINICO(56, "Atendente Clínico"),
    AUXILIAR_ADMINISTRATIVO(57, "Auxiliar Administrativo"),
    CONTROLE_QUALIDADE(58, "Controle de Qualidade"),
    SERVICO_SOCORRO_EMERGENCIA(59, "Serviço de Socorro e Emergência"),
    TECNICO_EMERGENCIA(60, "Técnico de Emergência"),
    PARAMEDICO(61, "Paramédico"),
    FISIOTERAPEUTA_ESPORTIVO(62, "Fisioterapeuta Esportivo"),
    FISIOTERAPEUTA_TRABALHO(63, "Fisioterapeuta do Trabalho"),
    HIGIENISTA(64, "Higienista"),
    AUXILIAR_SERVICOS_GERAIS(65, "Auxiliar de Serviços Gerais"),
    FONOAUDIOLOGO_CLINICO(66, "Fonoaudiólogo Clínico"),
    TERAPEUTA_OCUPACIONAL_HOSPITALAR(67, "Terapeuta Ocupacional Hospitalar"),
    TERAPEUTA_OCUPACIONAL_CLINICO(68, "Terapeuta Ocupacional Clínico"),
    ANALISTA_SAUDE_PUBLICA(69, "Analista de Saúde Pública"),
    EPIDEMIOLOGISTA(70, "Epidemiologista"),
    BIOLOGO_SAUDE(71, "Biólogo de Saúde"),
    MICROBIOLOGO(72, "Microbiologista"),
    LABORATORIAL_CULTURA(73, "Laboratorial Cultura"),
    LABORATORIAL_ANALISES_MEDICAS(74, "Laboratorial Análises Médicas"),
    IMUNIZADOR(75, "Imunizador"),
    VACINADOR(76, "Vacinador"),
    PSICOLOGO_CLINICO(77, "Psicólogo Clínico"),
    PSICOLOGO_ESCOLAR(78, "Psicólogo Escolar"),
    NUTRICIONISTA_HOSPITALAR(79, "Nutricionista Hospitalar"),
    MEDICO_COORDENADOR(80, "Médico Coordenador"),
    ENFERMEIRO_COORDENADOR(81, "Enfermeiro Coordenador"),
    FARMACEUTICO_CLINICO(82, "Farmacêutico Clínico"),
    FARMACEUTICO_ATENDIMENTO(83, "Farmacêutico Atendimento"),
    FARMACEUTICO_INDUSTRIAL(84, "Farmacêutico Industrial"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoProfissionalEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoProfissionalEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoProfissionalEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
