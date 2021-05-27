package com.workbook.crane.user.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workbook.crane.user.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByOauthId(Long oauthId);
	//List<User> findByOauthId(List<Long> oauthId);
}
