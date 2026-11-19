package com.ruoyi.campus.service;

import java.util.List;
import com.ruoyi.campus.domain.SeatBooking;
import com.ruoyi.campus.dto.SeatBookingCancelDTO;
import com.ruoyi.campus.dto.SeatBookingCreateDTO;
import com.ruoyi.campus.dto.SeatBookingListDTO;
import com.ruoyi.campus.dto.SeatBookingQueryDTO;
import com.ruoyi.campus.vo.MySeatBookingVO;
import com.ruoyi.campus.vo.SeatBookingListVO;
import com.ruoyi.campus.vo.SeatBookingVO;

/**
 * 座位预约Service接口
 * 
 * @author ruoyi
 * @date 2026-11-05
 */
public interface ISeatBookingService 
{
    /**
     * 查询座位预约
     * 
     * @param id 座位预约主键
     * @return 座位预约
     */
    public SeatBookingVO selectSeatBookingById(Long id);

    /**
     * 查询座位预约列表
     * 
     * @param seatBookingQueryDTO 座位预约列表查询条件
     * @return 座位预约集合
     */
    public List<SeatBookingVO> selectSeatBookingList(SeatBookingQueryDTO seatBookingQueryDTO);

    /**
     * 新增座位预约
     * 
     * @param seatBooking 座位预约
     * @return 结果
     */
    public int insertSeatBooking(SeatBooking seatBooking);

    /**
     * 修改座位预约
     * 
     * @param seatBooking 座位预约
     * @return 结果
     */
    public int updateSeatBooking(SeatBooking seatBooking);

    /**
     * 批量删除座位预约
     * 
     * @param ids 需要删除的座位预约主键集合
     * @return 结果
     */
    public int deleteSeatBookingByIds(Long[] ids);

    /**
     * 删除座位预约信息
     * 
     * @param id 座位预约主键
     * @return 结果
     */
    public int deleteSeatBookingById(Long id);

    /**
     * 创建座位预约
     * @param seatBookingCreateDTO
     * @return
     */
    public int createBooking(SeatBookingCreateDTO seatBookingCreateDTO);

    /**
     * 查询楼层所有座位状况列表
     * @param seatBookingListDTO
     * @return
     */
    List<SeatBookingListVO> getSeatAvailability(SeatBookingListDTO seatBookingListDTO);

    /**
     * 取消座位预约
     * @param id
     * @param seatBookingCancelDTO
     * @return
     */
    int cancelBooking(Long id, SeatBookingCancelDTO seatBookingCancelDTO);

    List<MySeatBookingVO> getMyBookings();

    int adminCancelBooking(Long bookingId, SeatBookingCancelDTO dto);

    int updateBookingRemark(Long bookingId, String remark);
}
