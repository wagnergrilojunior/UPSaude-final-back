# Guia de Uso da API - Integração CNES

## 🚀 Introdução

Este guia fornece instruções práticas para usar a API de integração CNES. A API permite sincronizar e consultar dados de estabelecimentos, profissionais, equipes e outros recursos do CNES (Cadastro Nacional de Estabelecimentos de Saúde).

## 🔑 Autenticação

### Obter Token JWT

Antes de usar a API, é necessário obter um token de autenticação:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "nataligrilobarros@gmail.com",
    "password": "Natali@123"
  }'
```

**Resposta**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 3600
}
```

### Usar Token nas Requisições

Inclua o token no header `Authorization`:

```bash
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 📍 Base URL

```
http://localhost:8080/api
```

## 🎯 Endpoints Principais

### Estabelecimentos

#### 1. Sincronizar Estabelecimento por CNES

**POST** `/v1/cnes/estabelecimentos/{codigoCnes}/sincronizar`

Sincroniza um estabelecimento específico do CNES.

**Parâmetros**:
- `codigoCnes` (path): Código CNES de 7 dígitos (ex: `2530031`)
- `competencia` (query, opcional): Competência no formato AAAAMM (ex: `202501`)

**Exemplo**:
```bash
curl -X POST "http://localhost:8080/api/v1/cnes/estabelecimentos/2530031/sincronizar?competencia=202501" \
  -H "Authorization: Bearer <token>"
```

**Resposta**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "tipoEntidade": "ESTABELECIMENTO",
  "codigoIdentificador": "2530031",
  "competencia": "202501",
  "status": "SUCESSO",
  "dataSincronizacao": "2025-01-07T10:30:00Z",
  "registrosInseridos": 1,
  "registrosAtualizados": 0
}
```

#### 2. Sincronizar Estabelecimentos por Município

**POST** `/v1/cnes/estabelecimentos/municipio/{codigoMunicipio}/sincronizar`

Sincroniza todos os estabelecimentos de um município.

**Parâmetros**:
- `codigoMunicipio` (path): Código IBGE do município (ex: `530010`)
- `competencia` (query, opcional): Competência no formato AAAAMM

**Exemplo**:
```bash
curl -X POST "http://localhost:8080/api/v1/cnes/estabelecimentos/municipio/530010/sincronizar" \
  -H "Authorization: Bearer <token>"
```

#### 3. Buscar Estabelecimento no CNES

**GET** `/v1/cnes/estabelecimentos/{codigoCnes}/buscar`

Busca um estabelecimento no CNES sem sincronizar (apenas consulta).

**Exemplo**:
```bash
curl -X GET "http://localhost:8080/api/v1/cnes/estabelecimentos/2530031/buscar" \
  -H "Authorization: Bearer <token>"
```

### Profissionais

#### 1. Sincronizar Profissional por CNS

**POST** `/v1/cnes/profissionais/cns/{numeroCns}/sincronizar`

**Exemplo**:
```bash
curl -X POST "http://localhost:8080/api/v1/cnes/profissionais/cns/701009864978597/sincronizar" \
  -H "Authorization: Bearer <token>"
```

#### 2. Sincronizar Profissional por CPF

**POST** `/v1/cnes/profissionais/cpf/{numeroCpf}/sincronizar`

**Exemplo**:
```bash
curl -X POST "http://localhost:8080/api/v1/cnes/profissionais/cpf/12345678901/sincronizar" \
  -H "Authorization: Bearer <token>"
```

### Equipes

#### 1. Sincronizar Equipes de Estabelecimento

**POST** `/v1/cnes/equipes/estabelecimento/{codigoCnes}/sincronizar`

**Exemplo**:
```bash
curl -X POST "http://localhost:8080/api/v1/cnes/equipes/estabelecimento/2530031/sincronizar" \
  -H "Authorization: Bearer <token>"
```

### Consulta de Sincronizações

#### 1. Listar Sincronizações

**GET** `/v1/cnes/sincronizacoes`

Lista sincronizações com filtros opcionais.

**Parâmetros de Query**:
- `tipoEntidade` (opcional): ESTABELECIMENTO, PROFISSIONAL, EQUIPE, etc.
- `status` (opcional): PENDENTE, PROCESSANDO, SUCESSO, ERRO
- `dataInicio` (opcional): Data início (ISO 8601)
- `dataFim` (opcional): Data fim (ISO 8601)
- `page` (opcional): Número da página (padrão: 0)
- `size` (opcional): Tamanho da página (padrão: 20)

**Exemplo**:
```bash
curl -X GET "http://localhost:8080/api/v1/cnes/sincronizacoes?tipoEntidade=ESTABELECIMENTO&status=SUCESSO&page=0&size=10" \
  -H "Authorization: Bearer <token>"
```

#### 2. Obter Sincronização por ID

**GET** `/v1/cnes/sincronizacoes/{id}`

**Exemplo**:
```bash
curl -X GET "http://localhost:8080/api/v1/cnes/sincronizacoes/550e8400-e29b-41d4-a716-446655440000" \
  -H "Authorization: Bearer <token>"
```

#### 3. Consultar Histórico de Estabelecimento

**GET** `/v1/cnes/sincronizacoes/historico/estabelecimento/{estabelecimentoId}`

**Parâmetros de Query**:
- `competencia` (opcional): Competência específica (AAAAMM)

