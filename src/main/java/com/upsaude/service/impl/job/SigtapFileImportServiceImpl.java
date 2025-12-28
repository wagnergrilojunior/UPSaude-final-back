package com.upsaude.service.impl.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.entity.referencia.sigtap.SigtapDescricao;
import com.upsaude.entity.referencia.sigtap.SigtapDescricaoDetalhe;
import com.upsaude.entity.referencia.sigtap.SigtapGrupo;
import com.upsaude.importacao.sigtap.file.SigtapFileParser;
import com.upsaude.importacao.sigtap.file.SigtapLayoutDefinition;
import com.upsaude.importacao.sigtap.file.SigtapLayoutReader;
import com.upsaude.mapper.sigtap.SigtapEntityMapper;
import com.upsaude.repository.referencia.sigtap.SigtapCompatibilidadeRepository;
import com.upsaude.repository.referencia.sigtap.SigtapComponenteRedeRepository;
import com.upsaude.repository.referencia.sigtap.SigtapDescricaoDetalheRepository;
import com.upsaude.repository.referencia.sigtap.SigtapDescricaoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapDetalheRepository;
import com.upsaude.repository.referencia.sigtap.SigtapExcecaoCompatibilidadeRepository;
import com.upsaude.repository.referencia.sigtap.SigtapFinanciamentoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapFormaOrganizacaoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapGrupoHabilitacaoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapGrupoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapHabilitacaoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapModalidadeRepository;
import com.upsaude.repository.referencia.sigtap.SigtapCboRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoCidRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoComponenteRedeRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoDetalheItemRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoHabilitacaoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoIncrementoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoLeitoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoModalidadeRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoOcupacaoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoOrigemRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRegistroRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRegraCondicionadaRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRenasesRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoServicoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoSiaSihRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoTussRepository;
import com.upsaude.repository.referencia.sigtap.SigtapRedeAtencaoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapRegistroRepository;
import com.upsaude.repository.referencia.sigtap.SigtapRegraCondicionadaRepository;
import com.upsaude.repository.referencia.sigtap.SigtapRenasesRepository;
import com.upsaude.repository.referencia.sigtap.SigtapRubricaRepository;
import com.upsaude.repository.referencia.sigtap.SigtapServicoClassificacaoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapServicoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapSiaSihRepository;
import com.upsaude.repository.referencia.sigtap.SigtapSubgrupoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapTipoLeitoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapTussRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Profile("legacy-import")
@Deprecated
public class SigtapFileImportServiceImpl {

    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    @Value("${sigtap.import.base-path:data_import/sigtap}")
    private String basePath;

    @Value("${sigtap.import.batch-size:500}")
    private int batchSize;

    private final SigtapLayoutReader layoutReader;
    private final SigtapFileParser fileParser;
    private final SigtapEntityMapper entityMapper;
    
    private final SigtapGrupoRepository grupoRepository;
    private final SigtapSubgrupoRepository subgrupoRepository;
    private final SigtapFormaOrganizacaoRepository formaOrganizacaoRepository;
    private final SigtapProcedimentoRepository procedimentoRepository;
    
    private final SigtapFinanciamentoRepository financiamentoRepository;
    private final SigtapRubricaRepository rubricaRepository;
    private final SigtapModalidadeRepository modalidadeRepository;
    private final SigtapRegistroRepository registroRepository;
    private final SigtapTipoLeitoRepository tipoLeitoRepository;
    private final SigtapServicoRepository servicoRepository;
    private final SigtapServicoClassificacaoRepository servicoClassificacaoRepository;
    private final SigtapCboRepository cboRepository;
    private final SigtapHabilitacaoRepository habilitacaoRepository;
    private final SigtapGrupoHabilitacaoRepository grupoHabilitacaoRepository;
    private final SigtapRegraCondicionadaRepository regraCondicionadaRepository;
    private final SigtapRenasesRepository renasesRepository;
    private final SigtapTussRepository tussRepository;
    private final SigtapComponenteRedeRepository componenteRedeRepository;
    private final SigtapRedeAtencaoRepository redeAtencaoRepository;
    private final SigtapSiaSihRepository siaSihRepository;
    private final SigtapDetalheRepository detalheRepository;
    private final SigtapDescricaoRepository descricaoRepository;
    private final SigtapDescricaoDetalheRepository descricaoDetalheRepository;
    
    // Repositories relacionais
    private final SigtapProcedimentoCidRepository procedimentoCidRepository;
    private final SigtapProcedimentoOcupacaoRepository procedimentoOcupacaoRepository;
    private final SigtapProcedimentoHabilitacaoRepository procedimentoHabilitacaoRepository;
    private final SigtapProcedimentoLeitoRepository procedimentoLeitoRepository;
    private final SigtapProcedimentoServicoRepository procedimentoServicoRepository;
    private final SigtapProcedimentoIncrementoRepository procedimentoIncrementoRepository;
    private final SigtapProcedimentoComponenteRedeRepository procedimentoComponenteRedeRepository;
    private final SigtapProcedimentoOrigemRepository procedimentoOrigemRepository;
    private final SigtapProcedimentoSiaSihRepository procedimentoSiaSihRepository;
    private final SigtapProcedimentoRegraCondicionadaRepository procedimentoRegraCondicionadaRepository;
    private final SigtapProcedimentoRenasesRepository procedimentoRenasesRepository;
    private final SigtapProcedimentoTussRepository procedimentoTussRepository;
    private final SigtapProcedimentoModalidadeRepository procedimentoModalidadeRepository;
    private final SigtapProcedimentoRegistroRepository procedimentoRegistroRepository;
    private final SigtapProcedimentoDetalheItemRepository procedimentoDetalheItemRepository;
    private final SigtapExcecaoCompatibilidadeRepository excecaoCompatibilidadeRepository;
    private final SigtapCompatibilidadeRepository compatibilidadeRepository;

