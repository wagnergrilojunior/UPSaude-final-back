# Tabelas SIGTAP - Documentação Completa

Esta documentação descreve todas as tabelas do banco de dados SIGTAP, suas estruturas, campos, relacionamentos e finalidades.

## Índice

1. [Visão Geral](#visão-geral)
2. [Como Pesquisar Medicamentos, Exames e Procedimentos](#como-pesquisar-medicamentos-exames-e-procedimentos) ⭐ **Início Rápido**
3. [Dados Principais e Exemplos de Uso](#dados-principais-e-exemplos-de-uso)
4. [Exemplos de Chamadas de API](#exemplos-de-chamadas-de-api)
5. [Tabelas de Referência (Cadastros Básicos)](#tabelas-de-referência)
6. [Tabelas de Procedimentos](#tabelas-de-procedimentos)
7. [Tabelas Relacionais](#tabelas-relacionais)
8. [Tabelas de Compatibilidade](#tabelas-de-compatibilidade)
9. [Tabelas de Descrições](#tabelas-de-descrições)
10. [Relacionamentos Principais](#relacionamentos-principais)

## Visão Geral

O SIGTAP possui aproximadamente **40+ tabelas** organizadas em:

- **Tabelas de Referência**: Dados básicos independentes (grupos, serviços, ocupações, etc.)
- **Tabela Principal**: Procedimentos
- **Tabelas Relacionais**: Relacionamentos entre procedimentos e outras entidades
- **Tabelas de Compatibilidade**: Regras de compatibilidade entre procedimentos
- **Tabelas de Descrições**: Descrições detalhadas de procedimentos

---

## Tabelas de Referência

### 1. sigtap_grupo

Grupo de procedimentos (1º nível de agregação).

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String, 20): Código do grupo (ex: "03")
- `nome` (String, 255): Nome do grupo (ex: "PROCEDIMENTOS CLINICOS")
- `competencia_inicial` (String, 6): Competência inicial (formato AAAAMM)
- `competencia_final` (String, 6): Competência final (NULL = ativo)
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Índices:**
- `idx_sigtap_grupo_nome`: Índice no campo nome
- `uk_sigtap_grupo_codigo_oficial`: Constraint único no código oficial

**Exemplo:**
```
Código: "03"
Nome: "PROCEDIMENTOS CLINICOS"
```

---

### 2. sigtap_subgrupo

Subgrupo de procedimentos (2º nível de agregação).

**Campos:**
- `id` (UUID): Chave primária
- `grupo_id` (UUID): Referência ao grupo pai (FK)
- `codigo_oficial` (String, 20): Código do subgrupo (ex: "0301")
- `nome` (String, 255): Nome do subgrupo
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_grupo`

**Índices:**
- `idx_sigtap_subgrupo_grupo_id`: Índice no grupo_id
- `idx_sigtap_subgrupo_nome`: Índice no nome
- `uk_sigtap_subgrupo_grupo_codigo`: Constraint único (grupo_id + codigo_oficial)

**Exemplo:**
```
Código: "0301"
Nome: "CONSULTA MEDICA"
Grupo: "03 - PROCEDIMENTOS CLINICOS"
```

---

### 3. sigtap_forma_organizacao

Forma de organização do procedimento (3º nível de agregação).

**Campos:**
- `id` (UUID): Chave primária
- `subgrupo_id` (UUID): Referência ao subgrupo pai (FK)
- `codigo_oficial` (String, 20): Código da forma de organização
- `nome` (String, 255): Nome da forma de organização
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_subgrupo`

**Índices:**
- `idx_sigtap_forma_org_subgrupo_id`: Índice no subgrupo_id
- `idx_sigtap_forma_org_nome`: Índice no nome
- `uk_sigtap_forma_org_subgrupo_codigo`: Constraint único (subgrupo_id + codigo_oficial)

**Exemplo:**
```
Código: "01"
Nome: "AMBULATORIAL"
Subgrupo: "0301 - CONSULTA MEDICA"
```

---

### 4. sigtap_servico

Serviços/Exames disponíveis no SIGTAP.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String, 3): Código do serviço (ex: "01")
- `nome` (String, 100): Nome do serviço (ex: "SERVIÇO HOSPITALAR")
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Índices:**
- `idx_sigtap_servico_nome`: Índice no nome
- `uk_sigtap_servico_codigo_oficial`: Constraint único no código oficial

**Exemplo:**
```
Código: "01"
Nome: "SERVIÇO HOSPITALAR"
```

---

### 5. sigtap_renases

RENASES - Rede Nacional de Atenção Especializada em Saúde.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String, 10): Código do RENASES
- `nome` (String, 100): Nome do RENASES
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Índices:**
- `idx_sigtap_renases_nome`: Índice no nome
- `uk_sigtap_renases_codigo_oficial`: Constraint único no código oficial

**Exemplo:**
```
Código: "01"
Nome: "CENTRO DE CARDIOLOGIA"
```

---

### 6. sigtap_ocupacao

Ocupações profissionais (CBO - Classificação Brasileira de Ocupações).

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String, 6): Código CBO (ex: "225110")
- `nome` (String, 150): Nome da ocupação (ex: "MÉDICO CLINICO GERAL")
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Índices:**
- `idx_sigtap_ocupacao_nome`: Índice no nome
- `uk_sigtap_ocupacao_codigo_oficial`: Constraint único no código oficial

**Exemplo:**
```
Código: "225110"
Nome: "MÉDICO CLINICO GERAL"
```

---

### 7. sigtap_habilitacao

Habilitações necessárias para execução de procedimentos.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String, 4): Código da habilitação
- `nome` (String, 150): Nome da habilitação
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Índices:**
- `idx_sigtap_habilitacao_nome`: Índice no nome
- `uk_sigtap_habilitacao_codigo_comp`: Constraint único (codigo_oficial + competencia_inicial)

---

### 8. sigtap_modalidade

Modalidades de procedimento.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String, 2): Código da modalidade
- `nome` (String, 100): Nome da modalidade
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Índices:**
- `idx_sigtap_modalidade_nome`: Índice no nome
- `uk_sigtap_modalidade_codigo_comp`: Constraint único (codigo_oficial + competencia_inicial)

---

### 9. sigtap_tuss

TUSS - Terminologia Unificada da Saúde Suplementar.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String, 10): Código TUSS
- `nome` (String, 100): Nome do TUSS
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Índices:**
- `idx_sigtap_tuss_nome`: Índice no nome
- `uk_sigtap_tuss_codigo_oficial`: Constraint único no código oficial

---

### 10. sigtap_financiamento

Tipos de financiamento.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String): Código do financiamento
- `nome` (String): Nome do financiamento
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

### 11. sigtap_tipo_leito

Tipos de leito hospitalar.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String): Código do tipo de leito
- `nome` (String): Nome do tipo de leito
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

### 12. sigtap_detalhe

Detalhes/tipos de detalhamento de procedimentos.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String): Código do detalhe
- `nome` (String): Nome do detalhe
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

