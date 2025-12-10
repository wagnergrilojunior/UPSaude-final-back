import fs from "fs";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

export class RepositoryFixer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }

    async fix(entityName: string): Promise<string> {
        return this.fixRepository(entityName);
    }

    async fixRepository(entityName: string): Promise<string> {
        logger.info(`Iniciando correção automática do Repository da entidade ${entityName}`);

        const repositoryPath = this.findRepositoryFile(entityName);
        if (!repositoryPath) {
            return `❌ Repositório de '${entityName}' não encontrado.`;
        }

        let content = fs.readFileSync(repositoryPath, "utf-8");
        const original = content;
        const report: string[] = [];

        const repositoryName = `${entityName}Repository`;
        const entityImport = this.findEntityImport(entityName);
        const entityClass = entityName;
        const tableRules = this.rules.entities?.[entityName] ?? {};

        //
        // 1 — Garante package correto
        //
        if (!content.startsWith("package")) {
            content = `package com.upsaude.repository;\n\n${content}`;
            report.push("✔ Adicionado package padrão com.upsaude.repository");
        }

        //
        // 2 — Verifica se estende JpaRepository
        //
        if (!/extends\s+JpaRepository/.test(content)) {
            content = content.replace(
                /public interface .* \{/,
                `public interface ${repositoryName} extends JpaRepository<${entityClass}, UUID> {`
            );

            report.push(`✔ Ajustado extends JpaRepository<${entityClass}, UUID>`);
        }

        //
        // 3 — Garantir imports essenciais
        //
        const requiredImports = [
            "import org.springframework.data.jpa.repository.JpaRepository;",
            "import java.util.UUID;",
            entityImport
        ];

        requiredImports.forEach((imp) => {
            if (!content.includes(imp)) {
                content = imp + "\n" + content;
                report.push(`✔ Import adicionado: ${imp}`);
            }
        });

        //
        // 4 — Gerar métodos existsByCampo automaticamente
        //
        const entityFields = this.extractEntityFields(entityClass);
        const duplicateCandidates = this.detectDuplicateFields(entityClass, entityFields);

        let methodsToAdd: string[] = [];

        duplicateCandidates.forEach((field) => {
            const method1 = `boolean existsBy${this.capitalize(field)}(String ${field});`;
            const method2 = `boolean existsBy${this.capitalize(field)}AndIdNot(String ${field}, UUID id);`;

            if (!content.includes(method1)) {
                methodsToAdd.push(method1);
                report.push(`✔ Criado método de duplicidade: existsBy${this.capitalize(field)}`);
            }
            if (!content.includes(method2)) {
                methodsToAdd.push(method2);
                report.push(`✔ Criado método de duplicidade: existsBy${this.capitalize(field)}AndIdNot`);
            }
        });

        if (methodsToAdd.length > 0) {
            content = content.replace(/\}\s*$/, `\n\n    ${methodsToAdd.join("\n    ")}\n}\n`);
        }

        //
        // 5 — Garantir JavaDoc padronizado
        //
        if (!content.includes("/**") && !content.includes("* Repositório")) {
            const javadoc = `
/**
 * Repositório da entidade ${entityClass}.
 * Responsável por operações de persistência e consultas simples.
 * NÃO deve conter lógica de negócio.
 */
`;
            content = content.replace(`public interface`, `${javadoc}\npublic interface`);
            report.push("✔ Adicionado JavaDoc padrão");
        }

        //
        // 6 — Salvar arquivo se houver mudanças
        //
        if (content !== original) {
            fs.writeFileSync(repositoryPath, content, "utf-8");
            report.unshift(`### Repositório '${repositoryName}' corrigido com sucesso`);
        } else {
            report.unshift(`### Nenhuma correção necessária no repositório '${repositoryName}'`);
        }

        return report.join("\n");
    }

    // ------------------------------------------------------------
    // UTILITÁRIOS
    // ------------------------------------------------------------

    private findRepositoryFile(entity: string): string | null {
        const repoName = `${entity}Repository.java`;
        const base = path.join(this.projectRoot, "src/main/java/com/upsaude/repository");

        if (!fs.existsSync(base)) return null;

        const files = fs.readdirSync(base);
        const file = files.find((f) => f === repoName);

        return file ? path.join(base, file) : null;
    }

    private findEntityImport(entity: string): string {
        return `import com.upsaude.entity.${entity};`;
    }

    private extractEntityFields(entity: string): string[] {
        const entityPath = path.join(
            this.projectRoot,
            "src/main/java/com/upsaude/entity",
            `${entity}.java`
        );

        if (!fs.existsSync(entityPath)) return [];

        const content = fs.readFileSync(entityPath, "utf-8");

        const fieldRegex = /private\s+[\w<>]+\s+(\w+)\s*;/g;
        let match;
        const fields = [];

        while ((match = fieldRegex.exec(content)) !== null) {
            fields.push(match[1]);
        }

        return fields;
    }

    private detectDuplicateFields(entity: string, fields: string[]): string[] {
        // CANDIDATOS ÓBVIOUS
        const keywords = ["nome", "email", "cpf", "cnpj", "codigo", "numero", "registro"];

        const detected = fields.filter((f) =>
            keywords.some((kw) => f.toLowerCase().includes(kw))
        );

        // FUTURO → integrar com domain-rules.yaml
        return detected;
    }

    private capitalize(str: string): string {
        return str.charAt(0).toUpperCase() + str.slice(1);
    }
}
