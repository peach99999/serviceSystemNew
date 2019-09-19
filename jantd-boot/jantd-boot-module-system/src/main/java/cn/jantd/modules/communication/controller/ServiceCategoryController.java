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
import cn.jantd.modules.communication.entity.ServiceCategory;
import cn.jantd.modules.communication.service.IServiceCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Description: 服务分类
 * @Author: xiagf
 * @Date: 2019-09-17
 */
@Slf4j
@Api(tags = "服务分类")
@RestController
@RequestMapping("/serviceCategory/serviceCategory")
public class ServiceCategoryController {
    @Autowired
    private IServiceCategoryService serviceCategoryService;

    /**
     * 分页列表查询
     *
     * @param serviceCategory
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "服务分类-分页列表查询")
    @ApiOperation(value = "服务分类-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ServiceCategory>> queryPageList(ServiceCategory serviceCategory,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {
        Result<IPage<ServiceCategory>> result = new Result<>();
        QueryWrapper<ServiceCategory> queryWrapper = QueryGenerator.initQueryWrapper(serviceCategory, req.getParameterMap());
        Page<ServiceCategory> page = new Page<ServiceCategory>(pageNo, pageSize);
        IPage<ServiceCategory> pageList = serviceCategoryService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @AutoLog(value = "服务分类-查询全部")
    @ApiOperation(value = "服务分类-查询全部")
    @GetMapping(value = "/list-all")
    public Result<List<ServiceCategory>> queryListAll() {
        Result<List<ServiceCategory>> result = new Result<>();

        List<ServiceCategory> pageList = serviceCategoryService.queryListAll();
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param serviceCategory
     * @return
     */
    @AutoLog(value = "服务分类-添加")
    @ApiOperation(value = "服务分类-添加")
    @PostMapping(value = "/add")
    public Result<ServiceCategory> add(@RequestBody ServiceCategory serviceCategory) {
        Result<ServiceCategory> result = new Result<>();
        try {
            serviceCategoryService.save(serviceCategory);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param serviceCategory
     * @return
     */
    @AutoLog(value = "服务分类-编辑")
    @ApiOperation(value = "服务分类-编辑")
    @PutMapping(value = "/edit")
    public Result<ServiceCategory> edit(@RequestBody ServiceCategory serviceCategory) {
        Result<ServiceCategory> result = new Result<>();
        ServiceCategory serviceCategoryEntity = serviceCategoryService.getById(serviceCategory.getId());
        if (serviceCategoryEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = serviceCategoryService.updateById(serviceCategory);
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "服务分类-通过id删除")
    @ApiOperation(value = "服务分类-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<ServiceCategory> delete(@RequestParam(name = "id", required = true) String id) {
        Result<ServiceCategory> result = new Result<>();
        ServiceCategory serviceCategory = serviceCategoryService.getById(id);
        if (serviceCategory == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = serviceCategoryService.removeById(id);
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
    @AutoLog(value = "服务分类-批量删除")
    @ApiOperation(value = "服务分类-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<ServiceCategory> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<ServiceCategory> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.serviceCategoryService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "服务分类-通过id查询")
    @ApiOperation(value = "服务分类-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ServiceCategory> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<ServiceCategory> result = new Result<>();
        ServiceCategory serviceCategory = serviceCategoryService.getById(id);
        if (serviceCategory == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(serviceCategory);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param serviceCategory
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(ServiceCategory serviceCategory, HttpServletRequest request) {
        // Step.1 组装查询条件
        QueryWrapper<ServiceCategory> queryWrapper = QueryGenerator.initQueryWrapper(serviceCategory, request.getParameterMap());
        List<ServiceCategory> pageList = serviceCategoryService.list(queryWrapper);
        // Step.2 AutoPoi 导出Excel
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        ModelAndView mv = new ModelAndView(new JantdEntityExcelViewBase());
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "服务分类列表");
        mv.addObject(NormalExcelConstants.CLASS, ServiceCategory.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("服务分类列表数据", "导出人:" + user.getRealname(), "导出信息"));
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
                List<ServiceCategory> listServiceCategorys = ExcelImportUtil.importExcel(file.getInputStream(), ServiceCategory.class, params);
                for (ServiceCategory serviceCategoryExcel : listServiceCategorys) {
                    serviceCategoryService.save(serviceCategoryExcel);
                }
                return Result.ok("文件导入成功！数据行数:" + listServiceCategorys.size());
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

}
