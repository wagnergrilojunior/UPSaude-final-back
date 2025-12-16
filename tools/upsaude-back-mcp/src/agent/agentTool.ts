/**
 * Tool MCP para usar o Agente de IA Personalizado
 * 
 * Permite que o Cursor use o agente através do MCP.
 */

import { z } from "zod";
import { UpSaudeAgent } from "./upsaudeAgent.js";
import { logger } from "../core/logger.js";

export const AgentGoalSchema = z.object({
    goal: z.string().describe("Descrição do objetivo a ser alcançado"),
    priority: z.enum(["low", "medium", "high", "critical"]).optional().default("medium"),
});

export const AgentSuggestSchema = z.object({
    context: z.string().optional().describe("Contexto adicional para as sugestões"),
});

/**
 * Tool: agent_achieve_goal
 * 
 * Permite que o Cursor use o agente para alcançar objetivos complexos.
 */
export async function agentAchieveGoalTool(projectRoot: string, args: unknown) {
    const params = AgentGoalSchema.parse(args);

    logger.info(`🤖 Agente recebeu objetivo: ${params.goal}`);

    const agent = new UpSaudeAgent(projectRoot);
    const result = await agent.achieveGoal(params.goal, params.priority);

    return {
        content: [
            {
                type: "text",
                text: result,
            },
        ],
    };
}

/**
 * Tool: agent_suggest_improvements
 * 
 * Obtém sugestões inteligentes baseadas no contexto atual.
 */
export async function agentSuggestImprovementsTool(projectRoot: string, args: unknown) {
    const params = AgentSuggestSchema.parse(args);

    logger.info("💡 Agente gerando sugestões de melhorias");

    const agent = new UpSaudeAgent(projectRoot);
    
    // Atualizar contexto se fornecido
    if (params.context) {
        agent.updateContext({
            currentEntity: params.context.match(/(?:entidade|entity)\s+['"]?(\w+)['"]?/i)?.[1],
        });
    }

    const suggestions = await agent.suggestImprovements();

    let report = `# 💡 Sugestões de Melhorias\n\n`;
    
    if (suggestions.length === 0) {
        report += "✅ Nenhuma sugestão no momento. O projeto está em bom estado!\n";
    } else {
        suggestions.forEach((suggestion, index) => {
            report += `${index + 1}. ${suggestion}\n`;
        });
    }

    return {
        content: [
            {
                type: "text",
                text: report,
            },
        ],
    };
}

/**
 * Tool: agent_get_context
 * 
 * Obtém o contexto atual do agente.
 */
export async function agentGetContextTool(projectRoot: string, args?: unknown) {
    const agent = new UpSaudeAgent(projectRoot);
    const context = agent.getContext();

    const report = `# 📋 Contexto do Agente\n\n`;
    const contextJson = JSON.stringify(context, null, 2);

    return {
        content: [
            {
                type: "text",
                text: report + "```json\n" + contextJson + "\n```",
            },
        ],
    };
}

/**
 * Definições das tools do agente para registro no MCP.
 */
export const agentAchieveGoalToolDefinition = {
    name: "agent_achieve_goal",
    description: "Usa o agente de IA personalizado para alcançar objetivos complexos no projeto. O agente planeja e executa ações automaticamente usando as ferramentas disponíveis.",
    inputSchema: AgentGoalSchema,
    handler: agentAchieveGoalTool,
};

export const agentSuggestImprovementsToolDefinition = {
    name: "agent_suggest_improvements",
    description: "Obtém sugestões inteligentes de melhorias baseadas no contexto atual do projeto.",
    inputSchema: AgentSuggestSchema,
    handler: agentSuggestImprovementsTool,
};

export const agentGetContextToolDefinition = {
    name: "agent_get_context",
    description: "Obtém o contexto atual do agente, incluindo ações recentes e estado do projeto.",
    inputSchema: z.object({}),
    handler: agentGetContextTool,
};
