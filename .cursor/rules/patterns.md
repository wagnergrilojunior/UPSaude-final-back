version: 1.0
description: >
  Modelos padrão usados pelo Cursor/IA para gerar automaticamente
  todas as camadas do UPSaude com consistência total.

patterns:

  # ==========================================================
  #  ENTITY
  # ==========================================================
  entity:
    template: |
      package {{package}}.entity;

      import jakarta.persistence.*;
      import lombok.*;
      {{imports}}

      /**
       * {{description}}
       */
      @Entity
      @Table(name = "{{table_name}}", schema = "public"{{indexes}})
      @Data
      @EqualsAndHashCode(callSuper = true)
      public class {{EntityName}} extends {{base_entity}} {

          public {{EntityName}}() {
              {{embeddable_initialization}}
          }

          {{fields}}

          @PrePersist
          @PreUpdate
          public void validateEmbeddables() {
              {{embeddable_validation}}
          }
      }

    field_template: |
      @Column(name = "{{column_name}}"{{column_details}})
      private {{type}} {{field_name}};

    embeddable_field_template: |
      @Embedded
      private {{type}} {{field_name}};

    index_template: |
      , indexes = {
          {{index_lines}}
      }

    index_line: |
      @Index(name = "{{index_name}}", columnList = "{{column_list}}")

  # ==========================================================
  # REQUEST
  # ==========================================================
  request:
    template: |
      package {{package}}.api.request;

      import lombok.*;
      import jakarta.validation.Valid;
      import jakarta.validation.constraints.*;
      {{imports}}

      @Getter
      @Setter
      @Builder
      @NoArgsConstructor
      @AllArgsConstructor
      public class {{EntityName}}Request {

          {{fields}}
      }

    field_template: |
      {{validations}}
      private {{type}} {{field_name}};

    validation_rules:
      not_null: "@NotNull(message = \"{{label}} é obrigatório\")"
      not_blank: "@NotBlank(message = \"{{label}} é obrigatório\")"
      size: "@Size(max = {{size}}, message = \"{{label}} deve ter no máximo {{size}} caracteres\")"
      pattern: "@Pattern(regexp = \"{{regex}}\", message = \"{{message}}\")"
      valid_embeddable: "@Valid"

  # ==========================================================
  # RESPONSE
  # ==========================================================
  response:
    template: |
      package {{package}}.api.response;

      import lombok.*;
      import java.time.OffsetDateTime;
      import java.util.UUID;
      {{imports}}

      @Getter
      @Setter
      @Builder
      @NoArgsConstructor
      @AllArgsConstructor
      public class {{EntityName}}Response {
          private UUID id;
          private OffsetDateTime createdAt;
          private OffsetDateTime updatedAt;
          private Boolean active;

          {{fields}}
      }

  # ==========================================================
  # MAPPER
  # ==========================================================
  mapper:
    template: |
      package {{package}}.mapper;

      import org.mapstruct.*;
      import {{package}}.mapper.config.MappingConfig;
      import {{package}}.entity.{{EntityName}};
      import {{package}}.api.request.{{EntityName}}Request;
      import {{package}}.api.response.{{EntityName}}Response;
      {{imports}}

      @Mapper(config = MappingConfig.class, uses = { {{embeddable_mappers}} })
      public interface {{EntityName}}Mapper {

          @Mapping(target = "id", ignore = true)
          @Mapping(target = "createdAt", ignore = true)
          @Mapping(target = "updatedAt", ignore = true)
          @Mapping(target = "active", ignore = true)
          {{EntityName}} fromRequest({{EntityName}}Request request);

          @Mapping(target = "id", ignore = true)
          @Mapping(target = "createdAt", ignore = true)
          @Mapping(target = "updatedAt", ignore = true)
          @Mapping(target = "active", ignore = true)
          void updateFromRequest({{EntityName}}Request request, @MappingTarget {{EntityName}} entity);

          {{EntityName}}Response toResponse({{EntityName}} entity);
      }

  # ==========================================================
  # REPOSITORY
  # ==========================================================
  repository:
    template: |
      package {{package}}.repository;

      import org.springframework.data.jpa.repository.JpaRepository;
      import java.util.UUID;
      import {{package}}.entity.{{EntityName}};

      public interface {{EntityName}}Repository extends JpaRepository<{{EntityName}}, UUID> {

          {{duplicity_methods}}
      }

    duplicity_method_template: |
      boolean existsBy{{Field}}(String {{field}});
      boolean existsBy{{Field}}AndIdNot(String {{field}}, UUID id);

  # ==========================================================
  # SERVICE INTERFACE
  # ==========================================================
  service:
    template: |
      package {{package}}.service;

      import org.springframework.data.domain.Page;
      import org.springframework.data.domain.Pageable;
      import java.util.UUID;
      import {{package}}.api.request.{{EntityName}}Request;
      import {{package}}.api.response.{{EntityName}}Response;

      public interface {{EntityName}}Service {

          {{EntityName}}Response criar({{EntityName}}Request request);

          {{EntityName}}Response obterPorId(UUID id);

          Page<{{EntityName}}Response> listar(Pageable pageable);

          {{EntityName}}Response atualizar(UUID id, {{EntityName}}Request request);

          void excluir(UUID id);
      }

  # ==========================================================
  # SERVICE IMPLEMENTATION
  # ==========================================================
  service_impl:
    template: |
      package {{package}}.service.impl;

      import lombok.RequiredArgsConstructor;
      import lombok.extern.slf4j.Slf4j;
      import org.springframework.stereotype.Service;
      import org.springframework.transaction.annotation.Transactional;
      import org.springframework.data.domain.Page;
      import org.springframework.data.domain.Pageable;
      import java.util.UUID;

      import {{package}}.entity.{{EntityName}};
      import {{package}}.api.request.{{EntityName}}Request;
      import {{package}}.api.response.{{EntityName}}Response;
      import {{package}}.repository.{{EntityName}}Repository;
      import {{package}}.mapper.{{EntityName}}Mapper;
      import {{package}}.exception.BadRequestException;
      import {{package}}.exception.NotFoundException;

      @Slf4j
      @Service
      @RequiredArgsConstructor
      public class {{EntityName}}ServiceImpl implements {{package}}.service.{{EntityName}}Service {

          private final {{EntityName}}Repository repository;
          private final {{EntityName}}Mapper mapper;

          @Override
          @Transactional
          public {{EntityName}}Response criar({{EntityName}}Request request) {
              log.debug("Criando {{EntityName}}: {}", request);

              validarDuplicidade(null, request);

              {{EntityName}} entity = mapper.fromRequest(request);
              entity.setActive(true);

              return mapper.toResponse(repository.save(entity));
          }

          @Override
          @Transactional(readOnly = true)
          public {{EntityName}}Response obterPorId(UUID id) {
              log.debug("Buscando {{EntityName}} por ID: {}", id);

              {{EntityName}} encontrado = repository.findById(id)
                      .orElseThrow(() -> new NotFoundException("{{EntityName}} não encontrado"));

              return mapper.toResponse(encontrado);
          }

          @Override
          @Transactional(readOnly = true)
          public Page<{{EntityName}}Response> listar(Pageable pageable) {
              return repository.findAll(pageable).map(mapper::toResponse);
          }

          @Override
          @Transactional
          public {{EntityName}}Response atualizar(UUID id, {{EntityName}}Request request) {

              {{EntityName}} existente = repository.findById(id)
                      .orElseThrow(() -> new NotFoundException("{{EntityName}} não encontrado"));

              validarDuplicidade(id, request);

              mapper.updateFromRequest(request, existente);

              return mapper.toResponse(repository.save(existente));
          }

          @Override
          @Transactional
          public void excluir(UUID id) {
              {{EntityName}} existente = repository.findById(id)
                      .orElseThrow(() -> new NotFoundException("{{EntityName}} não encontrado"));

              if (!existente.getActive())
                  throw new BadRequestException("Registro já inativo");

              existente.setActive(false);
              repository.save(existente);
          }

          private void validarDuplicidade(UUID id, {{EntityName}}Request request) {
              {{duplicity_checks}}
          }
      }

  # ==========================================================
  # CONTROLLER
  # ==========================================================
  controller:
    template: |
      package {{package}}.controller;

      import lombok.RequiredArgsConstructor;
      import org.springframework.http.ResponseEntity;
      import org.springframework.web.bind.annotation.*;
      import org.springframework.data.domain.Pageable;
      import org.springframework.data.domain.Page;

      import {{package}}.service.{{EntityName}}Service;
      import {{package}}.api.request.{{EntityName}}Request;
      import {{package}}.api.response.{{EntityName}}Response;

      @RestController
      @RequestMapping("/api/v1/{{resource}}")
      @RequiredArgsConstructor
      public class {{EntityName}}Controller {

          private final {{EntityName}}Service service;

          @PostMapping
          public ResponseEntity<{{EntityName}}Response> criar(@RequestBody @Valid {{EntityName}}Request request) {
              return ResponseEntity.ok(service.criar(request));
          }

          @GetMapping("/{id}")
          public ResponseEntity<{{EntityName}}Response> obterPorId(@PathVariable UUID id) {
              return ResponseEntity.ok(service.obterPorId(id));
          }

          @GetMapping
          public Page<{{EntityName}}Response> listar(Pageable pageable) {
              return service.listar(pageable);
          }

          @PutMapping("/{id}")
          public ResponseEntity<{{EntityName}}Response> atualizar(@PathVariable UUID id, @RequestBody @Valid {{EntityName}}Request request) {
              return ResponseEntity.ok(service.atualizar(id, request));
          }

          @DeleteMapping("/{id}")
          public ResponseEntity<Void> excluir(@PathVariable UUID id) {
              service.excluir(id);
              return ResponseEntity.noContent().build();
          }
      }
