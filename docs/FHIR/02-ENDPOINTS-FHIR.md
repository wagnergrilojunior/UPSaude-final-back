# 🔗 Endpoints FHIR Disponíveis

## URL Base

```
https://terminologia.saude.gov.br/fhir
```

---

## 1. Vacinação e Imunização

### CodeSystems (Sistemas de Código)

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRImunobiologico** | `/CodeSystem/BRImunobiologico` | Catálogo de vacinas (~100 itens) |
| **BRFabricantePNI** | `/CodeSystem/BRFabricantePNI` | Fabricantes de vacinas (~100 itens) |
| **BRDose** | `/CodeSystem/BRDose` | Tipos de dose (~80 tipos) |
| **BRLocalAplicacao** | `/CodeSystem/BRLocalAplicacao` | Locais anatômicos (~22 itens) |
| **BRViaAdministracao** | `/CodeSystem/BRViaAdministracao` | Vias de administração (~70 itens) |
| **BREstrategiaVacinacao** | `/CodeSystem/BREstrategiaVacinacao` | Estratégias de vacinação (~13 itens) |
| **BRElegibilidadeImunobiologico** | `/CodeSystem/BRElegibilidadeImunobiologico` | Elegibilidade CNI (2 itens) |

### ValueSets (Conjuntos de Valor)

| Recurso | URL |
|---------|-----|
| BRImunobiologico | `/ValueSet/BRImunobiologico` |
| BRFabricanteImunobiologico | `/ValueSet/BRFabricanteImunobiologico` |
| BRDose | `/ValueSet/BRDose` |
| BRLocalAplicacao | `/ValueSet/BRLocalAplicacao` |
| BRViaAdministracao | `/ValueSet/BRViaAdministracao` |
| BREstrategiaVacinacao | `/ValueSet/BREstrategiaVacinacao` |
| BRElegibilidadeImunobiologico | `/ValueSet/BRElegibilidadeImunobiologico` |

### Exemplos de Códigos de Vacinas

| Código | Nome |
|--------|------|
| 85 | COVID-19 SINOVAC/BUTANTAN - CORONAVAC |
| 86 | COVID-19 ASTRAZENECA/FIOCRUZ - COVISHIELD |
| 87 | COVID-19 PFIZER - COMIRNATY |
| 88 | COVID-19 JANSSEN - Ad26.COV2.S |
| 42 | PENTA |
| 15 | BCG |
| 14 | VFA (Febre Amarela) |
| 5 | DT |
| 1 | IGHT |

### Exemplos de Doses

| Código | Descrição |
|--------|-----------|
| 1 | 1ª Dose |
| 2 | 2ª Dose |
| 3 | 3ª Dose |
| 6 | 1º Reforço |
| 7 | 2º Reforço |
| 9 | Única |
| 10 | Revacinação |
| 37 | Dose Adicional |

---

## 2. Diagnósticos e Doenças

### CodeSystems

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRCID10** | `/CodeSystem/BRCID10` | Classificação Internacional de Doenças - 10ª Revisão |
| **BRCIAP2** | `/CodeSystem/BRCIAP2` | Classificação Internacional de Atenção Primária |
| **BRCategoriaDiagnostico** | `/CodeSystem/BRCategoriaDiagnostico` | Categoria do diagnóstico |

### ValueSets

| Recurso | URL | Descrição |
|---------|-----|-----------|
| BRCID10 | `/ValueSet/BRCID10` | CID-10 completo |
| BRCIAP2 | `/ValueSet/BRCIAP2` | CIAP-2 completo |
| BRProblemaDiagnostico | `/ValueSet/BRProblemaDiagnostico` | Problemas e diagnósticos |
| BRTerminologiaSuspeitaDiagnostica | `/ValueSet/BRTerminologiaSuspeitaDiagnostica` | Suspeitas diagnósticas |
| BRCategoriaDiagnostico | `/ValueSet/BRCategoriaDiagnostico` | Categorias |

---

## 3. Procedimentos

