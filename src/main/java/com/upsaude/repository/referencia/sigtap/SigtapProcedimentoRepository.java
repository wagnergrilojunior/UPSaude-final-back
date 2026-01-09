package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SigtapProcedimentoRepository extends JpaRepository<SigtapProcedimento, UUID>, JpaSpecificationExecutor<SigtapProcedimento> {
    Optional<SigtapProcedimento> findByCodigoOficial(String codigoOficial);
    Optional<SigtapProcedimento> findTopByCodigoOficialOrderByCompetenciaInicialDesc(String codigoOficial);
    Optional<SigtapProcedimento> findByCodigoOficialAndCompetenciaInicial(String codigoOficial, String competenciaInicial);
    
    @Query("SELECT p FROM SigtapProcedimento p " +
           "LEFT JOIN FETCH p.formaOrganizacao fo " +
           "LEFT JOIN FETCH fo.subgrupo sg " +
           "LEFT JOIN FETCH sg.grupo g " +
           "WHERE p.active = true")
    java.util.List<SigtapProcedimento> findAllWithRelationships();
}
