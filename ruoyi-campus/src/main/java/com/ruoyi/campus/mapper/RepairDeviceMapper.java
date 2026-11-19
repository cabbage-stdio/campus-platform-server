package com.ruoyi.campus.mapper;

import java.util.List;
import com.ruoyi.campus.domain.RepairDevice;

/**
 * 设备信息Mapper接口
 * 
 * @author ruoyi
 * @date 2026-10-29
 */
public interface RepairDeviceMapper 
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

    /** 新增后按自增 ID 写入设备编号 */
    public int updateDeviceCode(RepairDevice repairDevice);

    /**
     * 修改设备信息
     * 
     * @param repairDevice 设备信息
     * @return 结果
     */
    public int updateRepairDevice(RepairDevice repairDevice);

    /**
     * 删除设备信息
     * 
     * @param id 设备信息主键
     * @return 结果
     */
    public int deleteRepairDeviceById(Long id);

    /**
     * 批量删除设备信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepairDeviceByIds(Long[] ids);

    /**
     * 根据模型 ID 统计设备数量
     *
     * @param modelId 模型 ID
     * @return 设备数量
     */
    public int countDeviceByModelId(Long modelId);


    /**
     * 根据区域 ID 统计设备数量
     *
     * @param areaId 区域 ID
     * @return 设备数量
     */
    public int countDeviceByAreaId(Long areaId);
}
