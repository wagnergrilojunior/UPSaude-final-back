package com.upsaude.service.support.cidades;

import com.upsaude.api.request.geografico.CidadesRequest;
import com.upsaude.entity.geografico.Cidades;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.CidadesMapper;
import com.upsaude.repository.referencia.geografico.CidadesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CidadesUpdater {

    private final CidadesRepository repository;
    private final CidadesMapper mapper;
    private final CidadesValidationService validationService;
    private final CidadesRelacionamentosHandler relacionamentosHandler;

    public Cidades atualizar(UUID id, CidadesRequest request) {
        validationService.validarObrigatorios(request);

        Cidades existente = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + id));

        mapper.updateFromRequest(request, existente);
        relacionamentosHandler.processarRelacionamentos(existente, request);

        Cidades saved = repository.save(Objects.requireNonNull(existente));
        log.info("Cidade atualizada com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

