package com.upsaude.mapper.clinica.atendimento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.upsaude.api.request.clinica.atendimento.CheckInAtendimentoRequest;
import com.upsaude.api.response.clinica.atendimento.AgendamentoCheckInResponse;
import com.upsaude.api.response.clinica.atendimento.AtendimentoCheckInResponse;
import com.upsaude.api.response.clinica.atendimento.CheckInAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.ConvenioCheckInResponse;
import com.upsaude.api.response.clinica.atendimento.EquipeSaudeCheckInResponse;
import com.upsaude.api.response.clinica.atendimento.MedicoConsultaResponse;
import com.upsaude.api.response.clinica.atendimento.PacienteAtendimentoResponse;
import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.atendimento.CheckInAtendimento;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface CheckInAtendimentoMapper {

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        @Mapping(target = "active", ignore = true)
        @Mapping(target = "agendamento", ignore = true)
        @Mapping(target = "atendimento", ignore = true)
        @Mapping(target = "paciente", ignore = true)
        CheckInAtendimento fromRequest(CheckInAtendimentoRequest request);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        @Mapping(target = "active", ignore = true)
        @Mapping(target = "agendamento", ignore = true)
        @Mapping(target = "atendimento", ignore = true)
        @Mapping(target = "paciente", ignore = true)
        void updateFromRequest(CheckInAtendimentoRequest request, @MappingTarget CheckInAtendimento entity);

        @Mapping(target = "agendamento", source = "agendamento", qualifiedByName = "mapAgendamentoCheckIn")
        @Mapping(target = "atendimento", source = "atendimento", qualifiedByName = "mapAtendimentoCheckIn")
        @Mapping(target = "paciente", source = "paciente", qualifiedByName = "mapPacienteSimplificadoCheckIn")
        @Mapping(target = "estabelecimentoId", source = ".", qualifiedByName = "mapEstabelecimentoId")
        @Mapping(target = "tenantId", source = ".", qualifiedByName = "mapTenantId")
        CheckInAtendimentoResponse toResponse(CheckInAtendimento entity);

        @Named("mapPacienteSimplificadoCheckIn")
        default PacienteAtendimentoResponse mapPacienteSimplificadoCheckIn(Paciente paciente) {
                if (paciente == null) {
                        return null;
                }

                String cpf = paciente.getIdentificadores() != null
                                ? paciente.getIdentificadores().stream()
                                                .filter(id -> id.getTipo() == TipoIdentificadorEnum.CPF
                                                                && Boolean.TRUE.equals(id.getPrincipal()))
                                                .map(id -> id.getValor())
                                                .findFirst().orElse(null)
                                : null;

                String cns = paciente.getIdentificadores() != null
                                ? paciente.getIdentificadores().stream()
                                                .filter(id -> id.getTipo() == TipoIdentificadorEnum.CNS
                                                                && Boolean.TRUE.equals(id.getPrincipal()))
                                                .map(id -> id.getValor())
                                                .findFirst().orElse(null)
                                : null;

                String telefone = paciente.getContatos() != null
                                ? paciente.getContatos().stream()
                                                .filter(c -> c.getTipo() == TipoContatoEnum.TELEFONE)
                                                .map(c -> c.getTelefone())
                                                .filter(t -> t != null && !t.trim().isEmpty())
                                                .findFirst().orElse(null)
                                : null;

                String email = paciente.getContatos() != null
                                ? paciente.getContatos().stream()
                                                .filter(c -> c.getTipo() == TipoContatoEnum.EMAIL)
                                                .map(c -> c.getEmail())
                                                .filter(e -> e != null && !e.trim().isEmpty())
                                                .findFirst().orElse(null)
                                : null;

                return PacienteAtendimentoResponse.builder()
                                .id(paciente.getId())
                                .nomeCompleto(paciente.getNomeCompleto())
                                .cpf(cpf)
                                .cns(cns)
                                .telefone(telefone)
                                .email(email)
                                .dataNascimento(paciente.getDataNascimento())
                                .build();
        }

        @Named("mapAgendamentoCheckIn")
        default AgendamentoCheckInResponse mapAgendamentoCheckIn(Agendamento agendamento) {
                if (agendamento == null) {
                        return null;
                }

                return AgendamentoCheckInResponse.builder()
                                .id(agendamento.getId())
                                .dataHora(agendamento.getDataHora())
                                .dataHoraFim(agendamento.getDataHoraFim())
                                .duracaoPrevistaMinutos(agendamento.getDuracaoPrevistaMinutos())
                                .status(agendamento.getStatus())
                                .prioridade(agendamento.getPrioridade())
                                .ehEncaixe(agendamento.getEhEncaixe())
                                .ehRetorno(agendamento.getEhRetorno())
                                .motivoConsulta(agendamento.getMotivoConsulta())
                                .observacoesAgendamento(agendamento.getObservacoesAgendamento())
                                .profissional(mapProfissionalSimplificado(agendamento.getProfissional()))
                                .medico(mapMedicoSimplificado(agendamento.getMedico()))
                                .convenio(mapConvenioSimplificado(agendamento.getConvenio()))
                                .estabelecimentoId(agendamento.getEstabelecimento() != null
                                                ? agendamento.getEstabelecimento().getId()
                                                : null)
                                .tenantId(agendamento.getTenant() != null ? agendamento.getTenant().getId() : null)
                                .build();
        }

        @Named("mapAtendimentoCheckIn")
        default AtendimentoCheckInResponse mapAtendimentoCheckIn(Atendimento atendimento) {
                if (atendimento == null) {
                        return null;
                }

                return AtendimentoCheckInResponse.builder()
                                .id(atendimento.getId())
                                .createdAt(atendimento.getCreatedAt())
                                .updatedAt(atendimento.getUpdatedAt())
                                .active(atendimento.getActive())
                                .profissional(mapProfissionalSimplificado(atendimento.getProfissional()))
                                .equipeSaude(mapEquipeSaudeSimplificado(atendimento.getEquipeSaude()))
                                .convenio(mapConvenioSimplificado(atendimento.getConvenio()))
                                .anotacoes(atendimento.getAnotacoes())
                                .estabelecimentoId(atendimento.getEstabelecimento() != null
                                                ? atendimento.getEstabelecimento().getId()
                                                : null)
                                .tenantId(atendimento.getTenant() != null ? atendimento.getTenant().getId() : null)
                                .build();
        }

        @Named("mapEstabelecimentoId")
        default java.util.UUID mapEstabelecimentoId(CheckInAtendimento checkIn) {
                if (checkIn == null) {
                        return null;
                }

                if (checkIn.getAgendamento() != null && checkIn.getAgendamento().getEstabelecimento() != null) {
                        return checkIn.getAgendamento().getEstabelecimento().getId();
                } else if (checkIn.getEstabelecimento() != null) {
                        return checkIn.getEstabelecimento().getId();
                }
                return null;
        }

        @Named("mapTenantId")
        default java.util.UUID mapTenantId(CheckInAtendimento checkIn) {
                if (checkIn == null || checkIn.getTenant() == null) {
                        return null;
                }
                return checkIn.getTenant().getId();
        }

        default ProfissionalAtendimentoResponse mapProfissionalSimplificado(ProfissionaisSaude profissional) {
                if (profissional == null) {
                        return null;
                }

                String nomeCompleto = profissional.getDadosPessoaisBasicos() != null
                                ? profissional.getDadosPessoaisBasicos().getNomeCompleto()
                                : null;

                String registroProfissional = profissional.getRegistroProfissional() != null
                                ? profissional.getRegistroProfissional().getRegistroProfissional()
                                : null;

                String ufRegistro = profissional.getRegistroProfissional() != null
                                ? profissional.getRegistroProfissional().getUfRegistro()
                                : null;

                return ProfissionalAtendimentoResponse.builder()
                                .id(profissional.getId())
                                .nomeCompleto(nomeCompleto)
                                .registroProfissional(registroProfissional)
                                .ufRegistro(ufRegistro)
                                .build();
        }

        default MedicoConsultaResponse mapMedicoSimplificado(Medicos medico) {
                if (medico == null) {
                        return null;
                }

                String nomeCompleto = medico.getDadosPessoaisBasicos() != null
                                ? medico.getDadosPessoaisBasicos().getNomeCompleto()
                                : null;

                String crm = medico.getRegistroProfissional() != null
                                ? medico.getRegistroProfissional().getCrm()
                                : null;

                String crmUf = medico.getRegistroProfissional() != null
                                ? medico.getRegistroProfissional().getCrmUf()
                                : null;

                return MedicoConsultaResponse.builder()
                                .id(medico.getId())
                                .nomeCompleto(nomeCompleto)
                                .crm(crm)
                                .crmUf(crmUf)
                                .build();
        }

        default ConvenioCheckInResponse mapConvenioSimplificado(Convenio convenio) {
                if (convenio == null) {
                        return null;
                }

                return ConvenioCheckInResponse.builder()
                                .id(convenio.getId())
                                .nome(convenio.getNome())
                                .tipo(convenio.getTipo())
                                .build();
        }

        default EquipeSaudeCheckInResponse mapEquipeSaudeSimplificado(EquipeSaude equipeSaude) {
                if (equipeSaude == null) {
                        return null;
                }

                return EquipeSaudeCheckInResponse.builder()
                                .id(equipeSaude.getId())
                                .ine(equipeSaude.getIne())
                                .nomeReferencia(equipeSaude.getNomeReferencia())
                                .tipoEquipe(equipeSaude.getTipoEquipe())
                                .estabelecimentoId(equipeSaude.getEstabelecimento() != null
                                                ? equipeSaude.getEstabelecimento().getId()
                                                : null)
                                .build();
        }

}
