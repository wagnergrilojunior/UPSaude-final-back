package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;

import java.util.List;
import java.util.UUID;


public interface CnesEstabelecimentoService {

    
    CnesSincronizacaoResponse sincronizarEstabelecimentoPorCnes(String codigoCnes, String competencia,
            boolean persistir);

    
    List<CnesSincronizacaoResponse> sincronizarEstabelecimentosPorMunicipio(String codigoMunicipio, String competencia,
            boolean persistir);

    
    CnesSincronizacaoResponse atualizarDadosComplementares(String codigoMunicipio, String competencia,
            boolean persistir);

    
    EstabelecimentosResponse buscarEstabelecimentoNoCnes(String codigoCnes);
}
