package com.upsaude.service.vacinacao;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.dto.vacinacao.AplicacaoVacinaRequest;
import com.upsaude.dto.vacinacao.AplicacaoVacinaResponse;
import com.upsaude.dto.vacinacao.ReacaoRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.vacinacao.AplicacaoVacina;
import com.upsaude.entity.vacinacao.AplicacaoVacinaReacao;
import com.upsaude.entity.vacinacao.EstrategiaVacinacao;
import com.upsaude.entity.vacinacao.FabricanteImunobiologico;
import com.upsaude.entity.vacinacao.Imunobiologico;
import com.upsaude.entity.vacinacao.LocalAplicacao;
import com.upsaude.entity.vacinacao.LoteVacina;
import com.upsaude.entity.vacinacao.StatusAplicacao;
import com.upsaude.entity.vacinacao.TipoDose;
import com.upsaude.entity.vacinacao.ViaAdministracao;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.repository.vacinacao.AplicacaoVacinaRepository;
import com.upsaude.repository.vacinacao.EstrategiaVacinacaoRepository;
import com.upsaude.repository.vacinacao.FabricanteImunobiologicoRepository;
import com.upsaude.repository.vacinacao.ImunobiologicoRepository;
import com.upsaude.repository.vacinacao.LocalAplicacaoRepository;
import com.upsaude.repository.vacinacao.LoteVacinaRepository;
import com.upsaude.repository.vacinacao.TipoDoseRepository;
import com.upsaude.repository.vacinacao.ViaAdministracaoRepository;
import com.upsaude.service.api.sistema.multitenancy.TenantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AplicacaoVacinaService {

    private final AplicacaoVacinaRepository aplicacaoRepository;
    private final PacienteRepository pacienteRepository;
    private final ImunobiologicoRepository imunobiologicoRepository;
    private final LoteVacinaRepository loteRepository;
    private final FabricanteImunobiologicoRepository fabricanteRepository;
    private final TipoDoseRepository tipoDoseRepository;
    private final LocalAplicacaoRepository localAplicacaoRepository;
    private final ViaAdministracaoRepository viaAdministracaoRepository;
    private final EstrategiaVacinacaoRepository estrategiaRepository;
    private final ProfissionaisSaudeRepository profissionalRepository;
    private final EstabelecimentosRepository estabelecimentoRepository;
    private final TenantRepository tenantRepository;
    private final TenantService tenantService;
    private final LoteVacinaService loteVacinaService;

    @Transactional
    public AplicacaoVacinaResponse criar(AplicacaoVacinaRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();

        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));

        Imunobiologico imunobiologico = imunobiologicoRepository.findById(request.getImunobiologicoId())
                .orElseThrow(() -> new NotFoundException("Imunobiológico não encontrado"));

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado"));

        AplicacaoVacina aplicacao = AplicacaoVacina.builder()
                .paciente(paciente)
                .imunobiologico(imunobiologico)
                .dataAplicacao(request.getDataAplicacao())
                .dataRegistro(OffsetDateTime.now())
                .fhirStatus(StatusAplicacao.COMPLETED)
                .fontePrimaria(request.getFontePrimaria() != null ? request.getFontePrimaria() : true)
                .origemRegistro(request.getOrigemRegistro() != null ? request.getOrigemRegistro() : "SISTEMA")
                .doseSubpotente(request.getDoseSubpotente() != null ? request.getDoseSubpotente() : false)
                .motivoSubpotencia(request.getMotivoSubpotencia())
                .elegibilidadePrograma(request.getElegibilidadePrograma())
                .fonteFinanciamento(request.getFonteFinanciamento())
                .observacoes(request.getObservacoes())
                .tenant(tenant)
                .ativo(true)
                .build();

        if (request.getLoteId() != null) {
            LoteVacina lote = loteRepository.findById(request.getLoteId())
                    .orElseThrow(() -> new NotFoundException("Lote não encontrado"));
            aplicacao.setLote(lote);
            aplicacao.setNumeroLote(lote.getNumeroLote());
            aplicacao.setFabricante(lote.getFabricante());
            aplicacao.setDataValidade(lote.getDataValidade());
            loteVacinaService.decrementarEstoque(lote.getId(), 1);
        } else {
            aplicacao.setNumeroLote(request.getNumeroLote());
            aplicacao.setDataValidade(request.getDataValidade());
            if (request.getFabricanteId() != null) {
                FabricanteImunobiologico fabricante = fabricanteRepository.findById(request.getFabricanteId())
                        .orElseThrow(() -> new NotFoundException("Fabricante não encontrado"));
                aplicacao.setFabricante(fabricante);
            }
        }

        if (request.getTipoDoseId() != null) {
            TipoDose tipoDose = tipoDoseRepository.findById(request.getTipoDoseId())
                    .orElseThrow(() -> new NotFoundException("Tipo de dose não encontrado"));
            aplicacao.setTipoDose(tipoDose);
        }
        aplicacao.setNumeroDose(request.getNumeroDose());
        aplicacao.setDoseQuantidade(request.getDoseQuantidade());
        aplicacao.setDoseUnidade(request.getDoseUnidade());

        if (request.getLocalAplicacaoId() != null) {
            LocalAplicacao local = localAplicacaoRepository.findById(request.getLocalAplicacaoId())
                    .orElseThrow(() -> new NotFoundException("Local de aplicação não encontrado"));
            aplicacao.setLocalAplicacao(local);
        }

        if (request.getViaAdministracaoId() != null) {
            ViaAdministracao via = viaAdministracaoRepository.findById(request.getViaAdministracaoId())
                    .orElseThrow(() -> new NotFoundException("Via de administração não encontrada"));
            aplicacao.setViaAdministracao(via);
        }

        if (request.getEstrategiaId() != null) {
            EstrategiaVacinacao estrategia = estrategiaRepository.findById(request.getEstrategiaId())
                    .orElseThrow(() -> new NotFoundException("Estratégia de vacinação não encontrada"));
            aplicacao.setEstrategia(estrategia);
        }

        if (request.getProfissionalId() != null) {
            ProfissionaisSaude profissional = profissionalRepository.findById(request.getProfissionalId())
                    .orElseThrow(() -> new NotFoundException("Profissional não encontrado"));
            aplicacao.setProfissional(profissional);
        }
        aplicacao.setProfissionalFuncao(request.getProfissionalFuncao());

        if (request.getEstabelecimentoId() != null) {
            Estabelecimentos estabelecimento = estabelecimentoRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado"));
            aplicacao.setEstabelecimento(estabelecimento);
        }

        aplicacao = aplicacaoRepository.save(aplicacao);
        log.info("Aplicação de vacina registrada: {} - Paciente: {}", aplicacao.getId(), paciente.getNomeCompleto());

        return AplicacaoVacinaResponse.fromEntity(aplicacao);
    }

    @Transactional
    public AplicacaoVacinaResponse atualizar(UUID id, AplicacaoVacinaRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();

        AplicacaoVacina aplicacao = aplicacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aplicação não encontrada"));

        if (!aplicacao.getTenant().getId().equals(tenantId)) {
            throw new BadRequestException("Aplicação não pertence ao tenant atual");
        }

        if (!aplicacao.getImunobiologico().getId().equals(request.getImunobiologicoId())) {
            Imunobiologico imunobiologico = imunobiologicoRepository.findById(request.getImunobiologicoId())
                    .orElseThrow(() -> new NotFoundException("Imunobiológico não encontrado"));
            aplicacao.setImunobiologico(imunobiologico);
        }

        aplicacao.setDataAplicacao(request.getDataAplicacao());
        aplicacao.setNumeroLote(request.getNumeroLote());
        aplicacao.setDataValidade(request.getDataValidade());
        aplicacao.setNumeroDose(request.getNumeroDose());
        aplicacao.setDoseQuantidade(request.getDoseQuantidade());
        aplicacao.setDoseUnidade(request.getDoseUnidade());
        aplicacao.setProfissionalFuncao(request.getProfissionalFuncao());
        aplicacao.setFontePrimaria(request.getFontePrimaria());
        aplicacao.setOrigemRegistro(request.getOrigemRegistro());
        aplicacao.setDoseSubpotente(request.getDoseSubpotente());
        aplicacao.setMotivoSubpotencia(request.getMotivoSubpotencia());
        aplicacao.setElegibilidadePrograma(request.getElegibilidadePrograma());
        aplicacao.setFonteFinanciamento(request.getFonteFinanciamento());
        aplicacao.setObservacoes(request.getObservacoes());

        aplicacao = aplicacaoRepository.save(aplicacao);
        log.info("Aplicação de vacina atualizada: {}", aplicacao.getId());

        return AplicacaoVacinaResponse.fromEntity(aplicacao);
    }

    @Transactional(readOnly = true)
    public AplicacaoVacinaResponse buscarPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();

        AplicacaoVacina aplicacao = aplicacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aplicação não encontrada"));

        if (!aplicacao.getTenant().getId().equals(tenantId)) {
            throw new BadRequestException("Aplicação não pertence ao tenant atual");
        }

        return AplicacaoVacinaResponse.fromEntity(aplicacao);
    }

    @Transactional(readOnly = true)
    public List<AplicacaoVacinaResponse> listarPorPaciente(UUID pacienteId) {
        UUID tenantId = tenantService.validarTenantAtual();

        return aplicacaoRepository.findByPacienteIdAndTenantIdOrderByDataAplicacaoDesc(pacienteId, tenantId)
                .stream()
                .map(AplicacaoVacinaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AplicacaoVacinaResponse> listarPorPeriodo(OffsetDateTime inicio, OffsetDateTime fim) {
        UUID tenantId = tenantService.validarTenantAtual();

        return aplicacaoRepository.findByTenantIdAndDataAplicacaoBetweenOrderByDataAplicacaoDesc(tenantId, inicio, fim)
                .stream()
                .map(AplicacaoVacinaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AplicacaoVacinaResponse> listarHistoricoPaciente(UUID pacienteId, UUID imunobiologicoId) {
        UUID tenantId = tenantService.validarTenantAtual();

        return aplicacaoRepository
                .findByPacienteIdAndImunobiologicoIdAndTenantId(pacienteId, imunobiologicoId, tenantId)
                .stream()
                .map(AplicacaoVacinaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();

        AplicacaoVacina aplicacao = aplicacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aplicação não encontrada"));

        if (!aplicacao.getTenant().getId().equals(tenantId)) {
            throw new BadRequestException("Aplicação não pertence ao tenant atual");
        }

        aplicacao.setAtivo(false);
        aplicacaoRepository.save(aplicacao);
        log.info("Aplicação de vacina desativada: {}", id);
    }

    @Transactional
    public AplicacaoVacinaResponse registrarReacao(UUID aplicacaoId, ReacaoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();

        AplicacaoVacina aplicacao = aplicacaoRepository.findById(aplicacaoId)
                .orElseThrow(() -> new NotFoundException("Aplicação não encontrada"));

        if (!aplicacao.getTenant().getId().equals(tenantId)) {
            throw new BadRequestException("Aplicação não pertence ao tenant atual");
        }

        AplicacaoVacinaReacao reacao = AplicacaoVacinaReacao.builder()
                .aplicacao(aplicacao)
                .nomeReacao(request.getTipoReacao())
                .criticidade(request.getGravidade())
                .dataOcorrencia(request.getDataOcorrencia())
                .descricao(request.getDescricao())
                .tratamentoRealizado(request.getTratamento())
                .evolucao(request.getEvolucao())
                .build();

        aplicacao.addReacao(reacao);
        aplicacao = aplicacaoRepository.save(aplicacao);

        log.info("Reação adversa registrada para aplicação: {}", aplicacaoId);

        return AplicacaoVacinaResponse.fromEntity(aplicacao);
    }

    @Transactional(readOnly = true)
    public long contarPorPaciente(UUID pacienteId) {
        UUID tenantId = tenantService.validarTenantAtual();
        return aplicacaoRepository.countByPacienteIdAndTenantId(pacienteId, tenantId);
    }
}
