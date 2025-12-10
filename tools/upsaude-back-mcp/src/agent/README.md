# 🤖 Agente de IA Personalizado para UPSaude MCP

## Visão Geral

O Agente de IA Personalizado é um sistema inteligente que utiliza as ferramentas do MCP de forma autônoma para alcançar objetivos complexos no projeto UPSaude. Ele é capaz de:

- **Planejar ações** baseadas em objetivos descritos em linguagem natural
- **Executar sequências** de ferramentas de forma inteligente
- **Aprender com contexto** e manter memória de ações
- **Sugerir melhorias** baseadas no estado atual do projeto
- **Gerenciar dependências** entre ações

## Ferramentas do Agente

### 1. `agent_achieve_goal`

Alcança objetivos complexos através de planejamento e execução automática.

**Parâmetros:**
- `goal` (string, obrigatório): Descrição do objetivo em linguagem natural
- `priority` (enum, opcional): Prioridade do objetivo (`low`, `medium`, `high`, `critical`)

**Exemplos de uso:**

```
agent_achieve_goal({
  goal: "Analisar e corrigir problemas na entidade Medicacao",
  priority: "high"
})

agent_achieve_goal({
  goal: "Criar módulo completo para a entidade Paciente"
})

agent_achieve_goal({
  goal: "Executar auditoria completa do projeto"
})
```

### 2. `agent_suggest_improvements`

Obtém sugestões inteligentes de melhorias baseadas no contexto atual.

**Parâmetros:**
- `context` (string, opcional): Contexto adicional para as sugestões

**Exemplo:**

```
agent_suggest_improvements({
  context: "Estou trabalhando na entidade Medicacao"
})
```

### 3. `agent_get_context`

Obtém o contexto atual do agente, incluindo ações recentes e estado do projeto.

**Exemplo:**

```
agent_get_context({})
```

## Como o Agente Funciona

### 1. Planejamento Inteligente

Quando você fornece um objetivo, o agente:

1. **Analisa o objetivo** usando padrões de linguagem natural
2. **Identifica entidades** mencionadas
3. **Seleciona ferramentas** apropriadas do MCP
4. **Cria um plano** com sequência de ações
5. **Gerencia dependências** entre ações

### 2. Execução Automática

O agente executa o plano:

1. **Executa cada passo** na ordem correta
2. **Respeita dependências** entre ações
3. **Coleta resultados** de cada execução
4. **Atualiza contexto** com informações aprendidas
5. **Gera relatório** final detalhado

### 3. Aprendizado e Memória

O agente mantém:

- **Histórico de ações** recentes
- **Estado do projeto** (health score, issues)
- **Entidade atual** sendo trabalhada
- **Memória persistente** de informações importantes

## Exemplos de Objetivos

### Análise e Correção

```
"Analisar a entidade Paciente e corrigir todos os problemas encontrados"
```

O agente irá:
1. Executar `analyze_entity` para Paciente
2. Executar `fix_entity` para corrigir problemas
3. Gerar relatório completo

### Criação de Módulo

```
"Criar módulo completo para a entidade Alergia"
```

O agente irá:
1. Executar `create_module` para Alergia
2. Gerar todos os artefatos necessários

### Auditoria Completa

```
"Executar auditoria completa do projeto e corrigir problemas críticos"
```

O agente irá:
1. Executar `full_audit`
2. Identificar problemas críticos
3. Executar correções automáticas quando possível

## Integração com Cursor

O agente está totalmente integrado ao MCP e pode ser usado diretamente no Cursor:

1. **Através do chat**: Descreva o objetivo em linguagem natural
2. **Através de comandos**: Use `/mcp agent_achieve_goal`
3. **Através de código**: Chame as ferramentas programaticamente

## Arquitetura

```
UpSaudeAgent
├── Planning Engine (plan)
│   ├── Pattern Recognition
│   ├── Tool Selection
│   └── Dependency Management
├── Execution Engine (executePlan)
│   ├── Step Execution
│   ├── Error Handling
│   └── Result Collection
├── Context Manager
│   ├── Project Health
│   ├── Recent Actions
│   └── Current Entity
└── Memory System
    ├── Short-term Memory
    └── Long-term Memory
```

## Extensibilidade

O agente pode ser estendido para:

- **Novos padrões de objetivos**: Adicione novos padrões em `plan()`
- **Novas estratégias**: Implemente diferentes estratégias de planejamento
- **Aprendizado de máquina**: Integre modelos para melhorar decisões
- **Integração com outros sistemas**: Conecte com outros agentes ou ferramentas

## Boas Práticas

1. **Seja específico**: Objetivos claros geram melhores planos
2. **Use prioridades**: Priorize objetivos críticos
3. **Revise sugestões**: Use `agent_suggest_improvements` regularmente
4. **Monitore contexto**: Use `agent_get_context` para entender o estado atual

## Troubleshooting

### O agente não está executando ações esperadas

- Verifique se o objetivo está claro e específico
- Use `agent_get_context` para ver o estado atual
- Tente dividir objetivos complexos em menores

### O agente está executando ações desnecessárias

- Seja mais específico no objetivo
- Use `priority: "high"` apenas para ações críticas
- Revise o plano antes de executar (futura feature)

## Roadmap

- [ ] Modo interativo para aprovar planos antes de executar
- [ ] Integração com LLM para melhor compreensão de objetivos
- [ ] Sistema de aprendizado baseado em feedback
- [ ] Cache de planos para objetivos similares
- [ ] Métricas de sucesso e otimização de planos
