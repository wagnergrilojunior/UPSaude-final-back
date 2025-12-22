version: 1.0

# ==========================================================
#   REGRAS DE ARQUITETURA DO UPSAUDE
# ==========================================================
architecture:

  description: >
    Estas regras definem a arquitetura oficial do sistema UPSaude.
    Todo código novo ou revisado deve obedecer rigorosamente estas diretrizes,
    garantindo consistência, organização, escalabilidade e manutenção simples.

  layers:
    - entity
    - embeddable
    - request
    - response
    - dto
    - mapper
    - repository
    - service
    - service_impl
    - controller
    - exception
    - configuration
    - domain_rules (opcional)

  rules:
    - O projeto deve seguir arquitetura em camadas rigidamente separadas.
    - Nenhuma camada pode atravessar a outra diretamente (ex: controller não usa repository).
    - Todas as regras pesadas devem viver no domínio (domain-rules.yaml).
    - Toda lógica REST deve existir apenas no controller.
    - Serviços nunca retornam entidades, apenas Response.
    - Services não recebem entidades, apenas Request.
    - MapStruct deve ser a única forma de conversão entre camadas.
    - Toda entidade deve ser persistida via repository, nunca via entityManager direto.
    - Nenhuma camada pode depender ciclicamente da outra.
    - Todas as camadas devem conter logs padronizados.

# ==========================================================
#   ESTRUTURA DE PACOTES OBRIGATÓRIA
# ==========================================================
packages:
  entity: com.upsaude.entity
  embeddable: com.upsaude.entity.embeddable
  request: com.upsaude.api.request
  request_embeddable: com.upsaude.api.request.embeddable
  response: com.upsaude.api.response
  response_embeddable: com.upsaude.api.response.embeddable
  dto: com.upsaude.dto
  dto_embeddable: com.upsaude.dto.embeddable
  mapper: com.upsaude.mapper
  mapper_embeddable: com.upsaude.mapper.embeddable
  repository: com.upsaude.repository
  service: com.upsaude.service
  service_impl: com.upsaude.service.impl
  controller: com.upsaude.controller
  exception: com.upsaude.exception
  config: com.upsaude.config
  util: com.upsaude.util

package_rules:
  - Pacotes devem ser sempre minúsculos.
  - Não pode haver classes de layers diferentes no mesmo pacote.
  - Proibida a criação de pacotes fora da convenção sem justificativa arquitetural.

# ==========================================================
#   REGRAS DETALHADAS POR CAMADA
# ==========================================================

# ========== ENTITIES ==========
entity:
  rules:
    - Nome da classe deve ser SINGULAR e em PascalCase.
    - A tabela correspondente deve ser PLURAL em snake_case.
    - Anotações obrigatórias:
        - @Entity
        - @Table(name = "plural_snake_case", schema = "public")
    - Deve estender BaseEntity ou BaseEntityWithoutTenant.
    - Relacionamentos LAZY por padrão.
    - Embeddables devem ser inicializados no construtor.
    - Criar índices relevantes com @Index.
    - Não conter validações de request.
    - Comportamentos internos devem ser aplicados via @PrePersist e @PreUpdate.

# ========== EMBEDDABLES ==========
embeddable:
  rules:
    - Usar @Embeddable.
    - Nunca acessar banco ou outros serviços.
    - Conter apenas dados relacionados.
    - Versões de request, response e DTO devem existir.

# ========== REQUEST ==========
request:
  rules:
    - Nome: NomeEntidadeRequest.
    - Deve conter validações obrigatórias.
    - Não conter campos:
        - id
        - createdAt
        - updatedAt
        - active
    - Embeddables devem usar @Valid.

# ========== RESPONSE ==========
response:
  rules:
    - Nome: NomeEntidadeResponse.
    - Deve conter:
        - id
        - createdAt
        - updatedAt
        - active
    - Nunca retornar entidades.
    - Embeddables devem ter suas versões próprias.

# ========== MAPPER ==========
mapper:
  rules:
    - Deve usar @Mapper(config = MappingConfig.class)
    - Usar mappers de embeddable em "uses".
    - NÃO deve extender EntityMapper (DTOs foram removidos).
    - Deve conter:
        - toResponse(entity)
        - fromRequest(request)
        - updateFromRequest(request, entity)
    - Ignorar automaticamente:
        - id
        - createdAt
        - updatedAt
        - active

# ========== REPOSITORY ==========
repository:
  rules:
    - Deve estender JpaRepository<Entity, UUID>.
    - Métodos de duplicidade:
        - existsByCampo
        - existsByCampoAndIdNot
    - Nunca conter lógica de negócio.
    - Nunca validar dados.

# ========== SERVICE ==========
service:
  rules:
    - É uma interface.
    - Deve conter métodos:
        - criar(Request)
        - obterPorId(UUID)
        - listar(Pageable)
        - atualizar(UUID, Request)
        - excluir(UUID)
    - Não deve usar entidades diretamente.
    - Retorno sempre Response.

# ========== SERVICE IMPLEMENTATION ==========
service_impl:
  rules:
    - Anotações:
        - @Service
        - @RequiredArgsConstructor
        - @Transactional
    - Responsabilidades:
        - Validar duplicidade
        - Validar dados obrigatórios
        - Conversões via Mapper
        - Soft delete (active = false)
        - Lançar BadRequestException para erros de validação
        - Lançar NotFoundException quando registro não existir
    - Não pode conter lógicas pesadas.
    - Não acessar banco sem repository.

# ========== CONTROLLER ==========
controller:
  rules:
    - Anotações:
        - @RestController
        - @RequestMapping("/api/v1/{entidade}")
    - Métodos devem usar ResponseEntity.
    - Não retornar entidade.
    - Aceitar apenas Request.
    - Retornar apenas Response.
    - Paginação deve ser Pageable.
    - Exceções globais no ApiExceptionHandler.

# ========== EXCEPTION ==========
exception:
  rules:
    - Toda exceção do domínio deve estender:
        - BadRequestException
        - NotFoundException
        - ConflictException
        - InternalErrorException
    - Nunca usar RuntimeException diretamente.
    - Mensagens devem ser claras e padronizadas.

# ==========================================================
#   DEPENDÊNCIAS ENTRE CAMADAS
# ==========================================================
dependency_rules:
  - controller → service
  - service_impl → repository, mapper, domain_rules (opcional)
  - mapper → entity, dto, request, response
  - repository → entity
  - entity → embeddables
  - request → embeddables
  - dto → embeddables
  - response → embeddables

prohibited_dependencies:
  - controller → repository
  - controller → entity
  - repository → service
  - repository → controller
  - service → controller
  - entity → service
  - entity → controller
  - mapper → repository
  - dto → repository
  - request → repository
  - response → repository

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
