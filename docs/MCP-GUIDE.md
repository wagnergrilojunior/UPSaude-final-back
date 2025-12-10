# 🚀 UPSaude — MCP Development Guide

Guia oficial para criação, execução e manutenção do MCP (Model Context Protocol) usado no Cursor

Este documento explica:

* O objetivo de cada arquivo MCP
* A estrutura recomendada
* Como rodar o MCP
* Como criar novos comandos
* Como organizar validadores, analisadores e geradores
* Como seguir as boas práticas do projeto

---

# 📌 1. Objetivo do MCP no Projeto UPSaude

O MCP funciona como **um motor inteligente de automação**, que permite:

* Validar entidades, requests, responses e services
* Corrigir automaticamente classes fora do padrão
* Gerar novas entidades completas com mapper, service, controller, etc.
* Ler regras (YAML) e garantir que o código siga todos os padrões
* Executar análises, limpeza e melhorias estruturais
* Aumentar produtividade em dezenas de vezes

Com ele, você cria:

```
/mcp fix_entity_standard entityName="Paciente"
```

E ele ajusta **toda a classe**, garantindo consistência com as regras do projeto.

---

# 📌 2. Estrutura Recomendada de Arquivos MCP

Recomenda-se a seguinte estrutura:

```
.cursor/
  mcp/
    index.ts
    utils/
    validators/
    fixers/
    analyzers/
    generators/
    loaders/
  rules/
    master-rules.yaml
    architecture-rules.yaml
    domain-rules.yaml
    coding-style.yaml
    validation-rules.yaml
    patterns.yaml
    mapper-patterns.yaml
    service-patterns.yaml
    controller-patterns.yaml
    cursor-rules.yaml
```

Essa estrutura:

* Mantém organização corporativa
* Permite extensão sem bagunça
* Separa responsabilidades de forma modular

---

# 📌 3. Objetivo de Cada Arquivo MCP

## 🧩 **index.ts**

Arquivo principal que:

* Inicializa o servidor MCP
* Registra todas as ferramentas (tools)
* Carrega regras YAML
* Roteia chamadas para handlers corretos
* Funciona como gateway entre Cursor ↔ Projeto

Sem esse arquivo, nada funciona.

---

# 📂 4. Pastas Internas do MCP

---

## 📁 utils/

Funções auxiliares usadas globalmente.

| Arquivo         | Objetivo                                        |
| --------------- | ----------------------------------------------- |
| `fileUtils.ts`  | Lê/escreve arquivos, cria diretórios            |
| `pathUtils.ts`  | Resolve pacotes Java, caminhos de entidade, etc |
| `javaParser.ts` | Lê classes Java para extrair campos / anotações |
| `javaWriter.ts` | Reescreve classes mantendo formatação e imports |

---

## 📁 validators/

Usados para verificar se o código segue as regras YAML.

| Arquivo                  | Objetivo                                             |
| ------------------------ | ---------------------------------------------------- |
| `entityValidator.ts`     | Valida entidades conforme regras mestres e domínio   |
| `requestValidator.ts`    | Valida Requests (Bean Validation, embeddables, etc)  |
| `responseValidator.ts`   | Verifica Responses (nunca expor entity, etc)         |
| `mapperValidator.ts`     | Confere ignorados, métodos obrigatórios, embeddables |
| `serviceValidator.ts`    | Valida padrão do service e serviceImpl               |
| `controllerValidator.ts` | Verifica endpoints, ResponseEntity, pageable…        |
| `repositoryValidator.ts` | Checa duplicidade, métodos permitidos                |

---

## 📁 fixers/

Corrigem automaticamente o código.

| Arquivo              | Objetivo                                              |
| -------------------- | ----------------------------------------------------- |
| `entityFixer.ts`     | Corrige anotação, tabela, tamanho, validação, imports |
| `mapperFixer.ts`     | Recria mappers perdidos ou incompletos                |
| `serviceFixer.ts`    | Conserta assinatura, duplicidade, regras…             |
| `controllerFixer.ts` | Ajusta endpoints, retornos, RequestMapping            |
| `repositoryFixer.ts` | Padroniza métodos e estrutura                         |

