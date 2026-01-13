# 📋 Integração FHIR - Dados de Referência

## 1. Visão Geral

Este documento lista os dados de referência disponíveis no FHIR BR que podem ser sincronizados para o sistema.

---

## 2. Dados Geográficos

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRDivisaoGeograficaBrasil** | `/CodeSystem/BRDivisaoGeograficaBrasil` | Divisão territorial |
| **BRIBGE** | `/CodeSystem/BRIBGE` | Tabelas IBGE |
| **BRPais** | `/CodeSystem/BRPais` | Lista de países |
| **BRTipoLogradouro** | `/CodeSystem/BRTipoLogradouro` | Tipos de logradouro |
| **BRMunicipio** | `/ValueSet/BRMunicipio` | Municípios |
| **BRUnidadeFederativa** | `/ValueSet/BRUnidadeFederativa` | Estados (UFs) |

---

## 3. Dados de Paciente

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

---

## 4. Dados de Estabelecimento

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRTipoEstabelecimentoSaude** | `/CodeSystem/BRTipoEstabelecimentoSaude` | Tipos de estabelecimento |
| **BRInstalacoesFisicas** | `/CodeSystem/BRInstalacoesFisicas` | Instalações físicas |
| **BRModalidadeAssistencial** | `/CodeSystem/BRModalidadeAssistencial` | Modalidade assistencial |
| **BRModalidadeFinanceira** | `/CodeSystem/BRModalidadeFinanceira` | Modalidade financeira |
| **BRServicoEspecializado** | `/CodeSystem/BRServicoEspecializado` | Serviços especializados |

---

## 5. Dados de Atendimento

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRCaraterAtendimento** | `/CodeSystem/BRCaraterAtendimento` | Caráter (eletivo, urgência) |
| **BRAtendimentoPrestado** | `/CodeSystem/BRAtendimentoPrestado` | Tipo de atendimento |
| **BRProcedencia** | `/CodeSystem/BRProcedencia` | Procedência do paciente |

---

## 6. Identificadores Nacionais (NamingSystem)

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **CNS** | `/NamingSystem/cns` | Cartão Nacional de Saúde |
| **CNES** | `/NamingSystem/cnes` | Estabelecimentos de Saúde |
| **CPF** | `/NamingSystem/cpf` | Pessoa Física |
| **CNPJ** | `/NamingSystem/cnpj` | Pessoa Jurídica |
| **RNE** | `/NamingSystem/rne` | Estrangeiro |

---

## 7. Outros Recursos

| Recurso | URL | Descrição |
|---------|-----|-----------|
| **BRProgramaSaude** | `/CodeSystem/BRProgramaSaude` | Programas de saúde |
| **BRTerminologiaPatogeno** | `/CodeSystem/BRTerminologiaPatogeno` | Patógenos |
| **BROrtesesProtesesImplantaveis** | `/CodeSystem/BROrtesesProtesesImplantaveis` | OPM |
| **BREstadoEvento** | `/CodeSystem/BREstadoEvento` | Estado do evento |
| **BRRegistroOrigem** | `/CodeSystem/BRRegistroOrigem` | Origem do registro |

---

## 8. Prioridade de Implementação

| Prioridade | Recursos | Justificativa |
|------------|----------|---------------|
| 🔴 **Alta** | Raça/Cor, UF, Município, Tipos Doc | Cadastro de pacientes |
| 🟠 **Média** | Tipo Estabelecimento, Modalidade | Já usa dados CNES |
| 🟢 **Baixa** | Patógenos, OPM, Programas | Módulos específicos |

---

## 9. Observações

- Muitos desses dados já podem existir no sistema via IBGE ou CNES
- Avaliar antes de sincronizar para evitar duplicação
- Usar como fonte complementar ou validação
