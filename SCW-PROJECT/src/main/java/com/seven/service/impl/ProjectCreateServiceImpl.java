package com.seven.service.impl;

/**
 * @author: seven
 * @since: 2024/7/11 13:30
 */
import com.alibaba.fastjson.JSON;

import com.seven.entity.*;
import com.seven.enums.ProjectImageTypeEnume;
import com.seven.enums.ProjectStatusEnume;
import com.seven.repository.*;
import com.seven.service.ProjectCreateService;
import com.seven.vo.ProjectRedisStorageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class ProjectCreateServiceImpl implements ProjectCreateService {
    @Value("${temp.profix}")
    private String profix;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectImagesRepository projectImagesRepository;
    @Autowired
    private ProjectTagRepository projectTagRepository;
    @Autowired
    private ProjectTypeRepository projectTypeRepository;
    @Autowired
    private ReturnRepository returnRepository;

    @Override
    public String initCreateProject(Integer memberId) {

        String token = UUID.randomUUID().toString().replace("-", "");
        //创建项目临时对象
        ProjectRedisStorageVo initVo = new ProjectRedisStorageVo();
        //设置memberid
        initVo.setMemberid(memberId);
        //把对象转换为字符串
        String jsonString = JSON.toJSONString(initVo);

        //存储到redis
        redisTemplate.opsForValue().set(profix + token, jsonString);

        return token;

    }




    @Override
    public void saveProjectInfo(ProjectStatusEnume auth, ProjectRedisStorageVo vo) {
        //保存项目
        TProject project = new TProject();
        BeanUtils.copyProperties(vo, project);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        project.setCreatedate(simpleDateFormat.format(new Date()));
        //设置状态
        project.setStatus(auth.getCode() + "");
        projectRepository.save(project);

        //保存预览图
        TProjectImages head = new TProjectImages(null, project.getId(), vo.getHeaderImage(), ProjectImageTypeEnume.HEAD.getCode());
        projectImagesRepository.save(head);
        //保存详情图
        for (String url : vo.getDetailsImage()) {
            TProjectImages detail = new TProjectImages(null, project.getId(), url, ProjectImageTypeEnume.DETAIL.getCode());
            projectImagesRepository.save(detail);
        }
        //保存标签
        for (Integer tagId : vo.getTagids()) {
            TProjectTag tProjectTag = new TProjectTag(null, project.getId(), tagId);
            projectTagRepository.save(tProjectTag);
        }
        //保存分类
        for (Integer typeId : vo.getTypeids()) {
            TProjectType tProjectType = new TProjectType(null, project.getId(), typeId);
            projectTypeRepository.save(tProjectType);
        }
        //保存回报
        for (TReturn tReturn : vo.getProjectReturns()) {
            tReturn.setProjectid(project.getId());
            returnRepository.save(tReturn);
        }
        //删除缓存
        redisTemplate.delete(profix + vo.getProjectToken());
    }

}

