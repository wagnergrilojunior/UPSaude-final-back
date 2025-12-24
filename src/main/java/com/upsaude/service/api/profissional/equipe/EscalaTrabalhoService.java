package com.upsaude.service.api.profissional.equipe;

import com.upsaude.api.request.profissional.equipe.EscalaTrabalhoRequest;
import com.upsaude.api.response.profissional.equipe.EscalaTrabalhoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

public interface EscalaTrabalhoService {

    EscalaTrabalhoResponse criar(EscalaTrabalhoRequest request);

    EscalaTrabalhoResponse obterPorId(UUID id);

    Page<EscalaTrabalhoResponse> listar(
        Pageable pageable,
        UUID profissionalId,
        UUID medicoId,
        UUID estabelecimentoId,
        DayOfWeek diaSemana,
        LocalDate vigentesEm,
        Boolean apenasAtivos
    );

    EscalaTrabalhoResponse atualizar(UUID id, EscalaTrabalhoRequest request);

    void excluir(UUID id);
}
