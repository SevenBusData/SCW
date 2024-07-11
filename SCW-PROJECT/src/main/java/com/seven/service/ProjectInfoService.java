package com.seven.service;

/**
 * @author: seven
 * @since: 2024/7/11 16:04
 */
import com.seven.entity.TProject;
import com.seven.entity.TProjectImages;
import com.seven.entity.TReturn;

import java.util.List;

public interface ProjectInfoService {
    /**
     * 根据项目编号获取项目回报
     *
     * @param projectId
     * @return
     */
    public List<TReturn> getProjectReturns(Integer projectId);

    /**
     * 获取当前系统全部项目
     * @return
     */
    public List<TProject> getAllProjects();



    /**
     * 获取指定项目的配图
     * @param id  项目id
     * @return
     */
    public List<TProjectImages> getProjectImages(Integer id);

    /**
     * 获得项目信息
     *
     * @param projectId
     * @return
     */
    public TProject getProjectInfo(Integer projectId);

    /**
     * 获取项目回报详细信息
     * @param returnId
     * @return
     */
    public TReturn getRetuenInfo(Integer returnId);
}