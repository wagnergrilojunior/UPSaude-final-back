#!/usr/bin/env node
import { Server } from "@modelcontextprotocol/sdk/server/index.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import {
    CallToolRequestSchema,
    ListToolsRequestSchema,
} from "@modelcontextprotocol/sdk/types.js";

import { ContextProvider } from "./config/contextProvider.js";
import { ExecutionEnvironment } from "./config/executionEnvironment.js";
import { ToolRegistry } from "./config/toolRegistry.js";

// ------------------------------------------------------------
// 1. Preparação do projeto
// ------------------------------------------------------------
const PROJECT_ROOT =
    process.env.UPSAUDE_PROJECT_ROOT || process.cwd();

const contextProvider = new ContextProvider(PROJECT_ROOT);
const toolRegistry = new ToolRegistry();
const executor = new ExecutionEnvironment(contextProvider);

// ------------------------------------------------------------
// 2. Instanciando o servidor MCP
// ------------------------------------------------------------
async function main() {
    const server = new Server(
        {
            name: "upsaude-back-mcp",
            version: "3.0.0",
        },
        { capabilities: { tools: {} } }
    );

    // --------------------------------------------------------
    // 3. Fornecer lista de tools ao Cursor
    // --------------------------------------------------------
    server.setRequestHandler(ListToolsRequestSchema, async () => {
        const tools = toolRegistry.list();

        return {
            tools: tools.map((t: any) => ({
                name: t.name,
                description: t.description,
                inputSchema: t.inputSchema,
            })),
        };
    });

    // --------------------------------------------------------
    // 4. Executar tools quando o Cursor chamar
    // --------------------------------------------------------
    server.setRequestHandler(CallToolRequestSchema, async (req) => {
        const toolName = req.params.name;
        const args = req.params.arguments ?? {};

        const tool = toolRegistry.get(toolName);

        if (!tool) {
            return {
                isError: true,
                content: [
                    {
                        type: "text",
                        text: `❌ Tool '${toolName}' não encontrada.`,
                    },
                ],
            };
        }

        const result = await executor.execute(tool, args);

        return {
            content: [
                {
                    type: "text",
                    text: typeof result === "string" ? result : JSON.stringify(result, null, 2),
                },
            ],
        };
    });

    // --------------------------------------------------------
    // 5. Inicializar transporte STDIO
    // --------------------------------------------------------
    const transport = new StdioServerTransport();
    await server.connect(transport);

    console.error("🚀 MCP UPSaude iniciado com sucesso.");
    console.error("📌 Projeto carregado: " + PROJECT_ROOT);
}

main().catch((err) => {
    console.error("❌ Erro fatal ao iniciar MCP:", err);
    process.exit(1);
});
