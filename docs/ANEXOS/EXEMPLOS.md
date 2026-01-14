# Exemplos Práticos — Módulo de Anexos

## Exemplos de Uso

### 1. Upload de Documento do Paciente

**Cenário**: Anexar RG do paciente ao cadastro.

```bash
curl -X POST "http://localhost:8080/api/v1/anexos" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -F "file=@/home/usuario/documentos/rg_paciente.pdf" \
  -F "targetType=PACIENTE" \
  -F "targetId=123e4567-e89b-12d3-a456-426614174000" \
  -F "categoria=DOCUMENTO" \
  -F "visivelParaPaciente=false" \
  -F "descricao=Documento de identidade (RG)" \
  -F "tags=identidade,rg"
```

**Response**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2026-01-14T10:30:00Z",
  "targetType": "PACIENTE",
  "targetId": "123e4567-e89b-12d3-a456-426614174000",
  "fileNameOriginal": "rg_paciente.pdf",
  "mimeType": "application/pdf",
  "sizeBytes": 245760,
  "categoria": "DOCUMENTO",
  "status": "ATIVO"
}
```

---

### 2. Upload de Laudo de Exame

**Cenário**: Anexar resultado de exame à consulta.

```bash
curl -X POST "http://localhost:8080/api/v1/anexos" \
  -H "Authorization: Bearer <TOKEN>" \
  -F "file=@/home/usuario/exames/hemograma_completo.pdf" \
  -F "targetType=CONSULTA" \
  -F "targetId=789e0123-e45b-67c8-d901-234567890abc" \
  -F "categoria=EXAME" \
  -F "visivelParaPaciente=true" \
  -F "descricao=Hemograma completo - Resultado normal"
```

---

### 3. Upload de Imagem (Radiografia)

**Cenário**: Anexar imagem de radiografia ao atendimento.

```bash
curl -X POST "http://localhost:8080/api/v1/anexos" \
  -H "Authorization: Bearer <TOKEN>" \
  -F "file=@/home/usuario/imagens/radiografia_torax.jpg" \
  -F "targetType=ATENDIMENTO" \
  -F "targetId=456e7890-e12b-34c5-d678-901234567890" \
  -F "categoria=IMAGEM" \
  -F "visivelParaPaciente=true" \
  -F "descricao=Radiografia de tórax"
```

---

### 4. Listar Todos os Anexos de um Paciente

**Cenário**: Visualizar todos os documentos anexados ao paciente.

```bash
curl -X GET "http://localhost:8080/api/v1/anexos?targetType=PACIENTE&targetId=123e4567-e89b-12d3-a456-426614174000&page=0&size=20&sort=createdAt,desc" \
  -H "Authorization: Bearer <TOKEN>"
```

**Response**:
```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "fileNameOriginal": "rg_paciente.pdf",
      "mimeType": "application/pdf",
      "sizeBytes": 245760,
      "categoria": "DOCUMENTO",
      "createdAt": "2026-01-14T10:30:00Z"
    },
    {
      "id": "660e9500-f39c-52e5-b827-557766551111",
      "fileNameOriginal": "cpf_paciente.pdf",
      "mimeType": "application/pdf",
      "sizeBytes": 180000,
      "categoria": "DOCUMENTO",
      "createdAt": "2026-01-13T14:20:00Z"
    }
  ],
  "totalElements": 2,
  "totalPages": 1
}
```

---

### 5. Filtrar Anexos por Categoria

**Cenário**: Listar apenas exames de um paciente.

```bash
curl -X GET "http://localhost:8080/api/v1/anexos?targetType=PACIENTE&targetId=123e4567-e89b-12d3-a456-426614174000&categoria=EXAME" \
  -H "Authorization: Bearer <TOKEN>"
```

---

### 6. Download Direto de Arquivo

**Cenário**: Baixar documento do paciente.

```bash
curl -X GET "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000/download" \
  -H "Authorization: Bearer <TOKEN>" \
  --output documento_baixado.pdf
