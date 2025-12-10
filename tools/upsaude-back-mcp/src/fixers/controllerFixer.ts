import fs from "fs";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

export class ControllerFixer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }


    async fix(entityName: string): Promise<string> {
        return this.fixController(entityName);
    }

    async fixController(entityName: string): Promise<string> {
        logger.info(`🔧 Corrigindo Controller da entidade ${entityName}`);

        const controllerPath = this.findController(entityName);
        if (!controllerPath) {
            return `❌ Controller ${entityName}Controller não encontrado.`;
        }

        const content = fs.readFileSync(controllerPath, "utf-8");
        let updated = content;
        const report: string[] = [];

        const controllerName = `${entityName}Controller`;
        const serviceName = `${entityName}Service`;
        const request = `${entityName}Request`;
        const response = `${entityName}Response`;

        const basePath = `/api/v1/${entityName.toLowerCase()}`;

        // ----------------------------------------------------------------------------
        // IMPORTS OBRIGATÓRIOS
        // ----------------------------------------------------------------------------
        const mandatoryImports = [
            `import org.springframework.web.bind.annotation.*;`,
            `import org.springframework.http.ResponseEntity;`,
            `import lombok.RequiredArgsConstructor;`,
            `import org.springframework.data.domain.Pageable;`,
            `import com.upsaude.api.request.${request};`,
            `import com.upsaude.api.response.${response};`,
            `import com.upsaude.service.${serviceName};`,
            `import java.util.UUID;`,
        ];

        mandatoryImports.forEach(imp => {
            if (!updated.includes(imp)) {
                updated = imp + "\n" + updated;
                report.push(`✔ Import adicionado: ${imp}`);
            }
        });

        // ----------------------------------------------------------------------------
        // ANOTAÇÕES DO CONTROLLER
        // ----------------------------------------------------------------------------
        const requiredAnnotations = [
            "@RestController",
            "@RequiredArgsConstructor",
            `@RequestMapping("${basePath}")`,
        ];

        requiredAnnotations.forEach(a => {
            if (!updated.includes(a)) {
                updated = a + "\n" + updated;
                report.push(`✔ Anotação adicionada: ${a}`);
            }
        });

        // ----------------------------------------------------------------------------
        // INJEÇÃO DO SERVICE
        // ----------------------------------------------------------------------------
        const serviceField = `private final ${serviceName} ${this.lower(serviceName)};`;

        if (!updated.includes(serviceField)) {
            updated = updated.replace(/class [^{]+{/, match => match + `\n    ${serviceField}\n`);
            report.push(`✔ Injeção do service adicionada: ${serviceField}`);
        }

        // ----------------------------------------------------------------------------
        // MÉTODOS CRUD OBRIGATÓRIOS
        // ----------------------------------------------------------------------------

        const methods = {
            criar: `
    @PostMapping
    public ResponseEntity<${response}> criar(@RequestBody ${request} request) {
        return ResponseEntity.ok(${this.lower(serviceName)}.criar(request));
    }
`,
            obterPorId: `
    @GetMapping("/{id}")
    public ResponseEntity<${response}> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(${this.lower(serviceName)}.obterPorId(id));
    }
`,
            listar: `
    @GetMapping
    public ResponseEntity<?> listar(Pageable pageable) {
        return ResponseEntity.ok(${this.lower(serviceName)}.listar(pageable));
    }
`,
            atualizar: `
    @PutMapping("/{id}")
    public ResponseEntity<${response}> atualizar(
            @PathVariable UUID id,
            @RequestBody ${request} request) {
        return ResponseEntity.ok(${this.lower(serviceName)}.atualizar(id, request));
    }
`,
            excluir: `
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        ${this.lower(serviceName)}.excluir(id);
        return ResponseEntity.noContent().build();
    }
`,
        };

        Object.entries(methods).forEach(([methodName, block]) => {
            if (!updated.includes(` ${methodName}(`)) {
                updated = updated.replace(/\}\s*$/, `\n${block}\n}\n`);
                report.push(`✔ Método ${methodName}() adicionado`);
            }
        });

        // ----------------------------------------------------------------------------
        // SALVAR ALTERAÇÕES
        // ----------------------------------------------------------------------------
        if (updated !== content) {
            fs.writeFileSync(controllerPath, updated, "utf-8");
            return `### ${controllerName} corrigido com sucesso\n` + report.join("\n");
        }

        return `### ${controllerName} já está correto\n`;
    }

    // ----------------------------------------------------------------------------
    // LOCALIZA ARQUIVO
    // ----------------------------------------------------------------------------
    private findController(entity: string): string | null {
        const folder = path.join(this.projectRoot, "src/main/java/com/upsaude/controller");
        const fileName = `${entity}Controller.java`;
        const full = path.join(folder, fileName);

        return fs.existsSync(full) ? full : null;
    }

    private lower(name: string): string {
        return name.charAt(0).toLowerCase() + name.slice(1);
    }
}
