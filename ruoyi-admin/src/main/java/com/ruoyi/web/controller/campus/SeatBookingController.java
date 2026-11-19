package com.ruoyi.web.controller.campus;

import java.util.List;

import com.ruoyi.campus.dto.*;
import com.ruoyi.campus.vo.MySeatBookingVO;
import com.ruoyi.campus.vo.SeatBookingVO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
import com.ruoyi.campus.domain.SeatBooking;
import com.ruoyi.campus.service.ISeatBookingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 座位预约Controller
 * 
 * @author ruoyi
 * @date 2026-11-05
 */
@RestController
@RequestMapping("/campus/booking")
public class SeatBookingController extends BaseController
{
    @Autowired
    private ISeatBookingService seatBookingService;

    /**
     * 查询座位预约列表
     */
    @PreAuthorize("@ss.hasPermi('campus:booking:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeatBookingQueryDTO seatBookingQueryDTO)
    {
        startPage();
        List<SeatBookingVO> list = seatBookingService.selectSeatBookingList(seatBookingQueryDTO);
        return getDataTable(list);
    }

    /**
     * 导出座位预约列表
     */
    @PreAuthorize("@ss.hasPermi('campus:booking:export')")
    @Log(title = "座位预约", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SeatBookingQueryDTO seatBookingQueryDTO) {
        List<SeatBookingVO> rows = seatBookingService.selectSeatBookingList(seatBookingQueryDTO);
        ExcelUtil<SeatBookingVO> util = new ExcelUtil<>(SeatBookingVO.class);
        util.exportExcel(response, rows, "座位预约数据");
    }

    /**
     * 获取座位预约详细信息
     */
    @PreAuthorize("@ss.hasPermi('campus:booking:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(seatBookingService.selectSeatBookingById(id));
    }

    /**
     * 新增座位预约
     */
    @PreAuthorize("@ss.hasPermi('campus:booking:add')")
    @Log(title = "座位预约", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody SeatBookingCreateDTO seatBookingCreateDTO)
    {
        return toAjax(seatBookingService.createBooking(seatBookingCreateDTO));
    }

    /**
     * 查询楼层所有座位状况列表
     */
    @PreAuthorize("@ss.hasPermi('campus:booking:seats')")
    @Log(title = "查询楼层所有座位状况", businessType = BusinessType.OTHER)
    @GetMapping("/seats")
    public AjaxResult getBookingSeats(@Valid SeatBookingListDTO seatBookingListDTO)
    {
        return success(seatBookingService.getSeatAvailability(seatBookingListDTO));
    }

    /**
     * 取消座位预约
     * @param id
     * @param seatBookingCancelDTO
     * @return
     */
    @PreAuthorize("@ss.hasPermi('campus:booking:cancel')")
    @Log(title = "取消预约", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/cancel")
    public AjaxResult cancelSeatBooking(@PathVariable("id") Long id,@Valid @RequestBody SeatBookingCancelDTO seatBookingCancelDTO){
        return toAjax(seatBookingService.cancelBooking(id, seatBookingCancelDTO));
    }

    /**
     * 查询我的预约列表
     * @return
     */
    @PreAuthorize("@ss.hasPermi('campus:booking:my')")
    @Log(title = "我的预约", businessType = BusinessType.OTHER)
    @GetMapping("/my")
    public TableDataInfo getMySeatBookings() {
        startPage();
        List<MySeatBookingVO> list = seatBookingService.getMyBookings();
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('campus:booking:manage')")
    @Log(title = "管理员取消座位预约", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/admin/cancel")
    public AjaxResult adminCancelBooking(
            @PathVariable("id") Long id,
            @Valid @RequestBody SeatBookingCancelDTO dto) {
        return toAjax(seatBookingService.adminCancelBooking(id, dto));
    }

    @PreAuthorize("@ss.hasPermi('campus:booking:manage')")
    @Log(title = "管理员修改预约备注", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/admin/remark")
    public AjaxResult updateBookingRemark(
            @PathVariable("id") Long id,
            @Valid @RequestBody SeatBookingRemarkDTO dto) {
        return toAjax(seatBookingService.updateBookingRemark(id, dto.getRemark()));
    }


//    /**
//     * 修改座位预约
//     */
//    @PreAuthorize("@ss.hasPermi('campus:booking:edit')")
//    @Log(title = "座位预约", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody SeatBooking seatBooking)
//    {
//        return toAjax(seatBookingService.updateSeatBooking(seatBooking));
//    }
//
//    /**
//     * 删除座位预约
//     */
//    @PreAuthorize("@ss.hasPermi('campus:booking:remove')")
//    @Log(title = "座位预约", businessType = BusinessType.DELETE)
//	@DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids)
//    {
//        return toAjax(seatBookingService.deleteSeatBookingByIds(ids));
//    }
}
