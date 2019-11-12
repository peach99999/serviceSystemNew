package cn.jantd.modules.communication.util;

import cn.jantd.core.api.vo.Result;
import cn.jantd.core.constant.CoreConstant;
import cn.jantd.modules.communication.dto.communication.*;
import cn.jantd.modules.communication.entity.ServiceInfo;
import cn.jantd.modules.communication.param.SubmitRegisterParam;
import cn.jantd.modules.communication.service.IServiceInfoService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 对应部门的表,处理并查找树级数据
 * <p>
 *
 * @Author xiagf
 * @Date: 2019-01-22
 */
@Slf4j
public class ServiceMockDataUtil {


    private ServiceMockDataUtil() {
    }

    @Autowired
    private IServiceInfoService serviceInfoService;


    /**
     * 读取json格式文件
     *
     * @param jsonSrc
     * @return
     */
    private static String readJson(String jsonSrc) {
        String json = "";
        try {
            // 换个写法，解决springboot读取jar包中文件的问题
            InputStream stream = ServiceMockDataUtil.class.getClassLoader().getResourceAsStream(jsonSrc.replace("classpath:", ""));
            json = IOUtils.toString(stream, "UTF-8");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }


    public static Result<QueryServicesDTO> queryAllServices() {
        Result result = new Result();
        QueryServicesDTO queryServicesDTO = JSON.parseObject(readJson("classpath:cn/jantd/modules/demo/mock/json/queryAllServices.json"), QueryServicesDTO.class);
        result.setResult(queryServicesDTO);
        return result;
    }


    public static Result<QueryServicesDTO> queryNodeServices() {
        Result result = new Result();
        QueryServicesDTO queryServicesDTO = JSON.parseObject(readJson("classpath:cn/jantd/modules/demo/mock/json/queryNodeServices.json"), QueryServicesDTO.class);
        result.setResult(queryServicesDTO);
        return result;
    }

    public static Result<String> registerService() {
        Result result = new Result();
        result.setResult("11");
        return result;
    }


    public static Result<ServiceDetailDTO> getServiceDetail() {
        Result result = new Result();
        ServiceDetailDTO serviceDetailDTO = JSON.parseObject(readJson("classpath:cn/jantd/modules/demo/mock/json/getServiceDetail.json"), ServiceDetailDTO.class);
        result.setResult(serviceDetailDTO);
        return result;
    }

    public static Result<Object> deleteService() {
        Result result = new Result();
        result.setMessage("操作成功!");
        result.setSuccess(true);
        return result;
    }

    public static Result<Object> updateService() {
        Result result = new Result();
        result.setMessage("操作成功!");
        result.setSuccess(true);
        return result;
    }

    public static Result<Object> startService() {
        Result result = new Result();
        result.setMessage("操作成功!");
        result.setSuccess(true);
        return result;
    }

    public static Result<Object> sstopService() {
        Result result = new Result();
        result.setMessage("操作成功!");
        result.setSuccess(true);
        return result;
    }

    public static Result<Object> deployService() {
        Result result = new Result();
        result.setMessage("操作成功!");
        result.setSuccess(true);
        return result;
    }

    public static Result<Object> removeDeployService() {
        Result result = new Result();
        result.setMessage("操作成功!");
        result.setSuccess(true);
        return result;
    }

    public static Result<Object> uploadServiceFile() {
        Result result = new Result();
        result.setResult("adfadsf");
        result.setMessage("操作成功!");
        result.setSuccess(true);
        return result;
    }

    public static Result<Object> queryAllNodes() {
        Result result = new Result();
        QueryAllNodesDTO queryAllNodesDTO = JSON.parseObject(readJson("classpath:cn/jantd/modules/demo/mock/json/queryAllNodes.json"), QueryAllNodesDTO.class);
        result.setResult(queryAllNodesDTO.getNodes());
        return result;
    }


    public static Result<QueryNodeDetailDTO> queryNodeDetail(String nodeId) {
        Result result = new Result();
        QueryNodeDetailDTO queryNodeDetailDTO = JSON.parseObject(readJson("classpath:cn/jantd/modules/demo/mock/json/" + "queryNodeDetail_" + nodeId + ".json"), QueryNodeDetailDTO.class);
        result.setResult(queryNodeDetailDTO);
        return result;
    }

    public static Result<AggregateStatisticsDTO> aggregateStatistics() {
        Result result = new Result();
        AggregateStatisticsDTO aggregateStatisticsDTO = JSON.parseObject(readJson("classpath:cn/jantd/modules/demo/mock/json/aggregateStatistics.json"), AggregateStatisticsDTO.class);
        result.setResult(aggregateStatisticsDTO);
        return result;
    }

    public static Result<IndividualServiceStatisticsDTO> individualServiceStatistics() {
        Result result = new Result();
        IndividualServiceStatisticsDTO individualServiceStatisticsDTO = JSON.parseObject(readJson("classpath:cn/jantd/modules/demo/mock/json/individualServiceStatistics.json"), IndividualServiceStatisticsDTO.class);
        result.setResult(individualServiceStatisticsDTO);
        return result;
    }

    public static Result<IndividualNodeStatisticsDTO> individualNodeStatistics() {
        Result result = new Result();
        IndividualNodeStatisticsDTO individualServiceStatisticsDTO = JSON.parseObject(readJson("classpath:cn/jantd/modules/demo/mock/json/individualNodeStatistics.json"), IndividualNodeStatisticsDTO.class);
        result.setResult(individualServiceStatisticsDTO);
        return result;
    }

    public static Result<IndividualNodeServiceStatisticsDTO> individualNodeServiceStatistics() {
        Result result = new Result();
        IndividualNodeServiceStatisticsDTO individualNodeServiceStatisticsDTO = JSON.parseObject(readJson("classpath:cn/jantd/modules/demo/mock/json/individualNodeServiceStatistics.json"), IndividualNodeServiceStatisticsDTO.class);
        result.setResult(individualNodeServiceStatisticsDTO);
        return result;
    }

    public static Result<LastLogDTO> getLastLogs(String count) {
        Result result = new Result();
        LastLogDTO lastLogDTO = JSON.parseObject(readJson("classpath:cn/jantd/modules/demo/mock/json/getLastLogs.json"), LastLogDTO.class);
        result.setResult(lastLogDTO);
        return result;
    }

    public static Result<Object> downloadLogFile() {
        Result result = new Result();
        result.setMessage("操作成功!");
        result.setSuccess(true);
        return result;
    }

    public static Result<Object> submitService(SubmitRegisterParam submitRegisterParam) {
        Result result = new Result<>();
        result = registerService();
        return result;
    }

    public static ServiceInfo callExe(ServiceInfo serviceInfo) {
        String bizPath = "files";
        String uploadPath = "D://upFiles";
        // 文件保存路径名
        String nowDay = new SimpleDateFormat("SDF_YYYYMMDD").format(new Date());
        String exeCreatPath = uploadPath + File.separator + bizPath + File.separator + nowDay;
        String callPaht = exeCreatPath + File.separator + "stub.zip";
        String implementPaht = exeCreatPath + File.separator + "skel.zip";
        File filePath = new File(exeCreatPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String fileName = "stub.zip";
        String fileName1 = "skel.zip";
        creatFile(filePath, fileName);

        creatFile(filePath, fileName1);

        // db保存路径
        String dbpath = bizPath + File.separator + nowDay + File.separator;
        if (dbpath.contains(CoreConstant.DOUBLE_SLASH)) {
            dbpath = dbpath.replace("\\", "/");
        }
        serviceInfo.setServiceCallFramePath(dbpath + "stub.zip");
        serviceInfo.setServiceImplementFramePath(dbpath + "skel.zip");
        return serviceInfo;
    }

    private static void creatFile(File filePath, String fileName) {
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
