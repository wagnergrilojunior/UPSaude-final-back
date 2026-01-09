# Estrutura de Dados - SIGTAP

## 📋 Visão Geral

O banco de dados SIGTAP contém **44 tabelas** organizadas em duas categorias principais:
- **21 Tabelas de Referéncia**: Dados bésicos e independentes
- **15 Tabelas Relacionais**: Relacionamentos entre entidades
- **8 Tabelas Adicionais**: Compatibilidades, detalhes e outros

## 📋é Tabelas de Referéncia (tb_*)

### Hierarquia de Procedimentos

#### 1. `sigtap_grupo`
Agrupa procedimentos por rea de atuação.

**Campos Principais**:
- `codigo_oficial` (String, 20): Cdigo do grupo (ex: "03")
- `nome` (String, 255): Nome do grupo (ex: "Procedimentos Médicos")

**Exemplo**: Grupo 03 = Procedimentos Médicos

#### 2. `sigtap_subgrupo`
Subdivide grupos em categorias mais especéficas.

**Campos Principais**:
- `codigo_oficial` (String, 20): Cdigo do subgrupo (ex: "03.01")
- `nome` (String, 255): Nome do subgrupo
- `grupo_id` (UUID): Referéncia ao grupo pai

**Relacionamento**: Muitos para Um com `sigtap_grupo`

#### 3. `sigtap_forma_organizacao`
Classifica procedimentos por forma de organização.

**Campos Principais**:
- `codigo_oficial` (String, 20): Cdigo da forma
- `nome` (String, 255): Nome da forma
- `subgrupo_id` (UUID): Referéncia ao subgrupo pai

**Relacionamento**: Muitos para Um com `sigtap_subgrupo`

### Procedimentos

#### 4. `sigtap_procedimento`
Tabela principal de procedimentos.

**Campos Principais**:
- `codigo_oficial` (String, 20): Cdigo do procedimento (ex: "03.01.01.001-0")
- `nome` (String, 255): Nome do procedimento
- `sexo_permitido` (String, 30): Restriçãoo de sexo (M/F/I)
- `idade_minima` (Integer): Idade mínima em meses (9999 = não aplica)
- `idade_maxima` (Integer): Idade máxima em meses (9999 = não aplica)
- `valor_servico_hospitalar` (BigDecimal): Valor para ambiente hospitalar
- `valor_servico_ambulatorial` (BigDecimal): Valor para ambiente ambulatorial
- `valor_servico_profissional` (BigDecimal): Valor profissional
- `forma_organizacao_id` (UUID): Referéncia é forma de organização

**Relacionamentos**:
- Muitos para Um com `sigtap_forma_organizacao`
- Um para Muitos com tabelas relacionais (`sigtap_procedimento_cid`, etc.)

### Classificaçãoes e Referéncias

#### 5. `sigtap_cid`
Classificação Internacional de Doenças.

**Campos Principais**:
- `codigo_oficial` (String, 20): Cdigo CID (ex: "A00")
- `nome` (String, 255): Nome da doença
- `descricao` (String, 1000): Descriçãoo completa

**Total de Registros**: ~14.242 códigos

#### 6. `sigtap_ocupacao`
Ocupações profissionais (CBO).

**Campos Principais**:
- `codigo_oficial` (String, 20): Cdigo CBO (ex: "225110")
- `nome` (String, 255): Nome da ocupaçãoo

**Total de Registros**: ~2.718 ocupações

#### 7. `sigtap_habilitacao`
Habilitaçãoes necessérias para execuçãoo de procedimentos.

**Campos Principais**:
- `codigo_oficial` (String, 20): Cdigo da habilitaçãoo
- `nome` (String, 255): Nome da habilitaçãoo

**Total de Registros**: ~339 habilitações

#### 8. `sigtap_grupo_habilitacao`
Grupos de habilitações que devem ser atendidas em conjunto.

**Campos Principais**:
- `codigo_oficial` (String, 20): Cdigo do grupo
- `nome` (String, 255): Nome do grupo

### Outras Tabelas de Referéncia

