package com.upsaude.service.impl.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.entity.cnes.CnesSincronizacao;
import com.upsaude.entity.cnes.Leitos;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.enums.StatusLeitoEnum;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import com.upsaude.exception.CnesSincronizacaoException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.integration.cnes.soap.client.LeitoCnesSoapClient;
import com.upsaude.repository.cnes.LeitosRepository;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.service.api.cnes.CnesLeitoService;
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
public class CnesLeitoServiceImpl implements CnesLeitoService {

    private final LeitoCnesSoapClient soapClient;
    private final CnesSincronizacaoService sincronizacaoService;
    private final LeitosRepository leitosRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final TenantService tenantService;

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarLeitosPorEstabelecimento(String codigoCnes) {
        log.info("Iniciando sincronização de leitos por estabelecimento: {}", codigoCnes);
        
        CnesValidator.validarCnes(codigoCnes);
        
        UUID tenantId = tenantService.validarTenantAtual();
        var tenant = tenantService.obterTenantDoUsuarioAutenticado();
        
        // Buscar estabelecimento local
        Estabelecimentos estabelecimento = estabelecimentosRepository.findByCodigoCnesAndTenant(codigoCnes, tenant)
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com CNES: " + codigoCnes));
        
        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.LEITO,
                null,
                codigoCnes,
                null,
                estabelecimento.getId()
        );
        
        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());
            
            // TODO: Chamar SOAP Client quando classes WSDL forem geradas
            // Object resposta = soapClient.consultarLeitos(codigoCnes);
            
            // TODO: Processar leitos retornados e criar/atualizar registros na tabela leitos
            // TODO: Atualizar quantidade_leitos em estabelecimentos
            
            sincronizacaoService.finalizarComSucesso(registro.getId(), 0, 0);
            
            return sincronizacaoService.obterPorId(registro.getId());
            
        } catch (Exception e) {
            log.error("Erro ao sincronizar leitos: {}", codigoCnes, e);
            sincronizacaoService.finalizarComErro(registro.getId(), 
                    "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar leitos: " + e.getMessage(), e);
        }
    }
}

