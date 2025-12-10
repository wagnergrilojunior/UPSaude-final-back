# 🤖 Guia do Agente de IA Personalizado

## Introdução

O Agente de IA Personalizado é uma camada inteligente sobre o MCP UPSaude que permite alcançar objetivos complexos através de comandos em linguagem natural.

## Início Rápido

### Exemplo Básico

No Cursor, você pode simplesmente dizer:

```
Analise a entidade Medicacao e corrija todos os problemas encontrados
```

O agente irá:
1. Analisar a entidade Medicacao
2. Identificar problemas
3. Corrigir automaticamente
4. Gerar relatório completo

### Exemplo Avançado

```
Crie um módulo completo para a entidade Paciente incluindo todas as camadas
```

O agente irá criar:
- Entity
- Request/Response
- DTO
- Mapper
- Repository
- Service/ServiceImpl
- Controller

## Casos de Uso Comuns

### 1. Análise e Correção de Entidade

**Objetivo:**
```
Analisar e corrigir problemas na entidade Medicacao
```

**O que o agente faz:**
- Executa análise completa da entidade
- Identifica problemas de arquitetura, validação e padrões
- Aplica correções automáticas
- Gera relatório detalhado

### 2. Auditoria Completa

**Objetivo:**
```
Executar auditoria completa do projeto
```

**O que o agente faz:**
- Analisa todas as entidades
- Analisa todos os mappers
- Analisa todos os services
- Analisa todos os controllers
- Gera relatório consolidado

### 3. Criação de Módulo

**Objetivo:**
```
Criar módulo completo para Alergia
```

**O que o agente faz:**
- Gera todos os artefatos necessários
- Segue padrões do projeto
- Aplica validações corretas
- Cria estrutura completa

### 4. Refatoração Automática

**Objetivo:**
```
Refatorar automaticamente todo o projeto
```

**O que o agente faz:**
- Identifica problemas em todas as camadas
- Aplica correções automáticas
- Mantém consistência arquitetural
- Gera relatório de mudanças

## Padrões de Linguagem Reconhecidos

O agente reconhece os seguintes padrões:

### Análise
- "analisar [entidade]"
- "analise [entidade]"
- "verificar [entidade]"
- "auditar [entidade]"

### Correção
- "corrigir [entidade]"
- "corrija [entidade]"
- "fix [entidade]"
- "melhorar [entidade]"

### Criação
- "criar módulo para [entidade]"
- "gerar módulo para [entidade]"
- "create module for [entidade]"

### Auditoria
- "auditar tudo"
- "auditoria completa"
- "full audit"

### Refatoração
- "refatorar tudo"
- "corrigir automaticamente"
- "auto fix all"

## Prioridades

Você pode especificar prioridades:

- **low**: Ações não críticas, podem ser executadas em background
- **medium**: Ações normais (padrão)
- **high**: Ações importantes, devem ser executadas rapidamente
- **critical**: Ações críticas, máxima prioridade

**Exemplo:**
```
Analisar e corrigir problemas críticos na entidade Medicacao (prioridade: critical)
```

## Sugestões Inteligentes

Use `agent_suggest_improvements` para obter recomendações:

```
Obtenha sugestões de melhorias para o projeto
```

O agente analisa:
- Estado atual do projeto
- Ações recentes
- Entidades sendo trabalhadas
- Problemas conhecidos

## Contexto do Agente

O agente mantém contexto sobre:

- **Projeto atual**: Health score, issues, warnings
- **Entidade atual**: Última entidade analisada/trabalhada
- **Ações recentes**: Histórico das últimas 10 ações
- **Memória**: Informações importantes aprendidas

Para ver o contexto atual:

```
Mostre o contexto atual do agente
```

## Exemplos Práticos

### Exemplo 1: Workflow Completo

```
1. Analise a entidade Medicacao
2. Corrija todos os problemas encontrados
3. Execute auditoria completa para verificar
```

### Exemplo 2: Criação Rápida

```
Crie módulo completo para Vacina incluindo todas as validações
```

### Exemplo 3: Manutenção Preventiva

```
Execute auditoria completa do projeto e sugira melhorias
```

## Dicas e Truques

1. **Seja específico**: Quanto mais específico o objetivo, melhor o plano
2. **Use nomes de entidades**: Sempre mencione o nome da entidade quando relevante
3. **Combine objetivos**: Você pode descrever múltiplos objetivos em uma frase
4. **Revise sugestões**: Use sugestões antes de executar ações grandes
5. **Monitore contexto**: Verifique o contexto antes de ações importantes

## Limitações Atuais

- O agente não pode executar ações que requerem aprovação manual
- Alguns objetivos muito complexos podem precisar ser divididos
- O agente não aprende com feedback ainda (em desenvolvimento)

## Próximos Passos

- Experimente com diferentes objetivos
- Use sugestões para descobrir melhorias
- Monitore o contexto para entender o estado do projeto
- Compartilhe feedback para melhorias futuras

## Suporte

Para problemas ou sugestões:
1. Verifique o contexto atual do agente
2. Revise os logs do MCP
3. Tente dividir objetivos complexos em menores
4. Use ferramentas individuais se o agente não conseguir
