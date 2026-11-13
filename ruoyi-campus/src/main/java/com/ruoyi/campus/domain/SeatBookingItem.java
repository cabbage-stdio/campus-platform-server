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
 * 座位预约时段明细对象 seat_booking_item
 * 
 * @author ruoyi
 * @date 2026-11-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatBookingItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预约明细ID */
    private Long id;

    /** 预约主表ID */
    private Long bookingId;

    /** 时段编号：1至5 */
    private Long slotNo;

    /** 实际开始时间 */
    private Date startTime;

    /** 实际结束时间 */
    private Date endTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bookingId", getBookingId())
            .append("slotNo", getSlotNo())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("createTime", getCreateTime())
            .toString();
    }
}
