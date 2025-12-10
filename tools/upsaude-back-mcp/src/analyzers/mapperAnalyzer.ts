import fs from "fs";
import path from "path";
import type { MergedRules } from "../rules/mergedRules.js";
import { logger } from "../core/logger.js";
import type { MapperAnalysis, MapperInfo, MapperMethodInfo } from "../core/types.js";

/**
 * Analyzer responsável por validar Mappers MapStruct do UPSaude.
 * Garante conformidade com padrões:
 *  - estrutura
 *  - métodos obrigatórios
 *  - uso de MappingConfig
 *  - ignorações corretas
 *  - embeddables
 */
export class MapperAnalyzer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string, rules: MergedRules) {
        this.projectRoot = projectRoot;
        this.rules = rules;
    }

    /**
     * Carrega o conteúdo do arquivo de mapper.
     */
    private loadFile(path: string): string {
        if (!fs.existsSync(path)) {
            throw new Error(`Arquivo Mapper não encontrado: ${path}`);
        }
        return fs.readFileSync(path, "utf-8");
    }

    /**
     * Análise principal.
     */
    analyzeFile(filePath: string): MapperAnalysis {
        logger.info(`Analisando Mapper: ${filePath}`);

        const source = this.loadFile(filePath);
        const mapper = this.extractMapperInfo(source);

        return {
            filePath,
            mapper,
            issues: [],
            suggestions: [],
            validations: this.applyRules(mapper)
        };
    }

    /**
     * Extrai metadados do mapper.
     */
    extractMapperInfo(source: string): MapperInfo {
        return {
            name: this.extractName(source),
            extendsInterface: this.extractExtension(source),
            configPresent: this.hasMappingConfig(source),
            methods: this.extractMethods(source),
            rawSource: source
        };
    }

    extractName(source: string): string {
        const match = source.match(/interface\s+([A-Za-z0-9_]+)/);
        return match ? match[1] : "UnknownMapper";
    }

    extractExtension(source: string): string | null {
        const match = source.match(/extends\s+([A-Za-z0-9_<>, ]+)/);
        return match ? match[1].trim() : null;
    }

    hasMappingConfig(source: string): boolean {
        return /config\s*=\s*MappingConfig.class/.test(source);
    }

    /**
     * Extrai métodos do Mapper (simplificado para MapStruct).
     */
    extractMethods(source: string): MapperMethodInfo[] {
        const regex = /([A-Za-z0-9_<>, ]+)\s+([a-zA-Z0-9_]+)\([^)]*\)\s*;/g;
        const list: MapperMethodInfo[] = [];
        let match: RegExpExecArray | null;

        while ((match = regex.exec(source)) !== null) {
            list.push({
                returnType: match[1].trim(),
                name: match[2].trim()
            });
        }

        return list;
    }

    /**
     * Aplica regras extraídas dos arquivos YAML.
     */
    applyRules(mapper: MapperInfo): string[] {
        const errors: string[] = [];
        const requiredMethods = [
            "toEntity",
            "toDTO",
            "fromRequest",
            "updateFromRequest",
            "toResponse"
        ];

        //
        // REGRA 1 — Mapper deve ter MappingConfig
        //
        if (!mapper.configPresent) {
            errors.push(`Mapper '${mapper.name}' precisa usar MappingConfig.`);
        }

        //
        // REGRA 2 — Deve extender EntityMapper
        //
        if (!mapper.extendsInterface || !mapper.extendsInterface.includes("EntityMapper")) {
            errors.push(
                `Mapper '${mapper.name}' deve extender EntityMapper<Entidade, DTO>.`
            );
        }

        //
        // REGRA 3 — Métodos obrigatórios
        //
        const methodNames = mapper.methods.map(m => m.name);

        requiredMethods.forEach(req => {
            if (!methodNames.includes(req)) {
                errors.push(`Mapper '${mapper.name}' está faltando método obrigatório: ${req}()`);
            }
        });

        //
        // REGRA 4 — Campos ignorados nos mapeamentos de entrada
        //
        const systemFields = ["id", "createdAt", "updatedAt", "active"];
        const missingIgnores = systemFields.filter(field => {
            const pattern = new RegExp(`@Mapping\\(target = "${field}", ignore = true\\)`);
            return !pattern.test(mapper.rawSource);
        });

        if (missingIgnores.length > 0) {
            errors.push(
                `Mapper '${mapper.name}' deve ignorar campos do sistema: ${missingIgnores.join(", ")}`
            );
        }

        //
        // REGRA 5 — Verificação de embeddables
        //
        if (/Embedded/.test(mapper.rawSource) && !/uses\s*=/.test(mapper.rawSource)) {
            errors.push(
                `Mapper '${mapper.name}' contém Embeddables, mas não possui 'uses = {...}' configurado.`
            );
        }

        //
        // REGRA 6 — Não deve conter lógica de negócio
        //
        if (/{\s*return\s+/.test(mapper.rawSource)) {
            errors.push(`Mapper '${mapper.name}' contém lógica manual. Não permitido.`);
        }

        return errors;
    }

    /**
     * Executa auditoria completa de todos os mappers.
     */
    async auditAll(): Promise<string> {
        const mappersDir = path.join(this.projectRoot, "src/main/java/com/upsaude/mapper");
        if (!fs.existsSync(mappersDir)) {
            return "❌ Diretório de mappers não encontrado";
        }

        const files = fs.readdirSync(mappersDir).filter(f => f.endsWith("Mapper.java"));
        let report = `# 📘 Auditoria Completa de Mappers\n\n`;
        report += `**Total de mappers:** ${files.length}\n\n`;

        for (const file of files) {
            const mapperPath = path.join(mappersDir, file);
            try {
                const analysis = this.analyzeFile(mapperPath);
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
