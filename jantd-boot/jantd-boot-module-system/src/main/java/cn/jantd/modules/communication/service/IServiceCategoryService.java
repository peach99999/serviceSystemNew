package cn.jantd.modules.communication.service;

import cn.jantd.modules.communication.entity.ServiceCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 服务分类服务类
 * @Author: xiagf
 * @Date: 2019-09-17
 */
public interface IServiceCategoryService extends IService<ServiceCategory> {

    /**
     * 查询所有的服务分类
     *
     * @return
     */
    List<ServiceCategory> queryListAll();

    /**
     * 更新服务基本信息服务分类图片
     *
     * @param serviceCategory
     */
    void updateServiceInPhoto(ServiceCategory serviceCategory);
}
