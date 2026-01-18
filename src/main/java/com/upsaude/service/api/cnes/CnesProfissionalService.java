package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;


public interface CnesProfissionalService {

    
    CnesSincronizacaoResponse sincronizarProfissionalPorCns(String numeroCns, boolean persistir);

    
    CnesSincronizacaoResponse sincronizarProfissionalPorCpf(String numeroCpf, boolean persistir);

    
    Object buscarProfissionalNoCnes(String cnsOuCpf);

    
    Object buscarProfissionalPorCpf(String cpf);

    
    Object buscarProfissionalPorCns(String cns);

    
    Object listarProfissionais(int page, int size);
}
