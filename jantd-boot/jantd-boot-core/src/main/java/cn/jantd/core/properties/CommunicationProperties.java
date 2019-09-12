package cn.jantd.core.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by xiagf on 2019/09/11.
 */
@Component
@Data
//@PropertySource(ignoreResourceNotFound=true,value="classpath:core-${spring.profiles.active}.yml")
public class CommunicationProperties {

    /**
     * 查询所有服务
     */
    @Value("${communication.gatewayUrl}${communication.queryAllServices}")
    private String queryAllServices;

    /**
     * 查询某个节点上的服务
     */
    @Value("${communication.gatewayUrl}${communication.queryNodeServices}")
    private String queryNodeServices;


    /**
     * 查询某个节点上的服务
     */
    @Value("${communication.gatewayUrl}${communication.registerService}")
    private String registerService;


    /**
     * 查询单个服务
     */
    @Value("${communication.gatewayUrl}${communication.getServiceDetail}")
    private String getServiceDetail;


    /**
     * 删除一个服务
     */
    @Value("${communication.gatewayUrl}${communication.deleteService}")
    private String deleteService;


    /**
     * 更新服务信息
     */
    @Value("${communication.gatewayUrl}${communication.updateService}")
    private String updateService;


    /**
     * 启动一个服务
     */

    @Value("${communication.gatewayUrl}${communication.startService}")
    private String startService;


    /**
     * 停止一个服务
     */
    @Value("${communication.gatewayUrl}${communication.stopService}")
    private String stopService;


    /**
     * 部署一个服务
     */

    @Value("${communication.gatewayUrl}${communication.deployService}")
    private String deployService;


    /**
     * 移除一个服务的部署
     */

    @Value("${communication.gatewayUrl}${communication.removeDeployService}")
    private String removeDeployService;


    /**
     * 服务文件上传
     */

    @Value("${communication.gatewayUrl}${communication.uploadServiceFile}")
    private String uploadServiceFile;


    /**
     * queryAllNodes
     */

    @Value("${communication.gatewayUrl}${communication.queryAllNodes}")
    private String queryAllNodes;

    /**
     * 查询节点信息
     */

    @Value("${communication.gatewayUrl}${communication.queryNodeDetail}")
    private String queryNodeDetail;

    /**
     * 总体统计信息
     */

    @Value("${communication.gatewayUrl}${communication.aggregateStatistics}")
    private String aggregateStatistics;


    /**
     * 单个服务统计
     */
    @Value("${communication.gatewayUrl}${communication.individualServiceStatistics}")
    private String individualServiceStatistics;

    /**
     * 单个节点统计信息
     */

    @Value("${communication.gatewayUrl}${communication.individualNodeStatistics}")
    private String individualNodeStatistics;

    /**
     * 单个节点的单个服务的统计信息
     */

    @Value("${communication.gatewayUrl}${communication.individualNodeServiceStatistics}")
    private String individualNodeServiceStatistics;

    /**
     * 获取最近的日志
     */

    @Value("${communication.gatewayUrl}${communication.getLastLogs}")
    private String getLastLogs;

    /**
     * 下载日志文件
     */

    @Value("${communication.gatewayUrl}${communication.downloadLogFile}")
    private String downloadLogFile;

}
