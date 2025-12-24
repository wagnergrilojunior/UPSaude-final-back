package com.upsaude.service.job;

import com.upsaude.enums.ImportJobTipoEnum;

/**
 * Contrato de worker por tipo de importação.
 */
public interface ImportJobWorker {
    ImportJobTipoEnum getTipo();
    void processar(java.util.UUID jobId);
}


