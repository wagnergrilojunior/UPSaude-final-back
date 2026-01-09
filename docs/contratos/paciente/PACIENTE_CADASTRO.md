# Contrato de Cadastro de Paciente

## 1. Visão Geral do Cadastro

O cadastro de paciente é realizado através do endpoint `POST /v1/pacientes`, que recebe um objeto `PacienteRequest` e cria uma nova entidade `Paciente` no sistema.

**Endpoint:** `POST /v1/pacientes`  
**Content-Type:** `application/json`  
**Resposta de Sucesso:** `201 Created` com `PacienteResponse`  
**Respostas de Erro:** `400 Bad Request`, `403 Forbidden`, `409 Conflict`

O sistema aplica validações em múltiplas camadas:
- **Bean Validation** (@Valid, @NotBlank, @NotNull, @Size, @Pattern, etc.)
- **Validações customizadas** (CPFValido, CNSValido, EmailValido, etc.)
- **Validações de negócio** (PacienteValidationService)
- **Validações de integridade** (constraints de banco de dados)

---

## 2. Campos Obrigatórios no Cadastro Inicial

### 2.1. Dados Pessoais Básicos (`dadosPessoaisBasicos`)

**OBRIGATÓRIO:** O objeto `dadosPessoaisBasicos` é obrigatório e deve conter:

| Campo | Tipo | Validação | Descrição |
|-------|------|-----------|-----------|
| `nomeCompleto` | String | `@NotBlank`, `@Pattern("^[\\p{L}0-9 .'-]+$")`, `@Size(max=255)` | Nome completo do paciente. Aceita letras, números, espaços, pontos, hífens e apóstrofos. |
| `sexo` | SexoEnum | `@NotNull` | Sexo do paciente. Enum obrigatório. |

**OPCIONAL:**
- `nomeSocial`: String, máximo 255 caracteres
- `dataNascimento`: LocalDate (formato ISO: YYYY-MM-DD)

### 2.2. Campos da Entidade Paciente (aplicados automaticamente)

| Campo | Valor Default | Descrição |
|-------|---------------|-----------|
| `statusPaciente` | `StatusPacienteEnum.ATIVO` | Status do paciente. Se não fornecido, assume ATIVO. |
| `ativo` | `true` | Flag de ativação. Sempre definido como `true` no cadastro. |
| `id` | UUID gerado automaticamente | Identificador único gerado pelo sistema. |
| `createdAt` | OffsetDateTime gerado automaticamente | Data/hora de criação (auditoria). |
| `updatedAt` | OffsetDateTime gerado automaticamente | Data/hora de atualização (auditoria). |

---

## 3. Campos Opcionais

### 3.1. Documentos Básicos (`documentosBasicos`)

| Campo | Tipo | Validação | Descrição |
|-------|------|-----------|-----------|
| `cpf` | String | `@CPFValido` | CPF válido (formato e dígitos verificadores). |
| `rg` | String | `@Size(max=20)` | RG, máximo 20 caracteres. |
| `cns` | String | `@CNSValido` | CNS válido (15 dígitos). |
| `orgaoEmissorRg` | String | `@Size(max=10)` | Órgão emissor do RG. |
| `ufEmissorRg` | String | `@Size(max=2)` | UF de emissão do RG. |

**Comportamento:**
- Se `cpf` fornecido: cria `PacienteIdentificador` com `tipo=CPF`, `principal=true`
- Se `rg` fornecido: cria `PacienteIdentificador` com `tipo=RG`, `principal=false`
- Se `cns` fornecido: cria `PacienteIdentificador` com `tipo=CNS`, `principal=false`

### 3.2. Contato Básico (`contato`)

| Campo | Tipo | Validação | Descrição |
|-------|------|-----------|-----------|
| `telefone` | String | `@TelefoneValido`, `@Size(max=20)` | Telefone fixo válido. |
| `celular` | String | `@CelularValido`, `@Size(max=20)` | Celular válido. |
| `email` | String | `@EmailValido`, `@Size(max=255)` | Email válido. |

