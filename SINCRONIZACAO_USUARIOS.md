# Sincronização Automática de Usuários do Supabase Auth para usuarios_sistema

## Visão Geral

Este documento descreve como garantir que todo usuário criado na tabela `auth.users` do Supabase tenha automaticamente um registro correspondente na tabela `usuarios_sistema`.

## Status Atual

✅ **Todos os usuários existentes foram sincronizados**
- Função `sync_existing_auth_users()` criada e executada
- Função `ensure_all_users_synced()` criada para verificação contínua
- Função `handle_new_auth_user()` criada para novos usuários

## Configuração do Webhook Automático

Para garantir que novos usuários sejam sincronizados automaticamente, você precisa configurar um **Database Webhook** no Supabase Dashboard:

### Passo 1: Acessar Database Webhooks

1. Acesse o [Supabase Dashboard](https://app.supabase.com)
2. Selecione seu projeto
3. Vá em **Database** → **Webhooks**
4. Clique em **Create a new webhook**

### Passo 2: Configurar o Webhook

Configure o webhook com os seguintes parâmetros:

- **Name**: `sync_auth_user_to_usuarios_sistema`
- **Table**: `auth.users`
- **Events**: Selecione `INSERT`
- **Type**: `HTTP Request`
- **HTTP Request**:
  - **Method**: `POST`
  - **URL**: `https://[SEU_PROJETO].supabase.co/rest/v1/rpc/sync_auth_user_from_webhook`
  - **Headers**:
    - `Content-Type: application/json`
    - `apikey: [SUA_SERVICE_ROLE_KEY]`
    - `Authorization: Bearer [SUA_SERVICE_ROLE_KEY]`
  - **Body**: 
    ```json
    {
      "user_data": {
        "id": "{{ $1.id }}",
        "email": "{{ $1.email }}",
        "raw_user_meta_data": {{ $1.raw_user_meta_data }}
      }
    }
    ```

**Nota**: Substitua `[SEU_PROJETO]` pelo ID do seu projeto Supabase e `[SUA_SERVICE_ROLE_KEY]` pela Service Role Key (encontrada em Settings → API).

### Passo 3: Alternativa usando Edge Function (Recomendado)

Uma alternativa mais robusta é criar uma Edge Function que será chamada automaticamente quando um usuário é criado:

1. Crie uma Edge Function no Supabase chamada `sync-user-to-system`
2. Configure um Database Webhook que chama esta função quando `auth.users` recebe um INSERT
3. A função chama `handle_new_auth_user()` no banco de dados

## Funções Disponíveis

### 1. `sync_existing_auth_users()`

Sincroniza todos os usuários existentes que não têm registro em `usuarios_sistema`.

```sql
SELECT public.sync_existing_auth_users() as usuarios_sincronizados;
```

### 2. `ensure_all_users_synced()`

Verifica e sincroniza todos os usuários, retornando um relatório detalhado.

```sql
SELECT * FROM public.ensure_all_users_synced();
```

### 3. `sync_auth_user(p_user_id, p_email, p_metadata)`

Função RPC que pode ser chamada via HTTP/webhook. Parâmetros:
- `p_user_id` (UUID): ID do usuário do Supabase Auth
- `p_email` (TEXT, opcional): Email do usuário
- `p_metadata` (JSONB, opcional): Metadata do usuário

Retorna um JSONB com o resultado da operação.

### 4. `sync_auth_user_from_webhook(user_data)`

Função RPC que recebe um objeto JSONB completo do usuário. Mais fácil de usar com webhooks.

**Exemplo de chamada via API REST:**
```bash
curl -X POST 'https://[SEU_PROJETO].supabase.co/rest/v1/rpc/sync_auth_user_from_webhook' \
  -H "apikey: [SUA_SERVICE_ROLE_KEY]" \
  -H "Authorization: Bearer [SUA_SERVICE_ROLE_KEY]" \
  -H "Content-Type: application/json" \
  -d '{
    "user_data": {
      "id": "uuid-do-usuario",
      "email": "usuario@example.com",
      "raw_user_meta_data": {"nome": "Nome do Usuário"}
    }
  }'
```

## Valores Padrão

Quando um novo usuário é criado automaticamente, os seguintes valores são usados:

- **tenant_id**: Primeiro tenant criado (ordenado por `criado_em ASC`)
- **tipo_usuario**: `12` (OUTRO) - deve ser atualizado manualmente conforme necessário
- **ativo**: `true`
- **nome_exibicao**: `raw_user_meta_data->>'nome'` ou `email` se nome não estiver disponível

## Verificação Manual

Para verificar se todos os usuários estão sincronizados:

```sql
-- Verificar usuários sem registro
SELECT 
    au.id,
    au.email,
    au.created_at
FROM auth.users au
LEFT JOIN public.usuarios_sistema us ON au.id = us.user_id
WHERE us.id IS NULL;

-- Verificar todos os usuários e seus registros
SELECT 
    au.id as user_id,
    au.email,
    CASE WHEN us.id IS NOT NULL THEN 'SIM' ELSE 'NÃO' END as tem_registro,
    us.tipo_usuario,
    us.ativo
FROM auth.users au
LEFT JOIN public.usuarios_sistema us ON au.id = us.user_id
ORDER BY au.created_at DESC;
```

## Manutenção

### Sincronização Periódica

Recomenda-se executar a função `ensure_all_users_synced()` periodicamente (por exemplo, diariamente) para garantir que nenhum usuário fique sem registro. Isso pode ser feito através de:

1. **Cron Job** no Supabase (se disponível)
2. **Edge Function** agendada
3. **Sistema externo** que chama a função via API

### Atualização de Tipo de Usuário

Após a criação automática, o `tipo_usuario` será sempre `OUTRO` (12). É necessário atualizar manualmente através da API ou interface administrativa quando o tipo real do usuário for conhecido.

## Troubleshooting

### Erro: "Nenhum tenant encontrado"

Se você receber este erro, significa que não há nenhum tenant criado no sistema. Você precisa:

1. Criar pelo menos um tenant através da API ou interface administrativa
2. Executar novamente a função de sincronização

### Usuários não estão sendo sincronizados automaticamente

1. Verifique se o webhook está configurado corretamente no Supabase Dashboard
2. Verifique os logs do webhook para ver se há erros
3. Execute manualmente `ensure_all_users_synced()` para sincronizar usuários pendentes

## Notas Importantes

⚠️ **IMPORTANTE**: 
- A sincronização automática cria registros com `tipo_usuario = OUTRO` por padrão
- É necessário atualizar o `tipo_usuario` manualmente quando o tipo real for conhecido
- O `tenant_id` usado será sempre o primeiro tenant criado (ordenado por data de criação)
- Se um usuário precisar estar em múltiplos tenants, será necessário criar registros adicionais manualmente

