/**
 * Tipos centrais usados pelo MCP UPSaude.
 *
 * Objetivo:
 *  - Ser flexível o suficiente para não quebrar o build
 *  - Ser expressivo para futura evolução (refinamento dos analyzers/fixers)
 *  - Não ser excessivamente restritivo (muitos campos opcionais)
 */

/* ========================================================================== */
/*  SEVERIDADE / ISSUES GENÉRICOS                                             */
/* ========================================================================== */

export type Severity = "INFO" | "WARN" | "ERROR";

export interface AnalysisIssue {
    code: string;
    message: string;
    severity: Severity;
    location?: string;
    suggestion?: string;
}

/* ========================================================================== */
/*  ARQUIVOS / PROJETO                                                        */
/* ========================================================================== */

export interface FileInfo {
    path: string;          // Caminho relativo no projeto
    content: string;       // Conteúdo bruto do arquivo
    language?: string;     // java, ts, md, yaml, etc
}

/**
 * Representa uma entidade Java encontrada no projeto.
 * Usado por analyzers/fixers/generators.
 */
export interface ProjectEntity {
    name: string;          // Nome da classe (Paciente, Alergia, etc)
    path: string;          // Caminho do arquivo .java
    info?: EntityInfo;     // Metadados parseados (opcional)
}

/**
 * Regras de estrutura de projeto (usadas pelo ProjectStructureFixer).
 */
export interface ProjectStructureTemplateRule {
    path: string;              // Ex: "src/main/java/com/upsaude/config/SecurityConfig.java"
    description?: string;
    required?: boolean;
}

export interface ProjectStructureCheckRule {
    name: string;              // Ex: "hasMultiTenantConfig"
    description?: string;
    enabled?: boolean;
}

export interface ProjectStructureRules {
    requiredDirectories?: string[];                // Ex: ["src/main/java/com/upsaude/entity", ...]
    required_directories?: string[];               // Alias snake_case
    templates?: ProjectStructureTemplateRule[];    // Arquivos que devem existir
    checks?: ProjectStructureCheckRule[];          // Validações de estrutura
    auto_fix?: boolean;                            // Se deve aplicar correções automaticamente
    create_missing_templates?: boolean;            // Se deve criar templates faltantes
    missing_templates?: any[];                     // Lista de templates a criar
    misplaced_checks?: any[];                       // Validações de arquivos mal posicionados
}

/* ========================================================================== */
/*  CAMPOS / ENTIDADES / REQUEST / RESPONSE / DTO                             */
/* ========================================================================== */

export interface FieldInfo {
    name: string;               // nomeCampo
    type: string;               // String, UUID, TipoEnum, etc
    annotations: string[];      // ["NotNull", "Size(255)", ...]
    isNullable?: boolean;
    isCollection?: boolean;
    isId?: boolean;
    rawLine?: string;           // Linha original de código (opcional)
}

/**
 * Informações de uma entidade JPA.
 */
export interface EntityInfo {
    name: string;                       // Alergias, Paciente, etc
    className?: string;                 // Nome da classe (alias para name)
    packageName?: string;               // com.upsaude.entity
    tableName?: string | null;          // alergias, pacientes (plural snake_case)
    annotations: string[];              // ["Entity", "Table", ...]
    fields: FieldInfo[];                // Campos da entidade
    rawSource: string;                  // Código completo da classe
}

/**
 * Resultado da análise de entidade.
 * Alias usado por vários analyzers.
 */
export interface EntityAnalysisResult {
    entity: EntityInfo;
    issues: AnalysisIssue[];
    suggestions: string[];
    metrics?: Record<string, number>;
    filePath?: string;                  // Caminho do arquivo analisado
    validations?: string[];             // Validações aplicadas (compatibilidade)
}

export type EntityAnalysis = EntityAnalysisResult;

/* -------------------------------------------------------------------------- */
/*  REQUEST                                                                   */
/* -------------------------------------------------------------------------- */

export interface RequestInfo {
    name?: string;               // Nome lógico (AlergiaRequest)
    className?: string;          // Nome da classe Java
    packageName?: string;        // com.upsaude.api.request
    annotations?: string[];      // ["Builder", "Getter", ...]
    fields: FieldInfo[];
    rawSource: string;
}

export interface RequestAnalysisResult {
    request: RequestInfo;
    issues: AnalysisIssue[];
    suggestions: string[];
    metrics?: Record<string, number>;
    filePath?: string;                  // Caminho do arquivo analisado
    validations?: string[];             // Validações aplicadas (compatibilidade)
}

export type RequestAnalysis = RequestAnalysisResult;

/* -------------------------------------------------------------------------- */
/*  RESPONSE                                                                  */
/* -------------------------------------------------------------------------- */

export interface ResponseInfo {
    name?: string;
    className?: string;
    packageName?: string;
    annotations?: string[];
    fields: FieldInfo[];
    rawSource: string;
}

/* -------------------------------------------------------------------------- */
/*  DTO                                                                       */
/* -------------------------------------------------------------------------- */

export interface DTOInfo {
    name: string;
    packageName?: string;
    annotations?: string[];
    fields: FieldInfo[];
    rawSource: string;
}

/* ========================================================================== */
/*  MAPPERS (MapStruct)                                                       */
/* ========================================================================== */

export interface MapperMethodInfo {
    returnType: string;          // Alergia, AlergiaDTO, AlergiaResponse...
    name: string;                // toEntity, toDTO, fromRequest, etc
    annotations?: string[];      // ["Mapping(target=...)", ...]
    parameters?: string[];       // ["AlergiaDTO dto", "Alergia entity", ...]
}

