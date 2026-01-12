# 🗺️ Roadmap de Implementação FHIR

## Visão Geral

Este roadmap define a ordem sugerida para implementação das integrações FHIR, considerando:
- Valor para o negócio
- Dependências técnicas
- Complexidade de implementação

---

## Fase 1: Fundação (Semana 1-2)

### Objetivo: Criar infraestrutura base de integração

**Tarefas:**
- [ ] Criar pacote `integration.fhir`
- [ ] Implementar `FhirClient` (cliente HTTP)
- [ ] Implementar `FhirClientConfig` (configurações)
- [ ] Criar DTOs base para CodeSystem e ValueSet
- [ ] Implementar mecanismo de cache (Redis)
- [ ] Criar tabela de log de sincronização

**Entregáveis:**
- Cliente FHIR funcional
- Testes de conexão com servidor FHIR

---

## Fase 2: Módulo de Vacinação (Semana 3-5)

### Objetivo: Implementar módulo completo de vacinação

**Semana 3:**
- [ ] Criar migrations para tabelas de referência
- [ ] Criar entidades: `Imunobiologico`, `FabricanteImunobiologico`, `TipoDose`
- [ ] Implementar serviços de sincronização
- [ ] Criar endpoints de sincronização

**Semana 4:**
- [ ] Criar tabelas de negócio: `LoteVacina`, `AplicacaoVacina`
- [ ] Implementar CRUD de lotes
- [ ] Implementar CRUD de aplicações
- [ ] Criar DTOs e Mappers

**Semana 5:**
- [ ] Implementar carteira de vacinação
- [ ] Criar endpoint de histórico do paciente
- [ ] Implementar registro de reações adversas
- [ ] Testes de integração

**Entregáveis:**
- Catálogo de vacinas sincronizado
- CRUD de aplicações funcionando
- Carteira de vacinação do paciente

---

## Fase 3: Módulo de Diagnósticos (Semana 6-7)

### Objetivo: Integrar CID-10 e CIAP-2

**Semana 6:**
- [ ] Criar tabelas `cid10` e `ciap2`
- [ ] Implementar sincronização (batch, paginado)
- [ ] Criar índices de busca full-text
- [ ] Implementar endpoint de busca com autocomplete

**Semana 7:**
- [ ] Criar tabela `diagnosticos_paciente`
- [ ] Integrar com módulo de atendimento existente
- [ ] Implementar histórico de diagnósticos
- [ ] Testes

**Entregáveis:**
- CID-10 disponível para busca
- Diagnósticos vinculados a atendimentos

---

## Fase 4: Módulo de Alergias (Semana 8)

### Objetivo: Implementar registro e alertas de alergias

**Tarefas:**
- [ ] Criar tabelas de alérgenos e reações
- [ ] Sincronizar catálogos FHIR
- [ ] Implementar CRUD de alergias do paciente
- [ ] Criar sistema de alertas (flag no prontuário)
- [ ] Integrar com prescrição (futuro)

**Entregáveis:**
- Cadastro de alergias funcionando
- Alertas visíveis no prontuário

---

## Fase 5: Módulo de Profissionais (Semana 9)

### Objetivo: Padronizar CBO e conselhos

**Tarefas:**
- [ ] Sincronizar CBO
- [ ] Criar tabela de conselhos profissionais
- [ ] Atualizar cadastro de profissionais existente
- [ ] Implementar validação de formato de registro

**Entregáveis:**
- CBO integrado ao cadastro de profissionais
- Validação de conselhos

---

## Fase 6: Procedimentos e Medicamentos (Semana 10-12)

### Objetivo: Complementar tabelas de referência

**Semana 10:**
- [ ] Avaliar integração existente (SIGTAP)
- [ ] Sincronizar CBHPM/TUSS se necessário
- [ ] Integrar com faturamento

**Semana 11-12:**
- [ ] Sincronizar catálogo de medicamentos
- [ ] Integrar com módulo de farmácia existente
- [ ] Implementar busca por princípio ativo

**Entregáveis:**
- Tabelas de procedimentos completas
- Catálogo de medicamentos disponível

---

## Fase 7: Jobs e Automação (Semana 13)

### Objetivo: Automatizar sincronizações

**Tarefas:**
- [ ] Criar jobs agendados de sincronização
- [ ] Implementar notificações de falha
- [ ] Criar dashboard de status das integrações
- [ ] Documentar procedimentos operacionais

**Entregáveis:**
- Sincronização automática (diária/semanal)
- Monitoramento de integrações

---

## Resumo por Fase

| Fase | Módulo | Semanas | Prioridade |
|------|--------|---------|------------|
| 1 | Fundação | 1-2 | 🔴 Crítica |
| 2 | Vacinação | 3-5 | 🔴 Crítica |
| 3 | Diagnósticos | 6-7 | 🟠 Alta |
| 4 | Alergias | 8 | 🟠 Alta |
| 5 | Profissionais | 9 | 🟡 Média |
| 6 | Proc/Medicamentos | 10-12 | 🟡 Média |
| 7 | Automação | 13 | 🟢 Normal |

---

## Estimativa Total

- **Duração:** ~13 semanas (3 meses)
- **Esforço:** 60-80 horas por módulo
- **Total estimado:** 400-500 horas

---

## Dependências Externas

- Disponibilidade do servidor FHIR do governo
- Estabilidade da API
- Atualizações de terminologias

---

## Riscos

| Risco | Mitigação |
|-------|-----------|
| Servidor FHIR indisponível | Cache local, fallback |
| Volume alto de dados | Sincronização incremental |
| Mudanças na API | Versionamento, abstração |
| Performance de busca | Índices, full-text search |
