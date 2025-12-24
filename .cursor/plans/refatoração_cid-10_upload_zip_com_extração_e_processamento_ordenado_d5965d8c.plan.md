---
name: "Refatoração CID-10: Upload ZIP com Extração e Processamento Ordenado"
overview: Implementar upload de ZIP para CID-10/CID-O similar ao SIGTAP, com extração automática de arquivos CSV, ordenação por prioridade baseada em dependências, e processamento sequencial na fila de jobs, mantendo separação de conexões API vs JOB.
todos:
  - id: create-cid10-zip-extraction-service
    content: Criar Cid10ZipExtractionService com métodos para extrair arquivos CSV de ZIP, filtrar apenas .CSV, ignorar metadados macOS, e validar estrutura
    status: completed
  - id: create-cid10-job-orchestrator
    content: Criar Cid10JobOrchestrator com mapa de prioridades (CAPITULOS=1000, GRUPOS=800, CATEGORIAS=600, SUBCATEGORIAS=400, CID-O-GRUPOS=300, CID-O-CATEGORIAS=200) e método para ordenar arquivos
    status: completed
  - id: add-method-interface-upload-service
    content: Adicionar método criarJobsFromZipCid10 na interface ImportJobUploadService
    status: completed
  - id: implement-zip-upload-cid10
    content: "Implementar criarJobsFromZipCid10 em ImportJobUploadServiceImpl: extrair ZIP, validar, ordenar, fazer upload de cada arquivo para storage, criar jobs completos com prioridade"
    status: completed
    dependencies:
      - create-cid10-zip-extraction-service
      - create-cid10-job-orchestrator
      - add-method-interface-upload-service
  - id: create-helper-methods-cid10
    content: Criar métodos auxiliares criarJobCompletoCid10 e montarObjectPathExtractedCid10 em ImportJobUploadServiceImpl
    status: completed
    dependencies:
      - implement-zip-upload-cid10
  - id: add-endpoint-upload-zip
    content: Adicionar endpoint POST /v1/cid10/import/upload-zip em Cid10FileImportController que retorna 202 após jobs criados e arquivos no storage
    status: completed
    dependencies:
      - implement-zip-upload-cid10
  - id: add-config-properties
    content: Adicionar propriedades de configuração para upload ZIP CID-10 em application-import-cid10.properties
    status: completed
  - id: test-upload-and-processing
    content: Testar upload ZIP completo, verificar criação de jobs ordenados, validar processamento sequencial por prioridade, e confirmar separação de conexões API vs JOB
    status: completed
    dependencies:
      - add-endpoint-upload-zip
      - add-config-properties
---

# Refatoração CID-10: Upload ZIP com Extração e Processamento Ordenado

## Objetivo

Implementar upload de arquivo ZIP contendo todos os arquivos CSV do CID-10/CID-O de uma competência, com extração automática, salvamento no Supabase Storage, e criação de jobs ordenados por prioridade para processamento sequencial.

## Contexto Atual

### Arquivos Identificados

- `CID-10-CAPITULOS.CSV` - Tabela base (nível mais alto)
- `CID-10-GRUPOS.CSV` - Grupos de categorias
- `CID-10-CATEGORIAS.CSV` - Categorias principais
- `CID-10-SUBCATEGORIAS.CSV` - Subcategorias (depende de categorias)
- `CID-O-GRUPOS.CSV` - Grupos CID-O (independente)
- `CID-O-CATEGORIAS.CSV` - Categorias CID-O (independente)

### Endpoint Atual

- `POST /v1/cid10/import/upload` - Upload individual de arquivo CSV
- Processa um arquivo por vez, criando um job por upload

### Dependências Identificadas

1. **CID-10-CAPITULOS.CSV** (prioridade 1000) - Independente
2. **CID-10-GRUPOS.CSV** (prioridade 800) - Pode referenciar capítulos, mas é independente
3. **CID-10-CATEGORIAS.CSV** (prioridade 600) - Independente
4. **CID-10-SUBCATEGORIAS.CSV** (prioridade 400) - **DEPENDE** de categorias (campo `categoriaCat`)
5. **CID-O-GRUPOS.CSV** (prioridade 300) - Independente (CID-O)
6. **CID-O-CATEGORIAS.CSV** (prioridade 200) - Independente (CID-O)

## Arquitetura da Solução

### Fluxo Proposto

```javascript
1. Upload ZIP → Controller recebe ZIP + competência
2. Extração → Cid10ZipExtractionService extrai arquivos CSV
3. Validação → Verifica se contém arquivos esperados
4. Ordenação → Cid10JobOrchestrator ordena por prioridade
5. Upload Storage → Para cada arquivo: upload para Supabase Storage
6. Criação de Jobs → Cria ImportJob com prioridade, storage_path, etc.
7. Retorno 202 → Apenas após todos os jobs criados e arquivos no storage
8. Processamento → Scheduler processa jobs na ordem de prioridade
```



## Implementação Detalhada

### 1. Criar `Cid10ZipExtractionService`

**Arquivo**: `src/main/java/com/upsaude/service/job/Cid10ZipExtractionService.java`**Responsabilidades**:

