package com.upsaude.service.api.support.farmacia;

import com.upsaude.api.request.farmacia.ReceitaItemRequest;
import com.upsaude.api.request.farmacia.ReceitaRequest;
import com.upsaude.entity.clinica.atendimento.Consulta;
import com.upsaude.entity.farmacia.Receita;
import com.upsaude.entity.farmacia.ReceitaItem;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.farmacia.ReceitaItemMapper;
import com.upsaude.mapper.farmacia.ReceitaMapper;
import com.upsaude.repository.clinica.atendimento.ConsultasRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.profissional.MedicosRepository;
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
public class ReceitaCreator {

    private final ReceitaMapper receitaMapper;
    private final ReceitaItemMapper receitaItemMapper;
    private final PacienteRepository pacienteRepository;
    private final ConsultasRepository consultasRepository;
    private final MedicosRepository medicosRepository;
    private final SigtapProcedimentoRepository sigtapProcedimentoRepository;

    public Receita criar(ReceitaRequest request, UUID tenantId) {
        log.debug("Criando nova receita para paciente: {}", request.getPacienteId());

        Receita receita = receitaMapper.fromRequest(request);
        receita.setActive(true);

        Paciente paciente = pacienteRepository.findByIdAndTenant(request.getPacienteId(), tenantId)
                .orElseThrow(() -> {
                    log.warn("Paciente não encontrado com ID: {} para tenant: {}", request.getPacienteId(), tenantId);
                    return new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId());
                });
        receita.setPaciente(paciente);

        if (request.getConsultaId() != null) {
            Consulta consulta = consultasRepository.findByIdAndTenant(request.getConsultaId(), tenantId)
                    .orElseThrow(() -> {
                        log.warn("Consulta não encontrada com ID: {} para tenant: {}", request.getConsultaId(), tenantId);
                        return new NotFoundException("Consulta não encontrada com ID: " + request.getConsultaId());
                    });
            receita.setConsulta(consulta);
        }

        if (request.getMedicoId() != null) {
            Medicos medico = medicosRepository.findById(request.getMedicoId())
                    .filter(m -> m.getTenant().getId().equals(tenantId))
                    .orElseThrow(() -> {
                        log.warn("Médico não encontrado com ID: {} para tenant: {}", request.getMedicoId(), tenantId);
                        return new NotFoundException("Médico não encontrado com ID: " + request.getMedicoId());
                    });
            receita.setMedico(medico);
        }

        List<ReceitaItem> itens = new ArrayList<>();
        for (ReceitaItemRequest itemRequest : request.getItens()) {
            SigtapProcedimento procedimento = sigtapProcedimentoRepository.findById(itemRequest.getSigtapProcedimentoId())
                    .orElseThrow(() -> {
                        log.warn("Procedimento SIGTAP não encontrado com ID: {}", itemRequest.getSigtapProcedimentoId());
                        return new NotFoundException("Procedimento SIGTAP não encontrado com ID: " + itemRequest.getSigtapProcedimentoId());
                    });

            ReceitaItem item = receitaItemMapper.fromRequest(itemRequest, receita);
            item.setSigtapProcedimento(procedimento);
            itens.add(item);
        }

        receita.setItens(itens);

        log.info("Receita criada com sucesso. Paciente: {}, Itens: {}", paciente.getId(), itens.size());

        return receita;
    }
}
