# Integração CNES - Documentação Completa

Este diretório contém toda a documentação da integração com o **CNES (Cadastro Nacional de Estabelecimentos de Saúde)** do DATASUS.

## 📚 Estrutura da Documentação

A documentação está organizada em 4 categorias principais:

### 1. 📋 [Informações Técnicas](./01-tecnico/)
Documentação técnica sobre arquitetura, implementação, configuração e detalhes de desenvolvimento.

- [Arquitetura e Design](./01-tecnico/01-arquitetura-design.md)
- [Configuração e Setup](./01-tecnico/02-configuracao-setup.md)
- [Estrutura de Código](./01-tecnico/03-estrutura-codigo.md)
- [WSDL e Geração de Classes](./01-tecnico/04-wsdl-geracao-classes.md)
- [Segurança e Autenticação](./01-tecnico/05-seguranca-autenticacao.md)
- [Tratamento de Erros](./01-tecnico/06-tratamento-erros.md)

### 2. 🚀 [Informações de Uso](./02-uso/)
Guias práticos de como usar a API, exemplos de chamadas e casos de uso.

- [Guia de Uso da API](./02-uso/01-guia-uso-api.md)
- [Endpoints REST](./02-uso/02-endpoints-rest.md)
- [Exemplos de Requisições](./02-uso/03-exemplos-requisicoes.md)
- [Casos de Uso](./02-uso/04-casos-uso.md)
- [Troubleshooting](./02-uso/05-troubleshooting.md)

### 3. 💼 [Informações para o Negócio](./03-negocio/)
Informações sobre dados, tabelas, tipos de dados e valor agregado ao negócio.

- [Visão Geral dos Dados](./03-negocio/01-visao-geral-dados.md)
- [Estrutura de Tabelas](./03-negocio/02-estrutura-tabelas.md)
- [Tipos de Dados e Enums](./03-negocio/03-tipos-dados-enums.md)
- [Valor Agregado ao Negócio](./03-negocio/04-valor-agregado.md)
- [Fluxos de Sincronização](./03-negocio/05-fluxos-sincronizacao.md)
- [Relatórios e Consultas](./03-negocio/06-relatorios-consultas.md)

### 4. 📖 [Informações Oficiais](./04-oficial/)
Documentação oficial do DATASUS, referências e links úteis.

- [Documentação Oficial DATASUS](./04-oficial/01-documentacao-oficial.md)
- [Referências e Links](./04-oficial/02-referencias-links.md)
- [Glossário CNES](./04-oficial/03-glossario-cnes.md)
- [Normas e Regulamentações](./04-oficial/04-normas-regulamentacoes.md)

## 🎯 Início Rápido

Para começar a usar a integração CNES:

1. **Desenvolvedores**: Comece pela [Arquitetura e Design](./01-tecnico/01-arquitetura-design.md)
2. **Usuários da API**: Veja o [Guia de Uso da API](./02-uso/01-guia-uso-api.md)
3. **Gestores**: Consulte o [Valor Agregado ao Negócio](./03-negocio/04-valor-agregado.md)

## 📊 Resumo da Integração

A integração CNES permite:

- ✅ Sincronização de estabelecimentos de saúde
- ✅ Consulta de profissionais de saúde
- ✅ Gerenciamento de equipes de saúde
- ✅ Controle de vinculações profissionais
- ✅ Inventário de equipamentos
- ✅ Gestão de leitos

## 🔗 Links Rápidos

- [API REST - Swagger UI](http://localhost:8080/api/swagger-ui.html)
- [Health Check](http://localhost:8080/api/actuator/health)
- [Documentação DATASUS](https://datasus.saude.gov.br/)

## 📝 Última Atualização

**Data**: Janeiro 2025  
**Versão**: 1.0.0  
**Status**: ✅ Implementado e Funcional

