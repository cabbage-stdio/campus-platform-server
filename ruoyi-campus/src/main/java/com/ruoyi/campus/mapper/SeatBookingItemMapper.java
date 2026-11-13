package com.ruoyi.campus.mapper;

import java.util.List;
import com.ruoyi.campus.domain.SeatBookingItem;

/**
 * 座位预约时段明细Mapper接口
 * 
 * @author ruoyi
 * @date 2026-11-05
 */
public interface SeatBookingItemMapper 
{
    /**
     * 查询座位预约时段明细
     * 
     * @param id 座位预约时段明细主键
     * @return 座位预约时段明细
     */
    public SeatBookingItem selectSeatBookingItemById(Long id);

    /**
     * 查询座位预约时段明细列表
     * 
     * @param seatBookingItem 座位预约时段明细
     * @return 座位预约时段明细集合
     */
    public List<SeatBookingItem> selectSeatBookingItemList(SeatBookingItem seatBookingItem);

    /**
     * 新增座位预约时段明细
     * 
     * @param seatBookingItem 座位预约时段明细
     * @return 结果
     */
    public int insertSeatBookingItem(SeatBookingItem seatBookingItem);

    /**
     * 修改座位预约时段明细
     * 
     * @param seatBookingItem 座位预约时段明细
     * @return 结果
     */
    public int updateSeatBookingItem(SeatBookingItem seatBookingItem);

    /**
     * 删除座位预约时段明细
     * 
     * @param id 座位预约时段明细主键
     * @return 结果
     */
    public int deleteSeatBookingItemById(Long id);

    /**
     * 批量删除座位预约时段明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSeatBookingItemByIds(Long[] ids);

    /**
     * 批量新增座位预约时段明细
     *
     * @param seatBookingItem 座位预约时段明细
     * @return 结果
     */
    public int insertSeatBookingItemBatch(List<SeatBookingItem> seatBookingItem);

    /**
     * 根据预约id查询座位预约时段明细
     *
     * @param bookingIds 预约id
     * @return 座位预约时段明细
     */
    List<SeatBookingItem> selectByBookingIds(List<Long> bookingIds);
}
