create TABLE IF NOT EXISTS heavy_equipment
(
     id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
     equipment_type varchar(10) NOT NULL DEFAULT '' COMMENT '장비명',
     equipment_unit varchar(10) NOT NULL DEFAULT '' COMMENT '장비 무게 단위',
     equipment_weight SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '장비 무게',
     price_per_unit DECIMAL(19, 2) NOT NULL DEFAULT 0.0 COMMENT '금액',
     price_unit VARCHAR(3) NOT NULL DEFAULT 'WON' COMMENT '금액 단위',
     PRIMARY KEY (id)
)ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;위