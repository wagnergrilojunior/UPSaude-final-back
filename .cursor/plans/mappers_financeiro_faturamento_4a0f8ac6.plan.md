---
name: Mappers financeiro/faturamento
overview: Criar mappers MapStruct (Request↔Entity↔Response) para todas as entidades novas de financeiro/faturamento e ajustar mappers existentes (Agendamento/Atendimento) para os novos campos, seguindo o padrão atual do projeto.
todos:
  - id: mappers-financeiro-core
    content: Criar mappers financeiros base (CompetenciaFinanceira, CompetenciaFinanceiraTenant, PlanoContas, ContaContabil, RegraClassificacaoContabil, CentroCusto, ContaFinanceira) com fromRequest/updateFromRequest/toResponse e métodos simplificados onde existirem.
    status: completed
  - id: mappers-financeiro-banco
    content: Criar mappers de banco/conciliação (TransferenciaEntreContas, MovimentacaoConta, ExtratoBancarioImportado, ConciliacaoBancaria, ConciliacaoItem), garantindo uso de responses simplificadas para evitar ciclos.
    status: completed
    dependencies:
      - mappers-financeiro-core
  - id: mappers-financeiro-arap
    content: Criar mappers de contas a receber/pagar (ParteFinanceira, TituloReceber, BaixaReceber, RenegociacaoReceber, TituloPagar, PagamentoPagar, RecorrenciaFinanceira).
    status: in_progress
    dependencies:
      - mappers-financeiro-core
  - id: mappers-financeiro-orcamento
    content: Criar mappers de orçamento/GAA/estornos (OrcamentoCompetencia, CreditoOrcamentario, ReservaOrcamentariaAssistencial, GuiaAtendimentoAmbulatorial, EstornoFinanceiro).
    status: pending
    dependencies:
      - mappers-financeiro-core
  - id: mappers-financeiro-lancamentos
    content: Criar mappers de lançamentos contábeis (LancamentoFinanceiro, LancamentoFinanceiroItem) com mapeamento de lista de itens e referências simplificadas.
    status: pending
    dependencies:
      - mappers-financeiro-core
      - mappers-financeiro-arap
      - mappers-faturamento
  - id: mappers-financeiro-auditoria
    content: Criar mapper de LogFinanceiro (LogFinanceiroMapper) e alinhar campos com LogFinanceiroResponse/Request.
    status: pending
    dependencies:
      - mappers-financeiro-core
  - id: mappers-faturamento
    content: Criar mappers de faturamento (DocumentoFaturamento, DocumentoFaturamentoItem, Glosa) usando competência, convênio, agendamento/atendimento simplificados e procedimento SIGTAP simplificado.
    status: pending
  - id: mappers-clinica-procedimento
    content: Criar AtendimentoProcedimentoMapper e ajustar mapeamento de procedimento SIGTAP simplificado.
    status: pending
  - id: ajustar-agendamento-atendimento-mappers
    content: Ajustar AgendamentoMapper e AtendimentoMapper para os novos campos financeiros (competenciaFinanceira UUID vs entidade, statusFinanceiro/valorEstimadoTotal) e lista de procedimentos, além de adicionar métodos toSimplifiedResponse para uso no faturamento/financeiro.
    status: pending
    dependencies:
      - mappers-financeiro-core
      - mappers-clinica-procedimento
  - id: helpers-simplificados
    content: "Centralizar helpers de simplificados onde fizer sentido (ex.: adicionar toSimplifiedResponse em SigtapProcedimentoMapper e opcionalmente em ConvenioMapper)."
    status: pending
    dependencies:
      - mappers-faturamento
      - mappers-clinica-procedimento
  - id: validar-compilacao
    content: Rodar `mvn -DskipTests compile` para validar geração do MapStruct e corrigir eventuais erros de mapeamento por tipo/ciclo.
    status: pending
    dependencies:
      - mappers-financeiro-core
      - mappers-financeiro-banco
      - mappers-financeiro-arap
      - mappers-financeiro-orcamento
      - mappers-financeiro-lancamentos
      - mappers-financeiro-auditoria
      - mappers-faturamento
      - mappers-clinica-procedimento
      - ajustar-agendamento-atendimento-mappers
      - helpers-simplificados
---

# Plano detalhado — criação de Mappers (MapStruct)

## Objetivo

