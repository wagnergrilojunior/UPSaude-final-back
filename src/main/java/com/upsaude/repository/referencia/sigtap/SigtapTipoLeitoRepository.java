package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapTipoLeito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapTipoLeitoRepository extends JpaRepository<SigtapTipoLeito, UUID> {
}