```

**Com JavaScript/Fetch**:
```javascript
fetch('http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000/download', {
  headers: {
    'Authorization': 'Bearer <TOKEN>'
  }
})
.then(response => response.blob())
.then(blob => {
  const url = window.URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = 'documento.pdf';
  a.click();
});
```

---

### 7. Gerar URL Assinada para Download

**Cenário**: Obter URL temporária para download (útil para frontend).

```bash
curl -X POST "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000/download-url?expiresIn=600" \
  -H "Authorization: Bearer <TOKEN>"
```

**Response**:
```json
{
  "signedUrl": "https://xxx.supabase.co/storage/v1/object/sign/anexos/tenant/...?token=...",
  "expiresAt": "2026-01-14T10:40:00Z",
  "fileName": "rg_paciente.pdf",
  "mimeType": "application/pdf",
  "sizeBytes": 245760
}
```

**Uso no Frontend**:
```javascript
// Obter URL assinada
const response = await fetch('/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000/download-url', {
  method: 'POST',
  headers: {
    'Authorization': 'Bearer <TOKEN>'
  }
});
const data = await response.json();

// Usar URL assinada para download
window.open(data.signedUrl, '_blank');
```

---

### 8. Obter Miniatura de Imagem

**Cenário**: Exibir preview de imagem na listagem.

```bash
curl -X GET "http://localhost:8080/api/v1/anexos/660e9500-f39c-52e5-b827-557766551111/thumbnail?width=150&height=150" \
  -H "Authorization: Bearer <TOKEN>"
```

**Response**:
```json
{
  "thumbnailUrl": "https://xxx.supabase.co/storage/v1/object/sign/anexos/...",
  "width": "150",
  "height": "150"
}
```

**Uso no Frontend**:
```javascript
// Obter URL da miniatura
const response = await fetch('/api/v1/anexos/660e9500-f39c-52e5-b827-557766551111/thumbnail?width=150&height=150', {
  headers: {
    'Authorization': 'Bearer <TOKEN>'
  }
});
const data = await response.json();

// Exibir miniatura
const img = document.createElement('img');
img.src = data.thumbnailUrl;
img.alt = 'Thumbnail';
document.body.appendChild(img);
```

---

### 9. Gestão Completa - Listar Todos os Anexos

**Cenário**: Administrador visualiza todos os anexos do sistema com filtros.

```bash
curl -X GET "http://localhost:8080/api/v1/anexos/gestao?status=ATIVO&dataInicio=2026-01-01T00:00:00Z&dataFim=2026-01-31T23:59:59Z&page=0&size=50&sort=createdAt,desc" \
  -H "Authorization: Bearer <TOKEN>"
```

**Response**:
```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "createdAt": "2026-01-14T10:30:00Z",
      "targetType": "PACIENTE",
      "targetId": "123e4567-e89b-12d3-a456-426614174000",
      "fileNameOriginal": "rg_paciente.pdf",
      "mimeType": "application/pdf",
      "sizeBytes": 245760,
      "sizeFormatted": "240.00 KB",
      "categoria": "DOCUMENTO",
      "visivelParaPaciente": false,
      "status": "ATIVO",
      "criadoPor": "789e0123-e45b-67c8-d901-234567890abc",
      "criadoPorNome": "João Silva",
      "isImagem": false,
      "extensao": "pdf"
    }
  ],
  "totalElements": 1,
  "totalPages": 1
}
```

---

### 10. Buscar por Nome de Arquivo

**Cenário**: Encontrar anexo específico pelo nome.

```bash
curl -X GET "http://localhost:8080/api/v1/anexos/gestao?fileName=rg&page=0&size=20" \
  -H "Authorization: Bearer <TOKEN>"
```

---

### 11. Filtrar por Usuário que Criou

**Cenário**: Ver todos os anexos criados por um usuário específico.

```bash
curl -X GET "http://localhost:8080/api/v1/anexos/gestao?criadoPor=789e0123-e45b-67c8-d901-234567890abc&page=0&size=20" \
  -H "Authorization: Bearer <TOKEN>"
```

---

### 12. Atualizar Visibilidade do Anexo

**Cenário**: Tornar anexo visível para o paciente.

```bash
curl -X PATCH "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "visivelParaPaciente": true
  }'
```

---

### 13. Atualizar Categoria e Descrição

**Cenário**: Corrigir categoria e adicionar descrição.

```bash
curl -X PATCH "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "categoria": "LAUDO",
    "descricao": "Laudo médico atualizado",
    "tags": "laudo,medico,atualizado"
  }'
