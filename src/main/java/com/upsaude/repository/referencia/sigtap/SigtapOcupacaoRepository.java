package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SigtapOcupacaoRepository extends JpaRepository<SigtapOcupacao, UUID> {
    Optional<SigtapOcupacao> findByCodigoOficial(String codigoOficial);
}
