# Arquitetura Técnica - SIGTAP

## 🏗️ Visão Geral da Arquitetura

O sistema de importação SIGTAP foi desenvolvido seguindo os princípios de **Clean Architecture** e **SOLID**, utilizando Spring Boot 3.3.4 e Java 17+.

## 📋 Componentes Principais

### 1. Camada de Controle (Controller)

**Classe**: `SigtapFileImportController`  
**Pacote**: `com.upsaude.controller`  
**Responsabilidades**:
- Expor endpoints REST para importação
- Validar parâmetros de entrada
- Retornar resultados da importação

**Endpoints**:
- `POST /api/sigtap/import/{competencia}` - Importa arquivos de uma competência
- `GET /api/sigtap/import/{competencia}/arquivos` - Lista arquivos disponíveis

### 2. Camada de Serviéo (Service)

**Classe**: `SigtapFileImportServiceImpl`  
**Pacote**: `com.upsaude.service.impl`  
**Responsabilidades**:
- Orquestrar o processão de importação
- Gerenciar ordem de importação (dependências)
- Processar arquivos em batch
- Tratar erros e continuar processamento

**Caracterésticas**:
- Processamento em lotes (batch) configurével
- Tratamento robusto de exceçãoes
- Logging estruturado
- Suporte a transaçãoes longas (timeout de 1 hora)

### 3. Camada de Mapeamento (Mapper)

**Classe**: `SigtapEntityMapper`  
**Pacote**: `com.upsaude.mapper.sigtap`  
**Responsabilidades**:
- Converter dados parseados em entidades JPA
- Validar relacionamentos
- Buscar entidades relacionadas nos repositérios

**Caracterésticas**:
- Validação de relacionamentos obrigatórios
- Tratamento de valores especiais (ex: idade 9999)
- Limpeza de strings (remoção de espaços, caracteres especiais)

### 4. Camada de Parsing (Parser)

**Classes**:
- `SigtapFileParser` - Parseia linhas de arquivos de largura fixa
- `SigtapLayoutReader` - L e parseia arquivos de layout

**Pacote**: `com.upsaude.importacao.sigtap.file`  
**Responsabilidades**:
- Ler arquivos de layout (`_layout.txt`)
- Parsear linhas de dados de largura fixa
- Extrair campos conforme definição do layout
- Converter tipos de dados (String, Integer, BigDecimal)

**Caracterésticas**:
- Suporte a encoding ISO-8859-1
- Parsing robusto com tratamento de erros
- Validação de tipos de dados

### 5. Camada de Entidades (Entities)

**Pacote**: `com.upsaude.entity.sigtap`  
**Total**: 36 entidades JPA

**Categorias**:

#### Tabelas de Referência (21 entidades)
- `SigtapGrupo`, `SigtapSubgrupo`, `SigtapFormaOrganizacao`
- `SigtapProcedimento`, `SigtapCid`, `SigtapOcupacao`
- `SigtapHabilitacao`, `SigtapFinanciamento`, etc.

#### Tabelas Relacionais (15 entidades)
- `SigtapProcedimentoCid` - Relaciona procedimentos com CID
- `SigtapProcedimentoOcupacao` - Relaciona procedimentos com ocupações
- `SigtapProcedimentoHabilitacao` - Relaciona procedimentos com habilitações
- E outros relacionamentos...

**Caracterésticas**:
- Herdam de `BaseEntityWithoutTenant` (sem multi-tenancy)
- Usam UUID como chave priméria
- Possuem `codigo_oficial` como chave natural
- índices e constraints úúnicas para performance

### 6. Camada de Repositério (Repository)

**Pacote**: `com.upsaude.repository`  
**Total**: 36 interfaces `JpaRepository`

**Mtodos Comuns**:
- `findByCodigoOficial(String codigo)` - Busca por código oficial
- `findByCodigoOficialAndCompetenciaInicial(...)` - Busca com competência
- Mtodos específicos para relacionamentos

## 📋 Fluxo de Importaçãoo

### 1. Inicializaçãoo

```
Cliente HTTP é Controller é Service é Verifica pasta competência
```

### 2. Processamento por Fases

A importação segue uma ordem específica para respeitar dependências:

#### Fase 1: Tabelas de Referência (sem dependências)
```
tb_grupo.txt é sigtap_grupo
tb_cid.txt é sigtap_cid
tb_ocupacao.txt é sigtap_ocupacao
... (18 tabelas)
```

#### Fase 2: Hierarquia de Agregaçãoo
```
tb_sub_grupo.txt é sigtap_subgrupo (depende de sigtap_grupo)
tb_forma_organizacao.txt é sigtap_forma_organizacao (depende de sigtap_subgrupo)
```

#### Fase 3: Procedimentos
```
tb_procedimento.txt é sigtap_procedimento (depende de sigtap_forma_organizacao)
```

#### Fase 4: Descriçãoes
```
tb_descricao.txt é sigtap_descricao (depende de sigtap_procedimento)
tb_descricao_detalhe.txt é sigtap_descricao_detalhe (depende de sigtap_detalhe)
```

