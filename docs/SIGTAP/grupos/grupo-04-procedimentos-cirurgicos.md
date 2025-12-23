# Grupo 04 - Procedimentos cirúrgicos

## Informações Gerais

- **Código**: 04
- **Nome**: Procedimentos cirúrgicos
- **Competência Inicial**: 202512
- **Status**: Ativo

## Descrição

Este grupo contém todos os procedimentos cirúrgicos realizados em diversas especialidades, desde pequenas cirurgias até cirurgias complexas de transplantes e oncologia.

## Subgrupos

| Código | Nome |
|--------|------|
| 0401 | Pequenas cirurgias e cirurgias de pele, tecido subcutâneo e mucosa |
| 0402 | Cirurgia de glândulas endócrinas |
| 0403 | Cirurgia do sistema nervoso central e periférico |
| 0404 | Cirurgia das vias aéreas superiores, da face, da cabeça e do pescoço |
| 0405 | Cirurgia do aparelho da visão |
| 0406 | Cirurgia do aparelho circulatório |
| 0407 | Cirurgia do aparelho digestivo, orgãos anexos e parede abdominal |
| 0408 | Cirurgia do sistema osteomuscular |
| 0409 | Cirurgia do aparelho geniturinário |
| 0410 | Cirurgia de mama |
| 0411 | Cirurgia obstétrica |
| 0412 | Cirurgia torácica |
| 0413 | Cirurgia reparadora |
| 0414 | Bucomaxilofacial |
| 0415 | Outras cirurgias |
| 0416 | Cirurgia em oncologia |
| 0417 | Anestesiologia |
| 0418 | Cirurgia em nefrologia |

## Como Pesquisar Procedimentos Cirúrgicos

### Via API REST

#### Filtros Hierárquicos

**1. Filtrar apenas por grupo (todos os procedimentos cirúrgicos)**:
```bash
GET /v1/sigtap/procedimentos?grupoCodigo=04&page=0&size=20
```

**2. Filtrar por grupo e subgrupo (ex: pequenas cirurgias)**:
```bash
GET /v1/sigtap/procedimentos?grupoCodigo=04&subgrupoCodigo=01&page=0&size=20
```

**3. Filtrar por grupo, subgrupo e forma de organização**:
```bash
GET /v1/sigtap/procedimentos?grupoCodigo=04&subgrupoCodigo=01&formaOrganizacaoCodigo=01&page=0&size=20
```

#### Busca por Termo

```bash
# Buscar procedimentos cirúrgicos por nome
GET /v1/sigtap/procedimentos?grupoCodigo=04&q=cirurgia&page=0&size=50

# Buscar cirurgias específicas
GET /v1/sigtap/procedimentos?grupoCodigo=04&q=cirurgia%20cardíaca&page=0&size=20
```

#### Consultar Subgrupos

```bash
# Buscar todos os subgrupos do grupo 04
GET /v1/sigtap/subgrupos?grupoCodigo=04&page=0&size=20

# Buscar um subgrupo específico
GET /v1/sigtap/subgrupos?grupoCodigo=04&subgrupoCodigo=01&page=0&size=20
```

#### Consultar Formas de Organização

```bash
# Buscar formas de organização de um subgrupo específico
# Quando informar grupoCodigo + subgrupoCodigo, o endpoint retorna formas de organização
GET /v1/sigtap/subgrupos?grupoCodigo=04&subgrupoCodigo=01&page=0&size=20

# Ou usar o endpoint específico de formas de organização
GET /v1/sigtap/formas-organizacao?grupoCodigo=04&subgrupoCodigo=01&page=0&size=20
```

### Via SQL

```sql
-- Buscar procedimentos cirúrgicos
SELECT p.codigo_oficial, p.nome, p.valor_servico_hospitalar,
       sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '04'
ORDER BY p.codigo_oficial;

-- Buscar cirurgias por especialidade
SELECT p.codigo_oficial, p.nome, sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '04' AND sg.codigo_oficial = '06'  -- Aparelho circulatório
ORDER BY p.codigo_oficial;
```

## Exemplos de Uso

### Buscar cirurgias por especialidade

```bash
# Cirurgias cardíacas
GET /v1/sigtap/procedimentos?q=cirurgia%20cardíaca&page=0&size=20

# Cirurgias ortopédicas
GET /v1/sigtap/procedimentos?q=cirurgia%20ortopédica&page=0&size=20

# Cirurgias plásticas/reparadoras
GET /v1/sigtap/procedimentos?q=cirurgia%20reparadora&page=0&size=20
```

### Buscar anestesias

```bash
GET /v1/sigtap/procedimentos?q=anestesia&page=0&size=20
```

## Estrutura Hierárquica

```
Grupo 04 - Procedimentos cirúrgicos
  └── Subgrupo 0401 - Pequenas cirurgias
  └── Subgrupo 0402 - Cirurgia de glândulas endócrinas
  └── Subgrupo 0403 - Cirurgia do sistema nervoso
  └── Subgrupo 0404 - Cirurgia de vias aéreas superiores, face, cabeça e pescoço
  └── Subgrupo 0405 - Cirurgia do aparelho da visão
  └── Subgrupo 0406 - Cirurgia do aparelho circulatório
  └── Subgrupo 0407 - Cirurgia do aparelho digestivo
  └── Subgrupo 0408 - Cirurgia do sistema osteomuscular
  └── Subgrupo 0409 - Cirurgia do aparelho geniturinário
  └── Subgrupo 0410 - Cirurgia de mama
  └── Subgrupo 0411 - Cirurgia obstétrica
  └── Subgrupo 0412 - Cirurgia torácica
  └── Subgrupo 0413 - Cirurgia reparadora
  └── Subgrupo 0414 - Bucomaxilofacial
  └── Subgrupo 0415 - Outras cirurgias
  └── Subgrupo 0416 - Cirurgia em oncologia
  └── Subgrupo 0417 - Anestesiologia
  └── Subgrupo 0418 - Cirurgia em nefrologia
```

---

**Última atualização**: Dezembro 2025