    public SigtapFileImportServiceImpl(
            SigtapLayoutReader layoutReader,
            SigtapFileParser fileParser,
            SigtapEntityMapper entityMapper,
            SigtapGrupoRepository grupoRepository,
            SigtapSubgrupoRepository subgrupoRepository,
            SigtapFormaOrganizacaoRepository formaOrganizacaoRepository,
            SigtapProcedimentoRepository procedimentoRepository,
            SigtapFinanciamentoRepository financiamentoRepository,
            SigtapRubricaRepository rubricaRepository,
            SigtapModalidadeRepository modalidadeRepository,
            SigtapRegistroRepository registroRepository,
            SigtapTipoLeitoRepository tipoLeitoRepository,
            SigtapServicoRepository servicoRepository,
            SigtapServicoClassificacaoRepository servicoClassificacaoRepository,
            SigtapCboRepository cboRepository,
            SigtapHabilitacaoRepository habilitacaoRepository,
            SigtapGrupoHabilitacaoRepository grupoHabilitacaoRepository,
            SigtapRegraCondicionadaRepository regraCondicionadaRepository,
            SigtapRenasesRepository renasesRepository,
            SigtapTussRepository tussRepository,
            SigtapComponenteRedeRepository componenteRedeRepository,
            SigtapRedeAtencaoRepository redeAtencaoRepository,
            SigtapSiaSihRepository siaSihRepository,
            SigtapDetalheRepository detalheRepository,
            SigtapDescricaoRepository descricaoRepository,
            SigtapDescricaoDetalheRepository descricaoDetalheRepository,
            SigtapProcedimentoCidRepository procedimentoCidRepository,
            SigtapProcedimentoOcupacaoRepository procedimentoOcupacaoRepository,
            SigtapProcedimentoHabilitacaoRepository procedimentoHabilitacaoRepository,
            SigtapProcedimentoLeitoRepository procedimentoLeitoRepository,
            SigtapProcedimentoServicoRepository procedimentoServicoRepository,
            SigtapProcedimentoIncrementoRepository procedimentoIncrementoRepository,
            SigtapProcedimentoComponenteRedeRepository procedimentoComponenteRedeRepository,
            SigtapProcedimentoOrigemRepository procedimentoOrigemRepository,
            SigtapProcedimentoSiaSihRepository procedimentoSiaSihRepository,
            SigtapProcedimentoRegraCondicionadaRepository procedimentoRegraCondicionadaRepository,
            SigtapProcedimentoRenasesRepository procedimentoRenasesRepository,
            SigtapProcedimentoTussRepository procedimentoTussRepository,
            SigtapProcedimentoModalidadeRepository procedimentoModalidadeRepository,
            SigtapProcedimentoRegistroRepository procedimentoRegistroRepository,
            SigtapProcedimentoDetalheItemRepository procedimentoDetalheItemRepository,
            SigtapExcecaoCompatibilidadeRepository excecaoCompatibilidadeRepository,
            SigtapCompatibilidadeRepository compatibilidadeRepository) {
        this.layoutReader = layoutReader;
        this.fileParser = fileParser;
        this.entityMapper = entityMapper;
        this.grupoRepository = grupoRepository;
        this.subgrupoRepository = subgrupoRepository;
        this.formaOrganizacaoRepository = formaOrganizacaoRepository;
        this.procedimentoRepository = procedimentoRepository;
        this.financiamentoRepository = financiamentoRepository;
        this.rubricaRepository = rubricaRepository;
        this.modalidadeRepository = modalidadeRepository;
        this.registroRepository = registroRepository;
        this.tipoLeitoRepository = tipoLeitoRepository;
        this.servicoRepository = servicoRepository;
        this.servicoClassificacaoRepository = servicoClassificacaoRepository;
        this.cboRepository = cboRepository;
        this.habilitacaoRepository = habilitacaoRepository;
        this.grupoHabilitacaoRepository = grupoHabilitacaoRepository;
        this.regraCondicionadaRepository = regraCondicionadaRepository;
        this.renasesRepository = renasesRepository;
        this.tussRepository = tussRepository;
        this.componenteRedeRepository = componenteRedeRepository;
        this.redeAtencaoRepository = redeAtencaoRepository;
        this.siaSihRepository = siaSihRepository;
        this.detalheRepository = detalheRepository;
        this.descricaoRepository = descricaoRepository;
        this.descricaoDetalheRepository = descricaoDetalheRepository;
        this.procedimentoCidRepository = procedimentoCidRepository;
        this.procedimentoOcupacaoRepository = procedimentoOcupacaoRepository;
        this.procedimentoHabilitacaoRepository = procedimentoHabilitacaoRepository;
        this.procedimentoLeitoRepository = procedimentoLeitoRepository;
        this.procedimentoServicoRepository = procedimentoServicoRepository;
        this.procedimentoIncrementoRepository = procedimentoIncrementoRepository;
        this.procedimentoComponenteRedeRepository = procedimentoComponenteRedeRepository;
        this.procedimentoOrigemRepository = procedimentoOrigemRepository;
        this.procedimentoSiaSihRepository = procedimentoSiaSihRepository;
        this.procedimentoRegraCondicionadaRepository = procedimentoRegraCondicionadaRepository;
        this.procedimentoRenasesRepository = procedimentoRenasesRepository;
        this.procedimentoTussRepository = procedimentoTussRepository;
        this.procedimentoModalidadeRepository = procedimentoModalidadeRepository;
        this.procedimentoRegistroRepository = procedimentoRegistroRepository;
        this.procedimentoDetalheItemRepository = procedimentoDetalheItemRepository;
        this.excecaoCompatibilidadeRepository = excecaoCompatibilidadeRepository;
        this.compatibilidadeRepository = compatibilidadeRepository;
    }

