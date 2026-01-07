package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.entity.cnes.CnesSincronizacao;
import com.upsaude.enums.StatusSincronizacaoEnum;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service para gerenciar registros de controle de sincronização CNES.
 */
public interface CnesSincronizacaoService {

    /**
     * Cria um novo registro de sincronização com status PENDENTE.
     */
    CnesSincronizacao criarRegistroSincronizacao(
            TipoEntidadeCnesEnum tipoEntidade,
            UUID entidadeId,
            String codigoIdentificador,
            String competencia,
            UUID estabelecimentoId
    );

    /**
     * Atualiza o status de uma sincronização para PROCESSANDO.
     */
    void marcarComoProcessando(UUID sincronizacaoId);

    /**
     * Finaliza uma sincronização com sucesso.
     */
    void finalizarComSucesso(
            UUID sincronizacaoId,
            Integer registrosInseridos,
            Integer registrosAtualizados
    );

    /**
     * Finaliza uma sincronização com erro.
     */
    void finalizarComErro(
            UUID sincronizacaoId,
            String mensagemErro,
            String detalhesErro,
            Integer registrosErro
    );

    /**
     * Busca sincronização por ID.
     */
    CnesSincronizacaoResponse obterPorId(UUID id);

    /**
     * Lista sincronizações com filtros opcionais.
     */
    Page<CnesSincronizacaoResponse> listar(
            TipoEntidadeCnesEnum tipoEntidade,
            StatusSincronizacaoEnum status,
            OffsetDateTime dataInicio,
            OffsetDateTime dataFim,
            Pageable pageable
    );

    /**
     * Busca histórico de sincronizações de uma entidade.
     */
    List<CnesSincronizacaoResponse> buscarHistoricoPorEntidade(
            UUID entidadeId,
            TipoEntidadeCnesEnum tipoEntidade
    );

    /**
     * Busca sincronizações por código identificador.
     */
    List<CnesSincronizacaoResponse> buscarPorCodigoIdentificador(
            String codigoIdentificador,
            TipoEntidadeCnesEnum tipoEntidade
    );
}

