package cn.jantd.modules.communication.manage.impl;


import cn.jantd.core.api.vo.Result;
import cn.jantd.modules.communication.dto.communication.BaseDTO;
import cn.jantd.modules.communication.manage.ServitizationManager;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


@Service
public class ServitizationManagerImpl implements ServitizationManager {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 操作成功
     */
    public static final String RESPONSE_CODE_SUCCESS = "0";


    /**
     * 请求参数HttpEntity
     *
     * @param json
     * @return
     */
    @Override
    public HttpEntity<String> getStringHttpEntity(JSONObject json) {
        String requestJson = json.toJSONString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        return new HttpEntity<String>(requestJson, headers);
    }

    /**
     * 请求结果判断
     *
     * @param result
     * @param baseDTO
     * @return
     */
    @Override
    public boolean requestjudgment(Result result, BaseDTO baseDTO) {
        // 结果状态码
        String code = baseDTO.getCode();
        // 若结果状态码不等于0,返回错误信息
        if (!RESPONSE_CODE_SUCCESS.equals(code)) {
            // 返回响应结果描述
            String msg = baseDTO.getError_description();
            logger.info("结果状态码:[{}]响应结果描述[{}]", code, msg);
            result.error500(msg);
            return true;
        }
        return false;
    }

}