```

---

### 14. Inativar Anexo

**Cenário**: Ocultar anexo temporariamente sem excluir.

```bash
curl -X PATCH "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "status": "INATIVO"
  }'
```

---

### 15. Excluir Anexo (Soft Delete)

**Cenário**: Excluir anexo mantendo arquivo no storage.

```bash
curl -X DELETE "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000" \
  -H "Authorization: Bearer <TOKEN>"
```

---

### 16. Excluir Anexo (Hard Delete)

**Cenário**: Excluir anexo e remover arquivo do storage.

```bash
curl -X DELETE "http://localhost:8080/api/v1/anexos/550e8400-e29b-41d4-a716-446655440000?deleteFromStorage=true" \
  -H "Authorization: Bearer <TOKEN>"
```

---

## Exemplos de Integração Frontend

### React/TypeScript

```typescript
// Upload de arquivo
const uploadAnexo = async (file: File, targetType: string, targetId: string) => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('targetType', targetType);
  formData.append('targetId', targetId);
  formData.append('categoria', 'DOCUMENTO');
  formData.append('visivelParaPaciente', 'false');

  const response = await fetch('/api/v1/anexos', {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`
    },
    body: formData
  });

  return await response.json();
};

// Listar anexos
const listarAnexos = async (targetType: string, targetId: string) => {
  const response = await fetch(
    `/api/v1/anexos?targetType=${targetType}&targetId=${targetId}`,
    {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    }
  );

  return await response.json();
};

// Download direto
const downloadAnexo = async (anexoId: string, fileName: string) => {
  const response = await fetch(`/api/v1/anexos/${anexoId}/download`, {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });

  const blob = await response.blob();
  const url = window.URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = fileName;
  a.click();
  window.URL.revokeObjectURL(url);
};

// Obter thumbnail
const obterThumbnail = async (anexoId: string, width: number = 200, height: number = 200) => {
  const response = await fetch(
    `/api/v1/anexos/${anexoId}/thumbnail?width=${width}&height=${height}`,
    {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    }
  );

  const data = await response.json();
  return data.thumbnailUrl;
};
```

---

## Exemplos de Erros e Tratamento

### Erro: Arquivo muito grande

```json
{
  "timestamp": "2026-01-14T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Arquivo muito grande. Tamanho máximo: 50MB",
  "path": "/api/v1/anexos"
}
```

### Erro: Acesso negado

```json
{
  "timestamp": "2026-01-14T10:30:00Z",
  "status": 403,
  "error": "Forbidden",
  "message": "Acesso negado ao paciente",
  "path": "/api/v1/anexos"
}
```

### Erro: Recurso não encontrado

```json
{
  "timestamp": "2026-01-14T10:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Paciente não encontrado: 123e4567-e89b-12d3-a456-426614174000",
  "path": "/api/v1/anexos"
}
```

---

## Casos de Uso Completos

### Caso 1: Fluxo Completo - Upload e Visualização

1. **Upload**:
```bash
POST /v1/anexos
```

2. **Listar**:
```bash
GET /v1/anexos?targetType=PACIENTE&targetId=<ID>
```

3. **Visualizar Miniatura** (se for imagem):
```bash
GET /v1/anexos/<ID>/thumbnail
```

4. **Download**:
```bash
GET /v1/anexos/<ID>/download
```

### Caso 2: Gestão Administrativa

1. **Listar todos com filtros**:
```bash
GET /v1/anexos/gestao?status=ATIVO&dataInicio=2026-01-01T00:00:00Z
```

2. **Atualizar metadados**:
```bash
PATCH /v1/anexos/<ID>
```

3. **Excluir se necessário**:
```bash
DELETE /v1/anexos/<ID>
```

---

## Dicas de Uso

1. **Upload**: Use `multipart/form-data` e certifique-se de que o arquivo não excede 50MB
2. **Download**: Para arquivos grandes, prefira URL assinada para melhor performance
3. **Thumbnails**: Sempre verifique `isImagem` antes de tentar obter thumbnail
4. **Filtros**: Use filtros na gestão para reduzir carga no banco de dados
5. **Paginação**: Sempre use paginação em listagens para melhor performance
