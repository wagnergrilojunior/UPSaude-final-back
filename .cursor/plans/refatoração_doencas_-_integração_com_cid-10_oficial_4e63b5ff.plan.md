---
name: Refatoração Doencas - Integração com CID-10 Oficial
overview: Refatorar modelo de Doenças adotando padrão onde CID-10 é fonte única da verdade e Doencas é apenas extensão técnica. ManyToOne obrigatório, remoção de duplicidades, validações específicas e manutenção de endpoints existentes.
todos:
  - id: "1"
    content: "Análise: Mapear todos os campos duplicados entre Doencas e Cid10Subcategorias"
    status: completed
  - id: "2"
    content: "Entidade: Adicionar ManyToOne obrigatório com Cid10Subcategorias em Doencas"
    status: completed
  - id: "3"
    content: "Entidade: Remover campos nome, descricao e classificacao.codigoCidPrincipal de Doencas"
    status: completed
  - id: "4"
    content: "Entidade: Adicionar constraint UNIQUE(cid10_subcategorias_id) na tabela doencas"
    status: completed
  - id: "5"
    content: "Embeddable: Remover codigoCidPrincipal, categoria e subcategoria de ClassificacaoDoenca"
    status: completed
  - id: "6"
    content: "SQL: Criar script de migração em etapas (adicionar FK, migrar dados, tornar obrigatório, remover colunas)"
    status: completed
  - id: "7"
    content: "Repository: Adicionar existsByCid10SubcategoriaId e queries com JOIN FETCH"
    status: completed
  - id: "8"
    content: "Request: Remover nome/descricao, adicionar cid10SubcategoriaId obrigatório, adicionar validação de campos oficiais"
    status: completed
  - id: "9"
    content: "Response: Manter nome/descricao (populados do CID-10), adicionar campos oficiais do CID-10"
    status: completed
  - id: "10"
    content: "Mapper: Adicionar mapeamento customizado para popular dados oficiais do CID-10"
    status: completed
  - id: "11"
    content: "Creator: Validar CID-10 existe, validar não duplicado, associar FK obrigatória"
    status: completed
  - id: "12"
    content: "Updater: Remover atualização de campos oficiais, permitir apenas complementares"
    status: completed
  - id: "13"
    content: "Validation: Adicionar validações para CID-10, duplicidade e rejeição de campos oficiais"
    status: completed
  - id: "14"
    content: "Service: Ajustar queries para buscar por CID-10 ao invés de campos diretos"
    status: completed
  - id: "15"
    content: "Controller: Atualizar documentação Swagger mantendo endpoints existentes"
    status: completed
---

# Plano Detalhado: Refatoração Doencas - Integração com CID-10 Oficial

## 1. Análise Inicial

### 1.1 Entidade Oficial do CID-10

**Entidade identificada:** `Cid10Subcategorias` (tabela `cid10_subcategorias`)**Justificativa:**

- Representa o nível mais específico de classificação CID-10
- Contém código único (`subcat`) que identifica a doença
- Possui descrição oficial completa
- É a unidade de referência para diagnósticos

**Campos oficiais em Cid10Subcategorias:**

- `id` (UUID) - identificador único
- `subcat` (String, 10) - código CID-10 (ex: "A00.0")
- `descricao` (TEXT) - descrição oficial da doença
- `descrAbrev` (TEXT) - descrição abreviada
- `categoriaCat` (String) - referência à categoria
- `categoria` (Cid10Categorias) - relação com categoria
- `classif` (String) - classificação
- `restrSexo` (String) - restrição de sexo
- `causaObito` (String) - se é causa de óbito
- `refer` (TEXT) - referências
- `excluidos` (TEXT) - exclusões

### 1.2 Mapeamento de Campos Duplicados

