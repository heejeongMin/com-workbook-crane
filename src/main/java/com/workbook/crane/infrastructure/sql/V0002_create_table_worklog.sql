create TABLE IF NOT EXISTS worklog
(
     id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
     equipment_id BIGINT UNSIGNED NOT NULL DEFAULT '' COMMENT '장비 아이디',
     partner_id BIGNINT UNSIGNED NOT NULL DEFAULT '' COMMENT '거래처 아이디',
     operator_id BIGINT UNSIGNED NOT NULL DEFAULT '' COMMENT '기사 아이디',
     is_performed TINYINT(1) NOT NULL DEFAULT 0 COMMENT '실제 근무 여부',
     is_payment_collected TINYINT(1) NOT NULL DEFAULT 0 COMMENT '수금 여부',
     city VARCHAR(15) NOT NULL DEFAULT '' COMMENT '시/도',
     gu VARCHAR(10) NOT NULL DEFAULT '' COMMENT '구',
     dong VARCHAR(10) NOT NULL DEFAULT '' COMMENT '동',
     start_date datetime NOT NULL DEFAULT '' COMMENT '시작 일자 및 시간',
     end_date datetime NOT NULL DEFAULT '' COMMENT '종료 일자 및 시간',
     created_at datetime NOT NULL DEFAULT '' COMMENT '기록 생성 일자 및 시간',
     modified_at datetime NOT NULL DEFAULT '' COMMENT '기록 수정 일자 및 시간',
     PRIMARY KEY (id)
)ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;
