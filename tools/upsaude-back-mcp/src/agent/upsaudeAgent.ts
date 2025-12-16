/**
 * Agente de IA Personalizado para UPSaude MCP
 * 
 * Este agente utiliza as ferramentas do MCP de forma inteligente para:
 * - Analisar e entender o contexto do projeto
 * - Planejar ações baseadas em objetivos
 * - Executar correções e melhorias automáticas
 * - Aprender com o histórico de ações
 * - Fornecer recomendações inteligentes
 */

import { ToolRegistry } from "../config/toolRegistry.js";
import { ContextProvider } from "../config/contextProvider.js";
import { ExecutionEnvironment } from "../config/executionEnvironment.js";
import { logger } from "../core/logger.js";
import type { ToolDefinition } from "../core/types.js";

export interface AgentGoal {
    description: string;
    priority: "low" | "medium" | "high" | "critical";
    tools?: string[]; // Ferramentas sugeridas para este objetivo
}

export interface AgentContext {
    projectRoot: string;
    currentEntity?: string;
    recentActions: string[];
    projectHealth?: {
        score: number;
        issues: string[];
        warnings: string[];
    };
}

export interface AgentPlan {
    goal: AgentGoal;
    steps: PlanStep[];
    estimatedTime?: string;
}

export interface PlanStep {
    tool: string;
    description: string;
    args?: Record<string, any>;
    dependsOn?: string[]; // IDs de steps anteriores
}

export class UpSaudeAgent {
    private toolRegistry: ToolRegistry;
    private contextProvider: ContextProvider;
    private executor: ExecutionEnvironment;
    private context: AgentContext;
    private memory: Map<string, any>;

    constructor(projectRoot: string) {
        this.contextProvider = new ContextProvider(projectRoot);
        this.toolRegistry = new ToolRegistry();
        this.executor = new ExecutionEnvironment(this.contextProvider);
        this.context = {
            projectRoot,
            recentActions: [],
        };
        this.memory = new Map();
    }

