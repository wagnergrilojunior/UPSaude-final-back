# 🚀 Release 1.0.0.12

**Data de Lançamento:** 13 de Janeiro de 2026  
**Tipo:** Patch Release - Correções e Melhorias

---

## 📋 Resumo Executivo

Esta release foca em correções críticas de integridade de dados, melhorias na serialização JSON, ajustes no esquema do banco de dados e correção completa dos testes de regressão. Todas as melhorias garantem maior estabilidade e conformidade com os padrões FHIR e integrações SUS/RNDS.

---

## ✨ Novas Funcionalidades

### 🔗 Integração com Sistemas Externos
- **Sistema de Eventos de Integração (Outbox Pattern)**
  - Implementação completa do padrão Outbox para eventos de integração
  - Suporte para RNDS e e-SUS APS
  - Rastreamento de status, tentativas e versões de eventos
  - Validação pré-envio para prevenir rejeições externas

---

## 🐛 Correções Críticas

### 🔄 Referências Circulares
- **Correção de Serialização JSON**
  - Adicionado `@JsonIgnore` em relacionamentos bidirecionais em `BaseEntity`
  - Correção de referência circular entre `Tenant` e `Endereco`
  - Correção de referência circular entre `Paciente` e `PacienteIdentificador`
  - Refatoração de `IntegracaoEventoGenerator` para usar DTOs simplificados

### 💾 Mapeamento JSONB
- **Correção de Conversão JSONB**
  - Implementado `@JdbcTypeCode(SqlTypes.JSON)` em campos JSONB de `Agendamento`
  - Implementado `@JdbcTypeCode(SqlTypes.JSON)` em campos JSONB de `Atendimento`
  - Campos corrigidos:
    - `motivosAgendamento` e `periodoSolicitado` (Agendamento)
    - `motivoAtendimento`, `diagnosticosAdmissao`, `dadosInternacao`, `periodoReal` (Atendimento)

### 🗄️ Esquema de Banco de Dados
- **Adição de Colunas Faltantes**
  - `sinal_vital_record_id` na tabela `atendimentos`
  - `main_cid10_id`, `main_ciap2_id` na tabela `atendimentos`
  - `main_clinical_status`, `main_verification_status` na tabela `atendimentos`
  - `conselho_profissional_id` na tabela `profissionais_saude`
  - Colunas FHIR em `cidades` e `estados` (codigo_fhir, descricao_fhir)
  - Colunas em `sigtap_ocupacao` (grande_grupo, subgrupo_principal, subgrupo, familia, descricao_fhir, codigo_cbo_completo)

---

## 🔧 Melhorias Técnicas

### 📊 Validações e Enums
- **Correção de Enums**
  - `IdentidadeGeneroEnum`: Removido valor inválido "CIS", mantidos valores conforme FHIR BR
  - `TipoIdentificadorEnum`: Corrigido "OUTRO" para "OUTROS"
  - Deserializador de `IdentidadeGeneroEnum` com mensagens de erro dinâmicas

#### 🔍 Detalhamento das Alterações nos Enums

##### `IdentidadeGeneroEnum`
**Valores Válidos (conforme FHIR BR):**
- `HOMEM` (código: 1) - Homem
- `MULHER` (código: 2) - Mulher
- `HOMEM_TRANS` (código: 3) - Homem Transgênero
- `MULHER_TRANS` (código: 4) - Mulher Transgênero
- `TRAVESTI` (código: 5) - Travesti
- `NAO_BINARIO` (código: 6) - Não-Binário
- `NAO_INFORMADO` (código: 9) - Não Informado

**Valores Removidos/Corrigidos:**
- ❌ `CIS` - Valor inválido removido (não existe no padrão FHIR BR)
- ✅ Testes atualizados para usar `HOMEM` ou `MULHER` conforme apropriado

**Melhorias no Deserializador:**
- Suporte para deserialização por código numérico (1-9)
- Suporte para deserialização por nome do enum (`HOMEM`, `MULHER`, etc.)
- Suporte para deserialização por descrição ("Homem", "Mulher", etc.)
- Mensagens de erro dinâmicas listando todos os valores válidos
- Tratamento especial para valores inválidos como "CIS" com mensagem clara

**Exemplo de Uso:**
```java
// Aceita código numérico
"identidadeGenero": 1  // → HOMEM

// Aceita nome do enum
"identidadeGenero": "HOMEM"  // → HOMEM

// Aceita descrição
"identidadeGenero": "Homem"  // → HOMEM

// Valor inválido gera erro claro
"identidadeGenero": "CIS"  // → InvalidArgumentException com lista de valores válidos
```

