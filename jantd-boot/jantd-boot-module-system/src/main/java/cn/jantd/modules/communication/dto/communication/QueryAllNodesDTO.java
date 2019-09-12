package cn.jantd.modules.communication.dto.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * QueryAllNodesDTO 查询所有节点
 *
 * @author xiagf
 * @date 2019/09/12
 */
@Data
@ApiModel("查询所有节点")
public class QueryAllNodesDTO extends BaseDTO {


    /**
     * 部署节点信息
     */
    private String[] nodes;


}
