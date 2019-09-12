package cn.jantd.modules.communication.dto.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * RunningServicesDTO 运行服务DTO
 *
 * @author xiagf
 * @date 2019/09/12
 */
@Data
@ApiModel("运行服务DTO")
public class RunningServicesDTO {

    /**
     * 服务一
     */
    private String SERVICE_ID1;

    /**
     * 服务二
     */
    private String SERVICE_ID2;
}
