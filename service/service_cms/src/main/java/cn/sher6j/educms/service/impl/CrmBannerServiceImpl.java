package cn.sher6j.educms.service.impl;

import cn.sher6j.educms.entity.CrmBanner;
import cn.sher6j.educms.mapper.CrmBannerMapper;
import cn.sher6j.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author sher6j
 * @since 2020-07-16
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(key = "'selectIndexList'", value = "banner") //把查询数据放到缓存中
    @Override
    public List<CrmBanner> selectAllBanner() {
        //根据id进行降序排列，显示排练后的两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法，拼接SQL语句
        wrapper.last("limit 2");

        List<CrmBanner> banners = baseMapper.selectList(null);
        return banners;
    }
}