    public ImportResult importarCompetencia(String competencia) {
        // #region agent log
        log.info("Iniciando importa??o da compet?ncia: {}", competencia);
        ImportResult result = new ImportResult(competencia);

        Path competenciaPath = Paths.get(basePath, competencia);
        if (!Files.exists(competenciaPath) || !Files.isDirectory(competenciaPath)) {
            throw new IllegalArgumentException("Pasta da compet?ncia n?o encontrada: " + competenciaPath);
        }

        try {
            importarArquivo(competenciaPath, "tb_grupo.txt", competencia, result, this::importarGrupos);
            importarArquivo(competenciaPath, "tb_financiamento.txt", competencia, result, this::importarFinanciamentos);
            importarArquivo(competenciaPath, "tb_rubrica.txt", competencia, result, this::importarRubricas);
            importarArquivo(competenciaPath, "tb_modalidade.txt", competencia, result, this::importarModalidades);
            importarArquivo(competenciaPath, "tb_registro.txt", competencia, result, this::importarRegistros);
            importarArquivo(competenciaPath, "tb_tipo_leito.txt", competencia, result, this::importarTiposLeito);
            importarArquivo(competenciaPath, "tb_servico.txt", competencia, result, this::importarServicos);
            importarArquivo(competenciaPath, "tb_servico_classificacao.txt", competencia, result, this::importarServicosClassificacao);
            importarArquivo(competenciaPath, "tb_ocupacao.txt", competencia, result, this::importarOcupacoes);
            importarArquivo(competenciaPath, "tb_habilitacao.txt", competencia, result, this::importarHabilitacoes);
            importarArquivo(competenciaPath, "tb_grupo_habilitacao.txt", competencia, result, this::importarGruposHabilitacao);
            importarArquivo(competenciaPath, "tb_regra_condicionada.txt", competencia, result, this::importarRegrasCondicionadas);
            importarArquivo(competenciaPath, "tb_renases.txt", competencia, result, this::importarRenases);
            importarArquivo(competenciaPath, "tb_tuss.txt", competencia, result, this::importarTuss);
            importarArquivo(competenciaPath, "tb_componente_rede.txt", competencia, result, this::importarComponentesRede);
            importarArquivo(competenciaPath, "tb_rede_atencao.txt", competencia, result, this::importarRedesAtencao);
            importarArquivo(competenciaPath, "tb_sia_sih.txt", competencia, result, this::importarSiaSih);
            importarArquivo(competenciaPath, "tb_detalhe.txt", competencia, result, this::importarDetalhes);

            importarArquivo(competenciaPath, "tb_sub_grupo.txt", competencia, result, this::importarSubgrupos);
            importarArquivo(competenciaPath, "tb_forma_organizacao.txt", competencia, result, this::importarFormasOrganizacao);

            // Fase 3: Procedimentos (deve vir ANTES de tb_descricao.txt que depende deles)
            importarArquivo(competenciaPath, "tb_procedimento.txt", competencia, result, this::importarProcedimentos);

            // Fase 4: Descrições (dependem dos procedimentos)
            importarArquivo(competenciaPath, "tb_descricao.txt", competencia, result, this::importarDescricoes);
            importarArquivo(competenciaPath, "tb_descricao_detalhe.txt", competencia, result, this::importarDescricoesDetalhe);

            importarArquivo(competenciaPath, "rl_procedimento_compativel.txt", competencia, result, this::importarCompatibilidades);

            importarArquivo(competenciaPath, "rl_procedimento_cid.txt", competencia, result, this::importarProcedimentosCid);
            importarArquivo(competenciaPath, "rl_procedimento_ocupacao.txt", competencia, result, this::importarProcedimentosOcupacao);
            importarArquivo(competenciaPath, "rl_procedimento_habilitacao.txt", competencia, result, this::importarProcedimentosHabilitacao);
            importarArquivo(competenciaPath, "rl_procedimento_leito.txt", competencia, result, this::importarProcedimentosLeito);
            importarArquivo(competenciaPath, "rl_procedimento_servico.txt", competencia, result, this::importarProcedimentosServico);
            importarArquivo(competenciaPath, "rl_procedimento_incremento.txt", competencia, result, this::importarProcedimentosIncremento);
            importarArquivo(competenciaPath, "rl_procedimento_comp_rede.txt", competencia, result, this::importarProcedimentosComponenteRede);
            importarArquivo(competenciaPath, "rl_procedimento_origem.txt", competencia, result, this::importarProcedimentosOrigem);
            importarArquivo(competenciaPath, "rl_procedimento_sia_sih.txt", competencia, result, this::importarProcedimentosSiaSih);
            importarArquivo(competenciaPath, "rl_procedimento_regra_cond.txt", competencia, result, this::importarProcedimentosRegraCondicionada);
            importarArquivo(competenciaPath, "rl_procedimento_renases.txt", competencia, result, this::importarProcedimentosRenases);
            importarArquivo(competenciaPath, "rl_procedimento_tuss.txt", competencia, result, this::importarProcedimentosTuss);
            importarArquivo(competenciaPath, "rl_procedimento_modalidade.txt", competencia, result, this::importarProcedimentosModalidade);
            importarArquivo(competenciaPath, "rl_procedimento_registro.txt", competencia, result, this::importarProcedimentosRegistro);
            importarArquivo(competenciaPath, "rl_procedimento_detalhe.txt", competencia, result, this::importarProcedimentosDetalheItem);
            importarArquivo(competenciaPath, "rl_excecao_compatibilidade.txt", competencia, result, this::importarExcecoesCompatibilidade);

            log.info("Importa??o da compet?ncia {} conclu?da. Total: {} linhas processadas, {} erros", 
                    competencia, result.getTotalLinhasProcessadas(), result.getTotalErros());
        } catch (Exception e) {
            // Capturar exceções sem propagar para evitar fechamento do contexto Spring
            log.error("Erro ao importar compet?ncia {}: {}", competencia, e.getMessage(), e);
            result.addErro("Erro geral: " + e.getMessage());
            // NÃO relançar a exceção para evitar fechamento do contexto Spring
        }

        return result;
    }

