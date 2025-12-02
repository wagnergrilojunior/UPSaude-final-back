package com.upsaude.service.impl;

import com.upsaude.api.request.MedicacaoPacienteRequest;
import com.upsaude.api.response.MedicacaoPacienteResponse;
import com.upsaude.entity.MedicacaoPaciente;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.CidDoencas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicacaoPacienteMapper;
import com.upsaude.repository.MedicacaoPacienteRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.MedicacaoRepository;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.service.MedicacaoPacienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Medicações de Paciente.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicacaoPacienteServiceImpl implements MedicacaoPacienteService {

    private final MedicacaoPacienteRepository medicacaoPacienteRepository;
    private final MedicacaoPacienteMapper medicacaoPacienteMapper;
    private final PacienteRepository pacienteRepository;
    private final MedicacaoRepository medicacaoRepository;
    private final CidDoencasRepository cidDoencasRepository;

    @Override
    @Transactional
    @CacheEvict(value = "medicacaopaciente", allEntries = true)
    public MedicacaoPacienteResponse criar(MedicacaoPacienteRequest request) {
        log.debug("Criando nova ligação paciente-medicação");

        validarDadosBasicos(request);

        MedicacaoPaciente medicacaoPaciente = medicacaoPacienteMapper.fromRequest(request);
        
        // Carrega e define as entidades relacionadas
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
        medicacaoPaciente.setPaciente(paciente);

        Medicacao medicacao = medicacaoRepository.findById(request.getMedicacaoId())
                .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + request.getMedicacaoId()));
        medicacaoPaciente.setMedicacao(medicacao);

        // CID relacionado é opcional
        if (request.getCidRelacionadoId() != null) {
            CidDoencas cidRelacionado = cidDoencasRepository.findById(request.getCidRelacionadoId())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidRelacionadoId()));
            medicacaoPaciente.setCidRelacionado(cidRelacionado);
        }

        if (request.getMedicacaoAtiva() != null) {
            medicacaoPaciente.setMedicacaoAtiva(request.getMedicacaoAtiva());
        } else {
            medicacaoPaciente.setMedicacaoAtiva(true);
        }

        medicacaoPaciente.setActive(true);

        MedicacaoPaciente medicacaoPacienteSalvo = medicacaoPacienteRepository.save(medicacaoPaciente);
        log.info("Ligação paciente-medicação criada com sucesso. ID: {}", medicacaoPacienteSalvo.getId());

        return medicacaoPacienteMapper.toResponse(medicacaoPacienteSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "medicacaopaciente", key = "#id")
    public MedicacaoPacienteResponse obterPorId(UUID id) {
        log.debug("Buscando ligação paciente-medicação por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da ligação paciente-medicação é obrigatório");
        }

        MedicacaoPaciente medicacaoPaciente = medicacaoPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ligação paciente-medicação não encontrada com ID: " + id));

        return medicacaoPacienteMapper.toResponse(medicacaoPaciente);
    }

    @Override
    public Page<MedicacaoPacienteResponse> listar(Pageable pageable) {
        log.debug("Listando ligações paciente-medicação paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<MedicacaoPaciente> medicacoesPaciente = medicacaoPacienteRepository.findAll(pageable);
        return medicacoesPaciente.map(medicacaoPacienteMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicacaopaciente", key = "#id")
    public MedicacaoPacienteResponse atualizar(UUID id, MedicacaoPacienteRequest request) {
        log.debug("Atualizando ligação paciente-medicação. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da ligação paciente-medicação é obrigatório");
        }

        validarDadosBasicos(request);

        MedicacaoPaciente medicacaoPacienteExistente = medicacaoPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ligação paciente-medicação não encontrada com ID: " + id));

        atualizarDadosMedicacaoPaciente(medicacaoPacienteExistente, request);

        MedicacaoPaciente medicacaoPacienteAtualizado = medicacaoPacienteRepository.save(medicacaoPacienteExistente);
        log.info("Ligação paciente-medicação atualizada com sucesso. ID: {}", medicacaoPacienteAtualizado.getId());

        return medicacaoPacienteMapper.toResponse(medicacaoPacienteAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicacaopaciente", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo ligação paciente-medicação. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da ligação paciente-medicação é obrigatório");
        }

        MedicacaoPaciente medicacaoPaciente = medicacaoPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ligação paciente-medicação não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(medicacaoPaciente.getActive())) {
            throw new BadRequestException("Ligação paciente-medicação já está inativa");
        }

        medicacaoPaciente.setActive(false);
        medicacaoPacienteRepository.save(medicacaoPaciente);
        log.info("Ligação paciente-medicação excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(MedicacaoPacienteRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da ligação paciente-medicação são obrigatórios");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        if (request.getMedicacaoId() == null) {
            throw new BadRequestException("ID da medicação é obrigatório");
        }
    }

    private void atualizarDadosMedicacaoPaciente(MedicacaoPaciente medicacaoPaciente, MedicacaoPacienteRequest request) {
        // Atualiza campos simples
        if (request.getDose() != null) {
            medicacaoPaciente.setDose(request.getDose());
        }
        if (request.getFrequencia() != null) {
            medicacaoPaciente.setFrequencia(request.getFrequencia());
        }
        if (request.getVia() != null) {
            medicacaoPaciente.setVia(request.getVia());
        }
        if (request.getDataInicio() != null) {
            medicacaoPaciente.setDataInicio(request.getDataInicio());
        }
        if (request.getDataFim() != null) {
            medicacaoPaciente.setDataFim(request.getDataFim());
        }
        if (request.getMedicacaoAtiva() != null) {
            medicacaoPaciente.setMedicacaoAtiva(request.getMedicacaoAtiva());
        }
        if (request.getObservacoes() != null) {
            medicacaoPaciente.setObservacoes(request.getObservacoes());
        }

        // Atualiza relacionamentos se fornecidos
        if (request.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
            medicacaoPaciente.setPaciente(paciente);
        }

        if (request.getMedicacaoId() != null) {
            Medicacao medicacao = medicacaoRepository.findById(request.getMedicacaoId())
                    .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + request.getMedicacaoId()));
            medicacaoPaciente.setMedicacao(medicacao);
        }

        // CID relacionado é opcional
        if (request.getCidRelacionadoId() != null) {
            CidDoencas cidRelacionado = cidDoencasRepository.findById(request.getCidRelacionadoId())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidRelacionadoId()));
            medicacaoPaciente.setCidRelacionado(cidRelacionado);
        } else {
            // Se não fornecido, remove a relação
            medicacaoPaciente.setCidRelacionado(null);
        }
    }
}

