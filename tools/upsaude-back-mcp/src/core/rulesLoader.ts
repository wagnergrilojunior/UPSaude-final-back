// tools/upsaude-back-mcp/src/core/rulesLoader.ts
import { deepMerge, readYamlFile, resolveProjectPath } from "./fileUtils.js";
import { promises as fs } from "fs";
import * as path from "path";

export type RulesObject = Record<string, any>;

const RULES_DIR = ".cursor/rules";

const DEFAULT_RULE_FILES = [
  "master-rules.yaml",
  "architecture-rules.yaml",
  "domain-rules.yaml",
  "coding-style.yaml",
  "validation-rules.yaml",
  "patterns.yaml",
  "mapper-patterns.yaml",
  "service-patterns.yaml",
  "controller-patterns.yaml",
];

/**
 * Verifica se um arquivo existe relativo à raiz do projeto.
 */
async function fileExists(relativePath: string): Promise<boolean> {
  try {
    const fullPath = resolveProjectPath(relativePath);
    await fs.access(fullPath);
    return true;
  } catch {
    return false;
  }
}

/**
 * Carrega UM arquivo de regra se existir.
 */
async function loadSingleRuleFile(fileName: string): Promise<RulesObject | null> {
  const relativePath = path.join(RULES_DIR, fileName);
  const exists = await fileExists(relativePath);
  if (!exists) {
    return null;
  }

  try {
    const content = await readYamlFile<RulesObject>(relativePath);
    return content || {};
  } catch (err: any) {
    console.error(
      `[MCP][rulesLoader] Erro ao ler arquivo de regra ${relativePath}:`,
      err?.message || err
    );
    return {};
  }
}

/**
 * Carrega TODAS as regras padronizadas do projeto,
 * mesclando os arquivos em uma única estrutura.
 */
export async function loadAllRules(
  extraFiles: string[] = []
): Promise<RulesObject> {
  const result: RulesObject = {};

  const ruleFiles = [...DEFAULT_RULE_FILES, ...extraFiles];

  for (const file of ruleFiles) {
    const rules = await loadSingleRuleFile(file);
    if (rules) {
      deepMerge(result, rules);
    }
  }

  return result;
}

/**
 * Helper para carregar apenas um tipo específico de regra.
 * Ex: loadRulesBySection("entities") → retorna regras de entidades.
 */
export async function loadRulesBySection(
  section: string
): Promise<RulesObject> {
  const all = await loadAllRules();
  return (all && all[section]) || {};
}
