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
import com.ruoyi.campus.domain.RepairDeviceModel;
import com.ruoyi.campus.service.IRepairDeviceModelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备型号Controller
 * 
 * @author ruoyi
 * @date 2026-10-29
 */
@RestController
@RequestMapping("/campus/model")
public class RepairDeviceModelController extends BaseController
{
    @Autowired
    private IRepairDeviceModelService repairDeviceModelService;

    /**
     * 查询设备型号列表
     */
    @PreAuthorize("@ss.hasPermi('campus:model:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepairDeviceModel repairDeviceModel)
    {
        startPage();
        List<RepairDeviceModel> list = repairDeviceModelService.selectRepairDeviceModelList(repairDeviceModel);
        return getDataTable(list);
    }

    /**
     * 导出设备型号列表
     */
    @PreAuthorize("@ss.hasPermi('campus:model:export')")
    @Log(title = "设备型号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RepairDeviceModel repairDeviceModel)
    {
        List<RepairDeviceModel> list = repairDeviceModelService.selectRepairDeviceModelList(repairDeviceModel);
        ExcelUtil<RepairDeviceModel> util = new ExcelUtil<RepairDeviceModel>(RepairDeviceModel.class);
        util.exportExcel(response, list, "设备型号数据");
    }

    /**
     * 获取设备型号详细信息
     */
    @PreAuthorize("@ss.hasPermi('campus:model:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(repairDeviceModelService.selectRepairDeviceModelById(id));
    }

    /**
     * 新增设备型号
     */
    @PreAuthorize("@ss.hasPermi('campus:model:add')")
    @Log(title = "设备型号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepairDeviceModel repairDeviceModel)
    {
        return toAjax(repairDeviceModelService.insertRepairDeviceModel(repairDeviceModel));
    }

    /**
     * 修改设备型号
     */
    @PreAuthorize("@ss.hasPermi('campus:model:edit')")
    @Log(title = "设备型号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepairDeviceModel repairDeviceModel)
    {
        return toAjax(repairDeviceModelService.updateRepairDeviceModel(repairDeviceModel));
    }

    /**
     * 删除设备型号
     */
    @PreAuthorize("@ss.hasPermi('campus:model:remove')")
    @Log(title = "设备型号", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(repairDeviceModelService.deleteRepairDeviceModelByIds(ids));
    }
}
