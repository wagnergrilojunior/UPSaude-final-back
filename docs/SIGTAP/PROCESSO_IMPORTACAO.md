# Processo de Importação - SIGTAP

## 🔄 Visão Geral

Este documento explica como funciona o processo de importação dos arquivos SIGTAP para o banco de dados.

## 📁 Estrutura de Arquivos

### Localização

Os arquivos de importação estão organizados por competência:

```
data_import/sigtap/
└── 202512/                    # Competência (AAAAMM)
    ├── tb_procedimento.txt    # Arquivo de dados
    ├── tb_procedimento_layout.txt  # Layout do arquivo
    ├── rl_procedimento_cid.txt
    ├── rl_procedimento_cid_layout.txt
    └── ... (outros arquivos)
```

### Formato dos Arquivos

- **Encoding**: ISO-8859-1 (Latin-1)
- **Formato**: Texto de largura fixa
- **Layout**: Definido em arquivos `*_layout.txt` (CSV)

### Arquivo de Layout

Cada arquivo de dados possui um arquivo de layout correspondente que define:
- Nome do campo
- Tamanho em caracteres
- Posição inicial e final
- Tipo de dado (VARCHAR2, NUMBER, etc.)

**Exemplo** (`tb_procedimento_layout.txt`):
```csv
Coluna,Tamanho,Inicio,Fim,Tipo
CO_PROCEDIMENTO,10,1,10,VARCHAR2
NO_PROCEDIMENTO,250,11,260,VARCHAR2
TP_COMPLEXIDADE,1,261,261,VARCHAR2
```

## 🚀 Fluxo de Importação

### 1. Inicialização

```
POST /api/sigtap/import/202512
```

1. Valida se a pasta da competência existe
2. Cria objeto `ImportResult` para estatísticas
3. Inicia processamento sequencial

### 2. Ordem de Importação

A importação segue uma ordem específica para respeitar dependências:

#### Fase 1: Tabelas de Referência (18 arquivos)
```
1. tb_grupo.txt
2. tb_financiamento.txt
3. tb_rubrica.txt
4. tb_modalidade.txt
5. tb_registro.txt
6. tb_tipo_leito.txt
7. tb_servico.txt
8. tb_servico_classificacao.txt
9. tb_cid.txt
10. tb_ocupacao.txt
11. tb_habilitacao.txt
12. tb_grupo_habilitacao.txt
13. tb_regra_condicionada.txt
14. tb_renases.txt
15. tb_tuss.txt
16. tb_componente_rede.txt
17. tb_rede_atencao.txt
18. tb_sia_sih.txt
19. tb_detalhe.txt
```

#### Fase 2: Hierarquia de Agregaçãoo (2 arquivos)
```
20. tb_sub_grupo.txt (depende de tb_grupo.txt)
21. tb_forma_organizacao.txt (depende de tb_sub_grupo.txt)
```

#### Fase 3: Procedimentos (1 arquivo)
```
22. tb_procedimento.txt (depende de tb_forma_organizacao.txt)
```

#### Fase 4: Descriçãoes (2 arquivos)
```
23. tb_descricao.txt (depende de tb_procedimento.txt)
24. tb_descricao_detalhe.txt (depende de tb_detalhe.txt)
```

#### Fase 5: Compatibilidades (1 arquivo)
```
25. rl_procedimento_compativel.txt
```

#### Fase 6: Relacionamentos (15 arquivos)
```
26. rl_procedimento_cid.txt (depende de tb_procedimento.txt e tb_cid.txt)
27. rl_procedimento_ocupacao.txt (depende de tb_procedimento.txt e tb_ocupacao.txt)
28. rl_procedimento_habilitacao.txt
29. rl_procedimento_leito.txt
30. rl_procedimento_servico.txt
31. rl_procedimento_incremento.txt
32. rl_procedimento_comp_rede.txt
33. rl_procedimento_origem.txt
34. rl_procedimento_sia_sih.txt
35. rl_procedimento_regra_cond.txt
36. rl_procedimento_renases.txt
37. rl_procedimento_tuss.txt
38. rl_procedimento_modalidade.txt
39. rl_procedimento_registro.txt
40. rl_procedimento_detalhe.txt
41. rl_excecao_compatibilidade.txt
```

### 3. Processamento de Arquivo Individual

Para cada arquivo, o processo é:

```
1. Verificar se arquivo existe
2. Verificar se layout existe
3. Ler layout → SigtapLayoutDefinition
4. Criar ImportContext
5. Processar linhas em batch:
   a. Ler linha do arquivo
   b. Parsear linha → Map<String, String>
   c. Validar campos básicos
   d. Mapear para entidade → Entity
   e. Adicionar ao batch
   f. Quando batch atingir tamanho → salvar em lote
6. Salvar batch final (se houver)
7. Retornar estatísticas
```

### 4. Processamento em Batch

#### Configuração
- **Tamanho do batch**: 500 registros (configurável)
- **Método**: `repository.saveAll(batch)`
- **Transação**: Uma transação por batch

