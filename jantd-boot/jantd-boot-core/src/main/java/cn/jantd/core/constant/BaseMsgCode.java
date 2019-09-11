package cn.jantd.core.constant;

import lombok.Data;

/**
 * Created by xiagf on 2019/09/11.
 */
@Data
public class BaseMsgCode {
    public BaseMsgCode(String msgCode, String msg) {
        this.msgCode = msgCode;
        this.msg = msg;
    }

    private final String msgCode;
    private final String msg;

    /**
     * 信息代码格式：ABCCCCD
     * 第1位：0,系统信息,1.业务信息
     * 第2位：0,共通,1.Admin端,2.第三方接口
     * 第3~6位：顺序增长,代表不同的业务
     * 第7位：0.成功信息,1.一种异常信息,2.另一种异常信息,3.第三种异常信息,以此类推
     */
    public static final BaseMsgCode BIZ_EXCEPTION_DEFAULT = new BaseMsgCode("1000001", "业务异常");
    public static final BaseMsgCode INPUT_INVAILD_EXCEPTION = new BaseMsgCode("1000011", "输入参数错误");
    public static final BaseMsgCode MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION = new BaseMsgCode("1000021", "上传文件超过限定大小");

    public static final BaseMsgCode SYS_EXCEPTION_DEFAULT = new BaseMsgCode("000000", "系统异常");
}
