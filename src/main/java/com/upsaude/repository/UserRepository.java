package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {}
