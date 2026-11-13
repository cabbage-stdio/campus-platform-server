package com.ruoyi.campus.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.campus.domain.Seat;
import com.ruoyi.campus.domain.SeatBookingItem;
import com.ruoyi.campus.domain.SeatSlotOccupancy;
import com.ruoyi.campus.dto.SeatBookingCancelDTO;
import com.ruoyi.campus.dto.SeatBookingCreateDTO;
import com.ruoyi.campus.dto.SeatBookingListDTO;
import com.ruoyi.campus.enums.SeatTimeSlot;
import com.ruoyi.campus.mapper.SeatBookingItemMapper;
import com.ruoyi.campus.mapper.SeatMapper;
import com.ruoyi.campus.mapper.SeatSlotOccupancyMapper;
import com.ruoyi.campus.vo.MySeatBookingVO;
import com.ruoyi.campus.vo.SeatBookingListVO;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.ruoyi.campus.mapper.SeatBookingMapper;
import com.ruoyi.campus.domain.SeatBooking;
import com.ruoyi.campus.service.ISeatBookingService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 座位预约Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-11-05
 */
@Slf4j
@Service
public class SeatBookingServiceImpl implements ISeatBookingService
{
    @Autowired
    private SeatBookingMapper seatBookingMapper;

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private SeatBookingItemMapper seatBookingItemMapper;

    @Autowired
    private SeatSlotOccupancyMapper seatSlotOccupancyMapper;

    public List<SeatBooking> selectSeatBookingList(SeatBooking seatBooking)
    {
        return seatBookingMapper.selectSeatBookingList(seatBooking);
    }

    /**
     * 查询座位预约
     * 
     * @param id 座位预约主键
     * @return 座位预约
     */
    @Override
    public SeatBooking selectSeatBookingById(Long id)
    {
        return seatBookingMapper.selectSeatBookingById(id);
    }

    /**
     * 新增座位预约
     * 
     * @param seatBooking 座位预约
     * @return 结果
     */
    @Override
    public int insertSeatBooking(SeatBooking seatBooking)
    {
        seatBooking.setCreateTime(DateUtils.getNowDate());
        return seatBookingMapper.insertSeatBooking(seatBooking);
    }

    /**
     * 修改座位预约
     * 
     * @param seatBooking 座位预约
     * @return 结果
     */
    @Override
    public int updateSeatBooking(SeatBooking seatBooking)
    {
        seatBooking.setUpdateTime(DateUtils.getNowDate());
        return seatBookingMapper.updateSeatBooking(seatBooking);
    }

    /**
     * 批量删除座位预约
     * 
     * @param ids 需要删除的座位预约主键
     * @return 结果
     */
    @Override
    public int deleteSeatBookingByIds(Long[] ids)
    {
        return seatBookingMapper.deleteSeatBookingByIds(ids);
    }

    /**
     * 删除座位预约信息
     * 
     * @param id 座位预约主键
     * @return 结果
     */
    @Override
    public int deleteSeatBookingById(Long id)
    {
        return seatBookingMapper.deleteSeatBookingById(id);
    }

