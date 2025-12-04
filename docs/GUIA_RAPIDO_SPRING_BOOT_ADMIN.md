# Guia Rápido - Spring Boot Admin

## 🚀 Início Rápido (5 minutos)

### Passo 1: Inicie o Admin Server

```bash
cd /Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code/UPSaude-admin-server
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

✅ **Acesse**: http://localhost:9090/admin  
🔐 **Login**: `admin` / `UPSaudeAdmin2025Prod`

### Passo 2: Inicie o Backend

```bash
cd /Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code/UPSaude-back
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Passo 3: Visualize no Painel

1. Abra http://localhost:9090/admin
2. Veja a aplicação "UPSaude Backend" aparecer
3. Clique na aplicação para ver métricas

🎉 **Pronto!** Você já está monitorando sua aplicação!

## 📊 O Que Você Pode Ver

### 1. Dashboard Principal (Wallboard)

```
┌─────────────────────────────────────┐
│  UPSaude Backend                    │
│  Status: UP ✅                       │
│  Memória: 512MB / 2GB               │
│  Threads: 23                        │
│  Uptime: 2h 15m                     │
└─────────────────────────────────────┘
```

### 2. Métricas da JVM

- **Memória**: Heap usado, Garbage Collection
- **Threads**: Número de threads, estados
- **CPU**: Uso de processamento

### 3. Métricas HTTP

- **Requisições**: Total, por segundo, por endpoint
- **Latência**: Média, P95, P99
- **Erros**: 4xx e 5xx por endpoint

### 4. Banco de Dados (HikariCP)

- **Conexões**: Ativas, idle, disponíveis
- **Performance**: Tempo de espera, timeouts

### 5. Cache (Redis)

- **Status**: Online/Offline
- **Hit Rate**: Taxa de acerto do cache
- **Operações**: Gets, sets por segundo

## 🎯 Casos de Uso Rápidos

### Ver Memória em Tempo Real

1. Clique na aplicação
2. Vá em **Details** > **Metrics**
3. Selecione **JVM Memory**
4. Observe gráficos em tempo real

### Alterar Nível de Log (Sem Reiniciar!)

1. Clique na aplicação
2. Vá em **Loggers**
3. Encontre o logger (ex: `com.upsaude.service`)
4. Clique no nível atual
5. Selecione novo nível (DEBUG, INFO, etc)
6. **Pronto!** Log alterado sem restart

### Ver Requisições HTTP Recentes

1. Clique na aplicação
2. Vá em **HTTP Traces**
3. Veja últimas requisições
4. Analise tempos de resposta

### Fazer Thread Dump

1. Clique na aplicação
2. Vá em **Threads**
3. Veja thread dump completo
4. Identifique threads bloqueadas

### Verificar Health do Sistema

1. Clique na aplicação
2. Vá em **Health**
3. Veja status de:
   - ✅ Database
   - ✅ Redis
   - ✅ Disk Space
   - ✅ Ping

## 🔧 Comandos Úteis

### Iniciar Admin Server

```bash
# Desenvolvimento
cd UPSaude-admin-server
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Produção
java -jar target/upsaude-admin-server-1.0.0.jar --spring.profiles.active=prod
```

### Compilar Admin Server

```bash
cd UPSaude-admin-server
mvn clean package
```

### Testar Endpoints do Actuator

```bash
# Health check
curl http://localhost:8080/api/actuator/health

# Métricas
curl http://localhost:8080/api/actuator/metrics

# Info da aplicação
curl http://localhost:8080/api/actuator/info
```

## 📋 Checklist de Monitoramento

### Diariamente
- [ ] Verificar se todas aplicações estão UP
- [ ] Checar uso de memória (não deve estar perto do limite)
- [ ] Verificar taxa de erro (deve ser < 1%)

### Semanalmente
- [ ] Revisar latência de endpoints (identificar endpoints lentos)
- [ ] Verificar pool de conexões do banco (se está adequado)
- [ ] Revisar logs de erros

### Quando Houver Problemas
- [ ] Verificar Health de todos os componentes
- [ ] Fazer thread dump se app estiver travando
- [ ] Aumentar log level para DEBUG temporariamente
- [ ] Verificar métricas de memória (memory leak?)
- [ ] Verificar conexões do banco (connection pool exhausted?)

## 🆘 Resolução Rápida de Problemas

### Aplicação não aparece no painel

```bash
# 1. Verifique se backend está rodando
curl http://localhost:8080/api/actuator/health

# 2. Verifique logs do backend
grep "spring.boot.admin" logs/application.log

# 3. Confirme configuração
cat src/main/resources/application.properties | grep admin
```

### Métricas não carregam

```bash
# Teste endpoints do Actuator
curl http://localhost:8080/api/actuator/metrics
curl http://localhost:8080/api/actuator/health

# Se retornar 404, verifique se Actuator está habilitado
```

### Admin Server não inicia

```bash
# Verifique se porta 9090 está livre
lsof -i :9090

# Se estiver em uso, mate o processo ou mude a porta
kill -9 <PID>
# ou
mvn spring-boot:run -Dserver.port=9091
```

## 🌟 Dicas Pro

### 1. Atalhos de Teclado

- `Ctrl + K`: Pesquisa rápida
- `Ctrl + ,`: Configurações
- `Esc`: Fechar modal

### 2. Múltiplas Janelas

Abra múltiplas abas para monitorar:
- Tab 1: Wallboard (visão geral)
- Tab 2: Métricas da aplicação específica
- Tab 3: Logs em tempo real

### 3. Filtros

Use filtros no Wallboard para agrupar:
- Por ambiente (dev, prod)
- Por status (UP, DOWN)
- Por tags personalizadas

### 4. Favoritos

Marque aplicações importantes como favoritas para acesso rápido.

### 5. Notificações Desktop

Habilite notificações do navegador para alertas em tempo real.

## 📱 Acesso Remoto

### Via Celular

O painel é responsivo! Acesse de qualquer dispositivo:

1. Abra navegador no celular
2. Acesse: `http://IP_DO_SERVIDOR:9090/admin`
3. Faça login
4. Monitore em movimento

### Via Túnel SSH

Para acessar servidor remoto:

```bash
# Crie túnel SSH
ssh -L 9090:localhost:9090 usuario@servidor-remoto

# Acesse localmente
http://localhost:9090/admin
```

## 🎓 Próximos Passos

1. **Personalize Dashboards**: Adicione métricas personalizadas
2. **Configure Alertas**: Receba emails quando app cair
3. **Integre CI/CD**: Monitore deploys automaticamente
4. **Documente Playbooks**: Crie guias de resposta a incidentes
5. **Treine Equipe**: Garanta que todos saibam usar

## 📚 Links Úteis

- [Documentação Completa](./SPRING_BOOT_ADMIN.md)
- [README do Admin Server](../UPSaude-admin-server/README.md)
- [Spring Boot Admin Docs](https://codecentric.github.io/spring-boot-admin/current/)

---

**Dúvidas?** Consulte a documentação completa ou a equipe de desenvolvimento.

