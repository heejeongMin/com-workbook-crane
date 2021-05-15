package com.workbook.crane.user.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workbook.crane.user.domain.User;
import com.workbook.crane.user.domain.UserRepository;
import com.workbook.crane.user.presenation.dto.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
    //public User searchUser(long userId) {
	public UserDto searchUser(long userId) {
        return userRepository.findById(userId).orElse(null).toDto();
    }
	
	public UserDto saveUser(UserDto dto) {
		return userRepository.save(dto.toEntity()).toDto();
	}
	
	/*public UserDto findByUserName(String name) {
		return userRepository.findByName(name).orElse(null).toDto();
	}*/
	
	/*public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}*/
	
	/*public UserResponse createCustomer(UserRequest userRequest) {
	    return new UserResponse(new UserDto().toDto(userRepository.save(userRequest.getUserDto().toEntity())));
	}*/
	
	/* 기본 세팅에 있던거 
	public UserDto userService(UserDto dto) {
		return userRepository.save(dto.toEntity()).toDto();
	}*/

}