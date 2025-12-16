package com.upsaude.service.support.ciddoencas;

import com.upsaude.api.request.CidDoencasRequest;
import com.upsaude.entity.CidDoencas;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.CidDoencasMapper;
import com.upsaude.repository.CidDoencasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CidDoencasUpdater {

    private final CidDoencasRepository repository;
    private final CidDoencasMapper mapper;
    private final CidDoencasValidationService validationService;

    public CidDoencas atualizar(UUID id, CidDoencasRequest request) {
        validationService.validarObrigatorios(request);

        CidDoencas existente = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("CID de doença não encontrado com ID: " + id));

        if (request.getCodigo() != null && !request.getCodigo().equals(existente.getCodigo())) {
            validationService.validarUnicidadeParaAtualizacao(id, request.getCodigo());
        }

        mapper.updateFromRequest(request, existente);

        CidDoencas saved = repository.save(Objects.requireNonNull(existente));
        log.info("CID de doença atualizado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

