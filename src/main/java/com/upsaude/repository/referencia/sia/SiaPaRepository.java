package com.upsaude.repository.referencia.sia;

import com.upsaude.entity.referencia.sia.SiaPa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SiaPaRepository extends JpaRepository<SiaPa, UUID>, JpaSpecificationExecutor<SiaPa> {

    // Buscas por competência e UF
    List<SiaPa> findByCompetenciaAndUf(String competencia, String uf);
    
    Page<SiaPa> findByCompetenciaAndUf(String competencia, String uf, Pageable pageable);
    
    List<SiaPa> findByUf(String uf);
    
    Page<SiaPa> findByUf(String uf, Pageable pageable);
    
    // Busca por procedimento
    List<SiaPa> findByProcedimentoCodigo(String procedimentoCodigo);
    
    Page<SiaPa> findByProcedimentoCodigo(String procedimentoCodigo, Pageable pageable);
    
    // Busca por CNES
    List<SiaPa> findByCodigoCnes(String codigoCnes);
    
    Page<SiaPa> findByCodigoCnes(String codigoCnes, Pageable pageable);
    
    // Busca por CID
    List<SiaPa> findByCidPrincipalCodigo(String cidPrincipalCodigo);
    
    Page<SiaPa> findByCidPrincipalCodigo(String cidPrincipalCodigo, Pageable pageable);
    
    // Busca combinada
    @Query("SELECT s FROM SiaPa s WHERE s.competencia = :competencia AND s.uf = :uf AND s.procedimentoCodigo = :procedimentoCodigo")
    List<SiaPa> findByCompetenciaAndUfAndProcedimento(
        @Param("competencia") String competencia,
        @Param("uf") String uf,
        @Param("procedimentoCodigo") String procedimentoCodigo
    );
    
    // Contagem por competência e UF
    long countByCompetenciaAndUf(String competencia, String uf);
    
    long countByUf(String uf);
}

