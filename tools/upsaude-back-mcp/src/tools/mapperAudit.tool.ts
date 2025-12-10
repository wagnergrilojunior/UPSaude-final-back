import { MapperAnalyzer } from "../analyzers/mapperAnalyzer.js";
import { loadMergedRules } from "../rules/mergedRules.js";

export async function mapperAuditTool(projectRoot: string) {
    const rules = await loadMergedRules(projectRoot);
    const analyzer = new MapperAnalyzer(projectRoot, rules);
    const report = await analyzer.auditAll();

    return {
        content: [{ type: "text", text: report }],
    };
}

export const mapperAuditToolDefinition = {
    name: "mapper_audit",
    description: "Audita todos os mappers do projeto.",
    inputSchema: { type: "object", properties: {} },
};
