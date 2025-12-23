# API REST - SIGTAP

## 🌐 Endpoints Disponíveis

Esta documentação descreve os endpoints REST disponíveis para interagir com os dados SIGTAP.

## 🔐 Autenticação

Todos os endpoints requerem autenticação via **JWT Token**.

**Header obrigatório**:
```
Authorization: Bearer <token>
```

## 📥 Importação

### Importar Competência

Importa todos os arquivos de uma competência específica.

**Endpoint**: `POST /v1/sigtap/import/{competencia}`

**Parâmetros**:
- `competencia` (path): Competência no formato AAAAMM (ex: `202512`)

**Resposta de Sucesso** (200):
```json
{
  "competencia": "202512",
  "totalLinhasProcessadas": 198465,
  "totalErros": 0,
  "linhasPorArquivo": {
    "tb_procedimento.txt": 4957,
    "rl_procedimento_cid.txt": 81753,
    "rl_procedimento_ocupacao.txt": 87500
  },
  "erros": [],
  "sucesso": true
}
```

**Resposta com Erros** (200):
```json
{
  "competencia": "202512",
  "totalLinhasProcessadas": 150000,
  "totalErros": 5,
  "linhasPorArquivo": {
    "tb_procedimento.txt": 4957,
    "rl_procedimento_ocupacao.txt": 87500
  },
  "erros": [
    "rl_procedimento_ocupacao.txt: Erro ao processar linha 87501",
    "rl_procedimento_ocupacao.txt: Contexto Spring fechado"
  ],
  "sucesso": false
}
```

**Exemplo de Requisição**:
```bash
curl -X POST "http://localhost:8080/v1/sigtap/import/202512" \
  -H "Authorization: Bearer <token>"
```

### Listar Arquivos Disponíveis

Lista os arquivos disponíveis para importação de uma competência.

**Endpoint**: `GET /v1/sigtap/import/arquivos/{competencia}`

**Parâmetros**:
- `competencia` (path): Competência no formato AAAAMM

**Resposta** (200):
```json
{
  "competencia": "202512",
  "arquivos": [
    {
      "nome": "tb_procedimento.txt",
      "tamanho": 1234567,
      "existe": true,
      "layoutExiste": true
    },
    {
      "nome": "rl_procedimento_cid.txt",
      "tamanho": 2345678,
      "existe": true,
      "layoutExiste": true
    }
  ],
  "total": 41
}
```

**Exemplo de Requisição**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/import/arquivos/202512" \
  -H "Authorization: Bearer <token>"
