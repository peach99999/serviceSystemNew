package cn.jantd.modules.communication.dto.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * LastLogDTO 获取最近的日志
 *
 * @author xiagf
 * @date 2019/09/16
 */
@Data
@ApiModel("获取最近的日志")
public class LastLogDTO extends BaseDTO {

    /**
     * 节点Id
     */
    private String log_record_count;

    /**
     * 服务id数组
     */
    private String[] logs;
}
