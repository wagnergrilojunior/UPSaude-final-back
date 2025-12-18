package com.upsaude.repository.referencia.cid;

import com.upsaude.entity.referencia.cid.Cid10Categorias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Cid10CategoriasRepository extends JpaRepository<Cid10Categorias, UUID> {

    Optional<Cid10Categorias> findByCat(String cat);

    boolean existsByCatAndIdNot(String cat, UUID id);

    List<Cid10Categorias> findByCatIn(List<String> cats);
}

