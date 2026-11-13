package com.ruoyi.campus.dto;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatBookingCancelDTO {

    @Size(max = 200, message = "取消原因不能超过200个字符")
    private String cancelReason;
}
