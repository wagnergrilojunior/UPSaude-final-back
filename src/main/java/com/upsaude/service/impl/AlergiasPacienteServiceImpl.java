package com.upsaude.service.impl;

import com.upsaude.api.request.AlergiasPacienteRequest;
import com.upsaude.api.request.AlergiasPacienteSimplificadoRequest;
import com.upsaude.api.response.AlergiasPacienteResponse;
import com.upsaude.entity.Alergias;
import com.upsaude.entity.AlergiasPaciente;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.AlergiasPacienteMapper;
import com.upsaude.repository.AlergiasPacienteRepository;
import com.upsaude.repository.AlergiasRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.TenantRepository;
import com.upsaude.service.AlergiasPacienteService;
import com.upsaude.service.TenantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de AlergiasPaciente.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiasPacienteServiceImpl implements AlergiasPacienteService {

    private final AlergiasPacienteRepository alergiasPacienteRepository;
    private final AlergiasPacienteMapper alergiasPacienteMapper;
    private final PacienteRepository pacienteRepository;
    private final AlergiasRepository alergiasRepository;
    private final TenantRepository tenantRepository;
    private final TenantService tenantService;

    @Override
    @Transactional
    @CacheEvict(value = "alergiaspaciente", allEntries = true)
    public AlergiasPacienteResponse criar(AlergiasPacienteRequest request) {
        log.debug("Criando novo alergiaspaciente");

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        AlergiasPaciente alergiasPaciente = alergiasPacienteMapper.fromRequest(request);
        
        // Carrega e define relacionamentos obrigatórios
        if (request.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            alergiasPaciente.setPaciente(paciente);
        }
        
        if (request.getAlergia() != null) {
            Alergias alergia = alergiasRepository.findById(request.getAlergia())
                    .orElseThrow(() -> new NotFoundException("Alergia não encontrada com ID: " + request.getAlergia()));
            alergiasPaciente.setAlergia(alergia);
        }
        
        // Obtém o tenant do usuário autenticado (obrigatório para AlergiasPaciente que estende BaseEntity)
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar relacionamentos de alergias.");
        }
        alergiasPaciente.setTenant(tenant);
        
        alergiasPaciente.setActive(true);
        
        // Garante que alertaMedico não seja null (obrigatório no banco)
        if (alergiasPaciente.getAlertaMedico() == null) {
            alergiasPaciente.setAlertaMedico(true);
        }

        AlergiasPaciente alergiasPacienteSalvo = alergiasPacienteRepository.save(alergiasPaciente);
        log.info("AlergiasPaciente criado com sucesso. ID: {}", alergiasPacienteSalvo.getId());

        return alergiasPacienteMapper.toResponse(alergiasPacienteSalvo);
    }

    @Override
    @Transactional
    @CacheEvict(value = "alergiaspaciente", allEntries = true)
    public AlergiasPacienteResponse criarSimplificado(AlergiasPacienteSimplificadoRequest request) {
        log.debug("Criando novo alergiaspaciente simplificado - Paciente: {}, Tenant: {}, Alergia: {}", 
                request.getPaciente(), request.getTenant(), request.getAlergia());

        if (request == null) {
            throw new BadRequestException("Dados do alergiaspaciente são obrigatórios");
        }

        if (request.getPaciente() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        if (request.getTenant() == null) {
            throw new BadRequestException("ID do tenant é obrigatório");
        }

        if (request.getAlergia() == null) {
            throw new BadRequestException("ID da alergia é obrigatório");
        }

        // Busca paciente
        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));

        // Busca tenant
        Tenant tenant = tenantRepository.findById(request.getTenant())
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + request.getTenant()));

        // Busca alergia
        Alergias alergia = alergiasRepository.findById(request.getAlergia())
                .orElseThrow(() -> new NotFoundException("Alergia não encontrada com ID: " + request.getAlergia()));

        // Cria nova entidade com valores padrão
        AlergiasPaciente alergiasPaciente = new AlergiasPaciente();
        alergiasPaciente.setPaciente(paciente);
        alergiasPaciente.setTenant(tenant);
        alergiasPaciente.setAlergia(alergia);
        alergiasPaciente.setActive(true);
        alergiasPaciente.setAlertaMedico(true); // Define explicitamente para evitar NULL
        // Valores padrão: diagnostico e historicoReacoes são inicializados no construtor
        // observacoes fica null

        AlergiasPaciente alergiasPacienteSalvo = alergiasPacienteRepository.save(alergiasPaciente);
        log.info("AlergiasPaciente criado com sucesso (simplificado). ID: {}", alergiasPacienteSalvo.getId());

        return alergiasPacienteMapper.toResponse(alergiasPacienteSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "alergiaspaciente", key = "#id")
    public AlergiasPacienteResponse obterPorId(UUID id) {
        log.debug("Buscando alergiaspaciente por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do alergiaspaciente é obrigatório");
        }

        AlergiasPaciente alergiasPaciente = alergiasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AlergiasPaciente não encontrado com ID: " + id));

        return alergiasPacienteMapper.toResponse(alergiasPaciente);
    }

    @Override
    public Page<AlergiasPacienteResponse> listar(Pageable pageable) {
        log.debug("Listando AlergiasPacientes paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<AlergiasPaciente> alergiasPacientes = alergiasPacienteRepository.findAll(pageable);
        return alergiasPacientes.map(alergiasPacienteMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "alergiaspaciente", key = "#id")
    public AlergiasPacienteResponse atualizar(UUID id, AlergiasPacienteRequest request) {
        log.debug("Atualizando alergiaspaciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do alergiaspaciente é obrigatório");
        }

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        AlergiasPaciente alergiasPacienteExistente = alergiasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AlergiasPaciente não encontrado com ID: " + id));

        atualizarDadosAlergiasPaciente(alergiasPacienteExistente, request);

        AlergiasPaciente alergiasPacienteAtualizado = alergiasPacienteRepository.save(alergiasPacienteExistente);
        log.info("AlergiasPaciente atualizado com sucesso. ID: {}", alergiasPacienteAtualizado.getId());

        return alergiasPacienteMapper.toResponse(alergiasPacienteAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "alergiaspaciente", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo alergiaspaciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do alergiaspaciente é obrigatório");
        }

        AlergiasPaciente alergiasPaciente = alergiasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AlergiasPaciente não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(alergiasPaciente.getActive())) {
            throw new BadRequestException("AlergiasPaciente já está inativo");
        }

        alergiasPaciente.setActive(false);
        alergiasPacienteRepository.save(alergiasPaciente);
        log.info("AlergiasPaciente excluído (desativado) com sucesso. ID: {}", id);
    }

        // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

        private void atualizarDadosAlergiasPaciente(AlergiasPaciente alergiasPaciente, AlergiasPacienteRequest request) {
        AlergiasPaciente alergiasPacienteAtualizado = alergiasPacienteMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = alergiasPaciente.getId();
        com.upsaude.entity.Tenant tenantOriginal = alergiasPaciente.getTenant();
        Boolean activeOriginal = alergiasPaciente.getActive();
        java.time.OffsetDateTime createdAtOriginal = alergiasPaciente.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(alergiasPacienteAtualizado, alergiasPaciente);
        
        // Restaura campos de controle
        alergiasPaciente.setId(idOriginal);
        alergiasPaciente.setTenant(tenantOriginal);
        alergiasPaciente.setActive(activeOriginal);
        alergiasPaciente.setCreatedAt(createdAtOriginal);
    }

}
