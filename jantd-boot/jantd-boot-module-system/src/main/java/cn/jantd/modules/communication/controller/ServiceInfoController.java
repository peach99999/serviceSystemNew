package cn.jantd.modules.communication.controller;

import cn.jantd.core.annotation.AutoLog;
import cn.jantd.core.api.vo.Result;
import cn.jantd.core.poi.def.NormalExcelConstants;
import cn.jantd.core.poi.excel.ExcelImportUtil;
import cn.jantd.core.poi.excel.entity.ExportParams;
import cn.jantd.core.poi.excel.entity.ImportParams;
import cn.jantd.core.poi.view.JantdEntityExcelViewBase;
import cn.jantd.core.system.query.QueryGenerator;
import cn.jantd.core.system.vo.LoginUser;
import cn.jantd.modules.communication.entity.ServiceInfo;
import cn.jantd.modules.communication.param.SubmitRegisterParam;
import cn.jantd.modules.communication.service.IServiceInfoService;
import cn.jantd.modules.communication.util.ServiceMockDataUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 服务基本信息
 * @Author: xiagf
 * @Date: 2019-09-17
 */
@Slf4j
@Api(tags = "服务基本信息")
@RestController
@RequestMapping("/serviceInfo")
public class ServiceInfoController {
    @Autowired
    private IServiceInfoService serviceInfoService;

