# Grupo 01 - Ações de promoção e prevenção em saúde

## Informações Gerais

- **Código**: 01
- **Nome**: Ações de promoção e prevenção em saúde
- **Competência Inicial**: 202512
- **Status**: Ativo

## Descrição

Este grupo contém ações voltadas para promoção da saúde e prevenção de doenças, incluindo ações coletivas e individuais, além de vigilância em saúde.

## Subgrupos

| Código | Nome |
|--------|------|
| 0101 | Ações coletivas/individuais em saúde |
| 0102 | Vigilância em saúde |

## Como Pesquisar Procedimentos deste Grupo

### Via API REST

```bash
# Listar todos os grupos
GET /v1/sigtap/grupos

# Buscar TODOS os procedimentos do grupo 01
GET /v1/sigtap/procedimentos?grupoCodigo=01&page=0&size=20

# Buscar procedimentos de um subgrupo específico
GET /v1/sigtap/procedimentos?grupoCodigo=01&subgrupoCodigo=01&page=0&size=20

# Buscar subgrupos do grupo 01
GET /v1/sigtap/subgrupos?grupoCodigo=01&page=0&size=20

# Buscar um subgrupo específico
GET /v1/sigtap/subgrupos?grupoCodigo=01&subgrupoCodigo=01
```

### Via SQL

```sql
-- Buscar procedimentos do grupo 01
SELECT p.codigo_oficial, p.nome, p.valor_servico_ambulatorial,
       sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '01'
ORDER BY p.codigo_oficial;

-- Buscar subgrupos do grupo 01
SELECT sg.codigo_oficial, sg.nome
FROM sigtap_subgrupo sg
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '01'
ORDER BY sg.codigo_oficial;
```

## Exemplos de Uso

### Buscar ações coletivas de saúde

```bash
GET /v1/sigtap/procedimentos?q=ação%20coletiva&page=0&size=20
```

### Buscar ações de vigilância

```bash
GET /v1/sigtap/procedimentos?q=vigilância&page=0&size=20
```

## Estrutura Hierárquica

```
Grupo 01 - Ações de promoção e prevenção em saúde
  └── Subgrupo 0101 - Ações coletivas/individuais em saúde
        └── Forma de Organização
              └── Procedimento
  └── Subgrupo 0102 - Vigilância em saúde
        └── Forma de Organização
              └── Procedimento
```

---

**Última atualização**: Dezembro 2025
