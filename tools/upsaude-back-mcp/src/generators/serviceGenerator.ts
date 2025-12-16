import fs from "fs/promises";
import path from "path";
import { logger } from "../core/logger.js";

export class ServiceGenerator {
    private projectRoot: string;

    constructor(projectRoot?: string) {
        // Se não fornecido, tenta inferir do contexto ou usa caminho relativo
        this.projectRoot = projectRoot || process.cwd();
    }

    /**
     * Gera automaticamente a interface Service seguindo os padrões UPSaude.
     *
     * @param entityName Nome da entidade (ex: Alergia)
     * @returns Mensagem de status
     */
    async generate(entityName: string): Promise<string> {
        const filePath = this.getFilePath(entityName);
        const content = this.buildService(entityName);

        await fs.mkdir(path.dirname(filePath), { recursive: true });
        await fs.writeFile(filePath, content);

        logger.success(`Service gerado: ${filePath}`);
        return `✔ Service criado para ${entityName}`;
    }

    // ======================================================
    //      CONSTRUÇÃO DO SERVICE
    // ======================================================
    private buildService(entityName: string): string {
        const className = `${entityName}Service`;
        const requestClass = `${entityName}Request`;
        const responseClass = `${entityName}Response`;

        return `package com.upsaude.service;

import com.upsaude.api.request.${requestClass};
import com.upsaude.api.response.${responseClass};
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service gerado automaticamente pelo MCP UPSaude.
 * Implementações devem seguir as regras de negócio e padrões definidos no projeto.
 */
public interface ${className} {

    ${responseClass} criar(${requestClass} request);

    ${responseClass} obterPorId(UUID id);

    Page<${responseClass}> listar(Pageable pageable);

    ${responseClass} atualizar(UUID id, ${requestClass} request);

    void excluir(UUID id);
}
`;
    }

    // ======================================================
    // Helpers
    // ======================================================
    private getFilePath(entityName: string): string {
        return path.join(
            this.projectRoot,
            `src/main/java/com/upsaude/service/${entityName}Service.java`
        );
    }
}