### CodeSystems

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRTabelaSUS** | `/CodeSystem/BRTabelaSUS` | Tabela SUS de procedimentos |
| **BRSubgrupoTabelaSUS** | `/CodeSystem/BRSubgrupoTabelaSUS` | Subgrupos da tabela SUS |
| **BRCBHPMTUSS** | `/CodeSystem/BRCBHPMTUSS` | CBHPM e TUSS (convênios) |
| **BRDesfechoProcedimento** | `/CodeSystem/BRDesfechoProcedimento` | Desfechos |
| **BRMotivoProcedimentoNaoRealizado** | `/CodeSystem/BRMotivoProcedimentoNaoRealizado` | Motivos de não realização |

### ValueSets

| Recurso | URL |
|---------|-----|
| BRProcedimentosNacionais | `/ValueSet/BRProcedimentosNacionais` |
| BRSubgrupoTabelaSUS | `/ValueSet/BRSubgrupoTabelaSUS` |
| BRDesfechoProcedimento | `/ValueSet/BRDesfechoProcedimento` |
| BRMotivoProcedimentoNaoRealizado | `/ValueSet/BRMotivoProcedimentoNaoRealizado` |
| BRCategoriaProcedimento | `/ValueSet/BRCategoriaProcedimento` |

---

## 4. Medicamentos

### CodeSystems

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRMedicamento** | `/CodeSystem/BRMedicamento` | Catálogo de medicamentos |
| **BRObmVMP** | `/CodeSystem/BRObmVMP` | Produtos Medicinais Virtuais |
| **BRObmVTM** | `/CodeSystem/BRObmVtm` | Princípios Ativos Virtuais |
| **BRObmANVISA** | `/CodeSystem/BRObmANVISA` | Registros ANVISA |
| **BRObmCATMAT** | `/CodeSystem/BRObmCATMAT` | Catálogo de Materiais |
| **BRObmEAN** | `/CodeSystem/BRObmEAN` | Códigos EAN/Barcode |
| **BRUnidadeMedida** | `/CodeSystem/BRUnidadeMedida` | Unidades de medida |

### ValueSets

| Recurso | URL |
|---------|-----|
| BRTerminologiaMedicamento | `/ValueSet/BRTerminologiaMedicamento` |
| BRViaAdministracao | `/ValueSet/BRViaAdministracao` |
| BRUnidadeMedidaMedicamento | `/ValueSet/BRUnidadeMedidaMedicamento` |

---

## 5. Exames Laboratoriais

### CodeSystems

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRNomeExameLOINC** | `/CodeSystem/BRNomeExameLOINC` | Exames LOINC |
| **BRNomeExameGAL** | `/CodeSystem/BRNomeExameGAL` | Exames do GAL |
| **BRTipoAmostraGAL** | `/CodeSystem/BRTipoAmostraGAL` | Tipos de amostra biológica |
| **BRResultadoQualitativoExame** | `/CodeSystem/BRResultadoQualitativoExame` | Resultados qualitativos |

### ValueSets

| Recurso | URL |
|---------|-----|
| BRNomeExame | `/ValueSet/BRNomeExame` |
| BRTipoAmostra | `/ValueSet/BRTipoAmostra` |
| BRResultadoQualitativoExame | `/ValueSet/BRResultadoQualitativoExame` |
| BRCategoriaExame | `/ValueSet/BRCategoriaExame` |

---

## 6. Profissionais de Saúde

### CodeSystems

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRCBO** | `/CodeSystem/BRCBO` | Classificação Brasileira de Ocupações |
| **BRConselhoProfissional** | `/CodeSystem/BRConselhoProfissional` | Conselhos de classe |
| **BRResponsabilidadeParticipante** | `/CodeSystem/BRResponsabilidadeParticipante` | Papel do profissional |
| **BRTipoParticipante** | `/CodeSystem/BRTipoParticipante` | Tipo de participante |

### ValueSets

