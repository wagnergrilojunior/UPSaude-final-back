package com.upsaude.service.impl.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.entity.cnes.CnesSincronizacao;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.ProfissionalEstabelecimento;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.exception.CnesSincronizacaoException;
import com.upsaude.integration.cnes.soap.client.EstabelecimentoCnesSoapClient;
import com.upsaude.integration.cnes.soap.client.ProfissionalCnesSoapClient;
import com.upsaude.integration.cnes.wsdl.cnesservice.ResponseConsultarEstabelecimentoSaude;
import com.upsaude.integration.cnes.wsdl.profissional.DadosBasicosEstabelecimentoType;
import com.upsaude.integration.cnes.wsdl.profissional.ProfissionalSaudeType;
import com.upsaude.integration.cnes.wsdl.profissional.ResponseConsultarProfissionalSaude;
import com.upsaude.mapper.cnes.CnesVinculacaoMapper;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.estabelecimento.ProfissionalEstabelecimentoRepository;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.service.api.cnes.CnesSincronizacaoService;
import com.upsaude.service.api.cnes.CnesVinculacaoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.util.CnesValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CnesVinculacaoServiceImpl implements CnesVinculacaoService {

    private final ProfissionalCnesSoapClient profissionalSoapClient;
    private final EstabelecimentoCnesSoapClient estabelecimentoSoapClient;
    private final CnesSincronizacaoService sincronizacaoService;
    private final TenantService tenantService;
    private final ProfissionaisSaudeRepository profissionalRepository;
    private final EstabelecimentosRepository estabelecimentoRepository;
    private final ProfissionalEstabelecimentoRepository vinculacaoRepository;
    private final CnesVinculacaoMapper cnesMapper;

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarVinculacoesPorProfissional(String cpfOuCns, boolean persistir) {
        log.info("Iniciando sincronização de vinculações por profissional: {}, persistir: {}", cpfOuCns, persistir);

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.VINCULACAO,
                null,
                cpfOuCns,
                null,
                null);

        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());

            ResponseConsultarProfissionalSaude resposta;
            if (cpfOuCns.length() == 11) {
                resposta = profissionalSoapClient.consultarProfissionalPorCpf(cpfOuCns);
            } else {
                resposta = profissionalSoapClient.consultarProfissionalPorCns(cpfOuCns);
            }

            int inseridos = 0;
            int atualizados = 0;

            if (resposta != null && resposta.getProfissionalSaude() != null) {
                ProfissionalSaudeType profissionalCnes = resposta.getProfissionalSaude();

                if (persistir) {
                    ProfissionaisSaude profissional = profissionalRepository
                            .findByCpfAndTenant(profissionalCnes.getCPF().getNumeroCPF(), tenant)
                            .orElseThrow(() -> new CnesSincronizacaoException(
                                    "Profissional deve ser sincronizado primeiro: " + cpfOuCns));

                    List<DadosBasicosEstabelecimentoType> vinculacoesCnes = profissionalCnes.getCNES();
                    for (DadosBasicosEstabelecimentoType vincCnes : vinculacoesCnes) {
                        String codigoCnes = vincCnes.getCodigoCNES().getCodigo();

                        Optional<Estabelecimentos> estabOpt = estabelecimentoRepository
                                .findByCodigoCnesAndTenant(codigoCnes, tenant);

                        if (estabOpt.isPresent()) {
                            Estabelecimentos estab = estabOpt.get();

                            final boolean[] isNovo = { false };
                            ProfissionalEstabelecimento vinculacao = vinculacaoRepository
                                    .findByProfissionalIdAndEstabelecimentoId(profissional.getId(), estab.getId())
                                    .orElseGet(() -> {
                                        isNovo[0] = true;
                                        ProfissionalEstabelecimento novo = new ProfissionalEstabelecimento();
                                        novo.setTenant(tenant);
                                        return novo;
                                    });

                            cnesMapper.mapToVinculacao(vincCnes, profissional, estab, vinculacao);
                            vinculacaoRepository.save(Objects.requireNonNull(vinculacao));

                            if (isNovo[0])
                                inseridos++;
                            else
                                atualizados++;
                        }
                    }
                }
            }

            sincronizacaoService.finalizarComSucesso(registro.getId(), inseridos, atualizados);
            return sincronizacaoService.obterPorId(registro.getId());

        } catch (Exception e) {
            log.error("Erro ao sincronizar vinculações: {}", cpfOuCns, e);
            sincronizacaoService.finalizarComErro(registro.getId(), "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar vinculações: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarVinculacoesPorEstabelecimento(String codigoCnes, boolean persistir) {
        log.info("Iniciando sincronização de vinculações por estabelecimento: {}, persistir: {}", codigoCnes,
                persistir);

        CnesValidator.validarCnes(codigoCnes);
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.VINCULACAO,
                null,
                codigoCnes,
                null,
                null);

        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());

            ResponseConsultarEstabelecimentoSaude resposta = estabelecimentoSoapClient
                    .consultarEstabelecimentoPorCnes(codigoCnes);

            int inseridos = 0;
            int atualizados = 0;

            if (resposta != null && resposta.getResultadoPesquisaEstabelecimentoSaude() != null) {
                com.upsaude.integration.cnes.wsdl.cnesservice.ResultadoPesquisaEstabelecimentoSaudeType resultado = resposta
                        .getResultadoPesquisaEstabelecimentoSaude();

                if (persistir) {
                    Estabelecimentos estab = estabelecimentoRepository.findByCodigoCnesAndTenant(codigoCnes, tenant)
                            .orElseThrow(() -> new CnesSincronizacaoException(
                                    "Estabelecimento deve ser sincronizado primeiro: " + codigoCnes));

                    List<com.upsaude.integration.cnes.wsdl.cnesservice.ProfissionalSaudeType> profissionaisCnes = resultado
                            .getProfissional();
                    for (com.upsaude.integration.cnes.wsdl.cnesservice.ProfissionalSaudeType profCnes : profissionaisCnes) {
                        String cpf = profCnes.getCPF().getNumeroCPF();

                        Optional<ProfissionaisSaude> profOpt = profissionalRepository.findByCpfAndTenant(cpf, tenant);

                        if (profOpt.isPresent()) {
                            ProfissionaisSaude prof = profOpt.get();

                            final boolean[] isNovo = { false };
                            ProfissionalEstabelecimento vinculacao = vinculacaoRepository
                                    .findByProfissionalIdAndEstabelecimentoId(prof.getId(), estab.getId())
                                    .orElseGet(() -> {
                                        isNovo[0] = true;
                                        ProfissionalEstabelecimento novo = new ProfissionalEstabelecimento();
                                        novo.setTenant(tenant);
                                        return novo;
                                    });

                            vinculacao.setProfissional(prof);
                            vinculacao.setEstabelecimento(estab);
                            if (vinculacao.getDataInicio() == null)
                                vinculacao.setDataInicio(OffsetDateTime.now());
                            if (vinculacao.getTipoVinculo() == null)
                                vinculacao.setTipoVinculo(TipoVinculoProfissionalEnum.OUTRO);

                            vinculacaoRepository.save(Objects.requireNonNull(vinculacao));
                            if (isNovo[0])
                                inseridos++;
                            else
                                atualizados++;
                        }
                    }
                }
            }

            sincronizacaoService.finalizarComSucesso(registro.getId(), inseridos, atualizados);
            return sincronizacaoService.obterPorId(registro.getId());

        } catch (Exception e) {
            log.error("Erro ao sincronizar vinculações: {}", codigoCnes, e);
            sincronizacaoService.finalizarComErro(registro.getId(), "Erro: " + e.getMessage(), e.toString(), 1);
            throw new CnesSincronizacaoException("Falha ao sincronizar vinculações: " + e.getMessage(), e);
        }
    }
}
