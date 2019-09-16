package cn.jantd.modules.communication.service.impl;

import cn.jantd.core.api.vo.Result;
import cn.jantd.core.properties.CommunicationProperties;
import cn.jantd.modules.communication.constant.CommunicationMsgCode;
import cn.jantd.modules.communication.dto.communication.*;
import cn.jantd.modules.communication.manage.ServitizationManager;
import cn.jantd.modules.communication.param.*;
import cn.jantd.modules.communication.service.IServitizationService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description 服务化前端通信接口实现类
 * @Author xiagf
 * @date 2019-09-11
 */
@Slf4j
@Service
public class ServitizationServiceImpl implements IServitizationService {

    /**
     * 操作成功
     */
    public static final String RESPONSE_CODE_SUCCESS = "0";
    private static final String OPERATION_SUCCESS = "操作成功";

    /**
     * 操作失败
     */
    public static final String RESPONSE_CODE_FAILED = "1";

    @Autowired(required = true)
    private RestTemplate restTemplate;

    @Autowired
    private CommunicationProperties communicationProperties;

    @Autowired
    private ServitizationManager servitizationManager;

    /**
     * 查询所有服务
     *
     * @return
     */
    @Override
    public Result<QueryServicesDTO> queryAllServices() {

        Result<QueryServicesDTO> result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getQueryAllServices();
        log.info("查询所有服务，接口名[{}]:", url);
        ResponseEntity<String> queryAllServicesResult = restTemplate.getForEntity(url, String.class);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(queryAllServicesResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.QUERY_ALL_SERVICES_FAILED.getMsg());
            return result;
        }
        // 解析请求返回结果
        if (isFailed(result, queryAllServicesResult)) {
            return result;
        }

