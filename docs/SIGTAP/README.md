# Documentação SIGTAP - Sistema de Gestão da Tabela Unificada de Procedimentos

## 📋 índice de Documentação

Esta pasta contém a documentação completa sobre a integração do SIGTAP no sistema UpSaude. A documentação está organizada para atender tanto profissionais técnicos quanto de negócio.

### 📋 Documentação de Negócio

- **[Guia de Negócio](./GUIA_NEGOCIO.md)** - Visão geral do SIGTAP para profissionais não técnicos
- **[Glossário](./GLOSSARIO.md)** - Termos e conceitos importantes do SIGTAP
- **[Mapeamento Arquivos e Tabelas](./MAPEAMENTO_ARQUIVOS_TABELAS.md)** - Relação entre arquivos de importação e tabelas do banco

### 📋 Documentação Técnica

- **[Arquitetura Técnica](./ARQUITETURA_TECNICA.md)** - Detalhes técnicos da implementação
- **[Estrutura de Dados](./ESTRUTURA_DADOS.md)** - Modelo de dados e relacionamentos
- **[Processo de Importação](./PROCESSO_IMPORTACAO.md)** - Como funciona a importação de arquivos
- **[API REST](./API_REST.md)** - Endpoints disponíveis para consulta

## 📋 O que é o SIGTAP?

O **Sistema de Gestão da Tabela Unificada de Procedimentos (SIGTAP)** é um sistema do Ministério da Saúde que mantém o cadastro oficial de procedimentos, medicamentos, órteses, próteses e materiais especiais (OPME) do Sistema Único de Saúde (SUS).

### Principais Características

- **Fonte Oficial**: Dados oficiais do DATASUS/MS
- **Atualização Mensal**: Dados atualizados mensalmente por competência
- **Abrangência Nacional**: Cobre todos os procedimentos do SUS
- **Integração Completa**: Inclui procedimentos, CID, ocupações, habilitações e relacionamentos

## 📈 Dados Disponíveis

O sistema importa e disponibiliza:

- **Procedimentos**: Mais de 4.900 procedimentos cadastrados
- **CID**: Classificação Internacional de Doenças (14.242 códigos)
- **Ocupações**: Profissões relacionadas aos procedimentos (2.718 ocupações)
- **Habilitações**: Requisitos para execução de procedimentos
- **Relacionamentos**: Compatibilidades, restrições e associações entre procedimentos

## 🚀 Início Rápido

### Para Profissionais de Negócio

1. Leia o [Guia de Negócio](./GUIA_NEGOCIO.md) para entender o que é o SIGTAP
2. Consulte o [Glossário](./GLOSSARIO.md) para entender termos técnicos
3. Veja o [Mapeamento de Arquivos](./MAPEAMENTO_ARQUIVOS_TABELAS.md) para entender a estrutura

### Para Desenvolvedores

1. Leia a [Arquitetura Técnica](./ARQUITETURA_TECNICA.md) para entender a implementação
2. Consulte a [Estrutura de Dados](./ESTRUTURA_DADOS.md) para entender o modelo
3. Veja o [Processo de Importação](./PROCESSO_IMPORTACAO.md) para entender como funciona
4. Consulte a [API REST](./API_REST.md) para usar os endpoints

## 📅 Competências Disponíveis

As competências são identificadas pelo formato **AAAAMM** (Ano + Mês):

- Exemplo: `202512` = Dezembro de 2025
- Cada competência contém uma versão completa dos dados do SIGTAP
- Os dados são importados mensalmente do DATASUS

## 🔗 Links Úteis

- **Site Oficial SIGTAP**: https://sigtap.datasus.gov.br
- **DATASUS**: http://www.datasus.gov.br
- **Documentação Técnica DATASUS**: Disponível no site oficial

## 🔧 Manutenção

Esta documentação deve ser atualizada sempre que:

- Novas tabelas forem adicionadas
- Novos relacionamentos forem criados
- Mudanças na estrutura de importação ocorrerem
- Novos endpoints forem criados

---

**Última atualização**: Dezembro 2025  
**Versão do Sistema**: 1.0  
**Competência Atual**: 202512
