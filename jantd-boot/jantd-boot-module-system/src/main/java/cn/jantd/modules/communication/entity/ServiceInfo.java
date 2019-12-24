package cn.jantd.modules.communication.entity;

import cn.jantd.core.annotation.Dict;
import cn.jantd.core.poi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 服务基本信息
 * @Author: xiagf
 * @Date: 2019-09-25
 */
@Data
@TableName("service_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ServiceInfo对象", description = "服务基本信息")
public class ServiceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 服务分类ID
     */
    @Excel(name = "服务分类ID", width = 15)
    @ApiModelProperty(value = "服务分类ID")
    private String categoryId;

    /**
     * 服务分类图片
     */
    @Excel(name = "服务分类图片", width = 15)
    @ApiModelProperty(value = "服务分类图片")
    private String servicePhoto;
    

    /**
     * 服务名称
     */
    @Excel(name = "服务名称", width = 15)
    @ApiModelProperty(value = "服务名称")
    private String name;

    /**
     * 标签【中间用,隔开】
     */
    @Excel(name = "标签【中间用,隔开】", width = 15)
    @ApiModelProperty(value = "标签【中间用,隔开】")
    private String serviceLabel;

    /**
     * 设计人员名称
     */
    @Excel(name = "设计人员名称", width = 15)
    @ApiModelProperty(value = "设计人员名称")
    private String designer;

    /**
     * 设计人员ID
     */
    @Excel(name = "设计人员ID", width = 15)
    @ApiModelProperty(value = "设计人员ID")
    private String designerId;

    /**
     * 设计人员单位
     */
    @Excel(name = "设计人员单位", width = 15)
    @ApiModelProperty(value = "设计人员单位")
    private String designerDepartment;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式", width = 15)
    @ApiModelProperty(value = "联系方式")
    private String contactInformation;

    /**
     * 描述
     */
    @Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 接口描述文件名称
     */
    @Excel(name = "接口描述文件名称", width = 15)
    @ApiModelProperty(value = "接口描述文件名称")
    private String interfaceDescriptionFileName;

    /**
     * 接口描述文件路径
     */
    @Excel(name = "接口描述文件路径", width = 15)
    @ApiModelProperty(value = "接口描述文件路径")
    private String interfaceDescriptionFilePath;

    /**
     * 开发人员
     */
    @Excel(name = "开发人员", width = 15)
    @ApiModelProperty(value = "开发人员")
    @TableField(strategy = FieldStrategy.IGNORED)
    private String developer;

    /**
     * 开发人员ID
     */
    @Excel(name = "开发人员ID", width = 15)
    @ApiModelProperty(value = "开发人员ID")
    @TableField(strategy = FieldStrategy.IGNORED)
    private String developerId;

    /**
     * 设计状态0:草稿;1:提交
     */
    @Excel(name = "设计状态0:草稿;1:提交", width = 15, dicCode = "designer_status")
    @ApiModelProperty(value = "设计状态0:草稿;1:提交")
    @Dict(dicCode = "designer_status")
    private String designerStatus;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 设计提交时间
     */
    @Excel(name = "设计提交时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "设计提交时间")
    private Date designSubmissionTime;

    /**
     * 设计提交人
     */
    @Excel(name = "设计提交人", width = 15)
    @ApiModelProperty(value = "设计提交人")
    private String designSubmissionUser;

    /**
     * 设计提交人ID
     */
    @Excel(name = "设计提交人ID", width = 15)
    @ApiModelProperty(value = "设计提交人ID")
    private String designSubmissionUserId;

    /**
     * 服务实现文件名称
     */
    @Excel(name = "服务实现文件名称", width = 15)
    @ApiModelProperty(value = "服务实现文件名称")
    @TableField(strategy = FieldStrategy.IGNORED)
    private String serviceImplementationFileName;

    /**
     * 服务实现文件路径
     */
    @Excel(name = "服务实现文件路径", width = 15)
    @ApiModelProperty(value = "服务实现文件路径")
    @TableField(strategy = FieldStrategy.IGNORED)
    private String serviceImplementationFilePath;

    /**
     * 用户文档文件名称
     */
    @Excel(name = "用户文档文件名称", width = 15)
    @ApiModelProperty(value = "用户文档文件名称")
    @TableField(strategy = FieldStrategy.IGNORED)
    private String userManualFileName;

    /**
     * 用户文档文件路径
     */
    @Excel(name = "用户文档文件路径", width = 15)
    @ApiModelProperty(value = "用户文档文件路径")
    @TableField(strategy = FieldStrategy.IGNORED)
    private String userManualFilePath;

    /**
     * 使用案例文档文件名称
     */
    @Excel(name = "使用案例文档文件名称", width = 15)
    @ApiModelProperty(value = "使用案例文档文件名称")
    @TableField(strategy = FieldStrategy.IGNORED)
    private String demoFileName;

    /**
     * 使用案例文档文件路径
     */
    @Excel(name = "使用案例文档文件路径", width = 15)
    @ApiModelProperty(value = "使用案例文档文件路径")
    @TableField(strategy = FieldStrategy.IGNORED)
    private String demoFilePath;

    /**
     * 最小实例数
     */
    @Excel(name = "最小实例数", width = 15)
    @ApiModelProperty(value = "最小实例数")
    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer minInstance;

    /**
     * 最大实例数
     */
    @Excel(name = "最大实例数", width = 15)
    @ApiModelProperty(value = "最大实例数")
    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer maxInstance;

    /**
     * 开发提交时间
     */
    @Excel(name = "开发提交时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开发提交时间")
    private Date developerSubmissionTime;

    /**
     * 开发提交人
     */
    @Excel(name = "开发提交人", width = 15)
    @ApiModelProperty(value = "开发提交人")
    @TableField(strategy = FieldStrategy.IGNORED)
    private String developerSubmissionUser;

    /**
     * 开发提交人ID
     */
    @Excel(name = "开发提交人ID", width = 15)
    @ApiModelProperty(value = "开发提交人ID")
    @TableField(strategy = FieldStrategy.IGNORED)
    private String developerSubmissionUserId;

    /**
     * 开发状态 0:草稿;1:提交
     */
    @Excel(name = "开发状态 0:草稿;1:提交", width = 15, dicCode = "developer_status")
    @ApiModelProperty(value = "开发状态 0:草稿;1:提交")
    @Dict(dicCode = "developer_status")
    @TableField(strategy = FieldStrategy.IGNORED)
    private String developerStatus;

    /**
     * 部署提交时间
     */
    @Excel(name = "部署提交时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "部署提交时间")
    private Date deploySubmissionTime;

    /**
     * 部署提交人
     */
    @Excel(name = "部署提交人", width = 15)
    @ApiModelProperty(value = "部署提交人")
    private String deploySubmissionUser;

    /**
     * 部署提交人ID
     */
    @Excel(name = "部署提交人ID", width = 15)
    @ApiModelProperty(value = "部署提交人ID")
    private String deploySubmissionUserId;

    /**
     * 部署状态 0:草稿;1:提交
     */
    @Excel(name = "部署状态 0:草稿;1:提交", width = 15, dicCode = "deploy_status")
    @ApiModelProperty(value = "部署状态 0:草稿;1:提交")
    @Dict(dicCode = "deploy_status")
    private String deployStatus;

    /**
     * 删除状态（0，正常，1已删除）
     */
    @Excel(name = "删除状态（0，正常，1已删除）", width = 15)
    @ApiModelProperty(value = "删除状态（0，正常，1已删除）")
    private String delFlag;

    /**
     * 服务id
     */
    @Excel(name = "服务id", width = 15)
    @ApiModelProperty(value = "服务id")
    private String serviceId;

    /**
     * 服务调用代码框架路径
     */
    @ApiModelProperty(value = "服务调用代码框架路径")
    private String serviceCallFramePath;


    /**
     * 服务实现代码框架路径
     */
    @ApiModelProperty(value = "服务实现代码框架路径")
    private String serviceImplementFramePath;

    /**
     * 服务状态
     */
    @TableField(exist = false)
    private String serviceStates;



}
