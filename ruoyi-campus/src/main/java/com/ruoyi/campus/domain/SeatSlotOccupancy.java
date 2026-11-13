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
 * 座位有效时段占用对象 seat_slot_occupancy
 * 
 * @author ruoyi
 * @date 2026-11-05
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatSlotOccupancy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 占用ID */
    private Long id;

    /** 预约主表ID */
    private Long bookingId;

    /** 预约明细ID */
    private Long bookingItemId;

    /** 座位ID */
    @Excel(name = "座位ID")
    private Long seatId;

    /** 预约用户ID */
    @Excel(name = "预约用户ID")
    private Long userId;

    /** 预约使用日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预约使用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date bookingDate;

    /** 时段编号：1至5 */
    private Long slotNo;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bookingId", getBookingId())
            .append("bookingItemId", getBookingItemId())
            .append("seatId", getSeatId())
            .append("userId", getUserId())
            .append("bookingDate", getBookingDate())
            .append("slotNo", getSlotNo())
            .append("createTime", getCreateTime())
            .toString();
    }
}
