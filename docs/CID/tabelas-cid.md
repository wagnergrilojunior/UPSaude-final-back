# Tabelas CID-10 e CID-O - Documentação Completa

## Índice

1. [Visão Geral](#visão-geral)
2. [Estrutura Hierárquica](#estrutura-hierárquica)
3. [Como Pesquisar Códigos CID](#como-pesquisar-códigos-cid)
4. [Tabelas CID-10](#tabelas-cid-10)
5. [Tabelas CID-O](#tabelas-cid-o)
6. [Exemplos de Consultas SQL](#exemplos-de-consultas-sql)

## Visão Geral

O CID (Classificação Internacional de Doenças) é um sistema de classificação de doenças e problemas relacionados à saúde. Este sistema possui duas classificações principais:

- **CID-10**: Classificação Internacional de Doenças, 10ª Revisão - para classificação de doenças, sintomas, achados anormais, queixas, circunstâncias sociais e causas externas de danos ou doença.
- **CID-O**: Classificação Internacional de Doenças para Oncologia - para classificação de neoplasias (tumores).

## Estrutura Hierárquica

### CID-10

```
Capítulos (22 capítulos)
  └── Categorias (ex: A00, A01, A02)
        └── Subcategorias (ex: A000, A001, A009)
```

### CID-O

```
Grupos
  └── Categorias
```

## Como Pesquisar Códigos CID

### Via API REST

```bash
# Buscar categoria por código
GET /v1/cid/cid10/categorias/A00

# Pesquisar categorias
GET /v1/cid/cid10/categorias?q=cólera&page=0&size=20

# Buscar subcategoria por código
GET /v1/cid/cid10/subcategorias/A000

# Listar todas as subcategorias de uma categoria
GET /v1/cid/cid10/subcategorias/categoria/A00

# Listar todos os capítulos
GET /v1/cid/cid10/capitulos
```

### Via SQL

```sql
-- Buscar categoria por código
SELECT * FROM cid10_categorias WHERE cat = 'A00' AND ativo = true;

-- Pesquisar categorias por descrição
SELECT * FROM cid10_categorias 
WHERE LOWER(descricao) LIKE '%cólera%' AND ativo = true;

-- Buscar subcategoria por código
SELECT * FROM cid10_subcategorias WHERE subcat = 'A000' AND ativo = true;

-- Listar todas as subcategorias de uma categoria
SELECT * FROM cid10_subcategorias 
WHERE categoria_cat = 'A00' AND ativo = true 
ORDER BY subcat;

-- Buscar por intervalo de códigos
SELECT * FROM cid10_categorias 
WHERE cat >= 'A00' AND cat <= 'A09' AND ativo = true 
ORDER BY cat;
```

## Tabelas CID-10

### 1. cid10_capitulos

Capítulos do CID-10 (22 capítulos no total).

**Campos:**
- `id` (UUID): Chave primária
- `numcap` (Integer): Número do capítulo (1-22)
- `catinic` (String, 10): Código inicial do capítulo (ex: "A00")
- `catfim` (String, 10): Código final do capítulo (ex: "B99")
- `descricao` (TEXT): Descrição completa do capítulo
- `descricao_abreviada` (TEXT): Descrição abreviada
- `criado_em` (Timestamp): Data de criação
- `atualizado_em` (Timestamp): Data de atualização
- `ativo` (Boolean): Indica se o registro está ativo

**Exemplo:**
```json
{
  "numcap": 1,
  "catinic": "A00",
  "catfim": "B99",
  "descricao": "Capítulo I - Algumas doenças infecciosas e parasitárias",
  "descricaoAbreviada": "I.   Algumas doenças infecciosas e parasitárias"
}
```

### 2. cid10_categorias

Categorias do CID-10 (códigos de 3 caracteres).

**Campos:**
- `id` (UUID): Chave primária
- `cat` (String, 10): Código da categoria (ex: "A00")
- `classif` (String, 20): Classificação adicional
- `descricao` (TEXT): Descrição completa da categoria
- `descrAbrev` (TEXT): Descrição abreviada
- `refer` (TEXT): Referências
- `excluidos` (TEXT): Códigos excluídos
- `criado_em` (Timestamp): Data de criação
- `atualizado_em` (Timestamp): Data de atualização
- `ativo` (Boolean): Indica se o registro está ativo

**Exemplo:**
```json
{
  "cat": "A00",
  "descricao": "Cólera",
  "descrAbrev": "A00   Colera"
}
```

### 3. cid10_subcategorias

Subcategorias do CID-10 (códigos de 4 ou mais caracteres).

**Campos:**
- `id` (UUID): Chave primária
- `subcat` (String, 10): Código da subcategoria (ex: "A000")
- `categoria_cat` (String, 10): Código da categoria relacionada (ex: "A00")
- `categoria` (ManyToOne): Relacionamento com Cid10Categorias
- `classif` (String, 20): Classificação adicional
- `restrSexo` (String, 5): Restrição de sexo
- `causaObito` (String, 5): Indica se pode ser causa de óbito
- `descricao` (TEXT): Descrição completa da subcategoria
- `descrAbrev` (TEXT): Descrição abreviada
- `refer` (TEXT): Referências
- `excluidos` (TEXT): Códigos excluídos
- `criado_em` (Timestamp): Data de criação
- `atualizado_em` (Timestamp): Data de atualização
- `ativo` (Boolean): Indica se o registro está ativo

**Exemplo:**
```json
{
  "subcat": "A000",
  "categoriaCat": "A00",
  "descricao": "Cólera devida a Vibrio cholerae 01, biótipo cholerae",
  "descrAbrev": "A00.0 Colera dev Vibrio cholerae 01 biot cholerae"
}
```

### 4. cid10_grupos

Grupos de categorias do CID-10 (agrupamentos por intervalo).

**Campos:**
- `id` (UUID): Chave primária
- `catinic` (String, 10): Código inicial do grupo
- `catfim` (String, 10): Código final do grupo
- `descricao` (TEXT): Descrição do grupo
- `descricao_abreviada` (TEXT): Descrição abreviada
- `criado_em` (Timestamp): Data de criação
- `atualizado_em` (Timestamp): Data de atualização
- `ativo` (Boolean): Indica se o registro está ativo

## Tabelas CID-O

### 1. cid_o_grupos

Grupos de categorias do CID-O.

**Campos:**
- `id` (UUID): Chave primária
- `catinic` (String, 20): Código inicial do grupo
- `catfim` (String, 20): Código final do grupo
- `descricao` (TEXT): Descrição do grupo
- `refer` (TEXT): Referências
- `criado_em` (Timestamp): Data de criação
- `atualizado_em` (Timestamp): Data de atualização
- `ativo` (Boolean): Indica se o registro está ativo

### 2. cid_o_categorias

Categorias do CID-O (classificação de neoplasias).

**Campos:**
- `id` (UUID): Chave primária
- `cat` (String, 20): Código da categoria
- `descricao` (TEXT): Descrição completa da categoria
- `refer` (TEXT): Referências
- `criado_em` (Timestamp): Data de criação
- `atualizado_em` (Timestamp): Data de atualização
- `ativo` (Boolean): Indica se o registro está ativo

## Exemplos de Consultas SQL

### Buscar categoria e suas subcategorias

```sql
SELECT 
    c.cat,
    c.descricao as categoria_descricao,
    s.subcat,
    s.descricao as subcategoria_descricao
FROM cid10_categorias c
LEFT JOIN cid10_subcategorias s ON s.categoria_cat = c.cat AND s.ativo = true
WHERE c.cat = 'A00' AND c.ativo = true
ORDER BY s.subcat;
```

### Buscar todas as categorias de um capítulo

```sql
SELECT c.*
FROM cid10_categorias c
INNER JOIN cid10_capitulos cap ON c.cat >= cap.catinic AND c.cat <= cap.catfim
WHERE cap.numcap = 1 AND c.ativo = true AND cap.ativo = true
ORDER BY c.cat;
```

### Pesquisar subcategorias por descrição

```sql
SELECT s.*, c.descricao as categoria_descricao
FROM cid10_subcategorias s
INNER JOIN cid10_categorias c ON s.categoria_cat = c.cat
WHERE LOWER(s.descricao) LIKE '%diabetes%' 
  AND s.ativo = true 
  AND c.ativo = true
ORDER BY s.subcat;
```

### Contar subcategorias por categoria

```sql
SELECT 
    c.cat,
    c.descricao,
    COUNT(s.id) as total_subcategorias
FROM cid10_categorias c
LEFT JOIN cid10_subcategorias s ON s.categoria_cat = c.cat AND s.ativo = true
WHERE c.ativo = true
GROUP BY c.cat, c.descricao
ORDER BY c.cat;
```

## Principais Dados Cadastrados

- **22 capítulos** CID-10
- **2045 categorias** CID-10
- **12451 subcategorias** CID-10
- Vários grupos e categorias CID-O

## Dicas de Pesquisa

1. **Códigos CID-10** sempre começam com uma letra (A-Z) seguida de números
2. **Categorias** têm 3 caracteres (ex: A00, B01, C50)
3. **Subcategorias** têm 4 ou mais caracteres (ex: A000, A001, A00.9)
4. Use o campo `ativo = true` para filtrar apenas registros válidos
5. Para buscar por descrição, use `LIKE` com `%` para busca parcial
6. Códigos são case-insensitive na busca, mas armazenados em maiúsculas

---

**Última atualização**: Dezembro 2025
