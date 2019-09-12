package cn.jantd.modules.communication.service;

import cn.jantd.core.api.vo.Result;
import cn.jantd.modules.communication.dto.communication.QueryServicesDTO;
import cn.jantd.modules.communication.dto.communication.ServiceDetailDTO;
import cn.jantd.modules.communication.param.RegisterParam;

/**
 * @Description 服务化前端通信实现接口类
 * @Author xiagf
 * @date 2019-09-11
 */
public interface IServitizationService {
    /**
     * 查询所有服务
     *
     * @return
     */
    Result<QueryServicesDTO> queryAllServices();

    /**
     * 查询某个节点上的服务
     *
     * @param nodeId
     * @return
     */
    Result<QueryServicesDTO> queryNodeServices(String nodeId);

    /**
     * 注册一个服务
     *
     * @param registerParam
     * @return
     */
    Result<String> registerService(RegisterParam registerParam);

    /**
     * 查询单个服务
     *
     * @param serviceId
     * @return
     */
    Result<ServiceDetailDTO> getServiceDetail(String serviceId);
}
