package cn.jantd.modules.communication.dto.communication;

import lombok.Data;

/**
 * BaseDTO 基础DTO
 *
 * @author xiagf
 * @date 2019/09/11
 */
@Data
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
