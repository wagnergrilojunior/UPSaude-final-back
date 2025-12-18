package com.upsaude.service.support.alergias;

import com.upsaude.api.request.alergia.AlergiasRequest;
import com.upsaude.entity.alergia.Alergias;
import com.upsaude.mapper.AlergiasMapper;
import com.upsaude.repository.alergia.AlergiasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiasCreator {

    private final AlergiasRepository repository;
    private final AlergiasMapper mapper;
    private final AlergiasSanitizer sanitizer;
    private final AlergiasValidationService validationService;

    public Alergias criar(AlergiasRequest request) {
        sanitizer.sanitizarRequest(request);
        validationService.validarObrigatorios(request);
        validationService.validarDuplicidade(null, request);

        Alergias alergia = mapper.fromRequest(request);
        alergia.setActive(true);

        Alergias salvo = repository.save(Objects.requireNonNull(alergia));
        log.info("Alergia criada com sucesso. ID: {}", salvo.getId());
        return salvo;
    }
}
