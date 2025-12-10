import fs from "fs";
import path from "path";
import type { MergedRules } from "../rules/mergedRules.js";
import { logger } from "../core/logger.js";
import type { ServiceAnalysis, ServiceInfo, ServiceMethodInfo } from "../core/types.js";

/**
 * Analyzer responsável por validar Services e ServiceImpl do UPSaude.
 * Regras verificadas:
 *  - Métodos obrigatórios
 *  - Tipos corretos (UUID, Request, Response)
 *  - Uso correto de Mapper
 *  - Uso de Repository
 *  - Uso obrigatório de @Transactional
 *  - Logs padronizados
 *  - Soft delete obrigatório
 *  - Estrutura correta da camada
 */
export class ServiceAnalyzer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string, rules: MergedRules) {
        this.projectRoot = projectRoot;
        this.rules = rules;
    }

    private loadFile(path: string): string {
        if (!fs.existsSync(path)) {
            throw new Error(`Arquivo Service não encontrado: ${path}`);
        }
        return fs.readFileSync(path, "utf-8");
    }

    analyzeFile(filePath: string): ServiceAnalysis {
        logger.info(`Analisando Service: ${filePath}`);
        const source = this.loadFile(filePath);

        const info = this.extractServiceInfo(source);

        return {
            filePath,
            service: info,
            issues: [],
            suggestions: [],
            validations: this.applyRules(info)
        };
    }

    extractServiceInfo(source: string): ServiceInfo {
        return {
            name: this.extractName(source),
            isInterface: /interface\s+[A-Za-z0-9_]+/.test(source),
            isImpl: /class\s+[A-Za-z0-9_]+ServiceImpl/.test(source),
            methods: this.extractMethods(source),
            hasTransactional: /@Transactional/.test(source),
            hasLogger: /log\./.test(source),
            usesMapper: /\.mapper\./.test(source) || /\.map/.test(source) || /Mapper\.class/.test(source),
            usesRepository: /Repository/.test(source),
            rawSource: source
        };
    }

    extractName(source: string): string {
        const match = source.match(/(class|interface)\s+([A-Za-z0-9_]+)/);
        return match ? match[2] : "UnknownService";
    }

    extractMethods(source: string): ServiceMethodInfo[] {
        const regex = /(public|default)\s+([A-Za-z0-9_<>, ]+)\s+([a-zA-Z0-9_]+)\([^)]*\)/g;
        const list: ServiceMethodInfo[] = [];

        let match: RegExpExecArray | null;
        while ((match = regex.exec(source)) !== null) {
            list.push({
                name: match[3],
                returnType: match[2].trim()
            });
        }

        return list;
    }

    applyRules(info: ServiceInfo): string[] {
        const errors: string[] = [];

        //
        // 1 — SERVICE INTERFACE (REGRAS)
        //
        if (info.isInterface) {
            const requiredMethods = ["criar", "obterPorId", "listar", "atualizar", "excluir"];
            const methodNames = info.methods.map(m => m.name);

            requiredMethods.forEach(req => {
                if (!methodNames.includes(req)) {
                    errors.push(`Service '${info.name}' está faltando método obrigatório: ${req}()`);
                }
            });
        }

        //
        // 2 — SERVICE IMPL (REGRAS)
        //
        if (info.isImpl) {
            // 2.1 — Mapper
            if (!info.usesMapper) {
                errors.push(`ServiceImpl '${info.name}' deve utilizar um Mapper.`);
            }

            // 2.2 — Repository obrigatório
            if (!info.usesRepository) {
                errors.push(`ServiceImpl '${info.name}' deve utilizar um Repository.`);
            }

            // 2.3 — @Transactional obrigatório
            if (!info.hasTransactional) {
                errors.push(`ServiceImpl '${info.name}' deve usar @Transactional nos métodos.`);
            }

            // 2.4 — Logs obrigatórios
            if (!info.hasLogger) {
                errors.push(
                    `ServiceImpl '${info.name}' deve usar logs padronizados (log.debug/info/warn/error).`
                );
            }

            // 2.5 — Soft delete obrigatório (active = false)
            if (!/setActive\s*\(\s*false\s*\)/.test(info.rawSource)) {
                errors.push(
                    `ServiceImpl '${info.name}' DEVE realizar soft delete: entity.setActive(false).`
                );
            }

            // 2.6 — Validação de duplicidade
            if (!/validarDuplicidade/.test(info.rawSource)) {
                errors.push(
                    `ServiceImpl '${info.name}' deve possuir método validarDuplicidade() com regras de negócio.`
                );
            }

            // 2.7 — Exception handling
            if (!/BadRequestException/.test(info.rawSource)) {
                errors.push(
                    `ServiceImpl '${info.name}' deve lançar BadRequestException quando necessário.`
                );
            }

            if (!/NotFoundException/.test(info.rawSource)) {
                errors.push(
                    `ServiceImpl '${info.name}' deve lançar NotFoundException para registros não encontrados.`
                );
            }

            // 2.8 — Não permitir lógica pesada
            if (/for\s*\(|while\s*\(/.test(info.rawSource)) {
                errors.push(
                    `ServiceImpl '${info.name}' contém lógica pesada (loops) → mover para domínio.`
                );
            }
        }

        return errors;
    }

    /**
     * Executa auditoria completa de todos os services.
     */
    async auditAll(): Promise<string> {
        const servicesDir = path.join(this.projectRoot, "src/main/java/com/upsaude/service");
        if (!fs.existsSync(servicesDir)) {
            return "❌ Diretório de services não encontrado";
        }

        const files = fs.readdirSync(servicesDir).filter(f => f.endsWith("Service.java") || f.endsWith("ServiceImpl.java"));
        let report = `# 📘 Auditoria Completa de Services\n\n`;
        report += `**Total de services:** ${files.length}\n\n`;

        for (const file of files) {
            const servicePath = path.join(servicesDir, file);
            try {
                const analysis = this.analyzeFile(servicePath);
                const validations = analysis.validations || [];

                report += `## ${file}\n`;
                if (validations.length === 0) {
                    report += "✅ OK\n\n";
                } else {
                    report += `⚠️ ${validations.length} problema(s)\n\n`;
                }
            } catch (e) {
                report += `## ${file}\n❌ Erro ao analisar\n\n`;
            }
        }

        return report;
    }
}
