# Integração IBGE - Documentação Técnica

## Arquitetura

### Componentes

```
┌─────────────────────────────────────────────────────────┐
│                    REST Controller                        │
│         IbgeSincronizacaoController                       │
└────────────────────┬────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────┐
│                    Service Layer                         │
│              IbgeService / IbgeServiceImpl               │
│  - Lógica de negócio                                     │
│  - UPSERT logic                                          │
│  - Orquestração de sincronização                         │
└────────────────────┬────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────┐
│                    Client Layer                          │
│                  IbgeClient                              │
│  - Comunicação HTTP com API IBGE                         │
│  - Retry e tratamento de erros                          │
└────────────────────┬────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────┐
│                    API IBGE                              │
│         https://servicodados.ibge.gov.br                 │
└─────────────────────────────────────────────────────────┘
```

## Estrutura de Pacotes

```
com.upsaude.integration.ibge/
├── client/
│   ├── IbgeClient.java              # Cliente HTTP
│   └── IbgeClientConfig.java         # Configuração RestTemplate
├── dto/
│   ├── IbgeEstadoDTO.java           # DTO para estados
│   ├── IbgeMunicipioDTO.java        # DTO para municípios
│   ├── IbgeProjecaoPopulacaoDTO.java # DTO para população
│   └── IbgeRegiaoDTO.java           # DTO para regiões
├── exception/
│   ├── IbgeIntegrationException.java # Exceção genérica
│   └── IbgeTimeoutException.java    # Exceção de timeout
└── service/
    ├── IbgeService.java             # Interface do serviço
    └── IbgeServiceImpl.java         # Implementação
```

## Configuração

### Arquivo de Configuração

**Localização**: `src/main/resources/config/common/integrations/application-ibge.properties`

```properties
# URL base da API IBGE
ibge.api.base-url=https://servicodados.ibge.gov.br/api/v1

# Timeout em segundos
ibge.api.timeout-seconds=30
ibge.api.timeout-validation-seconds=10

# Configurações de retry
ibge.api.retry.max-attempts=3
ibge.api.retry.max-attempts-validation=2
ibge.api.retry.backoff-millis=1000
```

### Importação no Application Properties

O arquivo é importado automaticamente via:

```properties
spring.config.import=\
  optional:classpath:config/common/integrations/application-ibge.properties
```

## Banco de Dados

### Migrations

#### V046 - Adicionar Colunas IBGE em Estados

```sql
ALTER TABLE estados ADD COLUMN IF NOT EXISTS nome_oficial_ibge VARCHAR(200);
ALTER TABLE estados ADD COLUMN IF NOT EXISTS sigla_ibge VARCHAR(2);
ALTER TABLE estados ADD COLUMN IF NOT EXISTS regiao_ibge VARCHAR(50);
ALTER TABLE estados ADD COLUMN IF NOT EXISTS ativo_ibge BOOLEAN DEFAULT TRUE;
ALTER TABLE estados ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_ibge TIMESTAMPTZ;

CREATE UNIQUE INDEX IF NOT EXISTS idx_estados_codigo_ibge_unique 
ON estados(codigo_ibge) WHERE codigo_ibge IS NOT NULL;
```

#### V047 - Adicionar Colunas IBGE em Cidades

```sql
ALTER TABLE cidades ADD COLUMN IF NOT EXISTS nome_oficial_ibge VARCHAR(200);
ALTER TABLE cidades ADD COLUMN IF NOT EXISTS uf_ibge VARCHAR(2);
ALTER TABLE cidades ADD COLUMN IF NOT EXISTS populacao_estimada INTEGER;
ALTER TABLE cidades ADD COLUMN IF NOT EXISTS ativo_ibge BOOLEAN DEFAULT TRUE;
ALTER TABLE cidades ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_ibge TIMESTAMPTZ;

CREATE UNIQUE INDEX IF NOT EXISTS idx_cidades_codigo_ibge_unique 
ON cidades(codigo_ibge) WHERE codigo_ibge IS NOT NULL;
```

### Estrutura das Tabelas

#### Estados

| Coluna | Tipo | Descrição |
|--------|------|-----------|
| `codigo_ibge` | VARCHAR(20) | Código IBGE único (já existente) |
| `nome_oficial_ibge` | VARCHAR(200) | Nome oficial do estado no IBGE |
| `sigla_ibge` | VARCHAR(2) | Sigla UF do IBGE |
| `regiao_ibge` | VARCHAR(50) | Nome da região IBGE |
| `ativo_ibge` | BOOLEAN | Status ativo no IBGE |
| `data_ultima_sincronizacao_ibge` | TIMESTAMPTZ | Timestamp da última sincronização |

#### Cidades

