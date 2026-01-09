package com.upsaude.service.impl.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.entity.cnes.CnesHistoricoEstabelecimento;
import com.upsaude.entity.cnes.CnesSincronizacao;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import com.upsaude.exception.CnesSoapException;
import com.upsaude.exception.CnesSincronizacaoException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.integration.cnes.soap.client.EstabelecimentoCnesSoapClient;
import com.upsaude.integration.cnes.wsdl.cnesservice.*;
import com.upsaude.mapper.cnes.CnesEstabelecimentoMapper;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import com.upsaude.repository.cnes.CnesHistoricoEstabelecimentoRepository;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.service.api.cnes.CnesEstabelecimentoService;
import com.upsaude.service.api.cnes.CnesSincronizacaoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.util.CnesValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CnesEstabelecimentoServiceImpl implements CnesEstabelecimentoService {

    private final EstabelecimentoCnesSoapClient soapClient;
    private final CnesSincronizacaoService sincronizacaoService;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final CnesHistoricoEstabelecimentoRepository historicoRepository;
    private final TenantService tenantService;
    private final CnesEstabelecimentoMapper cnesMapper;
    private final EstabelecimentosMapper estabelecimentosMapper;

    @Override
    @Transactional
    public CnesSincronizacaoResponse sincronizarEstabelecimentoPorCnes(String codigoCnes, String competencia,
            boolean persistir) {
        log.info("Iniciando sincronização de estabelecimento CNES: {}", codigoCnes);

        // Validar formato
        CnesValidator.validarCnes(codigoCnes);
        String competenciaNormalizada = competencia != null ? CnesValidator.normalizarCompetencia(competencia)
                : calcularCompetenciaAtual();
        if (competenciaNormalizada != null) {
            CnesValidator.validarCompetencia(competenciaNormalizada);
        }

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        // Criar registro de sincronização
        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.ESTABELECIMENTO,
                null,
                codigoCnes,
                competenciaNormalizada,
                null);

        try {
            // Marcar como processando
            sincronizacaoService.marcarComoProcessando(registro.getId());

            // Chamar SOAP Client
            com.upsaude.integration.cnes.wsdl.cnesservice.ResponseConsultarEstabelecimentoSaude respostaCnes = soapClient
                    .consultarEstabelecimentoPorCnes(codigoCnes);

            if (respostaCnes == null || respostaCnes.getResultadoPesquisaEstabelecimentoSaude() == null) {
                throw new CnesSincronizacaoException("Resposta do CNES não contém dados do estabelecimento");
            }

            ResultadoPesquisaEstabelecimentoSaudeType resultado = respostaCnes
                    .getResultadoPesquisaEstabelecimentoSaude();
            DadosGeraisEstabelecimentoSaudeType dadosGerais = resultado.getEstabelecimentoSaude();

            if (dadosGerais == null) {
                throw new CnesSincronizacaoException(
                        "Dados gerais do estabelecimento não encontrados na resposta do CNES");
            }

            // Buscar ou criar estabelecimento local
            final boolean[] isNovo = { false };
            Estabelecimentos estabelecimento = estabelecimentosRepository.findByCodigoCnesAndTenant(codigoCnes, tenant)
                    .orElseGet(() -> {
                        isNovo[0] = true;
                        Estabelecimentos novo = new Estabelecimentos();
                        novo.setTenant(tenant);
                        return novo;
                    });

            if (persistir) {
                cnesMapper.mapToEstabelecimento(dadosGerais, estabelecimento, competenciaNormalizada);
                // Serializar resposta completa para histórico e para a entidade principal
                String dadosJson = cnesMapper.serializeToJson(resultado);
                estabelecimento.setCnesDadosJson(dadosJson);

                Estabelecimentos saved = estabelecimentosRepository.save(Objects.requireNonNull(estabelecimento));

                // Salvar histórico
                salvarHistorico(saved, competenciaNormalizada, dadosJson);

                // Atualizar registro de sincronização
                sincronizacaoService.finalizarComSucesso(
                        registro.getId(),
                        isNovo[0] ? 1 : 0,
                        isNovo[0] ? 0 : 1);

                log.info("Sincronização de estabelecimento concluída. CNES: {}, Estabelecimento ID: {}, Novo: {}",
                        codigoCnes, saved.getId(), isNovo);
            } else {
                sincronizacaoService.finalizarComSucesso(registro.getId(), 0, 0);
            }

            return sincronizacaoService.obterPorId(registro.getId());

        } catch (CnesSoapException e) {
            log.error("Erro SOAP ao sincronizar estabelecimento CNES: {}", codigoCnes, e);
            sincronizacaoService.finalizarComErro(registro.getId(),
                    "Erro SOAP: " + e.getMessage(),
                    e.toString(),
                    1);
            throw new CnesSincronizacaoException("Falha ao sincronizar estabelecimento: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Erro inesperado ao sincronizar estabelecimento CNES: {}", codigoCnes, e);
            sincronizacaoService.finalizarComErro(registro.getId(),
                    "Erro inesperado: " + e.getMessage(),
                    e.toString(),
                    1);
            throw new CnesSincronizacaoException("Falha ao sincronizar estabelecimento: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public List<CnesSincronizacaoResponse> sincronizarEstabelecimentosPorMunicipio(String codigoMunicipio,
            String competencia, boolean persistir) {
        log.info("Iniciando sincronização de estabelecimentos por município: {}", codigoMunicipio);

        String competenciaNormalizada = competencia != null ? CnesValidator.normalizarCompetencia(competencia)
                : calcularCompetenciaAtual();
        if (competenciaNormalizada != null) {
            CnesValidator.validarCompetencia(competenciaNormalizada);
        }

        List<CnesSincronizacaoResponse> resultados = new ArrayList<>();

        CnesSincronizacao registroLote = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.ESTABELECIMENTO,
                null,
                "LOTE-MUN-" + codigoMunicipio,
                competenciaNormalizada,
                null);
        try {
            sincronizacaoService.marcarComoProcessando(registroLote.getId());

            // Chamar SOAP Client para listar estabelecimentos do município
            ResponseConsultarEstabelecimentoSaudePorMunicipio resposta = soapClient
                    .consultarEstabelecimentosPorMunicipio(codigoMunicipio, competenciaNormalizada);

            if (resposta != null && resposta.getResultadoPesquisaCnesMunicipio() != null) {
                List<ResultadoPesquisaCnesMunicipioType> resultadosMunicipio = resposta
                        .getResultadoPesquisaCnesMunicipio();

                // Para cada resultado de município
                for (ResultadoPesquisaCnesMunicipioType resultado : resultadosMunicipio) {
                    // Para cada estabelecimento retornado, chamar sincronizarEstabelecimentoPorCnes
                    if (resultado.getDadosBasicosEstabelecimento() != null) {
                        for (DadosBasicosEstabelecimentoType dadosBasicos : resultado
                                .getDadosBasicosEstabelecimento()) {
                            if (dadosBasicos.getCodigoCNES() != null
                                    && dadosBasicos.getCodigoCNES().getCodigo() != null) {
                                try {
                                    CnesSincronizacaoResponse sync = sincronizarEstabelecimentoPorCnes(
                                            dadosBasicos.getCodigoCNES().getCodigo(), competenciaNormalizada,
                                            persistir);
                                    resultados.add(sync);
                                } catch (Exception e) {
                                    log.error("Erro ao sincronizar estabelecimento CNES: {}",
                                            dadosBasicos.getCodigoCNES().getCodigo(), e);
                                }
                            }
                        }
                    }
                }
            }

            sincronizacaoService.finalizarComSucesso(registroLote.getId(), 0, resultados.size());

            log.info("Sincronização por município concluída. Município: {}, Estabelecimentos processados: {}",
                    codigoMunicipio, resultados.size());

        } catch (Exception e) {
            log.error("Erro ao sincronizar estabelecimentos por município: {}", codigoMunicipio, e);
            throw new CnesSincronizacaoException(
                    "Falha ao sincronizar estabelecimentos do município: " + e.getMessage(), e);
        }

        return resultados;
    }

    @Override
    @Transactional
    public CnesSincronizacaoResponse atualizarDadosComplementares(String codigoMunicipio, String competencia,
            boolean persistir) {
        log.info("Atualizando dados complementares. Município: {}", codigoMunicipio);

        String competenciaNormalizada = competencia != null ? CnesValidator.normalizarCompetencia(competencia)
                : calcularCompetenciaAtual();
        if (competenciaNormalizada != null) {
            CnesValidator.validarCompetencia(competenciaNormalizada);
        }

        com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();

        CnesSincronizacao registro = sincronizacaoService.criarRegistroSincronizacao(
                TipoEntidadeCnesEnum.ESTABELECIMENTO,
                null,
                codigoMunicipio,
                competenciaNormalizada,
                null);

        try {
            sincronizacaoService.marcarComoProcessando(registro.getId());

            // Chamar consultarDadosComplementares (retorna sumário)
            ResponseConsultarDadosComplementaresEstabelecimentoSaude resposta = soapClient
                    .consultarDadosComplementares(codigoMunicipio, competenciaNormalizada);

            int atualizados = 0;
            if (resposta != null && resposta.getResultadoPesquisaDadosComplementaresCnes() != null) {
                log.info("Dados complementares (sumário) recebidos para município: {}", codigoMunicipio);

                if (persistir) {
                    // Como o CNES retorna apenas sumários nesta operação, vamos atualizar
                    // os estabelecimentos locais deste município buscando detalhes individuais
                    // que conterão os dados complementares mapeados.
                    List<Estabelecimentos> locais = estabelecimentosRepository
                            .findByCodigoIbgeMunicipioAndTenant(codigoMunicipio, tenant);
                    for (Estabelecimentos estab : locais) {
                        try {
                            // Sincronizar individualmente para pegar Dados Complementares detalhados
                            sincronizarEstabelecimentoPorCnes(estab.getDadosIdentificacao().getCnes(),
                                    competenciaNormalizada, true);
                            atualizados++;
                        } catch (Exception e) {
                            log.warn("Falha ao atualizar dados complementares do estabelecimento {}: {}",
                                    estab.getDadosIdentificacao().getCnes(), e.getMessage());
                        }
                    }
                }
            }

            sincronizacaoService.finalizarComSucesso(registro.getId(), 0, atualizados);

            return sincronizacaoService.obterPorId(registro.getId());

        } catch (Exception e) {
            log.error("Erro ao atualizar dados complementares: {}", codigoMunicipio, e);
            sincronizacaoService.finalizarComErro(registro.getId(),
                    "Erro: " + e.getMessage(),
                    e.toString(),
                    1);
            throw new CnesSincronizacaoException("Falha ao atualizar dados complementares: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EstabelecimentosResponse buscarEstabelecimentoNoCnes(String codigoCnes) {
        log.debug("Buscando estabelecimento no CNES: {}", codigoCnes);

        CnesValidator.validarCnes(codigoCnes);

        // Chamar SOAP Client apenas para buscar
        com.upsaude.integration.cnes.wsdl.cnesservice.ResponseConsultarEstabelecimentoSaude resposta = soapClient
                .consultarEstabelecimentoPorCnes(codigoCnes);

        if (resposta == null || resposta.getResultadoPesquisaEstabelecimentoSaude() == null) {
            throw new NotFoundException("Estabelecimento não encontrado no CNES: " + codigoCnes);
        }

        ResultadoPesquisaEstabelecimentoSaudeType resultado = resposta.getResultadoPesquisaEstabelecimentoSaude();
        DadosGeraisEstabelecimentoSaudeType dadosGerais = resultado.getEstabelecimentoSaude();

        if (dadosGerais == null) {
            throw new NotFoundException("Dados do estabelecimento não encontrados no CNES: " + codigoCnes);
        }

        // Criar estabelecimento temporário para mapear
        Estabelecimentos estabelecimentoTemp = new Estabelecimentos();
        cnesMapper.mapToEstabelecimento(dadosGerais, estabelecimentoTemp, null);

        // Converter para Response
        return estabelecimentosMapper.toResponse(estabelecimentoTemp);
    }

    private void salvarHistorico(Estabelecimentos estabelecimento, String competencia, String dadosJsonb) {
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        UUID tenantId = tenant.getId();

        // Verificar se já existe histórico para esta competência
        CnesHistoricoEstabelecimento historico = historicoRepository
                .findByEstabelecimentoIdAndCompetenciaAndTenant(estabelecimento.getId(), competencia, tenantId)
                .orElse(new CnesHistoricoEstabelecimento());

        historico.setEstabelecimento(estabelecimento);
        historico.setCompetencia(competencia);
        historico.setDadosJsonb(dadosJsonb);
        historico.setDataSincronizacao(OffsetDateTime.now());
        historico.setTenant(tenant);

        historicoRepository.save(historico);
    }

    private String calcularCompetenciaAtual() {
        YearMonth current = YearMonth.now().minusMonths(1); // Mês anterior
        return current.format(DateTimeFormatter.ofPattern("yyyyMM"));
    }
}
