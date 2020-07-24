package cn.sher6j.eduorder.service;

import cn.sher6j.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author sher6j
 * @since 2020-07-21
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 根据订单号生成支付二维码
     * @param orderNo 订单号
     * @return
     */
    Map createQRCode(String orderNo);

    /**
     * 根据订单号查询订单支付状态
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 向支付表中添加记录，并更新订单状态（由未支付改为已支付）
     * @param map
     */
    void updateOrderStatus(Map<String, String> map);
}