##### `TipoIdentificadorEnum`
**Valores Válidos (conforme FHIR BR):**
- `CPF` (código: 1) - CPF
- `CNPJ` (código: 2) - CNPJ
- `CNS` (código: 3) - Cartão Nacional de Saúde
- `RG` (código: 4) - Registro Geral
- `PASSAPORTE` (código: 5) - Passaporte
- `CTPS` (código: 6) - Carteira de Trabalho
- `TITULO_ELEITOR` (código: 7) - Título de Eleitor
- `CNH` (código: 8) - Carteira de Habilitação
- `CERTIDAO_NASCIMENTO` (código: 9) - Certidão de Nascimento
- `CERTIDAO_CASAMENTO` (código: 10) - Certidão de Casamento
- `CERTIDAO_OBITO` (código: 11) - Certidão de Óbito
- `PIS_PASEP` (código: 12) - PIS/PASEP
- `NIT` (código: 13) - Número de Identificação do Trabalhador
- `CAD_UNICO` (código: 14) - Cadastro Único
- `DNV` (código: 15) - Declaração de Nascido Vivo
- `AUTORIZACAO` (código: 16) - Número de Autorização
- `OUTROS` (código: 99) - Outros

**Valores Corrigidos:**
- ❌ `OUTRO` - Valor incorreto (singular)
- ✅ `OUTROS` - Valor correto (plural, conforme enum)

**Impacto nos Testes:**
- Todos os testes de regressão atualizados para usar `OUTROS` em vez de `OUTRO`
- Validação automática garante que apenas valores válidos sejam aceitos

**Exemplo de Uso:**
```java
// Valor correto
"tipo": "OUTROS"  // ✅ Aceito

// Valor incorreto (não aceito)
"tipo": "OUTRO"  // ❌ Rejeitado com erro de validação
```

##### 🔧 Melhorias Técnicas nos Deserializadores

**`IdentidadeGeneroEnumDeserializer`:**
- Ordem de tentativas de deserialização otimizada:
  1. Código numérico (1-9)
  2. Nome do enum (HOMEM, MULHER, etc.)
  3. Descrição ("Homem", "Mulher", etc.)
- Mensagens de erro informativas com lista completa de valores válidos
- Tratamento de valores comuns incorretos com feedback claro

**Compatibilidade:**
- ✅ Retrocompatível com códigos numéricos
- ✅ Retrocompatível com nomes de enum existentes
- ✅ Suporte para descrições em português
- ⚠️ Valores inválidos agora geram erros claros (antes podiam passar silenciosamente)

##### 📝 Impacto nos Testes de Regressão

**Testes Corrigidos:**
- `PacienteCadastroRegressionTest`: Alterado `"CIS"` → `"HOMEM"`
- `PacienteAtualizacaoRegressionTest`: Alterado `"TRANS"` → `"HOMEM_TRANS"`, `"OUTRO"` → `"OUTROS"`
- `ProfissionalSaudeCadastroRegressionTest`: Alterado `"CIS"` → `"HOMEM"`
- `ProfissionalSaudeAtualizacaoRegressionTest`: Alterado `"CIS"` → `"HOMEM"`

**Valores Corrigidos nos Testes:**
| Teste | Campo | Valor Antigo (❌) | Valor Novo (✅) |
|-------|-------|-------------------|-----------------|
| PacienteCadastroRegressionTest | `identidadeGenero` | `"CIS"` | `"HOMEM"` |
| PacienteCadastroRegressionTest | `tipo` (identificador) | `"OUTRO"` | `"OUTROS"` |
| PacienteAtualizacaoRegressionTest | `identidadeGenero` | `"TRANS"` | `"HOMEM_TRANS"` |
| PacienteAtualizacaoRegressionTest | `tipo` (identificador) | `"OUTRO"` | `"OUTROS"` |
| ProfissionalSaudeCadastroRegressionTest | `identidadeGenero` | `"CIS"` | `"HOMEM"` |
| ProfissionalSaudeAtualizacaoRegressionTest | `identidadeGenero` | `"CIS"` | `"HOMEM"` |

**Benefícios:**
- ✅ Validação mais rigorosa garante conformidade com padrões FHIR BR
- ✅ Mensagens de erro claras facilitam depuração
- ✅ Prevenção de dados inválidos sendo persistidos no banco
- ✅ Melhor experiência para desenvolvedores com feedback imediato

### 🧪 Testes de Regressão
- **Correção Completa dos Testes**
  - ✅ Módulo Paciente: Ajustes em enums e estrutura de dados
  - ✅ Módulo Médico: Validação de campos e relacionamentos
  - ✅ Módulo Profissional de Saúde: Correção de enums e colunas
  - ✅ Módulo Usuário Sistema: Validação de estrutura
  - ✅ Módulo Estabelecimento: Correção de relacionamentos
  - ✅ Módulo Agendamento: Validação de campos JSONB e enums
  - ✅ Módulo Consulta: Correção de colunas e mapeamento JSONB

### 🏗️ Arquitetura
- **Melhorias em Mappers**
  - `TenantMapper`: Ignorar campo `endereco` para evitar referências circulares
  - Mapeamento otimizado para serialização JSON

---