export interface MapperInfo {
    name: string;                // AlergiaMapper
    packageName?: string;        // com.upsaude.mapper
    annotations?: string[];      // ["Mapper", "ComponentModel", ...]
    extendsInterface?: string | null;
    configPresent?: boolean;
    methods: MapperMethodInfo[];
    rawSource: string;
}

export interface MapperAnalysisResult {
    mapper: MapperInfo;
    issues: AnalysisIssue[];
    suggestions: string[];
    metrics?: Record<string, number>;
    filePath?: string;                  // Caminho do arquivo analisado
    validations?: string[];             // Validações aplicadas (compatibilidade)
}

export type MapperAnalysis = MapperAnalysisResult;

/* ========================================================================== */
/*  SERVICES                                                                  */
/* ========================================================================== */

export interface ServiceMethodInfo {
    name: string;                // criar, listar, etc
    returnType: string;          // AlergiaResponse, Page<AlergiaResponse>, void
    annotations?: string[];      // ["Transactional", ...]
    parameters?: string[];       // ["UUID id", "AlergiaRequest request", ...]
}

export interface ServiceInfo {
    name: string;                // AlergiaService / AlergiaServiceImpl
    packageName?: string;        // com.upsaude.service / .service.impl
    annotations?: string[];      // ["Service", "Transactional", ...]
    isInterface?: boolean;
    isImpl?: boolean;
    methods: ServiceMethodInfo[];
    hasTransactional?: boolean;
    hasLogger?: boolean;
    usesMapper?: boolean;
    usesRepository?: boolean;
    rawSource: string;
}

export interface ServiceAnalysisResult {
    service: ServiceInfo;
    issues: AnalysisIssue[];
    suggestions: string[];
    metrics?: Record<string, number>;
    filePath?: string;                  // Caminho do arquivo analisado
    validations?: string[];             // Validações aplicadas (compatibilidade)
}

export type ServiceAnalysis = ServiceAnalysisResult;

/* ========================================================================== */
/*  CONTROLLERS                                                               */
/* ========================================================================== */

export interface ControllerMethodInfo {
    name: string;                // listar, criar, atualizar, etc
    returnType: string;          // ResponseEntity<...>, Page<...>, etc
    isResponseEntity?: boolean;
    annotations?: string[];      // ["GetMapping", "PostMapping", ...]
    parameters?: string[];       // ["UUID id", "@Valid AlergiaRequest request", ...]
}

export interface ControllerInfo {
    name: string;                // AlergiaController
    packageName?: string;        // com.upsaude.controller
    annotations?: string[];      // ["RestController", "RequestMapping", ...]
    hasRestController: boolean;
    hasRequestMapping: boolean;
    hasValidAnnotation: boolean;
    usesService: boolean;
    containsLogic: boolean;
    usesRepository: boolean;
    methods: ControllerMethodInfo[];
    rawSource: string;
}

export interface ControllerAnalysisResult {
    controller: ControllerInfo;
    issues: AnalysisIssue[];
    suggestions: string[];
    metrics?: Record<string, number>;
    filePath?: string;                  // Caminho do arquivo analisado
    validations?: string[];             // Validações aplicadas (compatibilidade)
}

export type ControllerAnalysis = ControllerAnalysisResult;

/* ========================================================================== */
/*  REGRAS (YAML)                                                             */
/* ========================================================================== */

/**
 * Estrutura bruta carregada dos YAMLs de regras.
 * Esse tipo é usado por rulesLoader / mergedRules.
 */
export interface LoadedRules {
    master?: any;
    architecture?: any;
    coding?: any;
    validation?: any;
    domain?: any;
    cursor?: any;
    patterns?: any;
    mapperPatterns?: any;
    servicePatterns?: any;
    controllerPatterns?: any;
    [key: string]: any;
}

/* ========================================================================== */
/*  HEALTH / RELATÓRIO DE PROJETO                                             */
/* ========================================================================== */

export interface ProjectHealthMetric {
    name: string;                // "entities.count", "controllers.with.logic", etc
    value: number;
    category?: string;           // "entity", "controller", "mapper", etc
}

export interface ProjectHealthReport {
    metrics: ProjectHealthMetric[];
    warnings: string[];
    errors: string[];
    suggestions: string[];
    entitiesAnalyzed?: number;
    mappersAnalyzed?: number;
    servicesAnalyzed?: number;
    controllersAnalyzed?: number;
    files?: FileInfo[];
}

/**
 * Relatório simplificado de validação de projeto
 * (usado por validateProject.tool).
 */
export interface ValidationReport {
    isValid: boolean;
    issues: AnalysisIssue[];
    summary?: string;
}

/* ========================================================================== */
/*  DEFINIÇÃO DE TOOLS / REGISTRY (MCP)                                       */
/* ========================================================================== */

/**
 * Definição simplificada de uma tool do MCP para o registry interno.
 * Não conflita com os tipos do @modelcontextprotocol porque
 * aqui é usado apenas dentro do servidor MCP.
 */
export interface ToolDefinition {
    name: string;
    description?: string;
    inputSchema?: any;
    handler?: (args: any, context?: any) => Promise<any>;
}

/* ========================================================================== */
/*  OUTROS TIPOS GENÉRICOS                                                    */
/* ========================================================================== */

/**
 * Contexto opcional que pode ser passado para analyzers/fixers/generators.
 */
export interface ProjectContext {
    projectRoot: string;
    rules?: LoadedRules | Record<string, any>;
}

/**
 * Resultado genérico usado por alguns fixers/engines.
 */
export interface FixResult {
    filePath: string;
    changed: boolean;
    summary?: string;
    diff?: string;
}

/**
 * Resultado genérico de execução de alguma operação de MCP.
 */
export interface OperationResult {
    success: boolean;
    message?: string;
    details?: any;
}
