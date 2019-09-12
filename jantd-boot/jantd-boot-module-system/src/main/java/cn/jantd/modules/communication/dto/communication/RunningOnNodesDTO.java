package cn.jantd.modules.communication.dto.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Map;

/**
 * RunningOnNodesDTO 运行节点DTO
 *
 * @author xiagf
 * @date 2019/09/11
 */
@Data
@ApiModel("运行节点DTO")
public class RunningOnNodesDTO {

    private String NODE_ID_1;

    private String NODE_ID_2;
}