### 13. sigtap_registro

Tipos de registro.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String): Código do registro
- `nome` (String): Nome do registro
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

### 14. sigtap_grupo_habilitacao

Grupos de habilitações que devem ser atendidas em conjunto.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String): Código do grupo
- `nome` (String): Nome do grupo
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

### 15. sigtap_rubrica

Rubricas de financiamento.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String): Código da rubrica
- `nome` (String): Nome da rubrica
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

### 16. sigtap_sia_sih

Mapeamento SIA/SIH.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String): Código SIA/SIH
- `nome` (String): Nome/Descrição
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

### 17. sigtap_rede_atencao

Redes de atenção à saúde.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String): Código da rede
- `nome` (String): Nome da rede
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

### 18. sigtap_componente_rede

Componentes de rede de atenção.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String): Código do componente
- `nome` (String): Nome do componente
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

### 19. sigtap_servico_classificacao

Classificações de serviços.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String): Código da classificação
- `nome` (String): Nome da classificação
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

### 20. sigtap_regra_condicionada

Regras condicionadas.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String): Código da regra
- `nome` (String): Nome da regra
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

## Tabelas de Procedimentos

### 21. sigtap_procedimento

Tabela principal de procedimentos (medicamentos, exames, consultas, etc.).

**Campos:**
- `id` (UUID): Chave primária
- `forma_organizacao_id` (UUID): Referência à forma de organização (FK)
- `codigo_oficial` (String, 20): Código do procedimento (ex: "0301010010")
- `nome` (String, 255): Nome do procedimento
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final (NULL = ativo)
- `sexo_permitido` (String, 30): Restrição de sexo (M/F/AMBOS/I)
- `idade_minima` (Integer): Idade mínima em meses (9999 = não aplica)
- `idade_maxima` (Integer): Idade máxima em meses (9999 = não aplica)
- `media_dias_internacao` (Integer): Média de dias de internação
- `quantidade_maxima_dias` (Integer): Quantidade máxima de dias
- `limite_maximo` (Integer): Limite máximo
- `valor_servico_hospitalar` (BigDecimal, 14,2): Valor para ambiente hospitalar
- `valor_servico_ambulatorial` (BigDecimal, 14,2): Valor para ambiente ambulatorial
- `valor_servico_profissional` (BigDecimal, 14,2): Valor profissional
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_forma_organizacao`
- `Um para Muitos` com todas as tabelas relacionais de procedimento

**Índices:**
- `idx_sigtap_procedimento_nome`: Índice no nome
- `idx_sigtap_procedimento_forma_org`: Índice no forma_organizacao_id
- `uk_sigtap_procedimento_codigo_comp_ini`: Constraint único (codigo_oficial + competencia_inicial)

**Exemplo:**
```
Código: "0301010010"
Nome: "CONSULTA MÉDICA EM ATENDIMENTO AMBULATORIAL"
Forma de Organização: "01 - AMBULATORIAL"
Valor Ambulatorial: 23.50
```

---

### 22. sigtap_procedimento_detalhe

Detalhes de procedimentos.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `detalhe_id` (UUID): Referência ao tipo de detalhe (FK)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `sigtap_detalhe`

---

### 23. sigtap_procedimento_detalhe_item

Itens de detalhes de procedimentos.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_detalhe_id` (UUID): Referência ao procedimento_detalhe (FK)
- (outros campos específicos conforme necessário)
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