    /**
     * 创建座位预约信息
     *
     * @param seatBookingCreateDTO 座位预约信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createBooking(SeatBookingCreateDTO seatBookingCreateDTO) {
        log.info("开始创建预约信息{}", seatBookingCreateDTO);
        //创建预约主表的信息
        Seat seat = seatMapper.selectSeatById(seatBookingCreateDTO.getSeatId());
        if(seat == null) {
            throw new ServiceException("所选择的座位不存在");
        }

        if(!"0".equals(seat.getStatus()))
            throw new ServiceException("所选择的座位已停用");

        SeatBooking seatBooking= SeatBooking.builder()
                .seatCode(seat.getSeatCode())
                .userId(SecurityUtils.getUserId())
                .bookingDate(java.sql.Date.valueOf(seatBookingCreateDTO.getBookingDate()))
                .status("0")
                .build();
        BeanUtils.copyProperties(seatBookingCreateDTO, seatBooking);
        seatBooking.setCreateTime(DateUtils.getNowDate());
        seatBooking.setUpdateTime(DateUtils.getNowDate());
        seatBooking.setCreateBy(SecurityUtils.getUsername());
        seatBooking.setUpdateBy(SecurityUtils.getUsername());

        int insertSeatBooking = seatBookingMapper.insertSeatBooking(seatBooking);

        if(insertSeatBooking != 1)
            throw new ServiceException("预约主表创建失败");

        //创建预约详情时段
        ZoneId zone = ZoneId.of("Asia/Shanghai");
        LocalDateTime now = LocalDateTime.now();
        LocalDate bookingDate = seatBookingCreateDTO.getBookingDate();
        List<Integer> slotNos = seatBookingCreateDTO.getSlotNos();

        //判断预约时间是否合法
        int maxDays = now.toLocalTime().isBefore(LocalTime.of(22, 0)) ? 2 : 3;

        long days = ChronoUnit.DAYS.between(now.toLocalDate(), bookingDate);

        if(days < 0 || days > maxDays)
            throw new ServiceException("所选日期不在有效预约日期内");

        if(new HashSet<>(slotNos).size() != slotNos.size())
            throw new ServiceException("预约时段有重复");



        List<SeatBookingItem> seatBookingItems = new ArrayList<>();

        for (Integer slotNo : slotNos) {
            SeatTimeSlot seatTimeSlot = SeatTimeSlot.fromCode(slotNo);

            LocalDateTime startTime = seatTimeSlot.getStartTime(bookingDate);
            LocalDateTime endTime = seatTimeSlot.getEndTime(bookingDate);

            if (endTime.isBefore(now)) {
                throw new ServiceException("所选时段已经结束，请重新选择");
            }

            SeatBookingItem item = SeatBookingItem.builder()
                    .bookingId(seatBooking.getId())
                    .slotNo(slotNo.longValue())
                    .startTime(Date.from(startTime.atZone(zone).toInstant()))
                    .endTime(Date.from(endTime.atZone(zone).toInstant()))
                    .build();
            item.setCreateTime(DateUtils.getNowDate());
            item.setUpdateTime(DateUtils.getNowDate());
            item.setCreateBy(SecurityUtils.getUsername());
            item.setUpdateBy(SecurityUtils.getUsername());

            seatBookingItems.add(item);
        }

        int insertSeatBookingItems = seatBookingItemMapper.insertSeatBookingItemBatch(seatBookingItems);

        if(insertSeatBookingItems != seatBookingItems.size())
            throw new ServiceException("预约详情时段创建失败");

        //创建座位占用信息

        List<SeatSlotOccupancy> seatSlotOccupancies = new ArrayList<>();

        for (SeatBookingItem seatBookingItem : seatBookingItems) {
            SeatSlotOccupancy seatSlotOccupancy = SeatSlotOccupancy.builder()
                    .bookingId(seatBooking.getId())
                    .bookingItemId(seatBookingItem.getId())
                    .seatId(seat.getId())
                    .userId(SecurityUtils.getUserId())
                    .bookingDate(java.sql.Date.valueOf(seatBookingCreateDTO.getBookingDate()))
                    .slotNo(seatBookingItem.getSlotNo())
                    .build();
            seatSlotOccupancy.setCreateTime(DateUtils.getNowDate());
            seatSlotOccupancies.add(seatSlotOccupancy);
        }

        try {
            int insertSeatSlotOccupancys = seatSlotOccupancyMapper.insertSeatSlotOccupancyBatch(seatSlotOccupancies);

            if(insertSeatSlotOccupancys != seatSlotOccupancies.size())
                throw new ServiceException("座位占用信息创建失败");
        } catch (DuplicateKeyException e) {
            throw new ServiceException("预约冲突，该座位已被预约或你已有同时段预约");
        }

        return insertSeatBooking;
    }

    /**
     * 获取座位预约信息
     *
     * @param seatBookingListDTO 座位预约查询信息
     * @return 座位预约信息
     */
    @Override
    public List<SeatBookingListVO> getSeatAvailability(SeatBookingListDTO seatBookingListDTO) {
        log.info("开始获取座位预约信息{}", seatBookingListDTO);

        //1.判断预约时间是否合法
        //获取时区
        ZoneId zone = ZoneId.of("Asia/Shanghai");
        //获取当前时间
        LocalDateTime now = LocalDateTime.now();
        //获取预约时间
        LocalDate bookingDate = seatBookingListDTO.getBookingDate();
        //获取最大预约天数
        int maxDays = now.toLocalTime().isBefore(LocalTime.of(22, 0)) ? 2 : 3;
        //计算预约时间与当前时间相差天数
        long days = ChronoUnit.DAYS.between(now.toLocalDate(), bookingDate);
        //判断预约时间是否合法
        if(days < 0 || days > maxDays)
            throw new ServiceException("所选日期不在有效预约范围内");

        //2.获取楼层座位列表
        Seat seatDTO=new Seat();
        seatDTO.setFloorNo(seatBookingListDTO.getFloorNo());
        seatDTO.setAreaId(seatBookingListDTO.getAreaId());

        //获取楼层座位列表
        List<Seat> seats = seatMapper.selectSeatList(seatDTO);

        if(seats == null || seats.size() == 0)
            return List.of();

        //3.获取位置占用信息
        List<SeatSlotOccupancy> seatSlotOccupancies = seatSlotOccupancyMapper.selectOccupancyByFloor(seatBookingListDTO);


        // 4. 整理成：座位ID -> 已占用时段列表
        Map<Long,List<Integer>> occupiedMap = new HashMap<>();

        for (SeatSlotOccupancy seatSlotOccupancy : seatSlotOccupancies) {
            occupiedMap
                    .computeIfAbsent(
                            seatSlotOccupancy.getSeatId(),
                            key -> new ArrayList<>()
                    )
                    .add(seatSlotOccupancy.getSlotNo().intValue());
        }

        //5. 将座位信息和占用信息组合成结果列表

        List<SeatBookingListVO> result=new ArrayList<>();

        for (Seat seat : seats) {
            SeatBookingListVO seatBookingListVO=new SeatBookingListVO();

            BeanUtils.copyProperties(seat, seatBookingListVO);

            //将编号转为座位编号
            String seatCode = seat.getSeatCode();
            Integer layoutNo = Integer.valueOf(seatCode.substring(seatCode.lastIndexOf('A') + 1));

            seatBookingListVO.setLayoutNo(layoutNo);

            seatBookingListVO.setOccupiedSlotNos(occupiedMap.getOrDefault(seat.getId(), List.of()));

            result.add(seatBookingListVO);
        }

        return result;
    }

