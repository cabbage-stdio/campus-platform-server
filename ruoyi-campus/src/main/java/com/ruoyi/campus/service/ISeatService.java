package com.ruoyi.campus.service;

import java.util.List;
import com.ruoyi.campus.domain.Seat;

/**
 * 座位管理Service接口
 * 
 * @author ruoyi
 * @date 2026-11-04
 */
public interface ISeatService 
{
    /**
     * 查询座位管理
     * 
     * @param id 座位管理主键
     * @return 座位管理
     */
    public Seat selectSeatById(Long id);

    /**
     * 查询座位管理列表
     * 
     * @param seat 座位管理
     * @return 座位管理集合
     */
    public List<Seat> selectSeatList(Seat seat);

    /**
     * 新增座位管理
     * 
     * @param seat 座位管理
     * @return 结果
     */
    public int insertSeat(Seat seat);

    /**
     * 修改座位管理
     * 
     * @param seat 座位管理
     * @return 结果
     */
    public int updateSeat(Seat seat);

    /**
     * 批量删除座位管理
     * 
     * @param ids 需要删除的座位管理主键集合
     * @return 结果
     */
    public int deleteSeatByIds(Long[] ids);

    /**
     * 删除座位管理信息
     * 
     * @param id 座位管理主键
     * @return 结果
     */
    public int deleteSeatById(Long id);
}
