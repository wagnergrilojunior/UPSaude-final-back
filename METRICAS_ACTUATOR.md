# Métricas do Actuator - Status e Configuração

## ✅ Métricas HABILITADAS (Funcionando normalmente)

### 1. Métricas HTTP
- `http.server.requests.*` - Todas as métricas de requisições HTTP
- Percentis: 0.5, 0.9, 0.95, 0.99
- SLAs: 100ms, 200ms, 500ms, 1s, 2s, 5s
- Histogramas de percentis habilitados

### 2. Métricas JVM
- `jvm.memory.*` - Memória da JVM
- `jvm.gc.*` - Garbage Collection
- `jvm.threads.*` - Threads
- `jvm.classes.*` - Classes carregadas

### 3. Métricas de Cache (Redis)
- `cache.*` - Métricas do cache Redis

### 4. Métricas Customizadas (MetricsConfig)
- `upsaude.http.requests.total` - Contador de requisições totais
- `upsaude.http.requests.failed` - Contador de requisições falhadas
- `upsaude.http.requests.latency` - Timer de latência
- `upsaude.cache.redis.available` - Disponibilidade do Redis

### 5. Tags Globais
- `application=UPSaude`
- `version=1.0.0`
- `environment={profile}` (local, dev, prod)

### 6. Endpoints do Actuator
- `/actuator/metrics` - Todas as métricas
- `/actuator/prometheus` - Exportação Prometheus
- `/actuator/health` - Health checks
- `/actuator/info` - Informações da aplicação
- `/actuator/loggers` - Gerenciamento de logs
- `/actuator/threaddump` - Dump de threads
- `/actuator/httpexchanges` - Histórico HTTP

## ❌ Métricas DESABILITADAS (Para evitar conexões extras)

### 1. Métricas JDBC do Actuator
- `management.metrics.jdbc.datasource.enabled=false`
- **Motivo**: Criava conexões extras durante inicialização
- **Impacto**: Apenas as métricas JDBC específicas do Actuator foram desabilitadas
- **Alternativa**: O HikariCP ainda expõe métricas via JMX (se habilitado)

### 2. Health Check do Banco (apenas em local)
- `management.health.db.enabled=false` (apenas em `application-local.properties`)
- **Motivo**: Evitar conexões durante inicialização em ambiente local
- **Status em DEV/PROD**: Health check do banco continua habilitado no readiness

## 📊 Métricas Disponíveis para Monitoramento Externo

O sistema de monitoramento externo pode consultar:

1. **Métricas HTTP**: `/actuator/metrics/http.server.requests`
2. **Métricas JVM**: `/actuator/metrics/jvm.*`
3. **Métricas de Cache**: `/actuator/metrics/cache.*`
4. **Métricas Customizadas**: `/actuator/metrics/upsaude.*`
5. **Prometheus**: `/actuator/prometheus` (formato Prometheus)

## ⚙️ Configurações por Ambiente

### Local
- Pool: 2 conexões máximas
- Health DB: Desabilitado
- Métricas JDBC: Desabilitado

### Dev
- Pool: 4 conexões máximas
- Health DB: Habilitado (readiness)
- Métricas JDBC: Desabilitado

### Prod
- Pool: 4 conexões máximas
- Health DB: Habilitado (readiness)
- Métricas JDBC: Desabilitado

## 🔍 Verificação

Para verificar se todas as métricas estão funcionando:

```bash
# Métricas HTTP
curl http://localhost:8080/actuator/metrics/http.server.requests

# Métricas JVM
curl http://localhost:8080/actuator/metrics/jvm.memory.used

# Métricas Customizadas
curl http://localhost:8080/actuator/metrics/upsaude.http.requests.total

# Prometheus
curl http://localhost:8080/actuator/prometheus
```

## ✅ Garantias

- ✅ Todas as métricas HTTP continuam funcionando
- ✅ Todas as métricas JVM continuam funcionando
- ✅ Todas as métricas de cache continuam funcionando
- ✅ Todas as métricas customizadas continuam funcionando
- ✅ Exportação Prometheus continua funcionando
- ✅ Health checks continuam funcionando (apenas ping em local)
- ❌ Apenas métricas JDBC específicas do Actuator foram desabilitadas