## 📊 Estatísticas

- **Arquivos Modificados:** ~25 arquivos
- **Colunas Adicionadas:** 12 colunas em 5 tabelas
- **Testes Corrigidos:** 8 módulos de testes de regressão
- **Enums Corrigidos:** 2 enums principais
- **Padrões Implementados:** Outbox Pattern para integrações

---

## 🔍 Detalhes Técnicos

### Entidades Modificadas

#### `BaseEntity`
```java
@JsonIgnore
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "tenant_id", nullable = false)
private Tenant tenant;

@JsonIgnore
@ManyToOne(fetch = FetchType.LAZY, optional = true)
@JoinColumn(name = "estabelecimento_id", nullable = true)
private Estabelecimentos estabelecimento;
```

#### `Agendamento`
```java
@JdbcTypeCode(SqlTypes.JSON)
@Column(name = "motivos_agendamento", columnDefinition = "jsonb")
private String motivosAgendamento;

@JdbcTypeCode(SqlTypes.JSON)
@Column(name = "periodo_solicitado", columnDefinition = "jsonb")
private String periodoSolicitado;
```

#### `Atendimento`
```java
@JdbcTypeCode(SqlTypes.JSON)
@Column(name = "motivo_atendimento", columnDefinition = "jsonb")
private String motivoAtendimento;

@JdbcTypeCode(SqlTypes.JSON)
@Column(name = "diagnosticos_admissao", columnDefinition = "jsonb")
private String diagnosticosAdmissao;

@JdbcTypeCode(SqlTypes.JSON)
@Column(name = "dados_internacao", columnDefinition = "jsonb")
private String dadosInternacao;

@JdbcTypeCode(SqlTypes.JSON)
@Column(name = "periodo_real", columnDefinition = "jsonb")
private String periodoReal;
```

### Migrações de Banco de Dados

#### Tabela `atendimentos`
```sql
ALTER TABLE public.atendimentos 
ADD COLUMN IF NOT EXISTS sinal_vital_record_id UUID,
ADD COLUMN IF NOT EXISTS main_cid10_id UUID,
ADD COLUMN IF NOT EXISTS main_ciap2_id UUID,
ADD COLUMN IF NOT EXISTS main_clinical_status VARCHAR(20),
ADD COLUMN IF NOT EXISTS main_verification_status VARCHAR(20);
```

#### Tabela `profissionais_saude`
```sql
ALTER TABLE public.profissionais_saude 
ADD COLUMN IF NOT EXISTS conselho_profissional_id UUID;
```

#### Tabelas `cidades` e `estados`
```sql
ALTER TABLE public.cidades 
ADD COLUMN IF NOT EXISTS codigo_fhir VARCHAR(50),
ADD COLUMN IF NOT EXISTS descricao_fhir TEXT;

ALTER TABLE public.estados 
ADD COLUMN IF NOT EXISTS codigo_fhir VARCHAR(50),
ADD COLUMN IF NOT EXISTS descricao_fhir TEXT;
```

#### Tabela `sigtap_ocupacao`
```sql
ALTER TABLE public.sigtap_ocupacao 
ADD COLUMN IF NOT EXISTS grande_grupo VARCHAR(10),
ADD COLUMN IF NOT EXISTS subgrupo_principal VARCHAR(10),
ADD COLUMN IF NOT EXISTS subgrupo VARCHAR(10),
ADD COLUMN IF NOT EXISTS familia VARCHAR(10),
ADD COLUMN IF NOT EXISTS descricao_fhir TEXT,
ADD COLUMN IF NOT EXISTS codigo_cbo_completo VARCHAR(20);
```

---

## ⚠️ Breaking Changes

Nenhum breaking change nesta release. Todas as alterações são retrocompatíveis.

---

## 🔄 Migração

### Passos para Atualização

1. **Backup do Banco de Dados**
   ```bash
   pg_dump -U postgres upsaude > backup_pre_1.0.0.12.sql
   ```

2. **Aplicar Migrações**
   - As migrações Flyway serão aplicadas automaticamente na inicialização
   - Verificar logs para confirmação de aplicação das migrações

3. **Validar Testes**
   ```bash
   mvn test -Dtest="com.upsaude.regression.**"
   ```

4. **Verificar Integrações**
   - Validar eventos de integração sendo gerados corretamente
   - Verificar serialização JSON em endpoints de resposta

---

## 📚 Documentação

- Documentação de integrações atualizada
- Guia de migração disponível
- Exemplos de uso dos novos recursos

---

## 🙏 Agradecimentos

Equipe de desenvolvimento e testes pela dedicação na correção e validação das melhorias.

---

## 📞 Suporte

Para questões ou problemas relacionados a esta release, entre em contato com a equipe de desenvolvimento.

---

**Versão Anterior:** 1.0.0.11  
**Versão Atual:** 1.0.0.12  
**Próxima Versão Planejada:** 1.0.1.0
