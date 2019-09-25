package cn.jantd.modules.communication.dto.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * RegisterServicesDTO 注册一个服务
 *
 * @author xiagf
 * @date 2019/09/11
 */
@Data
@ApiModel("注册一个服务")
@EqualsAndHashCode(callSuper = false)
public class RegisterServicesDTO extends BaseDTO {

    /**
     * 服务id
     */
    private String service_id;

}