- Extrair arquivos CSV de um ZIP
- Filtrar apenas arquivos `.CSV` (case-insensitive)
- Ignorar arquivos de metadados (começam com `._`)
- Validar estrutura do ZIP (verificar se contém arquivos esperados)
- Retornar lista de arquivos extraídos com metadados

**Métodos**:

- `extrairZip(InputStream zipInputStream): ExtrairResultado`
- Usa `ZipArchiveInputStream` com fallback para `ZipFile` (suporte a data descriptors)
- Filtra apenas `.CSV` (case-insensitive)
- Ignora `._*` (metadados macOS)
- Calcula checksum SHA-256 para cada arquivo
- `validarEstruturaZip(List<ArquivoExtraido> arquivos): void`
- Verifica se contém pelo menos alguns arquivos conhecidos do CID-10/CID-O
- Lista esperada: `CID-10-CAPITULOS.CSV`, `CID-10-CATEGORIAS.CSV`, `CID-10-GRUPOS.CSV`, `CID-10-SUBCATEGORIAS.CSV`, `CID-O-GRUPOS.CSV`, `CID-O-CATEGORIAS.CSV`

**Classes internas**:

- `ArquivoExtraido` (nome, conteudo, tamanho, checksum)
- `ExtrairResultado` (arquivos, erros)

### 2. Criar `Cid10JobOrchestrator`

**Arquivo**: `src/main/java/com/upsaude/service/job/Cid10JobOrchestrator.java`**Responsabilidades**:

- Definir prioridades de processamento baseadas em dependências
- Ordenar arquivos por prioridade (maior primeiro)

**Métodos**:

- `calcularPrioridade(String nomeArquivo): int`
- Retorna prioridade baseada no nome do arquivo
- Prioridades:
    - `CID-10-CAPITULOS.CSV` → 1000
    - `CID-10-GRUPOS.CSV` → 800
    - `CID-10-CATEGORIAS.CSV` → 600
    - `CID-10-SUBCATEGORIAS.CSV` → 400
    - `CID-O-GRUPOS.CSV` → 300
    - `CID-O-CATEGORIAS.CSV` → 200
    - Arquivos não mapeados → 0
- `ordenarArquivos(List<ArquivoExtraido> arquivos): List<ArquivoComPrioridade>`
- Ordena por prioridade descendente (maior primeiro)
- Retorna lista de `ArquivoComPrioridade`

**Classes internas**:

- `ArquivoComPrioridade` (arquivo, prioridade, nomeArquivo)

### 3. Atualizar `ImportJobUploadService`

**Arquivo**: `src/main/java/com/upsaude/service/job/ImportJobUploadService.java`**Adicionar método**:

```java
CriarJobsZipResultado criarJobsFromZipCid10(
    MultipartFile zipFile,
    String competenciaAno,
    String competenciaMes,
    Tenant tenant,
    UUID createdByUserId
);
```



### 4. Atualizar `ImportJobUploadServiceImpl`

**Arquivo**: `src/main/java/com/upsaude/service/impl/job/ImportJobUploadServiceImpl.java`**Adicionar dependências**:

- `Cid10ZipExtractionService`
- `Cid10JobOrchestrator`

**Implementar `criarJobsFromZipCid10`**:

1. Extrair ZIP usando `Cid10ZipExtractionService.extrairZip()`
2. Validar estrutura usando `Cid10ZipExtractionService.validarEstruturaZip()`
3. Ordenar arquivos usando `Cid10JobOrchestrator.ordenarArquivos()`
4. Para cada arquivo ordenado:

- Montar path no storage: `tenant/{tenantId}/tipo/cid10/competencia/{ano}/{mes}/extracted/{nomeArquivo}`
- **Fazer upload primeiro** para Supabase Storage
- Calcular checksum durante upload
- Criar `ImportJob` completo com:
    - `tipo = CID10`
    - `storageBucket` e `storagePath` preenchidos
    - `priority` baseada no orchestrator
    - `checksum` calculado
    - `status = ENFILEIRADO`
- Salvar job no banco (usando `ImportJobApiRepository` - pool API)

5. Retornar `CriarJobsZipResultado` com jobs criados e erros

**Novo método auxiliar**:

- `criarJobCompletoCid10(...)` - Similar a `criarJobCompletoZip` do SIGTAP, mas para CID-10

**Método auxiliar para path**:

- `montarObjectPathExtractedCid10(String tenantId, String ano, String mes, String nomeArquivo): String`
- Retorna: `tenant/{tenantId}/tipo/cid10/competencia/{ano}/{mes}/extracted/{nomeArquivo}`

### 5. Atualizar `Cid10FileImportController`

**Arquivo**: `src/main/java/com/upsaude/controller/job/Cid10FileImportController.java`**Adicionar novo endpoint**:

- `POST /v1/cid10/import/upload-zip`
- Parâmetros: `zipFile` (MultipartFile), `competencia` (String AAAAMM)
- Retorna 202 Accepted após todos os jobs criados e arquivos no storage
- Response: `{ totalJobsCriados, totalArquivosProcessados, totalErros, jobs[], erros[], mensagem }`