Criar **mappers MapStruct** para todas as entidades novas de **Financeiro** e **Faturamento** (e `AtendimentoProcedimento`), além de **ajustar** mappers existentes (`AgendamentoMapper`, `AtendimentoMapper`) para suportar os novos campos (`competenciaFinanceira`, procedimentos, etc.) e evitar erros de compilação por conflito de tipos (UUID ↔ entidade).

## Padrão do projeto (confirmado no código)

- **MapStruct + Spring** via `@Mapper(config = MappingConfig.class)`.
- Config global em [`src/main/java/com/upsaude/mapper/config/MappingConfig.java`](src/main/java/com/upsaude/mapper/config/MappingConfig.java):
  - `componentModel = "spring"`
  - `unmappedTargetPolicy = IGNORE`
  - `nullValuePropertyMappingStrategy = IGNORE`
- Convenção recorrente:
  - Métodos **Entity ← Request**:
    - `fromRequest(Request)`
    - `updateFromRequest(Request, @MappingTarget Entity)`
    - Sempre `@Mapping(target = "id|createdAt|updatedAt|active", ignore = true)`
    - **Relacionamentos** (ManyToOne/OneToMany) normalmente são `ignore = true` e preenchidos no service (porque o Request carrega UUID).
  - Método **Entity → Response**: `toResponse(Entity)`
  - Para “simplificados”: criar método adicional `toSimplifiedResponse(Entity)` quando existir `*SimplificadoResponse`.

## Pontos críticos (para não quebrar o build)

- Você adicionou `competenciaFinanceira` (UUID) em `AgendamentoRequest`/`AtendimentoRequest`, mas nas entidades o campo é `CompetenciaFinanceira` (entidade). Isso **vai quebrar a geração do mapper** se não tiver `@Mapping(target = "competenciaFinanceira", ignore = true)` nos métodos `fromRequest/updateFromRequest`.
- Você adicionou `procedimentos` (List<Request/Response)) em `AtendimentoRequest/Response`, mas a entidade usa `List<AtendimentoProcedimento>`.
  - Em `fromRequest/updateFromRequest`: **ignorar** `procedimentos` (pois envolve criação/atualização de filhos e amarração com atendimento).
  - Em `toResponse`: mapear a lista com um mapper dedicado `AtendimentoProcedimentoMapper`.
- Evitar recursão/ciclos: onde Response já usa `*SimplificadoResponse`, garantir que o mapper utilize esses tipos (MapStruct escolhe pelo tipo de destino se existirem métodos).

## Estrutura de pastas a criar

- Criar pacote **Financeiro**: [`src/main/java/com/upsaude/mapper/financeiro/`](src/main/java/com/upsaude/mapper/financeiro/)
- Criar pacote **Faturamento**: [`src/main/java/com/upsaude/mapper/faturamento/`](src/main/java/com/upsaude/mapper/faturamento/)
- Criar mapper no pacote existente: [`src/main/java/com/upsaude/mapper/clinica/atendimento/`](src/main/java/com/upsaude/mapper/clinica/atendimento/)

## Lista de mappers a criar (1:1 com as entidades novas)

### Financeiro (27 entidades)

Criar mappers (interface, salvo exceções) em `com.upsaude.mapper.financeiro`:

- **Período/competência**
  - [`src/main/java/com/upsaude/mapper/financeiro/CompetenciaFinanceiraMapper.java`](src/main/java/com/upsaude/mapper/financeiro/CompetenciaFinanceiraMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/CompetenciaFinanceiraTenantMapper.java`](src/main/java/com/upsaude/mapper/financeiro/CompetenciaFinanceiraTenantMapper.java)
- **Plano de contas / classificação**
  - [`src/main/java/com/upsaude/mapper/financeiro/PlanoContasMapper.java`](src/main/java/com/upsaude/mapper/financeiro/PlanoContasMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/ContaContabilMapper.java`](src/main/java/com/upsaude/mapper/financeiro/ContaContabilMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/RegraClassificacaoContabilMapper.java`](src/main/java/com/upsaude/mapper/financeiro/RegraClassificacaoContabilMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/CentroCustoMapper.java`](src/main/java/com/upsaude/mapper/financeiro/CentroCustoMapper.java)
