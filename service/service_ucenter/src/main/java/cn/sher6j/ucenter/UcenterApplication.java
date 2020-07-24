package cn.sher6j.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * UserCenter用户中心模块
 * @author sher6j
 * @create 2020-07-17-16:45
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.sher6j"})
@MapperScan("cn.sher6j.ucenter.mapper")
@EnableDiscoveryClient
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
