import fs from "fs/promises";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

export class RepositoryGenerator {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }


    /**
     * Gera a Repository da entidade.
     * @param entityName Nome da entidade (ex: Alergia)
     * @param uniqueFields Lista de campos que devem gerar existsByCampo
     */
    async generate(entityName: string, uniqueFields: string[] = []): Promise<string> {
        const filePath = this.getFilePath(entityName);
        const content = this.buildRepository(entityName, uniqueFields);

        await fs.writeFile(filePath, content);
        logger.success(`Repository criado: ${filePath}`);

        return `✔ Repository gerado para ${entityName}`;
    }

    // =====================================================
    // Gera o conteúdo da Repository
    // =====================================================
    private buildRepository(entityName: string, uniqueFields: string[]): string {
        const className = `${entityName}Repository`;

        const methods = uniqueFields.map(field => {
            const pascal = field.charAt(0).toUpperCase() + field.slice(1);
            return `
    boolean existsBy${pascal}(String ${field});
    boolean existsBy${pascal}AndIdNot(String ${field}, java.util.UUID id);`;
        }).join("\n");

        return `package com.upsaude.repository;

import com.upsaude.entity.${entityName};
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Repository para a entidade ${entityName}.
 * Segue os padrões UPSaude definidos em repository-patterns.yaml.
 */
public interface ${className} extends JpaRepository<${entityName}, UUID> {
    ${methods}
}
`;
    }

    // =====================================================
    // Helpers
    // =====================================================
    private getFilePath(entityName: string): string {
        return path.join(
            this.projectRoot,
            `src/main/java/com/upsaude/repository/${entityName}Repository.java`
        );
    }
}
