package com.upsaude.service.api.support.paciente;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.paciente.PacienteIdentificadorRepository;
import com.upsaude.repository.paciente.PacienteContatoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PacienteCreator {

    private final PacienteValidationService validationService;
    private final PacienteAssociacoesManager associacoesManager;
    private final PacienteRepository pacienteRepository;
    private final PacienteIdentificadorRepository identificadorRepository;
    private final PacienteContatoRepository contatoRepository;
    private final com.upsaude.repository.sistema.multitenancy.TenantRepository tenantRepository;

    public Paciente criar(PacienteRequest request, UUID tenantId) {
        log.info("Iniciando criação de paciente no modelo antigo (explícito). Nome: {}",
                request.getDadosPessoaisBasicos() != null ? request.getDadosPessoaisBasicos().getNomeCompleto()
                        : "N/A");

        
        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request, pacienteRepository, identificadorRepository,
                contatoRepository, tenantId);
        validationService.sanitizarFlags(request);

        
        Paciente paciente = new Paciente();
        var dadosPessoais = request.getDadosPessoaisBasicos();
        paciente.setNomeCompleto(dadosPessoais.getNomeCompleto());
        paciente.setNomeSocial(dadosPessoais.getNomeSocial());
        paciente.setDataNascimento(dadosPessoais.getDataNascimento());
        paciente.setSexo(dadosPessoais.getSexo());

        paciente.setStatusPaciente(
                request.getStatusPaciente() != null ? request.getStatusPaciente() : StatusPacienteEnum.ATIVO);
        paciente.setObservacoes(request.getObservacoes());
        paciente.setTipoAtendimentoPreferencial(request.getTipoAtendimentoPreferencial());
        paciente.setActive(true);

        
        
        com.upsaude.entity.sistema.multitenancy.Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new com.upsaude.exception.NotFoundException("Tenant não encontrado: " + tenantId));

        
        if (request.getDadosClinicosBasicos() != null) {
            var reqClinico = request.getDadosClinicosBasicos();
            var clinico = new com.upsaude.entity.paciente.DadosClinicosBasicos();
            clinico.setPaciente(paciente);
            clinico.setTenant(tenant);
            clinico.setGestante(reqClinico.getGestante() != null ? reqClinico.getGestante() : false);
            clinico.setFumante(reqClinico.getFumante() != null ? reqClinico.getFumante() : false);
            clinico.setAlcoolista(reqClinico.getAlcoolista() != null ? reqClinico.getAlcoolista() : false);
            clinico.setUsuarioDrogas(reqClinico.getUsuarioDrogas() != null ? reqClinico.getUsuarioDrogas() : false);
            clinico.setHistoricoViolencia(
                    reqClinico.getHistoricoViolencia() != null ? reqClinico.getHistoricoViolencia() : false);
            clinico.setAcompanhamentoPsicossocial(
                    reqClinico.getAcompanhamentoPsicossocial() != null ? reqClinico.getAcompanhamentoPsicossocial()
                            : false);

            paciente.setDadosClinicosBasicos(clinico);
        }

        
        if (request.getDadosSociodemograficos() != null || request.getDadosDemograficos() != null) {
            var sociodemograficos = new com.upsaude.entity.paciente.DadosSociodemograficos();
            sociodemograficos.setPaciente(paciente);
            sociodemograficos.setTenant(tenant);

            if (request.getDadosSociodemograficos() != null) {
                var req = request.getDadosSociodemograficos();
                sociodemograficos.setRacaCor(req.getRacaCor());
                sociodemograficos.setNacionalidade(req.getNacionalidade());
                sociodemograficos.setPaisNascimento(req.getPaisNascimento());
                sociodemograficos.setNaturalidade(req.getNaturalidade());
                sociodemograficos.setMunicipioNascimentoIbge(req.getMunicipioNascimentoIbge());
                sociodemograficos.setEscolaridade(req.getEscolaridade());
                sociodemograficos.setOcupacaoProfissao(req.getOcupacaoProfissao());
                sociodemograficos.setSituacaoRua(req.getSituacaoRua() != null ? req.getSituacaoRua() : false);
            } else if (request.getDadosDemograficos() != null) {
                var req = request.getDadosDemograficos();
                sociodemograficos.setRacaCor(req.getRacaCor());
                sociodemograficos.setNacionalidade(req.getNacionalidade());
                sociodemograficos.setPaisNascimento(req.getPaisNascimento());
                sociodemograficos.setNaturalidade(req.getNaturalidade());
                sociodemograficos.setMunicipioNascimentoIbge(req.getMunicipioNascimentoIbge());
                sociodemograficos.setEscolaridade(req.getEscolaridade());
                sociodemograficos.setOcupacaoProfissao(req.getOcupacaoProfissao());
            }
            paciente.setDadosSociodemograficos(sociodemograficos);
        }

        
        associacoesManager.processarTodas(paciente, request, tenantId);

        
        Paciente pacienteSalvo = pacienteRepository.save(paciente);

        log.info("Paciente criado com sucesso via modelo antigo. ID: {}", pacienteSalvo.getId());

        return pacienteSalvo;
    }
}
