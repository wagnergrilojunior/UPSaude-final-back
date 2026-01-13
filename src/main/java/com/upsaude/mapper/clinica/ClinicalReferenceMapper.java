package com.upsaude.mapper.clinica;

import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.entity.referencia.Ciap2;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class ClinicalReferenceMapper {

    @PersistenceContext
    private EntityManager entityManager;

    public Cid10Subcategorias mapCid10(UUID id) {
        if (id == null)
            return null;
        return entityManager.getReference(Cid10Subcategorias.class, id);
    }

    public Ciap2 mapCiap2(UUID id) {
        if (id == null)
            return null;
        return entityManager.getReference(Ciap2.class, id);
    }
}
