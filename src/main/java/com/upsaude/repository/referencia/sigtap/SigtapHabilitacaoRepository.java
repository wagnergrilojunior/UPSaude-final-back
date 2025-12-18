package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapHabilitacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapHabilitacaoRepository extends JpaRepository<SigtapHabilitacao, UUID> {
}
