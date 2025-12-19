package com.upsaude.repository.referencia.cid;

import com.upsaude.entity.referencia.cid.Cid10Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Cid10CategoriasRepository extends JpaRepository<Cid10Categorias, UUID>, JpaSpecificationExecutor<Cid10Categorias> {

    Optional<Cid10Categorias> findByCat(String cat);

    boolean existsByCatAndIdNot(String cat, UUID id);

    List<Cid10Categorias> findByCatIn(List<String> cats);

    @Query(value = "SELECT c.* FROM cid10_categorias c WHERE c.cat >= :catinic AND c.cat <= :catfim AND c.ativo = true ORDER BY c.cat", nativeQuery = true)
    List<Cid10Categorias> findByIntervalo(@Param("catinic") String catinic, @Param("catfim") String catfim);
}

