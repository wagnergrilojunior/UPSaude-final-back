import { z } from "zod";
import { FullModuleGenerator } from "../generators/fullModuleGenerator.js";

export const GenerateFullModuleSchema = z.object({
    entityName: z.string(),
});

export async function generateFullModuleTool(projectRoot: string, args: unknown) {
    const params = GenerateFullModuleSchema.parse(args);

    const generator = new FullModuleGenerator(projectRoot);
    const result = await generator.generate(params.entityName);

    return {
        content: [{ type: "text", text: result }],
    };
}

export const generateFullModuleToolDefinition = {
    name: "generate_full_module",
    description: "Gera entidade completa (Entity, Request, Response, DTO, Mapper, Repo, Service, Controller).",
    inputSchema: GenerateFullModuleSchema,
};
