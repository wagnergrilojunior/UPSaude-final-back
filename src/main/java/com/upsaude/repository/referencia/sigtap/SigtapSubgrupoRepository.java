package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapSubgrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SigtapSubgrupoRepository extends JpaRepository<SigtapSubgrupo, UUID>, JpaSpecificationExecutor<SigtapSubgrupo> {
    Optional<SigtapSubgrupo> findByGrupoCodigoOficialAndCodigoOficial(String codigoGrupo, String codigoSubgrupo);
    List<SigtapSubgrupo> findByGrupoCodigoOficialAndCodigoOficialIn(String codigoGrupo, List<String> codigos);
    List<SigtapSubgrupo> findByGrupoCodigoOficial(String codigoGrupo);
    
    @Query("SELECT s FROM SigtapSubgrupo s JOIN FETCH s.grupo WHERE s.grupo.codigoOficial = :grupoCodigo AND s.active = true")
    List<SigtapSubgrupo> findByGrupoCodigoOficialWithGrupo(@Param("grupoCodigo") String grupoCodigo);
    
    @Query("SELECT s FROM SigtapSubgrupo s JOIN FETCH s.grupo " +
           "WHERE s.grupo.codigoOficial = :grupoCodigo AND s.codigoOficial = :subgrupoCodigo AND s.active = true")
    List<SigtapSubgrupo> findByGrupoCodigoOficialAndSubgrupoCodigoWithGrupo(
            @Param("grupoCodigo") String grupoCodigo, 
            @Param("subgrupoCodigo") String subgrupoCodigo);
    
    @Query("SELECT s FROM SigtapSubgrupo s JOIN FETCH s.grupo " +
           "WHERE s.grupo.codigoOficial = :grupoCodigo AND s.active = true " +
           "AND (LOWER(s.codigoOficial) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(s.nome) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<SigtapSubgrupo> findByGrupoCodigoOficialWithQ(@Param("grupoCodigo") String grupoCodigo, @Param("q") String q);
    
    @Query("SELECT s FROM SigtapSubgrupo s JOIN FETCH s.grupo " +
           "WHERE s.grupo.codigoOficial = :grupoCodigo AND s.codigoOficial = :subgrupoCodigo AND s.active = true " +
           "AND (LOWER(s.codigoOficial) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(s.nome) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<SigtapSubgrupo> findByGrupoCodigoOficialAndSubgrupoCodigoWithQ(
            @Param("grupoCodigo") String grupoCodigo, 
            @Param("subgrupoCodigo") String subgrupoCodigo, 
            @Param("q") String q);
}
