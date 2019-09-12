package cn.jantd.modules.communication.controller;

import cn.jantd.core.api.vo.Result;
import cn.jantd.modules.communication.dto.communication.QueryServicesDTO;
import cn.jantd.modules.communication.dto.communication.ServiceDetailDTO;
import cn.jantd.modules.communication.param.RegisterParam;
import cn.jantd.modules.communication.service.IServitizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/query-all-services")
    @ApiOperation("查询所有服务")
    public Result<QueryServicesDTO> queryAllServices() {
        return servitizationService.queryAllServices();
    }

    @GetMapping(value = "/query-node-services")
    @ApiOperation("查询某个节点上的服务")
    public Result<QueryServicesDTO> queryNodeServices(@RequestParam(name = "nodeId") String nodeId) {
        return servitizationService.queryNodeServices(nodeId);
    }

    @PostMapping(value = "/register-service")
    @ApiOperation("注册一个服务")
    public Result<String> registerService(@RequestBody RegisterParam registerParam) {
        return servitizationService.registerService(registerParam);
    }

    @GetMapping(value = "/ger-service-detail")
    @ApiOperation("查询单个服务")
    public Result<ServiceDetailDTO> getServiceDetail(@RequestParam(name = "serviceId") String serviceId) {
        return servitizationService.getServiceDetail(serviceId);
    }

}
