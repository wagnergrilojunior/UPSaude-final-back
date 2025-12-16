/**
 * Logger centralizado para o MCP UPSaude.
 * 
 * Suporta:
 *  - níveis de log: debug, info, warn, error
 *  - cores ANSI
 *  - controle por variável de ambiente
 *  - formatação automática com timestamp
 *  - categorias por módulo (opcional)
 */

const ENABLE_DEBUG = process.env.MCP_DEBUG === "true";
const ENABLE_LOGS = process.env.MCP_SILENT !== "true";

function timestamp(): string {
    return new Date().toISOString();
}

function format(category: string | undefined, level: string, message: string) {
    const cat = category ? `[${category}]` : "";
    return `${timestamp()} ${cat} ${level} ${message}`;
}

// ANSI colors
const COLORS = {
    reset: "\x1b[0m",
    debug: "\x1b[36m",
    info: "\x1b[32m",
    warn: "\x1b[33m",
    error: "\x1b[31m",
};

export class Logger {
    private category?: string;

    constructor(category?: string) {
        this.category = category;
    }

    /** Permite criar instâncias com categoria: logger.withCategory("Analyzer") */
    withCategory(category: string) {
        return new Logger(category);
    }

    debug(...msg: any[]) {
        if (!ENABLE_DEBUG || !ENABLE_LOGS) return;

        console.log(
            COLORS.debug +
                format(this.category, "[DEBUG]", msg.join(" ")) +
                COLORS.reset
        );
    }

    info(...msg: any[]) {
        if (!ENABLE_LOGS) return;

        console.log(
            COLORS.info + format(this.category, "[INFO]", msg.join(" ")) + COLORS.reset
        );
    }

    warn(...msg: any[]) {
        if (!ENABLE_LOGS) return;

        console.warn(
            COLORS.warn + format(this.category, "[WARN]", msg.join(" ")) + COLORS.reset
        );
    }

    error(...msg: any[]) {
        if (!ENABLE_LOGS) return;

        console.error(
            COLORS.error + format(this.category, "[ERROR]", msg.join(" ")) + COLORS.reset
        );
    }

    success(...msg: any[]) {
        if (!ENABLE_LOGS) return;

        console.log(
            COLORS.info + format(this.category, "[SUCCESS]", msg.join(" ")) + COLORS.reset
        );
    }
}

// Instância global padrão
export const logger = new Logger();

// Função helper para criar logger (compatibilidade com código antigo)
export function createLogger(category?: string): Logger {
    return new Logger(category);
}
