package com.ruoyi.campus.service;

import java.util.List;
import com.ruoyi.campus.domain.RepairDevice;

/**
 * 设备信息Service接口
 * 
 * @author ruoyi
 * @date 2026-10-29
 */
public interface IRepairDeviceService 
{
    /**
     * 查询设备信息
     * 
     * @param id 设备信息主键
     * @return 设备信息
     */
    public RepairDevice selectRepairDeviceById(Long id);

    /**
     * 查询设备信息列表
     * 
     * @param repairDevice 设备信息
     * @return 设备信息集合
     */
    public List<RepairDevice> selectRepairDeviceList(RepairDevice repairDevice);

    /**
     * 新增设备信息
     * 
     * @param repairDevice 设备信息
     * @return 结果
     */
    public int insertRepairDevice(RepairDevice repairDevice);

    /**
     * 修改设备信息
     * 
     * @param repairDevice 设备信息
     * @return 结果
     */
    public int updateRepairDevice(RepairDevice repairDevice);

    /**
     * 批量删除设备信息
     * 
     * @param ids 需要删除的设备信息主键集合
     * @return 结果
     */
    public int deleteRepairDeviceByIds(Long[] ids);

    /**
     * 删除设备信息信息
     * 
     * @param id 设备信息主键
     * @return 结果
     */
    public int deleteRepairDeviceById(Long id);
}
