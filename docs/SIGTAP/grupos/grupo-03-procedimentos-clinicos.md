# Grupo 03 - Procedimentos clínicos

## Informações Gerais

- **Código**: 03
- **Nome**: Procedimentos clínicos
- **Competência Inicial**: 202512
- **Status**: Ativo

## Descrição

Este grupo contém procedimentos clínicos diversos, incluindo consultas médicas, tratamentos, fisioterapia, cuidados paliativos e outros procedimentos não cirúrgicos.

## Subgrupos

| Código | Nome |
|--------|------|
| 0301 | Consultas / Atendimentos / Acompanhamentos |
| 0302 | Fisioterapia |
| 0303 | Tratamentos clínicos (outras especialidades) |
| 0304 | Tratamento em oncologia |
| 0305 | Tratamento em nefrologia |
| 0306 | Hemoterapia |
| 0307 | Tratamentos odontológicos |
| 0308 | Tratamento de lesões, envenenamentos e outros, decorrentes de causas externas |
| 0309 | Terapias especializadas |
| 0310 | Parto e nascimento |
| 0311 | Cuidados Paliativos |

## Como Pesquisar Procedimentos Clínicos

### Via API REST

```bash
# Buscar TODOS os procedimentos do grupo 03
GET /v1/sigtap/procedimentos?grupoCodigo=03&competencia=202512&page=0&size=20

# Buscar procedimentos de um subgrupo específico (ex: consultas médicas - subgrupo 0301)
GET /v1/sigtap/procedimentos?grupoCodigo=03&subgrupoCodigo=0301&competencia=202512&page=0&size=20

# Buscar procedimentos por nome dentro do grupo 03
GET /v1/sigtap/procedimentos?grupoCodigo=03&q=consulta%20medica&competencia=202512&page=0&size=20

# Buscar consulta específica
GET /v1/sigtap/procedimentos/0301010056?competencia=202512

# Buscar subgrupos do grupo 03
GET /v1/sigtap/subgrupos?grupoCodigo=03&page=0&size=20

# Buscar um subgrupo específico
GET /v1/sigtap/subgrupos?grupoCodigo=03&subgrupoCodigo=0301
```

### Via SQL

```sql
-- Buscar consultas médicas
SELECT p.codigo_oficial, p.nome, p.valor_servico_ambulatorial
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '03' AND sg.codigo_oficial = '01'
ORDER BY p.codigo_oficial;

-- Buscar todos os procedimentos clínicos
SELECT p.codigo_oficial, p.nome, p.valor_servico_ambulatorial,
       sg.nome as subgrupo
FROM sigtap_procedimento p
JOIN sigtap_forma_organizacao fo ON p.forma_organizacao_id = fo.id
JOIN sigtap_subgrupo sg ON fo.subgrupo_id = sg.id
JOIN sigtap_grupo g ON sg.grupo_id = g.id
WHERE g.codigo_oficial = '03'
ORDER BY p.codigo_oficial;
```

## Exemplos de Procedimentos Cadastrados

### Consultas Médicas (Subgrupo 0301)
- `0301010056` - CONSULTA MEDICA EM SAUDE DO TRABALHADOR (R$ 10,00)
- `0301010064` - CONSULTA MEDICA EM ATENÇÃO PRIMÁRIA (R$ 0,00)
- `0301010072` - CONSULTA MEDICA EM ATENÇÃO ESPECIALIZADA (R$ 10,00)

## Exemplos de Uso

### Buscar consultas por tipo

```bash
# Consultas em atenção primária
GET /v1/sigtap/procedimentos?q=consulta%20atenção%20primária&page=0&size=20

# Consultas especializadas
GET /v1/sigtap/procedimentos?q=consulta%20especializada&page=0&size=20
```

### Buscar tratamentos

```bash
# Tratamentos em oncologia
GET /v1/sigtap/procedimentos?q=tratamento%20oncologia&page=0&size=20

# Tratamentos odontológicos
GET /v1/sigtap/procedimentos?q=tratamento%20odontológico&page=0&size=20
```

### Buscar fisioterapia

```bash
GET /v1/sigtap/procedimentos?q=fisioterapia&page=0&size=20
```

## Estrutura Hierárquica

```
Grupo 03 - Procedimentos clínicos
  └── Subgrupo 0301 - Consultas / Atendimentos / Acompanhamentos
        └── Forma Organização: 01 - Ambulatorial
              └── Procedimento: 0301010056 - CONSULTA MEDICA EM SAUDE DO TRABALHADOR
  └── Subgrupo 0302 - Fisioterapia
  └── Subgrupo 0303 - Tratamentos clínicos (outras especialidades)
  └── Subgrupo 0304 - Tratamento em oncologia
  └── Subgrupo 0305 - Tratamento em nefrologia
  └── Subgrupo 0306 - Hemoterapia
  └── Subgrupo 0307 - Tratamentos odontológicos
  └── Subgrupo 0308 - Tratamento de lesões, envenenamentos e outros
  └── Subgrupo 0309 - Terapias especializadas
  └── Subgrupo 0310 - Parto e nascimento
  └── Subgrupo 0311 - Cuidados Paliativos
```

---

**Última atualização**: Dezembro 2025
