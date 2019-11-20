package cn.jantd.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * UpdateServiceParam
 *
 * @author xiagf
 * @date 2019-09-11
 */
@Data
@ApiModel("更新服务信息")
public class UpdateServiceParam implements Serializable {
    private static final long serialVersionUID = -5296326540101761278L;

    @NotNull(message = "服务id不能为空")
    @ApiModelProperty("服务id")
    private String serviceId;

    @NotNull(message = "最小实例数不能为空")
    @ApiModelProperty("最小实例数")
    private String minInstanceCount;

    @NotNull(message = "最大实例数不能为空")
    @ApiModelProperty("最大实例数")
    private String maxInstanceCount;

    @ApiModelProperty("文件id")
    private String fileId;
}
