package com.ruoyi.campus.mapper;

import java.util.List;
import com.ruoyi.campus.domain.SeatBooking;
import com.ruoyi.campus.dto.SeatBookingQueryDTO;
import com.ruoyi.campus.vo.MySeatBookingVO;
import com.ruoyi.campus.vo.SeatBookingVO;

/**
 * 座位预约Mapper接口
 * 
 * @author ruoyi
 * @date 2026-11-05
 */
public interface SeatBookingMapper 
{
    /**
     * 查询座位预约
     * 
     * @param id 座位预约主键
     * @return 座位预约
     */
    SeatBookingVO selectSeatBookingById(Long id);

    /**
     * 查询座位预约列表
     * 
      * @param seatBookingQueryDTO 座位预约查询参数
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
     * 删除座位预约
     * 
     * @param id 座位预约主键
     * @return 结果
     */
    public int deleteSeatBookingById(Long id);

    /**
     * 批量删除座位预约
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSeatBookingByIds(Long[] ids);

    /**
     * 根据条件查询座位预约
     *
     * @param seatBooking 座位预约
     * @return 座位预约集合
     */
    public List<SeatBooking> getBookingSeats(SeatBooking seatBooking);

    /**
     * 取消预约
     *
     * @param seatBooking 座位预约
     * @return 结果
     */
    public int cancelBooking(SeatBooking seatBooking);

    /**
     * 查询我的预约
     *
     * @param userId 用户主键
     * @return 预约集合
     */
    public List<MySeatBookingVO> selectMyBookings(Long userId);
}
