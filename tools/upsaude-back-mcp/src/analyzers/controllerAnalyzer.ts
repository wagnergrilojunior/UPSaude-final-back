import fs from "fs";
import path from "path";
import type { MergedRules } from "../rules/mergedRules.js";
import { logger } from "../core/logger.js";
import type { ControllerAnalysis, ControllerInfo, ControllerMethodInfo } from "../core/types.js";

/**
 * Analyzer responsável por validar Controllers da arquitetura UPSaude.
 * Ele verifica:
 *  - Padrão de anotação (@RestController, @RequestMapping)
 *  - Métodos obrigatórios e padronização de retornos com ResponseEntity
 *  - Proibição de retornar Entity ou DTO
 *  - Proibição de acessar Repository ou Entity
 *  - Delegação obrigatória para Services
 *  - Proibição de lógica de negócio no Controller
 *  - Validações no Request (@Valid)
 */
export class ControllerAnalyzer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string, rules: MergedRules) {
        this.projectRoot = projectRoot;
        this.rules = rules;
    }

    private loadFile(path: string): string {
        if (!fs.existsSync(path)) {
            throw new Error(`Arquivo Controller não encontrado: ${path}`);
        }
        return fs.readFileSync(path, "utf-8");
    }

    analyzeFile(filePath: string): ControllerAnalysis {
        logger.info(`Analisando Controller: ${filePath}`);

        const source = this.loadFile(filePath);
        const info = this.extractControllerInfo(source);

        return {
            filePath,
            controller: info,
            issues: [],
            suggestions: [],
            validations: this.applyRules(info)
        };
    }

    extractControllerInfo(source: string): ControllerInfo {
        return {
            name: this.extractName(source),
            hasRestController: /@RestController/.test(source),
            hasRequestMapping: /@RequestMapping\(\"\/api\/v1\//.test(source),
            hasValidAnnotation: /@Valid/.test(source),
            usesService: /\.service\./.test(source) || /Service /.test(source),
            containsLogic: /(for\s*\(|while\s*\(|if\s*\(.{50,}\))/.test(source),
            usesRepository: /Repository/.test(source),
            methods: this.extractMethods(source),
            rawSource: source
        };
    }

    extractName(source: string): string {
        const match = source.match(/class\s+([A-Za-z0-9_]+)/);
        return match ? match[1] : "UnknownController";
    }

    extractMethods(source: string): ControllerMethodInfo[] {
        const regex = /(public)\s+([A-Za-z0-9_<>, ]+)\s+([a-zA-Z0-9_]+)\([^)]*\)/g;
        const list: ControllerMethodInfo[] = [];

        let match: RegExpExecArray | null;

        while ((match = regex.exec(source)) !== null) {
            list.push({
                name: match[3],
                returnType: match[2].trim(),
                isResponseEntity: /ResponseEntity/.test(match[2])
            });
        }

        return list;
    }

    applyRules(info: ControllerInfo): string[] {
        const errors: string[] = [];

        //
        // 1 — Nome correto
        //
        if (!info.name.endsWith("Controller")) {
            errors.push(`O Controller '${info.name}' deve terminar com o sufixo 'Controller'.`);
        }

        //
        // 2 — @RestController obrigatório
        //
        if (!info.hasRestController) {
            errors.push(`Controller '${info.name}' deve possuir @RestController.`);
        }

        //
        // 3 — @RequestMapping obrigatório com prefixo padrão
        //
        if (!info.hasRequestMapping) {
            errors.push(
                `Controller '${info.name}' deve possuir @RequestMapping("/api/v1/...") seguindo o padrão.`
            );
        }

        //
        // 4 — Métodos devem retornar ResponseEntity
        //
        info.methods.forEach((method) => {
            if (!method.isResponseEntity) {
                errors.push(
                    `Método '${method.name}' no Controller '${info.name}' deve retornar ResponseEntity<>`
                );
            }
        });

        //
        // 5 — Proibir retorno de Entity ou DTO
        //
        if (/\b[A-Za-z]+DTO\b/.test(info.rawSource)) {
            errors.push(`Controller '${info.name}' não deve retornar ou manipular DTO.`);
        }
        if (/\b@Entity\b/.test(info.rawSource) || /\.entity\./.test(info.rawSource)) {
            errors.push(`Controller '${info.name}' não deve retornar ou manipular Entities.`);
        }

        //
        // 6 — Proibido acessar Repository diretamente
        //
        if (info.usesRepository) {
            errors.push(`Controller '${info.name}' está acessando Repository → PROIBIDO.`);
        }

        //
        // 7 — Obrigatório delegar para o Service
        //
        if (!info.usesService) {
            errors.push(
                `Controller '${info.name}' deve delegar operações ao Service correspondente.`
            );
        }

        //
        // 8 — Proibido conter lógica de negócio
        //
        if (info.containsLogic) {
            errors.push(
                `Controller '${info.name}' contém lógica de negócio → mover para o Service ou domínio.`
            );
        }

        //
        // 9 — @Valid obrigatório em entradas de Request
        //
        if (!info.hasValidAnnotation) {
            errors.push(`Controller '${info.name}' deve usar @Valid nos parâmetros de entrada.`);
        }

        return errors;
    }

    /**
     * Executa auditoria completa de todos os controllers.
     */
    async auditAll(): Promise<string> {
        const controllersDir = path.join(this.projectRoot, "src/main/java/com/upsaude/controller");
        if (!fs.existsSync(controllersDir)) {
            return "❌ Diretório de controllers não encontrado";
        }

        const files = fs.readdirSync(controllersDir).filter(f => f.endsWith("Controller.java"));
        let report = `# 📘 Auditoria Completa de Controllers\n\n`;
        report += `**Total de controllers:** ${files.length}\n\n`;

        for (const file of files) {
            const controllerPath = path.join(controllersDir, file);
            try {
                const analysis = this.analyzeFile(controllerPath);
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
