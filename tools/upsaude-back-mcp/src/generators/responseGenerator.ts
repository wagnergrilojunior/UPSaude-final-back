import fs from "fs/promises";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

export class ResponseGenerator {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }


    /**
     * Gera a classe Response de uma entidade.
     * @param entityName Nome da entidade base (ex: Alergia)
     * @param fields Campos da entidade
     */
    async generate(entityName: string, fields: Record<string, any>): Promise<string> {
        const filePath = this.getFilePath(entityName);
        const content = this.buildResponse(entityName, fields);

        await fs.mkdir(path.dirname(filePath), { recursive: true });
        await fs.writeFile(filePath, content);

        logger.success(`Response gerado: ${filePath}`);
        return `✔ Response criado para ${entityName}`;
    }

    // ======================================================
    //      CONSTRUÇÃO DO RESPONSE
    // ======================================================
    private buildResponse(entityName: string, fields: Record<string, any>): string {
        const className = `${entityName}Response`;

        const imports = new Set<string>([
            "lombok.Getter",
            "lombok.Setter",
            "lombok.Builder",
            "lombok.NoArgsConstructor",
            "lombok.AllArgsConstructor",
            "java.util.UUID",
            "java.time.OffsetDateTime"
        ]);

        let fieldLines = `
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;\n\n`;

        // Gera campos adicionais
        for (const [name, meta] of Object.entries(fields)) {
            // Embeddable → usar Response correspondente
            const type = meta.embeddable === true
                ? `${meta.type}Response`
                : meta.type;

            fieldLines += `    private ${type} ${name};\n`;
        }

        const importBlock = [...imports].map(i => `import ${i};`).join("\n");

        return `package com.upsaude.api.response;

${importBlock}

/**
 * Response gerado automaticamente pelo MCP UPSaude.
 * Sempre retorna dados seguros, nunca expondo entidades JPA diretamente.
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

    // ======================================================
    // Helpers
    // ======================================================
    private getFilePath(entityName: string): string {
        return path.join(
            this.projectRoot,
            `src/main/java/com/upsaude/api/response/${entityName}Response.java`
        );
    }
}
