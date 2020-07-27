package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-26
 */
public interface CrmBannerService extends IService<CrmBanner> {

    //显示到前台页面中
    List<CrmBanner> getAllBanner();
}
