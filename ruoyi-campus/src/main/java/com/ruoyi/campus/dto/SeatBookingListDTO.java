package com.ruoyi.campus.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatBookingListDTO {
    // 区域ID
    @NotNull(message = "请选择区域")
    @Positive(message = "区域ID必须大于0")
    private Long areaId;

    // 楼层号
    @NotNull(message = "楼层不能为空")
    @Positive(message = "楼层必须大于0")
    private Long floorNo;

    //预约日期
    @NotNull(message = "预约日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;
}
