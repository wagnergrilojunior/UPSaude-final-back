package com.upsaude.service.api.support.cidades;

import com.upsaude.api.request.referencia.geografico.CidadesRequest;
import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.entity.referencia.geografico.Estados;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.referencia.geografico.EstadosRepository;
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