**Manter endpoint antigo** (deprecated):

- `POST /v1/cid10/import/upload` - Manter para compatibilidade, mas marcar como deprecated

### 6. Configurações

**Arquivo**: `src/main/resources/config/common/import/application-import-cid10.properties`**Adicionar propriedades**:

```properties
# Upload ZIP CID-10
cid10.import.zip.max-size-bytes=1073741824
cid10.import.zip.require-all-files=false
cid10.import.zip.extracted-path-prefix=extracted
```



## Separação de Conexões (API vs JOB)

### Garantias

- **Upload Service** (`ImportJobUploadServiceImpl`):
- Usa `ImportJobApiRepository` (pool API)
- Transações com `@Transactional` padrão (pool API)
- Upload para Storage (HTTP, não usa pool de banco)
- **Worker** (`Cid10ImportJobWorker`):
- Já usa `ImportJobJobRepository` (pool JOB) ✓
- Já usa `@Qualifier("jobTransactionManager")` ✓
- Já usa `@PersistenceContext(unitName = "job")` ✓
- **Scheduler** (`ImportJobScheduler`):
- Já usa `ImportJobJobRepository` (pool JOB) ✓
- Já usa `@Qualifier("jobTransactionManager")` ✓

**Nenhuma alteração necessária** na separação de conexões - já está correta.

## Ordem de Processamento

### Prioridades Definidas

1. **1000**: `CID-10-CAPITULOS.CSV` (tabela base)
2. **800**: `CID-10-GRUPOS.CSV` (grupos)
3. **600**: `CID-10-CATEGORIAS.CSV` (categorias)
4. **400**: `CID-10-SUBCATEGORIAS.CSV` (depende de categorias)
5. **300**: `CID-O-GRUPOS.CSV` (CID-O grupos)
6. **200**: `CID-O-CATEGORIAS.CSV` (CID-O categorias)

### Garantia de Ordem

- Jobs são criados com `priority` baseada no orchestrator
- Query SQL em `ImportJobJobRepository.claimNextJobForProcessing` ordena por `priority DESC`
- Com `max-concurrent-global=1`, apenas 1 job processa por vez
- **Resultado**: Processamento sequencial respeitando prioridades

## Validações e Tratamento de Erros

### Validações no Upload

- ZIP não pode ser nulo ou vazio
- Competência deve estar no formato AAAAMM
- ZIP deve conter pelo menos alguns arquivos conhecidos
- Arquivos devem ser `.CSV` (case-insensitive)
- Ignorar arquivos de metadados (`._*`)

### Tratamento de Erros

- Se extração falhar: retornar 400 com mensagem clara
- Se upload de arquivo falhar: adicionar ao array de erros, continuar com outros
- Se criação de job falhar: adicionar ao array de erros, continuar com outros
- Retornar lista de erros no response (mesmo que alguns jobs tenham sido criados)

## Testes e Validação

### Cenários de Teste

1. Upload ZIP com todos os 6 arquivos → Deve criar 6 jobs ordenados
2. Upload ZIP com arquivos faltando → Deve criar jobs dos arquivos presentes
3. Upload ZIP com arquivos extras → Deve processar apenas arquivos conhecidos
4. Upload ZIP com metadados macOS → Deve ignorar `._*`
5. Upload ZIP com arquivos não-CSV → Deve ignorar
6. Verificar ordem de processamento → Jobs devem ser processados na ordem correta

## Checklist de Implementação

- [ ] Criar `Cid10ZipExtractionService` com extração e validação
- [ ] Criar `Cid10JobOrchestrator` com mapa de prioridades
- [ ] Adicionar método `criarJobsFromZipCid10` em `ImportJobUploadService`
- [ ] Implementar `criarJobsFromZipCid10` em `ImportJobUploadServiceImpl`
- [ ] Criar método `criarJobCompletoCid10` em `ImportJobUploadServiceImpl`
- [ ] Criar método `montarObjectPathExtractedCid10` em `ImportJobUploadServiceImpl`
- [ ] Adicionar endpoint `POST /v1/cid10/import/upload-zip` em `Cid10FileImportController`
- [ ] Adicionar propriedades de configuração em `application-import-cid10.properties`
- [ ] Testar upload ZIP completo
- [ ] Verificar ordem de processamento dos jobs
- [ ] Validar separação de conexões (API vs JOB)

## Arquivos a Modificar/Criar

### Novos Arquivos

- `src/main/java/com/upsaude/service/job/Cid10ZipExtractionService.java`
- `src/main/java/com/upsaude/service/job/Cid10JobOrchestrator.java`

### Arquivos a Modificar

- `src/main/java/com/upsaude/service/job/ImportJobUploadService.java`
- `src/main/java/com/upsaude/service/impl/job/ImportJobUploadServiceImpl.java`
- `src/main/java/com/upsaude/controller/job/Cid10FileImportController.java`
- `src/main/resources/config/common/import/application-import-cid10.properties`

### Arquivos que NÃO Precisam Modificação

- `Cid10ImportJobWorker` - Já usa pool JOB corretamente