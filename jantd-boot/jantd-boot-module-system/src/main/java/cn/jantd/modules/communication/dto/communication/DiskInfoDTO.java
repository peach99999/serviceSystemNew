package cn.jantd.modules.communication.dto.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * DiskInfoDTO 磁盘信息
 *
 * @author xiagf
 * @date 2019/09/12
 */
@Data
@ApiModel("磁盘信息")
public class DiskInfoDTO {


    /**
     * C盘信息
     */
    private String C;

    /**
     * D盘信息
     */
    private String D;


}
