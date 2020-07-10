package org.clxmm.springsecurity02.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author clx
 * @date 2020/7/10 10:04
 */
@EnableTransactionManagement
@Configuration
@MapperScan("org.clxmm.springsecurity02.modules.**.mapper*")
public class MybatisPlusConfig {
}
