import {
    analyzeEntityToolDefinition,
} from "../tools/analyzeEntity.tool.js";

import {
    analyzeProjectToolDefinition,
} from "../tools/analyzeProject.tool.js";

import {
    autoFixAllToolDefinition,
} from "../tools/autoFixAll.tool.js";

import {
    createModuleToolDefinition,
} from "../tools/createModule.tool.js";

import {
    fullAuditToolDefinition,
} from "../tools/fullAudit.tool.js";

import {
    testToolDefinition,
} from "../tools/test.tool.js";

import {
    agentAchieveGoalToolDefinition,
    agentSuggestImprovementsToolDefinition,
    agentGetContextToolDefinition,
} from "../agent/agentTool.js";

import type { ToolDefinition } from "../core/types.js";

export class ToolRegistry {
    private tools: Map<string, ToolDefinition>;

    constructor() {
        this.tools = new Map();

        this.register(analyzeEntityToolDefinition);
        this.register(analyzeProjectToolDefinition);
        this.register(autoFixAllToolDefinition);
        this.register(createModuleToolDefinition);
        this.register(fullAuditToolDefinition);
        this.register(testToolDefinition);
        
        // Agente de IA Personalizado
        this.register(agentAchieveGoalToolDefinition);
        this.register(agentSuggestImprovementsToolDefinition);
        this.register(agentGetContextToolDefinition);
    }

    register(definition: ToolDefinition): void {
        if (!definition || !definition.name) {
            throw new Error("Invalid tool definition");
        }
        this.tools.set(definition.name, definition);
    }

    get(name: string): ToolDefinition | undefined {
        return this.tools.get(name);
    }

    list(): ToolDefinition[] {
        return Array.from(this.tools.values());
    }
}
