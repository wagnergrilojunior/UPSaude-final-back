# Guia de Negócio - SIGTAP

## 📋 Visão Geral

Este documento explica o **SIGTAP (Sistema de Gestão da Tabela Unificada de Procedimentos)** de forma simples e direta, para profissionais que não são técnicos mas precisam entender o que é e como funciona.

## 📋 O que é o SIGTAP?

O SIGTAP É o **cadastro oficial do Ministério da Saúde** que contém **todos os procedimentos médicos, medicamentos, equipamentos e materiais** que podem ser utilizados no Sistema único de Saúde (SUS).

### Analogia Simples

Imagine o SIGTAP como um **"catálogo gigante"** do SUS que contém:
- Todos os procedimentos médicos disponíveis (consultas, cirurgias, exames)
- Todos os medicamentos que podem ser prescritos
- Todas as regras sobre quando e como usar cada item
- Todas as informações sobre quem pode executar cada procedimento

## 📋 O que o SIGTAP Contém

### 1. Procedimentos Médicos

São os **serviços de saúde** que podem ser realizados, como:
- Consultas médicas
- Cirurgias
- Exames (sangue, imagem, etc.)
- Tratamentos
- Procedimentos odontológicos

**Exemplo**: O código `03.01.01.001-0` representa uma "CONSULTA MÉDICA EM ATENÇÃO BÁSICA".

### 2. Classificação Internacional de Doenças (CID)

é um sistema de **códigos para doenças e problemas de saúde**. Cada doença tem um código único.

**Exemplo**: O código `A00` representa "Cólera".

### 3. Ocupações Profissionais

São as **profissões** que podem executar determinados procedimentos:
- Médicos
- Enfermeiros
- Técnicos de enfermagem
- Fisioterapeutas
- Etc.

**Exemplo**: O código `225110` representa "Médico cardiologista".

### 4. Habilitações

São os **requisitos especiais** que um estabelecimento de saúde precisa ter para executar certos procedimentos:
- Equipamentos específicos
- Certificações
- Estrutura física adequada

**Exemplo**: Uma cirurgia cardíaca exige habilitação específica de centro cirúrgico cardíaco.

## 🔄 Como Funciona a Atualização?

### Competências

Os dados do SIGTAP são atualizados **mensalmente**. Cada atualização é chamada de **"competência"**.

- **Formato**: AAAAMM (Ano + Mês)
- **Exemplo**: `202512` = Dezembro de 2025

### O que Muda a Cada Competência?

A cada mês, o Ministério da Saúde pode:
- ✅ **Adicionar** novos procedimentos
- ✏️ **Alterar** informações de procedimentos existentes
- ❌ **Remover** procedimentos que não são mais utilizados
- 🔗 **Atualizar** relacionamentos entre procedimentos

## 💼 Por que o SIGTAP é Importante?

### Para o Sistema UpSaude

1. **Padronização**: Garante que estamos usando os códigos oficiais do SUS
2. **Conformidade**: Mantém o sistema alinhado com as regras do Ministério da Saúde
3. **Atualização**: Permite que o sistema tenha sempre os dados mais recentes
4. **Integração**: Facilita integração com outros sistemas do SUS

### Para os Usuários

1. **Busca de Procedimentos**: Permite buscar procedimentos pelos códigos oficiais
2. **Validação**: Garante que os procedimentos cadastrados são válidos
3. **Informações Completas**: Fornece todas as informações sobre cada procedimento
4. **Regras e Restrições**: Mostra quem pode executar e quando pode ser executado

## 📈 Dados Disponíveis no Sistema

### Estatísticas Atuais (Competência 202512)

- **Procedimentos**: 4.957 procedimentos cadastrados
- **CID**: 14.242 códigos de doenças
- **Ocupações**: 2.718 profissões relacionadas
- **Habilitações**: 339 tipos de habilitações
- **Relacionamentos**: Milhares de associações entre procedimentos

### Principais Categorias

1. **Tabelas Principais** (`tb_*`): Dados básicos (procedimentos, CID, ocupações)
2. **Relacionamentos** (`rl_*`): Associações entre dados (ex: quais CID podem ser usados com um procedimento)

## 🔍 Como Usar no Sistema?

### Buscar Procedimentos

Você pode buscar procedimentos por:
- **Código**: Ex: `03.01.01.001-0`
- **Nome**: Ex: "Consulta médica"
- **Categoria**: Ex: "Atenção Básica"

### Ver Informações

Para cada procedimento você pode ver:
- Nome completo
- Valores (hospitalar, ambulatorial, profissional)
- Restrições (idade, sexo)
- Quem pode executar (ocupações)
- Onde pode ser executado (habilitações)
- Compatibilidades com outros procedimentos

### Validar Procedimentos

O sistema usa o SIGTAP para:
- Validar se um código de procedimento existe
- Verificar se está ativo na competência atual
- Checar se pode ser usado em determinada situação

## ⚠️ Pontos de Atenção

### Competências

- ⚠️ **Sempre verifique a competência**: Um procedimento pode estar ativo em uma competência e inativo em outra
- ⚠️ **Dados históricos**: Procedimentos antigos podem não estar mais disponíveis

### Valores

- ⚠️ **Valores podem mudar**: Os valores dos procedimentos são atualizados mensalmente
- ⚠️ **Diferentes tipos**: Existem valores hospitalares, ambulatoriais e profissionais

### Restrições

- ⚠️ **Idade**: Alguns procedimentos têm restrições de idade mínima/máxima
- ⚠️ **Sexo**: Alguns procedimentos são específicos para homens ou mulheres
- ⚠️ **Habilitação**: Alguns procedimentos exigem habilitação especial do estabelecimento

## 📚 Próximos Passos

1. **Consulte o Glossário**: Veja o [Glossário](./GLOSSARIO.md) para entender termos técnicos
2. **Veja o Mapeamento**: Entenda a relação entre arquivos e tabelas no [Mapeamento](./MAPEAMENTO_ARQUIVOS_TABELAS.md)
3. **Explore a API**: Se precisar acessar programaticamente, veja a [Documentação da API](./API_REST.md)

## ❓ Perguntas Frequentes

### O que acontece se um procedimento não estiver no SIGTAP?

Se um procedimento não estiver cadastrado no SIGTAP, ele não pode ser utilizado oficialmente no SUS. O sistema valida isso automaticamente.

### Com que frequência os dados são atualizados?

Os dados são atualizados **mensalmente** através da importação de uma nova competência.

### Posso usar procedimentos de competências antigas?

Sim, mas é recomendado usar sempre a competência mais recente para garantir que você está usando os dados atualizados.

### O que fazer se encontrar um erro nos dados?

Os dados vêm diretamente do DATASUS/MS. Se encontrar inconsistências, é necessário verificar com a fonte oficial ou reportar ao suporte técnico.

---

**Última atualização**: Dezembro 2025
