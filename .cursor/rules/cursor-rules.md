version: 1
rules:
  - id: arquitetura-geral
    pattern: "**/*"
    description: >
      Todas as implementações devem seguir os princípios de arquitetura limpa,
      DDD, SOLID, separação de camadas e responsabilidade única.
    enforce:
      - "Não acoplar controller com repository."
      - "Service deve conter regras de negócio, controller apenas orquestrar."
      - "Repository deve conter exclusivamente consultas ao banco."
      - "Nunca acessar banco diretamente fora do repository."
      - "Nunca colocar lógica de negócio em Controllers."
      - "Evitar variáveis, métodos e classes com nomes genéricos."
      - "Sempre preferir estruturas imutáveis ou final quando aplicável."
      - "Multitenancy deve estar presente em todas as operações que manipulam dados."
      - "Lógica compartilhada deve ir para Facades."

  - id: entidades-padrao-upsaude
    pattern: "src/main/java/**/entity/*.java"
    description: >
      Define o padrão OFICIAL de entidades do UPSaúde.
    enforce:
      - "Todas as entidades devem estender BaseEntity ou BaseEntityWithoutTenant."
      - "Validar CPF, CNPJ, CNS, e-mail e telefone com anotações Jakarta."
      - "Todos campos obrigatórios devem conter @NotNull ou @NotBlank."
      - "Sempre usar @Size em Strings."
      - "Enum sempre armazenado via @Convert e converter dedicado."
      - "Todos relacionamentos @OneToMany devem ter orphanRemoval=true quando fizer sentido."
      - "Toda coleção deve ser iniciada no construtor."
      - "Adicionar métodos utilitários de sincronização bidirecional."
      - "Não usar FetchType.EAGER."
      - "Criar índices para campos de busca frequente."
      - "Criar UniqueConstraints quando business rules exigirem."

  - id: dto-request-response-padronizacao
    pattern: "src/main/java/**/api/{request,response}/**/*.java"
    description: >
      Regras de padronização para Requests e Responses.
    enforce:
      - "Request deve conter validações @NotNull @NotBlank @Size exatas."
      - "Response nunca expõe entidades diretamente."
      - "Requests não podem conter IDs de entidades internas (usar referências explícitas)."
      - "Response deve refletir dados sanitizados, seguros e não sensíveis."
      - "Nunca incluir entidades JPA dentro de Responses."

  - id: mapper-mapstruct
    pattern: "src/main/java/**/mapper/*.java"
    description: >
      Todos mappers devem seguir padrão oficial do MapStruct.
    enforce:
      - "Mapper deve ser interface com @Mapper(componentModel = 'spring')."
      - "Nunca escrever conversão manual se puder ser gerada."
      - "Criar método: toResponse(), fromRequest(), updateFromRequest()."
      - "Mapear enums explicitamente quando necessário."
      - "Não pode ter lógica de negócio dentro do mapper."

  - id: servicos
    pattern: "src/main/java/**/service/*.java"
    description: >
      Regras para camada de serviço UPSaúde.
    enforce:
      - "Toda service deve ser @Service."
      - "Service deve chamar repository e aplicar regras de negócio."
      - "Nunca retornar Optional para fora da service (erro deve ser tratado)."
      - "Toda ausência de recurso deve gerar NotFoundException padronizada."
      - "Toda regra inválida deve gerar BusinessException."
      - "Nunca expor entidades diretamente aos controllers."

  - id: controllers
    pattern: "src/main/java/**/controller/*.java"
    description: >
      Controllers estilo REST devem seguir convenções e boas práticas internacionais.
    enforce:
      - "Nunca conter lógica de negócio na controller."
      - "Sempre retornar ResponseEntity."
      - "Documentar com @Operation e @ApiResponses."
      - "Controller nunca deve montar objetos complexos manualmente — delegar ao serviço."
      - "CRUD deve seguir nomenclatura padrão: create/list/get/update/delete."
      - "Paginação sempre via Pageable."
      - "Validar Request com @Valid."

  - id: testes
    pattern: "src/test/java/**/*"
    description: >
      Testes obrigatórios para manter padrão de qualidade.
    enforce:
      - "Service deve ter teste unitário."
      - "Controller deve ter teste mockado usando MockMvc."
      - "Regras de negócio críticas devem possuir casos de teste."
      - "Nunca mockar o que está sendo testado."

  - id: regras-negocio-saude
    pattern: "**/*.java"
    description: >
      Regras de domínio específicas do setor de saúde.
    enforce:
      - "CPF deve sempre ter 11 dígitos numéricos."
      - "CNS deve ter exatamente 15 dígitos."
      - "Código IBGE deve ter 7 dígitos."
      - "CNES deve ser validado quando campo existir."
      - "Data de óbito só pode existir se statusPaciente = OBITO."
      - "Campos sensíveis devem ser criptografados seguindo LGPD."
      - "Consentimentos LGPD devem ser obrigatórios antes de gerar qualquer prontuário."

  - id: tratamento-excecoes
    pattern: "**/*.java"
    description: >
      Todas exceções devem seguir o padrão único do UPSaúde.
    enforce:
      - "Usar ApiExceptionHandler global."
      - "Nunca usar RuntimeException diretamente."
      - "Erros 400 → InvalidParameterException."
      - "Erros 404 → NotFoundException."
      - "Erros 409 → ConflictException."
      - "Erros 500 → InternalErrorException."

  - id: proibicoes
    pattern: "**/*"
    description: >
      Lista de práticas proibidas nos projetos UPSaúde.
    forbid:
      - "Uso de FetchType.EAGER."
      - "Instanciar repository dentro da service."
      - "Criar new ObjectMapper manualmente."
      - "Chamar System.out.println."
      - "Expor entidades JPA nos endpoints."
      - "Usar var para reduzir clareza em código empresarial."
      - "Métodos com mais de 40 linhas (exigir refatoração)."
      - "Controller com mais de 300 linhas."
      - "Service com mais de 600 linhas."
      - "Switch sem default."

  - id: multitenancy
    pattern: "**/*.java"
    description: >
      Regras obrigatórias para multitenancy do UPSaúde.
    enforce:
      - "Toda consulta deve filtrar tenantId automaticamente."
      - "Toda entidade deve conter tenantId exceto BaseEntityWithoutTenant."
      - "Nunca misturar dados de tenants diferentes."
      - "Operations administrativas devem exigir role MASTER."
