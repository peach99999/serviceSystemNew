package cn.jantd.modules.communication.constant;


import cn.jantd.core.constant.BaseMsgCode;

/**
 * Created by xiagf on 2019/09/11.
 */
public class CommunicationMsgCode {
    /**
     * 第三方业务信息代码格式：12CCCCD
     * 第1位：固定为1,代表业务信息
     * 第2位：固定为2,代表第三方业务端
     * 第3~6位：顺序增长,代表不同的业务
     * 第7位：0.成功信息,1.一种异常信息,2.另一种异常信息,3.第三种异常信息,以此类推
     */

    public static final BaseMsgCode QUERY_ALL_SERVICES_FAILED = new BaseMsgCode("1200011", "查询所有服务失败!");
    public static final BaseMsgCode QUERY_NODE_SERVICES_FAILED = new BaseMsgCode("1200021", "查询某个节点上的服务失败!");
    public static final BaseMsgCode REGISTER_SERVICE_FAILED = new BaseMsgCode("1200031", "注册服务失败!");
    public static final BaseMsgCode GET_SERVICE_DETAIL_FAILED = new BaseMsgCode("1200041", "查询单个服务失败!");
    public static final BaseMsgCode DELETE_SERVICE_FAILED = new BaseMsgCode("1200051", "删除服务失败!");
    public static final BaseMsgCode UPDATE_SERVICE_FAILED = new BaseMsgCode("1200061", "更新服务信息失败!");
    public static final BaseMsgCode START_SERVICE_FAILED = new BaseMsgCode("1200071", "启动服务失败!");
    public static final BaseMsgCode STOP_SERVICE_FAILED = new BaseMsgCode("1200081", "停止服务失败!");
    public static final BaseMsgCode DEPLOY_SERVICE_FAILED = new BaseMsgCode("1200091", "部署服务失败!");
    public static final BaseMsgCode REMOVE_DEPLOY_SERVICE_FAILED = new BaseMsgCode("1200101", "移除部署服务失败!");
    public static final BaseMsgCode UPLOAD_SERVICE_FFILE_FAILED = new BaseMsgCode("1200111", "服务文件上传失败!");
    public static final BaseMsgCode QUERY_ALL_NODE_FAILED = new BaseMsgCode("1200121", "查询所有节点失败!");
    public static final BaseMsgCode QUERY_NODE_DETAIL_FAILED = new BaseMsgCode("1200131", "查询节点信息失败!");
    public static final BaseMsgCode AGGREGATE_STATISTICS_FAILED = new BaseMsgCode("1200141", "查询总体统计信息失败!");
    public static final BaseMsgCode INDIVIDUAL_SERVICE_STATISTICS_FAILED = new BaseMsgCode("1200151", "查询单个服务统计信息失败!");
    public static final BaseMsgCode INDIVIDUAL_NODE_STATISTICS_FAILED = new BaseMsgCode("1200161", "查询单个节点统计信息失败!");
    public static final BaseMsgCode INDIVIDUAL_NODE_SERVICE_STATISTICS_FAILED = new BaseMsgCode("1200171", "查询单个节点的单个服务的统计信息失败!");
    public static final BaseMsgCode GRT_LAST_LOG_FAILED = new BaseMsgCode("1200181", "获取最近的日志信息失败!");
    public static final BaseMsgCode DOWNLOAD_LOG_FILR_FAILED = new BaseMsgCode("1200191", "下载日志文件失败!");

}
