package com.upsaude.service.impl.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.entity.cnes.CnesSincronizacao;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import com.upsaude.exception.CnesSincronizacaoException;
import com.upsaude.integration.cnes.soap.client.EquipeCnesSoapClient;
import com.upsaude.service.api.cnes.CnesEquipeService;
import com.upsaude.service.api.cnes.CnesSincronizacaoService;
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
public class CnesEquipeServiceImpl implements CnesEquipeService {

    private final EquipeCnesSoapClient soapClient;
    private final CnesSincronizacaoService sincronizacaoService;
    private final TenantService tenantService;

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarEquipesPorEstabelecimento(String codigoCnes) {
        log.info("Iniciando sincronização de equipes por estabelecimento: {}", codigoCnes);
        
        CnesValidator.validarCnes(codigoCnes);
        
        UUID tenantId = tenantService.validarTenantAtual();
        
        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.EQUIPE,
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
            log.error("Erro ao sincronizar equipes: {}", codigoCnes, e);
            sincronizacaoService.finalizarComErro(registro.getId(), 
                    "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar equipes: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarEquipe(String codigoCnes, String ine) {
        log.info("Iniciando sincronização de equipe específica. CNES: {}, INE: {}", codigoCnes, ine);
        
        CnesValidator.validarCnes(codigoCnes);
        CnesValidator.validarIne(ine);
        
        UUID tenantId = tenantService.validarTenantAtual();
        
        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.EQUIPE,
                null,
                ine,
                null,
                null
        );
        
        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());
            
            // TODO: Implementar quando classes WSDL forem geradas
            sincronizacaoService.finalizarComSucesso(registro.getId(), 0, 0);
            
            return sincronizacaoService.obterPorId(registro.getId());
            
        } catch (Exception e) {
            log.error("Erro ao sincronizar equipe: {}", ine, e);
            sincronizacaoService.finalizarComErro(registro.getId(), 
                    "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar equipe: " + e.getMessage(), e);
        }
    }
}

