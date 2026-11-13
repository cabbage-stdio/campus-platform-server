package com.ruoyi.campus.vo;

import com.ruoyi.campus.domain.SeatBooking;
import com.ruoyi.campus.domain.SeatBookingItem;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MySeatBookingVO extends SeatBooking {

    // 这条预约包含的时段
    private List<SeatBookingItem> items;

    // 是否允许取消
    private Boolean canCancel;
}