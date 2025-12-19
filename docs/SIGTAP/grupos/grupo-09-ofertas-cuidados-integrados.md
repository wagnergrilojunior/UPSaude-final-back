# Grupo 09 - Procedimentos para Ofertas de Cuidados Integrados

## Informações Gerais

- **Código**: 09
- **Nome**: Procedimentos para Ofertas de Cuidados Integrados
- **Competência Inicial**: 202512
- **Status**: Ativo

## Descrição

Este grupo contém procedimentos relacionados a ofertas de cuidados integrados em diversas especialidades, como oncologia, cardiologia, ortopedia, oftalmologia e outras.

## Subgrupos

| Código | Nome |
|--------|------|
| 0901 | Atenção em Oncologia |
| 0902 | Atenção em Cardiologia |
| 0903 | Atenção em Ortopedia |
| 0904 | Atenção em Otorrinolaringologia |
| 0905 | Atenção em Oftalmologia |
| 0906 | Atenção em Saúde Mulher |

## Como Pesquisar Cuidados Integrados

### Via API REST

```bash
# Buscar cuidados integrados
GET /v1/sigtap/procedimentos?q=09&page=0&size=20

# Buscar cuidados em oncologia
GET /v1/sigtap/procedimentos?q=atenção%20oncologia&page=0&size=20

# Buscar subgrupos do grupo 09
GET /v1/sigtap/subgrupos?grupoCodigo=09&page=0&size=20
```

### Via SQL

```sql
-- Buscar cuidados integrados
SELECT p.codigo_oficial, p.nome, sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '09'
ORDER BY p.codigo_oficial;

-- Buscar cuidados em especialidade específica
SELECT p.codigo_oficial, p.nome, sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '09' AND sg.codigo_oficial = '01'  -- Oncologia
ORDER BY p.nome;
```

## Exemplos de Uso

### Buscar por especialidade

```bash
# Atenção em Oncologia
GET /v1/sigtap/procedimentos?q=atenção%20oncologia&page=0&size=20

# Atenção em Cardiologia
GET /v1/sigtap/procedimentos?q=atenção%20cardiologia&page=0&size=20

# Atenção em Oftalmologia
GET /v1/sigtap/procedimentos?q=atenção%20oftalmologia&page=0&size=20

# Atenção em Saúde Mulher
GET /v1/sigtap/procedimentos?q=atenção%20saúde%20mulher&page=0&size=20
```

## Estrutura Hierárquica

```
Grupo 09 - Procedimentos para Ofertas de Cuidados Integrados
  └── Subgrupo 0901 - Atenção em Oncologia
  └── Subgrupo 0902 - Atenção em Cardiologia
  └── Subgrupo 0903 - Atenção em Ortopedia
  └── Subgrupo 0904 - Atenção em Otorrinolaringologia
  └── Subgrupo 0905 - Atenção em Oftalmologia
  └── Subgrupo 0906 - Atenção em Saúde Mulher
```

---

**Última atualização**: Dezembro 2025
