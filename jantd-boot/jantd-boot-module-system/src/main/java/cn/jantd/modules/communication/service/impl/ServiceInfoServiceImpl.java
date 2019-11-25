package cn.jantd.modules.communication.service.impl;

import cn.jantd.core.constant.CoreConstant;
import cn.jantd.modules.communication.entity.ServiceInfo;
import cn.jantd.modules.communication.mapper.ServiceInfoMapper;
import cn.jantd.modules.communication.service.IServiceInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 服务基本信息服务实现类
 * @Author: xiagf
 * @Date: 2019-09-17
 */
@Service
@Slf4j
public class ServiceInfoServiceImpl extends ServiceImpl<ServiceInfoMapper, ServiceInfo> implements IServiceInfoService {

    @Value(value = "${jantd.path.upload}")
    private String uploadPath;

    @Value(value = "${jantd.path.exePath}")
    private String exePath;

    // 服务调用代码框架
    private static final String SERVICE_CALL_FRAME_NAME = "stub.zip";
    // 服务实现代码框架
    private static final String SERVICE_IMPLEMENT_FRAME_NAME = "skel.zip";

    /**
     * 调用exe生成生成服务调用代码框架和服务实现代码文件
     *
     * @param serviceInfo
     * @return
     */
    @Override
    public ServiceInfo callExe(ServiceInfo serviceInfo) throws InterruptedException, IOException {
        String bizPath = "files";
        // 文件保存路径名
        String nowDay = new SimpleDateFormat("SDF_YYYYMMDD").format(new Date());
        // 接口描述文件sdl文件路径
        String sdlPath = uploadPath + File.separator + serviceInfo.getInterfaceDescriptionFilePath();
        // 调用exe生成生成服务调用代码框架和服务实现代码文件路径
        String exeCreatPath = uploadPath + File.separator + bizPath + File.separator + nowDay;
        // db保存路径
        String dbpath = bizPath + File.separator + nowDay + File.separator;
        Process process = null;
        Runtime runtime = Runtime.getRuntime();
        String[] cmds = {exePath, sdlPath, exeCreatPath};
        System.out.println("ServiceFrameworkGenerator：exePath:" +exePath);
        log.info("ServiceFrameworkGenerator：exePath:" +exePath);
        System.out.println("ServiceFrameworkGenerator：参数:" +"sdlPath:"+sdlPath);
        log.info("ServiceFrameworkGenerator：参数:" +"sdlPath:"+sdlPath);
        System.out.println("ServiceFrameworkGenerator：参数:" +"exeCreatPath:"+exeCreatPath);
        log.info("ServiceFrameworkGenerator：参数:" +"exeCreatPath:"+exeCreatPath);
        process = runtime.exec(cmds);
        System.out.println(process.isAlive());
        int exitCode = process.waitFor();
        System.out.println("ServiceFrameworkGenerator：返回:" +"exitCode:"+exitCode);
        log.info("ServiceFrameworkGenerator：返回:" +"exitCode:"+exitCode);
        if (dbpath.contains(CoreConstant.DOUBLE_SLASH)) {
            dbpath = dbpath.replace("\\", "/");
        }
        serviceInfo.setServiceCallFramePath(dbpath + SERVICE_CALL_FRAME_NAME);
        serviceInfo.setServiceImplementFramePath(dbpath + SERVICE_IMPLEMENT_FRAME_NAME);


        return serviceInfo;
    }
}