**Comportamento:**
- Se `telefone` fornecido: cria `PacienteContato` com `tipo=TELEFONE`
- Se `celular` fornecido: cria `PacienteContato` com `tipo=WHATSAPP`
- Se `email` fornecido: cria `PacienteContato` com `tipo=EMAIL`

### 3.3. Dados Demográficos (`dadosDemograficos`)

| Campo | Tipo | Validação | Descrição |
|-------|------|-----------|-----------|
| `escolaridade` | EscolaridadeEnum | - | Nível de escolaridade. |
| `nacionalidade` | NacionalidadeEnum | - | Nacionalidade. |
| `naturalidade` | String | `@Pattern("^$|^[\\p{L}0-9 .'-]+$")`, `@Size(max=100)` | Naturalidade. |
| `paisNascimento` | String | `@Pattern("^$|^[\\p{L}0-9 .'-]+$")`, `@Size(max=100)` | País de nascimento. |
| `municipioNascimentoIbge` | String | `@Size(max=7)` | Código IBGE do município de nascimento. |
| `racaCor` | RacaCorEnum | - | Raça/Cor. |
| `ocupacaoProfissao` | String | `@Size(max=150)` | Ocupação/Profissão. |
| `situacaoRua` | Boolean | - | Se o paciente está em situação de rua. Default: `false`. |
| `identidadeGenero` | IdentidadeGeneroEnum | - | Identidade de gênero. |
| `orientacaoSexual` | OrientacaoSexualEnum | - | Orientação sexual. |

**Comportamento:**
- Se fornecido, cria/atualiza `DadosSociodemograficos` e `PacienteDadosPessoaisComplementares`
- `situacaoRua` default: `false` se não fornecido

### 3.4. Convênio (`convenio`)

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `convenio` | UUID | ID do convênio (deve existir no sistema). |
| `informacoesConvenio` | InformacoesConvenioPacienteRequest | Informações do convênio. |

**InformacoesConvenioPacienteRequest:**
- `numeroCarteirinha`: String, máximo 50 caracteres
- `dataValidadeCarteirinha`: LocalDate

**Regra:** Se `convenio` for `null`, o campo `informacoesConvenio` é automaticamente definido como `null` (via `@PrePersist`/`@PreUpdate`).

### 3.5. Outros Campos Opcionais

| Campo | Tipo | Descrição |
|-------|------|-----------|
| `statusPaciente` | StatusPacienteEnum | Status do paciente. Default: `ATIVO`. |
| `tipoAtendimentoPreferencial` | TipoAtendimentoPreferencialEnum | Tipo de atendimento preferencial. |
| `observacoes` | String | Observações gerais (TEXT, sem limite explícito). |
| `enderecoPrincipal` | EnderecoRequest | Endereço principal do paciente. |
| `dadosSociodemograficos` | DadosSociodemograficosRequest | Dados sociodemográficos completos. |
| `dadosClinicosBasicos` | DadosClinicosBasicosRequest | Dados clínicos básicos. |
| `dadosPessoaisComplementares` | PacienteDadosPessoaisComplementaresRequest | Dados pessoais complementares. |
| `responsavelLegal` | ResponsavelLegalPacienteRequest | Responsável legal. |
| `obito` | PacienteObitoRequest | Informações de óbito. |
| `integracaoGov` | IntegracaoGovPacienteRequest | Dados de integração governamental. |
| `lgpdConsentimento` | LGPDConsentimentoRequest | Consentimento LGPD. |

---

## 4. Campos Condicionais (Regras IF / THEN)

### 4.1. Informações de Convênio

**IF** `convenio` é fornecido (não null)  
**THEN** `informacoesConvenio` pode ser fornecido  
**ELSE** `informacoesConvenio` é automaticamente definido como `null` (via `@PrePersist`/`@PreUpdate`)

### 4.2. Óbito

**IF** `obito` é fornecido (objeto não null)  
**THEN** `obito.dataObito` é **OBRIGATÓRIO**  
**ELSE** Nenhuma validação de óbito é aplicada

