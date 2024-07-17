package com.sudila.controller;

import com.sudila.modal.Chat;
import com.sudila.modal.Project;
import com.sudila.modal.User;
import com.sudila.response.MessageResponse;
import com.sudila.service.ProjectService;
import com.sudila.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false)String category,
            @RequestParam(required = false)String tag,
            @RequestHeader("Authorization")String jwt
    ) throws Exception  {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user, category, tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);

    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId,
            @RequestHeader("Authorization")String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestBody Project project,
            @RequestHeader("Authorization")String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project,user);
        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @RequestBody Project project,
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long projectId
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Project updatedProject = projectService.updateProject(project,projectId);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long projectId
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId,user.getId());
        MessageResponse res = new MessageResponse("Project deleted successfully");
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(
            @RequestParam(required = false)String keyword,
            @RequestHeader("Authorization")String jwt
    ) throws Exception  {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProject(keyword,user);
        return new ResponseEntity<>(projects, HttpStatus.OK);

    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long projectId

    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }
}