**Campos a REMOVER de Doencas (existem em CID-10):**| Campo em Doencas | Campo Oficial em CID-10 | Ação ||-----------------|------------------------|------|| `nome` | `cid10Subcategoria.descricao` | REMOVER - usar oficial || `descricao` | `cid10Subcategoria.descricao` | REMOVER - usar oficial || `classificacao.codigoCidPrincipal` | `cid10Subcategoria.subcat` | REMOVER - substituir por FK || `classificacao.categoria` | `cid10Subcategoria.categoria.cat` | REMOVER - vem do CID-10 || `classificacao.subcategoria` | `cid10Subcategoria.subcat` | REMOVER - vem do CID-10 |**Campos a MANTER em Doencas (complementares do sistema):**| Campo | Tipo | Justificativa ||-------|------|---------------|| `nomeCientifico` | String | Complementar - não existe no CID-10 || `codigoInterno` | String | Complementar - código interno do sistema || `classificacao.tipoDoenca` | Enum | Complementar - classificação interna || `classificacao.gravidade` | Enum | Complementar - classificação interna || `classificacao.doencaNotificavel` | Boolean | Complementar - flag do sistema || `classificacao.doencaTransmissivel` | Boolean | Complementar - flag do sistema || `cronica` | Boolean | Complementar - flag do sistema || `sintomas` | Embeddable | Complementar - dados clínicos adicionais || `tratamentoPadrao` | Embeddable | Complementar - protocolos internos || `epidemiologia` | Embeddable | Complementar - dados epidemiológicos || `causas` | TEXT | Complementar - detalhamento adicional || `fisiopatologia` | TEXT | Complementar - detalhamento adicional || `prognostico` | TEXT | Complementar - detalhamento adicional || `observacoes` | TEXT | Complementar - observações internas |

## 2. Refatoração da Entidade Doencas

### 2.1 Mudanças na Entidade

**Arquivo:** `src/main/java/com/upsaude/entity/clinica/doencas/Doencas.java`**Alterações:**

1. **Adicionar relacionamento ManyToOne obrigatório:**
```java
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "cid10_subcategorias_id", nullable = false)
private Cid10Subcategorias cid10Subcategoria;
```




2. **Adicionar constraint de unicidade:**
```java
@Table(
    name = "doencas",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_doencas_cid10_subcategoria",
            columnNames = {"cid10_subcategorias_id"}
        )
    }
)
```




3. **Adicionar índice para performance:**
```java
@Index(name = "idx_doencas_cid10_subcategoria", columnList = "cid10_subcategorias_id")
```




4. **Remover campos duplicados:**

                - Remover campo `nome`
                - Remover campo `descricao`
                - Remover índice `idx_doenca_nome`

5. **Adicionar métodos helper (opcional, para compatibilidade):**
```java
// Métodos que delegam para CID-10 oficial
public String getNome() {
    return cid10Subcategoria != null ? cid10Subcategoria.getDescricao() : null;
}

public String getDescricao() {
    return cid10Subcategoria != null ? cid10Subcategoria.getDescricao() : null;
}

public String getCodigoCid() {
    return cid10Subcategoria != null ? cid10Subcategoria.getSubcat() : null;
}
```




### 2.2 Mudanças no Embeddable ClassificacaoDoenca

**Arquivo:** `src/main/java/com/upsaude/entity/embed**Arquivo:** `src/main/java/com/upsaude/entity/embeddable/Cdable/ClassificacaoDoenca.java`**Alterações:**

1. **Remover campos duplicados:**

                - Remover `codigoCidPrincipal`
                - Remover `categoria`
                - Remover `subcategoria`

2. **Manter apenas campos complementares:**

                - `tipoDoenca` (Enum)
                - `gravidade` (Enum)
                - `doencaNotificavel` (Boolean)
                - `doencaTransmissivel` (Boolean)

## 3. Migração de Banco de Dados

### 3.1 Script SQL de Migração

**Estratégia de migração em etapas:Etapa 1: Preparação**

```sql
-- 1. Adicionar coluna de relacionamento (permitir NULL temporariamente)
ALTER TABLE doencas 
ADD COLUMN cid10_subcategorias_id UUID;

