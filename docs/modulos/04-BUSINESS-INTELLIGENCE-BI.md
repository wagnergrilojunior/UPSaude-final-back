# Módulo: Business Intelligence (BI) e Dashboards

## 📋 Visão Geral (Para Product Owner)

O módulo de Business Intelligence fornece dashboards interativos e relatórios analíticos para apoiar a tomada de decisão estratégica na gestão de saúde. Permite visualizar dados de forma centralizada, identificar tendências e monitorar indicadores de desempenho em tempo real.

### Objetivo de Negócio
- Fornecer visão estratégica dos dados do sistema
- Facilitar tomada de decisão baseada em dados
- Monitorar indicadores de desempenho (KPIs)
- Identificar tendências e padrões
- Gerar relatórios executivos

### Benefícios
- Decisões mais rápidas e informadas
- Identificação proativa de problemas
- Otimização de recursos
- Melhoria contínua dos processos
- Transparência na gestão

---

## 🎯 Funcionalidades Necessárias

### 1. Dashboards Principais

#### 1.1 Dashboard de Agendamentos e Atendimentos
- **Descrição**: Visualização centralizada das estatísticas de marcação e atendimento
- **Métricas**:
  - Consultas agendadas por período
  - Taxa de comparecimento
  - Consultas realizadas vs. agendadas
  - Tempo médio de espera
  - Taxa de cancelamento
  - Consultas por especialidade
  - Consultas por profissional
- **Visualizações**:
  - Gráficos de linha (tendência temporal)
  - Gráficos de barras (comparação)
  - Gráficos de pizza (distribuição)
  - Tabelas interativas
  - Indicadores (cards)

#### 1.2 Dashboard de Estoque
- **Descrição**: Visualização centralizada do consumo dos estoques
- **Métricas**:
  - Consumo por unidade
  - Itens em estoque mínimo
  - Itens vencidos ou próximos ao vencimento
  - Movimentações por período
  - Custo de estoque
  - Taxa de rotatividade
- **Visualizações**:
  - Gráficos de consumo
  - Alertas visuais
  - Tabelas de estoque crítico

#### 1.3 Dashboard de Procedimentos Faturados
- **Descrição**: Visualização dos procedimentos faturados por unidade
- **Métricas**:
  - Quantidade de procedimentos
  - Valor faturado
  - Por unidade
  - Por especialidade
  - Por período
  - Comparativo período anterior
- **Visualizações**:
  - Gráficos de barras comparativos
  - Gráficos de linha temporal
  - Tabelas detalhadas

#### 1.4 Dashboard de Leitos
- **Descrição**: Situação geral de leitos do município
- **Métricas**:
  - Taxa de ocupação
  - Leitos disponíveis/ocupados
  - Por setor
  - Por tipo de acomodação
  - Tempo médio de permanência
  - Taxa de rotatividade
- **Visualizações**:
  - Mapa de calor dos leitos
  - Gráficos de ocupação
  - Indicadores em tempo real

#### 1.5 Dashboard de Urgência/Emergência
- **Descrição**: Utilização das unidades de urgência/emergência por horário
- **Métricas**:
  - Atendimentos por hora do dia
  - Atendimentos por dia da semana
  - Classificação de risco
  - Tempo médio de atendimento
  - Taxa de ocupação por horário
- **Visualizações**:
  - Gráficos de calor (heatmap)
  - Gráficos de linha temporal
  - Distribuição por classificação

#### 1.6 Dashboard de Tempo de Espera
- **Descrição**: Tempo de espera de pacientes
- **Métricas**:
  - Tempo médio de espera
  - Por unidade
  - Por especialidade
  - Por classificação de risco
  - Pacientes aguardando (atual)
- **Visualizações**:
  - Gráficos de barras
  - Indicadores em tempo real
  - Alertas quando acima do esperado

#### 1.7 Dashboard de Regulação
- **Descrição**: Dados da regulação, valores contratados por prestador
- **Métricas**:
  - Autorizações emitidas
  - Utilização de cotas
  - Valores contratados vs. executados
  - Por prestador
  - Fila de espera
- **Visualizações**:
  - Gráficos comparativos
  - Tabelas de prestadores
  - Indicadores de utilização

### 2. Funcionalidades de Visualização

