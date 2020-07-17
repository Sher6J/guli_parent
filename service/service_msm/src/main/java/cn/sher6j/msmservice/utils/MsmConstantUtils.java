package cn.sher6j.msmservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author sher6j
 * @create 2020-07-17-15:55
 */
@Component
public class MsmConstantUtils implements InitializingBean {
    @Value("${aliyun.msm.keyid}")
    private String keyId;

    @Value("${aliyun.msm.keysecret}")
    private String keySecret;

    @Value("${aliyun.msm.signname}")
    private String signName;

    @Value("${aliyun.msm.templatecode}")
    private String templateCode;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String SIGN_NAME;
    public static String TEMPLATE_CODE;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = this.keyId;
        ACCESS_KEY_SECRET = this.keySecret;
        SIGN_NAME = this.signName;
        TEMPLATE_CODE = this.templateCode;
    }
}
