package com.upsaude.service.support.paciente;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class PacienteAssociacoesHandler {

    public <TRequest> void processarAssociacoes(
            java.util.Collection<UUID> ids,
            Function<UUID, TRequest> builder,
            Consumer<TRequest> serviceExecutor,
            String nomeEntidade,
            UUID pacienteId
    ) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        for (UUID id : ids) {
            TRequest req = builder.apply(id);
            serviceExecutor.accept(req);
        }
    }
}