## Tabelas Relacionais

### 24. sigtap_procedimento_cid

Relaciona procedimentos com CID (Classificação Internacional de Doenças).

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `cid10_subcategorias_id` (UUID): Referência ao CID (FK)
- `principal` (Boolean): Se é CID principal para este procedimento
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `cid10_subcategorias` (tabela de CID)

**Índices:**
- `idx_sigtap_proc_cid_procedimento_id`: Índice no procedimento_id
- `idx_sigtap_proc_cid_cid10_id`: Índice no cid10_subcategorias_id
- `uk_sigtap_proc_cid_proc_cid10_comp`: Constraint único (procedimento_id + cid10_subcategorias_id + competencia_inicial)

**Exemplo:**
```
Procedimento: "0301010010 - CONSULTA MÉDICA"
CID: "I10 - HIPERTENSÃO ARTERIAL ESSENCIAL"
Principal: true
```

---

### 25. sigtap_procedimento_ocupacao

Relaciona procedimentos com ocupações (quem pode executar o procedimento).

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `ocupacao_id` (UUID): Referência à ocupação (FK)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `sigtap_ocupacao`

**Exemplo:**
```
Procedimento: "0301010010 - CONSULTA MÉDICA"
Ocupação: "225110 - MÉDICO CLINICO GERAL"
```

---

### 26. sigtap_procedimento_habilitacao

Relaciona procedimentos com habilitações necessárias.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `habilitacao_id` (UUID): Referência à habilitação (FK)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `sigtap_habilitacao`

---

### 27. sigtap_procedimento_leito

Relaciona procedimentos com tipos de leito.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `tipo_leito_id` (UUID): Referência ao tipo de leito (FK)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `sigtap_tipo_leito`

---

### 28. sigtap_procedimento_servico

Relaciona procedimentos com serviços.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `servico_id` (UUID): Referência ao serviço (FK)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `sigtap_servico`

---

### 29. sigtap_procedimento_renases

Relaciona procedimentos com RENASES.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `renases_id` (UUID): Referência ao RENASES (FK)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `sigtap_renases`

---

### 30. sigtap_procedimento_tuss

Relaciona procedimentos com TUSS.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `tuss_id` (UUID): Referência ao TUSS (FK)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `sigtap_tuss`

---

### 31. sigtap_procedimento_modalidade

Relaciona procedimentos com modalidades.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `modalidade_id` (UUID): Referência à modalidade (FK)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `sigtap_modalidade`

---

### 32. sigtap_procedimento_registro

Relaciona procedimentos com registros.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `registro_id` (UUID): Referência ao registro (FK)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `sigtap_registro`

---

### 33. sigtap_procedimento_incremento

Incrementos de procedimentos.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- (campos específicos de incremento)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`

---

### 34. sigtap_procedimento_sia_sih

Relaciona procedimentos com mapeamento SIA/SIH.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `sia_sih_id` (UUID): Referência ao SIA/SIH (FK)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `sigtap_sia_sih`

---

### 35. sigtap_procedimento_componente_rede

Relaciona procedimentos com componentes de rede.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `componente_rede_id` (UUID): Referência ao componente de rede (FK)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `sigtap_componente_rede`

---

### 36. sigtap_procedimento_origem

Relaciona procedimentos com origens.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- (campos específicos de origem)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`

---

