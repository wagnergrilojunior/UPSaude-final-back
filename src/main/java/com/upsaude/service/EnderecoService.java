package com.upsaude.service;

import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.response.EnderecoResponse;
import com.upsaude.entity.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Endereco.
 *
 * PADRÃO DE USO DE ENDEREÇOS:
 * ============================
 * 
 * 1. SEMPRE use enderecoService.findOrCreate() ao criar/atualizar endereços
 * 2. NUNCA use enderecoRepository.save() diretamente
 * 3. NUNCA use enderecoMapper.fromRequest() seguido de save() sem findOrCreate
 * 
 * REQUESTS E ENDEREÇOS:
 * =====================
 * 
 * Os requests podem aceitar endereços de duas formas:
 * 
 * a) UUID (endereço existente):
 *    - ConvenioRequest.endereco: UUID
 *    - FabricantesVacinaRequest.endereco: UUID
 *    - ProfissionaisSaudeRequest.enderecoProfissional: UUID
 *    - EstabelecimentosRequest.enderecoPrincipal: UUID
 * 
 *    Neste caso, o service deve buscar o endereço por ID usando enderecoRepository.findById()
 * 
 * b) Objeto completo (novo endereço):
 *    - PacienteRequest.enderecos: List<EnderecoRequest>
 * 
 *    Neste caso, o service deve:
 *    1. Converter EnderecoRequest para Endereco usando enderecoMapper.fromRequest()
 *    2. Processar relacionamentos (estado, cidade, tenant)
 *    3. Chamar enderecoService.findOrCreate() para evitar duplicados
 *    4. Associar o endereço retornado (existente ou novo) à entidade
 * 
 * ATUALIZAÇÕES:
 * =============
 * 
 * Ao atualizar uma entidade com endereço:
 * - Se endereço vem com UUID diferente: buscar novo endereço por ID
 * - Se endereço vem como objeto completo: usar findOrCreate
 * - Se endereço não vem no request: manter endereço existente (não remover)
 *
 * @author UPSaúde
 */
public interface EnderecoService {

    EnderecoResponse criar(EnderecoRequest request);

    EnderecoResponse obterPorId(UUID id);

    Page<EnderecoResponse> listar(Pageable pageable);

    EnderecoResponse atualizar(UUID id, EnderecoRequest request);

    void excluir(UUID id);

    /**
     * Busca um endereço existente pelos campos principais ou cria um novo se não encontrar.
     * Usa comparação case-insensitive para evitar duplicados.
     * 
     * Campos usados para busca de duplicados:
     * - tipoLogradouro
     * - logradouro (case-insensitive, normalizado)
     * - numero (case-insensitive, normalizado)
     * - bairro (case-insensitive, normalizado)
     * - cep (case-insensitive, normalizado)
     * - cidadeId
     * - estadoId
     * 
     * IMPORTANTE: Este método normaliza dados automaticamente (trim, remove caracteres especiais de CEP/número)
     * antes de buscar, garantindo que endereços similares sejam encontrados.
     *
     * @param endereco endereço a ser buscado ou criado
     * @return endereço existente ou o novo endereço criado
     */
    Endereco findOrCreate(Endereco endereco);
}
