package com.upsaude.service.job;

import com.upsaude.enums.ImportJobTipoEnum;

public interface ImportJobWorker {
    ImportJobTipoEnum getTipo();
    void processar(java.util.UUID jobId);
}


