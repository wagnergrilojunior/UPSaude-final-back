version: 1.0
description: >
  Regras oficiais de validação do UPSaude.
  Usado pelo Cursor/IA para garantir consistência e segurança
  em todas as camadas (Request, Service, Domain).

validation:
  philosophy:
    - Toda validação de entrada é responsabilidade da camada Request (Bean Validation)
    - Toda validação de negócio pertence ao Service
    - Nenhuma validação deve ser colocada dentro de Entity (exceto constraints de banco)
    - Services devem lançar BadRequestException para dados inválidos
    - IA deve aplicar estas regras automaticamente ao gerar Requests/Services

# ==========================================================
#   1. VALIDAÇÕES PADRÃO JAVAX/JAKARTA BEAN VALIDATION
# ==========================================================
standard_rules:
  not_null:
    - "@NotNull deve ser aplicado a campos obrigatórios"
    - Campos obrigatórios nunca podem ser opcionais no Request
  not_blank:
    - "@NotBlank para Strings obrigatórias"
    - Não usar NotEmpty quando NotBlank é mais preciso
  size:
    - Strings devem sempre ter limite máximo
    - O limite deve ser coerente com o banco
  pattern:
    - Usado para validações estruturais simples
  valid_embeddable:
    - "@Valid deve ser aplicado para Requests que possuem embeddables ou listas"

# ==========================================================
#   2. VALIDAÇÕES DE DOCUMENTOS
# ==========================================================

documents:
  cpf:
    description: "CPF válido seguindo regra oficial (11 dígitos + DV)"
    rules:
      - Deve conter apenas números
      - Deve ter exatamente 11 dígitos
      - Validar dígitos verificadores (algoritmo oficial)
      - Não aceitar sequências repetidas (111.111.111-11)
    annotations:
      - "@CPF(message = 'CPF inválido')"
    backend_validation:
      - Validar DV no service antes de persistir

  cnpj:
    description: "CNPJ válido (14 dígitos + validar DV)"
    rules:
      - Apenas números
      - 14 dígitos
      - Validar DV oficial
    annotations:
      - "@CNPJ(message = 'CNPJ inválido')"

  rg:
    description: "RG não possui padrão único, mas deve seguir limite"
    rules:
      - Tamanho máximo: 20 caracteres
      - Permitido letras + números
    pattern: "^[0-9A-Za-z.-]{4,20}$"

  cns:
    description: "Validação completa do CNS (cartão SUS)"
    rules:
      - Pode ser tipo 1, 2 ou 3
      - 15 dígitos obrigatórios
      - Validar DV com algoritmo mod 11
      - Tipo 3 deve validar prefixo
    service_validation:
      - conferência com regras oficiais do DATASUS
    annotations:
      - "@Size(min = 15, max = 15, message = 'CNS deve conter 15 dígitos')"

  cnes:
    description: "CNES válido (7 dígitos)"
    rules:
      - Apenas números
      - Tamanho = 7
    pattern: "^[0-9]{7}$"

# ==========================================================
#   3. VALIDAÇÕES DE LOCALIZAÇÃO
# ==========================================================
location:
  cep:
    description: "CEP brasileiro (8 dígitos)"
    pattern: "^[0-9]{8}$"
    annotations:
      - "@Pattern(regexp = '^[0-9]{8}$', message = 'CEP deve conter 8 números')"

  codigo_ibge_municipio:
    description: "Código IBGE de município (7 dígitos)"
    rules:
      - 7 números obrigatórios
      - Validar existência em tabela IBGE local
    pattern: "^[0-9]{7}$"

  codigo_ibge_estado:
    description: "Código UF (2 dígitos)"
    rules:
      - Validar contra lista oficial de UFs
    pattern: "^[0-9]{2}$"

