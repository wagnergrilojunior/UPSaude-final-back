# Grupo 06 - Medicamentos

## Informações Gerais

- **Código**: 06
- **Nome**: Medicamentos
- **Competência Inicial**: 202512
- **Status**: Ativo

## Descrição

Este grupo contém todos os medicamentos disponíveis no SUS, organizados por tipo de dispensação e complexidade.

## Subgrupos

| Código | Nome |
|--------|------|
| 0601 | Medicamentos de dispensação excepcional |
| 0602 | Medicamentos estratégicos |
| 0603 | Medicamentos de âmbito hospitalar e urgência |
| 0604 | Componente especializado da assistência farmacêutica |

## Como Pesquisar Medicamentos

### Via API REST

```bash
# Buscar TODOS os medicamentos do grupo 06
GET /v1/sigtap/procedimentos?grupoCodigo=06&page=0&size=20

# Buscar medicamentos de um subgrupo específico (ex: dispensação excepcional)
GET /v1/sigtap/procedimentos?grupoCodigo=06&subgrupoCodigo=01&page=0&size=20

# Buscar medicamentos por nome dentro do grupo 06
GET /v1/sigtap/procedimentos?grupoCodigo=06&q=dipirona&page=0&size=20

# Buscar medicamento específico por código
GET /v1/sigtap/procedimentos/0601010101

# Buscar subgrupos do grupo 06
GET /v1/sigtap/subgrupos?grupoCodigo=06&page=0&size=20

# Buscar um subgrupo específico do grupo 06
GET /v1/sigtap/subgrupos?grupoCodigo=06&subgrupoCodigo=01
```

### Via SQL

```sql
-- Buscar medicamentos
SELECT p.codigo_oficial, p.nome, p.valor_servico_ambulatorial,
       sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '06'
ORDER BY p.nome;

-- Buscar medicamentos por subgrupo
SELECT p.codigo_oficial, p.nome, sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '06' AND sg.codigo_oficial = '01'  -- Dispensação excepcional
ORDER BY p.nome;
```

## Exemplos de Uso

### Buscar medicamentos por tipo

```bash
# Medicamentos de dispensação excepcional (subgrupo 01)
GET /v1/sigtap/procedimentos?grupoCodigo=06&subgrupoCodigo=01&page=0&size=20

# Medicamentos estratégicos (subgrupo 02)
GET /v1/sigtap/procedimentos?grupoCodigo=06&subgrupoCodigo=02&page=0&size=20

# Medicamentos de âmbito hospitalar e urgência (subgrupo 03)
GET /v1/sigtap/procedimentos?grupoCodigo=06&subgrupoCodigo=03&page=0&size=20

# Componente especializado da assistência farmacêutica (subgrupo 04)
GET /v1/sigtap/procedimentos?grupoCodigo=06&subgrupoCodigo=04&page=0&size=20
```

### Buscar medicamento específico

```bash
# Por código do medicamento
GET /v1/sigtap/procedimentos/0601010101?competencia=202512

# Por nome do medicamento
GET /v1/sigtap/procedimentos?q=paracetamol&page=0&size=20
```

## Estrutura Hierárquica

```
Grupo 06 - Medicamentos
  └── Subgrupo 0601 - Medicamentos de dispensação excepcional
        └── Forma de Organização
              └── Procedimento (medicamento)
  └── Subgrupo 0602 - Medicamentos estratégicos
  └── Subgrupo 0603 - Medicamentos de âmbito hospitalar e urgência
  └── Subgrupo 0604 - Componente especializado da assistência farmacêutica
```

## Notas Importantes

- Todos os medicamentos do SUS estão catalogados neste grupo
- Medicamentos podem ter diferentes valores conforme a forma de organização
- É importante verificar a competência para garantir que o medicamento está ativo
- Medicamentos de dispensação excepcional requerem autorização prévia

---

**Última atualização**: Dezembro 2025
