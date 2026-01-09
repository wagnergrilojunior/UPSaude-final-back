package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapRegraCondicionada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SigtapRegraCondicionadaRepository extends JpaRepository<SigtapRegraCondicionada, UUID> {
    Optional<SigtapRegraCondicionada> findByCodigoOficial(String codigoOficial);
}
