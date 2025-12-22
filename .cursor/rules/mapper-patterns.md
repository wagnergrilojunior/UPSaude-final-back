version: 1.0
description: >
  Regras avançadas para geração de Mappers MapStruct no UPSaude.
  Todo mapper deve seguir este padrão. Este arquivo define como
  gerar métodos, ignorar campos, lidar com embeddables e estruturas complexas.

mapper_rules:

  # =====================================================
  # CONFIGURAÇÃO GLOBAL
  # =====================================================
  global:
    mapping_config_class: "{{package}}.mapper.config.MappingConfig"
    generated_annotation: "@Generated(\"Cursor AI — UPSaude Mapper\")"

    ignore_fields_system:
      - id
      - createdAt
      - updatedAt
      - active

    default_uses:
      - "{{package}}.mapper.embeddable.*"
      - "{{package}}.mapper.shared.*"

    required_methods:
      - toResponse
      - fromRequest
      - updateFromRequest

  # =====================================================
  # TEMPLATE PRINCIPAL DO MAPPER
  # =====================================================
  mapper_template: |
    package {{package}}.mapper;

    import org.mapstruct.*;
    import {{mapping_config}};
    import {{package}}.entity.{{EntityName}};
    import {{package}}.api.request.{{EntityName}}Request;
    import {{package}}.api.response.{{EntityName}}Response;
    {{imports}}

    /**
     * Mapper para conversão de {{EntityName}}:
     * Entity ↔ Request ↔ Response
     *
     * Geração automática seguindo padrões UPSaude.
     */
    @Mapper(config = MappingConfig.class, uses = { {{uses}} })
    public interface {{EntityName}}Mapper {

        {{generated}}

        // ============================
        // REQUEST → ENTITY (NOVA INSTÂNCIA)
        // ============================
        {{request_mappings}}
        {{EntityName}} fromRequest({{EntityName}}Request request);

        // ============================
        // REQUEST → ENTITY (ATUALIZAÇÃO)
        // ============================
        {{update_request_mappings}}
        void updateFromRequest({{EntityName}}Request request, @MappingTarget {{EntityName}} entity);

        // ============================
        // ENTITY → RESPONSE
        // ============================
        {{EntityName}}Response toResponse({{EntityName}} entity);
    }

  # =====================================================
  # RULES: Mapping automático de campos que devem ser ignorados
  # =====================================================
  request_mapping_ignore_system_fields:
    template: |
      @Mapping(target = "{{field}}", ignore = true)

  update_request_mapping_ignore_system_fields:
    template: |
      @Mapping(target = "{{field}}", ignore = true)

  # =====================================================
  # RULES: Mapeamento de relacionamentos
  # Estes campos devem ser convertidos manualmente no Service.
  # =====================================================
  relation_rules:
    many_to_one:
      template: "@Mapping(target = \"{{relation_name}}\", ignore = true)   // Resolved manually in Service"
    one_to_many:
      template: "@Mapping(target = \"{{relation_name}}\", ignore = true)"
    many_to_many:
      template: "@Mapping(target = \"{{relation_name}}\", ignore = true)"

  # =====================================================
  # RULES: Collections automáticas
  # =====================================================
  collection_rules:
    list_template: |
      List<{{EntityName}}Response> toResponseList(List<{{EntityName}}> entities);

    set_template: |
      Set<{{EntityName}}Response> toResponseSet(Set<{{EntityName}}> entities);

  # =====================================================
  # RULES: MAPEAMENTO DE EMBEDDABLES
  # =====================================================
  embeddable_rules:
    template: |
      @Mapping(target = "{{embedded_name}}", qualifiedByName = "{{mapper_method}}")

    mapper_method_template: |
      @Named("{{mapper_method}}")
      {{type}} map{{EmbeddedType}}({{embedded_request}} request);

  # =====================================================
  # RULES: ENUMS (conversões automáticas)
  # =====================================================
  enum_rules:
    to_enum_template: |
      default {{EnumType}} to{{EnumType}}(String value) {
          if (value == null) return null;
          return {{EnumType}}.valueOf(value.toUpperCase());
      }

    to_string_template: |
      default String from{{EnumType}}({{EnumType}} value) {
          return value != null ? value.name() : null;
      }

  # =====================================================
  # RULES: Custom Boolean Converters
  # =====================================================
  boolean_rules:
    to_boolean_template: |
      default Boolean parseBoolean(String value) {
          if (value == null) return null;
          return value.equalsIgnoreCase("true") || value.equals("1") || value.equalsIgnoreCase("yes");
      }

    from_boolean_template: |
      default String booleanToString(Boolean value) {
          return value != null ? value.toString() : null;
      }

  # =====================================================
  # RULES: Custom Date/Time Converters
  # =====================================================
  datetime_rules:
    offsetdatetime_to_string: |
      default String toString(OffsetDateTime v) {
          return v != null ? v.toString() : null;
      }

    string_to_offsetdatetime: |
      default OffsetDateTime toOffsetDateTime(String v) {
          return v != null ? OffsetDateTime.parse(v) : null;
      }

  # =====================================================
  # DEFAULT IMPORTS
  # =====================================================
  imports:
    - "java.util.*"
    - "java.time.*"

  # =====================================================
  # USAGE INSTRUCTIONS
  # =====================================================
instructions: |
  Este arquivo define exatamente como o Cursor AI deve gerar mappers no UPSaude.

  Quando você executar:
    /generate mapper entity="Alergia"

  O Cursor irá:
    ✓ Criar métodos obrigatórios  
    ✓ Ignorar campos gerenciados pelo sistema  
    ✓ Criar mapeamentos para Request → Entity → Response  
    ✓ Criar métodos customizados para enums, boolean, datas  
    ✓ Aplicar MappingConfig  
    ✓ Criar conversões para coleções (List/Set)  
    ✓ Criar métodos para embeddables  
    ✓ Criar comentários e estrutura padronizada  
