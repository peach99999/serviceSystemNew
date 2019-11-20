package cn.jantd.util;

import cn.jantd.Result;
import cn.jantd.communication.*;
import cn.jantd.entity.ServiceInfo;
import cn.jantd.param.SubmitRegisterParam;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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


    /**
     * 读取json文件，返回json串
     *
     * @param fileName
     * @return
     */
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object queryAllServices() {
//        Result result = new Result();
        String path = ServiceMockDataUtil.class.getClassLoader().getResource("queryAllServices.json").getPath();
//        QueryServicesDTO queryServicesDTO = JSON.parseObject(readJsonFile(path), QueryServicesDTO.class);
//        result.setResult(queryServicesDTO);
        return readJsonFile(path);
    }


    public static Object queryNodeServices() {
//        Result result = new Result();
        String path = ServiceMockDataUtil.class.getClassLoader().getResource("queryNodeServices.json").getPath();
//        QueryServicesDTO queryServicesDTO = JSON.parseObject(readJsonFile(path), QueryServicesDTO.class);
//        result.setResult(queryServicesDTO);
        return readJsonFile(path);
    }

    public static Object registerService() {
        String path = ServiceMockDataUtil.class.getClassLoader().getResource("registerService.json").getPath();
        return readJsonFile(path);
    }


    public static Object getServiceDetail() {
//        Result result = new Result();
        String path = ServiceMockDataUtil.class.getClassLoader().getResource("getServiceDetail.json").getPath();
//        ServiceDetailDTO serviceDetailDTO = JSON.parseObject(readJsonFile(path), ServiceDetailDTO.class);
//        result.setResult(serviceDetailDTO);
        return readJsonFile(path);
    }

    public static Object deleteService() {
//        Result result = new Result();
//        result.setMessage("操作成功!");
//        result.setSuccess(true);
        return new JSONObject();
    }

    public static Object updateService() {
//        Result result = new Result();
//        result.setMessage("操作成功!");
//        result.setSuccess(true);
        return new JSONObject();
    }

    public static Object startService() {
//        Result result = new Result();
//        result.setMessage("操作成功!");
//        result.setSuccess(true);
        return new JSONObject();
    }

    public static Object sstopService() {
//        Result result = new Result();
//        result.setMessage("操作成功!");
//        result.setSuccess(true);
        return new JSONObject();
    }

    public static Object deployService() {
//        Result result = new Result();
//        result.setMessage("操作成功!");
//        result.setSuccess(true);
        return new JSONObject();
    }

    public static Object removeDeployService() {
//        Result result = new Result();
//        result.setMessage("操作成功!");
//        result.setSuccess(true);
        return new JSONObject();
    }

    public static Object uploadServiceFile() {
        String path = ServiceMockDataUtil.class.getClassLoader().getResource("uploadFile.json").getPath();
        return readJsonFile(path);
    }

    public static Object queryAllNodes() {
//        Result result = new Result();
        String path = ServiceMockDataUtil.class.getClassLoader().getResource("queryAllNodes.json").getPath();
//        QueryAllNodesDTO queryAllNodesDTO = JSON.parseObject(readJsonFile(path), QueryAllNodesDTO.class);
//        result.setResult(queryAllNodesDTO.getNodes());
        return readJsonFile(path);
    }


    public static Object queryNodeDetail(String nodeId) {
//        Result result = new Result();
        String path = ServiceMockDataUtil.class.getClassLoader().getResource("queryNodeDetail_" + nodeId + ".json").getPath();
//        QueryNodeDetailDTO queryNodeDetailDTO = JSON.parseObject(readJsonFile(path), QueryNodeDetailDTO.class);
//        result.setResult(queryNodeDetailDTO);
        return readJsonFile(path);
    }

    public static Object aggregateStatistics() {
//        Result result = new Result();
        String path = ServiceMockDataUtil.class.getClassLoader().getResource("aggregateStatistics.json").getPath();
//        AggregateStatisticsDTO aggregateStatisticsDTO = JSON.parseObject(readJsonFile(path), AggregateStatisticsDTO.class);
//        result.setResult(aggregateStatisticsDTO);
        return readJsonFile(path);
    }

    public static Object individualServiceStatistics() {
//        Result result = new Result();
        String path = ServiceMockDataUtil.class.getClassLoader().getResource("individualServiceStatistics.json").getPath();
//        IndividualServiceStatisticsDTO individualServiceStatisticsDTO = JSON.parseObject(readJsonFile(path), IndividualServiceStatisticsDTO.class);
//        result.setResult(individualServiceStatisticsDTO);
        return readJsonFile(path);
    }

    public static Object individualNodeStatistics() {
//        Result result = new Result();
        String path = ServiceMockDataUtil.class.getClassLoader().getResource("individualNodeStatistics.json").getPath();
//        IndividualNodeStatisticsDTO individualServiceStatisticsDTO = JSON.parseObject(readJsonFile(path), IndividualNodeStatisticsDTO.class);
//        result.setResult(individualServiceStatisticsDTO);
        return readJsonFile(path);
    }

    public static Object individualNodeServiceStatistics() {
//        Result result = new Result();
        String path = ServiceMockDataUtil.class.getClassLoader().getResource("individualNodeServiceStatistics.json").getPath();
//        IndividualNodeServiceStatisticsDTO individualNodeServiceStatisticsDTO = JSON.parseObject(readJsonFile(path), IndividualNodeServiceStatisticsDTO.class);
//        result.setResult(individualNodeServiceStatisticsDTO);
        return readJsonFile(path);
    }

    public static Object getLastLogs(String count) {
//        Result result = new Result();
        String path = ServiceMockDataUtil.class.getClassLoader().getResource("getLastLogs.json").getPath();
//        LastLogDTO lastLogDTO = JSON.parseObject(readJsonFile(path), LastLogDTO.class);
//        result.setResult(lastLogDTO);
        return readJsonFile(path);
    }

    public static Object downloadLogFile() {
//        Result result = new Result();
//        result.setMessage("操作成功!下载地址D://upFiles");
        try {
            downLoadByUrl("http://pic22.nipic.com/20120716/5666670_091558188000_2.jpg", "test.jpg", "D://upFiles");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        result.setSuccess(true);
        return new JSONObject();
    }

    public static Object submitService(SubmitRegisterParam submitRegisterParam) {
        return registerService();
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
        if (dbpath.contains("\\")) {
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

    public static void downLoadByUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(5 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);
        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        System.out.println("info:" + url + " download success");
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
