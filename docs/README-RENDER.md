# Configuração de Deploy no Render

Este documento descreve como configurar o deploy da aplicação UPSaude no Render.

## Arquivos de Configuração

### render.yaml
Arquivo principal de configuração do Render que define:
- Tipo de serviço (web)
- Runtime (Docker)
- Dockerfile path
- Variáveis de ambiente
- Health check path

### Dockerfile
Arquivo Docker para construir e executar a aplicação:
- Multi-stage build (Maven para compilar, JRE para executar)
- Otimizado para cache de dependências
- Configurado para usar o perfil de produção

### application-prod.properties
Configurações específicas para o ambiente de produção, incluindo:
- Porta do servidor (usa variável de ambiente PORT do Render)
- URL do servidor para Swagger: `https://api.upsaude.wgbsolucoes.com.br`
- Configurações do banco de dados
- Configurações de logging
- Configurações de segurança

### application-dev.properties
Configurações específicas para o ambiente de desenvolvimento, incluindo:
- URL do servidor para Swagger: `https://api-dev.upsaude.wgbsolucoes.com.br`
- Configurações do banco de dados de desenvolvimento

### OpenApiConfig.java
Classe de configuração do OpenAPI/Swagger que define automaticamente o servidor baseado no perfil ativo:
- Ambiente de desenvolvimento: `https://api-dev.upsaude.wgbsolucoes.com.br`
- Ambiente de produção: `https://api.upsaude.wgbsolucoes.com.br`

## Passos para Deploy

### 1. Criar Serviço no Render

1. Acesse o [Render Dashboard](https://dashboard.render.com)
2. Clique em "New +" e selecione "Web Service"
3. Conecte seu repositório Git (GitHub, GitLab, etc.)
4. O Render detectará automaticamente o arquivo `render.yaml` e o `Dockerfile`
5. O Render usará o Dockerfile para construir e executar a aplicação

### 2. Configurar Banco de Dados PostgreSQL

1. No Render Dashboard, crie um novo "PostgreSQL" database
2. Anote as credenciais de conexão:
   - Host (DB_HOST)
   - Port (DB_PORT)
   - Database Name (DB_NAME)
   - Username (DB_USER)
   - Password (DB_PASSWORD)

### 3. Configurar Variáveis de Ambiente

No painel do serviço web, configure as seguintes variáveis de ambiente:

**Obrigatórias:**
- `DB_HOST` - Host do banco de dados PostgreSQL
- `DB_NAME` - Nome do banco de dados
- `DB_USER` - Usuário do banco de dados
- `DB_PASSWORD` - Senha do banco de dados

**Opcionais (com valores padrão):**
- `DB_PORT` - Porta do banco (padrão: 5432)
- `JWT_SECRET` - Chave secreta para JWT (padrão definido no código)
- `JWT_EXPIRATION` - Tempo de expiração do JWT em ms (padrão: 86400000)

### 4. Deploy

1. O Render fará o deploy automaticamente quando você fizer push para o repositório
2. Ou você pode fazer deploy manual clicando em "Manual Deploy"
3. Acompanhe os logs do build e do serviço no painel

## Verificação

Após o deploy, você pode verificar:

**Ambiente de Produção:**
- **Health Check**: `https://api.upsaude.wgbsolucoes.com.br/api/actuator/health`
- **Swagger UI**: `https://api.upsaude.wgbsolucoes.com.br/api/swagger-ui.html`
- **API Docs**: `https://api.upsaude.wgbsolucoes.com.br/api/api-docs`

**Ambiente de Desenvolvimento:**
- **Health Check**: `https://api-dev.upsaude.wgbsolucoes.com.br/api/actuator/health`
- **Swagger UI**: `https://api-dev.upsaude.wgbsolucoes.com.br/api/swagger-ui.html`
- **API Docs**: `https://api-dev.upsaude.wgbsolucoes.com.br/api/api-docs`

## Notas Importantes

- O Render fornece automaticamente a variável de ambiente `PORT` que é usada pela aplicação
- O contexto path da aplicação é `/api`, então todas as rotas devem ser acessadas com esse prefixo
- O health check está configurado para `/api/actuator/health`
- Certifique-se de que o banco de dados está acessível do serviço web (mesma região recomendada)
- As URLs do Swagger são configuradas automaticamente baseadas no perfil ativo:
  - **Dev**: `https://api-dev.upsaude.wgbsolucoes.com.br`
  - **Prod**: `https://api.upsaude.wgbsolucoes.com.br`
- Configure o domínio personalizado no Render para usar essas URLs

## Troubleshooting

### Erro de conexão com banco de dados
- Verifique se todas as variáveis de ambiente do banco estão configuradas
- Verifique se o banco está na mesma região do serviço web
- Verifique se o SSL está habilitado (sslmode=require)

### Erro de porta
- O Render define automaticamente a variável PORT
- A aplicação está configurada para usar `${PORT:8080}`

### Erro de build
- Verifique os logs de build no painel do Render
- Certifique-se de que o Java 17 está disponível
- Verifique se todas as dependências do Maven estão corretas

