# Troubleshooting: Erro de Timeout no Render

## 🔴 Problema: Timeout no Health Check

**Erro observado:**
```
Timed out after waiting for internal health check to return a successful response code
```

## 🔍 Causa

A aplicação está tentando conectar ao Redis durante a inicialização e isso está causando timeout no health check do Render.

## ✅ Solução Aplicada

Foram feitas as seguintes melhorias:

### 1. Timeout Reduzido na Conexão Redis

- Timeout de conexão reduzido para **2 segundos**
- Não bloqueia a inicialização se Redis não estiver disponível
- Reconexão automática habilitada

### 2. Validação de Conexão Desabilitada no Startup

- `validateConnection(false)` - Não valida conexão durante startup
- Conexão é estabelecida apenas quando necessário (lazy)

### 3. Health Check do Redis Configurado

- Timeout do health check: **2 segundos**
- Não bloqueia o health check geral da aplicação

## 📝 Configurações Aplicadas

### RedisConfig.java
- Timeout de conexão: 2 segundos
- Validação de conexão desabilitada no startup
- Reconexão automática habilitada

### application-prod.properties
```properties
spring.redis.lettuce.shutdown-timeout=1000ms
management.health.redis.timeout=2000ms
```

## 🚀 Próximos Passos

### 1. Fazer Commit e Push das Correções

```bash
git add .
git commit -m "fix: corrige timeout do Redis no startup do Render"
git push origin feat/redis-cache-implementation
```

### 2. Aguardar Deploy no Render

- O Render fará deploy automático
- Aguarde o deploy completar

### 3. Verificar Logs

Após o deploy, verifique os logs:

**Sucesso:**
- Aplicação inicia sem timeout
- Health check retorna 200 OK
- Logs não mostram erros de conexão Redis

**Se ainda houver problemas:**
- Verifique se as variáveis de ambiente estão configuradas
- Verifique se o Redis está "Available" no dashboard
- Verifique os logs completos para erros específicos

## 🔧 Verificação das Variáveis de Ambiente

Certifique-se de que estas variáveis estão configuradas no Render:

```
REDIS_HOST = red-d4nggbemcj7s73euiah0
REDIS_PORT = 6379
REDIS_DATABASE = 0
REDIS_PASSWORD = (vazio ou não existe)
```

## 📊 Monitoramento

Após o deploy bem-sucedido:

1. **Verificar Health Check:**
   ```bash
   curl https://api.upsaude.wgbsolucoes.com.br/api/actuator/health
   ```
   Deve retornar `{"status":"UP"}`

2. **Verificar Logs do Redis:**
   - Procure por mensagens de conexão
   - Não deve haver erros de timeout

3. **Testar Cache:**
   - Faça uma requisição GET
   - Verifique se o cache está funcionando

## ⚠️ Se o Problema Persistir

### Opção 1: Desabilitar Redis Temporariamente

Se ainda houver problemas, você pode desabilitar o Redis temporariamente:

1. Remova as variáveis de ambiente `REDIS_HOST`, `REDIS_PORT`, etc.
2. A aplicação funcionará sem cache (mais lenta, mas funcional)
3. Reative depois quando o problema for resolvido

### Opção 2: Verificar Região do Redis

Certifique-se de que:
- Redis está na mesma região da aplicação (`oregon`)
- Hostname está correto (interno vs público)

### Opção 3: Verificar Status do Redis

No dashboard do Render:
- Redis deve estar com status "Available"
- Não deve estar "Creating" ou "Failed"

## 📞 Suporte

Se o problema persistir após essas correções:
1. Verifique os logs completos no Render
2. Verifique o status do Redis no dashboard
3. Consulte a documentação do Render sobre Redis

---

**Última atualização**: Dezembro 2024