**Validação:**
- Se `obito` fornecido e `dataObito` null → `BadRequestException: "Data de óbito é obrigatória quando informações de óbito são fornecidas"`

**Comportamento:**
- Se `obito` fornecido vazio (todos campos null/vazios) → remove óbito existente se houver
- Se `obito` não fornecido (null) → remove óbito existente se houver
- Se `obito` fornecido com dados válidos → cria/atualiza `PacienteObito`

### 4.3. Responsável Legal

**IF** `responsavelLegal` é fornecido  
**AND** todos os campos estão vazios/null (`nome`, `cpf`, `telefone`)  
**THEN** remove responsável legal existente se houver  
**ELSE** cria/atualiza `ResponsavelLegal`

### 4.4. Endereço Principal

**IF** `enderecoPrincipal` é fornecido  
**THEN** cria/atualiza `PacienteEndereco` com o endereço fornecido  
**ELSE** nenhuma ação

**Comportamento:**
- Busca endereço existente pelo conteúdo ou cria novo
- Adiciona à lista de endereços do paciente

---

## 5. Estruturas Obrigatórias (Listas, Objetos Embutidos)

### 5.1. Listas (sempre inicializadas como ArrayList vazio)

As seguintes listas são **sempre inicializadas** como `ArrayList` vazio, mesmo se não fornecidas:

- `enderecos`: List<PacienteEnderecoRequest> (default: `new ArrayList<>()`)
- `identificadores`: List<PacienteIdentificadorRequest> (default: `new ArrayList<>()`)
- `contatos`: List<PacienteContatoRequest> (default: `new ArrayList<>()`)
- `deficiencias`: List<DeficienciasPacienteRequest> (default: `new ArrayList<>()`)
- `vinculosTerritoriais`: List<PacienteVinculoTerritorialRequest> (default: `new ArrayList<>()`)

**Comportamento:**
- Se não fornecidas no request, são inicializadas como listas vazias
- Se fornecidas como `null`, são inicializadas como listas vazias (via `@PrePersist`/`@PreUpdate`)

### 5.2. Objetos OneToOne (criados sob demanda)

Os seguintes objetos são criados apenas se fornecidos no request:

- `dadosSociodemograficos`: DadosSociodemograficos (criado se `dadosSociodemograficos` ou `dadosDemograficos` fornecidos)
- `dadosClinicosBasicos`: DadosClinicosBasicos (criado se `dadosClinicosBasicos` fornecido)
- `dadosPessoaisComplementares`: PacienteDadosPessoaisComplementares (criado se `dadosPessoaisComplementares` ou `dadosDemograficos` fornecidos)
- `responsavelLegal`: ResponsavelLegal (criado se `responsavelLegal` fornecido e não vazio)
- `lgpdConsentimento`: LGPDConsentimento (criado se `lgpdConsentimento` fornecido)
- `obito`: PacienteObito (criado se `obito` fornecido com dados válidos)

---

## 6. Regras de Relacionamento e Cascata

### 6.1. Relacionamentos com CascadeType.ALL e orphanRemoval=true

Os seguintes relacionamentos são **gerenciados automaticamente** (criação, atualização e remoção):

- `enderecos`: List<PacienteEndereco> - `CascadeType.ALL`, `orphanRemoval=true`
- `identificadores`: List<PacienteIdentificador> - `CascadeType.ALL`, `orphanRemoval=true`
- `contatos`: List<PacienteContato> - `CascadeType.ALL`, `orphanRemoval=true`
- `deficiencias`: List<DeficienciasPaciente> - `CascadeType.ALL`, `orphanRemoval=true`
- `dadosSociodemograficos`: DadosSociodemograficos - `CascadeType.ALL`, `orphanRemoval=true`
- `dadosClinicosBasicos`: DadosClinicosBasicos - `CascadeType.ALL`, `orphanRemoval=true`
- `responsavelLegal`: ResponsavelLegal - `CascadeType.ALL`, `orphanRemoval=true`
- `lgpdConsentimento`: LGPDConsentimento - `CascadeType.ALL`, `orphanRemoval=true`
- `dadosPessoaisComplementares`: PacienteDadosPessoaisComplementares - `CascadeType.ALL`, `orphanRemoval=true`
- `obito`: PacienteObito - `CascadeType.ALL`, `orphanRemoval=true`

