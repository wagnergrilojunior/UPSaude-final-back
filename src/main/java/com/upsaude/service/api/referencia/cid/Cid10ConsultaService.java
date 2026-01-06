package com.upsaude.service.api.referencia.cid;

import com.upsaude.api.response.referencia.cid.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Cid10ConsultaService {

    List<Cid10CapituloResponse> listarCapitulos();
    Cid10CapituloResponse obterCapituloPorNumero(Integer numcap);

    Page<Cid10CategoriaResponse> pesquisarCategorias(String q, Pageable pageable);
    Cid10CategoriaResponse obterCategoriaPorCodigo(String cat);
    List<Cid10CategoriaResponse> listarCategoriasPorIntervalo(String catinic, String catfim);

    Page<Cid10SubcategoriaResponse> pesquisarSubcategorias(String q, String categoriaCodigo, Pageable pageable);
    Cid10SubcategoriaResponse obterSubcategoriaPorCodigo(String subcat);
    List<Cid10SubcategoriaResponse> listarSubcategoriasPorCategoria(String categoriaCodigo);

    List<Cid10GrupoResponse> listarGrupos();
    List<Cid10GrupoResponse> pesquisarGrupos(String q);

    List<CidOGrupoResponse> listarGruposCidO();
    List<CidOGrupoResponse> pesquisarGruposCidO(String q);

    Page<CidOCategoriaResponse> pesquisarCategoriasCidO(String q, Pageable pageable);
    CidOCategoriaResponse obterCategoriaCidOPorCodigo(String cat);
}
