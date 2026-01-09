# Integração IBGE - Documentação de Endpoints

## Base URL

Todos os endpoints da integração IBGE estão sob o prefixo:

```
/api/v1/integracoes/ibge
```

**Nota**: O sistema usa `server.servlet.context-path=/api`, então os controllers usam apenas `/v1/...` internamente. A URL completa sempre inclui `/api` no início.

## Autenticação

Todos os endpoints requerem autenticação JWT (padrão do sistema UPSaúde).

**Header obrigatório:**
```
Authorization: Bearer {token}
```

## Endpoints

### 1. Sincronização Completa

Sincroniza todos os dados geográficos do IBGE (regiões, estados, municípios e população).

**Endpoint:**
```
POST /api/v1/integracoes/ibge/sincronizar
```

**Parâmetros de Query (todos opcionais):**

| Parâmetro | Tipo | Default | Descrição |
|-----------|------|---------|-----------|
| `regioes` | boolean | `true` | Sincronizar regiões |
| `estados` | boolean | `true` | Sincronizar estados |
| `municipios` | boolean | `true` | Sincronizar municípios |
| `populacao` | boolean | `true` | Atualizar população |

**Exemplo de Requisição:**

```bash
# Sincronização completa (todos os parâmetros default true)
POST /api/v1/integracoes/ibge/sincronizar

# Sincronização apenas de estados e municípios
POST /api/v1/integracoes/ibge/sincronizar?regioes=false&populacao=false

# Sincronização apenas de população
POST /api/v1/integracoes/ibge/sincronizar?regioes=false&estados=false&municipios=false
```

**Resposta de Sucesso (200 OK):**

```json
{
  "regioesSincronizadas": 5,
  "estadosSincronizados": 27,
  "municipiosSincronizados": 5570,
  "populacaoAtualizada": 5570,
  "regioesErros": [],
  "estadosErros": [],
  "municipiosErros": [],
  "populacaoErros": [],
  "tempoExecucao": {
    "seconds": 245,
    "nano": 0
  },
  "etapasExecutadas": [
    "Regiões",
    "Estados",
    "Municípios",
    "População"
  ]
}
```

**Resposta de Erro (503 Service Unavailable):**

```json
{
  "timestamp": "2026-01-07T12:00:00Z",
  "status": 503,
  "error": "Service Unavailable",
  "message": "Erro na integração com IBGE: Timeout após 3 tentativas",
  "path": "/api/v1/integracoes/ibge/sincronizar"
}
```

**Códigos de Status:**

- `200 OK`: Sincronização executada com sucesso
- `403 Forbidden`: Acesso negado (falta de autenticação)
- `503 Service Unavailable`: Erro na integração com IBGE

---

### 2. Sincronizar Regiões

Sincroniza apenas as regiões do Brasil (Norte, Nordeste, Centro-Oeste, Sudeste, Sul).

**Endpoint:**
```
POST /api/v1/integracoes/ibge/sincronizar/regioes
```

**Exemplo de Requisição:**

```bash
POST /api/v1/integracoes/ibge/sincronizar/regioes
```

**Resposta de Sucesso (200 OK):**

```json
{
  "regioesSincronizadas": 5,
  "estadosSincronizados": 0,
  "municipiosSincronizados": 0,
  "populacaoAtualizada": 0,
  "regioesErros": [],
  "estadosErros": [],
  "municipiosErros": [],
  "populacaoErros": [],
  "tempoExecucao": {
    "seconds": 2,
    "nano": 0
  },
  "etapasExecutadas": [
    "Regiões"
  ]
}
```

**Notas:**

- Esta é a primeira etapa da sincronização hierárquica
- Deve ser executada antes de sincronizar estados
- Normalmente retorna 5 regiões

---

### 3. Sincronizar Estados

Sincroniza apenas os estados do Brasil (27 unidades federativas).

**Endpoint:**
```
POST /api/v1/integracoes/ibge/sincronizar/estados
```

**Exemplo de Requisição:**

```bash
POST /api/v1/integracoes/ibge/sincronizar/estados
```

**Resposta de Sucesso (200 OK):**

```json
{
  "regioesSincronizadas": 0,
  "estadosSincronizados": 27,
  "municipiosSincronizados": 0,
  "populacaoAtualizada": 0,
  "regioesErros": [],
  "estadosErros": [],
  "municipiosErros": [],
  "populacaoErros": [],
  "tempoExecucao": {
    "seconds": 5,
    "nano": 0
  },
  "etapasExecutadas": [
    "Estados"
  ]
}
```

**Notas:**

- Requer que as regiões já tenham sido sincronizadas
- Atualiza dados IBGE dos estados existentes
- Normalmente retorna 27 estados

---

### 4. Sincronizar Municípios

Sincroniza apenas os municípios do Brasil (~5.570 municípios).

**Endpoint:**
```
POST /api/v1/integracoes/ibge/sincronizar/municipios
```

