CREATE TABLE IF NOT EXISTS worklog(
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
  equipment_id BIGINT UNSIGNED NOT NULL default 0 COMMENT '장비 아이디',
  partner_id BIGINT UNSIGNED NOT NULL default 0  COMMENT '거래처 아이디',
  operator_id BIGINT UNSIGNED NOT NULL default 0 COMMENT '기사 아이디',
  is_performed TINYINT(1) NOT NULL COMMENT '실제 근무 여부',
  is_payment_collected TINYINT(1) NOT NULL DEFAULT 0 COMMENT '수금 여부',
  city VARCHAR(15) NOT NULL DEFAULT '' COMMENT '시/도',
  gu VARCHAR(10) NOT NULL DEFAULT '' COMMENT '구',
  dong VARCHAR(10) NOT NULL DEFAULT '' COMMENT '동',
  start_date datetime NOT NULL COMMENT '시작 일자 및 시간',
  end_date datetime NOT NULL COMMENT '종료 일자 및 시간',
  created_at datetime NOT NULL COMMENT '기록 생성 일자 및 시간',
  modified_at datetime NOT NULL COMMENT '기록 수정 일자 및 시간'
);

CREATE TABLE IF NOT EXISTS heavy_equipment
(
     id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
     equipment_type varchar(10) NOT NULL DEFAULT '' COMMENT '장비명',
     equipment_unit varchar(10) NOT NULL DEFAULT '' COMMENT '장비 무게 단위',
     equipment_weight SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '장비 무게',
     price_per_unit DECIMAL(19, 2) NOT NULL DEFAULT 0.0 COMMENT '금액',
     price_unit VARCHAR(3) NOT NULL DEFAULT 'WON' COMMENT '금액 단위'
);


