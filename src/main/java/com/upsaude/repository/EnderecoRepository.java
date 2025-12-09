package com.upsaude.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.Endereco;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoLogradouroEnum;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
    
    /**
     * Busca todos os endereços de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de endereços do estabelecimento
     */
    Page<Endereco> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os endereços de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de endereços do tenant
     */
    Page<Endereco> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os endereços de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de endereços
     */
    Page<Endereco> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    /**
     * Busca um endereço existente pelos campos principais usando comparação case-insensitive.
     * Usa lower() para comparar campos de texto e ignora diferenças de maiúsculas/minúsculas.
     * Compara apenas campos não-null fornecidos, ignorando campos null na busca.
     * 
     * IMPORTANTE: Esta query busca endereços que correspondem aos campos fornecidos.
     * Se um campo é null, ele não é considerado na busca (qualquer valor é aceito).
     * Para uma busca mais precisa, forneça o máximo de campos possível.
     * 
     * Campos mínimos recomendados para busca eficaz:
     * - logradouro OU cep (pelo menos um)
     * - cidadeId OU estadoId (pelo menos um)
     * 
     * @param tipoLogradouro tipo do logradouro (pode ser null)
     * @param logradouro logradouro normalizado (pode ser null, mas recomendado)
     * @param numero número normalizado (pode ser null)
     * @param bairro bairro normalizado (pode ser null)
     * @param cep CEP normalizado (pode ser null, mas recomendado se logradouro for null)
     * @param cidadeId ID da cidade (pode ser null, mas recomendado)
     * @param estadoId ID do estado (pode ser null, mas recomendado se cidadeId for null)
     * @return Optional com o endereço encontrado ou vazio se não encontrar
     */
    @Query("SELECT e FROM Endereco e WHERE " +
           "(:tipoLogradouro IS NULL OR e.tipoLogradouro = :tipoLogradouro) AND " +
           "(:logradouro IS NULL OR (e.logradouro IS NOT NULL AND LOWER(e.logradouro) = LOWER(:logradouro))) AND " +
           "(:numero IS NULL OR (e.numero IS NOT NULL AND LOWER(e.numero) = LOWER(:numero))) AND " +
           "(:bairro IS NULL OR (e.bairro IS NOT NULL AND LOWER(e.bairro) = LOWER(:bairro))) AND " +
           "(:cep IS NULL OR (e.cep IS NOT NULL AND LOWER(e.cep) = LOWER(:cep))) AND " +
           "(:cidadeId IS NULL OR (e.cidade IS NOT NULL AND e.cidade.id = :cidadeId)) AND " +
           "(:estadoId IS NULL OR (e.estado IS NOT NULL AND e.estado.id = :estadoId)) AND " +
           "e.active = true")
    Optional<Endereco> findByFields(
            @Param("tipoLogradouro") TipoLogradouroEnum tipoLogradouro,
            @Param("logradouro") String logradouro,
            @Param("numero") String numero,
            @Param("bairro") String bairro,
            @Param("cep") String cep,
            @Param("cidadeId") UUID cidadeId,
            @Param("estadoId") UUID estadoId
    );
}
