package com.seven.service.impl;

/**
 * @author: seven
 * @since: 2024/7/11 16:05
 */
import com.seven.entity.TProject;
import com.seven.entity.TProjectImages;
import com.seven.entity.TReturn;
import com.seven.repository.ProjectImagesRepository;
import com.seven.repository.ProjectRepository;
import com.seven.repository.ReturnRepository;
import com.seven.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
    @Autowired
    private ReturnRepository returnRepository;

    @Override
    public List<TReturn> getProjectReturns(Integer projectId) {
        return returnRepository.findByProjectid(projectId);
    }

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<TProject> getAllProjects() {

        return projectRepository.findAll();
    }

    @Autowired
    private ProjectImagesRepository projectImagesRepository;

    @Override
    public List<TProjectImages> getProjectImages(Integer id) {
        return projectImagesRepository.findByProjectid(id);
    }

    @Override
    public TProject getProjectInfo(Integer projectId) {

        return projectRepository.findById(projectId).get();
    }

    @Override
    public TReturn getRetuenInfo(Integer returnId) {
        return returnRepository.findById(returnId).get();
    }
}

