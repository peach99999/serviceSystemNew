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

    List<ServiceCategory> queryListAll();
}
