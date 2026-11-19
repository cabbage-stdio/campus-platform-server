package com.ruoyi.campus.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class SeatBookingQueryDTO {

    private Long userId;    // 保留原有的按用户 ID 查询
    private String userName;
    private String seatCode;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate bookingDate;

    private String status;
}