package com.ruoyi.campus.service.impl;

import java.util.List;

import com.ruoyi.campus.mapper.RepairDeviceMapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.campus.mapper.RepairDeviceModelMapper;
import com.ruoyi.campus.domain.RepairDeviceModel;
import com.ruoyi.campus.service.IRepairDeviceModelService;

/**
 * 设备型号Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-10-29
 */
@Service
public class RepairDeviceModelServiceImpl implements IRepairDeviceModelService 
{
    @Autowired
    private RepairDeviceModelMapper repairDeviceModelMapper;

    @Autowired
    private RepairDeviceMapper repairDeviceMapper;

    /**
     * 查询设备型号
     * 
     * @param id 设备型号主键
     * @return 设备型号
     */
    @Override
    public RepairDeviceModel selectRepairDeviceModelById(Long id)
    {
        return repairDeviceModelMapper.selectRepairDeviceModelById(id);
    }

    /**
     * 查询设备型号列表
     * 
     * @param repairDeviceModel 设备型号
     * @return 设备型号
     */
    @Override
    public List<RepairDeviceModel> selectRepairDeviceModelList(RepairDeviceModel repairDeviceModel)
    {
        return repairDeviceModelMapper.selectRepairDeviceModelList(repairDeviceModel);
    }

    /**
     * 新增设备型号
     * 
     * @param repairDeviceModel 设备型号
     * @return 结果
     */
    @Override
    public int insertRepairDeviceModel(RepairDeviceModel repairDeviceModel)
    {
        repairDeviceModel.setCreateTime(DateUtils.getNowDate());
        return repairDeviceModelMapper.insertRepairDeviceModel(repairDeviceModel);
    }

    /**
     * 修改设备型号
     * 
     * @param repairDeviceModel 设备型号
     * @return 结果
     */
    @Override
    public int updateRepairDeviceModel(RepairDeviceModel repairDeviceModel)
    {
        repairDeviceModel.setUpdateTime(DateUtils.getNowDate());
        return repairDeviceModelMapper.updateRepairDeviceModel(repairDeviceModel);
    }

    /**
     * 批量删除设备型号
     * 
     * @param ids 需要删除的设备型号主键
     * @return 结果
     */
    @Override
    public int deleteRepairDeviceModelByIds(Long[] ids)
    {
        for (Long id : ids) {
            if(repairDeviceMapper.countDeviceByModelId(id)>0)
                throw new ServiceException( "型号ID为" + id + "的型号正在被设备使用，不能删除");
        }
        return repairDeviceModelMapper.deleteRepairDeviceModelByIds(ids);
    }

    /**
     * 删除设备型号信息
     * 
     * @param id 设备型号主键
     * @return 结果
     */
    @Override
    public int deleteRepairDeviceModelById(Long id)
    {
        return deleteRepairDeviceModelByIds(new Long[]{id});
    }
}
