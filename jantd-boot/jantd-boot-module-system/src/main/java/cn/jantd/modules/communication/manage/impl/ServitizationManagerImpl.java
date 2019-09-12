package cn.jantd.modules.communication.manage.impl;


import cn.jantd.modules.communication.manage.ServitizationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ServitizationManagerImpl implements ServitizationManager {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 操作成功
     */
    public static final String RESPONSE_CODE_SUCCESS = "0";

    /**
     * 操作失败
     */
    public static final String RESPONSE_CODE_FAILED = "1";


}
