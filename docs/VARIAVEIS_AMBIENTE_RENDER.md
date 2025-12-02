# Variáveis de Ambiente no Render - UPSaúde

## 📋 Variáveis Obrigatórias

### Banco de Dados (PostgreSQL/Supabase)
Estas variáveis são **obrigatórias** e devem estar configuradas no Render:

- `DB_HOST` - Host do banco de dados
- `DB_PORT` - Porta do banco (padrão: 5432)
- `DB_NAME` - Nome do banco de dados
- `DB_USER` - Usuário do banco de dados
- `DB_PASSWORD` - Senha do banco de dados

### Supabase Auth (Opcional mas Recomendado)
Estas variáveis são **opcionais** porque há valores padrão no `application-prod.properties`, mas é **recomendado** configurá-las no Render para maior segurança e flexibilidade:

- `SUPABASE_URL` - URL do projeto Supabase (ex: `https://pririvdtylilyrtfbmmv.supabase.co`)
- `SUPABASE_ANON_KEY` - Chave anônima do Supabase (para autenticação)
- `SUPABASE_SERVICE_ROLE_KEY` - Chave de serviço do Supabase (para operações administrativas)

**Nota**: Se não configuradas, a aplicação usará os valores padrão do `application-prod.properties`.

### Redis/Valkey (Opcional)
Estas variáveis são **opcionais** - a aplicação funciona sem cache:

- `REDIS_HOST` - Host do Redis/Valkey
- `REDIS_PORT` - Porta do Redis (padrão: 6379)
- `REDIS_PASSWORD` - Senha do Redis (opcional)
- `REDIS_DATABASE` - Número do banco Redis (padrão: 0)

### Segurança (Opcional)
- `JWT_SECRET` - Chave secreta para JWT (se não configurada, usa valor padrão)
- `JWT_EXPIRATION` - Tempo de expiração do JWT em milissegundos (padrão: 86400000)

---

## 🔧 Como Configurar no Render

1. Acesse o dashboard do Render: https://dashboard.render.com
2. Vá em **Settings** do seu serviço `upsaude-backend`
3. Na seção **Environment Variables**, adicione as variáveis acima
4. Clique em **Save Changes**
5. Faça **redeploy** da aplicação

---

## ⚠️ Problema Atual: Erro 500 no Login

Se você está tendo erro 500 no login e as variáveis do Supabase **não estão configuradas** no Render, a aplicação está usando os valores padrão do `application-prod.properties`.

### Valores Padrão Atuais (application-prod.properties):
```properties
supabase.url=https://pririvdtylilyrtfbmmv.supabase.co
supabase.anon-key=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
supabase.service-role-key=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Possíveis Causas do Erro 500:

1. **Valores padrão incorretos ou expirados**
   - As chaves podem ter expirado
   - A URL pode estar incorreta

2. **Problema de conectividade**
   - A aplicação não consegue alcançar o Supabase
   - Firewall ou rede bloqueando conexões

3. **Problema de deserialização**
   - Resposta do Supabase em formato inesperado
   - Campos faltando na resposta

### Solução Recomendada:

**Configure as variáveis do Supabase no Render** mesmo que já existam valores padrão:

1. Acesse o painel do Supabase: https://app.supabase.com
2. Vá em **Settings** → **API**
3. Copie:
   - **Project URL** → use como `SUPABASE_URL`
   - **anon public** key → use como `SUPABASE_ANON_KEY`
   - **service_role** key → use como `SUPABASE_SERVICE_ROLE_KEY`
4. Configure essas variáveis no Render
5. Faça redeploy

---

## 📝 Checklist de Configuração

- [ ] `DB_HOST` configurado
- [ ] `DB_NAME` configurado
- [ ] `DB_USER` configurado
- [ ] `DB_PASSWORD` configurado
- [ ] `SUPABASE_URL` configurado (recomendado)
- [ ] `SUPABASE_ANON_KEY` configurado (recomendado)
- [ ] `SUPABASE_SERVICE_ROLE_KEY` configurado (recomendado)
- [ ] `REDIS_HOST` configurado (opcional, para cache)
- [ ] `REDIS_PORT` configurado (opcional, para cache)
- [ ] `JWT_SECRET` configurado (opcional, para segurança)

---

## 🔍 Como Verificar se Está Funcionando

Após configurar as variáveis e fazer deploy:

1. Teste o endpoint de login:
   ```bash
   curl -X POST https://api.upsaude.wgbsolucoes.com.br/api/v1/auth/login \
     -H "Content-Type: application/json" \
     -d '{"email":"seu-email@exemplo.com","password":"sua-senha"}'
   ```

2. Verifique os logs no Render:
   - Vá em **Logs** do serviço
   - Procure por mensagens de erro ou sucesso relacionadas ao Supabase

3. Se ainda houver erro 500:
   - Os logs agora mostrarão detalhes completos do erro
   - Procure por mensagens que começam com "Erro" ou "ERROR"
   - Procure por "=== ERRO NÃO TRATADO ===" para ver detalhes completos

---

## 📚 Referências

- [Documentação do Supabase Auth](https://supabase.com/docs/guides/auth)
- [Documentação do Render - Environment Variables](https://render.com/docs/environment-variables)