| Recurso | URL |
|---------|-----|
| BROcupacao | `/ValueSet/BROcupacao` |
| BRResponsabilidadeParticipante | `/ValueSet/BRResponsabilidadeParticipante` |
| BRTipoParticipante | `/ValueSet/BRTipoParticipante` |
| BRCRM | `/ValueSet/BRCRM` |
| BRCRO | `/ValueSet/BRCRO` |
| BRCOREN | `/ValueSet/BRCOREN` |
| BRCRF | `/ValueSet/BRCRF` |

---

## 7. Alergias e Reações Adversas

### CodeSystems

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRAlergenosCBARA** | `/CodeSystem/BRAlergenosCBARA` | Catálogo de alérgenos |
| **BRMedDRA** | `/CodeSystem/BRMedDRA` | Reações adversas (MedDRA) |

### ValueSets

| Recurso | URL | Descrição |
|---------|-----|-----------|
| BRAlergenos | `/ValueSet/BRAlergenos` | Alérgenos |
| BRReacoesAdversasMedDRA | `/ValueSet/BRReacoesAdversasMedDRA` | Reações adversas |
| BRCriticidadeAlergiasReacoesAdversas | `/ValueSet/BRCriticidadeAlergiasReacoesAdversas` | Criticidade |
| BRCategoriaAgenteAlergiasReacoesAdversas | `/ValueSet/BRCategoriaAgenteAlergiasReacoesAdversas` | Categoria do agente |
| BRGrauCertezaAlergiasReacoesAdversas | `/ValueSet/BRGrauCertezaAlergiasReacoesAdversas` | Grau de certeza |

### Exemplos de Reações (MedDRA)

| Código | Nome |
|--------|------|
| 10002198 | Anafilaxia |
| 10002424 | Angioedema |
| 10046735 | Urticária |
| 10037087 | Prurido |
| 10006482 | Broncoespasmo |
| 10013968 | Dispnéia |

---

## 8. Dados Demográficos do Paciente

### CodeSystems

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRRacaCor** | `/CodeSystem/BRRacaCor` | Raça/Cor |
| **BREtniaIndigena** | `/CodeSystem/BREtniaIndigena` | Etnia indígena |
| **BRPopulacaoTradicional** | `/CodeSystem/BRPopulacaoTradicional` | Populações tradicionais |
| **BRParentesco** | `/CodeSystem/BRParentesco` | Grau de parentesco |
| **BRTipoDocumento** | `/CodeSystem/BRTipoDocumento` | Tipos de documento |
| **BROrgaoExpedidor** | `/CodeSystem/BROrgaoExpedidor` | Órgãos expedidores |
| **BRCondicaoMaternal** | `/CodeSystem/BRCondicaoMaternal` | Condição maternal |
| **BRTipoAleitamentoMaterno** | `/CodeSystem/BRTipoAleitamentoMaterno` | Aleitamento materno |

### ValueSets

| Recurso | URL |
|---------|-----|
| BRRacaCor | `/ValueSet/BRRacaCor` |
| BRSexo | `/ValueSet/BRSexo` |
| BRSexoNascimento | `/ValueSet/BRSexoNascimento` |
| BREstadoCivil | `/ValueSet/BREstadoCivil` |
| BRIdentidadeGenero | `/ValueSet/BRIdentidadeGenero` |
| BREtniaIndigena | `/ValueSet/BREtniaIndigena` |
| BRParentesco | `/ValueSet/BRParentesco` |
| BRTipoDocumentoIndividuo | `/ValueSet/BRTipoDocumentoIndividuo` |
| BROrgaoExpedidor | `/ValueSet/BROrgaoExpedidor` |
| BRCondicaoMaternal | `/ValueSet/BRCondicaoMaternal` |
| BRTipoAleitamentoMaterno | `/ValueSet/BRTipoAleitamentoMaterno` |

---

## 9. Estabelecimentos e Atendimento

