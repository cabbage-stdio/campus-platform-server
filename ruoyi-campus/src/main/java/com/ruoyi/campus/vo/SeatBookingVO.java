package com.ruoyi.campus.vo;

import com.ruoyi.campus.domain.SeatBooking;
import com.ruoyi.campus.domain.SeatBookingItem;
import com.ruoyi.common.annotation.Excel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SeatBookingVO extends SeatBooking {

    @Excel(name = "预约账号")
    private String userName;

    @Excel(name = "用户昵称")
    private String nickName;

    @Excel(name = "预约时间段")
    private List<Integer> slotNos;

    private List<SeatBookingItem> items;
}