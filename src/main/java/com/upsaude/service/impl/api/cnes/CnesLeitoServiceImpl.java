package com.upsaude.service.impl.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.entity.cnes.CnesSincronizacao;
import com.upsaude.entity.cnes.Leitos;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import com.upsaude.exception.CnesSincronizacaoException;
import com.upsaude.integration.cnes.soap.client.LeitoCnesSoapClient;
import com.upsaude.repository.cnes.LeitosRepository;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.service.api.cnes.CnesLeitoService;
import com.upsaude.service.api.cnes.CnesSincronizacaoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.util.CnesValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.upsaude.integration.cnes.wsdl.leito.*;
import com.upsaude.mapper.cnes.CnesLeitoMapper;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CnesLeitoServiceImpl implements CnesLeitoService {

    private final LeitoCnesSoapClient soapClient;
    private final CnesSincronizacaoService sincronizacaoService;
    private final TenantService tenantService;
    private final LeitosRepository leitosRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final CnesLeitoMapper cnesMapper;

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarLeitosPorEstabelecimento(String codigoCnes, boolean persistir) {
        log.info("Iniciando sincronização de leitos por estabelecimento: {}", codigoCnes);

        CnesValidator.validarCnes(codigoCnes);

        com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.LEITO,
                null,
                codigoCnes,
                null,
                null);

        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());

            ResponseConsultarLeitosCNES resposta = soapClient.consultarLeitos(codigoCnes);

            int inseridos = 0;
            int atualizados = 0;

            if (resposta != null && resposta.getLeito() != null) {
                List<LeitoType> leitosCnes = resposta.getLeito();

                if (persistir) {
                    Estabelecimentos estab = estabelecimentosRepository.findByCodigoCnesAndTenant(codigoCnes, tenant)
                            .orElse(null);

                    for (LeitoType leitoCnes : leitosCnes) {
                        final boolean[] isNovo = { false };
                        Leitos leito = leitosRepository
                                .findByCodigoCnesLeitoAndTenant(leitoCnes.getCodigo(), tenant.getId())
                                .orElseGet(() -> {
                                    isNovo[0] = true;
                                    Leitos novo = new Leitos();
                                    novo.setTenant(tenant);
                                    novo.setEstabelecimento(estab);
                                    return novo;
                                });

                        cnesMapper.mapToLeito(leitoCnes, leito);
                        leitosRepository.save(Objects.requireNonNull(leito));

                        if (isNovo[0])
                            inseridos++;
                        else
                            atualizados++;
                    }

                    if (estab != null) {
                        List<Leitos> leitosLocal = leitosRepository.findByEstabelecimentoIdAndTenant(estab.getId(),
                                tenant.getId());
                        if (estab.getInfraestruturaFisica() == null) {
                            estab.setInfraestruturaFisica(
                                    new com.upsaude.entity.embeddable.InfraestruturaFisicaEstabelecimento());
                        }
                        estab.getInfraestruturaFisica().setQuantidadeLeitos(leitosLocal.size());
                        estabelecimentosRepository.save(Objects.requireNonNull(estab));
                        log.info("Quantidade de leitos atualizada para o estabelecimento {}: {}", codigoCnes,
                                leitosLocal.size());
                    }
                }
            }

            sincronizacaoService.finalizarComSucesso(registro.getId(), inseridos, atualizados);

            return sincronizacaoService.obterPorId(registro.getId());

        } catch (Exception e) {
            log.error("Erro ao sincronizar leitos: {}", codigoCnes, e);
            sincronizacaoService.finalizarComErro(registro.getId(),
                    "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar leitos: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<Object> listarLeitosPorCnes(String codigoCnes) {
        log.debug("Listando leitos do estabelecimento CNES: {}", codigoCnes);

        com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        Estabelecimentos estab = estabelecimentosRepository.findByCodigoCnesAndTenant(codigoCnes, tenant)
                .orElseThrow(() -> new com.upsaude.exception.NotFoundException(
                        "Estabelecimento não encontrado com CNES: " + codigoCnes));

        List<Leitos> leitos = leitosRepository.findByEstabelecimentoIdAndTenant(estab.getId(), tenant.getId());

        // Mapeando para objeto simples para evitar problemas de lazy loading no
        // controller (sem DTO específico)
        return leitos.stream().map(l -> {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            map.put("id", l.getId());
            map.put("codigoCnesLeito", l.getCodigoCnesLeito());
            map.put("numeroLeito", l.getNumeroLeito());
            map.put("status", l.getStatus());
            map.put("dataAtivacao", l.getDataAtivacao());
            map.put("setorUnidade", l.getSetorUnidade());
            map.put("sala", l.getSala());
            map.put("tipoLeito", l.getTipoLeito() != null ? l.getTipoLeito().getDescricao() : null);
            return map;
        }).collect(java.util.stream.Collectors.toList());
    }
}
