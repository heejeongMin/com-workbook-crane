package com.workbook.crane.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.workbook.crane.user.application.dto.UserDto;
import com.workbook.crane.user.domain.entity.User;
import com.workbook.crane.user.domain.entity.WorkType;
import com.workbook.crane.user.domain.repository.UserRepository;
import com.workbook.crane.user.presenation.response.UserResponse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class UserServiceTest {
	
	UserService userService;
	
	@Autowired
	UserRepository userRepository;

	@BeforeAll
	void createBaseRecord() {
		userRepository.save(User.builder()
				.oauthId(9000L)
				.name("김영희")
				.birthdate("020202")
				.phoneNumber("010-1111-2222")
				.address("서울시 도봉구 창4동")
				.nationality("대한민국")
				.workType(WorkType.EMPLOYED)
				.deletedAt(null)
				.build());
		
		userRepository.save(User.builder()
				.oauthId(9001L)
				.name("박철수")
				.birthdate("010202")
				.phoneNumber("010-2222-1111")
				.address("서울시 도봉구 창1동")
				.nationality("대한민국")
				.workType(WorkType.EMPLOYED)
				.deletedAt(null)
				.build());
	}

	@Test
	void userSaveAndSearch() {
		UserDto userDto = new UserDto().builder()
				.oauthId(9003L)
				.name("이제훈")
				.birthdate("900101")
				.phoneNumber("010-3333-2222")
				.address("서울시 양천구 신정동")
				.nationality("대한민국")
				.workType(WorkType.EMPLOYED)
				.deletedAt(null)
				.build();
		
		User newUser = userRepository.save(userDto.toEntity());
		
		assertEquals(userDto.getOauthId(), newUser.getOauthId());
		
		System.out.println(userDto.getOauthId());
		Optional<User> user = userRepository.findByOauthId(newUser.getOauthId());
		System.out.println("새로 추가된 유저 : " + user.get().getOauthId());
	}
	
	@Test
	void userUpdate() {
		Optional<User> updateUser = userRepository.findByOauthId(9002L);
		
		UserDto userDto = new UserDto(updateUser.get().getOauthId(), "곽두팔", "970101", "010-3333-2222", "서울시 강서구 화곡동", updateUser.get().getNationality(), updateUser.get().getWorkType(), updateUser.get().getDeletedAt());
		updateUser.ifPresent(selectUser -> {
			selectUser.updateName(userDto.getName());
			selectUser.updateBirthdate(userDto.getBirthdate());
			selectUser.updatePhoneNumber(userDto.getPhoneNumber());
			selectUser.updateAddress(userDto.getAddress());
			selectUser.updateNationality(userDto.getNationality());
		});	
		
		System.out.println("이름 : " + userDto.toEntity().getName());
	}

	@Test
	void userDelete() {
		Long oauthId = 9000L;
		Optional<User> user = userRepository.findByOauthId(oauthId);
		if (!user.isEmpty()) {
			user.get().deleteUser();
		}
		System.out.println("유저 삭제된 시간 : " + user.get().getDeletedAt());
	}
}
