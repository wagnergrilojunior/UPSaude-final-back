package com.upsaude.integration.fhir.config;

/**
 * Catálogo centralizado de nomes/IDs de recursos FHIR do Ministério da Saúde.
 * 
 * Este catálogo garante consistência entre documentação e implementação,
 * evitando divergências que podem causar 404.
 * 
 * Referência: https://terminologia.saude.gov.br/fhir
 */
public final class FhirResourceNames {

    private FhirResourceNames() {
        throw new UnsupportedOperationException("Utility class");
    }

    // ============== VACINAÇÃO ==============
    
    /** BRImunobiologico - Catálogo de vacinas (~100 itens) */
    public static final String IMUNOBIOLOGICO = "BRImunobiologico";
    
    /** BRFabricantePNI - Fabricantes de vacinas (~100 itens) */
    public static final String FABRICANTE_PNI = "BRFabricantePNI";
    
    /** BRDose - Tipos de dose (~80 tipos) */
    public static final String DOSE = "BRDose";
    
    /** BRLocalAplicacao - Locais anatômicos (~22 itens) */
    public static final String LOCAL_APLICACAO = "BRLocalAplicacao";
    
    /** BRViaAdministracao - Vias de administração (~70 itens) */
    public static final String VIA_ADMINISTRACAO = "BRViaAdministracao";
    
    /** BREstrategiaVacinacao - Estratégias de vacinação (~13 itens) */
    public static final String ESTRATEGIA_VACINACAO = "BREstrategiaVacinacao";
    
    /** BRElegibilidadeImunobiologico - Elegibilidade CNI (2 itens) */
    public static final String ELEGIBILIDADE_IMUNOBIOLOGICO = "BRElegibilidadeImunobiologico";

    // ============== DIAGNÓSTICOS ==============
    
    /** BRCID10 - Classificação Internacional de Doenças - 10ª Revisão (~14.000 códigos) */
    public static final String CID10 = "BRCID10";
    
    /** BRCIAP2 - Classificação Internacional de Atenção Primária (~700 códigos) */
    public static final String CIAP2 = "BRCIAP2";
    
    /** BRCategoriaDiagnostico - Categoria do diagnóstico */
    public static final String CATEGORIA_DIAGNOSTICO = "BRCategoriaDiagnostico";

    // ============== PROCEDIMENTOS ==============
    
    /** BRTabelaSUS - Tabela SUS de procedimentos */
    public static final String TABELA_SUS = "BRTabelaSUS";
    
    /** BRSubgrupoTabelaSUS - Subgrupos da tabela SUS */
    public static final String SUBGRUPO_TABELA_SUS = "BRSubgrupoTabelaSUS";
    
    /** BRCBHPMTUSS - CBHPM e TUSS (convênios) */
    public static final String CBHPM_TUSS = "BRCBHPMTUSS";

    // ============== MEDICAMENTOS ==============
    
    /** BRMedicamento - Catálogo de medicamentos */
    public static final String MEDICAMENTO = "BRMedicamento";
    
    /** BRObmVMP - Produtos Medicinais Virtuais */
    public static final String OBM_VMP = "BRObmVMP";
    
    /** BRObmVTM - Princípios Ativos Virtuais */
    public static final String OBM_VTM = "BRObmVTM";
    
    /** BRObmANVISA - Registros ANVISA */
    public static final String OBM_ANVISA = "BRObmANVISA";
    
    /** BRObmCATMAT - Catálogo de Materiais */
    public static final String OBM_CATMAT = "BRObmCATMAT";
    
    /** BRObmEAN - Códigos EAN/Barcode */
    public static final String OBM_EAN = "BRObmEAN";
    
    /** BRUnidadeMedida - Unidades de medida */
    public static final String UNIDADE_MEDIDA = "BRUnidadeMedida";

    // ============== EXAMES ==============
    
    /** BRNomeExameLOINC - Exames LOINC */
    public static final String NOME_EXAME_LOINC = "BRNomeExameLOINC";
    
    /** BRNomeExameGAL - Exames do GAL */
    public static final String NOME_EXAME_GAL = "BRNomeExameGAL";
    
    /** BRTipoAmostraGAL - Tipos de amostra biológica */
    public static final String TIPO_AMOSTRA_GAL = "BRTipoAmostraGAL";

    // ============== PROFISSIONAIS ==============
    
    /** BRCBO - Classificação Brasileira de Ocupações */
    public static final String CBO = "BRCBO";
    
    /** BRConselhoProfissional - Conselhos de classe */
    public static final String CONSELHO_PROFISSIONAL = "BRConselhoProfissional";

    // ============== ALERGIAS ==============
    
    /** BRAlergenosCBARA - Catálogo de alérgenos (CodeSystem) */
    public static final String ALERGENOS_CBARA = "BRAlergenosCBARA";
    
    /** BRAlergenos - ValueSet de alérgenos (inclui BRAlergenosCBARA) */
    public static final String ALERGENOS = "BRAlergenos";
    
    /** BRMedDRA - Reações adversas (MedDRA) */
    public static final String MED_DRA = "BRMedDRA";
    
    /** BRReacoesAdversasMedDRA - ValueSet de reações adversas */
    public static final String REACOES_ADVERSAS_MED_DRA = "BRReacoesAdversasMedDRA";
    
    /** BRCriticidadeAlergiasReacoesAdversas - Criticidade */
    public static final String CRITICIDADE_ALERGIAS = "BRCriticidadeAlergiasReacoesAdversas";
    
    /** BRCategoriaAgenteAlergiasReacoesAdversas - Categoria do agente */
    public static final String CATEGORIA_AGENTE_ALERGIAS = "BRCategoriaAgenteAlergiasReacoesAdversas";

    // ============== GEOGRAFIA ==============
    
    /** BRDivisaoGeograficaBrasil - Divisão territorial (CodeSystem completo) */
    public static final String DIVISAO_GEOGRAFICA_BRASIL = "BRDivisaoGeograficaBrasil";
    
    /** BRDivisaoGeografica - Alias comum para BRDivisaoGeograficaBrasil */
    public static final String DIVISAO_GEOGRAFICA = "BRDivisaoGeografica";
    
    /** BRIBGE - Tabelas IBGE */
    public static final String IBGE = "BRIBGE";
    
    /** BRPais - Lista de países */
    public static final String PAIS = "BRPais";
    
    /** BRTipoLogradouro - Tipos de logradouro */
    public static final String TIPO_LOGRADOURO = "BRTipoLogradouro";

    // ============== DADOS DEMOGRÁFICOS ==============
    
    /** BRRacaCor - Raça/Cor */
    public static final String RACA_COR = "BRRacaCor";
    
    /** BREtniaIndigena - Etnia indígena */
    public static final String ETNIA_INDIGENA = "BREtniaIndigena";
    
    /** BRParentesco - Grau de parentesco */
    public static final String PARENTESCO = "BRParentesco";
    
    /** BRTipoDocumento - Tipos de documento */
    public static final String TIPO_DOCUMENTO = "BRTipoDocumento";
}
