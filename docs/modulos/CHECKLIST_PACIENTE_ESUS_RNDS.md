# Checklist - Paciente Pronto para e-SUS APS / RNDS

Este checklist valida se o domínio Paciente está preparado para integrações com sistemas governamentais (e-SUS APS, RNDS, CADSUS).

## ✅ Estrutura de Dados

### Core do Paciente
- [x] Core mínimo e estável (apenas dados fundamentais)
- [x] Nenhum dado governamental acoplado ao core
- [x] Campos variáveis externalizados

### Identificadores
- [x] Tabela `paciente_identificador` criada
- [x] Suporta múltiplos identificadores por paciente
- [x] Suporta múltiplas origens (UPSAUDE, CADSUS, ESUS, RNDS)
- [x] Permite divergências controladas
- [x] Campos CPF, CNS, RG migrados

### Contatos
- [x] Tabela `paciente_contato` criada
- [x] Suporta múltiplos contatos por paciente
- [x] Campos telefone e email migrados

### Dados Sociodemográficos
- [x] Tabela `paciente_dados_sociodemograficos` padronizada
- [x] Alinhado com e-SUS APS
- [x] Dados consolidados e sem duplicidade

### Dados Clínicos
- [x] Tabela `paciente_dados_clinicos` padronizada
- [x] Separado do core

### Vínculo Territorial
- [x] Tabela `paciente_vinculo_territorial` criada
- [x] Suporta histórico de vínculos
- [x] Campos CNES, INE, microárea
- [x] Essencial para e-SUS APS

### Integração Governamental
- [x] Tabela `paciente_integracao_gov` refatorada
- [x] Suporta múltiplas integrações por paciente (OneToMany)
- [x] Campo `sistema` (ESUS, RNDS, CADSUS)
- [x] Campo `versao_layout` para versionamento
- [x] Campo `payload_bruto` em JSONB
- [x] Rastreabilidade completa (datas, status, origem)
- [x] Não depende de schema rígido do governo

### Endereços
- [x] Tabela `paciente_endereco` criada (vínculo)
- [x] Metadados completos (tipo, principal, origem, vigência)
- [x] Histórico de endereços

### Dados Pessoais Complementares
- [x] Tabela `paciente_dados_pessoais_complementares` criada
- [x] Rastreabilidade de origem

### Óbito
- [x] Tabela `paciente_obito` criada
- [x] Rastreabilidade de origem

## ✅ Padronização

- [x] Todas as tabelas seguem padrão `paciente_*`
- [x] Nomenclatura consistente
- [x] Índices apropriados criados

## ✅ Segurança e Integridade

### Cascade e OrphanRemoval
- [x] `orphanRemoval = false` em dados históricos (integrações, vínculos)
- [x] `orphanRemoval = true` apenas em dados do paciente
- [x] Proteção contra perda acidental de histórico

### Equals/HashCode
- [x] Todas entidades usam `@EqualsAndHashCode(onlyExplicitlyIncluded = true)`
- [x] Apenas `id` incluído no equals/hashCode
- [x] Sem dependência de coleções

## ✅ Performance

### EntityGraphs
- [x] EntityGraphs específicos por caso de uso criados
- [x] `Paciente.listagem` para listagens
- [x] `Paciente.cadastro` para cadastro/edição
- [x] `Paciente.prontuarioResumido` para prontuário rápido
- [x] `Paciente.integracaoGov` para integrações
- [x] `Paciente.prontuarioCompleto` com aviso de uso

### Índices
- [x] Índices criados para campos de busca frequente
- [x] Índice GIN em `payload_bruto` (JSONB)

## ✅ Migrações

- [x] Todas as migrations criadas
- [x] Migrações preservam dados existentes
- [x] Migrações são incrementais e seguras
- [x] Validação de dados migrados

## ✅ Documentação

- [x] Documentação do domínio criada
- [x] Checklist de validação criado
- [x] Decisões arquiteturais documentadas
- [x] Guia de uso dos EntityGraphs

## ⚠️ Validações Necessárias

### Dados
- [ ] Validar integridade referencial após migrações
- [ ] Validar que nenhum dado foi perdido
- [ ] Validar consistência de dados migrados

### Código
- [ ] Atualizar serviços para usar novos relacionamentos
- [ ] Atualizar DTOs e mappers
- [ ] Remover referências a campos antigos
- [ ] Atualizar queries customizadas

### Testes
- [ ] Criar testes de integração para migrações
- [ ] Testar EntityGraphs
- [ ] Testar serviços atualizados
- [ ] Validar performance com dados reais

### Integrações
- [ ] Testar integração com e-SUS APS
- [ ] Testar integração com RNDS
- [ ] Validar payload bruto JSONB
- [ ] Validar versionamento de layout

## 📋 Checklist de Validação de Dados

### Identificadores
- [ ] Todos os CPFs migrados
- [ ] Todos os CNS migrados
- [ ] Todos os RGs migrados
- [ ] Identificadores principais marcados corretamente

### Contatos
- [ ] Todos os telefones migrados
- [ ] Todos os emails migrados
- [ ] Contatos principais marcados corretamente

### Endereços
- [ ] Todos os vínculos migrados
- [ ] Endereços principais marcados corretamente
- [ ] Tipos de endereço definidos

### Dados Pessoais
- [ ] Nomes de mãe e pai migrados
- [ ] Identidade de gênero migrada
- [ ] Orientação sexual migrada

### Óbito
- [ ] Datas de óbito migradas
- [ ] Causas de óbito migradas

### Integrações
- [ ] Flags de integração migradas
- [ ] Dados de sincronização preservados

## 🎯 Resultado Esperado

Após completar este checklist, o domínio Paciente deve estar:
- ✅ Preparado para integrações governamentais
- ✅ Sem risco de perda de dados históricos
- ✅ Com performance otimizada
- ✅ Com rastreabilidade completa
- ✅ Alinhado com padrões de sistemas públicos de saúde