**Exemplo de Requisição:**

```bash
POST /api/v1/integracoes/ibge/sincronizar/municipios
```

**Resposta de Sucesso (200 OK):**

```json
{
  "regioesSincronizadas": 0,
  "estadosSincronizados": 0,
  "municipiosSincronizados": 5570,
  "populacaoAtualizada": 0,
  "regioesErros": [],
  "estadosErros": [],
  "municipiosErros": [
    "Erro ao sincronizar município 1234567: Timeout"
  ],
  "populacaoErros": [],
  "tempoExecucao": {
    "seconds": 180,
    "nano": 0
  },
  "etapasExecutadas": [
    "Municípios"
  ]
}
```

**Notas:**

- Requer que os estados já tenham sido sincronizados
- Processa municípios por estado (27 requisições)
- Pode levar vários minutos para completar
- Erros individuais não interrompem o processo

---

### 5. Atualizar População

Atualiza apenas a população estimada dos municípios já sincronizados.

**Endpoint:**
```
POST /api/v1/integracoes/ibge/sincronizar/populacao
```

**Exemplo de Requisição:**

```bash
POST /api/v1/integracoes/ibge/sincronizar/populacao
```

**Resposta de Sucesso (200 OK):**

```json
{
  "regioesSincronizadas": 0,
  "estadosSincronizados": 0,
  "municipiosSincronizados": 0,
  "populacaoAtualizada": 5570,
  "regioesErros": [],
  "estadosErros": [],
  "municipiosErros": [],
  "populacaoErros": [
    "Erro ao atualizar população do município 1234567: Município não encontrado"
  ],
  "tempoExecucao": {
    "seconds": 60,
    "nano": 0
  },
  "etapasExecutadas": [
    "População"
  ]
}
```

**Notas:**

- Requer que os municípios já tenham sido sincronizados
- Atualiza apenas municípios que possuem código IBGE
- Pode levar alguns minutos dependendo da quantidade

---

### 6. Validar Município por Código IBGE

Valida um município específico consultando diretamente a API IBGE.

**Endpoint:**
```
GET /api/v1/integracoes/ibge/validar-municipio/{codigoIbge}
```

**Parâmetros de Path:**

| Parâmetro | Tipo | Obrigatório | Descrição | Exemplo |
|-----------|------|-------------|-----------|---------|
| `codigoIbge` | string | Sim | Código IBGE do município (7 dígitos) | `3550308` |

**Exemplo de Requisição:**

```bash
GET /api/v1/integracoes/ibge/validar-municipio/3550308
```

**Resposta de Sucesso (200 OK):**

```json
{
  "id": 3550308,
  "nome": "São Paulo",
  "microrregiao": {
    "id": 35061,
    "nome": "São Paulo",
    "mesorregiao": {
      "id": 3515,
      "nome": "Metropolitana de São Paulo",
      "UF": {
        "id": 35,
        "sigla": "SP",
        "nome": "São Paulo",
        "regiao": {
          "id": 3,
          "sigla": "SE",
          "nome": "Sudeste"
        }
      }
    }
  }
}
```

**Resposta de Erro (404 Not Found):**

```json
{
  "timestamp": "2026-01-07T12:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Município não encontrado no IBGE",
  "path": "/api/v1/integracoes/ibge/validar-municipio/9999999"
}
```

**Resposta de Erro (503 Service Unavailable):**

```json
{
  "timestamp": "2026-01-07T12:00:00Z",
  "status": 503,
  "error": "Service Unavailable",
  "message": "Erro na integração com IBGE: Timeout após 2 tentativas",
  "path": "/api/v1/integracoes/ibge/validar-municipio/3550308"
}
```

**Códigos de Status:**

- `200 OK`: Município encontrado e validado
- `404 Not Found`: Município não encontrado no IBGE
- `403 Forbidden`: Acesso negado
- `503 Service Unavailable`: Erro na integração com IBGE

**Notas:**

- Consulta direta na API IBGE (não usa dados locais)
- Útil para validação em tempo real
- Timeout menor que sincronizações (10s vs 30s)

---

## Modelo de Dados

### IbgeSincronizacaoResponse

```json
{
  "regioesSincronizadas": 0,        // Integer: Quantidade de regiões sincronizadas
  "estadosSincronizados": 0,        // Integer: Quantidade de estados sincronizados
  "municipiosSincronizados": 0,     // Integer: Quantidade de municípios sincronizados
  "populacaoAtualizada": 0,         // Integer: Quantidade de municípios com população atualizada
  "regioesErros": [],               // List<String>: Lista de erros na sincronização de regiões
  "estadosErros": [],               // List<String>: Lista de erros na sincronização de estados
  "municipiosErros": [],            // List<String>: Lista de erros na sincronização de municípios
  "populacaoErros": [],             // List<String>: Lista de erros na atualização de população
  "tempoExecucao": {                // Duration: Tempo total de execução
    "seconds": 0,
    "nano": 0
  },
  "etapasExecutadas": []            // List<String>: Lista de etapas executadas
}
```

