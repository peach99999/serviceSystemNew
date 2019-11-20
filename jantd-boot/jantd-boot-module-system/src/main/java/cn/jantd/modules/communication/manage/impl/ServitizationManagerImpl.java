package cn.jantd.modules.communication.manage.impl;


import cn.jantd.core.api.vo.Result;
import cn.jantd.modules.communication.dto.communication.ErrorDTO;
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


}
