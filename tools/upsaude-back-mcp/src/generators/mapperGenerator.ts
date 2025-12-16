import fs from "fs/promises";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

export class MapperGenerator {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }


    /**
     * Gera o mapper de uma entidade.
     */
    async generate(entityName: string, embeddables: string[] = []): Promise<string> {
        const filePath = this.getFilePath(entityName);
        const content = this.buildMapper(entityName, embeddables);

        await fs.writeFile(filePath, content);
        logger.success(`Mapper criado: ${filePath}`);

        return `✔ Mapper gerado para ${entityName}`;
    }

    // =====================================================
    // Gera o conteúdo do Mapper
    // =====================================================
    private buildMapper(entityName: string, embeddables: string[]): string {
        const dtoName = `${entityName}DTO`;
        const requestName = `${entityName}Request`;
        const responseName = `${entityName}Response`;

        const embeddableImports = embeddables
            .map(e => `import com.upsaude.mapper.embeddable.${e}Mapper;`)
            .join("\n");

        const embeddableUses = embeddables.length > 0
            ? `, uses = { ${embeddables.map(e => `${e}Mapper.class`).join(", ")} }`
            : "";

        return `package com.upsaude.mapper;

import com.upsaude.entity.${entityName};
import com.upsaude.dto.${dtoName};
import com.upsaude.api.request.${requestName};
import com.upsaude.api.response.${responseName};
import com.upsaude.mapper.config.MappingConfig;
${embeddableImports}

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper da entidade ${entityName}.
 * Segue os padrões definidos em:
 * - mapper-patterns.yaml
 * - architecture-rules.yaml
 */
@Mapper(config = MappingConfig.class${embeddableUses})
public interface ${entityName}Mapper extends EntityMapper<${entityName}, ${dtoName}> {

    // =============================================================
    // DTO → ENTITY
    // =============================================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    ${entityName} toEntity(${dtoName} dto);

    // =============================================================
    // ENTITY → DTO
    // =============================================================
    ${dtoName} toDTO(${entityName} entity);

    // =============================================================
    // REQUEST → ENTITY
    // =============================================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    ${entityName} fromRequest(${requestName} request);

    // =============================================================
    // UPDATE ENTITY FROM REQUEST
    // =============================================================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(${requestName} request, @MappingTarget ${entityName} entity);

    // =============================================================
    // ENTITY → RESPONSE
    // =============================================================
    ${responseName} toResponse(${entityName} entity);
}
`;
    }

    // =====================================================
    // Helpers
    // =====================================================
    private getFilePath(entityName: string): string {
        return path.join(
            this.projectRoot,
            `src/main/java/com/upsaude/mapper/${entityName}Mapper.java`
        );
    }
}
