# Monitoramento com Spring Boot Admin

Este documento descreve como usar o Spring Boot Admin para monitorar a aplicação UPSaude Backend.

## 📊 Visão Geral

O Spring Boot Admin é uma aplicação web para gerenciar e monitorar aplicações Spring Boot. Ele fornece uma interface amigável para visualizar métricas, logs e informações de saúde das aplicações.

### Por Que Spring Boot Admin?

- ✅ **Interface gráfica intuitiva** - Dashboard visual para todas as métricas
- ✅ **Monitoramento em tempo real** - Métricas atualizadas automaticamente
- ✅ **Sem custo adicional** - Totalmente gratuito e open source
- ✅ **Fácil integração** - Apenas adicionar dependência e configuração
- ✅ **Gerenciamento de logs** - Visualize e altere níveis de log em tempo real
- ✅ **Notificações** - Alertas quando aplicações ficam offline
- ✅ **Múltiplos ambientes** - Monitore dev e prod no mesmo painel

## 🏗️ Arquitetura

```
┌─────────────────────────┐
│  UPSaude Admin Server   │  <- Servidor centralizado (porta 9090)
│   (Spring Boot Admin)   │     Exibe dashboards e métricas
└───────────┬─────────────┘
            │
            │ Registro via HTTP
            │
    ┌───────┴────────┬───────────────┐
    │                │               │
┌───▼────┐    ┌─────▼─────┐  ┌─────▼─────┐
│  Dev   │    │   Prod    │  │  Local    │
│ Client │    │  Client   │  │  Client   │
└────────┘    └───────────┘  └───────────┘
             
Aplicações clientes se registram automaticamente no servidor
e expõem endpoints do Actuator para coleta de métricas.
```

## 🚀 Como Funciona

### 1. Servidor Admin (UPSaude-admin-server)

O servidor é uma aplicação Spring Boot separada que:
- Roda na porta **9090**
- Recebe registros das aplicações clientes
- Coleta métricas via endpoints do Actuator
- Exibe dashboards e visualizações

**Localização**: `/code/UPSaude-admin-server/`

### 2. Cliente Admin (UPSaude-back)

A aplicação backend funciona como cliente e:
- Se registra automaticamente no servidor Admin
- Expõe endpoints do Actuator
- Envia métricas em tempo real
- Permite gerenciamento remoto

## 📦 Instalação e Configuração

### Servidor Admin

O servidor já está configurado no projeto `UPSaude-admin-server`. Para executá-lo:

