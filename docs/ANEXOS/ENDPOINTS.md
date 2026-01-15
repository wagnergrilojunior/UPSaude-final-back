# Catálogo de Endpoints — Módulo de Anexos

## Base URL

```
http://localhost:8080/api/v1/anexos
```

## Autenticação

Todos os endpoints requerem autenticação:

```
Authorization: Bearer <TOKEN>
```

---

## 1. Upload de Anexo

**POST** `/v1/anexos`

Faz upload de um arquivo e vincula a uma entidade do sistema.

### Request

**Content-Type**: `multipart/form-data`

**Campos**:
- `file` (obrigatório) - Arquivo a ser anexado
- `targetType` (obrigatório) - Tipo do recurso alvo (PACIENTE, AGENDAMENTO, etc.)
- `targetId` (obrigatório) - ID do recurso alvo (UUID)
- `categoria` (opcional) - Categoria do anexo (LAUDO, EXAME, DOCUMENTO, etc.)
- `visivelParaPaciente` (opcional) - Se é visível para o paciente (padrão: false)
- `descricao` (opcional) - Descrição do anexo
- `tags` (opcional) - Tags separadas por vírgula

### Response

**201 Created**

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2026-01-14T10:30:00Z",
  "updatedAt": "2026-01-14T10:30:00Z",
  "targetType": "PACIENTE",
  "targetId": "123e4567-e89b-12d3-a456-426614174000",
  "fileNameOriginal": "documento_rg.pdf",
  "mimeType": "application/pdf",
  "sizeBytes": 245760,
  "categoria": "DOCUMENTO",
  "visivelParaPaciente": false,
  "status": "ATIVO",
  "descricao": "Documento de identidade",
  "tags": "identidade,cpf",
  "criadoPor": "789e0123-e45b-67c8-d901-234567890abc"
}
```

### Exemplo cURL

```bash
curl -X POST "http://localhost:8080/api/v1/anexos" \
  -H "Authorization: Bearer <TOKEN>" \
  -F "file=@/path/to/documento.pdf" \
  -F "targetType=PACIENTE" \
  -F "targetId=123e4567-e89b-12d3-a456-426614174000" \
  -F "categoria=DOCUMENTO" \
  -F "visivelParaPaciente=false" \
  -F "descricao=Documento de identidade" \
  -F "tags=identidade,cpf"
```

### Códigos de Erro

- **400 Bad Request**: Arquivo inválido ou dados incorretos
- **403 Forbidden**: Acesso negado ao recurso alvo
- **404 Not Found**: Recurso alvo não encontrado
- **413 Payload Too Large**: Arquivo excede 50MB

---

## 2. Listar Anexos

**GET** `/v1/anexos`

Retorna lista paginada de anexos filtrados por targetType e targetId.

### Query Parameters

- `targetType` (obrigatório) - Tipo do recurso alvo
- `targetId` (obrigatório) - ID do recurso alvo
- `categoria` (opcional) - Filtrar por categoria
- `status` (opcional) - Filtrar por status
- `visivelParaPaciente` (opcional) - Filtrar apenas visíveis para paciente
- `page` (opcional) - Número da página (padrão: 0)
- `size` (opcional) - Tamanho da página (padrão: 20)
- `sort` (opcional) - Ordenação (ex: `createdAt,desc`)

### Response

**200 OK**

```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "createdAt": "2026-01-14T10:30:00Z",
      "updatedAt": "2026-01-14T10:30:00Z",
      "targetType": "PACIENTE",
      "targetId": "123e4567-e89b-12d3-a456-426614174000",
      "fileNameOriginal": "documento_rg.pdf",
      "mimeType": "application/pdf",
      "sizeBytes": 245760,
      "categoria": "DOCUMENTO",
      "visivelParaPaciente": false,
      "status": "ATIVO",
      "descricao": "Documento de identidade",
      "tags": "identidade,cpf",
      "criadoPor": "789e0123-e45b-67c8-d901-234567890abc"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": {
      "sorted": true,
      "unsorted": false
    }
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true,
  "numberOfElements": 1
}
```

### Exemplo cURL

```bash
curl -X GET "http://localhost:8080/api/v1/anexos?targetType=PACIENTE&targetId=123e4567-e89b-12d3-a456-426614174000&page=0&size=20" \
  -H "Authorization: Bearer <TOKEN>"
