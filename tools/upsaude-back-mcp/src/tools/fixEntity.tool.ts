import { z } from "zod";
import { EntityFixer } from "../fixers/entityFixer.js";
import { loadMergedRules } from "../rules/mergedRules.js";

export const FixEntitySchema = z.object({
    entityName: z.string(),
});

export async function fixEntityTool(projectRoot: string, args: unknown) {
    const params = FixEntitySchema.parse(args);

    const fixer = new EntityFixer(projectRoot);
    const result = await fixer.fix(params.entityName);

    return {
        content: [{ type: "text", text: result }],
    };
}

export const fixEntityToolDefinition = {
    name: "fix_entity",
    description: "Corrige automaticamente a entidade conforme regras.",
    inputSchema: FixEntitySchema,
};