### 37. sigtap_procedimento_regra_condicionada

Relaciona procedimentos com regras condicionadas.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `regra_condicionada_id` (UUID): Referência à regra condicionada (FK)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`
- `Muitos para Um` com `sigtap_regra_condicionada`

---

## Tabelas de Compatibilidade

### 38. sigtap_compatibilidade_possivel

Tipos de compatibilidade possíveis entre procedimentos.

**Campos:**
- `id` (UUID): Chave primária
- `codigo_oficial` (String): Código da compatibilidade possível
- `tipo_compatibilidade` (String): Tipo de compatibilidade
- `nome` (String): Nome/descrição
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

### 39. sigtap_compatibilidade

Procedimentos que podem ser executados juntos (compatibilidades).

**Campos:**
- `id` (UUID): Chave primária
- `compatibilidade_possivel_id` (UUID): Referência ao tipo de compatibilidade (FK)
- `procedimento_principal_id` (UUID): Referência ao procedimento principal (FK)
- `procedimento_secundario_id` (UUID): Referência ao procedimento secundário (FK)
- `quantidade_permitida` (Integer): Quantidade permitida
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_compatibilidade_possivel`
- `Muitos para Um` com `sigtap_procedimento` (procedimento_principal)
- `Muitos para Um` com `sigtap_procedimento` (procedimento_secundario)

**Exemplo:**
```
Procedimento Principal: "0301010010 - CONSULTA MÉDICA"
Procedimento Secundário: "0201010010 - EXAME COMPLEMENTAR"
Tipo: "PERMITIDA"
Quantidade Permitida: 1
```

---

### 40. sigtap_excecao_compatibilidade

Exceções que negam compatibilidades normalmente permitidas.

**Campos:**
- `id` (UUID): Chave primária
- `compatibilidade_id` (UUID): Referência à compatibilidade (FK)
- (campos específicos de exceção)
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_compatibilidade`

---

## Tabelas de Descrições

### 41. sigtap_descricao

Descrições completas de procedimentos.

**Campos:**
- `id` (UUID): Chave primária
- `procedimento_id` (UUID): Referência ao procedimento (FK)
- `descricao_completa` (TEXT): Descrição completa do procedimento
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_procedimento`

**Índices:**
- `idx_sigtap_descricao_procedimento_id`: Índice no procedimento_id
- `idx_sigtap_descricao_competencia`: Índice na competência inicial

---

### 42. sigtap_descricao_detalhe

Descrições de detalhes de procedimentos.

**Campos:**
- `id` (UUID): Chave primária
- `detalhe_id` (UUID): Referência ao detalhe (FK)
- `descricao_completa` (TEXT): Descrição completa do detalhe
- `competencia_inicial` (String, 6): Competência inicial
- `competencia_final` (String, 6): Competência final
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

**Relacionamentos:**
- `Muitos para Um` com `sigtap_detalhe`

---

## Tabelas Auxiliares

### 43. sigtap_sync_status

Status de sincronização do SIGTAP.

**Campos:**
- `id` (UUID): Chave primária
- `competencia` (String, 6): Competência sendo sincronizada
- `status` (String): Status da sincronização
- `arquivo_atual` (String): Arquivo sendo processado
- `linhas_processadas` (Long): Quantidade de linhas processadas
- `total_linhas` (Long): Total de linhas esperadas
- `iniciado_em` (OffsetDateTime): Data de início
- `finalizado_em` (OffsetDateTime): Data de finalização
- `erros` (TEXT): Erros encontrados (JSON)
- `ativo` (Boolean): Status ativo/inativo
- `criado_em` (OffsetDateTime): Data de criação
- `atualizado_em` (OffsetDateTime): Data de atualização

---

## Relacionamentos Principais

### Hierarquia de Procedimentos

```
sigtap_grupo (1º nível)
    └── sigtap_subgrupo (2º nível)
            └── sigtap_forma_organizacao (3º nível)
                    └── sigtap_procedimento (procedimento final)
```

**Exemplo:**
```
Grupo: "03 - PROCEDIMENTOS CLINICOS"
  └── Subgrupo: "0301 - CONSULTA MEDICA"
        └── Forma Organização: "01 - AMBULATORIAL"
              └── Procedimento: "0301010010 - CONSULTA MÉDICA EM ATENDIMENTO AMBULATORIAL"
```

### Relacionamentos de Procedimentos

Um procedimento pode ter relacionamentos com:

