# API REST - CID-10 e CID-O

Documentação completa da API REST para consulta de dados do CID-10 e CID-O.

## Base URL

```
/v1/cid
```

## Endpoints CID-10

### Capítulos

#### Listar Capítulos

Retorna lista de todos os capítulos CID-10 ordenados por número.

```http
GET /v1/cid/cid10/capitulos
```

**Resposta:**
```json
[
  {
    "id": "47df5b15-094a-4b15-af5c-b14fe67123b5",
    "numcap": 1,
    "catinic": "A00",
    "catfim": "B99",
    "descricao": "Capítulo I - Algumas doenças infecciosas e parasitárias",
    "descricaoAbreviada": "I.   Algumas doenças infecciosas e parasitárias"
  },
  {
    "id": "9a0fcea1-0614-4d2f-87ac-fc51c46f21a8",
    "numcap": 2,
    "catinic": "C00",
    "catfim": "D48",
    "descricao": "Capítulo II - Neoplasias [tumores]",
    "descricaoAbreviada": "II.  Neoplasias (tumores)"
  }
]
```

#### Obter Capítulo por Número

Retorna um capítulo específico pelo número.

```http
GET /v1/cid/cid10/capitulos/{numcap}
```

**Parâmetros:**
- `numcap` (path, obrigatório): Número do capítulo (1-22)

**Exemplo:**
```bash
curl -X GET "http://localhost:8080/v1/cid/cid10/capitulos/1"
```

**Resposta:**
```json
{
  "id": "47df5b15-094a-4b15-af5c-b14fe67123b5",
  "numcap": 1,
  "catinic": "A00",
  "catfim": "B99",
  "descricao": "Capítulo I - Algumas doenças infecciosas e parasitárias",
  "descricaoAbreviada": "I.   Algumas doenças infecciosas e parasitárias"
}
```

### Categorias

#### Pesquisar Categorias

Retorna uma lista paginada de categorias CID-10.

```http
GET /v1/cid/cid10/categorias
```

**Parâmetros de Query:**
- `q` (opcional): Termo de busca (código ou descrição)
- `page` (opcional): Número da página (padrão: 0)
- `size` (opcional): Tamanho da página (padrão: 20)
- `sort` (opcional): Ordenação (ex: `cat,asc`)

**Exemplos:**
```bash
# Listar todas as categorias (primeira página)
curl -X GET "http://localhost:8080/v1/cid/cid10/categorias?page=0&size=20"

# Pesquisar por código
curl -X GET "http://localhost:8080/v1/cid/cid10/categorias?q=A00"

# Pesquisar por descrição
curl -X GET "http://localhost:8080/v1/cid/cid10/categorias?q=cólera&page=0&size=10"
```

**Resposta:**
```json
{
  "content": [
    {
      "id": "53862b3f-c84f-4f35-b515-bf1357a75151",
      "cat": "A00",
      "classif": null,
      "descricao": "Cólera",
      "descrAbrev": "A00   Colera",
      "refer": null,
      "excluidos": null
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false
    },
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 2045,
  "totalPages": 103
}
```

#### Obter Categoria por Código

Retorna uma categoria específica pelo código.

```http
GET /v1/cid/cid10/categorias/{cat}
```

**Parâmetros:**
- `cat` (path, obrigatório): Código da categoria (ex: "A00")

**Exemplo:**
```bash
curl -X GET "http://localhost:8080/v1/cid/cid10/categorias/A00"
```

**Resposta:**
```json
{
  "id": "53862b3f-c84f-4f35-b515-bf1357a75151",
  "cat": "A00",
  "classif": null,
  "descricao": "Cólera",
  "descrAbrev": "A00   Colera",
  "refer": null,
  "excluidos": null
}
```

#### Listar Categorias por Intervalo

Retorna lista de categorias em um intervalo de códigos.

```http
GET /v1/cid/cid10/categorias/intervalo
```

