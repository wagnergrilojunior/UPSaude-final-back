# Módulo ANEXOS — UPSaúde (Documentação Completa)

Esta documentação cobre o **módulo de Anexos Centralizados** com foco em:

- **Visão Geral**: conceito, arquitetura e objetivos do módulo
- **Negócio**: regras de negócio, casos de uso e governança
- **Técnico**: arquitetura técnica, modelo de dados e integrações
- **Endpoints**: catálogo completo de endpoints REST
- **Exemplos**: exemplos práticos de uso

## 📋 Índice

1. [Visão Geral](#visão-geral)
2. [Estrutura da Documentação](#estrutura-da-documentação)
3. [Início Rápido](#início-rápido)
4. [Leitura Recomendada](#leitura-recomendada)

---

## Visão Geral

O módulo de **Anexos Centralizados** permite anexar arquivos (PDF, imagens, documentos, etc.) a qualquer entidade do sistema de forma unificada e independente do serviço. Os arquivos são armazenados no **Supabase Storage** (bucket privado) e os metadados são persistidos no banco de dados PostgreSQL.

### Características Principais

- ✅ **Centralizado**: Um único módulo para todos os tipos de anexos
- ✅ **Genérico**: Vincula anexos a qualquer entidade (paciente, agendamento, atendimento, consulta, etc.)
- ✅ **Seguro**: Bucket privado, URLs assinadas temporárias, validação de permissões
- ✅ **Multitenancy**: Isolamento completo por tenant
- ✅ **Auditoria**: Rastreabilidade completa (quem criou, quando, quem excluiu)
- ✅ **Miniaturas**: Suporte a thumbnails para imagens
- ✅ **Gestão Completa**: Endpoint dedicado para administração com filtros avançados

### Entidades Suportadas

- **PACIENTE** - Documentos do paciente (RG, CPF, carteirinha, etc.)
- **AGENDAMENTO** - Anexos relacionados a agendamentos
- **ATENDIMENTO** - Documentos de atendimento/triagem
- **CONSULTA** - Laudos, exames, receitas, atestados
- **PRONTUARIO_EVENTO** - Eventos do prontuário
- **PROFISSIONAL_SAUDE** - Documentos de profissionais
- **USUARIO_SISTEMA** - Documentos de usuários (além da foto)
- **FINANCEIRO_FATURAMENTO** - Documentos financeiros

---

## Estrutura da Documentação

```
docs/ANEXOS/
├── README.md                    # Este arquivo (visão geral)
├── TECNICO.md                  # Arquitetura técnica e modelo de dados
├── NEGOCIO.md                  # Regras de negócio e casos de uso
├── ENDPOINTS.md                # Catálogo completo de endpoints REST
└── EXEMPLOS.md                 # Exemplos práticos de uso
```

---

## Início Rápido

### 1. Upload de Anexo

```bash
curl -X POST "http://localhost:8080/api/v1/anexos" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: multipart/form-data" \
  -F "file=@documento.pdf" \
  -F "targetType=PACIENTE" \
  -F "targetId=<UUID_PACIENTE>" \
  -F "categoria=DOCUMENTO" \
  -F "visivelParaPaciente=true" \
  -F "descricao=Documento de identidade"
```

### 2. Listar Anexos

```bash
curl -X GET "http://localhost:8080/api/v1/anexos?targetType=PACIENTE&targetId=<UUID_PACIENTE>" \
  -H "Authorization: Bearer <TOKEN>"
```

### 3. Download Direto

```bash
curl -X GET "http://localhost:8080/api/v1/anexos/<UUID_ANEXO>/download" \
  -H "Authorization: Bearer <TOKEN>" \
  --output documento.pdf
```

### 4. Obter Miniatura (Imagens)

```bash
curl -X GET "http://localhost:8080/api/v1/anexos/<UUID_ANEXO>/thumbnail?width=200&height=200" \
  -H "Authorization: Bearer <TOKEN>"
```

### 5. Gestão Completa

```bash
curl -X GET "http://localhost:8080/api/v1/anexos/gestao?targetType=PACIENTE&status=ATIVO&page=0&size=20" \
  -H "Authorization: Bearer <TOKEN>"
```

---

## Leitura Recomendada

1. **Técnico**: [TECNICO.md](./TECNICO.md) - Entenda a arquitetura e modelo de dados
2. **Negócio**: [NEGOCIO.md](./NEGOCIO.md) - Conheça as regras de negócio e casos de uso
3. **Endpoints**: [ENDPOINTS.md](./ENDPOINTS.md) - Explore todos os endpoints disponíveis
4. **Exemplos**: [EXEMPLOS.md](./EXEMPLOS.md) - Veja exemplos práticos de integração

---

## Pré-requisitos

- **Base URL**: `http://localhost:8080/api` (ou conforme configuração)
- **Autenticação**: `Authorization: Bearer <TOKEN>`
- **Tenant**: Resolvido automaticamente via usuário autenticado
- **Supabase Storage**: Configurado e acessível

---

## Status do Módulo

✅ **Implementado e Funcional**

- Upload de arquivos
- Download direto e via URL assinada
- Listagem com filtros
- Gestão completa (admin)
- Miniaturas para imagens
- Validação de permissões
- Multitenancy
- Auditoria completa

---

## Suporte

Para dúvidas ou problemas, consulte:
- [TECNICO.md](./TECNICO.md) - Troubleshooting técnico
- [NEGOCIO.md](./NEGOCIO.md) - Regras de negócio e validações
- [EXEMPLOS.md](./EXEMPLOS.md) - Exemplos práticos