### CodeSystems

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRTipoEstabelecimentoSaude** | `/CodeSystem/BRTipoEstabelecimentoSaude` | Tipos de estabelecimento |
| **BRInstalacoesFisicas** | `/CodeSystem/BRInstalacoesFisicas` | Instalações físicas |
| **BRModalidadeAssistencial** | `/CodeSystem/BRModalidadeAssistencial` | Modalidade assistencial |
| **BRModalidadeFinanceira** | `/CodeSystem/BRModalidadeFinanceira` | Modalidade financeira |
| **BRCaraterAtendimento** | `/CodeSystem/BRCaraterAtendimento` | Caráter do atendimento |
| **BRAtendimentoPrestado** | `/CodeSystem/BRAtendimentoPrestado` | Tipo de atendimento |
| **BRServicoEspecializado** | `/CodeSystem/BRServicoEspecializado` | Serviços especializados |
| **BRProcedencia** | `/CodeSystem/BRProcedencia` | Procedência do paciente |

### ValueSets

| Recurso | URL |
|---------|-----|
| BRTipoEstabelecimentoSaude | `/ValueSet/BRTipoEstabelecimentoSaude` |
| BRInstalacoesFisicas | `/ValueSet/BRInstalacoesFisicas` |
| BRModalidadeAssistencial | `/ValueSet/BRModalidadeAssistencial` |
| BRModalidadeAssistencialMIRA | `/ValueSet/BRModalidadeAssistencialMIRA` |
| BRModalidadeFinanceira | `/ValueSet/BRModalidadeFinanceira` |
| BRCaraterAtendimento | `/ValueSet/BRCaraterAtendimento` |
| BRCaraterAtendimentoMIRA | `/ValueSet/BRCaraterAtendimentoMIRA` |
| BRAtendimentoPrestado | `/ValueSet/BRAtendimentoPrestado` |
| BRServicoEspecializado | `/ValueSet/BRServicoEspecializado` |
| BRProcedencia | `/ValueSet/BRProcedencia` |

---

## 10. Dados Geográficos

### CodeSystems

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRDivisaoGeograficaBrasil** | `/CodeSystem/BRDivisaoGeograficaBrasil` | Divisão territorial |
| **BRIBGE** | `/CodeSystem/BRIBGE` | Tabelas IBGE |
| **BRPais** | `/CodeSystem/BRPais` | Lista de países |
| **BRTipoLogradouro** | `/CodeSystem/BRTipoLogradouro` | Tipos de logradouro |

### ValueSets

| Recurso | URL |
|---------|-----|
| BRMunicipio | `/ValueSet/BRMunicipio` |
| BRUnidadeFederativa | `/ValueSet/BRUnidadeFederativa` |
| BRPais | `/ValueSet/BRPais` |
| BRTipoLogradouro | `/ValueSet/BRTipoLogradouro` |

---

## 11. Identificadores Nacionais (NamingSystem)

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **CNS** | `/NamingSystem/cns` | Cartão Nacional de Saúde |
| **CNES** | `/NamingSystem/cnes` | Cadastro Nacional de Estabelecimentos |
| **CPF** | `/NamingSystem/cpf` | Cadastro de Pessoa Física |
| **CNPJ** | `/NamingSystem/cnpj` | Cadastro Nacional de Pessoa Jurídica |
| **RNE** | `/NamingSystem/rne` | Registro Nacional de Estrangeiro |

### Conselhos Regionais

Todos os conselhos regionais têm NamingSystem próprio:

- CRM (por estado): `/NamingSystem/crm-{uf}`
- CRO (por estado): `/NamingSystem/cro-{uf}`
- COREN (por estado): `/NamingSystem/coren-{uf}`
- CRF (por estado): `/NamingSystem/crf-{uf}`
- CRN (por região): `/NamingSystem/crn-{região}`
- CRP (por estado): `/NamingSystem/crp-{uf}`
- CREFITO (por região): `/NamingSystem/crefito-{região}`
- CREFONO (por região): `/NamingSystem/crefono-{região}`

---

## 12. Outros Recursos

