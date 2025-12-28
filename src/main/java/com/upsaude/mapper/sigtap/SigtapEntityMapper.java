package com.upsaude.mapper.sigtap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.entity.referencia.sigtap.SigtapComponenteRede;
import com.upsaude.entity.referencia.sigtap.SigtapDescricao;
import com.upsaude.entity.referencia.sigtap.SigtapDescricaoDetalhe;
import com.upsaude.entity.referencia.sigtap.SigtapDetalhe;
import com.upsaude.entity.referencia.sigtap.SigtapExcecaoCompatibilidade;
import com.upsaude.entity.referencia.sigtap.SigtapFinanciamento;
import com.upsaude.entity.referencia.sigtap.SigtapFormaOrganizacao;
import com.upsaude.entity.referencia.sigtap.SigtapGrupo;
import com.upsaude.entity.referencia.sigtap.SigtapGrupoHabilitacao;
import com.upsaude.entity.referencia.sigtap.SigtapHabilitacao;
import com.upsaude.entity.referencia.sigtap.SigtapModalidade;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoCid;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoComponenteRede;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoDetalheItem;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoHabilitacao;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoIncremento;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoLeito;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoModalidade;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoOcupacao;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoOrigem;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoRegistro;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoRegraCondicionada;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoRenases;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoServico;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoSiaSih;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoTuss;
import com.upsaude.entity.referencia.sigtap.SigtapRedeAtencao;
import com.upsaude.entity.referencia.sigtap.SigtapRegistro;
import com.upsaude.entity.referencia.sigtap.SigtapRegraCondicionada;
import com.upsaude.entity.referencia.sigtap.SigtapRenases;
import com.upsaude.entity.referencia.sigtap.SigtapRubrica;
import com.upsaude.entity.referencia.sigtap.SigtapServico;
import com.upsaude.entity.referencia.sigtap.SigtapServicoClassificacao;
import com.upsaude.entity.referencia.sigtap.SigtapSiaSih;
import com.upsaude.entity.referencia.sigtap.SigtapSubgrupo;
import com.upsaude.entity.referencia.sigtap.SigtapTipoLeito;
import com.upsaude.entity.referencia.sigtap.SigtapTuss;
import com.upsaude.importacao.sigtap.file.SigtapFileParser;
import com.upsaude.repository.referencia.cid.Cid10SubcategoriasRepository;
import com.upsaude.repository.referencia.sigtap.SigtapComponenteRedeRepository;
import com.upsaude.repository.referencia.sigtap.SigtapDetalheRepository;
import com.upsaude.repository.referencia.sigtap.SigtapFinanciamentoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapGrupoHabilitacaoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapGrupoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapHabilitacaoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapModalidadeRepository;
import com.upsaude.repository.referencia.sigtap.SigtapCboRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRepository;
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
@Component
public class SigtapEntityMapper {
    private static final int IDADE_NAO_APLICA = 9999;

    private final SigtapFileParser parser;
    private final SigtapGrupoRepository grupoRepository;
    private final SigtapSubgrupoRepository subgrupoRepository;
    private final SigtapProcedimentoRepository procedimentoRepository;
    private final Cid10SubcategoriasRepository cid10SubcategoriasRepository;
    private final SigtapCboRepository cboRepository;
    private final SigtapHabilitacaoRepository habilitacaoRepository;
    private final SigtapGrupoHabilitacaoRepository grupoHabilitacaoRepository;
    private final SigtapTipoLeitoRepository tipoLeitoRepository;
    private final SigtapServicoRepository servicoRepository;
    private final SigtapServicoClassificacaoRepository servicoClassificacaoRepository;
    private final SigtapRegraCondicionadaRepository regraCondicionadaRepository;
    private final SigtapRenasesRepository renasesRepository;
    private final SigtapTussRepository tussRepository;
    private final SigtapComponenteRedeRepository componenteRedeRepository;
    private final SigtapSiaSihRepository siaSihRepository;
    private final SigtapModalidadeRepository modalidadeRepository;
    private final SigtapRegistroRepository registroRepository;
    private final SigtapDetalheRepository detalheRepository;
    private final SigtapFinanciamentoRepository financiamentoRepository;
    private final SigtapRubricaRepository rubricaRepository;

