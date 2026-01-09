# Grupo 07 - Órteses, próteses e materiais especiais

## Informações Gerais

- **Código**: 07
- **Nome**: Órteses, próteses e materiais especiais
- **Competência Inicial**: 202512
- **Status**: Ativo

## Descrição

Este grupo contém procedimentos relacionados a órteses, próteses e materiais especiais utilizados no tratamento de pacientes, tanto relacionados a atos cirúrgicos quanto independentes.

## Subgrupos

| Código | Nome |
|--------|------|
| 0701 | Órteses, próteses e materiais especiais não relacionados ao ato cirúrgico |
| 0702 | Órteses, próteses e materiais especiais relacionados ao ato cirúrgico |

## Como Pesquisar Órteses e Próteses

### Via API REST

```bash
# Buscar órteses e próteses
GET /v1/sigtap/procedimentos?q=órtese&page=0&size=20

GET /v1/sigtap/procedimentos?q=prótese&page=0&size=20

# Buscar subgrupos do grupo 07
GET /v1/sigtap/subgrupos?grupoCodigo=07&page=0&size=20
```

### Via SQL

```sql
-- Buscar órteses e próteses
SELECT p.codigo_oficial, p.nome, p.valor_servico_ambulatorial,
       sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '07'
ORDER BY p.codigo_oficial;

-- Buscar materiais não relacionados a cirurgia
SELECT p.codigo_oficial, p.nome, sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '07' AND sg.codigo_oficial = '01'
ORDER BY p.nome;
```

## Exemplos de Uso

### Buscar por tipo

```bash
# Órteses
GET /v1/sigtap/procedimentos?q=órtese&page=0&size=20

# Próteses
GET /v1/sigtap/procedimentos?q=prótese&page=0&size=20

# Materiais especiais
GET /v1/sigtap/procedimentos?q=material%20especial&page=0&size=20
```

## Estrutura Hierárquica

```
Grupo 07 - Órteses, próteses e materiais especiais
  └── Subgrupo 0701 - Não relacionados ao ato cirúrgico
        └── Forma de Organização
              └── Procedimento
  └── Subgrupo 0702 - Relacionados ao ato cirúrgico
        └── Forma de Organização
              └── Procedimento
```

---

**Última atualização**: Dezembro 2025
