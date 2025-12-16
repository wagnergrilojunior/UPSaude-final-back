import fs from "fs";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";
import { ProjectEntity } from "../core/types.js";

export class EntityFixer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }

    /**
     * Método principal de correção (alias para fixEntity).
     */
    async fix(entityName: string): Promise<string> {
        return this.fixEntity(entityName);
    }

    async fixEntity(entityName: string): Promise<string> {
        logger.info(`Iniciando correção automática da entidade ${entityName}`);

        const entityFilePath = this.findEntityFile(entityName);
        if (!entityFilePath) {
            return `❌ Entidade '${entityName}' não encontrada no projeto.`;
        }

        let content = fs.readFileSync(entityFilePath, "utf-8");
        const originalContent = content;
        let report: string[] = [];

        //
        // 1 — Garantir que possui @Entity
        //
        if (!/@Entity\b/.test(content)) {
            content = `@Entity\n` + content;
            report.push("✔ Adicionado @Entity");
        }

        //
        // 2 — Garantir @Table com nome correto
        //
        const tableName = this.toSnakePlural(entityName);

        if (!/@Table/.test(content)) {
            content = content.replace(
                /public class/,
                `@Table(name = "${tableName}", schema = "public")\npublic class`
            );
            report.push(`✔ Criado @Table(name="${tableName}")`);
        } else {
            // Atualiza caso esteja errado
            content = content.replace(/name\s*=\s*"([^"]+)"/, `name="${tableName}"`);
            report.push("✔ Corrigido nome da tabela no @Table");
        }

        //
        // 3 — Validar campos textuais longos
        //
        const longTextRegex = /String\s+([a-zA-Z0-9]+)\s*;/g;
        let match;

        while ((match = longTextRegex.exec(content)) !== null) {
            const field = match[1];

            if (!new RegExp(`${field}".*columnDefinition`).test(content)) {
                const replacement = `@Column(name = "${this.toSnake(field)}", columnDefinition = "TEXT")\n    private String ${field};`;
                content = content.replace(`private String ${field};`, replacement);
                report.push(`✔ Campo '${field}' convertido para TEXT`);
            }
        }

        //
        // 4 — Validar inicialização de embeddables no construtor
        //
        const embeddableRegex = /@Embedded\s+private\s+([A-Za-z0-9_]+)\s+([a-zA-Z0-9_]+)\s*;/g;
        const embeddables: Array<{ type: string; field: string }> = [];

        while ((match = embeddableRegex.exec(content)) !== null) {
            embeddables.push({ type: match[1], field: match[2] });
        }

        if (embeddables.length > 0) {
            if (!/public\s+[A-Za-z0-9]+\s*\(\)/.test(content)) {
                // criar construtor vazio
                content = content.replace(
                    /public class [A-Za-z0-9_]+ \{/,
                    (m) =>
                        `${m}\n\n    public ${entityName}() {\n${embeddables
                            .map((e) => `        this.${e.field} = new ${e.type}();`)
                            .join("\n")}\n    }\n`
                );
                report.push("✔ Construtor criado com inicialização de embeddables");
            } else {
                // atualizar construtor existente
                content = content.replace(
                    /public\s+[A-Za-z0-9]+\s*\(\)\s*\{/,
                    (m) =>
                        `${m}\n${embeddables
                            .map((e) => `        if (this.${e.field} == null) this.${e.field} = new ${e.type}();`)
                            .join("\n")}`
                );
                report.push("✔ Embeddables inicializados no construtor existente");
            }
        }

        //
        // 5 — Garantir lifecycle hooks (@PrePersist / @PreUpdate)
        //
        if (!/@PrePersist/.test(content)) {
            const hook = `
    @PrePersist
    @PreUpdate
    public void validateLifecycle() {
        // TODO: regras internas da entidade
    }
`;
            content = content.replace(/\}\s*$/, `${hook}\n}`);
            report.push("✔ Adicionados @PrePersist e @PreUpdate");
        }

        //
        // 6 — Organizar imports (simplificado)
        //
        content = this.cleanupImports(content);

        //
        // 7 — Reformatar classe (simplificado)
        //
        content = this.formatCode(content);

        //
        // 8 — Salvar arquivo
        //
        fs.writeFileSync(entityFilePath, content, "utf-8");

        //
        // 9 — Gerar relatório final
        //
        if (content !== originalContent) {
            report.unshift(`### Entidade '${entityName}' corrigida com sucesso`);
        } else {
            report.unshift(`### Nenhuma correção necessária para '${entityName}'`);
        }

        return report.join("\n");
    }

    //
    // ------------------------------------------------------------
    //  UTILITÁRIOS
    // ------------------------------------------------------------
    //

    private findEntityFile(entity: string): string | null {
        const base = path.join(this.projectRoot, "src/main/java");
        let result: string | null = null;

        const walk = (dir: string) => {
            if (result) return;

            const files = fs.readdirSync(dir);
            for (const file of files) {
                const full = path.join(dir, file);

                if (fs.statSync(full).isDirectory()) {
                    walk(full);
                } else if (file === `${entity}.java`) {
                    result = full;
                    return;
                }
            }
        };

        walk(base);
        return result;
    }

    private toSnake(str: string): string {
        return str.replace(/[A-Z]/g, (m) => `_${m.toLowerCase()}`).replace(/^_/, "");
    }

    private toSnakePlural(entity: string): string {
        return this.toSnake(entity).toLowerCase() + "s";
    }

    private cleanupImports(content: string): string {
        const lines = content.split("\n");
        const imports = lines.filter((l) => l.startsWith("import "));
        const unique = [...new Set(imports)].sort();

        const withoutImports = lines.filter((l) => !l.startsWith("import "));
        return [...unique, "", ...withoutImports].join("\n");
    }

    private formatCode(content: string): string {
        return content.replace(/\t/g, "    ").replace(/\n{3,}/g, "\n\n");
    }
}
