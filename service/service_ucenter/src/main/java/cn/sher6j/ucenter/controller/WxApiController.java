package cn.sher6j.ucenter.controller;

import cn.sher6j.commonutils.JwtUtils;
import cn.sher6j.servicebase.exceptionhandler.GuliException;
import cn.sher6j.ucenter.entity.Member;
import cn.sher6j.ucenter.service.MemberService;
import cn.sher6j.ucenter.utils.HttpClientUtils;
import cn.sher6j.ucenter.utils.WxConstantUtils;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author sher6j
 * @create 2020-07-19-17:05
 */
@CrossOrigin
@Controller //@RestController是请求地址和返回数据，而这里不需要返回数据
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("获取扫码人信息，添加数据")
    @GetMapping("callback")
    public String callback(String code, String state) {
//        System.out.println("code:" + code);
//        System.out.println("state:" + state);

        try {
            //1.获取code值，即临时票据，类似于验证码
            //2.拿着code请求微信的固定地址，得到两个值access_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    WxConstantUtils.WX_OPEN_APP_ID,
                    WxConstantUtils.WX_OPEN_APP_SECRET,
                    code
            );
            //请求这个拼接好的地址，得到返回两个值 accsess_token 和 openid
            //使用httpclient发送请求（不用浏览器，可以模拟浏览器发送请求），得到返回请求
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
//            System.out.println("accessTokenInfo" + accessTokenInfo);
            /**
             * accessTokenInfo:
             * {"access_token":"35_eG3l……Fp7tLRCkehNw",
             * "expires_in":7200,
             * "refresh_token":"35_R7E…………vM",
             * "openid":"o3_S…………jlBY",
             * "scope":"snsapi_login",
             * "unionid":"oWg…………iw"}
             */
            //从accessTokenInfo中获取出来两个值access_token和openid
            //使用json转换工具将该json字符串转换为map
            Gson gson = new Gson();
            HashMap accessTokenMap = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) accessTokenMap.get("access_token");
            String openid = (String) accessTokenMap.get("openid");

            //把扫码人信息添加到数据库里面
            Member member = memberService.getByOpenId(openid);
            if (member == null) {
                //3.用得到的access_token和openid去请求微信提供的固定地址，获取到扫描人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                //发送get请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
//            System.out.println("userInfo:" + userInfo);
                /**
                 * userInfo:
                 * {"openid":"o3_……Y",
                 * "nickname":"……",
                 * "sex":0,
                 * "language":"zh_CN",
                 * "city":"",
                 * "province":"",
                 * "country":"",
                 * "headimgurl":"http:\/\/t……",
                 * "privilege":[],
                 * "unionid":"o……iw"}
                 */
                //获取返回userInfo即扫码人微信信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");
                String headimgurl = (String) userInfoMap.get("headimgurl");

                member = new Member();
                member.setOpenid(openid);
                member.setNickname(nickname);
                System.out.println("==========================" + nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }

            //使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token=" + jwtToken; //回到首页面并通过路径传递token字符串
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "登录失败！");
        }
    }

    @ApiOperation("生成微信扫描二维码")
    @GetMapping("login")
    public String getWxCode() {
//        String url = "https://open.weixin.qq.com/" +
//                "connect/qrconnect?appid="+ WxConstantUtils.WX_OPEN_APP_ID+"&response_type=code";

        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_url进行URLEncoder编码
        String redirectUrl = WxConstantUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        }catch(Exception e) {
        }

        String url = String.format(baseUrl, WxConstantUtils.WX_OPEN_APP_ID, redirectUrl, "sher6j");

        //请求微信地址
        return "redirect:" + url;
    }
}
