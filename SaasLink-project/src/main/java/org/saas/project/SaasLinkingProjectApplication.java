package org.saas.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.saas.project.dao.mapper")
public class SaasLinkingProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaasLinkingProjectApplication.class);
    }
}
