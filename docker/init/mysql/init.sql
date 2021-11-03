CREATE TABLE IF NOT EXISTS example_table
(
     example_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
     example_name VARCHAR(10)  NOT NULL DEFAULT '' COMMENT 'example_name'
);

CREATE TABLE IF NOT EXISTS users
(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  oauth_id BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'kakao oauth id',
	name VARCHAR(20) NOT NULL DEFAULT '' COMMENT '이름',
  birthdate VARCHAR(50) NOT NULL DEFAULT '' COMMENT '생년월일',
	phone_number VARCHAR(50) NOT NULL DEFAULT '' COMMENT '전화번호',
	address VARCHAR(100) NOT NULL DEFAULT '' COMMENT '주소',
	nationality VARCHAR(50) NOT NULL DEFAULT '' COMMENT '국적',
	work_type VARCHAR(50) NOT NULL DEFAULT '' COMMENT '고용형태',
	deleted_at datetime COMMENT '삭제 여부',
	created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '기록 생성 일자 및 시간',
  modified_at datetime COMMENT '기록 수정 일자 및 시간'
);

CREATE TABLE IF NOT EXISTS worklog
(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  equipment_id BIGINT UNSIGNED NOT NULL default 0 COMMENT '장비 아이디',
  partner_id BIGINT UNSIGNED NOT NULL default 0  COMMENT '거래처 아이디',
  operator_id BIGINT UNSIGNED NOT NULL default 0 COMMENT '기사 아이디',
  is_performed TINYINT(1) NOT NULL COMMENT '실제 근무 여부',
  is_payment_collected TINYINT(1) NOT NULL DEFAULT 0 COMMENT '수금 여부',
  city VARCHAR(15) NOT NULL DEFAULT '' COMMENT '시/도',
  gu VARCHAR(10) NOT NULL DEFAULT '' COMMENT '구',
  dong VARCHAR(10) NOT NULL DEFAULT '' COMMENT '동',
  start_date TIMESTAMP NOT NULL COMMENT '시작 일자 및 시간',
  end_date TIMESTAMP NOT NULL COMMENT '종료 일자 및 시간',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '기록 생성 일자 및 시간',
  modified_at TIMESTAMP COMMENT '기록 수정 일자 및 시간',
  deleted_at TIMESTAMP COMMENT '기록 삭제 일자 및 시간'
);

CREATE TABLE IF NOT EXISTS heavy_equipment
(
     id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
     equipment_type varchar(10) NOT NULL DEFAULT '' COMMENT '장비명',
     equipment_unit varchar(10) NOT NULL DEFAULT '' COMMENT '장비 무게 단위',
     equipment_weight BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '장비 무게',
     price_per_unit double(19, 2) NOT NULL DEFAULT 0.0 COMMENT '금액',
     price_unit VARCHAR(3) NOT NULL DEFAULT 'WON' COMMENT '금액 단위'
);

insert into heavy_equipment
(equipment_type, equipment_unit, equipment_weight, price_per_unit, price_unit)
values
('CRANE', 'TON', '25', 300000, 'WON');

insert into heavy_equipment
(equipment_type, equipment_unit, equipment_weight, price_per_unit, price_unit)
values
('CRANE', 'TON', '50', 700000, 'WON');


