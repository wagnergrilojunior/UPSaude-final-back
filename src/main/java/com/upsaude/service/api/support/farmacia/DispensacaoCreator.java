package com.upsaude.service.api.support.farmacia;

import com.upsaude.api.request.farmacia.DispensacaoItemRequest;
import com.upsaude.api.request.farmacia.DispensacaoRequest;
import com.upsaude.entity.farmacia.Dispensacao;
import com.upsaude.entity.farmacia.DispensacaoItem;
import com.upsaude.entity.farmacia.Farmacia;
import com.upsaude.entity.farmacia.Receita;
import com.upsaude.entity.farmacia.ReceitaItem;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.farmacia.DispensacaoItemMapper;
import com.upsaude.mapper.farmacia.DispensacaoMapper;
import com.upsaude.repository.farmacia.FarmaciaRepository;
import com.upsaude.repository.farmacia.ReceitaRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispensacaoCreator {

    private final DispensacaoMapper dispensacaoMapper;
    private final DispensacaoItemMapper dispensacaoItemMapper;
    private final PacienteRepository pacienteRepository;
    private final FarmaciaRepository farmaciaRepository;
    private final ReceitaRepository receitaRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final SigtapProcedimentoRepository sigtapProcedimentoRepository;
    private final DispensacaoDomainService dispensacaoDomainService;
    private final ReceitaDomainService receitaDomainService;

    public Dispensacao criar(DispensacaoRequest request, UUID farmaciaId, UUID tenantId) {
        log.debug("Criando nova dispensação para farmácia: {}", farmaciaId);

        Dispensacao dispensacao = dispensacaoMapper.fromRequest(request);
        dispensacao.setActive(true);

        
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> {
                    log.warn("Paciente não encontrado com ID: {} para tenant: {}", request.getPacienteId(), tenantId);
                    return new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId());
                });
        dispensacao.setPaciente(paciente);

        Farmacia farmacia = farmaciaRepository.findByIdAndTenant(farmaciaId, tenantId)
                .orElseThrow(() -> {
                    log.warn("Farmácia não encontrada com ID: {} para tenant: {}", farmaciaId, tenantId);
                    return new NotFoundException("Farmácia não encontrada com ID: " + farmaciaId);
                });
        dispensacao.setFarmacia(farmacia);

        if (request.getReceitaId() != null) {
            Receita receita = receitaRepository.findByIdAndTenant(request.getReceitaId(), tenantId)
                    .orElseThrow(() -> {
                        log.warn("Receita não encontrada com ID: {} para tenant: {}", request.getReceitaId(), tenantId);
                        return new NotFoundException("Receita não encontrada com ID: " + request.getReceitaId());
                    });
            receitaDomainService.validarReceitaValida(receita);
            dispensacao.setReceita(receita);
        }

        if (request.getProfissionalSaudeId() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeRepository.findByIdAndTenant(
                    request.getProfissionalSaudeId(), tenantId)
                    .orElseThrow(() -> {
                        log.warn("Profissional de saúde não encontrado com ID: {} para tenant: {}",
                                request.getProfissionalSaudeId(), tenantId);
                        return new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissionalSaudeId());
                    });
            dispensacao.setProfissionalSaude(profissional);
        }

        List<DispensacaoItem> itens = new ArrayList<>();
        for (DispensacaoItemRequest itemRequest : request.getItens()) {
            SigtapProcedimento procedimento = sigtapProcedimentoRepository.findById(itemRequest.getSigtapProcedimentoId())
                    .orElseThrow(() -> {
                        log.warn("Procedimento SIGTAP não encontrado com ID: {}", itemRequest.getSigtapProcedimentoId());
                        return new NotFoundException("Procedimento SIGTAP não encontrado com ID: " + itemRequest.getSigtapProcedimentoId());
                    });

            DispensacaoItem item = dispensacaoItemMapper.fromRequest(itemRequest, dispensacao);
            item.setSigtapProcedimento(procedimento);

            if (itemRequest.getReceitaItemId() != null) {
                ReceitaItem receitaItem = receitaRepository.findByIdCompleto(request.getReceitaId())
                        .map(Receita::getItens)
                        .orElse(new ArrayList<>())
                        .stream()
                        .filter(ri -> ri.getId().equals(itemRequest.getReceitaItemId()))
                        .findFirst()
                        .orElseThrow(() -> {
                            log.warn("Item de receita não encontrado com ID: {}", itemRequest.getReceitaItemId());
                            return new NotFoundException("Item de receita não encontrado com ID: " + itemRequest.getReceitaItemId());
                        });

                item.setReceitaItem(receitaItem);
                dispensacaoDomainService.validarQuantidadeDispensada(receitaItem, itemRequest.getQuantidadeDispensada());
            }

            itens.add(item);
        }

        dispensacao.setItens(itens);

        log.info("Dispensação criada com sucesso. Farmácia: {}, Paciente: {}, Itens: {}", farmaciaId, paciente.getId(), itens.size());

        return dispensacao;
    }
}
