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
import cn.jantd.modules.communication.entity.ServiceDesploy;
import cn.jantd.modules.communication.service.IServiceDesployService;
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
 * @Description: 服务部署
 * @Author: xiagf
 * @Date: 2019-09-17
 */
@Slf4j
@Api(tags = "服务部署")
@RestController
@RequestMapping("/serviceDesploy")
public class ServiceDesployController {
	@Autowired
	private IServiceDesployService serviceDesployService;

	/**
	 * 分页列表查询
	 *
	 * @param serviceDesploy
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "服务部署-分页列表查询")
	@ApiOperation(value = "服务部署-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ServiceDesploy>> queryPageList(ServiceDesploy serviceDesploy,
											 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
									  		 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
									  		 HttpServletRequest req) {
		Result<IPage<ServiceDesploy>> result = new Result<>();
		QueryWrapper<ServiceDesploy> queryWrapper = QueryGenerator.initQueryWrapper(serviceDesploy, req.getParameterMap());
		Page<ServiceDesploy> page = new Page<ServiceDesploy>(pageNo, pageSize);
		IPage<ServiceDesploy> pageList = serviceDesployService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 * 添加
     *
	 * @param serviceDesploy
	 * @return
	 */
	@AutoLog(value = "服务部署-添加")
	@ApiOperation(value = "服务部署-添加")
	@PostMapping(value = "/add")
	public Result<ServiceDesploy> add(@RequestBody ServiceDesploy serviceDesploy) {
		Result<ServiceDesploy> result = new Result<>();
		try {
			serviceDesployService.save(serviceDesploy);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
     *  编辑
	 *
	 * @param serviceDesploy
	 * @return
	 */
	@AutoLog(value = "服务部署-编辑")
	@ApiOperation(value = "服务部署-编辑")
	@PutMapping(value = "/edit")
	public Result<ServiceDesploy> edit(@RequestBody ServiceDesploy serviceDesploy) {
		Result<ServiceDesploy> result = new Result<>();
		ServiceDesploy serviceDesployEntity = serviceDesployService.getById(serviceDesploy.getId());
		if(serviceDesployEntity == null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = serviceDesployService.updateById(serviceDesploy);
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}

	/**
     *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "服务部署-通过id删除")
	@ApiOperation(value = "服务部署-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<ServiceDesploy> delete(@RequestParam(name = "id", required = true) String id) {
		Result<ServiceDesploy> result = new Result<>();
		ServiceDesploy serviceDesploy = serviceDesployService.getById(id);
		if(serviceDesploy == null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = serviceDesployService.removeById(id);
			if(ok) {
				result.success("删除成功!");
			}
		}

		return result;
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "服务部署-批量删除")
	@ApiOperation(value = "服务部署-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<ServiceDesploy> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
		Result<ServiceDesploy> result = new Result<>();
		if(ids == null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.serviceDesployService.removeByIds(Arrays.asList(ids.split(",")));
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
	@AutoLog(value = "服务部署-通过id查询")
	@ApiOperation(value = "服务部署-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ServiceDesploy> queryById(@RequestParam(name = "id", required = true) String id) {
		Result<ServiceDesploy> result = new Result<>();
		ServiceDesploy serviceDesploy = serviceDesployService.getById(id);
		if(serviceDesploy == null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(serviceDesploy);
			result.setSuccess(true);
		}
		return result;
	}

  /**
   * 导出excel
   *
   * @param request
   * @param serviceDesploy
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(ServiceDesploy serviceDesploy, HttpServletRequest request) {
      // Step.1 组装查询条件
      QueryWrapper<ServiceDesploy> queryWrapper = QueryGenerator.initQueryWrapper(serviceDesploy, request.getParameterMap());
      List<ServiceDesploy> pageList = serviceDesployService.list(queryWrapper);
      // Step.2 AutoPoi 导出Excel
      LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
      ModelAndView mv = new ModelAndView(new JantdEntityExcelViewBase());
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "服务部署列表");
      mv.addObject(NormalExcelConstants.CLASS, ServiceDesploy.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("服务部署列表数据", "导出人:"+user.getRealname(), "导出信息"));
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
              List<ServiceDesploy> listServiceDesploys = ExcelImportUtil.importExcel(file.getInputStream(), ServiceDesploy.class, params);
              for (ServiceDesploy serviceDesployExcel : listServiceDesploys) {
                  serviceDesployService.save(serviceDesployExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listServiceDesploys.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
				  log.error(e.getMessage(),e);
              }
          }
      }
      return Result.ok("文件导入失败！");
  }

}
