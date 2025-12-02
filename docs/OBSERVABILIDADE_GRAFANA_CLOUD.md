# Observabilidade com Grafana Cloud

Este documento descreve como configurar o monitoramento profissional do backend UPSaude usando Grafana Cloud (plano free).

## 📋 Índice

1. [Visão Geral](#visão-geral)
2. [Pré-requisitos](#pré-requisitos)
3. [Configuração do Grafana Cloud](#configuração-do-grafana-cloud)
4. [Configuração do Prometheus Remote Write](#configuração-do-prometheus-remote-write)
5. [Importação de Dashboards](#importação-de-dashboards)
6. [Verificação e Testes](#verificação-e-testes)
7. [Métricas Disponíveis](#métricas-disponíveis)
8. [Troubleshooting](#troubleshooting)

## 🎯 Visão Geral

O backend UPSaude está configurado com:

- **Spring Actuator**: Endpoints de monitoramento e saúde
- **Micrometer**: Coleta de métricas
- **Prometheus**: Exportação de métricas no formato Prometheus
- **Métricas Personalizadas**: HTTP, JVM, DataSource (HikariCP), Cache (Redis)

### Endpoints do Actuator Disponíveis

Todos os endpoints estão disponíveis em: `https://seu-dominio.com/api/actuator/`

| Endpoint | Descrição | URL |
|----------|-----------|-----|
| `/health` | Status de saúde da aplicação | `/api/actuator/health` |
| `/info` | Informações da aplicação | `/api/actuator/info` |
| `/metrics` | Lista todas as métricas disponíveis | `/api/actuator/metrics` |
| `/prometheus` | Métricas no formato Prometheus | `/api/actuator/prometheus` |
| `/loggers` | Gerenciamento de loggers | `/api/actuator/loggers` |
| `/threaddump` | Dump de threads da JVM | `/api/actuator/threaddump` |
| `/httpexchanges` | Histórico de requisições HTTP | `/api/actuator/httpexchanges` |

## 📦 Pré-requisitos

1. Conta no Grafana Cloud (gratuita): https://grafana.com/auth/sign-up/create-user
2. Backend UPSaude rodando e acessível publicamente (ex: Render.com)
3. Acesso ao painel do Grafana Cloud

## 🚀 Configuração do Grafana Cloud

### Passo 1: Criar Conta no Grafana Cloud

1. Acesse https://grafana.com/auth/sign-up/create-user
2. Crie uma conta gratuita
3. Faça login no Grafana Cloud

### Passo 2: Criar Instância do Prometheus

1. No painel do Grafana Cloud, vá em **"Connections"** → **"Data Sources"**
2. Clique em **"Add new data source"**
3. Selecione **"Prometheus"**
4. Configure:
   - **Name**: `UPSaude Prometheus`
   - **URL**: Será fornecido pelo Grafana Cloud (ex: `https://prometheus-prod-XX.grafana.net`)
   - Clique em **"Save & Test"**

### Passo 3: Obter Credenciais do Remote Write

1. No painel do Grafana Cloud, vá em **"Connections"** → **"Data Sources"**
2. Selecione sua instância do Prometheus
3. Vá na aba **"Settings"** → **"Remote Write"**
4. Anote as seguintes informações:
   - **Remote Write URL**: `https://prometheus-prod-XX.grafana.net/api/prom/push`
   - **Username**: Seu usuário do Grafana Cloud
   - **Password/API Key**: Gere uma API Key em **"Security"** → **"API Keys"**

## ⚙️ Configuração do Prometheus Remote Write

### Opção 1: Usando Prometheus como Scraper (Recomendado)

O Grafana Cloud pode fazer scraping diretamente do endpoint `/actuator/prometheus` da sua aplicação.

#### Configuração no Grafana Cloud:

1. Acesse **"Connections"** → **"Data Sources"** → **"Prometheus"**
2. Vá em **"Settings"** → **"Scrape Config"**
3. Adicione uma nova configuração de scraping:

```yaml
scrape_configs:
  - job_name: 'upsaude-backend'
    scrape_interval: 30s
    metrics_path: '/api/actuator/prometheus'
    static_configs:
      - targets: ['seu-dominio.com']
        labels:
          application: 'UPSaude'
          environment: 'production'
```

**Substitua `seu-dominio.com` pelo domínio real da sua aplicação no Render.**

#### Configuração via API do Grafana Cloud:

Se preferir configurar via API, você pode usar o endpoint de Remote Write do Prometheus:

```bash
curl -X POST https://prometheus-prod-XX.grafana.net/api/prom/push \
  -u "SEU_USERNAME:SUA_API_KEY" \
  -H "Content-Type: text/plain" \
  --data-binary @<(curl -s https://seu-dominio.com/api/actuator/prometheus)
```

### Opção 2: Usando Prometheus Agent (Avançado)

Se você quiser rodar um Prometheus Agent localmente ou em um servidor:

1. Baixe o Prometheus Agent: https://prometheus.io/download/
2. Configure o `prometheus.yml`:

```yaml
global:
  scrape_interval: 30s
  external_labels:
    cluster: 'upsaude'
    environment: 'production'

scrape_configs:
  - job_name: 'upsaude-backend'
    scrape_interval: 30s
    metrics_path: '/api/actuator/prometheus'
    static_configs:
      - targets: ['seu-dominio.com']
        labels:
          application: 'UPSaude'

remote_write:
  - url: 'https://prometheus-prod-XX.grafana.net/api/prom/push'
    basic_auth:
      username: 'SEU_USERNAME'
      password: 'SUA_API_KEY'
```

## 📊 Importação de Dashboards

### Dashboard 1: JVM (Micrometer)

1. No Grafana Cloud, vá em **"Dashboards"** → **"Import"**
2. Use o ID: `4701`
3. Ou importe diretamente: https://grafana.com/grafana/dashboards/4701
4. Configure:
   - **Prometheus Data Source**: Selecione sua instância do Prometheus
   - **Application**: `UPSaude`
   - Clique em **"Import"**

### Dashboard 2: Spring Boot 2.1 Statistics

1. Vá em **"Dashboards"** → **"Import"**
2. Use o ID: `11378`
3. Ou importe diretamente: https://grafana.com/grafana/dashboards/11378
4. Configure o Data Source e importe

### Dashboard 3: Micrometer Spring Boot 2

1. Vá em **"Dashboards"** → **"Import"**
2. Use o ID: `12900`
3. Ou importe diretamente: https://grafana.com/grafana/dashboards/12900
4. Configure o Data Source e importe

### Dashboard 4: Spring Boot Statistics

1. Vá em **"Dashboards"** → **"Import"**
2. Use o ID: `6756`
3. Ou importe diretamente: https://grafana.com/grafana/dashboards/6756
4. Configure o Data Source e importe

### Dashboard Personalizado UPSaude

Você também pode criar dashboards personalizados usando as métricas específicas do UPSaude:

#### Métricas HTTP Personalizadas:
- `upsaude.http.requests.total` - Total de requisições
- `upsaude.http.requests.failed` - Requisições falhadas
- `upsaude.http.requests.latency` - Latência das requisições
- `upsaude.http.requests.exceptions` - Exceções não tratadas

#### Métricas do DataSource:
- `upsaude.datasource.connections.active` - Conexões ativas
- `upsaude.datasource.available` - Disponibilidade do DataSource
- `hikari.connections.*` - Métricas do HikariCP

#### Métricas do Cache:
- `upsaude.cache.redis.available` - Disponibilidade do Redis
- `cache.gets.*` - Métricas de cache do Spring

#### Métricas da JVM:
- `jvm.memory.*` - Memória da JVM
- `jvm.gc.*` - Garbage Collection
- `jvm.threads.*` - Threads da JVM

## ✅ Verificação e Testes

### 1. Verificar Endpoint de Health

```bash
curl https://seu-dominio.com/api/actuator/health
```

Resposta esperada:
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "PostgreSQL",
        "validationQuery": "isValid()"
      }
    },
    "redis": {
      "status": "UP"
    },
    "ping": {
      "status": "UP"
    }
  }
}
```

### 2. Verificar Endpoint Prometheus

```bash
curl https://seu-dominio.com/api/actuator/prometheus
```

Você deve ver métricas no formato Prometheus, por exemplo:
```
# HELP jvm_memory_used_bytes The amount of used memory
# TYPE jvm_memory_used_bytes gauge
jvm_memory_used_bytes{application="UPSaude",id="heap",} 1.23456789E8

# HELP upsaude_http_requests_total Total de requisições HTTP recebidas
# TYPE upsaude_http_requests_total counter
upsaude_http_requests_total{application="UPSaude",method="GET",uri="/api/v1/pacientes",} 42.0
```

### 3. Verificar Métricas no Grafana

1. No Grafana Cloud, vá em **"Explore"**
2. Selecione seu Data Source do Prometheus
3. Digite uma query, por exemplo: `upsaude_http_requests_total`
4. Você deve ver os dados sendo coletados

### 4. Testar Scraping Manual

```bash
# Teste se o Grafana Cloud consegue acessar seu endpoint
curl -I https://seu-dominio.com/api/actuator/prometheus
```

## 📈 Métricas Disponíveis

### Métricas HTTP Automáticas

O Spring Boot automaticamente coleta:
- `http.server.requests` - Requisições HTTP com tags: `method`, `uri`, `status`
- `http.server.requests.active` - Requisições ativas

### Métricas JVM Automáticas

- `jvm.memory.used` - Memória usada
- `jvm.memory.committed` - Memória comprometida
- `jvm.memory.max` - Memória máxima
- `jvm.gc.pause` - Pausas do Garbage Collection
- `jvm.threads.live` - Threads vivas
- `jvm.threads.daemon` - Threads daemon
- `jvm.classes.loaded` - Classes carregadas

### Métricas do DataSource (HikariCP)

- `hikari.connections.active` - Conexões ativas
- `hikari.connections.idle` - Conexões ociosas
- `hikari.connections.pending` - Conexões pendentes
- `hikari.connections.timeout` - Timeouts de conexão
- `hikari.connections.max` - Máximo de conexões

### Métricas do Cache (Redis)

- `cache.gets` - Gets do cache
- `cache.puts` - Puts do cache
- `cache.evictions` - Evicções do cache
- `cache.size` - Tamanho do cache

### Métricas Personalizadas UPSaude

- `upsaude.http.requests.total` - Total de requisições HTTP
- `upsaude.http.requests.failed` - Requisições falhadas (4xx, 5xx)
- `upsaude.http.requests.latency` - Latência das requisições
- `upsaude.http.requests.exceptions` - Exceções não tratadas
- `upsaude.datasource.connections.active` - Conexões ativas do DataSource
- `upsaude.datasource.available` - Disponibilidade do DataSource
- `upsaude.cache.redis.available` - Disponibilidade do Redis

## 🔧 Troubleshooting

### Problema: Métricas não aparecem no Grafana

**Solução:**
1. Verifique se o endpoint `/actuator/prometheus` está acessível publicamente
2. Verifique se o scraping está configurado corretamente no Grafana Cloud
3. Verifique os logs do Grafana Cloud para erros de scraping
4. Certifique-se de que a URL está correta (incluindo `/api` se usar context-path)

### Problema: Endpoint retorna 404

**Solução:**
1. Verifique se o Spring Actuator está habilitado no `application.properties`
2. Verifique se o endpoint está exposto: `management.endpoints.web.exposure.include=*`
3. Verifique se o SecurityConfig permite acesso ao endpoint
4. Lembre-se que o context-path é `/api`, então a URL completa é `/api/actuator/prometheus`

### Problema: Métricas personalizadas não aparecem

**Solução:**
1. Verifique se a classe `MetricsConfig` está sendo carregada pelo Spring
2. Verifique os logs da aplicação para erros ao registrar métricas
3. Certifique-se de que as métricas estão sendo registradas corretamente
4. Use o endpoint `/actuator/metrics` para listar todas as métricas disponíveis

### Problema: Scraping muito lento

**Solução:**
1. Ajuste o `scrape_interval` no Grafana Cloud (mínimo recomendado: 30s)
2. Verifique a latência da rede entre Grafana Cloud e sua aplicação
3. Considere usar Prometheus Agent para fazer scraping localmente

### Problema: Cardinalidade alta nas métricas

**Solução:**
1. O `HttpMetricsInterceptor` já normaliza URIs substituindo IDs por `{id}`
2. Evite criar tags com valores dinâmicos (como IDs de usuário)
3. Use tags fixas sempre que possível
4. Monitore o número de séries temporais no Grafana Cloud

## 📚 Recursos Adicionais

- [Documentação do Spring Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Documentação do Micrometer](https://micrometer.io/docs)
- [Documentação do Prometheus](https://prometheus.io/docs/)
- [Documentação do Grafana Cloud](https://grafana.com/docs/grafana-cloud/)
- [Dashboards do Grafana](https://grafana.com/grafana/dashboards/)

## 🔐 Segurança

**IMPORTANTE**: Os endpoints do Actuator estão configurados como públicos para facilitar o monitoramento. Em produção, considere:

1. Proteger os endpoints com autenticação básica
2. Usar HTTPS sempre
3. Restringir acesso por IP se possível
4. Considerar usar um gateway/reverse proxy para proteger os endpoints

Para adicionar autenticação básica aos endpoints do Actuator, adicione no `application.properties`:

```properties
management.endpoint.health.show-details=when-authorized
management.security.enabled=true
spring.security.user.name=admin
spring.security.user.password=senha-segura
```

## 📝 Notas Finais

- O plano free do Grafana Cloud tem limites de séries temporais e retenção de dados
- Monitore o uso de métricas para evitar exceder os limites
- Considere usar métricas de amostragem para reduzir cardinalidade
- Revise regularmente as métricas coletadas e remova as não utilizadas

---

**Última atualização**: 2024
**Versão do Spring Boot**: 3.3.4
**Versão do Micrometer**: Incluída no Spring Boot

