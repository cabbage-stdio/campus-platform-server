package com.ruoyi.campus.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatBookingListVO {
    private Long id;

    private String seatCode;

    private Long floorNo;

    private Integer layoutNo;

    /** 座位启停状态 */
    private String status;

    /** 查询日期已占用的时段 */
    private List<Integer> occupiedSlotNos = new ArrayList<>();
}
