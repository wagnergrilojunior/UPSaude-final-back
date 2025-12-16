import { loadMergedRules } from "../rules/mergedRules.js";
import { ProjectHealthAnalyzer } from "../analyzers/projectHealthAnalyzer.js";

export async function analyzeProjectTool(projectRoot: string) {
    const analyzer = new ProjectHealthAnalyzer(projectRoot);
    const report = await analyzer.generateReport();

    return {
        content: [{ type: "text", text: report }],
    };
}

export const analyzeProjectToolDefinition = {
    name: "analyze_project",
    description: "Análise completa do projeto, incluindo entidades, mappers, services, controllers e estrutura.",
    inputSchema: { type: "object", properties: {} },
};
