# Grupo 02 - Procedimentos com finalidade diagnóstica

## Informações Gerais

- **Código**: 02
- **Nome**: Procedimentos com finalidade diagnóstica
- **Competência Inicial**: 202512
- **Status**: Ativo

## Descrição

Este grupo contém todos os procedimentos relacionados a exames e diagnósticos, incluindo coletas de material, exames laboratoriais, exames de imagem, endoscopias e outros métodos diagnósticos.

## Subgrupos

| Código | Nome |
|--------|------|
| 0201 | Coleta de material |
| 0202 | Diagnóstico em laboratório clínico |
| 0203 | Diagnóstico por anatomia patológica e citopatologia |
| 0204 | Diagnóstico por radiologia |
| 0205 | Diagnóstico por ultrasonografia |
| 0206 | Diagnóstico por tomografia |
| 0207 | Diagnóstico por ressonância magnética |
| 0208 | Diagnóstico por medicina nuclear in vivo |
| 0209 | Diagnóstico por endoscopia |
| 0210 | Diagnóstico por radiologia intervencionista |
| 0211 | Métodos diagnósticos em especialidades |
| 0212 | Diagnóstico e procedimentos especiais em hemoterapia |
| 0213 | Diagnóstico em vigilância epidemiológica e ambiental |
| 0214 | Diagnóstico por teste rápido |

## Como Pesquisar Exames

### Via API REST

```bash
# Buscar TODOS os exames do grupo 02
GET /v1/sigtap/procedimentos?grupoCodigo=02&page=0&size=50

# Buscar exames de um subgrupo específico (ex: exames laboratoriais - subgrupo 02)
GET /v1/sigtap/procedimentos?grupoCodigo=02&subgrupoCodigo=02&page=0&size=50

# Buscar exames por nome dentro do grupo 02
GET /v1/sigtap/procedimentos?grupoCodigo=02&q=exame%20laboratorial&page=0&size=50

# Buscar exame específico por código
GET /v1/sigtap/procedimentos/0201020041

# Buscar subgrupos do grupo 02
GET /v1/sigtap/subgrupos?grupoCodigo=02&page=0&size=20

# Buscar um subgrupo específico do grupo 02
GET /v1/sigtap/subgrupos?grupoCodigo=02&subgrupoCodigo=02
```

### Via SQL

```sql
-- Buscar exames do grupo 02
SELECT p.codigo_oficial, p.nome, p.valor_servico_ambulatorial,
       sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '02'
ORDER BY p.codigo_oficial;

-- Buscar exames laboratoriais
SELECT p.codigo_oficial, p.nome, p.valor_servico_ambulatorial
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '02' AND sg.codigo_oficial = '02'
ORDER BY p.codigo_oficial;
```

## Exemplos de Exames Cadastrados

### Coleta de Material (Subgrupo 0201)
- `0201020041` - COLETA DE MATERIAL PARA EXAME LABORATORIAL
- `0201020033` - COLETA DE MATERIAL DO COLO DE ÚTERO PARA EXAME CITOPATOLÓGICO

### Exames Laboratoriais (Subgrupo 0202)
- `0202031225` - EXAME LABORATORIAL PARA DOENÇA DE GAUCHER I (R$ 80,00)
- `0202031233` - EXAME LABORATORIAL PARA DOENÇA DE GAUCHER II (R$ 120,00)
- `0202040038` - EXAME COPROLOGICO FUNCIONAL (R$ 3,04)

### Biópsias (Subgrupo 0201)
- `0201010011` - AMNIOCENTESE (R$ 2,20)
- `0201010020` - BIOPSIA / PUNCAO DE TUMOR SUPERFICIAL DA PELE (R$ 14,10)
- `0201010038` - BIOPSIA CIRURGICA DE TIREOIDE (R$ 123,70)

## Exemplos de Uso

### Buscar exames de imagem

```bash
# Exames de radiologia
GET /v1/sigtap/procedimentos?q=radiologia&page=0&size=20

# Exames de tomografia
GET /v1/sigtap/procedimentos?q=tomografia&page=0&size=20

# Exames de ressonância
GET /v1/sigtap/procedimentos?q=ressonância&page=0&size=20
```

### Buscar biópsias

```bash
GET /v1/sigtap/procedimentos?q=biópsia&page=0&size=20
```

### Buscar endoscopias

```bash
GET /v1/sigtap/procedimentos?q=endoscopia&page=0&size=20
```

## Estrutura Hierárquica

```
Grupo 02 - Procedimentos com finalidade diagnóstica
  └── Subgrupo 0201 - Coleta de material
  └── Subgrupo 0202 - Diagnóstico em laboratório clínico
  └── Subgrupo 0203 - Diagnóstico por anatomia patológica e citopatologia
  └── Subgrupo 0204 - Diagnóstico por radiologia
  └── Subgrupo 0205 - Diagnóstico por ultrasonografia
  └── Subgrupo 0206 - Diagnóstico por tomografia
  └── Subgrupo 0207 - Diagnóstico por ressonância magnética
  └── Subgrupo 0208 - Diagnóstico por medicina nuclear in vivo
  └── Subgrupo 0209 - Diagnóstico por endoscopia
  └── Subgrupo 0210 - Diagnóstico por radiologia intervencionista
  └── Subgrupo 0211 - Métodos diagnósticos em especialidades
  └── Subgrupo 0212 - Diagnóstico e procedimentos especiais em hemoterapia
  └── Subgrupo 0213 - Diagnóstico em vigilância epidemiológica e ambiental
  └── Subgrupo 0214 - Diagnóstico por teste rápido
```

---

**Última atualização**: Dezembro 2025