-- 2. Criar índice para performance na migração
CREATE INDEX idx_doencas_cid10_subcategoria_temp 
ON doencas(cid10_subcategorias_id);
```

**Etapa 2: Migração de Dados Existentes**

```sql
-- Associar doenças existentes pelo código CID armazenado em classificacao_doenca.codigo_cid_principal
UPDATE doencas d
SET cid10_subcategorias_id = (
    SELECT c.id 
    FROM cid10_subcategorias c 
    WHERE c.subcat = (
        SELECT cd.codigo_cid_principal 
        FROM doencas d2
        WHERE d2.id = d.id
        -- Acesso ao embeddable via join ou subquery
    )
)
WHERE EXISTS (
    SELECT 1 
    FROM cid10_subcategorias c 
    WHERE c.subcat = (
        -- Extrair codigo_cid_principal do embeddable
    )
);

-- Nota: Pode ser necessário criar função auxiliar para acessar campos do embeddable
```

**Etapa 3: Validação e Limpeza**

```sql
-- Identificar registros não migrados (sem CID-10 correspondente)
SELECT d.id, d.nome, cd.codigo_cid_principal
FROM doencas d
WHERE d.cid10_subcategorias_id IS NULL
AND EXISTS (
    SELECT 1 
    FROM doencas d2 
    WHERE d2.id = d.id 
    -- Verificar se tem codigo_cid_principal
);

-- Decisão: 
-- Opção A: Criar registros CID-10 temporários para doenças sem correspondência
-- Opção B: Exigir associação manual antes de tornar obrigatório
-- Opção C: Remover doenças sem CID-10 válido (se política permitir)
```

**Etapa 4: Tornar Relacionamento Obrigatório**

```sql
-- 1. Adicionar constraint NOT NULL
ALTER TABLE doencas 
ALTER COLUMN cid10_subcategorias_id SET NOT NULL;

-- 2. Adicionar Foreign Key
ALTER TABLE doencas 
ADD CONSTRAINT fk_doencas_cid10_subcategorias 
FOREIGN KEY (cid10_subcategorias_id) 
REFERENCES cid10_subcategorias(id);

-- 3. Adicionar constraint de unicidade
ALTER TABLE doencas 
ADD CONSTRAINT uk_doencas_cid10_subcategoria 
UNIQUE (cid10_subcategorias_id);

-- 4. Criar índice final
CREATE INDEX idx_doencas_cid10_subcategoria 
ON doencas(cid10_subcategorias_id);

-- 5. Remover índice temporário
DROP INDEX IF EXISTS idx_doencas_cid10_subcategoria_temp;
```

**Etapa 5: Remover Colunas Duplicadas**

```sql
-- 1. Remover índice antigo
DROP INDEX IF EXISTS idx_doenca_nome;

-- 2. Remover coluna nome
ALTER TABLE doencas 
DROP COLUMN nome;

-- 3. Remover coluna descricao
ALTER TABLE doencas 
DROP COLUMN descricao;

