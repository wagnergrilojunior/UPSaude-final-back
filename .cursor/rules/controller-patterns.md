version: 1.0
description: >
  Regras oficiais para geração de Controllers REST do UPSaude.
  Garante coerência com arquitetura, padrões REST, segurança,
  logs, validações e estrutura de camadas.

controller_rules:

  naming:
    suffix: Controller
    package: "{{package}}.controller"
    class_name_format: "{{EntityName}}Controller"

  base_path:
    prefix: "/api/v1"
    entity_path_format: "{{entityPluralLower}}"

  annotations:
    - "@RestController"
    - "@RequestMapping(\"/api/v1/{{entityPluralLower}}\")"
    - "@RequiredArgsConstructor"
    - "@Slf4j"

  forbidden:
    - "Nunca retornar Entity"
    - "Nunca retornar DTO"
    - "Nunca receber Entity ou DTO no payload"
    - "Nunca colocar regra de negócio no controller"
    - "Nunca acessar Repository diretamente"

  allowed:
    - Responses → sempre ResponseEntity
    - Entrada → sempre Request
    - Paginação → Pageable
    - Ordenação → Sort automático do Spring

  endpoint_structure:
    create:
      method: POST
      path: "/"
      signature: "ResponseEntity<{{EntityName}}Response> criar(@Valid @RequestBody {{EntityName}}Request request)"
      logs:
        before: "Recebida requisição para criar {{EntityName}} — payload: {}"
        after: "{{EntityName}} criada com sucesso — ID: {}"
      status: CREATED

    get_by_id:
      method: GET
      path: "/{id}"
      signature: "ResponseEntity<{{EntityName}}Response> obterPorId(@PathVariable UUID id)"
      logs:
        before: "Buscando {{EntityName}} — ID: {}"
        after: "{{EntityName}} encontrada — ID: {}"

    list:
      method: GET
      path: "/"
      signature: "ResponseEntity<Page<{{EntityName}}Response>> listar(Pageable pageable)"
      logs:
        before: "Listando {{EntityName}} — page={}, size={}"
        after: "{{EntityName}} listadas com sucesso"

    update:
      method: PUT
      path: "/{id}"
      signature: "ResponseEntity<{{EntityName}}Response> atualizar(@PathVariable UUID id, @Valid @RequestBody {{EntityName}}Request request)"
      logs:
        before: "Atualizando {{EntityName}} — ID: {}, payload: {}"
        after: "{{EntityName}} atualizada com sucesso — ID: {}"

    delete:
      method: DELETE
      path: "/{id}"
      signature: "ResponseEntity<Void> excluir(@PathVariable UUID id)"
      logs:
        before: "Excluindo {{EntityName}} — ID: {}"
        after: "{{EntityName}} excluída com sucesso — ID: {}"
      soft_delete_behavior:
        - "Nunca deletar fisicamente"
        - "Ativar somente active = false"

  validation_rules:
    - Controllers devem depender somente da Bean Validation + Service
    - Controllers NÃO DEVEM repetir validações do Service
    - Deve existir @Valid em todos os RequestBody com objetos complexos
    - Path variables UUID devem ser validados no Service (e não no Controller)

  exception_behavior:
    handled_by: ApiExceptionHandler
    do_not_handle_in_controller: true
    expected_exceptions:
      - BadRequestException  → retorna 400
      - NotFoundException    → retorna 404
      - ConflictException    → retorna 409
      - InternalError        → retorna 500

  responses:
    success:
      create: "ResponseEntity.status(HttpStatus.CREATED).body(response)"
      ok: "ResponseEntity.ok(response)"
      delete: "ResponseEntity.noContent().build()"

  logs:
    pattern:
      before_method: "log.debug(\"[CONTROLLER] {{message}}\", args)"
      after_method: "log.info(\"[CONTROLLER] {{message}}\", args)"

controller_template: |
  package {{package}}.controller;

  import {{package}}.api.request.{{EntityName}}Request;
  import {{package}}.api.response.{{EntityName}}Response;
  import {{package}}.service.{{EntityName}}Service;
  import lombok.RequiredArgsConstructor;
  import lombok.extern.slf4j.Slf4j;
  import org.springframework.http.HttpStatus;
  import org.springframework.http.ResponseEntity;
  import org.springframework.web.bind.annotation.*;
  import org.springframework.data.domain.Pageable;
  import jakarta.validation.Valid;
  import java.util.UUID;

  @Slf4j
  @RestController
  @RequiredArgsConstructor
  @RequestMapping("/api/v1/{{entityPluralLower}}")
  public class {{EntityName}}Controller {

      private final {{EntityName}}Service service;

      // =============================
      // CRIAR
      // =============================
      @PostMapping
      public ResponseEntity<{{EntityName}}Response> criar(@Valid @RequestBody {{EntityName}}Request request) {
          log.debug("Recebida requisição para criar {{EntityName}} — payload: {}", request);
          {{EntityName}}Response response = service.criar(request);
          log.info("{{EntityName}} criada com sucesso — ID: {}", response.getId());
          return ResponseEntity.status(HttpStatus.CREATED).body(response);
      }

      // =============================
      // BUSCAR POR ID
      // =============================
      @GetMapping("/{id}")
      public ResponseEntity<{{EntityName}}Response> obterPorId(@PathVariable UUID id) {
          log.debug("Buscando {{EntityName}} — ID: {}", id);
          return ResponseEntity.ok(service.obterPorId(id));
      }

      // =============================
      // LISTAR
      // =============================
      @GetMapping
      public ResponseEntity<?> listar(Pageable pageable) {
          log.debug("Listando {{EntityName}} — page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
          return ResponseEntity.ok(service.listar(pageable));
      }

      // =============================
      // ATUALIZAR
      // =============================
      @PutMapping("/{id}")
      public ResponseEntity<{{EntityName}}Response> atualizar(
              @PathVariable UUID id,
              @Valid @RequestBody {{EntityName}}Request request) {

          log.debug("Atualizando {{EntityName}} — ID: {}, payload: {}", id, request);
          {{EntityName}}Response response = service.atualizar(id, request);
          log.info("{{EntityName}} atualizada com sucesso — ID: {}", id);
          return ResponseEntity.ok(response);
      }

      // =============================
      // EXCLUIR (SOFT DELETE)
      // =============================
      @DeleteMapping("/{id}")
      public ResponseEntity<Void> excluir(@PathVariable UUID id) {
          log.debug("Excluindo {{EntityName}} — ID: {}", id);
          service.excluir(id);
          log.info("{{EntityName}} excluída com sucesso — ID: {}", id);
          return ResponseEntity.noContent().build();
      }
  }


instructions: |
  Este arquivo define como o Cursor deve gerar automaticamente:
    ✓ Controllers REST padronizados
    ✓ Estrutura CRUD profissional
    ✓ Versionamento de API
    ✓ Logs padronizados
    ✓ Respostas corretas HTTP
    ✓ Integração completa com Service
    ✓ Suporte automático a Pageable
    ✓ Suporte a validação
    ✓ Suporte a soft delete

  Para gerar:
    /generate controller entity="Alergia"

  O Cursor criará:
    - AlergiaController.java
    - Com endpoints REST completos
    - Com logs profissionais
    - Com validações
    - Com ResponseEntity padronizado