        return result;
    }

    /**
     * 查询某个节点上的服务
     *
     * @param nodeId
     * @return
     */
    @Override
    public Result<QueryServicesDTO> queryNodeServices(String nodeId) {
        Result<QueryServicesDTO> result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getQueryNodeServices();
        url = UriComponentsBuilder.fromUriString(url).queryParam("nodeid", nodeId).build().toString();
        log.info("查询某个节点上的服务，接口名[{}]:", url);
        ResponseEntity<String> queryNodeServicesResult = restTemplate.getForEntity(url, String.class);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(queryNodeServicesResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.QUERY_NODE_SERVICES_FAILED.getMsg());
            return result;
        }
        if (isFailed(result, queryNodeServicesResult)) {
            return result;
        }

        return result;
    }

    /**
     * 注册一个服务
     *
     * @param registerParam
     * @return
     */
    @Override
    public Result<String> registerService(RegisterParam registerParam) {
        Result<String> result = new Result<>();
        // 参数处理
        JSONObject json = new JSONObject();
        // 订单编号
        json.put("min_instance_count", registerParam.getMinInstanceCount());
        // 用户id
        json.put("max_instance_count", registerParam.getMaxInstanceCount());
        // 退款金额
        json.put("file_id", registerParam.getFileId());
        HttpEntity<String> entity = servitizationManager.getStringHttpEntity(json);

        // 注册一个服务接口url
        String url = communicationProperties.getRegisterService();
        log.info("注册一个服务，接口名[{}]:", url);
        // 请求返回结果
        ResponseEntity<String> registerServiceResult = restTemplate.postForEntity(url, entity, String.class);

        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(registerServiceResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.REGISTER_SERVICE_FAILED.getMsg());
            return result;
        }
        RegisterServicesDTO registerServicesDTO = JSON.parseObject(registerServiceResult.getBody(), RegisterServicesDTO.class);
        // 结果状态码
        String code = registerServicesDTO.getCode();
        // 返回响应结果描述
        String msg = registerServicesDTO.getError_description();
        // 若结果状态码不等于0,返回错误信息
        if (!RESPONSE_CODE_SUCCESS.equals(code)) {
            log.info("结果状态码:[{}]响应结果描述[{}]", code, msg);
            result.error500(msg);
            return result;
        }
        result.setResult(registerServicesDTO.getService_id());
        result.success("操作成功");
        return result;
    }

    /**
     * 查询单个服务
     *
     * @param serviceId
     * @return
     */
    @Override
    public Result<ServiceDetailDTO> getServiceDetail(String serviceId) {
        Result<ServiceDetailDTO> result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getGetServiceDetail();
        log.info("查询单个服务，接口名[{}]:", url);
        ResponseEntity<String> serviceDetailResult = restTemplate.getForEntity(url, String.class, serviceId);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(serviceDetailResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.GET_SERVICE_DETAIL_FAILED.getMsg());
            return result;
        }

        ServiceDetailDTO serviceDetailDTO = JSON.parseObject(serviceDetailResult.getBody(), ServiceDetailDTO.class);
        BaseDTO baseDTO = new BaseDTO();
        BeanUtils.copyProperties(serviceDetailDTO, baseDTO);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        result.setResult(serviceDetailDTO);
        result.success(OPERATION_SUCCESS);
        return result;
    }

    /**
     * 删除一个服务
     *
     * @param serviceId
     * @return
     */
    @Override
    public Result deleteService(String serviceId) {
        Result result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getDeleteService();
        log.info("查询删除一个服务，接口名[{}]:", url);
        try {
            restTemplate.delete(url, String.class, serviceId);
        } catch (Exception e) {
            log.info("错误信息", e.getMessage());
            result.error500(CommunicationMsgCode.DELETE_SERVICE_FAILED.getMsg());
            return result;
        }
        return Result.ok();
    }

    /**
     * 更新服务信息
     *
     * @param updateServiceParam
     * @return
     */
    @Override
    public Result updateService(UpdateServiceParam updateServiceParam) {
        Result result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getUpdateService();
        log.info("更新服务信息，接口名[{}]:", url);
        // 参数处理
        JSONObject json = new JSONObject();
        // 服务id
        json.put("service_id", updateServiceParam.getServiceId());
        // 订单编号
        json.put("min_instance_count", updateServiceParam.getMinInstanceCount());
        // 用户id
        json.put("max_instance_count", updateServiceParam.getMaxInstanceCount());
        // 退款金额
        json.put("file_id", updateServiceParam.getFileId());
        HttpEntity<String> entity = servitizationManager.getStringHttpEntity(json);
        // 请求返回结果
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class, updateServiceParam.getServiceId());

        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            result.error500(CommunicationMsgCode.UPDATE_SERVICE_FAILED.getMsg());
            return result;
        }
        BaseDTO baseDTO = JSON.parseObject(responseEntity.getBody(), BaseDTO.class);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        return Result.ok();
    }

    /**
     * 启动一个服务
     *
     * @param startStopServiceParam
     * @return
     */
    @Override
    public Result startService(StartStopServiceParam startStopServiceParam) {
        Result result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getStartService();
        log.info("启动一个服务，接口名[{}]:", url);
        // 参数处理
        JSONObject json = new JSONObject();
        // 服务id
        json.put("service_id", startStopServiceParam.getServiceId());
        HttpEntity<String> entity = servitizationManager.getStringHttpEntity(json);
        ResponseEntity<String> startServiceResult = restTemplate.postForEntity(url, entity, String.class, startStopServiceParam.getServiceId());
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(startServiceResult.getStatusCode())) {
            return Result.error(CommunicationMsgCode.START_SERVICE_FAILED.getMsg());
        }
        BaseDTO baseDTO = JSON.parseObject(startServiceResult.getBody(), BaseDTO.class);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        return Result.ok();
    }

    /**
     * 停止一个服务
     *
     * @param startStopServiceParam
     * @return
     */
    @Override
    public Result stopService(StartStopServiceParam startStopServiceParam) {
        Result result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getStopService();
        log.info("停止一个服务，接口名[{}]:", url);
        // 参数处理
        JSONObject json = new JSONObject();
        // 服务id
        json.put("service_id", startStopServiceParam.getServiceId());
        HttpEntity<String> entity = servitizationManager.getStringHttpEntity(json);
        ResponseEntity<String> stopServiceResult = restTemplate.postForEntity(url, entity, String.class, startStopServiceParam.getServiceId());
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(stopServiceResult.getStatusCode())) {
            return Result.error(CommunicationMsgCode.STOP_SERVICE_FAILED.getMsg());
        }
        BaseDTO baseDTO = JSON.parseObject(stopServiceResult.getBody(), BaseDTO.class);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        return Result.ok();
    }

    /**
     * 部署一个服务
     *
     * @param startStopServiceParam
     * @return
     */
    @Override
    public Result deployService(DeployServiceParam startStopServiceParam) {
        Result result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getDeployService();
        log.info("部署一个服务，接口名[{}]:", url);
        // 参数处理
        JSONObject json = new JSONObject();
        // 服务id
        json.put("service_id", startStopServiceParam.getServiceId());
        JSONObject nodes = new JSONObject();
        nodes.put("NODE_ID_1", startStopServiceParam.getNodes().getNODE_ID_1());
        nodes.put("NODE_ID_2", startStopServiceParam.getNodes().getNODE_ID_2());
        json.put("nodes", nodes);
        HttpEntity<String> entity = servitizationManager.getStringHttpEntity(json);
        ResponseEntity<String> deployServiceResult = restTemplate.postForEntity(url, entity, String.class, startStopServiceParam.getServiceId());
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(deployServiceResult.getStatusCode())) {
            return Result.error(CommunicationMsgCode.DEPLOY_SERVICE_FAILED.getMsg());
        }
        BaseDTO baseDTO = JSON.parseObject(deployServiceResult.getBody(), BaseDTO.class);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        return Result.ok();
    }

    /**
     * 移除一个服务的部署
     *
     * @param removeDeployServiceParam
     * @return
     */
    @Override
    public Result removeDeployService(RemoveDeployServiceParam removeDeployServiceParam) {
        Result result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getRemoveDeployService();
        log.info("移除一个服务的部署，接口名[{}]:", url);
        // 参数处理
        JSONObject removeDeployJson = new JSONObject();
        // 服务id
        removeDeployJson.put("service_id", removeDeployServiceParam.getServiceId());
        removeDeployJson.put("nodes", removeDeployServiceParam.getNodes());
        HttpEntity<String> entity = servitizationManager.getStringHttpEntity(removeDeployJson);
        ResponseEntity<String> removeDeployServiceResult = restTemplate.postForEntity(url, entity, String.class, removeDeployServiceParam.getServiceId());
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(removeDeployServiceResult.getStatusCode())) {
            return Result.error(CommunicationMsgCode.REMOVE_DEPLOY_SERVICE_FAILED.getMsg());
        }
        BaseDTO baseDTO = JSON.parseObject(removeDeployServiceResult.getBody(), BaseDTO.class);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        return Result.ok();
    }

    /**
     * 服务文件上传
     *
     * @param uploadServiceFileParam
     * @return
     */
    @Override
    public Result<Object> uploadServiceFile(UploadServiceFileParam uploadServiceFileParam) {
        Result result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getUploadServiceFile();
        log.info("服务文件上传，接口名[{}]:", url);
        // 参数处理
        JSONObject json = new JSONObject();
        // 服务id
        json.put("file_url", uploadServiceFileParam.getFileUrl());
        String requestJson = json.toJSONString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Lists.newArrayList(MediaType.MULTIPART_FORM_DATA));
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

        ResponseEntity<String> uploadServiceFileResult = restTemplate.postForEntity(url, entity, String.class);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(uploadServiceFileResult.getStatusCode())) {
            return Result.error(CommunicationMsgCode.UPLOAD_SERVICE_FFILE_FAILED.getMsg());
        }
        UploadServiceFileDTO uploadServiceFileDTO = JSON.parseObject(uploadServiceFileResult.getBody(), UploadServiceFileDTO.class);
        BaseDTO baseDTO = new BaseDTO();
        BeanUtils.copyProperties(uploadServiceFileDTO, baseDTO);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        result.setMessage(OPERATION_SUCCESS);
        result.setResult(uploadServiceFileDTO.getService_id());
        return Result.ok();
    }

    /**
     * 查询所有节点
     *
     * @return
     */
    @Override
    public Result<Object> queryAllNodes() {
        Result result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getQueryAllNodes();
        log.info("查询所有节点，接口名[{}]:", url);
        ResponseEntity<String> queryAllNodesResult = restTemplate.getForEntity(url, String.class);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(queryAllNodesResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.QUERY_ALL_NODE_FAILED.getMsg());
            return result;
        }
        QueryAllNodesDTO queryAllNodesDTO = JSON.parseObject(queryAllNodesResult.getBody(), QueryAllNodesDTO.class);
        BaseDTO baseDTO = new BaseDTO();
        BeanUtils.copyProperties(queryAllNodesDTO, baseDTO);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        result.setMessage(OPERATION_SUCCESS);
        result.setResult(queryAllNodesDTO.getNodes());
        return result;
    }

    /**
     * 查询节点信息【缓存信息】
     *
     * @param nodeId
     * @return
     */
    @Override
    public Result<QueryNodeDetailDTO> queryNodeDetail(String nodeId) {
        Result<QueryNodeDetailDTO> result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getQueryNodeDetail();
        log.info("查询节点信息【缓存信息】，接口名[{}]:", url);
        ResponseEntity<String> queryNodeDetailResult = restTemplate.getForEntity(url, String.class, nodeId);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(queryNodeDetailResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.QUERY_NODE_DETAIL_FAILED.getMsg());
            return result;
        }
        QueryNodeDetailDTO queryNodeDetailDTO = JSON.parseObject(queryNodeDetailResult.getBody(), QueryNodeDetailDTO.class);