# ==========================================================
#   4. VALIDAÇÕES DE CONTATO
# ==========================================================
contact:
  email:
    rules:
      - Deve ser válido conforme RFC 5322
      - Deve possuir limite máximo (255 chars)
    annotation:
      - "@Email(message='Email inválido')"

  telefone:
    description: "Telefone brasileiro com DDD"
    examples: "DDD + 8 ou 9 dígitos → 31988887777"
    pattern: "^[1-9]{2}[0-9]{8,9}$"
    annotations:
      - "@Pattern(regexp='^[1-9]{2}[0-9]{8,9}$', message='Telefone inválido')"

# ==========================================================
#   5. VALIDAÇÕES DE ENDEREÇOS
# ==========================================================
address:
  street:
    max_length: 255
  number:
    pattern: "^[0-9A-Za-z/-]{1,10}$"
  bairro:
    max_length: 150
  complemento:
    max_length: 150

# ==========================================================
#   6. VALIDAÇÕES DE DATAS E HORÁRIOS
# ==========================================================
dates:
  must_be_future:
    annotation: "@Future(message='Data deve ser futura')"
  must_be_past:
    annotation: "@Past(message='Data deve ser passada')"
  must_be_present_or_future:
    annotation: "@FutureOrPresent"
  consistency_rules:
    - dataInicio <= dataFim
    - dataNascimento não pode ser futura
    - datas de agendamento devem ter duração válida
    - horários devem respeitar abertura/fechamento da unidade
  domain_specific:
    paciente:
      - dataNascimento deve ser realista (>= 1900)
    profissional:
      - dataAdmissao <= hoje
    agendamento:
      - dataHoraFim > dataHora
      - agendamento não pode conflitar com outro no mesmo horário

# ==========================================================
#   7. VALIDAÇÕES DE SAÚDE (DOMÍNIO SUS)
# ==========================================================
sus_domain:
  paciente:
    - CPF ou CNS deve existir
    - CNS deve ser validado pelo algoritmo
    - SexoEnum não pode ser nulo
    - RacaCorEnum obrigatório
    - StatusPacienteEnum obrigatório
    - Endereço deve ser válido

  profissional:
    - CRO, CRM, COREN etc. devem respeitar padrões estaduais
    - Registro profissional deve obrigatoriamente ter UF
    - Validação de horário de atendimento

  estabelecimento:
    - CNES obrigatório
    - Telefone válido
    - Endereço completo obrigatório

  atendimento:
    - Não pode existir sem paciente válido
    - Não pode existir sem profissional válido
    - Deve validar classificação de risco
    - Deve validar procedimentos

# ==========================================================
#   8. VALIDAÇÕES DE NEGÓCIO
# ==========================================================
business_rules:
  duplicidade:
    rules:
      - Service deve validar duplicidade:
          - Nome
          - Código interno
          - Documentos (CPF, CNPJ, CNS)
    pattern_methods:
      - existsByCampo
      - existsByCampoAndIdNot

  soft_delete:
    rules:
      - Não permitir excluir registro ativo se já está inativo
      - active = false ao excluir
      - Deve validar dependências antes de desativar

  domain_consistency:
    - Entidade sempre deve ter estado válido após update
    - Embeddables devem estar completos
    - Não permitir valores inconsistentes (ex: alergia com tipo grave mas sem reação definida)

# ==========================================================
#   9. VALIDAÇÕES EM LISTAS
# ==========================================================
list_validations:
  - Listas devem usar @Valid
  - Tamanho máximo deve ser definido quando aplicável
  - Não pode aceitar listas null → usar lista vazia

# ==========================================================
#   10. REGRAS PARA IA (CURSOR)
# ==========================================================
ai_rules:
  - IA deve aplicar automaticamente todas as validações acima ao gerar Requests
  - IA deve avisar quando um campo obrigatório não tiver validação
  - IA não deve criar Entities com validações Bean Validation
  - IA deve sempre gerar erros 400 para validações
  - IA deve sugerir validações corretas sempre que detectar inconsistência
  - IA deve cruzar regras deste arquivo com domain-rules.yaml para validações mais avançadas