#### 2.1 Análise Dinâmica
- **Descrição**: Interação com dados através de cliques do mouse
- **Funcionalidades**:
  - Drill-down (aprofundar em dados)
  - Drill-up (voltar ao nível superior)
  - Filtros interativos
  - Tooltips informativos
  - Zoom em gráficos

#### 2.2 Ordenação e Filtros
- **Descrição**: Ordenação automática e filtro dos dados das colunas
- **Funcionalidades**:
  - Ordenar por qualquer coluna
  - Filtros múltiplos
  - Busca em tabelas
  - Filtros por período
  - Filtros por unidade/profissional

#### 2.3 Responsividade
- **Descrição**: Funcionamento em dispositivos mobile
- **Funcionalidades**:
  - Layout adaptativo
  - Gráficos responsivos
  - Navegação touch-friendly
  - Visualização otimizada para telas pequenas

#### 2.4 Impressão
- **Descrição**: Capacidade de impressão dos gráficos visíveis
- **Funcionalidades**:
  - Impressão de dashboards completos
  - Impressão de gráficos individuais
  - Exportação em PDF
  - Configuração de layout de impressão

### 3. Criação de Dashboards

#### 3.1 Estrutura de Menus
- **Descrição**: Acesso aos dashboards através de estrutura de menus
- **Funcionalidades**:
  - Menu hierárquico
  - Favoritos
  - Busca de dashboards
  - Agrupamento por categoria

#### 3.2 Criação de Dashboards Customizados
- **Descrição**: Permitir criar dashboards com diferentes visualizações
- **Tipos de visualização**:
  - Tabulações (tabelas)
  - Gráficos (barras, linhas, pizza, etc.)
  - Relatórios
  - Mapas interativos
  - Indicadores (KPIs)
- **Funcionalidades**:
  - Editor visual de dashboards
  - Arrastar e soltar componentes
  - Configuração de métricas
  - Salvamento de templates

### 4. Mapas Interativos
- **Descrição**: Visualização de dados em mapas geográficos
- **Funcionalidades**:
  - Mapa de calor por região
  - Distribuição de unidades
  - Cobertura de atendimento
  - Análise geográfica de demandas

---

## 📐 Arquitetura e Classes

### Entidades Principais

```java
// Dashboard.java
@Entity
@Table(name = "dashboards")
public class Dashboard extends BaseEntity {
    private String nome;
    private String descricao;
    private String categoria;
    private String icone;
    private Integer ordem;
    private String configuracao; // JSON com configuração do dashboard
    private Boolean publico; // se todos podem ver
    private Boolean ativo;
    
    @ManyToOne
    private UsuariosSistema criadoPor;
    
    @ManyToMany
    private List<Papeis> permissoesVisualizacao;
    
    @OneToMany(mappedBy = "dashboard")
    private List<ComponenteDashboard> componentes;
}

// ComponenteDashboard.java
@Entity
@Table(name = "componentes_dashboard")
public class ComponenteDashboard extends BaseEntity {
    @ManyToOne
    private Dashboard dashboard;
    
    private String tipo; // TABELA, GRAFICO_BARRA, GRAFICO_LINHA, GRAFICO_PIZZA, MAPA, INDICADOR
    private String titulo;
    private String configuracao; // JSON com configuração específica
    private Integer posicaoX;
    private Integer posicaoY;
    private Integer largura;
    private Integer altura;
    private Integer ordem;
    
    @ManyToOne
    private ConsultaBI consulta; // query ou métrica
}

// ConsultaBI.java
@Entity
@Table(name = "consultas_bi")
public class ConsultaBI extends BaseEntity {
    private String nome;
    private String descricao;
    private String tipo; // SQL, METRICA_PREDEFINIDA, API
    private String query; // SQL ou configuração
    private String parametros; // JSON com parâmetros
    private Integer tempoCacheSegundos; // cache de resultados
    private Boolean ativo;
}

// MetricaPredefinida.java
@Entity
@Table(name = "metricas_predefinidas")
public class MetricaPredefinida extends BaseEntityWithoutTenant {
    private String codigo;
    private String nome;
    private String descricao;
    private String categoria; // AGENDAMENTO, ESTOQUE, FATURAMENTO, etc.
    private String formula; // como calcular
    private String unidadeMedida; // %, quantidade, valor, etc.
    private String tipoVisualizacao; // INDICADOR, GRAFICO, TABELA
}

// FiltroDashboard.java
@Entity
@Table(name = "filtros_dashboard")
public class FiltroDashboard extends BaseEntity {
    @ManyToOne
    private Dashboard dashboard;
    
    private String nome;
    private String tipo; // PERIODO, UNIDADE, PROFISSIONAL, ESPECIALIDADE, etc.
    private String configuracao; // JSON
    private Boolean obrigatorio;
    private String valorPadrao;
}

// HistoricoAcessoDashboard.java
@Entity
@Table(name = "historico_acesso_dashboard")
public class HistoricoAcessoDashboard extends BaseEntity {
    @ManyToOne
    private Dashboard dashboard;
    
    @ManyToOne
    private UsuariosSistema usuario;
    
    private LocalDateTime dataAcesso;
    private Integer tempoVisualizacaoSegundos;
    private String acoesRealizadas; // JSON
}
```