- **Contas/Movimentações/Conciliação**
  - [`src/main/java/com/upsaude/mapper/financeiro/ContaFinanceiraMapper.java`](src/main/java/com/upsaude/mapper/financeiro/ContaFinanceiraMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/TransferenciaEntreContasMapper.java`](src/main/java/com/upsaude/mapper/financeiro/TransferenciaEntreContasMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/MovimentacaoContaMapper.java`](src/main/java/com/upsaude/mapper/financeiro/MovimentacaoContaMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/ExtratoBancarioImportadoMapper.java`](src/main/java/com/upsaude/mapper/financeiro/ExtratoBancarioImportadoMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/ConciliacaoBancariaMapper.java`](src/main/java/com/upsaude/mapper/financeiro/ConciliacaoBancariaMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/ConciliacaoItemMapper.java`](src/main/java/com/upsaude/mapper/financeiro/ConciliacaoItemMapper.java)
- **Contas a receber / pagar**
  - [`src/main/java/com/upsaude/mapper/financeiro/ParteFinanceiraMapper.java`](src/main/java/com/upsaude/mapper/financeiro/ParteFinanceiraMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/TituloReceberMapper.java`](src/main/java/com/upsaude/mapper/financeiro/TituloReceberMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/BaixaReceberMapper.java`](src/main/java/com/upsaude/mapper/financeiro/BaixaReceberMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/RenegociacaoReceberMapper.java`](src/main/java/com/upsaude/mapper/financeiro/RenegociacaoReceberMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/TituloPagarMapper.java`](src/main/java/com/upsaude/mapper/financeiro/TituloPagarMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/PagamentoPagarMapper.java`](src/main/java/com/upsaude/mapper/financeiro/PagamentoPagarMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/RecorrenciaFinanceiraMapper.java`](src/main/java/com/upsaude/mapper/financeiro/RecorrenciaFinanceiraMapper.java)
- **Lançamentos contábeis (partidas)**
  - [`src/main/java/com/upsaude/mapper/financeiro/LancamentoFinanceiroMapper.java`](src/main/java/com/upsaude/mapper/financeiro/LancamentoFinanceiroMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/LancamentoFinanceiroItemMapper.java`](src/main/java/com/upsaude/mapper/financeiro/LancamentoFinanceiroItemMapper.java)
