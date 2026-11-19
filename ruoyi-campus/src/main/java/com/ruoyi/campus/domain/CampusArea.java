package com.ruoyi.campus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 校园区域对象 campus_area
 * 
 * @author ruoyi
 * @date 2026-10-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CampusArea extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 区域ID */
    private Long id;

    /** 区域编码 */
    @Excel(name = "区域编码")
    private String areaCode;

    /** 区域名称 */
    @Excel(name = "区域名称")
    private String areaName;

    /** 显示顺序 */
    private Integer sortOrder;

    /** 0启用 1停用 */
    @Excel(name = "0启用 1停用")
    private String status;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("areaCode", getAreaCode())
            .append("areaName", getAreaName())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
