package io.backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.backend.DTO.ItemDTO;
import io.backend.entity.Item;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    Optional<User> findByEmail(String email);
    Optional<User> findByBasicToken(String basicToken);
}
