package com.upsaude.repository.referencia.cid;

import com.upsaude.entity.referencia.cid.CidOGrupos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CidOGruposRepository extends JpaRepository<CidOGrupos, UUID> {

    Optional<CidOGrupos> findByCatinicAndCatfim(String catinic, String catfim);

    boolean existsByCatinicAndCatfimAndIdNot(String catinic, String catfim, UUID id);

    @Query("SELECT g FROM CidOGrupos g WHERE g.catinic IN :catinics AND g.catfim IN :catfims")
    List<CidOGrupos> findByCatinicInAndCatfimIn(@Param("catinics") List<String> catinics, @Param("catfims") List<String> catfims);

    @Query(value = "SELECT g.* FROM cid_o_grupos g " +
            "INNER JOIN (SELECT unnest(:catinics::text[]) as catinic, unnest(:catfims::text[]) as catfim) pairs " +
            "ON g.catinic = pairs.catinic AND g.catfim = pairs.catfim", nativeQuery = true)
    List<CidOGrupos> findByCatinicAndCatfimPairs(@Param("catinics") List<String> catinics, @Param("catfims") List<String> catfims);
}

