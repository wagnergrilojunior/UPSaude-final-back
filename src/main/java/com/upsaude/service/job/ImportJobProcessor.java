package com.upsaude.service.job;

import java.util.UUID;

/**
 * Interface para processar um job de importação em background.
 */
public interface ImportJobProcessor {
    void processar(UUID jobId);
}


