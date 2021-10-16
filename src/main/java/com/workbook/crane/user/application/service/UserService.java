package com.workbook.crane.user.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workbook.crane.user.application.dto.UserDto;
import com.workbook.crane.user.domain.model.User;
import com.workbook.crane.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public UserDto createUser(UserDto userDto) {
		return userRepository.save(userDto.toEntity()).toDto();
	}

	@Transactional(readOnly = true)
	public UserDto searchUser(Long oauthId) {
		Optional<User> user = userRepository.findByOauthId(oauthId);
		return (user.isEmpty()) ? new UserDto() : user.get().toDto();
	}
	
	@Transactional
	public UserDto updateUser(Long oauthId, UserDto userDto) {
		Optional<User> updateUser = userRepository.findByOauthId(oauthId);
		
		updateUser.ifPresent(user -> {
			user.updateUser(
					userDto.getName(), 
					userDto.getBirthdate(),
					userDto.getPhoneNumber(), 
					userDto.getAddress());
		});
		
		return (updateUser.isEmpty()) ? new UserDto() : updateUser.get().toDto();
	}
	
	@Transactional
	public UserDto deleteUser(Long oauthId) {
		Optional<User> user = userRepository.findByOauthId(oauthId);
		if (!user.isEmpty()) {
			user.get().deleteUser();
		}
		return (user.isEmpty()) ? new UserDto() : user.get().toDto();
	}
}