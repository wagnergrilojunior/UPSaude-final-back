import { ProjectHealthAnalyzer } from "../analyzers/projectHealthAnalyzer.js";
import { loadMergedRules } from "../rules/mergedRules.js";

export async function analyzeProjectHealthTool(projectRoot: string) {
    const analyzer = new ProjectHealthAnalyzer(projectRoot);
    const report = await analyzer.generateReport();

    return {
        content: [{ type: "text", text: report }],
    };
}

export const analyzeProjectHealthToolDefinition = {
    name: "analyze_project_health",
    description: "Analisa o estado geral do projeto.",
    inputSchema: { type: "object", properties: {} },
};
