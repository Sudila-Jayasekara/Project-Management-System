package com.sudila.service;

import com.sudila.modal.Chat;
import com.sudila.modal.Project;
import com.sudila.modal.User;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project, User user) throws Exception;

    List<Project> getProjectByTeam(User user,String category,String tag) throws Exception;

    Project getProjectById(Long projectId) throws Exception;

    Void deleteProject(Long projectId, Long userId) throws Exception; //userid to check project owner and requesting user are same or not

    Project updateProject(Project updatedProject, Long id) throws Exception;

    void addUserToProject(Long projectId, Long userId) throws Exception;

    void removeUserFromProject(Long projectId, Long userId) throws Exception;

    Chat getChatByProjectId(Long projectId) throws Exception;

    List<Project> searchProject(String keyword, User user) throws Exception;
}

