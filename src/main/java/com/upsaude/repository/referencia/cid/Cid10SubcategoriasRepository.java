package com.upsaude.repository.referencia.cid;

import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Cid10SubcategoriasRepository extends JpaRepository<Cid10Subcategorias, UUID>, JpaSpecificationExecutor<Cid10Subcategorias> {

    Optional<Cid10Subcategorias> findBySubcat(String subcat);

    boolean existsBySubcatAndIdNot(String subcat, UUID id);

    List<Cid10Subcategorias> findBySubcatIn(List<String> subcats);

    List<Cid10Subcategorias> findByCategoriaCatAndActiveTrue(String categoriaCat);
}

