package com.dev.qlda.controller;

import com.dev.qlda.entity.Projects;
import com.dev.qlda.entity.Users;
import com.dev.qlda.model.ProjectProgress;
import com.dev.qlda.repo.ProjectRepo;
import com.dev.qlda.repo.UserRepo;
import com.dev.qlda.request.CreateProjectsRequest;
import com.dev.qlda.response.WrapResponse;
import com.dev.qlda.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;

    @PostMapping("/create")
    public WrapResponse<?> create(@RequestBody CreateProjectsRequest request){
        Projects projects = MappingUtils.mapObject(request, Projects.class);
        projects.setId(UUID.randomUUID().toString());
        return  WrapResponse.ok("Tao du an thanh cong", projectRepo.save(projects));
    }

    @PostMapping("/update/{id}")
    public WrapResponse<?> update(@PathVariable String id, @RequestBody CreateProjectsRequest request){
        Projects projects = projectRepo.findById(id).orElse(null);
        if (projects != null) {
            return WrapResponse.error("Du an khong ton tai");
        }
        Users users = userRepo.findByDepartment("QUAN LY DU AN");
        if (users == null) {
            users = Users.builder().id("VANG LAI").fullName("VANG LAI").build();
        }
        projects.setName(request.getName());
        projects.setPrice(request.getPrice());
        projects.setStatus(request.getStatus());
        projects.setStartDate(request.getStartDate());
        projects.setEndDate(request.getEndDate());
        projects.setTakeChargeId(users.getId());
        projects.setTakeChargeName(users.getFullName());
        return WrapResponse.ok("Cap nhat thong tin du an thanh cong", projectRepo.save(projects));
    }

    @PostMapping("/update-progress/{id}")
    public WrapResponse<?> updateProgress(@PathVariable String id, @RequestBody ProjectProgress request){
        Projects projects = projectRepo.findById(id).orElse(null);
        if (projects == null) {
            return WrapResponse.error("Du an khong ton tai");
        }
        projects.getProjectProgresses().add(request);
        if (CollectionUtils.isNotEmpty(projects.getProjectProgresses())) {
            projects.setRealPrice(projects.getProjectProgresses().stream().mapToDouble(ProjectProgress::getProjectPrice).sum());
        }
        return WrapResponse.ok("Cap nhat tien do du an thanh cong", projectRepo.save(projects));
    }

    @PostMapping("/delete/{id}")
    public WrapResponse<?> delete(@PathVariable String id){
        Projects projects = projectRepo.findById(id).orElse(null);
        if (projects == null) {
            return WrapResponse.error("Du an khong ton tai");
        }
        projectRepo.delete(projects);
        return WrapResponse.ok("Xoa du an thanh cong");
    }

    @GetMapping("/{id}")
    public WrapResponse<?> get(@PathVariable String id){
        return WrapResponse.ok(projectRepo.findById(id).orElse(null));
    }
}
