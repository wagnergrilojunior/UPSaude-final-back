import fs from "fs";
import path from "path";
import type { MergedRules } from "../rules/mergedRules.js";
import { logger } from "../core/logger.js";
import type { RequestAnalysis, FieldInfo, RequestInfo } from "../core/types.js";

/**
 * Analyzer responsável por validar Request DTOs conforme regras UPSaude.
 * Garante que todos os arquivos Request sigam padrões de arquitetura,
 * validações obrigatórias e restrições definidas nas rules YAML.
 */
export class RequestAnalyzer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string, rules: MergedRules) {
        this.projectRoot = projectRoot;
        this.rules = rules;
    }

    /**
     * Carrega um arquivo Request.
     */
    loadRequestFile(filePath: string): string {
        if (!fs.existsSync(filePath)) {
            throw new Error(`Arquivo Request não encontrado: ${filePath}`);
        }
        return fs.readFileSync(filePath, "utf-8");
    }

    /**
     * Análise principal de um Request.
     */
    analyzeFile(filePath: string): RequestAnalysis {
        logger.info(`Analisando Request: ${filePath}`);

        const source = this.loadRequestFile(filePath);
        const info = this.extractRequestInfo(source);

        return {
            filePath,
            request: info,
            issues: [],
            suggestions: [],
            validations: this.applyRules(info)
        };
    }

    /**
     * Extrai informações do Request.
     */
    extractRequestInfo(source: string): RequestInfo {
        return {
            className: this.extractClassName(source),
            fields: this.extractFields(source),
            rawSource: source
        };
    }

    /**
     * Encontra o nome da classe.
     */
    extractClassName(source: string): string {
        const match = source.match(/class\s+([A-Za-z0-9_]+)/);
        return match ? match[1] : "UnknownRequest";
    }

    /**
     * Extrai campos do Request com base em padrão Java.
     */
    extractFields(source: string): FieldInfo[] {
        const fields: FieldInfo[] = [];
        const regex = /(@[\s\S]*?)?\s+(private|protected|public)\s+([\w<>]+)\s+(\w+)\s*;/g;

        let match: RegExpExecArray | null;
        while ((match = regex.exec(source)) !== null) {
            const annotationsRaw = match[1] || "";

            fields.push({
                name: match[4],
                type: match[3],
                annotations: this.extractAnnotations(annotationsRaw)
            });
        }

        return fields;
    }

    /**
     * Extrai @Annotations de um campo.
     */
    extractAnnotations(block: string): string[] {
        return [...block.matchAll(/@(\w+)/g)].map(m => m[1]);
    }

    /**
     * Executa validações definidas nas regras YAML.
     */
    applyRules(req: RequestInfo): string[] {
        const errors: string[] = [];
        const r = this.rules;

        //
        // REGRA 1 — Nome deve terminar com "Request"
        //
        if (req.className && !req.className.endsWith("Request")) {
            errors.push(`Request deve ter sufixo *Request*: ${req.className}`);
        }

        //
        // REGRA 2 — Campos proibidos
        //
        const forbidden = ["id", "createdAt", "updatedAt", "active"];
        req.fields.forEach(f => {
            if (forbidden.includes(f.name)) {
                errors.push(`Request não pode conter '${f.name}'`);
            }
        });

        //
        // REGRA 3 — Requests devem ter validação Bean Validation
        //
        req.fields.forEach(f => {
            const requiresValidation = r.validation?.required_fields?.includes(f.type);

            if (requiresValidation) {
                if (!f.annotations.some(a => a === "NotNull" || a === "NotBlank")) {
                    errors.push(`Campo obrigatório sem validação: ${req.className}.${f.name}`);
                }
            }
        });

        //
        // REGRA 4 — Strings devem ter @Size
        //
        req.fields
            .filter(f => f.type === "String")
            .forEach(f => {
                if (!f.annotations.some(a => a === "Size")) {
                    errors.push(`Campo String deve ter @Size: ${req.className}.${f.name}`);
                }
            });

        //
        // REGRA 5 — Embeddables DEVEM ter @Valid
        //
        req.fields
            .filter(f => this.isEmbeddableType(f.type))
            .forEach(f => {
                if (!f.annotations.includes("Valid")) {
                    errors.push(`Embeddable ${f.name} precisa de @Valid`);
                }
            });

        //
        // REGRA 6 — Não permitir anotações JPA em Request
        //
        const jpaAnnotations = ["Column", "Table", "Entity", "Id", "GeneratedValue"];

        req.fields.forEach(f => {
            f.annotations.forEach(a => {
                if (jpaAnnotations.includes(a)) {
                    errors.push(`Request não pode usar anotações JPA: ${a} em ${f.name}`);
                }
            });
        });

        return errors;
    }

    /**
     * Identifica se tipo deve ser tratado como embeddable.
     * UPSaude naming patterns: *Request, *DTO, *Embeddable, etc.
     */
    private isEmbeddableType(type: string): boolean {
        return (
            type.endsWith("Request") ||
            type.endsWith("DTO") ||
            type.endsWith("Embeddable") ||
            /^[A-Z][A-Za-z]+Request$/.test(type)
        );
    }
}
