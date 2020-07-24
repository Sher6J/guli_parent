package cn.sher6j.eduorder.service.impl;

import cn.sher6j.eduorder.entity.Order;
import cn.sher6j.eduorder.entity.PayLog;
import cn.sher6j.eduorder.mapper.PayLogMapper;
import cn.sher6j.eduorder.service.OrderService;
import cn.sher6j.eduorder.service.PayLogService;
import cn.sher6j.eduorder.utils.HttpClient;
import cn.sher6j.servicebase.exceptionhandler.GuliException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author sher6j
 * @since 2020-07-21
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    /**
     * 根据订单号生成二维码
     * @param orderNo 订单号
     * @return
     */
    @Override
    public Map createQRCode(String orderNo) {
        try {
            //根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);

            //使用map设置生成二维码需要的参数
            Map map = new HashMap();
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", order.getCourseTitle());
            map.put("out_trade_no", orderNo);
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            map.put("trade_type", "NATIVE");

            //发送HTTPclient请求，传递参数为xml格式，微信支付提供的固定地址
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb")); //根据商户key对数据进行加密
            httpClient.setHttps(true);
            httpClient.post();

            //得到请求返回结果
            String content = httpClient.getContent(); //返回内容为xml结构

            //将xml格式转换为map，该map中包含二维码地址
            Map<String, String> codeAddress = WXPayUtil.xmlToMap(content);

            //最终返回数据的封装
            Map result = new HashMap();
            result.put("out_trade_no", orderNo);
            result.put("course_id", order.getCourseId());
            result.put("total_fee", order.getTotalFee());
            result.put("result_code", codeAddress.get("result_code")); //二维码操作状态码
            result.put("code_url", codeAddress.get("code_url")); //二维码地址

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "生成二维码错误！");
        }
    }

    /**
     * 根据订单号查询订单状态
     * @param orderNo 订单号
     * @return
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置并发送请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            //4、转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加支付记录并更新订单状态
     * @param map
     */
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //从map中获取订单号
        String orderNo = map.get("out_trade_no");
        //根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        //更新订单表中订单状态
        if (order.getStatus().intValue() == 1) return;//若已经支付，则不用处理
        order.setStatus(1);
        orderService.updateById(order);

        //向支付表中添加支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());//支付完成时间
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));//订单流水号
        payLog.setAttr(JSONObject.toJSONString(map));//其他属性
        baseMapper.insert(payLog);//插入到支付日志表
    }
}
