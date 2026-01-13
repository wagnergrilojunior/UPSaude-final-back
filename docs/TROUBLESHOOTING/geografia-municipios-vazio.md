# 🔍 Troubleshooting: Endpoints de Municípios Retornando Vazio

## Problema

Os endpoints abaixo estão retornando vazio quando deveriam retornar dados:

- `GET /api/fhir/dados-referencia/geografia/municipios?limit=100`
- `POST /api/fhir/dados-referencia/geografia/sincronizar/municipios`

## Causa Raiz

A tabela `cidades` está vazia (0 registros). Existem **dois serviços diferentes** para geografia:

1. **IBGE Service** (`/v1/integracoes/ibge/*`) - Popula a tabela `cidades` com dados do IBGE
2. **FHIR Geography Sync Service** (`/api/fhir/dados-referencia/geografia/*`) - Atualiza municípios existentes com dados FHIR

O serviço FHIR **não cria novos municípios**, apenas atualiza os existentes com códigos FHIR.

## Solução

### Passo 1: Sincronizar Municípios do IBGE

Primeiro, é necessário popular a tabela `cidades` com dados do IBGE:

```bash
POST http://localhost:8080/v1/integracoes/ibge/sincronizar/municipios
```

Este endpoint irá:
- Buscar todos os municípios do Brasil da API IBGE
- Criar/atualizar registros na tabela `cidades`
- Associar municípios aos estados existentes

**Pré-requisito:** Os estados devem estar sincronizados primeiro:
```bash
POST http://localhost:8080/v1/integracoes/ibge/sincronizar/estados
```

### Passo 2: Sincronizar com FHIR (Opcional)

Após ter municípios no banco, você pode sincronizar com FHIR para adicionar códigos FHIR:

```bash
POST http://localhost:8080/api/fhir/dados-referencia/geografia/sincronizar/municipios
```

Este endpoint irá:
- Buscar códigos FHIR do servidor FHIR do Ministério da Saúde
- Atualizar municípios existentes com `codigo_fhir` e `fhir_code_system`
- Adicionar informações de região de saúde se disponíveis

## Fluxo Completo Recomendado

```bash
# 1. Sincronizar estados do IBGE
POST /v1/integracoes/ibge/sincronizar/estados

# 2. Sincronizar municípios do IBGE
POST /v1/integracoes/ibge/sincronizar/municipios

# 3. (Opcional) Sincronizar estados com FHIR
POST /api/fhir/dados-referencia/geografia/sincronizar/estados

# 4. (Opcional) Sincronizar municípios com FHIR
POST /api/fhir/dados-referencia/geografia/sincronizar/municipios

# 5. Verificar dados
GET /api/fhir/dados-referencia/geografia/municipios?limit=100
```

## Verificação

### Verificar quantidade de municípios no banco:

```sql
SELECT COUNT(*) FROM public.cidades;
```

### Verificar municípios sincronizados com FHIR:

```sql
SELECT COUNT(*) FROM public.cidades WHERE codigo_fhir IS NOT NULL;
```

### Verificar status geral:

```bash
GET http://localhost:8080/api/fhir/dados-referencia/status
```

## Melhorias Implementadas

### 1. Mensagem Informativa no GET

O endpoint `GET /api/fhir/dados-referencia/geografia/municipios` agora retorna uma mensagem informativa quando não há dados:

```json
{
  "message": "Nenhum município encontrado. É necessário sincronizar os municípios do IBGE primeiro.",
  "sugestao": "Execute POST /v1/integracoes/ibge/sincronizar/municipios para popular a base de dados",
  "total": 0
}
```

### 2. Validação no POST de Sincronização FHIR

O endpoint `POST /api/fhir/dados-referencia/geografia/sincronizar/municipios` agora valida se há municípios no banco antes de tentar sincronizar:

```json
{
  "success": false,
  "recurso": "BRDivisaoGeografica-Municipios",
  "erro": "Nenhum município encontrado no banco de dados. É necessário sincronizar os municípios do IBGE primeiro através do endpoint POST /v1/integracoes/ibge/sincronizar/municipios"
}
```

## Endpoints Relacionados

### IBGE (População Inicial)
- `POST /v1/integracoes/ibge/sincronizar` - Sincronização completa
- `POST /v1/integracoes/ibge/sincronizar/estados` - Apenas estados
- `POST /v1/integracoes/ibge/sincronizar/municipios` - Apenas municípios

### FHIR (Enriquecimento)
- `POST /api/fhir/dados-referencia/geografia/sincronizar/estados` - Atualizar estados com FHIR
- `POST /api/fhir/dados-referencia/geografia/sincronizar/municipios` - Atualizar municípios com FHIR
- `GET /api/fhir/dados-referencia/geografia/municipios` - Listar municípios
- `GET /api/fhir/dados-referencia/status` - Status da sincronização

## Notas Importantes

1. **Ordem Importante**: Sempre sincronize IBGE antes de FHIR
2. **Estados Primeiro**: Sincronize estados antes de municípios
3. **Tempo de Execução**: A sincronização de municípios pode levar alguns minutos (há ~5500 municípios no Brasil)
4. **Idempotência**: Os endpoints são idempotentes - podem ser executados múltiplas vezes sem problemas
