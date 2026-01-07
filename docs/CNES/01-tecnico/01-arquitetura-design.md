# Arquitetura e Design - Integração CNES

## 📐 Visão Geral da Arquitetura

A integração CNES foi desenvolvida seguindo uma arquitetura em camadas, utilizando Spring Boot e padrões de design para garantir manutenibilidade e escalabilidade.

## 🏗️ Diagrama de Arquitetura

```mermaid
graph TB
    subgraph "Cliente"
        API[REST API]
    end
    
    subgraph "Camada de Apresentação"
        Controller[CnesController<br/>CnesSincronizacaoController]
    end
    
    subgraph "Camada de Serviço"
        Service[CnesEstabelecimentoService<br/>CnesProfissionalService<br/>CnesEquipeService<br/>etc.]
        SyncService[CnesSincronizacaoService]
    end
    
    subgraph "Camada de Integração"
        SoapClient[EstabelecimentoCnesSoapClient<br/>ProfissionalCnesSoapClient<br/>EquipeCnesSoapClient<br/>etc.]
        Config[CnesSoapConfig]
    end
    
    subgraph "Camada de Mapeamento"
        Mapper[CnesEstabelecimentoMapper<br/>MapStruct]
    end
    
    subgraph "Camada de Persistência"
        Repository[CnesSincronizacaoRepository<br/>EstabelecimentosRepository<br/>etc.]
    end
    
    subgraph "Banco de Dados"
        DB[(PostgreSQL<br/>Tabelas CNES)]
    end
    
    subgraph "Serviços Externos"
        DATASUS[SOA-CNES DATASUS<br/>Web Services SOAP]
    end
    
    API --> Controller
    Controller --> Service
    Service --> SyncService
    Service --> SoapClient
    Service --> Mapper
    Service --> Repository
    Mapper --> Repository
    Repository --> DB
    SoapClient --> Config
    Config --> DATASUS
    
    style API fill:#e1f5ff
    style Controller fill:#fff4e1
    style Service fill:#e8f5e9
    style SoapClient fill:#f3e5f5
    style Mapper fill:#fff9c4
    style Repository fill:#e0f2f1
    style DB fill:#ffebee
    style DATASUS fill:#fce4ec
```

## 🔄 Fluxo de Sincronização

```mermaid
sequenceDiagram
    participant Client
    participant Controller
    participant Service
    participant SyncService
    participant SoapClient
    participant DATASUS
    participant Mapper
    participant Repository
    participant DB
    
    Client->>Controller: POST /v1/cnes/estabelecimentos/{cnes}/sincronizar
    Controller->>Service: sincronizarEstabelecimentoPorCnes()
    
    Service->>SyncService: criarRegistroSincronizacao()
    SyncService->>DB: INSERT cnes_sincronizacao (PENDENTE)
    
    Service->>SyncService: marcarComoProcessando()
    SyncService->>DB: UPDATE status = PROCESSANDO
    
    Service->>SoapClient: consultarEstabelecimentoPorCnes()
    SoapClient->>DATASUS: SOAP Request
    DATASUS-->>SoapClient: SOAP Response
    SoapClient-->>Service: DadosGeraisEstabelecimentoSaudeType
    
    Service->>Mapper: toEntity(dadosCnes)
    Mapper-->>Service: Estabelecimentos entity
    
    Service->>Repository: save(estabelecimento)
    Repository->>DB: INSERT/UPDATE estabelecimentos
    
    Service->>Repository: salvarHistorico()
    Repository->>DB: INSERT cnes_historico_estabelecimento
    
    Service->>SyncService: finalizarComSucesso()
    SyncService->>DB: UPDATE status = SUCESSO
    
    Service-->>Controller: CnesSincronizacaoResponse
    Controller-->>Client: HTTP 200 OK
```

## 🧩 Componentes Principais

### 1. Controllers (Camada de Apresentação)

**Localização**: `com.upsaude.controller.api.cnes`

- **CnesController**: Endpoints principais de sincronização
- **CnesSincronizacaoController**: Endpoints de consulta e histórico

### 2. Services (Camada de Negócio)

**Localização**: `com.upsaude.service.api.cnes`

