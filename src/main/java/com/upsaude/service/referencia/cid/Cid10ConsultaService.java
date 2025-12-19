package com.upsaude.service.referencia.cid;

import com.upsaude.dto.referencia.cid.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Cid10ConsultaService {

    // CID-10 Capítulos
    List<Cid10CapituloResponse> listarCapitulos();
    Cid10CapituloResponse obterCapituloPorNumero(Integer numcap);

    // CID-10 Categorias
    Page<Cid10CategoriaResponse> pesquisarCategorias(String q, Pageable pageable);
    Cid10CategoriaResponse obterCategoriaPorCodigo(String cat);
    List<Cid10CategoriaResponse> listarCategoriasPorIntervalo(String catinic, String catfim);

    // CID-10 Subcategorias
    Page<Cid10SubcategoriaResponse> pesquisarSubcategorias(String q, String categoriaCodigo, Pageable pageable);
    Cid10SubcategoriaResponse obterSubcategoriaPorCodigo(String subcat);
    List<Cid10SubcategoriaResponse> listarSubcategoriasPorCategoria(String categoriaCodigo);

    // CID-10 Grupos
    List<Cid10GrupoResponse> listarGrupos();
    List<Cid10GrupoResponse> pesquisarGrupos(String q);

    // CID-O Grupos
    List<CidOGrupoResponse> listarGruposCidO();
    List<CidOGrupoResponse> pesquisarGruposCidO(String q);

    // CID-O Categorias
    Page<CidOCategoriaResponse> pesquisarCategoriasCidO(String q, Pageable pageable);
    CidOCategoriaResponse obterCategoriaCidOPorCodigo(String cat);
}
