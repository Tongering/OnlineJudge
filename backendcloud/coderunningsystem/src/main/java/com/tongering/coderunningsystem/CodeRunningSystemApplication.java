package com.tongering.coderunningsystem;

import com.tongering.coderunningsystem.service.impl.CodeRunningServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeRunningSystemApplication {
    public static void main(String[] args) {
        CodeRunningServiceImpl.codepool.start();
        SpringApplication.run(CodeRunningSystemApplication.class, args);
    }
}
