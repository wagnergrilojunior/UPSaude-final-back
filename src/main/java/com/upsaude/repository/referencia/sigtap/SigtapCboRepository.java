package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SigtapCboRepository extends JpaRepository<SigtapOcupacao, UUID>, JpaSpecificationExecutor<SigtapOcupacao> {
    Optional<SigtapOcupacao> findByCodigoOficial(String codigoOficial);
    
    @Query("SELECT COUNT(o) FROM SigtapOcupacao o WHERE o.codigoOficial LIKE :prefixo%")
    Long countByPrefixo(@Param("prefixo") String prefixo);
}

