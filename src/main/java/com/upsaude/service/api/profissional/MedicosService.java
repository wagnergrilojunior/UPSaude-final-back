package com.upsaude.service.api.profissional;

import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.api.response.profissional.EspecialidadeResponse;
import com.upsaude.api.response.profissional.MedicosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface MedicosService {

    MedicosResponse criar(MedicosRequest request);

    MedicosResponse obterPorId(UUID id);

    Page<MedicosResponse> listar(Pageable pageable);

    MedicosResponse atualizar(UUID id, MedicosRequest request);

    void excluir(UUID id);

    void inativar(UUID id);

    // Especialidades
    List<EspecialidadeResponse> listarEspecialidades(UUID medicoId);
    
    EspecialidadeResponse adicionarEspecialidade(UUID medicoId, String codigoCbo);
    
    void removerEspecialidade(UUID medicoId, String codigoCbo);
}
