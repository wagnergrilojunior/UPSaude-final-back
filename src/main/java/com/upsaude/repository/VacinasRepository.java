package com.upsaude.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.upsaude.entity.Vacinas;

public interface VacinasRepository extends JpaRepository<Vacinas, UUID> {}
