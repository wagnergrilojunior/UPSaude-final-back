package com.upsaude.service.impl.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.entity.cnes.CnesSincronizacao;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import com.upsaude.exception.CnesSoapException;
import com.upsaude.exception.CnesSincronizacaoException;
import com.upsaude.integration.cnes.soap.client.ProfissionalCnesSoapClient;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.service.api.cnes.CnesProfissionalService;
import com.upsaude.service.api.cnes.CnesSincronizacaoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.util.CnesValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CnesProfissionalServiceImpl implements CnesProfissionalService {

    private final ProfissionalCnesSoapClient soapClient;
    private final CnesSincronizacaoService sincronizacaoService;
    private final ProfissionaisSaudeRepository profissionaisRepository;
    private final TenantService tenantService;

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarProfissionalPorCns(String numeroCns) {
        log.info("Iniciando sincronização de profissional por CNS: {}", numeroCns);
        
        CnesValidator.validarCns(numeroCns);
        
        UUID tenantId = tenantService.validarTenantAtual();
        
        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.PROFISSIONAL,
                null,
                numeroCns,
                null,
                null
        );
        
        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());
            
            // TODO: Chamar SOAP Client quando classes WSDL forem geradas
            // Object resposta = soapClient.consultarProfissionalPorCns(numeroCns);
            
            // Buscar ou criar profissional local
            // TODO: Mapear dados do CNES para entidade local quando classes WSDL forem geradas
            
            sincronizacaoService.finalizarComSucesso(registro.getId(), 0, 0);
            
            return sincronizacaoService.obterPorId(registro.getId());
            
        } catch (CnesSoapException e) {
            log.error("Erro SOAP ao sincronizar profissional CNS: {}", numeroCns, e);
            sincronizacaoService.finalizarComErro(registro.getId(), 
                    "Erro SOAP: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar profissional: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Erro inesperado ao sincronizar profissional CNS: {}", numeroCns, e);
            sincronizacaoService.finalizarComErro(registro.getId(), 
                    "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar profissional: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarProfissionalPorCpf(String numeroCpf) {
        log.info("Iniciando sincronização de profissional por CPF: {}", numeroCpf);
        
        UUID tenantId = tenantService.validarTenantAtual();
        
        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.PROFISSIONAL,
                null,
                numeroCpf,
                null,
                null
        );
        
        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());
            
            // TODO: Implementar quando classes WSDL forem geradas
            sincronizacaoService.finalizarComSucesso(registro.getId(), 0, 0);
            
            return sincronizacaoService.obterPorId(registro.getId());
            
        } catch (Exception e) {
            log.error("Erro ao sincronizar profissional CPF: {}", numeroCpf, e);
            sincronizacaoService.finalizarComErro(registro.getId(), 
                    "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar profissional: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Object buscarProfissionalNoCnes(String cnsOuCpf) {
        log.debug("Buscando profissional no CNES: {}", cnsOuCpf);
        
        // TODO: Implementar quando classes WSDL forem geradas
        throw new UnsupportedOperationException("Busca no CNES será implementada após geração de classes WSDL");
    }
}

