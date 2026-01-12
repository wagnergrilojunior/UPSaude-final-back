# 🏗️ Visão Geral - Arquitetura FHIR

## 1. O que é FHIR?

**FHIR (Fast Healthcare Interoperability Resources)** é um padrão desenvolvido pelo HL7 para troca de dados de saúde. Ele define:

- **Recursos (Resources):** Estruturas de dados padronizadas (Patient, Observation, Immunization, etc.)
- **APIs RESTful:** Endpoints HTTP para leitura e escrita de dados
- **Terminologias:** Códigos padronizados para diagnósticos, procedimentos, medicamentos, etc.

---

## 2. FHIR no Brasil

O Brasil adotou o FHIR através do **Ministério da Saúde**, que mantém o:

### Guia de Implementação de Terminologias do Brasil
- **URL:** https://terminologia.saude.gov.br/fhir
- **Versão:** FHIR R4 (4.0.1)
- **Release:** 1.0.0 - STU1

Este guia define as terminologias brasileiras específicas, como:
- Códigos de vacinas do PNI
- CID-10 adaptado
- Tabela SUS de procedimentos
- Medicamentos ANVISA
- E muitos outros...

---

## 3. Tipos de Artefatos FHIR

O sistema FHIR brasileiro disponibiliza três tipos principais de artefatos:

### 3.1 CodeSystem (Sistema de Código)
Define um conjunto completo de códigos. É a **fonte de verdade** para os códigos.

```
Exemplo: BRImunobiologico
- Código: 85
- Nome: COVID-19 SINOVAC/BUTANTAN - CORONAVAC
```

**URL base:** `https://terminologia.saude.gov.br/fhir/CodeSystem/`

### 3.2 ValueSet (Conjunto de Valores)
Agrupa códigos de um ou mais CodeSystems para um uso específico. É usado para **validação**.

```
Exemplo: BRImunobiologico (ValueSet)
- Inclui todos os códigos do CodeSystem BRImunobiologico
```

**URL base:** `https://terminologia.saude.gov.br/fhir/ValueSet/`

### 3.3 NamingSystem (Sistema de Nomenclatura)
Define identificadores únicos para sistemas externos (CPF, CNES, CRM, etc.).

```
Exemplo: CNS
- Identificador: Cartão Nacional de Saúde
- OID: 2.16.840.1.113883.13.236
```

**URL base:** `https://terminologia.saude.gov.br/fhir/NamingSystem/`

---

## 4. Arquitetura de Integração Proposta

```
┌─────────────────────────────────────────────────────────────────┐
│                         UPSaude System                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌───────────────┐    ┌───────────────┐    ┌───────────────┐   │
│  │  Controller   │    │   Service     │    │  Repository   │   │
│  │  (REST API)   │───▶│  (Lógica)     │───▶│  (Database)   │   │
│  └───────────────┘    └───────────────┘    └───────────────┘   │
│         │                    │                                  │
│         │                    │                                  │
│         ▼                    ▼                                  │
│  ┌───────────────────────────────────────┐                     │
│  │         FHIR Integration Layer         │                     │
│  ├───────────────────────────────────────┤                     │
│  │  ┌─────────────┐  ┌─────────────────┐ │                     │
│  │  │ FhirClient  │  │  FhirMapper     │ │                     │
│  │  │ (HTTP)      │  │  (JSON→Entity)  │ │                     │
│  │  └─────────────┘  └─────────────────┘ │                     │
│  └───────────────────────────────────────┘                     │
│                        │                                        │
└────────────────────────│────────────────────────────────────────┘
                         │
                         │ HTTPS (JSON)
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│               FHIR Server (Governo)                             │
│         https://terminologia.saude.gov.br/fhir                  │
├─────────────────────────────────────────────────────────────────┤
│  ┌────────────┐  ┌────────────┐  ┌────────────┐                │
│  │ CodeSystem │  │  ValueSet  │  │ NamingSystem│                │
│  └────────────┘  └────────────┘  └────────────┘                │
└─────────────────────────────────────────────────────────────────┘
```

---

## 5. Modos de Operação

A integração terá dois modos de operação:

### 5.1 Modo Consulta (Query-Only)
- Consulta os dados diretamente no servidor FHIR
- **NÃO grava** nada no banco local
- Útil para: validação em tempo real, autocomplete, busca

