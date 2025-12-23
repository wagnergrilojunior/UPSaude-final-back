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
    public Page<SigtapProcedimentoResponse> pesquisarProcedimentos(String q, String grupoCodigo, String subgrupoCodigo, String formaOrganizacaoCodigo, String competencia, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<SigtapProcedimento> spec = Specification.where(null);

        // Filtrar por ativo (todos os procedimentos devem estar ativos)
        spec = spec.and((root, query, cb) -> cb.equal(root.get("active"), true));

        // Filtrar por hierarquia: grupo -> subgrupo -> forma de organização
        // O código oficial do procedimento SIGTAP tem a estrutura:
        // - Primeiros 2 dígitos: código do grupo
        // - Próximos 2 dígitos: código do subgrupo
        // - Próximos 2 dígitos: código da forma de organização
        // - Resto: código específico do procedimento
        if (grupoCodigo != null && !grupoCodigo.isBlank()) {
            String grupoCod = grupoCodigo.trim();
            // Garantir que o código do grupo tenha 2 dígitos
            if (grupoCod.length() == 1) {
                grupoCod = "0" + grupoCod;
            }
            
            if (subgrupoCodigo != null && !subgrupoCodigo.isBlank()) {
                // Filtrar por grupo + subgrupo
                String subgrupoCod = subgrupoCodigo.trim();
                if (subgrupoCod.length() == 1) {
                    subgrupoCod = "0" + subgrupoCod;
                }
                
                if (formaOrganizacaoCodigo != null && !formaOrganizacaoCodigo.isBlank()) {
                    // Filtrar por grupo + subgrupo + forma de organização (primeiros 6 dígitos)
                    String formaOrgCod = formaOrganizacaoCodigo.trim();
                    if (formaOrgCod.length() == 1) {
                        formaOrgCod = "0" + formaOrgCod;
                    }
                    final String prefixo = grupoCod + subgrupoCod + formaOrgCod;
                    spec = spec.and((root, query, cb) -> 
                        cb.like(root.get("codigoOficial"), prefixo + "%")
                    );
                } else {
                    // Filtrar por grupo + subgrupo (primeiros 4 dígitos)
                    final String prefixo = grupoCod + subgrupoCod;
                    spec = spec.and((root, query, cb) -> 
                        cb.like(root.get("codigoOficial"), prefixo + "%")
                    );
                }
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
        Page<SigtapProcedimentoResponse> responsePage = page.map(procedimentoMapper::toResponse);
        
        // Enriquecer responses com dados faltantes a partir do código oficial
        responsePage.getContent().forEach(response -> {
            enrichResponseFromCodigoOficial(response);
        });
        
        return responsePage;
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
    public Page<SigtapFormaOrganizacaoResponse> pesquisarFormasOrganizacao(String q, String grupoCodigo, String subgrupoCodigo, String competencia, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        
        // Usar métodos com JOIN FETCH quando temos filtros de grupo/subgrupo para evitar LazyInitializationException
        if (grupoCodigo != null && !grupoCodigo.isBlank() && 
            subgrupoCodigo != null && !subgrupoCodigo.isBlank()) {
            // Filtrar por grupo + subgrupo
            List<SigtapFormaOrganizacao> all = formaOrganizacaoRepository.findByGrupoCodigoAndSubgrupoCodigoWithRelationships(
                    grupoCodigo.trim(), subgrupoCodigo.trim());
            
            // Aplicar filtro de busca (q) se necessário
            if (q != null && !q.isBlank()) {
                String searchTerm = q.trim().toLowerCase(Locale.ROOT);
                all = all.stream()
                        .filter(fo -> (fo.getCodigoOficial() != null && fo.getCodigoOficial().toLowerCase(Locale.ROOT).contains(searchTerm)) ||
                                     (fo.getNome() != null && fo.getNome().toLowerCase(Locale.ROOT).contains(searchTerm)))
                        .toList();
            }
            
            // Aplicar filtro de competência se necessário
            if (competencia != null && !competencia.isBlank()) {
                String comp = competencia.replaceAll("[^0-9]", "");
                if (comp.matches("\\d{6}")) {
                    all = all.stream()
                            .filter(fo -> {
                                if (fo.getCompetenciaInicial() == null) return false;
                                boolean inicioOk = fo.getCompetenciaInicial().compareTo(comp) <= 0;
                                boolean fimOk = fo.getCompetenciaFinal() == null || fo.getCompetenciaFinal().compareTo(comp) >= 0;
                                return inicioOk && fimOk;
                            })
                            .toList();
                }
            }
            
            // Aplicar paginação manualmente
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), all.size());
            List<SigtapFormaOrganizacao> pageContent = start < all.size() ? all.subList(start, end) : List.of();
            Page<SigtapFormaOrganizacao> page = new PageImpl<>(pageContent, pageable, all.size());
            return page.map(formaOrganizacaoMapper::toResponse);
        } else if (subgrupoCodigo != null && !subgrupoCodigo.isBlank()) {
            // Filtrar apenas por subgrupo
            List<SigtapFormaOrganizacao> all = formaOrganizacaoRepository.findBySubgrupoCodigoWithRelationships(subgrupoCodigo.trim());
            
            // Aplicar filtro de busca (q) se necessário
            if (q != null && !q.isBlank()) {
                String searchTerm = q.trim().toLowerCase(Locale.ROOT);
                all = all.stream()
                        .filter(fo -> (fo.getCodigoOficial() != null && fo.getCodigoOficial().toLowerCase(Locale.ROOT).contains(searchTerm)) ||
                                     (fo.getNome() != null && fo.getNome().toLowerCase(Locale.ROOT).contains(searchTerm)))
                        .toList();
            }
            
            // Aplicar filtro de competência se necessário
            if (competencia != null && !competencia.isBlank()) {
                String comp = competencia.replaceAll("[^0-9]", "");
                if (comp.matches("\\d{6}")) {
                    all = all.stream()
                            .filter(fo -> {
                                if (fo.getCompetenciaInicial() == null) return false;
                                boolean inicioOk = fo.getCompetenciaInicial().compareTo(comp) <= 0;
                                boolean fimOk = fo.getCompetenciaFinal() == null || fo.getCompetenciaFinal().compareTo(comp) >= 0;
                                return inicioOk && fimOk;
                            })
                            .toList();
                }
            }
            
            // Aplicar paginação manualmente
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), all.size());
            List<SigtapFormaOrganizacao> pageContent = start < all.size() ? all.subList(start, end) : List.of();
            Page<SigtapFormaOrganizacao> page = new PageImpl<>(pageContent, pageable, all.size());
            return page.map(formaOrganizacaoMapper::toResponse);
        } else if (grupoCodigo != null && !grupoCodigo.isBlank()) {
            // Filtrar apenas por grupo
            List<SigtapFormaOrganizacao> all = formaOrganizacaoRepository.findByGrupoCodigoWithRelationships(grupoCodigo.trim());
            
            // Aplicar filtro de busca (q) se necessário
            if (q != null && !q.isBlank()) {
                String searchTerm = q.trim().toLowerCase(Locale.ROOT);
                all = all.stream()
                        .filter(fo -> (fo.getCodigoOficial() != null && fo.getCodigoOficial().toLowerCase(Locale.ROOT).contains(searchTerm)) ||
                                     (fo.getNome() != null && fo.getNome().toLowerCase(Locale.ROOT).contains(searchTerm)))
                        .toList();
            }
            
            // Aplicar filtro de competência se necessário
            if (competencia != null && !competencia.isBlank()) {
                String comp = competencia.replaceAll("[^0-9]", "");
                if (comp.matches("\\d{6}")) {
                    all = all.stream()
                            .filter(fo -> {
                                if (fo.getCompetenciaInicial() == null) return false;
                                boolean inicioOk = fo.getCompetenciaInicial().compareTo(comp) <= 0;
                                boolean fimOk = fo.getCompetenciaFinal() == null || fo.getCompetenciaFinal().compareTo(comp) >= 0;
                                return inicioOk && fimOk;
                            })
                            .toList();
                }
            }
            
            // Aplicar paginação manualmente
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), all.size());
            List<SigtapFormaOrganizacao> pageContent = start < all.size() ? all.subList(start, end) : List.of();
            Page<SigtapFormaOrganizacao> page = new PageImpl<>(pageContent, pageable, all.size());
            return page.map(formaOrganizacaoMapper::toResponse);
        }
        
        // Para outros casos, usar Specification normal (sem filtros de grupo/subgrupo)
        Specification<SigtapFormaOrganizacao> spec = Specification.where(null);

        // Filtrar por ativo
        spec = spec.and((root, query, cb) -> cb.equal(root.get("active"), true));

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

        Page<SigtapFormaOrganizacao> page = formaOrganizacaoRepository.findAll(spec, pageable);
        // Para casos sem filtros de relacionamento, precisamos carregar os relacionamentos manualmente
        // ou usar EntityGraph. Por enquanto, vamos tentar mapear e ver se funciona.
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

    /**
     * Enriquece o response com dados faltantes extraídos do código oficial.
     * O código oficial do procedimento SIGTAP tem a estrutura:
     * - Primeiros 2 dígitos: código do grupo
     * - Próximos 2 dígitos: código do subgrupo
     * - Próximos 2 dígitos: código da forma de organização
     * - Resto: código específico do procedimento
     */
    private void enrichResponseFromCodigoOficial(SigtapProcedimentoResponse response) {
        if (response == null || response.getCodigoOficial() == null || response.getCodigoOficial().length() < 4) {
            return;
        }

        String codigoOficial = response.getCodigoOficial();
        
        // Se já tem os dados, não precisa buscar
        if (response.getGrupoCodigo() != null && response.getSubgrupoCodigo() != null) {
            return;
        }

        // Extrair códigos do código oficial
        String grupoCodigo = codigoOficial.length() >= 2 ? codigoOficial.substring(0, 2) : null;
        String subgrupoCodigo = codigoOficial.length() >= 4 ? codigoOficial.substring(2, 4) : null;
        String formaOrganizacaoCodigo = codigoOficial.length() >= 6 ? codigoOficial.substring(4, 6) : null;

        // Buscar e preencher grupo se faltar
        if (grupoCodigo != null && (response.getGrupoCodigo() == null || response.getGrupoNome() == null)) {
            grupoRepository.findByCodigoOficial(grupoCodigo)
                    .ifPresent(grupo -> {
                        response.setGrupoCodigo(grupo.getCodigoOficial());
                        response.setGrupoNome(grupo.getNome());
                    });
        }

        // Buscar e preencher subgrupo se faltar (usando método com JOIN FETCH)
        if (grupoCodigo != null && subgrupoCodigo != null && 
            (response.getSubgrupoCodigo() == null || response.getSubgrupoNome() == null)) {
            List<SigtapSubgrupo> subgrupos = subgrupoRepository.findByGrupoCodigoOficialAndSubgrupoCodigoWithGrupo(grupoCodigo, subgrupoCodigo);
            if (!subgrupos.isEmpty()) {
                SigtapSubgrupo subgrupo = subgrupos.get(0);
                response.setSubgrupoCodigo(subgrupo.getCodigoOficial());
                response.setSubgrupoNome(subgrupo.getNome());
                // Garantir que grupo também está preenchido
                if (subgrupo.getGrupo() != null) {
                    response.setGrupoCodigo(subgrupo.getGrupo().getCodigoOficial());
                    response.setGrupoNome(subgrupo.getGrupo().getNome());
                }
            }
        }

        // Buscar e preencher forma de organização se faltar
        if (grupoCodigo != null && subgrupoCodigo != null && formaOrganizacaoCodigo != null &&
            (response.getFormaOrganizacaoCodigo() == null || response.getFormaOrganizacaoNome() == null)) {
            // Usar lista porque pode haver múltiplas competências, pegar a mais recente
            List<SigtapFormaOrganizacao> formasOrg = formaOrganizacaoRepository.findBySubgrupoCodigoOficialAndCodigoOficialIn(subgrupoCodigo, List.of(formaOrganizacaoCodigo));
            if (!formasOrg.isEmpty()) {
                // Ordenar por competência inicial (mais recente primeiro) e pegar a primeira
                SigtapFormaOrganizacao formaOrg = formasOrg.stream()
                        .sorted((a, b) -> {
                            String compA = a.getCompetenciaInicial() != null ? a.getCompetenciaInicial() : "";
                            String compB = b.getCompetenciaInicial() != null ? b.getCompetenciaInicial() : "";
                            return compB.compareTo(compA); // Ordem decrescente (mais recente primeiro)
                        })
                        .findFirst()
                        .orElse(formasOrg.get(0));
                
                response.setFormaOrganizacaoCodigo(formaOrg.getCodigoOficial());
                response.setFormaOrganizacaoNome(formaOrg.getNome());
                // Se subgrupo ainda não foi preenchido, buscar agora
                if (response.getSubgrupoCodigo() == null && formaOrg.getSubgrupo() != null) {
                    response.setSubgrupoCodigo(formaOrg.getSubgrupo().getCodigoOficial());
                    response.setSubgrupoNome(formaOrg.getSubgrupo().getNome());
                    // Se grupo ainda não foi preenchido, buscar agora
                    if (response.getGrupoCodigo() == null && formaOrg.getSubgrupo().getGrupo() != null) {
                        response.setGrupoCodigo(formaOrg.getSubgrupo().getGrupo().getCodigoOficial());
                        response.setGrupoNome(formaOrg.getSubgrupo().getGrupo().getNome());
                    }
                }
            }
        }
    }
}

