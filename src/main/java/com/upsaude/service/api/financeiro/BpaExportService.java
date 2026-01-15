package com.upsaude.service.api.financeiro;

import org.springframework.core.io.Resource;

import java.util.UUID;

public interface BpaExportService {

    Resource exportarBpa(UUID competenciaId, UUID tenantId);

    boolean bpaFoiGerado(UUID competenciaId, UUID tenantId);
}
