CREATE TABLE IF NOT EXISTS user
(
     id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
	 name VARCHAR(20) NOT NULL DEFAULT '' COMMENT '이름',
     birthdate VARCHAR(50) NOT NULL DEFAULT '' COMMENT '생년월일',
	 phone_number VARCHAR(50) NOT NULL DEFAULT '' COMMENT '전화번호',
	 address VARCHAR(100) NOT NULL DEFAULT '' COMMENT '주소',
	 nationality VARCHAR(50) NOT NULL DEFAULT '' COMMENT '국적',
	 work_type VARCHAR(50) NOT NULL DEFAULT '' COMMENT '고용형태',
	 deleted_at datetime NOT NULL DEFAULT '' COMMENT '삭제 여부',
	 created_at datetime NOT NULL DEFAULT '' COMMENT '기록 생성 일자 및 시간',
     modified_at datetime NOT NULL DEFAULT '' COMMENT '기록 수정 일자 및 시간'
)ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;