    /**
     * 取消座位预约信息
     *
     * @param bookingId 预约主键
     * @param seatBookingCancelDTO 预约取消信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancelBooking(Long bookingId, SeatBookingCancelDTO seatBookingCancelDTO) {
        log.info("开始取消预约信息{}", bookingId);

        // 获取旧的预约信息
        SeatBooking oldSeatBooking = seatBookingMapper.selectSeatBookingById(bookingId);
        // 判断预约信息是否存在
        if (oldSeatBooking == null) {
            throw new ServiceException("预约信息不存在");
        }

        if (!oldSeatBooking.getStatus().equals("0")) {
            throw new ServiceException("预约信息已处理，请勿重复操作");
        }

        if (!oldSeatBooking.getUserId().equals(SecurityUtils.getUserId()))
            throw new ServiceException("无权限取消他人预约");

        //判断取消时间是否合法
        SeatBookingItem seatBookingItem=new SeatBookingItem();
        seatBookingItem.setBookingId(bookingId);

        List<SeatBookingItem> seatBookingItems = seatBookingItemMapper.selectSeatBookingItemList(seatBookingItem);

        if (seatBookingItems.isEmpty()) {
            throw new ServiceException("预约时段信息不存在");
        }

        Date now =DateUtils.getNowDate();

        for (SeatBookingItem bookingItem : seatBookingItems) {
            Date endTime=bookingItem.getEndTime();

            if(endTime==null){
                throw new ServiceException("预约时段结束时间异常");
            }

            if (endTime.before(now)) {
                throw new ServiceException("预约时段已结束，无法取消");
            }
        }


        // 创建新预约信息对象
        SeatBooking newSeatBooking = SeatBooking.builder()
                .id(oldSeatBooking.getId())
                .userId(SecurityUtils.getUserId())
                .cancelReason(seatBookingCancelDTO.getCancelReason())
                .cancelTime(DateUtils.getNowDate())
                .build();
        newSeatBooking.setUpdateBy(SecurityUtils.getUsername());
        newSeatBooking.setUpdateTime(DateUtils.getNowDate());

        int cancelBooking = seatBookingMapper.cancelBooking(newSeatBooking);
        // 判断预约状态是否已变化
        if (cancelBooking != 1) {
            throw new ServiceException("预约状态已变化，请刷新后重试");
        }

        // 删除座位占用
        int deleteByBookingId = seatSlotOccupancyMapper.deleteByBookingId(bookingId);
        if (deleteByBookingId ==0) {
            throw new ServiceException("座位占用信息删除失败,请刷新后重试");
        }

        return cancelBooking;
    }

    /**
     * 查询我的座位预约信息
     * @return
     */
    @Override
    public List<MySeatBookingVO> getMyBookings() {
        //获取当前用户的预约信息
        Long userId = SecurityUtils.getUserId();
        List<MySeatBookingVO> mySeatBookingVOS = seatBookingMapper.selectMyBookings(userId);
        //判断预约信息是否存在
        if(mySeatBookingVOS.isEmpty()){
            return mySeatBookingVOS;
        }
        //获取预约信息中的所有预约ID
        List<Long> bookingIds = mySeatBookingVOS.stream().map(MySeatBookingVO::getId).collect(Collectors.toList());

        //根据预约ID获取预约时段信息
        List<SeatBookingItem> seatBookingItems = seatBookingItemMapper.selectByBookingIds(bookingIds);
        //判断预约时段信息是否存在
        Map<Long, List<SeatBookingItem>> itemsMap= new HashMap<>();
        //将预约时段信息按预约ID分组
        for (SeatBookingItem seatBookingItem : seatBookingItems) {
            itemsMap.computeIfAbsent(seatBookingItem.getBookingId(),key -> new ArrayList<>()).add(seatBookingItem);
        }

        Date now = DateUtils.getNowDate();

        for (MySeatBookingVO mySeatBookingVO : mySeatBookingVOS) {
            //获取预约时段信息
            List<SeatBookingItem> bookingItems = itemsMap.getOrDefault(mySeatBookingVO.getId(), List.of());
            //设置预约时段信息
            mySeatBookingVO.setItems(bookingItems);
            //判断是否可以取消预约
            boolean canCancel = "0".equals(mySeatBookingVO.getStatus()) && !bookingItems.isEmpty();
            //判断预约时段是否可以取消
            for (SeatBookingItem bookingItem : bookingItems) {
                Date endTime = bookingItem.getEndTime();
                //判断预约时段是否已结束
                if (endTime == null || !endTime.after(now)) {
                    canCancel = false;
                    break;
                }
            }
            mySeatBookingVO.setCanCancel(canCancel);
        }

        return mySeatBookingVOS;
    }


}
