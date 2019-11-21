package cn.jantd.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * RegisterParam
 *
 * @author xiagf
 * @date 2019-09-11
 */
@Data
@ApiModel("注册一个服务参数")
public class RegisterParam implements Serializable {
    private static final long serialVersionUID = -4567506752646827405L;

    @NotNull(message = "最小实例数不能为空")
    @ApiModelProperty("最小实例数")
    private String min_instance_count;

    @NotNull(message = "最大实例数不能为空")
    @ApiModelProperty("最大实例数")
    private String max_instance_count;

    @NotNull(message = "文件id不能为空")
    @ApiModelProperty("文件id")
    private String file_id;
}
