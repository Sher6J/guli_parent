package cn.sher6j.eduservice;

import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sher6j
 * @create 2020-05-13-19:01
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.sher6j"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class);
    }
}