//        QueryNodeDetailDTO queryNodeDetailDTO = JSON.parseObject(readJson("classpath:cn/jantd/modules/demo/mock/json/test.json"), QueryNodeDetailDTO.class);
        BaseDTO baseDTO = new BaseDTO();
        BeanUtils.copyProperties(queryNodeDetailDTO, baseDTO);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        result.setResult(queryNodeDetailDTO);
        result.success(OPERATION_SUCCESS);
        return result;
    }

    /**
     * 总体统计信息
     *
     * @return
     */
    @Override
    public Result<AggregateStatisticsDTO> aggregateStatistics() {
        Result result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getAggregateStatistics();
        log.info("总体统计信息，接口名[{}]:", url);
        ResponseEntity<String> aggregateStatisticsResult = restTemplate.getForEntity(url, String.class);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(aggregateStatisticsResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.AGGREGATE_STATISTICS_FAILED.getMsg());
            return result;
        }
        AggregateStatisticsDTO aggregateStatisticsDTO = JSON.parseObject(aggregateStatisticsResult.getBody(), AggregateStatisticsDTO.class);
        BaseDTO baseDTO = new BaseDTO();
        BeanUtils.copyProperties(aggregateStatisticsDTO, baseDTO);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        result.setMessage(OPERATION_SUCCESS);
        result.setResult(aggregateStatisticsDTO);
        return result;
    }

    /**
     * 单个服务统计
     *
     * @param serviceId
     * @return
     */
    @Override
    public Result<IndividualServiceStatisticsDTO> individualServiceStatistics(String serviceId) {
        Result<IndividualServiceStatisticsDTO> result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getIndividualServiceStatistics();
        log.info("单个服务统计，接口名[{}]:", url);
        ResponseEntity<String> individualServiceStatisticsResult = restTemplate.getForEntity(url, String.class, serviceId);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(individualServiceStatisticsResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.INDIVIDUAL_SERVICE_STATISTICS_FAILED.getMsg());
            return result;
        }
        IndividualServiceStatisticsDTO individualServiceStatisticsDTO = JSON.parseObject(individualServiceStatisticsResult.getBody(), IndividualServiceStatisticsDTO.class);
        BaseDTO baseDTO = new BaseDTO();
        BeanUtils.copyProperties(individualServiceStatisticsDTO, baseDTO);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        result.setResult(individualServiceStatisticsDTO);
        result.success(OPERATION_SUCCESS);
        return result;
    }

    @Override
    public Result<IndividualNodeStatisticsDTO> individualNodeStatistics(String nodeId) {
        Result<IndividualNodeStatisticsDTO> result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getIndividualNodeStatistics();
        log.info("单个节点统计信息，接口名[{}]:", url);
        ResponseEntity<String> individualNodeStatisticsResult = restTemplate.getForEntity(url, String.class, nodeId);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(individualNodeStatisticsResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.INDIVIDUAL_NODE_STATISTICS_FAILED.getMsg());
            return result;
        }
        IndividualNodeStatisticsDTO individualNodeStatisticsDTO = JSON.parseObject(individualNodeStatisticsResult.getBody(), IndividualNodeStatisticsDTO.class);
        BaseDTO baseDTO = new BaseDTO();
        BeanUtils.copyProperties(individualNodeStatisticsDTO, baseDTO);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        result.setResult(individualNodeStatisticsDTO);
        result.success(OPERATION_SUCCESS);
        return result;
    }

    /**
     * 单个节点的单个服务的统计信息
     *
     * @param nodeId
     * @param serviceId
     * @return
     */
    @Override
    public Result<IndividualNodeServiceStatisticsDTO> individualNodeServiceStatistics(String nodeId, String serviceId) {
        Result<IndividualNodeServiceStatisticsDTO> result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getIndividualNodeServiceStatistics();
        url = UriComponentsBuilder.fromUriString(url).queryParam("node_id", nodeId).build().toString();
        log.info("单个节点的单个服务的统计信息，接口名[{}]:", url);
//        // 参数处理
//        JSONObject json = new JSONObject();
//        // 服务id
//        json.put("service_id", serviceId);
//        json.put("node_id", nodeId);
//        HttpEntity<String> entity = servitizationManager.getStringHttpEntity(json);
        ResponseEntity<String> individualNodeServiceStatisticsResult = restTemplate.getForEntity(url, String.class, serviceId);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(individualNodeServiceStatisticsResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.INDIVIDUAL_NODE_SERVICE_STATISTICS_FAILED.getMsg());
            return result;
        }
        IndividualNodeServiceStatisticsDTO individualNodeServiceStatisticsDTO = JSON.parseObject(individualNodeServiceStatisticsResult.getBody(), IndividualNodeServiceStatisticsDTO.class);
        BaseDTO baseDTO = new BaseDTO();
        BeanUtils.copyProperties(individualNodeServiceStatisticsDTO, baseDTO);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        result.setResult(individualNodeServiceStatisticsDTO);
        result.success(OPERATION_SUCCESS);
        return result;
    }

    /**
     * 获取最近的日志
     *
     * @param count
     * @return
     */
    @Override
    public Result<LastLogDTO> getLastLogs(String count) {
        Result<LastLogDTO> result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getGetLastLogs();
        url = UriComponentsBuilder.fromUriString(url).queryParam("last", count).build().toString();
        log.info("获取最近的日志，接口名[{}]:", url);
        ResponseEntity<String> getLastLogsResult = restTemplate.getForEntity(url, String.class);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(getLastLogsResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.GRT_LAST_LOG_FAILED.getMsg());
            return result;
        }

        LastLogDTO lastLogDTO = JSON.parseObject(getLastLogsResult.getBody(), LastLogDTO.class);
        BaseDTO baseDTO = new BaseDTO();
        BeanUtils.copyProperties(lastLogDTO, baseDTO);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        result.setResult(lastLogDTO);
        result.success(OPERATION_SUCCESS);
        return result;
    }

    /**
     * 下载日志文件
     *
     * @return
     */
    @Override
    public Result<Object> downloadLogFile() {
        Result result = new Result<>();
        // 查询所有服接口url
        String url = communicationProperties.getDownloadLogFile();
        log.info("查询所有节点，接口名[{}]:", url);
        ResponseEntity<String> downloadLogFileResult = restTemplate.getForEntity(url, String.class);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(downloadLogFileResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.DOWNLOAD_LOG_FILR_FAILED.getMsg());
            return result;
        }
        BaseDTO baseDTO = JSON.parseObject(downloadLogFileResult.getBody(), BaseDTO.class);

        // 返回结果判断
        if (servitizationManager.requestjudgment(result, baseDTO)) {
            return result;
        }
        return Result.ok();
    }

    private boolean isFailed(Result<QueryServicesDTO> result, ResponseEntity<String> queryNodeServicesResult) {
        // 解析请求返回结果
        QueryServicesDTO queryAllServicesDTO = JSON.parseObject(queryNodeServicesResult.getBody(), QueryServicesDTO.class);
        BaseDTO baseDTO = new BaseDTO();
        BeanUtils.copyProperties(queryAllServicesDTO, baseDTO);
        // 返回结果判断
        if (servitizationManager.requestjudgment(result, queryAllServicesDTO)) {
            return true;
        }
        result.setResult(queryAllServicesDTO);
        result.success(OPERATION_SUCCESS);
        return false;
    }

    /**
     * 读取json格式文件
     *
     * @param jsonSrc
     * @return
     */
    private String readJson(String jsonSrc) {
        String json = "";
        try {
            // 换个写法，解决springboot读取jar包中文件的问题
            InputStream stream = getClass().getClassLoader().getResourceAsStream(jsonSrc.replace("classpath:", ""));
            json = IOUtils.toString(stream, "UTF-8");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

}