-- 4. Remover campos do embeddable classificacao_doenca
-- Nota: Campos embeddables são colunas na mesma tabela
ALTER TABLE doencas 
DROP COLUMN codigo_cid_principal,
DROP COLUMN categoria,
DROP COLUMN subcategoria;
```



### 3.2 Estratégia de Rollback

Criar script de rollback caso seja necessário reverter:

- Manter backup das colunas antes de remover
- Script para recriar colunas e popular com dados do CID-10

## 4. DTOs e Contratos de API

### 4.1 DoencasRequest (Request de Criação/Atualização)

**Arquivo:** `src/main/java/com/upsaude/api/request/clinica/doencas/DoencasRequest.java`**Mudanças:**

1. **Remover campos oficiais:**

                - Remover `nome` (vem do CID-10)
                - Remover `descricao` (vem do CID-10)
                - Remover validação `@NotBlank` de nome

2. **Adicionar campo para associação CID-10:**
```java
@NotNull(message = "ID da subcategoria CID-10 é obrigatório")
@Schema(description = "ID da subcategoria CID-10 oficial")
private UUID cid10SubcategoriaId;
```




3. **Manter apenas campos complementares:**

                - `nomeCientifico`
                - `codigoInterno`
                - `classificacao` (sem codigoCidPrincipal, categoria, subcategoria)
                - `sintomas`
                - `tratamentoPadrao`
                - `epidemiologia`
                - `causas`
                - `fisiopatologia`
                - `prognostico`
                - `observacoes`

4. **Adicionar validação customizada:**

                - Rejeitar se campos oficiais forem enviados (nome, descricao)
                - Validar que `cid10SubcategoriaId` existe

### 4.2 DoencasResponse (Response)

**Arquivo:** `src/main/java/com/upsaude/api/response/clinica/doencas/DoencasResponse.java`**Mudanças:**

1. **Manter campos para compatibilidade (populados do CID-10):**

                - `nome` - populado de `cid10Subcategoria.descricao`
                - `descricao` - populado de `cid10Subcategoria.descricao`

2. **Adicionar campos oficiais do CID-10:**
```java
// Dados oficiais do CID-10
private String codigoCid; // cid10Subcategoria.subcat
private String descricaoAbreviada; // cid10Subcategoria.descrAbrev
private String restricaoSexo; // cid10Subcategoria.restrSexo
private String causaObito; // cid10Subcategoria.causaObito
private String referencias; // cid10Subcategoria.refer
private String exclusoes; // cid10Subcategoria.excluidos

// Dados da categoria (se necessário)
private Cid10CategoriaInfo categoria; // nested object com dados da categoria
```




3. **Manter campos complementares:**

                - Todos os campos complementares de Doencas

### 4.3 Mapper (DoencasMapper)

**Arquivo:** `src/main/java/com/upsaude/mapper/clinica/doencas/DoencasMapper.java`**Mudanças:**

1. **Adicionar mapeamento customizado:**
```java
@Mapping(target = "nome", source = "cid10Subcategoria.descricao")
@Mapping(target = "descricao", source = "cid10Subcategoria.descricao")
@Mapping(target = "codigoCid", source = "cid10Subcategoria.subcat")
@Mapping(target = "descricaoAbreviada", source = "cid10Subcategoria.descrAbrev")
@Mapping(target = "restricaoSexo", source = "cid10Subcategoria.restrSexo")
@Mapping(target = "causaObito", source = "cid10Subcategoria.causaObito")
@Mapping(target = "referencias", source = "cid10Subcategoria.refer")
@Mapping(target = "exclusoes", source = "cid10Subcategoria.excluidos")
DoencasResponse toResponse(Doencas entity);
```




2. **Ajustar mapeamento de Request:**

                - Não mapear `nome` e `descricao` do request
                - Mapear `cid10SubcategoriaId` para buscar e associar entidade

3. **Adicionar método auxiliar:**
```java
@AfterMapping
default void populateCid10Data(@MappingTarget DoencasResponse response, Doencas entity) {
    if (entity.getCid10Subcategoria() != null) {
        // Garantir que dados oficiais estão populados
        // Pode ser necessário fazer fetch se LAZY
    }
}
```




## 5. Service Layer (Regras de Negócio)

### 5.1 DoencasCreator

**Arquivo:** `src/main/java/com/upsaude/service/support/doencas/DoencasCreator.java`**Mudanças:**

1. **Validação de CID-10:**
```java
// Validar que CID-10 existe
Cid10Subcategorias cid10 = cid10Repository.findById(request.getCid10SubcategoriaId())
    .orElseThrow(() -> new NotFoundException(
        "Subcategoria CID-10 não encontrada com ID: " + request.getCid10SubcategoriaId()
    ));