### CodeSystems

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRProgramaSaude** | `/CodeSystem/BRProgramaSaude` | Programas de saúde |
| **BRTerminologiaPatogeno** | `/CodeSystem/BRTerminologiaPatogeno` | Patógenos |
| **BROrtesesProtesesImplantaveis** | `/CodeSystem/BROrtesesProtesesImplantaveis` | OPM |
| **BREstadoEvento** | `/CodeSystem/BREstadoEvento` | Estado do evento |
| **BREstadoObservacao** | `/CodeSystem/BREstadoObservacao` | Estado de observação |
| **BRRegistroOrigem** | `/CodeSystem/BRRegistroOrigem` | Origem do registro |
| **BRDadoAusenteOuDesconhecido** | `/CodeSystem/BRDadoAusenteOuDesconhecido` | Dados ausentes (IPS) |

### ValueSets

| Recurso | URL |
|---------|-----|
| BRProgramaSaude | `/ValueSet/BRProgramaSaude` |
| BRTerminologiaPatogeno | `/ValueSet/BRTerminologiaPatogeno` |
| BREstadoEvento | `/ValueSet/BREstadoEvento` |
| BREstadoObservacao | `/ValueSet/BREstadoObservacao` |
| BRRegistroOrigem | `/ValueSet/BRRegistroOrigem` |
| BRSinaisVitais | `/ValueSet/BRSinaisVitais` |

---

## 13. Como Acessar os Recursos

### Formato JSON (Recomendado)
```bash
# CodeSystem
curl https://terminologia.saude.gov.br/fhir/CodeSystem-BRImunobiologico.json

# ValueSet
curl https://terminologia.saude.gov.br/fhir/ValueSet-BRImunobiologico.json

# NamingSystem
curl https://terminologia.saude.gov.br/fhir/NamingSystem-cns.json
```

### Formato XML
```bash
curl https://terminologia.saude.gov.br/fhir/CodeSystem-BRImunobiologico.xml
```

### Headers Recomendados
```http
Accept: application/fhir+json
Content-Type: application/fhir+json
```

---

## 14. Constantes Java Sugeridas

```java
public final class FhirEndpoints {
    
    public static final String BASE_URL = "https://terminologia.saude.gov.br/fhir";
    
    // Formatos
    public static final String FORMAT_JSON = ".json";
    public static final String FORMAT_XML = ".xml";
    
    // === VACINAÇÃO ===
    public static final String CS_IMUNOBIOLOGICO = BASE_URL + "/CodeSystem/BRImunobiologico";
    public static final String CS_FABRICANTE = BASE_URL + "/CodeSystem/BRFabricantePNI";
    public static final String CS_DOSE = BASE_URL + "/CodeSystem/BRDose";
    public static final String CS_LOCAL_APLICACAO = BASE_URL + "/CodeSystem/BRLocalAplicacao";
    public static final String CS_VIA_ADMINISTRACAO = BASE_URL + "/CodeSystem/BRViaAdministracao";
    public static final String CS_ESTRATEGIA = BASE_URL + "/CodeSystem/BREstrategiaVacinacao";
    
    // === DIAGNÓSTICOS ===
    public static final String CS_CID10 = BASE_URL + "/CodeSystem/BRCID10";
    public static final String CS_CIAP2 = BASE_URL + "/CodeSystem/BRCIAP2";
    
    // === PROCEDIMENTOS ===
    public static final String CS_TABELA_SUS = BASE_URL + "/CodeSystem/BRTabelaSUS";
    public static final String CS_CBHPM_TUSS = BASE_URL + "/CodeSystem/BRCBHPMTUSS";
    
    // === MEDICAMENTOS ===
    public static final String CS_MEDICAMENTO = BASE_URL + "/CodeSystem/BRMedicamento";
    
    // === PROFISSIONAIS ===
    public static final String CS_CBO = BASE_URL + "/CodeSystem/BRCBO";
    public static final String CS_CONSELHO = BASE_URL + "/CodeSystem/BRConselhoProfissional";
    
    // === ALERGIAS ===
    public static final String CS_ALERGENOS = BASE_URL + "/CodeSystem/BRAlergenosCBARA";
    public static final String CS_REACOES_ADVERSAS = BASE_URL + "/CodeSystem/BRMedDRA";
    
    private FhirEndpoints() {}
}
```