---

## 📁 analyzers/

Geração de relatórios de análise.

| Arquivo                   | Objetivo                                          |
| ------------------------- | ------------------------------------------------- |
| `entityAnalyzer.ts`       | Analisa entidade campo por campo e gera relatório |
| `projectAnalyzer.ts`      | Analisa o projeto inteiro                         |
| `serviceAnalyzer.ts`      | Detecta inconsistências nos services              |
| `repositoryAnalyzer.ts`   | Identifica falhas e duplicidades                  |
| `architectureAnalyzer.ts` | Valida camadas conforme YAML                      |
| `complexityAnalyzer.ts`   | Mede complexidade e profundidade                  |

---

## 📁 loaders/

Leitura dos YAML da pasta `/rules`.

| Arquivo                       | Objetivo                         |
| ----------------------------- | -------------------------------- |
| `rulesLoader.ts`              | Carrega TODOS os YAML em memória |
| `domainRulesLoader.ts`        | Regras de domínio                |
| `architectureRulesLoader.ts`  | Regras de arquitetura            |
| `validationRulesLoader.ts`    | Validação                        |
| `mapperPatternsLoader.ts`     | Mappers                          |
| `servicePatternsLoader.ts`    | Services                         |
| `controllerPatternsLoader.ts` | Controllers                      |

---

## 📁 generators/

Geração automática de classes.

| Arquivo                   | Objetivo                          |
| ------------------------- | --------------------------------- |
| `entityGenerator.ts`      | Cria entidade completa            |
| `requestGenerator.ts`     | Request com validações            |
| `responseGenerator.ts`    | Response seguindo regras          |
| `dtoGenerator.ts`         | DTO completo                      |
| `mapperGenerator.ts`      | Mapper usando MapStruct           |
| `repositoryGenerator.ts`  | Cria o repositório                |
| `serviceGenerator.ts`     | Interface Service                 |
| `serviceImplGenerator.ts` | Implementação com regras corretas |
| `controllerGenerator.ts`  | Controller REST padronizado       |

---

# 📌 5. Como Rodar o MCP

## ✔ Ver todas as ferramentas disponíveis:

```
/mcp tools
```

---

## ✔ Validar uma entidade:

```
/mcp validate_entity_standard entityName="Paciente"
```

---

## ✔ Corrigir automaticamente uma entidade:

```
/mcp fix_entity_standard entityName="Paciente"
```

---

## ✔ Gerar ENTIDADE COMPLETA:

```
/mcp generate_entity_artifacts entityName="Doenca"
```

Gera:

* Doenca.java
* DoencaRequest.java
* DoencaResponse.java
* DoencaDTO.java
* DoencaMapper.java
* DoencaRepository.java
* DoencaService.java
* DoencaServiceImpl.java
* DoencaController.java

---

## ✔ Sincronizar arquivos de uma entidade:

```
/mcp sync_entity_changes entityName="Alergia"
```

Mostra todas as versões para ajudar a corrigir.

---

## ✔ Analisar o projeto inteiro:

```
/mcp analyze_project
```

---

# 📌 6. Criar um Novo Comando MCP

No `index.ts`:

```ts
server.registerTool({
  name: "meu_comando",
  description: "Descrição",
  inputSchema: { type: "object", properties: { campo: { type: "string" } } },
  execute: async (args) => {
    return "Resultado";
  }
});
```

---

# 📌 7. Uso Diário (Recomendações)

* Sempre valide antes de corrigir
* Sempre commit antes de rodar `fix_entity_standard`
* Mantenha regras YAML atualizadas
* Gere entidades novas SOMENTE via MCP
* O MCP vira seu "Arquiteto Sênior" automatizado

---

# 📌 8. Conclusão

Com esse sistema:

* Todas as camadas ficam padronizadas
* Desenvolvimento fica 70% mais rápido
* Correções são automáticas
* Você alcança padrão **enterprise**
* Todo o backend segue arquitetura limpa, padronizada e viva
