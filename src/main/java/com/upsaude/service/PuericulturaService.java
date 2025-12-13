package com.upsaude.service;

import com.upsaude.api.request.PuericulturaRequest;
import com.upsaude.api.response.PuericulturaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

public interface PuericulturaService {

    PuericulturaResponse criar(PuericulturaRequest request);

    PuericulturaResponse obterPorId(UUID id);

    Page<PuericulturaResponse> listar(Pageable pageable);

    Page<PuericulturaResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    List<PuericulturaResponse> listarPorPaciente(UUID pacienteId);

    Page<PuericulturaResponse> listarAtivos(UUID estabelecimentoId, Pageable pageable);

    PuericulturaResponse obterAtivoPorPaciente(UUID pacienteId);

    Page<PuericulturaResponse> listarPorPeriodo(UUID estabelecimentoId, LocalDate inicio, LocalDate fim, Pageable pageable);

    PuericulturaResponse atualizar(UUID id, PuericulturaRequest request);

    void excluir(UUID id);
}
