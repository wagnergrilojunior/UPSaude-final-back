package com.upsaude.service.support.agendamento;

import com.upsaude.api.request.AgendamentoRequest;
import com.upsaude.entity.Agendamento;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.mapper.AgendamentoMapper;
import com.upsaude.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendamentoCreator {

    private final AgendamentoValidationService validationService;
    private final AgendamentoRelacionamentosHandler relacionamentosHandler;
    private final AgendamentoMapper mapper;
    private final AgendamentoRepository repository;

    public Agendamento criar(AgendamentoRequest request, UUID tenantId) {
        validationService.validarObrigatorios(request);
        validationService.verificarConflitosHorario(request, repository, tenantId);

        Agendamento agendamento = mapper.fromRequest(request);
        agendamento.setActive(true);
        agendamento.setStatus(StatusAgendamentoEnum.AGENDADO);
        agendamento.setDataUltimaAlteracao(OffsetDateTime.now());

        relacionamentosHandler.processarRelacionamentos(agendamento, request, tenantId);

        Agendamento salvo = repository.save(Objects.requireNonNull(agendamento));
        log.info("Agendamento criado com sucesso. ID: {}, tenant: {}", salvo.getId(), tenantId);
        return salvo;
    }
}
