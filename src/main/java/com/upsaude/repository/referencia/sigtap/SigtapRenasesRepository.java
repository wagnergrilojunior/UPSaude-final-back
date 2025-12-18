package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapRenases;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SigtapRenasesRepository extends JpaRepository<SigtapRenases, UUID> {
    Optional<SigtapRenases> findByCodigoOficial(String codigoOficial);
}
