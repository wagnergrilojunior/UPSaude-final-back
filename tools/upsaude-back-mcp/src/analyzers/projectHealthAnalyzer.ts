import fs from "fs";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

export class ProjectHealthAnalyzer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }

    /**
     * Gera relatório completo de saúde do projeto.
     */
    async generateReport(): Promise<string> {
        const analysis = this.analyzeProject();
        let report = `# 📊 Relatório de Saúde do Projeto\n\n`;
        report += `**Score:** ${analysis.score}/100\n\n`;
        
        if (analysis.issues.length > 0) {
            report += `## ❌ Problemas (${analysis.issues.length})\n\n`;
            analysis.issues.forEach((issue, i) => {
                report += `${i + 1}. ${issue}\n`;
            });
            report += `\n`;
        }

        if (analysis.warnings.length > 0) {
            report += `## ⚠️ Avisos (${analysis.warnings.length})\n\n`;
            analysis.warnings.forEach((warning, i) => {
                report += `${i + 1}. ${warning}\n`;
            });
            report += `\n`;
        }

        return report;
    }

    /**
     * Valida apenas sem gerar relatório completo.
     */
    async validateOnly(): Promise<string> {
        const analysis = this.analyzeProject();
        if (analysis.issues.length === 0) {
            return "✅ Projeto válido!";
        }
        return `❌ ${analysis.issues.length} problema(s) encontrado(s)`;
    }

    analyzeProject() {
        logger.info("Iniciando auditoria completa do projeto...");

        const report = {
            projectRoot: this.projectRoot,
            score: 100,
            issues: [] as string[],
            warnings: [] as string[],
            info: [] as string[],
            fileStats: [] as any[]
        };

        const allFiles = this.getAllJavaFiles();

        allFiles.forEach((filePath) => {
            const relative = path.relative(this.projectRoot, filePath);
            const content = fs.readFileSync(filePath, "utf-8");

            const stats = {
                file: relative,
                lines: content.split("\n").length,
                sizeKB: (content.length / 1024).toFixed(2)
            };

            report.fileStats.push(stats);

            //
            // 1 — Arquivos muito grandes
            //
            if (stats.lines > 800) {
                report.warnings.push(`Arquivo grande demais (${stats.lines} linhas): ${relative}`);
                report.score -= 2;
            }
            if (stats.lines > 1500) {
                report.issues.push(`Arquivo GIGANTE → Refatore urgente: ${relative}`);
                report.score -= 5;
            }

            //
            // 2 — Classes sem comentários
            //
            if (!/\/\*\*([\s\S]*?)\*\//.test(content)) {
                report.warnings.push(`Arquivo sem documentação Javadoc: ${relative}`);
                report.score -= 1;
            }

            //
            // 3 — Métodos muito grandes
            //
            const largeMethods = this.findLargeMethods(content);
            if (largeMethods.length > 0) {
                report.warnings.push(
                    `Métodos muito grandes em ${relative}: ${largeMethods.join(", ")}`
                );
                report.score -= largeMethods.length;
            }

            //
            // 4 — Código duplicado
            //
            if (this.containsCopyPastePatterns(content)) {
                report.warnings.push(`Possível código duplicado detectado em ${relative}`);
                report.score -= 2;
            }

            //
            // 5 — Controllers acessando Repository → erro grave
            //
            if (relative.includes("/controller/") && content.includes("Repository")) {
                report.issues.push(`Controller acessando Repository → ARQUITETURA VIOLADA: ${relative}`);
                report.score -= 8;
            }

            //
            // 6 — Controller acessando Entity
            //
            if (relative.includes("/controller/") && /\.entity\./.test(content)) {
                report.issues.push(`Controller manipulando Entity diretamente: ${relative}`);
                report.score -= 5;
            }

            //
            // 7 — Services sem interface correspondente
            //
            if (relative.includes("/service/impl/")) {
                const interfaceName = relative
                    .replace("/impl/", "/")
                    .replace("Impl.java", ".java");

                const interfacePath = path.join(this.projectRoot, interfaceName);

                if (!fs.existsSync(interfacePath)) {
                    report.issues.push(`ServiceImpl SEM interface correspondente: ${relative}`);
                    report.score -= 4;
                }
            }

            //
            // 8 — DTO usado em Controller → proibido
            //
            if (relative.includes("/controller/") && /\bDTO\b/.test(content)) {
                report.issues.push(`Controller usando DTO → proibido: ${relative}`);
                report.score -= 3;
            }

            //
            // 9 — Imports não usados
            //
            const unusedImports = this.detectUnusedImports(content);
            if (unusedImports.length > 0) {
                report.warnings.push(
                    `Imports não usados em ${relative}: ${unusedImports.join(", ")}`
                );
                report.score -= unusedImports.length * 0.5;
            }
        });

        //
        // 10 — Garantir que todos os pacotes obrigatórios existem
        //
        this.validateFolderStructure(report);

        logger.info("Auditoria concluída.");
        return report;
    }

    private getAllJavaFiles(): string[] {
        const results: string[] = [];

        const scan = (dir: string) => {
            if (!fs.existsSync(dir)) return;

            fs.readdirSync(dir).forEach((file) => {
                const full = path.join(dir, file);

                if (fs.statSync(full).isDirectory()) {
                    scan(full);
                } else if (file.endsWith(".java")) {
                    results.push(full);
                }
            });
        };

        scan(path.join(this.projectRoot, "src/main/java"));
        return results;
    }

    private findLargeMethods(content: string): string[] {
        const matches = [...content.matchAll(/public .*?\([\s\S]*?\)\s*\{([\s\S]*?)\n\}/g)];
        const large: string[] = [];

        matches.forEach((m) => {
            const body = m[1];
            const lines = body.split("\n").length;

            if (lines > 80) {
                const signature = m[0].split("{")[0].trim();
                large.push(`${signature} (${lines} linhas)`);
            }
        });

        return large;
    }

    private containsCopyPastePatterns(content: string): boolean {
        return (
            content.includes("catch (Exception") ||
            /(\/\/\s*copiado|COPY|TODO melhorar)/i.test(content)
        );
    }

    private detectUnusedImports(content: string): string[] {
        const importRegex = /import\s+([a-zA-Z0-9_.]+);/g;

        const imports = [...content.matchAll(importRegex)].map((m) => m[1]);
        const unused: string[] = [];

        imports.forEach((imp) => {
            const simpleName = imp.split(".").pop();

            if (simpleName) {
                const regex = new RegExp(`\\b${simpleName}\\b`);
                if (!regex.test(content.replace(new RegExp(`import\\s+${imp};`, "g"), ""))) {
                    unused.push(simpleName);
                }
            }
        });

        return unused;
    }

    private validateFolderStructure(report: any) {
        const requiredFolders = [
            "entity",
            "api/request",
            "api/response",
            "dto",
            "mapper",
            "repository",
            "service",
            "service/impl",
            "controller"
        ];

        requiredFolders.forEach((folder) => {
            const fullPath = path.join(
                this.projectRoot,
                "src/main/java/com/upsaude",
                folder
            );

            if (!fs.existsSync(fullPath)) {
                report.issues.push(`Pasta obrigatória ausente: ${folder}`);
                report.score -= 5;
            }
        });
    }
}
