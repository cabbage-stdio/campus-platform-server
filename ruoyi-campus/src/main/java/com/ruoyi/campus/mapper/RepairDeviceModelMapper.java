package com.ruoyi.campus.mapper;

import java.util.List;
import com.ruoyi.campus.domain.RepairDeviceModel;

/**
 * 设备型号Mapper接口
 * 
 * @author ruoyi
 * @date 2026-10-29
 */
public interface RepairDeviceModelMapper 
{
    /**
     * 查询设备型号
     * 
     * @param id 设备型号主键
     * @return 设备型号
     */
    public RepairDeviceModel selectRepairDeviceModelById(Long id);

    /**
     * 查询设备型号列表
     * 
     * @param repairDeviceModel 设备型号
     * @return 设备型号集合
     */
    public List<RepairDeviceModel> selectRepairDeviceModelList(RepairDeviceModel repairDeviceModel);

    /**
     * 新增设备型号
     * 
     * @param repairDeviceModel 设备型号
     * @return 结果
     */
    public int insertRepairDeviceModel(RepairDeviceModel repairDeviceModel);

    /**
     * 修改设备型号
     * 
     * @param repairDeviceModel 设备型号
     * @return 结果
     */
    public int updateRepairDeviceModel(RepairDeviceModel repairDeviceModel);

    /**
     * 删除设备型号
     * 
     * @param id 设备型号主键
     * @return 结果
     */
    public int deleteRepairDeviceModelById(Long id);

    /**
     * 批量删除设备型号
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepairDeviceModelByIds(Long[] ids);
}
