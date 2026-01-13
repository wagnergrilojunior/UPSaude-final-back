package com.upsaude.service.api.support.financeiro.competencia;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraRequest;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.mapper.financeiro.CompetenciaFinanceiraMapper;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraCreator {

    private final CompetenciaFinanceiraRepository repository;
    private final CompetenciaFinanceiraMapper mapper;
    private final CompetenciaFinanceiraValidationService validationService;

    public CompetenciaFinanceira criar(CompetenciaFinanceiraRequest request) {
        validationService.validarUnicidadeParaCriacao(request);

        CompetenciaFinanceira entity = mapper.fromRequest(request);
        entity.setActive(true);

        CompetenciaFinanceira saved = repository.save(Objects.requireNonNull(entity));
        log.info("Competência financeira criada com sucesso. ID: {}", saved.getId());
        return saved;
    }
}

