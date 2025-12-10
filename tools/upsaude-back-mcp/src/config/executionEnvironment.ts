import { createLogger, type Logger } from "../core/logger.js";
import type { ContextProvider } from "./contextProvider.js";
import type { ToolDefinition } from "../core/types.js";

export class ExecutionEnvironment {
    private context: ContextProvider;
    private logger: Logger;

    constructor(contextProvider: ContextProvider) {
        this.context = contextProvider;
        this.logger = createLogger("ExecutionEnvironment");
    }

    async execute(toolDefinition: ToolDefinition, args: Record<string, any> = {}): Promise<any> {
        try {
            this.logger.info(`Executando tool: ${toolDefinition.name}`);

            const projectRoot = this.context.getProjectRoot();

            if (!toolDefinition || typeof toolDefinition === "undefined") {
                throw new Error("ToolDefinition inválido.");
            }

            if (!toolDefinition.handler) {
                throw new Error(`Tool ${toolDefinition.name} não possui handler definido.`);
            }

            const result = await toolDefinition.handler(projectRoot, args);

            this.logger.info(`Tool concluída: ${toolDefinition.name}`);
            return result;
        } catch (error: unknown) {
            const errorMessage = error instanceof Error ? error.message : String(error);
            this.logger.error(
                `Erro ao executar tool ${toolDefinition.name}: ${errorMessage}`,
            );

            return {
                content: [
                    {
                        type: "text",
                        text: `Erro ao executar a tool '${toolDefinition.name}': ${errorMessage}`,
                    },
                ],
            };
        }
    }
}