#### Fase 5: Relacionamentos
```
rl_procedimento_cid.txt é sigtap_procedimento_cid
rl_procedimento_ocupacao.txt é sigtap_procedimento_ocupacao
... (15 relacionamentos)
```

### 3. Processamento de Arquivo Individual

```
1. Ler arquivo de layout (_layout.txt)
2. Criar ImportContext (arquivo, layout, competência)
3. Para cada linha do arquivo:
   a. Parsear linha é Map<String, String>
   b. Validar campos bésicos
   c. Mapear para entidade é Entity
   d. Adicionar ao batch
   e. Quando batch atingir tamanho é salvar em lote
4. Salvar batch final (se houver)
5. Retornar estatésticas
```

## 📋é Estrutura de Banco de Dados

### Schema

Todas as tabelas SIGTAP estáo no schema `public` e não possuem multi-tenancy (herdam de `BaseEntityWithoutTenant`).

### Chaves Primérias

- **Tipo**: UUID
- **Geração**: Automática pelo Hibernate
- **Motivo**: Evita conflitos em importaçãoes paralelas

### Chaves Naturais

- **Campo**: `codigo_oficial`
- **Uso**: Identificação única do registro no SIGTAP
- **Constraints**: Unique constraints para evitar duplicatas

### Competéncias

- **`competencia_inicial`**: Competéncia em que o registro comeéou a valer
- **`competencia_final`**: Competéncia em que o registro deixou de valer (NULL = ainda ativo)

### índices

Criados para otimizar consultas frequentes:
- índices em `codigo_oficial`
- índices em `nome` (para buscas textuais)
- índices em chaves estrangeiras

## 📋 Configuraçãoes

### application.properties

```properties
# Caminho base para arquivos de importação
sigtap.import.base-path=data_import/sigtap

# Tamanho do lote para processamento em batch
sigtap.import.batch-size=500

# Encoding dos arquivos TXT
sigtap.import.encoding=ISO-8859-1
```

### Transaçãoes

- **Timeout**: 1 hora (3600 segundos) para arquivos grandes
- **Isãolamento**: READ_COMMITTED (padréo Spring)
- **Propagaçãoo**: REQUIRED (padréo)

## 📋 Tratamento de Erros

### Estratégia de Erro

1. **Erro em linha individual**: Registra erro, continua processamento
2. **Erro crético**: Para processamento do arquivo, continua outros arquivos
3. **Erro fatal**: Para importação completa

### Tipos de Erros Tratados

- `IllegalArgumentException`: Entidade relacionada não encontrada
- `IllegalStateException`: Contexto Spring fechado
- `ConfigurationPropertiesBindException`: Problema com beans Spring
- `Exception`: Erros genéricos

### Logging

- **Nvel INFO**: Progressão geral da importação
- **Nvel WARN**: Erros em linhas individuais
- **Nvel ERROR**: Erros créticos que param processamento
- **Nvel DEBUG**: Detalhes de validação

## 📋 Performance

### Otimizaçãoes Implementadas

1. **Batch Processing**: Processa e salva em lotes de 500 registros
2. **Lazy Loading**: Relacionamentos carregados sãob demanda
3. **índices**: Criados em campos frequentemente consultados
4. **Transaçãoes Longas**: Timeout aumentado para arquivos grandes

### Mtricas Esperadas

- **Arquivo pequeno** (< 1.000 linhas): ~1-5 segundos
- **Arquivo médio** (1.000-10.000 linhas): ~10-60 segundos
- **Arquivo grande** (> 100.000 linhas): ~5-30 minutos

## 📋 Seguranéa

- **Autenticaçãoo**: Requer token JWT válido
- **Autorizaçãoo**: Endpoints protegidos pelo Spring Security
- **Validaçãoo**: Validaçãoo de parâmetros de entrada
- **Sanitizaçãoo**: Limpeza de strings de entrada

## 📋 Testes

### Estrutura de Testes (Recomendado)

```
src/test/java/com/upsaude/
ï¿½ï¿½ï¿½ importacao/sigtap/file/
ï¿½   ï¿½ï¿½ï¿½ SigtapFileParserTest.java
ï¿½   ï¿½ï¿½ï¿½ SigtapLayoutReaderTest.java
ï¿½ï¿½ï¿½ mapper/sigtap/
ï¿½   ï¿½ï¿½ï¿½ SigtapEntityMapperTest.java
ï¿½ï¿½ï¿½ service/impl/
    ï¿½ï¿½ï¿½ SigtapFileImportServiceImplTest.java
```

### Cobertura Esperada

- Parsing de arquivos: 90%+
- Mapeamento de entidades: 85%+
- Processamento de batch: 80%+
- Tratamento de erros: 75%+

## 🚀 Próximas Melhorias

1. **Processamento Assíncrono**: Usar `@Async` para importações grandes
2. **Validação de Integridade**: Verificar relacionamentos após importação
3. **Relatórios de Importação**: Gerar relatórios detalhados
4. **Importação Incremental**: Importar apenas mudanças entre competências
5. **Cache**: Cachear entidades frequentemente consultadas

---

**Última atualização**: Dezembro 2025
