# Grupo 05 - Transplantes de orgãos, tecidos e células

## Informações Gerais

- **Código**: 05
- **Nome**: Transplantes de orgãos, tecidos e células
- **Competência Inicial**: 202512
- **Status**: Ativo

## Descrição

Este grupo contém procedimentos relacionados a transplantes de órgãos, tecidos e células, incluindo avaliações, doações, processamentos e acompanhamentos.

## Subgrupos

| Código | Nome |
|--------|------|
| 0501 | Coleta e exames para fins de doação de orgãos, tecidos e células e de transplante |
| 0502 | Avaliação de morte encefálica |
| 0503 | Ações relacionadas à doação de orgãos e tecidos para transplante |
| 0504 | Processamento de tecidos para transplante |
| 0505 | Transplante de orgãos, tecidos e células |
| 0506 | Acompanhamento e intercorrências no pré e pós-transplante |

## Como Pesquisar Procedimentos de Transplante

### Via API REST

```bash
# Buscar procedimentos de transplante
GET /v1/sigtap/procedimentos?q=transplante&page=0&size=20

# Buscar subgrupos do grupo 05
GET /v1/sigtap/subgrupos?grupoCodigo=05&page=0&size=20
```

### Via SQL

```sql
-- Buscar procedimentos de transplante
SELECT p.codigo_oficial, p.nome, p.valor_servico_hospitalar,
       sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '05'
ORDER BY p.codigo_oficial;

-- Buscar transplantes específicos
SELECT p.codigo_oficial, p.nome, sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '05' AND sg.codigo_oficial = '05'  -- Transplante
ORDER BY p.codigo_oficial;
```

## Exemplos de Uso

### Buscar transplantes por tipo

```bash
# Transplantes de órgãos
GET /v1/sigtap/procedimentos?q=transplante%20órgão&page=0&size=20

# Avaliação de morte encefálica
GET /v1/sigtap/procedimentos?q=morte%20encefálica&page=0&size=20
```

### Buscar doações

```bash
GET /v1/sigtap/procedimentos?q=doação&page=0&size=20
```

## Estrutura Hierárquica

```
Grupo 05 - Transplantes de orgãos, tecidos e células
  └── Subgrupo 0501 - Coleta e exames para fins de doação
  └── Subgrupo 0502 - Avaliação de morte encefálica
  └── Subgrupo 0503 - Ações relacionadas à doação
  └── Subgrupo 0504 - Processamento de tecidos
  └── Subgrupo 0505 - Transplante de orgãos, tecidos e células
  └── Subgrupo 0506 - Acompanhamento pré e pós-transplante
```

---

**Última atualização**: Dezembro 2025