- **CIDs** (`sigtap_procedimento_cid`): Doenças relacionadas ao procedimento
- **Ocupações** (`sigtap_procedimento_ocupacao`): Profissionais que podem executar
- **Habilitações** (`sigtap_procedimento_habilitacao`): Habilitações necessárias
- **Serviços** (`sigtap_procedimento_servico`): Serviços relacionados
- **RENASES** (`sigtap_procedimento_renases`): Redes de atenção relacionadas
- **TUSS** (`sigtap_procedimento_tuss`): Códigos TUSS equivalentes
- **Modalidades** (`sigtap_procedimento_modalidade`): Modalidades do procedimento
- **Compatibilidades** (`sigtap_compatibilidade`): Procedimentos que podem ser executados juntos

---

## Convenções de Nomenclatura

### Tabelas
- Prefixo `sigtap_` para todas as tabelas
- Nomes em minúsculas com underscore
- Nomes descritivos e claros

### Campos Padrão
Todas as tabelas herdam da `BaseEntityWithoutTenant`:
- `id` (UUID): Chave primária gerada automaticamente
- `criado_em` (OffsetDateTime): Data/hora de criação (automático)
- `atualizado_em` (OffsetDateTime): Data/hora de atualização (automático)
- `ativo` (Boolean): Status ativo/inativo (padrão: true)

### Campos Específicos SIGTAP
- `codigo_oficial`: Código original do SIGTAP
- `competencia_inicial`: Competência de início (formato AAAAMM)
- `competencia_final`: Competência de fim (NULL = ativo)
- `nome`: Nome/descrição da entidade

---

## Consultas SQL Úteis

### Buscar Procedimento Completo com Hierarquia

```sql
SELECT 
    p.codigo_oficial,
    p.nome,
    fo.nome AS forma_organizacao,
    sg.nome AS subgrupo,
    g.nome AS grupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE p.codigo_oficial = '0301010010';
```

### Buscar CIDs de um Procedimento

```sql
SELECT 
    c.codigo_oficial,
    c.nome,
    pc.principal
FROM sigtap_procedimento_cid pc
JOIN cid10_subcategorias c ON pc.cid10_subcategorias_id = c.id
WHERE pc.procedimento_id = (
    SELECT id FROM sigtap_procedimento WHERE codigo_oficial = '0301010010'
);
```

### Buscar Ocupações de um Procedimento

```sql
SELECT 
    o.codigo_oficial,
    o.nome
FROM sigtap_procedimento_ocupacao po
JOIN sigtap_ocupacao o ON po.ocupacao_id = o.id
WHERE po.procedimento_id = (
    SELECT id FROM sigtap_procedimento WHERE codigo_oficial = '0301010010'
);
```

### Buscar Compatibilidades de um Procedimento

```sql
SELECT 
    pp.codigo_oficial AS procedimento_principal,
    pp.nome AS nome_principal,
    ps.codigo_oficial AS procedimento_secundario,
    ps.nome AS nome_secundario,
    cp.tipo_compatibilidade,
    c.quantidade_permitida
FROM sigtap_compatibilidade c
JOIN sigtap_compatibilidade_possivel cp ON c.compatibilidade_possivel_id = cp.id
JOIN sigtap_procedimento pp ON c.procedimento_principal_id = pp.id
JOIN sigtap_procedimento ps ON c.procedimento_secundario_id = ps.id
WHERE pp.codigo_oficial = '0301010010';
```

---

## Como Pesquisar Medicamentos, Exames e Procedimentos

### 📋 Guia Rápido de Pesquisa

#### 🏥 Medicamentos (Grupo 06)

**Via API REST:**
```bash
# Buscar medicamentos por nome
GET /v1/sigtap/procedimentos?q=dipirona&page=0&size=20

# Buscar todos os procedimentos do grupo de medicamentos
GET /v1/sigtap/procedimentos?q=06
```

**Via SQL:**
```sql
SELECT p.codigo_oficial, p.nome, p.valor_servico_ambulatorial
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '06'
ORDER BY p.nome;
```

**Descrição:** Todos os medicamentos do SUS estão catalogados no Grupo 06. Use este grupo para pesquisar fármacos disponíveis.

---

#### 🔬 Exames Diagnósticos (Grupo 02)

**Via API REST:**
```bash
# Buscar exames por nome
GET /v1/sigtap/procedimentos?q=exame%20laboratorial&page=0&size=50

# Buscar exame específico
GET /v1/sigtap/procedimentos/0201020041
```

**Via SQL:**
```sql
SELECT p.codigo_oficial, p.nome, p.valor_servico_ambulatorial
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '02' AND p.nome ILIKE '%exame%'
ORDER BY p.codigo_oficial;
```

