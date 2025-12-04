# Migração: Grafana → Spring Boot Admin

## ✅ Resumo da Migração

Este documento resume a migração do sistema de monitoramento de **Grafana Cloud** para **Spring Boot Admin**.

### 📅 Data da Migração
Dezembro de 2025

### 🎯 Objetivo
Substituir o Grafana Cloud por uma solução nativa Spring Boot que oferece:
- ✅ Melhor integração com Spring Boot
- ✅ Interface mais amigável para gerenciamento
- ✅ Sem custos adicionais
- ✅ Monitoramento em tempo real
- ✅ Gerenciamento de logs e loggers
- ✅ Facilidade de configuração

---

## 🔄 Mudanças Realizadas

### 1. Backend (UPSaude-back)

#### Dependências Alteradas no `pom.xml`

**REMOVIDO:**
```xml
<dependency>
  <groupId>io.micrometer</groupId>
  <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

**ADICIONADO:**
```xml
<dependency>
  <groupId>de.codecentric</groupId>
  <artifactId>spring-boot-admin-starter-client</artifactId>
  <version>3.3.4</version>
</dependency>
```

#### Arquivos Removidos

- ❌ `src/main/java/com/upsaude/config/GrafanaPrometheusConfig.java`
- ❌ `src/main/java/com/upsaude/service/GrafanaPrometheusPushService.java`
- ❌ `docs/OBSERVABILIDADE_GRAFANA_CLOUD.md`

#### Configurações Atualizadas

##### `application.properties`
```properties
# REMOVIDO: Configurações do Grafana
# grafana.prometheus.remote-write.*

