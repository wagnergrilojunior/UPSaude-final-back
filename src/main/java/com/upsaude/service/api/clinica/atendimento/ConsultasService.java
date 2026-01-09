package com.upsaude.service.api.clinica.atendimento;

import com.upsaude.api.request.clinica.atendimento.ConsultaCreateRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateAnamneseRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateAtestadoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateDiagnosticoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateEncaminhamentoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdateExamesRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaUpdatePrescricaoRequest;
import com.upsaude.api.request.clinica.atendimento.ConsultaRequest;
import com.upsaude.api.response.clinica.atendimento.ConsultaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ConsultasService {

    ConsultaResponse criar(ConsultaCreateRequest request);

    ConsultaResponse obterPorId(UUID id);

    Page<ConsultaResponse> listar(Pageable pageable);

    Page<ConsultaResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);

    Page<ConsultaResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ConsultaResponse atualizar(UUID id, ConsultaRequest request);

    void excluir(UUID id);

    void inativar(UUID id);

    ConsultaResponse atualizarAnamnese(UUID id, ConsultaUpdateAnamneseRequest request);

    ConsultaResponse atualizarDiagnostico(UUID id, ConsultaUpdateDiagnosticoRequest request);

    ConsultaResponse atualizarPrescricao(UUID id, ConsultaUpdatePrescricaoRequest request);

    ConsultaResponse atualizarExames(UUID id, ConsultaUpdateExamesRequest request);

    ConsultaResponse atualizarEncaminhamento(UUID id, ConsultaUpdateEncaminhamentoRequest request);

    ConsultaResponse atualizarAtestado(UUID id, ConsultaUpdateAtestadoRequest request);

    ConsultaResponse encerrar(UUID id);
}
