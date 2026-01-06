package com.upsaude.mapper.referencia.cid;

import com.upsaude.entity.referencia.cid.Cid10Capitulos;
import com.upsaude.entity.referencia.cid.Cid10Categorias;
import com.upsaude.entity.referencia.cid.Cid10Grupos;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.entity.referencia.cid.CidOCategorias;
import com.upsaude.entity.referencia.cid.CidOGrupos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class Cid10EntityMapper {

    public Cid10Capitulos mapToCapitulo(Map<String, String> fields, String competencia) {
        Cid10Capitulos capitulo = new Cid10Capitulos();

        String numcapStr = fields.get("NUMCAP");
        if (numcapStr != null && !numcapStr.trim().isEmpty()) {
            try {
                capitulo.setNumcap(Integer.parseInt(numcapStr.trim()));
            } catch (NumberFormatException e) {
                log.warn("Erro ao converter NUMCAP para Integer: {}", numcapStr);
                throw new IllegalArgumentException("NUMCAP inválido: " + numcapStr);
            }
        } else {
            throw new IllegalArgumentException("NUMCAP é obrigatório");
        }

        capitulo.setCatinic(limparString(fields.get("CATINIC")));
        capitulo.setCatfim(limparString(fields.get("CATFIM")));
        capitulo.setDescricao(limparString(fields.get("DESCRICAO")));
        capitulo.setDescricaoAbreviada(limparString(fields.get("DESCRABREV")));

        return capitulo;
    }

    public Cid10Grupos mapToGrupo(Map<String, String> fields, String competencia) {
        Cid10Grupos grupo = new Cid10Grupos();

        grupo.setCatinic(limparString(fields.get("CATINIC")));
        grupo.setCatfim(limparString(fields.get("CATFIM")));
        grupo.setDescricao(limparString(fields.get("DESCRICAO")));
        grupo.setDescricaoAbreviada(limparString(fields.get("DESCRABREV")));

        return grupo;
    }

    public Cid10Categorias mapToCategoria(Map<String, String> fields, String competencia) {
        Cid10Categorias categoria = new Cid10Categorias();

        categoria.setCat(limparString(fields.get("CAT")));
        categoria.setClassif(limparString(fields.get("CLASSIF")));
        categoria.setDescricao(limparString(fields.get("DESCRICAO")));
        categoria.setDescrAbrev(limparString(fields.get("DESCRABREV")));
        categoria.setRefer(limparString(fields.get("REFER")));
        categoria.setExcluidos(limparString(fields.get("EXCLUIDOS")));

        return categoria;
    }

    public Cid10Subcategorias mapToSubcategoria(Map<String, String> fields, String competencia) {
        Cid10Subcategorias subcategoria = new Cid10Subcategorias();

        String subcat = limparString(fields.get("SUBCAT"));
        if (subcat == null || subcat.isEmpty()) {
            throw new IllegalArgumentException("SUBCAT é obrigatório");
        }
        subcategoria.setSubcat(subcat);

        String categoriaCat = subcat.length() >= 3 ? subcat.substring(0, 3) : null;
        if (categoriaCat != null) {
            subcategoria.setCategoriaCat(categoriaCat);
        }

        subcategoria.setClassif(limparString(fields.get("CLASSIF")));
        subcategoria.setRestrSexo(limparString(fields.get("RESTRSEXO")));
        subcategoria.setCausaObito(limparString(fields.get("CAUSAOBITO")));
        subcategoria.setDescricao(limparString(fields.get("DESCRICAO")));
        subcategoria.setDescrAbrev(limparString(fields.get("DESCRABREV")));
        subcategoria.setRefer(limparString(fields.get("REFER")));
        subcategoria.setExcluidos(limparString(fields.get("EXCLUIDOS")));

        return subcategoria;
    }

    public CidOGrupos mapToCidOGrupo(Map<String, String> fields, String competencia) {
        CidOGrupos grupo = new CidOGrupos();

        grupo.setCatinic(limparString(fields.get("CATINIC")));
        grupo.setCatfim(limparString(fields.get("CATFIM")));
        grupo.setDescricao(limparString(fields.get("DESCRICAO")));
        grupo.setRefer(limparString(fields.get("REFER")));

        return grupo;
    }

    public CidOCategorias mapToCidOCategoria(Map<String, String> fields, String competencia) {
        CidOCategorias categoria = new CidOCategorias();

        categoria.setCat(limparString(fields.get("CAT")));
        categoria.setDescricao(limparString(fields.get("DESCRICAO")));
        categoria.setRefer(limparString(fields.get("REFER")));

        return categoria;
    }

    private String limparString(String valor) {
        if (valor == null) {
            return null;
        }
        String limpo = valor.trim();
        return limpo.isEmpty() ? null : limpo;
    }
}
