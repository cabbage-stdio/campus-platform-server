package com.ruoyi.campus.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备信息对象 repair_device
 * 
 * @author ruoyi
 * @date 2026-10-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairDevice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long id;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    @NotBlank(message = "设备名称不能为空")
    private String deviceName;

    /** 设备型号ID */
    @Excel(name = "设备型号ID")
    @NotNull(message = "设备型号ID不能为空")
    @Positive(message = "设备型号ID必须大于0")
    private Long modelId;

    /** 所属区域ID */
    @Excel(name = "所属区域ID")
    @NotNull(message = "所属区域ID不能为空")
    @Positive(message = "所属区域ID必须大于0")
    private Long areaId;

    /** 具体位置 */
    @Excel(name = "具体位置")
    @NotBlank(message = "具体位置不能为空")
    private String locationDetail;

    /** 0启用 1停用 */
    @Excel(name = "0启用 1停用")
    @NotNull(message = "0启用 1停用不能为空")
    @Pattern(regexp = "[01]", message = "设备状态只能为01")
    private String status;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceCode", getDeviceCode())
            .append("deviceName", getDeviceName())
            .append("modelId", getModelId())
            .append("areaId", getAreaId())
            .append("locationDetail", getLocationDetail())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
