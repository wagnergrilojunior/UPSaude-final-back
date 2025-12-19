# Grupo 08 - Ações complementares da atenção à saúde

## Informações Gerais

- **Código**: 08
- **Nome**: Ações complementares da atenção à saúde
- **Competência Inicial**: 202512
- **Status**: Ativo

## Descrição

Este grupo contém ações complementares relacionadas ao estabelecimento de saúde, atendimento, autorização/regulação e telessaúde.

## Subgrupos

| Código | Nome |
|--------|------|
| 0801 | Ações relacionadas ao estabelecimento |
| 0802 | Ações relacionadas ao atendimento |
| 0803 | Autorização / regulação |
| 0804 | Telessaúde |

## Como Pesquisar Ações Complementares

### Via API REST

```bash
# Buscar ações complementares
GET /v1/sigtap/procedimentos?q=08&page=0&size=20

# Buscar ações de telessaúde
GET /v1/sigtap/procedimentos?q=telessaúde&page=0&size=20

# Buscar subgrupos do grupo 08
GET /v1/sigtap/subgrupos?grupoCodigo=08&page=0&size=20
```

### Via SQL

```sql
-- Buscar ações complementares
SELECT p.codigo_oficial, p.nome, sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '08'
ORDER BY p.codigo_oficial;

-- Buscar ações de telessaúde
SELECT p.codigo_oficial, p.nome, sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '08' AND sg.codigo_oficial = '04'
ORDER BY p.nome;
```

## Exemplos de Uso

### Buscar por tipo de ação

```bash
# Ações relacionadas ao estabelecimento
GET /v1/sigtap/procedimentos?q=estabelecimento&page=0&size=20

# Autorização/Regulação
GET /v1/sigtap/procedimentos?q=autorização&page=0&size=20

# Telessaúde
GET /v1/sigtap/procedimentos?q=telessaúde&page=0&size=20
```

## Estrutura Hierárquica

```
Grupo 08 - Ações complementares da atenção à saúde
  └── Subgrupo 0801 - Ações relacionadas ao estabelecimento
  └── Subgrupo 0802 - Ações relacionadas ao atendimento
  └── Subgrupo 0803 - Autorização / regulação
  └── Subgrupo 0804 - Telessaúde
```

---

**Última atualização**: Dezembro 2025