```

---

## 3. Obter Anexo por ID

**GET** `/v1/anexos/{id}`

Retorna um anexo específico pelo seu ID.

### Path Parameters

- `id` (obrigatório) - ID do anexo (UUID)

### Response

**200 OK**

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2026-01-14T10:30:00Z",
  "updatedAt": "2026-01-14T10:30:00Z",
  "targetType": "PACIENTE",
  "targetId": "123e4567-e89b-12d3-a456-426614174000",
  "fileNameOriginal": "documento_rg.pdf",
  "mimeType": "application/pdf",
  "sizeBytes": 245760,
  "categoria": "DOCUMENTO",
  "visivelParaPaciente": false,
  "status": "ATIVO",
  "descricao": "Documento de identidade",
  "tags": "identidade,cpf",
  "criadoPor": "789e0123-e45b-67c8-d901-234567890abc"
}
```

### Exemplo cURL

```bash
curl -X GET "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000" \
  -H "Authorization: Bearer <TOKEN>"
```

---

## 4. Download Direto

**GET** `/v1/anexos/{id}/download`

Faz download direto do arquivo via streaming.

### Path Parameters

- `id` (obrigatório) - ID do anexo (UUID)

### Response

**200 OK**

- **Content-Type**: Tipo MIME do arquivo
- **Content-Disposition**: `attachment; filename="nome_arquivo.ext"`
- **Content-Length**: Tamanho do arquivo em bytes
- **Body**: Stream do arquivo

### Exemplo cURL

```bash
curl -X GET "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000/download" \
  -H "Authorization: Bearer <TOKEN>" \
  --output documento.pdf
```

### Códigos de Erro

- **403 Forbidden**: Acesso negado
- **404 Not Found**: Anexo não encontrado
- **400 Bad Request**: Anexo excluído

---

## 5. Obter Miniatura (Thumbnail)

**GET** `/v1/anexos/{id}/thumbnail`

Retorna URL assinada da miniatura do anexo (apenas para imagens).

### Path Parameters

- `id` (obrigatório) - ID do anexo (UUID)

### Query Parameters

- `width` (opcional) - Largura da miniatura em pixels (padrão: 200)
- `height` (opcional) - Altura da miniatura em pixels (padrão: 200)

### Response

**200 OK**

```json
{
  "thumbnailUrl": "https://xxx.supabase.co/storage/v1/object/sign/anexos/...",
  "width": "200",
  "height": "200"
}
```

### Exemplo cURL

```bash
curl -X GET "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000/thumbnail?width=200&height=200" \
  -H "Authorization: Bearer <TOKEN>"
```

### Códigos de Erro

- **400 Bad Request**: Anexo não é uma imagem
- **403 Forbidden**: Acesso negado
- **404 Not Found**: Anexo não encontrado

---

## 6. Gerar URL Assinada para Download

**POST** `/v1/anexos/{id}/download-url`

Gera uma URL assinada temporária para download do anexo.

### Path Parameters

- `id` (obrigatório) - ID do anexo (UUID)

### Query Parameters

- `expiresIn` (opcional) - Tempo de expiração em segundos (padrão: 300)

### Response

**200 OK**

```json
{
  "signedUrl": "https://xxx.supabase.co/storage/v1/object/sign/anexos/...",
  "expiresAt": "2026-01-14T10:35:00Z",
  "fileName": "documento_rg.pdf",
  "mimeType": "application/pdf",
  "sizeBytes": 245760
}
```

### Exemplo cURL

```bash
curl -X POST "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000/download-url?expiresIn=600" \
  -H "Authorization: Bearer <TOKEN>"
```

---

## 7. Listagem Completa para Gestão/Admin

**GET** `/v1/anexos/gestao`

Retorna lista paginada completa de anexos com todos os filtros e informações detalhadas.

### Query Parameters

- `targetType` (opcional) - Tipo do recurso alvo
- `targetId` (opcional) - ID do recurso alvo
- `categoria` (opcional) - Filtrar por categoria
- `status` (opcional) - Filtrar por status
- `visivelParaPaciente` (opcional) - Filtrar apenas visíveis para paciente
- `fileName` (opcional) - Busca parcial por nome do arquivo
- `criadoPor` (opcional) - Filtrar por ID do usuário que criou
- `dataInicio` (opcional) - Data de início (ISO 8601)
- `dataFim` (opcional) - Data de fim (ISO 8601)
- `page` (opcional) - Número da página (padrão: 0)
- `size` (opcional) - Tamanho da página (padrão: 20)
- `sort` (opcional) - Ordenação

### Response

**200 OK**