#### Vantagens
- ✅ Reduz número de commits ao banco
- ✅ Melhora performance significativamente
- ✅ Reduz carga no banco de dados

### 5. Tratamento de Erros

#### Erro em Linha Individual
- Registra erro no log
- Adiciona à lista de erros detalhados
- **Continua processamento** da próxima linha

#### Erro Crítico (Contexto Spring Fechado)
- Registra erro crítico
- **Para processamento** do arquivo atual
- **Continua** com próximo arquivo

#### Erro Fatal
- Registra erro fatal
- **Para importação completa**
- Retorna resultado com erros

## 🔍 Validações Realizadas

### Validações Básicas

1. **Linha não vazia**: Ignora linhas em branco
2. **Campos obrigatórios**: Verifica campos essenciais
3. **Tipos de dados**: Valida conversão de tipos
4. **Relacionamentos**: Verifica se entidades relacionadas existem

### Validações Especiais

1. **Idade 9999**: Tratado como "não aplica" (converte para NULL)
2. **Valores monetários**: Valida formato e converte para BigDecimal
3. **Encoding**: Garante leitura correta com ISO-8859-1
4. **Duplicatas**: Verifica antes de inserir (upsert logic)

## 📊 Estatísticas Retornadas

O resultado da importação contém:

```json
{
  "competencia": "202512",
  "totalLinhasProcessadas": 198465,
  "totalErros": 0,
  "linhasPorArquivo": {
    "tb_procedimento.txt": 4957,
    "rl_procedimento_cid.txt": 81753,
    ...
  },
  "erros": [],
  "sucesso": true
}
```

## ⚙️ Configurações

### application.properties

```properties
# Caminho base para arquivos
sigtap.import.base-path=data_import/sigtap

# Tamanho do batch
sigtap.import.batch-size=500

# Encoding dos arquivos
sigtap.import.encoding=ISO-8859-1
```

### Timeouts de Transação

- **Arquivos grandes**: 1 hora (3600 segundos)
  - `rl_procedimento_ocupacao.txt` (193k linhas)
  - `rl_procedimento_cid.txt` (81k linhas)
- **Arquivos normais**: Padrão Spring (30 segundos)

## 🔄 Idempotência

### Upsert Logic

Algumas tabelas implementam lógica de upsert para evitar duplicatas:

- `sigtap_descricao`: Verifica por `procedimento_id` + `competencia_inicial`
- `sigtap_descricao_detalhe`: Verifica por `detalhe_id` + `competencia_inicial`

### Reimportação Segura

É possível reimportar uma competência sem criar duplicatas:
- Tabelas com upsert: Não duplicam
- Tabelas sem upsert: Podem duplicar (requer limpeza prévia)

## 📈 Performance

### Tempos Estimados (Competência 202512)

- **Arquivos pequenos** (< 1k linhas): 1-5 segundos
- **Arquivos médios** (1k-10k linhas): 10-60 segundos
- **Arquivos grandes** (> 100k linhas): 5-30 minutos

### Otimizações Aplicadas

1. ✅ Batch processing (500 registros por vez)
2. ✅ Lazy loading de relacionamentos
3. ✅ Índices em campos frequentemente consultados
4. ✅ Transações otimizadas
5. ✅ Timeouts adequados para arquivos grandes

## 🐛 Troubleshooting

### Problema: Importação Interrompida

**Sintomas**:
- Arquivo parcialmente importado
- Erros de "contexto Spring fechado"

**Solução**:
1. Verificar logs para identificar arquivo problemático
2. Limpar tabelas afetadas
3. Reimportar competência completa

### Problema: Duplicatas

**Sintomas**:
- Mais registros no banco que no arquivo
- Erros de constraint única

**Solução**:
1. Limpar tabelas antes de reimportar
2. Verificar se upsert logic está funcionando
3. Reimportar competência

### Problema: Encoding Incorreto

**Sintomas**:
- Caracteres especiais incorretos (ã → a, ç → c)

**Solução**:
- Verificar se arquivo está em ISO-8859-1
- Verificar se leitura está usando encoding correto

## 📝 Logs

### Níveis de Log

- **INFO**: Progresso geral, arquivos processados
- **WARN**: Erros em linhas individuais
- **ERROR**: Erros críticos que param processamento
- **DEBUG**: Detalhes de validação (quando habilitado)

### Exemplo de Log

```
INFO  - Iniciando importação da competência: 202512
INFO  - Processando arquivo tb_procedimento.txt: 1000 linhas lidas, 1000 processadas, 0 erros
INFO  - Arquivo tb_procedimento.txt importado: 4957 linhas processadas
WARN  - Erro ao processar linha 1234: Procedimento não encontrado: 03.01.01.999-9
INFO  - Importação da competência 202512 concluída. Total: 198465 linhas processadas, 5 erros
```

---

**Última atualização**: Dezembro 2025
