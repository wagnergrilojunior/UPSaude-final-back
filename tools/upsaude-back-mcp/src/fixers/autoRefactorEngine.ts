import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";
import { ProjectStructureFixer } from "../fixers/projectStructureFixer.js";
import { EntityFixer } from "../fixers/entityFixer.js";
import { RepositoryFixer } from "../fixers/repositoryFixer.js";
import { MapperFixer } from "../fixers/mapperFixer.js";
import { ServiceFixer } from "../fixers/serviceFixer.js";
import { ControllerFixer } from "../fixers/controllerFixer.js";
import { DomainFixer } from "../fixers/domainFixer.js";
import fs from "fs";
import path from "path";

/**
 * AutoRefactorEngine
 * -------------------
 * Esta é a **camada mais poderosa do MCP**.
 *
 * Ele roda a pipeline completa:
 *
 *  ✔ Análise de regras  
 *  ✔ Auditoria do projeto  
 *  ✔ Correções automáticas  
 *  ✔ Refatoração profunda  
 *  ✔ Garantia de aderência total às master-rules  
 *  ✔ Geração de arquivos que estiverem faltando  
 *  ✔ Reestruturação do projeto  
 *
 * É literalmente seu "Arquiteto Sênior Automático".
 */
export class AutoRefactorEngine {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }
    private loadedEntities: string[] = [];


    /**
     * Refatora uma entidade específica.
     */
    async refactor(entityName: string): Promise<string> {
        return this.runFullRefactor(entityName);
    }

    /**
     * Refatora todas as entidades.
     */
    async refactorAll(): Promise<string> {
        return this.runFullRefactor();
    }

    /**
     * Roda TUDO.
     *
     * Você pode chamar:
     * 
     *   /mcp auto_refactor_all
     *
     * Ou:
     *
     *   /mcp auto_refactor_all entityName="Paciente"
     */
    async runFullRefactor(targetEntity?: string): Promise<string> {
        logger.info("🚀 Iniciando AutoRefactorEngine…");

        const report: string[] = [];
        report.push("# 🔧 AUTO REFACTOR REPORT\n");

        // ------------------------------------------------------------
        // 1. Carregar entidades (se não houver alvo, processa todas)
        // ------------------------------------------------------------
        this.loadedEntities = this.findAllEntities();

        const entitiesToProcess = targetEntity
            ? this.loadedEntities.filter(e => e === targetEntity)
            : this.loadedEntities;

        if (entitiesToProcess.length === 0) {
            return "⚠️ Nenhuma entidade encontrada para refatorar.";
        }

        report.push(`## 🧩 Entidades detectadas (${entitiesToProcess.length})`);
        entitiesToProcess.forEach(e => report.push(`- ${e}`));

        // ------------------------------------------------------------
        // 2. Ajustar estrutura de pastas e arquivos
        // ------------------------------------------------------------
        report.push("\n## 📁 Ajustando estrutura do projeto…\n");

        const structureFixer = new ProjectStructureFixer(this.projectRoot);
        const structureResult = await structureFixer.fixStructure();

        report.push(structureResult);

        // ------------------------------------------------------------
        // 3. Loop de refatoração ENTITY / REPO / MAPPER / SERVICE / CONTROLLER
        // ------------------------------------------------------------
        report.push("\n## 🔄 Processando entidades individualmente…\n");

        for (const entity of entitiesToProcess) {
            report.push(`### 🔧 Refatorando: ${entity}`);

            const entityFixer = new EntityFixer(this.projectRoot);
            const repoFixer = new RepositoryFixer(this.projectRoot);
            const mapperFixer = new MapperFixer(this.projectRoot);
            const serviceFixer = new ServiceFixer(this.projectRoot);
            const controllerFixer = new ControllerFixer(this.projectRoot);
            const domainFixer = new DomainFixer(this.projectRoot);

            try {
                const eFix = await entityFixer.fix(entity);
                const rFix = await repoFixer.fix(entity);
                const mFix = await mapperFixer.fix(entity);
                const sFix = await serviceFixer.fix(entity);
                const cFix = await controllerFixer.fix(entity);
                const dFix = await domainFixer.fix(entity);

                report.push(eFix);
                report.push(rFix);
                report.push(mFix);
                report.push(sFix);
                report.push(cFix);
                report.push(dFix);

            } catch (err: any) {
                logger.error(`Erro ao processar ${entity}: ${err.message}`);
                report.push(`❌ Erro ao refatorar ${entity}: ${err.message}`);
            }
        }

        // ------------------------------------------------------------
        // 4. Finalização
        // ------------------------------------------------------------
        report.push("\n## ✅ Refatoração concluída!");

        return report.join("\n");
    }

    // =========================================================================
    // UTILITÁRIOS
    // =========================================================================

    /**
     * Procura todas as entidades no projeto
     */
    private findAllEntities(): string[] {
        const entitiesDir = path.join(this.projectRoot, "src/main/java/com/upsaude/entity");

        if (!fs.existsSync(entitiesDir)) {
            logger.warn("Nenhuma pasta de entidades encontrada.");
            return [];
        }

        const files = fs.readdirSync(entitiesDir);
        return files
            .filter(f => f.endsWith(".java"))
            .map(f => path.basename(f).replace(".java", ""));
    }
}