- `sigtap_financiamento`: Tipos de financiamento (7 registros)
- `sigtap_rubrica`: Rubricas de financiamento (42 registros)
- `sigtap_modalidade`: Modalidades de procedimento (4 registros)
- `sigtap_registro`: Tipos de registro (10 registros)
- `sigtap_tipo_leito`: Tipos de leito (41 registros)
- `sigtap_servico`: Serviéos (73 registros)
- `sigtap_servico_classificacao`: Classificaçãoes de serviéo (432 registros)
- `sigtap_regra_condicionada`: Regras condicionadas (14 registros)
- `sigtap_renases`: Rede Nacional de Especialidades (201 registros)
- `sigtap_tuss`: Cdigos TUSS (5.766 registros)
- `sigtap_componente_rede`: Componentes de rede (20 registros)
- `sigtap_rede_atencao`: Redes de atenção (5 registros)
- `sigtap_sia_sih`: Mapeamento SIA/SIH (8.383 registros)
- `sigtap_detalhe`: Detalhes de procedimentos (48 registros)

## 📋 Tabelas Relacionais (rl_*)

### Relacionamentos com Procedimentos

#### 1. `sigtap_procedimento_cid`
Relaciona procedimentos com CID (doenças).

**Campos Principais**:
- `procedimento_id` (UUID): Referéncia ao procedimento
- `cid_id` (UUID): Referéncia ao CID
- `principal` (Boolean): Se é CID principal
- `competencia_inicial` (String, 6): Competéncia inicial

**Total de Registros**: ~81.753 relacionamentos

#### 2. `sigtap_procedimento_ocupacao`
Relaciona procedimentos com ocupações (quem pode executar).

**Campos Principais**:
- `procedimento_id` (UUID): Referéncia ao procedimento
- `ocupacao_id` (UUID): Referéncia É ocupaçãoo
- `competencia_inicial` (String, 6): Competéncia inicial

**Total de Registros**: ~87.500 relacionamentos (parcialmente importado)

#### 3. `sigtap_procedimento_habilitacao`
Relaciona procedimentos com habilitações necessérias.

**Campos Principais**:
- `procedimento_id` (UUID): Referéncia ao procedimento
- `habilitacao_id` (UUID): Referéncia é habilitaçãoo
- `competencia_inicial` (String, 6): Competéncia inicial

**Total Esperado**: ~10.981 relacionamentos

#### 4. `sigtap_procedimento_leito`
Relaciona procedimentos com tipos de leito.

**Campos Principais**:
- `procedimento_id` (UUID): Referéncia ao procedimento
- `tipo_leito_id` (UUID): Referéncia ao tipo de leito
- `competencia_inicial` (String, 6): Competéncia inicial

**Total Esperado**: ~4.147 relacionamentos

### Outros Relacionamentos

- `sigtap_procedimento_servico`: Procedimentos com serviços (~4.083)
- `sigtap_procedimento_incremento`: Incrementos de procedimentos (~2.388)
- `sigtap_procedimento_componente_rede`: Componentes de rede (~4)
- `sigtap_procedimento_origem`: Procedimentos de origem (~4)
- `sigtap_procedimento_sia_sih`: Mapeamento SIA/SIH (~5.382)
- `sigtap_procedimento_regra_condicionada`: Regras condicionadas (~3.305)
- `sigtap_procedimento_renases`: Renases (~5.370)
- `sigtap_procedimento_tuss`: TUSS (~0)
- `sigtap_procedimento_modalidade`: Modalidades (~7.938)
- `sigtap_procedimento_registro`: Registros (~7.439)
- `sigtap_procedimento_detalhe`: Detalhes de procedimentos (~10.142)

### Compatibilidades

#### `sigtap_compatibilidade`
Procedimentos que podem ser executados juntos.

**Campos Principais**:
- `procedimento_principal_id` (UUID): Procedimento principal
- `procedimento_compativel_id` (UUID): Procedimento compatével
- `tipo_compatibilidade` (String): Tipo de compatibilidade
- `competencia_inicial` (String, 6): Competéncia inicial

**Total Esperado**: ~12.133 compatibilidades

#### `sigtap_excecao_compatibilidade`
Exceçãoes que negam compatibilidades normalmente permitidas.

**Total Esperado**: ~5 exceçãoes

