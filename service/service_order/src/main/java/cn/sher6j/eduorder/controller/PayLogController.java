package cn.sher6j.eduorder.controller;


import cn.sher6j.commonutils.R;
import cn.sher6j.eduorder.service.PayLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author sher6j
 * @since 2020-07-21
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @ApiOperation("根据订单号生成微信支付二维码")
    @GetMapping("createQRCode/{orderNo}")
    public R createQRCode(@PathVariable String orderNo) {
        //返回信息，包含二维码地址还有其他信息
        Map map = payLogService.createQRCode(orderNo);
        System.out.println("============返回二维码map集合：" + map);
        return R.ok().data(map);
    }

    @ApiOperation("根据订单号查询订单状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("============查询订单状态map集合：" + map);
        if (map == null) return R.error().message("支付出错了！");
        if (map.get("trade_state").equals("SUCCESS")) {
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功！");
        }
        return R.ok().code(25000).message("支付中...");
    }
}

