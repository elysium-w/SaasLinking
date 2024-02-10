package org.saas.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.saas.admin.dao.mapper")
public class SaasLinkAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaasLinkAdminApplication.class);
    }
}
