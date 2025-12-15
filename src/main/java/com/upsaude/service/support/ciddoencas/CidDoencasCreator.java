package com.upsaude.service.support.ciddoencas;

import com.upsaude.api.request.CidDoencasRequest;
import com.upsaude.entity.CidDoencas;
import com.upsaude.mapper.CidDoencasMapper;
import com.upsaude.repository.CidDoencasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CidDoencasCreator {

    private final CidDoencasRepository repository;
    private final CidDoencasMapper mapper;
    private final CidDoencasValidationService validationService;

    public CidDoencas criar(CidDoencasRequest request) {
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request.getCodigo());

        CidDoencas entity = mapper.fromRequest(request);
        entity.setActive(true);

        CidDoencas saved = repository.save(Objects.requireNonNull(entity));
        log.info("CID de doença criado com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

