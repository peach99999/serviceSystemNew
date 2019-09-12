package cn.jantd.modules.communication.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * RemoveDeployServiceParam
 *
 * @author xiagf
 * @date 2019-09-11
 */
@Data
@ApiModel("移除一个服务的部署")
public class RemoveDeployServiceParam implements Serializable {
    private static final long serialVersionUID = 1570090175691683041L;

    @NotNull(message = "服务id不能为空")
    @ApiModelProperty("服务id")
    private String serviceId;

    @NotNull(message = "节点信息不能为空")
    @ApiModelProperty("节点信息")
    private String[] nodes;

}
