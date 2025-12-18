package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapRedeAtencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SigtapRedeAtencaoRepository extends JpaRepository<SigtapRedeAtencao, UUID> {
}
