import { loadMergedRules } from "../rules/mergedRules.js";
import { ProjectHealthAnalyzer } from "../analyzers/projectHealthAnalyzer.js";
import { MapperAnalyzer } from "../analyzers/mapperAnalyzer.js";
import { ServiceAnalyzer } from "../analyzers/serviceAnalyzer.js";
import { ControllerAnalyzer } from "../analyzers/controllerAnalyzer.js";
import { EntityAnalyzer } from "../analyzers/entityAnalyzer.js";

export async function fullAuditTool(projectRoot: string) {
    const rules = await loadMergedRules(projectRoot);

    const entityReport = await new EntityAnalyzer(projectRoot, rules).auditAll();
    const mapperReport = await new MapperAnalyzer(projectRoot, rules).auditAll();
    const serviceReport = await new ServiceAnalyzer(projectRoot, rules).auditAll();
    const controllerReport = await new ControllerAnalyzer(projectRoot, rules).auditAll();
    const projectHealthReport = await new ProjectHealthAnalyzer(projectRoot).generateReport();

    const final = [
        "===== AUDITORIA COMPLETA DO PROJETO =====\n",
        projectHealthReport,
        entityReport,
        mapperReport,
        serviceReport,
        controllerReport,
    ].join("\n\n");

    return {
        content: [{ type: "text", text: final }],
    };
}

export const fullAuditToolDefinition = {
    name: "full_audit",
    description: "Executa auditoria completa do projeto — entidades, mappers, services, controllers, estrutura e regras.",
    inputSchema: { type: "object", properties: {} },
};
