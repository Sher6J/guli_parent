package cn.sher6j.eduorder.controller;


import cn.sher6j.commonutils.JwtUtils;
import cn.sher6j.commonutils.R;
import cn.sher6j.eduorder.entity.Order;
import cn.sher6j.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author sher6j
 * @since 2020-07-21
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("生成订单的方法")
    @PostMapping("createOrder/{courseId}")
    public R addOrder(@PathVariable String courseId, HttpServletRequest request) {
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单并返回订单号
        String orderNo = orderService.addOrder(courseId, memberIdByJwtToken);
        return R.ok().data("orderNo", orderNo);
    }

    @ApiOperation("根据订单号查询订单信息")
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }
}

