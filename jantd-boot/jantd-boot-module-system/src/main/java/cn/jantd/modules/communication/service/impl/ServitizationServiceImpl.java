package cn.jantd.modules.communication.service.impl;

import cn.jantd.core.api.vo.Result;
import cn.jantd.core.properties.CommunicationProperties;
import cn.jantd.modules.communication.constant.CommunicationMsgCode;
import cn.jantd.modules.communication.dto.communication.QueryServicesDTO;
import cn.jantd.modules.communication.dto.communication.RegisterServicesDTO;
import cn.jantd.modules.communication.param.RegisterParam;
import cn.jantd.modules.communication.service.IServitizationService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Description 服务化前端通信接口实现类
 * @Author xiagf
 * @date 2019-09-11
 */
@Slf4j
@Service
public class ServitizationServiceImpl implements IServitizationService {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 操作成功
     */
    public static final String RESPONSE_CODE_SUCCESS = "0";

    /**
     * 操作失败
     */
    public static final String RESPONSE_CODE_FAILED = "1";

    @Autowired(required=true)
    private RestTemplate restTemplate;

    @Autowired
    private CommunicationProperties communicationProperties;

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
        logger.info("查询所有服务开始，接口名[{}]:", url);
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

        logger.info("查询所有服务接口结束");
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
        logger.info("查询某个节点上的服务开始，接口名[{}]:", url);
        ResponseEntity<String> queryNodeServicesResult = restTemplate.getForEntity(url, String.class, nodeId);
        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(queryNodeServicesResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.QUERY_NODE_SERVICES_FAILED.getMsg());
            return result;
        }
        if (isFailed(result, queryNodeServicesResult)) {
            return result;
        }

        logger.info("查询某个节点上的服务结束");
        return result;
    }

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
        String requestJson = json.toJSONString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

        // 订单退款到钱包接口url
        String url = communicationProperties.getRegisterService();
        // 调用订单退款到钱包接口,请求返回结果
        ResponseEntity<String> registerServiceResult = restTemplate.postForEntity(url, entity, String.class);

        // 若返回HTTP状态码不等于200,则抛出业务异常,返回错误信息
        if (!HttpStatus.OK.equals(registerServiceResult.getStatusCode())) {
            result.error500(CommunicationMsgCode.REGISTER_SERVICE_FAILED.getMsg());
            return result;
        }
        RegisterServicesDTO registerServicesDTO = JSON.parseObject(registerServiceResult.getBody(), RegisterServicesDTO.class);
        // 返回响应结果描述
        String msg = registerServicesDTO.getError_description();
        // 结果状态码
        String code = registerServicesDTO.getCode();
        logger.info("结果状态码:[{}]响应结果描述[{}]", code, msg);
        // 若结果状态码不等于0,返回错误信息
        if (!RESPONSE_CODE_SUCCESS.equals(code)) {
            result.error500(msg);
            return result;
        }
        result.setResult(registerServicesDTO.getService_id());
        result.setSuccess(true);
        result.success("操作成功");
        return result;
    }

    private boolean isFailed(Result<QueryServicesDTO> result, ResponseEntity<String> queryNodeServicesResult) {
        // 解析请求返回结果
        QueryServicesDTO queryAllServicesDTO = JSON.parseObject(queryNodeServicesResult.getBody(), QueryServicesDTO.class);

        // 返回响应结果描述
        String msg = queryAllServicesDTO.getError_description();
        // 结果状态码
        String code = queryAllServicesDTO.getCode();
        logger.info("结果状态码:[{}]响应结果描述[{}]", code, msg);
        // 若结果状态码不等于0,返回错误信息
        if (!RESPONSE_CODE_SUCCESS.equals(code)) {
            result.error500(msg);
            return true;
        }

        result.setResult(queryAllServicesDTO);
        result.setSuccess(true);
        result.success("操作成功");
        return false;
    }
}
