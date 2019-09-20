package cn.jantd.modules.communication.service.impl;

import cn.jantd.modules.communication.entity.ServiceCategory;
import cn.jantd.modules.communication.mapper.ServiceCategoryMapper;
import cn.jantd.modules.communication.service.IServiceCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 服务分类服务实现类
 * @Author: xiagf
 * @Date: 2019-09-17
 */
@Service
public class ServiceCategoryServiceImpl extends ServiceImpl<ServiceCategoryMapper, ServiceCategory> implements IServiceCategoryService {

    @Autowired
    private ServiceCategoryMapper serviceCategoryMapper;

    @Override
    public List<ServiceCategory> queryListAll() {

        return serviceCategoryMapper.selectList(new QueryWrapper<ServiceCategory>().orderByAsc("create_time"));
    }
}
