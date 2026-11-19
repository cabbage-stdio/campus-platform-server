package com.ruoyi.campus.service.impl;

import java.util.List;

import com.ruoyi.campus.mapper.CampusAreaMapper;
import com.ruoyi.campus.mapper.RepairDeviceModelMapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.campus.mapper.RepairDeviceMapper;
import com.ruoyi.campus.domain.RepairDevice;
import com.ruoyi.campus.service.IRepairDeviceService;

/**
 * 设备信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-10-29
 */
@Service
public class RepairDeviceServiceImpl implements IRepairDeviceService 
{
    @Autowired
    private RepairDeviceMapper repairDeviceMapper;

    @Autowired
    private RepairDeviceModelMapper repairDeviceModelMapper;

    @Autowired
    private CampusAreaMapper campusAreaMapper;

    /**
     * 查询设备信息
     * 
     * @param id 设备信息主键
     * @return 设备信息
     */
    @Override
    public RepairDevice selectRepairDeviceById(Long id)
    {
        return repairDeviceMapper.selectRepairDeviceById(id);
    }

    /**
     * 查询设备信息列表
     * 
     * @param repairDevice 设备信息
     * @return 设备信息
     */
    @Override
    public List<RepairDevice> selectRepairDeviceList(RepairDevice repairDevice)
    {
        return repairDeviceMapper.selectRepairDeviceList(repairDevice);
    }

    /**
     * 新增设备信息
     * 
     * @param repairDevice 设备信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRepairDevice(RepairDevice repairDevice)
    {
        repairDevice.setCreateTime(DateUtils.getNowDate());
        repairDevice.setUpdateTime(DateUtils.getNowDate());
        repairDevice.setUpdateBy(SecurityUtils.getUsername());
        repairDevice.setCreateBy(SecurityUtils.getUsername());


        if (repairDeviceModelMapper.selectRepairDeviceModelById(repairDevice.getModelId()) == null)
            throw new ServiceException("新增设备失败，设备型号不存在");

        if (campusAreaMapper.selectCampusAreaById(repairDevice.getAreaId()) == null)
            throw new ServiceException("新增设备失败，所属区域不存在");

        // 编号由数据库自增 ID 生成，忽略前端提交的编号
        repairDevice.setDeviceCode(null);
        int rows = repairDeviceMapper.insertRepairDevice(repairDevice);
        if (rows != 1 || repairDevice.getId() == null)
        {
            throw new IllegalStateException("新增设备失败，未获取到设备 ID");
        }

        repairDevice.setDeviceCode(String.format("DEV-%06d", repairDevice.getId()));
        if (repairDeviceMapper.updateDeviceCode(repairDevice) != 1)
        {
            throw new IllegalStateException("生成设备编号失败");
        }
        return rows;
    }

    /**
     * 修改设备信息
     * 
     * @param repairDevice 设备信息
     * @return 结果
     */
    @Override
    public int updateRepairDevice(RepairDevice repairDevice)
    {
        if(repairDevice.getId()==null){
            throw new ServiceException("更新设备信息失败，设备 ID 不能为空");
        }
        if(repairDeviceMapper.selectRepairDeviceById(repairDevice.getId())==null){
            throw new ServiceException("更新设备信息失败，设备不存在");
        }

        repairDevice.setUpdateBy(SecurityUtils.getUsername());
        repairDevice.setUpdateTime(DateUtils.getNowDate());
        return repairDeviceMapper.updateRepairDevice(repairDevice);
    }

    /**
     * 批量删除设备信息
     * 
     * @param ids 需要删除的设备信息主键
     * @return 结果
     */
    @Override
    public int deleteRepairDeviceByIds(Long[] ids)
    {
        return repairDeviceMapper.deleteRepairDeviceByIds(ids);
    }

    /**
     * 删除设备信息信息
     * 
     * @param id 设备信息主键
     * @return 结果
     */
    @Override
    public int deleteRepairDeviceById(Long id)
    {
        return repairDeviceMapper.deleteRepairDeviceById(id);
    }
}
