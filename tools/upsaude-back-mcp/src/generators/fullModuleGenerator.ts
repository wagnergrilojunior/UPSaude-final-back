import { RequestGenerator } from "./requestGenerator.js";
import { ResponseGenerator } from "./responseGenerator.js";
import { DTOGenerator } from "./dtoGenerator.js";
import { MapperGenerator } from "./mapperGenerator.js";
import { RepositoryGenerator } from "./repositoryGenerator.js";
import { ServiceGenerator } from "./serviceGenerator.js";
import { ControllerGenerator } from "./controllerGenerator.js";

import { logger } from "../core/logger.js";

export class FullModuleGenerator {
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
    }

    /**
     * Gera automaticamente:
     *  - Request
     *  - Response
     *  - DTO
     *  - Mapper
     *  - Repository
     *  - Service
     *  - Controller
     */
    async generate(entityName: string): Promise<string> {
        logger.info(`🔧 Iniciando geração completa do módulo: ${entityName}`);

        const requestGen = new RequestGenerator(this.projectRoot);
        const responseGen = new ResponseGenerator(this.projectRoot);
        const dtoGen = new DTOGenerator(this.projectRoot);
        const mapperGen = new MapperGenerator(this.projectRoot);
        const repoGen = new RepositoryGenerator(this.projectRoot);
        const serviceGen = new ServiceGenerator(this.projectRoot);
        const controllerGen = new ControllerGenerator(this.projectRoot);

        const results: string[] = [];

        // Request e Response precisam de campos vazios por enquanto
        results.push(await requestGen.generate(entityName, {}));
        results.push(await responseGen.generate(entityName, {}));
        results.push(await dtoGen.generate(entityName));
        results.push(await mapperGen.generate(entityName));
        results.push(await repoGen.generate(entityName));
        results.push(await serviceGen.generate(entityName));
        results.push(await controllerGen.generate(entityName));

        logger.success(`🎉 Módulo completo gerado com sucesso para ${entityName}`);

        return results.join("\n");
    }
}
