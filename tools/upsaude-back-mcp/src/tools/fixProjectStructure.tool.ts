import { ProjectStructureFixer } from "../fixers/projectStructureFixer.js";

export async function fixProjectStructureTool(projectRoot: string) {
    const fixer = new ProjectStructureFixer(projectRoot);
    const result = await fixer.fix();

    return {
        content: [{ type: "text", text: result }],
    };
}

export const fixProjectStructureToolDefinition = {
    name: "fix_project_structure",
    description: "Corrige problemas estruturais do projeto.",
    inputSchema: { type: "object", properties: {} },
};