    public SigtapEntityMapper(
            SigtapFileParser parser,
            SigtapGrupoRepository grupoRepository,
            SigtapSubgrupoRepository subgrupoRepository,
            SigtapProcedimentoRepository procedimentoRepository,
            Cid10SubcategoriasRepository cid10SubcategoriasRepository,
            SigtapCboRepository cboRepository,
            SigtapHabilitacaoRepository habilitacaoRepository,
            SigtapGrupoHabilitacaoRepository grupoHabilitacaoRepository,
            SigtapTipoLeitoRepository tipoLeitoRepository,
            SigtapServicoRepository servicoRepository,
            SigtapServicoClassificacaoRepository servicoClassificacaoRepository,
            SigtapRegraCondicionadaRepository regraCondicionadaRepository,
            SigtapRenasesRepository renasesRepository,
            SigtapTussRepository tussRepository,
            SigtapComponenteRedeRepository componenteRedeRepository,
            SigtapSiaSihRepository siaSihRepository,
            SigtapModalidadeRepository modalidadeRepository,
            SigtapRegistroRepository registroRepository,
            SigtapDetalheRepository detalheRepository,
            SigtapFinanciamentoRepository financiamentoRepository,
            SigtapRubricaRepository rubricaRepository) {
        this.parser = parser;
        this.grupoRepository = grupoRepository;
        this.subgrupoRepository = subgrupoRepository;
        this.procedimentoRepository = procedimentoRepository;
        this.cid10SubcategoriasRepository = cid10SubcategoriasRepository;
        this.cboRepository = cboRepository;
        this.habilitacaoRepository = habilitacaoRepository;
        this.grupoHabilitacaoRepository = grupoHabilitacaoRepository;
        this.tipoLeitoRepository = tipoLeitoRepository;
        this.servicoRepository = servicoRepository;
        this.servicoClassificacaoRepository = servicoClassificacaoRepository;
        this.regraCondicionadaRepository = regraCondicionadaRepository;
        this.renasesRepository = renasesRepository;
        this.tussRepository = tussRepository;
        this.componenteRedeRepository = componenteRedeRepository;
        this.siaSihRepository = siaSihRepository;
        this.modalidadeRepository = modalidadeRepository;
        this.registroRepository = registroRepository;
        this.detalheRepository = detalheRepository;
        this.financiamentoRepository = financiamentoRepository;
        this.rubricaRepository = rubricaRepository;
    }

    // ========== M?TODOS AUXILIARES ==========

    private Integer parseIdade(String valor) {
        Integer idade = parser.parseInteger(valor);
        if (idade != null && idade == IDADE_NAO_APLICA) {
            return null;
        }
        return idade;
    }

    // ========== CACHES (reduz lookup por linha) ==========
    /**
     * IMPORTANTE: este mapper é singleton. Caches sem limite podem crescer indefinidamente em jobs grandes.
     * Estratégia mínima: impor limite e limpar quando exceder (evita consumo de heap não controlado).
     */
    @Value("${sigtap.import.mapper-cache-max-size:20000}")
    private int mapperCacheMaxSize;

    private final ConcurrentMap<String, SigtapGrupo> grupoCache = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, SigtapSubgrupo> subgrupoCache = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, SigtapProcedimento> procedimentoCache = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Cid10Subcategorias> cid10SubcatCache = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, SigtapOcupacao> ocupacaoCache = new ConcurrentHashMap<>();

    private void enforceCacheLimit() {
        int limit = mapperCacheMaxSize;
        if (limit <= 0) return;
        if (grupoCache.size() > limit) grupoCache.clear();
        if (subgrupoCache.size() > limit) subgrupoCache.clear();
        if (procedimentoCache.size() > limit) procedimentoCache.clear();
        if (cid10SubcatCache.size() > limit) cid10SubcatCache.clear();
        if (ocupacaoCache.size() > limit) ocupacaoCache.clear();
    }

    private SigtapGrupo getGrupo(String codigoGrupo) {
        if (codigoGrupo == null || codigoGrupo.isBlank()) {
            throw new IllegalArgumentException("CO_GRUPO inválido");
        }
        enforceCacheLimit();
        return grupoCache.computeIfAbsent(codigoGrupo, k ->
                grupoRepository.findByCodigoOficial(k)
                        .orElseThrow(() -> new IllegalArgumentException("Grupo não encontrado: " + k))
        );
    }

    private SigtapSubgrupo getSubgrupo(String codigoGrupo, String codigoSubgrupo) {
        String key = (codigoGrupo == null ? "" : codigoGrupo) + "|" + (codigoSubgrupo == null ? "" : codigoSubgrupo);
        enforceCacheLimit();
        return subgrupoCache.computeIfAbsent(key, k ->
                subgrupoRepository.findByGrupoCodigoOficialAndCodigoOficial(codigoGrupo, codigoSubgrupo)
                        .orElseThrow(() -> new IllegalArgumentException("Subgrupo não encontrado: " + codigoGrupo + "/" + codigoSubgrupo))
        );
    }

    private SigtapProcedimento getProcedimento(String codigoProcedimento) {
        if (codigoProcedimento == null || codigoProcedimento.isBlank()) {
            throw new IllegalArgumentException("CO_PROCEDIMENTO inválido");
        }
        enforceCacheLimit();
        return procedimentoCache.computeIfAbsent(codigoProcedimento, k ->
                procedimentoRepository.findByCodigoOficial(k)
                        .orElseThrow(() -> new IllegalArgumentException("Procedimento não encontrado: " + k))
        );
    }

    private Cid10Subcategorias getCid10Subcat(String codigoCid) {
        if (codigoCid == null || codigoCid.isBlank()) {
            throw new IllegalArgumentException("CO_CID inválido");
        }
        enforceCacheLimit();
        return cid10SubcatCache.computeIfAbsent(codigoCid, k ->
                cid10SubcategoriasRepository.findBySubcat(k)
                        .orElseThrow(() -> new IllegalArgumentException("CID-10 Subcategoria não encontrada: " + k))
        );
    }