### DTOs Principais

```java
// DashboardResponse.java
public class DashboardResponse {
    private UUID id;
    private String nome;
    private String descricao;
    private String categoria;
    private List<ComponenteDashboardResponse> componentes;
    private List<FiltroDashboardResponse> filtros;
}

// ComponenteDashboardResponse.java
public class ComponenteDashboardResponse {
    private UUID id;
    private String tipo;
    private String titulo;
    private Object dados; // dados renderizados
    private Map<String, Object> configuracao;
}

// MetricaResponse.java
public class MetricaResponse {
    private String codigo;
    private String nome;
    private Object valor;
    private String unidadeMedida;
    private String tendencia; // CRESCENTE, DECRESCENTE, ESTAVEL
    private BigDecimal variacaoPercentual;
}
```

### Services Necessários

```java
public interface DashboardService {
    DashboardResponse criar(DashboardRequest request);
    DashboardResponse obterPorId(UUID id);
    Page<DashboardResponse> listar(Pageable pageable, FiltroDashboard filtro);
    DashboardResponse atualizar(UUID id, DashboardRequest request);
    void deletar(UUID id);
    DashboardResponse executar(UUID id, Map<String, Object> filtros);
}

public interface ComponenteDashboardService {
    ComponenteDashboardResponse criar(UUID dashboardId, ComponenteDashboardRequest request);
    Object obterDados(UUID componenteId, Map<String, Object> filtros);
    void atualizarPosicao(UUID componenteId, Integer x, Integer y, Integer largura, Integer altura);
}

public interface ConsultaBIService {
    Object executarConsulta(UUID consultaId, Map<String, Object> parametros);
    Object executarSQL(String sql, Map<String, Object> parametros);
    MetricaResponse calcularMetrica(String codigoMetrica, Map<String, Object> parametros);
}

public interface RelatorioBIService {
    byte[] gerarRelatorioPDF(UUID dashboardId, Map<String, Object> filtros);
    byte[] gerarRelatorioExcel(UUID dashboardId, Map<String, Object> filtros);
    void agendarRelatorio(UUID dashboardId, String frequencia, String emailDestino);
}
```

---

## 🔄 Fluxo de Processo

### Fluxo de Visualização de Dashboard

```
1. Usuário acessa menu de dashboards
   ↓
2. Seleciona dashboard desejado
   ↓
3. Sistema carrega configuração do dashboard
   ↓
4. Para cada componente:
   - Executa consulta/métrica
   - Aplica filtros
   - Renderiza visualização
   ↓
5. Usuário interage com dados:
   - Aplica filtros
   - Faz drill-down
   - Ordena tabelas
   ↓
6. Sistema atualiza visualizações em tempo real
```

---

## 🔐 Regras de Negócio

### RB-001: Permissões de Acesso
- Usuário só pode ver dashboards para os quais tem permissão
- Dashboards públicos são visíveis para todos
- Permissões podem ser por papel ou individual

### RB-002: Performance
- Consultas pesadas devem usar cache
- Tempo máximo de execução: 30 segundos
- Se exceder, retornar erro ou usar dados em cache

### RB-003: Dados Sensíveis
- Dados de pacientes devem ser anonimizados em dashboards agregados
- Acesso a dados individuais requer permissão específica

### RB-004: Cache
- Resultados de consultas são cacheados por tempo configurável
- Cache é invalidado quando dados são atualizados
- Usuário pode forçar atualização

