package com.ruoyi.web.controller.campus;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.campus.domain.CampusArea;
import com.ruoyi.campus.service.ICampusAreaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 校园区域Controller
 * 
 * @author ruoyi
 * @date 2026-10-30
 */
@RestController
@RequestMapping("/campus/area")
public class CampusAreaController extends BaseController
{
    @Autowired
    private ICampusAreaService campusAreaService;

    /**
     * 查询校园区域列表
     */
    @PreAuthorize("@ss.hasPermi('campus:area:list')")
    @GetMapping("/list")
    public TableDataInfo list(CampusArea campusArea)
    {
        startPage();
        List<CampusArea> list = campusAreaService.selectCampusAreaList(campusArea);
        return getDataTable(list);
    }

    /**
     * 导出校园区域列表
     */
    @PreAuthorize("@ss.hasPermi('campus:area:export')")
    @Log(title = "校园区域", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CampusArea campusArea)
    {
        List<CampusArea> list = campusAreaService.selectCampusAreaList(campusArea);
        ExcelUtil<CampusArea> util = new ExcelUtil<CampusArea>(CampusArea.class);
        util.exportExcel(response, list, "校园区域数据");
    }

    /**
     * 获取校园区域详细信息
     */
    @PreAuthorize("@ss.hasPermi('campus:area:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(campusAreaService.selectCampusAreaById(id));
    }

    /**
     * 新增校园区域
     */
    @PreAuthorize("@ss.hasPermi('campus:area:add')")
    @Log(title = "校园区域", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CampusArea campusArea)
    {
        return toAjax(campusAreaService.insertCampusArea(campusArea));
    }

    /**
     * 修改校园区域
     */
    @PreAuthorize("@ss.hasPermi('campus:area:edit')")
    @Log(title = "校园区域", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CampusArea campusArea)
    {
        return toAjax(campusAreaService.updateCampusArea(campusArea));
    }

    /**
     * 删除校园区域
     */
    @PreAuthorize("@ss.hasPermi('campus:area:remove')")
    @Log(title = "校园区域", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(campusAreaService.deleteCampusAreaByIds(ids));
    }
}
