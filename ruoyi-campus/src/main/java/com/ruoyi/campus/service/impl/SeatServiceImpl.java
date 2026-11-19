package com.ruoyi.campus.service.impl;

import java.util.List;
import java.util.Locale;

import com.ruoyi.campus.domain.CampusArea;
import com.ruoyi.campus.mapper.CampusAreaMapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.campus.mapper.SeatMapper;
import com.ruoyi.campus.domain.Seat;
import com.ruoyi.campus.service.ISeatService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 座位管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-11-04
 */
@Service
public class SeatServiceImpl implements ISeatService 
{
    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private CampusAreaMapper campusAreaMapper;

    /**
     * 查询座位管理
     * 
     * @param id 座位管理主键
     * @return 座位管理
     */
    @Override
    public Seat selectSeatById(Long id)
    {
        return seatMapper.selectSeatById(id);
    }

    /**
     * 查询座位管理列表
     * 
     * @param seat 座位管理
     * @return 座位管理
     */
    @Override
    public List<Seat> selectSeatList(Seat seat)
    {
        return seatMapper.selectSeatList(seat);
    }

    /**
     * 新增座位管理
     * 
     * @param seat 座位管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSeat(Seat seat)
    {
        if(seat.getAreaId() == null || seat.getFloorNo() == null)
            throw new ServiceException("区域ID和楼层号不能为空");

        if (seat.getFloorNo()<=0)
            throw new ServiceException("楼层号必须大于0");

        CampusArea campusArea = campusAreaMapper.selectCampusAreaById(seat.getAreaId());

        if (campusArea == null)
            throw new ServiceException("区域ID不存在");

        if(!"0".equals(campusArea.getStatus()))
            throw new ServiceException("区域已停用");

        String areaCode = campusArea.getAreaCode();
        if(areaCode==null)
            throw new ServiceException("该区域没有区域编号，无法添加座位");

        String prefix= areaCode+"-F"+seat.getFloorNo();

        seatMapper.increaseSeatNo(prefix);
        Long nextNo = seatMapper.selectSeatNoForUpdate(prefix);

        String seatCode = prefix+"-A"+String.format(Locale.ROOT,"%03d", nextNo);

        seat.setCreateBy(SecurityUtils.getUsername());
        seat.setUpdateBy(SecurityUtils.getUsername());
        seat.setCreateTime(DateUtils.getNowDate());
        seat.setUpdateTime(DateUtils.getNowDate());
        seat.setSeatCode(seatCode);

        int rows = seatMapper.insertSeat(seat);
        if(rows!=1)
            throw new ServiceException("添加座位失败");

        return rows;
    }

    /**
     * 修改座位管理
     * 
     * @param seat 座位管理
     * @return 结果
     */
    @Override
    public int updateSeat(Seat seat)
    {
        seat.setUpdateTime(DateUtils.getNowDate());
        return seatMapper.updateSeat(seat);
    }

    /**
     * 批量删除座位管理
     * 
     * @param ids 需要删除的座位管理主键
     * @return 结果
     */
    @Override
    public int deleteSeatByIds(Long[] ids)
    {
        return seatMapper.deleteSeatByIds(ids);
    }

    /**
     * 删除座位管理信息
     *
     * @param id 座位管理主键
     * @return 结果
     */
    @Override
    public int deleteSeatById(Long id)
    {
        return seatMapper.deleteSeatById(id);
    }
}
