package cn.jantd.modules.communication.dto.communication;

import lombok.Data;

/**
 * ServiceDetailDTO 查询单个服务
 *
 * @author xiagf
 * @date 2019/09/11
 */
@Data
public class ServiceDetailDTO extends BaseDTO {

    /**
     * 服务id
     */
    private String service_id;

    /**
     * 服务状态定义：
     * not_deployed：未部署
     * deploying：部署中
     * not_running：未运行
     * starting：启动中
     * running：正在运行
     */
    private String status;

    /**
     * 实例数
     */
    private String instance_count;

    /**
     * 最小实例数
     */
    private String min_instance_count;

    /**
     * 最大实例数
     */
    private String max_instance_count;

    /**
     * 部署节点信息
     */
    private String[] deployed_on_nodes;

    /**
     * 运行节点信息
     */
    private RunningOnNodesDTO runningOnNodesDTO;

}
