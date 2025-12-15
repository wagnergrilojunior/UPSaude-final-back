package com.upsaude.service.support.cidades;

import com.upsaude.api.request.CidadesRequest;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.Estados;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.EstadosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CidadesRelacionamentosHandler {

    private final EstadosRepository estadosRepository;

    public Cidades processarRelacionamentos(Cidades entity, CidadesRequest request) {
        Estados estado = estadosRepository.findById(request.getEstado())
            .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + request.getEstado()));
        entity.setEstado(estado);
        return entity;
    }
}