- **CnesEstabelecimentoService**: Lógica de sincronização de estabelecimentos
- **CnesProfissionalService**: Lógica de sincronização de profissionais
- **CnesEquipeService**: Lógica de sincronização de equipes
- **CnesSincronizacaoService**: Gerenciamento de registros de sincronização

### 3. SOAP Clients (Camada de Integração)

**Localização**: `com.upsaude.integration.cnes.soap.client`

- **AbstractCnesSoapClient**: Classe base abstrata com lógica comum
- **EstabelecimentoCnesSoapClient**: Cliente para serviços de estabelecimentos
- **ProfissionalCnesSoapClient**: Cliente para serviços de profissionais
- **EquipeCnesSoapClient**: Cliente para serviços de equipes
- **EquipamentoCnesSoapClient**: Cliente para serviços de equipamentos
- **LeitoCnesSoapClient**: Cliente para serviços de leitos

### 4. Configuration (Configuração)

**Localização**: `com.upsaude.config`

- **CnesSoapConfig**: Configuração do Spring Web Services para SOAP

### 5. Mappers (Camada de Transformação)

**Localização**: `com.upsaude.mapper.cnes`

- **CnesEstabelecimentoMapper**: Conversão WSDL → JPA Entity (MapStruct)

### 6. Repositories (Camada de Persistência)

**Localização**: `com.upsaude.repository.cnes`

- **CnesSincronizacaoRepository**: Acesso a dados de sincronização
- **CnesHistoricoEstabelecimentoRepository**: Acesso a histórico

## 🔐 Segurança

```mermaid
graph LR
    A[Cliente] -->|JWT Token| B[Spring Security]
    B -->|Validação| C[Tenant Context]
    C -->|Multitenancy| D[Service Layer]
    D -->|WS-Security| E[SOAP Client]
    E -->|UsernameToken| F[DATASUS]
    
    style B fill:#ffebee
    style C fill:#e8f5e9
    style E fill:#fff4e1
```

A integração utiliza:
- **JWT Authentication** para acesso à API REST
- **WS-Security UsernameToken** para autenticação SOAP
- **Multitenancy** para isolamento de dados por tenant

## 📦 Dependências Principais

```mermaid
graph TD
    A[Spring Boot 3.3.4] --> B[Spring Web Services]
    A --> C[Spring Data JPA]
    A --> D[MapStruct]
    
    B --> E[SaajSoapMessageFactory]
    B --> F[Wss4jSecurityInterceptor]
    
    C --> G[Hibernate]
    C --> H[PostgreSQL Driver]
    
    D --> I[Annotation Processor]
    
    style A fill:#e1f5ff
    style B fill:#fff4e1
    style C fill:#e8f5e9
    style D fill:#fff9c4
```

## 🎯 Padrões de Design Utilizados

1. **Repository Pattern**: Abstração de acesso a dados
2. **Service Layer Pattern**: Separação de lógica de negócio
3. **DTO Pattern**: Transferência de dados entre camadas
4. **Strategy Pattern**: Diferentes estratégias de sincronização
5. **Template Method**: Classe abstrata para SOAP clients
6. **Builder Pattern**: Construção de objetos complexos

## 🔄 Processamento Assíncrono (Futuro)

```mermaid
graph TB
    A[Request] --> B[Controller]
    B --> C[Service]
    C --> D[Queue]
    D --> E[Async Processor]
    E --> F[SOAP Call]
    F --> G[Database]
    E --> H[Notification]
    
    style D fill:#fff4e1
    style E fill:#e8f5e9
```

**Nota**: Atualmente a sincronização é síncrona. Uma implementação futura pode incluir processamento assíncrono para melhor performance.

## 📊 Monitoramento e Observabilidade

- **Logging**: SLF4J com Logback
- **Metrics**: Spring Boot Actuator
- **Health Checks**: `/actuator/health`
- **Tracing**: Preparado para integração com sistemas de tracing

## 🚀 Performance

- **Connection Pool**: HikariCP
- **Caching**: Preparado para Redis (futuro)
- **Timeout Configuration**: Configurável por propriedade
- **Retry Logic**: Implementado no AbstractCnesSoapClient

