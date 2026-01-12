package com.upsaude.service.api.farmacia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.upsaude.api.response.farmacia.MedicamentoResponse;
import com.upsaude.api.response.farmacia.PrincipioAtivoResponse;
import com.upsaude.api.response.farmacia.UnidadeMedidaResponse;
import com.upsaude.api.response.farmacia.ViaAdministracaoResponse;

public interface MedicamentoCatalogoService {
    Page<PrincipioAtivoResponse> listarPrincipiosAtivos(String termo, Pageable pageable);

    Page<MedicamentoResponse> listarMedicamentos(String termo, Pageable pageable);

    Page<com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoResponse> listarMedicamentosSigtap(String termo,
            Pageable pageable);

    Page<UnidadeMedidaResponse> listarUnidadesMedida(String termo, Pageable pageable);

    Page<ViaAdministracaoResponse> listarViasAdministracao(String termo, Pageable pageable);
}
