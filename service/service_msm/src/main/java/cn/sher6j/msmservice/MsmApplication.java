package cn.sher6j.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sher6j
 * @create 2020-07-17-11:05
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("cn.sher6j")
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class, args);
    }
}
