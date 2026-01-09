version: 1.0

coding_style:
  description: >
    Regras oficiais de estilo de código do UPSaude.
    Todo código gerado ou revisado pelo Cursor deve obedecer
    aos princípios de Clean Code, SOLID, consistência, clareza,
    testabilidade e segurança.

# ==========================================================
#   PRINCÍPIOS GERAIS DE ESCRITA DE CÓDIGO
# ==========================================================
general:
  - Código deve ser simples, claro e legível.
  - Nunca escrever código "apenas para funcionar".
  - Otimização prematura é proibida.
  - Priorizar coesão alta e acoplamento baixo.
  - Evitar abstrações desnecessárias.
  - Código deve explicar por si só (self-explanatory).
  - Comentários são usados apenas quando necessário.
  - Nenhuma lógica deve ser duplicada (DRY).
  - Funções devem ser pequenas e focadas.
  - Separar responsabilidades corretamente (SRP).

# ==========================================================
#   PRINCÍPIOS SOLID
# ==========================================================
solid:
  - SRP: Cada classe deve ter apenas uma responsabilidade.
  - OCP: Classes devem ser abertas para extensão e fechadas para modificação.
  - LSP: Subtipos devem poder substituir seus tipos base sem problemas.
  - ISP: Interfaces pequenas e específicas, nunca interfaces gigantes.
  - DIP: Classes devem depender de abstrações, não implementações.

# ==========================================================
#   PADRÕES DE NOMENCLATURA
# ==========================================================
naming:
  classes: PascalCase
  interfaces: PascalCase
  methods: camelCase
  fields: camelCase
  constants: UPPER_SNAKE_CASE
  packages: lower_snake_case
  table_names: snake_case
  entity_classes: singular
  request_names: PascalCase + "Request"
  response_names: PascalCase + "Response"

naming_rules:
  - Nomes devem refletir claramente seu propósito.
  - Proibido usar nomes curtos como a, b, c (exceto variáveis de laço).
  - Evitar sufixos inúteis (ex: Helper, Util sem necessidade real).
  - Métodos devem começar com verbos (criar, atualizar, validar).

# ==========================================================
#   PADRÕES DE FORMATAÇÃO
# ==========================================================
formatting:
  indentation: 4_spaces
  line_length: 120_chars
  blank_lines:
    - Sempre separar blocos lógicos com uma linha em branco.
    - Nunca deixar mais de uma linha em branco seguida.
  imports:
    - Nunca usar wildcard imports (*)
    - Ordenar por:
        1. Java standard
        2. Jakarta / Spring
        3. Bibliotecas externas
        4. Pacotes internos
  annotations:
    - Uma anotação por linha quando houver várias.
  spacing:
    - Sempre usar espaço após vírgula.
    - Nunca colocar espaço antes de "(" em chamadas de método.

# ==========================================================
#   PADRÕES DE MÉTODOS
# ==========================================================
methods:
  - Devem ser curtos (ideal < 20 linhas).
  - Cada método deve fazer apenas UMA coisa.
  - Nome deve deixar claro o objetivo.
  - Validar parâmetros no início.
  - Evitar retorno nulo → preferir Optional quando aplicável.
  - Métodos não devem depender de estados implícitos.

bad_practices_methods:
  - Métodos com muitos parâmetros.
  - Métodos gigantes fazendo várias coisas.
  - Métodos com efeitos colaterais ocultos.

# ==========================================================
#   PADRÕES DE CLASSES
# ==========================================================
classes:
  - Uma classe deve estar em um único arquivo.
  - Uma classe deve ter apenas uma responsabilidade (SRP).
  - Campos devem ser privados.
  - Imutabilidade é preferível quando possível.
  - Evitar classes "deus" que fazem tudo.
  - Evitar poluição do construtor → usar Builder quando necessário.

bad_practices_classes:
  - Classes com 1000+ linhas.
  - Classes com muitos métodos não relacionados.
  - Classes com estado mutável excessivo.

# ==========================================================
#   PADRÕES DE TRATAMENTO DE ERROS
# ==========================================================
exception_handling:
  - Nunca usar Exception genérica.
  - Nunca engolir exceptions.
  - Sempre logar erros com contexto.
  - Sempre lançar exceções específicas:
      - BadRequestException
      - NotFoundException
      - ConflictException
      - InternalErrorException
  - Mensagens devem ser claras e úteis.
  - Nunca expor stacktrace diretamente para o cliente.

avoid:
  - catch (Exception e) sem ação
  - throws Exception genérico
  - Propagar erros sem mensagem

# ==========================================================
#   PADRÕES DE LOGS
# ==========================================================
logging:
  - Usar @Slf4j sempre.
  - Logs devem seguir padrão:
      - debug → início de operações
      - info → operações concluídas
      - warn → problemas esperados (ex: validação)
      - error → falhas inesperadas
  - Nunca logar dados sensíveis (CPF, telefone, e-mail).
  - Sempre incluir contexto:
      - ID
      - payload resumido
      - classe e método

bad_logging_practices:
  - log.info("Chegou aqui")
  - log.error("Erro", e) sem mensagem explicativa

# ==========================================================
#   PADRÕES DE COLEÇÕES
# ==========================================================
collections:
  - Preferir interfaces (List, Set, Map) em vez de implementações.
  - Nunca retornar coleções mutáveis diretamente.
  - Evitar null → retornar lista vazia.

# ==========================================================
#   PADRÕES JAVA + SPRING
# ==========================================================
spring_rules:
  - Nunca acessar EntityManager diretamente (usar Repository).
  - Nunca colocar lógica no Controller.
  - Nunca fazer validação de request dentro da Entity.
  - @Transactional ONLY no ServiceImpl.
  - Mapper nunca injeta Repository.
  - ServiceImpl nunca expõe entidade.
  - Controller nunca recebe Entity, apenas Request.

# ==========================================================
#   LIMPEZA E QUALIDADE
# ==========================================================
clean_code:
  - Remover código morto.
  - Remover comentários inúteis.
  - Remover imports não usados.
  - Remover prints no console.
  - Revisar nomes antes de finalizar PR.
  - Escrever código para humanos, não para máquinas.

anti_patterns:
  - God Object
  - Anemic Domain Model em excesso (exceto para requests/responses)
  - Long Parameter Lists
  - Magic Numbers
  - Switch gigante com lógica duplicada
  - Booleans que controlam comportamento demais

# ==========================================================
#   PADRÕES DE DOCUMENTAÇÃO
# ==========================================================
documentation:
  - Toda classe pública deve ter Javadoc curto e direto.
  - Métodos complexos devem explicar o motivo, não o como.
  - Requests e Responses devem explicar para que servem.
  - Services devem documentar regras de negócios breves.
  - Entities devem explicar função no domínio.

avoid:
  - Comentários óbvios do tipo: "incrementa i"
  - Comentários mentirosos ou obsoletos
  - Comentários redundantes com o nome do método

# ==========================================================
#   CÓDIGO GERADO POR IA (CURSOR / GPT)
# ==========================================================
ai_generation:
  - Toda classe gerada deve seguir TODAS as regras deste arquivo.
  - IA nunca deve gerar código fora dos padrões da arquitetura UPSaude.
  - IA deve validar:
      - Camada correta
      - Nome correto
      - Imports corretos
      - Lógica coerente e limpa
      - Ausência de duplicações
      - SOLID aplicado
  - IA deve sugerir melhorias automaticamente quando detectar padrões ruins.
