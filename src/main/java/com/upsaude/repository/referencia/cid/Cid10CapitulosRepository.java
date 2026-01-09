package com.upsaude.repository.referencia.cid;

import com.upsaude.entity.referencia.cid.Cid10Capitulos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Cid10CapitulosRepository extends JpaRepository<Cid10Capitulos, UUID> {

    Optional<Cid10Capitulos> findByNumcap(Integer numcap);

    boolean existsByNumcapAndIdNot(Integer numcap, UUID id);

    List<Cid10Capitulos> findByNumcapIn(List<Integer> numcaps);
}

