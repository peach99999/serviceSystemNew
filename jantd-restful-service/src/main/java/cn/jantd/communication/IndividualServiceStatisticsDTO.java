package cn.jantd.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AggregateStatisticsDTO 单个服务统计
 *
 * @author xiagf
 * @date 2019/09/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("单个服务统计")
public class IndividualServiceStatisticsDTO extends BaseDTO {

    /**
     * 服务id
     */
    private String service_id;

    /**
     * 服务状态
     */
    private String status;

    /**
     * stats时间
     */
    private String stats_time;

    /**
     * 实例数
     */
    private String instance_count;

    /**
     * 运行节点数
     */
    private String running_nodes_count;

    /**
     * 调用总数
     */
    private String total_call_count;

    /**
     * cpu使用总数
     */
    private String total_cpu_used;

    /**
     * 内存使用量
     */
    private String total_memory_used;

    /**
     * 平均耗时
     */
    private String average_time_cost;


}