    /**
     * 分页列表查询
     *
     * @param serviceInfo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "服务基本信息-分页列表查询")
    @ApiOperation(value = "服务基本信息-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ServiceInfo>> queryPageList(ServiceInfo serviceInfo,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        Result<IPage<ServiceInfo>> result = new Result<>();
        QueryWrapper<ServiceInfo> queryWrapper = QueryGenerator.initQueryWrapper(serviceInfo, req.getParameterMap());
        Page<ServiceInfo> page = new Page<ServiceInfo>(pageNo, pageSize);
        IPage<ServiceInfo> pageList = serviceInfoService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 分页列表查询
     *
     * @param serviceInfo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "服务基本信息-分页列表查询")
    @ApiOperation(value = "服务基本信息-分页列表查询")
    @GetMapping(value = "/list-myself")
    public Result<IPage<ServiceInfo>> queryPageMyselfList(ServiceInfo serviceInfo,
                                                          @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                          HttpServletRequest req) {
        Result<IPage<ServiceInfo>> result = new Result<>();
        QueryWrapper<ServiceInfo> queryWrapper = QueryGenerator.initQueryWrapper(serviceInfo, req.getParameterMap());
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (queryWrapper.getExpression().getNormal().size() > 0){
            queryWrapper.and(wrapper -> wrapper.eq("developer", user.getRealname()).or().isNull("developer"));
        }else {
            queryWrapper.eq("developer", user.getRealname()).or().isNull("developer");
        }

        Page<ServiceInfo> page = new Page<ServiceInfo>(pageNo, pageSize);
        IPage<ServiceInfo> pageList = serviceInfoService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param serviceInfo
     * @return
     */
    @AutoLog(value = "服务基本信息-添加")
    @ApiOperation(value = "服务基本信息-添加")
    @PostMapping(value = "/add")
    public Result<ServiceInfo> add(@RequestBody ServiceInfo serviceInfo) {
        Result<ServiceInfo> result = new Result<>();
        try {
            setDesignUser(serviceInfo);
            // 新增服务提交时调用exe生成生成服务调用代码框架和服务实现代码文件
            if ("1".equals(serviceInfo.getDesignerStatus())) {
                serviceInfo.setDeveloperStatus("0");
                serviceInfo.setDeployStatus("0");
                serviceInfo = serviceInfoService.callExe(serviceInfo);
//                ServiceMockDataUtil.callExe(serviceInfo);
            }
            serviceInfoService.save(serviceInfo);
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑服务相关信息
     *
     * @param serviceInfo
     * @return
     */
    @AutoLog(value = "服务基本信息-编辑服务相关信息")
    @ApiOperation(value = "服务基本信息-编辑服务相关信息")
    @PutMapping(value = "/edit-register")
    public Result<ServiceInfo> editRegister(@RequestBody ServiceInfo serviceInfo) throws IOException, InterruptedException {
        Result<ServiceInfo> result = new Result<>();
        ServiceInfo serviceInfoEntity = serviceInfoService.getById(serviceInfo.getId());
        if (serviceInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            setDesignUser(serviceInfo);
            // 新增服务提交时调用exe生成生成服务调用代码框架和服务实现代码文件
            if ("1".equals(serviceInfo.getDesignerStatus())) {
                serviceInfo.setDeveloperStatus("0");
                serviceInfo.setDeployStatus("0");
                serviceInfo = serviceInfoService.callExe(serviceInfo);
//                ServiceMockDataUtil.callExe(serviceInfo);
            }
            boolean ok = serviceInfoService.updateById(serviceInfo);
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 编辑开发相关信息
     *
     * @param serviceInfo
     * @return
     */
    @AutoLog(value = "服务基本信息-编辑开发相关信息")
    @ApiOperation(value = "服务基本信息-编辑开发相关信息")
    @PutMapping(value = "/edit-developer")
    public Result<ServiceInfo> editDeveloper(@RequestBody ServiceInfo serviceInfo) {
        Result<ServiceInfo> result = new Result<>();
        ServiceInfo serviceInfoEntity = serviceInfoService.getById(serviceInfo.getId());
        if (serviceInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            if(serviceInfo.getMinInstance() != null){
                setDeveloperUser(serviceInfo);
                // 判断是否被他人提交
            }
            if ("1".equals(serviceInfoEntity.getDeveloperStatus())) {
                serviceInfo.setDeveloperStatus(serviceInfoEntity.getDeveloperStatus());
            }
            boolean ok = serviceInfoService.updateById(serviceInfo);
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }



    /**
     * 编辑部署相关信息
     *
     * @param serviceInfo
     * @return
     */
    @AutoLog(value = "服务基本信息-编辑部署相关信息")
    @ApiOperation(value = "服务基本信息-编辑部署相关信息")
    @PutMapping(value = "/edit-deploy")
    public Result<ServiceInfo> editDeploy(@RequestBody ServiceInfo serviceInfo) {
        Result<ServiceInfo> result = new Result<>();
        ServiceInfo serviceInfoEntity = serviceInfoService.getById(serviceInfo.getId());
        if (serviceInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            setDeployUser(serviceInfo);
            boolean ok = serviceInfoService.updateById(serviceInfo);
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 设置部署相关信息
     *
     * @param serviceInfo
     */
    private void setDeployUser(ServiceInfo serviceInfo) {
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(serviceInfo.getDeploySubmissionTime() == null){
            serviceInfo.setDeploySubmissionTime(new Date());
        }
        serviceInfo.setDeploySubmissionUser(user.getRealname());
        serviceInfo.setDeploySubmissionUserId(user.getId());
    }

    /**
     * 设置开发人员相关信息
     *
     * @param serviceInfo
     */
    private void setDeveloperUser(ServiceInfo serviceInfo) {
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(serviceInfo.getDeveloperSubmissionTime() == null){
            serviceInfo.setDeveloperSubmissionTime(new Date());
        }
        serviceInfo.setDeveloperSubmissionUser(user.getRealname());
        serviceInfo.setDeveloperSubmissionUserId(user.getId());
    }

    /**
     * 设置设计提交人员相关信息
     *
     * @param serviceInfo
     */
    private void setDesignUser(@RequestBody ServiceInfo serviceInfo) {
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(serviceInfo.getDesignSubmissionTime() == null){
            serviceInfo.setDesignSubmissionTime(new Date());
        }
        serviceInfo.setDesignSubmissionUser(user.getRealname());
        serviceInfo.setDesignSubmissionUserId(user.getId());
    }


    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "服务基本信息-通过id删除")
    @ApiOperation(value = "服务基本信息-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<ServiceInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<ServiceInfo> result = new Result<>();
        ServiceInfo serviceInfo = serviceInfoService.getById(id);
        if (serviceInfo == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = serviceInfoService.removeById(id);
            if (ok) {
                result.success("删除成功!");
            }
        }

        return result;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "服务基本信息-批量删除")
    @ApiOperation(value = "服务基本信息-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<ServiceInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<ServiceInfo> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.serviceInfoService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "服务基本信息-通过id查询")
    @ApiOperation(value = "服务基本信息-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ServiceInfo> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<ServiceInfo> result = new Result<>();
        ServiceInfo serviceInfo = serviceInfoService.getById(id);
        if (serviceInfo == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(serviceInfo);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 通过serviceid查询
     *
     * @param serviceId
     * @return
     */
    @AutoLog(value = "服务基本信息-通过serviceid查询")
    @ApiOperation(value = "服务基本信息-通过serviceid查询")
    @GetMapping(value = "/query-by-service-id")
    public Result<ServiceInfo> queryByServiceId(@RequestParam(name = "serviceId", required = true) String serviceId) {
        Result<ServiceInfo> result = new Result<>();
        LambdaQueryWrapper<ServiceInfo> query = new LambdaQueryWrapper<ServiceInfo>();
        query.eq(ServiceInfo::getServiceId, serviceId);
        query.orderByDesc(ServiceInfo::getDeploySubmissionTime);
        ServiceInfo serviceInfo = serviceInfoService.getOne(query);
        if (serviceInfo == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(serviceInfo);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param serviceInfo
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(ServiceInfo serviceInfo, HttpServletRequest request) {
        // Step.1 组装查询条件
        QueryWrapper<ServiceInfo> queryWrapper = QueryGenerator.initQueryWrapper(serviceInfo, request.getParameterMap());
        List<ServiceInfo> pageList = serviceInfoService.list(queryWrapper);
        // Step.2 AutoPoi 导出Excel
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        ModelAndView mv = new ModelAndView(new JantdEntityExcelViewBase());
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "服务基本信息列表");
        mv.addObject(NormalExcelConstants.CLASS, ServiceInfo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("服务基本信息列表数据", "导出人:" + user.getRealname(), "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/importExcel")
    public Result<Object> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
            MultipartFile file = entity.getValue();
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<ServiceInfo> listServiceInfos = ExcelImportUtil.importExcel(file.getInputStream(), ServiceInfo.class, params);
                for (ServiceInfo serviceInfoExcel : listServiceInfos) {
                    serviceInfoService.save(serviceInfoExcel);
                }
                return Result.ok("文件导入成功！数据行数:" + listServiceInfos.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return Result.ok("文件导入失败！");
    }

    @GetMapping(value = "/get-service-count")
    public Result<Object> getServiceCount(){
        Result result = new Result();
        int count = serviceInfoService.count();
        result.setResult(count);
        return result;
    }

}
