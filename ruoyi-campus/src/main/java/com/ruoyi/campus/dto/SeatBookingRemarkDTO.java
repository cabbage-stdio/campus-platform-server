package com.ruoyi.campus.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SeatBookingRemarkDTO {

    @NotNull(message = "请传入备注字段")
    @Size(max = 200, message = "备注不能超过200个字符")
    private String remark;
}