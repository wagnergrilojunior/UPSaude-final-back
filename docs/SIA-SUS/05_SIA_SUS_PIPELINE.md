# Pipeline de Dados SIA-SUS no UPSaúde

## Visão geral

O pipeline de ingestão de dados do SIA-SUS no UPSaúde segue boas práticas de engenharia de dados:

FTP DATASUS (.dbc)
↓
Conversão via R
↓
CSV
↓
PostgreSQL
↓
Views, BI e Dashboards

## Responsabilidades

- R: leitura e conversão do formato .dbc
- Java / Spring Boot: orquestração, validação e carga
- PostgreSQL: persistência e análise
- Frontend: visualização e indicadores

## Observações legais

- Os dados do SIA-SUS são públicos
- Não contêm identificação direta de pacientes
- Podem ser utilizados para fins analíticos e gerenciais
- Devem respeitar LGPD e boas práticas de segurança
