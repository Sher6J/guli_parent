package cn.sher6j.eduorder.service.impl;

import cn.sher6j.eduorder.entity.PayLog;
import cn.sher6j.eduorder.mapper.PayLogMapper;
import cn.sher6j.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
