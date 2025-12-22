package com.upsaude.service.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.referencia.sigtap.*;
import com.upsaude.entity.referencia.sigtap.*;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.sigtap.*;
import com.upsaude.repository.referencia.sigtap.*;
import com.upsaude.service.referencia.sigtap.SigtapConsultaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SigtapConsultaServiceImpl implements SigtapConsultaService {

    private final SigtapGrupoRepository grupoRepository;
    private final SigtapProcedimentoRepository procedimentoRepository;
    private final SigtapProcedimentoDetalheRepository procedimentoDetalheRepository;
    private final SigtapCompatibilidadeRepository compatibilidadeRepository;
    private final SigtapServicoRepository servicoRepository;
    private final SigtapRenasesRepository renasesRepository;
    private final SigtapSubgrupoRepository subgrupoRepository;
    private final SigtapFormaOrganizacaoRepository formaOrganizacaoRepository;
    private final SigtapHabilitacaoRepository habilitacaoRepository;
    private final SigtapTussRepository tussRepository;
    private final SigtapOcupacaoRepository ocupacaoRepository;
    private final SigtapModalidadeRepository modalidadeRepository;

    private final SigtapGrupoMapper grupoMapper;
    private final SigtapProcedimentoMapper procedimentoMapper;
    private final SigtapProcedimentoDetalheMapper procedimentoDetalheMapper;
    private final SigtapCompatibilidadeMapper compatibilidadeMapper;
    private final SigtapServicoMapper servicoMapper;
    private final SigtapRenasesMapper renasesMapper;
    private final SigtapSubgrupoMapper subgrupoMapper;
    private final SigtapFormaOrganizacaoMapper formaOrganizacaoMapper;
    private final SigtapHabilitacaoMapper habilitacaoMapper;
    private final SigtapTussMapper tussMapper;
    private final SigtapOcupacaoMapper ocupacaoMapper;
    private final SigtapModalidadeMapper modalidadeMapper;

    @Override
    public List<SigtapGrupoResponse> listarGrupos() {
        return grupoRepository.findAll(Sort.by(Sort.Direction.ASC, "codigoOficial"))
                .stream()
                .map(grupoMapper::toResponse)
                .toList();
    }

    @Override
    public Page<SigtapProcedimentoResponse> pesquisarProcedimentos(String q, String grupoCodigo, String subgrupoCodigo, String competencia, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<SigtapProcedimento> spec = Specification.where(null);

        // Filtrar por ativo (todos os procedimentos devem estar ativos)
        spec = spec.and((root, query, cb) -> cb.equal(root.get("active"), true));

        // Filtrar por grupo através do código oficial (primeiros 2 dígitos)
        // Exemplo: grupo 06 = procedimentos que começam com "06"
        if (grupoCodigo != null && !grupoCodigo.isBlank()) {
            String grupoCod = grupoCodigo.trim();
            // Garantir que o código do grupo tenha 2 dígitos
            if (grupoCod.length() == 1) {
                grupoCod = "0" + grupoCod;
            }
            
            if (subgrupoCodigo != null && !subgrupoCodigo.isBlank()) {
                // Filtrar por grupo + subgrupo (primeiros 4 dígitos: 2 do grupo + 2 do subgrupo)
                String subgrupoCod = subgrupoCodigo.trim();
                if (subgrupoCod.length() == 1) {
                    subgrupoCod = "0" + subgrupoCod;
                }
                final String prefixo = grupoCod + subgrupoCod;
                spec = spec.and((root, query, cb) -> 
                    cb.like(root.get("codigoOficial"), prefixo + "%")
                );
            } else {
                // Filtrar apenas por grupo (primeiros 2 dígitos)
                final String prefixo = grupoCod;
                spec = spec.and((root, query, cb) -> 
                    cb.like(root.get("codigoOficial"), prefixo + "%")
                );
            }
        }

        if (q != null && !q.isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("codigoOficial")), like),
                    cb.like(cb.lower(root.get("nome")), like)
            ));
        }

        if (competencia != null && !competencia.isBlank()) {
            String comp = competencia.replaceAll("[^0-9]", "");
            if (comp.matches("\\d{6}")) {
                spec = spec.and((root, query, cb) -> cb.and(
                        cb.lessThanOrEqualTo(root.get("competenciaInicial"), comp),
                        cb.or(
                                cb.isNull(root.get("competenciaFinal")),
                                cb.greaterThanOrEqualTo(root.get("competenciaFinal"), comp)
                        )
                ));
            }
        }

        Page<SigtapProcedimento> page = procedimentoRepository.findAll(spec, pageable);
        return page.map(procedimentoMapper::toResponse);
    }

    @Override
    public SigtapProcedimentoDetalhadoResponse obterProcedimentoDetalhado(String codigoProcedimento, String competencia) {
        if (codigoProcedimento == null || codigoProcedimento.isBlank()) {
            throw new NotFoundException("Procedimento SIGTAP n?o encontrado");
        }

        Specification<SigtapProcedimento> spec = Specification.where((root, query, cb) ->
                cb.equal(root.get("codigoOficial"), codigoProcedimento.trim())
        );

        if (competencia != null && !competencia.isBlank()) {
            String comp = competencia.replaceAll("[^0-9]", "");
            if (comp.matches("\\d{6}")) {
                spec = spec.and((root, query, cb) -> cb.and(
                        cb.lessThanOrEqualTo(root.get("competenciaInicial"), comp),
                        cb.or(
                                cb.isNull(root.get("competenciaFinal")),
                                cb.greaterThanOrEqualTo(root.get("competenciaFinal"), comp)
                        )
                ));
            }
        }

        Page<SigtapProcedimento> page = procedimentoRepository.findAll(
                spec,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "competenciaInicial"))
        );
        SigtapProcedimento procedimento = page.getContent().stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Procedimento SIGTAP n?o encontrado: " + codigoProcedimento));

        SigtapProcedimentoDetalhadoResponse resp = new SigtapProcedimentoDetalhadoResponse();
        resp.setProcedimento(procedimentoMapper.toResponse(procedimento));
        procedimentoDetalheRepository.findByProcedimentoId(procedimento.getId())
                .ifPresent(det -> resp.setDetalhe(procedimentoDetalheMapper.toResponse(det)));
        return resp;
    }

    @Override
    public Page<SigtapCompatibilidadeResponse> pesquisarCompatibilidades(String codigoProcedimentoPrincipal, String competencia, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<SigtapCompatibilidade> spec = Specification.where(null);

        if (codigoProcedimentoPrincipal != null && !codigoProcedimentoPrincipal.isBlank()) {
            String cod = codigoProcedimentoPrincipal.trim();
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.join("procedimentoPrincipal").get("codigoOficial"), cod)
            );
        }

        if (competencia != null && !competencia.isBlank()) {
            String comp = competencia.replaceAll("[^0-9]", "");
            if (comp.matches("\\d{6}")) {
                spec = spec.and((root, query, cb) -> cb.and(
                        cb.lessThanOrEqualTo(root.get("competenciaInicial"), comp),
                        cb.or(
                                cb.isNull(root.get("competenciaFinal")),
                                cb.greaterThanOrEqualTo(root.get("competenciaFinal"), comp)
                        )
                ));
            }
        }

        Page<SigtapCompatibilidade> page = compatibilidadeRepository.findAll(spec, pageable);
        return page.map(compatibilidadeMapper::toResponse);
    }

    @Override
    public Page<SigtapServicoResponse> pesquisarServicos(String q, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<SigtapServico> spec = Specification.where(null);

        if (q != null && !q.isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("codigoOficial")), like),
                    cb.like(cb.lower(root.get("nome")), like)
            ));
        }

        Page<SigtapServico> page = servicoRepository.findAll(spec, pageable);
        return page.map(servicoMapper::toResponse);
    }

    @Override
    public SigtapServicoResponse obterServicoPorCodigo(String codigoOficial) {
        if (codigoOficial == null || codigoOficial.isBlank()) {
            throw new NotFoundException("Serviço SIGTAP não encontrado");
        }
        SigtapServico servico = servicoRepository.findByCodigoOficial(codigoOficial.trim())
                .orElseThrow(() -> new NotFoundException("Serviço SIGTAP não encontrado: " + codigoOficial));
        return servicoMapper.toResponse(servico);
    }

    @Override
    public Page<SigtapRenasesResponse> pesquisarRenases(String q, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<SigtapRenases> spec = Specification.where(null);

        if (q != null && !q.isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("codigoOficial")), like),
                    cb.like(cb.lower(root.get("nome")), like)
            ));
        }

        Page<SigtapRenases> page = renasesRepository.findAll(spec, pageable);
        return page.map(renasesMapper::toResponse);
    }

    @Override
    public SigtapRenasesResponse obterRenasesPorCodigo(String codigoOficial) {
        if (codigoOficial == null || codigoOficial.isBlank()) {
            throw new NotFoundException("RENASES SIGTAP não encontrado");
        }
        SigtapRenases renases = renasesRepository.findByCodigoOficial(codigoOficial.trim())
                .orElseThrow(() -> new NotFoundException("RENASES SIGTAP não encontrado: " + codigoOficial));
        return renasesMapper.toResponse(renases);
    }

    @Override
    public Page<SigtapSubgrupoResponse> pesquisarSubgrupos(String q, String grupoCodigo, String subgrupoCodigo, String competencia, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        
        // Se temos grupoCodigo, usar método customizado com JOIN FETCH para evitar LazyInitializationException
        if (grupoCodigo != null && !grupoCodigo.isBlank()) {
            List<SigtapSubgrupo> all;
            
            // Se temos subgrupoCodigo também, filtrar por grupo + subgrupo
            if (subgrupoCodigo != null && !subgrupoCodigo.isBlank()) {
                if (q != null && !q.isBlank()) {
                    // Buscar com filtro grupo + subgrupo + q
                    all = subgrupoRepository.findByGrupoCodigoOficialAndSubgrupoCodigoWithQ(
                            grupoCodigo.trim(), 
                            subgrupoCodigo.trim(), 
                            q.trim().toLowerCase(Locale.ROOT));
                } else {
                    // Buscar apenas por grupo + subgrupo
                    all = subgrupoRepository.findByGrupoCodigoOficialAndSubgrupoCodigoWithGrupo(
                            grupoCodigo.trim(), 
                            subgrupoCodigo.trim());
                }
            } else {
                // Apenas grupoCodigo - buscar todos os subgrupos do grupo
                if (q != null && !q.isBlank()) {
                    // Buscar com filtro q
                    all = subgrupoRepository.findByGrupoCodigoOficialWithQ(grupoCodigo.trim(), q.trim().toLowerCase(Locale.ROOT));
                } else {
                    // Buscar todos do grupo
                    all = subgrupoRepository.findByGrupoCodigoOficialWithGrupo(grupoCodigo.trim());
                }
            }
            
            // Aplicar filtro de competência se necessário
            if (competencia != null && !competencia.isBlank()) {
                String comp = competencia.replaceAll("[^0-9]", "");
                if (comp.matches("\\d{6}")) {
                    all = all.stream()
                            .filter(s -> {
                                if (s.getCompetenciaInicial() == null) return false;
                                boolean inicioOk = s.getCompetenciaInicial().compareTo(comp) <= 0;
                                boolean fimOk = s.getCompetenciaFinal() == null || s.getCompetenciaFinal().compareTo(comp) >= 0;
                                return inicioOk && fimOk;
                            })
                            .toList();
                }
            }
            
            // Aplicar paginação manualmente
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), all.size());
            List<SigtapSubgrupo> pageContent = start < all.size() ? all.subList(start, end) : List.of();
            Page<SigtapSubgrupo> page = new PageImpl<>(pageContent, pageable, all.size());
            return page.map(subgrupoMapper::toResponse);
        }
        
        // Para outros casos, usar Specification normal (sem grupoCodigo)
        Specification<SigtapSubgrupo> spec = Specification.where(null);

        if (q != null && !q.isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("codigoOficial")), like),
                    cb.like(cb.lower(root.get("nome")), like)
            ));
        }

        if (competencia != null && !competencia.isBlank()) {
            String comp = competencia.replaceAll("[^0-9]", "");
            if (comp.matches("\\d{6}")) {
                spec = spec.and((root, query, cb) -> cb.and(
                        cb.lessThanOrEqualTo(root.get("competenciaInicial"), comp),
                        cb.or(
                                cb.isNull(root.get("competenciaFinal")),
                                cb.greaterThanOrEqualTo(root.get("competenciaFinal"), comp)
                        )
                ));
            }
        }

        Page<SigtapSubgrupo> page = subgrupoRepository.findAll(spec, pageable);
        return page.map(subgrupoMapper::toResponse);
    }

    @Override
    public SigtapSubgrupoResponse obterSubgrupoPorCodigo(String codigoOficial, String grupoCodigo) {
        if (codigoOficial == null || codigoOficial.isBlank()) {
            throw new NotFoundException("Subgrupo SIGTAP não encontrado");
        }
        SigtapSubgrupo subgrupo;
        if (grupoCodigo != null && !grupoCodigo.isBlank()) {
            subgrupo = subgrupoRepository.findByGrupoCodigoOficialAndCodigoOficial(grupoCodigo.trim(), codigoOficial.trim())
                    .orElseThrow(() -> new NotFoundException("Subgrupo SIGTAP não encontrado: " + grupoCodigo + "/" + codigoOficial));
        } else {
            Specification<SigtapSubgrupo> spec = Specification.where((root, query, cb) ->
                    cb.equal(root.get("codigoOficial"), codigoOficial.trim())
            );
            Page<SigtapSubgrupo> page = subgrupoRepository.findAll(spec, PageRequest.of(0, 1));
            subgrupo = page.getContent().stream().findFirst()
                    .orElseThrow(() -> new NotFoundException("Subgrupo SIGTAP não encontrado: " + codigoOficial));
        }
        return subgrupoMapper.toResponse(subgrupo);
    }

    @Override
    public Page<SigtapFormaOrganizacaoResponse> pesquisarFormasOrganizacao(String q, String subgrupoCodigo, String competencia, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<SigtapFormaOrganizacao> spec = Specification.where(null);

        if (q != null && !q.isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("codigoOficial")), like),
                    cb.like(cb.lower(root.get("nome")), like)
            ));
        }

        if (subgrupoCodigo != null && !subgrupoCodigo.isBlank()) {
            String cod = subgrupoCodigo.trim();
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.join("subgrupo").get("codigoOficial"), cod)
            );
        }

        if (competencia != null && !competencia.isBlank()) {
            String comp = competencia.replaceAll("[^0-9]", "");
            if (comp.matches("\\d{6}")) {
                spec = spec.and((root, query, cb) -> cb.and(
                        cb.lessThanOrEqualTo(root.get("competenciaInicial"), comp),
                        cb.or(
                                cb.isNull(root.get("competenciaFinal")),
                                cb.greaterThanOrEqualTo(root.get("competenciaFinal"), comp)
                        )
                ));
            }
        }

        Page<SigtapFormaOrganizacao> page = formaOrganizacaoRepository.findAll(spec, pageable);
        return page.map(formaOrganizacaoMapper::toResponse);
    }

    @Override
    public SigtapFormaOrganizacaoResponse obterFormaOrganizacaoPorCodigo(String codigoOficial, String subgrupoCodigo) {
        if (codigoOficial == null || codigoOficial.isBlank()) {
            throw new NotFoundException("Forma de Organização SIGTAP não encontrada");
        }
        SigtapFormaOrganizacao formaOrganizacao;
        if (subgrupoCodigo != null && !subgrupoCodigo.isBlank()) {
            formaOrganizacao = formaOrganizacaoRepository.findBySubgrupoCodigoOficialAndCodigoOficial(subgrupoCodigo.trim(), codigoOficial.trim())
                    .orElseThrow(() -> new NotFoundException("Forma de Organização SIGTAP não encontrada: " + subgrupoCodigo + "/" + codigoOficial));
        } else {
            Specification<SigtapFormaOrganizacao> spec = Specification.where((root, query, cb) ->
                    cb.equal(root.get("codigoOficial"), codigoOficial.trim())
            );
            Page<SigtapFormaOrganizacao> page = formaOrganizacaoRepository.findAll(spec, PageRequest.of(0, 1));
            formaOrganizacao = page.getContent().stream().findFirst()
                    .orElseThrow(() -> new NotFoundException("Forma de Organização SIGTAP não encontrada: " + codigoOficial));
        }
        return formaOrganizacaoMapper.toResponse(formaOrganizacao);
    }

    @Override
    public Page<SigtapHabilitacaoResponse> pesquisarHabilitacoes(String q, String competencia, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<SigtapHabilitacao> spec = Specification.where(null);

        if (q != null && !q.isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("codigoOficial")), like),
                    cb.like(cb.lower(root.get("nome")), like)
            ));
        }

        if (competencia != null && !competencia.isBlank()) {
            String comp = competencia.replaceAll("[^0-9]", "");
            if (comp.matches("\\d{6}")) {
                spec = spec.and((root, query, cb) -> cb.and(
                        cb.lessThanOrEqualTo(root.get("competenciaInicial"), comp),
                        cb.or(
                                cb.isNull(root.get("competenciaFinal")),
                                cb.greaterThanOrEqualTo(root.get("competenciaFinal"), comp)
                        )
                ));
            }
        }

        Page<SigtapHabilitacao> page = habilitacaoRepository.findAll(spec, pageable);
        return page.map(habilitacaoMapper::toResponse);
    }

    @Override
    public SigtapHabilitacaoResponse obterHabilitacaoPorCodigo(String codigoOficial, String competencia) {
        if (codigoOficial == null || codigoOficial.isBlank()) {
            throw new NotFoundException("Habilitação SIGTAP não encontrada");
        }
        SigtapHabilitacao habilitacao;
        if (competencia != null && !competencia.isBlank()) {
            String comp = competencia.replaceAll("[^0-9]", "");
            if (comp.matches("\\d{6}")) {
                habilitacao = habilitacaoRepository.findByCodigoOficialAndCompetenciaInicial(codigoOficial.trim(), comp)
                        .orElseThrow(() -> new NotFoundException("Habilitação SIGTAP não encontrada: " + codigoOficial + " para competência " + competencia));
            } else {
                habilitacao = habilitacaoRepository.findByCodigoOficial(codigoOficial.trim())
                        .orElseThrow(() -> new NotFoundException("Habilitação SIGTAP não encontrada: " + codigoOficial));
            }
        } else {
            habilitacao = habilitacaoRepository.findByCodigoOficial(codigoOficial.trim())
                    .orElseThrow(() -> new NotFoundException("Habilitação SIGTAP não encontrada: " + codigoOficial));
        }
        return habilitacaoMapper.toResponse(habilitacao);
    }

    @Override
    public Page<SigtapTussResponse> pesquisarTuss(String q, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<SigtapTuss> spec = Specification.where(null);

        if (q != null && !q.isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("codigoOficial")), like),
                    cb.like(cb.lower(root.get("nome")), like)
            ));
        }

        Page<SigtapTuss> page = tussRepository.findAll(spec, pageable);
        return page.map(tussMapper::toResponse);
    }

    @Override
    public SigtapTussResponse obterTussPorCodigo(String codigoOficial) {
        if (codigoOficial == null || codigoOficial.isBlank()) {
            throw new NotFoundException("TUSS SIGTAP não encontrado");
        }
        SigtapTuss tuss = tussRepository.findByCodigoOficial(codigoOficial.trim())
                .orElseThrow(() -> new NotFoundException("TUSS SIGTAP não encontrado: " + codigoOficial));
        return tussMapper.toResponse(tuss);
    }

    @Override
    public Page<SigtapOcupacaoResponse> pesquisarOcupacoes(String q, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<SigtapOcupacao> spec = Specification.where(null);

        if (q != null && !q.isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("codigoOficial")), like),
                    cb.like(cb.lower(root.get("nome")), like)
            ));
        }

        Page<SigtapOcupacao> page = ocupacaoRepository.findAll(spec, pageable);
        return page.map(ocupacaoMapper::toResponse);
    }

    @Override
    public SigtapOcupacaoResponse obterOcupacaoPorCodigo(String codigoOficial) {
        if (codigoOficial == null || codigoOficial.isBlank()) {
            throw new NotFoundException("Ocupação SIGTAP não encontrada");
        }
        SigtapOcupacao ocupacao = ocupacaoRepository.findByCodigoOficial(codigoOficial.trim())
                .orElseThrow(() -> new NotFoundException("Ocupação SIGTAP não encontrada: " + codigoOficial));
        return ocupacaoMapper.toResponse(ocupacao);
    }

    @Override
    public Page<SigtapModalidadeResponse> pesquisarModalidades(String q, String competencia, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<SigtapModalidade> spec = Specification.where(null);

        if (q != null && !q.isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("codigoOficial")), like),
                    cb.like(cb.lower(root.get("nome")), like)
            ));
        }

        if (competencia != null && !competencia.isBlank()) {
            String comp = competencia.replaceAll("[^0-9]", "");
            if (comp.matches("\\d{6}")) {
                spec = spec.and((root, query, cb) -> cb.and(
                        cb.lessThanOrEqualTo(root.get("competenciaInicial"), comp),
                        cb.or(
                                cb.isNull(root.get("competenciaFinal")),
                                cb.greaterThanOrEqualTo(root.get("competenciaFinal"), comp)
                        )
                ));
            }
        }

        Page<SigtapModalidade> page = modalidadeRepository.findAll(spec, pageable);
        return page.map(modalidadeMapper::toResponse);
    }

    @Override
    public SigtapModalidadeResponse obterModalidadePorCodigo(String codigoOficial, String competencia) {
        if (codigoOficial == null || codigoOficial.isBlank()) {
            throw new NotFoundException("Modalidade SIGTAP não encontrada");
        }
        SigtapModalidade modalidade;
        if (competencia != null && !competencia.isBlank()) {
            String comp = competencia.replaceAll("[^0-9]", "");
            if (comp.matches("\\d{6}")) {
                modalidade = modalidadeRepository.findByCodigoOficialAndCompetenciaInicial(codigoOficial.trim(), comp)
                        .orElseThrow(() -> new NotFoundException("Modalidade SIGTAP não encontrada: " + codigoOficial + " para competência " + competencia));
            } else {
                Specification<SigtapModalidade> spec = Specification.where((root, query, cb) ->
                        cb.equal(root.get("codigoOficial"), codigoOficial.trim())
                );
                Page<SigtapModalidade> page = modalidadeRepository.findAll(spec, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "competenciaInicial")));
                modalidade = page.getContent().stream().findFirst()
                        .orElseThrow(() -> new NotFoundException("Modalidade SIGTAP não encontrada: " + codigoOficial));
            }
        } else {
            Specification<SigtapModalidade> spec = Specification.where((root, query, cb) ->
                    cb.equal(root.get("codigoOficial"), codigoOficial.trim())
            );
            Page<SigtapModalidade> page = modalidadeRepository.findAll(spec, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "competenciaInicial")));
            modalidade = page.getContent().stream().findFirst()
                    .orElseThrow(() -> new NotFoundException("Modalidade SIGTAP não encontrada: " + codigoOficial));
        }
        return modalidadeMapper.toResponse(modalidade);
    }
}

