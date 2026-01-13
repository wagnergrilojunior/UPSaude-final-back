# 🏗️ Arquitetura FHIR - Modelo Canônico Único

## 1. Princípios Fundamentais

### 1.1 Modelo Canônico Único
Cada conceito de domínio possui **uma única tabela central**:
- CBO/Ocupações → `sigtap_ocupacao`
- Exames → `catalogo_exames`
- Procedimentos → `sigtap_procedimento`
- Diagnósticos → CID-10 existente

### 1.2 Enriquecimento, Nunca Substituição
FHIR **NÃO cria tabelas próprias**. Ele enriquece as existentes:

```sql
-- ❌ ERRADO: Criar tabela paralela
CREATE TABLE cbo_fhir (...);

-- ✅ CORRETO: Enriquecer tabela existente
ALTER TABLE sigtap_ocupacao ADD COLUMN codigo_cbo_completo VARCHAR(10);
```

### 1.3 Convivência de Origens
Múltiplas integrações compartilham a mesma tabela com campos de controle:

```sql
-- Campos de controle de origem
source_system VARCHAR(20),    -- SIGTAP, FHIR, CNES, LOINC
external_code VARCHAR(50),    -- Código na origem
external_version VARCHAR(50), -- Versão
last_sync_at TIMESTAMP        -- Última sincronização
```

---

## 2. Mapeamento por Módulo

| Domínio | Tabela Canônica | Origens Suportadas |
|---------|-----------------|-------------------|
| Ocupações | `sigtap_ocupacao` | SIGTAP, CBO/FHIR |
| Exames | `catalogo_exames` | SIGTAP, LOINC, GAL, TUSS |
| Procedimentos | `sigtap_procedimento` | SIGTAP |
| Diagnósticos | `cid10_*` | CID-10, CIAP-2 |
| Alergias | `alergias_*` | FHIR |
| Vacinas | `imunobiologicos` | FHIR |
| Medicamentos | `medicamentos` | FHIR, ANVISA |
| Conselhos | `conselhos_profissionais` | FHIR (novo) |

---

## 3. Regras de Sincronização

### 3.1 Isolamento entre Integrações
```java
// SIGTAP não sobrescreve FHIR
if (sourceSystem.equals("SIGTAP")) {
    // Atualiza apenas campos SIGTAP
    entity.setCodigoOficial(code);
}

// FHIR não sobrescreve SIGTAP
if (sourceSystem.equals("FHIR")) {
    // Atualiza apenas campos FHIR
    entity.setCodigoCboCompleto(code);
}
```

### 3.2 Estratégia de Merge
1. Buscar por `source_system` + `external_code`
2. Se existe: atualizar campos da mesma origem
3. Se não existe: criar novo registro

### 3.3 Nunca Apagar Dados de Outra Origem
```java
// ❌ ERRADO
entity.setCodigoSigtap(null); // Apaga dados SIGTAP

// ✅ CORRETO
if (entity.getCodigoSigtap() == null) {
    entity.setCodigoSigtap(sigtapCode);
}
```

---

## 4. Estrutura de Diretórios

```
src/main/java/com/upsaude/
├── entity/referencia/
│   ├── exame/
│   │   └── CatalogoExame.java      # Unificado
│   ├── profissional/
│   │   └── ConselhoProfissional.java
│   └── sigtap/
│       ├── SigtapOcupacao.java     # Enriquecido
│       └── SigtapProcedimento.java
│
├── integration/fhir/service/
│   ├── exame/ExameSyncService.java
│   └── profissional/ProfissionalSyncService.java
│
└── repository/referencia/
    └── exame/CatalogoExameRepository.java
```

---

## 5. Checklist de Nova Integração

Antes de criar qualquer entidade ou tabela:

- [ ] Verificar se já existe tabela para o conceito
- [ ] Se existe: enriquecer com novos campos
- [ ] Se não existe: criar com suporte a múltiplas origens
- [ ] Adicionar campos de controle: `source_system`, `external_code`, `last_sync_at`
- [ ] Garantir que não sobrescreve dados de outras origens
- [ ] Documentar convivência com integrações existentes

---

## 6. Integrações Existentes (Não Alteradas)

| Integração | Status | Descrição |
|------------|--------|-----------|
| SIGTAP | ✅ Mantida | Faturamento SUS |
| CID-10 | ✅ Mantida | Diagnósticos |
| IBGE | ✅ Mantida | Geografia |
| CNES | ✅ Mantida | Estabelecimentos |
| ViaCEP | ✅ Mantida | Endereços |

---

## 7. Conclusão

O sistema está preparado para:
- ✅ Receber dados de múltiplas fontes
- ✅ Manter histórico de sincronização
- ✅ Não quebrar integrações existentes
- ✅ Escalar para novas terminologias
- ✅ Governar origem dos dados