**Parâmetros de Query:**
- `catinic` (obrigatório): Código inicial do intervalo (ex: "A00")
- `catfim` (obrigatório): Código final do intervalo (ex: "A09")

**Exemplo:**
```bash
curl -X GET "http://localhost:8080/v1/cid/cid10/categorias/intervalo?catinic=A00&catfim=A09"
```

**Resposta:**
```json
[
  {
    "id": "53862b3f-c84f-4f35-b515-bf1357a75151",
    "cat": "A00",
    "descricao": "Cólera",
    "descrAbrev": "A00   Colera"
  },
  {
    "id": "c8a09eb8-9610-48e8-8fd2-fa6e3999044a",
    "cat": "A01",
    "descricao": "Febres tifóide e paratifóide",
    "descrAbrev": "A01   Febres tifoide e paratifoide"
  }
]
```

### Subcategorias

#### Pesquisar Subcategorias

Retorna uma lista paginada de subcategorias CID-10.

```http
GET /v1/cid/cid10/subcategorias
```

**Parâmetros de Query:**
- `q` (opcional): Termo de busca (código ou descrição)
- `categoriaCodigo` (opcional): Filtrar por código da categoria (ex: "A00")
- `page` (opcional): Número da página (padrão: 0)
- `size` (opcional): Tamanho da página (padrão: 20)
- `sort` (opcional): Ordenação (ex: `subcat,asc`)

**Exemplos:**
```bash
# Pesquisar todas as subcategorias
curl -X GET "http://localhost:8080/v1/cid/cid10/subcategorias?page=0&size=20"

# Pesquisar por código
curl -X GET "http://localhost:8080/v1/cid/cid10/subcategorias?q=A000"

# Filtrar por categoria
curl -X GET "http://localhost:8080/v1/cid/cid10/subcategorias?categoriaCodigo=A00&page=0&size=20"
```

**Resposta:**
```json
{
  "content": [
    {
      "id": "1a014f60-d660-4962-a937-0be07aec8d0b",
      "subcat": "A000",
      "categoriaCat": "A00",
      "categoriaDescricao": "Cólera",
      "classif": null,
      "restrSexo": null,
      "causaObito": null,
      "descricao": "Cólera devida a Vibrio cholerae 01, biótipo cholerae",
      "descrAbrev": "A00.0 Colera dev Vibrio cholerae 01 biot cholerae",
      "refer": null,
      "excluidos": null
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false
    },
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 12451,
  "totalPages": 623
}
```

#### Obter Subcategoria por Código

Retorna uma subcategoria específica pelo código.

```http
GET /v1/cid/cid10/subcategorias/{subcat}
```

**Parâmetros:**
- `subcat` (path, obrigatório): Código da subcategoria (ex: "A000")

**Exemplo:**
```bash
curl -X GET "http://localhost:8080/v1/cid/cid10/subcategorias/A000"
```

**Resposta:**
```json
{
  "id": "1a014f60-d660-4962-a937-0be07aec8d0b",
  "subcat": "A000",
  "categoriaCat": "A00",
  "categoriaDescricao": "Cólera",
  "classif": null,
  "restrSexo": null,
  "causaObito": null,
  "descricao": "Cólera devida a Vibrio cholerae 01, biótipo cholerae",
  "descrAbrev": "A00.0 Colera dev Vibrio cholerae 01 biot cholerae",
  "refer": null,
  "excluidos": null
}
```

#### Listar Subcategorias por Categoria

Retorna lista de todas as subcategorias de uma categoria específica.

```http
GET /v1/cid/cid10/subcategorias/categoria/{categoriaCodigo}
```

**Parâmetros:**
- `categoriaCodigo` (path, obrigatório): Código da categoria (ex: "A00")

**Exemplo:**
```bash
curl -X GET "http://localhost:8080/v1/cid/cid10/subcategorias/categoria/A00"
```