```




2. **Validação de duplicidade:**
```java
// Verificar se já existe extensão para este CID-10
if (doencasRepository.existsByCid10SubcategoriaId(request.getCid10SubcategoriaId())) {
    throw new ConflictException(
        "Já existe uma extensão de doença cadastrada para o CID-10: " + cid10.getSubcat()
    );
}
```




3. **Criação da entidade:**
```java
Doencas doenca = new Doencas();
doenca.setCid10Subcategoria(cid10); // OBRIGATÓRIO
// Popular apenas campos complementares do request
// NÃO popular nome/descricao (vem do CID-10)
```




### 5.2 DoencasUpdater

**Arquivo:** `src/main/java/com/upsaude/service/support/doencas/DoencasUpdater.java`**Mudanças:**

1. **Remover atualização de campos oficiais:**

                - Não permitir atualizar `nome`
                - Não permitir atualizar `descricao`
                - Não permitir atualizar `codigoCid`

2. **Permitir atualizar apenas campos complementares:**

                - Todos os campos complementares podem ser atualizados
                - CID-10 não pode ser alterado (relacionamento fixo)

3. **Validação:**

                - Rejeitar request que contenha campos oficiais

### 5.3 DoencasValidationService

**Arquivo:** `src/main/java/com/upsaude/service/support/doencas/DoencasValidationService.java`**Mudanças:**

1. **Remover validação de nome obrigatório**
2. **Adicionar validações:**
```java
// Validar que CID-10 ID foi fornecido
if (request.getCid10SubcategoriaId() == null) {
    throw new BadRequestException("ID da subcategoria CID-10 é obrigatório");
}

// Validar que CID-10 existe
if (!cid10Repository.existsById(request.getCid10SubcategoriaId())) {
    throw new NotFoundException(
        "Subcategoria CID-10 não encontrada com ID: " + request.getCid10SubcategoriaId()
    );
}

// Validar duplicidade (na criação)
if (creating && doencasRepository.existsByCid10SubcategoriaId(request.getCid10SubcategoriaId())) {
    throw new ConflictException(
        "Já existe uma extensão de doença para este CID-10"
    );
}

// Rejeitar campos oficiais no request
if (request.getNome() != null || request.getDescricao() != null) {
    throw new BadRequestException(
        "Campos oficiais (nome, descricao) não podem ser enviados. " +
        "Use cid10SubcategoriaId para associar a um CID-10 oficial."
    );
}
```




### 5.4 DoencasServiceImpl

**Arquivo:** `src/main/java/com/upsaude/service/impl/DoencasServiceImpl.java`**Mudanças:**

1. **Ajustar queries com fetch:**
```java
// Garantir fetch do CID-10 em consultas
@Query("SELECT d FROM Doencas d " +
       "LEFT JOIN FETCH d.cid10Subcategoria c " +
       "LEFT JOIN FETCH c.categoria " +
       "WHERE d.id = :id")
Optional<Doencas> findByIdWithCid10(@Param("id") UUID id);
```




2. **Ajustar método listarPorNome:**

                - Buscar por `cid10Subcategoria.descricao` ao invés de `nome`

3. **Ajustar método listarPorCodigoCid:**

                - Buscar por `cid10Subcategoria.subcat` ao invés de `classificacao.codigoCidPrincipal`

## 6. Repository

**Arquivo:** `src/main/java/com/upsaude/repository/clinica/doencas/DoencasRepository.java`**Mudanças:**

1. **Adicionar métodos:**
```java
// Verificar existência por CID-10
boolean existsByCid10SubcategoriaId(UUID cid10SubcategoriaId);

// Buscar por CID-10
Optional<Doencas> findByCid10SubcategoriaId(UUID cid10SubcategoriaId);

// Buscar por código CID (subcat)
@Query("SELECT d FROM Doencas d " +
       "JOIN FETCH d.cid10Subcategoria c " +
       "WHERE c.subcat = :codigoCid")
Page<Doencas> findByCodigoCid(@Param("codigoCid") String codigoCid, Pageable pageable);