    private SigtapOcupacao getOcupacao(String codigoOcupacao) {
        if (codigoOcupacao == null || codigoOcupacao.isBlank()) {
            throw new IllegalArgumentException("CO_OCUPACAO inválido");
        }
        enforceCacheLimit();
        return ocupacaoCache.computeIfAbsent(codigoOcupacao, k ->
                cboRepository.findByCodigoOficial(k)
                        .orElseThrow(() -> new IllegalArgumentException("Ocupa??o n?o encontrada: " + k))
        );
    }

    // ========== MAPEAMENTOS DE TABELAS PRINCIPAIS ==========

    public SigtapGrupo mapToGrupo(Map<String, String> fields, String competencia) {
        SigtapGrupo grupo = new SigtapGrupo();
        grupo.setCodigoOficial(fields.get("CO_GRUPO"));
        grupo.setNome(limparString(fields.get("NO_GRUPO")));
        grupo.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return grupo;
    }

    public SigtapSubgrupo mapToSubgrupo(Map<String, String> fields, String competencia) {
        String codigoGrupo = fields.get("CO_GRUPO");
        SigtapGrupo grupo = getGrupo(codigoGrupo);

        SigtapSubgrupo subgrupo = new SigtapSubgrupo();
        subgrupo.setGrupo(grupo);
        subgrupo.setCodigoOficial(fields.get("CO_SUB_GRUPO"));
        subgrupo.setNome(limparString(fields.get("NO_SUB_GRUPO")));
        subgrupo.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return subgrupo;
    }

    public SigtapFormaOrganizacao mapToFormaOrganizacao(Map<String, String> fields, String competencia) {
        String codigoGrupo = fields.get("CO_GRUPO");
        String codigoSubgrupo = fields.get("CO_SUB_GRUPO");
        SigtapSubgrupo subgrupo = getSubgrupo(codigoGrupo, codigoSubgrupo);

        SigtapFormaOrganizacao forma = new SigtapFormaOrganizacao();
        forma.setSubgrupo(subgrupo);
        forma.setCodigoOficial(fields.get("CO_FORMA_ORGANIZACAO"));
        forma.setNome(limparString(fields.get("NO_FORMA_ORGANIZACAO")));
        forma.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return forma;
    }