    /**
     * Analisa o objetivo e cria um plano de ação inteligente.
     */
    async plan(goal: AgentGoal): Promise<AgentPlan> {
        logger.info(`🎯 Criando plano para: ${goal.description}`);

        const steps: PlanStep[] = [];
        const availableTools = this.toolRegistry.list();

        // 1. Sempre começar com análise do projeto se não tivermos contexto
        if (!this.context.projectHealth) {
            steps.push({
                tool: "analyze_project_health",
                description: "Analisar saúde geral do projeto para entender o contexto atual",
                args: {},
            });
        }

        // 2. Se o objetivo menciona uma entidade específica, analisar primeiro
        const entityMatch = goal.description.match(/(?:entidade|entity)\s+['"]?(\w+)['"]?/i);
        if (entityMatch) {
            const entityName = entityMatch[1];
            this.context.currentEntity = entityName;

            steps.push({
                tool: "analyze_entity",
                description: `Analisar entidade ${entityName} para identificar problemas`,
                args: { entityName },
                dependsOn: steps.length > 0 ? [steps[0].tool] : undefined,
            });

            // Se o objetivo é corrigir ou melhorar, adicionar fix
            if (goal.description.match(/(?:corrigir|fix|melhorar|improve)/i)) {
                steps.push({
                    tool: "fix_entity",
                    description: `Corrigir problemas encontrados na entidade ${entityName}`,
                    args: { entityName },
                    dependsOn: [`analyze_entity_${entityName}`],
                });
            }
        }

        // 3. Se o objetivo é criar algo novo
        if (goal.description.match(/(?:criar|create|gerar|generate)\s+(?:módulo|module|entidade|entity)/i)) {
            const entityMatch = goal.description.match(/(?:criar|create|gerar|generate).*?['"]?(\w+)['"]?/i);
            if (entityMatch) {
                const entityName = entityMatch[1];
                steps.push({
                    tool: "create_module",
                    description: `Criar módulo completo para ${entityName}`,
                    args: { entityName },
                });
            }
        }

        // 4. Se o objetivo é auditoria completa
        if (goal.description.match(/(?:auditar|audit|verificar|check)\s+(?:tudo|all|completo|complete)/i)) {
            steps.push({
                tool: "full_audit",
                description: "Executar auditoria completa do projeto",
                args: {},
            });
        }

        // 5. Se o objetivo é refatoração automática
        if (goal.description.match(/(?:refatorar|refactor|corrigir|fix)\s+(?:tudo|all|automático|auto)/i)) {
            steps.push({
                tool: "auto_fix_all",
                description: "Executar refatoração automática completa",
                args: {},
            });
        }

        // 6. Se nenhum padrão específico foi encontrado, usar ferramentas sugeridas
        if (steps.length === 0 && goal.tools && goal.tools.length > 0) {
            goal.tools.forEach((toolName) => {
                const tool = this.toolRegistry.get(toolName);
                if (tool) {
                    steps.push({
                        tool: toolName,
                        description: `Executar ${toolName} conforme objetivo`,
                        args: {},
                    });
                }
            });
        }

        // Se ainda não temos steps, fazer análise geral
        if (steps.length === 0) {
            steps.push({
                tool: "analyze_project_health",
                description: "Analisar projeto para entender o estado atual",
                args: {},
            });
        }

        return {
            goal,
            steps,
            estimatedTime: this.estimateTime(steps),
        };
    }

    /**
     * Executa um plano completo, passo a passo.
     */
    async executePlan(plan: AgentPlan): Promise<string> {
        logger.info(`🚀 Executando plano: ${plan.goal.description}`);
        logger.info(`📋 ${plan.steps.length} passo(s) planejado(s)`);

        const results: string[] = [];
        const executedSteps = new Set<string>();

        for (let i = 0; i < plan.steps.length; i++) {
            const step = plan.steps[i];

            // Verificar dependências
            if (step.dependsOn && step.dependsOn.length > 0) {
                const allDepsMet = step.dependsOn.every((dep) => executedSteps.has(dep));
                if (!allDepsMet) {
                    logger.warn(`⏸️ Aguardando dependências para ${step.tool}`);
                    continue;
                }
            }

            try {
                logger.info(`▶️ Executando passo ${i + 1}/${plan.steps.length}: ${step.description}`);
                
                const tool = this.toolRegistry.get(step.tool);
                if (!tool) {
                    results.push(`❌ Ferramenta ${step.tool} não encontrada`);
                    continue;
                }

                const result = await this.executor.execute(tool, step.args || {});
                const resultText = typeof result === "string" ? result : JSON.stringify(result, null, 2);
                
                results.push(`\n## Passo ${i + 1}: ${step.description}\n${resultText}`);
                executedSteps.add(step.tool);
                this.context.recentActions.push(`${step.tool}: ${step.description}`);

                // Limitar histórico de ações
                if (this.context.recentActions.length > 10) {
                    this.context.recentActions.shift();
                }

            } catch (error: any) {
                const errorMsg = error instanceof Error ? error.message : String(error);
                logger.error(`❌ Erro ao executar ${step.tool}: ${errorMsg}`);
                results.push(`❌ Erro no passo ${i + 1}: ${errorMsg}`);
            }
        }

        const finalReport = this.generateReport(plan, results);
        return finalReport;
    }

    /**
     * Método principal: recebe um objetivo e executa automaticamente.
     */
    async achieveGoal(goalDescription: string, priority: AgentGoal["priority"] = "medium"): Promise<string> {
        const goal: AgentGoal = {
            description: goalDescription,
            priority,
        };

        const plan = await this.plan(goal);
        return await this.executePlan(plan);
    }

    /**
     * Aprende com o contexto e sugere melhorias.
     */
    async suggestImprovements(): Promise<string[]> {
        const suggestions: string[] = [];

        // Se não temos análise de saúde, sugerir
        if (!this.context.projectHealth) {
            suggestions.push("Execute 'analyze_project_health' para entender o estado do projeto");
        }

        // Se temos entidade atual, sugerir análise
        if (this.context.currentEntity) {
            suggestions.push(`Analise a entidade ${this.context.currentEntity} com 'analyze_entity'`);
        }

        // Se temos muitas ações recentes de fix, sugerir auditoria
        const recentFixes = this.context.recentActions.filter((a) => a.includes("fix"));
        if (recentFixes.length > 3) {
            suggestions.push("Considere executar 'full_audit' para verificar todas as correções");
        }

        return suggestions;
    }

    /**
     * Obtém contexto atual do agente.
     */
    getContext(): AgentContext {
        return { ...this.context };
    }

    /**
     * Atualiza o contexto do agente.
     */
    updateContext(updates: Partial<AgentContext>): void {
        this.context = { ...this.context, ...updates };
    }

    /**
     * Salva informação na memória do agente.
     */
    remember(key: string, value: any): void {
        this.memory.set(key, value);
    }

    /**
     * Recupera informação da memória do agente.
     */
    recall(key: string): any {
        return this.memory.get(key);
    }

    /**
     * Estima tempo de execução baseado nos steps.
     */
    private estimateTime(steps: PlanStep[]): string {
        const avgTimePerStep = 5; // segundos em média
        const totalSeconds = steps.length * avgTimePerStep;
        
        if (totalSeconds < 60) {
            return `${totalSeconds}s`;
        }
        return `${Math.ceil(totalSeconds / 60)}min`;
    }

    /**
     * Gera relatório final da execução.
     */
    private generateReport(plan: AgentPlan, results: string[]): string {
        let report = `# 🎯 Relatório de Execução do Agente\n\n`;
        report += `**Objetivo:** ${plan.goal.description}\n`;
        report += `**Prioridade:** ${plan.goal.priority}\n`;
        report += `**Tempo estimado:** ${plan.estimatedTime}\n\n`;
        report += `## 📊 Resultados\n\n`;
        
        results.forEach((result, index) => {
            report += result + "\n\n";
        });

        report += `## ✅ Conclusão\n\n`;
        report += `Plano executado com ${results.length} passo(s). `;
        
        const successCount = results.filter((r) => !r.includes("❌")).length;
        report += `${successCount} passo(s) concluído(s) com sucesso.\n`;

        return report;
    }
}
