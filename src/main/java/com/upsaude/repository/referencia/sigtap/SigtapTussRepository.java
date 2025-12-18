package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapTuss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapTussRepository extends JpaRepository<SigtapTuss, UUID> {
}
