package cn.jantd.modules.communication.dto.communication;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * QueryNodeDetailDTO 查询节点信息
 *
 * @author xiagf
 * @date 2019/09/12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("查询节点信息")
public class QueryNodeDetailDTO extends BaseDTO {

    /**
     * 节点Id
     */
    private String node_id;

    /**
     * 主机名
     */
    private String host_name;

    /**
     * ip
     */
    private String ip;

    /**
     * cpu数量
     */
    private String cpu_count;

    /**
     * 线程数
     */
    private String thread_count;

    /**
     * cpu频率
     */
    private String cpu_frequency;

    /**
     * 内存大小
     */
    private String memory_size;

    /**
     * 磁盘信息
     */
    private JSONObject disk_size;

    /**
     * 部署服务id数组
     */
    private String[] deployed_services;

    /**
     * 运行服务id信息
     */
    private JSONObject running_services;
}
