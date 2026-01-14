# Documentação de Negócio — Módulo de Anexos

## Visão Geral de Negócio

O módulo de anexos permite que profissionais de saúde e administradores anexem documentos digitais a qualquer entidade do sistema, facilitando o gerenciamento de documentos clínicos, administrativos e legais de forma centralizada e segura.

---

## Casos de Uso

### 1. Anexar Documento ao Paciente

**Cenário**: Profissional precisa anexar documento de identidade (RG) ao cadastro do paciente.

**Fluxo**:
1. Usuário seleciona arquivo (PDF ou imagem)
2. Sistema valida permissão de acesso ao paciente
3. Sistema faz upload para Supabase Storage
4. Sistema cria registro de anexo vinculado ao paciente
5. Documento fica disponível para visualização/download

**Benefícios**:
- Documentos organizados por paciente
- Histórico completo de documentos
- Acesso controlado por permissões

### 2. Anexar Laudo/Exame à Consulta

**Cenário**: Médico precisa anexar resultado de exame à consulta realizada.

**Fluxo**:
1. Médico acessa consulta específica
2. Faz upload do laudo/exame (PDF ou imagem)
3. Categoria: LAUDO ou EXAME
4. Sistema vincula anexo à consulta
5. Laudo fica disponível no prontuário do paciente

**Benefícios**:
- Laudos vinculados diretamente à consulta
- Facilita visualização no contexto clínico
- Histórico completo de exames

### 3. Anexar Receita/Atestado

**Cenário**: Médico precisa anexar receita médica ou atestado à consulta.

**Fluxo**:
1. Médico finaliza consulta
2. Gera receita/atestado (PDF)
3. Faz upload como anexo da consulta
4. Categoria: RECEITA ou ATESTADO
5. Pode marcar como `visivelParaPaciente=true` para acesso do paciente

**Benefícios**:
- Receitas/atestados organizados por consulta
- Paciente pode acessar via portal/app
- Reduz necessidade de impressão física

### 4. Gestão Administrativa de Arquivos

**Cenário**: Administrador precisa visualizar todos os anexos do sistema para auditoria.

**Fluxo**:
1. Administrador acessa endpoint de gestão
2. Aplica filtros (tipo, status, período, usuário)
3. Visualiza lista completa com metadados
4. Pode fazer download, atualizar ou excluir

**Benefícios**:
- Visão completa do sistema
- Auditoria e compliance
- Gestão centralizada

### 5. Visualização de Miniaturas

**Cenário**: Usuário precisa visualizar preview de imagens anexadas.

**Fluxo**:
1. Sistema identifica que anexo é imagem
2. Gera URL assinada da miniatura (200x200px)
3. Frontend exibe miniatura na listagem
4. Ao clicar, abre imagem em tamanho original

**Benefícios**:
- Interface mais amigável
- Carregamento rápido
- Melhor experiência do usuário

---

## Regras de Negócio

### 1. Permissões de Acesso

**RN-001**: Usuário só pode anexar arquivos a recursos que tem permissão de acesso.

- Validação: `AnexoTargetPermissionValidator`
- Exemplo: Usuário só pode anexar a paciente se tiver acesso aquele paciente

**RN-002**: Usuário só pode visualizar/baixar anexos de recursos que tem permissão de acesso.

- Validação: Sempre antes de download/listagem
- Exceção: Anexos marcados como `visivelParaPaciente=true` podem ser acessados pelo próprio paciente

### 2. Visibilidade para Paciente

**RN-003**: Apenas anexos marcados como `visivelParaPaciente=true` são visíveis no portal/app do paciente.

- Padrão: `false` (não visível)
- Uso: Receitas, atestados, exames que o paciente pode ver

**RN-004**: Anexos internos (visivelParaPaciente=false) não aparecem no portal do paciente.

- Proteção: Documentos administrativos, observações internas

### 3. Status do Anexo

**RN-005**: Anexo inicia com status `PENDENTE` durante upload.

- Transição: `PENDENTE` → `ATIVO` (após upload bem-sucedido)
- Erro: Se upload falhar, anexo permanece `PENDENTE`

**RN-006**: Anexos com status `EXCLUIDO` não podem ser baixados.

- Soft delete: Arquivo permanece no storage
- Hard delete: Opcional via parâmetro `deleteFromStorage=true`

**RN-007**: Anexos `INATIVOS` não aparecem em listagens padrão.

- Uso: Arquivos temporariamente ocultos
- Recuperação: Pode ser reativado alterando status para `ATIVO`

### 4. Categorização

**RN-008**: Categoria é opcional, padrão é `OUTROS`.

- Categorias disponíveis: LAUDO, EXAME, DOCUMENTO, IMAGEM, RECEITA, ATESTADO, ENCAMINHAMENTO, OUTROS
- Uso: Facilita filtros e organização

**RN-009**: Categoria pode ser alterada após criação.

- Via endpoint: `PATCH /v1/anexos/{id}`
- Uso: Correção de categorização incorreta

### 5. Tamanho e Tipo de Arquivo

**RN-010**: Tamanho máximo de arquivo é 50MB.

- Validação: No upload
- Erro: "Arquivo muito grande. Tamanho máximo: 50MB"

**RN-011**: Tipo MIME é validado no upload.

- Aceita: Qualquer tipo MIME válido
- Recomendado: PDF, imagens (JPEG, PNG), documentos (DOC, DOCX)

