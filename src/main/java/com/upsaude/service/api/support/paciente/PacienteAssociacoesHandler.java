package com.upsaude.service.api.support.paciente;

import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
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
            log.debug("Nenhuma associação de {} para processar para paciente ID: {}", nomeEntidade, pacienteId);
            return;
        }

        log.debug("Processando {} associação(ões) de {} para paciente ID: {}", ids.size(), nomeEntidade, pacienteId);

        for (UUID id : ids) {
            try {
                log.debug("Processando associação de {} com ID: {} para paciente ID: {}", nomeEntidade, id, pacienteId);
                TRequest req = builder.apply(id);
                serviceExecutor.accept(req);
                log.debug("Associação de {} com ID: {} processada com sucesso para paciente ID: {}", 
                        nomeEntidade, id, pacienteId);
            } catch (BadRequestException e) {
                log.error("Erro de validação ao processar associação de {} com ID: {} para paciente ID: {}. Erro: {}", 
                        nomeEntidade, id, pacienteId, e.getMessage());
                throw new BadRequestException(
                        String.format("Erro ao associar %s (ID: %s) ao paciente (ID: %s): %s", 
                                nomeEntidade, id, pacienteId, e.getMessage()), e);
            } catch (NotFoundException e) {
                log.error("Entidade não encontrada ao processar associação de {} com ID: {} para paciente ID: {}. Erro: {}", 
                        nomeEntidade, id, pacienteId, e.getMessage());
                throw new NotFoundException(
                        String.format("Erro ao associar %s (ID: %s) ao paciente (ID: %s): %s", 
                                nomeEntidade, id, pacienteId, e.getMessage()), e);
            } catch (RuntimeException e) {
                log.error("Erro inesperado ao processar associação de {} com ID: {} para paciente ID: {}. Exception: {}", 
                        nomeEntidade, id, pacienteId, e.getClass().getSimpleName(), e);
                throw new RuntimeException(
                        String.format("Erro ao associar %s (ID: %s) ao paciente (ID: %s)", 
                                nomeEntidade, id, pacienteId), e);
            }
        }

        log.info("Todas as associações de {} foram processadas com sucesso para paciente ID: {}", nomeEntidade, pacienteId);
    }
}
