package cn.jantd.modules.communication.dto.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UploadServiceFileDTO 服务文件上传
 *
 * @author xiagf
 * @date 2019/09/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("服务文件上传")
public class UploadServiceFileDTO {

    private String file_id;
}
