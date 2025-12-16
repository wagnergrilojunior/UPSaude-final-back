import path from "path";
import { loadMergedRules, type MergedRules } from "../rules/mergedRules.js";
import { ensureDirectoryExists } from "../core/utils.js";

export class ContextProvider {
    private projectRoot: string;
    private cache: Map<string, any>;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.cache = new Map();
    }

    async getRules(): Promise<MergedRules> {
        if (!this.cache.has("rules")) {
            const rules = await loadMergedRules(this.projectRoot);
            this.cache.set("rules", rules);
        }
        return this.cache.get("rules");
    }

    getProjectRoot(): string {
        return this.projectRoot;
    }

    resolvePath(...parts: string[]): string {
        return path.join(this.projectRoot, ...parts);
    }

    ensurePath(...parts: string[]): string {
        const full = this.resolvePath(...parts);
        ensureDirectoryExists(full);
        return full;
    }

    clearCache(): void {
        this.cache.clear();
    }
}
