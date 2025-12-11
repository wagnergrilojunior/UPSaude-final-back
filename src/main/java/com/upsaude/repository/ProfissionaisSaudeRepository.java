package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;

public interface ProfissionaisSaudeRepository extends JpaRepository<ProfissionaisSaude, UUID> {

    Page<ProfissionaisSaude> findByTenant(Tenant tenant, Pageable pageable);

    boolean existsByCpf(String cpf);

    boolean existsByRegistroProfissionalAndConselhoIdAndUfRegistro(String registroProfissional, UUID conselhoId, String ufRegistro);

    java.util.Optional<ProfissionaisSaude> findByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID id);

    boolean existsByRg(String rg);

    boolean existsByRgAndIdNot(String rg, UUID id);

    boolean existsByCns(String cns);

    boolean existsByCnsAndIdNot(String cns, UUID id);

    boolean existsByCpfAndIdNot(String cpf, UUID id);

    java.util.Optional<ProfissionaisSaude> findByRegistroProfissionalAndConselhoIdAndUfRegistro(
            String registroProfissional, UUID conselhoId, String ufRegistro);
}
