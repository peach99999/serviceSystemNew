package cn.jantd.modules.communication.manage;

import cn.jantd.core.api.vo.Result;
import cn.jantd.modules.communication.dto.communication.ErrorDTO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;

/**
 * Created by xiagf on 2019/09/11.
 */
public interface ServitizationManager {
    /**
     * 请求参数HttpEntity
     *
     * @param json
     * @return
     */
    HttpEntity<String> getStringHttpEntity(JSONObject json);

}
