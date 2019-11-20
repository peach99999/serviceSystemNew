package cn.jantd.modules.communication.dto.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IndividualNodeServiceStatisticsDTO 单个节点的单个服务的统计信息
 *
 * @author xiagf
 * @date 2019/09/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("单个节点的单个服务的统计信息")
public class IndividualNodeServiceStatisticsDTO{

    /**
     * 节点id
     */
    private String node_id;

    /**
     *服务id
     */
    private String service_id;

    /**
     * stats时间
     */
    private String stats_time;

    /**
     * 已用cpu
     */
    private String cpu_used;

    /**
     * 已用内存
     */
    private String memory_used;

    /**
     * 已用磁盘
     */
    private String disk_used;

    /**
     * 调用数
     */
    private String call_count;

    /**
     * 平均耗时
     */
    private String average_time_cost;


}
