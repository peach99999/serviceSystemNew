package cn.jantd.modules.communication.service;

import cn.jantd.core.api.vo.Result;
import cn.jantd.modules.communication.dto.communication.*;
import cn.jantd.modules.communication.param.*;

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

    /**
     * 删除服务
     *
     * @param serviceId
     * @return
     */
    Result deleteService(String serviceId);

    /**
     * 更新服务信息
     *
     * @param updateServiceParam
     * @return
     */
    Result updateService(UpdateServiceParam updateServiceParam);

    /**
     * 启动一个服务
     *
     * @param startStopServiceParam
     * @return
     */
    Result startService(StartStopServiceParam startStopServiceParam);

    /**
     * 停止一个服务
     *
     * @param startStopServiceParam
     * @return
     */
    Result stopService(StartStopServiceParam startStopServiceParam);

    /**
     * 部署一个服务
     *
     * @param startStopServiceParam
     * @return
     */
    Result deployService(DeployServiceParam startStopServiceParam);

    /**
     * 移除一个服务的部署
     *
     * @param startStopServiceParam
     * @return
     */
    Result removeDeployService(RemoveDeployServiceParam startStopServiceParam);

    /**
     * 服务文件上传
     *
     * @param uploadServiceFileParam
     * @return
     */
    Result<Object> uploadServiceFile(UploadServiceFileParam uploadServiceFileParam);

    /**
     * 查询所有节点
     *
     * @return
     */
    Result<Object> queryAllNodes();

    /**
     * 查询节点信息【缓存信息】
     *
     * @param nodeId
     * @return
     */
    Result<QueryNodeDetailDTO> queryNodeDetail(String nodeId);

    /**
     * 总体统计信息
     *
     * @return
     */
    Result<AggregateStatisticsDTO> aggregateStatistics();

    /**
     * 单个服务统计
     *
     * @param serviceId
     * @return
     */
    Result<IndividualServiceStatisticsDTO> individualServiceStatistics(String serviceId);

    /**
     * 单个节点统计信息
     *
     * @param nodeId
     * @return
     */
    Result<IndividualNodeStatisticsDTO> individualNodeStatistics(String nodeId);

    /**
     * 单个节点的单个服务的统计信息
     *
     * @param nodeId
     * @param serviceId
     * @return
     */
    Result<IndividualNodeServiceStatisticsDTO> individualNodeServiceStatistics(String nodeId, String serviceId);

    /**
     * 获取最近的日志
     *
     * @param count
     * @return
     */
    Result<LastLogDTO> getLastLogs(String count);

    /**
     * 下载日志文件
     *
     * @return
     */
    Result<Object> downloadLogFile();
}
