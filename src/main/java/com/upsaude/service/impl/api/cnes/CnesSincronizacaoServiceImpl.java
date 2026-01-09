package com.upsaude.service.impl.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.entity.cnes.CnesSincronizacao;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.StatusSincronizacaoEnum;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.cnes.CnesSincronizacaoRepository;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.service.api.cnes.CnesSincronizacaoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CnesSincronizacaoServiceImpl implements CnesSincronizacaoService {

    private final CnesSincronizacaoRepository repository;
    private final TenantService tenantService;
    private final EstabelecimentosRepository estabelecimentosRepository;

    @Override
    @Transactional
    public CnesSincronizacao criarRegistroSincronizacao(
            TipoEntidadeCnesEnum tipoEntidade,
            UUID entidadeId,
            String codigoIdentificador,
            String competencia,
            UUID estabelecimentoId) {
        
        log.debug("Criando registro de sincronização CNES. Tipo: {}, Identificador: {}", tipoEntidade, codigoIdentificador);
        
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        
        CnesSincronizacao sincronizacao = new CnesSincronizacao();
        sincronizacao.setTipoEntidade(tipoEntidade);
        sincronizacao.setEntidadeId(entidadeId);
        sincronizacao.setCodigoIdentificador(codigoIdentificador);
        sincronizacao.setCompetencia(competencia);
        sincronizacao.setStatus(StatusSincronizacaoEnum.PENDENTE);
        sincronizacao.setDataSincronizacao(OffsetDateTime.now());
        sincronizacao.setRegistrosInseridos(0);
        sincronizacao.setRegistrosAtualizados(0);
        sincronizacao.setRegistrosErro(0);
        sincronizacao.setTenant(tenant);
        
        if (estabelecimentoId != null) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(estabelecimentoId)
                    .orElse(null);
            if (estabelecimento != null) {
                sincronizacao.setEstabelecimento(estabelecimento);
            }
        }
        
        CnesSincronizacao saved = repository.save(sincronizacao);
        log.info("Registro de sincronização CNES criado. ID: {}", saved.getId());
        
        return saved;
    }

    @Override
    @Transactional
    public void marcarComoProcessando(UUID sincronizacaoId) {
        log.debug("Marcando sincronização como PROCESSANDO. ID: {}", sincronizacaoId);
        
        UUID tenantId = tenantService.validarTenantAtual();
        CnesSincronizacao sincronizacao = repository.findByIdAndTenant(sincronizacaoId, tenantId)
                .orElseThrow(() -> new NotFoundException("Sincronização não encontrada: " + sincronizacaoId));
        
        sincronizacao.setStatus(StatusSincronizacaoEnum.PROCESSANDO);
        repository.save(sincronizacao);
    }

    @Override
    @Transactional
    public void finalizarComSucesso(UUID sincronizacaoId, Integer registrosInseridos, Integer registrosAtualizados) {
        log.debug("Finalizando sincronização com sucesso. ID: {}, Inseridos: {}, Atualizados: {}", 
                sincronizacaoId, registrosInseridos, registrosAtualizados);
        
        UUID tenantId = tenantService.validarTenantAtual();
        CnesSincronizacao sincronizacao = repository.findByIdAndTenant(sincronizacaoId, tenantId)
                .orElseThrow(() -> new NotFoundException("Sincronização não encontrada: " + sincronizacaoId));
        
        sincronizacao.setStatus(StatusSincronizacaoEnum.SUCESSO);
        sincronizacao.setDataFim(OffsetDateTime.now());
        sincronizacao.setRegistrosInseridos(registrosInseridos != null ? registrosInseridos : 0);
        sincronizacao.setRegistrosAtualizados(registrosAtualizados != null ? registrosAtualizados : 0);
        sincronizacao.setRegistrosErro(0);
        
        repository.save(sincronizacao);
        log.info("Sincronização finalizada com sucesso. ID: {}", sincronizacaoId);
    }

    @Override
    @Transactional
    public void finalizarComErro(UUID sincronizacaoId, String mensagemErro, String detalhesErro, Integer registrosErro) {
        log.warn("Finalizando sincronização com erro. ID: {}, Erro: {}", sincronizacaoId, mensagemErro);
        
        UUID tenantId = tenantService.validarTenantAtual();
        CnesSincronizacao sincronizacao = repository.findByIdAndTenant(sincronizacaoId, tenantId)
                .orElseThrow(() -> new NotFoundException("Sincronização não encontrada: " + sincronizacaoId));
        
        sincronizacao.setStatus(StatusSincronizacaoEnum.ERRO);
        sincronizacao.setDataFim(OffsetDateTime.now());
        sincronizacao.setMensagemErro(mensagemErro);
        sincronizacao.setDetalhesErro(detalhesErro);
        sincronizacao.setRegistrosErro(registrosErro != null ? registrosErro : 0);
        
        repository.save(sincronizacao);
    }

    @Override
    @Transactional(readOnly = true)
    public CnesSincronizacaoResponse obterPorId(UUID id) {
        log.debug("Buscando sincronização por ID: {}", id);
        
        UUID tenantId = tenantService.validarTenantAtual();
        CnesSincronizacao sincronizacao = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Sincronização não encontrada: " + id));
        
        return toResponse(sincronizacao);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CnesSincronizacaoResponse> listar(
            TipoEntidadeCnesEnum tipoEntidade,
            StatusSincronizacaoEnum status,
            OffsetDateTime dataInicio,
            OffsetDateTime dataFim,
            Pageable pageable) {
        
        log.debug("Listando sincronizações CNES. Tipo: {}, Status: {}", tipoEntidade, status);
        
        UUID tenantId = tenantService.validarTenantAtual();
        
        if (tipoEntidade != null && status != null) {
            return repository.findByTipoEntidadeAndStatusAndTenant(tipoEntidade, status, tenantId, pageable)
                    .map(this::toResponse);
        } else if (tipoEntidade != null) {
            return repository.findByTipoEntidadeAndTenant(tipoEntidade, tenantId, pageable)
                    .map(this::toResponse);
        } else if (status != null) {
            return repository.findByStatusAndTenant(status, tenantId, pageable)
                    .map(this::toResponse);
        } else if (dataInicio != null && dataFim != null) {
            return repository.findByDataSincronizacaoBetweenAndTenant(dataInicio, dataFim, tenantId, pageable)
                    .map(this::toResponse);
        }
        
        return repository.findAllByTenant(tenantId, pageable).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CnesSincronizacaoResponse> buscarHistoricoPorEntidade(UUID entidadeId, TipoEntidadeCnesEnum tipoEntidade) {
        log.debug("Buscando histórico de sincronizações. Entidade ID: {}, Tipo: {}", entidadeId, tipoEntidade);
        
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findByEntidadeIdAndTipoEntidadeAndTenantOrderByDataSincronizacaoDesc(entidadeId, tipoEntidade, tenantId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CnesSincronizacaoResponse> buscarPorCodigoIdentificador(String codigoIdentificador, TipoEntidadeCnesEnum tipoEntidade) {
        log.debug("Buscando sincronizações por código. Identificador: {}, Tipo: {}", codigoIdentificador, tipoEntidade);
        
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findByCodigoIdentificadorAndTipoEntidadeAndTenantOrderByDataSincronizacaoDesc(codigoIdentificador, tipoEntidade, tenantId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private CnesSincronizacaoResponse toResponse(CnesSincronizacao entity) {
        return CnesSincronizacaoResponse.builder()
                .id(entity.getId())
                .tipoEntidade(entity.getTipoEntidade())
                .entidadeId(entity.getEntidadeId())
                .codigoIdentificador(entity.getCodigoIdentificador())
                .competencia(entity.getCompetencia())
                .status(entity.getStatus())
                .dataSincronizacao(entity.getDataSincronizacao())
                .dataFim(entity.getDataFim())
                .registrosInseridos(entity.getRegistrosInseridos())
                .registrosAtualizados(entity.getRegistrosAtualizados())
                .registrosErro(entity.getRegistrosErro())
                .mensagemErro(entity.getMensagemErro())
                .detalhesErro(entity.getDetalhesErro())
                .estabelecimentoId(entity.getEstabelecimento() != null ? entity.getEstabelecimento().getId() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}

