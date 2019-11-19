package cn.jantd;

import cn.jantd.communication.*;
import cn.jantd.param.*;
import cn.jantd.util.ServiceMockDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
public class ServiceController {


    @GetMapping(value = "/query-all-services")
    @ApiOperation("查询所有服务")
    public Result<QueryServicesDTO> queryAllServices() {
        return ServiceMockDataUtil.queryAllServices();
    }

    @GetMapping(value = "/query-node-services")
    @ApiOperation("查询某个节点上的服务")
    public Result<QueryServicesDTO> queryNodeServices(@RequestParam(name = "nodeId") String nodeId) {
        return ServiceMockDataUtil.queryNodeServices();
    }

    @PostMapping(value = "/register-service")
    @ApiOperation("注册一个服务")
    public Result<String> registerService(@RequestBody @Validated RegisterParam registerParam) {
        return ServiceMockDataUtil.registerService();
    }

    @GetMapping(value = "/get-service-detail")
    @ApiOperation("查询单个服务")
    public Result<ServiceDetailDTO> getServiceDetail(@RequestParam(name = "serviceId") String serviceId) {
        return ServiceMockDataUtil.getServiceDetail();
    }

    @DeleteMapping(value = "/delete-Service")
    @ApiOperation("删除一个服务")
    public Result<Object> deleteService(@RequestParam(name = "serviceId") String serviceId) {
        return ServiceMockDataUtil.deleteService();
    }

    @PutMapping(value = "/update-Service")
    @ApiOperation("更新服务信息")
    public Result<Object> updateService(@RequestBody UpdateServiceParam updateServiceParam) {
        return ServiceMockDataUtil.updateService();
    }

    @PostMapping(value = "/start-service")
    @ApiOperation("启动一个服务")
    public Result<Object> startService(@RequestBody @Validated StartStopServiceParam startStopServiceParam) {
        return ServiceMockDataUtil.startService();
    }

    @PostMapping(value = "/stop-service")
    @ApiOperation("停止一个服务")
    public Result<Object> sstopService(@RequestBody @Validated StartStopServiceParam startStopServiceParam) {
        return ServiceMockDataUtil.sstopService();
    }

    @PostMapping(value = "/deploy-service")
    @ApiOperation("部署一个服务")
    public Result<Object> deployService(@RequestBody @Validated DeployServiceParam deployServiceParam) {
        return ServiceMockDataUtil.deployService();
    }

    @PostMapping(value = "/remove-deploy-service")
    @ApiOperation("移除一个服务的部署")
    public Result<Object> removeDeployService(@RequestBody @Validated RemoveDeployServiceParam removeDeployServiceParam) {
        return ServiceMockDataUtil.removeDeployService();
    }

    @PostMapping(value = "/upload-service-file")
    @ApiOperation("服务文件上传")
    public Result<Object> uploadServiceFile(@RequestBody @Validated UploadServiceFileParam uploadServiceFileParam) {
        return ServiceMockDataUtil.uploadServiceFile();
    }

    @GetMapping(value = "/query-all-node")
    @ApiOperation("查询所有节点")
    public Result<Object> queryAllNodes() {
        return ServiceMockDataUtil.queryAllNodes();
    }


    @GetMapping(value = "/query-node-detail")
    @ApiOperation("查询节点信息【缓存信息】")
    public Result<QueryNodeDetailDTO> queryNodeDetail(@RequestParam(name = "nodeId") String nodeId) {
        return ServiceMockDataUtil.queryNodeDetail(nodeId);
    }

    @GetMapping(value = "/aggregate-statistics")
    @ApiOperation("总体统计信息")
    public Result<AggregateStatisticsDTO> aggregateStatistics() {
        return ServiceMockDataUtil.aggregateStatistics();
    }

    @GetMapping(value = "/query-individual-service-statistics")
    @ApiOperation("单个服务统计")
    public Result<IndividualServiceStatisticsDTO> individualServiceStatistics(@RequestParam(name = "serviceId") String serviceId) {
        return ServiceMockDataUtil.individualServiceStatistics();
    }

    @GetMapping(value = "/query-individual-node-Statistics")
    @ApiOperation("单个节点统计信息")
    public Result<IndividualNodeStatisticsDTO> individualNodeStatistics(@RequestParam(name = "nodeId") String nodeId) {
        return ServiceMockDataUtil.individualNodeStatistics();
    }

    @GetMapping(value = "/query-individual-node-service-statistics")
    @ApiOperation("单个节点的单个服务的统计信息")
    public Result<IndividualNodeServiceStatisticsDTO> individualNodeServiceStatistics(@RequestParam(name = "nodeId") String nodeId,
                                                                                      @RequestParam(name = "serviceId") String serviceId) {
        return ServiceMockDataUtil.individualNodeServiceStatistics();
    }

    @GetMapping(value = "/get-last-log")
    @ApiOperation("获取最近的日志")
    public Result<LastLogDTO> getLastLogs(@RequestParam(name = "count") String count) {
        return ServiceMockDataUtil.getLastLogs(count);
    }

    @GetMapping(value = "/download-Log-File")
    @ApiOperation("下载日志文件")
    public Result<Object> downloadLogFile() {
        return ServiceMockDataUtil.downloadLogFile();
    }

    /**
     * 提交服务
     *
     * @param submitRegisterParam
     * @return
     */
    @PostMapping(value = "/submit-service")
    @ApiOperation("提交服务")
    public Result<Object> submitService(@RequestBody @Validated SubmitRegisterParam submitRegisterParam) {


        // 测试代码
        Result result = ServiceMockDataUtil.submitService(submitRegisterParam);
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
        result.success("修改成功!");
        return Result.ok();
    }
}
