package cn.jantd.modules.communication.controller;

import cn.jantd.core.annotation.AutoLog;
import cn.jantd.core.api.vo.Result;
import cn.jantd.modules.communication.dto.communication.*;
import cn.jantd.modules.communication.entity.ServiceInfo;
import cn.jantd.modules.communication.param.*;
import cn.jantd.modules.communication.service.IServiceInfoService;
import cn.jantd.modules.communication.service.IServitizationService;
import cn.jantd.modules.communication.util.ServiceMockDataUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 服务化前端通信接口
 * @Author xiagf
 * @date 2019-09-11
 */
@RestController
@RequestMapping("/communication")
@Api(tags = "服务化前端通信接口")
@Slf4j
public class ServitizationController {
    @Autowired
    private IServitizationService servitizationService;

    @Autowired
    private IServiceInfoService serviceInfoService;


    @AutoLog(value = "查询所有服务")
    @GetMapping(value = "/query-all-services")
    @ApiOperation("查询所有服务")
    public Result<QueryServicesDTO> queryAllServices() {
        return servitizationService.queryAllServices();
//        return ServiceMockDataUtil.queryAllServices();
    }

    @AutoLog(value = "查询某个节点上的服务")
    @GetMapping(value = "/query-node-services")
    @ApiOperation("查询某个节点上的服务")
    public Result<QueryServicesDTO> queryNodeServices(@RequestParam(name = "nodeId") String nodeId) {
        return servitizationService.queryNodeServices(nodeId);
//        return ServiceMockDataUtil.queryNodeServices();
    }

    @AutoLog(value = "注册一个服务")
    @PostMapping(value = "/register-service")
    @ApiOperation("注册一个服务")
    public Result<String> registerService(@RequestBody @Validated RegisterParam registerParam) {
        return servitizationService.registerService(registerParam);
//        return ServiceMockDataUtil.registerService();
    }

    @AutoLog(value = "查询单个服务")
    @GetMapping(value = "/get-service-detail")
    @ApiOperation("查询单个服务")
    public Result<ServiceDetailDTO> getServiceDetail(@RequestParam(name = "serviceId") String serviceId) {
        return servitizationService.getServiceDetail(serviceId);
//        return ServiceMockDataUtil.getServiceDetail();
    }

    @AutoLog(value = "删除一个服务")
    @DeleteMapping(value = "/delete-Service")
    @ApiOperation("删除一个服务")
    public Result<Object> deleteService(@RequestParam(name = "serviceId") String serviceId) {
        return servitizationService.deleteService(serviceId);
//        return ServiceMockDataUtil.deleteService();
    }

    @AutoLog(value = "更新服务信息")
    @PutMapping(value = "/update-Service")
    @ApiOperation("更新服务信息")
    public Result<Object> updateService(@RequestBody UpdateServiceParam updateServiceParam) {
        return servitizationService.updateService(updateServiceParam);
//        return ServiceMockDataUtil.updateService();
    }

    @AutoLog(value = "启动一个服务")
    @PostMapping(value = "/start-service")
    @ApiOperation("启动一个服务")
    public Result<Object> startService(@RequestBody @Validated StartStopServiceParam startStopServiceParam) {
        return servitizationService.startService(startStopServiceParam);
//        return ServiceMockDataUtil.startService();
    }

    @AutoLog(value = "停止一个服务")
    @PostMapping(value = "/stop-service")
    @ApiOperation("停止一个服务")
    public Result<Object> sstopService(@RequestBody @Validated StartStopServiceParam startStopServiceParam) {
        return servitizationService.stopService(startStopServiceParam);
//        return ServiceMockDataUtil.sstopService();
    }

    @AutoLog(value = "部署一个服务")
    @PostMapping(value = "/deploy-service")
    @ApiOperation("部署一个服务")
    public Result<Object> deployService(@RequestBody @Validated DeployServiceParam deployServiceParam) {
        return servitizationService.deployService(deployServiceParam);
//        return ServiceMockDataUtil.deployService();
    }

    @AutoLog(value = "移除一个服务的部署")
    @PostMapping(value = "/remove-deploy-service")
    @ApiOperation("移除一个服务的部署")
    public Result<Object> removeDeployService(@RequestBody @Validated RemoveDeployServiceParam removeDeployServiceParam) {
        return servitizationService.removeDeployService(removeDeployServiceParam);
//        return ServiceMockDataUtil.removeDeployService();
    }