**Resposta:**
```json
[
  {
    "id": "1a014f60-d660-4962-a937-0be07aec8d0b",
    "subcat": "A000",
    "categoriaCat": "A00",
    "categoriaDescricao": "Cólera",
    "descricao": "Cólera devida a Vibrio cholerae 01, biótipo cholerae"
  },
  {
    "id": "a80d726d-a5e6-49b9-a0bd-bdc5033e39a7",
    "subcat": "A001",
    "categoriaCat": "A00",
    "categoriaDescricao": "Cólera",
    "descricao": "Cólera devida a Vibrio cholerae 01, biótipo El Tor"
  }
]
```

### Grupos CID-10

#### Listar Grupos

Retorna lista de todos os grupos CID-10.

```http
GET /v1/cid/cid10/grupos
```

**Parâmetros de Query:**
- `q` (opcional): Termo de busca

**Exemplo:**
```bash
curl -X GET "http://localhost:8080/v1/cid/cid10/grupos?q=infecciosas"
```

**Resposta:**
```json
[
  {
    "id": "uuid-example",
    "catinic": "A00",
    "catfim": "B99",
    "descricao": "Algumas doenças infecciosas e parasitárias",
    "descricaoAbreviada": "Doenças infecciosas"
  }
]
```

## Endpoints CID-O

### Grupos CID-O

#### Listar Grupos CID-O

Retorna lista de todos os grupos CID-O.

```http
GET /v1/cid/cid-o/grupos
```

**Parâmetros de Query:**
- `q` (opcional): Termo de busca

**Exemplo:**
```bash
curl -X GET "http://localhost:8080/v1/cid/cid-o/grupos"
```

### Categorias CID-O

#### Pesquisar Categorias CID-O

Retorna uma lista paginada de categorias CID-O.

```http
GET /v1/cid/cid-o/categorias
```

**Parâmetros de Query:**
- `q` (opcional): Termo de busca (código ou descrição)
- `page` (opcional): Número da página (padrão: 0)
- `size` (opcional): Tamanho da página (padrão: 20)
- `sort` (opcional): Ordenação

**Exemplo:**
```bash
curl -X GET "http://localhost:8080/v1/cid/cid-o/categorias?q=C00&page=0&size=20"
```

#### Obter Categoria CID-O por Código

Retorna uma categoria CID-O específica pelo código.

```http
GET /v1/cid/cid-o/categorias/{cat}
```

**Parâmetros:**
- `cat` (path, obrigatório): Código da categoria

**Exemplo:**
```bash
curl -X GET "http://localhost:8080/v1/cid/cid-o/categorias/C00"
```

## Códigos de Status HTTP

- `200 OK`: Requisição bem-sucedida
- `404 Not Found`: Recurso não encontrado
- `403 Forbidden`: Acesso negado
- `400 Bad Request`: Requisição inválida
- `500 Internal Server Error`: Erro interno do servidor

## Exemplos de Uso Comum

### Buscar diagnóstico por código CID

```bash
# Buscar categoria principal
curl -X GET "http://localhost:8080/v1/cid/cid10/categorias/E11"

# Buscar subcategoria específica
curl -X GET "http://localhost:8080/v1/cid/cid10/subcategorias/E11.9"
```

### Listar todas as subcategorias de uma categoria

```bash
curl -X GET "http://localhost:8080/v1/cid/cid10/subcategorias/categoria/E11"
```

### Pesquisar por descrição

```bash
# Pesquisar categorias
curl -X GET "http://localhost:8080/v1/cid/cid10/categorias?q=diabetes"

# Pesquisar subcategorias
curl -X GET "http://localhost:8080/v1/cid/cid10/subcategorias?q=diabetes&page=0&size=10"
```

### Obter informações de um capítulo completo

```bash
# Obter capítulo
curl -X GET "http://localhost:8080/v1/cid/cid10/capitulos/4"

# Listar categorias do capítulo (usando intervalo)
curl -X GET "http://localhost:8080/v1/cid/cid10/categorias/intervalo?catinic=E00&catfim=E90"
```

---

**Última atualização**: Dezembro 2025
