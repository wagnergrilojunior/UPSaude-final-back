// src/rules/rulesLoader.ts

import fs from "fs";
import path from "path";
import YAML from "yaml";
import { LoadedRules } from "../core/types.js";

/**
 * Carrega todos os arquivos YAML da pasta .cursor/rules e
 * devolve como um único objeto.
 */
export function loadRules(projectRoot: string): LoadedRules {
    const rulesDir = path.join(projectRoot, ".cursor", "rules");

    const merged: LoadedRules = {};

    if (!fs.existsSync(rulesDir)) {
        return merged;
    }

    const files = fs.readdirSync(rulesDir).filter(f => f.endsWith(".yaml") || f.endsWith(".yml"));

    for (const file of files) {
        const full = path.join(rulesDir, file);
        const content = fs.readFileSync(full, "utf8");

        try {
            const parsed = YAML.parse(content);
            const baseName = file.replace(/\.(yaml|yml)$/i, "");

            merged[baseName] = parsed;
        } catch (err) {
            console.error("Erro ao carregar YAML:", file, err);
        }
    }

    return merged;
}
