create TABLE IF NOT EXISTS users
(
  	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
	  username VARCHAR(20) NOT NULL DEFAULT '' COMMENT '유저네임',
	  password VARCHAR(100) NOT NULL DEFAULT '' COMMENT '비밀번호',
	  full_name VARCHAR(20) NOT NULL DEFAULT '' COMMENT '유저이름',
	  email VARCHAR(30) NOT NULL DEFAULT '' COMMENT '이메일',
	  role TINYINT NOT NULL DEFAULT 0 COMMENT '권한',
	  created_at DATETIME NOT NULL COMMENT '생성 일자',
    updated_at DATETIME NOT NULL COMMENT '수정 일자',
	  deleted_at DATETIME COMMENT '삭제 일자'
);

create TABLE IF NOT EXISTS partner
(
	 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   partner_number VARCHAR(50) NOT NULL DEFAULT '' COMMENT '거래처 번호',
	 company_name VARCHAR(50) NOT NULL DEFAULT '' COMMENT '회사명',
	 ceo_name VARCHAR(50) NOT NULL DEFAULT '' COMMENT '대표자명',
	 phone_number VARCHAR(50) NOT NULL DEFAULT '' COMMENT '전화번호',
	 created_by BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '생성자 아이디',
	 created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '기록 생성 일자 및 시간',
   modified_at datetime COMMENT '기록 수정 일자 및 시간',
   deleted_at datetime COMMENT '삭제 여부',
   UNIQUE KEY PARTNER_COMPANY_NAME_WITH_USER_ID(company_name, created_by)
);

create TABLE IF NOT EXISTS partner_number_tracker
(
	 id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
   identifier char(1) NOT NULL DEFAULT '' COMMENT '파트너 번호 identifier',
	 identifier_seq SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '파트너 번호 identifier seq'
);


create TABLE IF NOT EXISTS worklog
(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  equipment_id BIGINT UNSIGNED NOT NULL default 0 COMMENT '장비 아이디',
  work_location VARCHAR(25) NOT NULL DEFAULT '' COMMENT '시/도',
  work_time smallInt NOT NULL COMMENT '시작 일자 및 시간',
  work_pay double(11,2) DEFAULT 0 COMMENT '일당',
  partner_id BIGINT UNSIGNED NOT NULL default 0  COMMENT '거래처 아이디',
  user_id BIGINT UNSIGNED NOT NULL default 0 COMMENT '유저 아이디',
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '기록 생성 일자 및 시간',
  deleted_at DATETIME COMMENT '기록 삭제 일자 및 시간'
);

create TABLE IF NOT EXISTS heavy_equipment
(
     id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
     equipment_type varchar(10) NOT NULL DEFAULT '' COMMENT '장비명',
     equipment_unit varchar(10) NOT NULL DEFAULT '' COMMENT '장비 무게 단위',
     equipment_weight BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '장비 무게',
     half_day_amount double(11,2) DEFAULT 0 COMMENT '반일 장비 가격',
     full_day_amount double(11,2) DEFAULT 0 COMMENT '하루 장비 가격',
     night_shift_amount double(11,2) DEFAULT 0 COMMENT '야간 장비 가격',
     user_id BIGINT UNSIGNED NOT NULL default 0 COMMENT '유저 아이디',
     created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
     deleted_at DATETIME COMMENT '삭제 일자'
);

insert into partner_number_tracker (identifier, identifier_seq) values ('A', 0);
insert into partner_number_tracker (identifier, identifier_seq) values ('B', 0);
insert into partner_number_tracker (identifier, identifier_seq) values ('C', 0);
insert into partner_number_tracker (identifier, identifier_seq) values ('D', 0);
insert into partner_number_tracker (identifier, identifier_seq) values ('E', 0);



