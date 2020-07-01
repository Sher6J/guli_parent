package cn.sher6j.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sher6j
 * @create 2020-07-01-13:28
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //不加载数据源（不需要操作数据库）配置
@ComponentScan(basePackages = {"cn.sher6j"})
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
