// tools/upsaude-back-mcp/src/core/fileUtils.ts
import { promises as fs } from "fs";
import * as path from "path";
import YAML from "yaml";

const PROJECT_ROOT = process.env.UPSAUDE_PROJECT_ROOT || process.cwd();

/**
 * Resolve um caminho relativo à raiz do projeto UPSaude.
 */
export function resolveProjectPath(relativePath: string): string {
  return path.join(PROJECT_ROOT, relativePath);
}

/**
 * Lê arquivo texto a partir da raiz do projeto.
 */
export async function readTextFile(relativePath: string): Promise<string> {
  const fullPath = resolveProjectPath(relativePath);
  return fs.readFile(fullPath, "utf-8");
}

/**
 * Escreve arquivo texto a partir da raiz do projeto, criando diretórios se necessário.
 */
export async function writeTextFile(relativePath: string, content: string): Promise<void> {
  const fullPath = resolveProjectPath(relativePath);
  const dir = path.dirname(fullPath);
  await fs.mkdir(dir, { recursive: true });
  await fs.writeFile(fullPath, content, "utf-8");
}

/**
 * Lê um arquivo YAML e retorna como objeto JS.
 */
export async function readYamlFile<T = any>(relativePath: string): Promise<T> {
  const raw = await readTextFile(relativePath);
  return YAML.parse(raw) as T;
}

/**
 * Mescla profunda (deep merge) de objetos simples.
 * Regras posteriores sobrescrevem anteriores.
 */
export function deepMerge(target: any, source: any): any {
  if (!source) return target;

  for (const key of Object.keys(source)) {
    const srcValue = source[key];
    const tgtValue = (target as any)[key];

    if (Array.isArray(srcValue)) {
      // Arrays: concatena mantendo ordem (pode ajustar depois se quiser outra estratégia)
      (target as any)[key] = Array.isArray(tgtValue)
        ? [...tgtValue, ...srcValue]
        : [...srcValue];
    } else if (isPlainObject(srcValue)) {
      (target as any)[key] = deepMerge(
        isPlainObject(tgtValue) ? tgtValue : {},
        srcValue
      );
    } else {
      (target as any)[key] = srcValue;
    }
  }

  return target;
}

function isPlainObject(value: any): value is Record<string, any> {
  return (
    value !== null &&
    typeof value === "object" &&
    !Array.isArray(value)
  );
}
