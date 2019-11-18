package cn.jantd.communication;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * QueryAllNodesDTO 查询所有节点
 *
 * @author xiagf
 * @date 2019/09/12
 */
@Data
@ApiModel("查询所有节点")
@EqualsAndHashCode(callSuper = false)
public class QueryAllNodesDTO extends BaseDTO {


    /**
     * 部署节点信息
     */
    private String[] nodes;


}
