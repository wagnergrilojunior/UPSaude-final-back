import { loadRules } from "./rulesLoader.js";
import type { LoadedRules } from "../core/types.js";

/**
 * Estrutura consolidada de todas as regras carregadas.
 */
export interface MergedRules {
    master: any;
    architecture: any;
    coding: any;
    validation: any;
    domain: any;
    cursor: any;
    patterns: any;
    mapperPatterns: any;
    servicePatterns: any;
    controllerPatterns: any;
    project_structure?: any;
    entities?: Record<string, any>;

    all: Record<string, any>;
}

/** Merge profundo sem lodash */
function deepMerge(target: any, source: any): any {
    if (!source) return target;
    if (!target) return source;

    const result = { ...target };

    for (const key of Object.keys(source)) {
        const value = source[key];

        if (value && typeof value === "object" && !Array.isArray(value)) {
            result[key] = deepMerge(result[key], value);
        } else {
            result[key] = value;
        }
    }

    return result;
}

/**
 * Mescla e valida todas as regras carregadas a partir do YAML.
 */
export function mergeAllRules(raw: LoadedRules): MergedRules {
    const merged: MergedRules = {
        master: raw.master || {},
        architecture: raw.architecture || {},
        coding: raw.coding || {},
        validation: raw.validation || {},
        domain: raw.domain || {},
        cursor: raw.cursor || {},
        patterns: raw.patterns || {},
        mapperPatterns: raw.mapperPatterns || {},
        servicePatterns: raw.servicePatterns || {},
        controllerPatterns: raw.controllerPatterns || {},

        all: {}
    };

    merged.all = deepMerge(
        {},
        deepMerge(
            merged.master,
            deepMerge(
                merged.architecture,
                deepMerge(
                    merged.coding,
                    deepMerge(
                        merged.validation,
                        deepMerge(
                            merged.domain,
                            deepMerge(
                                merged.cursor,
                                deepMerge(
                                    merged.patterns,
                                    deepMerge(
                                        merged.mapperPatterns,
                                        deepMerge(
                                            merged.servicePatterns,
                                            merged.controllerPatterns
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    );

    return merged;
}

/**
 * Função esperada pelos tools do MCP.
 * Carrega → mescla → retorna regras unificadas
 */
export async function loadMergedRules(projectRoot: string): Promise<MergedRules> {
    const raw: LoadedRules = loadRules(projectRoot);
    return mergeAllRules(raw);
}

/**
 * Helper para obter regras mescladas sem precisar passar argumentos.
 * Carrega automaticamente do projeto atual (requer projectRoot no contexto).
 * ATENÇÃO: Esta função requer que o código seja executado no contexto correto.
 */
export function getMergedRulesSync(projectRoot?: string): MergedRules {
    if (!projectRoot) {
        // Tenta inferir do contexto ou retorna regras vazias
        throw new Error("projectRoot é obrigatório para getMergedRulesSync");
    }
    const raw: LoadedRules = loadRules(projectRoot);
    return mergeAllRules(raw);
}
