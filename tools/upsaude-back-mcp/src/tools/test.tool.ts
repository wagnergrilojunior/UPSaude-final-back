import { z } from "zod";

export const TestSchema = z.object({
    message: z.string().default("OK"),
});

export async function testTool(projectRoot: string, args: unknown) {
    const params = TestSchema.parse(args);

    return {
        content: [{ type: "text", text: `MCP funcionando: ${params.message}` }],
    };
}

export const testToolDefinition = {
    name: "test_mcp",
    description: "Verifica se o MCP está funcionando corretamente.",
    inputSchema: TestSchema,
};