    private void importarArquivo(Path competenciaPath, String nomeArquivo, String competencia, 
                                ImportResult result, Function<ImportContext, Integer> importador) {
        // #region agent log
        try {
            java.nio.file.Files.write(
                java.nio.file.Paths.get("/Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code/UPSaude-back/.cursor/debug.log"),
                (String.format("{\"sessionId\":\"debug-session\",\"runId\":\"import-start\",\"hypothesisId\":\"B\",\"location\":\"SigtapFileImportServiceImpl.java:257\",\"message\":\"Processando arquivo\",\"data\":{\"arquivo\":\"%s\",\"competencia\":\"%s\",\"timestamp\":%d},\"timestamp\":%d}%n", 
                    nomeArquivo, competencia, System.currentTimeMillis(), System.currentTimeMillis())).getBytes(),
                java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (Exception e) {}
        // #endregion
        Path arquivoPath = competenciaPath.resolve(nomeArquivo);
        Path layoutPath = competenciaPath.resolve(nomeArquivo.replace(".txt", "_layout.txt"));

        if (!Files.exists(arquivoPath)) {
            log.warn("Arquivo n?o encontrado: {}", arquivoPath);
            return;
        }

        if (!Files.exists(layoutPath)) {
            log.warn("Layout n?o encontrado: {}", layoutPath);
            result.addErro("Layout n?o encontrado: " + nomeArquivo);
            return;
        }

        try {
            SigtapLayoutDefinition layout = layoutReader.readLayout(layoutPath);
            ImportContext context = new ImportContext(arquivoPath, layout, competencia);
            int linhasProcessadas = importador.apply(context);
            result.addLinhasProcessadas(nomeArquivo, linhasProcessadas);
            log.info("Arquivo {} importado: {} linhas processadas", nomeArquivo, linhasProcessadas);
        } catch (IllegalStateException e) {
            log.error("ERRO CRÍTICO ao importar arquivo {}: Contexto Spring fechado. {}", nomeArquivo, e.getMessage(), e);
            result.addErro(nomeArquivo + ": ERRO CRÍTICO - Contexto Spring fechado: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erro ao importar arquivo {}: {}", nomeArquivo, e.getMessage(), e);
            result.addErro(nomeArquivo + ": " + e.getMessage());
        }
    }

    @Transactional
    private int importarGrupos(ImportContext context) {
        return importarComBatch(context, fields -> {
            SigtapGrupo grupo = entityMapper.mapToGrupo(fields, context.competencia);
            return grupoRepository.findByCodigoOficial(grupo.getCodigoOficial())
                    .map(existing -> {
                        existing.setNome(grupo.getNome());
                        existing.setCompetenciaInicial(grupo.getCompetenciaInicial());
                        return existing;
                    })
                    .orElse(grupo);
        }, grupoRepository::saveAll);
    }

    @Transactional
    private int importarSubgrupos(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToSubgrupo(fields, context.competencia),
                subgrupoRepository::saveAll);
    }

    @Transactional
    private int importarFormasOrganizacao(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToFormaOrganizacao(fields, context.competencia),
                formaOrganizacaoRepository::saveAll);
    }

    @Transactional
    private int importarProcedimentos(ImportContext context) {
        return importarComBatch(context, fields -> {
            com.upsaude.entity.referencia.sigtap.SigtapProcedimento procedimento = entityMapper.mapToProcedimento(fields, context.competencia);
            return procedimentoRepository.findByCodigoOficial(procedimento.getCodigoOficial())
                    .map(existing -> {
                        // Atualizar campos do procedimento existente
                        existing.setNome(procedimento.getNome());
                        existing.setCompetenciaInicial(procedimento.getCompetenciaInicial());
                        existing.setTipoComplexidade(procedimento.getTipoComplexidade());
                        existing.setSexoPermitido(procedimento.getSexoPermitido());
                        existing.setIdadeMinima(procedimento.getIdadeMinima());
                        existing.setIdadeMaxima(procedimento.getIdadeMaxima());
                        existing.setMediaDiasInternacao(procedimento.getMediaDiasInternacao());
                        existing.setQuantidadeMaximaDias(procedimento.getQuantidadeMaximaDias());
                        existing.setLimiteMaximo(procedimento.getLimiteMaximo());
                        existing.setPontos(procedimento.getPontos());
                        existing.setFinanciamento(procedimento.getFinanciamento());
                        existing.setRubrica(procedimento.getRubrica());
                        existing.setValorServicoHospitalar(procedimento.getValorServicoHospitalar());
                        existing.setValorServicoAmbulatorial(procedimento.getValorServicoAmbulatorial());
                        existing.setValorServicoProfissional(procedimento.getValorServicoProfissional());
                        return existing;
                    })
                    .orElse(procedimento);
        }, procedimentoRepository::saveAll);
    }

