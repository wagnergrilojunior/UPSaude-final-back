package com.upsaude.service.impl.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.entity.cnes.CnesSincronizacao;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import com.upsaude.exception.CnesSincronizacaoException;
import com.upsaude.integration.cnes.soap.client.VinculacaoCnesSoapClient;
import com.upsaude.service.api.cnes.CnesSincronizacaoService;
import com.upsaude.service.api.cnes.CnesVinculacaoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.util.CnesValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CnesVinculacaoServiceImpl implements CnesVinculacaoService {

    private final VinculacaoCnesSoapClient soapClient;
    private final CnesSincronizacaoService sincronizacaoService;
    private final TenantService tenantService;

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarVinculacoesPorProfissional(String cpfOuCns) {
        log.info("Iniciando sincronização de vinculações por profissional: {}", cpfOuCns);
        
        UUID tenantId = tenantService.validarTenantAtual();
        
        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.VINCULACAO,
                null,
                cpfOuCns,
                null,
                null
        );
        
        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());
            
            // TODO: Implementar quando classes WSDL forem geradas
            sincronizacaoService.finalizarComSucesso(registro.getId(), 0, 0);
            
            return sincronizacaoService.obterPorId(registro.getId());
            
        } catch (Exception e) {
            log.error("Erro ao sincronizar vinculações: {}", cpfOuCns, e);
            sincronizacaoService.finalizarComErro(registro.getId(), 
                    "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar vinculações: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarVinculacoesPorEstabelecimento(String codigoCnes) {
        log.info("Iniciando sincronização de vinculações por estabelecimento: {}", codigoCnes);
        
        CnesValidator.validarCnes(codigoCnes);
        
        UUID tenantId = tenantService.validarTenantAtual();
        
        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.VINCULACAO,
                null,
                codigoCnes,
                null,
                null
        );
        
        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());
            
            // TODO: Implementar quando classes WSDL forem geradas
            sincronizacaoService.finalizarComSucesso(registro.getId(), 0, 0);
            
            return sincronizacaoService.obterPorId(registro.getId());
            
        } catch (Exception e) {
            log.error("Erro ao sincronizar vinculações: {}", codigoCnes, e);
            sincronizacaoService.finalizarComErro(registro.getId(), 
                    "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar vinculações: " + e.getMessage(), e);
        }
    }
}

