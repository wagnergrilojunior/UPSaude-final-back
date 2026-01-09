package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapRenases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface SigtapRenasesRepository extends JpaRepository<SigtapRenases, UUID>, JpaSpecificationExecutor<SigtapRenases> {
    Optional<SigtapRenases> findByCodigoOficial(String codigoOficial);
}
