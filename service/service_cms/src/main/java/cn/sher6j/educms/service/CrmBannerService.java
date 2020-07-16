package cn.sher6j.educms.service;

import cn.sher6j.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author sher6j
 * @since 2020-07-16
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 查询所有的banner
     * @return
     */
    List<CrmBanner> selectAllBanner();
}
