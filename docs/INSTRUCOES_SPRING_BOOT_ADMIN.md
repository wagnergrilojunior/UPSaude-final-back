# 🚀 Instruções de Uso - Spring Boot Admin

## ✅ Migração Concluída!

A migração do Grafana para Spring Boot Admin foi concluída com sucesso! 🎉

---

## 🏃 Como Iniciar (Desenvolvimento Local)

### Opção 1: Passo a Passo Completo

#### 1️⃣ Inicie o Admin Server

```bash
# Terminal 1
cd /Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code/UPSaude-admin-server
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Aguarde ver**: `Started UpSaudeAdminServerApplication`

#### 2️⃣ Acesse o Painel Admin

Abra seu navegador em: **http://localhost:9090/admin**

**Faça login com**:
- Usuário: `admin`
- Senha: `UPSaudeAdmin2025Prod`

#### 3️⃣ Inicie o Backend

```bash
# Terminal 2
cd /Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code/UPSaude-back
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Aguarde ver**: `Started UpSaudeApplication`

#### 4️⃣ Visualize no Painel

Volte ao navegador (http://localhost:9090/admin) e você verá:

```
┌─────────────────────────────────────┐
│  Applications                   (1) │
│                                     │
│  ┌───────────────────────────────┐ │
│  │ UPSaude Backend           ✅  │ │
│  │ Status: UP                    │ │
│  │ Version: 1.0.0                │ │
│  │ Environment: dev              │ │
│  └───────────────────────────────┘ │
└─────────────────────────────────────┘
```

✅ **Pronto!** Clique em "UPSaude Backend" para ver todas as métricas!

---

### Opção 2: Scripts Rápidos

#### Para macOS/Linux

Crie um script `start-monitoring.sh`:

```bash
#!/bin/bash

# Cores para output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m'

echo -e "${BLUE}🚀 Iniciando Spring Boot Admin...${NC}"

# Inicia Admin Server
echo -e "${GREEN}1. Iniciando Admin Server na porta 9090...${NC}"
cd /Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code/UPSaude-admin-server
mvn spring-boot:run -Dspring-boot.run.profiles=dev > /tmp/admin-server.log 2>&1 &
ADMIN_PID=$!

# Aguarda servidor iniciar
echo "Aguardando Admin Server iniciar..."
sleep 15

# Inicia Backend
echo -e "${GREEN}2. Iniciando Backend na porta 8080...${NC}"
cd /Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code/UPSaude-back
mvn spring-boot:run -Dspring-boot.run.profiles=dev > /tmp/backend.log 2>&1 &
BACKEND_PID=$!

echo ""
echo -e "${GREEN}✅ Tudo iniciado!${NC}"
echo ""
echo "Admin Server: http://localhost:9090/admin (admin/UPSaudeAdmin2025Prod)"
echo "Backend API: http://localhost:8080/api"
echo ""
echo "PIDs: Admin=$ADMIN_PID Backend=$BACKEND_PID"
echo ""
echo "Para parar: kill $ADMIN_PID $BACKEND_PID"
```

Torne executável e execute:

```bash
chmod +x start-monitoring.sh
./start-monitoring.sh
```

---

## 📊 O Que Você Pode Monitorar

### 1. Métricas da JVM
- ✅ Memória Heap e Non-Heap
- ✅ Garbage Collection
- ✅ Threads (total, ativos, estados)
- ✅ Classes carregadas

### 2. Métricas HTTP
- ✅ Requisições por segundo
- ✅ Latência média, P95, P99
- ✅ Taxa de erro (4xx, 5xx)
- ✅ Métricas por endpoint

### 3. Banco de Dados (HikariCP)
- ✅ Conexões ativas/idle
- ✅ Tempo de espera
- ✅ Pool utilization
- ✅ Connection timeouts

### 4. Cache (Redis)
- ✅ Disponibilidade
- ✅ Hit rate / Miss rate
- ✅ Operações por segundo

### 5. Logs
- ✅ Visualização em tempo real
- ✅ Alterar níveis de log sem reiniciar
- ✅ Filtros e pesquisa

### 6. Análise Avançada
- ✅ Thread dumps
- ✅ Heap dumps
- ✅ Environment variables
- ✅ Configuration properties
- ✅ HTTP traces

---

## 🎯 Casos de Uso Rápidos

### Ver Uso de Memória

1. Clique em "UPSaude Backend"
2. Vá em **Details** > **Metrics**
3. Selecione **JVM Memory**
4. Observe gráficos em tempo real

### Debugar Problema em Produção

1. Vá em **Loggers**
2. Encontre o logger: `com.upsaude.service.SeuService`
3. Altere para `DEBUG`
4. Reproduza o problema
5. Vá em **Logfile** para ver detalhes
6. Volte para `INFO`

### Analisar Performance

1. Vá em **Metrics** > **HTTP**
2. Veja requisições por endpoint
3. Identifique endpoints lentos
4. Analise latência (P95, P99)

### Verificar Health

1. Vá em **Health**
2. Veja status de:
   - Database (PostgreSQL)
   - Redis
   - Disk Space
   - Ping

---

## 🔧 Configuração

### Variáveis de Ambiente (Desenvolvimento)

Nenhuma variável é necessária! As configurações padrão funcionam:

```properties
# Backend se conecta automaticamente em:
SPRING_BOOT_ADMIN_URL=http://localhost:9090
SPRING_BOOT_ADMIN_USER=admin
SPRING_BOOT_ADMIN_PASSWORD=admin
```

### Variáveis de Ambiente (Produção)

Configure estas variáveis no Render ou servidor:

```bash
# Admin Server URL (após deploy)
SPRING_BOOT_ADMIN_URL=https://admin.upsaude.wgbsolucoes.com.br

# Credenciais fortes
SPRING_BOOT_ADMIN_USER=admin
SPRING_BOOT_ADMIN_PASSWORD=SuaSenhaForteAqui123!

# URL base da aplicação
APP_BASE_URL=https://api.upsaude.wgbsolucoes.com.br/api
```

---

## 📚 Documentação

### Guias Disponíveis

1. **[GUIA_RAPIDO_SPRING_BOOT_ADMIN.md](./UPSaude-back/docs/GUIA_RAPIDO_SPRING_BOOT_ADMIN.md)**
   - ⏱️ 5 minutos para começar
   - Comandos úteis
   - Resolução rápida de problemas

2. **[SPRING_BOOT_ADMIN.md](./UPSaude-back/docs/SPRING_BOOT_ADMIN.md)**
   - 📖 Documentação completa
   - Arquitetura detalhada
   - Casos de uso avançados
   - Configuração de produção

3. **[Admin Server README](./UPSaude-admin-server/README.md)**
   - 🖥️ Documentação do servidor
   - Deploy em produção
   - Configuração de segurança

4. **[MIGRACAO_GRAFANA_PARA_SPRING_BOOT_ADMIN.md](./MIGRACAO_GRAFANA_PARA_SPRING_BOOT_ADMIN.md)**
   - 🔄 Detalhes da migração
   - Comparação Grafana vs Spring Boot Admin
   - Lista de mudanças

---

## 🆘 Troubleshooting

### Backend não aparece no painel

```bash
# 1. Verifique se backend está rodando
curl http://localhost:8080/api/actuator/health

# 2. Verifique logs do backend
tail -f /tmp/backend.log | grep admin

# 3. Verifique se Admin Server está rodando
curl http://localhost:9090/admin/actuator/health
```

### Erro de conexão

```bash
# Verifique se portas estão livres
lsof -i :9090  # Admin Server
lsof -i :8080  # Backend

# Se necessário, mate processos antigos
kill -9 <PID>
```

### Métricas não carregam

```bash
# Teste endpoints do Actuator
curl http://localhost:8080/api/actuator/metrics
curl http://localhost:8080/api/actuator/health

# Devem retornar JSON, não 404
```

---

## 🌐 Deploy em Produção

### 1. Deploy do Admin Server

#### No Render:

1. Crie novo Web Service
2. Configure:
   - **Build**: `cd UPSaude-admin-server && mvn clean package -DskipTests`
   - **Start**: `cd UPSaude-admin-server && java -jar target/upsaude-admin-server-1.0.0.jar --spring.profiles.active=prod`
3. Adicione variáveis de ambiente:
   - `ADMIN_USER=admin`
   - `ADMIN_PASSWORD=senha_forte_aqui`

### 2. Atualize Backend

Configure no Render as variáveis:

```
SPRING_BOOT_ADMIN_URL=https://admin.upsaude.wgbsolucoes.com.br
SPRING_BOOT_ADMIN_USER=admin
SPRING_BOOT_ADMIN_PASSWORD=senha_forte_aqui
```

---

## ✅ Checklist de Validação

### Desenvolvimento
- [ ] Admin Server iniciou na porta 9090
- [ ] Painel Admin acessível em http://localhost:9090/admin
- [ ] Login funciona (admin/UPSaudeAdmin2025Prod)
- [ ] Backend aparece na lista de aplicações
- [ ] Status mostra "UP" (verde)
- [ ] Métricas carregam corretamente
- [ ] Health check mostra todos componentes

### Produção
- [ ] Admin Server deployado e acessível
- [ ] Senha forte configurada
- [ ] Backend se registra automaticamente
- [ ] HTTPS configurado
- [ ] Variáveis de ambiente configuradas
- [ ] Métricas de prod e dev separadas por ambiente

---

## 🎓 Próximos Passos

1. ✅ **Execute localmente** - Siga as instruções acima
2. ✅ **Explore o painel** - Clique em todas as abas
3. ✅ **Teste alterar logs** - Mude nível de log em tempo real
4. ✅ **Analise métricas** - Veja gráficos de memória e CPU
5. ✅ **Leia a documentação** - Consulte os guias completos
6. ✅ **Faça deploy** - Coloque em produção
7. ✅ **Configure alertas** - Email quando app cair

---

## 💡 Dicas Pro

### 1. Marque como Favorito
Adicione http://localhost:9090/admin aos seus favoritos

### 2. Use Múltiplas Abas
- Tab 1: Wallboard (visão geral)
- Tab 2: Métricas da aplicação
- Tab 3: Logs

### 3. Atalhos de Teclado
- `Ctrl + K`: Busca rápida
- `Esc`: Fechar modal

### 4. Monitore Continuamente
Deixe o painel aberto em uma tela secundária

### 5. Notificações Desktop
Habilite notificações do navegador para alertas

---

## 📞 Suporte

**Dúvidas?**
1. Consulte a [documentação completa](./UPSaude-back/docs/SPRING_BOOT_ADMIN.md)
2. Veja o [guia rápido](./UPSaude-back/docs/GUIA_RAPIDO_SPRING_BOOT_ADMIN.md)
3. Entre em contato com a equipe de desenvolvimento

---

## 🎉 Benefícios

### vs Grafana Cloud

✅ **Mais fácil** - Setup em 5 minutos  
✅ **Mais barato** - 100% gratuito  
✅ **Mais integrado** - Nativo Spring Boot  
✅ **Mais completo** - Gerenciamento de logs  
✅ **Mais rápido** - Sem configuração de scraping  

---

**Desenvolvido para UPSaude** - Dezembro 2025

🚀 **Comece agora e monitore sua aplicação em tempo real!**

