import { promises as fs } from "fs";
import * as path from "path";

const PROJECT_ROOT = process.env.UPSAUDE_PROJECT_ROOT || process.cwd();
const PATTERN_FILE = path.join(PROJECT_ROOT, "tools/upsaude-back-mcp/patterns/patterns.json");

/**
 * Carrega os padrões de geração do projeto.
 * Caso o arquivo não exista, cria um padrão básico automaticamente.
 */
export async function loadProjectPatterns(): Promise<any> {
  try {
    const content = await fs.readFile(PATTERN_FILE, "utf-8");
    return JSON.parse(content);
  } catch (err) {
    // Criar default
    const defaults = {
      request: "public class {{EntityName}}Request { }",
      response: "public class {{EntityName}}Response { }",
      dto: "public class {{EntityName}}DTO { }",
      mapper:
        "public interface {{EntityName}}Mapper { {{EntityName}} toEntity({{EntityName}}DTO dto); {{EntityName}}DTO toDTO({{EntityName}} entity); }",
      repository: "public interface {{EntityName}}Repository extends JpaRepository<{{EntityName}}, Long> { }",
      service: "public class {{EntityName}}Service { }",
      controller:
        "@RestController\n@RequestMapping(\"/api/{{entityNamePluralLowercase}}\")\npublic class {{EntityName}}Controller { }"
    };

    await fs.mkdir(path.dirname(PATTERN_FILE), { recursive: true });
    await fs.writeFile(PATTERN_FILE, JSON.stringify(defaults, null, 2));

    return defaults;
  }
}