**Subgrupos do Grupo 02 (Procedimentos com finalidade diagnóstica):**
- 0201 - Coleta de material
- 0202 - Diagnóstico em laboratório clínico
- 0203 - Diagnóstico por anatomia patológica e citopatologia
- 0204 - Diagnóstico por radiologia
- 0205 - Diagnóstico por ultrasonografia
- 0206 - Diagnóstico por tomografia
- 0207 - Diagnóstico por ressonância magnética
- 0208 - Diagnóstico por medicina nuclear in vivo
- 0209 - Diagnóstico por endoscopia
- 0210 - Diagnóstico por radiologia intervencionista
- 0211 - Métodos diagnósticos em especialidades
- 0212 - Diagnóstico e procedimentos especiais em hemoterapia
- 0213 - Diagnóstico em vigilância epidemiológica e ambiental
- 0214 - Diagnóstico por teste rápido

**Exemplos de Exames:**
- `0201020041` - COLETA DE MATERIAL PARA EXAME LABORATORIAL
- `0202031225` - EXAME LABORATORIAL PARA DOENÇA DE GAUCHER I (R$ 80,00)
- `0202040038` - EXAME COPROLOGICO FUNCIONAL (R$ 3,04)

---

#### 👨‍⚕️ Consultas Médicas (Grupo 03, Subgrupo 01)

**Via API REST:**
```bash
# Buscar consultas
GET /v1/sigtap/procedimentos?q=consulta%20medica&competencia=202512&page=0&size=20

# Buscar consulta específica com detalhes
GET /v1/sigtap/procedimentos/0301010056?competencia=202512
```

**Via SQL:**
```sql
SELECT p.codigo_oficial, p.nome, p.valor_servico_ambulatorial
FROM sigtap_procedimento p
WHERE p.nome ILIKE '%consulta%medica%'
ORDER BY p.codigo_oficial;
```

**Exemplos de Consultas:**
- `0301010056` - CONSULTA MEDICA EM SAUDE DO TRABALHADOR (R$ 10,00)
- `0301010064` - CONSULTA MEDICA EM ATENÇÃO PRIMÁRIA (R$ 0,00)
- `0301010072` - CONSULTA MEDICA EM ATENÇÃO ESPECIALIZADA (R$ 10,00)

---

#### 🔍 Busca Geral por Código ou Nome

**Via API REST:**
```bash
# Busca genérica (busca em código e nome)
GET /v1/sigtap/procedimentos?q=030101&competencia=202512&page=0&size=20

# Com ordenação
GET /v1/sigtap/procedimentos?q=exame&sort=nome,asc&page=0&size=20
```

**Parâmetros disponíveis:**
- `q`: Termo de busca (código ou nome)
- `competencia`: Competência no formato AAAAMM (ex: 202512)
- `page`: Número da página (padrão: 0)
- `size`: Tamanho da página (padrão: 20)
- `sort`: Ordenação (ex: `codigoOficial,asc` ou `nome,desc`)

---

## Dados Principais e Exemplos de Uso

### Grupos de Procedimentos

O SIGTAP organiza os procedimentos em **9 grupos principais**:

| Código | Nome |
|--------|------|
| 01 | Ações de promoção e prevenção em saúde |
| 02 | Procedimentos com finalidade diagnóstica |
| 03 | Procedimentos clínicos |
| 04 | Procedimentos cirúrgicos |
| 05 | Transplantes de orgãos, tecidos e células |
| 06 | **Medicamentos** |
| 07 | Órteses, próteses e materiais especiais |
| 08 | Ações complementares da atenção à saúde |
| 09 | Procedimentos para Ofertas de Cuidados Integrados |

### Subgrupos do Grupo 02 (Procedimentos com finalidade diagnóstica)

| Código | Nome |
|--------|------|
| 0201 | Coleta de material |
| 0202 | Diagnóstico em laboratório clínico |
| 0203 | Diagnóstico por anatomia patológica e citopatologia |
| 0204 | Diagnóstico por radiologia |
| 0205 | Diagnóstico por ultrasonografia |
| 0206 | Diagnóstico por tomografia |
| 0207 | Diagnóstico por ressonância magnética |
| 0208 | Diagnóstico por medicina nuclear in vivo |
| 0209 | Diagnóstico por endoscopia |
| 0210 | Diagnóstico por radiologia intervencionista |
| 0211 | Métodos diagnósticos em especialidades |
| 0212 | Diagnóstico e procedimentos especiais em hemoterapia |
| 0213 | Diagnóstico em vigilância epidemiológica e ambiental |
| 0214 | Diagnóstico por teste rápido |

