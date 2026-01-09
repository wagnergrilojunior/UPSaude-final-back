package com.upsaude.repository.referencia.cid;

import com.upsaude.entity.referencia.cid.Cid10Grupos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Cid10GruposRepository extends JpaRepository<Cid10Grupos, UUID> {

    Optional<Cid10Grupos> findByCatinicAndCatfim(String catinic, String catfim);

    boolean existsByCatinicAndCatfimAndIdNot(String catinic, String catfim, UUID id);

    @Query("SELECT g FROM Cid10Grupos g WHERE g.catinic IN :catinics AND g.catfim IN :catfims")
    List<Cid10Grupos> findByCatinicInAndCatfimIn(@Param("catinics") List<String> catinics, @Param("catfims") List<String> catfims);

    @Query(value = "SELECT g.* FROM cid10_grupos g " +
            "INNER JOIN (SELECT unnest(:catinics::text[]) as catinic, unnest(:catfims::text[]) as catfim) pairs " +
            "ON g.catinic = pairs.catinic AND g.catfim = pairs.catfim", nativeQuery = true)
    List<Cid10Grupos> findByCatinicAndCatfimPairs(@Param("catinics") List<String> catinics, @Param("catfims") List<String> catfims);

    @Query(value = "SELECT g.* FROM cid10_grupos g WHERE g.catinic >= :catinic AND g.catfim <= :catfim AND g.ativo = true ORDER BY g.catinic", nativeQuery = true)
    List<Cid10Grupos> findByIntervalo(@Param("catinic") String catinic, @Param("catfim") String catfim);
}

