# Formato .DBC (DATASUS)

## O que é o formato .DBC

O arquivo .dbc é um formato proprietário do DATASUS utilizado para distribuição de grandes volumes de dados de forma compactada.

Características:
- Baseado em DBF compactado
- Não pode ser lido diretamente por bancos de dados
- Não deve ser aberto diretamente em Excel

## Motivo do uso

- Redução de tamanho dos arquivos
- Padronização histórica
- Compatibilidade com sistemas legados

## Fluxo correto de tratamento

O fluxo recomendado é:

.DBC → CSV → Banco de Dados

Não existe suporte nativo no PostgreSQL para arquivos .dbc.
