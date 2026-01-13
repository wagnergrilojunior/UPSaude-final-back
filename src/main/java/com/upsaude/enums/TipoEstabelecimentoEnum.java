package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Tipo de Estabelecimento de Saúde conforme FHIR BR
 * (BRTipoEstabelecimentoSaude).
 * Descrições em português para consumo pelo frontend.
 */
public enum TipoEstabelecimentoEnum {
    // Valores FHIR BR (BRTipoEstabelecimentoSaude)
    HOSPITAL_GERAL(1, "Hospital Geral", "01", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    HOSPITAL_ESPECIALIZADO(2, "Hospital Especializado", "02",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    PRONTO_SOCORRO(3, "Pronto Socorro Geral", "05",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    UBS(4, "Unidade Básica de Saúde", "15", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    UPA(5, "Unidade de Pronto Atendimento", "39",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    CLINICA(6, "Clínica/Centro de Especialidade", "04",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    CONSULTORIO(7, "Consultório Isolado", "22",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    LABORATORIO(8, "Laboratório de Análises Clínicas", "21",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    FARMACIA(9, "Farmácia", "43", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    POLICLINICA(10, "Policlínica", "20", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    CAPS(11, "Centro de Atenção Psicossocial - CAPS", "70",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    SAMU(12, "Unidade Móvel de Urgência - SAMU", "42",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    CENTRO_SAUDE(13, "Centro de Saúde/Unidade Básica", "02",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    HOSPITAL_DIA(14, "Hospital Dia - Isolado", "36",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    CENTRAL_REGULACAO(15, "Central de Regulação de Serviços de Saúde", "72",
            "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),

    // Valores legados para compatibilidade com código existente
    HOSPITAL(100, "Hospital", "01", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    POSTO_SAUDE(101, "Posto de Saúde", "20", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    OUTRO(99, "Outros", "99", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude"),
    OUTROS(98, "Outros", "99", "http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoEstabelecimentoSaude");

    private final Integer codigo;
    private final String descricao;
    private final String codigoFhir;
    private final String systemFhir;

    TipoEstabelecimentoEnum(Integer codigo, String descricao, String codigoFhir, String systemFhir) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.codigoFhir = codigoFhir;
        this.systemFhir = systemFhir;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCodigoFhir() {
        return codigoFhir;
    }

    public String getSystemFhir() {
        return systemFhir;
    }

    public static TipoEstabelecimentoEnum fromCodigo(Integer codigo) {
        if (codigo == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigo.equals(codigo)).findFirst().orElse(null);
    }

    public static TipoEstabelecimentoEnum fromCodigoFhir(String codigoFhir) {
        if (codigoFhir == null)
            return null;
        return Arrays.stream(values()).filter(v -> v.codigoFhir.equals(codigoFhir)).findFirst().orElse(null);
    }

    public static TipoEstabelecimentoEnum fromDescricao(String descricao) {
        if (descricao == null)
            return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values()).filter(v -> v.descricao.toLowerCase(Locale.ROOT).contains(d)).findFirst()
                .orElse(null);
    }
}
