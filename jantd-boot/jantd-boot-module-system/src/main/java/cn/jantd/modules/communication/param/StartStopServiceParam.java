package cn.jantd.modules.communication.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * StartStopServiceParam
 *
 * @author xiagf
 * @date 2019-09-11
 */
@Data
@ApiModel("启动停止一个服务参数")
public class StartStopServiceParam implements Serializable {
    private static final long serialVersionUID = 8322849366623470510L;

    @NotNull(message = "服务id不能为空")
    @ApiModelProperty("服务id")
    private String serviceId;

}
