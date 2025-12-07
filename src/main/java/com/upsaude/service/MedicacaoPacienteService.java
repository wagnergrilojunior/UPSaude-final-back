package com.upsaude.service;

import com.upsaude.api.request.MedicacaoPacienteRequest;
import com.upsaude.api.request.MedicacaoPacienteSimplificadoRequest;
import com.upsaude.api.response.MedicacaoPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Medicações de Paciente.
 *
 * @author UPSaúde
 */
public interface MedicacaoPacienteService {

    /**
     * Cria uma nova ligação entre paciente e medicação no sistema.
     *
     * @param request Dados da ligação a ser criada
     * @return Resposta com os dados da ligação criada
     */
    MedicacaoPacienteResponse criar(MedicacaoPacienteRequest request);

    /**
     * Cria uma nova ligação entre paciente e medicação no sistema de forma simplificada.
     * Apenas requer paciente, tenant e medicação. Os demais campos são criados com valores padrão.
     *
     * @param request Dados simplificados da ligação a ser criada
     * @return Resposta com os dados da ligação criada
     */
    MedicacaoPacienteResponse criarSimplificado(MedicacaoPacienteSimplificadoRequest request);

    /**
     * Obtém uma ligação paciente-medicação pelo seu ID.
     *
     * @param id ID da ligação
     * @return Resposta com os dados da ligação
     */
    MedicacaoPacienteResponse obterPorId(UUID id);

    /**
     * Lista todas as ligações paciente-medicação de forma paginada.
     *
     * @param pageable Parâmetros de paginação
     * @return Página com as ligações
     */
    Page<MedicacaoPacienteResponse> listar(Pageable pageable);

    /**
     * Atualiza uma ligação paciente-medicação existente.
     *
     * @param id ID da ligação a ser atualizada
     * @param request Dados atualizados da ligação
     * @return Resposta com os dados da ligação atualizada
     */
    MedicacaoPacienteResponse atualizar(UUID id, MedicacaoPacienteRequest request);

    /**
     * Exclui (desativa) uma ligação paciente-medicação do sistema.
     *
     * @param id ID da ligação a ser excluída
     */
    void excluir(UUID id);
}

