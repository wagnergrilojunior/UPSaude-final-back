import { loadMergedRules } from "../rules/mergedRules.js";
import { AutoRefactorEngine } from "../fixers/autoRefactorEngine.js";

export async function autoFixAllTool(projectRoot: string) {
    const engine = new AutoRefactorEngine(projectRoot);
    const report = await engine.refactorAll();

    return {
        content: [{ type: "text", text: report }],
    };
}

export const autoFixAllToolDefinition = {
    name: "auto_fix_all",
    description: "Executa refatoração completa em TODO o projeto, aplicando correções automáticas.",
    inputSchema: { type: "object", properties: {} },
};