### Subgrupos do Grupo 03 (Procedimentos Clínicos)

| Código | Nome |
|--------|------|
| 0301 | Consultas / Atendimentos / Acompanhamentos |
| 0302 | Fisioterapia |
| 0303 | Tratamentos clínicos (outras especialidades) |
| 0304 | Tratamento em oncologia |
| 0305 | Tratamento em nefrologia |
| 0306 | Hemoterapia |
| 0307 | Tratamentos odontológicos |
| 0308 | Tratamento de lesões, envenenamentos e outros, decorrentes de causas externas |
| 0309 | Terapias especializadas |
| 0310 | Parto e nascimento |
| 0311 | Cuidados Paliativos |

### Modalidades

| Código | Nome |
|--------|------|
| 01 | Ambulatorial |
| 02 | Hospitalar |
| 03 | Hospital Dia |
| 06 | Atenção Domiciliar |

### Estatísticas

- **Total de Procedimentos**: 4.957 procedimentos cadastrados
- **Grupos**: 9 grupos principais
- **Subgrupos**: Múltiplos subgrupos por grupo
- **Habilitações**: 20+ tipos de habilitações cadastradas
- **Ocupações (CBO)**: 20+ ocupações relacionadas

---

## Exemplos de Chamadas de API

### Exemplo 1: Buscar um Medicamento Específico

```bash
# Via código
curl -X GET "http://localhost:8080/v1/sigtap/procedimentos/0601010101" \
  -H "Authorization: Bearer <token>"

# Via busca por nome
curl -X GET "http://localhost:8080/v1/sigtap/procedimentos?q=dipirona&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

### Exemplo 2: Listar Todos os Grupos

```bash
curl -X GET "http://localhost:8080/v1/sigtap/grupos" \
  -H "Authorization: Bearer <token>"
```

**Resposta esperada:**
```json
[
  {
    "id": "...",
    "codigoOficial": "01",
    "nome": "Ações de promoção e prevenção em saúde",
    "competenciaInicial": "202512",
    "competenciaFinal": null
  },
  {
    "id": "...",
    "codigoOficial": "02",
    "nome": "Procedimentos com finalidade diagnóstica",
    "competenciaInicial": "202512",
    "competenciaFinal": null
  },
  ...
]
```

### Exemplo 3: Buscar Exames Laboratoriais

```bash
# Buscar exames
curl -X GET "http://localhost:8080/v1/sigtap/procedimentos?q=exame%20laboratorial&competencia=202512&page=0&size=50" \
  -H "Authorization: Bearer <token>"
```

### Exemplo 4: Buscar Consultas com Filtros

```bash
# Buscar consultas médicas
curl -X GET "http://localhost:8080/v1/sigtap/procedimentos?q=consulta&competencia=202512&page=0&size=20&sort=nome,asc" \
  -H "Authorization: Bearer <token>"
```

### Exemplo 5: Buscar Subgrupos de um Grupo

```bash
# Buscar subgrupos do Grupo 03 (Procedimentos Clínicos)
curl -X GET "http://localhost:8080/v1/sigtap/subgrupos?grupoCodigo=03&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

**Resposta esperada:**
```json
{
  "content": [
    {
      "id": "...",
      "codigoOficial": "01",
      "nome": "Consultas / Atendimentos / Acompanhamentos",
      "competenciaInicial": "202512",
      "competenciaFinal": null,
      "grupoCodigo": "03",
      "grupoNome": "Procedimentos clínicos"
    },
    ...
  ],
  "totalElements": 11
}
```

### Exemplo 6: Obter Procedimento Detalhado

```bash
curl -X GET "http://localhost:8080/v1/sigtap/procedimentos/0301010056?competencia=202512" \
  -H "Authorization: Bearer <token>"
```

**Resposta esperada:**
```json
{
  "procedimento": {
    "id": "...",
    "codigoOficial": "0301010056",
    "nome": "CONSULTA MEDICA EM SAUDE DO TRABALHADOR",
    "valorServicoAmbulatorial": 10.00,
    "grupoCodigo": "03",
    "grupoNome": "Procedimentos clínicos",
    "subgrupoCodigo": "01",
    "subgrupoNome": "Consultas / Atendimentos / Acompanhamentos",
    ...
  },
  "detalhe": {
    ...
  }
}
```

### Exemplo 7: Buscar Ocupações (CBO)

```bash
curl -X GET "http://localhost:8080/v1/sigtap/ocupacoes?q=médico&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

### Exemplo 8: Buscar Habilitações

```bash
curl -X GET "http://localhost:8080/v1/sigtap/habilitacoes?q=psiquiatria&competencia=202512&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

