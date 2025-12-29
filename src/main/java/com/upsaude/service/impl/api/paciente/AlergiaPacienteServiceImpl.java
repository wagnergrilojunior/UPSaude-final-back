package com.upsaude.service.impl.api.paciente;

import com.upsaude.api.request.AlergiaPacienteRequest;
import com.upsaude.api.response.AlergiaPacienteResponse;
import com.upsaude.entity.paciente.AlergiaPaciente;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.TipoAlergiaEnum;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.paciente.AlergiaPacienteMapper;
import com.upsaude.repository.paciente.AlergiaPacienteRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.service.api.paciente.AlergiaPacienteService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.util.StringNormalizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiaPacienteServiceImpl implements AlergiaPacienteService {

    private final AlergiaPacienteRepository alergiaPacienteRepository;
    private final PacienteRepository pacienteRepository;
    private final AlergiaPacienteMapper mapper;
    private final TenantService tenantService;

    @Override
    @Transactional
    public AlergiaPacienteResponse criar(UUID pacienteId, AlergiaPacienteRequest request) {
        log.debug("Criando alergia para paciente ID: {}", pacienteId);
        
        tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + pacienteId));
        
        // Validar duplicidade antes de criar
        validarDuplicidade(pacienteId, request.getSubstancia(), request.getTipo(), null);
        
        AlergiaPaciente alergia = mapper.toEntity(request);
        alergia.setPaciente(paciente);
        alergia.setTenant(tenant);
        // Estabelecimento será setado automaticamente pelo contexto de segurança se necessário
        
        alergia = alergiaPacienteRepository.save(alergia);
        log.info("Alergia criada com sucesso. ID: {}", alergia.getId());
        
        return mapper.toResponse(alergia);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlergiaPacienteResponse> listarPorPaciente(UUID pacienteId, Pageable pageable) {
        log.debug("Listando alergias do paciente ID: {}", pacienteId);
        
        pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + pacienteId));
        
        Page<AlergiaPaciente> alergias = alergiaPacienteRepository.findByPacienteIdAndActiveTrue(pacienteId, pageable);
        return alergias.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public AlergiaPacienteResponse obterPorId(UUID pacienteId, UUID alergiaId) {
        log.debug("Obtendo alergia ID: {} do paciente ID: {}", alergiaId, pacienteId);
        
        pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + pacienteId));
        
        AlergiaPaciente alergia = alergiaPacienteRepository.findByIdAndPacienteId(alergiaId, pacienteId)
                .orElseThrow(() -> new NotFoundException("Alergia não encontrada com ID: " + alergiaId + " para o paciente: " + pacienteId));
        
        if (!alergia.getActive()) {
            throw new NotFoundException("Alergia não encontrada");
        }
        
        return mapper.toResponse(alergia);
    }

    @Override
    @Transactional
    public AlergiaPacienteResponse atualizar(UUID pacienteId, UUID alergiaId, AlergiaPacienteRequest request) {
        log.debug("Atualizando alergia ID: {} do paciente ID: {}", alergiaId, pacienteId);
        
        pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + pacienteId));
        
        AlergiaPaciente alergia = alergiaPacienteRepository.findByIdAndPacienteId(alergiaId, pacienteId)
                .orElseThrow(() -> new NotFoundException("Alergia não encontrada com ID: " + alergiaId + " para o paciente: " + pacienteId));
        
        if (!alergia.getActive()) {
            throw new NotFoundException("Alergia não encontrada");
        }
        
        // Validar duplicidade antes de atualizar (desconsiderando o próprio registro)
        validarDuplicidade(pacienteId, request.getSubstancia(), request.getTipo(), alergiaId);
        
        mapper.updateFromRequest(request, alergia);
        alergia = alergiaPacienteRepository.save(alergia);
        
        log.info("Alergia atualizada com sucesso. ID: {}", alergia.getId());
        return mapper.toResponse(alergia);
    }

    @Override
    @Transactional
    public void excluir(UUID pacienteId, UUID alergiaId) {
        log.debug("Excluindo (delete lógico) alergia ID: {} do paciente ID: {}", alergiaId, pacienteId);
        
        pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + pacienteId));
        
        AlergiaPaciente alergia = alergiaPacienteRepository.findByIdAndPacienteId(alergiaId, pacienteId)
                .orElseThrow(() -> new NotFoundException("Alergia não encontrada com ID: " + alergiaId + " para o paciente: " + pacienteId));
        
        alergia.setActive(false);
        alergiaPacienteRepository.save(alergia);
        
        log.info("Alergia excluída logicamente. ID: {}", alergiaId);
    }

    /**
     * Valida se já existe uma alergia ativa duplicada para o paciente.
     * Duas alergias são consideradas duplicadas se:
     * - Pertencem ao mesmo paciente
     * - Têm o mesmo tipo
     * - Substância normalizada é igual (case-insensitive, sem espaços extras)
     * - Ambas estão ativas
     * 
     * @param pacienteId ID do paciente
     * @param substancia Substância a ser validada
     * @param tipo Tipo da alergia
     * @param excludeId ID da alergia a ser excluída da validação (para atualização)
     * @throws ConflictException se encontrar alergia duplicada
     */
    private void validarDuplicidade(UUID pacienteId, String substancia, TipoAlergiaEnum tipo, UUID excludeId) {
        String substanciaNormalizada = StringNormalizer.normalizeSubstancia(substancia);
        
        // Buscar todas as alergias ativas do paciente com o mesmo tipo
        List<AlergiaPaciente> alergiasExistentes = alergiaPacienteRepository
                .findByPacienteIdAndTipoAndActiveTrue(pacienteId, tipo);
        
        // Comparar substância normalizada em memória
        for (AlergiaPaciente alergiaExistente : alergiasExistentes) {
            // Pular o próprio registro se estiver atualizando
            if (excludeId != null && alergiaExistente.getId().equals(excludeId)) {
                continue;
            }
            
            String substanciaExistenteNormalizada = alergiaExistente.getSubstanciaNormalizada();
            
            if (substanciaNormalizada != null && substanciaNormalizada.equals(substanciaExistenteNormalizada)) {
                throw new ConflictException(
                    String.format("Já existe uma alergia ativa para a substância '%s' do tipo '%s' para este paciente", 
                        substancia, tipo.getDescricao())
                );
            }
        }
    }
}

