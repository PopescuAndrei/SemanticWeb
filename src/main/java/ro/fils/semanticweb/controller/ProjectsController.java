/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.fils.semanticweb.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.fils.semanticweb.domain.Project;
import ro.fils.semanticweb.repository.ProjectsDocumentRepository;
import ro.fils.semanticweb.util.ProjectConverter;

/**
 *
 * @author Vlad
 */

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    private ProjectConverter projectConverter;

    @Autowired
    ProjectsDocumentRepository projectsDocumentRepository;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<Project> getAllProjects() {
        projectConverter = new ProjectConverter();
        return projectConverter.readAllProjects(projectsDocumentRepository.findOne("564f596becece47bba5ff133").getContent());

    }
}
