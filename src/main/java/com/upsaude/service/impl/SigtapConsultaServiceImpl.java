package com.upsaude.service.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.upsaude.dto.referencia.sigtap.SigtapCompatibilidadeResponse;
import com.upsaude.dto.referencia.sigtap.SigtapGrupoResponse;
import com.upsaude.dto.referencia.sigtap.SigtapProcedimentoDetalhadoResponse;
import com.upsaude.dto.referencia.sigtap.SigtapProcedimentoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapCompatibilidade;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.sigtap.SigtapCompatibilidadeMapper;
import com.upsaude.mapper.sigtap.SigtapGrupoMapper;
import com.upsaude.mapper.sigtap.SigtapProcedimentoDetalheMapper;
import com.upsaude.mapper.sigtap.SigtapProcedimentoMapper;
import com.upsaude.repository.referencia.sigtap.SigtapCompatibilidadeRepository;
import com.upsaude.repository.referencia.sigtap.SigtapGrupoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoDetalheRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRepository;
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

    private final SigtapGrupoMapper grupoMapper;
    private final SigtapProcedimentoMapper procedimentoMapper;
    private final SigtapProcedimentoDetalheMapper procedimentoDetalheMapper;
    private final SigtapCompatibilidadeMapper compatibilidadeMapper;

    @Override
    public List<SigtapGrupoResponse> listarGrupos() {
        return grupoRepository.findAll(Sort.by(Sort.Direction.ASC, "codigoOficial"))
                .stream()
                .map(grupoMapper::toResponse)
                .toList();
    }

    @Override
    public Page<SigtapProcedimentoResponse> pesquisarProcedimentos(String q, String competencia, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<SigtapProcedimento> spec = Specification.where(null);

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
}