### IbgeMunicipioDTO

```json
{
  "id": 3550308,                    // Integer: Código IBGE do município
  "nome": "São Paulo",              // String: Nome do município
  "microrregiao": {                 // Objeto: Microrregião
    "id": 35061,                    // Integer: Código IBGE da microrregião
    "nome": "São Paulo",            // String: Nome da microrregião
    "mesorregiao": {                // Objeto: Mesorregião
      "id": 3515,                   // Integer: Código IBGE da mesorregião
      "nome": "Metropolitana de São Paulo", // String: Nome da mesorregião
      "UF": {                       // Objeto: Estado
        "id": 35,                   // Integer: Código IBGE do estado
        "sigla": "SP",              // String: Sigla do estado
        "nome": "São Paulo",        // String: Nome do estado
        "regiao": {                 // Objeto: Região
          "id": 3,                  // Integer: Código IBGE da região
          "sigla": "SE",            // String: Sigla da região
          "nome": "Sudeste"         // String: Nome da região
        }
      }
    }
  }
}
```

## Exemplos de Uso

### cURL

#### Sincronização Completa

```bash
curl -X POST "http://localhost:8080/api/v1/integracoes/ibge/sincronizar" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json"
```

#### Sincronização Apenas de Estados

```bash
curl -X POST "http://localhost:8080/api/v1/integracoes/ibge/sincronizar/estados" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json"
```

#### Validar Município

```bash
curl -X GET "http://localhost:8080/api/v1/integracoes/ibge/validar-municipio/3550308" \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json"
```

### JavaScript (Fetch API)

#### Sincronização Completa

```javascript
const response = await fetch('http://localhost:8080/api/v1/integracoes/ibge/sincronizar', {
  method: 'POST',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  }
});

const data = await response.json();
console.log('Regiões sincronizadas:', data.regioesSincronizadas);
console.log('Estados sincronizados:', data.estadosSincronizados);
console.log('Municípios sincronizados:', data.municipiosSincronizados);
```

#### Validar Município

```javascript
const codigoIbge = '3550308';
const response = await fetch(
  `http://localhost:8080/api/v1/integracoes/ibge/validar-municipio/${codigoIbge}`,
  {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  }
);

if (response.ok) {
  const municipio = await response.json();
  console.log('Município válido:', municipio.nome);
} else if (response.status === 404) {
  console.log('Município não encontrado');
}
```

### Java (RestTemplate)

```java
@Autowired
private RestTemplate restTemplate;

public IbgeSincronizacaoResponse sincronizar() {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);
    
    HttpEntity<?> entity = new HttpEntity<>(headers);
    
    ResponseEntity<IbgeSincronizacaoResponse> response = restTemplate.exchange(
        "http://localhost:8080/api/v1/integracoes/ibge/sincronizar",
        HttpMethod.POST,
        entity,
        IbgeSincronizacaoResponse.class
    );
    
    return response.getBody();
}
```

## Tratamento de Erros

### Erros Comuns

#### 503 Service Unavailable

**Causa**: Erro na comunicação com a API IBGE (timeout, conexão, etc.)

**Solução**: 
- Verificar conectividade com a API IBGE
- Aumentar timeout nas configurações
- Tentar novamente (retry automático já implementado)

#### 404 Not Found (Validação)

**Causa**: Código IBGE não existe na API IBGE

**Solução**: Verificar se o código IBGE está correto (7 dígitos)

#### 403 Forbidden

**Causa**: Token de autenticação inválido ou expirado

**Solução**: Obter novo token de autenticação

### Retry Automático

A integração implementa retry automático com backoff exponencial:

- **Máximo de tentativas**: 3 (sincronizações) ou 2 (validações)
- **Backoff**: 1000ms * número da tentativa
- **Timeout**: 30s (sincronizações) ou 10s (validações)

## Limites e Considerações

### Performance

- **Sincronização completa**: Pode levar 5-10 minutos
- **Sincronização de municípios**: Pode levar 3-5 minutos
- **Validação de município**: Geralmente < 1 segundo

### Rate Limiting

A API IBGE é pública mas pode ter limites de requisições. Recomenda-se:

- Executar sincronizações em horários de menor carga
- Não executar múltiplas sincronizações simultâneas
- Aguardar alguns segundos entre requisições grandes

### Dados

- **Regiões**: ~5 registros
- **Estados**: 27 registros
- **Municípios**: ~5.570 registros
- **População**: Atualizada para todos os municípios sincronizados

## Swagger/OpenAPI

A documentação completa dos endpoints está disponível no Swagger UI:

```
http://localhost:8080/api/swagger-ui.html
```

Tag: **Integração IBGE**

