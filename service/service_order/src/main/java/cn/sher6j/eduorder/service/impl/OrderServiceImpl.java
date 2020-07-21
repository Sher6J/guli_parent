package cn.sher6j.eduorder.service.impl;

import cn.sher6j.eduorder.entity.Order;
import cn.sher6j.eduorder.mapper.OrderMapper;
import cn.sher6j.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
