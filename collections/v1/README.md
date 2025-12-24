# UPSaude API - Postman Collections

Este diretório contém as collections e environments do Postman para a API UPSaude.

## Arquivos

- **UPSaude-API-v1.postman_collection.json**: Collection completa com todos os endpoints da API v1
- **UPSaude-Local.postman_environment.json**: Environment para ambiente local
- **UPSaude-Dev.postman_environment.json**: Environment para ambiente de desenvolvimento
- **UPSaude-Prod.postman_environment.json**: Environment para ambiente de produção

## Como Importar no Postman

### 1. Importar a Collection

1. Abra o Postman
2. Clique em **Import** (canto superior esquerdo)
3. Selecione o arquivo `UPSaude-API-v1.postman_collection.json`
4. Clique em **Import**

### 2. Importar os Environments

1. Clique em **Import** novamente
2. Selecione os três arquivos de environment:
   - `UPSaude-Local.postman_environment.json`
   - `UPSaude-Dev.postman_environment.json`
   - `UPSaude-Prod.postman_environment.json`
3. Clique em **Import**

### 3. Selecionar o Environment

1. No canto superior direito do Postman, clique no dropdown de environments
2. Selecione o environment desejado:
   - **UPSaude - Local**: Para desenvolvimento local (http://localhost:8080/api)
   - **UPSaude - Dev**: Para ambiente de desenvolvimento (https://api-dev.upsaude.wgbsolucoes.com.br/api)
   - **UPSaude - Prod**: Para ambiente de produção (https://api.upsaude.wgbsolucoes.com.br/api)

## Autenticação

A collection está configurada para usar autenticação Bearer Token. Para obter o token:

1. Execute a requisição **Login** na pasta **01 - Autenticação**
2. Copie o token retornado na resposta
3. Cole o token na variável `auth_token` do environment selecionado:
   - Clique no ícone de "olho" no canto superior direito
   - Edite a variável `auth_token`
   - Cole o token
   - Salve

Alternativamente, você pode configurar um script de teste na requisição de Login para salvar automaticamente o token:

```javascript
if (pm.response.code === 200) {
    const response = pm.response.json();
    if (response.token) {
        pm.environment.set("auth_token", response.token);
    } else if (response.access_token) {
        pm.environment.set("auth_token", response.access_token);
    }
}
```

## Estrutura da Collection

A collection está organizada em pastas por módulo:

1. **01 - Autenticação**: Endpoints de login e verificação de acesso
2. **02 - Sistema - Usuários**: Gerenciamento de usuários
3. **03 - Sistema - Multitenancy**: Gerenciamento de tenants
4. **04 - Pacientes**: CRUD de pacientes
5. **05 - Estabelecimentos**: CRUD de estabelecimentos
6. **06 - Profissionais**: Gerenciamento de profissionais de saúde
7. **07 - Referências**: Endpoints de referência (Estados, Cidades, CID, SIGTAP)
8. **08 - Import Jobs**: Consulta de jobs de importação
9. **09 - Sistema - Enums**: Listagem de enums do sistema
10. **10 - Sistema - Relatórios**: Endpoints de relatórios
11. **11 - Sistema - Auditoria**: Logs de auditoria
12. **12 - Sistema - LGPD**: Consentimentos LGPD

## Variáveis de Environment

Cada environment contém as seguintes variáveis:

- **base_url**: URL base da API
- **auth_token**: Token de autenticação (preenchido após login)
- **server_port**: Porta do servidor
- **context_path**: Caminho do contexto da aplicação (/api)

## Notas

- Todos os endpoints que requerem autenticação usam a variável `{{auth_token}}` automaticamente
- Os endpoints de listagem suportam paginação com parâmetros `page`, `size` e `sort`
- Alguns endpoints requerem IDs que devem ser preenchidos nas variáveis de path (ex: `:id`, `:jobId`)

## Atualizações

Para atualizar a collection quando novos endpoints forem adicionados:

1. Adicione os novos endpoints na collection
2. Exporte a collection atualizada
3. Substitua o arquivo `UPSaude-API-v1.postman_collection.json`