    @AutoLog(value = "服务文件上传")
    @PostMapping(value = "/upload-service-file")
    @ApiOperation("服务文件上传")
    public Result<Object> uploadServiceFile(@RequestBody @Validated UploadServiceFileParam uploadServiceFileParam) {
        return servitizationService.uploadServiceFile(uploadServiceFileParam);
//        return ServiceMockDataUtil.uploadServiceFile();
    }

    @AutoLog(value = "查询所有节点")
    @GetMapping(value = "/query-all-node")
    @ApiOperation("查询所有节点")
    public Result<Object> queryAllNodes() {
        return servitizationService.queryAllNodes();
//        return ServiceMockDataUtil.queryAllNodes();
    }


    @AutoLog(value = "查询节点信息【缓存信息】")
    @GetMapping(value = "/query-node-detail")
    @ApiOperation("查询节点信息【缓存信息】")
    public Result<QueryNodeDetailDTO> queryNodeDetail(@RequestParam(name = "nodeId") String nodeId) {
        return servitizationService.queryNodeDetail(nodeId);
//        return ServiceMockDataUtil.queryNodeDetail(nodeId);
    }

    @AutoLog(value = "总体统计信息")
    @GetMapping(value = "/aggregate-statistics")
    @ApiOperation("总体统计信息")
    public Result<AggregateStatisticsDTO> aggregateStatistics() {
        return servitizationService.aggregateStatistics();
//        return ServiceMockDataUtil.aggregateStatistics();
    }

    @AutoLog(value = "单个服务统计")
    @GetMapping(value = "/query-individual-service-statistics")
    @ApiOperation("单个服务统计")
    public Result<IndividualServiceStatisticsDTO> individualServiceStatistics(@RequestParam(name = "serviceId") String serviceId) {
        return servitizationService.individualServiceStatistics(serviceId);
//        return ServiceMockDataUtil.individualServiceStatistics();
    }

    @AutoLog(value = "单个节点统计信息")
    @GetMapping(value = "/query-individual-node-Statistics")
    @ApiOperation("单个节点统计信息")
    public Result<IndividualNodeStatisticsDTO> individualNodeStatistics(@RequestParam(name = "nodeId") String nodeId) {
        return servitizationService.individualNodeStatistics(nodeId);
//        return ServiceMockDataUtil.individualNodeStatistics();
    }

    @AutoLog(value = "单个节点的单个服务的统计信息")
    @GetMapping(value = "/query-individual-node-service-statistics")
    @ApiOperation("单个节点的单个服务的统计信息")
    public Result<IndividualNodeServiceStatisticsDTO> individualNodeServiceStatistics(@RequestParam(name = "nodeId") String nodeId,
                                                                               @RequestParam(name = "serviceId") String serviceId) {
        return servitizationService.individualNodeServiceStatistics(nodeId,serviceId);
//        return ServiceMockDataUtil.individualNodeServiceStatistics();
    }

    @AutoLog(value = "获取最近的日志")
    @GetMapping(value = "/get-last-log")
    @ApiOperation("获取最近的日志")
    public Result<LastLogDTO> getLastLogs(@RequestParam(name = "count") String count) {
        return servitizationService.getLastLogs(count);
//        return ServiceMockDataUtil.getLastLogs(count);
    }

    @AutoLog(value = "下载日志文件")
    @GetMapping(value = "/download-Log-File")
    @ApiOperation("下载日志文件")
    public Result<Object> downloadLogFile() {
        return servitizationService.downloadLogFile();
//        return ServiceMockDataUtil.downloadLogFile();
    }
    /**
     * 提交服务
     * @param submitRegisterParam
     * @return
     */
    @AutoLog(value = "提交服务")
    @PostMapping(value = "/submit-service")
    @ApiOperation("提交服务")
    public Result<Object> submitService(@RequestBody @Validated SubmitRegisterParam submitRegisterParam){

        return servitizationService.submitService(submitRegisterParam);

        // 测试代码
//        Result result = ServiceMockDataUtil.submitService(submitRegisterParam);
//        if (!ObjectUtils.isEmpty(result.getResult())) {
//            ServiceInfo serviceInfoEntity = serviceInfoService.getById(submitRegisterParam.getId());
//            if (serviceInfoEntity == null) {
//                result.error500("未找到对应实体");
//            } else {
//                serviceInfoEntity.setDeveloperStatus("1");
//                serviceInfoEntity.setServiceId(String.valueOf(result.getResult()));
//                boolean ok = serviceInfoService.updateById(serviceInfoEntity);
//                if (ok) {
//                    result.success("修改成功!");
//                }
//            }
//        }
//        return Result.ok();
    }
}
