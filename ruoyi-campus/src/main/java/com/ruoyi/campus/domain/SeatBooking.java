package com.ruoyi.campus.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 座位预约对象 seat_booking
 * 
 * @author ruoyi
 * @date 2026-11-05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatBooking extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预约 */
    @Excel(name = "预约")
    private Long id;

    /** 预约用户ID，对应sys_user.user_id */
    @Excel(name = "预约用户ID，对应sys_user.user_id")
    private Long userId;

    /** 座位ID，对应seat.id */
    private Long seatId;

    /** 预约时的座位编号快照 */
    @Excel(name = "预约时的座位编号快照")
    private String seatCode;

    /** 预约使用日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预约使用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date bookingDate;

    /** 预约状态：0已确认 1已取消 */
    @Excel(name = "预约状态：0已确认 1已取消")
    private String status;

    /** 取消时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "取消时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cancelTime;

    /** 取消原因 */
    private String cancelReason;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("seatId", getSeatId())
            .append("seatCode", getSeatCode())
            .append("bookingDate", getBookingDate())
            .append("status", getStatus())
            .append("cancelTime", getCancelTime())
            .append("cancelReason", getCancelReason())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
