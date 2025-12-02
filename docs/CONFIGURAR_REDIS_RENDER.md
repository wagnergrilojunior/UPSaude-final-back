# Passo a Passo: Configurar Redis/Valkey no Render

Este guia detalha exatamente como configurar o Redis/Valkey no Render para que o cache funcione em produção.

> **Nota**: O Render agora usa **Valkey** (fork do Redis) nas novas instâncias Key-Value. Valkey é 100% compatível com Redis e funciona com a mesma configuração. Este guia se aplica tanto para Redis quanto para Valkey.

## 📋 Pré-requisitos

- Conta no Render (https://render.com)
- Acesso ao dashboard do seu projeto
- Aplicação já deployada no Render

## 🚀 Passo a Passo Completo

### Passo 1: Criar Instância Redis no Render

1. **Acesse o Dashboard do Render**
   - Vá para: https://dashboard.render.com
   - Faça login na sua conta

2. **Criar Nova Instância Key-Value (Valkey)**
   - Clique no botão **"New +"** no canto superior direito
   - Selecione **"Key-Value"** no menu (isso criará uma instância Valkey)
   - ⚠️ **Nota**: O Render agora usa Valkey nas novas instâncias. Valkey é compatível com Redis.

3. **Configurar a Instância Key-Value (Valkey)**
   
   Preencha os campos:
   
   - **Name**: `upsaude-redis` (ou outro nome de sua preferência)
   - **Plan**: 
     - **Free**: Para desenvolvimento/testes (limitações de memória)
     - **Starter** ($7/mês): Recomendado para produção com mais recursos
   - **Region**: Escolha a **mesma região** da sua aplicação web (ex: `oregon`)
   - **Maxmemory Policy**: `allkeys-lru` (padrão, está bom)
   
   ⚠️ **IMPORTANTE**: Escolha a mesma região da sua aplicação para reduzir latência!

4. **Criar a Instância**
   - Clique em **"Create Key-Value"** (ou "Create Redis" se ainda aparecer)
   - Aguarde alguns minutos enquanto a instância é provisionada
   - Status mudará de "Creating" para "Available"
   - ⚠️ **Nota**: A instância será Valkey, mas funciona exatamente como Redis

### Passo 2: Obter Credenciais do Redis/Valkey

Após a instância estar disponível:

1. **Acesse a Instância Key-Value**
   - Clique na instância `upsaude-redis` que você acabou de criar

2. **Anotar as Credenciais**
   
   Na página da instância, você verá:
   
   - **Internal Redis URL**: `redis://red-xxxxx:6379`
   - **Redis Host**: `red-xxxxx` (hostname interno) ou `red-xxxxx.render.com` (hostname público)
   - **Redis Port**: `6379`
   - **Redis Password**: (se configurado, aparecerá aqui)
   
   📝 **ANOTE ESTAS INFORMAÇÕES** - você precisará delas no próximo passo!

### Passo 3: Configurar Variáveis de Ambiente na Aplicação Web

1. **Acesse sua Aplicação Web no Render**
   - No dashboard, encontre seu serviço web (ex: `upsaude-backend`)
   - Clique nele para abrir as configurações

2. **Ir para a Seção de Environment Variables**
   - No menu lateral, clique em **"Environment"**
   - Ou role até a seção **"Environment Variables"**

3. **Adicionar Variáveis do Redis**
   
   Clique em **"Add Environment Variable"** e adicione uma por uma:
   
   **Variável 1:**
   - **Key**: `REDIS_HOST`
   - **Value**: O hostname do Redis que você anotou (ex: `red-xxxxx.render.com` ou `red-xxxxx`)
   - ⚠️ **IMPORTANTE**: 
     - Se sua aplicação e Redis estão na mesma rede privada do Render, use o **hostname interno** (sem `.render.com`)
     - Se estão em redes diferentes, use o **hostname público** (com `.render.com`)
   
   **Variável 2:**
   - **Key**: `REDIS_PORT`
   - **Value**: `6379`
   
   **Variável 3:**
   - **Key**: `REDIS_PASSWORD`
   - **Value**: A senha do Redis (se houver, caso contrário deixe vazio)
   - ⚠️ Se o Redis não tiver senha, você pode deixar vazio ou não criar esta variável
   
   **Variável 4:**
   - **Key**: `REDIS_DATABASE`
   - **Value**: `0`
   
4. **Salvar as Variáveis**
   - Clique em **"Save Changes"** após adicionar cada variável
   - Ou adicione todas e salve de uma vez

### Passo 4: Verificar Configuração

1. **Verificar Variáveis Adicionadas**
   
   Na seção Environment Variables, você deve ver:
   ```
   REDIS_HOST = red-xxxxx.render.com
   REDIS_PORT = 6379
   REDIS_PASSWORD = (sua-senha-ou-vazio)
   REDIS_DATABASE = 0
   ```

2. **Verificar Profile Ativo**
   
   Certifique-se de que a variável `SPRING_PROFILES_ACTIVE` está configurada:
   - Para produção: `prod`
   - Para desenvolvimento: `dev`

### Passo 5: Fazer Deploy (se necessário)

1. **Se você já fez commit das mudanças**
   - O Render fará deploy automático
   - Aguarde o deploy completar

2. **Se ainda não fez commit**
   ```bash
   git add render.yaml
   git commit -m "feat: adiciona configuração Redis para cache"
   git push origin dev  # ou sua branch principal
   ```

3. **Monitorar o Deploy**
   - Vá para a aba **"Events"** ou **"Logs"** da sua aplicação
   - Procure por mensagens relacionadas ao Redis
   - Se houver erros de conexão, verifique as variáveis de ambiente

### Passo 6: Verificar se Está Funcionando

1. **Verificar Logs da Aplicação**
   
   Na aba **"Logs"** da sua aplicação no Render, procure por:
   
   ✅ **Sucesso:**
   ```
   Redis conectado com sucesso
   ```
   
   ❌ **Erro (se houver):**
   ```
   Unable to connect to Redis
   Connection refused
   ```

2. **Testar uma Requisição**
   
   Faça uma requisição GET para um endpoint que usa cache:
   ```bash
   curl -X GET "https://sua-api.render.com/api/tenants/{id}" \
     -H "Authorization: Bearer {seu-token}"
   ```
   
   - Primeira requisição: deve criar cache (verifique logs)
   - Segunda requisição: deve usar cache (mais rápida)

3. **Verificar no Redis (Opcional)**
   
   Se você tiver acesso ao Redis CLI:
   ```bash
   redis-cli -h red-xxxxx.render.com -p 6379 -a {senha}
   KEYS upsaude::*
   ```
   
   Deve mostrar chaves criadas pelo cache.

## 🔍 Troubleshooting

### Problema: Erro "Connection refused"

**Causa**: Hostname ou porta incorretos

**Solução**:
1. Verifique se `REDIS_HOST` está correto
2. Verifique se `REDIS_PORT` é `6379`
3. Se estiver usando hostname interno, certifique-se de que aplicação e Redis estão na mesma região

### Problema: Erro "Authentication failed"

**Causa**: Senha incorreta ou não configurada

**Solução**:
1. Verifique se `REDIS_PASSWORD` está correto
2. Se o Redis não tem senha, remova a variável `REDIS_PASSWORD` ou deixe vazia

### Problema: Cache não está funcionando

**Causa**: Variáveis não configuradas ou profile incorreto

**Solução**:
1. Verifique se todas as variáveis estão configuradas
2. Verifique se `SPRING_PROFILES_ACTIVE` está como `prod` ou `dev`
3. Verifique os logs da aplicação para erros

### Problema: Aplicação não inicia

**Causa**: Redis não disponível ou configuração incorreta

**Solução**:
1. Verifique se a instância Redis está "Available" no dashboard
2. Verifique se as variáveis de ambiente estão corretas
3. Verifique os logs de erro da aplicação

## 📊 Verificação Final

Após seguir todos os passos, verifique:

- [ ] Instância Redis criada e disponível no Render
- [ ] Variáveis `REDIS_HOST`, `REDIS_PORT`, `REDIS_PASSWORD`, `REDIS_DATABASE` configuradas na aplicação web
- [ ] Aplicação fazendo deploy sem erros
- [ ] Logs não mostram erros de conexão com Redis
- [ ] Cache funcionando (requisições subsequentes são mais rápidas)

## 💡 Dicas Importantes

1. **Mesma Região**: Sempre coloque Redis e aplicação na mesma região para melhor performance

2. **Hostname Interno vs Público**:
   - **Interno** (`red-xxxxx`): Mais rápido, só funciona se estiverem na mesma rede privada
   - **Público** (`red-xxxxx.render.com`): Funciona sempre, mas pode ser mais lento

3. **Senha do Redis**:
   - Em produção, sempre use senha
   - No plano Free, pode não ter senha por padrão

4. **Monitoramento**:
   - Acompanhe o uso de memória do Redis no dashboard
   - Configure alertas se necessário

5. **Backup**:
   - O Redis no Render tem backup automático em planos pagos
   - Considere fazer backup manual de dados importantes

## 📝 Checklist Rápido

```
[ ] Criar instância Redis no Render
[ ] Anotar credenciais (host, porta, senha)
[ ] Adicionar variáveis REDIS_HOST, REDIS_PORT, REDIS_PASSWORD, REDIS_DATABASE na aplicação web
[ ] Verificar SPRING_PROFILES_ACTIVE está configurado
[ ] Fazer commit e push do render.yaml (se necessário)
[ ] Aguardar deploy automático
[ ] Verificar logs para confirmar conexão com Redis
[ ] Testar requisições para verificar cache funcionando
```

---

**Última atualização**: Dezembro 2024

