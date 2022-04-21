package com.crux.crowd.member.service.task;

import com.crux.crowd.member.entity.po.ProjectPO;
import com.crux.crowd.member.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目定时器。每天4点检查所有项目的过期时间；
 * 如果已经过期：如果众筹成功，项目更新为众筹成功；如果未众筹成功，更新为众筹失败。
 */
@Slf4j
@Component
public class ProjectTask{

    private final ProjectService projectService;
    private final String taskName = getClass().getSimpleName();

    public ProjectTask(ProjectService projectService){
        this.projectService = projectService;
        log.info("[守护线程]  {}定时器已开启！将于每天4:00更新所有项目的筹集状态", taskName);
    }

    /**
     * 每天4点检查所有项目状态并更新
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void checkExpired(){
        log.info("==> {}：准备更新所有项目状态 <==", taskName);

        List<ProjectPO> projectAll = projectService.list();
        List<ProjectPO> updateProjectList = projectAll.stream()
                .filter(this::isExpired)
                .peek(projectPO -> {
                    switch(projectPO.getMoney().compareTo(projectPO.getSupportMoney())){
                        case 1 : projectPO.setStatus(ProjectPO.Status.FAILED);break;
                        case 0 : case -1 : projectPO.setStatus(ProjectPO.Status.SUCCESS);break;
                    }})
                .collect(Collectors.toList());

        if(updateProjectList.isEmpty()){
            log.info("==> {}：没有项目的状态需要更新！ <==", taskName);
            return;
        }
        boolean result = projectService.updateBatchById(updateProjectList);

        if(result) log.info("==> {}：所有项目的状态已更新！ <==", taskName);
        else log.error("==> {}：未能成功更新项目状态，请检查服务器！ <==", taskName);
    }

    /**
     * 检查project是否过期
     * @param projectPO 查询到的project
     * @return 如果过期，返回true
     */
    private boolean isExpired(ProjectPO projectPO){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = projectPO.getDeployDate().plusDays(projectPO.getDay());
        return now.isAfter(deadline);
    }
}
