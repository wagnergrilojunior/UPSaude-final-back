# 📋 Documentação Completa - Campos do Paciente

> **Objetivo:** Este documento detalha todos os campos disponíveis da entidade `Paciente` para uso no front-end.
> 
> **Público-alvo:** Desenvolvedores front-end e analistas de sistemas.

---

## 📑 Índice Rápido

1. [Campos de Sistema](#-1-campos-de-sistema)
2. [Dados Pessoais Básicos](#-2-dados-pessoais-básicos)
3. [Documentos e Identificação](#-3-documentos-e-identificação)
4. [Dados de Contato](#-4-dados-de-contato)
5. [Dados de Filiação](#-5-dados-de-filiação)
6. [Dados Sociodemográficos Básicos](#-6-dados-sociodemográficos-básicos)
7. [Dados de Saúde e Status](#-7-dados-de-saúde-e-status)
8. [Dados de Convênio](#-8-dados-de-convênio)
9. [Dados de Óbito](#-9-dados-de-óbito)
10. [Dados de Sistema SUS](#-10-dados-de-sistema-sus)
11. [Observações Gerais](#-11-observações-gerais)
12. [Relacionamentos OneToOne](#-relacionamentos-onetoone-1-para-1)
13. [Relacionamentos OneToMany](#-relacionamentos-onetomany-1-para-muitos)
14. [Lista Completa de Enums](#-lista-completa-de-enums)
15. [Exemplos Práticos](#-exemplos-práticos-de-código)

---

## 🔧 1. Campos de Sistema

**Descrição:** Campos técnicos do sistema, gerenciados automaticamente.

| Campo | Tipo | Obrigatório | Descrição | Exemplo |
|-------|------|-------------|-----------|---------|
| `id` | UUID | ✅ Sim | Identificador único do paciente | "550e8400-e29b-41d4-a716-446655440000" |
| `createdAt` | OffsetDateTime | ✅ Sim | Data/hora de criação do cadastro | "2024-12-04T10:30:00-03:00" |
| `updatedAt` | OffsetDateTime | ✅ Sim | Data/hora da última atualização | "2024-12-04T15:45:00-03:00" |
| `active` | Boolean | ✅ Sim | Indica se o cadastro está ativo | true ou false |

### 📝 Observações
- **id**: Gerado automaticamente pelo sistema
- **createdAt/updatedAt**: Gerenciados automaticamente
- **active**: Usado para exclusão lógica (não exclui fisicamente do banco)

---

## 👤 2. Dados Pessoais Básicos

**Descrição:** Informações básicas de identificação do paciente.

| Campo | Tipo | Obrigatório | Tamanho | Descrição | Exemplo |
|-------|------|-------------|---------|-----------|---------|
| `nomeCompleto` | String | ✅ Sim | 255 | Nome completo do paciente | "João da Silva Santos" |
| `nomeSocial` | String | ❌ Não | 255 | Nome social (se diferente do nome civil) | "Maria Silva" |
| `dataNascimento` | LocalDate | ❌ Não | - | Data de nascimento | "1990-05-15" |
| `sexo` | SexoEnum | ❌ Não | - | Sexo biológico | MASCULINO, FEMININO, INTERSEXO |
| `identidadeGenero` | IdentidadeGeneroEnum | ❌ Não | - | Identidade de gênero | CISGÊNERO, TRANSGENERO, NAO_BINARIO, etc |
| `orientacaoSexual` | OrientacaoSexualEnum | ❌ Não | - | Orientação sexual | HETEROSSEXUAL, HOMOSSEXUAL, BISSEXUAL, etc |
| `estadoCivil` | EstadoCivilEnum | ❌ Não | - | Estado civil | SOLTEIRO, CASADO, DIVORCIADO, etc |

### 📝 Observações
- **nomeCompleto**: Sempre use este campo como nome principal
- **nomeSocial**: Se preenchido, deve ser usado preferencialmente na interface
- **dataNascimento**: Formato ISO 8601 (YYYY-MM-DD)

---

## 🆔 3. Documentos e Identificação

**Descrição:** Documentos oficiais de identificação.

| Campo | Tipo | Obrigatório | Tamanho | Formato | Descrição | Exemplo |
|-------|------|-------------|---------|---------|-----------|---------|
| `cpf` | String | ❌ Não | 11 | Somente números | CPF do paciente (único no sistema) | "12345678901" |
| `rg` | String | ❌ Não | 20 | Livre | RG ou documento de identidade | "MG-12.345.678" |
| `cns` | String | ❌ Não | 15 | 15 dígitos | Cartão Nacional de Saúde (único no sistema) | "123456789012345" |
| `cnsValidado` | Boolean | ✅ Sim | - | - | Se o CNS foi validado pelo sistema | true ou false |
| `tipoCns` | TipoCnsEnum | ❌ Não | - | - | Tipo do CNS | DEFINITIVO ou PROVISORIO |
| `cartaoSusAtivo` | Boolean | ✅ Sim | - | - | Se o CNS está ativo no SUS | true ou false |
| `dataAtualizacaoCns` | LocalDate | ❌ Não | - | YYYY-MM-DD | Data da última atualização do CNS | "2024-12-04" |

### 📝 Observações
- **CPF**: Deve ser validado e único no sistema
- **CNS**: Cartão Nacional de Saúde - documento do SUS
- **cnsValidado**: true = validado, false = não validado
- **tipoCns**: DEFINITIVO é permanente, PROVISORIO é temporário

---

## 📞 4. Dados de Contato

**Descrição:** Informações para contato com o paciente.

| Campo | Tipo | Obrigatório | Tamanho | Descrição | Exemplo |
|-------|------|-------------|---------|-----------|---------|
| `telefone` | String | ❌ Não | 20 | Telefone principal (com DDD) | "(31) 98765-4321" |
| `email` | String | ❌ Não | 100 | E-mail do paciente (único no sistema) | "joao.silva@email.com" |

### 📝 Observações
- **telefone**: Formato livre, pode incluir DDD e código do país
- **email**: Deve ser único no sistema e validado
- ⚠️ **IMPORTANTE**: Sempre verificar consentimento LGPD antes de usar para contato!

---

## 👨‍👩‍👧 5. Dados de Filiação

**Descrição:** Informações sobre os pais e responsável legal.

| Campo | Tipo | Obrigatório | Tamanho | Descrição | Exemplo |
|-------|------|-------------|---------|-----------|---------|
| `nomeMae` | String | ❌ Não | 100 | Nome completo da mãe | "Maria Santos Silva" |
| `nomePai` | String | ❌ Não | 100 | Nome completo do pai | "José da Silva" |
| `responsavelNome` | String | ❌ Não | 255 | Nome do responsável legal | "Ana Silva Santos" |
| `responsavelCpf` | String | ❌ Não | 11 | CPF do responsável (somente números) | "98765432100" |
| `responsavelTelefone` | String | ❌ Não | 20 | Telefone do responsável (10 ou 11 dígitos) | "31987654321" |

### 📝 Observações
- Campos `responsavel*` são para dados resumidos
- Para dados completos do responsável, consulte o relacionamento `responsavelLegal`
- Responsável legal é obrigatório para menores de 18 anos

---

## 🌍 6. Dados Sociodemográficos Básicos

**Descrição:** Informações sociais e demográficas básicas.

| Campo | Tipo | Obrigatório | Tamanho | Descrição | Valores Possíveis |
|-------|------|-------------|---------|-----------|-------------------|
| `racaCor` | RacaCorEnum | ❌ Não | - | Raça/Cor conforme IBGE | BRANCA, PRETA, PARDA, AMARELA, INDIGENA |
| `nacionalidade` | NacionalidadeEnum | ❌ Não | - | Nacionalidade | BRASILEIRO, ESTRANGEIRO, NATURALIZADO |
| `paisNascimento` | String | ❌ Não | 100 | País de nascimento | "Brasil" |
| `naturalidade` | String | ❌ Não | 100 | Cidade de nascimento | "Belo Horizonte" |
| `municipioNascimentoIbge` | String | ❌ Não | 7 | Código IBGE do município | "3106200" |
| `escolaridade` | EscolaridadeEnum | ❌ Não | - | Nível de escolaridade | Ver tabela de Enums |
| `ocupacaoProfissao` | String | ❌ Não | 150 | Profissão ou ocupação atual | "Engenheiro Civil" |

### 📝 Observações
- **racaCor**: Classificação do IBGE para políticas de equidade
- **municipioNascimentoIbge**: Código de 7 dígitos do IBGE
- Para dados sociodemográficos completos, consulte `dadosSociodemograficos`

---

## 🏥 7. Dados de Saúde e Status

**Descrição:** Informações sobre status de saúde e situação do paciente.

| Campo | Tipo | Obrigatório | Descrição | Valores/Formato |
|-------|------|-------------|-----------|-----------------|
| `statusPaciente` | StatusPacienteEnum | ✅ Sim | Status atual do paciente | ATIVO (padrão), INATIVO, OBITO |
| `situacaoRua` | Boolean | ✅ Sim | Se está em situação de rua | true ou false (padrão: false) |
| `possuiDeficiencia` | Boolean | ✅ Sim | Se possui alguma deficiência | true ou false (padrão: false) |
| `tipoDeficiencia` | String | ❌ Não | Descrição da deficiência | Texto livre (máx. 255 caracteres) |
| `tipoAtendimentoPreferencial` | TipoAtendimentoPreferencialEnum | ❌ Não | Tipo de atendimento prioritário | IDOSO, GESTANTE, LACTANTE, DEFICIENTE, OBESO |
| `acompanhadoPorEquipeEsf` | Boolean | ✅ Sim | Se é acompanhado pela ESF | true ou false (padrão: false) |

### 📝 Observações
- **statusPaciente**: 
  - ATIVO = paciente em atendimento normal
  - INATIVO = cadastro inativo (não exclui do sistema)
  - OBITO = paciente falecido
- **situacaoRua**: Importante para políticas públicas de atenção básica
- **possuiDeficiencia**: Se true, consulte a lista `deficiencias` para detalhes
- **ESF**: Estratégia de Saúde da Família (atenção básica)

---

## 🏥 8. Dados de Convênio

**Descrição:** Informações sobre plano de saúde/convênio.

| Campo | Tipo | Obrigatório | Tamanho | Descrição | Exemplo |
|-------|------|-------------|---------|-----------|---------|
| `convenio` | ConvenioResponse | ❌ Não | - | Objeto completo do convênio | Ver estrutura do objeto |
| `numeroCarteirinha` | String | ❌ Não | 50 | Número da carteirinha do convênio | "123456789" |
| `dataValidadeCarteirinha` | LocalDate | ❌ Não | - | Validade da carteirinha | "2025-12-31" |

### 📝 Observações
- Se `convenio` é `null`, o paciente não possui convênio (usa apenas SUS)
- Sempre verificar `dataValidadeCarteirinha` antes de usar o convênio
- A estrutura completa do convênio está no objeto `ConvenioResponse`

---

## ⚰️ 9. Dados de Óbito

**Descrição:** Informações sobre o falecimento do paciente.

| Campo | Tipo | Obrigatório | Tamanho | Descrição | Exemplo |
|-------|------|-------------|---------|-----------|---------|
| `dataObito` | LocalDate | ❌ Não | - | Data do óbito | "2024-11-15" |
| `causaObitoCid10` | String | ❌ Não | 10 | CID-10 da causa do óbito | "I21.9" |

### 📝 Observações
- ⚠️ **IMPORTANTE**: Estes campos só devem ser preenchidos quando `statusPaciente = OBITO`
- **causaObitoCid10**: Formato CID-10 (A99 ou A99.99)
- Quando `statusPaciente = OBITO`, o sistema deve:
  - Impedir agendamentos
  - Mostrar indicador visual de óbito
  - Manter histórico para fins legais/estatísticos

---

## 🏛️ 10. Dados de Sistema SUS

**Descrição:** Informações específicas do Sistema Único de Saúde.

| Campo | Tipo | Obrigatório | Tamanho | Descrição | Exemplo |
|-------|------|-------------|---------|-----------|---------|
| `origemCadastro` | String | ❌ Não | 30 | Origem do cadastro | "e-SUS", "SISAB", "Manual" |

### 📝 Observações
- **origemCadastro**: Indica de onde veio o cadastro inicial
- Para informações completas de integração SUS, consulte `integracaoGov`

---

## 📝 11. Observações Gerais

| Campo | Tipo | Obrigatório | Descrição |
|-------|------|-------------|-----------|
| `observacoes` | String | ❌ Não | Campo de texto longo para observações gerais sobre o paciente |

### 📝 Observações
- Campo de texto livre (sem limite definido)
- Usado para anotações importantes que não se encaixam em outros campos

---

## 🔗 Relacionamentos OneToOne (1 para 1)

> **Explicação:** Cada paciente pode ter **apenas um registro** de cada tipo abaixo.

---

### 1️⃣ `enderecos` - Lista de Endereços

**Tipo:** `List<EnderecoResponse>`

**Descrição:** Lista de endereços do paciente (residencial, comercial, etc).

#### Campos Principais do Endereço:

| Campo | Tipo | Descrição | Exemplo |
|-------|------|-----------|---------|
| `id` | UUID | ID único do endereço | "550e8400-..." |
| `tipoLogradouro` | TipoLogradouroEnum | Tipo do logradouro | RUA, AVENIDA, PRACA, etc |
| `logradouro` | String | Nome da rua/avenida | "Rua das Flores" |
| `numero` | String | Número | "123" |
| `complemento` | String | Complemento | "Apto 201" |
| `bairro` | String | Bairro | "Centro" |
| `cep` | String | CEP | "30130-100" |
| `cidade` | CidadesResponse | Objeto da cidade | Ver estrutura |
| `estado` | EstadosResponse | Objeto do estado | Ver estrutura |
| `pais` | String | País | "Brasil" |
| `tipoEndereco` | TipoEnderecoEnum | Tipo | RESIDENCIAL, COMERCIAL, etc |
| `zona` | ZonaDomicilioEnum | Zona | URBANA ou RURAL |
| `latitude` | Double | Coordenada geográfica | -19.912345 |
| `longitude` | Double | Coordenada geográfica | -43.987654 |
| `pontoReferencia` | String | Ponto de referência | "Próximo ao supermercado" |
| `microarea` | String | Microárea da ESF | "01" |
| `ineEquipe` | String | INE da equipe | "0000123456" |

#### 📝 Como usar:
```typescript
// Buscar endereço residencial
const enderecoResidencial = paciente.enderecos?.find(e => e.tipoEndereco === 'RESIDENCIAL');

// Montar endereço completo
const enderecoCompleto = `${end.logradouro}, ${end.numero} - ${end.bairro}, ${end.cidade.nome}/${end.estado.uf}`;
```

---

### 2️⃣ `dadosSociodemograficos` - Dados Sociodemográficos Completos

**Tipo:** `DadosSociodemograficosResponse`

**Descrição:** Informações sociodemográficas detalhadas do paciente (1 registro por paciente).

#### Campos Completos:

| Campo | Tipo | Descrição | Valores Possíveis |
|-------|------|-----------|-------------------|
| `id` | UUID | ID único do registro | - |
| `racaCor` | RacaCorEnum | Raça/Cor IBGE | BRANCA, PRETA, PARDA, AMARELA, INDIGENA |
| `nacionalidade` | NacionalidadeEnum | Nacionalidade | BRASILEIRO, ESTRANGEIRO, NATURALIZADO |
| `paisNascimento` | String | País de nascimento | "Brasil" |
| `naturalidade` | String | Cidade de nascimento | "Belo Horizonte" |
| `municipioNascimentoIbge` | String | Código IBGE (7 dígitos) | "3106200" |
| `escolaridade` | EscolaridadeEnum | Nível de escolaridade | Ver tabela de Enums |
| `ocupacaoProfissao` | String | Ocupação/Profissão | "Engenheiro" |
| `situacaoRua` | Boolean | Se está em situação de rua | true ou false |
| `tempoSituacaoRua` | Integer | Tempo em situação de rua (meses) | 6 |
| `condicaoMoradia` | CondicaoMoradiaEnum | Condição da moradia | Ver tabela de Enums |
| `situacaoFamiliar` | SituacaoFamiliarEnum | Situação familiar | Ver tabela de Enums |

#### 📝 Quando usar:
- Para relatórios sociodemográficos
- Para políticas de equidade em saúde
- Para identificar vulnerabilidades sociais

---

### 3️⃣ `dadosClinicosBasicos` - Dados Clínicos Básicos

**Tipo:** `DadosClinicosBasicosResponse`

**Descrição:** Informações clínicas básicas e fatores de risco (1 registro por paciente).

#### Campos Completos:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | UUID | ID único do registro |
| `gestante` | Boolean | Se está gestante atualmente |
| `fumante` | Boolean | Se é fumante |
| `alcoolista` | Boolean | Se faz uso abusivo de álcool |
| `usuarioDrogas` | Boolean | Se faz uso de drogas ilícitas |
| `historicoViolencia` | Boolean | Se possui histórico de violência |
| `acompanhamentoPsicossocial` | Boolean | Se está em acompanhamento psicossocial |

#### 📝 Quando usar:
- Para avaliação de risco cardiovascular
- Para identificar necessidade de acompanhamento específico
- Para políticas de redução de danos

---

### 4️⃣ `responsavelLegal` - Responsável Legal Completo

**Tipo:** `ResponsavelLegalResponse`

**Descrição:** Dados completos do responsável legal (obrigatório para menores de 18 anos).

#### Campos Completos:

| Campo | Tipo | Descrição | Valores Possíveis |
|-------|------|-----------|-------------------|
| `id` | UUID | ID único do registro | - |
| `nome` | String | Nome completo do responsável | "Ana Silva Santos" |
| `cpf` | String | CPF do responsável | "12345678901" |
| `telefone` | String | Telefone de contato | "(31) 98765-4321" |
| `tipoResponsavel` | TipoResponsavelEnum | Tipo de responsável | PAI, MAE, TUTOR, CURADOR, etc |
| `autorizacaoUsoDadosLGPD` | Boolean | Autorização LGPD | true ou false |
| `autorizacaoResponsavel` | Boolean | Autorização para tratamento | true ou false |

#### 📝 Quando usar:
- Sempre para pacientes menores de 18 anos
- Para pacientes incapazes civilmente
- Antes de procedimentos que exigem autorização

---

### 5️⃣ `lgpdConsentimento` - Consentimentos LGPD

**Tipo:** `LGPDConsentimentoResponse`

**Descrição:** Registro de consentimentos conforme LGPD (1 registro por paciente).

#### Campos Completos:

| Campo | Tipo | Descrição | Uso Prático |
|-------|------|-----------|-------------|
| `id` | UUID | ID único do registro | - |
| `autorizacaoUsoDados` | Boolean | Autorização geral de uso de dados | Obrigatório para usar os dados |
| `autorizacaoContatoWhatsApp` | Boolean | Autorização para contato via WhatsApp | Verificar antes de enviar mensagens |
| `autorizacaoContatoEmail` | Boolean | Autorização para contato via e-mail | Verificar antes de enviar e-mails |
| `dataConsentimento` | LocalDateTime | Data/hora do consentimento | Para registro legal |

#### 📝 ⚠️ IMPORTANTE - LGPD:
```typescript
// SEMPRE verificar antes de contatar:
if (paciente.lgpdConsentimento?.autorizacaoContatoWhatsApp === true) {
  // PODE enviar WhatsApp
} else {
  // NÃO PODE enviar - solicitar autorização primeiro
}
```

---

### 6️⃣ `integracaoGov` - Integração Governamental

**Tipo:** `IntegracaoGovResponse`

**Descrição:** Informações de integração com sistemas do governo (SUS, e-SUS, RNDS).

#### Campos Completos:

| Campo | Tipo | Descrição | Exemplo |
|-------|------|-----------|---------|
| `id` | UUID | ID único do registro | - |
| `uuidRnds` | UUID | UUID no RNDS (Registro Nacional de Dados de Saúde) | "550e8400-..." |
| `idIntegracaoGov` | String | ID de integração com sistemas governamentais | "123456789" |
| `dataSincronizacaoGov` | LocalDateTime | Data da última sincronização | "2024-12-04T10:30:00" |
| `ineEquipe` | String | INE da equipe de saúde (ESF) | "0000123456" |
| `microarea` | String | Microárea de cobertura | "01" |
| `cnesEstabelecimentoOrigem` | String | CNES do estabelecimento de origem | "1234567" |
| `origemCadastro` | String | Origem do cadastro | "e-SUS", "SISAB" |

#### 📝 Quando usar:
- Para sincronização com sistemas governamentais
- Para identificar a equipe de ESF responsável
- Para rastreamento de dados do SUS

---

## 📚 Relacionamentos OneToMany (1 para Muitos)

> **Explicação:** Cada paciente pode ter **vários registros** de cada tipo abaixo (lista/array).

---

### 1️⃣ `doencas` - Lista de Doenças/Comorbidades

**Tipo:** `List<DoencasPacienteResponse>`

**Descrição:** Lista de todas as doenças e comorbidades diagnosticadas no paciente.

#### Campos do Relacionamento:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | UUID | ID único do registro |
| `doenca` | DoencasResponse | **Objeto completo da doença** (ver abaixo) |
| `cidPrincipal` | CidDoencasResponse | CID-10 específico para este paciente |
| `diagnostico` | DiagnosticoDoencaPaciente | Dados do diagnóstico (embeddable) |
| `acompanhamento` | AcompanhamentoDoencaPaciente | Dados de acompanhamento (embeddable) |
| `tratamentoAtual` | TratamentoAtualDoencaPaciente | Tratamento atual (embeddable) |
| `observacoes` | String | Observações específicas |

#### Campos do Objeto `DoencasResponse`:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | UUID | ID único da doença no catálogo |
| `nome` | String | Nome da doença | "Hipertensão Arterial" |
| `nomeCientifico` | String | Nome científico | "Hypertension" |
| `cronica` | Boolean | Se é doença crônica | true ou false |
| `cidPrincipal` | CidDoencasResponse | CID-10 principal da doença |
| `descricao` | String | Descrição geral |
| `causas` | String | Causas conhecidas |
| `fisiopatologia` | String | Fisiopatologia |
| `prognostico` | String | Prognóstico geral |

#### 📝 Exemplo de uso:
```typescript
// Listar doenças crônicas
const doencasCronicas = paciente.doencas?.filter(d => d.doenca.cronica === true);

// Exibir lista
doencasCronicas?.forEach(doenca => {
  console.log(`${doenca.doenca.nome} - CID: ${doenca.cidPrincipal.codigo}`);
});
```

---

### 2️⃣ `alergias` - Lista de Alergias

**Tipo:** `List<AlergiasPacienteResponse>`

**Descrição:** Lista de todas as alergias do paciente.

#### Campos do Relacionamento:

| Campo | Tipo | Descrição | ⚠️ Importante |
|-------|------|-----------|---------------|
| `id` | UUID | ID único do registro | - |
| `alergia` | AlergiasResponse | **Objeto completo da alergia** (ver abaixo) | - |
| `diagnostico` | DiagnosticoAlergiaPaciente | Dados do diagnóstico (embeddable) | - |
| `historicoReacoes` | HistoricoReacoesAlergiaPaciente | Histórico de reações (embeddable) | - |
| `observacoes` | String | Observações específicas | - |
| `alertaMedico` | Boolean | **Se deve exibir alerta no prontuário** | ⚠️ **CRÍTICO!** |

#### Campos do Objeto `AlergiasResponse`:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | UUID | ID único da alergia no catálogo |
| `nome` | String | Nome da alergia | "Penicilina" |
| `nomeCientifico` | String | Nome científico | "Penicillin" |
| `descricao` | String | Descrição geral |
| `substanciasRelacionadas` | String | Substâncias relacionadas |

#### 📝 ⚠️ ALERTA CRÍTICO:
```typescript
// SEMPRE verificar alertas médicos
const alergiasComAlerta = paciente.alergias?.filter(a => a.alertaMedico === true);

if (alergiasComAlerta && alergiasComAlerta.length > 0) {
  // EXIBIR ALERTA VISUAL PROEMINENTE (vermelho, piscante, etc)
  exibirAlertaCritico('PACIENTE COM ALERGIAS CRÍTICAS!', alergiasComAlerta);
}
```

---

### 3️⃣ `deficiencias` - Lista de Deficiências

**Tipo:** `List<DeficienciasPacienteResponse>`

**Descrição:** Lista de todas as deficiências do paciente.

#### Campos do Relacionamento:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | UUID | ID único do registro |
| `deficiencia` | DeficienciasResponse | **Objeto completo da deficiência** (ver abaixo) |
| `possuiLaudo` | Boolean | Se possui laudo médico |
| `dataDiagnostico` | LocalDate | Data do diagnóstico |
| `observacoes` | String | Observações específicas |

#### Campos do Objeto `DeficienciasResponse`:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | UUID | ID único da deficiência no catálogo |
| `nome` | String | Nome da deficiência | "Deficiência Visual" |
| `descricao` | String | Descrição |
| `tipoDeficiencia` | TipoDeficienciaEnum | Tipo | FISICA, VISUAL, AUDITIVA, INTELECTUAL, MULTIPLA |
| `cid10Relacionado` | String | CID-10 relacionado | "H54.0" |
| `permanente` | Boolean | Se é permanente |
| `acompanhamentoContinuo` | Boolean | Se exige acompanhamento contínuo |

#### 📝 Exemplo de uso:
```typescript
// Verificar se exige acompanhamento
const deficienciasComAcompanhamento = paciente.deficiencias?.filter(
  d => d.deficiencia.acompanhamentoContinuo === true
);
```

---

### 4️⃣ `medicacoes` - Lista de Medicações Contínuas

**Tipo:** `List<MedicacaoPacienteResponse>`

**Descrição:** Lista de todas as medicações em uso contínuo pelo paciente.

#### Campos do Relacionamento:

| Campo | Tipo | Descrição | Exemplo |
|-------|------|-----------|---------|
| `id` | UUID | ID único do registro | - |
| `medicacao` | MedicacaoResponse | **Objeto completo do medicamento** | - |
| `dose` | String | Dosagem | "500mg" |
| `frequencia` | FrequenciaMedicacaoEnum | Frequência de uso | HORAS_8, DIARIA, BID, etc |
| `via` | ViaAdministracaoEnum | Via de administração | ORAL, INTRAVENOSA, etc |
| `cidRelacionado` | CidDoencasResponse | CID relacionado à medicação | - |
| `dataInicio` | LocalDate | Data de início do uso | "2024-01-15" |
| `dataFim` | LocalDate | Data de término (se aplicável) | "2024-12-31" ou null |
| `medicacaoAtiva` | Boolean | **Se a medicação está ativa** | true ou false |
| `observacoes` | String | Observações sobre o uso | - |

#### Campos do Objeto `MedicacaoResponse`:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `id` | UUID | ID único do medicamento no catálogo |
| `identificacao` | IdentificacaoMedicamento | Dados de identificação (embedded) |
| `dosagemAdministracao` | DosagemAdministracaoMedicamento | Dosagem padrão (embedded) |
| `classificacao` | ClassificacaoMedicamento | Classificação (embedded) |
| `descricao` | String | Descrição do medicamento |
| `indicacoes` | String | Indicações de uso |

#### 📝 ⚠️ IMPORTANTE:
```typescript
// SEMPRE filtrar apenas medicações ATIVAS
const medicacoesAtivas = paciente.medicacoes?.filter(m => m.medicacaoAtiva === true);

// Exibir lista de medicações em uso
medicacoesAtivas?.forEach(med => {
  console.log(`${med.medicacao.identificacao.principioAtivo} - ${med.dose} - ${med.frequencia}`);
});

// Verificar interações medicamentosas (importante!)
verificarInteracoes(medicacoesAtivas);
```

---

## 🎨 Lista Completa de Enums

### SexoEnum - Sexo Biológico
| Valor | Descrição |
|-------|-----------|
| `MASCULINO` | Masculino |
| `FEMININO` | Feminino |
| `INTERSEXO` | Intersexo |

---

### IdentidadeGeneroEnum - Identidade de Gênero
| Valor | Descrição |
|-------|-----------|
| `CISGENERO` | Cisgênero (identifica-se com sexo biológico) |
| `TRANSGENERO` | Transgênero |
| `NAO_BINARIO` | Não binário |
| `GENERO_FLUIDO` | Gênero fluído |
| `OUTRO` | Outro |

---

### OrientacaoSexualEnum - Orientação Sexual
| Valor | Descrição |
|-------|-----------|
| `HETEROSSEXUAL` | Heterossexual |
| `HOMOSSEXUAL` | Homossexual |
| `BISSEXUAL` | Bissexual |
| `PANSEXUAL` | Pansexual |
| `ASSEXUAL` | Assexual |
| `OUTRO` | Outro |

---

### EstadoCivilEnum - Estado Civil
| Valor | Descrição |
|-------|-----------|
| `SOLTEIRO` | Solteiro(a) |
| `CASADO` | Casado(a) |
| `DIVORCIADO` | Divorciado(a) |
| `VIUVO` | Viúvo(a) |
| `UNIAO_ESTAVEL` | União Estável |
| `SEPARADO` | Separado(a) |

---

### RacaCorEnum - Raça/Cor (IBGE)
| Valor | Descrição |
|-------|-----------|
| `BRANCA` | Branca |
| `PRETA` | Preta |
| `PARDA` | Parda |
| `AMARELA` | Amarela |
| `INDIGENA` | Indígena |

---

### NacionalidadeEnum - Nacionalidade
| Valor | Descrição |
|-------|-----------|
| `BRASILEIRO` | Brasileiro(a) |
| `ESTRANGEIRO` | Estrangeiro(a) |
| `NATURALIZADO` | Naturalizado(a) |

---

### EscolaridadeEnum - Nível de Escolaridade
| Valor | Descrição |
|-------|-----------|
| `SEM_ESCOLARIDADE` | Sem escolaridade |
| `FUNDAMENTAL_INCOMPLETO` | Ensino Fundamental Incompleto |
| `FUNDAMENTAL_COMPLETO` | Ensino Fundamental Completo |
| `MEDIO_INCOMPLETO` | Ensino Médio Incompleto |
| `MEDIO_COMPLETO` | Ensino Médio Completo |
| `SUPERIOR_INCOMPLETO` | Ensino Superior Incompleto |
| `SUPERIOR_COMPLETO` | Ensino Superior Completo |
| `POS_GRADUACAO` | Pós-graduação (Especialização, Mestrado, Doutorado) |

---

### StatusPacienteEnum - Status do Paciente
| Valor | Descrição | Quando Usar |
|-------|-----------|-------------|
| `ATIVO` | Ativo (padrão) | Paciente em atendimento normal |
| `INATIVO` | Inativo | Cadastro desativado (mas não excluído) |
| `OBITO` | Óbito | Paciente falecido |

---

### TipoCnsEnum - Tipo do CNS
| Valor | Descrição |
|-------|-----------|
| `DEFINITIVO` | CNS Definitivo (permanente) |
| `PROVISORIO` | CNS Provisório (temporário) |

---

### TipoAtendimentoPreferencialEnum - Atendimento Preferencial
| Valor | Descrição |
|-------|-----------|
| `IDOSO` | Idoso (60 anos ou mais) |
| `GESTANTE` | Gestante |
| `LACTANTE` | Lactante (amamentando) |
| `DEFICIENTE` | Pessoa com Deficiência |
| `OBESO` | Pessoa com Obesidade |

---

### CondicaoMoradiaEnum - Condição de Moradia
| Valor | Descrição |
|-------|-----------|
| `PROPRIA` | Casa própria |
| `ALUGADA` | Casa alugada |
| `CEDIDA` | Casa cedida |
| `SITUACAO_RUA` | Situação de rua |
| `OCUPACAO` | Ocupação |
| `OUTRO` | Outro |

---

### SituacaoFamiliarEnum - Situação Familiar
| Valor | Descrição |
|-------|-----------|
| `MORA_SOZINHO` | Mora sozinho(a) |
| `MORA_COM_FAMILIA` | Mora com família |
| `MORA_COM_COMPANHEIRO` | Mora com companheiro(a) |
| `INSTITUCIONALIZADO` | Institucionalizado (abrigo, asilo, etc) |
| `OUTRO` | Outro |

---

### TipoDeficienciaEnum - Tipo de Deficiência
| Valor | Descrição |
|-------|-----------|
| `FISICA` | Deficiência Física |
| `VISUAL` | Deficiência Visual |
| `AUDITIVA` | Deficiência Auditiva |
| `INTELECTUAL` | Deficiência Intelectual |
| `MULTIPLA` | Deficiência Múltipla |

---

### FrequenciaMedicacaoEnum - Frequência de Medicação
| Valor | Descrição | Horários Típicos |
|-------|-----------|------------------|
| `HORAS_4` | A cada 4 horas | 06h, 10h, 14h, 18h, 22h, 02h |
| `HORAS_6` | A cada 6 horas | 06h, 12h, 18h, 00h |
| `HORAS_8` | A cada 8 horas | 06h, 14h, 22h |
| `HORAS_12` | A cada 12 horas | 08h, 20h |
| `DIARIA` | 1 vez ao dia | 08h |
| `BID` | 2 vezes ao dia | 08h, 20h |
| `TID` | 3 vezes ao dia | 08h, 14h, 20h |
| `SOS` | Se necessário | Quando houver sintoma |

---

### ViaAdministracaoEnum - Via de Administração
| Valor | Descrição |
|-------|-----------|
| `ORAL` | Via Oral (pela boca) |
| `SUBLINGUAL` | Via Sublingual (embaixo da língua) |
| `INTRAVENOSA` | Via Intravenosa (na veia) |
| `INTRAMUSCULAR` | Via Intramuscular (no músculo) |
| `SUBCUTANEA` | Via Subcutânea (sob a pele) |
| `TOPICA` | Via Tópica (na pele) |
| `INALATORIA` | Via Inalatória (por inalação) |
| `RETAL` | Via Retal |
| `OCULAR` | Via Ocular (no olho) |
| `NASAL` | Via Nasal (no nariz) |

---

### TipoResponsavelEnum - Tipo de Responsável Legal
| Valor | Descrição |
|-------|-----------|
| `PAI` | Pai |
| `MAE` | Mãe |
| `TUTOR` | Tutor |
| `CURADOR` | Curador |
| `CONJUGE` | Cônjuge |
| `FILHO` | Filho(a) |
| `OUTRO` | Outro |

---

## 💡 Exemplos Práticos de Código

### Exemplo 1: Verificar Alergias Críticas (IMPORTANTE!)

```typescript
/**
 * Verifica se o paciente tem alergias com alerta médico
 * e exibe aviso crítico na interface
 */
function verificarAlergiasComAlerta(paciente: PacienteResponse) {
  // Filtrar alergias com alerta médico ativado
  const alergiasComAlerta = paciente.alergias?.filter(
    alergia => alergia.alertaMedico === true
  );
  
  if (alergiasComAlerta && alergiasComAlerta.length > 0) {
    // EXIBIR ALERTA VISUAL PROEMINENTE
    const mensagem = alergiasComAlerta
      .map(a => a.alergia.nome)
      .join(', ');
    
    exibirAlertaCritico({
      titulo: '⚠️ ALERTA: PACIENTE COM ALERGIAS CRÍTICAS',
      mensagem: `Alergias: ${mensagem}`,
      tipo: 'danger',
      destaque: true,
      som: true
    });
  }
}
```

---

### Exemplo 2: Listar Medicações Ativas

```typescript
/**
 * Lista todas as medicações em uso ativo
 */
function listarMedicacoesAtivas(paciente: PacienteResponse) {
  // Filtrar apenas medicações ativas
  const medicacoesAtivas = paciente.medicacoes?.filter(
    med => med.medicacaoAtiva === true
  );
  
  if (!medicacoesAtivas || medicacoesAtivas.length === 0) {
    console.log('Paciente não possui medicações ativas');
    return;
  }
  
  console.log('=== MEDICAÇÕES EM USO ===');
  medicacoesAtivas.forEach(med => {
    console.log(`
      Medicamento: ${med.medicacao.identificacao?.principioAtivo}
      Dose: ${med.dose}
      Frequência: ${med.frequencia}
      Via: ${med.via}
      Início: ${formatarData(med.dataInicio)}
    `);
  });
}
```

---

### Exemplo 3: Verificar Necessidade de Responsável Legal

```typescript
/**
 * Verifica se paciente menor de 18 anos tem responsável cadastrado
 */
function verificarResponsavelLegal(paciente: PacienteResponse) {
  // Calcular idade
  const idade = calcularIdade(paciente.dataNascimento);
  
  // Verificar se é menor de 18 anos
  if (idade < 18) {
    if (!paciente.responsavelLegal) {
      // ALERTA: Menor sem responsável
      exibirAlerta({
        tipo: 'warning',
        titulo: 'Responsável Legal Necessário',
        mensagem: 'Paciente menor de idade precisa de responsável legal cadastrado'
      });
      return false;
    }
    
    // Verificar se tem autorização
    if (!paciente.responsavelLegal.autorizacaoResponsavel) {
      exibirAlerta({
        tipo: 'warning',
        titulo: 'Autorização Pendente',
        mensagem: 'Responsável legal não autorizou tratamento'
      });
      return false;
    }
  }
  
  return true;
}

/**
 * Função auxiliar para calcular idade
 */
function calcularIdade(dataNascimento: string): number {
  const hoje = new Date();
  const nascimento = new Date(dataNascimento);
  let idade = hoje.getFullYear() - nascimento.getFullYear();
  const mes = hoje.getMonth() - nascimento.getMonth();
  
  if (mes < 0 || (mes === 0 && hoje.getDate() < nascimento.getDate())) {
    idade--;
  }
  
  return idade;
}
```

---

### Exemplo 4: Montar Endereço Completo

```typescript
/**
 * Monta endereço completo formatado
 */
function montarEnderecoCompleto(endereco: EnderecoResponse): string {
  const partes = [
    endereco.tipoLogradouro ? `${endereco.tipoLogradouro} ${endereco.logradouro}` : endereco.logradouro,
    endereco.numero,
    endereco.complemento,
    endereco.bairro,
    `${endereco.cidade?.nome}/${endereco.estado?.uf}`,
    `CEP: ${formatarCep(endereco.cep)}`
  ];
  
  return partes.filter(Boolean).join(' - ');
}

/**
 * Buscar endereço residencial do paciente
 */
function buscarEnderecoResidencial(paciente: PacienteResponse): EnderecoResponse | null {
  return paciente.enderecos?.find(
    end => end.tipoEndereco === 'RESIDENCIAL'
  ) || null;
}

// Uso:
const endereco = buscarEnderecoResidencial(paciente);
if (endereco) {
  console.log(montarEnderecoCompleto(endereco));
  // Ex: "Rua das Flores - 123 - Apto 201 - Centro - Belo Horizonte/MG - CEP: 30130-100"
}
```

---

### Exemplo 5: Verificar Consentimento LGPD

```typescript
/**
 * Verifica se pode enviar comunicação ao paciente
 */
function podeEnviarComunicacao(
  paciente: PacienteResponse, 
  tipo: 'email' | 'whatsapp'
): boolean {
  
  if (!paciente.lgpdConsentimento) {
    console.log('Paciente sem registro de consentimento LGPD');
    return false;
  }
  
  // Verificar autorização geral
  if (!paciente.lgpdConsentimento.autorizacaoUsoDados) {
    console.log('Paciente não autorizou uso de dados');
    return false;
  }
  
  // Verificar autorização específica do canal
  if (tipo === 'email') {
    return paciente.lgpdConsentimento.autorizacaoContatoEmail === true;
  }
  
  if (tipo === 'whatsapp') {
    return paciente.lgpdConsentimento.autorizacaoContatoWhatsApp === true;
  }
  
  return false;
}

// Uso:
if (podeEnviarComunicacao(paciente, 'whatsapp')) {
  enviarWhatsApp(paciente.telefone, 'Sua consulta está agendada!');
} else {
  console.log('Paciente não autorizou contato via WhatsApp');
  solicitarConsentimento(paciente.id, 'whatsapp');
}
```

---

### Exemplo 6: Exibir Status do Paciente

```typescript
/**
 * Retorna informações de status do paciente para exibição
 */
function obterStatusPaciente(paciente: PacienteResponse) {
  let status = {
    cor: 'green',
    texto: 'Ativo',
    icone: 'check-circle',
    permitirAgendamento: true
  };
  
  // Verificar óbito
  if (paciente.statusPaciente === 'OBITO') {
    status = {
      cor: 'black',
      texto: `Óbito em ${formatarData(paciente.dataObito)}`,
      icone: 'x-circle',
      permitirAgendamento: false
    };
  }
  
  // Verificar inativo
  else if (paciente.statusPaciente === 'INATIVO') {
    status = {
      cor: 'gray',
      texto: 'Inativo',
      icone: 'pause-circle',
      permitirAgendamento: false
    };
  }
  
  // Adicionar badges adicionais
  const badges = [];
  
  if (paciente.situacaoRua) {
    badges.push({ texto: 'Situação de Rua', cor: 'orange' });
  }
  
  if (paciente.acompanhadoPorEquipeEsf) {
    badges.push({ texto: 'ESF', cor: 'blue' });
  }
  
  if (paciente.tipoAtendimentoPreferencial) {
    badges.push({ 
      texto: `Atend. Preferencial: ${paciente.tipoAtendimentoPreferencial}`, 
      cor: 'purple' 
    });
  }
  
  return { ...status, badges };
}

// Uso na interface:
const status = obterStatusPaciente(paciente);
exibirBadge(status.cor, status.texto, status.icone);
status.badges.forEach(badge => exibirBadge(badge.cor, badge.texto));
```

---

### Exemplo 7: Validar Dados Obrigatórios

```typescript
/**
 * Valida se o paciente tem todos os dados obrigatórios preenchidos
 */
function validarDadosObrigatorios(paciente: PacienteResponse): {
  valido: boolean;
  erros: string[];
} {
  const erros: string[] = [];
  
  // Campos obrigatórios
  if (!paciente.nomeCompleto || paciente.nomeCompleto.trim() === '') {
    erros.push('Nome completo é obrigatório');
  }
  
  // CPF ou CNS obrigatório
  if (!paciente.cpf && !paciente.cns) {
    erros.push('CPF ou CNS é obrigatório');
  }
  
  // Validar CPF se preenchido
  if (paciente.cpf && !validarCPF(paciente.cpf)) {
    erros.push('CPF inválido');
  }
  
  // Validar CNS se preenchido
  if (paciente.cns && !validarCNS(paciente.cns)) {
    erros.push('CNS inválido');
  }
  
  // Verificar menor de idade
  if (paciente.dataNascimento) {
    const idade = calcularIdade(paciente.dataNascimento);
    if (idade < 18 && !paciente.responsavelLegal) {
      erros.push('Menor de idade precisa de responsável legal cadastrado');
    }
  }
  
  return {
    valido: erros.length === 0,
    erros
  };
}
```

---

## 🔒 Checklist LGPD - Proteção de Dados

### Antes de Usar Dados Pessoais:

- [ ] Verificar se `lgpdConsentimento.autorizacaoUsoDados === true`
- [ ] Para e-mail: verificar `lgpdConsentimento.autorizacaoContatoEmail === true`
- [ ] Para WhatsApp: verificar `lgpdConsentimento.autorizacaoContatoWhatsApp === true`
- [ ] Registrar `lgpdConsentimento.dataConsentimento` quando coletar autorização
- [ ] Para menores: verificar `responsavelLegal.autorizacaoUsoDadosLGPD === true`

### Dados Sensíveis (Atenção Especial):

- **Dados de Saúde**: Todas as doenças, alergias, medicações
- **Dados de Origem Racial/Étnica**: Campo `racaCor`
- **Dados de Orientação Sexual**: Campos `identidadeGenero` e `orientacaoSexual`

---

## 📞 Suporte e Dúvidas

### Em caso de dúvidas:

1. **Documentação da API**: Consulte o Swagger/OpenAPI
2. **Equipe Back-end**: Para questões sobre estrutura de dados
3. **Este Documento**: Sempre atualizado com a versão mais recente

### Atualizações deste documento:

- Sempre sincronizado com as entidades do back-end
- Versão controlada junto com o código
- Qualquer alteração nas entidades deve refletir aqui

---

## 📌 Convenções e Boas Práticas

### Nomenclatura:

- **Campos booleanos**: Sempre usar `is`, `possui`, `tem`, `pode`
- **Datas**: Formato ISO 8601 (YYYY-MM-DD)
- **Enums**: UPPER_CASE_SNAKE_CASE
- **Null vs Undefined**: `null` = não informado, `undefined` = não aplicável

### Performance:

- **Listas grandes**: Sempre filtrar no front para melhor UX
- **Relacionamentos**: Lazy loading - carregar sob demanda
- **Cache**: Cachear dados estáticos (enums, catálogos)

### Segurança:

- **Nunca exibir**: Senhas, tokens, dados de integração
- **Mascarar**: CPF, telefone em listagens
- **Validar**: Sempre validar dados antes de enviar ao back

---

**📅 Última Atualização:** 04 de Dezembro de 2025  
**📌 Versão:** 1.0.0  
**👥 Responsável:** Equipe de Desenvolvimento UPSaúde  
**🔄 Status:** Sincronizado com o Back-end
