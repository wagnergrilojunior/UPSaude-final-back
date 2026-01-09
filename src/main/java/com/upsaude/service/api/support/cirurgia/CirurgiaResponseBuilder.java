package com.upsaude.service.api.support.cirurgia;

import com.upsaude.api.response.clinica.cirurgia.CirurgiaResponse;
import com.upsaude.entity.clinica.cirurgia.Cirurgia;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgica;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgicaMedico;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgicaProfissional;
import com.upsaude.mapper.clinica.cirurgia.CirurgiaMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CirurgiaResponseBuilder {

    private final CirurgiaMapper mapper;

    public CirurgiaResponse build(Cirurgia entity) {
        if (entity != null) {
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
            if (entity.getCirurgiaoPrincipal() != null) Hibernate.initialize(entity.getCirurgiaoPrincipal());
            if (entity.getMedicoCirurgiao() != null) Hibernate.initialize(entity.getMedicoCirurgiao());
            if (entity.getConvenio() != null) Hibernate.initialize(entity.getConvenio());
            if (entity.getDiagnosticoPrincipal() != null) Hibernate.initialize(entity.getDiagnosticoPrincipal());
            if (entity.getProcedimentos() != null) {
                Hibernate.initialize(entity.getProcedimentos());
                entity.getProcedimentos().forEach(proc -> {
                    if (proc.getProcedimento() != null) Hibernate.initialize(proc.getProcedimento());
                });
            }
            if (entity.getEquipe() != null) {
                Hibernate.initialize(entity.getEquipe());
                for (EquipeCirurgica equipe : entity.getEquipe()) {
                    if (equipe.getProfissionais() != null) {
                        Hibernate.initialize(equipe.getProfissionais());
                        for (EquipeCirurgicaProfissional prof : equipe.getProfissionais()) {
                            if (prof.getProfissional() != null) Hibernate.initialize(prof.getProfissional());
                        }
                    }
                    if (equipe.getMedicos() != null) {
                        Hibernate.initialize(equipe.getMedicos());
                        for (EquipeCirurgicaMedico med : equipe.getMedicos()) {
                            if (med.getMedico() != null) Hibernate.initialize(med.getMedico());
                        }
                    }
                }
            }
        }
        return mapper.toResponse(entity);
    }
}
