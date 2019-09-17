package cn.jantd.modules.communication.entity;

import java.util.Date;
import java.time.LocalDateTime;
import java.io.Serializable;
import cn.jantd.core.poi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 服务部署
 * @Author: xiagf
 * @Date: 2019-09-17
 */
@Data
@TableName("service_desploy")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ServiceDesploy对象", description = "服务部署")
public class ServiceDesploy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 服务ID
     */
    @Excel(name = "服务ID", width = 15)
    @ApiModelProperty(value = "服务ID")
	private Long serviceId;

    /**
     * 节点ID
     */
    @Excel(name = "节点ID", width = 15)
    @ApiModelProperty(value = "节点ID")
	private String nodeId;

    /**
     * 实例数
     */
    @Excel(name = "实例数", width = 15)
    @ApiModelProperty(value = "实例数")
	private Integer instanceCount;

    /**
     * 创建时间
     */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
	private String createBy;

    /**
     * 创建人ID
     */
    @Excel(name = "创建人ID", width = 15)
    @ApiModelProperty(value = "创建人ID")
	private Long createUserid;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间", width = 15)
    @ApiModelProperty(value = "更新时间")
	private Date updateTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
	private String updateBy;

    /**
     * 更新人ID
     */
    @Excel(name = "更新人ID", width = 15)
    @ApiModelProperty(value = "更新人ID")
	private Long updateUserid;

}
