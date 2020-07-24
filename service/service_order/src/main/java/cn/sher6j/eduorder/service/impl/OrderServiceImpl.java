package cn.sher6j.eduorder.service.impl;

import cn.sher6j.commonutils.ordervo.CourseVo;
import cn.sher6j.commonutils.ordervo.OrderMember;
import cn.sher6j.eduorder.client.EduClient;
import cn.sher6j.eduorder.client.UCenterClient;
import cn.sher6j.eduorder.entity.Order;
import cn.sher6j.eduorder.mapper.OrderMapper;
import cn.sher6j.eduorder.service.OrderService;
import cn.sher6j.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author sher6j
 * @since 2020-07-21
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UCenterClient uCenterClient;

    /**
     * 生成订单的方法
     * @param courseId 课程id
     * @param memberIdByJwtToken 会员id
     * @return 订单id
     */
    @Override
    public String addOrder(String courseId, String memberIdByJwtToken) {
        //通过远程调用根据用户id获取用户信息
        OrderMember memberInfo = uCenterClient.getUserInfo(memberIdByJwtToken);
        //通过远程调用根据课程id获取课程信息
        CourseVo courseInfo = eduClient.getCourseInfo(courseId);

        //创建order对象并设置数据添加数据库
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo()); //订单号
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(memberInfo.getMobile());
        order.setNickname(memberInfo.getNickname());
        order.setStatus(0); //支付状态 0：未支付 1：已支付
        order.setPayType(1); //支付类型 1：微信

        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
