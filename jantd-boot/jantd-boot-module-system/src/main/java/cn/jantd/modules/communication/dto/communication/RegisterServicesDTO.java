package cn.jantd.modules.communication.dto.communication;

import lombok.Data;

/**
 * RegisterServicesDTO 注册一个服务
 *
 * @author xiagf
 * @date 2019/09/11
 */
@Data
public class RegisterServicesDTO extends BaseDTO {

    /**
     * 服务id
     */
    private String service_id;

}
