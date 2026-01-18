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

import com.upsaude.integration.cnes.wsdl.equipe.*;
import com.upsaude.mapper.cnes.CnesEquipeMapper;
import com.upsaude.repository.profissional.equipe.EquipeSaudeRepository;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import java.util.List;
import java.time.OffsetDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CnesEquipeServiceImpl implements CnesEquipeService {

    private final EquipeCnesSoapClient soapClient;
    private final CnesSincronizacaoService sincronizacaoService;
    private final TenantService tenantService;
    private final EquipeSaudeRepository equipeRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final CnesEquipeMapper cnesMapper;

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarEquipesPorEstabelecimento(String codigoCnes, boolean persistir) {
        log.info("Iniciando sincronização de equipes por estabelecimento: {}", codigoCnes);

        CnesValidator.validarCnes(codigoCnes);

        com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.EQUIPE,
                null,
                codigoCnes,
                null,
                null);

        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());

            ResponsePesquisarEquipe resposta = soapClient.pesquisarEquipes(codigoCnes, 1, 100);

            int inseridos = 0;
            int atualizados = 0;

            if (resposta != null && resposta.getEquipes() != null && resposta.getEquipes().getEquipe() != null) {
                List<EquipeType> equipesCnes = resposta.getEquipes().getEquipe();

                if (persistir) {
                    Estabelecimentos estab = estabelecimentosRepository.findByCodigoCnesAndTenant(codigoCnes, tenant)
                            .orElse(null);

                    if (estab == null) {
                        log.warn("Estabelecimento CNES {} não encontrado para o tenant. Equipes não serão associadas.",
                                codigoCnes);
                    }

                    for (EquipeType equipeCnes : equipesCnes) {
                        final boolean[] isNovo = { false };
                        EquipeSaude equipe;

                        if (estab != null) {
                            equipe = equipeRepository
                                    .findByIneAndEstabelecimentoIdAndTenantId(equipeCnes.getCodigoEquipe(),
                                            estab.getId(), tenant.getId())
                                    .orElseGet(() -> {
                                        isNovo[0] = true;
                                        EquipeSaude novo = new EquipeSaude();
                                        novo.setTenant(tenant);
                                        novo.setEstabelecimento(estab);
                                        return novo;
                                    });
                        } else {
                            
                            
                            
                            continue;
                        }

                        cnesMapper.mapToEquipe(equipeCnes, equipe);
                        equipeRepository.save(Objects.requireNonNull(equipe));

                        if (isNovo[0])
                            inseridos++;
                        else
                            atualizados++;
                    }
                }
            }

            sincronizacaoService.finalizarComSucesso(registro.getId(), inseridos, atualizados);

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
    public CnesSincronizacaoResponse sincronizarEquipe(String codigoCnes, String ine, boolean persistir) {
        log.info("Iniciando sincronização de equipe específica. CNES: {}, INE: {}", codigoCnes, ine);

        CnesValidator.validarCnes(codigoCnes);
        CnesValidator.validarIne(ine);

        com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.EQUIPE,
                null,
                ine,
                null,
                null);

        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());

            ResponseDetalharEquipe resposta = soapClient.detalharEquipe(ine);

            if (resposta == null) {
                throw new CnesSincronizacaoException("Equipe não encontrada no CNES: " + ine);
            }

            
            
            
            
            
            

            int inseridos = 0;
            int atualizados = 0;

            if (persistir) {
                Estabelecimentos estab = estabelecimentosRepository.findByCodigoCnesAndTenant(codigoCnes, tenant)
                        .orElseThrow(() -> new CnesSincronizacaoException(
                                "Estabelecimento local não encontrado para o CNES: " + codigoCnes));

                final boolean[] isNovo = { false };
                EquipeSaude equipe = equipeRepository
                        .findByIneAndEstabelecimentoIdAndTenantId(ine, estab.getId(), tenant.getId())
                        .orElseGet(() -> {
                            isNovo[0] = true;
                            EquipeSaude novo = new EquipeSaude();
                            novo.setTenant(tenant);
                            novo.setEstabelecimento(estab);
                            novo.setIne(ine);
                            novo.setNomeReferencia("EQUIPE " + ine); 
                            return novo;
                        });

                
                
                

                equipe.setDataUltimaSincronizacaoCnes(OffsetDateTime.now());
                equipeRepository.save(equipe);

                if (isNovo[0])
                    inseridos++;
                else
                    atualizados++;
            }

            sincronizacaoService.finalizarComSucesso(registro.getId(), inseridos, atualizados);

            return sincronizacaoService.obterPorId(registro.getId());

        } catch (Exception e) {
            log.error("Erro ao sincronizar equipe: {}", ine, e);
            sincronizacaoService.finalizarComErro(registro.getId(),
                    "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar equipe: " + e.getMessage(), e);
        }
    }
}
