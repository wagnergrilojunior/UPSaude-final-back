package com.upsaude.repository.paciente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.TipoLogradouroEnum;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {

        Page<Endereco> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

        Page<Endereco> findByTenant(Tenant tenant, Pageable pageable);

        Page<Endereco> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

        @Query("SELECT e FROM Endereco e WHERE " +
                        "(:tipoLogradouro IS NULL OR e.tipoLogradouro = :tipoLogradouro) AND " +
                        "(:logradouro IS NULL OR (e.logradouro IS NOT NULL AND LOWER(e.logradouro) = LOWER(:logradouro))) AND "
                        +
                        "(:numero IS NULL OR (e.numero IS NOT NULL AND LOWER(e.numero) = LOWER(:numero))) AND " +
                        "(:bairro IS NULL OR (e.bairro IS NOT NULL AND LOWER(e.bairro) = LOWER(:bairro))) AND " +
                        "(:cep IS NULL OR (e.cep IS NOT NULL AND LOWER(e.cep) = LOWER(:cep))) AND " +
                        "(:cidadeId IS NULL OR (e.cidade IS NOT NULL AND e.cidade.id = :cidadeId)) AND " +
                        "(:estadoId IS NULL OR (e.estado IS NOT NULL AND e.estado.id = :estadoId)) AND " +
                        "(:tipoEndereco IS NULL OR e.tipoEndereco = :tipoEndereco) AND " +
                        "(:zona IS NULL OR e.zona = :zona) AND " +
                        "e.active = true")
        Optional<Endereco> findByFields(
                        @Param("tipoLogradouro") TipoLogradouroEnum tipoLogradouro,
                        @Param("logradouro") String logradouro,
                        @Param("numero") String numero,
                        @Param("bairro") String bairro,
                        @Param("cep") String cep,
                        @Param("cidadeId") UUID cidadeId,
                        @Param("estadoId") UUID estadoId,
                        @Param("tipoEndereco") com.upsaude.enums.TipoEnderecoEnum tipoEndereco,
                        @Param("zona") com.upsaude.enums.ZonaDomicilioEnum zona);

        @Query(value = "SELECT e.* FROM enderecos e WHERE " +
                        "(:tipoLogradouro IS NULL OR e.tipo_logradouro = CAST(:tipoLogradouro AS integer)) AND " +
                        "(:logradouro IS NULL OR (e.logradouro IS NOT NULL AND LOWER(CAST(e.logradouro AS text)) = LOWER(:logradouro))) AND "
                        +
                        "(:numero IS NULL OR (e.numero IS NOT NULL AND LOWER(CAST(e.numero AS text)) = LOWER(:numero))) AND "
                        +
                        "(:bairro IS NULL OR (e.bairro IS NOT NULL AND LOWER(CAST(e.bairro AS text)) = LOWER(:bairro))) AND "
                        +
                        "(:cep IS NULL OR (e.cep IS NOT NULL AND LOWER(CAST(e.cep AS text)) = LOWER(:cep))) AND " +
                        "(:cidadeId IS NULL OR (e.cidade_id IS NOT NULL AND e.cidade_id::text = :cidadeId)) AND " +
                        "(:estadoId IS NULL OR (e.estado_id IS NOT NULL AND e.estado_id::text = :estadoId)) AND " +
                        "(:tipoEndereco IS NULL OR e.tipo_endereco = CAST(:tipoEndereco AS integer)) AND " +
                        "(:zona IS NULL OR e.zona = CAST(:zona AS integer)) AND " +
                        "e.ativo = true", nativeQuery = true)
        List<Endereco> findByFieldsList(
                        @Param("tipoLogradouro") String tipoLogradouro,
                        @Param("logradouro") String logradouro,
                        @Param("numero") String numero,
                        @Param("bairro") String bairro,
                        @Param("cep") String cep,
                        @Param("cidadeId") String cidadeId,
                        @Param("estadoId") String estadoId,
                        @Param("tipoEndereco") String tipoEndereco,
                        @Param("zona") String zona);

        @Query(value = "SELECT COUNT(*) FROM pacientes_enderecos WHERE endereco_id = :enderecoId", nativeQuery = true)
        Long countAssociacoesPaciente(@Param("enderecoId") UUID enderecoId);
}
