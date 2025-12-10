import { z } from "zod";
import { AutoRefactorEngine } from "../fixers/autoRefactorEngine.js";
import { loadMergedRules } from "../rules/mergedRules.js";

export const AutoRefactorSchema = z.object({
    entityName: z.string(),
});

export async function autoRefactorTool(projectRoot: string, args: unknown) {
    const params = AutoRefactorSchema.parse(args);

    const engine = new AutoRefactorEngine(projectRoot);
    const report = await engine.refactor(params.entityName);

    return {
        content: [{ type: "text", text: report }],
    };
}

export const autoRefactorToolDefinition = {
    name: "auto_refactor",
    description: "Executa refatoração automática profunda.",
    inputSchema: AutoRefactorSchema,
};
