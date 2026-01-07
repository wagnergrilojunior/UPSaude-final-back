# Integração IBGE - Informações de Negócio

## Propósito

A integração com o IBGE permite ao UPSaúde manter dados geográficos atualizados e alinhados com os padrões oficiais do governo brasileiro, garantindo:

1. **Conformidade Regulatória**: Uso de códigos e nomenclaturas oficiais
2. **Precisão de Dados**: Informações atualizadas diretamente da fonte oficial
3. **Interoperabilidade**: Compatibilidade com sistemas governamentais
4. **Indicadores Confiáveis**: Base para relatórios e análises epidemiológicas

## Casos de Uso

### 1. Sincronização Inicial de Dados Geográficos

**Cenário**: Sistema precisa de dados geográficos completos e atualizados.

**Solução**: Endpoint de sincronização completa que:
- Importa todas as regiões do Brasil
- Importa todos os estados (27 unidades federativas)
- Importa todos os municípios (~5.570 municípios)
- Atualiza população estimada

**Benefício**: Base de dados geográfica completa e oficial em minutos.

### 2. Atualização Periódica de Municípios

**Cenário**: Novos municípios são criados ou limites são alterados.

**Solução**: Sincronização periódica de municípios mantém o sistema atualizado.

**Benefício**: Sistema sempre alinhado com mudanças administrativas oficiais.

### 3. Validação de Código IBGE

**Cenário**: Sistema precisa validar se um código IBGE de município é válido.

**Solução**: Endpoint de validação que consulta diretamente a API IBGE.

**Benefício**: Validação em tempo real sem depender de dados locais.

### 4. Atualização de População Estimada

**Cenário**: Relatórios e indicadores precisam de dados populacionais atualizados.

**Solução**: Endpoint específico para atualizar apenas dados de população.

**Benefício**: Indicadores epidemiológicos precisos sem recarregar todos os dados.

### 5. Correção de Inconsistências

**Cenário**: Dados locais podem estar desatualizados ou incorretos.

**Solução**: Sincronização sob demanda permite corrigir dados específicos.

**Benefício**: Qualidade de dados garantida pela fonte oficial.

## Fluxo de Sincronização

### Ordem de Execução

A sincronização segue uma ordem hierárquica obrigatória:

```
1. Regiões (Norte, Nordeste, Centro-Oeste, Sudeste, Sul)
   ↓
2. Estados (27 UF)
   ↓
3. Municípios (~5.570 municípios)
   ↓
4. População Estimada (atualização de municípios existentes)
```

**Importante**: Cada etapa depende da anterior. Estados precisam de regiões, municípios precisam de estados.

### Estratégia de Persistência

- **UPSERT**: Se o registro existe (por código IBGE), atualiza; se não existe, cria
- **Não Duplica**: Índices únicos garantem que não haverá duplicatas
- **Preserva Dados Existentes**: Campos não-IBGE não são alterados
- **Transacional**: Toda sincronização é executada em transação

## Dados Sincronizados

### Regiões

- Código IBGE da região
- Nome oficial da região
- Sigla (se aplicável)

### Estados

- Código IBGE do estado
- Nome oficial do estado
- Sigla IBGE (UF)
- Região IBGE (associação com região)
- Status ativo no IBGE
- Data da última sincronização

### Municípios

- Código IBGE do município
- Nome oficial do município
- UF IBGE (associação com estado)
- População estimada
- Status ativo no IBGE
- Data da última sincronização

## Periodicidade Recomendada

### Sincronização Completa

- **Inicial**: Uma vez ao configurar o sistema
- **Mensal**: Para manter dados atualizados
- **Sob Demanda**: Quando necessário corrigir inconsistências

### Atualização de População

- **Trimestral**: População muda com menor frequência
- **Sob Demanda**: Quando necessário para relatórios específicos

### Validação de Município

- **Tempo Real**: Sempre que necessário validar um código IBGE

## Impacto no Sistema

### Tabelas Afetadas

- `estados`: Adicionadas colunas IBGE (retrocompatível)
- `cidades`: Adicionadas colunas IBGE (retrocompatível)

### Compatibilidade

- ✅ **100% Retrocompatível**: Nenhum dado existente é alterado
- ✅ **Campos Opcionais**: Todas as colunas IBGE são nullable
- ✅ **Endpoints Existentes**: Continuam funcionando normalmente
- ✅ **Sem Breaking Changes**: Nenhum contrato de API foi alterado

## Benefícios de Negócio

### Para o Sistema

- Dados geográficos oficiais e atualizados
- Base sólida para relatórios e análises
- Compatibilidade com sistemas governamentais
- Rastreabilidade de sincronizações

### Para os Usuários

- Validação automática de códigos IBGE
- Dados confiáveis e atualizados
- Relatórios precisos com população estimada
- Interface com dados oficiais

### Para a Organização

- Conformidade com padrões governamentais
- Redução de erros manuais
- Automação de processos
- Melhor qualidade de dados

## Limitações e Considerações

### Limitações da API IBGE

- Rate limiting: A API pública tem limites de requisições
- Disponibilidade: Depende da disponibilidade do serviço IBGE
- Timeout: Requisições podem demorar para municípios grandes

### Mitigações Implementadas

- ✅ Retry automático com backoff exponencial
- ✅ Timeouts configuráveis
- ✅ Tratamento de erros robusto
- ✅ Logs detalhados para troubleshooting

### Recomendações

- Executar sincronizações em horários de menor carga
- Monitorar logs para identificar problemas
- Validar periodicamente a qualidade dos dados
- Manter backups antes de sincronizações grandes

## Métricas de Sucesso

### Indicadores

- Taxa de sucesso de sincronização > 95%
- Tempo médio de sincronização completa < 10 minutos
- Zero duplicatas de códigos IBGE
- 100% dos estados e municípios sincronizados

### Monitoramento

- Logs de sincronização com contadores
- Timestamps de última sincronização
- Lista de erros por etapa
- Tempo de execução total

