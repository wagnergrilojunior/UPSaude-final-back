# upsaude-back-mcp

Servidor MCP (Model Context Protocol) para o projeto UPSaude Backend com **Agente de IA Personalizado**.

## 🚀 Recursos Principais

- ✅ **Análise Automática** de entidades, mappers, services e controllers
- ✅ **Correção Automática** de problemas arquiteturais e de padrões
- ✅ **Geração Automática** de módulos completos
- ✅ **Auditoria Completa** do projeto
- ✅ **🤖 Agente de IA Personalizado** para objetivos complexos

## 📦 Instalação

```bash
npm install
```

## 🔨 Build

```bash
npm run build
```

## 💻 Desenvolvimento

```bash
npm run dev
```

## 🎯 Uso

```bash
npm start
```

## 🤖 Agente de IA Personalizado

O agente permite alcançar objetivos complexos através de comandos em linguagem natural:

### Exemplos Rápidos

**No Cursor, você pode simplesmente dizer:**

```
Analise a entidade Medicacao e corrija todos os problemas encontrados
```

```
Crie módulo completo para a entidade Paciente
```

```
Execute auditoria completa do projeto
```

### Ferramentas do Agente

1. **`agent_achieve_goal`** - Alcança objetivos complexos automaticamente
2. **`agent_suggest_improvements`** - Obtém sugestões inteligentes
3. **`agent_get_context`** - Visualiza contexto atual do agente

📖 Veja a [documentação completa do agente](./AGENT_GUIDE.md) para mais detalhes.

## 🛠️ Ferramentas Disponíveis

### Análise
- `analyze_entity` - Analisa uma entidade específica
- `analyze_project` - Analisa o projeto completo
- `analyze_project_health` - Analisa saúde do projeto
- `full_audit` - Auditoria completa
- `mapper_audit` - Auditoria de mappers
- `validate_project` - Validação do projeto

### Correção
- `fix_entity` - Corrige problemas em uma entidade
- `fix_project_structure` - Corrige estrutura do projeto
- `auto_refactor` - Refatoração automática de uma entidade
- `auto_fix_all` - Correção automática completa

### Geração
- `create_module` - Cria módulo completo
- `generate_full_module` - Gera módulo completo com todos os artefatos

### Agente de IA
- `agent_achieve_goal` - Alcança objetivos complexos
- `agent_suggest_improvements` - Sugestões inteligentes
- `agent_get_context` - Contexto do agente

## 📁 Estrutura

```
src/
├── agent/              # 🤖 Agente de IA Personalizado
│   ├── upsaudeAgent.ts    # Classe principal do agente
│   ├── agentTool.ts       # Tools MCP do agente
│   └── README.md          # Documentação do agente
├── analyzers/          # Analisadores de código
├── config/             # Configurações e registros
├── core/               # Utilitários centrais
├── fixers/             # Corretores automáticos
├── generators/         # Geradores de código
├── rules/              # Sistema de regras YAML
└── tools/              # Definições de tools MCP
```

## 📚 Documentação

- [Guia do Agente](./AGENT_GUIDE.md) - Guia completo do agente de IA
- [Exemplos de Uso](./examples/agent-examples.md) - Exemplos práticos
- [Documentação Técnica do Agente](./src/agent/README.md) - Documentação técnica

## 🔧 Adicionando Tools

Para adicionar novas tools:

1. Crie o arquivo em `src/tools/nomeTool.tool.ts`
2. Exporte a definição da tool
3. Registre no `src/config/toolRegistry.ts`

Exemplo:

```typescript
export const minhaToolDefinition = {
    name: "minha_tool",
    description: "Descrição da tool",
    inputSchema: z.object({ ... }),
    handler: async (projectRoot: string, args: unknown) => {
        // Implementação
    }
};
```

## 🎓 Exemplos de Uso do Agente

### Análise e Correção
```
Analise a entidade Medicacao e corrija todos os problemas
```

### Criação de Módulo
```
Crie módulo completo para a entidade Vacina
```

### Auditoria
```
Execute auditoria completa do projeto com prioridade alta
```

Veja mais exemplos em [examples/agent-examples.md](./examples/agent-examples.md)

## 🤝 Contribuindo

1. Adicione novas ferramentas conforme necessário
2. Melhore os padrões de reconhecimento do agente
3. Adicione novos casos de uso
4. Documente novas funcionalidades

## 📝 Licença

ISC