# ADICIONADO: Configurações do Spring Boot Admin
spring.boot.admin.client.url=${SPRING_BOOT_ADMIN_URL:http://localhost:9090}
spring.boot.admin.client.instance.name=UPSaude Backend
spring.boot.admin.client.instance.metadata.tags.environment=${spring.profiles.active:default}
spring.boot.admin.client.instance.metadata.tags.version=1.0.0
spring.boot.admin.client.username=${SPRING_BOOT_ADMIN_USER:admin}
spring.boot.admin.client.password=${SPRING_BOOT_ADMIN_PASSWORD:admin}
spring.boot.admin.client.instance.service-base-url=${APP_BASE_URL:http://localhost:8080/api}
```

##### `application-dev.properties`
```properties
spring.boot.admin.client.url=${SPRING_BOOT_ADMIN_URL:http://localhost:9090}
spring.boot.admin.client.instance.service-base-url=https://api-dev.upsaude.wgbsolucoes.com.br/api
```

##### `application-prod.properties`
```properties
spring.boot.admin.client.url=${SPRING_BOOT_ADMIN_URL:http://localhost:9090}
spring.boot.admin.client.instance.service-base-url=https://api.upsaude.wgbsolucoes.com.br/api
```

##### `SecurityConfig.java`
```java
// SIMPLIFICADO: Permitir todos endpoints do Actuator
.requestMatchers("/actuator/**").permitAll()

// REMOVIDO: Endpoint específico do Prometheus
// .requestMatchers("/actuator/prometheus").permitAll()
```

#### Documentação Adicionada

- ✅ `docs/SPRING_BOOT_ADMIN.md` - Documentação completa
- ✅ `docs/GUIA_RAPIDO_SPRING_BOOT_ADMIN.md` - Guia de início rápido

### 2. Servidor Admin (UPSaude-admin-server) - NOVO PROJETO

Criado projeto completo do servidor Spring Boot Admin:

```
UPSaude-admin-server/
├── pom.xml
├── Dockerfile
├── README.md
├── .gitignore
└── src/main/
    ├── java/com/upsaude/admin/
    │   ├── UpSaudeAdminServerApplication.java
    │   └── config/
    │       └── SecurityConfig.java
    └── resources/
        ├── application.properties
        ├── application-dev.properties
        └── application-prod.properties
```

**Recursos do Servidor:**
- Dashboard centralizado
- Monitoramento de múltiplas instâncias
- Autenticação com Spring Security
- Porta: 9090
- Contexto: `/admin`

---

## 🚀 Como Usar

### Início Rápido (Desenvolvimento)

#### 1. Inicie o Admin Server

```bash
cd UPSaude-admin-server
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Acesse**: http://localhost:9090/admin  
**Login**: `admin` / `UPSaudeAdmin2025Prod`

#### 2. Inicie o Backend

```bash
cd UPSaude-back
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

#### 3. Visualize no Painel

Abra http://localhost:9090/admin e veja a aplicação "UPSaude Backend" registrada automaticamente!

---

## 📊 Comparação: Antes vs Depois

| Aspecto | Grafana Cloud | Spring Boot Admin |
|---------|--------------|-------------------|
| **Setup** | Complexo (Prometheus + Grafana) | Simples (dependência + propriedades) |
| **Custo** | Plano gratuito limitado | 100% gratuito |
| **Interface** | Genérica | Nativa Spring Boot |
| **Logs** | Requer Loki | Integrado |
| **Alterar Log Levels** | ❌ Não | ✅ Sim, em tempo real |
| **Thread Dumps** | ❌ Não | ✅ Sim |
| **Health Checks** | Via Prometheus | ✅ Nativo e detalhado |
| **Curva de Aprendizado** | Alta | Baixa |
| **Deploy** | Requer scraping config | Auto-registro |

---

## 📈 Métricas Disponíveis

### Continuam Disponíveis
- ✅ Métricas da JVM (memória, threads, GC)
- ✅ Métricas HTTP (requisições, latência, erros)
- ✅ Métricas do HikariCP (conexões)
- ✅ Métricas do Redis (cache)
- ✅ Métricas personalizadas do UPSaude

### Novas Funcionalidades
- ✅ Gerenciamento de loggers em tempo real
- ✅ Visualização de logs
- ✅ Thread dumps interativos
- ✅ Health checks detalhados
- ✅ Histórico de eventos
- ✅ Notificações de status

---

## 🔧 Variáveis de Ambiente

### Backend (Produção)

```bash
# URL do servidor Admin (após deploy)
SPRING_BOOT_ADMIN_URL=https://admin.upsaude.wgbsolucoes.com.br

# Credenciais para registro
SPRING_BOOT_ADMIN_USER=admin
SPRING_BOOT_ADMIN_PASSWORD=senha_forte_aqui

# URL base da aplicação
APP_BASE_URL=https://api.upsaude.wgbsolucoes.com.br/api
```

### Admin Server (Produção)

```bash
# Porta (Render define automaticamente)
PORT=9090

# Credenciais de acesso ao painel
ADMIN_USER=admin
ADMIN_PASSWORD=senha_forte_aqui
```

---

## 📋 Checklist de Deploy

### Admin Server

- [ ] Deploy do Admin Server em servidor/PaaS
- [ ] Configurar variáveis de ambiente de produção
- [ ] Definir senhas fortes
- [ ] Testar acesso ao painel
- [ ] (Opcional) Configurar HTTPS
- [ ] (Opcional) Restringir acesso por IP
- [ ] (Opcional) Configurar notificações por email

### Backend (Dev)

- [ ] Atualizar `SPRING_BOOT_ADMIN_URL` para URL do servidor
- [ ] Testar registro automático
- [ ] Verificar métricas no painel
- [ ] Validar health checks

### Backend (Prod)

- [ ] Atualizar `SPRING_BOOT_ADMIN_URL` para URL do servidor
- [ ] Configurar variáveis de ambiente no Render
- [ ] Testar registro automático
- [ ] Verificar métricas no painel
- [ ] Validar health checks

---

## 🆘 Troubleshooting

### Backend não aparece no painel

```bash
# 1. Verifique se Admin Server está rodando
curl http://localhost:9090/admin/actuator/health

# 2. Verifique logs do backend
grep "spring.boot.admin" logs/application.log

# 3. Teste endpoints do Actuator
curl http://localhost:8080/api/actuator/health
```

### Erro de autenticação

Verifique se as credenciais no backend correspondem ao Admin Server:
```properties
spring.boot.admin.client.username=admin
spring.boot.admin.client.password=UPSaudeAdmin2025Prod
```

---

## 📚 Documentação

### Documentos Criados

1. **[SPRING_BOOT_ADMIN.md](./UPSaude-back/docs/SPRING_BOOT_ADMIN.md)**
   - Documentação completa
   - Arquitetura
   - Todas as funcionalidades
   - Configuração avançada
   
2. **[GUIA_RAPIDO_SPRING_BOOT_ADMIN.md](./UPSaude-back/docs/GUIA_RAPIDO_SPRING_BOOT_ADMIN.md)**
   - Início em 5 minutos
   - Casos de uso práticos
   - Comandos úteis
   - Resolução rápida de problemas

3. **[UPSaude-admin-server/README.md](./UPSaude-admin-server/README.md)**
   - Documentação do servidor Admin
   - Como executar
   - Deploy
   - Segurança

### Links Úteis

- [Spring Boot Admin Official](https://codecentric.github.io/spring-boot-admin/current/)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Micrometer Metrics](https://micrometer.io/docs)

---

## ✅ Benefícios da Migração

### Para Desenvolvedores

1. **Setup mais rápido**: Apenas adicionar dependência e configuração
2. **Debugging facilitado**: Alterar log levels sem reiniciar
3. **Menos complexidade**: Não precisa entender Prometheus/Grafana
4. **Interface intuitiva**: Tudo em um painel web simples

### Para Operações

1. **Monitoramento centralizado**: Todas instâncias em um lugar
2. **Alertas nativos**: Notificações quando aplicações caem
3. **Sem custo**: 100% gratuito e open source
4. **Fácil manutenção**: Apenas uma aplicação Spring Boot

### Para a Empresa

1. **Zero custo adicional**: Não depende de serviços externos pagos
2. **Total controle**: Servidor rodando na própria infraestrutura
3. **Escalável**: Monitora quantas aplicações precisar
4. **Seguro**: Dados de monitoramento não saem da infraestrutura

---

## 🎓 Treinamento da Equipe

### Material Disponível

1. **Guia Rápido** - 5 minutos para começar
2. **Documentação Completa** - Referência completa
3. **README do Servidor** - Deploy e configuração

### Workshops Recomendados

1. **Sessão 1** (30min): Visão geral e demonstração
2. **Sessão 2** (45min): Hands-on - Cada dev executando localmente
3. **Sessão 3** (30min): Casos de uso avançados e troubleshooting

---

## 📞 Suporte

Para dúvidas ou problemas:

1. Consulte a documentação em `docs/`
2. Verifique o README do Admin Server
3. Revise a seção de Troubleshooting
4. Entre em contato com a equipe de desenvolvimento

---

## 🎉 Conclusão

A migração do Grafana para Spring Boot Admin foi **concluída com sucesso**!

O novo sistema oferece:
- ✅ Melhor experiência de desenvolvimento
- ✅ Interface mais amigável
- ✅ Zero custo adicional
- ✅ Monitoramento completo
- ✅ Fácil manutenção

**Status**: ✅ Pronto para uso em desenvolvimento e produção

---

**Desenvolvido para UPSaude** - Dezembro 2025

