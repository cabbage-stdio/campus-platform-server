package com.ruoyi.campus.mapper;

import java.util.List;
import com.ruoyi.campus.domain.SeatSlotOccupancy;
import com.ruoyi.campus.dto.SeatBookingListDTO;

/**
 * 座位有效时段占用Mapper接口
 * 
 * @author ruoyi
 * @date 2026-11-05
 */
public interface SeatSlotOccupancyMapper 
{
    /**
     * 查询座位有效时段占用
     * 
     * @param id 座位有效时段占用主键
     * @return 座位有效时段占用
     */
    public SeatSlotOccupancy selectSeatSlotOccupancyById(Long id);

    /**
     * 查询座位有效时段占用列表
     * 
     * @param seatSlotOccupancy 座位有效时段占用
     * @return 座位有效时段占用集合
     */
    public List<SeatSlotOccupancy> selectSeatSlotOccupancyList(SeatSlotOccupancy seatSlotOccupancy);

    /**
     * 新增座位有效时段占用
     * 
     * @param seatSlotOccupancy 座位有效时段占用
     * @return 结果
     */
    public int insertSeatSlotOccupancy(SeatSlotOccupancy seatSlotOccupancy);

    /**
     * 修改座位有效时段占用
     * 
     * @param seatSlotOccupancy 座位有效时段占用
     * @return 结果
     */
    public int updateSeatSlotOccupancy(SeatSlotOccupancy seatSlotOccupancy);

    /**
     * 删除座位有效时段占用
     * 
     * @param id 座位有效时段占用主键
     * @return 结果
     */
    public int deleteSeatSlotOccupancyById(Long id);

    /**
     * 批量删除座位有效时段占用
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSeatSlotOccupancyByIds(Long[] ids);

    /**
     * 批量新增座位有效时段占用
     *
     * @param seatSlotOccupancy 座位有效时段占用
     * @return 结果
     */
    public int insertSeatSlotOccupancyBatch(List<SeatSlotOccupancy> seatSlotOccupancy);

    /**
     * 根据楼层查询座位有效时段占用
     *
     * @param seatBookingListDTO 查询参数
     * @return 座位有效时段占用列表
     */
    List<SeatSlotOccupancy> selectOccupancyByFloor(SeatBookingListDTO seatBookingListDTO);

    /**
     * 根据预约id删除座位有效时段占用
     *
     * @param bookingId 预约id
     * @return 结果
     */
    public int deleteByBookingId(Long bookingId);
}
