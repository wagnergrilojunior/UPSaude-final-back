package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapRegistro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapRegistroRepository extends JpaRepository<SigtapRegistro, UUID> {
}
