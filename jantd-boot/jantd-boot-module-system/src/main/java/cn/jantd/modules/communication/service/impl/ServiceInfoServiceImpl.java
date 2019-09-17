package cn.jantd.modules.communication.service.impl;

import cn.jantd.modules.communication.entity.ServiceInfo;
import cn.jantd.modules.communication.mapper.ServiceInfoMapper;
import cn.jantd.modules.communication.service.IServiceInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 服务基本信息服务实现类
 * @Author: xiagf
 * @Date: 2019-09-17
 */
@Service
public class ServiceInfoServiceImpl extends ServiceImpl<ServiceInfoMapper, ServiceInfo> implements IServiceInfoService {

}
