package com.upsaude.service.support.doencas;

import com.upsaude.api.request.doencas.DoencasRequest;
import com.upsaude.entity.doencas.Doencas;
import com.upsaude.mapper.DoencasMapper;
import com.upsaude.repository.doencas.DoencasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoencasCreator {

    private final DoencasRepository repository;
    private final DoencasMapper mapper;
    private final DoencasValidationService validationService;
    private final DoencasRelacionamentosHandler relacionamentosHandler;

    public Doencas criar(DoencasRequest request) {
        validationService.validarObrigatorios(request);
        validationService.validarDuplicidade(null, request);

        Doencas doenca = mapper.fromRequest(request);
        doenca.setActive(true);

        relacionamentosHandler.processarRelacionamentos(doenca, request);

        Doencas salvo = repository.save(Objects.requireNonNull(doenca));
        log.info("Doença criada com sucesso. ID: {}", salvo.getId());
        return salvo;
    }
}

