package com.workbook.crane.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workbook.crane.user.presenation.dto.UserDto;

public interface UserRepository extends JpaRepository<User, Long> {
	UserDto findByName(String name);
}
