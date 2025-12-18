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

**Endpoint**: `POST /api/sigtap/import/{competencia}`

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

**Exemplo de Requisiçãoo**:
```bash
curl -X POST "http://localhost:8080/api/sigtap/import/202512" \
  -H "Authorization: Bearer <token>"
```

### Listar Arquivos Disponéveis

Lista os arquivos disponíveis para importação de uma competência.

**Endpoint**: `GET /api/sigtap/import/{competencia}/arquivos`

**Parémetros**:
- `competencia` (path): Competéncia no formato AAAAMM

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

**Exemplo de Requisiçãoo**:
```bash
curl -X GET "http://localhost:8080/api/sigtap/import/202512/arquivos" \
  -H "Authorization: Bearer <token>"
```

## 📋 Consultas (Futuro)

> **Nota**: Endpoints de consulta ainda não foram implementados. Esta seção descreve a estrutura planejada.

### Buscar Procedimento

**Endpoint**: `GET /api/sigtap/procedimentos/{codigo}`

**Parémetros**:
- `codigo` (path): Cdigo do procedimento (ex: `03.01.01.001-0`)

**Resposta Esperada**:
```json
{
  "id": "uuid",
  "codigoOficial": "03.01.01.001-0",
  "nome": "CONSULTA MÉDICA EM ATENÇÃO BÁSICA",
  "competenciaInicial": "202512",
  "competenciaFinal": null,
  "sexoPermitido": "I",
  "idadeMinima": null,
  "idadeMaxima": null,
  "valorServicoHospitalar": 0.00,
  "valorServicoAmbulatorial": 45.00,
  "valorServicoProfissional": 30.00,
  "formaOrganizacao": {
    "codigoOficial": "03.01.01",
    "nome": "Consulta Mdica"
  }
}
```

### Listar Procedimentos

**Endpoint**: `GET /api/sigtap/procedimentos`

**Parémetros de Query**:
- `page` (opcional): Nmero da pégina (padréo: 0)
- `size` (opcional): Tamanho da pégina (padréo: 20)
- `nome` (opcional): Filtrar por nome (busca parcial)
- `competencia` (opcional): Filtrar por competência

**Resposta Esperada**:
```json
{
  "content": [
    {
      "id": "uuid",
      "codigoOficial": "03.01.01.001-0",
      "nome": "CONSULTA MÉDICA EM ATENÇÃO BÁSICA"
    }
  ],
  "page": {
    "number": 0,
    "size": 20,
    "totalElements": 4957,
    "totalPages": 248
  }
}
```

### Buscar CID de um Procedimento

**Endpoint**: `GET /api/sigtap/procedimentos/{codigo}/cids`

**Resposta Esperada**:
```json
{
  "procedimento": {
    "codigoOficial": "03.01.01.001-0",
    "nome": "CONSULTA MÉDICA EM ATENÇÃO BÁSICA"
  },
  "cids": [
    {
      "codigoOficial": "A00",
      "nome": "Cólera",
      "principal": true
    }
  ]
}
```

### Buscar Ocupações de um Procedimento

**Endpoint**: `GET /api/sigtap/procedimentos/{codigo}/ocupacoes`

**Resposta Esperada**:
```json
{
  "procedimento": {
    "codigoOficial": "03.01.01.001-0",
    "nome": "CONSULTA MÉDICA EM ATENÇÃO BÁSICA"
  },
  "ocupacoes": [
    {
      "codigoOficial": "225110",
      "nome": "Mdico cardiologista"
    }
  ]
}
```

## 📋 Cdigos de Status HTTP

- **200 OK**: Requisição bem-sucedida
- **400 Bad Request**: Parâmetros inválidos
- **401 Unauthorized**: Token ausente ou inválido
- **404 Not Found**: Recursão não encontrado
- **500 Internal Server Error**: Erro interno do servidor

## 📋 Seguranéa

### Autenticaçãoo

Todos os endpoints requerem autenticação JWT. O token deve ser obtido através do endpoint de login:

```
POST /api/v1/auth/login
{
  "email": "usuario@example.com",
  "password": "senha"
}
```

### Autorizaçãoo

Atualmente, todos os endpoints SIGTAP requerem apenas autenticação. Futuras implementações podem incluir controle de acessão baseado em roles.

## 📋 Exemplos de Usão

### Importar Competéncia Completa

```bash
# 1. Obter token
TOKEN=$(curl -X POST "http://localhost:8080/api/v1/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"email":"usuario@example.com","password":"senha"}' \
  | jq -r '.accessToken')

# 2. Importar competência
curl -X POST "http://localhost:8080/api/sigtap/import/202512" \
  -H "Authorization: Bearer $TOKEN" \
  | jq
```

### Verificar Arquivos Disponéveis

```bash
curl -X GET "http://localhost:8080/api/sigtap/import/202512/arquivos" \
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

### Erro de Validaçãoo

```json
{
  "timestamp": "2025-12-17T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Competéncia invélida. Formato esperado: AAAAMM"
}
```

### Erro de Recursão No Encontrado

```json
{
  "timestamp": "2025-12-17T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Pasta da competência não encontrada: 202513"
}
```

## 📋 Swagger/OpenAPI

A documentação completa da API está disponível via Swagger UI:

```
http://localhost:8080/api/swagger-ui.html
```

Navegue até a seção **"SIGTAP Importação"** para ver todos os endpoints disponíveis.

---

**Última atualização**: Dezembro 2025
