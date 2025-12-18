package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapComponenteRede;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SigtapComponenteRedeRepository extends JpaRepository<SigtapComponenteRede, UUID> {
    Optional<SigtapComponenteRede> findByCodigoOficial(String codigoOficial);
    boolean existsByCodigoOficial(String codigoOficial);
    List<SigtapComponenteRede> findByCodigoOficialIn(List<String> codigos);
}
