package com.ruoyi.campus.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备型号对象 repair_device_model
 * 
 * @author ruoyi
 * @date 2026-10-29
 */
public class RepairDeviceModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 型号ID */
    private Long id;

    /** 品牌或厂商 */
    @Excel(name = "品牌或厂商")
    private String manufacturer;

    /** 厂商型号 */
    @Excel(name = "厂商型号")
    private String modelCode;

    /** 型号名称 */
    @Excel(name = "型号名称")
    private String modelName;

    /** 设备分类，使用系统字典 */
    @Excel(name = "设备分类，使用系统字典")
    private String deviceType;

    /** 0启用 1停用 */
    @Excel(name = "0启用 1停用")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setManufacturer(String manufacturer) 
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }

    public void setModelCode(String modelCode) 
    {
        this.modelCode = modelCode;
    }

    public String getModelCode() 
    {
        return modelCode;
    }

    public void setModelName(String modelName) 
    {
        this.modelName = modelName;
    }

    public String getModelName() 
    {
        return modelName;
    }

    public void setDeviceType(String deviceType) 
    {
        this.deviceType = deviceType;
    }

    public String getDeviceType() 
    {
        return deviceType;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("manufacturer", getManufacturer())
            .append("modelCode", getModelCode())
            .append("modelName", getModelName())
            .append("deviceType", getDeviceType())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
