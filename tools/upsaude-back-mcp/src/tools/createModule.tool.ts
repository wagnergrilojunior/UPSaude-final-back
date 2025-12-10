import { z } from "zod";
import { FullModuleGenerator } from "../generators/fullModuleGenerator.js";

export const CreateModuleSchema = z.object({
    entityName: z.string(),
});

export async function createModuleTool(projectRoot: string, args: unknown) {
    const params = CreateModuleSchema.parse(args);

    const generator = new FullModuleGenerator(projectRoot);
    const report = await generator.generate(params.entityName);

    return {
        content: [{ type: "text", text: report }],
    };
}

export const createModuleToolDefinition = {
    name: "create_module",
    description: "Cria um módulo completo a partir de uma entidade (entity, request, response, dto, mapper, repo, service, controller).",
    inputSchema: CreateModuleSchema,
};
