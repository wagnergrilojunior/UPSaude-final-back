# UPSaude API v2 - Postman Collections

Esta pasta contém as collections do Postman para a API UPSaude versão 2.0, refatorada com arquitetura orientada a casos de uso.

## Arquivos

- **UPSaude-API-v2.postman_collection.json**: Collection principal com todos os endpoints organizados por categorias
- **UPSaude-Local.postman_environment.json**: Environment para ambiente local
- **UPSaude-Dev.postman_environment.json**: Environment para ambiente de desenvolvimento
- **UPSaude-Prod.postman_environment.json**: Environment para ambiente de produção

## Como Importar

1. Abra o Postman
2. Clique em **Import** no canto superior esquerdo
3. Selecione os arquivos `.json` desta pasta
4. Importe a collection e os environments

## Estrutura da Collection

A collection está organizada nas seguintes categorias:

1. **01 - Autenticação**: Login, verificação de acesso, usuário atual
2. **02 - Clínica - Atendimentos**: CRUD e operações de atendimento (iniciar, triagem, encerrar)
3. **03 - Clínica - Consultas Médicas**: CRUD e operações de consulta (anamnese, diagnóstico, prescrição, etc.)
4. **04 - Clínica - Prontuário**: Timeline, resumo e eventos do prontuário (somente leitura)
5. **05 - Farmácia**: Receitas pendentes, dispensações
6. **06 - Receitas**: CRUD de receitas médicas
7. **07 - Pacientes**: CRUD completo de pacientes
8. **08 - Profissionais de Saúde**: CRUD de profissionais de saúde
9. **09 - Médicos**: CRUD de médicos e gerenciamento de especialidades
10. **10 - Convênios**: CRUD de convênios
11. **11 - Estabelecimentos**: CRUD de estabelecimentos
12. **12 - Referência - SIGTAP**: Consultas ao SIGTAP (grupos, procedimentos, CBO, medicamentos)
13. **13 - Referência - CID**: Consultas ao CID-10 (capítulos, categorias)
14. **14 - Sistema - Enums**: Listagem de enums do sistema
15. **15 - Cirurgias**: CRUD de cirurgias

## Autenticação

A collection está configurada para usar autenticação Bearer Token. O token é automaticamente salvo no environment após o login bem-sucedido através do script de teste no endpoint de Login.

## Variáveis de Environment

- `base_url`: URL base da API
- `auth_token`: Token de autenticação (preenchido automaticamente após login)
- `server_port`: Porta do servidor
- `context_path`: Caminho do contexto da API

## Notas

- Todos os endpoints que requerem autenticação usam o token armazenado em `{{auth_token}}`
- Os endpoints de paginação usam parâmetros padrão: `page=0`, `size=20`
- Os IDs nos exemplos são placeholders (`00000000-0000-0000-0000-000000000000`) e devem ser substituídos por IDs reais
- A collection v2 reflete a nova arquitetura orientada a casos de uso, onde os controllers representam fluxos de negócio ao invés de CRUD genérico

