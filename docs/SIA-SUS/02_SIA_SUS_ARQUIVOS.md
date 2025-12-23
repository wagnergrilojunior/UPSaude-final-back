# SIA-SUS — Tipos de Arquivos

Os dados do SIA-SUS são distribuídos mensalmente em arquivos organizados por tipo de informação.

## PA — Produção Ambulatorial
- Registra procedimentos efetivamente realizados
- Contém CNES, procedimento (SIGTAP), CID-10, quantidade, valores e competência
- Principal fonte para análises e odontologia

## PS — Produção por Profissional
- Produção associada à categoria profissional (CBO)
- Não contém dados pessoais
- Usado para análise de força de trabalho

## AQ — Autorizações Ambulatoriais
- Registra procedimentos autorizados
- Permite análise de autorizado versus executado
- Usado em auditorias

## PF — Produção Financeira
- Contém valores apresentados, aprovados e pagos
- Complementa o arquivo PA

## APAC — Alta Complexidade
- Procedimentos de média e alta complexidade
- Geralmente hospitalares
- Estrutura diferente do PA

## Padrão de nomenclatura

PA<UF><ANO><MES>.dbc

Exemplo:
PAMG202212.dbc

Onde:
- PA = Produção Ambulatorial
- MG = Unidade Federativa
- 202212 = competência (dezembro/2022)
