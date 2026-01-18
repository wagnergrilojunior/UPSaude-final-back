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


public interface CnesSincronizacaoService {

    
    CnesSincronizacao criarRegistroSincronizacao(
            TipoEntidadeCnesEnum tipoEntidade,
            UUID entidadeId,
            String codigoIdentificador,
            String competencia,
            UUID estabelecimentoId
    );

    
    void marcarComoProcessando(UUID sincronizacaoId);

    
    void finalizarComSucesso(
            UUID sincronizacaoId,
            Integer registrosInseridos,
            Integer registrosAtualizados
    );

    
    void finalizarComErro(
            UUID sincronizacaoId,
            String mensagemErro,
            String detalhesErro,
            Integer registrosErro
    );

    
    CnesSincronizacaoResponse obterPorId(UUID id);

    
    Page<CnesSincronizacaoResponse> listar(
            TipoEntidadeCnesEnum tipoEntidade,
            StatusSincronizacaoEnum status,
            OffsetDateTime dataInicio,
            OffsetDateTime dataFim,
            Pageable pageable
    );

    
    List<CnesSincronizacaoResponse> buscarHistoricoPorEntidade(
            UUID entidadeId,
            TipoEntidadeCnesEnum tipoEntidade
    );

    
    List<CnesSincronizacaoResponse> buscarPorCodigoIdentificador(
            String codigoIdentificador,
            TipoEntidadeCnesEnum tipoEntidade
    );
}

