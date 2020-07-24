package cn.sher6j.eduorder.service;

import cn.sher6j.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author sher6j
 * @since 2020-07-21
 */
public interface OrderService extends IService<Order> {

    /**
     * 根据课程id和会员信息生成订单号
     * @param courseId 课程id
     * @param memberIdByJwtToken 会员id
     * @return 订单号
     */
    String addOrder(String courseId, String memberIdByJwtToken);
}
