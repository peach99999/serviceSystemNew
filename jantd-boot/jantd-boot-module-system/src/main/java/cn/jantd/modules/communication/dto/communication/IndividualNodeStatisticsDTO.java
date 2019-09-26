package cn.jantd.modules.communication.dto.communication;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IndividualNodeStatisticsDTO 单个节点统计信息
 *
 * @author xiagf
 * @date 2019/09/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("单个节点统计信息")
public class IndividualNodeStatisticsDTO extends BaseDTO {

    /**
     * 节点id
     */
    private String node_id;

    /**
     * stats时间
     */
    private String stats_time;

    /**
     * 主机up时间
     */
    private String host_up_time;

    /**
     * 代理up时间
     */
    private String agent_up_time;

    /**
     * cpu可用数量
     */
    private String cpu_available;

    /**
     * 内存可用大小
     */
    private String memory_avalable;

    /**
     * 磁盘可用大小信息
     */
    private JSONObject disk_available;

    /**
     * 运行中服务数量
     */
    private String running_services_count;


}
