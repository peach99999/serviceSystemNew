package cn.jantd.modules.communication.service.impl;

import cn.jantd.modules.communication.entity.ServiceCategory;
import cn.jantd.modules.communication.mapper.ServiceCategoryMapper;
import cn.jantd.modules.communication.service.IServiceCategoryService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 服务分类服务实现类
 * @Author: xiagf
 * @Date: 2019-09-17
 */
@Service
public class ServiceCategoryServiceImpl extends ServiceImpl<ServiceCategoryMapper, ServiceCategory> implements IServiceCategoryService {

}