**Exemplo**:
```bash
curl -X GET "http://localhost:8080/api/v1/cnes/sincronizacoes/historico/estabelecimento/550e8400-e29b-41d4-a716-446655440000?competencia=202501" \
  -H "Authorization: Bearer <token>"
```

## 📊 Fluxograma de Uso

```mermaid
flowchart TD
    A[Iniciar] --> B[Autenticar]
    B --> C{Token Válido?}
    C -->|Não| D[Erro 401]
    C -->|Sim| E[Escolher Operação]
    
    E --> F[Sincronizar Estabelecimento]
    E --> G[Buscar Estabelecimento]
    E --> H[Sincronizar Profissional]
    E --> I[Consultar Sincronizações]
    
    F --> J[POST /estabelecimentos/{cnes}/sincronizar]
    G --> K[GET /estabelecimentos/{cnes}/buscar]
    H --> L[POST /profissionais/cns/{cns}/sincronizar]
    I --> M[GET /sincronizacoes]
    
    J --> N[Verificar Status]
    K --> O[Receber Dados]
    L --> N
    M --> P[Receber Lista]
    
    N --> Q{Status = SUCESSO?}
    Q -->|Sim| R[Operação Concluída]
    Q -->|Não| S[Verificar Erro]
    
    style B fill:#e1f5ff
    style J fill:#fff4e1
    style R fill:#e8f5e9
    style S fill:#ffebee
```

## 🔍 Códigos de Status HTTP

| Código | Significado | Quando Ocorre |
|--------|------------|---------------|
| 200 | OK | Operação bem-sucedida |
| 400 | Bad Request | Parâmetros inválidos |
| 401 | Unauthorized | Token ausente ou inválido |
| 403 | Forbidden | Acesso negado |
| 404 | Not Found | Recurso não encontrado |
| 500 | Internal Server Error | Erro no servidor |

## ⚠️ Validações

### Formato CNES
- Deve conter exatamente 7 dígitos numéricos
- Exemplo válido: `2530031`
- Exemplo inválido: `253003` (6 dígitos)

### Formato CNS
- Deve conter exatamente 15 dígitos numéricos
- Exemplo válido: `701009864978597`

### Formato Competência
- Formato: `AAAAMM` (ano + mês)
- Exemplo válido: `202501` (Janeiro 2025)
- Exemplo inválido: `2025-01` (formato incorreto)

## 📝 Exemplos Completos

### Exemplo 1: Sincronização Completa

```bash
# 1. Autenticar
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"nataligrilobarros@gmail.com","password":"Natali@123"}' \
  | jq -r '.token')

# 2. Sincronizar estabelecimento
curl -X POST "http://localhost:8080/api/v1/cnes/estabelecimentos/2530031/sincronizar" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"

# 3. Verificar status
curl -X GET "http://localhost:8080/api/v1/cnes/sincronizacoes?tipoEntidade=ESTABELECIMENTO&status=SUCESSO" \
  -H "Authorization: Bearer $TOKEN"
```

### Exemplo 2: Busca e Consulta

```bash
# 1. Buscar estabelecimento
curl -X GET "http://localhost:8080/api/v1/cnes/estabelecimentos/2530031/buscar" \
  -H "Authorization: Bearer $TOKEN"

# 2. Consultar histórico
ESTABELECIMENTO_ID="550e8400-e29b-41d4-a716-446655440000"
curl -X GET "http://localhost:8080/api/v1/cnes/sincronizacoes/historico/estabelecimento/$ESTABELECIMENTO_ID" \
  -H "Authorization: Bearer $TOKEN"
```

## 🛠️ Ferramentas Recomendadas

### Postman

Coleção Postman disponível em: `docs/CNES/postman/CNES_API.postman_collection.json`

### cURL

Exemplos de cURL fornecidos acima.

### Swagger UI

Acesse a documentação interativa em:
```
http://localhost:8080/api/swagger-ui.html
```

## 🔄 Processamento Assíncrono

**Nota**: Atualmente, as sincronizações são processadas de forma síncrona. Para operações longas:

1. A requisição retorna imediatamente com status `PENDENTE` ou `PROCESSANDO`
2. Consulte o status periodicamente usando `GET /sincronizacoes/{id}`
3. Quando `status = SUCESSO`, os dados estão disponíveis

## 📊 Monitoramento

### Health Check

```bash
curl http://localhost:8080/api/actuator/health
```

### Métricas

```bash
curl http://localhost:8080/api/actuator/metrics
```

## 🚨 Troubleshooting

### Erro 401 Unauthorized
- Verifique se o token está presente no header
- Verifique se o token não expirou
- Faça login novamente para obter novo token

### Erro 400 Bad Request
- Verifique o formato dos parâmetros
- CNES deve ter 7 dígitos
- CNS deve ter 15 dígitos
- Competência deve estar no formato AAAAMM

### Erro 500 Internal Server Error
- Verifique os logs da aplicação
- Verifique conectividade com DATASUS
- Tente novamente após alguns segundos

## 📚 Próximos Passos

- Veja [Exemplos de Requisições](./03-exemplos-requisicoes.md) para mais detalhes
- Consulte [Casos de Uso](./04-casos-uso.md) para cenários práticos
- Leia [Troubleshooting](./05-troubleshooting.md) para resolver problemas

