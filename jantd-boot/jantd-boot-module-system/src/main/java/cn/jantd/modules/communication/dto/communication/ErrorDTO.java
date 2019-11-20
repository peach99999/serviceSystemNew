package cn.jantd.modules.communication.dto.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * ErrorDTO 错误返回DTO
 *
 * @author xiagf
 * @date 2019/09/11
 */
@Data
@ApiModel("错误返回DTO")
public class ErrorDTO {

    /**
     * 错误编码
     */
    private String error_code;

    /**
     * 响应结果描述
     */
    private String error_description;

}
