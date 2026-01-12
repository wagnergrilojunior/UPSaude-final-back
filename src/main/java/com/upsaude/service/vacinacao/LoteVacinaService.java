package com.upsaude.service.vacinacao;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.dto.vacinacao.LoteVacinaRequest;
import com.upsaude.dto.vacinacao.LoteVacinaResponse;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.vacinacao.FabricanteImunobiologico;
import com.upsaude.entity.vacinacao.Imunobiologico;
import com.upsaude.entity.vacinacao.LoteVacina;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.repository.vacinacao.FabricanteImunobiologicoRepository;
import com.upsaude.repository.vacinacao.ImunobiologicoRepository;
import com.upsaude.repository.vacinacao.LoteVacinaRepository;
import com.upsaude.service.api.sistema.multitenancy.TenantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoteVacinaService {

    private final LoteVacinaRepository loteRepository;
    private final ImunobiologicoRepository imunobiologicoRepository;
    private final FabricanteImunobiologicoRepository fabricanteRepository;
    private final EstabelecimentosRepository estabelecimentoRepository;
    private final TenantRepository tenantRepository;
    private final TenantService tenantService;

    @Transactional
    public LoteVacinaResponse criar(LoteVacinaRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();

        validarLoteDuplicado(request, tenantId, null);

        Imunobiologico imunobiologico = imunobiologicoRepository.findById(request.getImunobiologicoId())
                .orElseThrow(() -> new NotFoundException("Imunobiológico não encontrado"));

        FabricanteImunobiologico fabricante = fabricanteRepository.findById(request.getFabricanteId())
                .orElseThrow(() -> new NotFoundException("Fabricante não encontrado"));

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado"));

        Estabelecimentos estabelecimento = null;
        if (request.getEstabelecimentoId() != null) {
            estabelecimento = estabelecimentoRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado"));
        }

        LoteVacina lote = LoteVacina.builder()
                .imunobiologico(imunobiologico)
                .fabricante(fabricante)
                .numeroLote(request.getNumeroLote())
                .dataFabricacao(request.getDataFabricacao())
                .dataValidade(request.getDataValidade())
                .quantidadeRecebida(request.getQuantidadeRecebida())
                .quantidadeDisponivel(request.getQuantidadeDisponivel() != null ? request.getQuantidadeDisponivel()
                        : request.getQuantidadeRecebida())
                .precoUnitario(request.getPrecoUnitario())
                .observacoes(request.getObservacoes())
                .estabelecimento(estabelecimento)
                .tenant(tenant)
                .ativo(true)
                .build();

        lote = loteRepository.save(lote);
        log.info("Lote de vacina criado: {} - {}", lote.getId(), lote.getNumeroLote());

        return LoteVacinaResponse.fromEntity(lote);
    }

    @Transactional
    public LoteVacinaResponse atualizar(UUID id, LoteVacinaRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();

        LoteVacina lote = loteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lote não encontrado"));

        if (!lote.getTenant().getId().equals(tenantId)) {
            throw new BadRequestException("Lote não pertence ao tenant atual");
        }

        validarLoteDuplicado(request, tenantId, id);

        if (!lote.getImunobiologico().getId().equals(request.getImunobiologicoId())) {
            Imunobiologico imunobiologico = imunobiologicoRepository.findById(request.getImunobiologicoId())
                    .orElseThrow(() -> new NotFoundException("Imunobiológico não encontrado"));
            lote.setImunobiologico(imunobiologico);
        }

        if (!lote.getFabricante().getId().equals(request.getFabricanteId())) {
            FabricanteImunobiologico fabricante = fabricanteRepository.findById(request.getFabricanteId())
                    .orElseThrow(() -> new NotFoundException("Fabricante não encontrado"));
            lote.setFabricante(fabricante);
        }

        if (request.getEstabelecimentoId() != null) {
            Estabelecimentos estabelecimento = estabelecimentoRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado"));
            lote.setEstabelecimento(estabelecimento);
        } else {
            lote.setEstabelecimento(null);
        }

        lote.setNumeroLote(request.getNumeroLote());
        lote.setDataFabricacao(request.getDataFabricacao());
        lote.setDataValidade(request.getDataValidade());
        lote.setQuantidadeRecebida(request.getQuantidadeRecebida());
        lote.setQuantidadeDisponivel(request.getQuantidadeDisponivel());
        lote.setPrecoUnitario(request.getPrecoUnitario());
        lote.setObservacoes(request.getObservacoes());

        lote = loteRepository.save(lote);
        log.info("Lote de vacina atualizado: {}", lote.getId());

        return LoteVacinaResponse.fromEntity(lote);
    }

    @Transactional(readOnly = true)
    public LoteVacinaResponse buscarPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();

        LoteVacina lote = loteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lote não encontrado"));

        if (!lote.getTenant().getId().equals(tenantId)) {
            throw new BadRequestException("Lote não pertence ao tenant atual");
        }

        return LoteVacinaResponse.fromEntity(lote);
    }

    @Transactional(readOnly = true)
    public List<LoteVacinaResponse> listarTodos() {
        UUID tenantId = tenantService.validarTenantAtual();

        return loteRepository.findByTenantIdAndAtivoTrueOrderByDataValidadeAsc(tenantId)
                .stream()
                .map(LoteVacinaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoteVacinaResponse> listarDisponiveis() {
        UUID tenantId = tenantService.validarTenantAtual();

        return loteRepository.findLotesDisponiveis(tenantId, LocalDate.now())
                .stream()
                .map(LoteVacinaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoteVacinaResponse> listarPorImunobiologico(UUID imunobiologicoId) {
        UUID tenantId = tenantService.validarTenantAtual();

        return loteRepository.findByTenantIdAndImunobiologicoIdAndAtivoTrue(tenantId, imunobiologicoId)
                .stream()
                .map(LoteVacinaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoteVacinaResponse> listarPorEstabelecimento(UUID estabelecimentoId) {
        UUID tenantId = tenantService.validarTenantAtual();

        return loteRepository.findByTenantIdAndEstabelecimentoIdAndAtivoTrue(tenantId, estabelecimentoId)
                .stream()
                .map(LoteVacinaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();

        LoteVacina lote = loteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lote não encontrado"));

        if (!lote.getTenant().getId().equals(tenantId)) {
            throw new BadRequestException("Lote não pertence ao tenant atual");
        }

        lote.setAtivo(false);
        loteRepository.save(lote);
        log.info("Lote de vacina desativado: {}", id);
    }

    @Transactional
    public LoteVacinaResponse decrementarEstoque(UUID loteId, int quantidade) {
        LoteVacina lote = loteRepository.findById(loteId)
                .orElseThrow(() -> new NotFoundException("Lote não encontrado"));

        if (lote.getQuantidadeDisponivel() == null || lote.getQuantidadeDisponivel() < quantidade) {
            throw new BadRequestException("Estoque insuficiente no lote");
        }

        lote.setQuantidadeDisponivel(lote.getQuantidadeDisponivel() - quantidade);
        lote = loteRepository.save(lote);

        return LoteVacinaResponse.fromEntity(lote);
    }

    private void validarLoteDuplicado(LoteVacinaRequest request, UUID tenantId, UUID excludeId) {
        loteRepository.findByNumeroLoteAndImunobiologicoIdAndFabricanteIdAndTenantId(
                request.getNumeroLote(),
                request.getImunobiologicoId(),
                request.getFabricanteId(),
                tenantId).ifPresent(existing -> {
                    if (excludeId == null || !existing.getId().equals(excludeId)) {
                        throw new BadRequestException(
                                "Já existe um lote com este número para o mesmo imunobiológico e fabricante");
                    }
                });
    }
}
