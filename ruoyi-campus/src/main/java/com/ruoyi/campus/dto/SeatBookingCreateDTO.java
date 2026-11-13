package com.ruoyi.campus.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatBookingCreateDTO {

    @NotNull(message = "请选择座位")
    @Positive(message = "座位ID必须大于0")
    private Long seatId;

    @NotNull(message = "请选择预约日期")
    private LocalDate bookingDate;

    @NotEmpty(message = "请选择预约时段")
    @Size(max = 5, message = "最多选择5个时段")
    private List<@NotNull @Min(1) @Max(5) Integer> slotNos;
}
