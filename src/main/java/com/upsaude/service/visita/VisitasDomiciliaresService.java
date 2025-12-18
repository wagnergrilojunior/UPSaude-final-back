package com.upsaude.service.visita;

import com.upsaude.api.request.visita.VisitasDomiciliaresRequest;
import com.upsaude.api.response.visita.VisitasDomiciliaresResponse;
import com.upsaude.enums.TipoVisitaDomiciliarEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface VisitasDomiciliaresService {

    VisitasDomiciliaresResponse criar(VisitasDomiciliaresRequest request);

    VisitasDomiciliaresResponse obterPorId(UUID id);

    Page<VisitasDomiciliaresResponse> listar(Pageable pageable,
                                            UUID estabelecimentoId,
                                            UUID pacienteId,
                                            UUID profissionalId,
                                            TipoVisitaDomiciliarEnum tipoVisita,
                                            OffsetDateTime inicio,
                                            OffsetDateTime fim);

    VisitasDomiciliaresResponse atualizar(UUID id, VisitasDomiciliaresRequest request);

    void excluir(UUID id);
}
