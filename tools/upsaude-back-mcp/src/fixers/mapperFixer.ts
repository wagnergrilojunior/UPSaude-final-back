import fs from "fs";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

export class MapperFixer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }


    async fix(entityName: string): Promise<string> {
        return this.fixMapper(entityName);
    }

    async fixMapper(entityName: string): Promise<string> {
        logger.info(`Corrigindo Mapper da entidade ${entityName}`);

        const mapperFile = this.findMapperFile(entityName);
        if (!mapperFile) {
            return `❌ Mapper de '${entityName}' não encontrado.`;
        }

        let content = fs.readFileSync(mapperFile, "utf-8");
        const originalContent = content;
        const report: string[] = [];

        const mapperName = `${entityName}Mapper`;
        const entityClass = entityName;
        const dtoClass = `${entityName}DTO`;
        const requestClass = `${entityName}Request`;
        const responseClass = `${entityName}Response`;

        //
        // 1 — Garante package correto
        //
        if (!content.startsWith("package com.upsaude.mapper")) {
            content = `package com.upsaude.mapper;\n\n${content}`;
            report.push("✔ Ajustado package para com.upsaude.mapper");
        }

        //
        // 2 — Imports obrigatórios
        //
        const requiredImports = [
            "import org.mapstruct.Mapper;",
            "import org.mapstruct.Mapping;",
            "import org.mapstruct.MappingTarget;",
            "import com.upsaude.mapper.config.MappingConfig;",
            `import com.upsaude.entity.${entityClass};`,
            `import com.upsaude.dto.${dtoClass};`,
            `import com.upsaude.api.request.${requestClass};`,
            `import com.upsaude.api.response.${responseClass};`
        ];

        requiredImports.forEach((imp) => {
            if (!content.includes(imp)) {
                content = imp + "\n" + content;
                report.push(`✔ Import adicionado: ${imp}`);
            }
        });

        //
        // 3 — Mapper annotation
        //
        if (!/@Mapper/.test(content)) {
            const annotation = `@Mapper(config = MappingConfig.class)`;
            content = content.replace(/public interface/, `${annotation}\npublic interface`);
            report.push("✔ Adicionada anotação @Mapper(config = MappingConfig.class)");
        }

        //
        // 4 — Garante extends EntityMapper
        //
        if (!/extends\s+EntityMapper/.test(content)) {
            content = content.replace(
                /public interface .* \{/,
                `public interface ${mapperName} extends EntityMapper<${entityClass}, ${dtoClass}> {`
            );
            report.push("✔ Mapper agora extende EntityMapper padrão do projeto");
        }

        //
        // 5 — Identificar embeddables e adicionar automaticamente no uses = {}
        //
        const embeddables = this.detectEmbeddables(entityClass);
        if (embeddables.length > 0 && !/@Mapper\(.*uses/.test(content)) {
            const usesStr = embeddables.map(e => `${e}Mapper.class`).join(", ");
            content = content.replace(
                /@Mapper\((.*?)\)/,
                `@Mapper(config = MappingConfig.class, uses = { ${usesStr} })`
            );
            report.push(`✔ Embeddables detectados adicionados ao uses: ${usesStr}`);
        }

        //
        // 6 — Métodos obrigatórios
        //
        const requiredMethods = {
            toEntity: `    ${entityClass} toEntity(${dtoClass} dto);`,
            toDTO: `    ${dtoClass} toDTO(${entityClass} entity);`,
            toResponse: `    ${responseClass} toResponse(${entityClass} entity);`,
            fromRequest: 
`    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    ${entityClass} fromRequest(${requestClass} request);`,
            updateFromRequest:
`    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(${requestClass} request, @MappingTarget ${entityClass} entity);`
        };

        Object.entries(requiredMethods).forEach(([name, definition]) => {
            if (!content.includes(`${name}(`)) {
                content = content.replace(/\}\s*$/, `\n${definition}\n}\n`);
                report.push(`✔ Método obrigatório adicionado: ${name}()`);
            }
        });

        //
        // 7 — Adicionar JavaDoc padrão se não existir
        //
        if (!content.includes("/** Mapper para")) {
            const javadoc = `
/**
 * Mapper para conversão da entidade ${entityClass}.
 * Regras aplicadas:
 *  - Nunca mapear id, createdAt, updatedAt, active a partir de Request
 *  - Conversão entre Entity ↔ DTO ↔ Request/Response
 *  - Embeddables tratados via mappers específicos
 */
`;
            content = content.replace(/public interface/, `${javadoc}\npublic interface`);
            report.push("✔ Adicionado JavaDoc padronizado ao Mapper");
        }

        //
        // 8 — Aplicar mudanças no arquivo
        //
        if (content !== originalContent) {
            fs.writeFileSync(mapperFile, content, "utf-8");
            report.unshift(`### Mapper '${mapperName}' corrigido com sucesso`);
        } else {
            report.unshift(`### Nenhuma alteração necessária no mapper '${mapperName}'`);
        }

        return report.join("\n");
    }

    // ------------------------------------------------------------------
    // UTILITÁRIOS
    // ------------------------------------------------------------------

    private findMapperFile(entity: string): string | null {
        const folder = path.join(this.projectRoot, "src/main/java/com/upsaude/mapper");
        const file = `${entity}Mapper.java`;

        const full = path.join(folder, file);

        return fs.existsSync(full) ? full : null;
    }

    private detectEmbeddables(entity: string): string[] {
        const entityPath = path.join(
            this.projectRoot,
            "src/main/java/com/upsaude/entity",
            `${entity}.java`
        );

        if (!fs.existsSync(entityPath)) return [];

        const content = fs.readFileSync(entityPath, "utf-8");

        const regex = /@Embedded\s+private\s+(\w+)/g;
        const embeddables: string[] = [];

        let match;
        while ((match = regex.exec(content)) !== null) {
            embeddables.push(match[1]);
        }

        return embeddables;
    }
}
