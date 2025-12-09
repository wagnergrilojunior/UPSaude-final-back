package com.upsaude.service.impl;

import com.upsaude.api.request.MedicacaoPacienteRequest;
import com.upsaude.api.request.MedicacaoPacienteSimplificadoRequest;
import com.upsaude.api.response.MedicacaoPacienteResponse;
import com.upsaude.entity.MedicacaoPaciente;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicacaoPacienteMapper;
import com.upsaude.repository.MedicacaoPacienteRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.MedicacaoRepository;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.repository.TenantRepository;
import com.upsaude.service.MedicacaoPacienteService;
import com.upsaude.service.TenantService;
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
    private final TenantRepository tenantRepository;
    private final TenantService tenantService;

    @Override
    @Transactional
    @CacheEvict(value = "medicacaopaciente", allEntries = true)
    public MedicacaoPacienteResponse criar(MedicacaoPacienteRequest request) {
        log.debug("Criando nova ligação paciente-medicação");

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        MedicacaoPaciente medicacaoPaciente = medicacaoPacienteMapper.fromRequest(request);
        
        // Carrega e define as entidades relacionadas
        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
        medicacaoPaciente.setPaciente(paciente);

        Medicacao medicacao = medicacaoRepository.findById(request.getMedicacao())
                .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + request.getMedicacao()));
        medicacaoPaciente.setMedicacao(medicacao);

        // CID relacionado é opcional
        if (request.getCidRelacionado() != null) {
            CidDoencas cidRelacionado = cidDoencasRepository.findById(request.getCidRelacionado())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidRelacionado()));
            medicacaoPaciente.setCidRelacionado(cidRelacionado);
        }

        // medicacaoAtiva não faz parte do Request - definido como true por padrão
        medicacaoPaciente.setMedicacaoAtiva(true);

        // Obtém o tenant do usuário autenticado (obrigatório para MedicacaoPaciente que estende BaseEntity)
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar relacionamentos de medicações.");
        }
        medicacaoPaciente.setTenant(tenant);

        medicacaoPaciente.setActive(true);

        MedicacaoPaciente medicacaoPacienteSalvo = medicacaoPacienteRepository.save(medicacaoPaciente);
        log.info("Ligação paciente-medicação criada com sucesso. ID: {}", medicacaoPacienteSalvo.getId());

        return medicacaoPacienteMapper.toResponse(medicacaoPacienteSalvo);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicacaopaciente", allEntries = true)
    public MedicacaoPacienteResponse criarSimplificado(MedicacaoPacienteSimplificadoRequest request) {
        log.debug("Criando nova ligação paciente-medicação simplificada - Paciente: {}, Tenant: {}, Medicação: {}", 
                request.getPaciente(), request.getTenant(), request.getMedicacao());

        if (request == null) {
            throw new BadRequestException("Dados da ligação paciente-medicação são obrigatórios");
        }

        if (request.getPaciente() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        if (request.getTenant() == null) {
            throw new BadRequestException("ID do tenant é obrigatório");
        }

        if (request.getMedicacao() == null) {
            throw new BadRequestException("ID da medicação é obrigatório");
        }

        // Busca paciente
        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));

        // Busca tenant
        Tenant tenant = tenantRepository.findById(request.getTenant())
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + request.getTenant()));

        // Busca medicação
        Medicacao medicacao = medicacaoRepository.findById(request.getMedicacao())
                .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + request.getMedicacao()));

        // Cria nova entidade com valores padrão
        MedicacaoPaciente medicacaoPaciente = new MedicacaoPaciente();
        medicacaoPaciente.setPaciente(paciente);
        medicacaoPaciente.setTenant(tenant);
        medicacaoPaciente.setMedicacao(medicacao);
        medicacaoPaciente.setActive(true);
        medicacaoPaciente.setMedicacaoAtiva(true); // Valor padrão
        // dose, frequencia, via, cidRelacionado, dataInicio, dataFim e observacoes ficam null

        MedicacaoPaciente medicacaoPacienteSalvo = medicacaoPacienteRepository.save(medicacaoPaciente);
        log.info("Ligação paciente-medicação criada com sucesso (simplificado). ID: {}", medicacaoPacienteSalvo.getId());

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

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

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

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

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
        // medicacaoAtiva não faz parte do Request
        if (request.getObservacoes() != null) {
            medicacaoPaciente.setObservacoes(request.getObservacoes());
        }

        // Atualiza relacionamentos se fornecidos
        if (request.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            medicacaoPaciente.setPaciente(paciente);
        }

        if (request.getMedicacao() != null) {
            Medicacao medicacao = medicacaoRepository.findById(request.getMedicacao())
                    .orElseThrow(() -> new NotFoundException("Medicação não encontrada com ID: " + request.getMedicacao()));
            medicacaoPaciente.setMedicacao(medicacao);
        }

        // CID relacionado é opcional
        if (request.getCidRelacionado() != null) {
            CidDoencas cidRelacionado = cidDoencasRepository.findById(request.getCidRelacionado())
                    .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidRelacionado()));
            medicacaoPaciente.setCidRelacionado(cidRelacionado);
        } else {
            // Se não fornecido, remove a relação
            medicacaoPaciente.setCidRelacionado(null);
        }
    }

}

