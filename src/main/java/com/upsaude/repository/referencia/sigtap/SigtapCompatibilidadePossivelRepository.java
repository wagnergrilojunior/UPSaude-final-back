package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapCompatibilidadePossivel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SigtapCompatibilidadePossivelRepository extends JpaRepository<SigtapCompatibilidadePossivel, UUID> {

    Optional<SigtapCompatibilidadePossivel> findByCodigoOficial(String codigoOficial);

    boolean existsByCodigoOficial(String codigoOficial);
}

