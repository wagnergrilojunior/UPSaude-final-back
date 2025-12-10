import { z } from "zod";
import { EntityAnalyzer } from "../analyzers/entityAnalyzer.js";
import { loadMergedRules } from "../rules/mergedRules.js";
import { logger } from "../core/logger.js";

export const AnalyzeEntitySchema = z.object({
    entityName: z.string()
});

/**
 * MCP Tool: analyze_entity
 * 
 * Executa uma análise profunda da entidade com base em:
 * - master-rules.yaml
 * - architecture-rules.yaml
 * - validation-rules.yaml
 * - patterns.yaml
 * - mapper-patterns.yaml
 * - service-patterns.yaml
 * - controller-patterns.yaml
 */
export async function analyzeEntityTool(projectRoot: string, args: unknown) {
    const params = AnalyzeEntitySchema.parse(args);

    logger.info(`🔍 Analisando entidade: ${params.entityName}`);

    // Carrega todas as regras fundidas
    const rules = await loadMergedRules(projectRoot);

    // Executa análise da entidade
    const analyzer = new EntityAnalyzer(projectRoot, rules);
    const report = await analyzer.analyze(params.entityName);

    logger.success(`📘 Análise concluída para: ${params.entityName}`);

    return {
        content: [
            {
                type: "text",
                text: report
            }
        ]
    };
}

/**
 * Registro formal da ferramenta para o MCP
 */
export const analyzeEntityToolDefinition = {
    name: "analyze_entity",
    description: "Analisa uma entidade Java completa verificando regras de arquitetura, validação, business rules e boas práticas UPSaude.",
    inputSchema: AnalyzeEntitySchema
};
