package com.upsaude.service;

import com.upsaude.api.request.VinculoProfissionalEquipeRequest;
import com.upsaude.api.response.VinculoProfissionalEquipeResponse;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Interface de serviço para gerenciamento de vínculos entre profissionais e equipes de saúde.
 *
 * @author UPSaúde
 */
public interface VinculoProfissionalEquipeService {

    /**
     * Cria um novo vínculo entre um profissional e uma equipe.
     *
     * @param request dados do vínculo
     * @return vínculo criado
     */
    VinculoProfissionalEquipeResponse criar(VinculoProfissionalEquipeRequest request);

    /**
     * Obtém um vínculo por ID.
     *
     * @param id ID do vínculo
     * @return vínculo encontrado
     */
    VinculoProfissionalEquipeResponse obterPorId(UUID id);

    /**
     * Lista todos os vínculos paginados.
     *
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<VinculoProfissionalEquipeResponse> listar(Pageable pageable);

    /**
     * Lista vínculos de um profissional.
     *
     * @param profissionalId ID do profissional
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<VinculoProfissionalEquipeResponse> listarPorProfissional(UUID profissionalId, Pageable pageable);

    /**
     * Lista vínculos de uma equipe.
     *
     * @param equipeId ID da equipe
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<VinculoProfissionalEquipeResponse> listarPorEquipe(UUID equipeId, Pageable pageable);

    /**
     * Lista vínculos por tipo de vínculo e equipe.
     *
     * @param tipoVinculo tipo de vínculo
     * @param equipeId ID da equipe
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<VinculoProfissionalEquipeResponse> listarPorTipoVinculo(TipoVinculoProfissionalEnum tipoVinculo, UUID equipeId, Pageable pageable);

    /**
     * Lista vínculos por status e equipe.
     *
     * @param status status do vínculo
     * @param equipeId ID da equipe
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<VinculoProfissionalEquipeResponse> listarPorStatus(StatusAtivoEnum status, UUID equipeId, Pageable pageable);

    /**
     * Atualiza um vínculo existente.
     *
     * @param id ID do vínculo
     * @param request dados atualizados
     * @return vínculo atualizado
     */
    VinculoProfissionalEquipeResponse atualizar(UUID id, VinculoProfissionalEquipeRequest request);

    /**
     * Exclui (desativa) um vínculo.
     *
     * @param id ID do vínculo
     */
    void excluir(UUID id);
}