- **Orçamento municipal / GAA / estornos**
  - [`src/main/java/com/upsaude/mapper/financeiro/OrcamentoCompetenciaMapper.java`](src/main/java/com/upsaude/mapper/financeiro/OrcamentoCompetenciaMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/CreditoOrcamentarioMapper.java`](src/main/java/com/upsaude/mapper/financeiro/CreditoOrcamentarioMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/ReservaOrcamentariaAssistencialMapper.java`](src/main/java/com/upsaude/mapper/financeiro/ReservaOrcamentariaAssistencialMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/GuiaAtendimentoAmbulatorialMapper.java`](src/main/java/com/upsaude/mapper/financeiro/GuiaAtendimentoAmbulatorialMapper.java)
  - [`src/main/java/com/upsaude/mapper/financeiro/EstornoFinanceiroMapper.java`](src/main/java/com/upsaude/mapper/financeiro/EstornoFinanceiroMapper.java)
- **Auditoria**
  - [`src/main/java/com/upsaude/mapper/financeiro/LogFinanceiroMapper.java`](src/main/java/com/upsaude/mapper/financeiro/LogFinanceiroMapper.java)

### Faturamento (3 entidades)

Criar mappers em `com.upsaude.mapper.faturamento`:

- [`src/main/java/com/upsaude/mapper/faturamento/DocumentoFaturamentoMapper.java`](src/main/java/com/upsaude/mapper/faturamento/DocumentoFaturamentoMapper.java)
- [`src/main/java/com/upsaude/mapper/faturamento/DocumentoFaturamentoItemMapper.java`](src/main/java/com/upsaude/mapper/faturamento/DocumentoFaturamentoItemMapper.java)
- [`src/main/java/com/upsaude/mapper/faturamento/GlosaMapper.java`](src/main/java/com/upsaude/mapper/faturamento/GlosaMapper.java)

### Clínica (entidade nova)

Criar mapper em `com.upsaude.mapper.clinica.atendimento`:

- [`src/main/java/com/upsaude/mapper/clinica/atendimento/AtendimentoProcedimentoMapper.java`](src/main/java/com/upsaude/mapper/clinica/atendimento/AtendimentoProcedimentoMapper.java)

## Ajustes obrigatórios em mappers existentes

### 1) `AgendamentoMapper`

Arquivo: [`src/main/java/com/upsaude/mapper/agendamento/AgendamentoMapper.java`](src/main/java/com/upsaude/mapper/agendamento/AgendamentoMapper.java)

- **fromRequest/updateFromRequest**
  - Adicionar `@Mapping(target = "competenciaFinanceira", ignore = true)` (Request tem UUID; entidade é relação)
  - Garantir mapeamento de `valorEstimadoTotal` e `statusFinanceiro` (tipos compatíveis → MapStruct mapeia automaticamente; manter explícito se preferir)
- **toResponse**
  - Mapear `competenciaFinanceira` para `CompetenciaFinanceiraResponse` usando `CompetenciaFinanceiraMapper` (adicionar em `uses = { ... }`).
- **Método novo sugerido**
  - `AgendamentoSimplificadoResponse toSimplifiedResponse(Agendamento entity)` para reuso em faturamento/financeiro.

### 2) `AtendimentoMapper`

Arquivo: [`src/main/java/com/upsaude/mapper/clinica/atendimento/AtendimentoMapper.java`](src/main/java/com/upsaude/mapper/clinica/atendimento/AtendimentoMapper.java)

- **fromRequest/updateFromRequest**
  - Adicionar `@Mapping(target = "competenciaFinanceira", ignore = true)`
  - Adicionar `@Mapping(target = "procedimentos", ignore = true)` (criação/atualização dos filhos deve ser no service)
- **toResponse**
  - Mapear `competenciaFinanceira` (entity) → `CompetenciaFinanceiraResponse` via `CompetenciaFinanceiraMapper`.
  - Mapear `procedimentos` (entity list) → `List<AtendimentoProcedimentoResponse>` via `AtendimentoProcedimentoMapper`.
- **Método novo sugerido**
  - `AtendimentoSimplificadoResponse toSimplifiedResponse(Atendimento entity)` mapeando:
    - `dataHora` ← `entity.informacoes.dataHora`
    - `statusAtendimento` ← `entity.informacoes.statusAtendimento`

## Reusos/Helpers recomendados (evitar duplicação)

### 1) Simplificado de SIGTAP Procedimento

Arquivo existente: [`src/main/java/com/upsaude/mapper/sigtap/SigtapProcedimentoMapper.java`](src/main/java/com/upsaude/mapper/sigtap/SigtapProcedimentoMapper.java)

- Adicionar método: `ProcedimentoSigtapSimplificadoResponse toSimplifiedResponse(SigtapProcedimento entity)`.
- Assim, `DocumentoFaturamentoItemMapper` e `AtendimentoProcedimentoMapper` podem usar o método automaticamente.

### 2) Simplificado de Convênio

Opções (preferível centralizar):

- Adicionar em [`src/main/java/com/upsaude/mapper/convenio/ConvenioMapper.java`](src/main/java/com/upsaude/mapper/convenio/ConvenioMapper.java) um método `ConvenioSimplificadoResponse toSimplifiedResponse(Convenio entity)` reaproveitando a lógica de `tenantId/estabelecimentoId`.

## Diagrama de dependências (alto nível)

```mermaid
flowchart TD
  DocumentoFaturamentoMapper-->CompetenciaFinanceiraMapper
  DocumentoFaturamentoMapper-->ConvenioMapper
  DocumentoFaturamentoMapper-->AgendamentoMapper
  DocumentoFaturamentoMapper-->AtendimentoMapper
  DocumentoFaturamentoMapper-->GuiaAtendimentoAmbulatorialMapper
  DocumentoFaturamentoMapper-->DocumentoFaturamentoItemMapper
  DocumentoFaturamentoItemMapper-->SigtapProcedimentoMapper

  LancamentoFinanceiroMapper-->CompetenciaFinanceiraMapper
  LancamentoFinanceiroMapper-->DocumentoFaturamentoMapper
  LancamentoFinanceiroMapper-->TituloReceberMapper
  LancamentoFinanceiroMapper-->TituloPagarMapper
  LancamentoFinanceiroMapper-->LancamentoFinanceiroItemMapper

  AtendimentoMapper-->CompetenciaFinanceiraMapper
  AtendimentoMapper-->AtendimentoProcedimentoMapper
```

## Validação

- Rodar compilação para garantir geração do MapStruct e ausência de erros de tipos:
  - `mvn -DskipTests compile`