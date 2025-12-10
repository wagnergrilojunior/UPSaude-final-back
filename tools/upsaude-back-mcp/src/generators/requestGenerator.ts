import fs from "fs/promises";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

export class RequestGenerator {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }


    /**
     * Gera class Request para uma entidade seguindo os padrões UPSaude.
     * @param entityName Nome da entidade (ex: Alergia)
     * @param fields Estrutura dos campos: { nome: { type: "String", required: true, max: 255 } }
     */
    async generate(entityName: string, fields: Record<string, any>): Promise<string> {
        const filePath = this.getFilePath(entityName);
        const content = this.buildRequest(entityName, fields);

        await fs.mkdir(path.dirname(filePath), { recursive: true });
        await fs.writeFile(filePath, content);

        logger.success(`Request gerado: ${filePath}`);
        return `✔ Request criado para ${entityName}`;
    }

    // =====================================================
    //     GERA O CONTEÚDO DO REQUEST
    // =====================================================
    private buildRequest(entityName: string, fields: Record<string, any>): string {
        const className = `${entityName}Request`;

        const imports = new Set<string>([
            "lombok.Getter",
            "lombok.Setter",
            "lombok.Builder",
            "lombok.NoArgsConstructor",
            "lombok.AllArgsConstructor",
        ]);

        let fieldLines = "";

        for (const [name, meta] of Object.entries(fields)) {
            let annotations = "";

            // ====== NOT NULL / NOT BLANK ======
            if (meta.required) {
                if (meta.type === "String") {
                    imports.add("jakarta.validation.constraints.NotBlank");
                    annotations += `    @NotBlank(message = "${this.formatLabel(entityName, name)} é obrigatório")\n`;
                } else {
                    imports.add("jakarta.validation.constraints.NotNull");
                    annotations += `    @NotNull(message = "${this.formatLabel(entityName, name)} é obrigatório")\n`;
                }
            }

            // ====== LENGTH ======
            if (meta.max) {
                imports.add("jakarta.validation.constraints.Size");
                annotations += `    @Size(max = ${meta.max}, message = "${this.formatLabel(entityName, name)} deve ter no máximo ${meta.max} caracteres")\n`;
            }

            // ====== VALID (para embeddables) ======
            if (meta.embeddable === true) {
                imports.add("jakarta.validation.Valid");
                annotations += `    @Valid\n`;
            }

            fieldLines += `${annotations}    private ${meta.type} ${name};\n\n`;
        }

        const importBlock = [...imports]
            .map((i) => `import ${i};`)
            .join("\n");

        return `package com.upsaude.api.request;

${importBlock}

/**
 * Request gerado automaticamente pelo MCP UPSaude.
 * Representa os dados de ENTRADA da entidade ${entityName}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ${className} {

${fieldLines}}
`;
    }

    // =====================================================
    // Helpers
    // =====================================================
    private getFilePath(entityName: string): string {
        return path.join(
            this.projectRoot,
            `src/main/java/com/upsaude/api/request/${entityName}Request.java`
        );
    }

    private formatLabel(entity: string, field: string): string {
        const nome = field.replace(/([A-Z])/g, " $1").trim();
        return nome.charAt(0).toUpperCase() + nome.slice(1);
    }
}
