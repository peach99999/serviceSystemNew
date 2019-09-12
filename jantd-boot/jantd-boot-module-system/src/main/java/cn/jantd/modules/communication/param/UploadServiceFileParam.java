package cn.jantd.modules.communication.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * UploadServiceFileParam
 *
 * @author xiagf
 * @date 2019-09-12
 */
@Data
@ApiModel("服务文件上传")
public class UploadServiceFileParam implements Serializable {
    private static final long serialVersionUID = 262191460846912951L;

    @NotNull(message = "文件地址不能为空")
    @ApiModelProperty("文件地址")
    private String fileUrl;

}