**Comportamento:**
- Ao remover o paciente, todos os relacionamentos acima são removidos automaticamente
- Ao atualizar, itens removidos da lista são deletados do banco

### 6.2. Relacionamentos com CascadeType.PERSIST, MERGE (sem orphanRemoval)

- `vinculosTerritoriais`: List<PacienteVinculoTerritorial> - `CascadeType.PERSIST, MERGE`
- `integracoesGov`: List<IntegracaoGov> - `CascadeType.PERSIST, MERGE`

**Comportamento:**
- Criados e atualizados junto com o paciente
- **NÃO são removidos automaticamente** se removidos da lista (requer remoção explícita)

### 6.3. Relacionamento ManyToOne (sem cascade)

- `convenio`: Convenio - `FetchType.LAZY`, sem cascade

**Comportamento:**
- Deve existir no banco antes de ser associado
- Se `convenio` fornecido não existir, a associação não é feita (silenciosamente ignorada)

---

## 7. Regras de Unicidade e Integridade

### 7.1. Constraints de Banco de Dados

#### PacienteIdentificador
- **Unique Constraint:** `uk_paciente_identificador_tipo_valor_tenant` (tipo, valor, tenant_id)
  - **Regra:** Não pode existir dois identificadores com o mesmo `tipo` e `valor` no mesmo `tenant`

#### PacienteObito
- **Unique Constraint:** `uk_paciente_obito_paciente` (paciente_id)
  - **Regra:** Um paciente pode ter apenas um registro de óbito

#### PacienteDadosPessoaisComplementares
- **Unique Constraint:** `uk_paciente_dados_pessoais_complementares_paciente` (paciente_id)
  - **Regra:** Um paciente pode ter apenas um registro de dados pessoais complementares

#### LGPDConsentimento
- **Unique Constraint:** `uk_paciente_lgpd_consentimento_paciente` (paciente_id) - implícito via `@JoinColumn(unique=true)`
  - **Regra:** Um paciente pode ter apenas um registro de consentimento LGPD

### 7.2. Validações de Unicidade no Service

**IMPORTANTE:** O método `validarUnicidadeParaCriacao` em `PacienteValidationService` está **vazio** (não implementado).

**Métodos privados existentes (não chamados no cadastro):**
- `validarCpfUnico`: Valida se CPF já existe em outro paciente (mesmo tenant)
- `validarCnsUnico`: Valida se CNS já existe em outro paciente (mesmo tenant)
- `validarRgUnico`: Valida se RG já existe em outro paciente (mesmo tenant)
- `validarEmailUnico`: Valida se email já existe em outro paciente (mesmo tenant)

**Status Atual:** Validações de unicidade de CPF, CNS, RG e Email **NÃO são executadas no cadastro**.

**Regra implícita / depende de service:** A validação de unicidade de documentos e contatos deve ser implementada no `PacienteValidationService.validarUnicidadeParaCriacao`.

### 7.3. Validações de Integridade Referencial

- **Convenio:** Se `convenio` fornecido, deve existir no banco. Se não existir, a associação não é feita (silenciosamente ignorada).
- **Deficiencia:** Se `deficiencias` fornecidas, cada `deficiencia.id` deve existir. Se não existir → `NotFoundException: "Deficiência não encontrada com ID: {id}"`

---

## 8. Regras LGPD Aplicáveis ao Cadastro

### 8.1. Consentimento LGPD (`lgpdConsentimento`)

**Opcional no cadastro:** O consentimento LGPD pode ser fornecido no cadastro, mas não é obrigatório.

**Estrutura:**
- `paciente`: UUID (obrigatório se fornecido)
- `autorizacaoUsoDados`: Boolean (default: `false`)
- `autorizacaoContatoWhatsApp`: Boolean (default: `false`)
- `autorizacaoContatoEmail`: Boolean (default: `false`)
- `dataConsentimento`: LocalDateTime

