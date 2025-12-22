version: 1.0

# ==========================================================
#   ARQUITETURA PRINCIPAL DO UPSAUDE
# ==========================================================
architecture:
  entity_naming: singular
  table_naming: plural_snake_case
  id_type: UUID
  active_field: true
  timestamps: true

  layers:
    - entity
    - request
    - response
    - dto
    - mapper
    - repository
    - service
    - service_impl
    - controller

# ==========================================================
#   REGRAS GLOBAIS
# ==========================================================
global_rules:
  - Todo novo módulo deve seguir a arquitetura multicamada do UPSaude.
  - Estrutura de pacotes deve ser SEMPRE:
      - com.upsaude.entity
      - com.upsaude.api.request
      - com.upsaude.api.response
      - com.upsaude.dto
      - com.upsaude.mapper
      - com.upsaude.repository
      - com.upsaude.service
      - com.upsaude.service.impl
      - com.upsaude.controller
  - Todas as classes devem usar Lombok (@Data, @Getter, @Setter, @Builder etc.)
  - Todas as ENTIDADES devem ser SINGULAR (Alergia, Paciente, Doenca)
  - Todas as TABELAS devem ser PLURAL snake_case (alergias, pacientes)
  - Nunca expor ENTIDADES em controllers
  - Controllers recebem Request e retornam Response
  - Mapeamentos ENTIDADE ↔ REQUEST/RESPONSE deve ser feito por MapStruct
  - Toda lógica de negócio deve estar no service_impl
  - Toda regra pesada deve ir para domain-rules.yaml
  - Toda classe deve ser documentada
  - Validações SEMPRE por Bean Validation e exceções customizadas
  - Relacionamentos LAZY por padrão
  - Nunca duplicar lógica entre camadas
  - Jamais usar lógica no repository

# ==========================================================
#   ENTIDADES
# ==========================================================
entity:
  rules:
    - Nome no SINGULAR.
    - Anotações obrigatórias:
        - @Entity
        - @Table(name="plural_snake_case", schema="public")
    - Campos auditáveis (id, createdAt, updatedAt, active) vêm do BaseEntity.
    - Relacionamentos LAZY por padrão.
    - Embeddables devem ser inicializados no construtor.
    - Campos textuais longos → columnDefinition="TEXT".
    - Criar @Index para campos importantes.
    - Implementar @PrePersist e @PreUpdate para regras internas.
    - Não usar validações de Request aqui.
    - Documentação obrigatória em cada campo crítico.
    - Sempre usar BaseEntity ou BaseEntityWithoutTenant conforme o domínio.

# ==========================================================
#   REQUESTS
# ==========================================================
request:
  rules:
    - Nome: NomeEntidadeRequest.
    - Nunca conter id, active, createdAt, updatedAt.
    - Deve conter validações obrigatórias (NotNull, NotBlank, Size, Pattern).
    - Usar @Valid para embeddables.
    - Embeddables devem estar em api.request.embeddable.
    - Representa APENAS o payload de entrada.

# ==========================================================
#   RESPONSES
# ==========================================================
response:
  rules:
    - Nome: NomeEntidadeResponse.
    - Deve conter id, createdAt, updatedAt, active.
    - Nunca expor entidades JPA.
    - Embeddables devem ter suas versões response.
    - Estrutura equilibrada, sem dados desnecessários.

# ==========================================================
#   MAPPER
# ==========================================================
mapper:
  rules:
    - Deve usar @Mapper(config = MappingConfig.class)
    - NÃO deve extender EntityMapper (DTOs foram removidos).
    - Métodos obrigatórios:
        - fromRequest(request)
        - updateFromRequest(request, @MappingTarget entity)
        - toResponse(entity)
    - Campos gerenciados pelo sistema devem ser ignorados em updates:
        - id
        - createdAt
        - updatedAt
        - active
    - Embeddables devem ter mappers próprios.
    - Nunca aplicar lógica de negócio no mapper.

# ==========================================================
#   REPOSITORY
# ==========================================================
repository:
  rules:
    - Deve estender JpaRepository<Entity, UUID>.
    - Métodos de duplicidade devem seguir padrão:
        - existsByCampo
        - existsByCampoAndIdNot
    - Nunca usar @Query para lógicas de negócio.
    - Nunca aplicar validações aqui.
    - Apenas acesso ao banco.

# ==========================================================
#   SERVICE
# ==========================================================
service:
  rules:
    - Interface deve ter métodos padrão:
        - criar(Request)
        - obterPorId(UUID)
        - listar(Pageable)
        - atualizar(UUID, Request)
        - excluir(UUID)
    - Nunca trabalhar com entidades diretamente.
    - Sempre retornar Response.
    - Nunca injetar repository em controller.

# ==========================================================
#   SERVICE IMPLEMENTATION
# ==========================================================
service_impl:
  rules:
    - Deve implementar interface da entidade.
    - Anotações obrigatórias:
        - @Service
        - @RequiredArgsConstructor
        - @Transactional
    - Deve:
        - Validar duplicidade
        - Validar dados obrigatórios
        - Lançar BadRequestException quando request inválido
        - Lançar NotFoundException quando recurso não existir
        - Realizar soft delete (active = false)
        - Logar todas as ações
        - Delegar conversões ao mapper
        - Delegar regras complexas ao domínio (domain-rules.yaml)
    - Não deve:
        - Implementar regras pesadas
        - Expor entidades
        - Aplicar validações do banco

# ==========================================================
#   CONTROLLER
# ==========================================================
controller:
  rules:
    - Anotações:
        - @RestController
        - @RequestMapping("/api/v1/{entidade}")
    - Retornos sempre ResponseEntity
    - Nunca retornar Entity → APENAS Response
    - Paginação deve usar Pageable
    - Tratamento de erros centralizado no ApiExceptionHandler
    - Controllers NÃO devem conter lógica de negócio

# ==========================================================
#   PADRÕES DE NOMENCLATURA
# ==========================================================
naming_conventions:
  class_case: PascalCase
  field_case: camelCase
  table_case: snake_case_plural
  package_case: lower_case
  request_suffix: Request
  response_suffix: Response
  mapper_suffix: Mapper
  service_suffix: Service
  service_impl_suffix: ServiceImpl
  repository_suffix: Repository