```
[Frontend] → [Controller] → [FhirService] → [FHIR Server]
                                  ↓
                           [Return DTO]
```

### 5.2 Modo Sincronização (Sync)
- Consulta os dados no servidor FHIR
- **GRAVA** os dados no banco local
- Útil para: cache local, performance, operação offline

```
[Frontend] → [Controller] → [FhirSyncService] → [FHIR Server]
                                  ↓
                           [FhirMapper]
                                  ↓
                           [Repository]
                                  ↓
                           [Database]
```

---

## 6. Estrutura de Pacotes Proposta

```
com.upsaude
└── integration
    └── fhir
        ├── client/
        │   ├── FhirClient.java              # Cliente HTTP
        │   └── FhirClientConfig.java        # Configuração
        │
        ├── dto/
        │   ├── fhir/                        # DTOs nativos FHIR
        │   │   ├── CodeSystemDTO.java
        │   │   ├── ValueSetDTO.java
        │   │   └── ConceptDTO.java
        │   │
        │   └── local/                       # DTOs de resposta
        │       ├── ImunobiologicoDTO.java
        │       └── SincronizacaoResultDTO.java
        │
        ├── mapper/
        │   ├── ImunobiologicoFhirMapper.java
        │   └── ...Mapper.java
        │
        ├── service/
        │   ├── consulta/                    # Serviços de consulta
        │   │   └── FhirConsulta*Service.java
        │   │
        │   └── sync/                        # Serviços de sincronização
        │       └── FhirSync*Service.java
        │
        └── job/
            └── FhirSyncScheduler.java       # Jobs agendados
```

---

## 7. Formato de Dados

O servidor FHIR retorna dados em formato **JSON** (ou XML). 

### Exemplo de resposta CodeSystem:

```json
{
  "resourceType": "CodeSystem",
  "id": "BRImunobiologico",
  "url": "https://terminologia.saude.gov.br/fhir/CodeSystem/BRImunobiologico",
  "version": "1.0.0",
  "name": "BRImunobiologico",
  "status": "active",
  "content": "complete",
  "concept": [
    {
      "code": "85",
      "display": "COVID-19 SINOVAC/BUTANTAN - CORONAVAC"
    },
    {
      "code": "86",
      "display": "COVID-19 ASTRAZENECA/FIOCRUZ - COVISHIELD"
    }
  ]
}
```

### Acessando os dados:

```
# JSON
GET https://terminologia.saude.gov.br/fhir/CodeSystem-BRImunobiologico.json

# XML
GET https://terminologia.saude.gov.br/fhir/CodeSystem-BRImunobiologico.xml
```

---

## 8. Considerações de Implementação

### 8.1 Cache
- Implementar cache local para evitar chamadas excessivas
- Usar Redis ou cache em memória
- TTL sugerido: 24 horas para dados de referência

### 8.2 Tratamento de Erros
- Timeout configurável (sugestão: 30 segundos)
- Retry com backoff exponencial
- Fallback para dados locais em caso de indisponibilidade

### 8.3 Multi-tenancy
- Tabelas de referência podem ser compartilhadas entre tenants
- Registros de negócio (vacinação, etc.) devem ser por tenant

### 8.4 Auditoria
- Registrar data/hora de sincronização
- Manter histórico de versões
- Log de erros de integração

---

## 9. Dependências Sugeridas

### Opção 1: HAPI FHIR (Recomendado para parsing nativo)
```xml
<dependency>
    <groupId>ca.uhn.hapi.fhir</groupId>
    <artifactId>hapi-fhir-client</artifactId>
    <version>7.0.0</version>
</dependency>

<dependency>
    <groupId>ca.uhn.hapi.fhir</groupId>
    <artifactId>hapi-fhir-structures-r4</artifactId>
    <version>7.0.0</version>
</dependency>
```

### Opção 2: RestTemplate/WebClient (Mais leve)
```xml
<!-- Já incluído no Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

---

## 10. Próximos Passos

1. ✅ Documentação inicial (este arquivo)
2. ⏳ Definir modelagem de banco de dados
3. ⏳ Implementar cliente HTTP FHIR
4. ⏳ Criar serviços de sincronização
5. ⏳ Criar endpoints REST
6. ⏳ Implementar jobs de sincronização agendada
7. ⏳ Testes de integração
