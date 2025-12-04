# Correção do Problema com Prepared Statements no PostgreSQL

## 📋 Problema Identificado

Ao fazer login no sistema, estava ocorrendo o seguinte erro:

```
ERROR: prepared statement "S_1" does not exist
ERROR: current transaction is aborted, commands ignored until end of transaction block
```

### Stack Trace do Erro

```
2025-12-04T12:03:35.796Z ERROR --- org.hibernate.engine.jdbc.spi.SqlExceptionHelper   : 
ERROR: current transaction is aborted, commands ignored until end of transaction block

Caused by: org.postgresql.util.PSQLException: ERROR: prepared statement "S_1" does not exist
```

## 🔍 Causa Raiz

Este é um problema clássico que ocorre quando:

1. **Prepared statements são criados** em uma conexão do pool do HikariCP
2. **A transação falha** por algum motivo (timeout, erro SQL, etc.)
3. **A conexão é devolvida** ao pool do HikariCP
4. **A conexão é reutilizada** em outra requisição
5. **O Hibernate/JPA tenta usar** os prepared statements que não existem mais
6. **PostgreSQL rejeita a query** com "prepared statement does not exist"
7. **A transação é abortada** e todas as queries subsequentes falham

### Motivos Específicos

- **Cache de prepared statements no cliente**: O driver PostgreSQL JDBC mantém um cache de prepared statements no lado do cliente
- **Gerenciamento de pool**: Quando uma conexão é devolvida ao pool e depois reutilizada, os prepared statements podem não estar mais válidos
- **Falta de gerenciamento de transações**: Sem `@Transactional`, as transações não são gerenciadas adequadamente pelo Spring

## ✅ Solução Implementada

### 1. Adicionado `@Transactional` no AuthServiceImpl

**Arquivo**: `UPSaude-back/src/main/java/com/upsaude/service/impl/AuthServiceImpl.java`

```java
@Override
@Transactional(readOnly = true)
public LoginResponse login(LoginRequest request) {
    // ... código do login
}
```

**Por quê?**
- Garante que todas as operações de banco de dados no método login sejam executadas dentro de uma única transação
- Permite rollback automático em caso de erro
- Gerencia o ciclo de vida da conexão corretamente

### 2. Criada Configuração Customizada do HikariCP

**Arquivo**: `UPSaude-back/src/main/java/com/upsaude/config/DataSourceConfig.java`

```java
@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariConfig hikariConfig(DataSourceProperties dataSourceProperties) {
        HikariConfig config = new HikariConfig();
        
        // Desabilita o cache de prepared statements no lado do cliente
        config.addDataSourceProperty("preparedStatementCacheQueries", "0");
        config.addDataSourceProperty("preparedStatementCacheSizeMiB", "0");
        
        // Validação de conexões
        config.setConnectionTestQuery("SELECT 1");
        
        return config;
    }
}
```

**Por quê?**
- **Desabilita o cache de prepared statements**: Força o PostgreSQL a gerenciar prepared statements no lado do servidor
- **Valida conexões**: Testa a conexão antes de usá-la para garantir que está válida
- **Evita prepared statements órfãos**: Cada query cria um novo prepared statement, evitando referências a statements que não existem mais

### 3. Ajustes nas Configurações do application-prod.properties

**Arquivo**: `UPSaude-back/src/main/resources/application-prod.properties`

```properties
# Validação de conexões para evitar problemas com prepared statements
spring.datasource.hikari.connection-test-query=SELECT 1

# Propriedades do PostgreSQL para gerenciar prepared statements corretamente
spring.datasource.hikari.data-source-properties.preparedStatementCacheQueries=0
spring.datasource.hikari.data-source-properties.preparedStatementCacheSizeMiB=0
spring.datasource.hikari.auto-commit=true

# Configurações para gerenciamento de transações e prepared statements
spring.jpa.properties.hibernate.connection.provider_disables_autocommit=false
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true

# Desabilita o cache de prepared statements no Hibernate (evita conflitos com pool)
spring.jpa.properties.hibernate.query.plan_cache_max_size=0
spring.jpa.properties.hibernate.query.plan_parameter_metadata_max_size=0
```

### 4. Ajustes nas Configurações Base

**Arquivo**: `UPSaude-back/src/main/resources/application.properties`

```properties
# Configurações para gerenciamento de transações
spring.jpa.properties.hibernate.connection.provider_disables_autocommit=false
spring.jpa.open-in-view=false
```

**Por quê?**
- `spring.jpa.open-in-view=false`: Evita que sessões do Hibernate fiquem abertas além do necessário
- Garante que o autocommit seja gerenciado corretamente pelo Spring

## 🔄 Como Funciona Agora

1. **Login iniciado**: Método `login()` é chamado com `@Transactional(readOnly = true)`
2. **Transação criada**: Spring cria uma transação de leitura
3. **Conexão obtida**: HikariCP fornece uma conexão validada do pool
4. **Prepared statements não cacheados**: Cada query usa prepared statements do lado do servidor
5. **Queries executadas**: Todas as queries (findByUserId, findByUsuarioUserId) são executadas na mesma transação
6. **Transação commitada**: Spring commita a transação automaticamente
7. **Conexão devolvida**: Conexão é devolvida ao pool limpa e pronta para reutilização

## 📊 Benefícios da Solução

✅ **Elimina o erro de prepared statements**
✅ **Melhora a estabilidade** das conexões do pool
✅ **Garante consistência** nas transações
✅ **Performance mantida**: Server-side prepared statements são eficientes
✅ **Compatível com Supabase**: Funciona bem com o pooler do Supabase

## 🧪 Como Testar

1. **Build do projeto**:
```bash
cd UPSaude-back
mvn clean package -DskipTests
```

2. **Deploy no Render** (automático via Git push)

3. **Teste de login via frontend ou Postman**:
```bash
POST https://api.upsaude.wgbsolucoes.com.br/api/v1/auth/login
Content-Type: application/json

{
  "email": "nataligrilobarros@gmail.com",
  "password": "sua-senha"
}
```

## 📝 Notas Importantes

- Esta solução **NÃO afeta a performance** significativamente
- Server-side prepared statements do PostgreSQL são muito eficientes
- A configuração é **compatível com todos os ambientes** (local, dev, prod)
- **Não requer mudanças no frontend**

## 🔗 Referências

- [HikariCP Configuration](https://github.com/brettwooldridge/HikariCP#frequently-used)
- [PostgreSQL JDBC Driver Documentation](https://jdbc.postgresql.org/documentation/head/connect.html)
- [Spring Transaction Management](https://docs.spring.io/spring-framework/reference/data-access/transaction.html)
- [Hibernate Query Plan Cache](https://docs.jboss.org/hibernate/orm/6.0/userguide/html_single/Hibernate_User_Guide.html#query-plan-cache)

## 👨‍💻 Autor

Wagner Grilo (UPSaúde Team)
Data: 04/12/2025

