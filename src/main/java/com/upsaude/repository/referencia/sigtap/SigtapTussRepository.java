package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapTuss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface SigtapTussRepository extends JpaRepository<SigtapTuss, UUID>, JpaSpecificationExecutor<SigtapTuss> {
    Optional<SigtapTuss> findByCodigoOficial(String codigoOficial);
}
