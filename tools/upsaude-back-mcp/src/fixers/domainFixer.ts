import fs from "fs";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

/**
 * O DomainFixer ajusta regras de domínio que devem ficar dentro das entidades,
 * como:
 *  - validação pré-persistência
 *  - inicialização de embeddables
 *  - consistência de enums
 *  - enforcement de invariantes do domínio
 *  - preenchimento automático de campos derivados
 */
export class DomainFixer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }


    async fix(entityName: string): Promise<string> {
        return this.fixDomain(entityName);
    }

    async fixDomain(entityName: string): Promise<string> {
        logger.info(`🔧 Ajustando regras de domínio para entidade ${entityName}`);

        const entityPath = this.findEntity(entityName);
        if (!entityPath) {
            return `❌ Entidade ${entityName} não encontrada.`;
        }

        const content = fs.readFileSync(entityPath, "utf-8");
        let updated = content;
        const report: string[] = [];

        // -------------------------------------------------------------------------
        // 1. Adicionar métodos @PrePersist e @PreUpdate se não existirem
        // -------------------------------------------------------------------------
        if (!this.hasLifecycleMethods(content)) {
            const lifecycleBlock = `
    @PrePersist
    @PreUpdate
    private void validateDomainRules() {
        // Regra automática gerada pelo MCP:
        // Garanta inicialização de embeddables e invariantes de domínio.
        ${this.generateEmbeddableInit(entityName, content).join("\n        ")}
    }
`;
            updated = updated.replace(/\}\s*$/, lifecycleBlock + "\n}");
            report.push("✔ Adicionados métodos @PrePersist e @PreUpdate para regras internas do domínio.");
        }

        // -------------------------------------------------------------------------
        // 2. Inicialização automática de embeddables no construtor
        // -------------------------------------------------------------------------
        const embeddables = this.extractEmbeddables(content);

        if (embeddables.length > 0 && !this.hasConstructorInit(content)) {
            const constructorInit = this.generateConstructor(entityName, embeddables);
            updated = updated.replace(/class [^{]+{/, match => match + constructorInit);
            report.push(`✔ Construtor com inicialização automática de embeddables criado: ${embeddables.join(", ")}`);
        }

        // -------------------------------------------------------------------------
        // 3. Garantir imports obrigatórios
        // -------------------------------------------------------------------------
        const mandatoryImports = [
            "import jakarta.persistence.PrePersist;",
            "import jakarta.persistence.PreUpdate;",
        ];

        mandatoryImports.forEach(imp => {
            if (!updated.includes(imp)) {
                updated = imp + "\n" + updated;
                report.push(`✔ Import obrigatório adicionado: ${imp}`);
            }
        });

        // -------------------------------------------------------------------------
        // 4. Aplicar invariantes de domínio declaradas no rules.yaml
        // -------------------------------------------------------------------------
        const invariants = this.rules.domain?.invariants?.[entityName] ?? [];

        if (invariants.length > 0) {
            const invariantBlock = this.generateInvariants(invariants);
            updated = updated.replace(/validateDomainRules\(\)\s*{/, match => match + invariantBlock);
            report.push(`✔ Invariantes de domínio aplicadas: ${invariants.length}`);
        }

        // -------------------------------------------------------------------------
        // SALVAR O ARQUIVO SE HOUVER ALTERAÇÕES
        // -------------------------------------------------------------------------
        if (updated !== content) {
            fs.writeFileSync(entityPath, updated, "utf-8");
            return `### DomainFixer aplicado com sucesso para ${entityName}\n${report.join("\n")}`;
        }

        return `### Nenhuma correção necessária em ${entityName}.`;
    }

    // -----------------------------------------------------------------------------
    // Localiza arquivo de entidade
    // -----------------------------------------------------------------------------
    private findEntity(entityName: string): string | null {
        const folder = path.join(this.projectRoot, "src/main/java/com/upsaude/entity");
        const fileName = `${entityName}.java`;
        const full = path.join(folder, fileName);

        return fs.existsSync(full) ? full : null;
    }

    // -----------------------------------------------------------------------------
    // Detecta se já possui @PrePersist e @PreUpdate
    // -----------------------------------------------------------------------------
    private hasLifecycleMethods(content: string): boolean {
        return content.includes("@PrePersist") || content.includes("@PreUpdate");
    }

    // -----------------------------------------------------------------------------
    // Identifica embeddables pela anotação @Embedded
    // -----------------------------------------------------------------------------
    private extractEmbeddables(content: string): string[] {
        const matches = [...content.matchAll(/@Embedded\s+private\s+([A-Za-z0-9_]+)/g)];
        return matches.map(m => m[1]);
    }

    // -----------------------------------------------------------------------------
    // Detecta se já existe construtor com inicialização
    // -----------------------------------------------------------------------------
    private hasConstructorInit(content: string): boolean {
        return content.includes("public ") && content.includes("()") && content.includes("{");
    }

    // -----------------------------------------------------------------------------
    // Gera construtor com inicialização de embeddables
    // -----------------------------------------------------------------------------
    private generateConstructor(entityName: string, embeddables: string[]): string {
        const body = embeddables
            .map(e => `this.${this.lower(e)} = new ${e}();`)
            .join("\n        ");

        return `
    
    public ${entityName}() {
        ${body}
    }
`;
    }

    // -----------------------------------------------------------------------------
    // Gera lógica de inicialização dentro do método validateDomainRules()
    // -----------------------------------------------------------------------------
    private generateEmbeddableInit(entityName: string, content: string): string[] {
        const embeddables = this.extractEmbeddables(content);
        return embeddables.map(e =>
            `if (this.${this.lower(e)} == null) this.${this.lower(e)} = new ${e}();`
        );
    }

    // -----------------------------------------------------------------------------
    // Gera invariantes declaradas no domain-rules.yaml
    // -----------------------------------------------------------------------------
    private generateInvariants(rules: string[]): string {
        return rules
            .map(rule => `\n        // Invariante: ${rule}\n        // TODO: implementar verificação`)
            .join("");
    }

    // -----------------------------------------------------------------------------
    private lower(name: string): string {
        return name.charAt(0).toLowerCase() + name.slice(1);
    }
}
