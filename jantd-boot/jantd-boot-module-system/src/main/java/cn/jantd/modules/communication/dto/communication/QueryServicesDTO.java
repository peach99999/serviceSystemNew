package cn.jantd.modules.communication.dto.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * QueryAllServicesDTO 查询所有服务
 *
 * @author xiagf
 * @date 2019/09/11
 */
@Data
@ApiModel("查询所有服务")
public class QueryServicesDTO extends BaseDTO {

    /**
     * 节点Id
     */
    private String node_id;

    /**
     * 服务id数组
     */
    private String[] services;
}
