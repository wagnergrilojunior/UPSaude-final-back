// src/core/utils.ts

import fs from "fs";
import path from "path";

/** Garante que um diretório existe (equivalente ao mkdir -p). */
export function ensureDirectoryExists(dirPath: string): void {
    if (!fs.existsSync(dirPath)) {
        fs.mkdirSync(dirPath, { recursive: true });
    }
}

/** Lê um arquivo com segurança. */
export function readFileSafe(file: string): string | null {
    if (!fs.existsSync(file)) return null;
    return fs.readFileSync(file, "utf8");
}

/** Salva arquivo criando pastas automaticamente. */
export function writeFileSafe(file: string, content: string): void {
    ensureDirectoryExists(path.dirname(file));
    fs.writeFileSync(file, content, "utf8");
}