    @Transactional
    private int importarFinanciamentos(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToFinanciamento(fields, context.competencia),
                financiamentoRepository::saveAll);
    }

    @Transactional
    private int importarRubricas(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToRubrica(fields, context.competencia),
                rubricaRepository::saveAll);
    }

    @Transactional
    private int importarModalidades(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToModalidade(fields, context.competencia),
                modalidadeRepository::saveAll);
    }

    @Transactional
    private int importarRegistros(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToRegistro(fields, context.competencia),
                registroRepository::saveAll);
    }

    @Transactional
    private int importarTiposLeito(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToTipoLeito(fields),
                tipoLeitoRepository::saveAll);
    }

    @Transactional
    private int importarServicos(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToServico(fields),
                servicoRepository::saveAll);
    }

    @Transactional
    private int importarServicosClassificacao(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToServicoClassificacao(fields, context.competencia),
                servicoClassificacaoRepository::saveAll);
    }


    @Transactional
    private int importarOcupacoes(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToOcupacao(fields),
                cboRepository::saveAll);
    }

    @Transactional
    private int importarHabilitacoes(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToHabilitacao(fields, context.competencia),
                habilitacaoRepository::saveAll);
    }

    @Transactional
    private int importarGruposHabilitacao(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToGrupoHabilitacao(fields),
                grupoHabilitacaoRepository::saveAll);
    }

    @Transactional
    private int importarRegrasCondicionadas(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToRegraCondicionada(fields),
                regraCondicionadaRepository::saveAll);
    }

    @Transactional
    private int importarRenases(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToRenases(fields),
                renasesRepository::saveAll);
    }

    @Transactional
    private int importarTuss(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToTuss(fields),
                tussRepository::saveAll);
    }

    @Transactional
    private int importarComponentesRede(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToComponenteRede(fields),
                componenteRedeRepository::saveAll);
    }

    @Transactional
    private int importarRedesAtencao(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToRedeAtencao(fields),
                redeAtencaoRepository::saveAll);
    }

    @Transactional
    private int importarSiaSih(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToSiaSih(fields),
                siaSihRepository::saveAll);
    }

    @Transactional
    private int importarDetalhes(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToDetalhe(fields, context.competencia),
                detalheRepository::saveAll);
    }

    @Transactional
    private int importarDescricoes(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToDescricao(fields, context.competencia),
                entities -> {
                    List<SigtapDescricao> toSave = new ArrayList<>();
                    for (SigtapDescricao descricao : entities) {
                        if (descricao.getProcedimento() != null && descricao.getCompetenciaInicial() != null) {
                            Optional<SigtapDescricao> existing = descricaoRepository.findByProcedimentoAndCompetenciaInicial(
                                    descricao.getProcedimento(), descricao.getCompetenciaInicial());
                            if (existing.isEmpty()) {
                                toSave.add(descricao);
                            }
                        } else {
                            toSave.add(descricao);
                        }
                    }
                    if (!toSave.isEmpty()) {
                        return descricaoRepository.saveAll(toSave);
                    }
                    return new ArrayList<>();
                });
    }

    @Transactional
    private int importarDescricoesDetalhe(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToDescricaoDetalhe(fields, context.competencia),
                entities -> {
                    List<SigtapDescricaoDetalhe> toSave = new ArrayList<>();
                    for (SigtapDescricaoDetalhe descricaoDetalhe : entities) {
                        if (descricaoDetalhe.getDetalhe() != null && descricaoDetalhe.getCompetenciaInicial() != null) {
                            Optional<SigtapDescricaoDetalhe> existing = descricaoDetalheRepository.findByDetalheAndCompetenciaInicial(
                                    descricaoDetalhe.getDetalhe(), descricaoDetalhe.getCompetenciaInicial());
                            if (existing.isEmpty()) {
                                toSave.add(descricaoDetalhe);
                            }
                        } else {
                            toSave.add(descricaoDetalhe);
                        }
                    }
                    if (!toSave.isEmpty()) {
                        return descricaoDetalheRepository.saveAll(toSave);
                    }
                    return new ArrayList<>();
                });
    }

    @Transactional
    private int importarCompatibilidades(ImportContext context) {
        // TODO: Implementar quando tivermos o mapeamento de compatibilidade
        log.warn("Importa??o de compatibilidades ainda n?o implementada");
        return 0;
    }

    @Transactional(timeout = 3600) // Timeout de 1 hora para arquivos grandes (81k linhas)
    private int importarProcedimentosCid(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoCid(fields, context.competencia),
                procedimentoCidRepository::saveAll);
    }

    @Transactional(timeout = 3600)
    private int importarProcedimentosOcupacao(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoOcupacao(fields, context.competencia),
                procedimentoOcupacaoRepository::saveAll);
    }

    @Transactional
    private int importarProcedimentosHabilitacao(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoHabilitacao(fields, context.competencia),
                procedimentoHabilitacaoRepository::saveAll);
    }

    @Transactional
    private int importarProcedimentosLeito(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoLeito(fields, context.competencia),
                procedimentoLeitoRepository::saveAll);
    }

    @Transactional
    private int importarProcedimentosServico(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoServico(fields, context.competencia),
                procedimentoServicoRepository::saveAll);
    }

    @Transactional
    private int importarProcedimentosIncremento(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoIncremento(fields, context.competencia),
                procedimentoIncrementoRepository::saveAll);
    }

    @Transactional
    private int importarProcedimentosComponenteRede(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoComponenteRede(fields),
                procedimentoComponenteRedeRepository::saveAll);
    }

    @Transactional
    private int importarProcedimentosOrigem(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoOrigem(fields, context.competencia),
                procedimentoOrigemRepository::saveAll);
    }

    @Transactional
    private int importarProcedimentosSiaSih(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoSiaSih(fields, context.competencia),
                entities -> {
                    List<com.upsaude.entity.referencia.sigtap.SigtapProcedimentoSiaSih> toSave = new ArrayList<>();
                    for (com.upsaude.entity.referencia.sigtap.SigtapProcedimentoSiaSih procSiaSih : entities) {
                        if (procSiaSih.getProcedimento() != null && procSiaSih.getSiaSih() != null && procSiaSih.getCompetenciaInicial() != null) {
                            java.util.Optional<com.upsaude.entity.referencia.sigtap.SigtapProcedimentoSiaSih> existing = 
                                    procedimentoSiaSihRepository.findByProcedimentoIdAndSiaSihIdAndCompetenciaInicial(
                                            procSiaSih.getProcedimento().getId(), 
                                            procSiaSih.getSiaSih().getId(),
                                            procSiaSih.getCompetenciaInicial());
                            if (existing.isEmpty()) {
                                toSave.add(procSiaSih);
                            }
                        } else {
                            toSave.add(procSiaSih);
                        }
                    }
                    if (!toSave.isEmpty()) {
                        return procedimentoSiaSihRepository.saveAll(toSave);
                    }
                    return new ArrayList<>();
                });
    }

    @Transactional
    private int importarProcedimentosRegraCondicionada(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoRegraCondicionada(fields),
                procedimentoRegraCondicionadaRepository::saveAll);
    }

    @Transactional
    private int importarProcedimentosRenases(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoRenases(fields),
                entities -> {
                    List<com.upsaude.entity.referencia.sigtap.SigtapProcedimentoRenases> toSave = new ArrayList<>();
                    for (com.upsaude.entity.referencia.sigtap.SigtapProcedimentoRenases procRenases : entities) {
                        if (procRenases.getProcedimento() != null && procRenases.getRenases() != null) {
                            java.util.Optional<com.upsaude.entity.referencia.sigtap.SigtapProcedimentoRenases> existing = 
                                    procedimentoRenasesRepository.findByProcedimentoIdAndRenasesId(
                                            procRenases.getProcedimento().getId(), 
                                            procRenases.getRenases().getId());
                            if (existing.isEmpty()) {
                                toSave.add(procRenases);
                            }
                        } else {
                            toSave.add(procRenases);
                        }
                    }
                    if (!toSave.isEmpty()) {
                        return procedimentoRenasesRepository.saveAll(toSave);
                    }
                    return new ArrayList<>();
                });
    }

    @Transactional
    private int importarProcedimentosTuss(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoTuss(fields),
                procedimentoTussRepository::saveAll);
    }

    @Transactional
    private int importarProcedimentosModalidade(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoModalidade(fields, context.competencia),
                procedimentoModalidadeRepository::saveAll);
    }

    @Transactional
    private int importarProcedimentosRegistro(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoRegistro(fields, context.competencia),
                procedimentoRegistroRepository::saveAll);
    }

    @Transactional
    private int importarProcedimentosDetalheItem(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToProcedimentoDetalheItem(fields),
                procedimentoDetalheItemRepository::saveAll);
    }

    @Transactional
    private int importarExcecoesCompatibilidade(ImportContext context) {
        return importarComBatch(context, fields -> entityMapper.mapToExcecaoCompatibilidade(fields, context.competencia),
                excecaoCompatibilidadeRepository::saveAll);
    }

    // ========== M?TODO GEN?RICO DE IMPORTA??O COM BATCH ==========
    // 
    // IMPORTANTE: Este método NÃO tem @Transactional porque é chamado por métodos
    // que já têm @Transactional. Isso evita problemas com transações aninhadas
    // e fechamento do contexto Spring em caso de erro.

    private <T> int importarComBatch(ImportContext context, 
                                     Function<Map<String, String>, T> mapper,
                                     Function<List<T>, List<T>> saver) {
        List<T> batch = new ArrayList<>();
        int totalLinhas = 0;
        int linhasComErro = 0;
        int linhasLidas = 0;
        long inicioTempo = System.currentTimeMillis();
        List<String> errosDetalhados = new ArrayList<>();
        
        // Contadores de erros por tipo
        int errosEntidadeNaoEncontrada = 0;
        int errosValidacao = 0;
        int errosEntidadeNula = 0;
        int errosOutros = 0;

        try (BufferedReader reader = Files.newBufferedReader(context.arquivoPath, ISO_8859_1)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhasLidas++;
                
                if (linhasLidas % 1000 == 0) {
                    log.info("Processando arquivo {}: {} linhas lidas, {} processadas, {} erros", 
                            context.arquivoPath.getFileName(), linhasLidas, totalLinhas, linhasComErro);
                }

                try {
                    if (linha.trim().isEmpty()) {
                        continue;
                    }

                    Map<String, String> fields = fileParser.parseLine(linha, context.layout);
                    
                    if (!validarCamposBasicos(fields, linhasLidas, errosDetalhados)) {
                        linhasComErro++;
                        errosValidacao++;
                        continue;
                    }

                    T entity;
                    try {
                        entity = mapper.apply(fields);
                    } catch (IllegalStateException e) {
                        if (e.getMessage() != null && e.getMessage().contains("closed")) {
                            linhasComErro++;
                            String erroMsg = String.format("Linha %d: ERRO CRÍTICO - Contexto Spring fechado durante mapeamento: %s", linhasLidas, e.getMessage());
                            errosDetalhados.add(erroMsg);
                            // #region agent log
                            try {
                                java.nio.file.Files.write(
                                    java.nio.file.Paths.get("/Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code/UPSaude-back/.cursor/debug.log"),
                                    (String.format("{\"sessionId\":\"debug-session\",\"runId\":\"import-start\",\"hypothesisId\":\"L\",\"location\":\"SigtapFileImportServiceImpl.java:683\",\"message\":\"IllegalStateException durante mapeamento\",\"data\":{\"arquivo\":\"%s\",\"linha\":%d,\"erro\":\"%s\",\"timestamp\":%d},\"timestamp\":%d}%n", 
                                        context.arquivoPath.getFileName().toString(), linhasLidas, e.getMessage().replace("\"", "'"), System.currentTimeMillis(), System.currentTimeMillis())).getBytes(),
                                    java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
                            } catch (Exception ex) {}
                            // #endregion
                            log.error("ERRO CRÍTICO ao mapear linha {} do arquivo {}: Contexto Spring fechado. Parando processamento deste arquivo.", 
                                    linhasLidas, context.arquivoPath.getFileName(), e);
                            // Parar processamento deste arquivo para evitar mais erros
                            break;
                        }
                        // Se não for erro de contexto fechado, relançar
                        throw e;
                    } catch (org.springframework.boot.context.properties.ConfigurationPropertiesBindException e) {
                        // Erro crítico: Spring tentando recriar bean quando contexto está fechado durante mapeamento
                        linhasComErro++;
                        String erroMsg = String.format("Linha %d: ERRO CRÍTICO - Spring tentando recriar bean quando contexto fechado durante mapeamento: %s", linhasLidas, e.getMessage());
                        errosDetalhados.add(erroMsg);
                        log.error("ERRO CRÍTICO ao mapear linha {} do arquivo {}: Spring tentando recriar bean quando contexto fechado. Parando processamento deste arquivo.", 
                                linhasLidas, context.arquivoPath.getFileName(), e);
                        break;
                    }
                    
                    if (entity == null) {
                        linhasComErro++;
                        errosEntidadeNula++;
                        String erroMsg = String.format("Linha %d: Entidade nula após mapeamento (entidade relacionada não encontrada)", linhasLidas);
                        errosDetalhados.add(erroMsg);
                        log.warn("Entidade nula na linha {} do arquivo {}: provavelmente entidade relacionada não encontrada", 
                                linhasLidas, context.arquivoPath.getFileName());
                        continue;
                    }

                    batch.add(entity);

                    if (batch.size() >= batchSize) {
                        try {
                            saver.apply(batch);
                            totalLinhas += batch.size();
                            batch.clear();
                        } catch (IllegalStateException e) {
                            if (e.getMessage() != null && e.getMessage().contains("closed")) {
                                log.error("ERRO CRÍTICO ao salvar batch no arquivo {}: Contexto Spring fechado. Parando processamento deste arquivo.", 
                                        context.arquivoPath.getFileName(), e);
                                linhasComErro += batch.size();
                                batch.clear();
                                break;
                            }
                            log.error("Erro ao salvar batch no arquivo {} (linhas {}-{}): {}", 
                                    context.arquivoPath.getFileName(), 
                                    linhasLidas - batchSize + 1, 
                                    linhasLidas, 
                                    e.getMessage(), e);
                            linhasComErro += batch.size();
                            batch.clear();
                        } catch (org.springframework.boot.context.properties.ConfigurationPropertiesBindException e) {
                            log.error("ERRO CRÍTICO ao salvar batch no arquivo {}: Spring tentando recriar bean quando contexto fechado. Parando processamento deste arquivo.", 
                                    context.arquivoPath.getFileName(), e);
                            linhasComErro += batch.size();
                            batch.clear();
                            break;
                        } catch (Exception e) {
                            log.error("Erro ao salvar batch no arquivo {} (linhas {}-{}): {}", 
                                    context.arquivoPath.getFileName(), 
                                    linhasLidas - batchSize + 1, 
                                    linhasLidas, 
                                    e.getMessage(), e);
                            linhasComErro += batch.size();
                            batch.clear();
                        }
                    }
                } catch (IllegalArgumentException e) {
                    linhasComErro++;
                    String erroMsg = String.format("Linha %d: %s", linhasLidas, e.getMessage());
                    errosDetalhados.add(erroMsg);
                    
                    // Classificar o tipo de erro baseado na mensagem
                    String mensagemErro = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
                    if (mensagemErro.contains("não encontrado") || mensagemErro.contains("nao encontrado")) {
                        errosEntidadeNaoEncontrada++;
                        log.warn("Entidade não encontrada na linha {} do arquivo {}: {}", 
                                linhasLidas, context.arquivoPath.getFileName(), e.getMessage());
                    } else {
                        errosValidacao++;
                        log.warn("Erro de validação na linha {} do arquivo {}: {}", 
                                linhasLidas, context.arquivoPath.getFileName(), e.getMessage());
                    }
                    
                    if (errosDetalhados.size() > 100) {
                        errosDetalhados.remove(0);
                    }
                } catch (IllegalStateException e) {
                    linhasComErro++;
                    String erroMsg = String.format("Linha %d: ERRO CRÍTICO - Contexto Spring fechado ou problema de estado: %s", linhasLidas, e.getMessage());
                    errosDetalhados.add(erroMsg);
                    log.error("ERRO CRÍTICO ao processar linha {} do arquivo {}: Contexto Spring pode estar fechado. Parando processamento deste arquivo.", 
                            linhasLidas, context.arquivoPath.getFileName(), e);
                    break;
                } catch (org.springframework.boot.context.properties.ConfigurationPropertiesBindException e) {
                    linhasComErro++;
                    String erroMsg = String.format("Linha %d: ERRO CRÍTICO - Spring tentando recriar bean quando contexto fechado: %s", linhasLidas, e.getMessage());
                    errosDetalhados.add(erroMsg);
                    log.error("ERRO CRÍTICO ao processar linha {} do arquivo {}: Spring tentando recriar bean quando contexto fechado. Parando processamento deste arquivo.", 
                            linhasLidas, context.arquivoPath.getFileName(), e);
                    break;
                } catch (Exception e) {
                    linhasComErro++;
                    errosOutros++;
                    String erroMsg = String.format("Linha %d: %s", linhasLidas, e.getMessage());
                    errosDetalhados.add(erroMsg);
                    log.warn("Erro ao processar linha {} do arquivo {}: {} (tipo: {})", 
                            linhasLidas, context.arquivoPath.getFileName(), e.getMessage(), e.getClass().getName());
                    if (e.getCause() != null) {
                        log.warn("Causa do erro: {} - {}", e.getCause().getClass().getName(), e.getCause().getMessage());
                    }
                    
                    if (e instanceof IllegalStateException && e.getMessage() != null && e.getMessage().contains("closed")) {
                        log.error("ERRO CRÍTICO: Contexto Spring fechado detectado. Parando processamento deste arquivo.");
                        break;
                    }
                    
                    if (linhasComErro % 100 == 0) {
                        log.warn("Total de {} erros até agora no arquivo {} (último erro: {})", 
                                linhasComErro, context.arquivoPath.getFileName(), e.getMessage());
                    }
                    
                    if (errosDetalhados.size() > 100) {
                        errosDetalhados.remove(0);
                    }
                }
            }

            if (!batch.isEmpty()) {
                try {
                    saver.apply(batch);
                    totalLinhas += batch.size();
                } catch (Exception e) {
                    log.error("Erro ao salvar batch final no arquivo {}: {}", 
                            context.arquivoPath.getFileName(), e.getMessage(), e);
                    linhasComErro += batch.size();
                }
            }
        } catch (IOException e) {
            log.error("Erro ao ler arquivo {}: {}", context.arquivoPath, e.getMessage(), e);
            throw new RuntimeException("Erro ao ler arquivo: " + context.arquivoPath, e);
        }

        long tempoDecorrido = System.currentTimeMillis() - inicioTempo;
        
        if (linhasComErro > 0) {
            log.warn("Arquivo {} concluído: {} linhas processadas, {} erros, {} linhas lidas, tempo: {}ms", 
                    context.arquivoPath.getFileName(), totalLinhas, linhasComErro, linhasLidas, tempoDecorrido);
            
            // Estatísticas de erros por tipo
            log.warn("Estatísticas de erros por tipo:");
            log.warn("  - Entidade não encontrada: {}", errosEntidadeNaoEncontrada);
            log.warn("  - Entidade nula (relacionamento não encontrado): {}", errosEntidadeNula);
            log.warn("  - Erros de validação: {}", errosValidacao);
            log.warn("  - Outros erros: {}", errosOutros);
            
            // Log dos primeiros 10 erros detalhados
            int errosParaLogar = Math.min(10, errosDetalhados.size());
            for (int i = 0; i < errosParaLogar; i++) {
                log.warn("  Erro {}: {}", i + 1, errosDetalhados.get(i));
            }
            if (errosDetalhados.size() > 10) {
                log.warn("  ... e mais {} erros (total: {})", 
                        errosDetalhados.size() - 10, errosDetalhados.size());
            }
        } else {
            log.info("Arquivo {} concluído com sucesso: {} linhas processadas, {} linhas lidas, tempo: {}ms", 
                    context.arquivoPath.getFileName(), totalLinhas, linhasLidas, tempoDecorrido);
        }

        return totalLinhas;
    }

    private boolean validarCamposBasicos(Map<String, String> fields, int numeroLinha, List<String> errosDetalhados) {
        boolean temCampos = fields.values().stream().anyMatch(v -> v != null && !v.trim().isEmpty());
        
        if (!temCampos) {
            errosDetalhados.add(String.format("Linha %d: Linha vazia ou sem campos v?lidos", numeroLinha));
            return false;
        }
        
        return true;
    }

    // ========== CLASSES AUXILIARES ==========

    private static class ImportContext {
        final Path arquivoPath;
        final SigtapLayoutDefinition layout;
        final String competencia;

        ImportContext(Path arquivoPath, SigtapLayoutDefinition layout, String competencia) {
            this.arquivoPath = arquivoPath;
            this.layout = layout;
            this.competencia = competencia;
        }
    }

    public static class ImportResult {
        private final String competencia;
        private int totalLinhasProcessadas = 0;
        private int totalErros = 0;
        private final java.util.Map<String, Integer> linhasPorArquivo = new java.util.HashMap<>();
        private final java.util.List<String> erros = new java.util.ArrayList<>();

        public ImportResult(String competencia) {
            this.competencia = competencia;
        }

        public void addLinhasProcessadas(String arquivo, int linhas) {
            linhasPorArquivo.put(arquivo, linhas);
            totalLinhasProcessadas += linhas;
        }

        public void addErro(String erro) {
            erros.add(erro);
            totalErros++;
        }

        public String getCompetencia() { return competencia; }
        public int getTotalLinhasProcessadas() { return totalLinhasProcessadas; }
        public int getTotalErros() { return totalErros; }
        public java.util.Map<String, Integer> getLinhasPorArquivo() { return linhasPorArquivo; }
        public java.util.List<String> getErros() { return erros; }
    }
}