**Comportamento:**
- Se fornecido no cadastro, cria `LGPDConsentimento` associado ao paciente
- Se não fornecido, nenhum consentimento é criado
- Um paciente pode ter apenas um consentimento (constraint única)

**Regra implícita / depende de service:** Não há validação explícita de obrigatoriedade de consentimento no cadastro. A regra de negócio sobre quando o consentimento é obrigatório deve ser verificada no service ou na camada de aplicação.

---

## 9. Comportamentos Automáticos do Sistema

### 9.1. Valores Default Aplicados

| Campo | Valor Default | Onde é Aplicado |
|-------|---------------|-----------------|
| `ativo` | `true` | `PacienteCreator.criar()` |
| `statusPaciente` | `StatusPacienteEnum.ATIVO` | `PacienteCreator.criar()` (se null) |
| `id` | UUID gerado | JPA `@GeneratedValue` |
| `createdAt` | OffsetDateTime atual | JPA `@CreatedDate` |
| `updatedAt` | OffsetDateTime atual | JPA `@LastModifiedDate` |
| `PacienteEndereco.ativo` | `true` | `PacienteEndereco.@PrePersist` |
| `PacienteEndereco.principal` | `false` | `PacienteEndereco.@PrePersist` |
| `PacienteIdentificador.validado` | `false` | Campo da entidade |
| `PacienteIdentificador.principal` | `false` | Campo da entidade |
| `DadosSociodemograficos.situacaoRua` | `false` | `PacienteAssociacoesManager.processarDadosDemograficosBasicos()` |

### 9.2. @PrePersist e @PreUpdate

**Paciente.@PrePersist / @PreUpdate:**
```java
validateCollectionsAndConvenioInfo()
```
- Inicializa todas as listas como `ArrayList` se forem `null`
- Se `convenio == null`, define `informacoesConvenio = null`

**PacienteEndereco.@PrePersist:**
```java
prePersist()
```
- Se `ativo == null`, define `ativo = true`
- Se `principal == null`, define `principal = false`

### 9.3. Processamento de Associações

**PacienteAssociacoesManager.processarTodas():**

1. **Documentos Básicos:**
   - CPF → `PacienteIdentificador` com `tipo=CPF`, `principal=true`
   - RG → `PacienteIdentificador` com `tipo=RG`, `principal=false`
   - CNS → `PacienteIdentificador` com `tipo=CNS`, `principal=false`

2. **Contato Básico:**
   - Telefone → `PacienteContato` com `tipo=TELEFONE`
   - Celular → `PacienteContato` com `tipo=WHATSAPP`
   - Email → `PacienteContato` com `tipo=EMAIL`

3. **Endereço Principal:**
   - Busca endereço existente ou cria novo via `EnderecoService.findOrCreate()`
   - Adiciona à lista de endereços do paciente

4. **Listas:**
   - `enderecos`: Limpa lista existente, adiciona novos
   - `identificadores`: Remove itens não presentes no request, atualiza existentes, adiciona novos
   - `contatos`: Remove itens não presentes no request, atualiza existentes, adiciona novos
   - `deficiencias`: Remove itens não presentes no request, atualiza existentes, adiciona novos
   - `vinculosTerritoriais`: Limpa lista existente, adiciona novos

5. **Óbito:**
   - Se fornecido vazio → remove óbito existente
   - Se não fornecido → remove óbito existente
   - Se fornecido com dados → valida `dataObito` obrigatória, cria/atualiza

6. **Responsável Legal:**
   - Se fornecido vazio → remove responsável existente
   - Se fornecido com dados → cria/atualiza

---

## 10. Erros Esperados (400 / 409) e Suas Causas

### 10.1. Erro 400 Bad Request

