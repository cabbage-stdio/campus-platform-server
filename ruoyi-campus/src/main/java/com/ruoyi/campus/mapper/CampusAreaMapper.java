package com.ruoyi.campus.mapper;

import java.util.List;
import com.ruoyi.campus.domain.CampusArea;

/**
 * 校园区域Mapper接口
 * 
 * @author ruoyi
 * @date 2026-10-30
 */
public interface CampusAreaMapper 
{
    /**
     * 查询校园区域
     * 
     * @param id 校园区域主键
     * @return 校园区域
     */
    public CampusArea selectCampusAreaById(Long id);

    /**
     * 查询校园区域列表
     * 
     * @param campusArea 校园区域
     * @return 校园区域集合
     */
    public List<CampusArea> selectCampusAreaList(CampusArea campusArea);

    /**
     * 新增校园区域
     * 
     * @param campusArea 校园区域
     * @return 结果
     */
    public int insertCampusArea(CampusArea campusArea);

    /**
     * 修改校园区域
     * 
     * @param campusArea 校园区域
     * @return 结果
     */
    public int updateCampusArea(CampusArea campusArea);

    /**
     * 删除校园区域
     * 
     * @param id 校园区域主键
     * @return 结果
     */
    public int deleteCampusAreaById(Long id);

    /**
     * 批量删除校园区域
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCampusAreaByIds(Long[] ids);
}
