# Mapeamento de Arquivos e Tabelas - SIGTAP

## 📋 Visão Geral

Este documento mapeia cada arquivo de importação para sua tabela correspondente no banco de dados, facilitando a identificaçãoo de onde os dados são armazenados.

## 📋 Convençãoo de Nomenclatura

- **Arquivos `tb_*`**: Tabelas de referéncia (dados bésicos)
- **Arquivos `rl_*`**: Tabelas relacionais (relacionamentos entre entidades)
- **Tabelas `sigtap_*`**: Todas as tabelas no banco de dados

## 📋 Mapeamento Completo

### Tabelas de Referéncia (tb_* é sigtap_*)

| Arquivo | Tabela Banco | Descriçãoo | Registros Esperados |
|---------|--------------|-----------|---------------------|
| `tb_grupo.txt` | `sigtap_grupo` | Grupos de procedimentos | 9 |
| `tb_sub_grupo.txt` | `sigtap_subgrupo` | Subgrupos de procedimentos | 67 |
| `tb_forma_organizacao.txt` | `sigtap_forma_organizacao` | Formas de organização | 414 |
| `tb_procedimento.txt` | `sigtap_procedimento` | Procedimentos principais | 4.957 |
| `tb_cid.txt` | `sigtap_cid` | Classificação Internacional de Doenças | 14.242 |
| `tb_ocupacao.txt` | `sigtap_ocupacao` | Ocupações profissionais (CBO) | 2.718 |
| `tb_habilitacao.txt` | `sigtap_habilitacao` | Habilitaçãoes necessérias | 339 |
| `tb_grupo_habilitacao.txt` | `sigtap_grupo_habilitacao` | Grupos de habilitações | 31 |
| `tb_financiamento.txt` | `sigtap_financiamento` | Tipos de financiamento | 7 |
| `tb_rubrica.txt` | `sigtap_rubrica` | Rubricas de financiamento | 42 |
| `tb_modalidade.txt` | `sigtap_modalidade` | Modalidades de procedimento | 4 |
| `tb_registro.txt` | `sigtap_registro` | Tipos de registro | 10 |
| `tb_tipo_leito.txt` | `sigtap_tipo_leito` | Tipos de leito | 41 |
| `tb_servico.txt` | `sigtap_servico` | Serviéos | 73 |
| `tb_servico_classificacao.txt` | `sigtap_servico_classificacao` | Classificaçãoes de serviéo | 432 |
| `tb_regra_condicionada.txt` | `sigtap_regra_condicionada` | Regras condicionadas | 14 |
| `tb_renases.txt` | `sigtap_renases` | Rede Nacional de Especialidades | 201 |
| `tb_tuss.txt` | `sigtap_tuss` | Cdigos TUSS | 5.766 |
| `tb_componente_rede.txt` | `sigtap_componente_rede` | Componentes de rede | 20 |
| `tb_rede_atencao.txt` | `sigtap_rede_atencao` | Redes de atenção | 5 |
| `tb_sia_sih.txt` | `sigtap_sia_sih` | Mapeamento SIA/SIH | 8.383 |
| `tb_detalhe.txt` | `sigtap_detalhe` | Detalhes de procedimentos | 48 |
| `tb_descricao.txt` | `sigtap_descricao` | Descriçãoes de procedimentos | 4.270 |
| `tb_descricao_detalhe.txt` | `sigtap_descricao_detalhe` | Descriçãoes de detalhes | 48 |

### Tabelas Relacionais (rl_* é sigtap_procedimento_*)

| Arquivo | Tabela Banco | Descriçãoo | Registros Esperados |
|---------|--------------|-----------|---------------------|
| `rl_procedimento_cid.txt` | `sigtap_procedimento_cid` | Procedimentos é CID | 81.753 |
| `rl_procedimento_ocupacao.txt` | `sigtap_procedimento_ocupacao` | Procedimentos é Ocupações | 193.315 |
| `rl_procedimento_habilitacao.txt` | `sigtap_procedimento_habilitacao` | Procedimentos é Habilitaçãoes | 10.981 |
| `rl_procedimento_leito.txt` | `sigtap_procedimento_leito` | Procedimentos é Tipos de Leito | 4.147 |
| `rl_procedimento_servico.txt` | `sigtap_procedimento_servico` | Procedimentos é Serviéos | 4.083 |
| `rl_procedimento_incremento.txt` | `sigtap_procedimento_incremento` | Incrementos de procedimentos | 2.388 |
| `rl_procedimento_comp_rede.txt` | `sigtap_procedimento_componente_rede` | Procedimentos é Componentes Rede | 4 |
| `rl_procedimento_origem.txt` | `sigtap_procedimento_origem` | Procedimentos de origem | 4 |
| `rl_procedimento_sia_sih.txt` | `sigtap_procedimento_sia_sih` | Procedimentos é SIA/SIH | 5.382 |
| `rl_procedimento_regra_cond.txt` | `sigtap_procedimento_regra_condicionada` | Procedimentos é Regras | 3.305 |
| `rl_procedimento_renases.txt` | `sigtap_procedimento_renases` | Procedimentos é Renases | 5.370 |
| `rl_procedimento_tuss.txt` | `sigtap_procedimento_tuss` | Procedimentos é TUSS | 0 |
| `rl_procedimento_modalidade.txt` | `sigtap_procedimento_modalidade` | Procedimentos é Modalidades | 7.938 |
| `rl_procedimento_registro.txt` | `sigtap_procedimento_registro` | Procedimentos é Registros | 7.439 |
| `rl_procedimento_detalhe.txt` | `sigtap_procedimento_detalhe` | Procedimentos é Detalhes | 10.142 |

