DROP PROCEDURE IF EXISTS CreateMonthlyPartitionTables;


DELIMITER $$

CREATE PROCEDURE CreateMonthlyPartitionTables(
    IN dbName VARCHAR(64),        -- 数据库名称
    IN baseTableName VARCHAR(64) -- 基表名称，例如 "order"
)
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE sqlStatement TEXT;

    -- 检查并创建数据库
    SET @checkDbSql = CONCAT('CREATE DATABASE IF NOT EXISTS ', dbName);
    PREPARE stmt FROM @checkDbSql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;

    WHILE i <= 12 DO
        -- 动态生成分表名称和字段
        SET sqlStatement = CONCAT(
            'CREATE TABLE IF NOT EXISTS `', dbName, '`.`', baseTableName, '_', CAST(i AS CHAR), '` (',
            '  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT \'主键ID\',',
            '  `order_no` VARCHAR(32) NOT NULL COMMENT \'订单号\',',
            '  `top_channel_order_no` VARCHAR(64) DEFAULT NULL COMMENT \'顶级渠道订单号(乐刷、网商在支付宝、微信的订单号)\',',
            '  `channel_order_no` VARCHAR(64) DEFAULT NULL COMMENT \'渠道订单号\',',
            '  `business_order_no` VARCHAR(64) DEFAULT NULL COMMENT \'业务系统订单号\',',
            '  `pay_way` VARCHAR(16) DEFAULT NULL COMMENT \'支付类型(balance:余额, alipay:支付宝, wechat:微信, cloudPay:云闪付)\',',
            '  `pay_type` VARCHAR(16) DEFAULT NULL COMMENT \'支付方式(activeScan:主扫, passiveScan:被扫, faceScan:扫脸，applet:小程序, app:app)\',',
            '  `pay_biz` VARCHAR(16) DEFAULT \'\' COMMENT \'支付业务 app:app支付 pos：pos支付 applet:小程序支付 api：接口支付 web：网页支付\',',
            '  `pay_type_expand` VARCHAR(255) DEFAULT NULL COMMENT \'支付方式拓展参数(如果是被扫则是收款码，如果是扫脸则是脸部特征码 等)\',',
            '  `pay_channel` VARCHAR(16) DEFAULT NULL COMMENT \'支付渠道(leshua:乐刷，alipay:支付宝, wechat: 微信, newland:新大陆, mybank:网商, pingan:平安银行)\',',
            '  `benefit_amount` DECIMAL(16,2) DEFAULT NULL COMMENT \'优惠金额(1=1元)\',',
            '  `total_amount` DECIMAL(16,2) NOT NULL COMMENT \'订单总金额(1=1元)\',',
            '  `pay_amount` DECIMAL(16,2) DEFAULT \'0.00\' COMMENT \'实付金额\',',
            '  `actual_amount` DECIMAL(16,2) NOT NULL COMMENT \'实收金额(1=1元)\',',
            '  `stage_type` VARCHAR(16) DEFAULT NULL COMMENT \'分期类型(huaBei:花呗)\',',
            '  `stage_num` INT(5) DEFAULT NULL COMMENT \'分期数\',',
            '  `order_status` INT(2) NOT NULL COMMENT \'订单状态(0:待支付, 1:支付中, 2:支付成功, 3:交易关闭, 4:部分退款, 5:全部退款)\',',
            '  `pay_time` DATETIME DEFAULT NULL COMMENT \'支付时间\',',
            '  `user_type` VARCHAR(16) NOT NULL COMMENT \'用户类型(member:会员, ordinary:普通用户)\',',
            '  `user_id` VARCHAR(64) DEFAULT NULL COMMENT \'用户id(会员是会员id，普通用户根据支付类型判断是支付宝用户id还是微信用户id)\',',
            '  `user_name` VARCHAR(64) DEFAULT NULL COMMENT \'用户名(会员是会员名，普通用户根据支付类型判断是支付宝用户名还是微信用户名)\',',
            '  `device_no` VARCHAR(32) DEFAULT NULL COMMENT \'设备编号\',',
            '  `channel_agent_code` VARCHAR(32) NOT NULL COMMENT \'渠道代理商编号\',',
            '  `pay_channel_agent_code` VARCHAR(32) DEFAULT NULL COMMENT \'实际支付通道的渠道代理商编号\',',
            '  `agent_type` VARCHAR(16) DEFAULT NULL COMMENT \'服务商类型(topAgent:顶级服务商, secondAgent:二级服务商, partner:合伙人)\',',
            '  `agent_no` VARCHAR(32) DEFAULT NULL COMMENT \'服务商类型判断(顶级服务商编号，二级服务商编号，合伙人编号)\',',
            '  `channel_merchant_no` VARCHAR(64) DEFAULT NULL COMMENT \'一般情况是渠道商户号(只有为直连支付宝时：这个值为app_auth_token)\',',
            '  `business_merchant_no` VARCHAR(64) DEFAULT NULL COMMENT \'业务商户号\',',
            '  `merchant_no` VARCHAR(32) NOT NULL COMMENT \'商户号\',',
            '  `merchant_name` VARCHAR(32) NOT NULL COMMENT \'商户名\',',
            '  `parent_shop_no` VARCHAR(32) DEFAULT NULL COMMENT \'总店编号\',',
            '  `shop_no` VARCHAR(32) DEFAULT NULL COMMENT \'门店号\',',
            '  `shop_name` VARCHAR(32) DEFAULT NULL COMMENT \'门店名\',',
            '  `group_no` VARCHAR(32) DEFAULT NULL COMMENT \'分组号\',',
            '  `group_name` VARCHAR(32) DEFAULT NULL COMMENT \'分组名\',',
            '  `cashier_no` VARCHAR(32) DEFAULT NULL COMMENT \'收银员编号\',',
            '  `cashier_name` VARCHAR(32) DEFAULT NULL COMMENT \'收银员名\',',
            '  `merchant_wx_official_account` VARCHAR(255) DEFAULT NULL COMMENT \'商户微信公众号\',',
            '  `merchant_rate` DECIMAL(10,4) DEFAULT NULL COMMENT \'商户费率\',',
            '  `currency` VARCHAR(255) DEFAULT NULL COMMENT \'货币类型(cny:人名币, usd:美元, eur:欧元， gbp:英镑, jpy:日元，krw:韩元)\',',
            '  `can_refund_amount` DECIMAL(16,2) DEFAULT NULL COMMENT \'剩余可退金额\',',
            '  `refund_num` INT(8) DEFAULT NULL COMMENT \'退款次数\',',
            '  `notify_url` VARCHAR(512) DEFAULT NULL COMMENT \'支付回调地址\',',
            '  `secret_solt` VARCHAR(128) DEFAULT NULL COMMENT \'加密盐(回调第三方签名时使用)\',',
            '  `detail_info` VARCHAR(512) DEFAULT NULL COMMENT \'订单详情信息\',',
            '  `origin` VARCHAR(16) DEFAULT NULL COMMENT \'订单来源 smallProgramPay:小程序、codeBoardPay:门店码牌、appPay:app收款、hardWarePay:收银硬件、pluginPay:收银插件、 apiPay:对接接口、othersPay: 其他\',',
            '  `init_service_fee` DECIMAL(12,6) DEFAULT \'0.000000\' COMMENT \'初始化手续费\',',
            '  `service_fee` DECIMAL(12,6) NOT NULL DEFAULT \'0.000000\' COMMENT \'支付手续费\',',
            '  `init_our_commission` DECIMAL(12,6) NOT NULL DEFAULT \'0.000000\' COMMENT \'初始化 通道给平台发放的佣金-常量，下单生成，不做更改\',',
            '  `our_commission` DECIMAL(12,6) NOT NULL DEFAULT \'0.000000\' COMMENT \'通道给平台发放的佣金\',',
            '  `init_indirect_commission` DECIMAL(12,6) DEFAULT \'0.000000\' COMMENT \'初始化，间连佣金(结算给服务商的佣金)-常量，下单生成，不做更改\',',
            '  `indirect_commission` DECIMAL(12,6) NOT NULL DEFAULT \'0.000000\' COMMENT \'间连佣金(结算给服务商的佣金)\',',
            '  `init_top_agent_commission` DECIMAL(12,6) DEFAULT \'0.000000\' COMMENT \'初始化，顶级代理商所得佣金-常量，下单生成，不做更改\',',
            '  `top_agent_commission` DECIMAL(12,6) NOT NULL DEFAULT \'0.000000\' COMMENT \'顶级代理商所得佣金\',',
            '  `init_agent_commission` DECIMAL(12,6) DEFAULT \'0.000000\' COMMENT \'初始化，代理商分润-常量，下单生成，不做更改\',',
            '  `agent_commission` DECIMAL(12,6) NOT NULL DEFAULT \'0.000000\' COMMENT \'一级代理商所得佣金\',',
            '  `init_sub_agent_commission` DECIMAL(12,6) DEFAULT \'0.000000\' COMMENT \'初始化，下级代理商所得佣金,下单生成，不做更改\',',
            '  `sub_agent_commission` DECIMAL(12,6) NOT NULL DEFAULT \'0.000000\' COMMENT \'下级代理商所得佣金\',',
            '  `init_partner_commission` DECIMAL(12,6) DEFAULT \'0.000000\' COMMENT \'初始化，合伙人所得佣金-常量，下单生成，不做更改\',',
            '  `partner_commission` DECIMAL(12,6) NOT NULL DEFAULT \'0.000000\' COMMENT \'合伙人所得佣金\',',
            '  `remark` VARCHAR(512) DEFAULT NULL COMMENT \'订单备注\',',
            '  `is_deleted` TINYINT(1) NOT NULL DEFAULT \'0\' COMMENT \'是否删除(0:未删除,1:已删除)\',',
            '  `join_activity_types` VARCHAR(500) DEFAULT NULL COMMENT \'参与活动类型 share_amount:分账活动\',',
            '  `settle_method_type` VARCHAR(50) DEFAULT NULL COMMENT \'D0,D1,T1,delayed\',',
            '  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT \'创建时间\',',
            '  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT \'修改时间\',',
            '  `init_actual_amount` DECIMAL(12,2) DEFAULT \'0.00\' COMMENT \'订单创建时的实收金额\',',
            '  `is_calculate` TINYINT(1) NOT NULL DEFAULT \'0\' COMMENT \'是否计算佣金,0未计算1已计算2已退款\',',
            '  PRIMARY KEY (`id`) USING BTREE,',
            'UNIQUE KEY `unidx_order_no` (`order_no`) USING BTREE,',
            ' KEY `idx_device_no` (`device_no`) USING BTREE,',
            ' KEY `idx_top_channel_order_no` (`top_channel_order_no`) USING BTREE,',
            ' KEY `idx_channel_order_no` (`channel_order_no`) USING BTREE,',
            ' KEY `idx_business_order_no` (`business_order_no`) USING BTREE,',
            ' KEY `idx_order_paytime_orderstatus` (`pay_time`,`order_status`),',
            ' KEY `idx_order_create_time` (`create_time`),',
            ' KEY `idx_cn_sn_mn_del_pt_os_pw` (`cashier_no`,`shop_no`,`merchant_no`,`is_deleted`,`order_status`,`pay_way`) USING BTREE,',
            ' KEY `idx_sn_mn_del_pt_os_pw` (`shop_no`,`merchant_no`,`is_deleted`,`pay_time`,`order_status`,`pay_way`) USING BTREE,',
            ' KEY `idx_mn_del_pt_os_pw` (`merchant_no`,`is_deleted`,`pay_time`,`order_status`,`pay_way`) USING BTREE,',
            ' KEY `idx_psn_mn_del_pt_os_pw` (`parent_shop_no`,`merchant_no`,`is_deleted`,`pay_time`,`order_status`,`pay_way`) USING BTREE',
            ') ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT=\'订单表\' '
        );


        -- 执行动态SQL语句
        SET @stmt = sqlStatement;  -- Store the SQL in a user-defined variable
        PREPARE stmt FROM @stmt;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

        -- 递增到下一个月份
        SET i = i + 1;
    END WHILE;
    COMMIT;
END$$

DELIMITER ;


CALL CreateMonthlyPartitionTables('archive_pay_orde_2025','archive_order')