### Exemplo 9: Buscar RENASES

```bash
curl -X GET "http://localhost:8080/v1/sigtap/renases?q=oftalmologia&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

### Exemplo 10: Buscar Compatibilidades

```bash
# Buscar procedimentos compatíveis com uma consulta médica
curl -X GET "http://localhost:8080/v1/sigtap/compatibilidades?codigoProcedimentoPrincipal=0301010056&competencia=202512&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

---

## Principais Dados Cadastrados

### Grupos e Quantidade de Procedimentos

| Grupo | Nome | Exemplos |
|-------|------|----------|
| 01 | Ações de promoção e prevenção em saúde | Ações coletivas, vigilância |
| 02 | Procedimentos com finalidade diagnóstica | Exames, biópsias, diagnósticos |
| 03 | Procedimentos clínicos | Consultas, tratamentos, fisioterapia |
| 04 | Procedimentos cirúrgicos | Cirurgias de diversos tipos |
| 05 | Transplantes | Transplantes de órgãos e tecidos |
| **06** | **Medicamentos** | **Todos os medicamentos do SUS** |
| 07 | Órteses, próteses e materiais especiais | Próteses, órteses |
| 08 | Ações complementares | Ações diversas |
| 09 | Ofertas de Cuidados Integrados | Cuidados integrados |

### Exemplos de Procedimentos por Categoria

#### Consultas Médicas (Grupo 03, Subgrupo 01)
- `0301010056` - CONSULTA MEDICA EM SAUDE DO TRABALHADOR (R$ 10,00)
- `0301010064` - CONSULTA MEDICA EM ATENÇÃO PRIMÁRIA (R$ 0,00)
- `0301010072` - CONSULTA MEDICA EM ATENÇÃO ESPECIALIZADA (R$ 10,00)

#### Exames Diagnósticos (Grupo 02)
- `0201020041` - COLETA DE MATERIAL PARA EXAME LABORATORIAL
- `0202031225` - EXAME LABORATORIAL PARA DOENÇA DE GAUCHER I (R$ 80,00)
- `0202031233` - EXAME LABORATORIAL PARA DOENÇA DE GAUCHER II (R$ 120,00)
- `0202040038` - EXAME COPROLOGICO FUNCIONAL (R$ 3,04)

#### Biópsias (Grupo 02, Subgrupo 01)
- `0201010011` - AMNIOCENTESE (R$ 2,20)
- `0201010020` - BIOPSIA / PUNCAO DE TUMOR SUPERFICIAL DA PELE (R$ 14,10)
- `0201010038` - BIOPSIA CIRURGICA DE TIREOIDE (R$ 123,70)

### Habilitações Principais

Exemplos de habilitações cadastradas:
- `0101` - Centro de referência em atenção a saúde do idoso
- `0202` - Unidade de alta complexidade ao paciente portador de obesidade grave
- `0301` - Centros/Núcleos para realização de implante coclear
- `0403` - Centro de Especialidade Odontológica - CEO Tipo I
- `0503` - Unidade de Atenção Especializada em Oftalmologia
- `0601` - Psiquiatria - Classe I
- `0602` - Psiquiatria - Classe II

### Ocupações (CBO)

Exemplos de ocupações relacionadas aos procedimentos:
- `225110` - MÉDICO CLINICO GERAL
- `225120` - MÉDICO DE FAMÍLIA E COMUNIDADE
- `225103` - MÉDICO EM MEDICINA DE TRÁFEGO
- `225210` - MÉDICO CARDIOLOGISTA

---

## Estrutura Hierárquica Completa

```
Grupo (01-09)
  └── Subgrupo (01-99)
        └── Forma de Organização (01-99)
              └── Procedimento (código completo)
```

**Exemplo prático:**
```
Grupo: 03 - Procedimentos clínicos
  └── Subgrupo: 0301 - Consultas / Atendimentos / Acompanhamentos
        └── Forma Organização: 01 - Ambulatorial
              └── Procedimento: 0301010056 - CONSULTA MEDICA EM SAUDE DO TRABALHADOR
```

---

## Dicas de Pesquisa

1. **Use o código oficial**: A busca por código é mais precisa que por nome
2. **Combine filtros**: Use grupo + busca por nome para refinar resultados
3. **Verifique a competência**: Sempre especifique a competência para obter dados atualizados
4. **Use paginação**: Para grandes volumes, use page e size adequados
5. **Ordene os resultados**: Use sort para organizar (ex: `sort=codigoOficial,asc`)

---

**Última atualização**: Dezembro 2025
