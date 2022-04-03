package com.workbook.crane.user.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workbook.crane.user.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmailAndDeletedAtIsNull(String email);
  Optional<User> findByUsername(String username);
}
