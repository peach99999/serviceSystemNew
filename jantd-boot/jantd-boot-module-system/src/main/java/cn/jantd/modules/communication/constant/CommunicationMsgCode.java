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

}
