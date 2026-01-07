# Integração IBGE - UPSaúde

## Visão Geral

Esta documentação descreve a integração do sistema UPSaúde com a API REST pública do IBGE (Instituto Brasileiro de Geografia e Estatística) para sincronização de dados geográficos oficiais.

## Estrutura da Documentação

- **[README.md](./README.md)** - Este arquivo (visão geral)
- **[NEGOCIO.md](./NEGOCIO.md)** - Informações de negócio, propósito e casos de uso
- **[TECNICO.md](./TECNICO.md)** - Detalhes técnicos, arquitetura e implementação
- **[ENDPOINTS.md](./ENDPOINTS.md)** - Documentação completa dos endpoints REST

## Objetivo

A integração IBGE permite ao UPSaúde:

- ✅ Sincronizar dados geográficos oficiais (Regiões, Estados, Municípios)
- ✅ Manter informações atualizadas de população estimada
- ✅ Validar códigos IBGE de municípios
- ✅ Garantir compatibilidade com padrões governamentais
- ✅ Suportar relatórios e indicadores epidemiológicos

## Características Principais

- 🔄 **Sincronização Automatizada**: Endpoints para sincronização completa ou parcial
- 🔒 **Retrocompatibilidade**: Não quebra dados ou contratos existentes
- ⚡ **Performance**: Configuração de timeouts e retry para resiliência
- 📊 **Rastreabilidade**: Logs detalhados e timestamps de sincronização
- 🛡️ **Tratamento de Erros**: Exceções customizadas e retry automático

## Início Rápido

### Sincronização Completa

```bash
POST http://localhost:8080/api/v1/integracoes/ibge/sincronizar
```

### Sincronização Parcial

```bash
# Apenas estados
POST http://localhost:8080/api/v1/integracoes/ibge/sincronizar/estados

# Apenas municípios
POST http://localhost:8080/api/v1/integracoes/ibge/sincronizar/municipios

# Apenas população
POST http://localhost:8080/api/v1/integracoes/ibge/sincronizar/populacao
```

### Validação de Município

```bash
GET http://localhost:8080/api/v1/integracoes/ibge/validar-municipio/{codigoIbge}
```

## Documentação Detalhada

Consulte os documentos específicos para mais informações:

- **[NEGOCIO.md](./NEGOCIO.md)** - Para entender o propósito e casos de uso
- **[TECNICO.md](./TECNICO.md)** - Para detalhes de implementação e configuração
- **[ENDPOINTS.md](./ENDPOINTS.md)** - Para referência completa da API

## Suporte

Para questões técnicas ou de negócio relacionadas à integração IBGE, consulte a documentação específica ou entre em contato com a equipe de desenvolvimento.