## 📋 Descriçãoes

### `sigtap_descricao`
Descriçãoes completas de procedimentos.

**Campos Principais**:
- `procedimento_id` (UUID): Referéncia ao procedimento
- `descricao_completa` (String): Descriçãoo completa
- `competencia_inicial` (String, 6): Competéncia inicial

**Total de Registros**: ~4.270 descriçãoes

### `sigtap_descricao_detalhe`
Descriçãoes de detalhes de procedimentos.

**Campos Principais**:
- `detalhe_id` (UUID): Referéncia ao detalhe
- `descricao_completa` (String): Descriçãoo completa
- `competencia_inicial` (String, 6): Competéncia inicial

**Total de Registros**: ~48 descriçãoes

## 📋 Chaves e índices

### Chaves Primérias
- Todas as tabelas usam **UUID** como chave priméria
- Geraçãoo automética pelo Hibernate

### Chaves úúnicas
- `codigo_oficial` + `competencia_inicial` (quando aplicével)
- Evita duplicatas em reimportaçãoes

### índices Criados

**Performance**:
- índices em `codigo_oficial` para buscas répidas
- índices em `nome` para buscas textuais
- índices em chaves estrangeiras para joins eficientes

**Exemplo**:
```sql
CREATE INDEX idx_sigtap_procedimento_nome ON sigtap_procedimento(nome);
CREATE INDEX idx_sigtap_proc_cid_procedimento_id ON sigtap_procedimento_cid(procedimento_id);
```

## 📋 Estatésticas de Dados

### Tabelas com Mais Registros

1. **sigtap_procedimento_ocupacao**: 87.500 (parcial - esperado 193.315)
2. **sigtap_procedimento_cid**: 81.753
3. **sigtap_cid**: 14.242
4. **sigtap_procedimento**: 4.957
5. **sigtap_descricao**: 4.270

### Tabelas Vazias (Aguardando Importaçãoo)

- `sigtap_compatibilidade`: 0 (esperado 12.133)
- `sigtap_procedimento_detalhe`: 0 (esperado 10.142)
- `sigtap_procedimento_habilitacao`: 0 (esperado 10.981)
- E outras 18 tabelas...

## 📋 Relacionamentos Principais

### Hierarquia de Procedimentos
```
sigtap_grupo (9)
  ï¿½ï¿½ sigtap_subgrupo (67)
      ï¿½ï¿½ sigtap_forma_organizacao (414)
          ï¿½ï¿½ sigtap_procedimento (4.957)
```

### Relacionamentos de Procedimentos
```
sigtap_procedimento
  ï¿½ï¿½ sigtap_procedimento_cid (81.753)
  ï¿½ï¿½ sigtap_procedimento_ocupacao (87.500)
  ï¿½ï¿½ sigtap_procedimento_habilitacao (0)
  ï¿½ï¿½ sigtap_procedimento_leito (0)
  ï¿½ï¿½ ... (outros relacionamentos)
```

## 📋 Convençãoes de Nomenclatura

### Tabelas
- Prefixo `sigtap_` para todas as tabelas
- Nomes em minésculas com underscore
- Nomes descritivos e claros

### Campos
- `codigo_oficial`: Cdigo original do SIGTAP
- `competencia_inicial`: Competéncia de inécio
- `competencia_final`: Competéncia de fim (NULL = ativo)
- `id`: Chave priméria UUID
- `criado_em`, `atualizado_em`: Timestamps autométicos

## 📋 Consultas úteis

### Buscar Procedimento por Cdigo
```sql
SELECT * FROM sigtap_procedimento 
WHERE codigo_oficial = '03.01.01.001-0';
```

### Buscar CID de um Procedimento
```sql
SELECT c.* FROM sigtap_cid c
JOIN sigtap_procedimento_cid pc ON c.id = pc.cid_id
WHERE pc.procedimento_id = '...';
```

### Buscar Ocupações de um Procedimento
```sql
SELECT o.* FROM sigtap_ocupacao o
JOIN sigtap_procedimento_ocupacao po ON o.id = po.ocupacao_id
WHERE po.procedimento_id = '...';
```

---

**Última atualização**: Dezembro 2025
