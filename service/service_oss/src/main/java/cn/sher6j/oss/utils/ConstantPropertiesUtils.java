package cn.sher6j.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置文件常量类
 * @author sher6j
 * @create 2020-05-17-14:39
 */
//InitializingBean接口实现当项目一启动，spring加载之后，就会执行afterPropertiesSet方法
@Component
public class ConstantPropertiesUtils implements InitializingBean {
    //读取配置文件内容，@Value属性注入注解
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoit;
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    //定义公开静态常量
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoit;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketname;
    }
}
