# Scripts SQL para Ajuste das Tabelas

Este diretório contém scripts SQL para ajustar as tabelas do banco de dados Supabase.

## Scripts Disponíveis

### 1. `adicionar_colunas_fhir_cidades.sql`

**Objetivo**: Adicionar as colunas FHIR faltantes na tabela `cidades`.

**Como executar**:
1. Acesse o Supabase Dashboard
2. Vá em SQL Editor
3. Cole o conteúdo do arquivo `adicionar_colunas_fhir_cidades.sql`
4. Execute o script

**Ou via psql**:
```bash
psql -h <host> -U <user> -d <database> -f scripts/adicionar_colunas_fhir_cidades.sql
```

**O que faz**:
- **APENAS ADICIONA** 5 colunas FHIR na tabela **EXISTENTE** `cidades`:
  - `codigo_fhir` VARCHAR(20)
  - `fhir_code_system` VARCHAR(200)
  - `regiao_saude` VARCHAR(100)
  - `macrorregiao_saude` VARCHAR(100)
  - `data_ultima_sincronizacao_fhir` TIMESTAMPTZ
- **NÃO altera** o nome da tabela
- **NÃO cria** nova tabela
- **NÃO remove** dados existentes
- Verifica se as colunas já existem antes de adicionar (idempotente)
- Mostra um resumo das colunas criadas

### 2. `verificar_estrutura_tabelas.sql`

**Objetivo**: Verificar a estrutura das tabelas e identificar colunas faltantes.

**Como executar**:
1. Acesse o Supabase Dashboard
2. Vá em SQL Editor
3. Cole o conteúdo do arquivo `verificar_estrutura_tabelas.sql`
4. Execute o script

**O que faz**:
- Mostra a estrutura completa das tabelas `cidades`, `estados` e `integracao_eventos`
- Identifica quais colunas FHIR estão faltando
- Mostra índices da tabela `integracao_eventos`
- Fornece um resumo do status das colunas

## Problema Identificado

A tabela `cidades` está faltando 5 colunas FHIR que são definidas na entidade Java `Cidades.java`:

```java
@Column(name = "codigo_fhir", length = 20)
private String codigoFhir;

@Column(name = "fhir_code_system", length = 200)
private String fhirCodeSystem;

@Column(name = "regiao_saude", length = 100)
private String regiaoSaude;

@Column(name = "macrorregiao_saude", length = 100)
private String macrorregiaoSaude;

@Column(name = "data_ultima_sincronizacao_fhir")
private java.time.OffsetDateTime dataUltimaSincronizacaoFhir;
```

## Status Atual

- ✅ **Tabela `integracao_eventos`**: Criada com sucesso, todas as colunas e índices corretos
- ✅ **Tabela `estados`**: Colunas FHIR criadas com sucesso
- ❌ **Tabela `cidades`**: Colunas FHIR faltando (devido a timeout nas operações DDL via API)

## Solução

Execute o script `adicionar_colunas_fhir_cidades.sql` diretamente no Supabase SQL Editor para adicionar as colunas faltantes.

## Notas Importantes

- ✅ **Todos os scripts são idempotentes** (podem ser executados múltiplas vezes sem causar erros)
- ✅ **As colunas FHIR são todas nullable** para garantir retrocompatibilidade
- ✅ **A tabela `cidades` mantém o mesmo nome** - não há alteração de nome
- ✅ **Nenhuma tabela é criada ou removida** - apenas colunas são adicionadas
- ✅ **Nenhum dado é removido** - apenas novas colunas são adicionadas
- ⚠️ A tabela `cidades` está atualmente vazia (0 registros), então a operação deve ser rápida