```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "createdAt": "2026-01-14T10:30:00Z",
      "updatedAt": "2026-01-14T10:30:00Z",
      "targetType": "PACIENTE",
      "targetId": "123e4567-e89b-12d3-a456-426614174000",
      "fileNameOriginal": "documento_rg.pdf",
      "mimeType": "application/pdf",
      "sizeBytes": 245760,
      "sizeFormatted": "240.00 KB",
      "categoria": "DOCUMENTO",
      "visivelParaPaciente": false,
      "status": "ATIVO",
      "descricao": "Documento de identidade",
      "tags": "identidade,cpf",
      "criadoPor": "789e0123-e45b-67c8-d901-234567890abc",
      "criadoPorNome": "João Silva",
      "excluidoPor": null,
      "excluidoPorNome": null,
      "thumbnailUrl": null,
      "isImagem": false,
      "extensao": "pdf"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### Exemplo cURL

```bash
curl -X GET "http://localhost:8080/api/v1/anexos/gestao?targetType=PACIENTE&status=ATIVO&dataInicio=2026-01-01T00:00:00Z&page=0&size=20" \
  -H "Authorization: Bearer <TOKEN>"
```

---

## 8. Atualizar Metadados

**PATCH** `/v1/anexos/{id}`

Atualiza metadados do anexo (categoria, visibilidade, descrição, tags, status).

### Path Parameters

- `id` (obrigatório) - ID do anexo (UUID)

### Request Body

```json
{
  "categoria": "DOCUMENTO",
  "visivelParaPaciente": true,
  "status": "ATIVO",
  "descricao": "Documento atualizado",
  "tags": "identidade,cpf,atualizado"
}
```

**Todos os campos são opcionais** - apenas os campos enviados serão atualizados.

### Response

**200 OK**

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2026-01-14T10:30:00Z",
  "updatedAt": "2026-01-14T11:00:00Z",
  "targetType": "PACIENTE",
  "targetId": "123e4567-e89b-12d3-a456-426614174000",
  "fileNameOriginal": "documento_rg.pdf",
  "mimeType": "application/pdf",
  "sizeBytes": 245760,
  "categoria": "DOCUMENTO",
  "visivelParaPaciente": true,
  "status": "ATIVO",
  "descricao": "Documento atualizado",
  "tags": "identidade,cpf,atualizado",
  "criadoPor": "789e0123-e45b-67c8-d901-234567890abc"
}
```

### Exemplo cURL

```bash
curl -X PATCH "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "visivelParaPaciente": true,
    "descricao": "Documento atualizado"
  }'
```

---

## 9. Excluir Anexo

**DELETE** `/v1/anexos/{id}`

Exclui um anexo (soft delete). Opcionalmente pode deletar o arquivo do storage também.

### Path Parameters

- `id` (obrigatório) - ID do anexo (UUID)

### Query Parameters

- `deleteFromStorage` (opcional) - Se true, deleta o arquivo do storage também (padrão: false)

### Response

**204 No Content**

### Exemplo cURL

```bash
# Soft delete (padrão)
curl -X DELETE "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000" \
  -H "Authorization: Bearer <TOKEN>"

# Hard delete (remove do storage também)
curl -X DELETE "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000?deleteFromStorage=true" \
  -H "Authorization: Bearer <TOKEN>"
```

### Códigos de Erro

- **403 Forbidden**: Acesso negado
- **404 Not Found**: Anexo não encontrado

---

## Resumo de Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/v1/anexos` | Upload de anexo |
| GET | `/v1/anexos` | Listar anexos (filtrado por target) |
| GET | `/v1/anexos/{id}` | Obter anexo por ID |
| GET | `/v1/anexos/{id}/download` | Download direto (streaming) |
| GET | `/v1/anexos/{id}/thumbnail` | Obter URL da miniatura |
| POST | `/v1/anexos/{id}/download-url` | Gerar URL assinada |
| GET | `/v1/anexos/gestao` | Listagem completa (admin) |
| PATCH | `/v1/anexos/{id}` | Atualizar metadados |
| DELETE | `/v1/anexos/{id}` | Excluir anexo |

---

## Códigos de Status HTTP

- **200 OK**: Operação bem-sucedida
- **201 Created**: Recurso criado com sucesso
- **204 No Content**: Operação bem-sucedida sem conteúdo
- **400 Bad Request**: Dados inválidos ou arquivo inválido
- **403 Forbidden**: Acesso negado
- **404 Not Found**: Recurso não encontrado
- **413 Payload Too Large**: Arquivo muito grande (>50MB)
- **500 Internal Server Error**: Erro interno do servidor

---

## Validações

### Upload

- Arquivo não pode ser vazio
- Tamanho máximo: 50MB
- Tipo MIME deve ser válido
- targetType e targetId são obrigatórios
- Usuário deve ter acesso ao recurso alvo

### Download

- Anexo deve existir e pertencer ao tenant
- Anexo não pode estar excluído
- Usuário deve ter acesso ao recurso alvo

### Thumbnail

- Anexo deve ser uma imagem (MIME type `image/*`)
- Anexo deve estar ativo
