package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapCid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SigtapCidRepository extends JpaRepository<SigtapCid, UUID> {
    Optional<SigtapCid> findByCodigoOficial(String codigoOficial);
    boolean existsByCodigoOficial(String codigoOficial);
    List<SigtapCid> findByCodigoOficialIn(List<String> codigos);
}
