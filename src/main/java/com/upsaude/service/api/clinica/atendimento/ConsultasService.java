package com.upsaude.service.api.clinica.atendimento;

import com.upsaude.api.request.clinica.atendimento.ConsultaCreateRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateAnamneseRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateAtestadoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateDiagnosticoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateEncaminhamentoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateExamesRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdatePrescricaoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultasRequest;
import com.upsaude.api.response.clinica.atendimento.ConsultasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ConsultasService {

    ConsultasResponse criar(ConsultaCreateRequest request);

    ConsultasResponse obterPorId(UUID id);

    Page<ConsultasResponse> listar(Pageable pageable);

    Page<ConsultasResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);

    Page<ConsultasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ConsultasResponse atualizar(UUID id, ConsultasRequest request);

    void excluir(UUID id);

    void inativar(UUID id);

    ConsultasResponse atualizarAnamnese(UUID id, ConsultaUpdateAnamneseRequest request);

    ConsultasResponse atualizarDiagnostico(UUID id, ConsultaUpdateDiagnosticoRequest request);

    ConsultasResponse atualizarPrescricao(UUID id, ConsultaUpdatePrescricaoRequest request);

    ConsultasResponse atualizarExames(UUID id, ConsultaUpdateExamesRequest request);

    ConsultasResponse atualizarEncaminhamento(UUID id, ConsultaUpdateEncaminhamentoRequest request);

    ConsultasResponse atualizarAtestado(UUID id, ConsultaUpdateAtestadoRequest request);

    ConsultasResponse encerrar(UUID id);
}
