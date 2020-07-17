package cn.sher6j.msmservice.service.impl;

import cn.sher6j.msmservice.service.MsmService;
import cn.sher6j.msmservice.utils.MsmConstantUtils;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author sher6j
 * @create 2020-07-17-11:06
 */
@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 发送短信
     * @param param 包含短信信息的集合
     * @param phone 目的手机号码
     * @return 短信是否发送成功
     */
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if (StringUtils.isEmpty(phone)) return false;
        String keyId = MsmConstantUtils.ACCESS_KEY_ID;
        String secret = MsmConstantUtils.ACCESS_KEY_SECRET;
        DefaultProfile profile = DefaultProfile.getProfile("default", keyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关参数
        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST); //过时
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        //设置发送相关的参数
//        String signName = MsmConstantUtils.SIGN_NAME;
//        String templateCode = MsmConstantUtils.TEMPLATE_CODE;
        request.putQueryParameter("PhoneNumbers", phone); //手机号
        request.putQueryParameter("SignName", "六甲在线平台"); //阿里云短信服务的签名名称
        request.putQueryParameter("TemplateCode", "SMS_189520829"); //短信模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //要求传的是json格式，如{"code":234567}，所以用map封装

        //最终发送
        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean isSuccess = response.getHttpResponse().isSuccess();
            return isSuccess;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