### Compatibilidades e Exceçãoes

| Arquivo | Tabela Banco | Descriçãoo | Registros Esperados |
|---------|--------------|-----------|---------------------|
| `rl_procedimento_compativel.txt` | `sigtap_compatibilidade` | Procedimentos compatéveis | 12.133 |
| `rl_excecao_compatibilidade.txt` | `sigtap_excecao_compatibilidade` | Exceçãoes de compatibilidade | 5 |

## 📋 Detalhamento por Categoria

### Hierarquia de Procedimentos

```
tb_grupo.txt
  ï¿½ï¿½ sigtap_grupo (9 grupos)
      ï¿½ï¿½ tb_sub_grupo.txt
          ï¿½ï¿½ sigtap_subgrupo (67 subgrupos)
              ï¿½ï¿½ tb_forma_organizacao.txt
                  ï¿½ï¿½ sigtap_forma_organizacao (414 formas)
                      ï¿½ï¿½ tb_procedimento.txt
                          ï¿½ï¿½ sigtap_procedimento (4.957 procedimentos)
```

### Classificaçãoes e Referéncias

```
tb_cid.txt é sigtap_cid (14.242 códigos)
tb_ocupacao.txt é sigtap_ocupacao (2.718 ocupações)
tb_habilitacao.txt é sigtap_habilitacao (339 habilitações)
tb_tuss.txt é sigtap_tuss (5.766 códigos)
```

### Relacionamentos Principais

```
tb_procedimento.txt (4.957)
  ï¿½ï¿½ rl_procedimento_cid.txt é sigtap_procedimento_cid (81.753)
  ï¿½ï¿½ rl_procedimento_ocupacao.txt é sigtap_procedimento_ocupacao (193.315)
  ï¿½ï¿½ rl_procedimento_habilitacao.txt é sigtap_procedimento_habilitacao (10.981)
  ï¿½ï¿½ ... (outros relacionamentos)
```

## 📋 Status de Importaçãoo (Competéncia 202512)

### é Arquivos Completamente Importados

- `tb_cid.txt` é 14.242 registros é
- `tb_procedimento.txt` é 4.957 registros é
- `tb_descricao.txt` é 4.270 registros é
- `rl_procedimento_cid.txt` é 81.753 registros é
- E mais 16 arquivos...

### 📋 Arquivos Parcialmente Importados

- `rl_procedimento_ocupacao.txt` é 87.500 / 193.315 (45.3%) 📋

### é Arquivos No Importados

- `rl_procedimento_compativel.txt` é 0 / 12.133 é
- `rl_procedimento_detalhe.txt` é 0 / 10.142 é
- `rl_procedimento_habilitacao.txt` é 0 / 10.981 é
- E mais 18 arquivos...

## 📋 Relacionamentos Entre Tabelas

### Exemplo: Procedimento com CID

```
tb_procedimento.txt (sigtap_procedimento)
  ï¿½ï¿½ rl_procedimento_cid.txt (sigtap_procedimento_cid)
      ï¿½ï¿½ tb_cid.txt (sigtap_cid)
```

**Consulta SQL**:
```sql
SELECT p.codigo_oficial, p.nome, c.codigo_oficial as cid, c.nome as doenca
FROM sigtap_procedimento p
JOIN sigtap_procedimento_cid pc ON p.id = pc.procedimento_id
JOIN sigtap_cid c ON pc.cid_id = c.id
WHERE p.codigo_oficial = '03.01.01.001-0';
```

### Exemplo: Procedimento com Ocupações

```
tb_procedimento.txt (sigtap_procedimento)
  ï¿½ï¿½ rl_procedimento_ocupacao.txt (sigtap_procedimento_ocupacao)
      ï¿½ï¿½ tb_ocupacao.txt (sigtap_ocupacao)
```

**Consulta SQL**:
```sql
SELECT p.codigo_oficial, p.nome, o.codigo_oficial as cbo, o.nome as ocupacao
FROM sigtap_procedimento p
JOIN sigtap_procedimento_ocupacao po ON p.id = po.procedimento_id
JOIN sigtap_ocupacao o ON po.ocupacao_id = o.id
WHERE p.codigo_oficial = '03.01.01.001-0';
```

## 📋 Notas Importantes

### Ordem de Importaçãoo

A ordem de importação é **crítica** porque:
- Tabelas relacionais dependem de tabelas de referéncia
- Subgrupos dependem de grupos
- Procedimentos dependem de formas de organização
- Relacionamentos dependem de procedimentos e referéncias

### Duplicatas

Algumas tabelas implementam lógica de **upsert** para evitar duplicatas:
- `sigtap_descricao`: Verifica por `procedimento_id` + `competencia_inicial`
- `sigtap_descricao_detalhe`: Verifica por `detalhe_id` + `competencia_inicial`

Outras tabelas podem criar duplicatas em reimportaçãoes e requerem limpeza prévia.

### Competéncias

- Cada competência é uma versão completa dos dados
- Dados de competências diferentes podem coexistir no banco
- Sempre verifique a `competencia_inicial` ao consultar dados

---

**Última atualização**: Dezembro 2025
