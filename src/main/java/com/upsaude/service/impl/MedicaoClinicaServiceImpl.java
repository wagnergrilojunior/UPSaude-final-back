package com.upsaude.service.impl;

import com.upsaude.api.request.MedicaoClinicaRequest;
import com.upsaude.api.response.MedicaoClinicaResponse;
import com.upsaude.entity.MedicaoClinica;
import com.upsaude.entity.Paciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicaoClinicaMapper;
import com.upsaude.repository.MedicaoClinicaRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.service.MedicaoClinicaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Medições Clínicas.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicaoClinicaServiceImpl implements MedicaoClinicaService {

    private final MedicaoClinicaRepository medicaoClinicaRepository;
    private final MedicaoClinicaMapper medicaoClinicaMapper;
    private final PacienteRepository pacienteRepository;

    @Override
    @Transactional
    @CacheEvict(value = "medicaoclinica", allEntries = true)
    public MedicaoClinicaResponse criar(MedicaoClinicaRequest request) {
        log.debug("Criando nova medição clínica para paciente: {}", request.getPacienteId());

        validarDadosBasicos(request);

        MedicaoClinica medicaoClinica = medicaoClinicaMapper.fromRequest(request);

        // Carrega e define o paciente
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
        medicaoClinica.setPaciente(paciente);

        // Calcula IMC se peso e altura forem fornecidos
        calcularImc(medicaoClinica);

        medicaoClinica.setActive(true);

        MedicaoClinica medicaoClinicaSalva = medicaoClinicaRepository.save(medicaoClinica);
        log.info("Medição clínica criada com sucesso. ID: {}", medicaoClinicaSalva.getId());

        return medicaoClinicaMapper.toResponse(medicaoClinicaSalva);
    }

    @Override
    @Transactional
    @Cacheable(value = "medicaoclinica", key = "#id")
    public MedicaoClinicaResponse obterPorId(UUID id) {
        log.debug("Buscando medição clínica por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID da medição clínica é obrigatório");
        }

        MedicaoClinica medicaoClinica = medicaoClinicaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medição clínica não encontrada com ID: " + id));

        return medicaoClinicaMapper.toResponse(medicaoClinica);
    }

    @Override
    public Page<MedicaoClinicaResponse> listar(Pageable pageable) {
        log.debug("Listando medições clínicas paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<MedicaoClinica> medicoesClinicas = medicaoClinicaRepository.findAll(pageable);
        return medicoesClinicas.map(medicaoClinicaMapper::toResponse);
    }

    @Override
    public Page<MedicaoClinicaResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        log.debug("Listando medições clínicas do paciente: {}. Página: {}, Tamanho: {}",
                pacienteId, pageable.getPageNumber(), pageable.getPageSize());

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Page<MedicaoClinica> medicoesClinicas = medicaoClinicaRepository.findByPacienteIdOrderByDataHoraDesc(pacienteId, pageable);
        return medicoesClinicas.map(medicaoClinicaMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicaoclinica", key = "#id")
    public MedicaoClinicaResponse atualizar(UUID id, MedicaoClinicaRequest request) {
        log.debug("Atualizando medição clínica. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da medição clínica é obrigatório");
        }

        validarDadosBasicos(request);

        MedicaoClinica medicaoClinicaExistente = medicaoClinicaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medição clínica não encontrada com ID: " + id));

        atualizarDadosMedicaoClinica(medicaoClinicaExistente, request);

        // Recalcula IMC se peso ou altura foram atualizados
        calcularImc(medicaoClinicaExistente);

        MedicaoClinica medicaoClinicaAtualizada = medicaoClinicaRepository.save(medicaoClinicaExistente);
        log.info("Medição clínica atualizada com sucesso. ID: {}", medicaoClinicaAtualizada.getId());

        return medicaoClinicaMapper.toResponse(medicaoClinicaAtualizada);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicaoclinica", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo medição clínica. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da medição clínica é obrigatório");
        }

        MedicaoClinica medicaoClinica = medicaoClinicaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medição clínica não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(medicaoClinica.getActive())) {
            throw new BadRequestException("Medição clínica já está inativa");
        }

        medicaoClinica.setActive(false);
        medicaoClinicaRepository.save(medicaoClinica);
        log.info("Medição clínica excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(MedicaoClinicaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da medição clínica são obrigatórios");
        }
        if (request.getPacienteId() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }
        if (request.getDataHora() == null) {
            throw new BadRequestException("Data e hora da medição são obrigatórias");
        }
    }

    private void atualizarDadosMedicaoClinica(MedicaoClinica medicaoClinica, MedicaoClinicaRequest request) {
        // Atualiza data/hora
        if (request.getDataHora() != null) {
            medicaoClinica.setDataHora(request.getDataHora());
        }

        // Atualiza sinais vitais
        if (request.getPressaoSistolica() != null) {
            medicaoClinica.setPressaoSistolica(request.getPressaoSistolica());
        }
        if (request.getPressaoDiastolica() != null) {
            medicaoClinica.setPressaoDiastolica(request.getPressaoDiastolica());
        }
        if (request.getFrequenciaCardiaca() != null) {
            medicaoClinica.setFrequenciaCardiaca(request.getFrequenciaCardiaca());
        }
        if (request.getFrequenciaRespiratoria() != null) {
            medicaoClinica.setFrequenciaRespiratoria(request.getFrequenciaRespiratoria());
        }
        if (request.getTemperatura() != null) {
            medicaoClinica.setTemperatura(request.getTemperatura());
        }
        if (request.getSaturacaoOxigenio() != null) {
            medicaoClinica.setSaturacaoOxigenio(request.getSaturacaoOxigenio());
        }
        if (request.getGlicemiaCapilar() != null) {
            medicaoClinica.setGlicemiaCapilar(request.getGlicemiaCapilar());
        }

        // Atualiza medidas antropométricas
        if (request.getPeso() != null) {
            medicaoClinica.setPeso(request.getPeso());
        }
        if (request.getAltura() != null) {
            medicaoClinica.setAltura(request.getAltura());
        }
        if (request.getCircunferenciaAbdominal() != null) {
            medicaoClinica.setCircunferenciaAbdominal(request.getCircunferenciaAbdominal());
        }
        if (request.getImc() != null) {
            medicaoClinica.setImc(request.getImc());
        }

        // Atualiza observações
        if (request.getObservacoes() != null) {
            medicaoClinica.setObservacoes(request.getObservacoes());
        }

        // Atualiza relacionamento com paciente se fornecido
        if (request.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPacienteId()));
            medicaoClinica.setPaciente(paciente);
        }
    }

    /**
     * Calcula o IMC baseado no peso e altura.
     * Utiliza BigDecimal para garantir precisão nos cálculos.
     *
     * @param medicaoClinica Medição clínica para calcular o IMC
     */
    private void calcularImc(MedicaoClinica medicaoClinica) {
        if (medicaoClinica.getPeso() != null && medicaoClinica.getAltura() != null
                && medicaoClinica.getAltura().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal alturaQuadrado = medicaoClinica.getAltura().multiply(medicaoClinica.getAltura());
            medicaoClinica.setImc(medicaoClinica.getPeso().divide(alturaQuadrado, 2, RoundingMode.HALF_UP));
        } else {
            medicaoClinica.setImc(null);
        }
    }
}

