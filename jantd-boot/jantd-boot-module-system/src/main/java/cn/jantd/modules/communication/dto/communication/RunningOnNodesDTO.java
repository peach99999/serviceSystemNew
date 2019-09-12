package cn.jantd.modules.communication.dto.communication;

import lombok.Data;

import java.util.Map;

/**
 * RunningOnNodesDTO 运行节点DTO
 *
 * @author xiagf
 * @date 2019/09/11
 */
@Data
public class RunningOnNodesDTO {

    private Map<String,String> RunOnNodesMap;
}
