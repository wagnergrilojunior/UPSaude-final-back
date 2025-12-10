import fs from "fs/promises";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

export class DTOGenerator {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }


    /**
     * Gera um DTO completo baseado na entidade.
     */
    async generate(entityName: string, fields: Record<string, string> = {}): Promise<string> {
        const filePath = this.getFilePath(entityName);
        const content = this.buildDTO(entityName, fields);

        await fs.writeFile(filePath, content);
        logger.success(`DTO criado: ${filePath}`);

        return `✔ DTO gerado para ${entityName}`;
    }

    // =====================================================
    // Gera o conteúdo da classe DTO
    // =====================================================
    private buildDTO(entityName: string, fields: Record<string, string>): string {
        const baseFields = `
    private java.util.UUID id;
    private java.time.OffsetDateTime createdAt;
    private java.time.OffsetDateTime updatedAt;
    private Boolean active;
`;

        const generatedFields = Object.entries(fields)
            .map(([field, type]) => `    private ${type} ${field};`)
            .join("\n");

        return `package com.upsaude.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO da entidade ${entityName}.
 * Segue os padrões definidos em:
 * - dto-patterns.yaml
 * - architecture-rules.yaml
 * - naming conventions UPSaude
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ${entityName}DTO {
${baseFields}${generatedFields ? "\n" + generatedFields : ""}
}
`;
    }

    // =====================================================
    // Helpers
    // =====================================================
    private getFilePath(entityName: string): string {
        return path.join(
            this.projectRoot,
            `src/main/java/com/upsaude/dto/${entityName}DTO.java`
        );
    }
}