| Coluna | Tipo | Descrição |
|--------|------|-----------|
| `codigo_ibge` | VARCHAR(10) | Código IBGE único (já existente) |
| `nome_oficial_ibge` | VARCHAR(200) | Nome oficial do município no IBGE |
| `uf_ibge` | VARCHAR(2) | UF do município |
| `populacao_estimada` | INTEGER | População estimada |
| `ativo_ibge` | BOOLEAN | Status ativo no IBGE |
| `data_ultima_sincronizacao_ibge` | TIMESTAMPTZ | Timestamp da última sincronização |

## Endpoints IBGE Utilizados

### 1. Listar Regiões

```
GET https://servicodados.ibge.gov.br/api/v1/localidades/regioes
```

**Uso**: Sincronização inicial de regiões do Brasil.

### 2. Listar Estados

```
GET https://servicodados.ibge.gov.br/api/v1/localidades/estados
```

**Uso**: Sincronização de estados e validação de UF.

### 3. Listar Municípios por UF

```
GET https://servicodados.ibge.gov.br/api/v1/localidades/estados/{UF}/municipios
```

**Uso**: Sincronização de municípios por estado.

### 4. Buscar Município por Código IBGE

```
GET https://servicodados.ibge.gov.br/api/v1/localidades/municipios/{codigo_ibge}
```

**Uso**: Validação pontual de município.

### 5. Projeção de População

```
GET https://servicodados.ibge.gov.br/api/v1/projecoes/populacao/{codigoMunicipio}
```

**Uso**: Atualização de população estimada.

## Lógica de Sincronização

### UPSERT Strategy

```java
// Pseudocódigo da lógica UPSERT
Optional<Estados> estadoExistente = estadosRepository.findByCodigoIbge(codigoIbge);

if (estadoExistente.isPresent()) {
    // UPDATE: Atualiza campos IBGE
    Estados estado = estadoExistente.get();
    estado.setNomeOficialIbge(dto.getNome());
    estado.setSiglaIbge(dto.getSigla());
    estado.setRegiaoIbge(regiaoNome);
    estado.setAtivoIbge(true);
    estado.setDataUltimaSincronizacaoIbge(OffsetDateTime.now());
    estadosRepository.save(estado);
} else {
    // INSERT: Cria novo registro (se necessário)
    // Nota: Normalmente não cria novos, apenas atualiza existentes
}
```

### Tratamento de Erros

#### Retry Logic

```java
// Estratégia de retry com backoff exponencial
for (int attempt = 1; attempt <= maxAttempts; attempt++) {
    try {
        // Tentativa de requisição
        return restTemplate.exchange(...);
    } catch (ResourceAccessException e) {
        if (attempt < maxAttempts) {
            Thread.sleep(backoffMillis * attempt); // Backoff exponencial
            continue;
        }
        throw new IbgeTimeoutException(...);
    }
}
```

#### Exceções Customizadas

- **IbgeIntegrationException**: Erro genérico de integração
- **IbgeTimeoutException**: Timeout ou erro de conexão

## Segurança

### Autenticação

- API IBGE é pública, não requer autenticação
- Endpoints internos requerem autenticação JWT (padrão do sistema)

### Validação

- Validação de códigos IBGE antes de persistir
- Sanitização de dados recebidos da API
- Validação de tipos e formatos

## Performance

### Otimizações

- **Transações**: Sincronizações executadas em transação única
- **Batch Processing**: Processamento em lote quando possível
- **Cache de Regiões**: Cache interno para evitar consultas repetidas
- **Lazy Loading**: Beans SOAP/JAXB configurados como lazy

### Métricas

- Timeout configurável (padrão: 30s)
- Retry automático (padrão: 3 tentativas)
- Backoff exponencial (padrão: 1000ms)

## Logging

### Níveis de Log

- **INFO**: Sincronizações iniciadas/concluídas
- **DEBUG**: Detalhes de requisições HTTP
- **WARN**: Retries e timeouts
- **ERROR**: Erros críticos de integração

### Informações Logadas

- Contadores de registros sincronizados
- Tempo de execução
- Erros por etapa
- URLs das requisições

## Testes

### Testes Unitários

- Testes de DTOs e mapeamentos
- Testes de lógica de UPSERT
- Testes de tratamento de erros

### Testes de Integração

- Testes com API IBGE (mock ou real)
- Testes de sincronização completa
- Testes de validação de municípios

## Manutenção

### Monitoramento

- Verificar logs de sincronização
- Monitorar taxa de sucesso
- Verificar timestamps de última sincronização

### Troubleshooting

1. **Timeout**: Aumentar `ibge.api.timeout-seconds`
2. **Erros de Conexão**: Verificar conectividade com API IBGE
3. **Duplicatas**: Verificar índices únicos no banco
4. **Dados Inconsistentes**: Executar sincronização completa

## Dependências

### Maven

```xml
<!-- Spring Web (RestTemplate) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

## Próximos Passos

### Melhorias Futuras

- [ ] Cache de resultados da API IBGE
- [ ] Sincronização incremental (apenas mudanças)
- [ ] Webhook para notificações de mudanças
- [ ] Dashboard de monitoramento
- [ ] Sincronização agendada (cron job)