### RB-005: Exportação
- Exportação em PDF/Excel mantém formatação
- Dados exportados respeitam filtros aplicados
- Log de exportações para auditoria

---

## 📱 APIs REST Necessárias

### Endpoints de Dashboards
- `POST /api/v1/bi/dashboards` - Criar
- `GET /api/v1/bi/dashboards` - Listar
- `GET /api/v1/bi/dashboards/{id}` - Obter
- `PUT /api/v1/bi/dashboards/{id}` - Atualizar
- `DELETE /api/v1/bi/dashboards/{id}` - Deletar
- `POST /api/v1/bi/dashboards/{id}/executar` - Executar com filtros

### Endpoints de Componentes
- `POST /api/v1/bi/componentes` - Criar componente
- `GET /api/v1/bi/componentes/{id}/dados` - Obter dados do componente
- `PUT /api/v1/bi/componentes/{id}/posicao` - Atualizar posição

### Endpoints de Métricas
- `GET /api/v1/bi/metricas` - Listar métricas disponíveis
- `GET /api/v1/bi/metricas/{codigo}/calcular` - Calcular métrica

### Endpoints de Relatórios
- `GET /api/v1/bi/relatorios/dashboard/{id}/pdf` - Gerar PDF
- `GET /api/v1/bi/relatorios/dashboard/{id}/excel` - Gerar Excel

---

## 🔧 Tecnologias e Dependências

### Bibliotecas Sugeridas
- **Visualização**: Chart.js, D3.js, Apache ECharts, ou Plotly
- **Tabelas Interativas**: DataTables ou AG-Grid
- **Mapas**: Leaflet ou Google Maps API
- **Geração de PDF**: iText ou Apache PDFBox
- **Geração de Excel**: Apache POI
- **Cache**: Redis ou Caffeine
- **Agendamento**: Quartz Scheduler

### Arquitetura
- **Backend**: Spring Boot com endpoints REST
- **Frontend**: Framework JavaScript moderno (React, Vue, Angular)
- **Banco de Dados**: PostgreSQL (já existente)
- **Cache**: Redis (recomendado)

---

## 📊 Dashboards Predefinidos Sugeridos

### 1. Dashboard Executivo
- Visão geral de todos os indicadores principais
- Gráficos de tendência
- Alertas e notificações

### 2. Dashboard Operacional
- Foco em operações do dia a dia
- Situação atual de leitos, agendamentos
- Fila de espera

### 3. Dashboard Financeiro
- Faturamento
- Custos
- Análise de receitas e despesas

### 4. Dashboard Clínico
- Indicadores de qualidade
- Taxa de infecção
- Tempo de resposta
- Satisfação do paciente

---

## 🚀 Fases de Implementação

### Fase 1: Infraestrutura Base (2 semanas)
- Estrutura de dashboards e componentes
- Sistema de consultas
- Cache básico
- APIs REST

### Fase 2: Visualizações Básicas (2 semanas)
- Gráficos de barras, linhas, pizza
- Tabelas interativas
- Indicadores (KPIs)

### Fase 3: Dashboards Predefinidos (3 semanas)
- Dashboard de agendamentos
- Dashboard de estoque
- Dashboard de procedimentos
- Dashboard de leitos

### Fase 4: Funcionalidades Avançadas (2 semanas)
- Drill-down/drill-up
- Filtros interativos
- Mapas
- Editor de dashboards

### Fase 5: Responsividade e Exportação (1 semana)
- Layout responsivo
- Impressão
- Exportação PDF/Excel

### Fase 6: Otimização (1 semana)
- Performance
- Cache avançado
- Otimização de consultas

**Total estimado: 11 semanas**

---

## 📈 Métricas e KPIs Sugeridos

### KPIs de Agendamento
- Taxa de comparecimento
- Taxa de cancelamento
- Tempo médio de espera
- Consultas por profissional

### KPIs de Estoque
- Taxa de rotatividade
- Itens em estoque crítico
- Custo de estoque
- Itens vencidos

### KPIs de Faturamento
- Valor faturado por período
- Procedimentos realizados
- Comparativo período anterior

### KPIs de Leitos
- Taxa de ocupação
- Tempo médio de permanência
- Taxa de rotatividade

### KPIs de Regulação
- Utilização de cotas
- Tempo médio de autorização
- Fila de espera

