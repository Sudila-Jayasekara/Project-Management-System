package com.sudila.repository;

import com.sudila.modal.Project;
import com.sudila.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

//    List<Project>findByOwner(User user);  //get list of project owner

    List<Project>findByNameContainingAndTeamContains(String partialName, User user); //for search

//    @Query("SELECT p FROM project p join p.team t where t=:user")
//    List<Project>findProjectByTeam(@Param("user") User user);

    List<Project>findByTeamContainingOrOwner(User user, User owner);
}
