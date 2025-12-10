import fs from "fs";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

export class ServiceFixer {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }


    async fix(entityName: string): Promise<string> {
        return this.fixService(entityName);
    }

    async fixService(entityName: string): Promise<string> {
        logger.info(`🔧 Corrigindo Services para entidade ${entityName}`);

        const serviceInterface = this.findServiceInterface(entityName);
        const serviceImpl = this.findServiceImpl(entityName);

        const report: string[] = [];

        if (!serviceInterface) {
            report.push(`❌ Interface ${entityName}Service não encontrada.`);
        } else {
            report.push(await this.fixServiceInterface(serviceInterface, entityName));
        }

        if (!serviceImpl) {
            report.push(`❌ Classe ${entityName}ServiceImpl não encontrada.`);
        } else {
            report.push(await this.fixServiceImpl(serviceImpl, entityName));
        }

        return report.join("\n\n");
    }

    // -------------------------------------------------------------------------
    // FIX: INTERFACE SERVICE
    // -------------------------------------------------------------------------

    private async fixServiceInterface(filePath: string, entity: string): Promise<string> {
        let content = fs.readFileSync(filePath, "utf-8");
        const original = content;
        const report: string[] = [];

        const request = `${entity}Request`;
        const response = `${entity}Response`;

        //
        // 1 — Imports obrigatórios
        //
        const imports = [
            `import com.upsaude.api.request.${request};`,
            `import com.upsaude.api.response.${response};`,
            `import org.springframework.data.domain.Page;`,
            `import org.springframework.data.domain.Pageable;`,
            `import java.util.UUID;`
        ];

        imports.forEach(imp => {
            if (!content.includes(imp)) {
                content = imp + "\n" + content;
                report.push(`✔ Import adicionado: ${imp}`);
            }
        });

        //
        // 2 — Métodos obrigatórios
        //
        const requiredMethods = [
            `    ${response} criar(${request} request);`,
            `    ${response} obterPorId(UUID id);`,
            `    Page<${response}> listar(Pageable pageable);`,
            `    ${response} atualizar(UUID id, ${request} request);`,
            `    void excluir(UUID id);`
        ];

        requiredMethods.forEach(m => {
            const name = m.trim().split(" ")[1].split("(")[0];
            if (!content.includes(name + "(")) {
                content = content.replace(/\}\s*$/, `\n${m}\n}\n`);
                report.push(`✔ Método obrigatório adicionado: ${name}()`);
            }
        });

        //
        // 3 — Salvar arquivo se alterado
        //
        if (content !== original) {
            fs.writeFileSync(filePath, content, "utf-8");
            report.unshift(`### Interface ${entity}Service corrigida\n`);
        } else {
            report.unshift(`### Interface ${entity}Service já está correta`);
        }

        return report.join("\n");
    }

    // -------------------------------------------------------------------------
    // FIX: SERVICE IMPLEMENTATION
    // -------------------------------------------------------------------------

    private async fixServiceImpl(filePath: string, entity: string): Promise<string> {
        let content = fs.readFileSync(filePath, "utf-8");
        const original = content;
        const report: string[] = [];

        const repository = `${entity}Repository`;
        const mapper = `${entity}Mapper`;
        const request = `${entity}Request`;
        const response = `${entity}Response`;
        const className = `${entity}ServiceImpl`;

        //
        // 1 — Imports obrigatórios
        //
        const mandatoryImports = [
            `import org.springframework.stereotype.Service;`,
            `import org.springframework.transaction.annotation.Transactional;`,
            `import lombok.RequiredArgsConstructor;`,
            `import lombok.extern.slf4j.Slf4j;`,
            `import java.util.UUID;`,
            `import com.upsaude.exception.BadRequestException;`,
            `import com.upsaude.exception.NotFoundException;`,
            `import com.upsaude.api.request.${request};`,
            `import com.upsaude.api.response.${response};`,
            `import com.upsaude.repository.${repository};`,
            `import com.upsaude.mapper.${mapper};`,
            `import com.upsaude.entity.${entity};`
        ];

        mandatoryImports.forEach(imp => {
            if (!content.includes(imp)) {
                content = imp + "\n" + content;
                report.push(`✔ Import adicionado: ${imp}`);
            }
        });

        //
        // 2 — Garante @Service, @RequiredArgsConstructor e @Slf4j
        //
        const annotations = ["@Service", "@RequiredArgsConstructor", "@Slf4j"];

        annotations.forEach(a => {
            if (!content.includes(a)) {
                content = a + "\n" + content;
                report.push(`✔ Anotação adicionada: ${a}`);
            }
        });

        //
        // 3 — Garante atributos finais: repository e mapper
        //
        const requiredFields = [
            `private final ${repository} ${this.lower(repository)};`,
            `private final ${mapper} ${this.lower(mapper)};`
        ];

        requiredFields.forEach(f => {
            if (!content.includes(f)) {
                content = content.replace(/class [^{]+{/, match => match + `\n    ${f}\n`);
                report.push(`✔ Campo obrigatório adicionado: ${f}`);
            }
        });

        //
        // 4 — Garante métodos CRUD completos
        //
        const crudTemplates = {
            criar: `
    @Override
    @Transactional
    public ${response} criar(${request} request) {
        log.debug("Criando ${entity} — payload: {}", request);
        if (request == null) throw new BadRequestException("Dados obrigatórios não informados");

        validarDuplicidade(null, request);

        ${entity} novo = ${this.lower(mapper)}.fromRequest(request);
        novo.setActive(true);

        ${entity} salvo = ${this.lower(repository)}.save(novo);
        return ${this.lower(mapper)}.toResponse(salvo);
    }
`,
            obterPorId: `
    @Override
    @Transactional(readOnly = true)
    public ${response} obterPorId(UUID id) {
        if (id == null) throw new BadRequestException("ID é obrigatório");

        ${entity} encontrado = ${this.lower(repository)}.findById(id)
                .orElseThrow(() -> new NotFoundException("${entity} não encontrado"));

        return ${this.lower(mapper)}.toResponse(encontrado);
    }
`,
            atualizar: `
    @Override
    @Transactional
    public ${response} atualizar(UUID id, ${request} request) {
        if (id == null) throw new BadRequestException("ID é obrigatório");
        if (request == null) throw new BadRequestException("Dados obrigatórios não informados");

        ${entity} existente = ${this.lower(repository)}.findById(id)
                .orElseThrow(() -> new NotFoundException("${entity} não encontrado"));

        validarDuplicidade(id, request);

        ${this.lower(mapper)}.updateFromRequest(request, existente);

        ${entity} atualizado = ${this.lower(repository)}.save(existente);
        return ${this.lower(mapper)}.toResponse(atualizado);
    }
`,
            excluir: `
    @Override
    @Transactional
    public void excluir(UUID id) {
        if (id == null) throw new BadRequestException("ID é obrigatório");

        ${entity} existente = ${this.lower(repository)}.findById(id)
                .orElseThrow(() -> new NotFoundException("${entity} não encontrado"));

        existente.setActive(false);
        ${this.lower(repository)}.save(existente);
    }
`
        };

        Object.entries(crudTemplates).forEach(([name, block]) => {
            if (!content.includes(` ${name}(`)) {
                content = content.replace(/\}\s*$/, `\n${block}\n}\n`);
                report.push(`✔ Método ${name}() inserido conforme padrão`);
            }
        });

        //
        // 5 — Garante método validarDuplicidade
        //
        if (!content.includes("validarDuplicidade(")) {
            const block = `
    private void validarDuplicidade(UUID id, ${request} request) {
        // TODO: regra automática baseada no repository
        // Este método será aprimorado pelo MCP futuramente
    }
`;
            content = content.replace(/\}\s*$/, `\n${block}\n}\n`);
            report.push("✔ Método validarDuplicidade() criado");
        }

        //
        // 6 — Salvar arquivo
        //
        if (content !== original) {
            fs.writeFileSync(filePath, content, "utf-8");
            report.unshift(`### ${className} corrigido com sucesso`);
        } else {
            report.unshift(`### ${className} já estava no padrão`);
        }

        return report.join("\n");
    }

    // -------------------------------------------------------------------------
    // UTILITÁRIOS
    // -------------------------------------------------------------------------

    private findServiceInterface(entity: string): string | null {
        const folder = path.join(this.projectRoot, "src/main/java/com/upsaude/service");
        const file = `${entity}Service.java`;
        const full = path.join(folder, file);

        return fs.existsSync(full) ? full : null;
    }

    private findServiceImpl(entity: string): string | null {
        const folder = path.join(this.projectRoot, "src/main/java/com/upsaude/service/impl");
        const file = `${entity}ServiceImpl.java`;
        const full = path.join(folder, file);

        return fs.existsSync(full) ? full : null;
    }

    private lower(str: string): string {
        return str.charAt(0).toLowerCase() + str.slice(1);
    }
}
