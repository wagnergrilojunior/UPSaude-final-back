package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapServicoRepository extends JpaRepository<SigtapServico, UUID> {
}
