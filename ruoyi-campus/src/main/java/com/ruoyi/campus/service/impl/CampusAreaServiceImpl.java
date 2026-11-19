package com.ruoyi.campus.service.impl;

import java.util.List;

import com.ruoyi.campus.mapper.RepairDeviceMapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.campus.mapper.CampusAreaMapper;
import com.ruoyi.campus.domain.CampusArea;
import com.ruoyi.campus.service.ICampusAreaService;

/**
 * 校园区域Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-10-30
 */
@Service
public class CampusAreaServiceImpl implements ICampusAreaService 
{
    @Autowired
    private CampusAreaMapper campusAreaMapper;

    @Autowired
    private RepairDeviceMapper repairDeviceMapper;

    /**
     * 查询校园区域
     * 
     * @param id 校园区域主键
     * @return 校园区域
     */
    @Override
    public CampusArea selectCampusAreaById(Long id)
    {
        return campusAreaMapper.selectCampusAreaById(id);
    }

    /**
     * 查询校园区域列表
     * 
     * @param campusArea 校园区域
     * @return 校园区域
     */
    @Override
    public List<CampusArea> selectCampusAreaList(CampusArea campusArea)
    {
        return campusAreaMapper.selectCampusAreaList(campusArea);
    }

    /**
     * 新增校园区域
     * 
     * @param campusArea 校园区域
     * @return 结果
     */
    @Override
    public int insertCampusArea(CampusArea campusArea)
    {
        campusArea.setCreateTime(DateUtils.getNowDate());
        return campusAreaMapper.insertCampusArea(campusArea);
    }

    /**
     * 修改校园区域
     * 
     * @param campusArea 校园区域
     * @return 结果
     */
    @Override
    public int updateCampusArea(CampusArea campusArea)
    {
        campusArea.setUpdateTime(DateUtils.getNowDate());
        return campusAreaMapper.updateCampusArea(campusArea);
    }

    /**
     * 批量删除校园区域
     * 
     * @param ids 需要删除的校园区域主键
     * @return 结果
     */
    @Override
    public int deleteCampusAreaByIds(Long[] ids)
    {
        for (Long id : ids) {
            if (repairDeviceMapper.countDeviceByAreaId(id) > 0) {
                throw new ServiceException("区域ID为" + id + "的区域正在被设备使用，不能删除");
            }
        }
        return campusAreaMapper.deleteCampusAreaByIds(ids);
    }

    /**
     * 删除校园区域信息
     * 
     * @param id 校园区域主键
     * @return 结果
     */
    @Override
    public int deleteCampusAreaById(Long id)
    {
        return deleteCampusAreaByIds(new Long[]{id});
    }
}