| Causa | Mensagem | Onde é Validado |
|-------|----------|-----------------|
| `dadosPessoaisBasicos` null | Validação Bean Validation | `@Valid` em `PacienteRequest` |
| `nomeCompleto` null/blank | "Nome completo é obrigatório" | `@NotBlank` em `DadosPessoaisBasicosPacienteRequest` |
| `nomeCompleto` com caracteres inválidos | "Caracteres inválidos no nome completo" | `@Pattern` em `DadosPessoaisBasicosPacienteRequest` |
| `nomeCompleto` > 255 caracteres | "Nome completo deve ter no máximo 255 caracteres" | `@Size` em `DadosPessoaisBasicosPacienteRequest` |
| `sexo` null | "Sexo é obrigatório" | `@NotNull` em `DadosPessoaisBasicosPacienteRequest` |
| `cpf` inválido | Validação `@CPFValido` | Anotação customizada |
| `cns` inválido | Validação `@CNSValido` | Anotação customizada |
| `email` inválido | Validação `@EmailValido` | Anotação customizada |
| `telefone` inválido | Validação `@TelefoneValido` | Anotação customizada |
| `celular` inválido | Validação `@CelularValido` | Anotação customizada |
| `obito` fornecido sem `dataObito` | "Data de óbito é obrigatória quando informações de óbito são fornecidas" | `PacienteValidationService.validarObrigatorios()` |
| `deficiencia.id` não existe | "Deficiência não encontrada com ID: {id}" | `PacienteAssociacoesManager.processarTodas()` |
| `PacienteIdentificador.tipo` null | "Tipo de identificador é obrigatório" | `@NotNull` em `PacienteIdentificadorRequest` |
| `PacienteIdentificador.valor` null/blank | "Valor do identificador é obrigatório" | `@NotBlank` em `PacienteIdentificadorRequest` |
| `PacienteContato.tipo` null | "Tipo de contato é obrigatório" | `@NotNull` em `PacienteContatoRequest` |

### 10.2. Erro 409 Conflict

**IMPORTANTE:** Atualmente, **não há validação de unicidade no cadastro**, então erros 409 relacionados a documentos duplicados **não são retornados**.

**Regra implícita / depende de service:** Se a validação de unicidade for implementada, os seguintes erros 409 podem ocorrer:

| Causa | Mensagem Esperada | Onde Seria Validado |
|-------|-------------------|----------------------|
| CPF duplicado | "Já existe um paciente cadastrado com o CPF: {cpf}" | `PacienteValidationService.validarCpfUnico()` (não chamado) |
| CNS duplicado | "Já existe um paciente cadastrado com o CNS: {cns}" | `PacienteValidationService.validarCnsUnico()` (não chamado) |
| RG duplicado | "Já existe um paciente cadastrado com o RG: {rg}" | `PacienteValidationService.validarRgUnico()` (não chamado) |
| Email duplicado | "Já existe um paciente cadastrado com o email: {email}" | `PacienteValidationService.validarEmailUnico()` (não chamado) |
| LGPD Consentimento duplicado | "Consentimento LGPD já existe para este paciente" | `LGPDConsentimentoCreator.criar()` (se criado separadamente) |

**Constraint de Banco de Dados:**
- Se houver tentativa de criar `PacienteIdentificador` com `tipo`, `valor` e `tenant_id` duplicados → erro de constraint única do banco (geralmente 500 ou 409, dependendo do tratamento)

### 10.3. Erro 403 Forbidden

- Acesso negado (validação de autorização/permissão)

### 10.4. Erro 500 Internal Server Error

- Erro de acesso a dados (`DataAccessException`)
- Erro inesperado no processamento

---

## 11. O Que NÃO é Permitido no Cadastro

### 11.1. Campos Não Permitidos

- **ID do paciente:** Não deve ser fornecido (gerado automaticamente)
- **Datas de auditoria:** `createdAt`, `updatedAt` não devem ser fornecidas (geradas automaticamente)
- **Tenant:** Não deve ser fornecido (obtido do contexto de segurança)

### 11.2. Valores Não Permitidos

- **`ativo = false`:** Sempre definido como `true` no cadastro (ignorado se fornecido)
- **`statusPaciente = null`:** Se fornecido null, assume `ATIVO` automaticamente
- **Listas null:** Todas as listas são inicializadas como vazias (null é convertido para ArrayList vazio)

