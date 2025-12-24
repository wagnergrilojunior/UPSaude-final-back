package com.upsaude.service.impl.api.referencia.cid;

import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.referencia.cid.*;
import com.upsaude.entity.referencia.cid.*;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.referencia.cid.*;
import com.upsaude.repository.referencia.cid.*;

import com.upsaude.service.api.referencia.cid.Cid10ConsultaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class Cid10ConsultaServiceImpl implements Cid10ConsultaService {

    private final Cid10CapitulosRepository capitulosRepository;
    private final Cid10CategoriasRepository categoriasRepository;
    private final Cid10SubcategoriasRepository subcategoriasRepository;
    private final Cid10GruposRepository gruposRepository;
    private final CidOGruposRepository gruposCidORepository;
    private final CidOCategoriasRepository categoriasCidORepository;

    private final Cid10CapituloMapper capituloMapper;
    private final Cid10CategoriaMapper categoriaMapper;
    private final Cid10SubcategoriaMapper subcategoriaMapper;
    private final Cid10GrupoMapper grupoMapper;
    private final CidOGrupoMapper grupoCidOMapper;
    private final CidOCategoriaMapper categoriaCidOMapper;

    @Override
    public List<Cid10CapituloResponse> listarCapitulos() {
        return capitulosRepository.findAll(Sort.by(Sort.Direction.ASC, "numcap"))
                .stream()
                .filter(c -> c.getActive() != null && c.getActive())
                .map(capituloMapper::toResponse)
                .toList();
    }

    @Override
    public Cid10CapituloResponse obterCapituloPorNumero(Integer numcap) {
        if (numcap == null) {
            throw new NotFoundException("Capítulo CID-10 não encontrado");
        }
        Cid10Capitulos capitulo = capitulosRepository.findByNumcap(numcap)
                .orElseThrow(() -> new NotFoundException("Capítulo CID-10 não encontrado: " + numcap));
        if (capitulo.getActive() == null || !capitulo.getActive()) {
            throw new NotFoundException("Capítulo CID-10 não encontrado: " + numcap);
        }
        return capituloMapper.toResponse(capitulo);
    }

    @Override
    public Page<Cid10CategoriaResponse> pesquisarCategorias(String q, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<Cid10Categorias> spec = Specification.where((root, query, cb) -> 
                cb.isTrue(root.get("active"))
        );

        if (q != null && !q.isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("cat")), like),
                    cb.like(cb.lower(root.get("descricao")), like),
                    cb.like(cb.lower(root.get("descrAbrev")), like)
            ));
        }

        Page<Cid10Categorias> page = categoriasRepository.findAll(spec, pageable);
        return page.map(categoriaMapper::toResponse);
    }

    @Override
    public Cid10CategoriaResponse obterCategoriaPorCodigo(String cat) {
        if (cat == null || cat.isBlank()) {
            throw new NotFoundException("Categoria CID-10 não encontrada");
        }
        Cid10Categorias categoria = categoriasRepository.findByCat(cat.trim().toUpperCase())
                .orElseThrow(() -> new NotFoundException("Categoria CID-10 não encontrada: " + cat));
        if (categoria.getActive() == null || !categoria.getActive()) {
            throw new NotFoundException("Categoria CID-10 não encontrada: " + cat);
        }
        return categoriaMapper.toResponse(categoria);
    }

    @Override
    public List<Cid10CategoriaResponse> listarCategoriasPorIntervalo(String catinic, String catfim) {
        if (catinic == null || catfim == null) {
            throw new IllegalArgumentException("Intervalo (catinic e catfim) são obrigatórios");
        }
        return categoriasRepository.findByIntervalo(catinic.trim().toUpperCase(), catfim.trim().toUpperCase())
                .stream()
                .filter(c -> c.getActive() != null && c.getActive())
                .map(categoriaMapper::toResponse)
                .toList();
    }

    @Override
    public Page<Cid10SubcategoriaResponse> pesquisarSubcategorias(String q, String categoriaCodigo, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<Cid10Subcategorias> spec = Specification.where((root, query, cb) -> 
                cb.isTrue(root.get("active"))
        );

        if (q != null && !q.isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("subcat")), like),
                    cb.like(cb.lower(root.get("descricao")), like),
                    cb.like(cb.lower(root.get("descrAbrev")), like)
            ));
        }

        if (categoriaCodigo != null && !categoriaCodigo.isBlank()) {
            String cat = categoriaCodigo.trim().toUpperCase();
            spec = spec.and((root, query, cb) -> cb.equal(root.get("categoriaCat"), cat));
        }

        Page<Cid10Subcategorias> page = subcategoriasRepository.findAll(spec, pageable);
        return page.map(subcategoriaMapper::toResponse);
    }

    @Override
    public Cid10SubcategoriaResponse obterSubcategoriaPorCodigo(String subcat) {
        if (subcat == null || subcat.isBlank()) {
            throw new NotFoundException("Subcategoria CID-10 não encontrada");
        }
        Cid10Subcategorias subcategoria = subcategoriasRepository.findBySubcat(subcat.trim().toUpperCase())
                .orElseThrow(() -> new NotFoundException("Subcategoria CID-10 não encontrada: " + subcat));
        if (subcategoria.getActive() == null || !subcategoria.getActive()) {
            throw new NotFoundException("Subcategoria CID-10 não encontrada: " + subcat);
        }
        return subcategoriaMapper.toResponse(subcategoria);
    }

    @Override
    public List<Cid10SubcategoriaResponse> listarSubcategoriasPorCategoria(String categoriaCodigo) {
        if (categoriaCodigo == null || categoriaCodigo.isBlank()) {
            throw new IllegalArgumentException("Código da categoria é obrigatório");
        }
        return subcategoriasRepository.findByCategoriaCatAndActiveTrue(categoriaCodigo.trim().toUpperCase())
                .stream()
                .map(subcategoriaMapper::toResponse)
                .toList();
    }

    @Override
    public List<Cid10GrupoResponse> listarGrupos() {
        return gruposRepository.findAll(Sort.by(Sort.Direction.ASC, "catinic"))
                .stream()
                .filter(g -> g.getActive() != null && g.getActive())
                .map(grupoMapper::toResponse)
                .toList();
    }

    @Override
    public List<Cid10GrupoResponse> pesquisarGrupos(String q) {
        List<Cid10Grupos> grupos = gruposRepository.findAll(Sort.by(Sort.Direction.ASC, "catinic"));
        if (q == null || q.isBlank()) {
            return grupos.stream()
                    .filter(g -> g.getActive() != null && g.getActive())
                    .map(grupoMapper::toResponse)
                    .toList();
        }
        String searchTerm = q.trim().toLowerCase(Locale.ROOT);
        return grupos.stream()
                .filter(g -> g.getActive() != null && g.getActive())
                .filter(g -> (g.getDescricao() != null && g.getDescricao().toLowerCase(Locale.ROOT).contains(searchTerm))
                        || (g.getDescricaoAbreviada() != null && g.getDescricaoAbreviada().toLowerCase(Locale.ROOT).contains(searchTerm))
                        || (g.getCatinic() != null && g.getCatinic().toLowerCase(Locale.ROOT).contains(searchTerm))
                        || (g.getCatfim() != null && g.getCatfim().toLowerCase(Locale.ROOT).contains(searchTerm)))
                .map(grupoMapper::toResponse)
                .toList();
    }

    @Override
    public List<CidOGrupoResponse> listarGruposCidO() {
        return gruposCidORepository.findAll(Sort.by(Sort.Direction.ASC, "catinic"))
                .stream()
                .filter(g -> g.getActive() != null && g.getActive())
                .map(grupoCidOMapper::toResponse)
                .toList();
    }

    @Override
    public List<CidOGrupoResponse> pesquisarGruposCidO(String q) {
        List<CidOGrupos> grupos = gruposCidORepository.findAll(Sort.by(Sort.Direction.ASC, "catinic"));
        if (q == null || q.isBlank()) {
            return grupos.stream()
                    .filter(g -> g.getActive() != null && g.getActive())
                    .map(grupoCidOMapper::toResponse)
                    .toList();
        }
        String searchTerm = q.trim().toLowerCase(Locale.ROOT);
        return grupos.stream()
                .filter(g -> g.getActive() != null && g.getActive())
                .filter(g -> (g.getDescricao() != null && g.getDescricao().toLowerCase(Locale.ROOT).contains(searchTerm))
                        || (g.getCatinic() != null && g.getCatinic().toLowerCase(Locale.ROOT).contains(searchTerm))
                        || (g.getCatfim() != null && g.getCatfim().toLowerCase(Locale.ROOT).contains(searchTerm)))
                .map(grupoCidOMapper::toResponse)
                .toList();
    }

    @Override
    public Page<CidOCategoriaResponse> pesquisarCategoriasCidO(String q, Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 20);
        }
        Specification<CidOCategorias> spec = Specification.where((root, query, cb) -> 
                cb.isTrue(root.get("active"))
        );

        if (q != null && !q.isBlank()) {
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("cat")), like),
                    cb.like(cb.lower(root.get("descricao")), like)
            ));
        }

        Page<CidOCategorias> page = categoriasCidORepository.findAll(spec, pageable);
        return page.map(categoriaCidOMapper::toResponse);
    }

    @Override
    public CidOCategoriaResponse obterCategoriaCidOPorCodigo(String cat) {
        if (cat == null || cat.isBlank()) {
            throw new NotFoundException("Categoria CID-O não encontrada");
        }
        CidOCategorias categoria = categoriasCidORepository.findByCat(cat.trim().toUpperCase())
                .orElseThrow(() -> new NotFoundException("Categoria CID-O não encontrada: " + cat));
        if (categoria.getActive() == null || !categoria.getActive()) {
            throw new NotFoundException("Categoria CID-O não encontrada: " + cat);
        }
        return categoriaCidOMapper.toResponse(categoria);
    }
}
