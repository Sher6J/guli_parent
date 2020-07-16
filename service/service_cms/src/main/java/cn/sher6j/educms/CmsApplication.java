package cn.sher6j.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sher6j
 * @create 2020-07-16-11:28
 */
@SpringBootApplication
@ComponentScan({"cn.sher6j"})
@MapperScan("cn.sher6j.educms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