    public SigtapProcedimento mapToProcedimento(Map<String, String> fields, String competencia) {
        SigtapProcedimento procedimento = new SigtapProcedimento();
        procedimento.setCodigoOficial(fields.get("CO_PROCEDIMENTO"));
        procedimento.setNome(limparString(fields.get("NO_PROCEDIMENTO")));
        procedimento.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));

        // Campos de procedimento
        procedimento.setTipoComplexidade(fields.get("TP_COMPLEXIDADE"));
        procedimento.setSexoPermitido(fields.get("TP_SEXO"));
        procedimento.setIdadeMinima(parseIdade(fields.get("VL_IDADE_MINIMA")));
        procedimento.setIdadeMaxima(parseIdade(fields.get("VL_IDADE_MAXIMA")));
        procedimento.setMediaDiasInternacao(parser.parseInteger(fields.get("QT_DIAS_PERMANENCIA")));
        procedimento.setQuantidadeMaximaDias(parser.parseInteger(fields.get("QT_TEMPO_PERMANENCIA")));
        procedimento.setLimiteMaximo(parser.parseInteger(fields.get("QT_MAXIMA_EXECUCAO")));
        procedimento.setPontos(parser.parseInteger(fields.get("QT_PONTOS")));

        // Buscar e mapear financiamento
        String codigoFinanciamento = fields.get("CO_FINANCIAMENTO");
        if (codigoFinanciamento != null && !codigoFinanciamento.isBlank()) {
            financiamentoRepository.findByCodigoOficialAndCompetenciaInicial(
                    codigoFinanciamento.trim(), competencia)
                    .ifPresent(procedimento::setFinanciamento);
        }

        // Buscar e mapear rubrica
        String codigoRubrica = fields.get("CO_RUBRICA");
        if (codigoRubrica != null && !codigoRubrica.isBlank()) {
            rubricaRepository.findByCodigoOficialAndCompetenciaInicial(
                    codigoRubrica.trim(), competencia)
                    .ifPresent(procedimento::setRubrica);
        }

        // Valores monet?rios (assumindo que est?o em centavos, dividir por 100)
        procedimento.setValorServicoHospitalar(parser.parseBigDecimal(fields.get("VL_SH"), true));
        procedimento.setValorServicoAmbulatorial(parser.parseBigDecimal(fields.get("VL_SA"), true));
        procedimento.setValorServicoProfissional(parser.parseBigDecimal(fields.get("VL_SP"), true));

        // Nota: O arquivo tb_procedimento.txt não contém CO_GRUPO, CO_SUB_GRUPO, CO_FORMA_ORGANIZACAO
        // A relação com forma de organização é determinada pela estrutura hierárquica do código do procedimento
        // A forma de organização é estabelecida através dos relacionamentos nas tabelas de subgrupo e forma_organizacao

        return procedimento;
    }

    // ========== MAPEAMENTOS DE TABELAS DE REFER?NCIA ==========


    public SigtapOcupacao mapToOcupacao(Map<String, String> fields) {
        SigtapOcupacao ocupacao = new SigtapOcupacao();
        ocupacao.setCodigoOficial(fields.get("CO_OCUPACAO"));
        ocupacao.setNome(limparString(fields.get("NO_OCUPACAO")));
        return ocupacao;
    }

    public SigtapFinanciamento mapToFinanciamento(Map<String, String> fields, String competencia) {
        SigtapFinanciamento financiamento = new SigtapFinanciamento();
        financiamento.setCodigoOficial(fields.get("CO_FINANCIAMENTO"));
        financiamento.setNome(limparString(fields.get("NO_FINANCIAMENTO")));
        financiamento.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return financiamento;
    }

    public SigtapRubrica mapToRubrica(Map<String, String> fields, String competencia) {
        SigtapRubrica rubrica = new SigtapRubrica();
        rubrica.setCodigoOficial(fields.get("CO_RUBRICA"));
        rubrica.setNome(limparString(fields.get("NO_RUBRICA")));
        rubrica.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return rubrica;
    }

    public SigtapModalidade mapToModalidade(Map<String, String> fields, String competencia) {
        SigtapModalidade modalidade = new SigtapModalidade();
        modalidade.setCodigoOficial(fields.get("CO_MODALIDADE"));
        modalidade.setNome(limparString(fields.get("NO_MODALIDADE")));
        modalidade.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return modalidade;
    }

    public SigtapRegistro mapToRegistro(Map<String, String> fields, String competencia) {
        SigtapRegistro registro = new SigtapRegistro();
        registro.setCodigoOficial(fields.get("CO_REGISTRO"));
        registro.setNome(limparString(fields.get("NO_REGISTRO")));
        registro.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return registro;
    }

    public SigtapTipoLeito mapToTipoLeito(Map<String, String> fields) {
        SigtapTipoLeito tipoLeito = new SigtapTipoLeito();
        tipoLeito.setCodigoOficial(fields.get("CO_TIPO_LEITO"));
        tipoLeito.setNome(limparString(fields.get("NO_TIPO_LEITO")));
        return tipoLeito;
    }

    public SigtapServico mapToServico(Map<String, String> fields) {
        SigtapServico servico = new SigtapServico();
        servico.setCodigoOficial(fields.get("CO_SERVICO"));
        servico.setNome(limparString(fields.get("NO_SERVICO")));
        return servico;
    }

    public SigtapServicoClassificacao mapToServicoClassificacao(Map<String, String> fields, String competencia) {
        String codigoServico = fields.get("CO_SERVICO");
        SigtapServico servico = servicoRepository.findByCodigoOficial(codigoServico)
                .orElseThrow(() -> new IllegalArgumentException("Servi?o n?o encontrado: " + codigoServico));

        SigtapServicoClassificacao classificacao = new SigtapServicoClassificacao();
        classificacao.setServico(servico);
        classificacao.setCodigoClassificacao(fields.get("CO_CLASSIFICACAO"));
        classificacao.setNome(limparString(fields.get("NO_CLASSIFICACAO")));
        classificacao.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return classificacao;
    }

    public SigtapHabilitacao mapToHabilitacao(Map<String, String> fields, String competencia) {
        SigtapHabilitacao habilitacao = new SigtapHabilitacao();
        habilitacao.setCodigoOficial(fields.get("CO_HABILITACAO"));
        habilitacao.setNome(limparString(fields.get("NO_HABILITACAO")));
        habilitacao.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return habilitacao;
    }

    public SigtapGrupoHabilitacao mapToGrupoHabilitacao(Map<String, String> fields) {
        SigtapGrupoHabilitacao grupo = new SigtapGrupoHabilitacao();
        grupo.setCodigoOficial(fields.get("NU_GRUPO_HABILITACAO"));
        grupo.setNome(limparString(fields.get("NO_GRUPO_HABILITACAO")));
        grupo.setDescricao(limparString(fields.get("DS_GRUPO_HABILITACAO")));
        return grupo;
    }

    public SigtapRegraCondicionada mapToRegraCondicionada(Map<String, String> fields) {
        SigtapRegraCondicionada regra = new SigtapRegraCondicionada();
        regra.setCodigoOficial(fields.get("CO_REGRA_CONDICIONADA"));
        regra.setNome(limparString(fields.get("NO_REGRA_CONDICIONADA")));
        return regra;
    }

    public SigtapRenases mapToRenases(Map<String, String> fields) {
        SigtapRenases renases = new SigtapRenases();
        renases.setCodigoOficial(fields.get("CO_RENASES"));
        renases.setNome(limparString(fields.get("NO_RENASES")));
        return renases;
    }

    public SigtapTuss mapToTuss(Map<String, String> fields) {
        SigtapTuss tuss = new SigtapTuss();
        tuss.setCodigoOficial(fields.get("CO_TUSS"));
        tuss.setNome(limparString(fields.get("NO_TUSS")));
        return tuss;
    }

    public SigtapComponenteRede mapToComponenteRede(Map<String, String> fields) {
        SigtapComponenteRede componente = new SigtapComponenteRede();
        componente.setCodigoOficial(fields.get("CO_COMPONENTE_REDE"));
        componente.setNome(limparString(fields.get("NO_COMPONENTE_REDE")));
        // Nota: O campo CO_REDE_ATENCAO existe no arquivo mas não é armazenado diretamente na entidade
        // O relacionamento entre componente e rede é feito através de outras tabelas relacionais
        return componente;
    }

    public SigtapRedeAtencao mapToRedeAtencao(Map<String, String> fields) {
        SigtapRedeAtencao rede = new SigtapRedeAtencao();
        rede.setCodigoOficial(fields.get("CO_REDE_ATENCAO"));
        rede.setNome(limparString(fields.get("NO_REDE_ATENCAO")));
        return rede;
    }

    public SigtapSiaSih mapToSiaSih(Map<String, String> fields) {
        SigtapSiaSih siaSih = new SigtapSiaSih();
        siaSih.setCodigoOficial(fields.get("CO_PROCEDIMENTO_SIA_SIH"));
        siaSih.setNome(limparString(fields.get("NO_PROCEDIMENTO_SIA_SIH")));
        return siaSih;
    }

    public SigtapDetalhe mapToDetalhe(Map<String, String> fields, String competencia) {
        SigtapDetalhe detalhe = new SigtapDetalhe();
        detalhe.setCodigoOficial(fields.get("CO_DETALHE"));
        detalhe.setNome(limparString(fields.get("NO_DETALHE")));
        detalhe.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return detalhe;
    }

    public SigtapDescricao mapToDescricao(Map<String, String> fields, String competencia) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        SigtapDescricao descricao = new SigtapDescricao();
        if (codigoProcedimento != null) {
            java.util.Optional<SigtapProcedimento> optProcedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento);
            if (optProcedimento.isPresent()) {
                descricao.setProcedimento(optProcedimento.get());
            }
        }
        descricao.setDescricaoCompleta(limparString(fields.get("DS_PROCEDIMENTO")));
        descricao.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return descricao;
    }

    public SigtapDescricaoDetalhe mapToDescricaoDetalhe(Map<String, String> fields, String competencia) {
        SigtapDescricaoDetalhe descricaoDetalhe = new SigtapDescricaoDetalhe();
        String codigoDetalhe = fields.get("CO_DETALHE");
        SigtapDetalhe detalhe = detalheRepository.findByCodigoOficialAndCompetenciaInicial(
                codigoDetalhe, competencia)
                .orElseThrow(() -> new IllegalArgumentException("Detalhe n?o encontrado: " + codigoDetalhe));
        descricaoDetalhe.setDetalhe(detalhe);
        descricaoDetalhe.setDescricaoCompleta(limparString(fields.get("DS_DETALHE")));
        descricaoDetalhe.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return descricaoDetalhe;
    }

    // ========== MAPEAMENTOS DE TABELAS RELACIONAIS ==========

    public SigtapProcedimentoCid mapToProcedimentoCid(Map<String, String> fields, String competencia) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoCid = fields.get("CO_CID");
        SigtapProcedimento procedimento = getProcedimento(codigoProcedimento);
        Cid10Subcategorias cid10Subcategoria = getCid10Subcat(codigoCid);

        SigtapProcedimentoCid procCid = new SigtapProcedimentoCid();
        procCid.setProcedimento(procedimento);
        procCid.setCid10Subcategoria(cid10Subcategoria);
        procCid.setPrincipal(parser.parseBoolean(fields.get("ST_PRINCIPAL")));
        procCid.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return procCid;
    }

    public SigtapProcedimentoOcupacao mapToProcedimentoOcupacao(Map<String, String> fields, String competencia) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoOcupacao = fields.get("CO_OCUPACAO");
        SigtapProcedimento procedimento = getProcedimento(codigoProcedimento);
        SigtapOcupacao ocupacao = getOcupacao(codigoOcupacao);

        SigtapProcedimentoOcupacao procOcup = new SigtapProcedimentoOcupacao();
        procOcup.setProcedimento(procedimento);
        procOcup.setOcupacao(ocupacao);
        procOcup.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return procOcup;
    }

    public SigtapProcedimentoHabilitacao mapToProcedimentoHabilitacao(Map<String, String> fields, String competencia) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoHabilitacao = fields.get("CO_HABILITACAO");
        SigtapProcedimento procedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento)
                .orElseThrow(() -> new IllegalArgumentException("Procedimento n?o encontrado: " + codigoProcedimento));
        SigtapHabilitacao habilitacao = habilitacaoRepository.findByCodigoOficialAndCompetenciaInicial(
                codigoHabilitacao, competencia)
                .orElseThrow(() -> new IllegalArgumentException("Habilita??o n?o encontrada: " + codigoHabilitacao));

        SigtapProcedimentoHabilitacao procHab = new SigtapProcedimentoHabilitacao();
        procHab.setProcedimento(procedimento);
        procHab.setHabilitacao(habilitacao);
        String codigoGrupoHab = fields.get("NU_GRUPO_HABILITACAO");
        if (codigoGrupoHab != null) {
            grupoHabilitacaoRepository.findByCodigoOficial(codigoGrupoHab)
                    .ifPresent(procHab::setGrupoHabilitacao);
        }
        procHab.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return procHab;
    }

    public SigtapProcedimentoLeito mapToProcedimentoLeito(Map<String, String> fields, String competencia) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoTipoLeito = fields.get("CO_TIPO_LEITO");
        SigtapProcedimento procedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento)
                .orElseThrow(() -> new IllegalArgumentException("Procedimento n?o encontrado: " + codigoProcedimento));
        SigtapTipoLeito tipoLeito = tipoLeitoRepository.findByCodigoOficial(codigoTipoLeito)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de leito n?o encontrado: " + codigoTipoLeito));

        SigtapProcedimentoLeito procLeito = new SigtapProcedimentoLeito();
        procLeito.setProcedimento(procedimento);
        procLeito.setTipoLeito(tipoLeito);
        procLeito.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return procLeito;
    }

    public SigtapProcedimentoServico mapToProcedimentoServico(Map<String, String> fields, String competencia) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoServico = fields.get("CO_SERVICO");
        String codigoClassificacao = fields.get("CO_CLASSIFICACAO");
        SigtapProcedimento procedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento)
                .orElseThrow(() -> new IllegalArgumentException("Procedimento n?o encontrado: " + codigoProcedimento));
        SigtapServico servico = servicoRepository.findByCodigoOficial(codigoServico)
                .orElseThrow(() -> new IllegalArgumentException("Servi?o n?o encontrado: " + codigoServico));
        SigtapServicoClassificacao servicoClass = servicoClassificacaoRepository
                .findByServicoAndCodigoClassificacaoAndCompetenciaInicial(servico, codigoClassificacao, competencia)
                .orElseThrow(() -> new IllegalArgumentException("Servi?o Classifica??o n?o encontrado: " + codigoServico + "/" + codigoClassificacao));

        SigtapProcedimentoServico procServ = new SigtapProcedimentoServico();
        procServ.setProcedimento(procedimento);
        procServ.setServicoClassificacao(servicoClass);
        procServ.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return procServ;
    }

    public SigtapProcedimentoIncremento mapToProcedimentoIncremento(Map<String, String> fields, String competencia) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoHabilitacao = fields.get("CO_HABILITACAO");
        SigtapProcedimento procedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento)
                .orElseThrow(() -> new IllegalArgumentException("Procedimento n?o encontrado: " + codigoProcedimento));
        SigtapHabilitacao habilitacao = habilitacaoRepository.findByCodigoOficialAndCompetenciaInicial(
                codigoHabilitacao, competencia)
                .orElseThrow(() -> new IllegalArgumentException("Habilita??o n?o encontrada: " + codigoHabilitacao));

        SigtapProcedimentoIncremento procInc = new SigtapProcedimentoIncremento();
        procInc.setProcedimento(procedimento);
        procInc.setHabilitacao(habilitacao);
        procInc.setValorPercentualSh(parser.parseBigDecimal(fields.get("VL_PERCENTUAL_SH"), true));
        procInc.setValorPercentualSa(parser.parseBigDecimal(fields.get("VL_PERCENTUAL_SA"), true));
        procInc.setValorPercentualSp(parser.parseBigDecimal(fields.get("VL_PERCENTUAL_SP"), true));
        procInc.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return procInc;
    }

    public SigtapProcedimentoComponenteRede mapToProcedimentoComponenteRede(Map<String, String> fields) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoComponente = fields.get("CO_COMPONENTE_REDE");
        SigtapProcedimento procedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento)
                .orElseThrow(() -> new IllegalArgumentException("Procedimento n?o encontrado: " + codigoProcedimento));
        SigtapComponenteRede componente = componenteRedeRepository.findByCodigoOficial(codigoComponente)
                .orElseThrow(() -> new IllegalArgumentException("Componente de rede n?o encontrado: " + codigoComponente));

        SigtapProcedimentoComponenteRede procComp = new SigtapProcedimentoComponenteRede();
        procComp.setProcedimento(procedimento);
        procComp.setComponenteRede(componente);
        return procComp;
    }

    public SigtapProcedimentoOrigem mapToProcedimentoOrigem(Map<String, String> fields, String competencia) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoProcedimentoOrigem = fields.get("CO_PROCEDIMENTO_ORIGEM");
        
        java.util.Optional<SigtapProcedimento> optProcedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento);
        if (optProcedimento.isEmpty()) {
            log.warn("Procedimento não encontrado ao mapear Origem: {} (Origem: {})", codigoProcedimento, codigoProcedimentoOrigem);
            return null;
        }
        
        java.util.Optional<SigtapProcedimento> optProcedimentoOrigem = procedimentoRepository.findByCodigoOficial(codigoProcedimentoOrigem);
        if (optProcedimentoOrigem.isEmpty()) {
            log.warn("Procedimento origem não encontrado ao mapear relacionamento: {} (Procedimento: {})", codigoProcedimentoOrigem, codigoProcedimento);
            return null;
        }

        SigtapProcedimentoOrigem procOrigem = new SigtapProcedimentoOrigem();
        procOrigem.setProcedimento(optProcedimento.get());
        procOrigem.setProcedimentoOrigem(optProcedimentoOrigem.get());
        procOrigem.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return procOrigem;
    }

    public SigtapProcedimentoSiaSih mapToProcedimentoSiaSih(Map<String, String> fields, String competencia) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoSiaSih = fields.get("CO_PROCEDIMENTO_SIA_SIH");
        
        java.util.Optional<SigtapProcedimento> optProcedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento);
        if (optProcedimento.isEmpty()) {
            log.warn("Procedimento não encontrado ao mapear SIA/SIH: {} (SIA/SIH: {})", codigoProcedimento, codigoSiaSih);
            return null;
        }
        
        java.util.Optional<SigtapSiaSih> optSiaSih = siaSihRepository.findByCodigoOficial(codigoSiaSih);
        if (optSiaSih.isEmpty()) {
            log.warn("SIA/SIH não encontrado ao mapear relacionamento: {} (Procedimento: {})", codigoSiaSih, codigoProcedimento);
            return null;
        }

        SigtapProcedimentoSiaSih procSiaSih = new SigtapProcedimentoSiaSih();
        procSiaSih.setProcedimento(optProcedimento.get());
        procSiaSih.setSiaSih(optSiaSih.get());
        procSiaSih.setTipoProcedimento(fields.get("TP_PROCEDIMENTO"));
        procSiaSih.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return procSiaSih;
    }

    public SigtapProcedimentoRegraCondicionada mapToProcedimentoRegraCondicionada(Map<String, String> fields) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoRegra = fields.get("CO_REGRA_CONDICIONADA");
        
        java.util.Optional<SigtapProcedimento> optProcedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento);
        if (optProcedimento.isEmpty()) {
            log.warn("Procedimento não encontrado ao mapear Regra Condicionada: {} (Regra: {})", codigoProcedimento, codigoRegra);
            return null;
        }
        
        java.util.Optional<SigtapRegraCondicionada> optRegra = regraCondicionadaRepository.findByCodigoOficial(codigoRegra);
        if (optRegra.isEmpty()) {
            log.warn("Regra condicionada não encontrada ao mapear relacionamento: {} (Procedimento: {})", codigoRegra, codigoProcedimento);
            return null;
        }

        SigtapProcedimentoRegraCondicionada procRegra = new SigtapProcedimentoRegraCondicionada();
        procRegra.setProcedimento(optProcedimento.get());
        procRegra.setRegraCondicionada(optRegra.get());
        return procRegra;
    }

    public SigtapProcedimentoRenases mapToProcedimentoRenases(Map<String, String> fields) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoRenases = fields.get("CO_RENASES");
        
        java.util.Optional<SigtapProcedimento> optProcedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento);
        if (optProcedimento.isEmpty()) {
            log.warn("Procedimento não encontrado ao mapear RENASES: {} (RENASES: {})", codigoProcedimento, codigoRenases);
            return null;
        }
        
        java.util.Optional<SigtapRenases> optRenases = renasesRepository.findByCodigoOficial(codigoRenases);
        if (optRenases.isEmpty()) {
            log.warn("RENASES não encontrado ao mapear relacionamento: {} (Procedimento: {})", codigoRenases, codigoProcedimento);
            return null;
        }

        SigtapProcedimentoRenases procRenases = new SigtapProcedimentoRenases();
        procRenases.setProcedimento(optProcedimento.get());
        procRenases.setRenases(optRenases.get());
        return procRenases;
    }

    public SigtapProcedimentoTuss mapToProcedimentoTuss(Map<String, String> fields) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoTuss = fields.get("CO_TUSS");
        
        java.util.Optional<SigtapProcedimento> optProcedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento);
        if (optProcedimento.isEmpty()) {
            log.warn("Procedimento não encontrado ao mapear TUSS: {} (TUSS: {})", codigoProcedimento, codigoTuss);
            return null;
        }
        
        java.util.Optional<SigtapTuss> optTuss = tussRepository.findByCodigoOficial(codigoTuss);
        if (optTuss.isEmpty()) {
            log.warn("TUSS não encontrado ao mapear relacionamento: {} (Procedimento: {})", codigoTuss, codigoProcedimento);
            return null;
        }

        SigtapProcedimentoTuss procTuss = new SigtapProcedimentoTuss();
        procTuss.setProcedimento(optProcedimento.get());
        procTuss.setTuss(optTuss.get());
        return procTuss;
    }

    public SigtapProcedimentoModalidade mapToProcedimentoModalidade(Map<String, String> fields, String competencia) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoModalidade = fields.get("CO_MODALIDADE");
        SigtapProcedimento procedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento)
                .orElseThrow(() -> new IllegalArgumentException("Procedimento n?o encontrado: " + codigoProcedimento));
        SigtapModalidade modalidade = modalidadeRepository.findByCodigoOficialAndCompetenciaInicial(
                codigoModalidade, competencia)
                .orElseThrow(() -> new IllegalArgumentException("Modalidade n?o encontrada: " + codigoModalidade));

        SigtapProcedimentoModalidade procModal = new SigtapProcedimentoModalidade();
        procModal.setProcedimento(procedimento);
        procModal.setModalidade(modalidade);
        procModal.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return procModal;
    }

    public SigtapProcedimentoRegistro mapToProcedimentoRegistro(Map<String, String> fields, String competencia) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoRegistro = fields.get("CO_REGISTRO");
        SigtapProcedimento procedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento)
                .orElseThrow(() -> new IllegalArgumentException("Procedimento n?o encontrado: " + codigoProcedimento));
        SigtapRegistro registro = registroRepository.findByCodigoOficialAndCompetenciaInicial(
                codigoRegistro, competencia)
                .orElseThrow(() -> new IllegalArgumentException("Registro n?o encontrado: " + codigoRegistro));

        SigtapProcedimentoRegistro procReg = new SigtapProcedimentoRegistro();
        procReg.setProcedimento(procedimento);
        procReg.setRegistro(registro);
        procReg.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return procReg;
    }

    public SigtapProcedimentoDetalheItem mapToProcedimentoDetalheItem(Map<String, String> fields) {
        String codigoProcedimento = fields.get("CO_PROCEDIMENTO");
        String codigoDetalhe = fields.get("CO_DETALHE");
        SigtapProcedimento procedimento = procedimentoRepository.findByCodigoOficial(codigoProcedimento)
                .orElseThrow(() -> new IllegalArgumentException("Procedimento n?o encontrado: " + codigoProcedimento));
        SigtapDetalhe detalhe = detalheRepository.findByCodigoOficialAndCompetenciaInicial(
                codigoDetalhe, fields.get("DT_COMPETENCIA"))
                .orElseThrow(() -> new IllegalArgumentException("Detalhe n?o encontrado: " + codigoDetalhe));

        SigtapProcedimentoDetalheItem procDet = new SigtapProcedimentoDetalheItem();
        procDet.setProcedimento(procedimento);
        procDet.setDetalhe(detalhe);
        return procDet;
    }

    public SigtapExcecaoCompatibilidade mapToExcecaoCompatibilidade(Map<String, String> fields, String competencia) {
        String codigoRestricao = fields.get("CO_PROCEDIMENTO_RESTRICAO");
        String codigoPrincipal = fields.get("CO_PROCEDIMENTO_PRINCIPAL");
        String codigoCompativel = fields.get("CO_PROCEDIMENTO_COMPATIVEL");
        SigtapProcedimento restricao = procedimentoRepository.findByCodigoOficial(codigoRestricao)
                .orElseThrow(() -> new IllegalArgumentException("Procedimento restri??o n?o encontrado: " + codigoRestricao));
        SigtapProcedimento principal = procedimentoRepository.findByCodigoOficial(codigoPrincipal)
                .orElseThrow(() -> new IllegalArgumentException("Procedimento principal n?o encontrado: " + codigoPrincipal));
        SigtapProcedimento compativel = procedimentoRepository.findByCodigoOficial(codigoCompativel)
                .orElseThrow(() -> new IllegalArgumentException("Procedimento compat?vel n?o encontrado: " + codigoCompativel));

        SigtapExcecaoCompatibilidade excecao = new SigtapExcecaoCompatibilidade();
        excecao.setProcedimentoRestricao(restricao);
        excecao.setProcedimentoPrincipal(principal);
        excecao.setProcedimentoCompativel(compativel);
        String codigoRegistroPrincipal = fields.get("CO_REGISTRO_PRINCIPAL");
        if (codigoRegistroPrincipal != null) {
            registroRepository.findByCodigoOficialAndCompetenciaInicial(codigoRegistroPrincipal, competencia)
                    .ifPresent(excecao::setRegistroPrincipal);
        }
        String codigoRegistroCompativel = fields.get("CO_REGISTRO_COMPATIVEL");
        if (codigoRegistroCompativel != null) {
            registroRepository.findByCodigoOficialAndCompetenciaInicial(codigoRegistroCompativel, competencia)
                    .ifPresent(excecao::setRegistroCompativel);
        }
        excecao.setTipoCompatibilidade(fields.get("TP_COMPATIBILIDADE"));
        excecao.setCompetenciaInicial(fields.getOrDefault("DT_COMPETENCIA", competencia));
        return excecao;
    }

    // ========== M?TODOS AUXILIARES ==========

    private String limparString(String valor) {
        if (valor == null) {
            return null;
        }
        String limpo = valor.trim();
        return limpo.isEmpty() ? null : limpo;
    }
}
