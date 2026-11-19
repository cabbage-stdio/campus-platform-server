package com.ruoyi.campus.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 座位管理对象 seat
 * 
 * @author ruoyi
 * @date 2026-11-04
 */
public class Seat extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 座位 */
    private Long id;

    /** 所属楼栋 */
    @Excel(name = "所属楼栋")
    private Long areaId;

    /** 楼层 */
    @Excel(name = "楼层")
    private Long floorNo;

    /** 座位编号 */
    @Excel(name = "座位编号")
    private String seatCode;

    /** 具体位置 */
    private String locationDetail;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setAreaId(Long areaId) 
    {
        this.areaId = areaId;
    }

    public Long getAreaId() 
    {
        return areaId;
    }

    public void setFloorNo(Long floorNo) 
    {
        this.floorNo = floorNo;
    }

    public Long getFloorNo() 
    {
        return floorNo;
    }

    public void setSeatCode(String seatCode) 
    {
        this.seatCode = seatCode;
    }

    public String getSeatCode() 
    {
        return seatCode;
    }

    public void setLocationDetail(String locationDetail) 
    {
        this.locationDetail = locationDetail;
    }

    public String getLocationDetail() 
    {
        return locationDetail;
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
            .append("areaId", getAreaId())
            .append("floorNo", getFloorNo())
            .append("seatCode", getSeatCode())
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
