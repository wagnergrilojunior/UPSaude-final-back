package com.upsaude.service.support.agendamento;

import com.upsaude.api.request.agendamento.AgendamentoRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.profissional.EspecialidadesMedicas;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.convenio.ConvenioRepository;
import com.upsaude.repository.profissional.EspecialidadesMedicasRepository;
import com.upsaude.repository.profissional.MedicosRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgendamentoRelacionamentosHandler {

    private final PacienteRepository pacienteRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final MedicosRepository medicosRepository;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;
    private final ConvenioRepository convenioRepository;
    private final AgendamentoRepository agendamentoRepository;

    public Agendamento processarRelacionamentos(Agendamento agendamento, AgendamentoRequest request, UUID tenantId) {
        Paciente paciente = pacienteRepository.findByIdAndTenant(request.getPaciente(), tenantId)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
        agendamento.setPaciente(paciente);

        ProfissionaisSaude profissional = profissionaisSaudeRepository.findByIdAndTenant(request.getProfissional(), tenantId)
                .orElseThrow(() -> new NotFoundException("Profissional não encontrado com ID: " + request.getProfissional()));
        agendamento.setProfissional(profissional);

        agendamento.setEstabelecimento(profissional.getEstabelecimento());
        agendamento.setTenant(profissional.getTenant());

        if (request.getMedico() != null) {
            Medicos medico = medicosRepository.findByIdAndTenant(request.getMedico(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico()));
            agendamento.setMedico(medico);
        } else {
            agendamento.setMedico(null);
        }

        if (request.getEspecialidade() != null) {
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(request.getEspecialidade())
                    .orElseThrow(() -> new NotFoundException("Especialidade não encontrada com ID: " + request.getEspecialidade()));
            agendamento.setEspecialidade(especialidade);
        } else {
            agendamento.setEspecialidade(null);
        }

        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findById(request.getConvenio())
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            if (convenio.getTenant() == null || convenio.getTenant().getId() == null || !convenio.getTenant().getId().equals(tenantId)) {
                throw new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio());
            }
            agendamento.setConvenio(convenio);
        } else {
            agendamento.setConvenio(null);
        }

        if (request.getAgendamentoOriginal() != null) {
            Agendamento original = agendamentoRepository.findByIdAndTenant(request.getAgendamentoOriginal(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Agendamento original não encontrado com ID: " + request.getAgendamentoOriginal()));
            agendamento.setAgendamentoOriginal(original);
        } else {
            agendamento.setAgendamentoOriginal(null);
        }

        return agendamento;
    }
}
