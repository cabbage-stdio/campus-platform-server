package com.ruoyi.campus.enums;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public enum SeatTimeSlot {

    SLOT_1(1, LocalTime.of(6, 0), LocalTime.of(10, 0)),
    SLOT_2(2, LocalTime.of(10, 0), LocalTime.of(14, 0)),
    SLOT_3(3, LocalTime.of(14, 0), LocalTime.of(18, 0)),
    SLOT_4(4, LocalTime.of(18, 0), LocalTime.of(22, 0)),
    SLOT_5(5, LocalTime.of(22, 0), LocalTime.MIDNIGHT);

    private final int code;
    private final LocalTime start;
    private final LocalTime end;

    SeatTimeSlot(int code, LocalTime start, LocalTime end) {
        this.code = code;
        this.start = start;
        this.end = end;
    }

    public int getCode() {
        return code;
    }

    public LocalDateTime getStartTime(LocalDate date) {
        return date.atTime(start);
    }

    public LocalDateTime getEndTime(LocalDate date) {
        // 最后一段跨到第二天凌晨
        LocalDate endDate = end.isAfter(start)
                ? date
                : date.plusDays(1);

        return endDate.atTime(end);
    }

    public static SeatTimeSlot fromCode(int code) {
        for (SeatTimeSlot slot : values()) {
            if (slot.code == code) {
                return slot;
            }
        }
        throw new IllegalArgumentException("无效的预约时段：" + code);
    }
}