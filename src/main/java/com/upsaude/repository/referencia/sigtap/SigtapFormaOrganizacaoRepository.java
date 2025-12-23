package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapFormaOrganizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SigtapFormaOrganizacaoRepository extends JpaRepository<SigtapFormaOrganizacao, UUID>, JpaSpecificationExecutor<SigtapFormaOrganizacao> {

    Optional<SigtapFormaOrganizacao> findBySubgrupoCodigoOficialAndCodigoOficial(String codigoSubgrupo, String codigoFormaOrganizacao);

    List<SigtapFormaOrganizacao> findBySubgrupoCodigoOficial(String codigoSubgrupo);

    boolean existsBySubgrupoIdAndCodigoOficial(UUID subgrupoId, String codigoOficial);

    List<SigtapFormaOrganizacao> findBySubgrupoCodigoOficialAndCodigoOficialIn(String codigoSubgrupo, List<String> codigos);

    List<SigtapFormaOrganizacao> findBySubgrupoGrupoCodigoOficialAndSubgrupoCodigoOficialAndCodigoOficialIn(
            String codigoGrupo, String codigoSubgrupo, List<String> codigos);
    
    @Query("SELECT fo FROM SigtapFormaOrganizacao fo " +
           "JOIN FETCH fo.subgrupo sg " +
           "JOIN FETCH sg.grupo g " +
           "WHERE fo.active = true AND g.codigoOficial = :grupoCodigo AND sg.codigoOficial = :subgrupoCodigo")
    List<SigtapFormaOrganizacao> findByGrupoCodigoAndSubgrupoCodigoWithRelationships(
            @Param("grupoCodigo") String grupoCodigo, 
            @Param("subgrupoCodigo") String subgrupoCodigo);
    
    @Query("SELECT fo FROM SigtapFormaOrganizacao fo " +
           "JOIN FETCH fo.subgrupo sg " +
           "JOIN FETCH sg.grupo g " +
           "WHERE fo.active = true AND sg.codigoOficial = :subgrupoCodigo")
    List<SigtapFormaOrganizacao> findBySubgrupoCodigoWithRelationships(@Param("subgrupoCodigo") String subgrupoCodigo);
    
    @Query("SELECT fo FROM SigtapFormaOrganizacao fo " +
           "JOIN FETCH fo.subgrupo sg " +
           "JOIN FETCH sg.grupo g " +
           "WHERE fo.active = true AND g.codigoOficial = :grupoCodigo")
    List<SigtapFormaOrganizacao> findByGrupoCodigoWithRelationships(@Param("grupoCodigo") String grupoCodigo);
}

