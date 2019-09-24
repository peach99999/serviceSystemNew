package cn.jantd.modules.communication.entity;

import cn.jantd.core.annotation.Dict;
import cn.jantd.core.poi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 服务基本信息
 * @Author: xiagf
 * @Date: 2019-09-19
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
     * 接口描述文件路径
     */
    @Excel(name = "接口描述文件路径", width = 15)
    @ApiModelProperty(value = "接口描述文件路径")
    private String interfaceDescriptionFilePath;

    /**
     * 接口描述文件名称
     */
    @Excel(name = "接口描述文件名称", width = 15)
    @ApiModelProperty(value = "接口描述文件名称")
    private String interfaceDescriptionFileName;

    /**
     * 开发人员
     */
    @Excel(name = "开发人员", width = 15)
    @ApiModelProperty(value = "开发人员")
    private String developer;

    /**
     * 开发人员ID
     */
    @Excel(name = "开发人员ID", width = 15)
    @ApiModelProperty(value = "开发人员ID")
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
     * 服务实现ID
     */
    @Excel(name = "服务实现ID", width = 15)
    @ApiModelProperty(value = "服务实现ID")
    private String serviceImplementationFileId;

    /**
     * 用户文档ID
     */
    @Excel(name = "用户文档ID", width = 15)
    @ApiModelProperty(value = "用户文档ID")
    private String userManualFileId;

    /**
     * 使用案例文档ID
     */
    @Excel(name = "使用案例文档ID", width = 15)
    @ApiModelProperty(value = "使用案例文档ID")
    private String demoFileId;

    /**
     * 最小实例数
     */
    @Excel(name = "最小实例数", width = 15)
    @ApiModelProperty(value = "最小实例数")
    private Integer minInstance;

    /**
     * 最大实例数
     */
    @Excel(name = "最大实例数", width = 15)
    @ApiModelProperty(value = "最大实例数")
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
    private String developerSubmissionUser;

    /**
     * 开发提交人ID
     */
    @Excel(name = "开发提交人ID", width = 15)
    @ApiModelProperty(value = "开发提交人ID")
    private String developerSubmissionUserId;

    /**
     * 开发状态 0:草稿;1:提交
     */
    @Excel(name = "开发状态 0:草稿;1:提交", width = 15, dicCode = "developer_status")
    @ApiModelProperty(value = "开发状态 0:草稿;1:提交")
    @Dict(dicCode = "developer_status")
    private Boolean developerStatus;

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
    private Boolean deployStatus;

    /**
     * 服务id
     */
    @Excel(name = "服务id", width = 15)
    @ApiModelProperty(value = "服务id")
    private String serviceId;

}
