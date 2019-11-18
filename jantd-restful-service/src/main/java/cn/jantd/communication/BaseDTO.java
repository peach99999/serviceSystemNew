package cn.jantd.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * BaseDTO 基础DTO
 *
 * @author xiagf
 * @date 2019/09/11
 */
@Data
@ApiModel("基础DTO")
public class BaseDTO {
    /**
     * 结果状态码 1:操作成功 0:操作失败
     */
    private String code;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 响应结果描述
     */
    private String error_description;

}
