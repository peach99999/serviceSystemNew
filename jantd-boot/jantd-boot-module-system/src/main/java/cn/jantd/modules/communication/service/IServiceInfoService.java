package cn.jantd.modules.communication.service;

import cn.jantd.modules.communication.entity.ServiceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
 * @Description: 服务基本信息服务类
 * @Author: xiagf
 * @Date: 2019-09-17
 */
public interface IServiceInfoService extends IService<ServiceInfo> {

    /**
     * 调用exe生成生成服务调用代码框架和服务实现代码文件路径并返回
     *
     * @param serviceInfo
     * @return
     */
    ServiceInfo callExe(ServiceInfo serviceInfo) throws InterruptedException, IOException;
}
