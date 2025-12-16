version: 1.0
description: >
  Regras oficiais para geração de Services e ServicesImpl no UPSaude.
  Define padrões para interface, implementação, transações, duplicidade, logs,
  validações, tratamento de exceções e estrutura de regras de negócio.

service_rules:

  # =====================================================================
  # ESTRUTURA MÍNIMA DE UM SERVICE (INTERFACE)
  # =====================================================================
  interface:
    naming_suffix: Service
    package: "{{package}}.service"

    required_methods:
      - criar(request)
      - obterPorId(id)
      - listar(pageable)
      - atualizar(id, request)
      - excluir(id)

    method_signatures:
      criar: "{{EntityName}}Response criar({{EntityName}}Request request)"
      obterPorId: "{{EntityName}}Response obterPorId(UUID id)"
      listar: "Page<{{EntityName}}Response> listar(Pageable pageable)"
      atualizar: "{{EntityName}}Response atualizar(UUID id, {{EntityName}}Request request)"
      excluir: "void excluir(UUID id)"

    documentation_template: |
      /**
       * Service responsável por operações de {{EntityName}}.
       * Contém regras de negócio, validações e integração com repositório.
       */
    
  # =====================================================================
  # ESTRUTURA MÍNIMA DE UM SERVICE IMPLEMENTATION
  # =====================================================================
  implementation:
    naming_suffix: ServiceImpl
    package: "{{package}}.service.impl"

    annotations:
      - "@Service"
      - "@RequiredArgsConstructor"
      - "@Slf4j"

    constructor_injection: true

    transactional:
      read_only_annotation: "@Transactional(readOnly = true)"
      write_annotation: "@Transactional"

    must_use_mapper: true
    must_use_repository: true

    required_sections:
      - validarDadosObrigatorios
      - validarDuplicidade
      - converterRequestParaEntity
      - persistir
      - registrarLog
      - retornarResponse

    soft_delete:
      enabled: true
      field: active
      deactivate_value: false

    exception_rules:
      bad_request_exception: "BadRequestException"
      not_found_exception: "NotFoundException"
      unexpected_exception_message: "Erro inesperado ao processar {{EntityName}}"

    logs:
      criar: "Criando {{EntityName}} — payload: {}"
      atualizar: "Atualizando {{EntityName}} — ID: {}, payload: {}"
      excluir: "Excluindo {{EntityName}} — ID: {}"
      obter: "Buscando {{EntityName}} por ID: {}"
      listar: "Listando {{EntityName}} — page: {}, size: {}"

    duplication_rules:
      description: "Toda entidade deve validar duplicidade com existsByCampo e existsByCampoAndIdNot"
      method_template: |
        private void validarDuplicidade(UUID id, {{EntityName}}Request request) {
            // Gerado automaticamente com base nos campos únicos
        }

  # =====================================================================
  # TEMPLATE COMPLETO DO SERVICEIMPL GERADO
  # =====================================================================
  service_impl_template: |
    package {{package}}.service.impl;

    import {{package}}.entity.{{EntityName}};
    import {{package}}.service.{{EntityName}}Service;
    import {{package}}.repository.{{EntityName}}Repository;
    import {{package}}.mapper.{{EntityName}}Mapper;
    import {{package}}.api.request.{{EntityName}}Request;
    import {{package}}.api.response.{{EntityName}}Response;
    import {{package}}.exception.BadRequestException;
    import {{package}}.exception.NotFoundException;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import java.util.UUID;

    @Slf4j
    @Service
    @RequiredArgsConstructor
    public class {{EntityName}}ServiceImpl implements {{EntityName}}Service {

        private final {{EntityName}}Repository repository;
        private final {{EntityName}}Mapper mapper;

        // ====================================================================
        // CRIAÇÃO
        // ====================================================================
        @Override
        @Transactional
        public {{EntityName}}Response criar({{EntityName}}Request request) {
            log.debug("Criando {{EntityName}} — payload: {}", request);

            if (request == null) {
                throw new BadRequestException("Dados de {{EntityName}} são obrigatórios");
            }

            validarDuplicidade(null, request);

            {{EntityName}} entity = mapper.fromRequest(request);
            entity.setActive(true);

            {{EntityName}} salvo = repository.save(entity);
            log.info("{{EntityName}} criada com sucesso. ID: {}", salvo.getId());

            return mapper.toResponse(salvo);
        }

        // ====================================================================
        // CONSULTA POR ID
        // ====================================================================
        @Override
        @Transactional(readOnly = true)
        public {{EntityName}}Response obterPorId(UUID id) {
            log.debug("Buscando {{EntityName}} por ID: {}", id);

            if (id == null) {
                throw new BadRequestException("ID é obrigatório");
            }

            {{EntityName}} entity = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("{{EntityName}} não encontrada"));

            return mapper.toResponse(entity);
        }

        // ====================================================================
        // LISTAGEM
        // ====================================================================
        @Override
        @Transactional(readOnly = true)
        public Page<{{EntityName}}Response> listar(Pageable pageable) {
            log.debug("Listando {{EntityName}} — page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
            Page<{{EntityName}}> page = repository.findAll(pageable);
            return page.map(mapper::toResponse);
        }

        // ====================================================================
        // ATUALIZAÇÃO
        // ====================================================================
        @Override
        @Transactional
        public {{EntityName}}Response atualizar(UUID id, {{EntityName}}Request request) {
            log.debug("Atualizando {{EntityName}} — ID: {}, payload: {}", id, request);

            if (id == null) {
                throw new BadRequestException("ID é obrigatório");
            }

            if (request == null) {
                throw new BadRequestException("Dados de atualização são obrigatórios");
            }

            {{EntityName}} existente = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("{{EntityName}} não encontrada"));

            validarDuplicidade(id, request);

            mapper.updateFromRequest(request, existente);
            {{EntityName}} atualizado = repository.save(existente);

            log.info("{{EntityName}} atualizada com sucesso. ID: {}", atualizado.getId());
            return mapper.toResponse(atualizado);
        }

        // ====================================================================
        // EXCLUSÃO (SOFT DELETE)
        // ====================================================================
        @Override
        @Transactional
        public void excluir(UUID id) {
            log.debug("Excluindo {{EntityName}} — ID: {}", id);

            if (id == null) {
                throw new BadRequestException("ID é obrigatório");
            }

            {{EntityName}} existente = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("{{EntityName}} não encontrada"));

            if (Boolean.FALSE.equals(existente.getActive())) {
                throw new BadRequestException("{{EntityName}} já está inativa");
            }

            existente.setActive(false);
            repository.save(existente);

            log.info("{{EntityName}} desativada com sucesso. ID: {}", id);
        }

        // ====================================================================
        // VALIDAÇÃO DE DUPLICIDADE
        // ====================================================================
        private void validarDuplicidade(UUID id, {{EntityName}}Request request) {
            // O Cursor preencherá esta seção automaticamente
        }
    }

  # =====================================================================
  # GERAÇÃO AUTOMÁTICA DE MÉTODOS DE DUPLICIDADE
  # =====================================================================
  duplication_detection:
    unique_fields_pattern:
      - nome
      - codigo
      - codigoInterno
      - cpf
      - cnpj
    template: |
      if (request.get{{FieldNamePascal}}() != null && !request.get{{FieldNamePascal}}().trim().isEmpty()) {
          boolean duplicado = (id == null)
              ? repository.existsBy{{FieldNamePascal}}(request.get{{FieldNamePascal}}().trim())
              : repository.existsBy{{FieldNamePascal}}AndIdNot(request.get{{FieldNamePascal}}().trim(), id);

          if (duplicado) {
              throw new BadRequestException(
                  "{{EntityName}} com {{fieldName}} já existe no banco de dados"
              );
          }
      }

instructions: |
  Este arquivo define como o Cursor deve gerar automáticamente:
    ✓ Service (interface)
    ✓ ServiceImpl (implementação)
    ✓ Métodos de duplicidade
    ✓ Lógica de validação
    ✓ Lógica de transação
    ✓ Logs de alto padrão
    ✓ Tratamento padronizado de exceções
    ✓ Soft delete
    ✓ Estrutura corporativa UPSaude

  Para gerar:
    /generate service entity="Alergia"

  O Cursor criará:
    - AlergiaService.java
    - AlergiaServiceImpl.java
    - Com duplicidade automática
    - Com logs
    - Com transação
    - Com validações  
