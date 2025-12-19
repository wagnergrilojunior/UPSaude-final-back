package com.upsaude.repository.referencia.cid;

import com.upsaude.entity.referencia.cid.CidOCategorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CidOCategoriasRepository extends JpaRepository<CidOCategorias, UUID>, JpaSpecificationExecutor<CidOCategorias> {

    Optional<CidOCategorias> findByCat(String cat);

    boolean existsByCatAndIdNot(String cat, UUID id);

    List<CidOCategorias> findByCatIn(List<String> cats);
}