// Buscar por nome (busca na descrição do CID-10)
@Query("SELECT d FROM Doencas d " +
       "JOIN FETCH d.cid10Subcategoria c " +
       "WHERE LOWER(c.descricao) LIKE LOWER(CONCAT('%', :nome, '%'))")
Page<Doencas> findByNomeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);

// Buscar com fetch para performance
@Query("SELECT d FROM Doencas d " +
       "LEFT JOIN FETCH d.cid10Subcategoria c " +
       "LEFT JOIN FETCH c.categoria")
List<Doencas> findAllWithCid10();
```




2. **Remover métodos obsoletos:**

                - `findByNome` (substituído por busca no CID-10)
                - `existsByNome` (substituído por `existsByCid10SubcategoriaId`)

## 7. Controller

**Arquivo:** `src/main/java/com/upsaude/controller/clinica/doencas/DoencasController.java`**Mudanças mínimas (endpoints preservados):**

1. **Manter todos os endpoints existentes:**

                - `POST /v1/doencas` - criar
                - `GET /v1/doencas` - listar
                - `GET /v1/doencas/{id}` - obter por ID
                - `GET /v1/doencas/buscar?nome=...` - buscar por nome
                - `GET /v1/doencas/cid/{codigoCid}` - buscar por código CID
                - `PUT /v1/doencas/{id}` - atualizar
                - `DELETE /v1/doencas/{id}` - excluir

2. **Atualizar documentação Swagger:**

                - Documentar que `nome` e `descricao` vêm do CID-10
                - Documentar campo `cid10SubcategoriaId` no request
                - Documentar novos campos oficiais no response

## 8. Validações e Tratamento de Erros

### 8.1 Códigos HTTP e Exceções

| Situação | HTTP | Exceção | Mensagem ||----------|------|---------|----------|| Campos oficiais no request | 400 | BadRequestException | "Campos oficiais (nome, descricao) não podem ser enviados" || CID-10 não encontrado | 404 | NotFoundException | "Subcategoria CID-10 não encontrada com ID: {id}" || Extensão duplicada para CID-10 | 409 | ConflictException | "Já existe uma extensão de doença para este CID-10" || CID-10 ID não fornecido | 400 | BadRequestException | "ID da subcategoria CID-10 é obrigatório" || Doença não encontrada | 404 | NotFoundException | "Doença não encontrada com ID: {id}" |

### 8.2 Validações Customizadas

Criar validador customizado para rejeitar campos oficiais:

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoencasRequestValidator.class)
public @interface ValidDoencasRequest {
    String message() default "Request contém campos oficiais que não são permitidos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```



## 9. Estrutura Final

### 9.1 Diagrama de Relacionamento

```javascript
┌─────────────────────────┐
│  cid10_subcategorias    │ (Tabela Oficial - Read Only)
│  (Fonte de Verdade)     │
├─────────────────────────┤
│ id (PK)                 │
│ subcat (código CID)     │◄──────┐
│ descricao               │       │
│ descrabrev              │       │
│ categoria_cat           │       │
│ restrsexo               │       │
│ causaobito              │       │
│ refer                   │       │
│ excluidos               │       │
└─────────────────────────┘       │
                                  │
                                  │ ManyToOne
                                  │ (obrigatório)
                                  │
┌─────────────────────────┐       │
│      doencas            │       │
│  (Extensão Técnica)     │       │
├─────────────────────────┤       │
│ id (PK)                 │       │
│ cid10_subcategorias_id  │───────┘ (FK, NOT NULL, UNIQUE)
│ nome_cientifico         │
│ codigo_interno          │
│ tipo_doenca             │
│ gravidade               │
│ doenca_notificavel      │
│ doenca_transmissivel    │
│ cronica                 │
│ sintomas_*              │
│ tratamento_*            │
│ epidemiologia_*         │
│ causas                  │
│ fisiopatologia          │
│ prognostico             │
│ observacoes             │
└─────────────────────────┘
```