**RN-012**: Miniaturas só estão disponíveis para imagens.

- Validação: MIME type deve começar com `image/`
- Erro: "Thumbnail só está disponível para arquivos de imagem"

### 6. Multitenancy

**RN-013**: Anexos são isolados por tenant.

- Validação: Sempre verifica tenant do usuário autenticado
- Storage: Caminho inclui tenantId

**RN-014**: Usuário não pode acessar anexos de outros tenants.

- Proteção: Validação em todas as operações
- RLS: Políticas PostgreSQL garantem isolamento

### 7. Auditoria

**RN-015**: Sistema registra quem criou cada anexo.

- Campo: `criadoPor` (UUID do usuário)
- Timestamp: `createdAt`

**RN-016**: Sistema registra quem excluiu cada anexo.

- Campo: `excluidoPor` (UUID do usuário)
- Timestamp: Mantido em `updatedAt`

**RN-017**: Histórico de alterações é mantido.

- Campo: `updatedAt` atualizado em cada modificação
- Logs: Todas as operações são logadas

### 8. Storage e URLs

**RN-018**: Arquivos são armazenados em bucket privado.

- Bucket: `anexos` (privado)
- Acesso: Apenas via API com autenticação

**RN-019**: URLs assinadas expiram em 5 minutos (padrão).

- Configurável: Parâmetro `expiresIn` (segundos)
- Uso: Download temporário sem expor bucket

**RN-020**: Download direto sempre disponível via API.

- Endpoint: `GET /v1/anexos/{id}/download`
- Streaming: Não carrega arquivo inteiro em memória

---

## Integração com Outros Módulos

### Prontuário

**Integração**: Anexos podem ser vinculados a eventos do prontuário.

- Target Type: `PRONTUARIO_EVENTO`
- Uso: Documentos relacionados a eventos específicos do prontuário

**Visualização**: Anexos aparecem no timeline do prontuário quando vinculados a eventos.

### Paciente

**Integração**: Anexos vinculados ao paciente aparecem no cadastro.

- Target Type: `PACIENTE`
- Uso: Documentos pessoais (RG, CPF, carteirinha, etc.)

### Consulta/Atendimento

**Integração**: Anexos vinculados a consultas/atendimentos aparecem no contexto clínico.

- Target Types: `CONSULTA`, `ATENDIMENTO`
- Uso: Laudos, exames, receitas, atestados

### Financeiro

**Integração**: Anexos podem ser vinculados a documentos financeiros.

- Target Type: `FINANCEIRO_FATURAMENTO`
- Uso: Comprovantes, notas fiscais, guias

---

## Governança e Compliance

### LGPD

**Proteção de Dados**: Anexos podem conter dados pessoais sensíveis.

- **Retenção**: Arquivos são mantidos mesmo após exclusão lógica (soft delete)
- **Acesso**: Controlado por permissões e multitenancy
- **Auditoria**: Rastreabilidade completa de quem acessou/modificou

### Auditoria

**Rastreabilidade**: Todas as operações são registradas.

- Upload: `criadoPor`, `createdAt`
- Download: Logado no sistema
- Exclusão: `excluidoPor`, `updatedAt`
- Alteração: `updatedAt`

### Retenção

**Política**: Arquivos não são deletados automaticamente do storage.

- Soft delete: Padrão (mantém arquivo)
- Hard delete: Opcional via parâmetro
- Recomendação: Implementar política de retenção conforme necessidade

---

## Limitações e Considerações

### Limitações Atuais

1. **Thumbnails**: Apenas URLs assinadas (não há processamento de imagem no backend)
2. **Deduplicação**: Checksum calculado mas não usado para deduplicação automática
3. **Versionamento**: Não há controle de versões do mesmo arquivo
4. **Compartilhamento**: Anexos não podem ser compartilhados entre tenants

### Melhorias Futuras

1. **Processamento de Imagens**: Gerar thumbnails no backend
2. **Deduplicação**: Usar checksum para evitar uploads duplicados
3. **Versionamento**: Permitir múltiplas versões do mesmo documento
4. **Compartilhamento**: Permitir compartilhamento controlado entre tenants
5. **OCR**: Extração de texto de PDFs/imagens
6. **Antivírus**: Varredura de arquivos antes do armazenamento

---

## Métricas e Monitoramento

### Métricas Recomendadas

- Total de anexos por tenant
- Tamanho total de armazenamento por tenant
- Taxa de upload/download por dia
- Tipos de arquivo mais comuns
- Anexos por categoria
- Taxa de exclusão

### Logs Importantes

- Upload: `anexoId`, `targetType`, `targetId`, `fileName`, `size`, `userId`
- Download: `anexoId`, `userId`, `method` (direto/url-assinada)
- Exclusão: `anexoId`, `userId`, `deleteFromStorage`
- Erros: Detalhes completos de falhas

---

## Glossário

- **Anexo**: Arquivo digital vinculado a uma entidade do sistema
- **Target**: Entidade alvo do anexo (paciente, consulta, etc.)
- **Bucket**: Container de armazenamento no Supabase Storage
- **URL Assinada**: URL temporária para download sem expor bucket privado
- **Thumbnail**: Miniatura de imagem para preview
- **Soft Delete**: Exclusão lógica (mantém arquivo no storage)
- **Hard Delete**: Exclusão física (remove arquivo do storage)
- **Checksum**: Hash SHA-256 do arquivo para integridade
