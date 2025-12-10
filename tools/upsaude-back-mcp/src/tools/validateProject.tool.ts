import { loadMergedRules } from "../rules/mergedRules.js";
import { ProjectHealthAnalyzer } from "../analyzers/projectHealthAnalyzer.js";

export async function validateProjectTool(projectRoot: string) {
    const analyzer = new ProjectHealthAnalyzer(projectRoot);
    const report = await analyzer.validateOnly();

    return {
        content: [{ type: "text", text: report }],
    };
}

export const validateProjectToolDefinition = {
    name: "validate_project",
    description: "Executa validação completa do projeto sem correções.",
    inputSchema: { type: "object", properties: {} },
};