### 11.3. Estruturas Não Permitidas

- **Óbito sem data:** Se `obito` fornecido, `dataObito` é obrigatória
- **Convênio inexistente:** Se `convenio` fornecido não existir, a associação não é feita (silenciosamente ignorada)
- **Deficiência inexistente:** Se `deficiencia.id` fornecido não existir, lança `NotFoundException`

### 11.4. Comportamentos Não Permitidos

- **Múltiplos identificadores principais:** Não há validação explícita, mas apenas um deve ser marcado como `principal=true` por boas práticas
- **Múltiplos endereços principais:** Não há validação explícita, mas apenas um deve ser marcado como `principal=true` por boas práticas
- **CPF/CNS/RG/Email duplicados:** Atualmente não validado, mas deve ser implementado

### 11.5. Regras de Negócio Não Validadas

**Regra implícita / depende de service:** As seguintes validações **não são executadas** no cadastro atual:

1. **Unicidade de documentos:** CPF, CNS, RG não são validados como únicos
2. **Unicidade de contatos:** Email não é validado como único
3. **Validação de idade:** Não há validação se `dataNascimento` é no futuro
4. **Validação de óbito:** Não há validação se `dataObito` é anterior a `dataNascimento`
5. **Obrigatoriedade de consentimento LGPD:** Não há validação se consentimento é obrigatório em determinados contextos
6. **Validação de endereço:** Não há validação se endereço fornecido é válido (CEP, etc.)

---

## 12. Observações Importantes

### 12.1. Tenant (Multi-tenancy)

- O sistema é multi-tenant
- O `tenant_id` é obtido automaticamente do contexto de segurança via `TenantService.validarTenantAtual()`
- Todas as validações de unicidade são feitas por tenant (mesmo CPF pode existir em tenants diferentes)

### 12.2. Cache

- Após criar o paciente, o resultado é armazenado em cache (`CacheKeyUtil.CACHE_PACIENTES`)
- Chave do cache: `paciente(tenantId, pacienteId)`

### 12.3. Transações

- O método `criar()` é executado em transação (`@Transactional`)
- Em caso de erro, todas as alterações são revertidas (rollback)

### 12.4. Logging

- Logs de debug são gerados no início e fim do processo
- Logs de erro são gerados em caso de exceções
- Logs de informação são gerados após criação bem-sucedida

---

## 13. Exemplo de Request Mínimo

```json
{
  "dadosPessoaisBasicos": {
    "nomeCompleto": "João da Silva",
    "sexo": "MASCULINO"
  }
}
```

## 14. Exemplo de Request Completo

```json
{
  "dadosPessoaisBasicos": {
    "nomeCompleto": "João da Silva",
    "nomeSocial": "João",
    "dataNascimento": "1990-01-15",
    "sexo": "MASCULINO"
  },
  "documentosBasicos": {
    "cpf": "12345678901",
    "rg": "1234567",
    "cns": "123456789012345",
    "orgaoEmissorRg": "SSP",
    "ufEmissorRg": "SP"
  },
  "contato": {
    "telefone": "(11) 1234-5678",
    "celular": "(11) 98765-4321",
    "email": "joao@example.com"
  },
  "dadosDemograficos": {
    "escolaridade": "ENSINO_MEDIO_COMPLETO",
    "nacionalidade": "BRASILEIRA",
    "naturalidade": "São Paulo",
    "racaCor": "BRANCA",
    "situacaoRua": false
  },
  "statusPaciente": "ATIVO",
  "convenio": "550e8400-e29b-41d4-a716-446655440000",
  "informacoesConvenio": {
    "numeroCarteirinha": "123456",
    "dataValidadeCarteirinha": "2025-12-31"
  },
  "tipoAtendimentoPreferencial": "NORMAL",
  "observacoes": "Paciente com histórico de alergias"
}
```

---

**Documento gerado com base no código existente em:** `UPSaude-final-back/src/main/java/com/upsaude/`  
**Última atualização:** Baseado na análise do código em janeiro de 2025  
**Status:** Documentação do contrato REAL conforme implementação atual