```bash
# Entre no diretório do servidor
cd /Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code/UPSaude-admin-server

# Compile o projeto
mvn clean package

# Execute em modo desenvolvimento
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Acesse**: http://localhost:9090/admin

**Credenciais padrão**:
- Usuário: `admin`
- Senha: `UPSaudeAdmin2025Prod`

### Cliente (Backend UPSaude)

O backend já está configurado com as seguintes propriedades:

```properties
# application.properties
spring.boot.admin.client.url=${SPRING_BOOT_ADMIN_URL:http://localhost:9090}
spring.boot.admin.client.instance.name=UPSaude Backend
spring.boot.admin.client.username=${SPRING_BOOT_ADMIN_USER:admin}
spring.boot.admin.client.password=${SPRING_BOOT_ADMIN_PASSWORD:admin}
```

## 🎯 Métricas Disponíveis

### 1. Métricas da JVM

- **Memória**:
  - Heap usado/disponível
  - Non-heap (Metaspace, Code Cache)
  - Garbage Collection (frequência e duração)
  
- **Threads**:
  - Threads ativos
  - Threads daemon
  - Peak threads
  - Estados (runnable, waiting, blocked)

- **Classes**:
  - Classes carregadas
  - Classes descarregadas

### 2. Métricas HTTP

- **Requisições**:
  - Total de requisições
  - Taxa de requisições por segundo
  - Latência média/p95/p99
  - Erros por status code (4xx, 5xx)
  
- **Por Endpoint**:
  - Tempo médio de resposta
  - Taxa de sucesso/erro
  - Distribuição de latência

### 3. Métricas de Banco de Dados (HikariCP)

- **Pool de Conexões**:
  - Conexões ativas
  - Conexões idle
  - Conexões aguardando
  - Tempo de espera por conexão
  - Taxa de timeout

### 4. Métricas de Cache (Redis)

- **Redis**:
  - Disponibilidade
  - Hit rate / Miss rate
  - Latência de operações
  - Tamanho do cache

### 5. Métricas Personalizadas UPSaude

```java
// Exemplos de métricas personalizadas já configuradas:
upsaude.http.requests.total      // Total de requisições
upsaude.http.requests.failed     // Requisições falhadas
upsaude.http.requests.latency    // Latência das requisições
upsaude.datasource.connections.active  // Conexões ativas
upsaude.datasource.available     // Disponibilidade do banco
upsaude.cache.redis.available    // Disponibilidade do Redis
```

## 🖥️ Usando o Painel Admin

### Dashboard Principal (Wallboard)

Visão geral de todas as aplicações registradas:
- Status (UP/DOWN)
- Versão da aplicação
- Uptime
- Memória utilizada
- Threads
- Ambiente (dev/prod)

### Visualizações por Aplicação

#### 1. Details

Informações gerais da instância:
- Nome da aplicação
- URL base
- Versão Java
- Uptime
- Process ID

#### 2. Health

Status de saúde detalhado:
- ✅ Database (PostgreSQL/Supabase)
- ✅ Redis (Cache)
- ✅ Disk Space
- ✅ Ping

#### 3. Metrics

Gráficos em tempo real:
- **JVM Memory**: Heap, Non-heap, usado vs disponível
- **JVM Threads**: Total de threads, estados
- **HTTP**: Requisições por segundo, latência
- **Tomcat**: Sessions, threads do Tomcat
- **HikariCP**: Conexões ativas, idle, tempo de espera
- **Cache**: Hit rate, operações

#### 4. Environment

Visualize todas as propriedades e variáveis:
- System Properties
- Environment Variables
- Application Properties
- Spring Profiles ativos

#### 5. Loggers

Gerencie níveis de log em tempo real:
- Visualize todos os loggers
- Altere nível de log sem reiniciar
- Útil para debugging em produção

#### 6. JVM

Detalhes da JVM:
- Informações do sistema
- Argumentos da JVM
- Versão do Java
- System Properties

#### 7. Threads

Análise de threads:
- Thread dump completo
- Estado de cada thread
- Stack traces
- Detecção de deadlocks

#### 8. HTTP Traces

Histórico de requisições HTTP:
- Últimas requisições recebidas
- Status codes
- Tempo de resposta
- Headers

#### 9. Caches

Estatísticas de cache:
- Cache hits/misses
- Taxa de acerto
- Tamanho do cache
- Operações por cache

#### 10. Scheduled Tasks

Tarefas agendadas:
- Lista de tasks @Scheduled
- Próxima execução
- Última execução

## 🔧 Configuração de Ambientes

### Desenvolvimento (Local)

```properties
# application-dev.properties
spring.boot.admin.client.url=http://localhost:9090
spring.boot.admin.client.instance.service-base-url=https://api-dev.upsaude.wgbsolucoes.com.br/api
```

### Produção

```properties
# application-prod.properties
spring.boot.admin.client.url=${SPRING_BOOT_ADMIN_URL:http://localhost:9090}
spring.boot.admin.client.instance.service-base-url=https://api.upsaude.wgbsolucoes.com.br/api
```

**Variáveis de Ambiente para Produção**:

```bash
SPRING_BOOT_ADMIN_URL=https://admin.upsaude.wgbsolucoes.com.br
SPRING_BOOT_ADMIN_USER=admin
SPRING_BOOT_ADMIN_PASSWORD=senha_forte_aqui
```

## 📈 Casos de Uso

### 1. Monitorar Memória em Produção

1. Acesse o painel Admin
2. Selecione a aplicação "UPSaude Backend - prod"
3. Vá em **Metrics** > **JVM Memory**
4. Observe gráficos de Heap e Non-Heap
5. Identifique memory leaks ou necessidade de ajuste de heap

### 2. Debugar Problemas em Produção

1. Acesse **Loggers**
2. Encontre o logger específico (ex: `com.upsaude.service.PacienteService`)
3. Altere o nível para `DEBUG` temporariamente
4. Reproduza o problema
5. Acesse **Logfile** para ver logs detalhados
6. Restaure nível para `INFO` após debug

### 3. Analisar Performance de Endpoints

1. Acesse **Metrics** > **HTTP**
2. Visualize requisições por endpoint
3. Identifique endpoints lentos (p95, p99)
4. Verifique taxa de erro
5. Otimize endpoints problemáticos

### 4. Monitorar Conexões de Banco

1. Acesse **Metrics** > **Data Source**
2. Observe:
   - Conexões ativas vs máximo
   - Tempo de espera por conexão
   - Connection pool exhaustion
3. Ajuste configurações do HikariCP se necessário

### 5. Verificar Cache Redis

1. Acesse **Health** > **Redis**
2. Verifique disponibilidade
3. Acesse **Caches** para estatísticas
4. Analise hit rate
5. Otimize estratégia de cache se necessário

## 🔔 Notificações e Alertas

O Spring Boot Admin Server pode enviar notificações quando:
- Aplicação fica offline (DOWN)
- Aplicação volta online (UP)
- Status de health muda

### Configurar Notificações por Email

Adicione no `application.properties` do Admin Server:

```properties
spring.boot.admin.notify.mail.enabled=true
spring.boot.admin.notify.mail.from=admin@upsaude.com.br
spring.boot.admin.notify.mail.to=equipe@upsaude.com.br

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-app
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## 🔒 Segurança

### Protegendo o Painel Admin

O painel Admin está protegido com Spring Security:

```java
// Usuários em memória (desenvolvimento)
admin / UPSaudeAdmin2025Prod   - Acesso total
viewer / viewer123 - Apenas visualização
```

### Produção

Em produção, **sempre**:

1. **Defina senha forte** via variável de ambiente
2. **Use HTTPS** para proteger comunicação
3. **Restrinja acesso por IP** no firewall
4. **Considere OAuth2/LDAP** para autenticação corporativa

### Protegendo Endpoints do Actuator

Em produção, considere adicionar autenticação aos endpoints do Actuator:

```java
// SecurityConfig.java
.requestMatchers("/actuator/**").hasRole("ADMIN")
```

## 🌐 Deploy

### Deploy do Admin Server

#### Render (Recomendado)

1. Crie novo Web Service no Render
2. Configure:
   - **Build**: `mvn clean package -DskipTests`
   - **Start**: `java -jar target/upsaude-admin-server-1.0.0.jar --spring.profiles.active=prod`
3. Adicione variáveis de ambiente

#### Docker

```bash
cd UPSaude-admin-server
docker build -t upsaude-admin .
docker run -p 9090:9090 \
  -e ADMIN_USER=admin \
  -e ADMIN_PASSWORD=senha_forte \
  upsaude-admin
```

### Conectando Clientes ao Admin Server Remoto

Após deploy do Admin Server, atualize o backend:

```bash
# Variável de ambiente no Render/servidor
SPRING_BOOT_ADMIN_URL=https://admin.upsaude.wgbsolucoes.com.br
SPRING_BOOT_ADMIN_USER=admin
SPRING_BOOT_ADMIN_PASSWORD=senha_forte
```

## 🆘 Troubleshooting

### Aplicação não aparece no painel

**Problema**: Backend não aparece na lista de aplicações.

**Soluções**:
1. Verifique se o backend está rodando
2. Verifique logs do backend: `spring.boot.admin.client`
3. Confirme URL do Admin Server: `spring.boot.admin.client.url`
4. Verifique credenciais de autenticação
5. Confirme que endpoints do Actuator estão acessíveis

### Erro 401 ao registrar

**Problema**: Backend não consegue se registrar (401 Unauthorized).

**Soluções**:
1. Verifique `spring.boot.admin.client.username/password`
2. Confirme que usuário existe no Admin Server
3. Verifique logs do Admin Server

### Métricas não aparecem

**Problema**: Aplicação aparece, mas métricas não carregam.

**Soluções**:
1. Verifique `management.endpoints.web.exposure.include=*`
2. Confirme que Actuator está habilitado
3. Teste endpoints manualmente: `/actuator/health`, `/actuator/metrics`
4. Verifique firewall/segurança não está bloqueando

### Admin Server não inicia

**Problema**: Erro ao iniciar Admin Server.

**Soluções**:
1. Verifique porta 9090 não está em uso
2. Confirme Java 17 está instalado
3. Execute `mvn clean install` para baixar dependências
4. Verifique logs de erro

## 📚 Recursos Adicionais

- [Spring Boot Admin Docs](https://codecentric.github.io/spring-boot-admin/current/)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Micrometer Metrics](https://micrometer.io/docs)

## 🎓 Comparação: Grafana vs Spring Boot Admin

| Recurso | Spring Boot Admin | Grafana |
|---------|------------------|---------|
| **Facilidade de Setup** | ✅ Muito fácil (apenas dependência) | ❌ Requer Prometheus + configuração |
| **Interface para Spring Boot** | ✅ Nativa e otimizada | ⚠️ Genérica |
| **Gerenciamento de Logs** | ✅ Integrado e fácil | ❌ Requer Loki |
| **Alteração de Log Levels** | ✅ Em tempo real | ❌ Não disponível |
| **Thread Dumps** | ✅ Integrado | ❌ Não disponível |
| **Health Checks Detalhados** | ✅ Nativo | ⚠️ Requer configuração |
| **Custo** | ✅ 100% gratuito | ⚠️ Grafana Cloud tem limites |
| **Aprendizado** | ✅ Fácil | ⚠️ Curva de aprendizado |
| **Dashboards Customizados** | ⚠️ Limitado | ✅ Muito flexível |
| **Alertas Avançados** | ⚠️ Básico | ✅ Muito completo |
| **Múltiplas Fontes de Dados** | ❌ Apenas Spring Boot | ✅ Qualquer fonte |

**Conclusão**: Spring Boot Admin é ideal para monitoramento direto de aplicações Spring Boot, oferecendo integração nativa e facilidade de uso sem custos adicionais.

## ✅ Próximos Passos

1. ✅ Execute o Admin Server localmente
2. ✅ Acesse o painel em http://localhost:9090/admin
3. ✅ Execute o backend e veja aparecer no painel
4. ✅ Explore as métricas e funcionalidades
5. ✅ Configure notificações por email (opcional)
6. ✅ Faça deploy do Admin Server em produção
7. ✅ Configure variáveis de ambiente nos clientes

---

**Desenvolvido para UPSaude** - Sistema de Gestão de Saúde Pública e Privada