### 9.2 Fluxo de Criação

```javascript
1. Cliente envia POST /v1/doencas
   {
     "cid10SubcategoriaId": "uuid-do-cid10",
     "nomeCientifico": "...",
     "cronica": true,
     ...
   }

2. Service valida:
            - CID-10 existe? → 404 se não
            - Já existe extensão? → 409 se sim
            - Campos oficiais no request? → 400 se sim

3. Service cria:
            - Busca CID-10 oficial
            - Cria Doencas com FK para CID-10
            - Popula apenas campos complementares

4. Response retorna:
   {
     "id": "...",
     "nome": "Descrição do CID-10", // ← do oficial
     "descricao": "Descrição do CID-10", // ← do oficial
     "codigoCid": "A00.0", // ← do oficial
     "nomeCientifico": "...", // ← complementar
     ...
   }
```



### 9.3 Fluxo de Consulta

```javascript
1. Cliente envia GET /v1/doencas/{id}

2. Repository busca com JOIN FETCH:
   SELECT d.*, c.* 
   FROM doencas d
   JOIN cid10_subcategorias c ON d.cid10_subcategorias_id = c.id
   WHERE d.id = ?

3. Mapper popula response:
            - Dados oficiais ← cid10Subcategoria.*
            - Dados complementares ← doencas.*

4. Response contém dados agregados
```



## 10. Checklist de Implementação

### Fase 1: Preparação

- [ ] Criar script SQL de migração
- [ ] Fazer backup da tabela doencas
- [ ] Documentar estratégia de rollback

### Fase 2: Entidades

- [ ] Atualizar Doencas.java (adicionar FK, remover campos)
- [ ] Atualizar ClassificacaoDoenca.java (remover campos)
- [ ] Testar compilação

### Fase 3: Migração SQL

- [ ] Executar etapa 1 (adicionar coluna)
- [ ] Executar etapa 2 (migrar dados)
- [ ] Validar dados migrados
- [ ] Executar etapa 3 (tornar obrigatório)
- [ ] Executar etapa 4 (remover colunas)

### Fase 4: Repository

- [ ] Atualizar DoencasRepository
- [ ] Adicionar queries com JOIN FETCH
- [ ] Testar queries

### Fase 5: DTOs e Mappers

- [ ] Atualizar DoencasRequest
- [ ] Atualizar DoencasResponse
- [ ] Atualizar DoencasMapper
- [ ] Testar mapeamentos

### Fase 6: Services

- [ ] Atualizar DoencasCreator
- [ ] Atualizar DoencasUpdater
- [ ] Atualizar DoencasValidationService
- [ ] Atualizar DoencasServiceImpl
- [ ] Testar regras de negócio

### Fase 7: Controller

- [ ] Atualizar documentação Swagger
- [ ] Testar endpoints

### Fase 8: Testes

- [ ] Testar criação com CID-10 válido
- [ ] Testar criação com CID-10 inválido (404)
- [ ] Testar criação duplicada (409)
- [ ] Testar criação com campos oficiais (400)
- [ ] Testar atualização (apenas complementares)
- [ ] Testar consulta (dados agregados)
- [ ] Testar busca por nome (no CID-10)
- [ ] Testar busca por código CID

### Fase 9: Documentação

- [ ] Atualizar documentação da API
- [ ] Documentar mudanças para desenvolvedores
- [ ] Criar guia de migração

## 11. Considerações de Performance

1. **Índices:**

                - Índice em `cid10_subcategorias_id` (já criado)
                - Considerar índice composto se necessário

2. **Fetch Strategy:**

                - Usar `JOIN FETCH` em consultas que precisam dos dados oficiais
                - Manter `LAZY` no relacionamento para evitar N+1

3. **Cache:**

                - Manter cache de DoencasResponse
                - Invalidar cache quando CID-10 for atualizado (se aplicável)

## 12. Compatibilidade e Versionamento

- Endpoints mantidos (sem breaking changes na URL)