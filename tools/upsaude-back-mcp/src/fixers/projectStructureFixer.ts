import fs from "fs";
import path from "path";
import { logger } from "../core/logger.js";
import { ProjectStructureRules } from "../core/types.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

/**
 * ProjectStructureFixer
 * ----------------------
 * Garante que o projeto siga a estrutura de pastas definida pelas regras.
 *
 * Ele pode:
 *   ✔ Criar pastas obrigatórias se estiverem faltando  
 *   ✔ Verificar pacotes quebrados  
 *   ✔ Garantir que cada camada esteja no lugar correto  
 *   ✔ Gerar relatório de problemas estruturais  
 *   ✔ Corrigir automaticamente pastas inconsistentes  
 */
export class ProjectStructureFixer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }


    /**
     * Método principal de correção (alias para fixStructure).
     */
    async fix(): Promise<string> {
        return this.fixStructure();
    }

    /**
     * Aplica correções estruturais em todo o projeto.
     */
    async fixStructure(): Promise<string> {
        logger.info("📁 Executando ProjectStructureFixer...");

        const structureRules = this.rules.project_structure as ProjectStructureRules;
        if (!structureRules) {
            return "⚠️ Nenhuma regra de estrutura encontrada.";
        }

        const report: string[] = [];

        // ---------------------------------------------------------------------
        // 1. Criar pastas obrigatórias se estiverem faltando
        // ---------------------------------------------------------------------
        report.push("## 🔧 Verificando pastas obrigatórias...\n");

        const requiredDirs = structureRules.required_directories ?? [];

        requiredDirs.forEach((dir: string) => {
            const fullPath = path.join(this.projectRoot, dir);

            if (!fs.existsSync(fullPath)) {
                fs.mkdirSync(fullPath, { recursive: true });
                report.push(`✔ Criada pasta faltante: ${dir}`);
                logger.warn(`Criando pasta ausente: ${fullPath}`);
            } else {
                report.push(`✔ Pasta OK: ${dir}`);
            }
        });

        // ---------------------------------------------------------------------
        // 2. Detectar arquivos fora do pacote correto
        // ---------------------------------------------------------------------
        report.push("\n## 🔍 Verificando arquivos fora do pacote correto...\n");

        const misplaced = this.findMisplacedFiles(structureRules);

        if (misplaced.length > 0) {
            report.push("### ⚠️ Arquivos fora da estrutura esperada:");
            misplaced.forEach(m => report.push(`- ${m.file} deveria estar em ${m.expected}`));

            if (structureRules.auto_fix) {
                report.push("\n### ✨ Movendo arquivos automaticamente...");

                misplaced.forEach(m => {
                    const newPath = path.join(this.projectRoot, m.expected, path.basename(m.file));
                    const folder = path.dirname(newPath);

                    if (!fs.existsSync(folder)) {
                        fs.mkdirSync(folder, { recursive: true });
                    }

                    fs.renameSync(m.file, newPath);

                    report.push(`✔ Movido: ${path.basename(m.file)} → ${m.expected}`);
                });
            }
        } else {
            report.push("✔ Nenhum arquivo fora da estrutura.");
        }

        // ---------------------------------------------------------------------
        // 3. Criar arquivos padrão se estiverem faltando (ex: ApiExceptionHandler)
        // ---------------------------------------------------------------------
        if (structureRules.create_missing_templates) {
            report.push("\n## 📄 Criando templates obrigatórios faltantes...\n");

            const templates = structureRules.missing_templates ?? [];

            templates.forEach((template: any) => {
                const filePath = path.join(this.projectRoot, template.path);

                if (!fs.existsSync(filePath)) {
                    fs.mkdirSync(path.dirname(filePath), { recursive: true });
                    fs.writeFileSync(filePath, template.content, "utf-8");

                    report.push(`✔ Template criado: ${template.path}`);
                } else {
                    report.push(`✔ Template OK: ${template.path}`);
                }
            });
        }

        return report.join("\n");
    }

    // =========================================================================
    // Localiza arquivos que estão fora da pasta correta
    // =========================================================================
    private findMisplacedFiles(structureRules: ProjectStructureRules) {
        const result: Array<{ file: string; expected: string }> = [];

        const checkRules = structureRules.misplaced_checks ?? [];

        checkRules.forEach((rule: any) => {
            const realPath = path.join(this.projectRoot, rule.scan_dir);
            if (!fs.existsSync(realPath)) return;

            const files = this.collectFiles(realPath);

            files.forEach(file => {
                // Se o arquivo não contém o pacote esperado
                if (!file.includes(rule.must_be_in)) {
                    result.push({
                        file,
                        expected: rule.must_be_in,
                    });
                }
            });
        });

        return result;
    }

    // =========================================================================
    // Função utilitária para coletar arquivos de forma recursiva
    // =========================================================================
    private collectFiles(dir: string): string[] {
        let results: string[] = [];
        const list = fs.readdirSync(dir);

        list.forEach(file => {
            const full = path.join(dir, file);
            const stat = fs.statSync(full);

            if (stat && stat.isDirectory()) {
                results = results.concat(this.collectFiles(full));
            } else {
                results.push(full);
            }
        });

        return results;
    }
}
