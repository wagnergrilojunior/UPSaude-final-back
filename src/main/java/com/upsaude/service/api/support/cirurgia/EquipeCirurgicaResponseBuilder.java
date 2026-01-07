package com.upsaude.service.api.support.cirurgia;

import com.upsaude.api.response.clinica.cirurgia.EquipeCirurgicaResponse;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgica;
import com.upsaude.mapper.clinica.cirurgia.EquipeCirurgicaMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipeCirurgicaResponseBuilder {

    private final EquipeCirurgicaMapper mapper;

    public EquipeCirurgicaResponse build(EquipeCirurgica entity) {
        if (entity != null) {
            if (entity.getProfissionais() != null) {
                entity.getProfissionais().forEach(profissional -> {
                    Hibernate.initialize(profissional.getProfissional());
                });
            }
            if (entity.getMedicos() != null) {
                entity.getMedicos().forEach(medico -> {
                    Hibernate.initialize(medico.getMedico());
                });
            }
            if (entity.getCirurgia() != null) {
                Hibernate.initialize(entity.getCirurgia());
            }
        }
        return mapper.toResponse(entity);
    }
}

