# Visualização do Processo de Sincronização CNES

Para facilitar o entendimento de como os dados são integrados entre o DATASUS (CNES) e o sistema UPSaude, preparamos as visualizações abaixo.

## 🔄 Fluxo de Dados Conceitual

O diagrama abaixo ilustra o caminho que um dado percorre desde a solicitação do usuário até a persistência no banco de dados local, passando pelas etapas de transformação e validação.

![Fluxo de Sincronização CNES](file:///Users/wagnergrilo/.gemini/antigravity/brain/a281b829-7cd2-427a-a21f-4ef03c6a768e/cnes_sync_flowchart_vibrant_1767781492784.png)

1.  **Usuário/API**: Uma requisição é feita via REST para sincronizar uma entidade (ex: Estabelecimento).
2.  **CNES API (External)**: O sistema UPSaude se conecta aos servidores do DATASUS usando SOAP e WS-Security.
3.  **Mapeamento e Transformação**: Os dados brutos (XML/WSDL) são convertidos em entidades Java utilizando nossos Mappers específicos.
4.  **Validação e Regras**: Aplicamos validações de negócios e garantimos a integridade do multitenancy.
5.  **UPSaude DB**: Os dados são persistidos ou atualizados no banco de dados local.

---

## 📊 Mockup do Painel de Controle (Dashboard)

Abaixo, uma representação visual de como as estatísticas de sincronização podem ser visualizadas pela equipe de gestão, permitindo um acompanhamento em tempo real da saúde da base de dados.

![Dashboard de Sincronização](file:///Users/wagnergrilo/.gemini/antigravity/brain/a281b829-7cd2-427a-a21f-4ef03c6a768e/cnes_sync_dashboard_mockup_1767781471398.png)

*   **Status de Sincronização**: Cartões indicando o percentual de dados sincronizados por categoria.
*   **Histórico**: Gráfico de evolução da sincronização nos últimos 30 dias.
*   **Praticidade**: Interface focada no usuário final, facilitando a identificação de pendências.