```

## 📋 Consultas

### 1. Procedimentos (Medicamentos e Procedimentos)

#### Pesquisar Procedimentos

Busca procedimentos com paginação e filtros opcionais.

**Endpoint**: `GET /v1/sigtap/procedimentos`

**Parâmetros de Query**:
- `q` (opcional): Termo de busca livre em código ou nome do procedimento. Busca parcial e case-insensitive
- `grupoCodigo` (opcional): Código do grupo para filtrar (2 dígitos). Exemplos: "03" (Procedimentos clínicos), "04" (Procedimentos cirúrgicos), "06" (Medicamentos)
- `subgrupoCodigo` (opcional): Código do subgrupo para filtrar (2 dígitos). Deve ser usado junto com `grupoCodigo`
- `formaOrganizacaoCodigo` (opcional): Código da forma de organização para filtrar (2 dígitos). Deve ser usado junto com `grupoCodigo` e `subgrupoCodigo`
- `competencia` (opcional): Competência no formato AAAAMM (ex: 202512)
- `page` (opcional): Número da página (padrão: 0)
- `size` (opcional): Tamanho da página (padrão: 20)
- `sort` (opcional): Ordenação (ex: `codigoOficial,asc` ou `nome,desc`)

**Exemplo de Requisição - Buscar todos os procedimentos**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/procedimentos?q=0301010010&competencia=202512&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Requisição - Buscar todos os medicamentos (grupo 06)**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/procedimentos?grupoCodigo=06&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Requisição - Buscar medicamentos de um subgrupo específico**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/procedimentos?grupoCodigo=06&subgrupoCodigo=01&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Resposta** (200):
```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "codigoOficial": "0301010010",
      "nome": "CONSULTA MÉDICA EM ATENDIMENTO AMBULATORIAL",
      "competenciaInicial": "202501",
      "competenciaFinal": null,
      "grupoCodigo": "03",
      "grupoNome": "PROCEDIMENTOS CLINICOS",
      "subgrupoCodigo": "0301",
      "subgrupoNome": "CONSULTA MEDICA",
      "formaOrganizacaoCodigo": "01",
      "formaOrganizacaoNome": "AMBULATORIAL",
      "sexoPermitido": "AMBOS",
      "idadeMinima": null,
      "idadeMaxima": null,
      "valorServicoAmbulatorial": 23.50,
      "valorServicoHospitalar": null,
      "valorServicoProfissional": null
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 4957,
  "totalPages": 248
}
```

#### Obter Procedimento Detalhado

Retorna um procedimento específico com seus detalhes completos.

**Endpoint**: `GET /v1/sigtap/procedimentos/{codigo}`

**Parâmetros**:
- `codigo` (path): Código do procedimento
- `competencia` (query, opcional): Competência no formato AAAAMM

**Exemplo de Requisição**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/procedimentos/0301010010?competencia=202512" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Resposta** (200):
```json
{
  "procedimento": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "codigoOficial": "0301010010",
    "nome": "CONSULTA MÉDICA EM ATENDIMENTO AMBULATORIAL",
    "competenciaInicial": "202501",
    "competenciaFinal": null,
    "grupoCodigo": "03",
    "grupoNome": "PROCEDIMENTOS CLINICOS",
    "subgrupoCodigo": "0301",
    "subgrupoNome": "CONSULTA MEDICA",
    "formaOrganizacaoCodigo": "01",
    "formaOrganizacaoNome": "AMBULATORIAL",
    "sexoPermitido": "AMBOS",
    "idadeMinima": null,
    "idadeMaxima": null,
    "mediaDiasInternacao": null,
    "quantidadeMaximaDias": null,
    "limiteMaximo": null,
    "valorServicoHospitalar": null,
    "valorServicoAmbulatorial": 23.50,
    "valorServicoProfissional": null
  },
  "detalhe": {
    "id": "660e8400-e29b-41d4-a716-446655440001",
    "procedimentoId": "550e8400-e29b-41d4-a716-446655440000",
    "competenciaInicial": "202501",
    "competenciaFinal": null
  }
}
```

### 2. Serviços/Exames

#### Pesquisar Serviços/Exames

Busca serviços/exames com paginação.

**Endpoint**: `GET /v1/sigtap/servicos`

**Parâmetros de Query**:
- `q` (opcional): Termo de busca em código ou nome
- `page` (opcional): Número da página
- `size` (opcional): Tamanho da página

**Exemplo de Requisição**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/servicos?q=hemograma&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Resposta** (200):
```json
{
  "content": [
    {
      "id": "770e8400-e29b-41d4-a716-446655440002",
      "codigoOficial": "01",
      "nome": "SERVIÇO HOSPITALAR"
    },
    {
      "id": "880e8400-e29b-41d4-a716-446655440003",
      "codigoOficial": "02",
      "nome": "SERVIÇO AMBULATORIAL"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 2,
  "totalPages": 1
}
```

#### Obter Serviço por Código

**Endpoint**: `GET /v1/sigtap/servicos/{codigo}`

**Exemplo de Requisição**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/servicos/01" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Resposta** (200):
```json
{
  "id": "770e8400-e29b-41d4-a716-446655440002",
  "codigoOficial": "01",
  "nome": "SERVIÇO HOSPITALAR"
}
```

### 3. RENASES (Rede Nacional de Atenção Especializada em Saúde)

#### Pesquisar RENASES

**Endpoint**: `GET /v1/sigtap/renases`

**Parâmetros de Query**:
- `q` (opcional): Termo de busca em código ou nome
- `page` (opcional): Número da página
- `size` (opcional): Tamanho da página

**Exemplo de Requisição**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/renases?q=cardiologia&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Resposta** (200):
```json
{
  "content": [
    {
      "id": "990e8400-e29b-41d4-a716-446655440004",
      "codigoOficial": "01",
      "nome": "CENTRO DE CARDIOLOGIA"
    }
  ],
  "totalElements": 1
}
```

#### Obter RENASES por Código

**Endpoint**: `GET /v1/sigtap/renases/{codigo}`

### 4. Grupos

#### Listar Grupos

Retorna lista de todos os grupos SIGTAP.

**Endpoint**: `GET /v1/sigtap/grupos`

**Exemplo de Requisição**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/grupos" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Resposta** (200):
```json
[
  {
    "id": "aa0e8400-e29b-41d4-a716-446655440005",
    "codigoOficial": "03",
    "nome": "PROCEDIMENTOS CLINICOS",
    "competenciaInicial": "202501",
    "competenciaFinal": null
  },
  {
    "id": "bb0e8400-e29b-41d4-a716-446655440006",
    "codigoOficial": "04",
    "nome": "PROCEDIMENTOS CIRURGICOS",
    "competenciaInicial": "202501",
    "competenciaFinal": null
  }
]
```

### 5. Subgrupos e Formas de Organização

#### Pesquisar Subgrupos ou Formas de Organização

**Endpoint**: `GET /v1/sigtap/subgrupos`

Este endpoint tem **comportamento dinâmico** baseado nos parâmetros informados:

- **Apenas `grupoCodigo`**: Retorna **subgrupos** do grupo especificado
- **`grupoCodigo` + `subgrupoCodigo`**: Retorna **formas de organização** do subgrupo especificado

**Parâmetros de Query**:
- `q` (opcional): Termo de busca em código ou nome
- `grupoCodigo` (opcional): Código do grupo para filtrar (2 dígitos)
- `subgrupoCodigo` (opcional): Código do subgrupo para filtrar (2 dígitos). Quando usado junto com `grupoCodigo`, retorna formas de organização
- `competencia` (opcional): Competência no formato AAAAMM
- `page` (opcional): Número da página
- `size` (opcional): Tamanho da página

**Exemplo de Requisição - Buscar todos os subgrupos de um grupo**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/subgrupos?grupoCodigo=06&competencia=202512" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Requisição - Buscar formas de organização de um subgrupo específico**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/subgrupos?grupoCodigo=06&subgrupoCodigo=01&competencia=202512" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Requisição - Buscar subgrupos com termo de busca**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/subgrupos?grupoCodigo=03&q=consulta&competencia=202512" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Resposta - Subgrupos** (200):
```json
{
  "content": [
    {
      "id": "cc0e8400-e29b-41d4-a716-446655440007",
      "codigoOficial": "0301",
      "nome": "CONSULTA MEDICA",
      "competenciaInicial": "202501",
      "competenciaFinal": null,
      "grupoCodigo": "03",
      "grupoNome": "PROCEDIMENTOS CLINICOS"
    }
  ],
  "totalElements": 1
}
```

**Exemplo de Resposta - Formas de Organização** (200):
```json
{
  "content": [
    {
      "id": "dd0e8400-e29b-41d4-a716-446655440008",
      "codigoOficial": "01",
      "nome": "Pequenas cirurgias",
      "competenciaInicial": "202501",
      "competenciaFinal": null,
      "subgrupoCodigo": "01",
      "subgrupoNome": "Pequenas cirurgias e cirurgias de pele",
      "grupoCodigo": "04",
      "grupoNome": "Procedimentos cirúrgicos"
    }
  ],
  "totalElements": 1
}
```

#### Obter Subgrupo por Código

**Endpoint**: `GET /v1/sigtap/subgrupos/{codigo}`

**Parâmetros**:
- `codigo` (path): Código do subgrupo
- `grupoCodigo` (query, opcional): Código do grupo para busca mais precisa

### 6. Formas de Organização

#### Pesquisar Formas de Organização

**Endpoint**: `GET /v1/sigtap/formas-organizacao`

**Parâmetros de Query**:
- `q` (opcional): Termo de busca em código ou nome
- `grupoCodigo` (opcional): Código do grupo para filtrar (2 dígitos). Pode ser usado sozinho ou combinado com `subgrupoCodigo`
- `subgrupoCodigo` (opcional): Código do subgrupo para filtrar (2 dígitos). Pode ser usado sozinho ou combinado com `grupoCodigo`
- `competencia` (opcional): Competência no formato AAAAMM
- `page` (opcional): Número da página
- `size` (opcional): Tamanho da página

**Exemplo de Requisição - Filtrar por grupo**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/formas-organizacao?grupoCodigo=04" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Requisição - Filtrar por subgrupo**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/formas-organizacao?subgrupoCodigo=01&q=ambulatorial" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Requisição - Filtrar por grupo e subgrupo**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/formas-organizacao?grupoCodigo=04&subgrupoCodigo=01" \
  -H "Authorization: Bearer <token>"
```

#### Obter Forma de Organização por Código

**Endpoint**: `GET /v1/sigtap/formas-organizacao/{codigo}`

**Parâmetros**:
- `codigo` (path): Código da forma de organização
- `subgrupoCodigo` (query, opcional): Código do subgrupo para busca mais precisa

### 7. Habilitações

#### Pesquisar Habilitações

**Endpoint**: `GET /v1/sigtap/habilitacoes`

**Parâmetros de Query**:
- `q` (opcional): Termo de busca
- `competencia` (opcional): Competência no formato AAAAMM
- `page` (opcional): Número da página
- `size` (opcional): Tamanho da página

**Exemplo de Requisição**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/habilitacoes?q=hospital&competencia=202512" \
  -H "Authorization: Bearer <token>"
```

#### Obter Habilitação por Código

**Endpoint**: `GET /v1/sigtap/habilitacoes/{codigo}`

**Parâmetros**:
- `codigo` (path): Código da habilitação
- `competencia` (query, opcional): Competência no formato AAAAMM

### 8. TUSS (Terminologia Unificada da Saúde Suplementar)

#### Pesquisar TUSS

**Endpoint**: `GET /v1/sigtap/tuss`

**Parâmetros de Query**:
- `q` (opcional): Termo de busca em código ou nome
- `page` (opcional): Número da página
- `size` (opcional): Tamanho da página

**Exemplo de Requisição**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/tuss?q=10101010&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

#### Obter TUSS por Código

**Endpoint**: `GET /v1/sigtap/tuss/{codigo}`

### 9. Ocupações (CBO)

#### Pesquisar Ocupações

**Endpoint**: `GET /v1/sigtap/ocupacoes`

**Parâmetros de Query**:
- `q` (opcional): Termo de busca em código ou nome
- `page` (opcional): Número da página
- `size` (opcional): Tamanho da página

**Exemplo de Requisição**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/ocupacoes?q=médico&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Resposta** (200):
```json
{
  "content": [
    {
      "id": "ee0e8400-e29b-41d4-a716-446655440009",
      "codigoOficial": "225110",
      "nome": "MÉDICO CLINICO GERAL"
    }
  ],
  "totalElements": 1
}
```

#### Obter Ocupação por Código

**Endpoint**: `GET /v1/sigtap/ocupacoes/{codigo}`

### 10. Modalidades

#### Pesquisar Modalidades

**Endpoint**: `GET /v1/sigtap/modalidades`

**Parâmetros de Query**:
- `q` (opcional): Termo de busca
- `competencia` (opcional): Competência no formato AAAAMM
- `page` (opcional): Número da página
- `size` (opcional): Tamanho da página

**Exemplo de Requisição**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/modalidades?q=01&competencia=202512" \
  -H "Authorization: Bearer <token>"
```

#### Obter Modalidade por Código

**Endpoint**: `GET /v1/sigtap/modalidades/{codigo}`

**Parâmetros**:
- `codigo` (path): Código da modalidade
- `competencia` (query, opcional): Competência no formato AAAAMM

### 11. Compatibilidades

#### Pesquisar Compatibilidades

Busca compatibilidades entre procedimentos.

**Endpoint**: `GET /v1/sigtap/compatibilidades`

**Parâmetros de Query**:
- `codigoProcedimentoPrincipal` (opcional): Código do procedimento principal para filtrar
- `competencia` (opcional): Competência no formato AAAAMM
- `page` (opcional): Número da página
- `size` (opcional): Tamanho da página

**Exemplo de Requisição**:
```bash
curl -X GET "http://localhost:8080/v1/sigtap/compatibilidades?codigoProcedimentoPrincipal=0301010010&competencia=202512" \
  -H "Authorization: Bearer <token>"
```

**Exemplo de Resposta** (200):
```json
{
  "content": [
    {
      "id": "ff0e8400-e29b-41d4-a716-446655440010",
      "codigoCompatibilidadePossivel": "01",
      "tipoCompatibilidade": "PERMITIDA",
      "codigoProcedimentoPrincipal": "0301010010",
      "nomeProcedimentoPrincipal": "CONSULTA MÉDICA",
      "codigoProcedimentoSecundario": "0201010010",
      "nomeProcedimentoSecundario": "EXAME COMPLEMENTAR",
      "competenciaInicial": "202501",
      "competenciaFinal": null,
      "quantidadePermitida": 1
    }
  ],
  "totalElements": 1
}
```

## 📋 Exemplos de Uso Comuns

### Buscar um Medicamento/Procedimento por Código
```bash
curl -X GET "http://localhost:8080/v1/sigtap/procedimentos/0301010010?competencia=202512" \
  -H "Authorization: Bearer <token>"
```

### Buscar Exames por Nome
```bash
curl -X GET "http://localhost:8080/v1/sigtap/servicos?q=hemograma&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

### Listar Todos os Grupos de Procedimentos
```bash
curl -X GET "http://localhost:8080/v1/sigtap/grupos" \
  -H "Authorization: Bearer <token>"
```

### Buscar Procedimentos de uma Competência Específica
```bash
curl -X GET "http://localhost:8080/v1/sigtap/procedimentos?competencia=202512&page=0&size=50" \
  -H "Authorization: Bearer <token>"
```

### Buscar Subgrupos de um Grupo Específico
```bash
curl -X GET "http://localhost:8080/v1/sigtap/subgrupos?grupoCodigo=03&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

### Buscar Formas de Organização de um Subgrupo
```bash
curl -X GET "http://localhost:8080/v1/sigtap/subgrupos?grupoCodigo=06&subgrupoCodigo=01&page=0&size=20" \
  -H "Authorization: Bearer <token>"
```

## 📋 Códigos de Status HTTP

- **200 OK**: Requisição bem-sucedida
- **400 Bad Request**: Parâmetros inválidos
- **401 Unauthorized**: Token ausente ou inválido
- **404 Not Found**: Recurso não encontrado
- **500 Internal Server Error**: Erro interno do servidor

## 📋 Segurança

### Autenticação

Todos os endpoints requerem autenticação JWT. O token deve ser obtido através do endpoint de login:

```
POST /v1/auth/login
{
  "email": "usuario@example.com",
  "password": "senha"
}
```

### Autorização

Atualmente, todos os endpoints SIGTAP requerem apenas autenticação. Futuras implementações podem incluir controle de acesso baseado em roles.

## 📋 Exemplos de Uso

### Importar Competência Completa

```bash
# 1. Obter token
TOKEN=$(curl -X POST "http://localhost:8080/api/v1/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"email":"usuario@example.com","password":"senha"}' \
  | jq -r '.accessToken')

# 2. Importar competência
curl -X POST "http://localhost:8080/v1/sigtap/import/202512" \
  -H "Authorization: Bearer $TOKEN" \
  | jq
```

### Verificar Arquivos Disponéveis

```bash
curl -X GET "http://localhost:8080/v1/sigtap/import/arquivos/202512" \
  -H "Authorization: Bearer $TOKEN" \
  | jq
```

## 📋 Tratamento de Erros

### Erro de Autenticação

```json
{
  "timestamp": "2025-12-17T10:00:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Token inválido ou expirado"
}
```

### Erro de Validação

```json
{
  "timestamp": "2025-12-17T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Competência inválida. Formato esperado: AAAAMM"
}
```

### Erro de Recurso Não Encontrado

```json
{
  "timestamp": "2025-12-17T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Pasta da competência não encontrada: 202513"
}
```

## 📋 Observações Importantes

### Competência
- Formato: **AAAAMM** (ex: 202512 para dezembro de 2025)
- A competência é usada para filtrar dados válidos em uma determinada data

### Paginação
- Padrão: página 0 com 20 itens por página
- Use `page` e `size` para controlar a paginação
- Use `sort` para ordenar (ex: `sort=codigoOficial,asc` ou `sort=nome,desc`)

### Busca
- O parâmetro `q` busca tanto no código quanto no nome
- A busca é case-insensitive (não diferencia maiúsculas/minúsculas)
- A busca é parcial (LIKE) - não precisa do termo completo

### Filtros Hierárquicos
- **Filtro por Grupo**: Use apenas `grupoCodigo` para filtrar todos os procedimentos de um grupo
  - Exemplo: `grupoCodigo=04` retorna todos os procedimentos cirúrgicos
- **Filtro por Grupo + Subgrupo**: Use `grupoCodigo` e `subgrupoCodigo` para filtrar procedimentos de um subgrupo específico
  - Exemplo: `grupoCodigo=04&subgrupoCodigo=01` retorna procedimentos que começam com "0401"
- **Filtro por Grupo + Subgrupo + Forma de Organização**: Use os três parâmetros para filtrar procedimentos de uma forma de organização específica
  - Exemplo: `grupoCodigo=04&subgrupoCodigo=01&formaOrganizacaoCodigo=01` retorna procedimentos que começam com "040101"
- Múltiplos filtros podem ser combinados com busca por termo (`q`) para refinar ainda mais os resultados
- Filtros hierárquicos são baseados nos primeiros dígitos do código oficial do procedimento:
  - Primeiros 2 dígitos: grupo
  - Próximos 2 dígitos: subgrupo
  - Próximos 2 dígitos: forma de organização

### Estrutura de Resposta Paginada

Todas as listas paginadas seguem o formato padrão do Spring Data:

```json
{
  "content": [
    // Array de itens
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": {
      "sorted": false
    }
  },
  "totalElements": 100,
  "totalPages": 5,
  "last": false,
  "first": true,
  "numberOfElements": 20,
  "size": 20,
  "number": 0,
  "empty": false
}
```

## 📋 Swagger/OpenAPI

A documentação completa da API está disponível via Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

Navegue até as seções:
- **"SIGTAP Importação"** - Endpoints para importação de dados
- **"SIGTAP Consulta"** - Endpoints para consulta de dados (procedimentos, serviços, etc.)

---

**Última atualização**: Dezembro 2025
