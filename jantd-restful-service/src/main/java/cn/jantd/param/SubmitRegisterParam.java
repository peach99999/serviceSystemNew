package cn.jantd.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * SubmitRegisterParam
 *
 * @author xiagf
 * @date 2019-09-27
 */
@Data
@ApiModel("提交注册一个服务参数")
public class SubmitRegisterParam implements Serializable {
    private static final long serialVersionUID = -7543407679500236445L;

    @NotNull(message = "服务基本信息id")
    @ApiModelProperty("serviceId")
    private String id;

    @NotNull(message = "最小实例数不能为空")
    @ApiModelProperty("最小实例数")
    private String minInstanceCount;

    @NotNull(message = "最大实例数不能为空")
    @ApiModelProperty("最大实例数")
    private String maxInstanceCount;

    @NotNull(message = "文件id不能为空")
    @ApiModelProperty("文件id")
    private String fileId;
}
