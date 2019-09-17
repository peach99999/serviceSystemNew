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
 * @Description: 服务分类
 * @Author: xiagf
 * @Date: 2019-09-17
 */
@Data
@TableName("service_category")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ServiceCategory对象", description = "服务分类")
public class ServiceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称", width = 15)
    @ApiModelProperty(value = "分类名称")
	private String name;

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
