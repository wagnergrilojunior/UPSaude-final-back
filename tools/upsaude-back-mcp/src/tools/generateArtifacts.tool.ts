import { z } from "zod";
import { EntityArtifactsGenerator } from "../generators/entityArtifacts.js";

export const GenerateEntityArtifactsSchema = z.object({
    entityName: z.string(),
});

export async function generateEntityArtifactsTool(projectRoot: string, args: unknown) {
    const params = GenerateEntityArtifactsSchema.parse(args);

    const generator = new EntityArtifactsGenerator(projectRoot);
    const result = await generator.generate(params.entityName);

    return {
        content: [{ type: "text", text: result }],
    };
}

export const generateEntityArtifactsToolDefinition = {
    name: "generate_entity_artifacts",
    description: "Gera todos os arquivos de uma entidade.",
    inputSchema: GenerateEntityArtifactsSchema,
};
