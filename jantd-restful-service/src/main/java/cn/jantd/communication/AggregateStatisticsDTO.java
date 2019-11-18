package cn.jantd.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AggregateStatisticsDTO 总体总计信息
 *
 * @author xiagf
 * @date 2019/09/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("总体统计信息")
public class AggregateStatisticsDTO extends BaseDTO {

    /**
     * 状态时间
     */
    private String stats_time;

    /**
     * 服务总数
     */
    private String total_service_count;

    /**
     * 部署服务数
     */
    private String deployed_service_count;

    /**
     * 运行服务数
     */
    private String running_service_count;

    /**
     * 运行服务实例数
     */
    private String running_service_instance_count;

    /**
     * 主机数
     */
    private String host_count;

    /**
     * 使用主机数
     */
    private String used_host_count;

    /**
     * 被调用总数
     */
    private String total_call_count;

    /**
     * up时间
     */
    private String up_time;


}
