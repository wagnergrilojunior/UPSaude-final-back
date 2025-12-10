import fs from "fs";
import path from "path";
import type { MergedRules } from "../rules/mergedRules.js";
import type { EntityAnalysis, FieldInfo, EntityInfo } from "../core/types.js";
import { logger } from "../core/logger.js";

/**
 * Analisa uma classe Java de entidade conforme as regras definidas no MCP.
 * Não é um parser Java completo — mas detecta tudo que é necessário para
 * analisar e validar qualidade, padrões e conformidade arquitetural.
 */

export class EntityAnalyzer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string, rules: MergedRules) {
        this.projectRoot = projectRoot;
        this.rules = rules;
    }

    /**
     * Carrega o conteúdo de um arquivo Java.
     */
    loadEntityFile(filePath: string): string {
        if (!fs.existsSync(filePath)) {
            throw new Error(`Arquivo não encontrado: ${filePath}`);
        }
        return fs.readFileSync(filePath, "utf-8");
    }

    /**
     * Executa análise completa em um arquivo Java de entidade.
     */
    analyzeFile(filePath: string): EntityAnalysis {
        logger.info(`Analisando entidade: ${filePath}`);

        const source = this.loadEntityFile(filePath);
        const entityInfo = this.extractEntityInfo(source);

        return {
            filePath,
            entity: entityInfo,
            issues: [],
            suggestions: [],
            validations: this.applyRules(entityInfo)
        };
    }

    /**
     * Analisa uma entidade pelo nome e retorna relatório em texto.
     */
    async analyze(entityName: string): Promise<string> {
        const entityPath = path.join(
            this.projectRoot,
            "src/main/java/com/upsaude/entity",
            `${entityName}.java`
        );

        if (!fs.existsSync(entityPath)) {
            return `❌ Entidade '${entityName}' não encontrada em ${entityPath}`;
        }

        const analysis = this.analyzeFile(entityPath);
        const validations = analysis.validations || [];

        let report = `# 📘 Análise da Entidade: ${entityName}\n\n`;
        report += `**Arquivo:** ${entityPath}\n\n`;

        if (validations.length === 0) {
            report += "✅ Nenhum problema encontrado!\n";
        } else {
            report += `## ⚠️ Problemas Encontrados (${validations.length})\n\n`;
            validations.forEach((v, i) => {
                report += `${i + 1}. ${v}\n`;
            });
        }

        return report;
    }

    /**
     * Executa auditoria completa de todas as entidades.
     */
    async auditAll(): Promise<string> {
        const entitiesDir = path.join(this.projectRoot, "src/main/java/com/upsaude/entity");
        if (!fs.existsSync(entitiesDir)) {
            return "❌ Diretório de entidades não encontrado";
        }

        const files = fs.readdirSync(entitiesDir).filter(f => f.endsWith(".java"));
        let report = `# 📘 Auditoria Completa de Entidades\n\n`;
        report += `**Total de entidades:** ${files.length}\n\n`;

        for (const file of files) {
            const entityName = file.replace(".java", "");
            const entityPath = path.join(entitiesDir, file);
            const analysis = this.analyzeFile(entityPath);
            const validations = analysis.validations || [];

            report += `## ${entityName}\n`;
            if (validations.length === 0) {
                report += "✅ OK\n\n";
            } else {
                report += `⚠️ ${validations.length} problema(s)\n\n`;
            }
        }

        return report;
    }

    /**
     * Extrai metadados essenciais de uma entidade Java.
     */
    extractEntityInfo(source: string): EntityInfo {
        const className = this.extractClassName(source);
        const tableName = this.extractTableName(source);
        const fields = this.extractFields(source);
        const classAnnotations = this.extractClassAnnotations(source);

        return {
            name: className,
            className: className,
            tableName: tableName,
            fields: fields,
            annotations: classAnnotations,
            rawSource: source
        };
    }

    private extractClassAnnotations(source: string): string[] {
        const annotations: string[] = [];
        // Extrai anotações antes da declaração da classe
        const classMatch = source.match(/^([^@]*@[\w.()\s,="]*)*\s*(public\s+)?class\s+(\w+)/m);
        if (classMatch) {
            const annotationRegex = /@(\w+)/g;
            let match;
            while ((match = annotationRegex.exec(classMatch[0])) !== null) {
                annotations.push(match[1]);
            }
        }
        return annotations;
    }

    /**
     * Extrai o nome da classe.
     */
    extractClassName(source: string): string {
        const match = source.match(/class\s+([A-Za-z0-9_]+)/);
        return match ? match[1] : "UnknownEntity";
    }

    /**
     * Extrai o nome da tabela a partir da anotação @Table.
     */
    extractTableName(source: string): string | null {
        const match = source.match(/@Table\s*\(\s*name\s*=\s*"([^"]+)"/);
        return match ? match[1] : null;
    }

    /**
     * Extrai campos da entidade com base em padrões Java.
     * Detecta:
     *  - tipo
     *  - nome
     *  - validações Bean Validation
     *  - anotações JPA
     */
    extractFields(source: string): FieldInfo[] {
        const fields: FieldInfo[] = [];
        const regex = /(@[\s\S]*?)?\s+(private|protected|public)\s+([\w<>]+)\s+(\w+)\s*;/g;

        let match;
        while ((match = regex.exec(source)) !== null) {
            const annotations = match[1] || "";
            fields.push({
                name: match[4],
                type: match[3],
                annotations: this.extractAnnotations(annotations)
            });
        }

        return fields;
    }

    /**
     * Extrai anotações de um campo.
     */
    extractAnnotations(annotationBlock: string): string[] {
        return [...annotationBlock.matchAll(/@(\w+)/g)].map(m => m[1]);
    }

    /**
     * Executa validações definidas em master-rules + architecture + validation rules.
     */
    applyRules(entity: EntityInfo): string[] {
        const errors: string[] = [];

        const r = this.rules;

        //
        // REGRA 1 — Nome da classe deve ser singular
        //
        if (r.master?.global_rules?.includes("entity_singular") && entity.className && entity.className.endsWith("s")) {
            errors.push(`Nome da entidade deve ser SINGULAR: ${entity.className || entity.name}`);
        }

        //
        // REGRA 2 — Nome da tabela deve estar em snake_case plural
        //
        if (r.master?.global_rules) {
            if (!entity.tableName) {
                errors.push(`Entidade ${entity.className || entity.name} deve possuir @Table(name="...")`);
            } else if (!/^[a-z0-9_]+s$/.test(entity.tableName)) {
                errors.push(`Nome da tabela deve ser plural snake_case: ${entity.tableName}`);
            }
        }

        //
        // REGRA 3 — Campos obrigatórios precisam ter validação @NotNull/@NotBlank
        //
        entity.fields.forEach(f => {
            const requiresValidation = r.validation?.required_fields?.includes(f.type);

            if (requiresValidation && !f.annotations.some(a => a === "NotNull" || a === "NotBlank")) {
                errors.push(`Campo obrigatório sem validação: ${entity.className || entity.name}.${f.name}`);
            }
        });

        //
        // REGRA 4 — Strings devem ter @Size
        //
        entity.fields
            .filter(f => f.type === "String")
            .forEach(f => {
                if (!f.annotations.some(a => a === "Size")) {
                    errors.push(`Campo String deve ter @Size: ${entity.className || entity.name}.${f.name}`);
                }
            });

        //
        // REGRA 5 — Embeddables devem ter validação
        //
        entity.fields
            .filter(f => f.type.endsWith("Alergia") || f.type.endsWith("DTO"))
            .forEach(f => {
                if (!f.annotations.includes("Valid")) {
                    errors.push(`Embeddable precisa de @Valid: ${entity.className || entity.name}.${f.name}`);
                }
            });

        return errors;
    }
}
